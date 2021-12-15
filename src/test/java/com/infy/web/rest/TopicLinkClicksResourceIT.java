/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicLinkClicks;
import com.infy.repository.TopicLinkClicksRepository;
import com.infy.service.TopicLinkClicksService;
import com.infy.service.dto.TopicLinkClicksDTO;
import com.infy.service.mapper.TopicLinkClicksMapper;

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
 * Integration tests for the {@link TopicLinkClicksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicLinkClicksResourceIT {

    private static final Long DEFAULT_TOPIC_LINK_ID = 1L;
    private static final Long UPDATED_TOPIC_LINK_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private TopicLinkClicksRepository topicLinkClicksRepository;

    @Autowired
    private TopicLinkClicksMapper topicLinkClicksMapper;

    @Autowired
    private TopicLinkClicksService topicLinkClicksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicLinkClicksMockMvc;

    private TopicLinkClicks topicLinkClicks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicLinkClicks createEntity(EntityManager em) {
        TopicLinkClicks topicLinkClicks = new TopicLinkClicks()
            .topicLinkId(DEFAULT_TOPIC_LINK_ID)
            .userId(DEFAULT_USER_ID)
            .ipAddress(DEFAULT_IP_ADDRESS);
        return topicLinkClicks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicLinkClicks createUpdatedEntity(EntityManager em) {
        TopicLinkClicks topicLinkClicks = new TopicLinkClicks()
            .topicLinkId(UPDATED_TOPIC_LINK_ID)
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS);
        return topicLinkClicks;
    }

    @BeforeEach
    public void initTest() {
        topicLinkClicks = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicLinkClicks() throws Exception {
        int databaseSizeBeforeCreate = topicLinkClicksRepository.findAll().size();
        // Create the TopicLinkClicks
        TopicLinkClicksDTO topicLinkClicksDTO = topicLinkClicksMapper.toDto(topicLinkClicks);
        restTopicLinkClicksMockMvc.perform(post("/api/topic-link-clicks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinkClicksDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicLinkClicks in the database
        List<TopicLinkClicks> topicLinkClicksList = topicLinkClicksRepository.findAll();
        assertThat(topicLinkClicksList).hasSize(databaseSizeBeforeCreate + 1);
        TopicLinkClicks testTopicLinkClicks = topicLinkClicksList.get(topicLinkClicksList.size() - 1);
        assertThat(testTopicLinkClicks.getTopicLinkId()).isEqualTo(DEFAULT_TOPIC_LINK_ID);
        assertThat(testTopicLinkClicks.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopicLinkClicks.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createTopicLinkClicksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicLinkClicksRepository.findAll().size();

        // Create the TopicLinkClicks with an existing ID
        topicLinkClicks.setId(1L);
        TopicLinkClicksDTO topicLinkClicksDTO = topicLinkClicksMapper.toDto(topicLinkClicks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicLinkClicksMockMvc.perform(post("/api/topic-link-clicks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinkClicksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicLinkClicks in the database
        List<TopicLinkClicks> topicLinkClicksList = topicLinkClicksRepository.findAll();
        assertThat(topicLinkClicksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicLinkIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinkClicksRepository.findAll().size();
        // set the field null
        topicLinkClicks.setTopicLinkId(null);

        // Create the TopicLinkClicks, which fails.
        TopicLinkClicksDTO topicLinkClicksDTO = topicLinkClicksMapper.toDto(topicLinkClicks);


        restTopicLinkClicksMockMvc.perform(post("/api/topic-link-clicks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinkClicksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinkClicks> topicLinkClicksList = topicLinkClicksRepository.findAll();
        assertThat(topicLinkClicksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicLinkClicks() throws Exception {
        // Initialize the database
        topicLinkClicksRepository.saveAndFlush(topicLinkClicks);

        // Get all the topicLinkClicksList
        restTopicLinkClicksMockMvc.perform(get("/api/topic-link-clicks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicLinkClicks.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicLinkId").value(hasItem(DEFAULT_TOPIC_LINK_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)));
    }

    @Test
    @Transactional
    public void getTopicLinkClicks() throws Exception {
        // Initialize the database
        topicLinkClicksRepository.saveAndFlush(topicLinkClicks);

        // Get the topicLinkClicks
        restTopicLinkClicksMockMvc.perform(get("/api/topic-link-clicks/{id}", topicLinkClicks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicLinkClicks.getId().intValue()))
            .andExpect(jsonPath("$.topicLinkId").value(DEFAULT_TOPIC_LINK_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingTopicLinkClicks() throws Exception {
        // Get the topicLinkClicks
        restTopicLinkClicksMockMvc.perform(get("/api/topic-link-clicks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicLinkClicks() throws Exception {
        // Initialize the database
        topicLinkClicksRepository.saveAndFlush(topicLinkClicks);

        int databaseSizeBeforeUpdate = topicLinkClicksRepository.findAll().size();

        // Update the topicLinkClicks
        TopicLinkClicks updatedTopicLinkClicks = topicLinkClicksRepository.findById(topicLinkClicks.getId()).get();
        // Disconnect from session so that the updates on updatedTopicLinkClicks are not directly saved in db
        em.detach(updatedTopicLinkClicks);
        updatedTopicLinkClicks
            .topicLinkId(UPDATED_TOPIC_LINK_ID)
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS);
        TopicLinkClicksDTO topicLinkClicksDTO = topicLinkClicksMapper.toDto(updatedTopicLinkClicks);

        restTopicLinkClicksMockMvc.perform(put("/api/topic-link-clicks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinkClicksDTO)))
            .andExpect(status().isOk());

        // Validate the TopicLinkClicks in the database
        List<TopicLinkClicks> topicLinkClicksList = topicLinkClicksRepository.findAll();
        assertThat(topicLinkClicksList).hasSize(databaseSizeBeforeUpdate);
        TopicLinkClicks testTopicLinkClicks = topicLinkClicksList.get(topicLinkClicksList.size() - 1);
        assertThat(testTopicLinkClicks.getTopicLinkId()).isEqualTo(UPDATED_TOPIC_LINK_ID);
        assertThat(testTopicLinkClicks.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopicLinkClicks.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicLinkClicks() throws Exception {
        int databaseSizeBeforeUpdate = topicLinkClicksRepository.findAll().size();

        // Create the TopicLinkClicks
        TopicLinkClicksDTO topicLinkClicksDTO = topicLinkClicksMapper.toDto(topicLinkClicks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicLinkClicksMockMvc.perform(put("/api/topic-link-clicks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinkClicksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicLinkClicks in the database
        List<TopicLinkClicks> topicLinkClicksList = topicLinkClicksRepository.findAll();
        assertThat(topicLinkClicksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicLinkClicks() throws Exception {
        // Initialize the database
        topicLinkClicksRepository.saveAndFlush(topicLinkClicks);

        int databaseSizeBeforeDelete = topicLinkClicksRepository.findAll().size();

        // Delete the topicLinkClicks
        restTopicLinkClicksMockMvc.perform(delete("/api/topic-link-clicks/{id}", topicLinkClicks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicLinkClicks> topicLinkClicksList = topicLinkClicksRepository.findAll();
        assertThat(topicLinkClicksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
