/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryFeaturedTopics;
import com.infy.repository.CategoryFeaturedTopicsRepository;
import com.infy.service.CategoryFeaturedTopicsService;
import com.infy.service.dto.CategoryFeaturedTopicsDTO;
import com.infy.service.mapper.CategoryFeaturedTopicsMapper;

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
 * Integration tests for the {@link CategoryFeaturedTopicsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryFeaturedTopicsResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;

    @Autowired
    private CategoryFeaturedTopicsRepository categoryFeaturedTopicsRepository;

    @Autowired
    private CategoryFeaturedTopicsMapper categoryFeaturedTopicsMapper;

    @Autowired
    private CategoryFeaturedTopicsService categoryFeaturedTopicsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryFeaturedTopicsMockMvc;

    private CategoryFeaturedTopics categoryFeaturedTopics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryFeaturedTopics createEntity(EntityManager em) {
        CategoryFeaturedTopics categoryFeaturedTopics = new CategoryFeaturedTopics()
            .categoryId(DEFAULT_CATEGORY_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .rank(DEFAULT_RANK);
        return categoryFeaturedTopics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryFeaturedTopics createUpdatedEntity(EntityManager em) {
        CategoryFeaturedTopics categoryFeaturedTopics = new CategoryFeaturedTopics()
            .categoryId(UPDATED_CATEGORY_ID)
            .topicId(UPDATED_TOPIC_ID)
            .rank(UPDATED_RANK);
        return categoryFeaturedTopics;
    }

    @BeforeEach
    public void initTest() {
        categoryFeaturedTopics = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryFeaturedTopics() throws Exception {
        int databaseSizeBeforeCreate = categoryFeaturedTopicsRepository.findAll().size();
        // Create the CategoryFeaturedTopics
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);
        restCategoryFeaturedTopicsMockMvc.perform(post("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryFeaturedTopics in the database
        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryFeaturedTopics testCategoryFeaturedTopics = categoryFeaturedTopicsList.get(categoryFeaturedTopicsList.size() - 1);
        assertThat(testCategoryFeaturedTopics.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryFeaturedTopics.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testCategoryFeaturedTopics.getRank()).isEqualTo(DEFAULT_RANK);
    }

    @Test
    @Transactional
    public void createCategoryFeaturedTopicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryFeaturedTopicsRepository.findAll().size();

        // Create the CategoryFeaturedTopics with an existing ID
        categoryFeaturedTopics.setId(1L);
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryFeaturedTopicsMockMvc.perform(post("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryFeaturedTopics in the database
        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryFeaturedTopicsRepository.findAll().size();
        // set the field null
        categoryFeaturedTopics.setCategoryId(null);

        // Create the CategoryFeaturedTopics, which fails.
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);


        restCategoryFeaturedTopicsMockMvc.perform(post("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryFeaturedTopicsRepository.findAll().size();
        // set the field null
        categoryFeaturedTopics.setTopicId(null);

        // Create the CategoryFeaturedTopics, which fails.
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);


        restCategoryFeaturedTopicsMockMvc.perform(post("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRankIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryFeaturedTopicsRepository.findAll().size();
        // set the field null
        categoryFeaturedTopics.setRank(null);

        // Create the CategoryFeaturedTopics, which fails.
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);


        restCategoryFeaturedTopicsMockMvc.perform(post("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryFeaturedTopics() throws Exception {
        // Initialize the database
        categoryFeaturedTopicsRepository.saveAndFlush(categoryFeaturedTopics);

        // Get all the categoryFeaturedTopicsList
        restCategoryFeaturedTopicsMockMvc.perform(get("/api/category-featured-topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryFeaturedTopics.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)));
    }

    @Test
    @Transactional
    public void getCategoryFeaturedTopics() throws Exception {
        // Initialize the database
        categoryFeaturedTopicsRepository.saveAndFlush(categoryFeaturedTopics);

        // Get the categoryFeaturedTopics
        restCategoryFeaturedTopicsMockMvc.perform(get("/api/category-featured-topics/{id}", categoryFeaturedTopics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryFeaturedTopics.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryFeaturedTopics() throws Exception {
        // Get the categoryFeaturedTopics
        restCategoryFeaturedTopicsMockMvc.perform(get("/api/category-featured-topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryFeaturedTopics() throws Exception {
        // Initialize the database
        categoryFeaturedTopicsRepository.saveAndFlush(categoryFeaturedTopics);

        int databaseSizeBeforeUpdate = categoryFeaturedTopicsRepository.findAll().size();

        // Update the categoryFeaturedTopics
        CategoryFeaturedTopics updatedCategoryFeaturedTopics = categoryFeaturedTopicsRepository.findById(categoryFeaturedTopics.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryFeaturedTopics are not directly saved in db
        em.detach(updatedCategoryFeaturedTopics);
        updatedCategoryFeaturedTopics
            .categoryId(UPDATED_CATEGORY_ID)
            .topicId(UPDATED_TOPIC_ID)
            .rank(UPDATED_RANK);
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(updatedCategoryFeaturedTopics);

        restCategoryFeaturedTopicsMockMvc.perform(put("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryFeaturedTopics in the database
        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeUpdate);
        CategoryFeaturedTopics testCategoryFeaturedTopics = categoryFeaturedTopicsList.get(categoryFeaturedTopicsList.size() - 1);
        assertThat(testCategoryFeaturedTopics.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryFeaturedTopics.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testCategoryFeaturedTopics.getRank()).isEqualTo(UPDATED_RANK);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryFeaturedTopics() throws Exception {
        int databaseSizeBeforeUpdate = categoryFeaturedTopicsRepository.findAll().size();

        // Create the CategoryFeaturedTopics
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO = categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryFeaturedTopicsMockMvc.perform(put("/api/category-featured-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryFeaturedTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryFeaturedTopics in the database
        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryFeaturedTopics() throws Exception {
        // Initialize the database
        categoryFeaturedTopicsRepository.saveAndFlush(categoryFeaturedTopics);

        int databaseSizeBeforeDelete = categoryFeaturedTopicsRepository.findAll().size();

        // Delete the categoryFeaturedTopics
        restCategoryFeaturedTopicsMockMvc.perform(delete("/api/category-featured-topics/{id}", categoryFeaturedTopics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryFeaturedTopics> categoryFeaturedTopicsList = categoryFeaturedTopicsRepository.findAll();
        assertThat(categoryFeaturedTopicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
