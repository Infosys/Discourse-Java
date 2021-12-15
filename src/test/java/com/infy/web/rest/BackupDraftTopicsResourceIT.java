/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.BackupDraftTopics;
import com.infy.repository.BackupDraftTopicsRepository;
import com.infy.service.BackupDraftTopicsService;
import com.infy.service.dto.BackupDraftTopicsDTO;
import com.infy.service.mapper.BackupDraftTopicsMapper;

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
 * Integration tests for the {@link BackupDraftTopicsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BackupDraftTopicsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private BackupDraftTopicsRepository backupDraftTopicsRepository;

    @Autowired
    private BackupDraftTopicsMapper backupDraftTopicsMapper;

    @Autowired
    private BackupDraftTopicsService backupDraftTopicsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBackupDraftTopicsMockMvc;

    private BackupDraftTopics backupDraftTopics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackupDraftTopics createEntity(EntityManager em) {
        BackupDraftTopics backupDraftTopics = new BackupDraftTopics()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return backupDraftTopics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackupDraftTopics createUpdatedEntity(EntityManager em) {
        BackupDraftTopics backupDraftTopics = new BackupDraftTopics()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        return backupDraftTopics;
    }

    @BeforeEach
    public void initTest() {
        backupDraftTopics = createEntity(em);
    }

    @Test
    @Transactional
    public void createBackupDraftTopics() throws Exception {
        int databaseSizeBeforeCreate = backupDraftTopicsRepository.findAll().size();
        // Create the BackupDraftTopics
        BackupDraftTopicsDTO backupDraftTopicsDTO = backupDraftTopicsMapper.toDto(backupDraftTopics);
        restBackupDraftTopicsMockMvc.perform(post("/api/backup-draft-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftTopicsDTO)))
            .andExpect(status().isCreated());

        // Validate the BackupDraftTopics in the database
        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeCreate + 1);
        BackupDraftTopics testBackupDraftTopics = backupDraftTopicsList.get(backupDraftTopicsList.size() - 1);
        assertThat(testBackupDraftTopics.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBackupDraftTopics.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createBackupDraftTopicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backupDraftTopicsRepository.findAll().size();

        // Create the BackupDraftTopics with an existing ID
        backupDraftTopics.setId(1L);
        BackupDraftTopicsDTO backupDraftTopicsDTO = backupDraftTopicsMapper.toDto(backupDraftTopics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackupDraftTopicsMockMvc.perform(post("/api/backup-draft-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackupDraftTopics in the database
        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = backupDraftTopicsRepository.findAll().size();
        // set the field null
        backupDraftTopics.setUserId(null);

        // Create the BackupDraftTopics, which fails.
        BackupDraftTopicsDTO backupDraftTopicsDTO = backupDraftTopicsMapper.toDto(backupDraftTopics);


        restBackupDraftTopicsMockMvc.perform(post("/api/backup-draft-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = backupDraftTopicsRepository.findAll().size();
        // set the field null
        backupDraftTopics.setTopicId(null);

        // Create the BackupDraftTopics, which fails.
        BackupDraftTopicsDTO backupDraftTopicsDTO = backupDraftTopicsMapper.toDto(backupDraftTopics);


        restBackupDraftTopicsMockMvc.perform(post("/api/backup-draft-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBackupDraftTopics() throws Exception {
        // Initialize the database
        backupDraftTopicsRepository.saveAndFlush(backupDraftTopics);

        // Get all the backupDraftTopicsList
        restBackupDraftTopicsMockMvc.perform(get("/api/backup-draft-topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backupDraftTopics.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBackupDraftTopics() throws Exception {
        // Initialize the database
        backupDraftTopicsRepository.saveAndFlush(backupDraftTopics);

        // Get the backupDraftTopics
        restBackupDraftTopicsMockMvc.perform(get("/api/backup-draft-topics/{id}", backupDraftTopics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(backupDraftTopics.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBackupDraftTopics() throws Exception {
        // Get the backupDraftTopics
        restBackupDraftTopicsMockMvc.perform(get("/api/backup-draft-topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackupDraftTopics() throws Exception {
        // Initialize the database
        backupDraftTopicsRepository.saveAndFlush(backupDraftTopics);

        int databaseSizeBeforeUpdate = backupDraftTopicsRepository.findAll().size();

        // Update the backupDraftTopics
        BackupDraftTopics updatedBackupDraftTopics = backupDraftTopicsRepository.findById(backupDraftTopics.getId()).get();
        // Disconnect from session so that the updates on updatedBackupDraftTopics are not directly saved in db
        em.detach(updatedBackupDraftTopics);
        updatedBackupDraftTopics
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        BackupDraftTopicsDTO backupDraftTopicsDTO = backupDraftTopicsMapper.toDto(updatedBackupDraftTopics);

        restBackupDraftTopicsMockMvc.perform(put("/api/backup-draft-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftTopicsDTO)))
            .andExpect(status().isOk());

        // Validate the BackupDraftTopics in the database
        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeUpdate);
        BackupDraftTopics testBackupDraftTopics = backupDraftTopicsList.get(backupDraftTopicsList.size() - 1);
        assertThat(testBackupDraftTopics.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBackupDraftTopics.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBackupDraftTopics() throws Exception {
        int databaseSizeBeforeUpdate = backupDraftTopicsRepository.findAll().size();

        // Create the BackupDraftTopics
        BackupDraftTopicsDTO backupDraftTopicsDTO = backupDraftTopicsMapper.toDto(backupDraftTopics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBackupDraftTopicsMockMvc.perform(put("/api/backup-draft-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackupDraftTopics in the database
        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBackupDraftTopics() throws Exception {
        // Initialize the database
        backupDraftTopicsRepository.saveAndFlush(backupDraftTopics);

        int databaseSizeBeforeDelete = backupDraftTopicsRepository.findAll().size();

        // Delete the backupDraftTopics
        restBackupDraftTopicsMockMvc.perform(delete("/api/backup-draft-topics/{id}", backupDraftTopics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BackupDraftTopics> backupDraftTopicsList = backupDraftTopicsRepository.findAll();
        assertThat(backupDraftTopicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
