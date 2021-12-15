/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicSearchData;
import com.infy.repository.TopicSearchDataRepository;
import com.infy.service.TopicSearchDataService;
import com.infy.service.dto.TopicSearchDataDTO;
import com.infy.service.mapper.TopicSearchDataMapper;

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
 * Integration tests for the {@link TopicSearchDataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicSearchDataResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final String DEFAULT_RAW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DATA = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private TopicSearchDataRepository topicSearchDataRepository;

    @Autowired
    private TopicSearchDataMapper topicSearchDataMapper;

    @Autowired
    private TopicSearchDataService topicSearchDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicSearchDataMockMvc;

    private TopicSearchData topicSearchData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicSearchData createEntity(EntityManager em) {
        TopicSearchData topicSearchData = new TopicSearchData()
            .topicId(DEFAULT_TOPIC_ID)
            .rawData(DEFAULT_RAW_DATA)
            .locale(DEFAULT_LOCALE)
            .searchData(DEFAULT_SEARCH_DATA)
            .version(DEFAULT_VERSION);
        return topicSearchData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicSearchData createUpdatedEntity(EntityManager em) {
        TopicSearchData topicSearchData = new TopicSearchData()
            .topicId(UPDATED_TOPIC_ID)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .searchData(UPDATED_SEARCH_DATA)
            .version(UPDATED_VERSION);
        return topicSearchData;
    }

    @BeforeEach
    public void initTest() {
        topicSearchData = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicSearchData() throws Exception {
        int databaseSizeBeforeCreate = topicSearchDataRepository.findAll().size();
        // Create the TopicSearchData
        TopicSearchDataDTO topicSearchDataDTO = topicSearchDataMapper.toDto(topicSearchData);
        restTopicSearchDataMockMvc.perform(post("/api/topic-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicSearchDataDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicSearchData in the database
        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeCreate + 1);
        TopicSearchData testTopicSearchData = topicSearchDataList.get(topicSearchDataList.size() - 1);
        assertThat(testTopicSearchData.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicSearchData.getRawData()).isEqualTo(DEFAULT_RAW_DATA);
        assertThat(testTopicSearchData.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testTopicSearchData.getSearchData()).isEqualTo(DEFAULT_SEARCH_DATA);
        assertThat(testTopicSearchData.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createTopicSearchDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicSearchDataRepository.findAll().size();

        // Create the TopicSearchData with an existing ID
        topicSearchData.setId(1L);
        TopicSearchDataDTO topicSearchDataDTO = topicSearchDataMapper.toDto(topicSearchData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicSearchDataMockMvc.perform(post("/api/topic-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicSearchData in the database
        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicSearchDataRepository.findAll().size();
        // set the field null
        topicSearchData.setTopicId(null);

        // Create the TopicSearchData, which fails.
        TopicSearchDataDTO topicSearchDataDTO = topicSearchDataMapper.toDto(topicSearchData);


        restTopicSearchDataMockMvc.perform(post("/api/topic-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicSearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicSearchDataRepository.findAll().size();
        // set the field null
        topicSearchData.setLocale(null);

        // Create the TopicSearchData, which fails.
        TopicSearchDataDTO topicSearchDataDTO = topicSearchDataMapper.toDto(topicSearchData);


        restTopicSearchDataMockMvc.perform(post("/api/topic-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicSearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicSearchData() throws Exception {
        // Initialize the database
        topicSearchDataRepository.saveAndFlush(topicSearchData);

        // Get all the topicSearchDataList
        restTopicSearchDataMockMvc.perform(get("/api/topic-search-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicSearchData.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].rawData").value(hasItem(DEFAULT_RAW_DATA)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].searchData").value(hasItem(DEFAULT_SEARCH_DATA)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getTopicSearchData() throws Exception {
        // Initialize the database
        topicSearchDataRepository.saveAndFlush(topicSearchData);

        // Get the topicSearchData
        restTopicSearchDataMockMvc.perform(get("/api/topic-search-data/{id}", topicSearchData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicSearchData.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.rawData").value(DEFAULT_RAW_DATA))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.searchData").value(DEFAULT_SEARCH_DATA))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingTopicSearchData() throws Exception {
        // Get the topicSearchData
        restTopicSearchDataMockMvc.perform(get("/api/topic-search-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicSearchData() throws Exception {
        // Initialize the database
        topicSearchDataRepository.saveAndFlush(topicSearchData);

        int databaseSizeBeforeUpdate = topicSearchDataRepository.findAll().size();

        // Update the topicSearchData
        TopicSearchData updatedTopicSearchData = topicSearchDataRepository.findById(topicSearchData.getId()).get();
        // Disconnect from session so that the updates on updatedTopicSearchData are not directly saved in db
        em.detach(updatedTopicSearchData);
        updatedTopicSearchData
            .topicId(UPDATED_TOPIC_ID)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .searchData(UPDATED_SEARCH_DATA)
            .version(UPDATED_VERSION);
        TopicSearchDataDTO topicSearchDataDTO = topicSearchDataMapper.toDto(updatedTopicSearchData);

        restTopicSearchDataMockMvc.perform(put("/api/topic-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicSearchDataDTO)))
            .andExpect(status().isOk());

        // Validate the TopicSearchData in the database
        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeUpdate);
        TopicSearchData testTopicSearchData = topicSearchDataList.get(topicSearchDataList.size() - 1);
        assertThat(testTopicSearchData.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicSearchData.getRawData()).isEqualTo(UPDATED_RAW_DATA);
        assertThat(testTopicSearchData.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testTopicSearchData.getSearchData()).isEqualTo(UPDATED_SEARCH_DATA);
        assertThat(testTopicSearchData.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicSearchData() throws Exception {
        int databaseSizeBeforeUpdate = topicSearchDataRepository.findAll().size();

        // Create the TopicSearchData
        TopicSearchDataDTO topicSearchDataDTO = topicSearchDataMapper.toDto(topicSearchData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicSearchDataMockMvc.perform(put("/api/topic-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicSearchData in the database
        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicSearchData() throws Exception {
        // Initialize the database
        topicSearchDataRepository.saveAndFlush(topicSearchData);

        int databaseSizeBeforeDelete = topicSearchDataRepository.findAll().size();

        // Delete the topicSearchData
        restTopicSearchDataMockMvc.perform(delete("/api/topic-search-data/{id}", topicSearchData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicSearchData> topicSearchDataList = topicSearchDataRepository.findAll();
        assertThat(topicSearchDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
