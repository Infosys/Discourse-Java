/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.JavascriptCaches;
import com.infy.repository.JavascriptCachesRepository;
import com.infy.service.JavascriptCachesService;
import com.infy.service.dto.JavascriptCachesDTO;
import com.infy.service.mapper.JavascriptCachesMapper;

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
 * Integration tests for the {@link JavascriptCachesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class JavascriptCachesResourceIT {

    private static final Long DEFAULT_THEME_FIELD_ID = 1L;
    private static final Long UPDATED_THEME_FIELD_ID = 2L;

    private static final String DEFAULT_DIGEST = "AAAAAAAAAA";
    private static final String UPDATED_DIGEST = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    @Autowired
    private JavascriptCachesRepository javascriptCachesRepository;

    @Autowired
    private JavascriptCachesMapper javascriptCachesMapper;

    @Autowired
    private JavascriptCachesService javascriptCachesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJavascriptCachesMockMvc;

    private JavascriptCaches javascriptCaches;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JavascriptCaches createEntity(EntityManager em) {
        JavascriptCaches javascriptCaches = new JavascriptCaches()
            .themeFieldId(DEFAULT_THEME_FIELD_ID)
            .digest(DEFAULT_DIGEST)
            .content(DEFAULT_CONTENT)
            .themeId(DEFAULT_THEME_ID);
        return javascriptCaches;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JavascriptCaches createUpdatedEntity(EntityManager em) {
        JavascriptCaches javascriptCaches = new JavascriptCaches()
            .themeFieldId(UPDATED_THEME_FIELD_ID)
            .digest(UPDATED_DIGEST)
            .content(UPDATED_CONTENT)
            .themeId(UPDATED_THEME_ID);
        return javascriptCaches;
    }

    @BeforeEach
    public void initTest() {
        javascriptCaches = createEntity(em);
    }

    @Test
    @Transactional
    public void createJavascriptCaches() throws Exception {
        int databaseSizeBeforeCreate = javascriptCachesRepository.findAll().size();
        // Create the JavascriptCaches
        JavascriptCachesDTO javascriptCachesDTO = javascriptCachesMapper.toDto(javascriptCaches);
        restJavascriptCachesMockMvc.perform(post("/api/javascript-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(javascriptCachesDTO)))
            .andExpect(status().isCreated());

        // Validate the JavascriptCaches in the database
        List<JavascriptCaches> javascriptCachesList = javascriptCachesRepository.findAll();
        assertThat(javascriptCachesList).hasSize(databaseSizeBeforeCreate + 1);
        JavascriptCaches testJavascriptCaches = javascriptCachesList.get(javascriptCachesList.size() - 1);
        assertThat(testJavascriptCaches.getThemeFieldId()).isEqualTo(DEFAULT_THEME_FIELD_ID);
        assertThat(testJavascriptCaches.getDigest()).isEqualTo(DEFAULT_DIGEST);
        assertThat(testJavascriptCaches.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testJavascriptCaches.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
    }

    @Test
    @Transactional
    public void createJavascriptCachesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = javascriptCachesRepository.findAll().size();

        // Create the JavascriptCaches with an existing ID
        javascriptCaches.setId(1L);
        JavascriptCachesDTO javascriptCachesDTO = javascriptCachesMapper.toDto(javascriptCaches);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJavascriptCachesMockMvc.perform(post("/api/javascript-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(javascriptCachesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JavascriptCaches in the database
        List<JavascriptCaches> javascriptCachesList = javascriptCachesRepository.findAll();
        assertThat(javascriptCachesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = javascriptCachesRepository.findAll().size();
        // set the field null
        javascriptCaches.setContent(null);

        // Create the JavascriptCaches, which fails.
        JavascriptCachesDTO javascriptCachesDTO = javascriptCachesMapper.toDto(javascriptCaches);


        restJavascriptCachesMockMvc.perform(post("/api/javascript-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(javascriptCachesDTO)))
            .andExpect(status().isBadRequest());

        List<JavascriptCaches> javascriptCachesList = javascriptCachesRepository.findAll();
        assertThat(javascriptCachesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJavascriptCaches() throws Exception {
        // Initialize the database
        javascriptCachesRepository.saveAndFlush(javascriptCaches);

        // Get all the javascriptCachesList
        restJavascriptCachesMockMvc.perform(get("/api/javascript-caches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(javascriptCaches.getId().intValue())))
            .andExpect(jsonPath("$.[*].themeFieldId").value(hasItem(DEFAULT_THEME_FIELD_ID.intValue())))
            .andExpect(jsonPath("$.[*].digest").value(hasItem(DEFAULT_DIGEST)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())));
    }

    @Test
    @Transactional
    public void getJavascriptCaches() throws Exception {
        // Initialize the database
        javascriptCachesRepository.saveAndFlush(javascriptCaches);

        // Get the javascriptCaches
        restJavascriptCachesMockMvc.perform(get("/api/javascript-caches/{id}", javascriptCaches.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(javascriptCaches.getId().intValue()))
            .andExpect(jsonPath("$.themeFieldId").value(DEFAULT_THEME_FIELD_ID.intValue()))
            .andExpect(jsonPath("$.digest").value(DEFAULT_DIGEST))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingJavascriptCaches() throws Exception {
        // Get the javascriptCaches
        restJavascriptCachesMockMvc.perform(get("/api/javascript-caches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJavascriptCaches() throws Exception {
        // Initialize the database
        javascriptCachesRepository.saveAndFlush(javascriptCaches);

        int databaseSizeBeforeUpdate = javascriptCachesRepository.findAll().size();

        // Update the javascriptCaches
        JavascriptCaches updatedJavascriptCaches = javascriptCachesRepository.findById(javascriptCaches.getId()).get();
        // Disconnect from session so that the updates on updatedJavascriptCaches are not directly saved in db
        em.detach(updatedJavascriptCaches);
        updatedJavascriptCaches
            .themeFieldId(UPDATED_THEME_FIELD_ID)
            .digest(UPDATED_DIGEST)
            .content(UPDATED_CONTENT)
            .themeId(UPDATED_THEME_ID);
        JavascriptCachesDTO javascriptCachesDTO = javascriptCachesMapper.toDto(updatedJavascriptCaches);

        restJavascriptCachesMockMvc.perform(put("/api/javascript-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(javascriptCachesDTO)))
            .andExpect(status().isOk());

        // Validate the JavascriptCaches in the database
        List<JavascriptCaches> javascriptCachesList = javascriptCachesRepository.findAll();
        assertThat(javascriptCachesList).hasSize(databaseSizeBeforeUpdate);
        JavascriptCaches testJavascriptCaches = javascriptCachesList.get(javascriptCachesList.size() - 1);
        assertThat(testJavascriptCaches.getThemeFieldId()).isEqualTo(UPDATED_THEME_FIELD_ID);
        assertThat(testJavascriptCaches.getDigest()).isEqualTo(UPDATED_DIGEST);
        assertThat(testJavascriptCaches.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testJavascriptCaches.getThemeId()).isEqualTo(UPDATED_THEME_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingJavascriptCaches() throws Exception {
        int databaseSizeBeforeUpdate = javascriptCachesRepository.findAll().size();

        // Create the JavascriptCaches
        JavascriptCachesDTO javascriptCachesDTO = javascriptCachesMapper.toDto(javascriptCaches);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJavascriptCachesMockMvc.perform(put("/api/javascript-caches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(javascriptCachesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JavascriptCaches in the database
        List<JavascriptCaches> javascriptCachesList = javascriptCachesRepository.findAll();
        assertThat(javascriptCachesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJavascriptCaches() throws Exception {
        // Initialize the database
        javascriptCachesRepository.saveAndFlush(javascriptCaches);

        int databaseSizeBeforeDelete = javascriptCachesRepository.findAll().size();

        // Delete the javascriptCaches
        restJavascriptCachesMockMvc.perform(delete("/api/javascript-caches/{id}", javascriptCaches.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JavascriptCaches> javascriptCachesList = javascriptCachesRepository.findAll();
        assertThat(javascriptCachesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
