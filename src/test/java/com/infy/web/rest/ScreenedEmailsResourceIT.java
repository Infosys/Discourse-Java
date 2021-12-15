/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ScreenedEmails;
import com.infy.repository.ScreenedEmailsRepository;
import com.infy.service.ScreenedEmailsService;
import com.infy.service.dto.ScreenedEmailsDTO;
import com.infy.service.mapper.ScreenedEmailsMapper;

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
 * Integration tests for the {@link ScreenedEmailsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ScreenedEmailsResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTION_TYPE = 1;
    private static final Integer UPDATED_ACTION_TYPE = 2;

    private static final Integer DEFAULT_MATCH_COUNT = 1;
    private static final Integer UPDATED_MATCH_COUNT = 2;

    private static final Instant DEFAULT_LAST_MATCH_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MATCH_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ScreenedEmailsRepository screenedEmailsRepository;

    @Autowired
    private ScreenedEmailsMapper screenedEmailsMapper;

    @Autowired
    private ScreenedEmailsService screenedEmailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScreenedEmailsMockMvc;

    private ScreenedEmails screenedEmails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScreenedEmails createEntity(EntityManager em) {
        ScreenedEmails screenedEmails = new ScreenedEmails()
            .email(DEFAULT_EMAIL)
            .actionType(DEFAULT_ACTION_TYPE)
            .matchCount(DEFAULT_MATCH_COUNT)
            .lastMatchAt(DEFAULT_LAST_MATCH_AT)
            .ipAddress(DEFAULT_IP_ADDRESS);
        return screenedEmails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScreenedEmails createUpdatedEntity(EntityManager em) {
        ScreenedEmails screenedEmails = new ScreenedEmails()
            .email(UPDATED_EMAIL)
            .actionType(UPDATED_ACTION_TYPE)
            .matchCount(UPDATED_MATCH_COUNT)
            .lastMatchAt(UPDATED_LAST_MATCH_AT)
            .ipAddress(UPDATED_IP_ADDRESS);
        return screenedEmails;
    }

    @BeforeEach
    public void initTest() {
        screenedEmails = createEntity(em);
    }

    @Test
    @Transactional
    public void createScreenedEmails() throws Exception {
        int databaseSizeBeforeCreate = screenedEmailsRepository.findAll().size();
        // Create the ScreenedEmails
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(screenedEmails);
        restScreenedEmailsMockMvc.perform(post("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ScreenedEmails in the database
        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeCreate + 1);
        ScreenedEmails testScreenedEmails = screenedEmailsList.get(screenedEmailsList.size() - 1);
        assertThat(testScreenedEmails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testScreenedEmails.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testScreenedEmails.getMatchCount()).isEqualTo(DEFAULT_MATCH_COUNT);
        assertThat(testScreenedEmails.getLastMatchAt()).isEqualTo(DEFAULT_LAST_MATCH_AT);
        assertThat(testScreenedEmails.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createScreenedEmailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = screenedEmailsRepository.findAll().size();

        // Create the ScreenedEmails with an existing ID
        screenedEmails.setId(1L);
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(screenedEmails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreenedEmailsMockMvc.perform(post("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScreenedEmails in the database
        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedEmailsRepository.findAll().size();
        // set the field null
        screenedEmails.setEmail(null);

        // Create the ScreenedEmails, which fails.
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(screenedEmails);


        restScreenedEmailsMockMvc.perform(post("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedEmailsRepository.findAll().size();
        // set the field null
        screenedEmails.setActionType(null);

        // Create the ScreenedEmails, which fails.
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(screenedEmails);


        restScreenedEmailsMockMvc.perform(post("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatchCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedEmailsRepository.findAll().size();
        // set the field null
        screenedEmails.setMatchCount(null);

        // Create the ScreenedEmails, which fails.
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(screenedEmails);


        restScreenedEmailsMockMvc.perform(post("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScreenedEmails() throws Exception {
        // Initialize the database
        screenedEmailsRepository.saveAndFlush(screenedEmails);

        // Get all the screenedEmailsList
        restScreenedEmailsMockMvc.perform(get("/api/screened-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screenedEmails.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE)))
            .andExpect(jsonPath("$.[*].matchCount").value(hasItem(DEFAULT_MATCH_COUNT)))
            .andExpect(jsonPath("$.[*].lastMatchAt").value(hasItem(DEFAULT_LAST_MATCH_AT.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)));
    }

    @Test
    @Transactional
    public void getScreenedEmails() throws Exception {
        // Initialize the database
        screenedEmailsRepository.saveAndFlush(screenedEmails);

        // Get the screenedEmails
        restScreenedEmailsMockMvc.perform(get("/api/screened-emails/{id}", screenedEmails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(screenedEmails.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE))
            .andExpect(jsonPath("$.matchCount").value(DEFAULT_MATCH_COUNT))
            .andExpect(jsonPath("$.lastMatchAt").value(DEFAULT_LAST_MATCH_AT.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingScreenedEmails() throws Exception {
        // Get the screenedEmails
        restScreenedEmailsMockMvc.perform(get("/api/screened-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScreenedEmails() throws Exception {
        // Initialize the database
        screenedEmailsRepository.saveAndFlush(screenedEmails);

        int databaseSizeBeforeUpdate = screenedEmailsRepository.findAll().size();

        // Update the screenedEmails
        ScreenedEmails updatedScreenedEmails = screenedEmailsRepository.findById(screenedEmails.getId()).get();
        // Disconnect from session so that the updates on updatedScreenedEmails are not directly saved in db
        em.detach(updatedScreenedEmails);
        updatedScreenedEmails
            .email(UPDATED_EMAIL)
            .actionType(UPDATED_ACTION_TYPE)
            .matchCount(UPDATED_MATCH_COUNT)
            .lastMatchAt(UPDATED_LAST_MATCH_AT)
            .ipAddress(UPDATED_IP_ADDRESS);
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(updatedScreenedEmails);

        restScreenedEmailsMockMvc.perform(put("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isOk());

        // Validate the ScreenedEmails in the database
        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeUpdate);
        ScreenedEmails testScreenedEmails = screenedEmailsList.get(screenedEmailsList.size() - 1);
        assertThat(testScreenedEmails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testScreenedEmails.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testScreenedEmails.getMatchCount()).isEqualTo(UPDATED_MATCH_COUNT);
        assertThat(testScreenedEmails.getLastMatchAt()).isEqualTo(UPDATED_LAST_MATCH_AT);
        assertThat(testScreenedEmails.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingScreenedEmails() throws Exception {
        int databaseSizeBeforeUpdate = screenedEmailsRepository.findAll().size();

        // Create the ScreenedEmails
        ScreenedEmailsDTO screenedEmailsDTO = screenedEmailsMapper.toDto(screenedEmails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScreenedEmailsMockMvc.perform(put("/api/screened-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedEmailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScreenedEmails in the database
        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScreenedEmails() throws Exception {
        // Initialize the database
        screenedEmailsRepository.saveAndFlush(screenedEmails);

        int databaseSizeBeforeDelete = screenedEmailsRepository.findAll().size();

        // Delete the screenedEmails
        restScreenedEmailsMockMvc.perform(delete("/api/screened-emails/{id}", screenedEmails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScreenedEmails> screenedEmailsList = screenedEmailsRepository.findAll();
        assertThat(screenedEmailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
