/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategorySearchData;
import com.infy.repository.CategorySearchDataRepository;
import com.infy.service.CategorySearchDataService;
import com.infy.service.dto.CategorySearchDataDTO;
import com.infy.service.mapper.CategorySearchDataMapper;

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
 * Integration tests for the {@link CategorySearchDataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategorySearchDataResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_SEARCH_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private CategorySearchDataRepository categorySearchDataRepository;

    @Autowired
    private CategorySearchDataMapper categorySearchDataMapper;

    @Autowired
    private CategorySearchDataService categorySearchDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorySearchDataMockMvc;

    private CategorySearchData categorySearchData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorySearchData createEntity(EntityManager em) {
        CategorySearchData categorySearchData = new CategorySearchData()
            .categoryId(DEFAULT_CATEGORY_ID)
            .searchData(DEFAULT_SEARCH_DATA)
            .rawData(DEFAULT_RAW_DATA)
            .locale(DEFAULT_LOCALE)
            .version(DEFAULT_VERSION);
        return categorySearchData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorySearchData createUpdatedEntity(EntityManager em) {
        CategorySearchData categorySearchData = new CategorySearchData()
            .categoryId(UPDATED_CATEGORY_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION);
        return categorySearchData;
    }

    @BeforeEach
    public void initTest() {
        categorySearchData = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorySearchData() throws Exception {
        int databaseSizeBeforeCreate = categorySearchDataRepository.findAll().size();
        // Create the CategorySearchData
        CategorySearchDataDTO categorySearchDataDTO = categorySearchDataMapper.toDto(categorySearchData);
        restCategorySearchDataMockMvc.perform(post("/api/category-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorySearchDataDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorySearchData in the database
        List<CategorySearchData> categorySearchDataList = categorySearchDataRepository.findAll();
        assertThat(categorySearchDataList).hasSize(databaseSizeBeforeCreate + 1);
        CategorySearchData testCategorySearchData = categorySearchDataList.get(categorySearchDataList.size() - 1);
        assertThat(testCategorySearchData.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategorySearchData.getSearchData()).isEqualTo(DEFAULT_SEARCH_DATA);
        assertThat(testCategorySearchData.getRawData()).isEqualTo(DEFAULT_RAW_DATA);
        assertThat(testCategorySearchData.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testCategorySearchData.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createCategorySearchDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorySearchDataRepository.findAll().size();

        // Create the CategorySearchData with an existing ID
        categorySearchData.setId(1L);
        CategorySearchDataDTO categorySearchDataDTO = categorySearchDataMapper.toDto(categorySearchData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorySearchDataMockMvc.perform(post("/api/category-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorySearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorySearchData in the database
        List<CategorySearchData> categorySearchDataList = categorySearchDataRepository.findAll();
        assertThat(categorySearchDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorySearchDataRepository.findAll().size();
        // set the field null
        categorySearchData.setCategoryId(null);

        // Create the CategorySearchData, which fails.
        CategorySearchDataDTO categorySearchDataDTO = categorySearchDataMapper.toDto(categorySearchData);


        restCategorySearchDataMockMvc.perform(post("/api/category-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorySearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<CategorySearchData> categorySearchDataList = categorySearchDataRepository.findAll();
        assertThat(categorySearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorySearchData() throws Exception {
        // Initialize the database
        categorySearchDataRepository.saveAndFlush(categorySearchData);

        // Get all the categorySearchDataList
        restCategorySearchDataMockMvc.perform(get("/api/category-search-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorySearchData.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].searchData").value(hasItem(DEFAULT_SEARCH_DATA)))
            .andExpect(jsonPath("$.[*].rawData").value(hasItem(DEFAULT_RAW_DATA)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getCategorySearchData() throws Exception {
        // Initialize the database
        categorySearchDataRepository.saveAndFlush(categorySearchData);

        // Get the categorySearchData
        restCategorySearchDataMockMvc.perform(get("/api/category-search-data/{id}", categorySearchData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorySearchData.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.searchData").value(DEFAULT_SEARCH_DATA))
            .andExpect(jsonPath("$.rawData").value(DEFAULT_RAW_DATA))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingCategorySearchData() throws Exception {
        // Get the categorySearchData
        restCategorySearchDataMockMvc.perform(get("/api/category-search-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorySearchData() throws Exception {
        // Initialize the database
        categorySearchDataRepository.saveAndFlush(categorySearchData);

        int databaseSizeBeforeUpdate = categorySearchDataRepository.findAll().size();

        // Update the categorySearchData
        CategorySearchData updatedCategorySearchData = categorySearchDataRepository.findById(categorySearchData.getId()).get();
        // Disconnect from session so that the updates on updatedCategorySearchData are not directly saved in db
        em.detach(updatedCategorySearchData);
        updatedCategorySearchData
            .categoryId(UPDATED_CATEGORY_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION);
        CategorySearchDataDTO categorySearchDataDTO = categorySearchDataMapper.toDto(updatedCategorySearchData);

        restCategorySearchDataMockMvc.perform(put("/api/category-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorySearchDataDTO)))
            .andExpect(status().isOk());

        // Validate the CategorySearchData in the database
        List<CategorySearchData> categorySearchDataList = categorySearchDataRepository.findAll();
        assertThat(categorySearchDataList).hasSize(databaseSizeBeforeUpdate);
        CategorySearchData testCategorySearchData = categorySearchDataList.get(categorySearchDataList.size() - 1);
        assertThat(testCategorySearchData.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategorySearchData.getSearchData()).isEqualTo(UPDATED_SEARCH_DATA);
        assertThat(testCategorySearchData.getRawData()).isEqualTo(UPDATED_RAW_DATA);
        assertThat(testCategorySearchData.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testCategorySearchData.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorySearchData() throws Exception {
        int databaseSizeBeforeUpdate = categorySearchDataRepository.findAll().size();

        // Create the CategorySearchData
        CategorySearchDataDTO categorySearchDataDTO = categorySearchDataMapper.toDto(categorySearchData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorySearchDataMockMvc.perform(put("/api/category-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorySearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorySearchData in the database
        List<CategorySearchData> categorySearchDataList = categorySearchDataRepository.findAll();
        assertThat(categorySearchDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorySearchData() throws Exception {
        // Initialize the database
        categorySearchDataRepository.saveAndFlush(categorySearchData);

        int databaseSizeBeforeDelete = categorySearchDataRepository.findAll().size();

        // Delete the categorySearchData
        restCategorySearchDataMockMvc.perform(delete("/api/category-search-data/{id}", categorySearchData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorySearchData> categorySearchDataList = categorySearchDataRepository.findAll();
        assertThat(categorySearchDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
