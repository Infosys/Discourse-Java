/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ReviewableHistories;
import com.infy.repository.ReviewableHistoriesRepository;
import com.infy.service.ReviewableHistoriesService;
import com.infy.service.dto.ReviewableHistoriesDTO;
import com.infy.service.mapper.ReviewableHistoriesMapper;

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
 * Integration tests for the {@link ReviewableHistoriesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReviewableHistoriesResourceIT {

    private static final Long DEFAULT_REVIEWABLE_ID = 1L;
    private static final Long UPDATED_REVIEWABLE_ID = 2L;

    private static final Integer DEFAULT_REVIEWABLE_HISTORY_TYPE = 1;
    private static final Integer UPDATED_REVIEWABLE_HISTORY_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_EDITED = "AAAAAAAAAA";
    private static final String UPDATED_EDITED = "BBBBBBBBBB";

    @Autowired
    private ReviewableHistoriesRepository reviewableHistoriesRepository;

    @Autowired
    private ReviewableHistoriesMapper reviewableHistoriesMapper;

    @Autowired
    private ReviewableHistoriesService reviewableHistoriesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewableHistoriesMockMvc;

    private ReviewableHistories reviewableHistories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewableHistories createEntity(EntityManager em) {
        ReviewableHistories reviewableHistories = new ReviewableHistories()
            .reviewableId(DEFAULT_REVIEWABLE_ID)
            .reviewableHistoryType(DEFAULT_REVIEWABLE_HISTORY_TYPE)
            .status(DEFAULT_STATUS)
            .edited(DEFAULT_EDITED);
        return reviewableHistories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewableHistories createUpdatedEntity(EntityManager em) {
        ReviewableHistories reviewableHistories = new ReviewableHistories()
            .reviewableId(UPDATED_REVIEWABLE_ID)
            .reviewableHistoryType(UPDATED_REVIEWABLE_HISTORY_TYPE)
            .status(UPDATED_STATUS)
            .edited(UPDATED_EDITED);
        return reviewableHistories;
    }

    @BeforeEach
    public void initTest() {
        reviewableHistories = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewableHistories() throws Exception {
        int databaseSizeBeforeCreate = reviewableHistoriesRepository.findAll().size();
        // Create the ReviewableHistories
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(reviewableHistories);
        restReviewableHistoriesMockMvc.perform(post("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewableHistories in the database
        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewableHistories testReviewableHistories = reviewableHistoriesList.get(reviewableHistoriesList.size() - 1);
        assertThat(testReviewableHistories.getReviewableId()).isEqualTo(DEFAULT_REVIEWABLE_ID);
        assertThat(testReviewableHistories.getReviewableHistoryType()).isEqualTo(DEFAULT_REVIEWABLE_HISTORY_TYPE);
        assertThat(testReviewableHistories.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReviewableHistories.getEdited()).isEqualTo(DEFAULT_EDITED);
    }

    @Test
    @Transactional
    public void createReviewableHistoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewableHistoriesRepository.findAll().size();

        // Create the ReviewableHistories with an existing ID
        reviewableHistories.setId(1L);
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(reviewableHistories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewableHistoriesMockMvc.perform(post("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewableHistories in the database
        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReviewableIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableHistoriesRepository.findAll().size();
        // set the field null
        reviewableHistories.setReviewableId(null);

        // Create the ReviewableHistories, which fails.
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(reviewableHistories);


        restReviewableHistoriesMockMvc.perform(post("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewableHistoryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableHistoriesRepository.findAll().size();
        // set the field null
        reviewableHistories.setReviewableHistoryType(null);

        // Create the ReviewableHistories, which fails.
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(reviewableHistories);


        restReviewableHistoriesMockMvc.perform(post("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableHistoriesRepository.findAll().size();
        // set the field null
        reviewableHistories.setStatus(null);

        // Create the ReviewableHistories, which fails.
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(reviewableHistories);


        restReviewableHistoriesMockMvc.perform(post("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviewableHistories() throws Exception {
        // Initialize the database
        reviewableHistoriesRepository.saveAndFlush(reviewableHistories);

        // Get all the reviewableHistoriesList
        restReviewableHistoriesMockMvc.perform(get("/api/reviewable-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewableHistories.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewableId").value(hasItem(DEFAULT_REVIEWABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reviewableHistoryType").value(hasItem(DEFAULT_REVIEWABLE_HISTORY_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].edited").value(hasItem(DEFAULT_EDITED)));
    }

    @Test
    @Transactional
    public void getReviewableHistories() throws Exception {
        // Initialize the database
        reviewableHistoriesRepository.saveAndFlush(reviewableHistories);

        // Get the reviewableHistories
        restReviewableHistoriesMockMvc.perform(get("/api/reviewable-histories/{id}", reviewableHistories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reviewableHistories.getId().intValue()))
            .andExpect(jsonPath("$.reviewableId").value(DEFAULT_REVIEWABLE_ID.intValue()))
            .andExpect(jsonPath("$.reviewableHistoryType").value(DEFAULT_REVIEWABLE_HISTORY_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.edited").value(DEFAULT_EDITED));
    }
    @Test
    @Transactional
    public void getNonExistingReviewableHistories() throws Exception {
        // Get the reviewableHistories
        restReviewableHistoriesMockMvc.perform(get("/api/reviewable-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewableHistories() throws Exception {
        // Initialize the database
        reviewableHistoriesRepository.saveAndFlush(reviewableHistories);

        int databaseSizeBeforeUpdate = reviewableHistoriesRepository.findAll().size();

        // Update the reviewableHistories
        ReviewableHistories updatedReviewableHistories = reviewableHistoriesRepository.findById(reviewableHistories.getId()).get();
        // Disconnect from session so that the updates on updatedReviewableHistories are not directly saved in db
        em.detach(updatedReviewableHistories);
        updatedReviewableHistories
            .reviewableId(UPDATED_REVIEWABLE_ID)
            .reviewableHistoryType(UPDATED_REVIEWABLE_HISTORY_TYPE)
            .status(UPDATED_STATUS)
            .edited(UPDATED_EDITED);
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(updatedReviewableHistories);

        restReviewableHistoriesMockMvc.perform(put("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isOk());

        // Validate the ReviewableHistories in the database
        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeUpdate);
        ReviewableHistories testReviewableHistories = reviewableHistoriesList.get(reviewableHistoriesList.size() - 1);
        assertThat(testReviewableHistories.getReviewableId()).isEqualTo(UPDATED_REVIEWABLE_ID);
        assertThat(testReviewableHistories.getReviewableHistoryType()).isEqualTo(UPDATED_REVIEWABLE_HISTORY_TYPE);
        assertThat(testReviewableHistories.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReviewableHistories.getEdited()).isEqualTo(UPDATED_EDITED);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewableHistories() throws Exception {
        int databaseSizeBeforeUpdate = reviewableHistoriesRepository.findAll().size();

        // Create the ReviewableHistories
        ReviewableHistoriesDTO reviewableHistoriesDTO = reviewableHistoriesMapper.toDto(reviewableHistories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewableHistoriesMockMvc.perform(put("/api/reviewable-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewableHistories in the database
        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviewableHistories() throws Exception {
        // Initialize the database
        reviewableHistoriesRepository.saveAndFlush(reviewableHistories);

        int databaseSizeBeforeDelete = reviewableHistoriesRepository.findAll().size();

        // Delete the reviewableHistories
        restReviewableHistoriesMockMvc.perform(delete("/api/reviewable-histories/{id}", reviewableHistories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReviewableHistories> reviewableHistoriesList = reviewableHistoriesRepository.findAll();
        assertThat(reviewableHistoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
