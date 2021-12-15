/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Themes;
import com.infy.repository.ThemesRepository;
import com.infy.service.ThemesService;
import com.infy.service.dto.ThemesDTO;
import com.infy.service.mapper.ThemesMapper;

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
 * Integration tests for the {@link ThemesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ThemesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPILER_VERSION = 1;
    private static final Integer UPDATED_COMPILER_VERSION = 2;

    private static final Boolean DEFAULT_USER_SELECTABLE = false;
    private static final Boolean UPDATED_USER_SELECTABLE = true;

    private static final Boolean DEFAULT_HIDDEN = false;
    private static final Boolean UPDATED_HIDDEN = true;

    private static final Long DEFAULT_COLOR_SCHEME_ID = 1L;
    private static final Long UPDATED_COLOR_SCHEME_ID = 2L;

    private static final Long DEFAULT_REMOTE_THEME_ID = 1L;
    private static final Long UPDATED_REMOTE_THEME_ID = 2L;

    private static final Boolean DEFAULT_COMPONENT_AVAILABLE = false;
    private static final Boolean UPDATED_COMPONENT_AVAILABLE = true;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Boolean DEFAULT_AUTO_UPDATE = false;
    private static final Boolean UPDATED_AUTO_UPDATE = true;

    @Autowired
    private ThemesRepository themesRepository;

    @Autowired
    private ThemesMapper themesMapper;

    @Autowired
    private ThemesService themesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemesMockMvc;

    private Themes themes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Themes createEntity(EntityManager em) {
        Themes themes = new Themes()
            .name(DEFAULT_NAME)
            .userId(DEFAULT_USER_ID)
            .compilerVersion(DEFAULT_COMPILER_VERSION)
            .userSelectable(DEFAULT_USER_SELECTABLE)
            .hidden(DEFAULT_HIDDEN)
            .colorSchemeId(DEFAULT_COLOR_SCHEME_ID)
            .remoteThemeId(DEFAULT_REMOTE_THEME_ID)
            .componentAvailable(DEFAULT_COMPONENT_AVAILABLE)
            .enabled(DEFAULT_ENABLED)
            .autoUpdate(DEFAULT_AUTO_UPDATE);
        return themes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Themes createUpdatedEntity(EntityManager em) {
        Themes themes = new Themes()
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .compilerVersion(UPDATED_COMPILER_VERSION)
            .userSelectable(UPDATED_USER_SELECTABLE)
            .hidden(UPDATED_HIDDEN)
            .colorSchemeId(UPDATED_COLOR_SCHEME_ID)
            .remoteThemeId(UPDATED_REMOTE_THEME_ID)
            .componentAvailable(UPDATED_COMPONENT_AVAILABLE)
            .enabled(UPDATED_ENABLED)
            .autoUpdate(UPDATED_AUTO_UPDATE);
        return themes;
    }

    @BeforeEach
    public void initTest() {
        themes = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemes() throws Exception {
        int databaseSizeBeforeCreate = themesRepository.findAll().size();
        // Create the Themes
        ThemesDTO themesDTO = themesMapper.toDto(themes);
        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isCreated());

        // Validate the Themes in the database
        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeCreate + 1);
        Themes testThemes = themesList.get(themesList.size() - 1);
        assertThat(testThemes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThemes.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testThemes.getCompilerVersion()).isEqualTo(DEFAULT_COMPILER_VERSION);
        assertThat(testThemes.isUserSelectable()).isEqualTo(DEFAULT_USER_SELECTABLE);
        assertThat(testThemes.isHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testThemes.getColorSchemeId()).isEqualTo(DEFAULT_COLOR_SCHEME_ID);
        assertThat(testThemes.getRemoteThemeId()).isEqualTo(DEFAULT_REMOTE_THEME_ID);
        assertThat(testThemes.isComponentAvailable()).isEqualTo(DEFAULT_COMPONENT_AVAILABLE);
        assertThat(testThemes.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testThemes.isAutoUpdate()).isEqualTo(DEFAULT_AUTO_UPDATE);
    }

    @Test
    @Transactional
    public void createThemesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themesRepository.findAll().size();

        // Create the Themes with an existing ID
        themes.setId(1L);
        ThemesDTO themesDTO = themesMapper.toDto(themes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Themes in the database
        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setName(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setUserId(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompilerVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setCompilerVersion(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserSelectableIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setUserSelectable(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setHidden(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComponentAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setComponentAvailable(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setEnabled(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutoUpdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = themesRepository.findAll().size();
        // set the field null
        themes.setAutoUpdate(null);

        // Create the Themes, which fails.
        ThemesDTO themesDTO = themesMapper.toDto(themes);


        restThemesMockMvc.perform(post("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemes() throws Exception {
        // Initialize the database
        themesRepository.saveAndFlush(themes);

        // Get all the themesList
        restThemesMockMvc.perform(get("/api/themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].compilerVersion").value(hasItem(DEFAULT_COMPILER_VERSION)))
            .andExpect(jsonPath("$.[*].userSelectable").value(hasItem(DEFAULT_USER_SELECTABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].hidden").value(hasItem(DEFAULT_HIDDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].colorSchemeId").value(hasItem(DEFAULT_COLOR_SCHEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].remoteThemeId").value(hasItem(DEFAULT_REMOTE_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].componentAvailable").value(hasItem(DEFAULT_COMPONENT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].autoUpdate").value(hasItem(DEFAULT_AUTO_UPDATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getThemes() throws Exception {
        // Initialize the database
        themesRepository.saveAndFlush(themes);

        // Get the themes
        restThemesMockMvc.perform(get("/api/themes/{id}", themes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(themes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.compilerVersion").value(DEFAULT_COMPILER_VERSION))
            .andExpect(jsonPath("$.userSelectable").value(DEFAULT_USER_SELECTABLE.booleanValue()))
            .andExpect(jsonPath("$.hidden").value(DEFAULT_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.colorSchemeId").value(DEFAULT_COLOR_SCHEME_ID.intValue()))
            .andExpect(jsonPath("$.remoteThemeId").value(DEFAULT_REMOTE_THEME_ID.intValue()))
            .andExpect(jsonPath("$.componentAvailable").value(DEFAULT_COMPONENT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.autoUpdate").value(DEFAULT_AUTO_UPDATE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingThemes() throws Exception {
        // Get the themes
        restThemesMockMvc.perform(get("/api/themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemes() throws Exception {
        // Initialize the database
        themesRepository.saveAndFlush(themes);

        int databaseSizeBeforeUpdate = themesRepository.findAll().size();

        // Update the themes
        Themes updatedThemes = themesRepository.findById(themes.getId()).get();
        // Disconnect from session so that the updates on updatedThemes are not directly saved in db
        em.detach(updatedThemes);
        updatedThemes
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .compilerVersion(UPDATED_COMPILER_VERSION)
            .userSelectable(UPDATED_USER_SELECTABLE)
            .hidden(UPDATED_HIDDEN)
            .colorSchemeId(UPDATED_COLOR_SCHEME_ID)
            .remoteThemeId(UPDATED_REMOTE_THEME_ID)
            .componentAvailable(UPDATED_COMPONENT_AVAILABLE)
            .enabled(UPDATED_ENABLED)
            .autoUpdate(UPDATED_AUTO_UPDATE);
        ThemesDTO themesDTO = themesMapper.toDto(updatedThemes);

        restThemesMockMvc.perform(put("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isOk());

        // Validate the Themes in the database
        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeUpdate);
        Themes testThemes = themesList.get(themesList.size() - 1);
        assertThat(testThemes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThemes.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testThemes.getCompilerVersion()).isEqualTo(UPDATED_COMPILER_VERSION);
        assertThat(testThemes.isUserSelectable()).isEqualTo(UPDATED_USER_SELECTABLE);
        assertThat(testThemes.isHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testThemes.getColorSchemeId()).isEqualTo(UPDATED_COLOR_SCHEME_ID);
        assertThat(testThemes.getRemoteThemeId()).isEqualTo(UPDATED_REMOTE_THEME_ID);
        assertThat(testThemes.isComponentAvailable()).isEqualTo(UPDATED_COMPONENT_AVAILABLE);
        assertThat(testThemes.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testThemes.isAutoUpdate()).isEqualTo(UPDATED_AUTO_UPDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingThemes() throws Exception {
        int databaseSizeBeforeUpdate = themesRepository.findAll().size();

        // Create the Themes
        ThemesDTO themesDTO = themesMapper.toDto(themes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemesMockMvc.perform(put("/api/themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Themes in the database
        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThemes() throws Exception {
        // Initialize the database
        themesRepository.saveAndFlush(themes);

        int databaseSizeBeforeDelete = themesRepository.findAll().size();

        // Delete the themes
        restThemesMockMvc.perform(delete("/api/themes/{id}", themes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Themes> themesList = themesRepository.findAll();
        assertThat(themesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
