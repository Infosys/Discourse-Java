/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostSearchData;
import com.infy.repository.PostSearchDataRepository;
import com.infy.service.PostSearchDataService;
import com.infy.service.dto.PostSearchDataDTO;
import com.infy.service.mapper.PostSearchDataMapper;

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
 * Integration tests for the {@link PostSearchDataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostSearchDataResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_SEARCH_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Boolean DEFAULT_PRIVATE_MESSAGE = false;
    private static final Boolean UPDATED_PRIVATE_MESSAGE = true;

    @Autowired
    private PostSearchDataRepository postSearchDataRepository;

    @Autowired
    private PostSearchDataMapper postSearchDataMapper;

    @Autowired
    private PostSearchDataService postSearchDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostSearchDataMockMvc;

    private PostSearchData postSearchData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostSearchData createEntity(EntityManager em) {
        PostSearchData postSearchData = new PostSearchData()
            .postId(DEFAULT_POST_ID)
            .searchData(DEFAULT_SEARCH_DATA)
            .rawData(DEFAULT_RAW_DATA)
            .locale(DEFAULT_LOCALE)
            .version(DEFAULT_VERSION)
            .privateMessage(DEFAULT_PRIVATE_MESSAGE);
        return postSearchData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostSearchData createUpdatedEntity(EntityManager em) {
        PostSearchData postSearchData = new PostSearchData()
            .postId(UPDATED_POST_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION)
            .privateMessage(UPDATED_PRIVATE_MESSAGE);
        return postSearchData;
    }

    @BeforeEach
    public void initTest() {
        postSearchData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostSearchData() throws Exception {
        int databaseSizeBeforeCreate = postSearchDataRepository.findAll().size();
        // Create the PostSearchData
        PostSearchDataDTO postSearchDataDTO = postSearchDataMapper.toDto(postSearchData);
        restPostSearchDataMockMvc.perform(post("/api/post-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postSearchDataDTO)))
            .andExpect(status().isCreated());

        // Validate the PostSearchData in the database
        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeCreate + 1);
        PostSearchData testPostSearchData = postSearchDataList.get(postSearchDataList.size() - 1);
        assertThat(testPostSearchData.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostSearchData.getSearchData()).isEqualTo(DEFAULT_SEARCH_DATA);
        assertThat(testPostSearchData.getRawData()).isEqualTo(DEFAULT_RAW_DATA);
        assertThat(testPostSearchData.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testPostSearchData.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPostSearchData.isPrivateMessage()).isEqualTo(DEFAULT_PRIVATE_MESSAGE);
    }

    @Test
    @Transactional
    public void createPostSearchDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postSearchDataRepository.findAll().size();

        // Create the PostSearchData with an existing ID
        postSearchData.setId(1L);
        PostSearchDataDTO postSearchDataDTO = postSearchDataMapper.toDto(postSearchData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostSearchDataMockMvc.perform(post("/api/post-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostSearchData in the database
        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postSearchDataRepository.findAll().size();
        // set the field null
        postSearchData.setPostId(null);

        // Create the PostSearchData, which fails.
        PostSearchDataDTO postSearchDataDTO = postSearchDataMapper.toDto(postSearchData);


        restPostSearchDataMockMvc.perform(post("/api/post-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postSearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrivateMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = postSearchDataRepository.findAll().size();
        // set the field null
        postSearchData.setPrivateMessage(null);

        // Create the PostSearchData, which fails.
        PostSearchDataDTO postSearchDataDTO = postSearchDataMapper.toDto(postSearchData);


        restPostSearchDataMockMvc.perform(post("/api/post-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postSearchDataDTO)))
            .andExpect(status().isBadRequest());

        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostSearchData() throws Exception {
        // Initialize the database
        postSearchDataRepository.saveAndFlush(postSearchData);

        // Get all the postSearchDataList
        restPostSearchDataMockMvc.perform(get("/api/post-search-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postSearchData.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].searchData").value(hasItem(DEFAULT_SEARCH_DATA)))
            .andExpect(jsonPath("$.[*].rawData").value(hasItem(DEFAULT_RAW_DATA)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].privateMessage").value(hasItem(DEFAULT_PRIVATE_MESSAGE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPostSearchData() throws Exception {
        // Initialize the database
        postSearchDataRepository.saveAndFlush(postSearchData);

        // Get the postSearchData
        restPostSearchDataMockMvc.perform(get("/api/post-search-data/{id}", postSearchData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postSearchData.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.searchData").value(DEFAULT_SEARCH_DATA))
            .andExpect(jsonPath("$.rawData").value(DEFAULT_RAW_DATA))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.privateMessage").value(DEFAULT_PRIVATE_MESSAGE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPostSearchData() throws Exception {
        // Get the postSearchData
        restPostSearchDataMockMvc.perform(get("/api/post-search-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostSearchData() throws Exception {
        // Initialize the database
        postSearchDataRepository.saveAndFlush(postSearchData);

        int databaseSizeBeforeUpdate = postSearchDataRepository.findAll().size();

        // Update the postSearchData
        PostSearchData updatedPostSearchData = postSearchDataRepository.findById(postSearchData.getId()).get();
        // Disconnect from session so that the updates on updatedPostSearchData are not directly saved in db
        em.detach(updatedPostSearchData);
        updatedPostSearchData
            .postId(UPDATED_POST_ID)
            .searchData(UPDATED_SEARCH_DATA)
            .rawData(UPDATED_RAW_DATA)
            .locale(UPDATED_LOCALE)
            .version(UPDATED_VERSION)
            .privateMessage(UPDATED_PRIVATE_MESSAGE);
        PostSearchDataDTO postSearchDataDTO = postSearchDataMapper.toDto(updatedPostSearchData);

        restPostSearchDataMockMvc.perform(put("/api/post-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postSearchDataDTO)))
            .andExpect(status().isOk());

        // Validate the PostSearchData in the database
        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeUpdate);
        PostSearchData testPostSearchData = postSearchDataList.get(postSearchDataList.size() - 1);
        assertThat(testPostSearchData.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostSearchData.getSearchData()).isEqualTo(UPDATED_SEARCH_DATA);
        assertThat(testPostSearchData.getRawData()).isEqualTo(UPDATED_RAW_DATA);
        assertThat(testPostSearchData.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testPostSearchData.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPostSearchData.isPrivateMessage()).isEqualTo(UPDATED_PRIVATE_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPostSearchData() throws Exception {
        int databaseSizeBeforeUpdate = postSearchDataRepository.findAll().size();

        // Create the PostSearchData
        PostSearchDataDTO postSearchDataDTO = postSearchDataMapper.toDto(postSearchData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostSearchDataMockMvc.perform(put("/api/post-search-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postSearchDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostSearchData in the database
        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostSearchData() throws Exception {
        // Initialize the database
        postSearchDataRepository.saveAndFlush(postSearchData);

        int databaseSizeBeforeDelete = postSearchDataRepository.findAll().size();

        // Delete the postSearchData
        restPostSearchDataMockMvc.perform(delete("/api/post-search-data/{id}", postSearchData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostSearchData> postSearchDataList = postSearchDataRepository.findAll();
        assertThat(postSearchDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
