/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryTagGroups;
import com.infy.repository.CategoryTagGroupsRepository;
import com.infy.service.CategoryTagGroupsService;
import com.infy.service.dto.CategoryTagGroupsDTO;
import com.infy.service.mapper.CategoryTagGroupsMapper;

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
 * Integration tests for the {@link CategoryTagGroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryTagGroupsResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_TAG_GROUP_ID = 1L;
    private static final Long UPDATED_TAG_GROUP_ID = 2L;

    @Autowired
    private CategoryTagGroupsRepository categoryTagGroupsRepository;

    @Autowired
    private CategoryTagGroupsMapper categoryTagGroupsMapper;

    @Autowired
    private CategoryTagGroupsService categoryTagGroupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryTagGroupsMockMvc;

    private CategoryTagGroups categoryTagGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryTagGroups createEntity(EntityManager em) {
        CategoryTagGroups categoryTagGroups = new CategoryTagGroups()
            .categoryId(DEFAULT_CATEGORY_ID)
            .tagGroupId(DEFAULT_TAG_GROUP_ID);
        return categoryTagGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryTagGroups createUpdatedEntity(EntityManager em) {
        CategoryTagGroups categoryTagGroups = new CategoryTagGroups()
            .categoryId(UPDATED_CATEGORY_ID)
            .tagGroupId(UPDATED_TAG_GROUP_ID);
        return categoryTagGroups;
    }

    @BeforeEach
    public void initTest() {
        categoryTagGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryTagGroups() throws Exception {
        int databaseSizeBeforeCreate = categoryTagGroupsRepository.findAll().size();
        // Create the CategoryTagGroups
        CategoryTagGroupsDTO categoryTagGroupsDTO = categoryTagGroupsMapper.toDto(categoryTagGroups);
        restCategoryTagGroupsMockMvc.perform(post("/api/category-tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryTagGroups in the database
        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryTagGroups testCategoryTagGroups = categoryTagGroupsList.get(categoryTagGroupsList.size() - 1);
        assertThat(testCategoryTagGroups.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryTagGroups.getTagGroupId()).isEqualTo(DEFAULT_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void createCategoryTagGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryTagGroupsRepository.findAll().size();

        // Create the CategoryTagGroups with an existing ID
        categoryTagGroups.setId(1L);
        CategoryTagGroupsDTO categoryTagGroupsDTO = categoryTagGroupsMapper.toDto(categoryTagGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryTagGroupsMockMvc.perform(post("/api/category-tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryTagGroups in the database
        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagGroupsRepository.findAll().size();
        // set the field null
        categoryTagGroups.setCategoryId(null);

        // Create the CategoryTagGroups, which fails.
        CategoryTagGroupsDTO categoryTagGroupsDTO = categoryTagGroupsMapper.toDto(categoryTagGroups);


        restCategoryTagGroupsMockMvc.perform(post("/api/category-tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagGroupsRepository.findAll().size();
        // set the field null
        categoryTagGroups.setTagGroupId(null);

        // Create the CategoryTagGroups, which fails.
        CategoryTagGroupsDTO categoryTagGroupsDTO = categoryTagGroupsMapper.toDto(categoryTagGroups);


        restCategoryTagGroupsMockMvc.perform(post("/api/category-tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryTagGroups() throws Exception {
        // Initialize the database
        categoryTagGroupsRepository.saveAndFlush(categoryTagGroups);

        // Get all the categoryTagGroupsList
        restCategoryTagGroupsMockMvc.perform(get("/api/category-tag-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryTagGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagGroupId").value(hasItem(DEFAULT_TAG_GROUP_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCategoryTagGroups() throws Exception {
        // Initialize the database
        categoryTagGroupsRepository.saveAndFlush(categoryTagGroups);

        // Get the categoryTagGroups
        restCategoryTagGroupsMockMvc.perform(get("/api/category-tag-groups/{id}", categoryTagGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryTagGroups.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.tagGroupId").value(DEFAULT_TAG_GROUP_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryTagGroups() throws Exception {
        // Get the categoryTagGroups
        restCategoryTagGroupsMockMvc.perform(get("/api/category-tag-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryTagGroups() throws Exception {
        // Initialize the database
        categoryTagGroupsRepository.saveAndFlush(categoryTagGroups);

        int databaseSizeBeforeUpdate = categoryTagGroupsRepository.findAll().size();

        // Update the categoryTagGroups
        CategoryTagGroups updatedCategoryTagGroups = categoryTagGroupsRepository.findById(categoryTagGroups.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryTagGroups are not directly saved in db
        em.detach(updatedCategoryTagGroups);
        updatedCategoryTagGroups
            .categoryId(UPDATED_CATEGORY_ID)
            .tagGroupId(UPDATED_TAG_GROUP_ID);
        CategoryTagGroupsDTO categoryTagGroupsDTO = categoryTagGroupsMapper.toDto(updatedCategoryTagGroups);

        restCategoryTagGroupsMockMvc.perform(put("/api/category-tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryTagGroups in the database
        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeUpdate);
        CategoryTagGroups testCategoryTagGroups = categoryTagGroupsList.get(categoryTagGroupsList.size() - 1);
        assertThat(testCategoryTagGroups.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryTagGroups.getTagGroupId()).isEqualTo(UPDATED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryTagGroups() throws Exception {
        int databaseSizeBeforeUpdate = categoryTagGroupsRepository.findAll().size();

        // Create the CategoryTagGroups
        CategoryTagGroupsDTO categoryTagGroupsDTO = categoryTagGroupsMapper.toDto(categoryTagGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTagGroupsMockMvc.perform(put("/api/category-tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryTagGroups in the database
        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryTagGroups() throws Exception {
        // Initialize the database
        categoryTagGroupsRepository.saveAndFlush(categoryTagGroups);

        int databaseSizeBeforeDelete = categoryTagGroupsRepository.findAll().size();

        // Delete the categoryTagGroups
        restCategoryTagGroupsMockMvc.perform(delete("/api/category-tag-groups/{id}", categoryTagGroups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryTagGroups> categoryTagGroupsList = categoryTagGroupsRepository.findAll();
        assertThat(categoryTagGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
