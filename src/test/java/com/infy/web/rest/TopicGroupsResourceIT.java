/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicGroups;
import com.infy.repository.TopicGroupsRepository;
import com.infy.service.TopicGroupsService;
import com.infy.service.dto.TopicGroupsDTO;
import com.infy.service.mapper.TopicGroupsMapper;

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
 * Integration tests for the {@link TopicGroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicGroupsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_LAST_READ_POST_NUMBER = 1L;
    private static final Long UPDATED_LAST_READ_POST_NUMBER = 2L;

    @Autowired
    private TopicGroupsRepository topicGroupsRepository;

    @Autowired
    private TopicGroupsMapper topicGroupsMapper;

    @Autowired
    private TopicGroupsService topicGroupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicGroupsMockMvc;

    private TopicGroups topicGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicGroups createEntity(EntityManager em) {
        TopicGroups topicGroups = new TopicGroups()
            .groupId(DEFAULT_GROUP_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .lastReadPostNumber(DEFAULT_LAST_READ_POST_NUMBER);
        return topicGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicGroups createUpdatedEntity(EntityManager em) {
        TopicGroups topicGroups = new TopicGroups()
            .groupId(UPDATED_GROUP_ID)
            .topicId(UPDATED_TOPIC_ID)
            .lastReadPostNumber(UPDATED_LAST_READ_POST_NUMBER);
        return topicGroups;
    }

    @BeforeEach
    public void initTest() {
        topicGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicGroups() throws Exception {
        int databaseSizeBeforeCreate = topicGroupsRepository.findAll().size();
        // Create the TopicGroups
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(topicGroups);
        restTopicGroupsMockMvc.perform(post("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicGroups in the database
        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicGroups testTopicGroups = topicGroupsList.get(topicGroupsList.size() - 1);
        assertThat(testTopicGroups.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testTopicGroups.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicGroups.getLastReadPostNumber()).isEqualTo(DEFAULT_LAST_READ_POST_NUMBER);
    }

    @Test
    @Transactional
    public void createTopicGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicGroupsRepository.findAll().size();

        // Create the TopicGroups with an existing ID
        topicGroups.setId(1L);
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(topicGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicGroupsMockMvc.perform(post("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicGroups in the database
        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicGroupsRepository.findAll().size();
        // set the field null
        topicGroups.setGroupId(null);

        // Create the TopicGroups, which fails.
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(topicGroups);


        restTopicGroupsMockMvc.perform(post("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicGroupsRepository.findAll().size();
        // set the field null
        topicGroups.setTopicId(null);

        // Create the TopicGroups, which fails.
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(topicGroups);


        restTopicGroupsMockMvc.perform(post("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastReadPostNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicGroupsRepository.findAll().size();
        // set the field null
        topicGroups.setLastReadPostNumber(null);

        // Create the TopicGroups, which fails.
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(topicGroups);


        restTopicGroupsMockMvc.perform(post("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicGroups() throws Exception {
        // Initialize the database
        topicGroupsRepository.saveAndFlush(topicGroups);

        // Get all the topicGroupsList
        restTopicGroupsMockMvc.perform(get("/api/topic-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastReadPostNumber").value(hasItem(DEFAULT_LAST_READ_POST_NUMBER.intValue())));
    }

    @Test
    @Transactional
    public void getTopicGroups() throws Exception {
        // Initialize the database
        topicGroupsRepository.saveAndFlush(topicGroups);

        // Get the topicGroups
        restTopicGroupsMockMvc.perform(get("/api/topic-groups/{id}", topicGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicGroups.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.lastReadPostNumber").value(DEFAULT_LAST_READ_POST_NUMBER.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTopicGroups() throws Exception {
        // Get the topicGroups
        restTopicGroupsMockMvc.perform(get("/api/topic-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicGroups() throws Exception {
        // Initialize the database
        topicGroupsRepository.saveAndFlush(topicGroups);

        int databaseSizeBeforeUpdate = topicGroupsRepository.findAll().size();

        // Update the topicGroups
        TopicGroups updatedTopicGroups = topicGroupsRepository.findById(topicGroups.getId()).get();
        // Disconnect from session so that the updates on updatedTopicGroups are not directly saved in db
        em.detach(updatedTopicGroups);
        updatedTopicGroups
            .groupId(UPDATED_GROUP_ID)
            .topicId(UPDATED_TOPIC_ID)
            .lastReadPostNumber(UPDATED_LAST_READ_POST_NUMBER);
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(updatedTopicGroups);

        restTopicGroupsMockMvc.perform(put("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicGroups in the database
        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeUpdate);
        TopicGroups testTopicGroups = topicGroupsList.get(topicGroupsList.size() - 1);
        assertThat(testTopicGroups.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testTopicGroups.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicGroups.getLastReadPostNumber()).isEqualTo(UPDATED_LAST_READ_POST_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicGroups() throws Exception {
        int databaseSizeBeforeUpdate = topicGroupsRepository.findAll().size();

        // Create the TopicGroups
        TopicGroupsDTO topicGroupsDTO = topicGroupsMapper.toDto(topicGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicGroupsMockMvc.perform(put("/api/topic-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicGroups in the database
        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicGroups() throws Exception {
        // Initialize the database
        topicGroupsRepository.saveAndFlush(topicGroups);

        int databaseSizeBeforeDelete = topicGroupsRepository.findAll().size();

        // Delete the topicGroups
        restTopicGroupsMockMvc.perform(delete("/api/topic-groups/{id}", topicGroups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicGroups> topicGroupsList = topicGroupsRepository.findAll();
        assertThat(topicGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
