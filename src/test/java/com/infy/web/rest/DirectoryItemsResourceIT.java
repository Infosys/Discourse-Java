/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.DirectoryItems;
import com.infy.repository.DirectoryItemsRepository;
import com.infy.service.DirectoryItemsService;
import com.infy.service.dto.DirectoryItemsDTO;
import com.infy.service.mapper.DirectoryItemsMapper;

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
 * Integration tests for the {@link DirectoryItemsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DirectoryItemsResourceIT {

    private static final Integer DEFAULT_PERIOD_TYPE = 1;
    private static final Integer UPDATED_PERIOD_TYPE = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIKES_RECEIVED = 1;
    private static final Integer UPDATED_LIKES_RECEIVED = 2;

    private static final Integer DEFAULT_LIKES_GIVEN = 1;
    private static final Integer UPDATED_LIKES_GIVEN = 2;

    private static final Integer DEFAULT_TOPICS_ENTERED = 1;
    private static final Integer UPDATED_TOPICS_ENTERED = 2;

    private static final Integer DEFAULT_TOPIC_COUNT = 1;
    private static final Integer UPDATED_TOPIC_COUNT = 2;

    private static final Integer DEFAULT_POST_COUNT = 1;
    private static final Integer UPDATED_POST_COUNT = 2;

    private static final Integer DEFAULT_DAYS_VISITED = 1;
    private static final Integer UPDATED_DAYS_VISITED = 2;

    private static final Integer DEFAULT_POSTS_READ = 1;
    private static final Integer UPDATED_POSTS_READ = 2;

    @Autowired
    private DirectoryItemsRepository directoryItemsRepository;

    @Autowired
    private DirectoryItemsMapper directoryItemsMapper;

    @Autowired
    private DirectoryItemsService directoryItemsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDirectoryItemsMockMvc;

    private DirectoryItems directoryItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DirectoryItems createEntity(EntityManager em) {
        DirectoryItems directoryItems = new DirectoryItems()
            .periodType(DEFAULT_PERIOD_TYPE)
            .userId(DEFAULT_USER_ID)
            .likesReceived(DEFAULT_LIKES_RECEIVED)
            .likesGiven(DEFAULT_LIKES_GIVEN)
            .topicsEntered(DEFAULT_TOPICS_ENTERED)
            .topicCount(DEFAULT_TOPIC_COUNT)
            .postCount(DEFAULT_POST_COUNT)
            .daysVisited(DEFAULT_DAYS_VISITED)
            .postsRead(DEFAULT_POSTS_READ);
        return directoryItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DirectoryItems createUpdatedEntity(EntityManager em) {
        DirectoryItems directoryItems = new DirectoryItems()
            .periodType(UPDATED_PERIOD_TYPE)
            .userId(UPDATED_USER_ID)
            .likesReceived(UPDATED_LIKES_RECEIVED)
            .likesGiven(UPDATED_LIKES_GIVEN)
            .topicsEntered(UPDATED_TOPICS_ENTERED)
            .topicCount(UPDATED_TOPIC_COUNT)
            .postCount(UPDATED_POST_COUNT)
            .daysVisited(UPDATED_DAYS_VISITED)
            .postsRead(UPDATED_POSTS_READ);
        return directoryItems;
    }

    @BeforeEach
    public void initTest() {
        directoryItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createDirectoryItems() throws Exception {
        int databaseSizeBeforeCreate = directoryItemsRepository.findAll().size();
        // Create the DirectoryItems
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);
        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the DirectoryItems in the database
        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeCreate + 1);
        DirectoryItems testDirectoryItems = directoryItemsList.get(directoryItemsList.size() - 1);
        assertThat(testDirectoryItems.getPeriodType()).isEqualTo(DEFAULT_PERIOD_TYPE);
        assertThat(testDirectoryItems.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDirectoryItems.getLikesReceived()).isEqualTo(DEFAULT_LIKES_RECEIVED);
        assertThat(testDirectoryItems.getLikesGiven()).isEqualTo(DEFAULT_LIKES_GIVEN);
        assertThat(testDirectoryItems.getTopicsEntered()).isEqualTo(DEFAULT_TOPICS_ENTERED);
        assertThat(testDirectoryItems.getTopicCount()).isEqualTo(DEFAULT_TOPIC_COUNT);
        assertThat(testDirectoryItems.getPostCount()).isEqualTo(DEFAULT_POST_COUNT);
        assertThat(testDirectoryItems.getDaysVisited()).isEqualTo(DEFAULT_DAYS_VISITED);
        assertThat(testDirectoryItems.getPostsRead()).isEqualTo(DEFAULT_POSTS_READ);
    }

    @Test
    @Transactional
    public void createDirectoryItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = directoryItemsRepository.findAll().size();

        // Create the DirectoryItems with an existing ID
        directoryItems.setId(1L);
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DirectoryItems in the database
        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPeriodTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setPeriodType(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setUserId(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikesReceivedIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setLikesReceived(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikesGivenIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setLikesGiven(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicsEnteredIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setTopicsEntered(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setTopicCount(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setPostCount(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDaysVisitedIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setDaysVisited(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostsReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = directoryItemsRepository.findAll().size();
        // set the field null
        directoryItems.setPostsRead(null);

        // Create the DirectoryItems, which fails.
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);


        restDirectoryItemsMockMvc.perform(post("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDirectoryItems() throws Exception {
        // Initialize the database
        directoryItemsRepository.saveAndFlush(directoryItems);

        // Get all the directoryItemsList
        restDirectoryItemsMockMvc.perform(get("/api/directory-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(directoryItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodType").value(hasItem(DEFAULT_PERIOD_TYPE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].likesReceived").value(hasItem(DEFAULT_LIKES_RECEIVED)))
            .andExpect(jsonPath("$.[*].likesGiven").value(hasItem(DEFAULT_LIKES_GIVEN)))
            .andExpect(jsonPath("$.[*].topicsEntered").value(hasItem(DEFAULT_TOPICS_ENTERED)))
            .andExpect(jsonPath("$.[*].topicCount").value(hasItem(DEFAULT_TOPIC_COUNT)))
            .andExpect(jsonPath("$.[*].postCount").value(hasItem(DEFAULT_POST_COUNT)))
            .andExpect(jsonPath("$.[*].daysVisited").value(hasItem(DEFAULT_DAYS_VISITED)))
            .andExpect(jsonPath("$.[*].postsRead").value(hasItem(DEFAULT_POSTS_READ)));
    }

    @Test
    @Transactional
    public void getDirectoryItems() throws Exception {
        // Initialize the database
        directoryItemsRepository.saveAndFlush(directoryItems);

        // Get the directoryItems
        restDirectoryItemsMockMvc.perform(get("/api/directory-items/{id}", directoryItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(directoryItems.getId().intValue()))
            .andExpect(jsonPath("$.periodType").value(DEFAULT_PERIOD_TYPE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.likesReceived").value(DEFAULT_LIKES_RECEIVED))
            .andExpect(jsonPath("$.likesGiven").value(DEFAULT_LIKES_GIVEN))
            .andExpect(jsonPath("$.topicsEntered").value(DEFAULT_TOPICS_ENTERED))
            .andExpect(jsonPath("$.topicCount").value(DEFAULT_TOPIC_COUNT))
            .andExpect(jsonPath("$.postCount").value(DEFAULT_POST_COUNT))
            .andExpect(jsonPath("$.daysVisited").value(DEFAULT_DAYS_VISITED))
            .andExpect(jsonPath("$.postsRead").value(DEFAULT_POSTS_READ));
    }
    @Test
    @Transactional
    public void getNonExistingDirectoryItems() throws Exception {
        // Get the directoryItems
        restDirectoryItemsMockMvc.perform(get("/api/directory-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDirectoryItems() throws Exception {
        // Initialize the database
        directoryItemsRepository.saveAndFlush(directoryItems);

        int databaseSizeBeforeUpdate = directoryItemsRepository.findAll().size();

        // Update the directoryItems
        DirectoryItems updatedDirectoryItems = directoryItemsRepository.findById(directoryItems.getId()).get();
        // Disconnect from session so that the updates on updatedDirectoryItems are not directly saved in db
        em.detach(updatedDirectoryItems);
        updatedDirectoryItems
            .periodType(UPDATED_PERIOD_TYPE)
            .userId(UPDATED_USER_ID)
            .likesReceived(UPDATED_LIKES_RECEIVED)
            .likesGiven(UPDATED_LIKES_GIVEN)
            .topicsEntered(UPDATED_TOPICS_ENTERED)
            .topicCount(UPDATED_TOPIC_COUNT)
            .postCount(UPDATED_POST_COUNT)
            .daysVisited(UPDATED_DAYS_VISITED)
            .postsRead(UPDATED_POSTS_READ);
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(updatedDirectoryItems);

        restDirectoryItemsMockMvc.perform(put("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isOk());

        // Validate the DirectoryItems in the database
        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeUpdate);
        DirectoryItems testDirectoryItems = directoryItemsList.get(directoryItemsList.size() - 1);
        assertThat(testDirectoryItems.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testDirectoryItems.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDirectoryItems.getLikesReceived()).isEqualTo(UPDATED_LIKES_RECEIVED);
        assertThat(testDirectoryItems.getLikesGiven()).isEqualTo(UPDATED_LIKES_GIVEN);
        assertThat(testDirectoryItems.getTopicsEntered()).isEqualTo(UPDATED_TOPICS_ENTERED);
        assertThat(testDirectoryItems.getTopicCount()).isEqualTo(UPDATED_TOPIC_COUNT);
        assertThat(testDirectoryItems.getPostCount()).isEqualTo(UPDATED_POST_COUNT);
        assertThat(testDirectoryItems.getDaysVisited()).isEqualTo(UPDATED_DAYS_VISITED);
        assertThat(testDirectoryItems.getPostsRead()).isEqualTo(UPDATED_POSTS_READ);
    }

    @Test
    @Transactional
    public void updateNonExistingDirectoryItems() throws Exception {
        int databaseSizeBeforeUpdate = directoryItemsRepository.findAll().size();

        // Create the DirectoryItems
        DirectoryItemsDTO directoryItemsDTO = directoryItemsMapper.toDto(directoryItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectoryItemsMockMvc.perform(put("/api/directory-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(directoryItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DirectoryItems in the database
        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDirectoryItems() throws Exception {
        // Initialize the database
        directoryItemsRepository.saveAndFlush(directoryItems);

        int databaseSizeBeforeDelete = directoryItemsRepository.findAll().size();

        // Delete the directoryItems
        restDirectoryItemsMockMvc.perform(delete("/api/directory-items/{id}", directoryItems.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DirectoryItems> directoryItemsList = directoryItemsRepository.findAll();
        assertThat(directoryItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
