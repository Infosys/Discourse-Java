/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicInvites;
import com.infy.repository.TopicInvitesRepository;
import com.infy.service.TopicInvitesService;
import com.infy.service.dto.TopicInvitesDTO;
import com.infy.service.mapper.TopicInvitesMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopicInvitesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicInvitesResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_INVITE_ID = 1L;
    private static final Long UPDATED_INVITE_ID = 2L;

    @Autowired
    private TopicInvitesRepository topicInvitesRepository;

    @Autowired
    private TopicInvitesMapper topicInvitesMapper;

    @Autowired
    private TopicInvitesService topicInvitesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicInvitesMockMvc;

    private TopicInvites topicInvites;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicInvites createEntity(EntityManager em) {
        TopicInvites topicInvites = new TopicInvites()
            .topicId(DEFAULT_TOPIC_ID)
            .inviteId(DEFAULT_INVITE_ID);
        return topicInvites;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicInvites createUpdatedEntity(EntityManager em) {
        TopicInvites topicInvites = new TopicInvites()
            .topicId(UPDATED_TOPIC_ID)
            .inviteId(UPDATED_INVITE_ID);
        return topicInvites;
    }

    @BeforeEach
    public void initTest() {
        topicInvites = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicInvites() throws Exception {
        int databaseSizeBeforeCreate = topicInvitesRepository.findAll().size();
        // Create the TopicInvites
        TopicInvitesDTO topicInvitesDTO = topicInvitesMapper.toDto(topicInvites);
        restTopicInvitesMockMvc.perform(post("/api/topic-invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicInvitesDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicInvites in the database
        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeCreate + 1);
        TopicInvites testTopicInvites = topicInvitesList.get(topicInvitesList.size() - 1);
        assertThat(testTopicInvites.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicInvites.getInviteId()).isEqualTo(DEFAULT_INVITE_ID);
    }

    @Test
    @Transactional
    public void createTopicInvitesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicInvitesRepository.findAll().size();

        // Create the TopicInvites with an existing ID
        topicInvites.setId(1L);
        TopicInvitesDTO topicInvitesDTO = topicInvitesMapper.toDto(topicInvites);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicInvitesMockMvc.perform(post("/api/topic-invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicInvitesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicInvites in the database
        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicInvitesRepository.findAll().size();
        // set the field null
        topicInvites.setTopicId(null);

        // Create the TopicInvites, which fails.
        TopicInvitesDTO topicInvitesDTO = topicInvitesMapper.toDto(topicInvites);


        restTopicInvitesMockMvc.perform(post("/api/topic-invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicInvitesDTO)))
            .andExpect(status().isBadRequest());

        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInviteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicInvitesRepository.findAll().size();
        // set the field null
        topicInvites.setInviteId(null);

        // Create the TopicInvites, which fails.
        TopicInvitesDTO topicInvitesDTO = topicInvitesMapper.toDto(topicInvites);


        restTopicInvitesMockMvc.perform(post("/api/topic-invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicInvitesDTO)))
            .andExpect(status().isBadRequest());

        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicInvites() throws Exception {
        // Initialize the database
        topicInvitesRepository.saveAndFlush(topicInvites);

        // Get all the topicInvitesList
        restTopicInvitesMockMvc.perform(get("/api/topic-invites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicInvites.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].inviteId").value(hasItem(DEFAULT_INVITE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTopicInvites() throws Exception {
        // Initialize the database
        topicInvitesRepository.saveAndFlush(topicInvites);

        // Get the topicInvites
        restTopicInvitesMockMvc.perform(get("/api/topic-invites/{id}", topicInvites.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicInvites.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.inviteId").value(DEFAULT_INVITE_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTopicInvites() throws Exception {
        // Get the topicInvites
        restTopicInvitesMockMvc.perform(get("/api/topic-invites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicInvites() throws Exception {
        // Initialize the database
        topicInvitesRepository.saveAndFlush(topicInvites);

        int databaseSizeBeforeUpdate = topicInvitesRepository.findAll().size();

        // Update the topicInvites
        TopicInvites updatedTopicInvites = topicInvitesRepository.findById(topicInvites.getId()).get();
        // Disconnect from session so that the updates on updatedTopicInvites are not directly saved in db
        em.detach(updatedTopicInvites);
        updatedTopicInvites
            .topicId(UPDATED_TOPIC_ID)
            .inviteId(UPDATED_INVITE_ID);
        TopicInvitesDTO topicInvitesDTO = topicInvitesMapper.toDto(updatedTopicInvites);

        restTopicInvitesMockMvc.perform(put("/api/topic-invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicInvitesDTO)))
            .andExpect(status().isOk());

        // Validate the TopicInvites in the database
        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeUpdate);
        TopicInvites testTopicInvites = topicInvitesList.get(topicInvitesList.size() - 1);
        assertThat(testTopicInvites.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicInvites.getInviteId()).isEqualTo(UPDATED_INVITE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicInvites() throws Exception {
        int databaseSizeBeforeUpdate = topicInvitesRepository.findAll().size();

        // Create the TopicInvites
        TopicInvitesDTO topicInvitesDTO = topicInvitesMapper.toDto(topicInvites);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicInvitesMockMvc.perform(put("/api/topic-invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicInvitesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicInvites in the database
        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicInvites() throws Exception {
        // Initialize the database
        topicInvitesRepository.saveAndFlush(topicInvites);

        int databaseSizeBeforeDelete = topicInvitesRepository.findAll().size();

        // Delete the topicInvites
        restTopicInvitesMockMvc.perform(delete("/api/topic-invites/{id}", topicInvites.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicInvites> topicInvitesList = topicInvitesRepository.findAll();
        assertThat(topicInvitesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
