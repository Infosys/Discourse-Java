/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ReviewableScores;
import com.infy.repository.ReviewableScoresRepository;
import com.infy.service.ReviewableScoresService;
import com.infy.service.dto.ReviewableScoresDTO;
import com.infy.service.mapper.ReviewableScoresMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReviewableScoresResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReviewableScoresResourceIT {

    private static final Long DEFAULT_REVIEWABLE_ID = 1L;
    private static final Long UPDATED_REVIEWABLE_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_REVIEWABLE_SCORE_TYPE = 1;
    private static final Integer UPDATED_REVIEWABLE_SCORE_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final Double DEFAULT_TAKE_ACTION_BONUS = 1D;
    private static final Double UPDATED_TAKE_ACTION_BONUS = 2D;

    private static final String DEFAULT_REVIEWED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWED_BY_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_REVIEWED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVIEWED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_META_TOPIC_ID = 1L;
    private static final Long UPDATED_META_TOPIC_ID = 2L;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Double DEFAULT_USER_ACCURACY_BONUS = 1D;
    private static final Double UPDATED_USER_ACCURACY_BONUS = 2D;

    @Autowired
    private ReviewableScoresRepository reviewableScoresRepository;

    @Autowired
    private ReviewableScoresMapper reviewableScoresMapper;

    @Autowired
    private ReviewableScoresService reviewableScoresService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewableScoresMockMvc;

    private ReviewableScores reviewableScores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewableScores createEntity(EntityManager em) {
        ReviewableScores reviewableScores = new ReviewableScores()
            .reviewableId(DEFAULT_REVIEWABLE_ID)
            .userId(DEFAULT_USER_ID)
            .reviewableScoreType(DEFAULT_REVIEWABLE_SCORE_TYPE)
            .status(DEFAULT_STATUS)
            .score(DEFAULT_SCORE)
            .takeActionBonus(DEFAULT_TAKE_ACTION_BONUS)
            .reviewedById(DEFAULT_REVIEWED_BY_ID)
            .reviewedAt(DEFAULT_REVIEWED_AT)
            .metaTopicId(DEFAULT_META_TOPIC_ID)
            .reason(DEFAULT_REASON)
            .userAccuracyBonus(DEFAULT_USER_ACCURACY_BONUS);
        return reviewableScores;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewableScores createUpdatedEntity(EntityManager em) {
        ReviewableScores reviewableScores = new ReviewableScores()
            .reviewableId(UPDATED_REVIEWABLE_ID)
            .userId(UPDATED_USER_ID)
            .reviewableScoreType(UPDATED_REVIEWABLE_SCORE_TYPE)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .takeActionBonus(UPDATED_TAKE_ACTION_BONUS)
            .reviewedById(UPDATED_REVIEWED_BY_ID)
            .reviewedAt(UPDATED_REVIEWED_AT)
            .metaTopicId(UPDATED_META_TOPIC_ID)
            .reason(UPDATED_REASON)
            .userAccuracyBonus(UPDATED_USER_ACCURACY_BONUS);
        return reviewableScores;
    }

    @BeforeEach
    public void initTest() {
        reviewableScores = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewableScores() throws Exception {
        int databaseSizeBeforeCreate = reviewableScoresRepository.findAll().size();
        // Create the ReviewableScores
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);
        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewableScores in the database
        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewableScores testReviewableScores = reviewableScoresList.get(reviewableScoresList.size() - 1);
        assertThat(testReviewableScores.getReviewableId()).isEqualTo(DEFAULT_REVIEWABLE_ID);
        assertThat(testReviewableScores.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testReviewableScores.getReviewableScoreType()).isEqualTo(DEFAULT_REVIEWABLE_SCORE_TYPE);
        assertThat(testReviewableScores.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReviewableScores.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testReviewableScores.getTakeActionBonus()).isEqualTo(DEFAULT_TAKE_ACTION_BONUS);
        assertThat(testReviewableScores.getReviewedById()).isEqualTo(DEFAULT_REVIEWED_BY_ID);
        assertThat(testReviewableScores.getReviewedAt()).isEqualTo(DEFAULT_REVIEWED_AT);
        assertThat(testReviewableScores.getMetaTopicId()).isEqualTo(DEFAULT_META_TOPIC_ID);
        assertThat(testReviewableScores.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testReviewableScores.getUserAccuracyBonus()).isEqualTo(DEFAULT_USER_ACCURACY_BONUS);
    }

    @Test
    @Transactional
    public void createReviewableScoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewableScoresRepository.findAll().size();

        // Create the ReviewableScores with an existing ID
        reviewableScores.setId(1L);
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewableScores in the database
        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReviewableIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setReviewableId(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setUserId(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewableScoreTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setReviewableScoreType(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setStatus(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setScore(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTakeActionBonusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setTakeActionBonus(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserAccuracyBonusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewableScoresRepository.findAll().size();
        // set the field null
        reviewableScores.setUserAccuracyBonus(null);

        // Create the ReviewableScores, which fails.
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);


        restReviewableScoresMockMvc.perform(post("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviewableScores() throws Exception {
        // Initialize the database
        reviewableScoresRepository.saveAndFlush(reviewableScores);

        // Get all the reviewableScoresList
        restReviewableScoresMockMvc.perform(get("/api/reviewable-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewableScores.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewableId").value(hasItem(DEFAULT_REVIEWABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].reviewableScoreType").value(hasItem(DEFAULT_REVIEWABLE_SCORE_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].takeActionBonus").value(hasItem(DEFAULT_TAKE_ACTION_BONUS.doubleValue())))
            .andExpect(jsonPath("$.[*].reviewedById").value(hasItem(DEFAULT_REVIEWED_BY_ID)))
            .andExpect(jsonPath("$.[*].reviewedAt").value(hasItem(DEFAULT_REVIEWED_AT.toString())))
            .andExpect(jsonPath("$.[*].metaTopicId").value(hasItem(DEFAULT_META_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].userAccuracyBonus").value(hasItem(DEFAULT_USER_ACCURACY_BONUS.doubleValue())));
    }

    @Test
    @Transactional
    public void getReviewableScores() throws Exception {
        // Initialize the database
        reviewableScoresRepository.saveAndFlush(reviewableScores);

        // Get the reviewableScores
        restReviewableScoresMockMvc.perform(get("/api/reviewable-scores/{id}", reviewableScores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reviewableScores.getId().intValue()))
            .andExpect(jsonPath("$.reviewableId").value(DEFAULT_REVIEWABLE_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.reviewableScoreType").value(DEFAULT_REVIEWABLE_SCORE_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.takeActionBonus").value(DEFAULT_TAKE_ACTION_BONUS.doubleValue()))
            .andExpect(jsonPath("$.reviewedById").value(DEFAULT_REVIEWED_BY_ID))
            .andExpect(jsonPath("$.reviewedAt").value(DEFAULT_REVIEWED_AT.toString()))
            .andExpect(jsonPath("$.metaTopicId").value(DEFAULT_META_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.userAccuracyBonus").value(DEFAULT_USER_ACCURACY_BONUS.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReviewableScores() throws Exception {
        // Get the reviewableScores
        restReviewableScoresMockMvc.perform(get("/api/reviewable-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewableScores() throws Exception {
        // Initialize the database
        reviewableScoresRepository.saveAndFlush(reviewableScores);

        int databaseSizeBeforeUpdate = reviewableScoresRepository.findAll().size();

        // Update the reviewableScores
        ReviewableScores updatedReviewableScores = reviewableScoresRepository.findById(reviewableScores.getId()).get();
        // Disconnect from session so that the updates on updatedReviewableScores are not directly saved in db
        em.detach(updatedReviewableScores);
        updatedReviewableScores
            .reviewableId(UPDATED_REVIEWABLE_ID)
            .userId(UPDATED_USER_ID)
            .reviewableScoreType(UPDATED_REVIEWABLE_SCORE_TYPE)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .takeActionBonus(UPDATED_TAKE_ACTION_BONUS)
            .reviewedById(UPDATED_REVIEWED_BY_ID)
            .reviewedAt(UPDATED_REVIEWED_AT)
            .metaTopicId(UPDATED_META_TOPIC_ID)
            .reason(UPDATED_REASON)
            .userAccuracyBonus(UPDATED_USER_ACCURACY_BONUS);
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(updatedReviewableScores);

        restReviewableScoresMockMvc.perform(put("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isOk());

        // Validate the ReviewableScores in the database
        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeUpdate);
        ReviewableScores testReviewableScores = reviewableScoresList.get(reviewableScoresList.size() - 1);
        assertThat(testReviewableScores.getReviewableId()).isEqualTo(UPDATED_REVIEWABLE_ID);
        assertThat(testReviewableScores.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testReviewableScores.getReviewableScoreType()).isEqualTo(UPDATED_REVIEWABLE_SCORE_TYPE);
        assertThat(testReviewableScores.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReviewableScores.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testReviewableScores.getTakeActionBonus()).isEqualTo(UPDATED_TAKE_ACTION_BONUS);
        assertThat(testReviewableScores.getReviewedById()).isEqualTo(UPDATED_REVIEWED_BY_ID);
        assertThat(testReviewableScores.getReviewedAt()).isEqualTo(UPDATED_REVIEWED_AT);
        assertThat(testReviewableScores.getMetaTopicId()).isEqualTo(UPDATED_META_TOPIC_ID);
        assertThat(testReviewableScores.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testReviewableScores.getUserAccuracyBonus()).isEqualTo(UPDATED_USER_ACCURACY_BONUS);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewableScores() throws Exception {
        int databaseSizeBeforeUpdate = reviewableScoresRepository.findAll().size();

        // Create the ReviewableScores
        ReviewableScoresDTO reviewableScoresDTO = reviewableScoresMapper.toDto(reviewableScores);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewableScoresMockMvc.perform(put("/api/reviewable-scores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewableScoresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewableScores in the database
        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviewableScores() throws Exception {
        // Initialize the database
        reviewableScoresRepository.saveAndFlush(reviewableScores);

        int databaseSizeBeforeDelete = reviewableScoresRepository.findAll().size();

        // Delete the reviewableScores
        restReviewableScoresMockMvc.perform(delete("/api/reviewable-scores/{id}", reviewableScores.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReviewableScores> reviewableScoresList = reviewableScoresRepository.findAll();
        assertThat(reviewableScoresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
