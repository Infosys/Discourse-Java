/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ReviewableClaimedTopics;
import com.infy.repository.ReviewableClaimedTopicsRepository;
import com.infy.service.ReviewableClaimedTopicsService;
import com.infy.service.dto.ReviewableClaimedTopicsDTO;
import com.infy.service.mapper.ReviewableClaimedTopicsMapper;

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
 * Integration tests for the {@link ReviewableClaimedTopicsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReviewableClaimedTopicsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private ReviewableClaimedTopicsRepository reviewableClaimedTopicsRepository;

    @Autowired
    private ReviewableClaimedTopicsMapper reviewableClaimedTopicsMapper;

    @Autowired
    private ReviewableClaimedTopicsService reviewableClaimedTopicsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewableClaimedTopicsMockMvc;

    private ReviewableClaimedTopics reviewableClaimedTopics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewableClaimedTopics createEntity(EntityManager em) {
        ReviewableClaimedTopics reviewableClaimedTopics = new ReviewableClaimedTopics()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return reviewableClaimedTopics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewableClaimedTopics createUpdatedEntity(EntityManager em) {
        ReviewableClaimedTopics reviewableClaimedTopics = new ReviewableClaimedTopics()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        return reviewableClaimedTopics;
    }

    @BeforeEach
    public void initTest() {
        reviewableClaimedTopics = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewableClaimedTopics() throws Exception {
        int databaseSizeBeforeCreate = reviewableClaimedTopicsRepository.findAll().size();
        // Create the ReviewableClaimedTopics
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO = reviewableClaimedTopicsMapper.toDto(reviewableClaimedTopics);
        restReviewableClaimedTopicsMockMvc.perform(post("/api/reviewable-claimed-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableClaimedTopicsDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewableClaimedTopics in the database
        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewableClaimedTopics testReviewableClaimedTopics = reviewableClaimedTopicsList.get(reviewableClaimedTopicsList.size() - 1);
        assertThat(testReviewableClaimedTopics.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testReviewableClaimedTopics.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createReviewableClaimedTopicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewableClaimedTopicsRepository.findAll().size();

        // Create the ReviewableClaimedTopics with an existing ID
        reviewableClaimedTopics.setId(1L);
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO = reviewableClaimedTopicsMapper.toDto(reviewableClaimedTopics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewableClaimedTopicsMockMvc.perform(post("/api/reviewable-claimed-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableClaimedTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewableClaimedTopics in the database
        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableClaimedTopicsRepository.findAll().size();
        // set the field null
        reviewableClaimedTopics.setUserId(null);

        // Create the ReviewableClaimedTopics, which fails.
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO = reviewableClaimedTopicsMapper.toDto(reviewableClaimedTopics);


        restReviewableClaimedTopicsMockMvc.perform(post("/api/reviewable-claimed-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableClaimedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableClaimedTopicsRepository.findAll().size();
        // set the field null
        reviewableClaimedTopics.setTopicId(null);

        // Create the ReviewableClaimedTopics, which fails.
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO = reviewableClaimedTopicsMapper.toDto(reviewableClaimedTopics);


        restReviewableClaimedTopicsMockMvc.perform(post("/api/reviewable-claimed-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableClaimedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviewableClaimedTopics() throws Exception {
        // Initialize the database
        reviewableClaimedTopicsRepository.saveAndFlush(reviewableClaimedTopics);

        // Get all the reviewableClaimedTopicsList
        restReviewableClaimedTopicsMockMvc.perform(get("/api/reviewable-claimed-topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewableClaimedTopics.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getReviewableClaimedTopics() throws Exception {
        // Initialize the database
        reviewableClaimedTopicsRepository.saveAndFlush(reviewableClaimedTopics);

        // Get the reviewableClaimedTopics
        restReviewableClaimedTopicsMockMvc.perform(get("/api/reviewable-claimed-topics/{id}", reviewableClaimedTopics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reviewableClaimedTopics.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReviewableClaimedTopics() throws Exception {
        // Get the reviewableClaimedTopics
        restReviewableClaimedTopicsMockMvc.perform(get("/api/reviewable-claimed-topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewableClaimedTopics() throws Exception {
        // Initialize the database
        reviewableClaimedTopicsRepository.saveAndFlush(reviewableClaimedTopics);

        int databaseSizeBeforeUpdate = reviewableClaimedTopicsRepository.findAll().size();

        // Update the reviewableClaimedTopics
        ReviewableClaimedTopics updatedReviewableClaimedTopics = reviewableClaimedTopicsRepository.findById(reviewableClaimedTopics.getId()).get();
        // Disconnect from session so that the updates on updatedReviewableClaimedTopics are not directly saved in db
        em.detach(updatedReviewableClaimedTopics);
        updatedReviewableClaimedTopics
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO = reviewableClaimedTopicsMapper.toDto(updatedReviewableClaimedTopics);

        restReviewableClaimedTopicsMockMvc.perform(put("/api/reviewable-claimed-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableClaimedTopicsDTO)))
            .andExpect(status().isOk());

        // Validate the ReviewableClaimedTopics in the database
        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeUpdate);
        ReviewableClaimedTopics testReviewableClaimedTopics = reviewableClaimedTopicsList.get(reviewableClaimedTopicsList.size() - 1);
        assertThat(testReviewableClaimedTopics.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testReviewableClaimedTopics.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewableClaimedTopics() throws Exception {
        int databaseSizeBeforeUpdate = reviewableClaimedTopicsRepository.findAll().size();

        // Create the ReviewableClaimedTopics
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO = reviewableClaimedTopicsMapper.toDto(reviewableClaimedTopics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewableClaimedTopicsMockMvc.perform(put("/api/reviewable-claimed-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableClaimedTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewableClaimedTopics in the database
        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviewableClaimedTopics() throws Exception {
        // Initialize the database
        reviewableClaimedTopicsRepository.saveAndFlush(reviewableClaimedTopics);

        int databaseSizeBeforeDelete = reviewableClaimedTopicsRepository.findAll().size();

        // Delete the reviewableClaimedTopics
        restReviewableClaimedTopicsMockMvc.perform(delete("/api/reviewable-claimed-topics/{id}", reviewableClaimedTopics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReviewableClaimedTopics> reviewableClaimedTopicsList = reviewableClaimedTopicsRepository.findAll();
        assertThat(reviewableClaimedTopicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
