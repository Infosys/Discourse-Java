/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.StylesheetCache;
import com.infy.repository.StylesheetCacheRepository;
import com.infy.service.StylesheetCacheService;
import com.infy.service.dto.StylesheetCacheDTO;
import com.infy.service.mapper.StylesheetCacheMapper;

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
 * Integration tests for the {@link StylesheetCacheResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StylesheetCacheResourceIT {

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_DIGEST = "AAAAAAAAAA";
    private static final String UPDATED_DIGEST = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    private static final String DEFAULT_SOURCE_MAP = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_MAP = "BBBBBBBBBB";

    @Autowired
    private StylesheetCacheRepository stylesheetCacheRepository;

    @Autowired
    private StylesheetCacheMapper stylesheetCacheMapper;

    @Autowired
    private StylesheetCacheService stylesheetCacheService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStylesheetCacheMockMvc;

    private StylesheetCache stylesheetCache;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StylesheetCache createEntity(EntityManager em) {
        StylesheetCache stylesheetCache = new StylesheetCache()
            .target(DEFAULT_TARGET)
            .digest(DEFAULT_DIGEST)
            .content(DEFAULT_CONTENT)
            .themeId(DEFAULT_THEME_ID)
            .sourceMap(DEFAULT_SOURCE_MAP);
        return stylesheetCache;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StylesheetCache createUpdatedEntity(EntityManager em) {
        StylesheetCache stylesheetCache = new StylesheetCache()
            .target(UPDATED_TARGET)
            .digest(UPDATED_DIGEST)
            .content(UPDATED_CONTENT)
            .themeId(UPDATED_THEME_ID)
            .sourceMap(UPDATED_SOURCE_MAP);
        return stylesheetCache;
    }

    @BeforeEach
    public void initTest() {
        stylesheetCache = createEntity(em);
    }

    @Test
    @Transactional
    public void createStylesheetCache() throws Exception {
        int databaseSizeBeforeCreate = stylesheetCacheRepository.findAll().size();
        // Create the StylesheetCache
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);
        restStylesheetCacheMockMvc.perform(post("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isCreated());

        // Validate the StylesheetCache in the database
        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeCreate + 1);
        StylesheetCache testStylesheetCache = stylesheetCacheList.get(stylesheetCacheList.size() - 1);
        assertThat(testStylesheetCache.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testStylesheetCache.getDigest()).isEqualTo(DEFAULT_DIGEST);
        assertThat(testStylesheetCache.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testStylesheetCache.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
        assertThat(testStylesheetCache.getSourceMap()).isEqualTo(DEFAULT_SOURCE_MAP);
    }

    @Test
    @Transactional
    public void createStylesheetCacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stylesheetCacheRepository.findAll().size();

        // Create the StylesheetCache with an existing ID
        stylesheetCache.setId(1L);
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStylesheetCacheMockMvc.perform(post("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StylesheetCache in the database
        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTargetIsRequired() throws Exception {
        int databaseSizeBeforeTest = stylesheetCacheRepository.findAll().size();
        // set the field null
        stylesheetCache.setTarget(null);

        // Create the StylesheetCache, which fails.
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);


        restStylesheetCacheMockMvc.perform(post("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isBadRequest());

        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDigestIsRequired() throws Exception {
        int databaseSizeBeforeTest = stylesheetCacheRepository.findAll().size();
        // set the field null
        stylesheetCache.setDigest(null);

        // Create the StylesheetCache, which fails.
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);


        restStylesheetCacheMockMvc.perform(post("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isBadRequest());

        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = stylesheetCacheRepository.findAll().size();
        // set the field null
        stylesheetCache.setContent(null);

        // Create the StylesheetCache, which fails.
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);


        restStylesheetCacheMockMvc.perform(post("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isBadRequest());

        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThemeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stylesheetCacheRepository.findAll().size();
        // set the field null
        stylesheetCache.setThemeId(null);

        // Create the StylesheetCache, which fails.
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);


        restStylesheetCacheMockMvc.perform(post("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isBadRequest());

        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStylesheetCaches() throws Exception {
        // Initialize the database
        stylesheetCacheRepository.saveAndFlush(stylesheetCache);

        // Get all the stylesheetCacheList
        restStylesheetCacheMockMvc.perform(get("/api/stylesheet-caches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stylesheetCache.getId().intValue())))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET)))
            .andExpect(jsonPath("$.[*].digest").value(hasItem(DEFAULT_DIGEST)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].sourceMap").value(hasItem(DEFAULT_SOURCE_MAP)));
    }

    @Test
    @Transactional
    public void getStylesheetCache() throws Exception {
        // Initialize the database
        stylesheetCacheRepository.saveAndFlush(stylesheetCache);

        // Get the stylesheetCache
        restStylesheetCacheMockMvc.perform(get("/api/stylesheet-caches/{id}", stylesheetCache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stylesheetCache.getId().intValue()))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET))
            .andExpect(jsonPath("$.digest").value(DEFAULT_DIGEST))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()))
            .andExpect(jsonPath("$.sourceMap").value(DEFAULT_SOURCE_MAP));
    }
    @Test
    @Transactional
    public void getNonExistingStylesheetCache() throws Exception {
        // Get the stylesheetCache
        restStylesheetCacheMockMvc.perform(get("/api/stylesheet-caches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStylesheetCache() throws Exception {
        // Initialize the database
        stylesheetCacheRepository.saveAndFlush(stylesheetCache);

        int databaseSizeBeforeUpdate = stylesheetCacheRepository.findAll().size();

        // Update the stylesheetCache
        StylesheetCache updatedStylesheetCache = stylesheetCacheRepository.findById(stylesheetCache.getId()).get();
        // Disconnect from session so that the updates on updatedStylesheetCache are not directly saved in db
        em.detach(updatedStylesheetCache);
        updatedStylesheetCache
            .target(UPDATED_TARGET)
            .digest(UPDATED_DIGEST)
            .content(UPDATED_CONTENT)
            .themeId(UPDATED_THEME_ID)
            .sourceMap(UPDATED_SOURCE_MAP);
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(updatedStylesheetCache);

        restStylesheetCacheMockMvc.perform(put("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isOk());

        // Validate the StylesheetCache in the database
        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeUpdate);
        StylesheetCache testStylesheetCache = stylesheetCacheList.get(stylesheetCacheList.size() - 1);
        assertThat(testStylesheetCache.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testStylesheetCache.getDigest()).isEqualTo(UPDATED_DIGEST);
        assertThat(testStylesheetCache.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testStylesheetCache.getThemeId()).isEqualTo(UPDATED_THEME_ID);
        assertThat(testStylesheetCache.getSourceMap()).isEqualTo(UPDATED_SOURCE_MAP);
    }

    @Test
    @Transactional
    public void updateNonExistingStylesheetCache() throws Exception {
        int databaseSizeBeforeUpdate = stylesheetCacheRepository.findAll().size();

        // Create the StylesheetCache
        StylesheetCacheDTO stylesheetCacheDTO = stylesheetCacheMapper.toDto(stylesheetCache);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStylesheetCacheMockMvc.perform(put("/api/stylesheet-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stylesheetCacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StylesheetCache in the database
        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStylesheetCache() throws Exception {
        // Initialize the database
        stylesheetCacheRepository.saveAndFlush(stylesheetCache);

        int databaseSizeBeforeDelete = stylesheetCacheRepository.findAll().size();

        // Delete the stylesheetCache
        restStylesheetCacheMockMvc.perform(delete("/api/stylesheet-caches/{id}", stylesheetCache.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StylesheetCache> stylesheetCacheList = stylesheetCacheRepository.findAll();
        assertThat(stylesheetCacheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
