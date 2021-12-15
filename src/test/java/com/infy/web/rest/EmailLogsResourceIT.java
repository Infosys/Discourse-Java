/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.EmailLogs;
import com.infy.repository.EmailLogsRepository;
import com.infy.service.EmailLogsService;
import com.infy.service.dto.EmailLogsDTO;
import com.infy.service.mapper.EmailLogsMapper;

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
 * Integration tests for the {@link EmailLogsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmailLogsResourceIT {

    private static final String DEFAULT_TO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_TO_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_BOUNCE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_BOUNCE_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BOUNCED = false;
    private static final Boolean UPDATED_BOUNCED = true;

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    @Autowired
    private EmailLogsRepository emailLogsRepository;

    @Autowired
    private EmailLogsMapper emailLogsMapper;

    @Autowired
    private EmailLogsService emailLogsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailLogsMockMvc;

    private EmailLogs emailLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailLogs createEntity(EntityManager em) {
        EmailLogs emailLogs = new EmailLogs()
            .toAddress(DEFAULT_TO_ADDRESS)
            .emailType(DEFAULT_EMAIL_TYPE)
            .userId(DEFAULT_USER_ID)
            .postId(DEFAULT_POST_ID)
            .bounceKey(DEFAULT_BOUNCE_KEY)
            .bounced(DEFAULT_BOUNCED)
            .messageId(DEFAULT_MESSAGE_ID);
        return emailLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailLogs createUpdatedEntity(EntityManager em) {
        EmailLogs emailLogs = new EmailLogs()
            .toAddress(UPDATED_TO_ADDRESS)
            .emailType(UPDATED_EMAIL_TYPE)
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .bounceKey(UPDATED_BOUNCE_KEY)
            .bounced(UPDATED_BOUNCED)
            .messageId(UPDATED_MESSAGE_ID);
        return emailLogs;
    }

    @BeforeEach
    public void initTest() {
        emailLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailLogs() throws Exception {
        int databaseSizeBeforeCreate = emailLogsRepository.findAll().size();
        // Create the EmailLogs
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(emailLogs);
        restEmailLogsMockMvc.perform(post("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailLogs in the database
        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeCreate + 1);
        EmailLogs testEmailLogs = emailLogsList.get(emailLogsList.size() - 1);
        assertThat(testEmailLogs.getToAddress()).isEqualTo(DEFAULT_TO_ADDRESS);
        assertThat(testEmailLogs.getEmailType()).isEqualTo(DEFAULT_EMAIL_TYPE);
        assertThat(testEmailLogs.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmailLogs.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testEmailLogs.getBounceKey()).isEqualTo(DEFAULT_BOUNCE_KEY);
        assertThat(testEmailLogs.isBounced()).isEqualTo(DEFAULT_BOUNCED);
        assertThat(testEmailLogs.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
    }

    @Test
    @Transactional
    public void createEmailLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailLogsRepository.findAll().size();

        // Create the EmailLogs with an existing ID
        emailLogs.setId(1L);
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(emailLogs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailLogsMockMvc.perform(post("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailLogs in the database
        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkToAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailLogsRepository.findAll().size();
        // set the field null
        emailLogs.setToAddress(null);

        // Create the EmailLogs, which fails.
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(emailLogs);


        restEmailLogsMockMvc.perform(post("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isBadRequest());

        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailLogsRepository.findAll().size();
        // set the field null
        emailLogs.setEmailType(null);

        // Create the EmailLogs, which fails.
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(emailLogs);


        restEmailLogsMockMvc.perform(post("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isBadRequest());

        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBouncedIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailLogsRepository.findAll().size();
        // set the field null
        emailLogs.setBounced(null);

        // Create the EmailLogs, which fails.
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(emailLogs);


        restEmailLogsMockMvc.perform(post("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isBadRequest());

        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailLogs() throws Exception {
        // Initialize the database
        emailLogsRepository.saveAndFlush(emailLogs);

        // Get all the emailLogsList
        restEmailLogsMockMvc.perform(get("/api/email-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].toAddress").value(hasItem(DEFAULT_TO_ADDRESS)))
            .andExpect(jsonPath("$.[*].emailType").value(hasItem(DEFAULT_EMAIL_TYPE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].bounceKey").value(hasItem(DEFAULT_BOUNCE_KEY)))
            .andExpect(jsonPath("$.[*].bounced").value(hasItem(DEFAULT_BOUNCED.booleanValue())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID)));
    }

    @Test
    @Transactional
    public void getEmailLogs() throws Exception {
        // Initialize the database
        emailLogsRepository.saveAndFlush(emailLogs);

        // Get the emailLogs
        restEmailLogsMockMvc.perform(get("/api/email-logs/{id}", emailLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailLogs.getId().intValue()))
            .andExpect(jsonPath("$.toAddress").value(DEFAULT_TO_ADDRESS))
            .andExpect(jsonPath("$.emailType").value(DEFAULT_EMAIL_TYPE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.bounceKey").value(DEFAULT_BOUNCE_KEY))
            .andExpect(jsonPath("$.bounced").value(DEFAULT_BOUNCED.booleanValue()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID));
    }
    @Test
    @Transactional
    public void getNonExistingEmailLogs() throws Exception {
        // Get the emailLogs
        restEmailLogsMockMvc.perform(get("/api/email-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailLogs() throws Exception {
        // Initialize the database
        emailLogsRepository.saveAndFlush(emailLogs);

        int databaseSizeBeforeUpdate = emailLogsRepository.findAll().size();

        // Update the emailLogs
        EmailLogs updatedEmailLogs = emailLogsRepository.findById(emailLogs.getId()).get();
        // Disconnect from session so that the updates on updatedEmailLogs are not directly saved in db
        em.detach(updatedEmailLogs);
        updatedEmailLogs
            .toAddress(UPDATED_TO_ADDRESS)
            .emailType(UPDATED_EMAIL_TYPE)
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .bounceKey(UPDATED_BOUNCE_KEY)
            .bounced(UPDATED_BOUNCED)
            .messageId(UPDATED_MESSAGE_ID);
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(updatedEmailLogs);

        restEmailLogsMockMvc.perform(put("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isOk());

        // Validate the EmailLogs in the database
        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeUpdate);
        EmailLogs testEmailLogs = emailLogsList.get(emailLogsList.size() - 1);
        assertThat(testEmailLogs.getToAddress()).isEqualTo(UPDATED_TO_ADDRESS);
        assertThat(testEmailLogs.getEmailType()).isEqualTo(UPDATED_EMAIL_TYPE);
        assertThat(testEmailLogs.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmailLogs.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testEmailLogs.getBounceKey()).isEqualTo(UPDATED_BOUNCE_KEY);
        assertThat(testEmailLogs.isBounced()).isEqualTo(UPDATED_BOUNCED);
        assertThat(testEmailLogs.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailLogs() throws Exception {
        int databaseSizeBeforeUpdate = emailLogsRepository.findAll().size();

        // Create the EmailLogs
        EmailLogsDTO emailLogsDTO = emailLogsMapper.toDto(emailLogs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailLogsMockMvc.perform(put("/api/email-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailLogs in the database
        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailLogs() throws Exception {
        // Initialize the database
        emailLogsRepository.saveAndFlush(emailLogs);

        int databaseSizeBeforeDelete = emailLogsRepository.findAll().size();

        // Delete the emailLogs
        restEmailLogsMockMvc.perform(delete("/api/email-logs/{id}", emailLogs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailLogs> emailLogsList = emailLogsRepository.findAll();
        assertThat(emailLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
