/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryCustomFields;
import com.infy.repository.CategoryCustomFieldsRepository;
import com.infy.service.CategoryCustomFieldsService;
import com.infy.service.dto.CategoryCustomFieldsDTO;
import com.infy.service.mapper.CategoryCustomFieldsMapper;

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
 * Integration tests for the {@link CategoryCustomFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryCustomFieldsResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CategoryCustomFieldsRepository categoryCustomFieldsRepository;

    @Autowired
    private CategoryCustomFieldsMapper categoryCustomFieldsMapper;

    @Autowired
    private CategoryCustomFieldsService categoryCustomFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryCustomFieldsMockMvc;

    private CategoryCustomFields categoryCustomFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryCustomFields createEntity(EntityManager em) {
        CategoryCustomFields categoryCustomFields = new CategoryCustomFields()
            .categoryId(DEFAULT_CATEGORY_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return categoryCustomFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryCustomFields createUpdatedEntity(EntityManager em) {
        CategoryCustomFields categoryCustomFields = new CategoryCustomFields()
            .categoryId(UPDATED_CATEGORY_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return categoryCustomFields;
    }

    @BeforeEach
    public void initTest() {
        categoryCustomFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryCustomFields() throws Exception {
        int databaseSizeBeforeCreate = categoryCustomFieldsRepository.findAll().size();
        // Create the CategoryCustomFields
        CategoryCustomFieldsDTO categoryCustomFieldsDTO = categoryCustomFieldsMapper.toDto(categoryCustomFields);
        restCategoryCustomFieldsMockMvc.perform(post("/api/category-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryCustomFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryCustomFields in the database
        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryCustomFields testCategoryCustomFields = categoryCustomFieldsList.get(categoryCustomFieldsList.size() - 1);
        assertThat(testCategoryCustomFields.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryCustomFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategoryCustomFields.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCategoryCustomFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryCustomFieldsRepository.findAll().size();

        // Create the CategoryCustomFields with an existing ID
        categoryCustomFields.setId(1L);
        CategoryCustomFieldsDTO categoryCustomFieldsDTO = categoryCustomFieldsMapper.toDto(categoryCustomFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryCustomFieldsMockMvc.perform(post("/api/category-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryCustomFields in the database
        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryCustomFieldsRepository.findAll().size();
        // set the field null
        categoryCustomFields.setCategoryId(null);

        // Create the CategoryCustomFields, which fails.
        CategoryCustomFieldsDTO categoryCustomFieldsDTO = categoryCustomFieldsMapper.toDto(categoryCustomFields);


        restCategoryCustomFieldsMockMvc.perform(post("/api/category-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryCustomFieldsRepository.findAll().size();
        // set the field null
        categoryCustomFields.setName(null);

        // Create the CategoryCustomFields, which fails.
        CategoryCustomFieldsDTO categoryCustomFieldsDTO = categoryCustomFieldsMapper.toDto(categoryCustomFields);


        restCategoryCustomFieldsMockMvc.perform(post("/api/category-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryCustomFields() throws Exception {
        // Initialize the database
        categoryCustomFieldsRepository.saveAndFlush(categoryCustomFields);

        // Get all the categoryCustomFieldsList
        restCategoryCustomFieldsMockMvc.perform(get("/api/category-custom-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryCustomFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getCategoryCustomFields() throws Exception {
        // Initialize the database
        categoryCustomFieldsRepository.saveAndFlush(categoryCustomFields);

        // Get the categoryCustomFields
        restCategoryCustomFieldsMockMvc.perform(get("/api/category-custom-fields/{id}", categoryCustomFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryCustomFields.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryCustomFields() throws Exception {
        // Get the categoryCustomFields
        restCategoryCustomFieldsMockMvc.perform(get("/api/category-custom-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryCustomFields() throws Exception {
        // Initialize the database
        categoryCustomFieldsRepository.saveAndFlush(categoryCustomFields);

        int databaseSizeBeforeUpdate = categoryCustomFieldsRepository.findAll().size();

        // Update the categoryCustomFields
        CategoryCustomFields updatedCategoryCustomFields = categoryCustomFieldsRepository.findById(categoryCustomFields.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryCustomFields are not directly saved in db
        em.detach(updatedCategoryCustomFields);
        updatedCategoryCustomFields
            .categoryId(UPDATED_CATEGORY_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        CategoryCustomFieldsDTO categoryCustomFieldsDTO = categoryCustomFieldsMapper.toDto(updatedCategoryCustomFields);

        restCategoryCustomFieldsMockMvc.perform(put("/api/category-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryCustomFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryCustomFields in the database
        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
        CategoryCustomFields testCategoryCustomFields = categoryCustomFieldsList.get(categoryCustomFieldsList.size() - 1);
        assertThat(testCategoryCustomFields.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryCustomFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategoryCustomFields.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryCustomFields() throws Exception {
        int databaseSizeBeforeUpdate = categoryCustomFieldsRepository.findAll().size();

        // Create the CategoryCustomFields
        CategoryCustomFieldsDTO categoryCustomFieldsDTO = categoryCustomFieldsMapper.toDto(categoryCustomFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryCustomFieldsMockMvc.perform(put("/api/category-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryCustomFields in the database
        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryCustomFields() throws Exception {
        // Initialize the database
        categoryCustomFieldsRepository.saveAndFlush(categoryCustomFields);

        int databaseSizeBeforeDelete = categoryCustomFieldsRepository.findAll().size();

        // Delete the categoryCustomFields
        restCategoryCustomFieldsMockMvc.perform(delete("/api/category-custom-fields/{id}", categoryCustomFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryCustomFields> categoryCustomFieldsList = categoryCustomFieldsRepository.findAll();
        assertThat(categoryCustomFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
