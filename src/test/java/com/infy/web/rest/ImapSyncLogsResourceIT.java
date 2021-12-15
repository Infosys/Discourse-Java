/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ImapSyncLogs;
import com.infy.repository.ImapSyncLogsRepository;
import com.infy.service.ImapSyncLogsService;
import com.infy.service.dto.ImapSyncLogsDTO;
import com.infy.service.mapper.ImapSyncLogsMapper;

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
 * Integration tests for the {@link ImapSyncLogsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ImapSyncLogsResourceIT {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    @Autowired
    private ImapSyncLogsRepository imapSyncLogsRepository;

    @Autowired
    private ImapSyncLogsMapper imapSyncLogsMapper;

    @Autowired
    private ImapSyncLogsService imapSyncLogsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImapSyncLogsMockMvc;

    private ImapSyncLogs imapSyncLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImapSyncLogs createEntity(EntityManager em) {
        ImapSyncLogs imapSyncLogs = new ImapSyncLogs()
            .level(DEFAULT_LEVEL)
            .message(DEFAULT_MESSAGE)
            .groupId(DEFAULT_GROUP_ID);
        return imapSyncLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImapSyncLogs createUpdatedEntity(EntityManager em) {
        ImapSyncLogs imapSyncLogs = new ImapSyncLogs()
            .level(UPDATED_LEVEL)
            .message(UPDATED_MESSAGE)
            .groupId(UPDATED_GROUP_ID);
        return imapSyncLogs;
    }

    @BeforeEach
    public void initTest() {
        imapSyncLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createImapSyncLogs() throws Exception {
        int databaseSizeBeforeCreate = imapSyncLogsRepository.findAll().size();
        // Create the ImapSyncLogs
        ImapSyncLogsDTO imapSyncLogsDTO = imapSyncLogsMapper.toDto(imapSyncLogs);
        restImapSyncLogsMockMvc.perform(post("/api/imap-sync-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imapSyncLogsDTO)))
            .andExpect(status().isCreated());

        // Validate the ImapSyncLogs in the database
        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeCreate + 1);
        ImapSyncLogs testImapSyncLogs = imapSyncLogsList.get(imapSyncLogsList.size() - 1);
        assertThat(testImapSyncLogs.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testImapSyncLogs.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testImapSyncLogs.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
    }

    @Test
    @Transactional
    public void createImapSyncLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imapSyncLogsRepository.findAll().size();

        // Create the ImapSyncLogs with an existing ID
        imapSyncLogs.setId(1L);
        ImapSyncLogsDTO imapSyncLogsDTO = imapSyncLogsMapper.toDto(imapSyncLogs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImapSyncLogsMockMvc.perform(post("/api/imap-sync-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imapSyncLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImapSyncLogs in the database
        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = imapSyncLogsRepository.findAll().size();
        // set the field null
        imapSyncLogs.setLevel(null);

        // Create the ImapSyncLogs, which fails.
        ImapSyncLogsDTO imapSyncLogsDTO = imapSyncLogsMapper.toDto(imapSyncLogs);


        restImapSyncLogsMockMvc.perform(post("/api/imap-sync-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imapSyncLogsDTO)))
            .andExpect(status().isBadRequest());

        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = imapSyncLogsRepository.findAll().size();
        // set the field null
        imapSyncLogs.setMessage(null);

        // Create the ImapSyncLogs, which fails.
        ImapSyncLogsDTO imapSyncLogsDTO = imapSyncLogsMapper.toDto(imapSyncLogs);


        restImapSyncLogsMockMvc.perform(post("/api/imap-sync-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imapSyncLogsDTO)))
            .andExpect(status().isBadRequest());

        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImapSyncLogs() throws Exception {
        // Initialize the database
        imapSyncLogsRepository.saveAndFlush(imapSyncLogs);

        // Get all the imapSyncLogsList
        restImapSyncLogsMockMvc.perform(get("/api/imap-sync-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imapSyncLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())));
    }

    @Test
    @Transactional
    public void getImapSyncLogs() throws Exception {
        // Initialize the database
        imapSyncLogsRepository.saveAndFlush(imapSyncLogs);

        // Get the imapSyncLogs
        restImapSyncLogsMockMvc.perform(get("/api/imap-sync-logs/{id}", imapSyncLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imapSyncLogs.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingImapSyncLogs() throws Exception {
        // Get the imapSyncLogs
        restImapSyncLogsMockMvc.perform(get("/api/imap-sync-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImapSyncLogs() throws Exception {
        // Initialize the database
        imapSyncLogsRepository.saveAndFlush(imapSyncLogs);

        int databaseSizeBeforeUpdate = imapSyncLogsRepository.findAll().size();

        // Update the imapSyncLogs
        ImapSyncLogs updatedImapSyncLogs = imapSyncLogsRepository.findById(imapSyncLogs.getId()).get();
        // Disconnect from session so that the updates on updatedImapSyncLogs are not directly saved in db
        em.detach(updatedImapSyncLogs);
        updatedImapSyncLogs
            .level(UPDATED_LEVEL)
            .message(UPDATED_MESSAGE)
            .groupId(UPDATED_GROUP_ID);
        ImapSyncLogsDTO imapSyncLogsDTO = imapSyncLogsMapper.toDto(updatedImapSyncLogs);

        restImapSyncLogsMockMvc.perform(put("/api/imap-sync-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imapSyncLogsDTO)))
            .andExpect(status().isOk());

        // Validate the ImapSyncLogs in the database
        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeUpdate);
        ImapSyncLogs testImapSyncLogs = imapSyncLogsList.get(imapSyncLogsList.size() - 1);
        assertThat(testImapSyncLogs.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testImapSyncLogs.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testImapSyncLogs.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingImapSyncLogs() throws Exception {
        int databaseSizeBeforeUpdate = imapSyncLogsRepository.findAll().size();

        // Create the ImapSyncLogs
        ImapSyncLogsDTO imapSyncLogsDTO = imapSyncLogsMapper.toDto(imapSyncLogs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImapSyncLogsMockMvc.perform(put("/api/imap-sync-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imapSyncLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImapSyncLogs in the database
        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImapSyncLogs() throws Exception {
        // Initialize the database
        imapSyncLogsRepository.saveAndFlush(imapSyncLogs);

        int databaseSizeBeforeDelete = imapSyncLogsRepository.findAll().size();

        // Delete the imapSyncLogs
        restImapSyncLogsMockMvc.perform(delete("/api/imap-sync-logs/{id}", imapSyncLogs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImapSyncLogs> imapSyncLogsList = imapSyncLogsRepository.findAll();
        assertThat(imapSyncLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
