/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.EmailTokens;
import com.infy.repository.EmailTokensRepository;
import com.infy.service.EmailTokensService;
import com.infy.service.dto.EmailTokensDTO;
import com.infy.service.mapper.EmailTokensMapper;

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
 * Integration tests for the {@link EmailTokensResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmailTokensResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFIRMED = false;
    private static final Boolean UPDATED_CONFIRMED = true;

    private static final Boolean DEFAULT_EXPIRED = false;
    private static final Boolean UPDATED_EXPIRED = true;

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmailTokensRepository emailTokensRepository;

    @Autowired
    private EmailTokensMapper emailTokensMapper;

    @Autowired
    private EmailTokensService emailTokensService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailTokensMockMvc;

    private EmailTokens emailTokens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTokens createEntity(EntityManager em) {
        EmailTokens emailTokens = new EmailTokens()
            .userId(DEFAULT_USER_ID)
            .email(DEFAULT_EMAIL)
            .token(DEFAULT_TOKEN)
            .confirmed(DEFAULT_CONFIRMED)
            .expired(DEFAULT_EXPIRED)
            .updatedAt(DEFAULT_UPDATED_AT);
        return emailTokens;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTokens createUpdatedEntity(EntityManager em) {
        EmailTokens emailTokens = new EmailTokens()
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .token(UPDATED_TOKEN)
            .confirmed(UPDATED_CONFIRMED)
            .expired(UPDATED_EXPIRED)
            .updatedAt(UPDATED_UPDATED_AT);
        return emailTokens;
    }

    @BeforeEach
    public void initTest() {
        emailTokens = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTokens() throws Exception {
        int databaseSizeBeforeCreate = emailTokensRepository.findAll().size();
        // Create the EmailTokens
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);
        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailTokens in the database
        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTokens testEmailTokens = emailTokensList.get(emailTokensList.size() - 1);
        assertThat(testEmailTokens.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmailTokens.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmailTokens.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testEmailTokens.isConfirmed()).isEqualTo(DEFAULT_CONFIRMED);
        assertThat(testEmailTokens.isExpired()).isEqualTo(DEFAULT_EXPIRED);
        assertThat(testEmailTokens.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createEmailTokensWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTokensRepository.findAll().size();

        // Create the EmailTokens with an existing ID
        emailTokens.setId(1L);
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTokens in the database
        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTokensRepository.findAll().size();
        // set the field null
        emailTokens.setUserId(null);

        // Create the EmailTokens, which fails.
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);


        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTokensRepository.findAll().size();
        // set the field null
        emailTokens.setEmail(null);

        // Create the EmailTokens, which fails.
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);


        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTokensRepository.findAll().size();
        // set the field null
        emailTokens.setToken(null);

        // Create the EmailTokens, which fails.
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);


        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfirmedIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTokensRepository.findAll().size();
        // set the field null
        emailTokens.setConfirmed(null);

        // Create the EmailTokens, which fails.
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);


        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTokensRepository.findAll().size();
        // set the field null
        emailTokens.setExpired(null);

        // Create the EmailTokens, which fails.
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);


        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTokensRepository.findAll().size();
        // set the field null
        emailTokens.setUpdatedAt(null);

        // Create the EmailTokens, which fails.
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);


        restEmailTokensMockMvc.perform(post("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailTokens() throws Exception {
        // Initialize the database
        emailTokensRepository.saveAndFlush(emailTokens);

        // Get all the emailTokensList
        restEmailTokensMockMvc.perform(get("/api/email-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTokens.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].confirmed").value(hasItem(DEFAULT_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].expired").value(hasItem(DEFAULT_EXPIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getEmailTokens() throws Exception {
        // Initialize the database
        emailTokensRepository.saveAndFlush(emailTokens);

        // Get the emailTokens
        restEmailTokensMockMvc.perform(get("/api/email-tokens/{id}", emailTokens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailTokens.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.confirmed").value(DEFAULT_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.expired").value(DEFAULT_EXPIRED.booleanValue()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmailTokens() throws Exception {
        // Get the emailTokens
        restEmailTokensMockMvc.perform(get("/api/email-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTokens() throws Exception {
        // Initialize the database
        emailTokensRepository.saveAndFlush(emailTokens);

        int databaseSizeBeforeUpdate = emailTokensRepository.findAll().size();

        // Update the emailTokens
        EmailTokens updatedEmailTokens = emailTokensRepository.findById(emailTokens.getId()).get();
        // Disconnect from session so that the updates on updatedEmailTokens are not directly saved in db
        em.detach(updatedEmailTokens);
        updatedEmailTokens
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .token(UPDATED_TOKEN)
            .confirmed(UPDATED_CONFIRMED)
            .expired(UPDATED_EXPIRED)
            .updatedAt(UPDATED_UPDATED_AT);
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(updatedEmailTokens);

        restEmailTokensMockMvc.perform(put("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isOk());

        // Validate the EmailTokens in the database
        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeUpdate);
        EmailTokens testEmailTokens = emailTokensList.get(emailTokensList.size() - 1);
        assertThat(testEmailTokens.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmailTokens.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailTokens.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testEmailTokens.isConfirmed()).isEqualTo(UPDATED_CONFIRMED);
        assertThat(testEmailTokens.isExpired()).isEqualTo(UPDATED_EXPIRED);
        assertThat(testEmailTokens.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailTokens() throws Exception {
        int databaseSizeBeforeUpdate = emailTokensRepository.findAll().size();

        // Create the EmailTokens
        EmailTokensDTO emailTokensDTO = emailTokensMapper.toDto(emailTokens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTokensMockMvc.perform(put("/api/email-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTokensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTokens in the database
        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailTokens() throws Exception {
        // Initialize the database
        emailTokensRepository.saveAndFlush(emailTokens);

        int databaseSizeBeforeDelete = emailTokensRepository.findAll().size();

        // Delete the emailTokens
        restEmailTokensMockMvc.perform(delete("/api/email-tokens/{id}", emailTokens.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailTokens> emailTokensList = emailTokensRepository.findAll();
        assertThat(emailTokensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
