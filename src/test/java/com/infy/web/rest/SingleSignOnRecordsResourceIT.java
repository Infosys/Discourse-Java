/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SingleSignOnRecords;
import com.infy.repository.SingleSignOnRecordsRepository;
import com.infy.service.SingleSignOnRecordsService;
import com.infy.service.dto.SingleSignOnRecordsDTO;
import com.infy.service.mapper.SingleSignOnRecordsMapper;

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
 * Integration tests for the {@link SingleSignOnRecordsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SingleSignOnRecordsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_LAST_PAYLOAD = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_AVATAR_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_AVATAR_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_PROFILE_BACKGROUND_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_PROFILE_BACKGROUND_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_CARD_BACKGROUND_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_CARD_BACKGROUND_URL = "BBBBBBBBBB";

    @Autowired
    private SingleSignOnRecordsRepository singleSignOnRecordsRepository;

    @Autowired
    private SingleSignOnRecordsMapper singleSignOnRecordsMapper;

    @Autowired
    private SingleSignOnRecordsService singleSignOnRecordsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSingleSignOnRecordsMockMvc;

    private SingleSignOnRecords singleSignOnRecords;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SingleSignOnRecords createEntity(EntityManager em) {
        SingleSignOnRecords singleSignOnRecords = new SingleSignOnRecords()
            .userId(DEFAULT_USER_ID)
            .externalId(DEFAULT_EXTERNAL_ID)
            .lastPayload(DEFAULT_LAST_PAYLOAD)
            .externalUsername(DEFAULT_EXTERNAL_USERNAME)
            .externalEmail(DEFAULT_EXTERNAL_EMAIL)
            .externalName(DEFAULT_EXTERNAL_NAME)
            .externalAvatarUrl(DEFAULT_EXTERNAL_AVATAR_URL)
            .externalProfileBackgroundUrl(DEFAULT_EXTERNAL_PROFILE_BACKGROUND_URL)
            .externalCardBackgroundUrl(DEFAULT_EXTERNAL_CARD_BACKGROUND_URL);
        return singleSignOnRecords;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SingleSignOnRecords createUpdatedEntity(EntityManager em) {
        SingleSignOnRecords singleSignOnRecords = new SingleSignOnRecords()
            .userId(UPDATED_USER_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .lastPayload(UPDATED_LAST_PAYLOAD)
            .externalUsername(UPDATED_EXTERNAL_USERNAME)
            .externalEmail(UPDATED_EXTERNAL_EMAIL)
            .externalName(UPDATED_EXTERNAL_NAME)
            .externalAvatarUrl(UPDATED_EXTERNAL_AVATAR_URL)
            .externalProfileBackgroundUrl(UPDATED_EXTERNAL_PROFILE_BACKGROUND_URL)
            .externalCardBackgroundUrl(UPDATED_EXTERNAL_CARD_BACKGROUND_URL);
        return singleSignOnRecords;
    }

    @BeforeEach
    public void initTest() {
        singleSignOnRecords = createEntity(em);
    }

    @Test
    @Transactional
    public void createSingleSignOnRecords() throws Exception {
        int databaseSizeBeforeCreate = singleSignOnRecordsRepository.findAll().size();
        // Create the SingleSignOnRecords
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(singleSignOnRecords);
        restSingleSignOnRecordsMockMvc.perform(post("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isCreated());

        // Validate the SingleSignOnRecords in the database
        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeCreate + 1);
        SingleSignOnRecords testSingleSignOnRecords = singleSignOnRecordsList.get(singleSignOnRecordsList.size() - 1);
        assertThat(testSingleSignOnRecords.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSingleSignOnRecords.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testSingleSignOnRecords.getLastPayload()).isEqualTo(DEFAULT_LAST_PAYLOAD);
        assertThat(testSingleSignOnRecords.getExternalUsername()).isEqualTo(DEFAULT_EXTERNAL_USERNAME);
        assertThat(testSingleSignOnRecords.getExternalEmail()).isEqualTo(DEFAULT_EXTERNAL_EMAIL);
        assertThat(testSingleSignOnRecords.getExternalName()).isEqualTo(DEFAULT_EXTERNAL_NAME);
        assertThat(testSingleSignOnRecords.getExternalAvatarUrl()).isEqualTo(DEFAULT_EXTERNAL_AVATAR_URL);
        assertThat(testSingleSignOnRecords.getExternalProfileBackgroundUrl()).isEqualTo(DEFAULT_EXTERNAL_PROFILE_BACKGROUND_URL);
        assertThat(testSingleSignOnRecords.getExternalCardBackgroundUrl()).isEqualTo(DEFAULT_EXTERNAL_CARD_BACKGROUND_URL);
    }

    @Test
    @Transactional
    public void createSingleSignOnRecordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = singleSignOnRecordsRepository.findAll().size();

        // Create the SingleSignOnRecords with an existing ID
        singleSignOnRecords.setId(1L);
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(singleSignOnRecords);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSingleSignOnRecordsMockMvc.perform(post("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SingleSignOnRecords in the database
        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = singleSignOnRecordsRepository.findAll().size();
        // set the field null
        singleSignOnRecords.setUserId(null);

        // Create the SingleSignOnRecords, which fails.
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(singleSignOnRecords);


        restSingleSignOnRecordsMockMvc.perform(post("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isBadRequest());

        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExternalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = singleSignOnRecordsRepository.findAll().size();
        // set the field null
        singleSignOnRecords.setExternalId(null);

        // Create the SingleSignOnRecords, which fails.
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(singleSignOnRecords);


        restSingleSignOnRecordsMockMvc.perform(post("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isBadRequest());

        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastPayloadIsRequired() throws Exception {
        int databaseSizeBeforeTest = singleSignOnRecordsRepository.findAll().size();
        // set the field null
        singleSignOnRecords.setLastPayload(null);

        // Create the SingleSignOnRecords, which fails.
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(singleSignOnRecords);


        restSingleSignOnRecordsMockMvc.perform(post("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isBadRequest());

        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSingleSignOnRecords() throws Exception {
        // Initialize the database
        singleSignOnRecordsRepository.saveAndFlush(singleSignOnRecords);

        // Get all the singleSignOnRecordsList
        restSingleSignOnRecordsMockMvc.perform(get("/api/single-sign-on-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(singleSignOnRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].lastPayload").value(hasItem(DEFAULT_LAST_PAYLOAD)))
            .andExpect(jsonPath("$.[*].externalUsername").value(hasItem(DEFAULT_EXTERNAL_USERNAME)))
            .andExpect(jsonPath("$.[*].externalEmail").value(hasItem(DEFAULT_EXTERNAL_EMAIL)))
            .andExpect(jsonPath("$.[*].externalName").value(hasItem(DEFAULT_EXTERNAL_NAME)))
            .andExpect(jsonPath("$.[*].externalAvatarUrl").value(hasItem(DEFAULT_EXTERNAL_AVATAR_URL)))
            .andExpect(jsonPath("$.[*].externalProfileBackgroundUrl").value(hasItem(DEFAULT_EXTERNAL_PROFILE_BACKGROUND_URL)))
            .andExpect(jsonPath("$.[*].externalCardBackgroundUrl").value(hasItem(DEFAULT_EXTERNAL_CARD_BACKGROUND_URL)));
    }

    @Test
    @Transactional
    public void getSingleSignOnRecords() throws Exception {
        // Initialize the database
        singleSignOnRecordsRepository.saveAndFlush(singleSignOnRecords);

        // Get the singleSignOnRecords
        restSingleSignOnRecordsMockMvc.perform(get("/api/single-sign-on-records/{id}", singleSignOnRecords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(singleSignOnRecords.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.lastPayload").value(DEFAULT_LAST_PAYLOAD))
            .andExpect(jsonPath("$.externalUsername").value(DEFAULT_EXTERNAL_USERNAME))
            .andExpect(jsonPath("$.externalEmail").value(DEFAULT_EXTERNAL_EMAIL))
            .andExpect(jsonPath("$.externalName").value(DEFAULT_EXTERNAL_NAME))
            .andExpect(jsonPath("$.externalAvatarUrl").value(DEFAULT_EXTERNAL_AVATAR_URL))
            .andExpect(jsonPath("$.externalProfileBackgroundUrl").value(DEFAULT_EXTERNAL_PROFILE_BACKGROUND_URL))
            .andExpect(jsonPath("$.externalCardBackgroundUrl").value(DEFAULT_EXTERNAL_CARD_BACKGROUND_URL));
    }
    @Test
    @Transactional
    public void getNonExistingSingleSignOnRecords() throws Exception {
        // Get the singleSignOnRecords
        restSingleSignOnRecordsMockMvc.perform(get("/api/single-sign-on-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSingleSignOnRecords() throws Exception {
        // Initialize the database
        singleSignOnRecordsRepository.saveAndFlush(singleSignOnRecords);

        int databaseSizeBeforeUpdate = singleSignOnRecordsRepository.findAll().size();

        // Update the singleSignOnRecords
        SingleSignOnRecords updatedSingleSignOnRecords = singleSignOnRecordsRepository.findById(singleSignOnRecords.getId()).get();
        // Disconnect from session so that the updates on updatedSingleSignOnRecords are not directly saved in db
        em.detach(updatedSingleSignOnRecords);
        updatedSingleSignOnRecords
            .userId(UPDATED_USER_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .lastPayload(UPDATED_LAST_PAYLOAD)
            .externalUsername(UPDATED_EXTERNAL_USERNAME)
            .externalEmail(UPDATED_EXTERNAL_EMAIL)
            .externalName(UPDATED_EXTERNAL_NAME)
            .externalAvatarUrl(UPDATED_EXTERNAL_AVATAR_URL)
            .externalProfileBackgroundUrl(UPDATED_EXTERNAL_PROFILE_BACKGROUND_URL)
            .externalCardBackgroundUrl(UPDATED_EXTERNAL_CARD_BACKGROUND_URL);
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(updatedSingleSignOnRecords);

        restSingleSignOnRecordsMockMvc.perform(put("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isOk());

        // Validate the SingleSignOnRecords in the database
        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeUpdate);
        SingleSignOnRecords testSingleSignOnRecords = singleSignOnRecordsList.get(singleSignOnRecordsList.size() - 1);
        assertThat(testSingleSignOnRecords.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSingleSignOnRecords.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testSingleSignOnRecords.getLastPayload()).isEqualTo(UPDATED_LAST_PAYLOAD);
        assertThat(testSingleSignOnRecords.getExternalUsername()).isEqualTo(UPDATED_EXTERNAL_USERNAME);
        assertThat(testSingleSignOnRecords.getExternalEmail()).isEqualTo(UPDATED_EXTERNAL_EMAIL);
        assertThat(testSingleSignOnRecords.getExternalName()).isEqualTo(UPDATED_EXTERNAL_NAME);
        assertThat(testSingleSignOnRecords.getExternalAvatarUrl()).isEqualTo(UPDATED_EXTERNAL_AVATAR_URL);
        assertThat(testSingleSignOnRecords.getExternalProfileBackgroundUrl()).isEqualTo(UPDATED_EXTERNAL_PROFILE_BACKGROUND_URL);
        assertThat(testSingleSignOnRecords.getExternalCardBackgroundUrl()).isEqualTo(UPDATED_EXTERNAL_CARD_BACKGROUND_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingSingleSignOnRecords() throws Exception {
        int databaseSizeBeforeUpdate = singleSignOnRecordsRepository.findAll().size();

        // Create the SingleSignOnRecords
        SingleSignOnRecordsDTO singleSignOnRecordsDTO = singleSignOnRecordsMapper.toDto(singleSignOnRecords);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSingleSignOnRecordsMockMvc.perform(put("/api/single-sign-on-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singleSignOnRecordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SingleSignOnRecords in the database
        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSingleSignOnRecords() throws Exception {
        // Initialize the database
        singleSignOnRecordsRepository.saveAndFlush(singleSignOnRecords);

        int databaseSizeBeforeDelete = singleSignOnRecordsRepository.findAll().size();

        // Delete the singleSignOnRecords
        restSingleSignOnRecordsMockMvc.perform(delete("/api/single-sign-on-records/{id}", singleSignOnRecords.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SingleSignOnRecords> singleSignOnRecordsList = singleSignOnRecordsRepository.findAll();
        assertThat(singleSignOnRecordsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
