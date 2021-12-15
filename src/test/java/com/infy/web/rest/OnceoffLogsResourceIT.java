/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.OnceoffLogs;
import com.infy.repository.OnceoffLogsRepository;
import com.infy.service.OnceoffLogsService;
import com.infy.service.dto.OnceoffLogsDTO;
import com.infy.service.mapper.OnceoffLogsMapper;

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
 * Integration tests for the {@link OnceoffLogsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OnceoffLogsResourceIT {

    private static final String DEFAULT_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NAME = "BBBBBBBBBB";

    @Autowired
    private OnceoffLogsRepository onceoffLogsRepository;

    @Autowired
    private OnceoffLogsMapper onceoffLogsMapper;

    @Autowired
    private OnceoffLogsService onceoffLogsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnceoffLogsMockMvc;

    private OnceoffLogs onceoffLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OnceoffLogs createEntity(EntityManager em) {
        OnceoffLogs onceoffLogs = new OnceoffLogs()
            .jobName(DEFAULT_JOB_NAME);
        return onceoffLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OnceoffLogs createUpdatedEntity(EntityManager em) {
        OnceoffLogs onceoffLogs = new OnceoffLogs()
            .jobName(UPDATED_JOB_NAME);
        return onceoffLogs;
    }

    @BeforeEach
    public void initTest() {
        onceoffLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createOnceoffLogs() throws Exception {
        int databaseSizeBeforeCreate = onceoffLogsRepository.findAll().size();
        // Create the OnceoffLogs
        OnceoffLogsDTO onceoffLogsDTO = onceoffLogsMapper.toDto(onceoffLogs);
        restOnceoffLogsMockMvc.perform(post("/api/onceoff-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onceoffLogsDTO)))
            .andExpect(status().isCreated());

        // Validate the OnceoffLogs in the database
        List<OnceoffLogs> onceoffLogsList = onceoffLogsRepository.findAll();
        assertThat(onceoffLogsList).hasSize(databaseSizeBeforeCreate + 1);
        OnceoffLogs testOnceoffLogs = onceoffLogsList.get(onceoffLogsList.size() - 1);
        assertThat(testOnceoffLogs.getJobName()).isEqualTo(DEFAULT_JOB_NAME);
    }

    @Test
    @Transactional
    public void createOnceoffLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = onceoffLogsRepository.findAll().size();

        // Create the OnceoffLogs with an existing ID
        onceoffLogs.setId(1L);
        OnceoffLogsDTO onceoffLogsDTO = onceoffLogsMapper.toDto(onceoffLogs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnceoffLogsMockMvc.perform(post("/api/onceoff-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onceoffLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OnceoffLogs in the database
        List<OnceoffLogs> onceoffLogsList = onceoffLogsRepository.findAll();
        assertThat(onceoffLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOnceoffLogs() throws Exception {
        // Initialize the database
        onceoffLogsRepository.saveAndFlush(onceoffLogs);

        // Get all the onceoffLogsList
        restOnceoffLogsMockMvc.perform(get("/api/onceoff-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onceoffLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME)));
    }

    @Test
    @Transactional
    public void getOnceoffLogs() throws Exception {
        // Initialize the database
        onceoffLogsRepository.saveAndFlush(onceoffLogs);

        // Get the onceoffLogs
        restOnceoffLogsMockMvc.perform(get("/api/onceoff-logs/{id}", onceoffLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onceoffLogs.getId().intValue()))
            .andExpect(jsonPath("$.jobName").value(DEFAULT_JOB_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingOnceoffLogs() throws Exception {
        // Get the onceoffLogs
        restOnceoffLogsMockMvc.perform(get("/api/onceoff-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOnceoffLogs() throws Exception {
        // Initialize the database
        onceoffLogsRepository.saveAndFlush(onceoffLogs);

        int databaseSizeBeforeUpdate = onceoffLogsRepository.findAll().size();

        // Update the onceoffLogs
        OnceoffLogs updatedOnceoffLogs = onceoffLogsRepository.findById(onceoffLogs.getId()).get();
        // Disconnect from session so that the updates on updatedOnceoffLogs are not directly saved in db
        em.detach(updatedOnceoffLogs);
        updatedOnceoffLogs
            .jobName(UPDATED_JOB_NAME);
        OnceoffLogsDTO onceoffLogsDTO = onceoffLogsMapper.toDto(updatedOnceoffLogs);

        restOnceoffLogsMockMvc.perform(put("/api/onceoff-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onceoffLogsDTO)))
            .andExpect(status().isOk());

        // Validate the OnceoffLogs in the database
        List<OnceoffLogs> onceoffLogsList = onceoffLogsRepository.findAll();
        assertThat(onceoffLogsList).hasSize(databaseSizeBeforeUpdate);
        OnceoffLogs testOnceoffLogs = onceoffLogsList.get(onceoffLogsList.size() - 1);
        assertThat(testOnceoffLogs.getJobName()).isEqualTo(UPDATED_JOB_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOnceoffLogs() throws Exception {
        int databaseSizeBeforeUpdate = onceoffLogsRepository.findAll().size();

        // Create the OnceoffLogs
        OnceoffLogsDTO onceoffLogsDTO = onceoffLogsMapper.toDto(onceoffLogs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnceoffLogsMockMvc.perform(put("/api/onceoff-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onceoffLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OnceoffLogs in the database
        List<OnceoffLogs> onceoffLogsList = onceoffLogsRepository.findAll();
        assertThat(onceoffLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOnceoffLogs() throws Exception {
        // Initialize the database
        onceoffLogsRepository.saveAndFlush(onceoffLogs);

        int databaseSizeBeforeDelete = onceoffLogsRepository.findAll().size();

        // Delete the onceoffLogs
        restOnceoffLogsMockMvc.perform(delete("/api/onceoff-logs/{id}", onceoffLogs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OnceoffLogs> onceoffLogsList = onceoffLogsRepository.findAll();
        assertThat(onceoffLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
