/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SkippedEmailLogs;
import com.infy.repository.SkippedEmailLogsRepository;
import com.infy.service.SkippedEmailLogsService;
import com.infy.service.dto.SkippedEmailLogsDTO;
import com.infy.service.mapper.SkippedEmailLogsMapper;

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
 * Integration tests for the {@link SkippedEmailLogsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SkippedEmailLogsResourceIT {

    private static final String DEFAULT_EMAIL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_TO_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Integer DEFAULT_REASON_TYPE = 1;
    private static final Integer UPDATED_REASON_TYPE = 2;

    private static final String DEFAULT_CUSTOM_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_REASON = "BBBBBBBBBB";

    @Autowired
    private SkippedEmailLogsRepository skippedEmailLogsRepository;

    @Autowired
    private SkippedEmailLogsMapper skippedEmailLogsMapper;

    @Autowired
    private SkippedEmailLogsService skippedEmailLogsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkippedEmailLogsMockMvc;

    private SkippedEmailLogs skippedEmailLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkippedEmailLogs createEntity(EntityManager em) {
        SkippedEmailLogs skippedEmailLogs = new SkippedEmailLogs()
            .emailType(DEFAULT_EMAIL_TYPE)
            .toAddress(DEFAULT_TO_ADDRESS)
            .userId(DEFAULT_USER_ID)
            .postId(DEFAULT_POST_ID)
            .reasonType(DEFAULT_REASON_TYPE)
            .customReason(DEFAULT_CUSTOM_REASON);
        return skippedEmailLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkippedEmailLogs createUpdatedEntity(EntityManager em) {
        SkippedEmailLogs skippedEmailLogs = new SkippedEmailLogs()
            .emailType(UPDATED_EMAIL_TYPE)
            .toAddress(UPDATED_TO_ADDRESS)
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .reasonType(UPDATED_REASON_TYPE)
            .customReason(UPDATED_CUSTOM_REASON);
        return skippedEmailLogs;
    }

    @BeforeEach
    public void initTest() {
        skippedEmailLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkippedEmailLogs() throws Exception {
        int databaseSizeBeforeCreate = skippedEmailLogsRepository.findAll().size();
        // Create the SkippedEmailLogs
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(skippedEmailLogs);
        restSkippedEmailLogsMockMvc.perform(post("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isCreated());

        // Validate the SkippedEmailLogs in the database
        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeCreate + 1);
        SkippedEmailLogs testSkippedEmailLogs = skippedEmailLogsList.get(skippedEmailLogsList.size() - 1);
        assertThat(testSkippedEmailLogs.getEmailType()).isEqualTo(DEFAULT_EMAIL_TYPE);
        assertThat(testSkippedEmailLogs.getToAddress()).isEqualTo(DEFAULT_TO_ADDRESS);
        assertThat(testSkippedEmailLogs.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSkippedEmailLogs.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testSkippedEmailLogs.getReasonType()).isEqualTo(DEFAULT_REASON_TYPE);
        assertThat(testSkippedEmailLogs.getCustomReason()).isEqualTo(DEFAULT_CUSTOM_REASON);
    }

    @Test
    @Transactional
    public void createSkippedEmailLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skippedEmailLogsRepository.findAll().size();

        // Create the SkippedEmailLogs with an existing ID
        skippedEmailLogs.setId(1L);
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(skippedEmailLogs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkippedEmailLogsMockMvc.perform(post("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkippedEmailLogs in the database
        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = skippedEmailLogsRepository.findAll().size();
        // set the field null
        skippedEmailLogs.setEmailType(null);

        // Create the SkippedEmailLogs, which fails.
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(skippedEmailLogs);


        restSkippedEmailLogsMockMvc.perform(post("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isBadRequest());

        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = skippedEmailLogsRepository.findAll().size();
        // set the field null
        skippedEmailLogs.setToAddress(null);

        // Create the SkippedEmailLogs, which fails.
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(skippedEmailLogs);


        restSkippedEmailLogsMockMvc.perform(post("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isBadRequest());

        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = skippedEmailLogsRepository.findAll().size();
        // set the field null
        skippedEmailLogs.setReasonType(null);

        // Create the SkippedEmailLogs, which fails.
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(skippedEmailLogs);


        restSkippedEmailLogsMockMvc.perform(post("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isBadRequest());

        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkippedEmailLogs() throws Exception {
        // Initialize the database
        skippedEmailLogsRepository.saveAndFlush(skippedEmailLogs);

        // Get all the skippedEmailLogsList
        restSkippedEmailLogsMockMvc.perform(get("/api/skipped-email-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skippedEmailLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailType").value(hasItem(DEFAULT_EMAIL_TYPE)))
            .andExpect(jsonPath("$.[*].toAddress").value(hasItem(DEFAULT_TO_ADDRESS)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].reasonType").value(hasItem(DEFAULT_REASON_TYPE)))
            .andExpect(jsonPath("$.[*].customReason").value(hasItem(DEFAULT_CUSTOM_REASON)));
    }

    @Test
    @Transactional
    public void getSkippedEmailLogs() throws Exception {
        // Initialize the database
        skippedEmailLogsRepository.saveAndFlush(skippedEmailLogs);

        // Get the skippedEmailLogs
        restSkippedEmailLogsMockMvc.perform(get("/api/skipped-email-logs/{id}", skippedEmailLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skippedEmailLogs.getId().intValue()))
            .andExpect(jsonPath("$.emailType").value(DEFAULT_EMAIL_TYPE))
            .andExpect(jsonPath("$.toAddress").value(DEFAULT_TO_ADDRESS))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.reasonType").value(DEFAULT_REASON_TYPE))
            .andExpect(jsonPath("$.customReason").value(DEFAULT_CUSTOM_REASON));
    }
    @Test
    @Transactional
    public void getNonExistingSkippedEmailLogs() throws Exception {
        // Get the skippedEmailLogs
        restSkippedEmailLogsMockMvc.perform(get("/api/skipped-email-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkippedEmailLogs() throws Exception {
        // Initialize the database
        skippedEmailLogsRepository.saveAndFlush(skippedEmailLogs);

        int databaseSizeBeforeUpdate = skippedEmailLogsRepository.findAll().size();

        // Update the skippedEmailLogs
        SkippedEmailLogs updatedSkippedEmailLogs = skippedEmailLogsRepository.findById(skippedEmailLogs.getId()).get();
        // Disconnect from session so that the updates on updatedSkippedEmailLogs are not directly saved in db
        em.detach(updatedSkippedEmailLogs);
        updatedSkippedEmailLogs
            .emailType(UPDATED_EMAIL_TYPE)
            .toAddress(UPDATED_TO_ADDRESS)
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .reasonType(UPDATED_REASON_TYPE)
            .customReason(UPDATED_CUSTOM_REASON);
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(updatedSkippedEmailLogs);

        restSkippedEmailLogsMockMvc.perform(put("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isOk());

        // Validate the SkippedEmailLogs in the database
        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeUpdate);
        SkippedEmailLogs testSkippedEmailLogs = skippedEmailLogsList.get(skippedEmailLogsList.size() - 1);
        assertThat(testSkippedEmailLogs.getEmailType()).isEqualTo(UPDATED_EMAIL_TYPE);
        assertThat(testSkippedEmailLogs.getToAddress()).isEqualTo(UPDATED_TO_ADDRESS);
        assertThat(testSkippedEmailLogs.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSkippedEmailLogs.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testSkippedEmailLogs.getReasonType()).isEqualTo(UPDATED_REASON_TYPE);
        assertThat(testSkippedEmailLogs.getCustomReason()).isEqualTo(UPDATED_CUSTOM_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingSkippedEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = skippedEmailLogsRepository.findAll().size();

        // Create the SkippedEmailLogs
        SkippedEmailLogsDTO skippedEmailLogsDTO = skippedEmailLogsMapper.toDto(skippedEmailLogs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkippedEmailLogsMockMvc.perform(put("/api/skipped-email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skippedEmailLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkippedEmailLogs in the database
        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkippedEmailLogs() throws Exception {
        // Initialize the database
        skippedEmailLogsRepository.saveAndFlush(skippedEmailLogs);

        int databaseSizeBeforeDelete = skippedEmailLogsRepository.findAll().size();

        // Delete the skippedEmailLogs
        restSkippedEmailLogsMockMvc.perform(delete("/api/skipped-email-logs/{id}", skippedEmailLogs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkippedEmailLogs> skippedEmailLogsList = skippedEmailLogsRepository.findAll();
        assertThat(skippedEmailLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
