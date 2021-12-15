/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ThemeSettings;
import com.infy.repository.ThemeSettingsRepository;
import com.infy.service.ThemeSettingsService;
import com.infy.service.dto.ThemeSettingsDTO;
import com.infy.service.mapper.ThemeSettingsMapper;

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
 * Integration tests for the {@link ThemeSettingsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ThemeSettingsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DATA_TYPE = 1;
    private static final Integer UPDATED_DATA_TYPE = 2;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    @Autowired
    private ThemeSettingsRepository themeSettingsRepository;

    @Autowired
    private ThemeSettingsMapper themeSettingsMapper;

    @Autowired
    private ThemeSettingsService themeSettingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeSettingsMockMvc;

    private ThemeSettings themeSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeSettings createEntity(EntityManager em) {
        ThemeSettings themeSettings = new ThemeSettings()
            .name(DEFAULT_NAME)
            .dataType(DEFAULT_DATA_TYPE)
            .value(DEFAULT_VALUE)
            .themeId(DEFAULT_THEME_ID);
        return themeSettings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeSettings createUpdatedEntity(EntityManager em) {
        ThemeSettings themeSettings = new ThemeSettings()
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .value(UPDATED_VALUE)
            .themeId(UPDATED_THEME_ID);
        return themeSettings;
    }

    @BeforeEach
    public void initTest() {
        themeSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemeSettings() throws Exception {
        int databaseSizeBeforeCreate = themeSettingsRepository.findAll().size();
        // Create the ThemeSettings
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(themeSettings);
        restThemeSettingsMockMvc.perform(post("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isCreated());

        // Validate the ThemeSettings in the database
        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        ThemeSettings testThemeSettings = themeSettingsList.get(themeSettingsList.size() - 1);
        assertThat(testThemeSettings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThemeSettings.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testThemeSettings.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testThemeSettings.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
    }

    @Test
    @Transactional
    public void createThemeSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeSettingsRepository.findAll().size();

        // Create the ThemeSettings with an existing ID
        themeSettings.setId(1L);
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(themeSettings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeSettingsMockMvc.perform(post("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeSettings in the database
        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeSettingsRepository.findAll().size();
        // set the field null
        themeSettings.setName(null);

        // Create the ThemeSettings, which fails.
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(themeSettings);


        restThemeSettingsMockMvc.perform(post("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeSettingsRepository.findAll().size();
        // set the field null
        themeSettings.setDataType(null);

        // Create the ThemeSettings, which fails.
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(themeSettings);


        restThemeSettingsMockMvc.perform(post("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThemeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeSettingsRepository.findAll().size();
        // set the field null
        themeSettings.setThemeId(null);

        // Create the ThemeSettings, which fails.
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(themeSettings);


        restThemeSettingsMockMvc.perform(post("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemeSettings() throws Exception {
        // Initialize the database
        themeSettingsRepository.saveAndFlush(themeSettings);

        // Get all the themeSettingsList
        restThemeSettingsMockMvc.perform(get("/api/theme-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themeSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())));
    }

    @Test
    @Transactional
    public void getThemeSettings() throws Exception {
        // Initialize the database
        themeSettingsRepository.saveAndFlush(themeSettings);

        // Get the themeSettings
        restThemeSettingsMockMvc.perform(get("/api/theme-settings/{id}", themeSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(themeSettings.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingThemeSettings() throws Exception {
        // Get the themeSettings
        restThemeSettingsMockMvc.perform(get("/api/theme-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemeSettings() throws Exception {
        // Initialize the database
        themeSettingsRepository.saveAndFlush(themeSettings);

        int databaseSizeBeforeUpdate = themeSettingsRepository.findAll().size();

        // Update the themeSettings
        ThemeSettings updatedThemeSettings = themeSettingsRepository.findById(themeSettings.getId()).get();
        // Disconnect from session so that the updates on updatedThemeSettings are not directly saved in db
        em.detach(updatedThemeSettings);
        updatedThemeSettings
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .value(UPDATED_VALUE)
            .themeId(UPDATED_THEME_ID);
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(updatedThemeSettings);

        restThemeSettingsMockMvc.perform(put("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isOk());

        // Validate the ThemeSettings in the database
        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeUpdate);
        ThemeSettings testThemeSettings = themeSettingsList.get(themeSettingsList.size() - 1);
        assertThat(testThemeSettings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThemeSettings.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testThemeSettings.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testThemeSettings.getThemeId()).isEqualTo(UPDATED_THEME_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingThemeSettings() throws Exception {
        int databaseSizeBeforeUpdate = themeSettingsRepository.findAll().size();

        // Create the ThemeSettings
        ThemeSettingsDTO themeSettingsDTO = themeSettingsMapper.toDto(themeSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeSettingsMockMvc.perform(put("/api/theme-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeSettings in the database
        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThemeSettings() throws Exception {
        // Initialize the database
        themeSettingsRepository.saveAndFlush(themeSettings);

        int databaseSizeBeforeDelete = themeSettingsRepository.findAll().size();

        // Delete the themeSettings
        restThemeSettingsMockMvc.perform(delete("/api/theme-settings/{id}", themeSettings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThemeSettings> themeSettingsList = themeSettingsRepository.findAll();
        assertThat(themeSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
