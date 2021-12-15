/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.EmailChangeRequests;
import com.infy.repository.EmailChangeRequestsRepository;
import com.infy.service.EmailChangeRequestsService;
import com.infy.service.dto.EmailChangeRequestsDTO;
import com.infy.service.mapper.EmailChangeRequestsMapper;

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
 * Integration tests for the {@link EmailChangeRequestsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmailChangeRequestsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OLD_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_OLD_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_NEW_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_OLD_EMAIL_TOKEN_ID = 1;
    private static final Integer UPDATED_OLD_EMAIL_TOKEN_ID = 2;

    private static final Integer DEFAULT_NEW_EMAIL_TOKEN_ID = 1;
    private static final Integer UPDATED_NEW_EMAIL_TOKEN_ID = 2;

    private static final Integer DEFAULT_CHANGE_STATE = 1;
    private static final Integer UPDATED_CHANGE_STATE = 2;

    private static final String DEFAULT_REQUESTED_BY_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY_USER_ID = "BBBBBBBBBB";

    @Autowired
    private EmailChangeRequestsRepository emailChangeRequestsRepository;

    @Autowired
    private EmailChangeRequestsMapper emailChangeRequestsMapper;

    @Autowired
    private EmailChangeRequestsService emailChangeRequestsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailChangeRequestsMockMvc;

    private EmailChangeRequests emailChangeRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailChangeRequests createEntity(EntityManager em) {
        EmailChangeRequests emailChangeRequests = new EmailChangeRequests()
            .userId(DEFAULT_USER_ID)
            .oldEmail(DEFAULT_OLD_EMAIL)
            .newEmail(DEFAULT_NEW_EMAIL)
            .oldEmailTokenId(DEFAULT_OLD_EMAIL_TOKEN_ID)
            .newEmailTokenId(DEFAULT_NEW_EMAIL_TOKEN_ID)
            .changeState(DEFAULT_CHANGE_STATE)
            .requestedByUserId(DEFAULT_REQUESTED_BY_USER_ID);
        return emailChangeRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailChangeRequests createUpdatedEntity(EntityManager em) {
        EmailChangeRequests emailChangeRequests = new EmailChangeRequests()
            .userId(UPDATED_USER_ID)
            .oldEmail(UPDATED_OLD_EMAIL)
            .newEmail(UPDATED_NEW_EMAIL)
            .oldEmailTokenId(UPDATED_OLD_EMAIL_TOKEN_ID)
            .newEmailTokenId(UPDATED_NEW_EMAIL_TOKEN_ID)
            .changeState(UPDATED_CHANGE_STATE)
            .requestedByUserId(UPDATED_REQUESTED_BY_USER_ID);
        return emailChangeRequests;
    }

    @BeforeEach
    public void initTest() {
        emailChangeRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailChangeRequests() throws Exception {
        int databaseSizeBeforeCreate = emailChangeRequestsRepository.findAll().size();
        // Create the EmailChangeRequests
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(emailChangeRequests);
        restEmailChangeRequestsMockMvc.perform(post("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailChangeRequests in the database
        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        EmailChangeRequests testEmailChangeRequests = emailChangeRequestsList.get(emailChangeRequestsList.size() - 1);
        assertThat(testEmailChangeRequests.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmailChangeRequests.getOldEmail()).isEqualTo(DEFAULT_OLD_EMAIL);
        assertThat(testEmailChangeRequests.getNewEmail()).isEqualTo(DEFAULT_NEW_EMAIL);
        assertThat(testEmailChangeRequests.getOldEmailTokenId()).isEqualTo(DEFAULT_OLD_EMAIL_TOKEN_ID);
        assertThat(testEmailChangeRequests.getNewEmailTokenId()).isEqualTo(DEFAULT_NEW_EMAIL_TOKEN_ID);
        assertThat(testEmailChangeRequests.getChangeState()).isEqualTo(DEFAULT_CHANGE_STATE);
        assertThat(testEmailChangeRequests.getRequestedByUserId()).isEqualTo(DEFAULT_REQUESTED_BY_USER_ID);
    }

    @Test
    @Transactional
    public void createEmailChangeRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailChangeRequestsRepository.findAll().size();

        // Create the EmailChangeRequests with an existing ID
        emailChangeRequests.setId(1L);
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(emailChangeRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailChangeRequestsMockMvc.perform(post("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailChangeRequests in the database
        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailChangeRequestsRepository.findAll().size();
        // set the field null
        emailChangeRequests.setUserId(null);

        // Create the EmailChangeRequests, which fails.
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(emailChangeRequests);


        restEmailChangeRequestsMockMvc.perform(post("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNewEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailChangeRequestsRepository.findAll().size();
        // set the field null
        emailChangeRequests.setNewEmail(null);

        // Create the EmailChangeRequests, which fails.
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(emailChangeRequests);


        restEmailChangeRequestsMockMvc.perform(post("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChangeStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailChangeRequestsRepository.findAll().size();
        // set the field null
        emailChangeRequests.setChangeState(null);

        // Create the EmailChangeRequests, which fails.
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(emailChangeRequests);


        restEmailChangeRequestsMockMvc.perform(post("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailChangeRequests() throws Exception {
        // Initialize the database
        emailChangeRequestsRepository.saveAndFlush(emailChangeRequests);

        // Get all the emailChangeRequestsList
        restEmailChangeRequestsMockMvc.perform(get("/api/email-change-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailChangeRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].oldEmail").value(hasItem(DEFAULT_OLD_EMAIL)))
            .andExpect(jsonPath("$.[*].newEmail").value(hasItem(DEFAULT_NEW_EMAIL)))
            .andExpect(jsonPath("$.[*].oldEmailTokenId").value(hasItem(DEFAULT_OLD_EMAIL_TOKEN_ID)))
            .andExpect(jsonPath("$.[*].newEmailTokenId").value(hasItem(DEFAULT_NEW_EMAIL_TOKEN_ID)))
            .andExpect(jsonPath("$.[*].changeState").value(hasItem(DEFAULT_CHANGE_STATE)))
            .andExpect(jsonPath("$.[*].requestedByUserId").value(hasItem(DEFAULT_REQUESTED_BY_USER_ID)));
    }

    @Test
    @Transactional
    public void getEmailChangeRequests() throws Exception {
        // Initialize the database
        emailChangeRequestsRepository.saveAndFlush(emailChangeRequests);

        // Get the emailChangeRequests
        restEmailChangeRequestsMockMvc.perform(get("/api/email-change-requests/{id}", emailChangeRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailChangeRequests.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.oldEmail").value(DEFAULT_OLD_EMAIL))
            .andExpect(jsonPath("$.newEmail").value(DEFAULT_NEW_EMAIL))
            .andExpect(jsonPath("$.oldEmailTokenId").value(DEFAULT_OLD_EMAIL_TOKEN_ID))
            .andExpect(jsonPath("$.newEmailTokenId").value(DEFAULT_NEW_EMAIL_TOKEN_ID))
            .andExpect(jsonPath("$.changeState").value(DEFAULT_CHANGE_STATE))
            .andExpect(jsonPath("$.requestedByUserId").value(DEFAULT_REQUESTED_BY_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingEmailChangeRequests() throws Exception {
        // Get the emailChangeRequests
        restEmailChangeRequestsMockMvc.perform(get("/api/email-change-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailChangeRequests() throws Exception {
        // Initialize the database
        emailChangeRequestsRepository.saveAndFlush(emailChangeRequests);

        int databaseSizeBeforeUpdate = emailChangeRequestsRepository.findAll().size();

        // Update the emailChangeRequests
        EmailChangeRequests updatedEmailChangeRequests = emailChangeRequestsRepository.findById(emailChangeRequests.getId()).get();
        // Disconnect from session so that the updates on updatedEmailChangeRequests are not directly saved in db
        em.detach(updatedEmailChangeRequests);
        updatedEmailChangeRequests
            .userId(UPDATED_USER_ID)
            .oldEmail(UPDATED_OLD_EMAIL)
            .newEmail(UPDATED_NEW_EMAIL)
            .oldEmailTokenId(UPDATED_OLD_EMAIL_TOKEN_ID)
            .newEmailTokenId(UPDATED_NEW_EMAIL_TOKEN_ID)
            .changeState(UPDATED_CHANGE_STATE)
            .requestedByUserId(UPDATED_REQUESTED_BY_USER_ID);
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(updatedEmailChangeRequests);

        restEmailChangeRequestsMockMvc.perform(put("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the EmailChangeRequests in the database
        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeUpdate);
        EmailChangeRequests testEmailChangeRequests = emailChangeRequestsList.get(emailChangeRequestsList.size() - 1);
        assertThat(testEmailChangeRequests.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmailChangeRequests.getOldEmail()).isEqualTo(UPDATED_OLD_EMAIL);
        assertThat(testEmailChangeRequests.getNewEmail()).isEqualTo(UPDATED_NEW_EMAIL);
        assertThat(testEmailChangeRequests.getOldEmailTokenId()).isEqualTo(UPDATED_OLD_EMAIL_TOKEN_ID);
        assertThat(testEmailChangeRequests.getNewEmailTokenId()).isEqualTo(UPDATED_NEW_EMAIL_TOKEN_ID);
        assertThat(testEmailChangeRequests.getChangeState()).isEqualTo(UPDATED_CHANGE_STATE);
        assertThat(testEmailChangeRequests.getRequestedByUserId()).isEqualTo(UPDATED_REQUESTED_BY_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailChangeRequests() throws Exception {
        int databaseSizeBeforeUpdate = emailChangeRequestsRepository.findAll().size();

        // Create the EmailChangeRequests
        EmailChangeRequestsDTO emailChangeRequestsDTO = emailChangeRequestsMapper.toDto(emailChangeRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailChangeRequestsMockMvc.perform(put("/api/email-change-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailChangeRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailChangeRequests in the database
        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailChangeRequests() throws Exception {
        // Initialize the database
        emailChangeRequestsRepository.saveAndFlush(emailChangeRequests);

        int databaseSizeBeforeDelete = emailChangeRequestsRepository.findAll().size();

        // Delete the emailChangeRequests
        restEmailChangeRequestsMockMvc.perform(delete("/api/email-change-requests/{id}", emailChangeRequests.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailChangeRequests> emailChangeRequestsList = emailChangeRequestsRepository.findAll();
        assertThat(emailChangeRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
