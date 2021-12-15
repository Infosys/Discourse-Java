/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserSearchData;
import com.infy.repository.UserSearchDataRepository;
import com.infy.service.UserSearchDataService;
import com.infy.service.dto.UserSearchDataDTO;
import com.infy.service.mapper.UserSearchDataMapper;

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
 * Integration tests for the {@link UserSearchDataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserSearchDataResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private UserSearchDataRepository userSearchDataRepository;

    @Autowired
    private UserSearchDataMapper userSearchDataMapper;

    @Autowired
    private UserSearchDataService userSearchDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSearchDataMockMvc;

    private UserSearchData userSearchData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSearchData createEntity(EntityManager em) {
        UserSearchData userSearchData = new UserSearchData()
            .userId(DEFAULT_USER_ID)
            .searchData(DEFAULT_SEARCH_DATA)
            .rawData(DEFAULT_RAW_DATA)
            .locale(DEFAULT_LOCALE)
            .version(DEFAULT_VERSION);
        return userSearchData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSearchData createUpdatedEntity(EntityManager em) {
        UserSearchData userSearchData = new UserSearchData()
            .userId(UPDATED_USER_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION);
        return userSearchData;
    }

    @BeforeEach
    public void initTest() {
        userSearchData = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSearchData() throws Exception {
        int databaseSizeBeforeCreate = userSearchDataRepository.findAll().size();
        // Create the UserSearchData
        UserSearchDataDTO userSearchDataDTO = userSearchDataMapper.toDto(userSearchData);
        restUserSearchDataMockMvc.perform(post("/api/user-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSearchDataDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSearchData in the database
        List<UserSearchData> userSearchDataList = userSearchDataRepository.findAll();
        assertThat(userSearchDataList).hasSize(databaseSizeBeforeCreate + 1);
        UserSearchData testUserSearchData = userSearchDataList.get(userSearchDataList.size() - 1);
        assertThat(testUserSearchData.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserSearchData.getSearchData()).isEqualTo(DEFAULT_SEARCH_DATA);
        assertThat(testUserSearchData.getRawData()).isEqualTo(DEFAULT_RAW_DATA);
        assertThat(testUserSearchData.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testUserSearchData.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createUserSearchDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSearchDataRepository.findAll().size();

        // Create the UserSearchData with an existing ID
        userSearchData.setId(1L);
        UserSearchDataDTO userSearchDataDTO = userSearchDataMapper.toDto(userSearchData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSearchDataMockMvc.perform(post("/api/user-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSearchData in the database
        List<UserSearchData> userSearchDataList = userSearchDataRepository.findAll();
        assertThat(userSearchDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSearchDataRepository.findAll().size();
        // set the field null
        userSearchData.setUserId(null);

        // Create the UserSearchData, which fails.
        UserSearchDataDTO userSearchDataDTO = userSearchDataMapper.toDto(userSearchData);


        restUserSearchDataMockMvc.perform(post("/api/user-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<UserSearchData> userSearchDataList = userSearchDataRepository.findAll();
        assertThat(userSearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserSearchData() throws Exception {
        // Initialize the database
        userSearchDataRepository.saveAndFlush(userSearchData);

        // Get all the userSearchDataList
        restUserSearchDataMockMvc.perform(get("/api/user-search-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSearchData.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].searchData").value(hasItem(DEFAULT_SEARCH_DATA)))
            .andExpect(jsonPath("$.[*].rawData").value(hasItem(DEFAULT_RAW_DATA)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getUserSearchData() throws Exception {
        // Initialize the database
        userSearchDataRepository.saveAndFlush(userSearchData);

        // Get the userSearchData
        restUserSearchDataMockMvc.perform(get("/api/user-search-data/{id}", userSearchData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSearchData.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.searchData").value(DEFAULT_SEARCH_DATA))
            .andExpect(jsonPath("$.rawData").value(DEFAULT_RAW_DATA))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingUserSearchData() throws Exception {
        // Get the userSearchData
        restUserSearchDataMockMvc.perform(get("/api/user-search-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSearchData() throws Exception {
        // Initialize the database
        userSearchDataRepository.saveAndFlush(userSearchData);

        int databaseSizeBeforeUpdate = userSearchDataRepository.findAll().size();

        // Update the userSearchData
        UserSearchData updatedUserSearchData = userSearchDataRepository.findById(userSearchData.getId()).get();
        // Disconnect from session so that the updates on updatedUserSearchData are not directly saved in db
        em.detach(updatedUserSearchData);
        updatedUserSearchData
            .userId(UPDATED_USER_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION);
        UserSearchDataDTO userSearchDataDTO = userSearchDataMapper.toDto(updatedUserSearchData);

        restUserSearchDataMockMvc.perform(put("/api/user-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSearchDataDTO)))
            .andExpect(status().isOk());

        // Validate the UserSearchData in the database
        List<UserSearchData> userSearchDataList = userSearchDataRepository.findAll();
        assertThat(userSearchDataList).hasSize(databaseSizeBeforeUpdate);
        UserSearchData testUserSearchData = userSearchDataList.get(userSearchDataList.size() - 1);
        assertThat(testUserSearchData.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserSearchData.getSearchData()).isEqualTo(UPDATED_SEARCH_DATA);
        assertThat(testUserSearchData.getRawData()).isEqualTo(UPDATED_RAW_DATA);
        assertThat(testUserSearchData.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testUserSearchData.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSearchData() throws Exception {
        int databaseSizeBeforeUpdate = userSearchDataRepository.findAll().size();

        // Create the UserSearchData
        UserSearchDataDTO userSearchDataDTO = userSearchDataMapper.toDto(userSearchData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSearchDataMockMvc.perform(put("/api/user-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSearchData in the database
        List<UserSearchData> userSearchDataList = userSearchDataRepository.findAll();
        assertThat(userSearchDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSearchData() throws Exception {
        // Initialize the database
        userSearchDataRepository.saveAndFlush(userSearchData);

        int databaseSizeBeforeDelete = userSearchDataRepository.findAll().size();

        // Delete the userSearchData
        restUserSearchDataMockMvc.perform(delete("/api/user-search-data/{id}", userSearchData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSearchData> userSearchDataList = userSearchDataRepository.findAll();
        assertThat(userSearchDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
