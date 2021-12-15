/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TranslationOverrides;
import com.infy.repository.TranslationOverridesRepository;
import com.infy.service.TranslationOverridesService;
import com.infy.service.dto.TranslationOverridesDTO;
import com.infy.service.mapper.TranslationOverridesMapper;

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
 * Integration tests for the {@link TranslationOverridesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TranslationOverridesResourceIT {

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPILED_JS = "AAAAAAAAAA";
    private static final String UPDATED_COMPILED_JS = "BBBBBBBBBB";

    @Autowired
    private TranslationOverridesRepository translationOverridesRepository;

    @Autowired
    private TranslationOverridesMapper translationOverridesMapper;

    @Autowired
    private TranslationOverridesService translationOverridesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTranslationOverridesMockMvc;

    private TranslationOverrides translationOverrides;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranslationOverrides createEntity(EntityManager em) {
        TranslationOverrides translationOverrides = new TranslationOverrides()
            .locale(DEFAULT_LOCALE)
            .translationKey(DEFAULT_TRANSLATION_KEY)
            .value(DEFAULT_VALUE)
            .compiledJs(DEFAULT_COMPILED_JS);
        return translationOverrides;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranslationOverrides createUpdatedEntity(EntityManager em) {
        TranslationOverrides translationOverrides = new TranslationOverrides()
            .locale(UPDATED_LOCALE)
            .translationKey(UPDATED_TRANSLATION_KEY)
            .value(UPDATED_VALUE)
            .compiledJs(UPDATED_COMPILED_JS);
        return translationOverrides;
    }

    @BeforeEach
    public void initTest() {
        translationOverrides = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslationOverrides() throws Exception {
        int databaseSizeBeforeCreate = translationOverridesRepository.findAll().size();
        // Create the TranslationOverrides
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(translationOverrides);
        restTranslationOverridesMockMvc.perform(post("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isCreated());

        // Validate the TranslationOverrides in the database
        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeCreate + 1);
        TranslationOverrides testTranslationOverrides = translationOverridesList.get(translationOverridesList.size() - 1);
        assertThat(testTranslationOverrides.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testTranslationOverrides.getTranslationKey()).isEqualTo(DEFAULT_TRANSLATION_KEY);
        assertThat(testTranslationOverrides.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTranslationOverrides.getCompiledJs()).isEqualTo(DEFAULT_COMPILED_JS);
    }

    @Test
    @Transactional
    public void createTranslationOverridesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translationOverridesRepository.findAll().size();

        // Create the TranslationOverrides with an existing ID
        translationOverrides.setId(1L);
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(translationOverrides);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslationOverridesMockMvc.perform(post("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TranslationOverrides in the database
        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLocaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = translationOverridesRepository.findAll().size();
        // set the field null
        translationOverrides.setLocale(null);

        // Create the TranslationOverrides, which fails.
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(translationOverrides);


        restTranslationOverridesMockMvc.perform(post("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTranslationKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = translationOverridesRepository.findAll().size();
        // set the field null
        translationOverrides.setTranslationKey(null);

        // Create the TranslationOverrides, which fails.
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(translationOverrides);


        restTranslationOverridesMockMvc.perform(post("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = translationOverridesRepository.findAll().size();
        // set the field null
        translationOverrides.setValue(null);

        // Create the TranslationOverrides, which fails.
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(translationOverrides);


        restTranslationOverridesMockMvc.perform(post("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isBadRequest());

        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTranslationOverrides() throws Exception {
        // Initialize the database
        translationOverridesRepository.saveAndFlush(translationOverrides);

        // Get all the translationOverridesList
        restTranslationOverridesMockMvc.perform(get("/api/translation-overrides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translationOverrides.getId().intValue())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].translationKey").value(hasItem(DEFAULT_TRANSLATION_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].compiledJs").value(hasItem(DEFAULT_COMPILED_JS)));
    }

    @Test
    @Transactional
    public void getTranslationOverrides() throws Exception {
        // Initialize the database
        translationOverridesRepository.saveAndFlush(translationOverrides);

        // Get the translationOverrides
        restTranslationOverridesMockMvc.perform(get("/api/translation-overrides/{id}", translationOverrides.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(translationOverrides.getId().intValue()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.translationKey").value(DEFAULT_TRANSLATION_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.compiledJs").value(DEFAULT_COMPILED_JS));
    }
    @Test
    @Transactional
    public void getNonExistingTranslationOverrides() throws Exception {
        // Get the translationOverrides
        restTranslationOverridesMockMvc.perform(get("/api/translation-overrides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslationOverrides() throws Exception {
        // Initialize the database
        translationOverridesRepository.saveAndFlush(translationOverrides);

        int databaseSizeBeforeUpdate = translationOverridesRepository.findAll().size();

        // Update the translationOverrides
        TranslationOverrides updatedTranslationOverrides = translationOverridesRepository.findById(translationOverrides.getId()).get();
        // Disconnect from session so that the updates on updatedTranslationOverrides are not directly saved in db
        em.detach(updatedTranslationOverrides);
        updatedTranslationOverrides
            .locale(UPDATED_LOCALE)
            .translationKey(UPDATED_TRANSLATION_KEY)
            .value(UPDATED_VALUE)
            .compiledJs(UPDATED_COMPILED_JS);
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(updatedTranslationOverrides);

        restTranslationOverridesMockMvc.perform(put("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isOk());

        // Validate the TranslationOverrides in the database
        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeUpdate);
        TranslationOverrides testTranslationOverrides = translationOverridesList.get(translationOverridesList.size() - 1);
        assertThat(testTranslationOverrides.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testTranslationOverrides.getTranslationKey()).isEqualTo(UPDATED_TRANSLATION_KEY);
        assertThat(testTranslationOverrides.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTranslationOverrides.getCompiledJs()).isEqualTo(UPDATED_COMPILED_JS);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslationOverrides() throws Exception {
        int databaseSizeBeforeUpdate = translationOverridesRepository.findAll().size();

        // Create the TranslationOverrides
        TranslationOverridesDTO translationOverridesDTO = translationOverridesMapper.toDto(translationOverrides);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranslationOverridesMockMvc.perform(put("/api/translation-overrides").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(translationOverridesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TranslationOverrides in the database
        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranslationOverrides() throws Exception {
        // Initialize the database
        translationOverridesRepository.saveAndFlush(translationOverrides);

        int databaseSizeBeforeDelete = translationOverridesRepository.findAll().size();

        // Delete the translationOverrides
        restTranslationOverridesMockMvc.perform(delete("/api/translation-overrides/{id}", translationOverrides.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TranslationOverrides> translationOverridesList = translationOverridesRepository.findAll();
        assertThat(translationOverridesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
