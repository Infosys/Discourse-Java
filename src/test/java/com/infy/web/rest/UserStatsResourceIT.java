/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserStats;
import com.infy.repository.UserStatsRepository;
import com.infy.service.UserStatsService;
import com.infy.service.dto.UserStatsDTO;
import com.infy.service.mapper.UserStatsMapper;

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
 * Integration tests for the {@link UserStatsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserStatsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOPICS_ENTERED = 1;
    private static final Integer UPDATED_TOPICS_ENTERED = 2;

    private static final Integer DEFAULT_TIME_READ = 1;
    private static final Integer UPDATED_TIME_READ = 2;

    private static final Integer DEFAULT_DAYS_VISITED = 1;
    private static final Integer UPDATED_DAYS_VISITED = 2;

    private static final Integer DEFAULT_POSTS_READ_COUNT = 1;
    private static final Integer UPDATED_POSTS_READ_COUNT = 2;

    private static final Integer DEFAULT_LIKES_GIVEN = 1;
    private static final Integer UPDATED_LIKES_GIVEN = 2;

    private static final Integer DEFAULT_LIKES_RECEIVED = 1;
    private static final Integer UPDATED_LIKES_RECEIVED = 2;

    private static final Instant DEFAULT_NEW_SINCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEW_SINCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_READ_FAQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_READ_FAQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIRST_POST_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_POST_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_POST_COUNT = 1;
    private static final Integer UPDATED_POST_COUNT = 2;

    private static final Integer DEFAULT_TOPIC_COUNT = 1;
    private static final Integer UPDATED_TOPIC_COUNT = 2;

    private static final Double DEFAULT_BOUNCE_SCORE = 1D;
    private static final Double UPDATED_BOUNCE_SCORE = 2D;

    private static final Instant DEFAULT_RESET_BOUNCE_SCORE_AFTER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESET_BOUNCE_SCORE_AFTER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_FLAGS_AGREED = 1;
    private static final Integer UPDATED_FLAGS_AGREED = 2;

    private static final Integer DEFAULT_FLAGS_DISAGREED = 1;
    private static final Integer UPDATED_FLAGS_DISAGREED = 2;

    private static final Integer DEFAULT_FLAGS_IGNORED = 1;
    private static final Integer UPDATED_FLAGS_IGNORED = 2;

    private static final Instant DEFAULT_FIRST_UNREAD_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_UNREAD_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DISTINCT_BADGE_COUNT = 1;
    private static final Integer UPDATED_DISTINCT_BADGE_COUNT = 2;

    private static final Instant DEFAULT_FIRST_UNREAD_PM_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_UNREAD_PM_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DIGEST_ATTEMPTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DIGEST_ATTEMPTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserStatsRepository userStatsRepository;

    @Autowired
    private UserStatsMapper userStatsMapper;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserStatsMockMvc;

    private UserStats userStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserStats createEntity(EntityManager em) {
        UserStats userStats = new UserStats()
            .userId(DEFAULT_USER_ID)
            .topicsEntered(DEFAULT_TOPICS_ENTERED)
            .timeRead(DEFAULT_TIME_READ)
            .daysVisited(DEFAULT_DAYS_VISITED)
            .postsReadCount(DEFAULT_POSTS_READ_COUNT)
            .likesGiven(DEFAULT_LIKES_GIVEN)
            .likesReceived(DEFAULT_LIKES_RECEIVED)
            .newSince(DEFAULT_NEW_SINCE)
            .readFaq(DEFAULT_READ_FAQ)
            .firstPostCreatedAt(DEFAULT_FIRST_POST_CREATED_AT)
            .postCount(DEFAULT_POST_COUNT)
            .topicCount(DEFAULT_TOPIC_COUNT)
            .bounceScore(DEFAULT_BOUNCE_SCORE)
            .resetBounceScoreAfter(DEFAULT_RESET_BOUNCE_SCORE_AFTER)
            .flagsAgreed(DEFAULT_FLAGS_AGREED)
            .flagsDisagreed(DEFAULT_FLAGS_DISAGREED)
            .flagsIgnored(DEFAULT_FLAGS_IGNORED)
            .firstUnreadAt(DEFAULT_FIRST_UNREAD_AT)
            .distinctBadgeCount(DEFAULT_DISTINCT_BADGE_COUNT)
            .firstUnreadPmAt(DEFAULT_FIRST_UNREAD_PM_AT)
            .digestAttemptedAt(DEFAULT_DIGEST_ATTEMPTED_AT);
        return userStats;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserStats createUpdatedEntity(EntityManager em) {
        UserStats userStats = new UserStats()
            .userId(UPDATED_USER_ID)
            .topicsEntered(UPDATED_TOPICS_ENTERED)
            .timeRead(UPDATED_TIME_READ)
            .daysVisited(UPDATED_DAYS_VISITED)
            .postsReadCount(UPDATED_POSTS_READ_COUNT)
            .likesGiven(UPDATED_LIKES_GIVEN)
            .likesReceived(UPDATED_LIKES_RECEIVED)
            .newSince(UPDATED_NEW_SINCE)
            .readFaq(UPDATED_READ_FAQ)
            .firstPostCreatedAt(UPDATED_FIRST_POST_CREATED_AT)
            .postCount(UPDATED_POST_COUNT)
            .topicCount(UPDATED_TOPIC_COUNT)
            .bounceScore(UPDATED_BOUNCE_SCORE)
            .resetBounceScoreAfter(UPDATED_RESET_BOUNCE_SCORE_AFTER)
            .flagsAgreed(UPDATED_FLAGS_AGREED)
            .flagsDisagreed(UPDATED_FLAGS_DISAGREED)
            .flagsIgnored(UPDATED_FLAGS_IGNORED)
            .firstUnreadAt(UPDATED_FIRST_UNREAD_AT)
            .distinctBadgeCount(UPDATED_DISTINCT_BADGE_COUNT)
            .firstUnreadPmAt(UPDATED_FIRST_UNREAD_PM_AT)
            .digestAttemptedAt(UPDATED_DIGEST_ATTEMPTED_AT);
        return userStats;
    }

    @BeforeEach
    public void initTest() {
        userStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserStats() throws Exception {
        int databaseSizeBeforeCreate = userStatsRepository.findAll().size();
        // Create the UserStats
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);
        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserStats in the database
        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeCreate + 1);
        UserStats testUserStats = userStatsList.get(userStatsList.size() - 1);
        assertThat(testUserStats.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserStats.getTopicsEntered()).isEqualTo(DEFAULT_TOPICS_ENTERED);
        assertThat(testUserStats.getTimeRead()).isEqualTo(DEFAULT_TIME_READ);
        assertThat(testUserStats.getDaysVisited()).isEqualTo(DEFAULT_DAYS_VISITED);
        assertThat(testUserStats.getPostsReadCount()).isEqualTo(DEFAULT_POSTS_READ_COUNT);
        assertThat(testUserStats.getLikesGiven()).isEqualTo(DEFAULT_LIKES_GIVEN);
        assertThat(testUserStats.getLikesReceived()).isEqualTo(DEFAULT_LIKES_RECEIVED);
        assertThat(testUserStats.getNewSince()).isEqualTo(DEFAULT_NEW_SINCE);
        assertThat(testUserStats.getReadFaq()).isEqualTo(DEFAULT_READ_FAQ);
        assertThat(testUserStats.getFirstPostCreatedAt()).isEqualTo(DEFAULT_FIRST_POST_CREATED_AT);
        assertThat(testUserStats.getPostCount()).isEqualTo(DEFAULT_POST_COUNT);
        assertThat(testUserStats.getTopicCount()).isEqualTo(DEFAULT_TOPIC_COUNT);
        assertThat(testUserStats.getBounceScore()).isEqualTo(DEFAULT_BOUNCE_SCORE);
        assertThat(testUserStats.getResetBounceScoreAfter()).isEqualTo(DEFAULT_RESET_BOUNCE_SCORE_AFTER);
        assertThat(testUserStats.getFlagsAgreed()).isEqualTo(DEFAULT_FLAGS_AGREED);
        assertThat(testUserStats.getFlagsDisagreed()).isEqualTo(DEFAULT_FLAGS_DISAGREED);
        assertThat(testUserStats.getFlagsIgnored()).isEqualTo(DEFAULT_FLAGS_IGNORED);
        assertThat(testUserStats.getFirstUnreadAt()).isEqualTo(DEFAULT_FIRST_UNREAD_AT);
        assertThat(testUserStats.getDistinctBadgeCount()).isEqualTo(DEFAULT_DISTINCT_BADGE_COUNT);
        assertThat(testUserStats.getFirstUnreadPmAt()).isEqualTo(DEFAULT_FIRST_UNREAD_PM_AT);
        assertThat(testUserStats.getDigestAttemptedAt()).isEqualTo(DEFAULT_DIGEST_ATTEMPTED_AT);
    }

    @Test
    @Transactional
    public void createUserStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStatsRepository.findAll().size();

        // Create the UserStats with an existing ID
        userStats.setId(1L);
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserStats in the database
        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setUserId(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicsEnteredIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setTopicsEntered(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setTimeRead(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDaysVisitedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setDaysVisited(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostsReadCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setPostsReadCount(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikesGivenIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setLikesGiven(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikesReceivedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setLikesReceived(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNewSinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setNewSince(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setPostCount(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setTopicCount(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBounceScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setBounceScore(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlagsAgreedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setFlagsAgreed(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlagsDisagreedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setFlagsDisagreed(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlagsIgnoredIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setFlagsIgnored(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstUnreadAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setFirstUnreadAt(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistinctBadgeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setDistinctBadgeCount(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstUnreadPmAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStatsRepository.findAll().size();
        // set the field null
        userStats.setFirstUnreadPmAt(null);

        // Create the UserStats, which fails.
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);


        restUserStatsMockMvc.perform(post("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserStats() throws Exception {
        // Initialize the database
        userStatsRepository.saveAndFlush(userStats);

        // Get all the userStatsList
        restUserStatsMockMvc.perform(get("/api/user-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicsEntered").value(hasItem(DEFAULT_TOPICS_ENTERED)))
            .andExpect(jsonPath("$.[*].timeRead").value(hasItem(DEFAULT_TIME_READ)))
            .andExpect(jsonPath("$.[*].daysVisited").value(hasItem(DEFAULT_DAYS_VISITED)))
            .andExpect(jsonPath("$.[*].postsReadCount").value(hasItem(DEFAULT_POSTS_READ_COUNT)))
            .andExpect(jsonPath("$.[*].likesGiven").value(hasItem(DEFAULT_LIKES_GIVEN)))
            .andExpect(jsonPath("$.[*].likesReceived").value(hasItem(DEFAULT_LIKES_RECEIVED)))
            .andExpect(jsonPath("$.[*].newSince").value(hasItem(DEFAULT_NEW_SINCE.toString())))
            .andExpect(jsonPath("$.[*].readFaq").value(hasItem(DEFAULT_READ_FAQ.toString())))
            .andExpect(jsonPath("$.[*].firstPostCreatedAt").value(hasItem(DEFAULT_FIRST_POST_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].postCount").value(hasItem(DEFAULT_POST_COUNT)))
            .andExpect(jsonPath("$.[*].topicCount").value(hasItem(DEFAULT_TOPIC_COUNT)))
            .andExpect(jsonPath("$.[*].bounceScore").value(hasItem(DEFAULT_BOUNCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].resetBounceScoreAfter").value(hasItem(DEFAULT_RESET_BOUNCE_SCORE_AFTER.toString())))
            .andExpect(jsonPath("$.[*].flagsAgreed").value(hasItem(DEFAULT_FLAGS_AGREED)))
            .andExpect(jsonPath("$.[*].flagsDisagreed").value(hasItem(DEFAULT_FLAGS_DISAGREED)))
            .andExpect(jsonPath("$.[*].flagsIgnored").value(hasItem(DEFAULT_FLAGS_IGNORED)))
            .andExpect(jsonPath("$.[*].firstUnreadAt").value(hasItem(DEFAULT_FIRST_UNREAD_AT.toString())))
            .andExpect(jsonPath("$.[*].distinctBadgeCount").value(hasItem(DEFAULT_DISTINCT_BADGE_COUNT)))
            .andExpect(jsonPath("$.[*].firstUnreadPmAt").value(hasItem(DEFAULT_FIRST_UNREAD_PM_AT.toString())))
            .andExpect(jsonPath("$.[*].digestAttemptedAt").value(hasItem(DEFAULT_DIGEST_ATTEMPTED_AT.toString())));
    }

    @Test
    @Transactional
    public void getUserStats() throws Exception {
        // Initialize the database
        userStatsRepository.saveAndFlush(userStats);

        // Get the userStats
        restUserStatsMockMvc.perform(get("/api/user-stats/{id}", userStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userStats.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicsEntered").value(DEFAULT_TOPICS_ENTERED))
            .andExpect(jsonPath("$.timeRead").value(DEFAULT_TIME_READ))
            .andExpect(jsonPath("$.daysVisited").value(DEFAULT_DAYS_VISITED))
            .andExpect(jsonPath("$.postsReadCount").value(DEFAULT_POSTS_READ_COUNT))
            .andExpect(jsonPath("$.likesGiven").value(DEFAULT_LIKES_GIVEN))
            .andExpect(jsonPath("$.likesReceived").value(DEFAULT_LIKES_RECEIVED))
            .andExpect(jsonPath("$.newSince").value(DEFAULT_NEW_SINCE.toString()))
            .andExpect(jsonPath("$.readFaq").value(DEFAULT_READ_FAQ.toString()))
            .andExpect(jsonPath("$.firstPostCreatedAt").value(DEFAULT_FIRST_POST_CREATED_AT.toString()))
            .andExpect(jsonPath("$.postCount").value(DEFAULT_POST_COUNT))
            .andExpect(jsonPath("$.topicCount").value(DEFAULT_TOPIC_COUNT))
            .andExpect(jsonPath("$.bounceScore").value(DEFAULT_BOUNCE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.resetBounceScoreAfter").value(DEFAULT_RESET_BOUNCE_SCORE_AFTER.toString()))
            .andExpect(jsonPath("$.flagsAgreed").value(DEFAULT_FLAGS_AGREED))
            .andExpect(jsonPath("$.flagsDisagreed").value(DEFAULT_FLAGS_DISAGREED))
            .andExpect(jsonPath("$.flagsIgnored").value(DEFAULT_FLAGS_IGNORED))
            .andExpect(jsonPath("$.firstUnreadAt").value(DEFAULT_FIRST_UNREAD_AT.toString()))
            .andExpect(jsonPath("$.distinctBadgeCount").value(DEFAULT_DISTINCT_BADGE_COUNT))
            .andExpect(jsonPath("$.firstUnreadPmAt").value(DEFAULT_FIRST_UNREAD_PM_AT.toString()))
            .andExpect(jsonPath("$.digestAttemptedAt").value(DEFAULT_DIGEST_ATTEMPTED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserStats() throws Exception {
        // Get the userStats
        restUserStatsMockMvc.perform(get("/api/user-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStats() throws Exception {
        // Initialize the database
        userStatsRepository.saveAndFlush(userStats);

        int databaseSizeBeforeUpdate = userStatsRepository.findAll().size();

        // Update the userStats
        UserStats updatedUserStats = userStatsRepository.findById(userStats.getId()).get();
        // Disconnect from session so that the updates on updatedUserStats are not directly saved in db
        em.detach(updatedUserStats);
        updatedUserStats
            .userId(UPDATED_USER_ID)
            .topicsEntered(UPDATED_TOPICS_ENTERED)
            .timeRead(UPDATED_TIME_READ)
            .daysVisited(UPDATED_DAYS_VISITED)
            .postsReadCount(UPDATED_POSTS_READ_COUNT)
            .likesGiven(UPDATED_LIKES_GIVEN)
            .likesReceived(UPDATED_LIKES_RECEIVED)
            .newSince(UPDATED_NEW_SINCE)
            .readFaq(UPDATED_READ_FAQ)
            .firstPostCreatedAt(UPDATED_FIRST_POST_CREATED_AT)
            .postCount(UPDATED_POST_COUNT)
            .topicCount(UPDATED_TOPIC_COUNT)
            .bounceScore(UPDATED_BOUNCE_SCORE)
            .resetBounceScoreAfter(UPDATED_RESET_BOUNCE_SCORE_AFTER)
            .flagsAgreed(UPDATED_FLAGS_AGREED)
            .flagsDisagreed(UPDATED_FLAGS_DISAGREED)
            .flagsIgnored(UPDATED_FLAGS_IGNORED)
            .firstUnreadAt(UPDATED_FIRST_UNREAD_AT)
            .distinctBadgeCount(UPDATED_DISTINCT_BADGE_COUNT)
            .firstUnreadPmAt(UPDATED_FIRST_UNREAD_PM_AT)
            .digestAttemptedAt(UPDATED_DIGEST_ATTEMPTED_AT);
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(updatedUserStats);

        restUserStatsMockMvc.perform(put("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isOk());

        // Validate the UserStats in the database
        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeUpdate);
        UserStats testUserStats = userStatsList.get(userStatsList.size() - 1);
        assertThat(testUserStats.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserStats.getTopicsEntered()).isEqualTo(UPDATED_TOPICS_ENTERED);
        assertThat(testUserStats.getTimeRead()).isEqualTo(UPDATED_TIME_READ);
        assertThat(testUserStats.getDaysVisited()).isEqualTo(UPDATED_DAYS_VISITED);
        assertThat(testUserStats.getPostsReadCount()).isEqualTo(UPDATED_POSTS_READ_COUNT);
        assertThat(testUserStats.getLikesGiven()).isEqualTo(UPDATED_LIKES_GIVEN);
        assertThat(testUserStats.getLikesReceived()).isEqualTo(UPDATED_LIKES_RECEIVED);
        assertThat(testUserStats.getNewSince()).isEqualTo(UPDATED_NEW_SINCE);
        assertThat(testUserStats.getReadFaq()).isEqualTo(UPDATED_READ_FAQ);
        assertThat(testUserStats.getFirstPostCreatedAt()).isEqualTo(UPDATED_FIRST_POST_CREATED_AT);
        assertThat(testUserStats.getPostCount()).isEqualTo(UPDATED_POST_COUNT);
        assertThat(testUserStats.getTopicCount()).isEqualTo(UPDATED_TOPIC_COUNT);
        assertThat(testUserStats.getBounceScore()).isEqualTo(UPDATED_BOUNCE_SCORE);
        assertThat(testUserStats.getResetBounceScoreAfter()).isEqualTo(UPDATED_RESET_BOUNCE_SCORE_AFTER);
        assertThat(testUserStats.getFlagsAgreed()).isEqualTo(UPDATED_FLAGS_AGREED);
        assertThat(testUserStats.getFlagsDisagreed()).isEqualTo(UPDATED_FLAGS_DISAGREED);
        assertThat(testUserStats.getFlagsIgnored()).isEqualTo(UPDATED_FLAGS_IGNORED);
        assertThat(testUserStats.getFirstUnreadAt()).isEqualTo(UPDATED_FIRST_UNREAD_AT);
        assertThat(testUserStats.getDistinctBadgeCount()).isEqualTo(UPDATED_DISTINCT_BADGE_COUNT);
        assertThat(testUserStats.getFirstUnreadPmAt()).isEqualTo(UPDATED_FIRST_UNREAD_PM_AT);
        assertThat(testUserStats.getDigestAttemptedAt()).isEqualTo(UPDATED_DIGEST_ATTEMPTED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserStats() throws Exception {
        int databaseSizeBeforeUpdate = userStatsRepository.findAll().size();

        // Create the UserStats
        UserStatsDTO userStatsDTO = userStatsMapper.toDto(userStats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserStatsMockMvc.perform(put("/api/user-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserStats in the database
        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserStats() throws Exception {
        // Initialize the database
        userStatsRepository.saveAndFlush(userStats);

        int databaseSizeBeforeDelete = userStatsRepository.findAll().size();

        // Delete the userStats
        restUserStatsMockMvc.perform(delete("/api/user-stats/{id}", userStats.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserStats> userStatsList = userStatsRepository.findAll();
        assertThat(userStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
