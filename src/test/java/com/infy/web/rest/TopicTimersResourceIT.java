/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicTimers;
import com.infy.repository.TopicTimersRepository;
import com.infy.service.TopicTimersService;
import com.infy.service.dto.TopicTimersDTO;
import com.infy.service.mapper.TopicTimersMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopicTimersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicTimersResourceIT {

    private static final Instant DEFAULT_EXECUTE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS_TYPE = 1;
    private static final Integer UPDATED_STATUS_TYPE = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Boolean DEFAULT_BASED_ON_LAST_POST = false;
    private static final Boolean UPDATED_BASED_ON_LAST_POST = true;

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Boolean DEFAULT_PUBLIC_TYPE = false;
    private static final Boolean UPDATED_PUBLIC_TYPE = true;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_DURATION_MINUTES = 1;
    private static final Integer UPDATED_DURATION_MINUTES = 2;

    @Autowired
    private TopicTimersRepository topicTimersRepository;

    @Autowired
    private TopicTimersMapper topicTimersMapper;

    @Autowired
    private TopicTimersService topicTimersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicTimersMockMvc;

    private TopicTimers topicTimers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicTimers createEntity(EntityManager em) {
        TopicTimers topicTimers = new TopicTimers()
            .executeAt(DEFAULT_EXECUTE_AT)
            .statusType(DEFAULT_STATUS_TYPE)
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .basedOnLastPost(DEFAULT_BASED_ON_LAST_POST)
            .deletedAt(DEFAULT_DELETED_AT)
            .deletedById(DEFAULT_DELETED_BY_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .publicType(DEFAULT_PUBLIC_TYPE)
            .duration(DEFAULT_DURATION)
            .durationMinutes(DEFAULT_DURATION_MINUTES);
        return topicTimers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicTimers createUpdatedEntity(EntityManager em) {
        TopicTimers topicTimers = new TopicTimers()
            .executeAt(UPDATED_EXECUTE_AT)
            .statusType(UPDATED_STATUS_TYPE)
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .basedOnLastPost(UPDATED_BASED_ON_LAST_POST)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .publicType(UPDATED_PUBLIC_TYPE)
            .duration(UPDATED_DURATION)
            .durationMinutes(UPDATED_DURATION_MINUTES);
        return topicTimers;
    }

    @BeforeEach
    public void initTest() {
        topicTimers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicTimers() throws Exception {
        int databaseSizeBeforeCreate = topicTimersRepository.findAll().size();
        // Create the TopicTimers
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);
        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicTimers in the database
        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeCreate + 1);
        TopicTimers testTopicTimers = topicTimersList.get(topicTimersList.size() - 1);
        assertThat(testTopicTimers.getExecuteAt()).isEqualTo(DEFAULT_EXECUTE_AT);
        assertThat(testTopicTimers.getStatusType()).isEqualTo(DEFAULT_STATUS_TYPE);
        assertThat(testTopicTimers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopicTimers.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicTimers.isBasedOnLastPost()).isEqualTo(DEFAULT_BASED_ON_LAST_POST);
        assertThat(testTopicTimers.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testTopicTimers.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
        assertThat(testTopicTimers.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testTopicTimers.isPublicType()).isEqualTo(DEFAULT_PUBLIC_TYPE);
        assertThat(testTopicTimers.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTopicTimers.getDurationMinutes()).isEqualTo(DEFAULT_DURATION_MINUTES);
    }

    @Test
    @Transactional
    public void createTopicTimersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicTimersRepository.findAll().size();

        // Create the TopicTimers with an existing ID
        topicTimers.setId(1L);
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicTimers in the database
        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExecuteAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTimersRepository.findAll().size();
        // set the field null
        topicTimers.setExecuteAt(null);

        // Create the TopicTimers, which fails.
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);


        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTimersRepository.findAll().size();
        // set the field null
        topicTimers.setStatusType(null);

        // Create the TopicTimers, which fails.
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);


        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTimersRepository.findAll().size();
        // set the field null
        topicTimers.setUserId(null);

        // Create the TopicTimers, which fails.
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);


        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTimersRepository.findAll().size();
        // set the field null
        topicTimers.setTopicId(null);

        // Create the TopicTimers, which fails.
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);


        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBasedOnLastPostIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTimersRepository.findAll().size();
        // set the field null
        topicTimers.setBasedOnLastPost(null);

        // Create the TopicTimers, which fails.
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);


        restTopicTimersMockMvc.perform(post("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicTimers() throws Exception {
        // Initialize the database
        topicTimersRepository.saveAndFlush(topicTimers);

        // Get all the topicTimersList
        restTopicTimersMockMvc.perform(get("/api/topic-timers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicTimers.getId().intValue())))
            .andExpect(jsonPath("$.[*].executeAt").value(hasItem(DEFAULT_EXECUTE_AT.toString())))
            .andExpect(jsonPath("$.[*].statusType").value(hasItem(DEFAULT_STATUS_TYPE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].basedOnLastPost").value(hasItem(DEFAULT_BASED_ON_LAST_POST.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].publicType").value(hasItem(DEFAULT_PUBLIC_TYPE.booleanValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].durationMinutes").value(hasItem(DEFAULT_DURATION_MINUTES)));
    }

    @Test
    @Transactional
    public void getTopicTimers() throws Exception {
        // Initialize the database
        topicTimersRepository.saveAndFlush(topicTimers);

        // Get the topicTimers
        restTopicTimersMockMvc.perform(get("/api/topic-timers/{id}", topicTimers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicTimers.getId().intValue()))
            .andExpect(jsonPath("$.executeAt").value(DEFAULT_EXECUTE_AT.toString()))
            .andExpect(jsonPath("$.statusType").value(DEFAULT_STATUS_TYPE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.basedOnLastPost").value(DEFAULT_BASED_ON_LAST_POST.booleanValue()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.deletedById").value(DEFAULT_DELETED_BY_ID))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.publicType").value(DEFAULT_PUBLIC_TYPE.booleanValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.durationMinutes").value(DEFAULT_DURATION_MINUTES));
    }
    @Test
    @Transactional
    public void getNonExistingTopicTimers() throws Exception {
        // Get the topicTimers
        restTopicTimersMockMvc.perform(get("/api/topic-timers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicTimers() throws Exception {
        // Initialize the database
        topicTimersRepository.saveAndFlush(topicTimers);

        int databaseSizeBeforeUpdate = topicTimersRepository.findAll().size();

        // Update the topicTimers
        TopicTimers updatedTopicTimers = topicTimersRepository.findById(topicTimers.getId()).get();
        // Disconnect from session so that the updates on updatedTopicTimers are not directly saved in db
        em.detach(updatedTopicTimers);
        updatedTopicTimers
            .executeAt(UPDATED_EXECUTE_AT)
            .statusType(UPDATED_STATUS_TYPE)
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .basedOnLastPost(UPDATED_BASED_ON_LAST_POST)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .publicType(UPDATED_PUBLIC_TYPE)
            .duration(UPDATED_DURATION)
            .durationMinutes(UPDATED_DURATION_MINUTES);
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(updatedTopicTimers);

        restTopicTimersMockMvc.perform(put("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isOk());

        // Validate the TopicTimers in the database
        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeUpdate);
        TopicTimers testTopicTimers = topicTimersList.get(topicTimersList.size() - 1);
        assertThat(testTopicTimers.getExecuteAt()).isEqualTo(UPDATED_EXECUTE_AT);
        assertThat(testTopicTimers.getStatusType()).isEqualTo(UPDATED_STATUS_TYPE);
        assertThat(testTopicTimers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopicTimers.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicTimers.isBasedOnLastPost()).isEqualTo(UPDATED_BASED_ON_LAST_POST);
        assertThat(testTopicTimers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testTopicTimers.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
        assertThat(testTopicTimers.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testTopicTimers.isPublicType()).isEqualTo(UPDATED_PUBLIC_TYPE);
        assertThat(testTopicTimers.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTopicTimers.getDurationMinutes()).isEqualTo(UPDATED_DURATION_MINUTES);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicTimers() throws Exception {
        int databaseSizeBeforeUpdate = topicTimersRepository.findAll().size();

        // Create the TopicTimers
        TopicTimersDTO topicTimersDTO = topicTimersMapper.toDto(topicTimers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicTimersMockMvc.perform(put("/api/topic-timers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTimersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicTimers in the database
        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicTimers() throws Exception {
        // Initialize the database
        topicTimersRepository.saveAndFlush(topicTimers);

        int databaseSizeBeforeDelete = topicTimersRepository.findAll().size();

        // Delete the topicTimers
        restTopicTimersMockMvc.perform(delete("/api/topic-timers/{id}", topicTimers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicTimers> topicTimersList = topicTimersRepository.findAll();
        assertThat(topicTimersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
