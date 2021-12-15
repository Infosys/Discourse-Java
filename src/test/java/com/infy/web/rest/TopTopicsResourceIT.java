/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopTopics;
import com.infy.repository.TopTopicsRepository;
import com.infy.service.TopTopicsService;
import com.infy.service.dto.TopTopicsDTO;
import com.infy.service.mapper.TopTopicsMapper;

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
 * Integration tests for the {@link TopTopicsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopTopicsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Integer DEFAULT_YEARLY_POSTS_COUNT = 1;
    private static final Integer UPDATED_YEARLY_POSTS_COUNT = 2;

    private static final Integer DEFAULT_YEARLY_VIEWS_COUNT = 1;
    private static final Integer UPDATED_YEARLY_VIEWS_COUNT = 2;

    private static final Integer DEFAULT_YEARLY_LIKES_COUNT = 1;
    private static final Integer UPDATED_YEARLY_LIKES_COUNT = 2;

    private static final Integer DEFAULT_MONTHLY_POSTS_COUNT = 1;
    private static final Integer UPDATED_MONTHLY_POSTS_COUNT = 2;

    private static final Integer DEFAULT_MONTHLY_VIEWS_COUNT = 1;
    private static final Integer UPDATED_MONTHLY_VIEWS_COUNT = 2;

    private static final Integer DEFAULT_MONTHLY_LIKES_COUNT = 1;
    private static final Integer UPDATED_MONTHLY_LIKES_COUNT = 2;

    private static final Integer DEFAULT_WEEKLY_POSTS_COUNT = 1;
    private static final Integer UPDATED_WEEKLY_POSTS_COUNT = 2;

    private static final Integer DEFAULT_WEEKLY_VIEWS_COUNT = 1;
    private static final Integer UPDATED_WEEKLY_VIEWS_COUNT = 2;

    private static final Integer DEFAULT_WEEKLY_LIKES_COUNT = 1;
    private static final Integer UPDATED_WEEKLY_LIKES_COUNT = 2;

    private static final Integer DEFAULT_DAILY_POSTS_COUNT = 1;
    private static final Integer UPDATED_DAILY_POSTS_COUNT = 2;

    private static final Integer DEFAULT_DAILY_VIEWS_COUNT = 1;
    private static final Integer UPDATED_DAILY_VIEWS_COUNT = 2;

    private static final Integer DEFAULT_DAILY_LIKES_COUNT = 1;
    private static final Integer UPDATED_DAILY_LIKES_COUNT = 2;

    private static final Double DEFAULT_DAILY_SCORE = 1D;
    private static final Double UPDATED_DAILY_SCORE = 2D;

    private static final Double DEFAULT_WEEKLY_SCORE = 1D;
    private static final Double UPDATED_WEEKLY_SCORE = 2D;

    private static final Double DEFAULT_MONTHLY_SCORE = 1D;
    private static final Double UPDATED_MONTHLY_SCORE = 2D;

    private static final Double DEFAULT_YEARLY_SCORE = 1D;
    private static final Double UPDATED_YEARLY_SCORE = 2D;

    private static final Double DEFAULT_ALL_SCORE = 1D;
    private static final Double UPDATED_ALL_SCORE = 2D;

    private static final Integer DEFAULT_DAILY_OP_LIKES_COUNT = 1;
    private static final Integer UPDATED_DAILY_OP_LIKES_COUNT = 2;

    private static final Integer DEFAULT_WEEKLY_OP_LIKES_COUNT = 1;
    private static final Integer UPDATED_WEEKLY_OP_LIKES_COUNT = 2;

    private static final Integer DEFAULT_MONTHLY_OP_LIKES_COUNT = 1;
    private static final Integer UPDATED_MONTHLY_OP_LIKES_COUNT = 2;

    private static final Integer DEFAULT_YEARLY_OP_LIKES_COUNT = 1;
    private static final Integer UPDATED_YEARLY_OP_LIKES_COUNT = 2;

    private static final Integer DEFAULT_QUARTERLY_POSTS_COUNT = 1;
    private static final Integer UPDATED_QUARTERLY_POSTS_COUNT = 2;

    private static final Integer DEFAULT_QUARTERLY_VIEWS_COUNT = 1;
    private static final Integer UPDATED_QUARTERLY_VIEWS_COUNT = 2;

    private static final Integer DEFAULT_QUARTERLY_LIKES_COUNT = 1;
    private static final Integer UPDATED_QUARTERLY_LIKES_COUNT = 2;

    private static final Double DEFAULT_QUARTERLY_SCORE = 1D;
    private static final Double UPDATED_QUARTERLY_SCORE = 2D;

    private static final Integer DEFAULT_QUARTERLY_OP_LIKES_COUNT = 1;
    private static final Integer UPDATED_QUARTERLY_OP_LIKES_COUNT = 2;

    @Autowired
    private TopTopicsRepository topTopicsRepository;

    @Autowired
    private TopTopicsMapper topTopicsMapper;

    @Autowired
    private TopTopicsService topTopicsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopTopicsMockMvc;

    private TopTopics topTopics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopTopics createEntity(EntityManager em) {
        TopTopics topTopics = new TopTopics()
            .topicId(DEFAULT_TOPIC_ID)
            .yearlyPostsCount(DEFAULT_YEARLY_POSTS_COUNT)
            .yearlyViewsCount(DEFAULT_YEARLY_VIEWS_COUNT)
            .yearlyLikesCount(DEFAULT_YEARLY_LIKES_COUNT)
            .monthlyPostsCount(DEFAULT_MONTHLY_POSTS_COUNT)
            .monthlyViewsCount(DEFAULT_MONTHLY_VIEWS_COUNT)
            .monthlyLikesCount(DEFAULT_MONTHLY_LIKES_COUNT)
            .weeklyPostsCount(DEFAULT_WEEKLY_POSTS_COUNT)
            .weeklyViewsCount(DEFAULT_WEEKLY_VIEWS_COUNT)
            .weeklyLikesCount(DEFAULT_WEEKLY_LIKES_COUNT)
            .dailyPostsCount(DEFAULT_DAILY_POSTS_COUNT)
            .dailyViewsCount(DEFAULT_DAILY_VIEWS_COUNT)
            .dailyLikesCount(DEFAULT_DAILY_LIKES_COUNT)
            .dailyScore(DEFAULT_DAILY_SCORE)
            .weeklyScore(DEFAULT_WEEKLY_SCORE)
            .monthlyScore(DEFAULT_MONTHLY_SCORE)
            .yearlyScore(DEFAULT_YEARLY_SCORE)
            .allScore(DEFAULT_ALL_SCORE)
            .dailyOpLikesCount(DEFAULT_DAILY_OP_LIKES_COUNT)
            .weeklyOpLikesCount(DEFAULT_WEEKLY_OP_LIKES_COUNT)
            .monthlyOpLikesCount(DEFAULT_MONTHLY_OP_LIKES_COUNT)
            .yearlyOpLikesCount(DEFAULT_YEARLY_OP_LIKES_COUNT)
            .quarterlyPostsCount(DEFAULT_QUARTERLY_POSTS_COUNT)
            .quarterlyViewsCount(DEFAULT_QUARTERLY_VIEWS_COUNT)
            .quarterlyLikesCount(DEFAULT_QUARTERLY_LIKES_COUNT)
            .quarterlyScore(DEFAULT_QUARTERLY_SCORE)
            .quarterlyOpLikesCount(DEFAULT_QUARTERLY_OP_LIKES_COUNT);
        return topTopics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopTopics createUpdatedEntity(EntityManager em) {
        TopTopics topTopics = new TopTopics()
            .topicId(UPDATED_TOPIC_ID)
            .yearlyPostsCount(UPDATED_YEARLY_POSTS_COUNT)
            .yearlyViewsCount(UPDATED_YEARLY_VIEWS_COUNT)
            .yearlyLikesCount(UPDATED_YEARLY_LIKES_COUNT)
            .monthlyPostsCount(UPDATED_MONTHLY_POSTS_COUNT)
            .monthlyViewsCount(UPDATED_MONTHLY_VIEWS_COUNT)
            .monthlyLikesCount(UPDATED_MONTHLY_LIKES_COUNT)
            .weeklyPostsCount(UPDATED_WEEKLY_POSTS_COUNT)
            .weeklyViewsCount(UPDATED_WEEKLY_VIEWS_COUNT)
            .weeklyLikesCount(UPDATED_WEEKLY_LIKES_COUNT)
            .dailyPostsCount(UPDATED_DAILY_POSTS_COUNT)
            .dailyViewsCount(UPDATED_DAILY_VIEWS_COUNT)
            .dailyLikesCount(UPDATED_DAILY_LIKES_COUNT)
            .dailyScore(UPDATED_DAILY_SCORE)
            .weeklyScore(UPDATED_WEEKLY_SCORE)
            .monthlyScore(UPDATED_MONTHLY_SCORE)
            .yearlyScore(UPDATED_YEARLY_SCORE)
            .allScore(UPDATED_ALL_SCORE)
            .dailyOpLikesCount(UPDATED_DAILY_OP_LIKES_COUNT)
            .weeklyOpLikesCount(UPDATED_WEEKLY_OP_LIKES_COUNT)
            .monthlyOpLikesCount(UPDATED_MONTHLY_OP_LIKES_COUNT)
            .yearlyOpLikesCount(UPDATED_YEARLY_OP_LIKES_COUNT)
            .quarterlyPostsCount(UPDATED_QUARTERLY_POSTS_COUNT)
            .quarterlyViewsCount(UPDATED_QUARTERLY_VIEWS_COUNT)
            .quarterlyLikesCount(UPDATED_QUARTERLY_LIKES_COUNT)
            .quarterlyScore(UPDATED_QUARTERLY_SCORE)
            .quarterlyOpLikesCount(UPDATED_QUARTERLY_OP_LIKES_COUNT);
        return topTopics;
    }

    @BeforeEach
    public void initTest() {
        topTopics = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopTopics() throws Exception {
        int databaseSizeBeforeCreate = topTopicsRepository.findAll().size();
        // Create the TopTopics
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);
        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopTopics in the database
        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeCreate + 1);
        TopTopics testTopTopics = topTopicsList.get(topTopicsList.size() - 1);
        assertThat(testTopTopics.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopTopics.getYearlyPostsCount()).isEqualTo(DEFAULT_YEARLY_POSTS_COUNT);
        assertThat(testTopTopics.getYearlyViewsCount()).isEqualTo(DEFAULT_YEARLY_VIEWS_COUNT);
        assertThat(testTopTopics.getYearlyLikesCount()).isEqualTo(DEFAULT_YEARLY_LIKES_COUNT);
        assertThat(testTopTopics.getMonthlyPostsCount()).isEqualTo(DEFAULT_MONTHLY_POSTS_COUNT);
        assertThat(testTopTopics.getMonthlyViewsCount()).isEqualTo(DEFAULT_MONTHLY_VIEWS_COUNT);
        assertThat(testTopTopics.getMonthlyLikesCount()).isEqualTo(DEFAULT_MONTHLY_LIKES_COUNT);
        assertThat(testTopTopics.getWeeklyPostsCount()).isEqualTo(DEFAULT_WEEKLY_POSTS_COUNT);
        assertThat(testTopTopics.getWeeklyViewsCount()).isEqualTo(DEFAULT_WEEKLY_VIEWS_COUNT);
        assertThat(testTopTopics.getWeeklyLikesCount()).isEqualTo(DEFAULT_WEEKLY_LIKES_COUNT);
        assertThat(testTopTopics.getDailyPostsCount()).isEqualTo(DEFAULT_DAILY_POSTS_COUNT);
        assertThat(testTopTopics.getDailyViewsCount()).isEqualTo(DEFAULT_DAILY_VIEWS_COUNT);
        assertThat(testTopTopics.getDailyLikesCount()).isEqualTo(DEFAULT_DAILY_LIKES_COUNT);
        assertThat(testTopTopics.getDailyScore()).isEqualTo(DEFAULT_DAILY_SCORE);
        assertThat(testTopTopics.getWeeklyScore()).isEqualTo(DEFAULT_WEEKLY_SCORE);
        assertThat(testTopTopics.getMonthlyScore()).isEqualTo(DEFAULT_MONTHLY_SCORE);
        assertThat(testTopTopics.getYearlyScore()).isEqualTo(DEFAULT_YEARLY_SCORE);
        assertThat(testTopTopics.getAllScore()).isEqualTo(DEFAULT_ALL_SCORE);
        assertThat(testTopTopics.getDailyOpLikesCount()).isEqualTo(DEFAULT_DAILY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getWeeklyOpLikesCount()).isEqualTo(DEFAULT_WEEKLY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getMonthlyOpLikesCount()).isEqualTo(DEFAULT_MONTHLY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getYearlyOpLikesCount()).isEqualTo(DEFAULT_YEARLY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getQuarterlyPostsCount()).isEqualTo(DEFAULT_QUARTERLY_POSTS_COUNT);
        assertThat(testTopTopics.getQuarterlyViewsCount()).isEqualTo(DEFAULT_QUARTERLY_VIEWS_COUNT);
        assertThat(testTopTopics.getQuarterlyLikesCount()).isEqualTo(DEFAULT_QUARTERLY_LIKES_COUNT);
        assertThat(testTopTopics.getQuarterlyScore()).isEqualTo(DEFAULT_QUARTERLY_SCORE);
        assertThat(testTopTopics.getQuarterlyOpLikesCount()).isEqualTo(DEFAULT_QUARTERLY_OP_LIKES_COUNT);
    }

    @Test
    @Transactional
    public void createTopTopicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topTopicsRepository.findAll().size();

        // Create the TopTopics with an existing ID
        topTopics.setId(1L);
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopTopics in the database
        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkYearlyPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setYearlyPostsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearlyViewsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setYearlyViewsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearlyLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setYearlyLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlyPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setMonthlyPostsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlyViewsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setMonthlyViewsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlyLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setMonthlyLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeeklyPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setWeeklyPostsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeeklyViewsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setWeeklyViewsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeeklyLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setWeeklyLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDailyPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setDailyPostsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDailyViewsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setDailyViewsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDailyLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setDailyLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDailyOpLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setDailyOpLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeeklyOpLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setWeeklyOpLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlyOpLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setMonthlyOpLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearlyOpLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setYearlyOpLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuarterlyPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setQuarterlyPostsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuarterlyViewsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setQuarterlyViewsCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuarterlyLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setQuarterlyLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuarterlyOpLikesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topTopicsRepository.findAll().size();
        // set the field null
        topTopics.setQuarterlyOpLikesCount(null);

        // Create the TopTopics, which fails.
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);


        restTopTopicsMockMvc.perform(post("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopTopics() throws Exception {
        // Initialize the database
        topTopicsRepository.saveAndFlush(topTopics);

        // Get all the topTopicsList
        restTopTopicsMockMvc.perform(get("/api/top-topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topTopics.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].yearlyPostsCount").value(hasItem(DEFAULT_YEARLY_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].yearlyViewsCount").value(hasItem(DEFAULT_YEARLY_VIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].yearlyLikesCount").value(hasItem(DEFAULT_YEARLY_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].monthlyPostsCount").value(hasItem(DEFAULT_MONTHLY_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].monthlyViewsCount").value(hasItem(DEFAULT_MONTHLY_VIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].monthlyLikesCount").value(hasItem(DEFAULT_MONTHLY_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].weeklyPostsCount").value(hasItem(DEFAULT_WEEKLY_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].weeklyViewsCount").value(hasItem(DEFAULT_WEEKLY_VIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].weeklyLikesCount").value(hasItem(DEFAULT_WEEKLY_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].dailyPostsCount").value(hasItem(DEFAULT_DAILY_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].dailyViewsCount").value(hasItem(DEFAULT_DAILY_VIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].dailyLikesCount").value(hasItem(DEFAULT_DAILY_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].dailyScore").value(hasItem(DEFAULT_DAILY_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].weeklyScore").value(hasItem(DEFAULT_WEEKLY_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].monthlyScore").value(hasItem(DEFAULT_MONTHLY_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].yearlyScore").value(hasItem(DEFAULT_YEARLY_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].allScore").value(hasItem(DEFAULT_ALL_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].dailyOpLikesCount").value(hasItem(DEFAULT_DAILY_OP_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].weeklyOpLikesCount").value(hasItem(DEFAULT_WEEKLY_OP_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].monthlyOpLikesCount").value(hasItem(DEFAULT_MONTHLY_OP_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].yearlyOpLikesCount").value(hasItem(DEFAULT_YEARLY_OP_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].quarterlyPostsCount").value(hasItem(DEFAULT_QUARTERLY_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].quarterlyViewsCount").value(hasItem(DEFAULT_QUARTERLY_VIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].quarterlyLikesCount").value(hasItem(DEFAULT_QUARTERLY_LIKES_COUNT)))
            .andExpect(jsonPath("$.[*].quarterlyScore").value(hasItem(DEFAULT_QUARTERLY_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].quarterlyOpLikesCount").value(hasItem(DEFAULT_QUARTERLY_OP_LIKES_COUNT)));
    }

    @Test
    @Transactional
    public void getTopTopics() throws Exception {
        // Initialize the database
        topTopicsRepository.saveAndFlush(topTopics);

        // Get the topTopics
        restTopTopicsMockMvc.perform(get("/api/top-topics/{id}", topTopics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topTopics.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.yearlyPostsCount").value(DEFAULT_YEARLY_POSTS_COUNT))
            .andExpect(jsonPath("$.yearlyViewsCount").value(DEFAULT_YEARLY_VIEWS_COUNT))
            .andExpect(jsonPath("$.yearlyLikesCount").value(DEFAULT_YEARLY_LIKES_COUNT))
            .andExpect(jsonPath("$.monthlyPostsCount").value(DEFAULT_MONTHLY_POSTS_COUNT))
            .andExpect(jsonPath("$.monthlyViewsCount").value(DEFAULT_MONTHLY_VIEWS_COUNT))
            .andExpect(jsonPath("$.monthlyLikesCount").value(DEFAULT_MONTHLY_LIKES_COUNT))
            .andExpect(jsonPath("$.weeklyPostsCount").value(DEFAULT_WEEKLY_POSTS_COUNT))
            .andExpect(jsonPath("$.weeklyViewsCount").value(DEFAULT_WEEKLY_VIEWS_COUNT))
            .andExpect(jsonPath("$.weeklyLikesCount").value(DEFAULT_WEEKLY_LIKES_COUNT))
            .andExpect(jsonPath("$.dailyPostsCount").value(DEFAULT_DAILY_POSTS_COUNT))
            .andExpect(jsonPath("$.dailyViewsCount").value(DEFAULT_DAILY_VIEWS_COUNT))
            .andExpect(jsonPath("$.dailyLikesCount").value(DEFAULT_DAILY_LIKES_COUNT))
            .andExpect(jsonPath("$.dailyScore").value(DEFAULT_DAILY_SCORE.doubleValue()))
            .andExpect(jsonPath("$.weeklyScore").value(DEFAULT_WEEKLY_SCORE.doubleValue()))
            .andExpect(jsonPath("$.monthlyScore").value(DEFAULT_MONTHLY_SCORE.doubleValue()))
            .andExpect(jsonPath("$.yearlyScore").value(DEFAULT_YEARLY_SCORE.doubleValue()))
            .andExpect(jsonPath("$.allScore").value(DEFAULT_ALL_SCORE.doubleValue()))
            .andExpect(jsonPath("$.dailyOpLikesCount").value(DEFAULT_DAILY_OP_LIKES_COUNT))
            .andExpect(jsonPath("$.weeklyOpLikesCount").value(DEFAULT_WEEKLY_OP_LIKES_COUNT))
            .andExpect(jsonPath("$.monthlyOpLikesCount").value(DEFAULT_MONTHLY_OP_LIKES_COUNT))
            .andExpect(jsonPath("$.yearlyOpLikesCount").value(DEFAULT_YEARLY_OP_LIKES_COUNT))
            .andExpect(jsonPath("$.quarterlyPostsCount").value(DEFAULT_QUARTERLY_POSTS_COUNT))
            .andExpect(jsonPath("$.quarterlyViewsCount").value(DEFAULT_QUARTERLY_VIEWS_COUNT))
            .andExpect(jsonPath("$.quarterlyLikesCount").value(DEFAULT_QUARTERLY_LIKES_COUNT))
            .andExpect(jsonPath("$.quarterlyScore").value(DEFAULT_QUARTERLY_SCORE.doubleValue()))
            .andExpect(jsonPath("$.quarterlyOpLikesCount").value(DEFAULT_QUARTERLY_OP_LIKES_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingTopTopics() throws Exception {
        // Get the topTopics
        restTopTopicsMockMvc.perform(get("/api/top-topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopTopics() throws Exception {
        // Initialize the database
        topTopicsRepository.saveAndFlush(topTopics);

        int databaseSizeBeforeUpdate = topTopicsRepository.findAll().size();

        // Update the topTopics
        TopTopics updatedTopTopics = topTopicsRepository.findById(topTopics.getId()).get();
        // Disconnect from session so that the updates on updatedTopTopics are not directly saved in db
        em.detach(updatedTopTopics);
        updatedTopTopics
            .topicId(UPDATED_TOPIC_ID)
            .yearlyPostsCount(UPDATED_YEARLY_POSTS_COUNT)
            .yearlyViewsCount(UPDATED_YEARLY_VIEWS_COUNT)
            .yearlyLikesCount(UPDATED_YEARLY_LIKES_COUNT)
            .monthlyPostsCount(UPDATED_MONTHLY_POSTS_COUNT)
            .monthlyViewsCount(UPDATED_MONTHLY_VIEWS_COUNT)
            .monthlyLikesCount(UPDATED_MONTHLY_LIKES_COUNT)
            .weeklyPostsCount(UPDATED_WEEKLY_POSTS_COUNT)
            .weeklyViewsCount(UPDATED_WEEKLY_VIEWS_COUNT)
            .weeklyLikesCount(UPDATED_WEEKLY_LIKES_COUNT)
            .dailyPostsCount(UPDATED_DAILY_POSTS_COUNT)
            .dailyViewsCount(UPDATED_DAILY_VIEWS_COUNT)
            .dailyLikesCount(UPDATED_DAILY_LIKES_COUNT)
            .dailyScore(UPDATED_DAILY_SCORE)
            .weeklyScore(UPDATED_WEEKLY_SCORE)
            .monthlyScore(UPDATED_MONTHLY_SCORE)
            .yearlyScore(UPDATED_YEARLY_SCORE)
            .allScore(UPDATED_ALL_SCORE)
            .dailyOpLikesCount(UPDATED_DAILY_OP_LIKES_COUNT)
            .weeklyOpLikesCount(UPDATED_WEEKLY_OP_LIKES_COUNT)
            .monthlyOpLikesCount(UPDATED_MONTHLY_OP_LIKES_COUNT)
            .yearlyOpLikesCount(UPDATED_YEARLY_OP_LIKES_COUNT)
            .quarterlyPostsCount(UPDATED_QUARTERLY_POSTS_COUNT)
            .quarterlyViewsCount(UPDATED_QUARTERLY_VIEWS_COUNT)
            .quarterlyLikesCount(UPDATED_QUARTERLY_LIKES_COUNT)
            .quarterlyScore(UPDATED_QUARTERLY_SCORE)
            .quarterlyOpLikesCount(UPDATED_QUARTERLY_OP_LIKES_COUNT);
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(updatedTopTopics);

        restTopTopicsMockMvc.perform(put("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isOk());

        // Validate the TopTopics in the database
        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeUpdate);
        TopTopics testTopTopics = topTopicsList.get(topTopicsList.size() - 1);
        assertThat(testTopTopics.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopTopics.getYearlyPostsCount()).isEqualTo(UPDATED_YEARLY_POSTS_COUNT);
        assertThat(testTopTopics.getYearlyViewsCount()).isEqualTo(UPDATED_YEARLY_VIEWS_COUNT);
        assertThat(testTopTopics.getYearlyLikesCount()).isEqualTo(UPDATED_YEARLY_LIKES_COUNT);
        assertThat(testTopTopics.getMonthlyPostsCount()).isEqualTo(UPDATED_MONTHLY_POSTS_COUNT);
        assertThat(testTopTopics.getMonthlyViewsCount()).isEqualTo(UPDATED_MONTHLY_VIEWS_COUNT);
        assertThat(testTopTopics.getMonthlyLikesCount()).isEqualTo(UPDATED_MONTHLY_LIKES_COUNT);
        assertThat(testTopTopics.getWeeklyPostsCount()).isEqualTo(UPDATED_WEEKLY_POSTS_COUNT);
        assertThat(testTopTopics.getWeeklyViewsCount()).isEqualTo(UPDATED_WEEKLY_VIEWS_COUNT);
        assertThat(testTopTopics.getWeeklyLikesCount()).isEqualTo(UPDATED_WEEKLY_LIKES_COUNT);
        assertThat(testTopTopics.getDailyPostsCount()).isEqualTo(UPDATED_DAILY_POSTS_COUNT);
        assertThat(testTopTopics.getDailyViewsCount()).isEqualTo(UPDATED_DAILY_VIEWS_COUNT);
        assertThat(testTopTopics.getDailyLikesCount()).isEqualTo(UPDATED_DAILY_LIKES_COUNT);
        assertThat(testTopTopics.getDailyScore()).isEqualTo(UPDATED_DAILY_SCORE);
        assertThat(testTopTopics.getWeeklyScore()).isEqualTo(UPDATED_WEEKLY_SCORE);
        assertThat(testTopTopics.getMonthlyScore()).isEqualTo(UPDATED_MONTHLY_SCORE);
        assertThat(testTopTopics.getYearlyScore()).isEqualTo(UPDATED_YEARLY_SCORE);
        assertThat(testTopTopics.getAllScore()).isEqualTo(UPDATED_ALL_SCORE);
        assertThat(testTopTopics.getDailyOpLikesCount()).isEqualTo(UPDATED_DAILY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getWeeklyOpLikesCount()).isEqualTo(UPDATED_WEEKLY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getMonthlyOpLikesCount()).isEqualTo(UPDATED_MONTHLY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getYearlyOpLikesCount()).isEqualTo(UPDATED_YEARLY_OP_LIKES_COUNT);
        assertThat(testTopTopics.getQuarterlyPostsCount()).isEqualTo(UPDATED_QUARTERLY_POSTS_COUNT);
        assertThat(testTopTopics.getQuarterlyViewsCount()).isEqualTo(UPDATED_QUARTERLY_VIEWS_COUNT);
        assertThat(testTopTopics.getQuarterlyLikesCount()).isEqualTo(UPDATED_QUARTERLY_LIKES_COUNT);
        assertThat(testTopTopics.getQuarterlyScore()).isEqualTo(UPDATED_QUARTERLY_SCORE);
        assertThat(testTopTopics.getQuarterlyOpLikesCount()).isEqualTo(UPDATED_QUARTERLY_OP_LIKES_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTopTopics() throws Exception {
        int databaseSizeBeforeUpdate = topTopicsRepository.findAll().size();

        // Create the TopTopics
        TopTopicsDTO topTopicsDTO = topTopicsMapper.toDto(topTopics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopTopicsMockMvc.perform(put("/api/top-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopTopics in the database
        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopTopics() throws Exception {
        // Initialize the database
        topTopicsRepository.saveAndFlush(topTopics);

        int databaseSizeBeforeDelete = topTopicsRepository.findAll().size();

        // Delete the topTopics
        restTopTopicsMockMvc.perform(delete("/api/top-topics/{id}", topTopics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopTopics> topTopicsList = topTopicsRepository.findAll();
        assertThat(topTopicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
