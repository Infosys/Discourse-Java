/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ScreenedUrls;
import com.infy.repository.ScreenedUrlsRepository;
import com.infy.service.ScreenedUrlsService;
import com.infy.service.dto.ScreenedUrlsDTO;
import com.infy.service.mapper.ScreenedUrlsMapper;

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
 * Integration tests for the {@link ScreenedUrlsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ScreenedUrlsResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTION_TYPE = 1;
    private static final Integer UPDATED_ACTION_TYPE = 2;

    private static final Integer DEFAULT_MATCH_COUNT = 1;
    private static final Integer UPDATED_MATCH_COUNT = 2;

    private static final Instant DEFAULT_LAST_MATCH_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MATCH_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ScreenedUrlsRepository screenedUrlsRepository;

    @Autowired
    private ScreenedUrlsMapper screenedUrlsMapper;

    @Autowired
    private ScreenedUrlsService screenedUrlsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScreenedUrlsMockMvc;

    private ScreenedUrls screenedUrls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScreenedUrls createEntity(EntityManager em) {
        ScreenedUrls screenedUrls = new ScreenedUrls()
            .url(DEFAULT_URL)
            .domain(DEFAULT_DOMAIN)
            .actionType(DEFAULT_ACTION_TYPE)
            .matchCount(DEFAULT_MATCH_COUNT)
            .lastMatchAt(DEFAULT_LAST_MATCH_AT)
            .ipAddress(DEFAULT_IP_ADDRESS);
        return screenedUrls;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScreenedUrls createUpdatedEntity(EntityManager em) {
        ScreenedUrls screenedUrls = new ScreenedUrls()
            .url(UPDATED_URL)
            .domain(UPDATED_DOMAIN)
            .actionType(UPDATED_ACTION_TYPE)
            .matchCount(UPDATED_MATCH_COUNT)
            .lastMatchAt(UPDATED_LAST_MATCH_AT)
            .ipAddress(UPDATED_IP_ADDRESS);
        return screenedUrls;
    }

    @BeforeEach
    public void initTest() {
        screenedUrls = createEntity(em);
    }

    @Test
    @Transactional
    public void createScreenedUrls() throws Exception {
        int databaseSizeBeforeCreate = screenedUrlsRepository.findAll().size();
        // Create the ScreenedUrls
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);
        restScreenedUrlsMockMvc.perform(post("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isCreated());

        // Validate the ScreenedUrls in the database
        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeCreate + 1);
        ScreenedUrls testScreenedUrls = screenedUrlsList.get(screenedUrlsList.size() - 1);
        assertThat(testScreenedUrls.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testScreenedUrls.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testScreenedUrls.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testScreenedUrls.getMatchCount()).isEqualTo(DEFAULT_MATCH_COUNT);
        assertThat(testScreenedUrls.getLastMatchAt()).isEqualTo(DEFAULT_LAST_MATCH_AT);
        assertThat(testScreenedUrls.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createScreenedUrlsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = screenedUrlsRepository.findAll().size();

        // Create the ScreenedUrls with an existing ID
        screenedUrls.setId(1L);
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreenedUrlsMockMvc.perform(post("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScreenedUrls in the database
        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedUrlsRepository.findAll().size();
        // set the field null
        screenedUrls.setUrl(null);

        // Create the ScreenedUrls, which fails.
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);


        restScreenedUrlsMockMvc.perform(post("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedUrlsRepository.findAll().size();
        // set the field null
        screenedUrls.setDomain(null);

        // Create the ScreenedUrls, which fails.
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);


        restScreenedUrlsMockMvc.perform(post("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedUrlsRepository.findAll().size();
        // set the field null
        screenedUrls.setActionType(null);

        // Create the ScreenedUrls, which fails.
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);


        restScreenedUrlsMockMvc.perform(post("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatchCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedUrlsRepository.findAll().size();
        // set the field null
        screenedUrls.setMatchCount(null);

        // Create the ScreenedUrls, which fails.
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);


        restScreenedUrlsMockMvc.perform(post("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScreenedUrls() throws Exception {
        // Initialize the database
        screenedUrlsRepository.saveAndFlush(screenedUrls);

        // Get all the screenedUrlsList
        restScreenedUrlsMockMvc.perform(get("/api/screened-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screenedUrls.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE)))
            .andExpect(jsonPath("$.[*].matchCount").value(hasItem(DEFAULT_MATCH_COUNT)))
            .andExpect(jsonPath("$.[*].lastMatchAt").value(hasItem(DEFAULT_LAST_MATCH_AT.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)));
    }

    @Test
    @Transactional
    public void getScreenedUrls() throws Exception {
        // Initialize the database
        screenedUrlsRepository.saveAndFlush(screenedUrls);

        // Get the screenedUrls
        restScreenedUrlsMockMvc.perform(get("/api/screened-urls/{id}", screenedUrls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(screenedUrls.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE))
            .andExpect(jsonPath("$.matchCount").value(DEFAULT_MATCH_COUNT))
            .andExpect(jsonPath("$.lastMatchAt").value(DEFAULT_LAST_MATCH_AT.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingScreenedUrls() throws Exception {
        // Get the screenedUrls
        restScreenedUrlsMockMvc.perform(get("/api/screened-urls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScreenedUrls() throws Exception {
        // Initialize the database
        screenedUrlsRepository.saveAndFlush(screenedUrls);

        int databaseSizeBeforeUpdate = screenedUrlsRepository.findAll().size();

        // Update the screenedUrls
        ScreenedUrls updatedScreenedUrls = screenedUrlsRepository.findById(screenedUrls.getId()).get();
        // Disconnect from session so that the updates on updatedScreenedUrls are not directly saved in db
        em.detach(updatedScreenedUrls);
        updatedScreenedUrls
            .url(UPDATED_URL)
            .domain(UPDATED_DOMAIN)
            .actionType(UPDATED_ACTION_TYPE)
            .matchCount(UPDATED_MATCH_COUNT)
            .lastMatchAt(UPDATED_LAST_MATCH_AT)
            .ipAddress(UPDATED_IP_ADDRESS);
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(updatedScreenedUrls);

        restScreenedUrlsMockMvc.perform(put("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isOk());

        // Validate the ScreenedUrls in the database
        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeUpdate);
        ScreenedUrls testScreenedUrls = screenedUrlsList.get(screenedUrlsList.size() - 1);
        assertThat(testScreenedUrls.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testScreenedUrls.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testScreenedUrls.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testScreenedUrls.getMatchCount()).isEqualTo(UPDATED_MATCH_COUNT);
        assertThat(testScreenedUrls.getLastMatchAt()).isEqualTo(UPDATED_LAST_MATCH_AT);
        assertThat(testScreenedUrls.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingScreenedUrls() throws Exception {
        int databaseSizeBeforeUpdate = screenedUrlsRepository.findAll().size();

        // Create the ScreenedUrls
        ScreenedUrlsDTO screenedUrlsDTO = screenedUrlsMapper.toDto(screenedUrls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScreenedUrlsMockMvc.perform(put("/api/screened-urls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedUrlsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScreenedUrls in the database
        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScreenedUrls() throws Exception {
        // Initialize the database
        screenedUrlsRepository.saveAndFlush(screenedUrls);

        int databaseSizeBeforeDelete = screenedUrlsRepository.findAll().size();

        // Delete the screenedUrls
        restScreenedUrlsMockMvc.perform(delete("/api/screened-urls/{id}", screenedUrls.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScreenedUrls> screenedUrlsList = screenedUrlsRepository.findAll();
        assertThat(screenedUrlsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
