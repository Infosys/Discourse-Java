/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicViews;
import com.infy.repository.TopicViewsRepository;
import com.infy.service.TopicViewsService;
import com.infy.service.dto.TopicViewsDTO;
import com.infy.service.mapper.TopicViewsMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopicViewsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicViewsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final LocalDate DEFAULT_VIEWED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VIEWED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private TopicViewsRepository topicViewsRepository;

    @Autowired
    private TopicViewsMapper topicViewsMapper;

    @Autowired
    private TopicViewsService topicViewsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicViewsMockMvc;

    private TopicViews topicViews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicViews createEntity(EntityManager em) {
        TopicViews topicViews = new TopicViews()
            .topicId(DEFAULT_TOPIC_ID)
            .viewedAt(DEFAULT_VIEWED_AT)
            .userId(DEFAULT_USER_ID)
            .ipAddress(DEFAULT_IP_ADDRESS);
        return topicViews;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicViews createUpdatedEntity(EntityManager em) {
        TopicViews topicViews = new TopicViews()
            .topicId(UPDATED_TOPIC_ID)
            .viewedAt(UPDATED_VIEWED_AT)
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS);
        return topicViews;
    }

    @BeforeEach
    public void initTest() {
        topicViews = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicViews() throws Exception {
        int databaseSizeBeforeCreate = topicViewsRepository.findAll().size();
        // Create the TopicViews
        TopicViewsDTO topicViewsDTO = topicViewsMapper.toDto(topicViews);
        restTopicViewsMockMvc.perform(post("/api/topic-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicViewsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicViews in the database
        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicViews testTopicViews = topicViewsList.get(topicViewsList.size() - 1);
        assertThat(testTopicViews.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicViews.getViewedAt()).isEqualTo(DEFAULT_VIEWED_AT);
        assertThat(testTopicViews.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopicViews.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createTopicViewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicViewsRepository.findAll().size();

        // Create the TopicViews with an existing ID
        topicViews.setId(1L);
        TopicViewsDTO topicViewsDTO = topicViewsMapper.toDto(topicViews);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicViewsMockMvc.perform(post("/api/topic-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicViewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicViews in the database
        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicViewsRepository.findAll().size();
        // set the field null
        topicViews.setTopicId(null);

        // Create the TopicViews, which fails.
        TopicViewsDTO topicViewsDTO = topicViewsMapper.toDto(topicViews);


        restTopicViewsMockMvc.perform(post("/api/topic-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicViewsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViewedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicViewsRepository.findAll().size();
        // set the field null
        topicViews.setViewedAt(null);

        // Create the TopicViews, which fails.
        TopicViewsDTO topicViewsDTO = topicViewsMapper.toDto(topicViews);


        restTopicViewsMockMvc.perform(post("/api/topic-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicViewsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicViews() throws Exception {
        // Initialize the database
        topicViewsRepository.saveAndFlush(topicViews);

        // Get all the topicViewsList
        restTopicViewsMockMvc.perform(get("/api/topic-views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicViews.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].viewedAt").value(hasItem(DEFAULT_VIEWED_AT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)));
    }

    @Test
    @Transactional
    public void getTopicViews() throws Exception {
        // Initialize the database
        topicViewsRepository.saveAndFlush(topicViews);

        // Get the topicViews
        restTopicViewsMockMvc.perform(get("/api/topic-views/{id}", topicViews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicViews.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.viewedAt").value(DEFAULT_VIEWED_AT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingTopicViews() throws Exception {
        // Get the topicViews
        restTopicViewsMockMvc.perform(get("/api/topic-views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicViews() throws Exception {
        // Initialize the database
        topicViewsRepository.saveAndFlush(topicViews);

        int databaseSizeBeforeUpdate = topicViewsRepository.findAll().size();

        // Update the topicViews
        TopicViews updatedTopicViews = topicViewsRepository.findById(topicViews.getId()).get();
        // Disconnect from session so that the updates on updatedTopicViews are not directly saved in db
        em.detach(updatedTopicViews);
        updatedTopicViews
            .topicId(UPDATED_TOPIC_ID)
            .viewedAt(UPDATED_VIEWED_AT)
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS);
        TopicViewsDTO topicViewsDTO = topicViewsMapper.toDto(updatedTopicViews);

        restTopicViewsMockMvc.perform(put("/api/topic-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicViewsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicViews in the database
        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeUpdate);
        TopicViews testTopicViews = topicViewsList.get(topicViewsList.size() - 1);
        assertThat(testTopicViews.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicViews.getViewedAt()).isEqualTo(UPDATED_VIEWED_AT);
        assertThat(testTopicViews.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopicViews.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicViews() throws Exception {
        int databaseSizeBeforeUpdate = topicViewsRepository.findAll().size();

        // Create the TopicViews
        TopicViewsDTO topicViewsDTO = topicViewsMapper.toDto(topicViews);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicViewsMockMvc.perform(put("/api/topic-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicViewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicViews in the database
        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicViews() throws Exception {
        // Initialize the database
        topicViewsRepository.saveAndFlush(topicViews);

        int databaseSizeBeforeDelete = topicViewsRepository.findAll().size();

        // Delete the topicViews
        restTopicViewsMockMvc.perform(delete("/api/topic-views/{id}", topicViews.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicViews> topicViewsList = topicViewsRepository.findAll();
        assertThat(topicViewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
