/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ThemeTranslationOverrides;
import com.infy.repository.ThemeTranslationOverridesRepository;
import com.infy.service.ThemeTranslationOverridesService;
import com.infy.service.dto.ThemeTranslationOverridesDTO;
import com.infy.service.mapper.ThemeTranslationOverridesMapper;

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
 * Integration tests for the {@link ThemeTranslationOverridesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ThemeTranslationOverridesResourceIT {

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ThemeTranslationOverridesRepository themeTranslationOverridesRepository;

    @Autowired
    private ThemeTranslationOverridesMapper themeTranslationOverridesMapper;

    @Autowired
    private ThemeTranslationOverridesService themeTranslationOverridesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeTranslationOverridesMockMvc;

    private ThemeTranslationOverrides themeTranslationOverrides;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeTranslationOverrides createEntity(EntityManager em) {
        ThemeTranslationOverrides themeTranslationOverrides = new ThemeTranslationOverrides()
            .themeId(DEFAULT_THEME_ID)
            .locale(DEFAULT_LOCALE)
            .translationKey(DEFAULT_TRANSLATION_KEY)
            .value(DEFAULT_VALUE);
        return themeTranslationOverrides;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeTranslationOverrides createUpdatedEntity(EntityManager em) {
        ThemeTranslationOverrides themeTranslationOverrides = new ThemeTranslationOverrides()
            .themeId(UPDATED_THEME_ID)
            .locale(UPDATED_LOCALE)
            .translationKey(UPDATED_TRANSLATION_KEY)
            .value(UPDATED_VALUE);
        return themeTranslationOverrides;
    }

    @BeforeEach
    public void initTest() {
        themeTranslationOverrides = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemeTranslationOverrides() throws Exception {
        int databaseSizeBeforeCreate = themeTranslationOverridesRepository.findAll().size();
        // Create the ThemeTranslationOverrides
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);
        restThemeTranslationOverridesMockMvc.perform(post("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isCreated());

        // Validate the ThemeTranslationOverrides in the database
        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeCreate + 1);
        ThemeTranslationOverrides testThemeTranslationOverrides = themeTranslationOverridesList.get(themeTranslationOverridesList.size() - 1);
        assertThat(testThemeTranslationOverrides.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
        assertThat(testThemeTranslationOverrides.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testThemeTranslationOverrides.getTranslationKey()).isEqualTo(DEFAULT_TRANSLATION_KEY);
        assertThat(testThemeTranslationOverrides.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createThemeTranslationOverridesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeTranslationOverridesRepository.findAll().size();

        // Create the ThemeTranslationOverrides with an existing ID
        themeTranslationOverrides.setId(1L);
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeTranslationOverridesMockMvc.perform(post("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeTranslationOverrides in the database
        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkThemeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeTranslationOverridesRepository.findAll().size();
        // set the field null
        themeTranslationOverrides.setThemeId(null);

        // Create the ThemeTranslationOverrides, which fails.
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);


        restThemeTranslationOverridesMockMvc.perform(post("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeTranslationOverridesRepository.findAll().size();
        // set the field null
        themeTranslationOverrides.setLocale(null);

        // Create the ThemeTranslationOverrides, which fails.
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);


        restThemeTranslationOverridesMockMvc.perform(post("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTranslationKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeTranslationOverridesRepository.findAll().size();
        // set the field null
        themeTranslationOverrides.setTranslationKey(null);

        // Create the ThemeTranslationOverrides, which fails.
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);


        restThemeTranslationOverridesMockMvc.perform(post("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeTranslationOverridesRepository.findAll().size();
        // set the field null
        themeTranslationOverrides.setValue(null);

        // Create the ThemeTranslationOverrides, which fails.
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);


        restThemeTranslationOverridesMockMvc.perform(post("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemeTranslationOverrides() throws Exception {
        // Initialize the database
        themeTranslationOverridesRepository.saveAndFlush(themeTranslationOverrides);

        // Get all the themeTranslationOverridesList
        restThemeTranslationOverridesMockMvc.perform(get("/api/theme-translation-overrides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themeTranslationOverrides.getId().intValue())))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].translationKey").value(hasItem(DEFAULT_TRANSLATION_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getThemeTranslationOverrides() throws Exception {
        // Initialize the database
        themeTranslationOverridesRepository.saveAndFlush(themeTranslationOverrides);

        // Get the themeTranslationOverrides
        restThemeTranslationOverridesMockMvc.perform(get("/api/theme-translation-overrides/{id}", themeTranslationOverrides.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(themeTranslationOverrides.getId().intValue()))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.translationKey").value(DEFAULT_TRANSLATION_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingThemeTranslationOverrides() throws Exception {
        // Get the themeTranslationOverrides
        restThemeTranslationOverridesMockMvc.perform(get("/api/theme-translation-overrides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemeTranslationOverrides() throws Exception {
        // Initialize the database
        themeTranslationOverridesRepository.saveAndFlush(themeTranslationOverrides);

        int databaseSizeBeforeUpdate = themeTranslationOverridesRepository.findAll().size();

        // Update the themeTranslationOverrides
        ThemeTranslationOverrides updatedThemeTranslationOverrides = themeTranslationOverridesRepository.findById(themeTranslationOverrides.getId()).get();
        // Disconnect from session so that the updates on updatedThemeTranslationOverrides are not directly saved in db
        em.detach(updatedThemeTranslationOverrides);
        updatedThemeTranslationOverrides
            .themeId(UPDATED_THEME_ID)
            .locale(UPDATED_LOCALE)
            .translationKey(UPDATED_TRANSLATION_KEY)
            .value(UPDATED_VALUE);
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(updatedThemeTranslationOverrides);

        restThemeTranslationOverridesMockMvc.perform(put("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isOk());

        // Validate the ThemeTranslationOverrides in the database
        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeUpdate);
        ThemeTranslationOverrides testThemeTranslationOverrides = themeTranslationOverridesList.get(themeTranslationOverridesList.size() - 1);
        assertThat(testThemeTranslationOverrides.getThemeId()).isEqualTo(UPDATED_THEME_ID);
        assertThat(testThemeTranslationOverrides.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testThemeTranslationOverrides.getTranslationKey()).isEqualTo(UPDATED_TRANSLATION_KEY);
        assertThat(testThemeTranslationOverrides.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingThemeTranslationOverrides() throws Exception {
        int databaseSizeBeforeUpdate = themeTranslationOverridesRepository.findAll().size();

        // Create the ThemeTranslationOverrides
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO = themeTranslationOverridesMapper.toDto(themeTranslationOverrides);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeTranslationOverridesMockMvc.perform(put("/api/theme-translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeTranslationOverridesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeTranslationOverrides in the database
        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThemeTranslationOverrides() throws Exception {
        // Initialize the database
        themeTranslationOverridesRepository.saveAndFlush(themeTranslationOverrides);

        int databaseSizeBeforeDelete = themeTranslationOverridesRepository.findAll().size();

        // Delete the themeTranslationOverrides
        restThemeTranslationOverridesMockMvc.perform(delete("/api/theme-translation-overrides/{id}", themeTranslationOverrides.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThemeTranslationOverrides> themeTranslationOverridesList = themeTranslationOverridesRepository.findAll();
        assertThat(themeTranslationOverridesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
