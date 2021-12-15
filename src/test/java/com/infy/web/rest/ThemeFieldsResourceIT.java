/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ThemeFields;
import com.infy.repository.ThemeFieldsRepository;
import com.infy.service.ThemeFieldsService;
import com.infy.service.dto.ThemeFieldsDTO;
import com.infy.service.mapper.ThemeFieldsMapper;

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
 * Integration tests for the {@link ThemeFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ThemeFieldsResourceIT {

    private static final Long DEFAULT_THEME_ID = 1L;
    private static final Long UPDATED_THEME_ID = 2L;

    private static final Long DEFAULT_TARGET_ID = 1L;
    private static final Long UPDATED_TARGET_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_BAKED = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_BAKED = "BBBBBBBBBB";

    private static final String DEFAULT_COMPILER_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_COMPILER_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_ERROR = "BBBBBBBBBB";

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    private static final Long DEFAULT_TYPE_ID = 1L;
    private static final Long UPDATED_TYPE_ID = 2L;

    @Autowired
    private ThemeFieldsRepository themeFieldsRepository;

    @Autowired
    private ThemeFieldsMapper themeFieldsMapper;

    @Autowired
    private ThemeFieldsService themeFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeFieldsMockMvc;

    private ThemeFields themeFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeFields createEntity(EntityManager em) {
        ThemeFields themeFields = new ThemeFields()
            .themeId(DEFAULT_THEME_ID)
            .targetId(DEFAULT_TARGET_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueBaked(DEFAULT_VALUE_BAKED)
            .compilerVersion(DEFAULT_COMPILER_VERSION)
            .error(DEFAULT_ERROR)
            .uploadId(DEFAULT_UPLOAD_ID)
            .typeId(DEFAULT_TYPE_ID);
        return themeFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeFields createUpdatedEntity(EntityManager em) {
        ThemeFields themeFields = new ThemeFields()
            .themeId(UPDATED_THEME_ID)
            .targetId(UPDATED_TARGET_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueBaked(UPDATED_VALUE_BAKED)
            .compilerVersion(UPDATED_COMPILER_VERSION)
            .error(UPDATED_ERROR)
            .uploadId(UPDATED_UPLOAD_ID)
            .typeId(UPDATED_TYPE_ID);
        return themeFields;
    }

    @BeforeEach
    public void initTest() {
        themeFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemeFields() throws Exception {
        int databaseSizeBeforeCreate = themeFieldsRepository.findAll().size();
        // Create the ThemeFields
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);
        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the ThemeFields in the database
        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        ThemeFields testThemeFields = themeFieldsList.get(themeFieldsList.size() - 1);
        assertThat(testThemeFields.getThemeId()).isEqualTo(DEFAULT_THEME_ID);
        assertThat(testThemeFields.getTargetId()).isEqualTo(DEFAULT_TARGET_ID);
        assertThat(testThemeFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThemeFields.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testThemeFields.getValueBaked()).isEqualTo(DEFAULT_VALUE_BAKED);
        assertThat(testThemeFields.getCompilerVersion()).isEqualTo(DEFAULT_COMPILER_VERSION);
        assertThat(testThemeFields.getError()).isEqualTo(DEFAULT_ERROR);
        assertThat(testThemeFields.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
        assertThat(testThemeFields.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
    }

    @Test
    @Transactional
    public void createThemeFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeFieldsRepository.findAll().size();

        // Create the ThemeFields with an existing ID
        themeFields.setId(1L);
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeFields in the database
        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkThemeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeFieldsRepository.findAll().size();
        // set the field null
        themeFields.setThemeId(null);

        // Create the ThemeFields, which fails.
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);


        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeFieldsRepository.findAll().size();
        // set the field null
        themeFields.setTargetId(null);

        // Create the ThemeFields, which fails.
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);


        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeFieldsRepository.findAll().size();
        // set the field null
        themeFields.setName(null);

        // Create the ThemeFields, which fails.
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);


        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeFieldsRepository.findAll().size();
        // set the field null
        themeFields.setValue(null);

        // Create the ThemeFields, which fails.
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);


        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompilerVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeFieldsRepository.findAll().size();
        // set the field null
        themeFields.setCompilerVersion(null);

        // Create the ThemeFields, which fails.
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);


        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeFieldsRepository.findAll().size();
        // set the field null
        themeFields.setTypeId(null);

        // Create the ThemeFields, which fails.
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);


        restThemeFieldsMockMvc.perform(post("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemeFields() throws Exception {
        // Initialize the database
        themeFieldsRepository.saveAndFlush(themeFields);

        // Get all the themeFieldsList
        restThemeFieldsMockMvc.perform(get("/api/theme-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themeFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].themeId").value(hasItem(DEFAULT_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].targetId").value(hasItem(DEFAULT_TARGET_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueBaked").value(hasItem(DEFAULT_VALUE_BAKED)))
            .andExpect(jsonPath("$.[*].compilerVersion").value(hasItem(DEFAULT_COMPILER_VERSION)))
            .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR)))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].typeId").value(hasItem(DEFAULT_TYPE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getThemeFields() throws Exception {
        // Initialize the database
        themeFieldsRepository.saveAndFlush(themeFields);

        // Get the themeFields
        restThemeFieldsMockMvc.perform(get("/api/theme-fields/{id}", themeFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(themeFields.getId().intValue()))
            .andExpect(jsonPath("$.themeId").value(DEFAULT_THEME_ID.intValue()))
            .andExpect(jsonPath("$.targetId").value(DEFAULT_TARGET_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueBaked").value(DEFAULT_VALUE_BAKED))
            .andExpect(jsonPath("$.compilerVersion").value(DEFAULT_COMPILER_VERSION))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.typeId").value(DEFAULT_TYPE_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingThemeFields() throws Exception {
        // Get the themeFields
        restThemeFieldsMockMvc.perform(get("/api/theme-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemeFields() throws Exception {
        // Initialize the database
        themeFieldsRepository.saveAndFlush(themeFields);

        int databaseSizeBeforeUpdate = themeFieldsRepository.findAll().size();

        // Update the themeFields
        ThemeFields updatedThemeFields = themeFieldsRepository.findById(themeFields.getId()).get();
        // Disconnect from session so that the updates on updatedThemeFields are not directly saved in db
        em.detach(updatedThemeFields);
        updatedThemeFields
            .themeId(UPDATED_THEME_ID)
            .targetId(UPDATED_TARGET_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueBaked(UPDATED_VALUE_BAKED)
            .compilerVersion(UPDATED_COMPILER_VERSION)
            .error(UPDATED_ERROR)
            .uploadId(UPDATED_UPLOAD_ID)
            .typeId(UPDATED_TYPE_ID);
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(updatedThemeFields);

        restThemeFieldsMockMvc.perform(put("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the ThemeFields in the database
        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeUpdate);
        ThemeFields testThemeFields = themeFieldsList.get(themeFieldsList.size() - 1);
        assertThat(testThemeFields.getThemeId()).isEqualTo(UPDATED_THEME_ID);
        assertThat(testThemeFields.getTargetId()).isEqualTo(UPDATED_TARGET_ID);
        assertThat(testThemeFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThemeFields.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testThemeFields.getValueBaked()).isEqualTo(UPDATED_VALUE_BAKED);
        assertThat(testThemeFields.getCompilerVersion()).isEqualTo(UPDATED_COMPILER_VERSION);
        assertThat(testThemeFields.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testThemeFields.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
        assertThat(testThemeFields.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingThemeFields() throws Exception {
        int databaseSizeBeforeUpdate = themeFieldsRepository.findAll().size();

        // Create the ThemeFields
        ThemeFieldsDTO themeFieldsDTO = themeFieldsMapper.toDto(themeFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeFieldsMockMvc.perform(put("/api/theme-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeFields in the database
        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThemeFields() throws Exception {
        // Initialize the database
        themeFieldsRepository.saveAndFlush(themeFields);

        int databaseSizeBeforeDelete = themeFieldsRepository.findAll().size();

        // Delete the themeFields
        restThemeFieldsMockMvc.perform(delete("/api/theme-fields/{id}", themeFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThemeFields> themeFieldsList = themeFieldsRepository.findAll();
        assertThat(themeFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
