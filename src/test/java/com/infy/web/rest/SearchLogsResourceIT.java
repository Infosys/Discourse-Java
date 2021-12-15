/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SearchLogs;
import com.infy.repository.SearchLogsRepository;
import com.infy.service.SearchLogsService;
import com.infy.service.dto.SearchLogsDTO;
import com.infy.service.mapper.SearchLogsMapper;

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
 * Integration tests for the {@link SearchLogsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SearchLogsResourceIT {

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_SEARCH_RESULT_ID = 1L;
    private static final Long UPDATED_SEARCH_RESULT_ID = 2L;

    private static final Integer DEFAULT_SEARCH_TYPE = 1;
    private static final Integer UPDATED_SEARCH_TYPE = 2;

    private static final Integer DEFAULT_SEARCH_RESULT_TYPE = 1;
    private static final Integer UPDATED_SEARCH_RESULT_TYPE = 2;

    @Autowired
    private SearchLogsRepository searchLogsRepository;

    @Autowired
    private SearchLogsMapper searchLogsMapper;

    @Autowired
    private SearchLogsService searchLogsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSearchLogsMockMvc;

    private SearchLogs searchLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchLogs createEntity(EntityManager em) {
        SearchLogs searchLogs = new SearchLogs()
            .term(DEFAULT_TERM)
            .userId(DEFAULT_USER_ID)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .searchResultId(DEFAULT_SEARCH_RESULT_ID)
            .searchType(DEFAULT_SEARCH_TYPE)
            .searchResultType(DEFAULT_SEARCH_RESULT_TYPE);
        return searchLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchLogs createUpdatedEntity(EntityManager em) {
        SearchLogs searchLogs = new SearchLogs()
            .term(UPDATED_TERM)
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS)
            .searchResultId(UPDATED_SEARCH_RESULT_ID)
            .searchType(UPDATED_SEARCH_TYPE)
            .searchResultType(UPDATED_SEARCH_RESULT_TYPE);
        return searchLogs;
    }

    @BeforeEach
    public void initTest() {
        searchLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createSearchLogs() throws Exception {
        int databaseSizeBeforeCreate = searchLogsRepository.findAll().size();
        // Create the SearchLogs
        SearchLogsDTO searchLogsDTO = searchLogsMapper.toDto(searchLogs);
        restSearchLogsMockMvc.perform(post("/api/search-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(searchLogsDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchLogs in the database
        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeCreate + 1);
        SearchLogs testSearchLogs = searchLogsList.get(searchLogsList.size() - 1);
        assertThat(testSearchLogs.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testSearchLogs.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSearchLogs.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testSearchLogs.getSearchResultId()).isEqualTo(DEFAULT_SEARCH_RESULT_ID);
        assertThat(testSearchLogs.getSearchType()).isEqualTo(DEFAULT_SEARCH_TYPE);
        assertThat(testSearchLogs.getSearchResultType()).isEqualTo(DEFAULT_SEARCH_RESULT_TYPE);
    }

    @Test
    @Transactional
    public void createSearchLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchLogsRepository.findAll().size();

        // Create the SearchLogs with an existing ID
        searchLogs.setId(1L);
        SearchLogsDTO searchLogsDTO = searchLogsMapper.toDto(searchLogs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchLogsMockMvc.perform(post("/api/search-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(searchLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SearchLogs in the database
        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTermIsRequired() throws Exception {
        int databaseSizeBeforeTest = searchLogsRepository.findAll().size();
        // set the field null
        searchLogs.setTerm(null);

        // Create the SearchLogs, which fails.
        SearchLogsDTO searchLogsDTO = searchLogsMapper.toDto(searchLogs);


        restSearchLogsMockMvc.perform(post("/api/search-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(searchLogsDTO)))
            .andExpect(status().isBadRequest());

        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSearchTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = searchLogsRepository.findAll().size();
        // set the field null
        searchLogs.setSearchType(null);

        // Create the SearchLogs, which fails.
        SearchLogsDTO searchLogsDTO = searchLogsMapper.toDto(searchLogs);


        restSearchLogsMockMvc.perform(post("/api/search-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(searchLogsDTO)))
            .andExpect(status().isBadRequest());

        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSearchLogs() throws Exception {
        // Initialize the database
        searchLogsRepository.saveAndFlush(searchLogs);

        // Get all the searchLogsList
        restSearchLogsMockMvc.perform(get("/api/search-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].searchResultId").value(hasItem(DEFAULT_SEARCH_RESULT_ID.intValue())))
            .andExpect(jsonPath("$.[*].searchType").value(hasItem(DEFAULT_SEARCH_TYPE)))
            .andExpect(jsonPath("$.[*].searchResultType").value(hasItem(DEFAULT_SEARCH_RESULT_TYPE)));
    }

    @Test
    @Transactional
    public void getSearchLogs() throws Exception {
        // Initialize the database
        searchLogsRepository.saveAndFlush(searchLogs);

        // Get the searchLogs
        restSearchLogsMockMvc.perform(get("/api/search-logs/{id}", searchLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(searchLogs.getId().intValue()))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.searchResultId").value(DEFAULT_SEARCH_RESULT_ID.intValue()))
            .andExpect(jsonPath("$.searchType").value(DEFAULT_SEARCH_TYPE))
            .andExpect(jsonPath("$.searchResultType").value(DEFAULT_SEARCH_RESULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingSearchLogs() throws Exception {
        // Get the searchLogs
        restSearchLogsMockMvc.perform(get("/api/search-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSearchLogs() throws Exception {
        // Initialize the database
        searchLogsRepository.saveAndFlush(searchLogs);

        int databaseSizeBeforeUpdate = searchLogsRepository.findAll().size();

        // Update the searchLogs
        SearchLogs updatedSearchLogs = searchLogsRepository.findById(searchLogs.getId()).get();
        // Disconnect from session so that the updates on updatedSearchLogs are not directly saved in db
        em.detach(updatedSearchLogs);
        updatedSearchLogs
            .term(UPDATED_TERM)
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS)
            .searchResultId(UPDATED_SEARCH_RESULT_ID)
            .searchType(UPDATED_SEARCH_TYPE)
            .searchResultType(UPDATED_SEARCH_RESULT_TYPE);
        SearchLogsDTO searchLogsDTO = searchLogsMapper.toDto(updatedSearchLogs);

        restSearchLogsMockMvc.perform(put("/api/search-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(searchLogsDTO)))
            .andExpect(status().isOk());

        // Validate the SearchLogs in the database
        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeUpdate);
        SearchLogs testSearchLogs = searchLogsList.get(searchLogsList.size() - 1);
        assertThat(testSearchLogs.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testSearchLogs.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSearchLogs.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testSearchLogs.getSearchResultId()).isEqualTo(UPDATED_SEARCH_RESULT_ID);
        assertThat(testSearchLogs.getSearchType()).isEqualTo(UPDATED_SEARCH_TYPE);
        assertThat(testSearchLogs.getSearchResultType()).isEqualTo(UPDATED_SEARCH_RESULT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSearchLogs() throws Exception {
        int databaseSizeBeforeUpdate = searchLogsRepository.findAll().size();

        // Create the SearchLogs
        SearchLogsDTO searchLogsDTO = searchLogsMapper.toDto(searchLogs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchLogsMockMvc.perform(put("/api/search-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(searchLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SearchLogs in the database
        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSearchLogs() throws Exception {
        // Initialize the database
        searchLogsRepository.saveAndFlush(searchLogs);

        int databaseSizeBeforeDelete = searchLogsRepository.findAll().size();

        // Delete the searchLogs
        restSearchLogsMockMvc.perform(delete("/api/search-logs/{id}", searchLogs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SearchLogs> searchLogsList = searchLogsRepository.findAll();
        assertThat(searchLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
