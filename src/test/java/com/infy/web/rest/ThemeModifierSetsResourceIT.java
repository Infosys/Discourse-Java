/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ThemeModifierSets;
import com.infy.repository.ThemeModifierSetsRepository;
import com.infy.service.ThemeModifierSetsService;
import com.infy.service.dto.ThemeModifierSetsDTO;
import com.infy.service.mapper.ThemeModifierSetsMapper;

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
 * Integration tests for the {@link ThemeModifierSetsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ThemeModifierSetsResourceIT {

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    private static final Boolean DEFAULT_SERIALIZE_TOPIC_EXCERPTS = false;
    private static final Boolean UPDATED_SERIALIZE_TOPIC_EXCERPTS = true;

    private static final String DEFAULT_CSP_EXTENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_CSP_EXTENSIONS = "BBBBBBBBBB";

    private static final String DEFAULT_SVG_ICONS = "AAAAAAAAAA";
    private static final String UPDATED_SVG_ICONS = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC_THUMBNAIL_SIZES = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_THUMBNAIL_SIZES = "BBBBBBBBBB";

    @Autowired
    private ThemeModifierSetsRepository themeModifierSetsRepository;

    @Autowired
    private ThemeModifierSetsMapper themeModifierSetsMapper;

    @Autowired
    private ThemeModifierSetsService themeModifierSetsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeModifierSetsMockMvc;

    private ThemeModifierSets themeModifierSets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeModifierSets createEntity(EntityManager em) {
        ThemeModifierSets themeModifierSets = new ThemeModifierSets()
            .themeId(DEFAULT_THEME_ID)
            .serializeTopicExcerpts(DEFAULT_SERIALIZE_TOPIC_EXCERPTS)
            .cspExtensions(DEFAULT_CSP_EXTENSIONS)
            .svgIcons(DEFAULT_SVG_ICONS)
            .topicThumbnailSizes(DEFAULT_TOPIC_THUMBNAIL_SIZES);
        return themeModifierSets;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeModifierSets createUpdatedEntity(EntityManager em) {
        ThemeModifierSets themeModifierSets = new ThemeModifierSets()
            .themeId(UPDATED_THEME_ID)
            .serializeTopicExcerpts(UPDATED_SERIALIZE_TOPIC_EXCERPTS)
            .cspExtensions(UPDATED_CSP_EXTENSIONS)
            .svgIcons(UPDATED_SVG_ICONS)
            .topicThumbnailSizes(UPDATED_TOPIC_THUMBNAIL_SIZES);
        return themeModifierSets;
    }

    @BeforeEach
    public void initTest() {
        themeModifierSets = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemeModifierSets() throws Exception {
        int databaseSizeBeforeCreate = themeModifierSetsRepository.findAll().size();
        // Create the ThemeModifierSets
        ThemeModifierSetsDTO themeModifierSetsDTO = themeModifierSetsMapper.toDto(themeModifierSets);
        restThemeModifierSetsMockMvc.perform(post("/api/theme-modifier-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeModifierSetsDTO)))
            .andExpect(status().isCreated());

        // Validate the ThemeModifierSets in the database
        List<ThemeModifierSets> themeModifierSetsList = themeModifierSetsRepository.findAll();
        assertThat(themeModifierSetsList).hasSize(databaseSizeBeforeCreate + 1);
        ThemeModifierSets testThemeModifierSets = themeModifierSetsList.get(themeModifierSetsList.size() - 1);
        assertThat(testThemeModifierSets.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
        assertThat(testThemeModifierSets.isSerializeTopicExcerpts()).isEqualTo(DEFAULT_SERIALIZE_TOPIC_EXCERPTS);
        assertThat(testThemeModifierSets.getCspExtensions()).isEqualTo(DEFAULT_CSP_EXTENSIONS);
        assertThat(testThemeModifierSets.getSvgIcons()).isEqualTo(DEFAULT_SVG_ICONS);
        assertThat(testThemeModifierSets.getTopicThumbnailSizes()).isEqualTo(DEFAULT_TOPIC_THUMBNAIL_SIZES);
    }

    @Test
    @Transactional
    public void createThemeModifierSetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeModifierSetsRepository.findAll().size();

        // Create the ThemeModifierSets with an existing ID
        themeModifierSets.setId(1L);
        ThemeModifierSetsDTO themeModifierSetsDTO = themeModifierSetsMapper.toDto(themeModifierSets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeModifierSetsMockMvc.perform(post("/api/theme-modifier-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeModifierSetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeModifierSets in the database
        List<ThemeModifierSets> themeModifierSetsList = themeModifierSetsRepository.findAll();
        assertThat(themeModifierSetsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkThemeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeModifierSetsRepository.findAll().size();
        // set the field null
        themeModifierSets.setThemeId(null);

        // Create the ThemeModifierSets, which fails.
        ThemeModifierSetsDTO themeModifierSetsDTO = themeModifierSetsMapper.toDto(themeModifierSets);


        restThemeModifierSetsMockMvc.perform(post("/api/theme-modifier-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeModifierSetsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeModifierSets> themeModifierSetsList = themeModifierSetsRepository.findAll();
        assertThat(themeModifierSetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemeModifierSets() throws Exception {
        // Initialize the database
        themeModifierSetsRepository.saveAndFlush(themeModifierSets);

        // Get all the themeModifierSetsList
        restThemeModifierSetsMockMvc.perform(get("/api/theme-modifier-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themeModifierSets.getId().intValue())))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].serializeTopicExcerpts").value(hasItem(DEFAULT_SERIALIZE_TOPIC_EXCERPTS.booleanValue())))
            .andExpect(jsonPath("$.[*].cspExtensions").value(hasItem(DEFAULT_CSP_EXTENSIONS)))
            .andExpect(jsonPath("$.[*].svgIcons").value(hasItem(DEFAULT_SVG_ICONS)))
            .andExpect(jsonPath("$.[*].topicThumbnailSizes").value(hasItem(DEFAULT_TOPIC_THUMBNAIL_SIZES)));
    }

    @Test
    @Transactional
    public void getThemeModifierSets() throws Exception {
        // Initialize the database
        themeModifierSetsRepository.saveAndFlush(themeModifierSets);

        // Get the themeModifierSets
        restThemeModifierSetsMockMvc.perform(get("/api/theme-modifier-sets/{id}", themeModifierSets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(themeModifierSets.getId().intValue()))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()))
            .andExpect(jsonPath("$.serializeTopicExcerpts").value(DEFAULT_SERIALIZE_TOPIC_EXCERPTS.booleanValue()))
            .andExpect(jsonPath("$.cspExtensions").value(DEFAULT_CSP_EXTENSIONS))
            .andExpect(jsonPath("$.svgIcons").value(DEFAULT_SVG_ICONS))
            .andExpect(jsonPath("$.topicThumbnailSizes").value(DEFAULT_TOPIC_THUMBNAIL_SIZES));
    }
    @Test
    @Transactional
    public void getNonExistingThemeModifierSets() throws Exception {
        // Get the themeModifierSets
        restThemeModifierSetsMockMvc.perform(get("/api/theme-modifier-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemeModifierSets() throws Exception {
        // Initialize the database
        themeModifierSetsRepository.saveAndFlush(themeModifierSets);

        int databaseSizeBeforeUpdate = themeModifierSetsRepository.findAll().size();

        // Update the themeModifierSets
        ThemeModifierSets updatedThemeModifierSets = themeModifierSetsRepository.findById(themeModifierSets.getId()).get();
        // Disconnect from session so that the updates on updatedThemeModifierSets are not directly saved in db
        em.detach(updatedThemeModifierSets);
        updatedThemeModifierSets
            .themeId(UPDATED_THEME_ID)
            .serializeTopicExcerpts(UPDATED_SERIALIZE_TOPIC_EXCERPTS)
            .cspExtensions(UPDATED_CSP_EXTENSIONS)
            .svgIcons(UPDATED_SVG_ICONS)
            .topicThumbnailSizes(UPDATED_TOPIC_THUMBNAIL_SIZES);
        ThemeModifierSetsDTO themeModifierSetsDTO = themeModifierSetsMapper.toDto(updatedThemeModifierSets);

        restThemeModifierSetsMockMvc.perform(put("/api/theme-modifier-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeModifierSetsDTO)))
            .andExpect(status().isOk());

        // Validate the ThemeModifierSets in the database
        List<ThemeModifierSets> themeModifierSetsList = themeModifierSetsRepository.findAll();
        assertThat(themeModifierSetsList).hasSize(databaseSizeBeforeUpdate);
        ThemeModifierSets testThemeModifierSets = themeModifierSetsList.get(themeModifierSetsList.size() - 1);
        assertThat(testThemeModifierSets.getThemeId()).isEqualTo(UPDATED_THEME_ID);
        assertThat(testThemeModifierSets.isSerializeTopicExcerpts()).isEqualTo(UPDATED_SERIALIZE_TOPIC_EXCERPTS);
        assertThat(testThemeModifierSets.getCspExtensions()).isEqualTo(UPDATED_CSP_EXTENSIONS);
        assertThat(testThemeModifierSets.getSvgIcons()).isEqualTo(UPDATED_SVG_ICONS);
        assertThat(testThemeModifierSets.getTopicThumbnailSizes()).isEqualTo(UPDATED_TOPIC_THUMBNAIL_SIZES);
    }

    @Test
    @Transactional
    public void updateNonExistingThemeModifierSets() throws Exception {
        int databaseSizeBeforeUpdate = themeModifierSetsRepository.findAll().size();

        // Create the ThemeModifierSets
        ThemeModifierSetsDTO themeModifierSetsDTO = themeModifierSetsMapper.toDto(themeModifierSets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeModifierSetsMockMvc.perform(put("/api/theme-modifier-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeModifierSetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeModifierSets in the database
        List<ThemeModifierSets> themeModifierSetsList = themeModifierSetsRepository.findAll();
        assertThat(themeModifierSetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThemeModifierSets() throws Exception {
        // Initialize the database
        themeModifierSetsRepository.saveAndFlush(themeModifierSets);

        int databaseSizeBeforeDelete = themeModifierSetsRepository.findAll().size();

        // Delete the themeModifierSets
        restThemeModifierSetsMockMvc.perform(delete("/api/theme-modifier-sets/{id}", themeModifierSets.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThemeModifierSets> themeModifierSetsList = themeModifierSetsRepository.findAll();
        assertThat(themeModifierSetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
