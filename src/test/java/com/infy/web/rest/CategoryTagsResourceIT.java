/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryTags;
import com.infy.repository.CategoryTagsRepository;
import com.infy.service.CategoryTagsService;
import com.infy.service.dto.CategoryTagsDTO;
import com.infy.service.mapper.CategoryTagsMapper;

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
 * Integration tests for the {@link CategoryTagsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryTagsResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    @Autowired
    private CategoryTagsRepository categoryTagsRepository;

    @Autowired
    private CategoryTagsMapper categoryTagsMapper;

    @Autowired
    private CategoryTagsService categoryTagsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryTagsMockMvc;

    private CategoryTags categoryTags;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryTags createEntity(EntityManager em) {
        CategoryTags categoryTags = new CategoryTags()
            .categoryId(DEFAULT_CATEGORY_ID)
            .tagId(DEFAULT_TAG_ID);
        return categoryTags;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryTags createUpdatedEntity(EntityManager em) {
        CategoryTags categoryTags = new CategoryTags()
            .categoryId(UPDATED_CATEGORY_ID)
            .tagId(UPDATED_TAG_ID);
        return categoryTags;
    }

    @BeforeEach
    public void initTest() {
        categoryTags = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryTags() throws Exception {
        int databaseSizeBeforeCreate = categoryTagsRepository.findAll().size();
        // Create the CategoryTags
        CategoryTagsDTO categoryTagsDTO = categoryTagsMapper.toDto(categoryTags);
        restCategoryTagsMockMvc.perform(post("/api/category-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagsDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryTags in the database
        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryTags testCategoryTags = categoryTagsList.get(categoryTagsList.size() - 1);
        assertThat(testCategoryTags.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryTags.getTagId()).isEqualTo(DEFAULT_TAG_ID);
    }

    @Test
    @Transactional
    public void createCategoryTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryTagsRepository.findAll().size();

        // Create the CategoryTags with an existing ID
        categoryTags.setId(1L);
        CategoryTagsDTO categoryTagsDTO = categoryTagsMapper.toDto(categoryTags);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryTagsMockMvc.perform(post("/api/category-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryTags in the database
        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagsRepository.findAll().size();
        // set the field null
        categoryTags.setCategoryId(null);

        // Create the CategoryTags, which fails.
        CategoryTagsDTO categoryTagsDTO = categoryTagsMapper.toDto(categoryTags);


        restCategoryTagsMockMvc.perform(post("/api/category-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagsRepository.findAll().size();
        // set the field null
        categoryTags.setTagId(null);

        // Create the CategoryTags, which fails.
        CategoryTagsDTO categoryTagsDTO = categoryTagsMapper.toDto(categoryTags);


        restCategoryTagsMockMvc.perform(post("/api/category-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryTags() throws Exception {
        // Initialize the database
        categoryTagsRepository.saveAndFlush(categoryTags);

        // Get all the categoryTagsList
        restCategoryTagsMockMvc.perform(get("/api/category-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCategoryTags() throws Exception {
        // Initialize the database
        categoryTagsRepository.saveAndFlush(categoryTags);

        // Get the categoryTags
        restCategoryTagsMockMvc.perform(get("/api/category-tags/{id}", categoryTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryTags.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryTags() throws Exception {
        // Get the categoryTags
        restCategoryTagsMockMvc.perform(get("/api/category-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryTags() throws Exception {
        // Initialize the database
        categoryTagsRepository.saveAndFlush(categoryTags);

        int databaseSizeBeforeUpdate = categoryTagsRepository.findAll().size();

        // Update the categoryTags
        CategoryTags updatedCategoryTags = categoryTagsRepository.findById(categoryTags.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryTags are not directly saved in db
        em.detach(updatedCategoryTags);
        updatedCategoryTags
            .categoryId(UPDATED_CATEGORY_ID)
            .tagId(UPDATED_TAG_ID);
        CategoryTagsDTO categoryTagsDTO = categoryTagsMapper.toDto(updatedCategoryTags);

        restCategoryTagsMockMvc.perform(put("/api/category-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagsDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryTags in the database
        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeUpdate);
        CategoryTags testCategoryTags = categoryTagsList.get(categoryTagsList.size() - 1);
        assertThat(testCategoryTags.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryTags.getTagId()).isEqualTo(UPDATED_TAG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryTags() throws Exception {
        int databaseSizeBeforeUpdate = categoryTagsRepository.findAll().size();

        // Create the CategoryTags
        CategoryTagsDTO categoryTagsDTO = categoryTagsMapper.toDto(categoryTags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTagsMockMvc.perform(put("/api/category-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryTags in the database
        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryTags() throws Exception {
        // Initialize the database
        categoryTagsRepository.saveAndFlush(categoryTags);

        int databaseSizeBeforeDelete = categoryTagsRepository.findAll().size();

        // Delete the categoryTags
        restCategoryTagsMockMvc.perform(delete("/api/category-tags/{id}", categoryTags.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryTags> categoryTagsList = categoryTagsRepository.findAll();
        assertThat(categoryTagsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
