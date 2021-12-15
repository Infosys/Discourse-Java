/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryTagStats;
import com.infy.repository.CategoryTagStatsRepository;
import com.infy.service.CategoryTagStatsService;
import com.infy.service.dto.CategoryTagStatsDTO;
import com.infy.service.mapper.CategoryTagStatsMapper;

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
 * Integration tests for the {@link CategoryTagStatsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryTagStatsResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    private static final Integer DEFAULT_TOPIC_COUNT = 1;
    private static final Integer UPDATED_TOPIC_COUNT = 2;

    @Autowired
    private CategoryTagStatsRepository categoryTagStatsRepository;

    @Autowired
    private CategoryTagStatsMapper categoryTagStatsMapper;

    @Autowired
    private CategoryTagStatsService categoryTagStatsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryTagStatsMockMvc;

    private CategoryTagStats categoryTagStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryTagStats createEntity(EntityManager em) {
        CategoryTagStats categoryTagStats = new CategoryTagStats()
            .categoryId(DEFAULT_CATEGORY_ID)
            .tagId(DEFAULT_TAG_ID)
            .topicCount(DEFAULT_TOPIC_COUNT);
        return categoryTagStats;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryTagStats createUpdatedEntity(EntityManager em) {
        CategoryTagStats categoryTagStats = new CategoryTagStats()
            .categoryId(UPDATED_CATEGORY_ID)
            .tagId(UPDATED_TAG_ID)
            .topicCount(UPDATED_TOPIC_COUNT);
        return categoryTagStats;
    }

    @BeforeEach
    public void initTest() {
        categoryTagStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryTagStats() throws Exception {
        int databaseSizeBeforeCreate = categoryTagStatsRepository.findAll().size();
        // Create the CategoryTagStats
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(categoryTagStats);
        restCategoryTagStatsMockMvc.perform(post("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryTagStats in the database
        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryTagStats testCategoryTagStats = categoryTagStatsList.get(categoryTagStatsList.size() - 1);
        assertThat(testCategoryTagStats.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryTagStats.getTagId()).isEqualTo(DEFAULT_TAG_ID);
        assertThat(testCategoryTagStats.getTopicCount()).isEqualTo(DEFAULT_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void createCategoryTagStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryTagStatsRepository.findAll().size();

        // Create the CategoryTagStats with an existing ID
        categoryTagStats.setId(1L);
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(categoryTagStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryTagStatsMockMvc.perform(post("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryTagStats in the database
        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagStatsRepository.findAll().size();
        // set the field null
        categoryTagStats.setCategoryId(null);

        // Create the CategoryTagStats, which fails.
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(categoryTagStats);


        restCategoryTagStatsMockMvc.perform(post("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagStatsRepository.findAll().size();
        // set the field null
        categoryTagStats.setTagId(null);

        // Create the CategoryTagStats, which fails.
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(categoryTagStats);


        restCategoryTagStatsMockMvc.perform(post("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTagStatsRepository.findAll().size();
        // set the field null
        categoryTagStats.setTopicCount(null);

        // Create the CategoryTagStats, which fails.
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(categoryTagStats);


        restCategoryTagStatsMockMvc.perform(post("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryTagStats() throws Exception {
        // Initialize the database
        categoryTagStatsRepository.saveAndFlush(categoryTagStats);

        // Get all the categoryTagStatsList
        restCategoryTagStatsMockMvc.perform(get("/api/category-tag-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryTagStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicCount").value(hasItem(DEFAULT_TOPIC_COUNT)));
    }

    @Test
    @Transactional
    public void getCategoryTagStats() throws Exception {
        // Initialize the database
        categoryTagStatsRepository.saveAndFlush(categoryTagStats);

        // Get the categoryTagStats
        restCategoryTagStatsMockMvc.perform(get("/api/category-tag-stats/{id}", categoryTagStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryTagStats.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()))
            .andExpect(jsonPath("$.topicCount").value(DEFAULT_TOPIC_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryTagStats() throws Exception {
        // Get the categoryTagStats
        restCategoryTagStatsMockMvc.perform(get("/api/category-tag-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryTagStats() throws Exception {
        // Initialize the database
        categoryTagStatsRepository.saveAndFlush(categoryTagStats);

        int databaseSizeBeforeUpdate = categoryTagStatsRepository.findAll().size();

        // Update the categoryTagStats
        CategoryTagStats updatedCategoryTagStats = categoryTagStatsRepository.findById(categoryTagStats.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryTagStats are not directly saved in db
        em.detach(updatedCategoryTagStats);
        updatedCategoryTagStats
            .categoryId(UPDATED_CATEGORY_ID)
            .tagId(UPDATED_TAG_ID)
            .topicCount(UPDATED_TOPIC_COUNT);
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(updatedCategoryTagStats);

        restCategoryTagStatsMockMvc.perform(put("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryTagStats in the database
        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeUpdate);
        CategoryTagStats testCategoryTagStats = categoryTagStatsList.get(categoryTagStatsList.size() - 1);
        assertThat(testCategoryTagStats.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryTagStats.getTagId()).isEqualTo(UPDATED_TAG_ID);
        assertThat(testCategoryTagStats.getTopicCount()).isEqualTo(UPDATED_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryTagStats() throws Exception {
        int databaseSizeBeforeUpdate = categoryTagStatsRepository.findAll().size();

        // Create the CategoryTagStats
        CategoryTagStatsDTO categoryTagStatsDTO = categoryTagStatsMapper.toDto(categoryTagStats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTagStatsMockMvc.perform(put("/api/category-tag-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryTagStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryTagStats in the database
        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryTagStats() throws Exception {
        // Initialize the database
        categoryTagStatsRepository.saveAndFlush(categoryTagStats);

        int databaseSizeBeforeDelete = categoryTagStatsRepository.findAll().size();

        // Delete the categoryTagStats
        restCategoryTagStatsMockMvc.perform(delete("/api/category-tag-stats/{id}", categoryTagStats.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryTagStats> categoryTagStatsList = categoryTagStatsRepository.findAll();
        assertThat(categoryTagStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
