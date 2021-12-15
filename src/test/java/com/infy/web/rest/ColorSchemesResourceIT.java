/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ColorSchemes;
import com.infy.repository.ColorSchemesRepository;
import com.infy.service.ColorSchemesService;
import com.infy.service.dto.ColorSchemesDTO;
import com.infy.service.mapper.ColorSchemesMapper;

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
 * Integration tests for the {@link ColorSchemesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ColorSchemesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Boolean DEFAULT_VIA_WIZARD = false;
    private static final Boolean UPDATED_VIA_WIZARD = true;

    private static final String DEFAULT_BASE_SCHEME_ID = "AAAAAAAAAA";
    private static final String UPDATED_BASE_SCHEME_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    private static final Boolean DEFAULT_USER_SELECTABLE = false;
    private static final Boolean UPDATED_USER_SELECTABLE = true;

    @Autowired
    private ColorSchemesRepository colorSchemesRepository;

    @Autowired
    private ColorSchemesMapper colorSchemesMapper;

    @Autowired
    private ColorSchemesService colorSchemesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColorSchemesMockMvc;

    private ColorSchemes colorSchemes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColorSchemes createEntity(EntityManager em) {
        ColorSchemes colorSchemes = new ColorSchemes()
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .viaWizard(DEFAULT_VIA_WIZARD)
            .baseSchemeId(DEFAULT_BASE_SCHEME_ID)
            .themeId(DEFAULT_THEME_ID)
            .userSelectable(DEFAULT_USER_SELECTABLE);
        return colorSchemes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColorSchemes createUpdatedEntity(EntityManager em) {
        ColorSchemes colorSchemes = new ColorSchemes()
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .viaWizard(UPDATED_VIA_WIZARD)
            .baseSchemeId(UPDATED_BASE_SCHEME_ID)
            .themeId(UPDATED_THEME_ID)
            .userSelectable(UPDATED_USER_SELECTABLE);
        return colorSchemes;
    }

    @BeforeEach
    public void initTest() {
        colorSchemes = createEntity(em);
    }

    @Test
    @Transactional
    public void createColorSchemes() throws Exception {
        int databaseSizeBeforeCreate = colorSchemesRepository.findAll().size();
        // Create the ColorSchemes
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);
        restColorSchemesMockMvc.perform(post("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isCreated());

        // Validate the ColorSchemes in the database
        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeCreate + 1);
        ColorSchemes testColorSchemes = colorSchemesList.get(colorSchemesList.size() - 1);
        assertThat(testColorSchemes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testColorSchemes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testColorSchemes.isViaWizard()).isEqualTo(DEFAULT_VIA_WIZARD);
        assertThat(testColorSchemes.getBaseSchemeId()).isEqualTo(DEFAULT_BASE_SCHEME_ID);
        assertThat(testColorSchemes.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
        assertThat(testColorSchemes.isUserSelectable()).isEqualTo(DEFAULT_USER_SELECTABLE);
    }

    @Test
    @Transactional
    public void createColorSchemesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colorSchemesRepository.findAll().size();

        // Create the ColorSchemes with an existing ID
        colorSchemes.setId(1L);
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColorSchemesMockMvc.perform(post("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColorSchemes in the database
        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemesRepository.findAll().size();
        // set the field null
        colorSchemes.setName(null);

        // Create the ColorSchemes, which fails.
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);


        restColorSchemesMockMvc.perform(post("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemesRepository.findAll().size();
        // set the field null
        colorSchemes.setVersion(null);

        // Create the ColorSchemes, which fails.
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);


        restColorSchemesMockMvc.perform(post("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViaWizardIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemesRepository.findAll().size();
        // set the field null
        colorSchemes.setViaWizard(null);

        // Create the ColorSchemes, which fails.
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);


        restColorSchemesMockMvc.perform(post("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserSelectableIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemesRepository.findAll().size();
        // set the field null
        colorSchemes.setUserSelectable(null);

        // Create the ColorSchemes, which fails.
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);


        restColorSchemesMockMvc.perform(post("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColorSchemes() throws Exception {
        // Initialize the database
        colorSchemesRepository.saveAndFlush(colorSchemes);

        // Get all the colorSchemesList
        restColorSchemesMockMvc.perform(get("/api/color-schemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colorSchemes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].viaWizard").value(hasItem(DEFAULT_VIA_WIZARD.booleanValue())))
            .andExpect(jsonPath("$.[*].baseSchemeId").value(hasItem(DEFAULT_BASE_SCHEME_ID)))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].userSelectable").value(hasItem(DEFAULT_USER_SELECTABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getColorSchemes() throws Exception {
        // Initialize the database
        colorSchemesRepository.saveAndFlush(colorSchemes);

        // Get the colorSchemes
        restColorSchemesMockMvc.perform(get("/api/color-schemes/{id}", colorSchemes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colorSchemes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.viaWizard").value(DEFAULT_VIA_WIZARD.booleanValue()))
            .andExpect(jsonPath("$.baseSchemeId").value(DEFAULT_BASE_SCHEME_ID))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()))
            .andExpect(jsonPath("$.userSelectable").value(DEFAULT_USER_SELECTABLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingColorSchemes() throws Exception {
        // Get the colorSchemes
        restColorSchemesMockMvc.perform(get("/api/color-schemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColorSchemes() throws Exception {
        // Initialize the database
        colorSchemesRepository.saveAndFlush(colorSchemes);

        int databaseSizeBeforeUpdate = colorSchemesRepository.findAll().size();

        // Update the colorSchemes
        ColorSchemes updatedColorSchemes = colorSchemesRepository.findById(colorSchemes.getId()).get();
        // Disconnect from session so that the updates on updatedColorSchemes are not directly saved in db
        em.detach(updatedColorSchemes);
        updatedColorSchemes
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .viaWizard(UPDATED_VIA_WIZARD)
            .baseSchemeId(UPDATED_BASE_SCHEME_ID)
            .themeId(UPDATED_THEME_ID)
            .userSelectable(UPDATED_USER_SELECTABLE);
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(updatedColorSchemes);

        restColorSchemesMockMvc.perform(put("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isOk());

        // Validate the ColorSchemes in the database
        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeUpdate);
        ColorSchemes testColorSchemes = colorSchemesList.get(colorSchemesList.size() - 1);
        assertThat(testColorSchemes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testColorSchemes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testColorSchemes.isViaWizard()).isEqualTo(UPDATED_VIA_WIZARD);
        assertThat(testColorSchemes.getBaseSchemeId()).isEqualTo(UPDATED_BASE_SCHEME_ID);
        assertThat(testColorSchemes.getThemeId()).isEqualTo(UPDATED_THEME_ID);
        assertThat(testColorSchemes.isUserSelectable()).isEqualTo(UPDATED_USER_SELECTABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingColorSchemes() throws Exception {
        int databaseSizeBeforeUpdate = colorSchemesRepository.findAll().size();

        // Create the ColorSchemes
        ColorSchemesDTO colorSchemesDTO = colorSchemesMapper.toDto(colorSchemes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColorSchemesMockMvc.perform(put("/api/color-schemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColorSchemes in the database
        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColorSchemes() throws Exception {
        // Initialize the database
        colorSchemesRepository.saveAndFlush(colorSchemes);

        int databaseSizeBeforeDelete = colorSchemesRepository.findAll().size();

        // Delete the colorSchemes
        restColorSchemesMockMvc.perform(delete("/api/color-schemes/{id}", colorSchemes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ColorSchemes> colorSchemesList = colorSchemesRepository.findAll();
        assertThat(colorSchemesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
