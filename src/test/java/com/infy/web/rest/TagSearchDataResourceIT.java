/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TagSearchData;
import com.infy.repository.TagSearchDataRepository;
import com.infy.service.TagSearchDataService;
import com.infy.service.dto.TagSearchDataDTO;
import com.infy.service.mapper.TagSearchDataMapper;

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
 * Integration tests for the {@link TagSearchDataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TagSearchDataResourceIT {

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    private static final String DEFAULT_SEARCH_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private TagSearchDataRepository tagSearchDataRepository;

    @Autowired
    private TagSearchDataMapper tagSearchDataMapper;

    @Autowired
    private TagSearchDataService tagSearchDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagSearchDataMockMvc;

    private TagSearchData tagSearchData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagSearchData createEntity(EntityManager em) {
        TagSearchData tagSearchData = new TagSearchData()
            .tagId(DEFAULT_TAG_ID)
            .searchData(DEFAULT_SEARCH_DATA)
            .rawData(DEFAULT_RAW_DATA)
            .locale(DEFAULT_LOCALE)
            .version(DEFAULT_VERSION);
        return tagSearchData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagSearchData createUpdatedEntity(EntityManager em) {
        TagSearchData tagSearchData = new TagSearchData()
            .tagId(UPDATED_TAG_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION);
        return tagSearchData;
    }

    @BeforeEach
    public void initTest() {
        tagSearchData = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagSearchData() throws Exception {
        int databaseSizeBeforeCreate = tagSearchDataRepository.findAll().size();
        // Create the TagSearchData
        TagSearchDataDTO tagSearchDataDTO = tagSearchDataMapper.toDto(tagSearchData);
        restTagSearchDataMockMvc.perform(post("/api/tag-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagSearchDataDTO)))
            .andExpect(status().isCreated());

        // Validate the TagSearchData in the database
        List<TagSearchData> tagSearchDataList = tagSearchDataRepository.findAll();
        assertThat(tagSearchDataList).hasSize(databaseSizeBeforeCreate + 1);
        TagSearchData testTagSearchData = tagSearchDataList.get(tagSearchDataList.size() - 1);
        assertThat(testTagSearchData.getTagId()).isEqualTo(DEFAULT_TAG_ID);
        assertThat(testTagSearchData.getSearchData()).isEqualTo(DEFAULT_SEARCH_DATA);
        assertThat(testTagSearchData.getRawData()).isEqualTo(DEFAULT_RAW_DATA);
        assertThat(testTagSearchData.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testTagSearchData.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createTagSearchDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagSearchDataRepository.findAll().size();

        // Create the TagSearchData with an existing ID
        tagSearchData.setId(1L);
        TagSearchDataDTO tagSearchDataDTO = tagSearchDataMapper.toDto(tagSearchData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagSearchDataMockMvc.perform(post("/api/tag-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagSearchData in the database
        List<TagSearchData> tagSearchDataList = tagSearchDataRepository.findAll();
        assertThat(tagSearchDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagSearchDataRepository.findAll().size();
        // set the field null
        tagSearchData.setTagId(null);

        // Create the TagSearchData, which fails.
        TagSearchDataDTO tagSearchDataDTO = tagSearchDataMapper.toDto(tagSearchData);


        restTagSearchDataMockMvc.perform(post("/api/tag-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagSearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<TagSearchData> tagSearchDataList = tagSearchDataRepository.findAll();
        assertThat(tagSearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagSearchData() throws Exception {
        // Initialize the database
        tagSearchDataRepository.saveAndFlush(tagSearchData);

        // Get all the tagSearchDataList
        restTagSearchDataMockMvc.perform(get("/api/tag-search-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagSearchData.getId().intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())))
            .andExpect(jsonPath("$.[*].searchData").value(hasItem(DEFAULT_SEARCH_DATA)))
            .andExpect(jsonPath("$.[*].rawData").value(hasItem(DEFAULT_RAW_DATA)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getTagSearchData() throws Exception {
        // Initialize the database
        tagSearchDataRepository.saveAndFlush(tagSearchData);

        // Get the tagSearchData
        restTagSearchDataMockMvc.perform(get("/api/tag-search-data/{id}", tagSearchData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagSearchData.getId().intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()))
            .andExpect(jsonPath("$.searchData").value(DEFAULT_SEARCH_DATA))
            .andExpect(jsonPath("$.rawData").value(DEFAULT_RAW_DATA))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingTagSearchData() throws Exception {
        // Get the tagSearchData
        restTagSearchDataMockMvc.perform(get("/api/tag-search-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagSearchData() throws Exception {
        // Initialize the database
        tagSearchDataRepository.saveAndFlush(tagSearchData);

        int databaseSizeBeforeUpdate = tagSearchDataRepository.findAll().size();

        // Update the tagSearchData
        TagSearchData updatedTagSearchData = tagSearchDataRepository.findById(tagSearchData.getId()).get();
        // Disconnect from session so that the updates on updatedTagSearchData are not directly saved in db
        em.detach(updatedTagSearchData);
        updatedTagSearchData
            .tagId(UPDATED_TAG_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION);
        TagSearchDataDTO tagSearchDataDTO = tagSearchDataMapper.toDto(updatedTagSearchData);

        restTagSearchDataMockMvc.perform(put("/api/tag-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagSearchDataDTO)))
            .andExpect(status().isOk());

        // Validate the TagSearchData in the database
        List<TagSearchData> tagSearchDataList = tagSearchDataRepository.findAll();
        assertThat(tagSearchDataList).hasSize(databaseSizeBeforeUpdate);
        TagSearchData testTagSearchData = tagSearchDataList.get(tagSearchDataList.size() - 1);
        assertThat(testTagSearchData.getTagId()).isEqualTo(UPDATED_TAG_ID);
        assertThat(testTagSearchData.getSearchData()).isEqualTo(UPDATED_SEARCH_DATA);
        assertThat(testTagSearchData.getRawData()).isEqualTo(UPDATED_RAW_DATA);
        assertThat(testTagSearchData.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testTagSearchData.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingTagSearchData() throws Exception {
        int databaseSizeBeforeUpdate = tagSearchDataRepository.findAll().size();

        // Create the TagSearchData
        TagSearchDataDTO tagSearchDataDTO = tagSearchDataMapper.toDto(tagSearchData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagSearchDataMockMvc.perform(put("/api/tag-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagSearchData in the database
        List<TagSearchData> tagSearchDataList = tagSearchDataRepository.findAll();
        assertThat(tagSearchDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagSearchData() throws Exception {
        // Initialize the database
        tagSearchDataRepository.saveAndFlush(tagSearchData);

        int databaseSizeBeforeDelete = tagSearchDataRepository.findAll().size();

        // Delete the tagSearchData
        restTagSearchDataMockMvc.perform(delete("/api/tag-search-data/{id}", tagSearchData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagSearchData> tagSearchDataList = tagSearchDataRepository.findAll();
        assertThat(tagSearchDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
