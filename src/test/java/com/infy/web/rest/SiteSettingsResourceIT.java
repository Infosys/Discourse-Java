/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SiteSettings;
import com.infy.repository.SiteSettingsRepository;
import com.infy.service.SiteSettingsService;
import com.infy.service.dto.SiteSettingsDTO;
import com.infy.service.mapper.SiteSettingsMapper;

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
 * Integration tests for the {@link SiteSettingsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SiteSettingsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DATA_TYPE = 1;
    private static final Integer UPDATED_DATA_TYPE = 2;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SiteSettingsRepository siteSettingsRepository;

    @Autowired
    private SiteSettingsMapper siteSettingsMapper;

    @Autowired
    private SiteSettingsService siteSettingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteSettingsMockMvc;

    private SiteSettings siteSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSettings createEntity(EntityManager em) {
        SiteSettings siteSettings = new SiteSettings()
            .name(DEFAULT_NAME)
            .dataType(DEFAULT_DATA_TYPE)
            .value(DEFAULT_VALUE);
        return siteSettings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSettings createUpdatedEntity(EntityManager em) {
        SiteSettings siteSettings = new SiteSettings()
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .value(UPDATED_VALUE);
        return siteSettings;
    }

    @BeforeEach
    public void initTest() {
        siteSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiteSettings() throws Exception {
        int databaseSizeBeforeCreate = siteSettingsRepository.findAll().size();
        // Create the SiteSettings
        SiteSettingsDTO siteSettingsDTO = siteSettingsMapper.toDto(siteSettings);
        restSiteSettingsMockMvc.perform(post("/api/site-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteSettingsDTO)))
            .andExpect(status().isCreated());

        // Validate the SiteSettings in the database
        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        SiteSettings testSiteSettings = siteSettingsList.get(siteSettingsList.size() - 1);
        assertThat(testSiteSettings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSiteSettings.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testSiteSettings.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSiteSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteSettingsRepository.findAll().size();

        // Create the SiteSettings with an existing ID
        siteSettings.setId(1L);
        SiteSettingsDTO siteSettingsDTO = siteSettingsMapper.toDto(siteSettings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteSettingsMockMvc.perform(post("/api/site-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteSettings in the database
        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteSettingsRepository.findAll().size();
        // set the field null
        siteSettings.setName(null);

        // Create the SiteSettings, which fails.
        SiteSettingsDTO siteSettingsDTO = siteSettingsMapper.toDto(siteSettings);


        restSiteSettingsMockMvc.perform(post("/api/site-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteSettingsRepository.findAll().size();
        // set the field null
        siteSettings.setDataType(null);

        // Create the SiteSettings, which fails.
        SiteSettingsDTO siteSettingsDTO = siteSettingsMapper.toDto(siteSettings);


        restSiteSettingsMockMvc.perform(post("/api/site-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSiteSettings() throws Exception {
        // Initialize the database
        siteSettingsRepository.saveAndFlush(siteSettings);

        // Get all the siteSettingsList
        restSiteSettingsMockMvc.perform(get("/api/site-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getSiteSettings() throws Exception {
        // Initialize the database
        siteSettingsRepository.saveAndFlush(siteSettings);

        // Get the siteSettings
        restSiteSettingsMockMvc.perform(get("/api/site-settings/{id}", siteSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteSettings.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingSiteSettings() throws Exception {
        // Get the siteSettings
        restSiteSettingsMockMvc.perform(get("/api/site-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiteSettings() throws Exception {
        // Initialize the database
        siteSettingsRepository.saveAndFlush(siteSettings);

        int databaseSizeBeforeUpdate = siteSettingsRepository.findAll().size();

        // Update the siteSettings
        SiteSettings updatedSiteSettings = siteSettingsRepository.findById(siteSettings.getId()).get();
        // Disconnect from session so that the updates on updatedSiteSettings are not directly saved in db
        em.detach(updatedSiteSettings);
        updatedSiteSettings
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .value(UPDATED_VALUE);
        SiteSettingsDTO siteSettingsDTO = siteSettingsMapper.toDto(updatedSiteSettings);

        restSiteSettingsMockMvc.perform(put("/api/site-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteSettingsDTO)))
            .andExpect(status().isOk());

        // Validate the SiteSettings in the database
        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeUpdate);
        SiteSettings testSiteSettings = siteSettingsList.get(siteSettingsList.size() - 1);
        assertThat(testSiteSettings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSiteSettings.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testSiteSettings.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSiteSettings() throws Exception {
        int databaseSizeBeforeUpdate = siteSettingsRepository.findAll().size();

        // Create the SiteSettings
        SiteSettingsDTO siteSettingsDTO = siteSettingsMapper.toDto(siteSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSettingsMockMvc.perform(put("/api/site-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteSettings in the database
        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSiteSettings() throws Exception {
        // Initialize the database
        siteSettingsRepository.saveAndFlush(siteSettings);

        int databaseSizeBeforeDelete = siteSettingsRepository.findAll().size();

        // Delete the siteSettings
        restSiteSettingsMockMvc.perform(delete("/api/site-settings/{id}", siteSettings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteSettings> siteSettingsList = siteSettingsRepository.findAll();
        assertThat(siteSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
