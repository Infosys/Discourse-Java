/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryGroups;
import com.infy.repository.CategoryGroupsRepository;
import com.infy.service.CategoryGroupsService;
import com.infy.service.dto.CategoryGroupsDTO;
import com.infy.service.mapper.CategoryGroupsMapper;

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
 * Integration tests for the {@link CategoryGroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryGroupsResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Integer DEFAULT_PERMISSION_TYPE = 1;
    private static final Integer UPDATED_PERMISSION_TYPE = 2;

    @Autowired
    private CategoryGroupsRepository categoryGroupsRepository;

    @Autowired
    private CategoryGroupsMapper categoryGroupsMapper;

    @Autowired
    private CategoryGroupsService categoryGroupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryGroupsMockMvc;

    private CategoryGroups categoryGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryGroups createEntity(EntityManager em) {
        CategoryGroups categoryGroups = new CategoryGroups()
            .categoryId(DEFAULT_CATEGORY_ID)
            .groupId(DEFAULT_GROUP_ID)
            .permissionType(DEFAULT_PERMISSION_TYPE);
        return categoryGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryGroups createUpdatedEntity(EntityManager em) {
        CategoryGroups categoryGroups = new CategoryGroups()
            .categoryId(UPDATED_CATEGORY_ID)
            .groupId(UPDATED_GROUP_ID)
            .permissionType(UPDATED_PERMISSION_TYPE);
        return categoryGroups;
    }

    @BeforeEach
    public void initTest() {
        categoryGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryGroups() throws Exception {
        int databaseSizeBeforeCreate = categoryGroupsRepository.findAll().size();
        // Create the CategoryGroups
        CategoryGroupsDTO categoryGroupsDTO = categoryGroupsMapper.toDto(categoryGroups);
        restCategoryGroupsMockMvc.perform(post("/api/category-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryGroups in the database
        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryGroups testCategoryGroups = categoryGroupsList.get(categoryGroupsList.size() - 1);
        assertThat(testCategoryGroups.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryGroups.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testCategoryGroups.getPermissionType()).isEqualTo(DEFAULT_PERMISSION_TYPE);
    }

    @Test
    @Transactional
    public void createCategoryGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryGroupsRepository.findAll().size();

        // Create the CategoryGroups with an existing ID
        categoryGroups.setId(1L);
        CategoryGroupsDTO categoryGroupsDTO = categoryGroupsMapper.toDto(categoryGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryGroupsMockMvc.perform(post("/api/category-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryGroups in the database
        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryGroupsRepository.findAll().size();
        // set the field null
        categoryGroups.setCategoryId(null);

        // Create the CategoryGroups, which fails.
        CategoryGroupsDTO categoryGroupsDTO = categoryGroupsMapper.toDto(categoryGroups);


        restCategoryGroupsMockMvc.perform(post("/api/category-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryGroupsRepository.findAll().size();
        // set the field null
        categoryGroups.setGroupId(null);

        // Create the CategoryGroups, which fails.
        CategoryGroupsDTO categoryGroupsDTO = categoryGroupsMapper.toDto(categoryGroups);


        restCategoryGroupsMockMvc.perform(post("/api/category-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryGroups() throws Exception {
        // Initialize the database
        categoryGroupsRepository.saveAndFlush(categoryGroups);

        // Get all the categoryGroupsList
        restCategoryGroupsMockMvc.perform(get("/api/category-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].permissionType").value(hasItem(DEFAULT_PERMISSION_TYPE)));
    }

    @Test
    @Transactional
    public void getCategoryGroups() throws Exception {
        // Initialize the database
        categoryGroupsRepository.saveAndFlush(categoryGroups);

        // Get the categoryGroups
        restCategoryGroupsMockMvc.perform(get("/api/category-groups/{id}", categoryGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryGroups.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.permissionType").value(DEFAULT_PERMISSION_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryGroups() throws Exception {
        // Get the categoryGroups
        restCategoryGroupsMockMvc.perform(get("/api/category-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryGroups() throws Exception {
        // Initialize the database
        categoryGroupsRepository.saveAndFlush(categoryGroups);

        int databaseSizeBeforeUpdate = categoryGroupsRepository.findAll().size();

        // Update the categoryGroups
        CategoryGroups updatedCategoryGroups = categoryGroupsRepository.findById(categoryGroups.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryGroups are not directly saved in db
        em.detach(updatedCategoryGroups);
        updatedCategoryGroups
            .categoryId(UPDATED_CATEGORY_ID)
            .groupId(UPDATED_GROUP_ID)
            .permissionType(UPDATED_PERMISSION_TYPE);
        CategoryGroupsDTO categoryGroupsDTO = categoryGroupsMapper.toDto(updatedCategoryGroups);

        restCategoryGroupsMockMvc.perform(put("/api/category-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryGroups in the database
        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeUpdate);
        CategoryGroups testCategoryGroups = categoryGroupsList.get(categoryGroupsList.size() - 1);
        assertThat(testCategoryGroups.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryGroups.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testCategoryGroups.getPermissionType()).isEqualTo(UPDATED_PERMISSION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryGroups() throws Exception {
        int databaseSizeBeforeUpdate = categoryGroupsRepository.findAll().size();

        // Create the CategoryGroups
        CategoryGroupsDTO categoryGroupsDTO = categoryGroupsMapper.toDto(categoryGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryGroupsMockMvc.perform(put("/api/category-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryGroups in the database
        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryGroups() throws Exception {
        // Initialize the database
        categoryGroupsRepository.saveAndFlush(categoryGroups);

        int databaseSizeBeforeDelete = categoryGroupsRepository.findAll().size();

        // Delete the categoryGroups
        restCategoryGroupsMockMvc.perform(delete("/api/category-groups/{id}", categoryGroups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryGroups> categoryGroupsList = categoryGroupsRepository.findAll();
        assertThat(categoryGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
