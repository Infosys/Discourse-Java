/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Reviewables;
import com.infy.repository.ReviewablesRepository;
import com.infy.service.ReviewablesService;
import com.infy.service.dto.ReviewablesDTO;
import com.infy.service.mapper.ReviewablesMapper;

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
 * Integration tests for the {@link ReviewablesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReviewablesResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Boolean DEFAULT_REVIEWABLE_BY_MODERATOR = false;
    private static final Boolean UPDATED_REVIEWABLE_BY_MODERATOR = true;

    private static final Long DEFAULT_REVIEWABLE_BY_GROUP_ID = 1L;
    private static final Long UPDATED_REVIEWABLE_BY_GROUP_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final Boolean DEFAULT_POTENTIAL_SPAM = false;
    private static final Boolean UPDATED_POTENTIAL_SPAM = true;

    private static final Long DEFAULT_TARGET_ID = 1L;
    private static final Long UPDATED_TARGET_ID = 2L;

    private static final String DEFAULT_TARGET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_CREATED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_CREATED_BY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Instant DEFAULT_LATEST_SCORE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LATEST_SCORE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FORCE_REVIEW = false;
    private static final Boolean UPDATED_FORCE_REVIEW = true;

    private static final String DEFAULT_REJECT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECT_REASON = "BBBBBBBBBB";

    @Autowired
    private ReviewablesRepository reviewablesRepository;

    @Autowired
    private ReviewablesMapper reviewablesMapper;

    @Autowired
    private ReviewablesService reviewablesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewablesMockMvc;

    private Reviewables reviewables;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviewables createEntity(EntityManager em) {
        Reviewables reviewables = new Reviewables()
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .reviewableByModerator(DEFAULT_REVIEWABLE_BY_MODERATOR)
            .reviewableByGroupId(DEFAULT_REVIEWABLE_BY_GROUP_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .score(DEFAULT_SCORE)
            .potentialSpam(DEFAULT_POTENTIAL_SPAM)
            .targetId(DEFAULT_TARGET_ID)
            .targetType(DEFAULT_TARGET_TYPE)
            .targetCreatedById(DEFAULT_TARGET_CREATED_BY_ID)
            .payload(DEFAULT_PAYLOAD)
            .version(DEFAULT_VERSION)
            .latestScore(DEFAULT_LATEST_SCORE)
            .forceReview(DEFAULT_FORCE_REVIEW)
            .rejectReason(DEFAULT_REJECT_REASON);
        return reviewables;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviewables createUpdatedEntity(EntityManager em) {
        Reviewables reviewables = new Reviewables()
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .reviewableByModerator(UPDATED_REVIEWABLE_BY_MODERATOR)
            .reviewableByGroupId(UPDATED_REVIEWABLE_BY_GROUP_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .topicId(UPDATED_TOPIC_ID)
            .score(UPDATED_SCORE)
            .potentialSpam(UPDATED_POTENTIAL_SPAM)
            .targetId(UPDATED_TARGET_ID)
            .targetType(UPDATED_TARGET_TYPE)
            .targetCreatedById(UPDATED_TARGET_CREATED_BY_ID)
            .payload(UPDATED_PAYLOAD)
            .version(UPDATED_VERSION)
            .latestScore(UPDATED_LATEST_SCORE)
            .forceReview(UPDATED_FORCE_REVIEW)
            .rejectReason(UPDATED_REJECT_REASON);
        return reviewables;
    }

    @BeforeEach
    public void initTest() {
        reviewables = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewables() throws Exception {
        int databaseSizeBeforeCreate = reviewablesRepository.findAll().size();
        // Create the Reviewables
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);
        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isCreated());

        // Validate the Reviewables in the database
        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeCreate + 1);
        Reviewables testReviewables = reviewablesList.get(reviewablesList.size() - 1);
        assertThat(testReviewables.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReviewables.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReviewables.isReviewableByModerator()).isEqualTo(DEFAULT_REVIEWABLE_BY_MODERATOR);
        assertThat(testReviewables.getReviewableByGroupId()).isEqualTo(DEFAULT_REVIEWABLE_BY_GROUP_ID);
        assertThat(testReviewables.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testReviewables.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testReviewables.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testReviewables.isPotentialSpam()).isEqualTo(DEFAULT_POTENTIAL_SPAM);
        assertThat(testReviewables.getTargetId()).isEqualTo(DEFAULT_TARGET_ID);
        assertThat(testReviewables.getTargetType()).isEqualTo(DEFAULT_TARGET_TYPE);
        assertThat(testReviewables.getTargetCreatedById()).isEqualTo(DEFAULT_TARGET_CREATED_BY_ID);
        assertThat(testReviewables.getPayload()).isEqualTo(DEFAULT_PAYLOAD);
        assertThat(testReviewables.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testReviewables.getLatestScore()).isEqualTo(DEFAULT_LATEST_SCORE);
        assertThat(testReviewables.isForceReview()).isEqualTo(DEFAULT_FORCE_REVIEW);
        assertThat(testReviewables.getRejectReason()).isEqualTo(DEFAULT_REJECT_REASON);
    }

    @Test
    @Transactional
    public void createReviewablesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewablesRepository.findAll().size();

        // Create the Reviewables with an existing ID
        reviewables.setId(1L);
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reviewables in the database
        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setType(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setStatus(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewableByModeratorIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setReviewableByModerator(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setScore(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPotentialSpamIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setPotentialSpam(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setVersion(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForceReviewIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewablesRepository.findAll().size();
        // set the field null
        reviewables.setForceReview(null);

        // Create the Reviewables, which fails.
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);


        restReviewablesMockMvc.perform(post("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviewables() throws Exception {
        // Initialize the database
        reviewablesRepository.saveAndFlush(reviewables);

        // Get all the reviewablesList
        restReviewablesMockMvc.perform(get("/api/reviewables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewables.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].reviewableByModerator").value(hasItem(DEFAULT_REVIEWABLE_BY_MODERATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].reviewableByGroupId").value(hasItem(DEFAULT_REVIEWABLE_BY_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].potentialSpam").value(hasItem(DEFAULT_POTENTIAL_SPAM.booleanValue())))
            .andExpect(jsonPath("$.[*].targetId").value(hasItem(DEFAULT_TARGET_ID.intValue())))
            .andExpect(jsonPath("$.[*].targetType").value(hasItem(DEFAULT_TARGET_TYPE)))
            .andExpect(jsonPath("$.[*].targetCreatedById").value(hasItem(DEFAULT_TARGET_CREATED_BY_ID)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].latestScore").value(hasItem(DEFAULT_LATEST_SCORE.toString())))
            .andExpect(jsonPath("$.[*].forceReview").value(hasItem(DEFAULT_FORCE_REVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].rejectReason").value(hasItem(DEFAULT_REJECT_REASON)));
    }

    @Test
    @Transactional
    public void getReviewables() throws Exception {
        // Initialize the database
        reviewablesRepository.saveAndFlush(reviewables);

        // Get the reviewables
        restReviewablesMockMvc.perform(get("/api/reviewables/{id}", reviewables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reviewables.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.reviewableByModerator").value(DEFAULT_REVIEWABLE_BY_MODERATOR.booleanValue()))
            .andExpect(jsonPath("$.reviewableByGroupId").value(DEFAULT_REVIEWABLE_BY_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.potentialSpam").value(DEFAULT_POTENTIAL_SPAM.booleanValue()))
            .andExpect(jsonPath("$.targetId").value(DEFAULT_TARGET_ID.intValue()))
            .andExpect(jsonPath("$.targetType").value(DEFAULT_TARGET_TYPE))
            .andExpect(jsonPath("$.targetCreatedById").value(DEFAULT_TARGET_CREATED_BY_ID))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.latestScore").value(DEFAULT_LATEST_SCORE.toString()))
            .andExpect(jsonPath("$.forceReview").value(DEFAULT_FORCE_REVIEW.booleanValue()))
            .andExpect(jsonPath("$.rejectReason").value(DEFAULT_REJECT_REASON));
    }
    @Test
    @Transactional
    public void getNonExistingReviewables() throws Exception {
        // Get the reviewables
        restReviewablesMockMvc.perform(get("/api/reviewables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewables() throws Exception {
        // Initialize the database
        reviewablesRepository.saveAndFlush(reviewables);

        int databaseSizeBeforeUpdate = reviewablesRepository.findAll().size();

        // Update the reviewables
        Reviewables updatedReviewables = reviewablesRepository.findById(reviewables.getId()).get();
        // Disconnect from session so that the updates on updatedReviewables are not directly saved in db
        em.detach(updatedReviewables);
        updatedReviewables
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .reviewableByModerator(UPDATED_REVIEWABLE_BY_MODERATOR)
            .reviewableByGroupId(UPDATED_REVIEWABLE_BY_GROUP_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .topicId(UPDATED_TOPIC_ID)
            .score(UPDATED_SCORE)
            .potentialSpam(UPDATED_POTENTIAL_SPAM)
            .targetId(UPDATED_TARGET_ID)
            .targetType(UPDATED_TARGET_TYPE)
            .targetCreatedById(UPDATED_TARGET_CREATED_BY_ID)
            .payload(UPDATED_PAYLOAD)
            .version(UPDATED_VERSION)
            .latestScore(UPDATED_LATEST_SCORE)
            .forceReview(UPDATED_FORCE_REVIEW)
            .rejectReason(UPDATED_REJECT_REASON);
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(updatedReviewables);

        restReviewablesMockMvc.perform(put("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isOk());

        // Validate the Reviewables in the database
        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeUpdate);
        Reviewables testReviewables = reviewablesList.get(reviewablesList.size() - 1);
        assertThat(testReviewables.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReviewables.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReviewables.isReviewableByModerator()).isEqualTo(UPDATED_REVIEWABLE_BY_MODERATOR);
        assertThat(testReviewables.getReviewableByGroupId()).isEqualTo(UPDATED_REVIEWABLE_BY_GROUP_ID);
        assertThat(testReviewables.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testReviewables.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testReviewables.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testReviewables.isPotentialSpam()).isEqualTo(UPDATED_POTENTIAL_SPAM);
        assertThat(testReviewables.getTargetId()).isEqualTo(UPDATED_TARGET_ID);
        assertThat(testReviewables.getTargetType()).isEqualTo(UPDATED_TARGET_TYPE);
        assertThat(testReviewables.getTargetCreatedById()).isEqualTo(UPDATED_TARGET_CREATED_BY_ID);
        assertThat(testReviewables.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testReviewables.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testReviewables.getLatestScore()).isEqualTo(UPDATED_LATEST_SCORE);
        assertThat(testReviewables.isForceReview()).isEqualTo(UPDATED_FORCE_REVIEW);
        assertThat(testReviewables.getRejectReason()).isEqualTo(UPDATED_REJECT_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewables() throws Exception {
        int databaseSizeBeforeUpdate = reviewablesRepository.findAll().size();

        // Create the Reviewables
        ReviewablesDTO reviewablesDTO = reviewablesMapper.toDto(reviewables);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewablesMockMvc.perform(put("/api/reviewables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviewablesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reviewables in the database
        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviewables() throws Exception {
        // Initialize the database
        reviewablesRepository.saveAndFlush(reviewables);

        int databaseSizeBeforeDelete = reviewablesRepository.findAll().size();

        // Delete the reviewables
        restReviewablesMockMvc.perform(delete("/api/reviewables/{id}", reviewables.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reviewables> reviewablesList = reviewablesRepository.findAll();
        assertThat(reviewablesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
