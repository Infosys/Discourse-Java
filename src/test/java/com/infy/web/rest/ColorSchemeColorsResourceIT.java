/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ColorSchemeColors;
import com.infy.repository.ColorSchemeColorsRepository;
import com.infy.service.ColorSchemeColorsService;
import com.infy.service.dto.ColorSchemeColorsDTO;
import com.infy.service.mapper.ColorSchemeColorsMapper;

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
 * Integration tests for the {@link ColorSchemeColorsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ColorSchemeColorsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HEX = "AAAAAAAAAA";
    private static final String UPDATED_HEX = "BBBBBBBBBB";

    private static final Long DEFAULT_COLOR_SCHEME_ID = 1L;
    private static final Long UPDATED_COLOR_SCHEME_ID = 2L;

    @Autowired
    private ColorSchemeColorsRepository colorSchemeColorsRepository;

    @Autowired
    private ColorSchemeColorsMapper colorSchemeColorsMapper;

    @Autowired
    private ColorSchemeColorsService colorSchemeColorsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColorSchemeColorsMockMvc;

    private ColorSchemeColors colorSchemeColors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColorSchemeColors createEntity(EntityManager em) {
        ColorSchemeColors colorSchemeColors = new ColorSchemeColors()
            .name(DEFAULT_NAME)
            .hex(DEFAULT_HEX)
            .colorSchemeId(DEFAULT_COLOR_SCHEME_ID);
        return colorSchemeColors;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColorSchemeColors createUpdatedEntity(EntityManager em) {
        ColorSchemeColors colorSchemeColors = new ColorSchemeColors()
            .name(UPDATED_NAME)
            .hex(UPDATED_HEX)
            .colorSchemeId(UPDATED_COLOR_SCHEME_ID);
        return colorSchemeColors;
    }

    @BeforeEach
    public void initTest() {
        colorSchemeColors = createEntity(em);
    }

    @Test
    @Transactional
    public void createColorSchemeColors() throws Exception {
        int databaseSizeBeforeCreate = colorSchemeColorsRepository.findAll().size();
        // Create the ColorSchemeColors
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(colorSchemeColors);
        restColorSchemeColorsMockMvc.perform(post("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isCreated());

        // Validate the ColorSchemeColors in the database
        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeCreate + 1);
        ColorSchemeColors testColorSchemeColors = colorSchemeColorsList.get(colorSchemeColorsList.size() - 1);
        assertThat(testColorSchemeColors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testColorSchemeColors.getHex()).isEqualTo(DEFAULT_HEX);
        assertThat(testColorSchemeColors.getColorSchemeId()).isEqualTo(DEFAULT_COLOR_SCHEME_ID);
    }

    @Test
    @Transactional
    public void createColorSchemeColorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colorSchemeColorsRepository.findAll().size();

        // Create the ColorSchemeColors with an existing ID
        colorSchemeColors.setId(1L);
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(colorSchemeColors);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColorSchemeColorsMockMvc.perform(post("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColorSchemeColors in the database
        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemeColorsRepository.findAll().size();
        // set the field null
        colorSchemeColors.setName(null);

        // Create the ColorSchemeColors, which fails.
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(colorSchemeColors);


        restColorSchemeColorsMockMvc.perform(post("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHexIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemeColorsRepository.findAll().size();
        // set the field null
        colorSchemeColors.setHex(null);

        // Create the ColorSchemeColors, which fails.
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(colorSchemeColors);


        restColorSchemeColorsMockMvc.perform(post("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorSchemeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorSchemeColorsRepository.findAll().size();
        // set the field null
        colorSchemeColors.setColorSchemeId(null);

        // Create the ColorSchemeColors, which fails.
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(colorSchemeColors);


        restColorSchemeColorsMockMvc.perform(post("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isBadRequest());

        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColorSchemeColors() throws Exception {
        // Initialize the database
        colorSchemeColorsRepository.saveAndFlush(colorSchemeColors);

        // Get all the colorSchemeColorsList
        restColorSchemeColorsMockMvc.perform(get("/api/color-scheme-colors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colorSchemeColors.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hex").value(hasItem(DEFAULT_HEX)))
            .andExpect(jsonPath("$.[*].colorSchemeId").value(hasItem(DEFAULT_COLOR_SCHEME_ID.intValue())));
    }

    @Test
    @Transactional
    public void getColorSchemeColors() throws Exception {
        // Initialize the database
        colorSchemeColorsRepository.saveAndFlush(colorSchemeColors);

        // Get the colorSchemeColors
        restColorSchemeColorsMockMvc.perform(get("/api/color-scheme-colors/{id}", colorSchemeColors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colorSchemeColors.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hex").value(DEFAULT_HEX))
            .andExpect(jsonPath("$.colorSchemeId").value(DEFAULT_COLOR_SCHEME_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingColorSchemeColors() throws Exception {
        // Get the colorSchemeColors
        restColorSchemeColorsMockMvc.perform(get("/api/color-scheme-colors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColorSchemeColors() throws Exception {
        // Initialize the database
        colorSchemeColorsRepository.saveAndFlush(colorSchemeColors);

        int databaseSizeBeforeUpdate = colorSchemeColorsRepository.findAll().size();

        // Update the colorSchemeColors
        ColorSchemeColors updatedColorSchemeColors = colorSchemeColorsRepository.findById(colorSchemeColors.getId()).get();
        // Disconnect from session so that the updates on updatedColorSchemeColors are not directly saved in db
        em.detach(updatedColorSchemeColors);
        updatedColorSchemeColors
            .name(UPDATED_NAME)
            .hex(UPDATED_HEX)
            .colorSchemeId(UPDATED_COLOR_SCHEME_ID);
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(updatedColorSchemeColors);

        restColorSchemeColorsMockMvc.perform(put("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isOk());

        // Validate the ColorSchemeColors in the database
        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeUpdate);
        ColorSchemeColors testColorSchemeColors = colorSchemeColorsList.get(colorSchemeColorsList.size() - 1);
        assertThat(testColorSchemeColors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testColorSchemeColors.getHex()).isEqualTo(UPDATED_HEX);
        assertThat(testColorSchemeColors.getColorSchemeId()).isEqualTo(UPDATED_COLOR_SCHEME_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingColorSchemeColors() throws Exception {
        int databaseSizeBeforeUpdate = colorSchemeColorsRepository.findAll().size();

        // Create the ColorSchemeColors
        ColorSchemeColorsDTO colorSchemeColorsDTO = colorSchemeColorsMapper.toDto(colorSchemeColors);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColorSchemeColorsMockMvc.perform(put("/api/color-scheme-colors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colorSchemeColorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColorSchemeColors in the database
        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColorSchemeColors() throws Exception {
        // Initialize the database
        colorSchemeColorsRepository.saveAndFlush(colorSchemeColors);

        int databaseSizeBeforeDelete = colorSchemeColorsRepository.findAll().size();

        // Delete the colorSchemeColors
        restColorSchemeColorsMockMvc.perform(delete("/api/color-scheme-colors/{id}", colorSchemeColors.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ColorSchemeColors> colorSchemeColorsList = colorSchemeColorsRepository.findAll();
        assertThat(colorSchemeColorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
