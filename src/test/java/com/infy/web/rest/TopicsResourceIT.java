/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Topics;
import com.infy.repository.TopicsRepository;
import com.infy.service.TopicsService;
import com.infy.service.dto.TopicsDTO;
import com.infy.service.mapper.TopicsMapper;
import com.infy.service.dto.TopicsCriteria;
import com.infy.service.TopicsQueryService;

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
 * Integration tests for the {@link TopicsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_POSTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_POSTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VIEWS = 1;
    private static final Integer UPDATED_VIEWS = 2;
    private static final Integer SMALLER_VIEWS = 1 - 1;

    private static final Integer DEFAULT_POSTS_COUNT = 1;
    private static final Integer UPDATED_POSTS_COUNT = 2;
    private static final Integer SMALLER_POSTS_COUNT = 1 - 1;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_POST_USER_ID = "1";
    private static final String UPDATED_LAST_POST_USER_ID = "2";
    private static final String SMALLER_LAST_POST_USER_ID = "0";

    private static final Integer DEFAULT_REPLY_COUNT = 1;
    private static final Integer UPDATED_REPLY_COUNT = 2;
    private static final Integer SMALLER_REPLY_COUNT = 1 - 1;

    private static final String DEFAULT_FEATURED_USER_1_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEATURED_USER_1_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURED_USER_2_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEATURED_USER_2_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURED_USER_3_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEATURED_USER_3_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HIGHEST_POST_NUMBER = 1;
    private static final Integer UPDATED_HIGHEST_POST_NUMBER = 2;
    private static final Integer SMALLER_HIGHEST_POST_NUMBER = 1 - 1;

    private static final Integer DEFAULT_LIKE_COUNT = 1;
    private static final Integer UPDATED_LIKE_COUNT = 2;
    private static final Integer SMALLER_LIKE_COUNT = 1 - 1;

    private static final Integer DEFAULT_INCOMING_LINK_COUNT = 1;
    private static final Integer UPDATED_INCOMING_LINK_COUNT = 2;
    private static final Integer SMALLER_INCOMING_LINK_COUNT = 1 - 1;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;
    private static final Long SMALLER_CATEGORY_ID = 1L - 1L;

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final Integer DEFAULT_MODERATOR_POSTS_COUNT = 1;
    private static final Integer UPDATED_MODERATOR_POSTS_COUNT = 2;
    private static final Integer SMALLER_MODERATOR_POSTS_COUNT = 1 - 1;

    private static final Boolean DEFAULT_CLOSED = false;
    private static final Boolean UPDATED_CLOSED = true;

    private static final Boolean DEFAULT_ARCHIVED = false;
    private static final Boolean UPDATED_ARCHIVED = true;

    private static final Instant DEFAULT_BUMPED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BUMPED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_HAS_SUMMARY = false;
    private static final Boolean UPDATED_HAS_SUMMARY = true;

    private static final String DEFAULT_ARCHETYPE = "AAAAAAAAAA";
    private static final String UPDATED_ARCHETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURED_USER_4_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEATURED_USER_4_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTIFY_MODERATORS_COUNT = 1;
    private static final Integer UPDATED_NOTIFY_MODERATORS_COUNT = 2;
    private static final Integer SMALLER_NOTIFY_MODERATORS_COUNT = 1 - 1;

    private static final Integer DEFAULT_SPAM_COUNT = 1;
    private static final Integer UPDATED_SPAM_COUNT = 2;
    private static final Integer SMALLER_SPAM_COUNT = 1 - 1;

    private static final Instant DEFAULT_PINNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PINNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;
    private static final Double SMALLER_SCORE = 1D - 1D;

    private static final Double DEFAULT_PERCENT_RANK = 1D;
    private static final Double UPDATED_PERCENT_RANK = 2D;
    private static final Double SMALLER_PERCENT_RANK = 1D - 1D;

    private static final String DEFAULT_SUBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARTICIPANT_COUNT = 1;
    private static final Integer UPDATED_PARTICIPANT_COUNT = 2;
    private static final Integer SMALLER_PARTICIPANT_COUNT = 1 - 1;

    private static final Integer DEFAULT_WORD_COUNT = 1;
    private static final Integer UPDATED_WORD_COUNT = 2;
    private static final Integer SMALLER_WORD_COUNT = 1 - 1;

    private static final String DEFAULT_EXCERPT = "AAAAAAAAAA";
    private static final String UPDATED_EXCERPT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PINNED_GLOBALLY = false;
    private static final Boolean UPDATED_PINNED_GLOBALLY = true;

    private static final Instant DEFAULT_PINNED_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PINNED_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FANCY_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FANCY_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_HIGHEST_STAFF_POST_NUMBER = 1;
    private static final Integer UPDATED_HIGHEST_STAFF_POST_NUMBER = 2;
    private static final Integer SMALLER_HIGHEST_STAFF_POST_NUMBER = 1 - 1;

    private static final String DEFAULT_FEATURED_LINK = "AAAAAAAAAA";
    private static final String UPDATED_FEATURED_LINK = "BBBBBBBBBB";

    private static final Double DEFAULT_REVIEWABLE_SCORE = 1D;
    private static final Double UPDATED_REVIEWABLE_SCORE = 2D;
    private static final Double SMALLER_REVIEWABLE_SCORE = 1D - 1D;

    private static final Long DEFAULT_IMAGE_UPLOAD_ID = 1L;
    private static final Long UPDATED_IMAGE_UPLOAD_ID = 2L;
    private static final Long SMALLER_IMAGE_UPLOAD_ID = 1L - 1L;

    private static final Integer DEFAULT_SLOW_MODE_SECONDS = 1;
    private static final Integer UPDATED_SLOW_MODE_SECONDS = 2;
    private static final Integer SMALLER_SLOW_MODE_SECONDS = 1 - 1;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private TopicsMapper topicsMapper;

    @Autowired
    private TopicsService topicsService;

    @Autowired
    private TopicsQueryService topicsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicsMockMvc;

    private Topics topics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topics createEntity(EntityManager em) {
        Topics topics = new Topics()
            .title(DEFAULT_TITLE)
            .lastPostedAt(DEFAULT_LAST_POSTED_AT)
            .views(DEFAULT_VIEWS)
            .postsCount(DEFAULT_POSTS_COUNT)
            .userId(DEFAULT_USER_ID)
            .lastPostUserId(DEFAULT_LAST_POST_USER_ID)
            .replyCount(DEFAULT_REPLY_COUNT)
            .featuredUser1Id(DEFAULT_FEATURED_USER_1_ID)
            .featuredUser2Id(DEFAULT_FEATURED_USER_2_ID)
            .featuredUser3Id(DEFAULT_FEATURED_USER_3_ID)
            .deletedAt(DEFAULT_DELETED_AT)
            .highestPostNumber(DEFAULT_HIGHEST_POST_NUMBER)
            .likeCount(DEFAULT_LIKE_COUNT)
            .incomingLinkCount(DEFAULT_INCOMING_LINK_COUNT)
            .categoryId(DEFAULT_CATEGORY_ID)
            .visible(DEFAULT_VISIBLE)
            .moderatorPostsCount(DEFAULT_MODERATOR_POSTS_COUNT)
            .closed(DEFAULT_CLOSED)
            .archived(DEFAULT_ARCHIVED)
            .bumpedAt(DEFAULT_BUMPED_AT)
            .hasSummary(DEFAULT_HAS_SUMMARY)
            .archetype(DEFAULT_ARCHETYPE)
            .featuredUser4Id(DEFAULT_FEATURED_USER_4_ID)
            .notifyModeratorsCount(DEFAULT_NOTIFY_MODERATORS_COUNT)
            .spamCount(DEFAULT_SPAM_COUNT)
            .pinnedAt(DEFAULT_PINNED_AT)
            .score(DEFAULT_SCORE)
            .percentRank(DEFAULT_PERCENT_RANK)
            .subtype(DEFAULT_SUBTYPE)
            .slug(DEFAULT_SLUG)
            .deletedById(DEFAULT_DELETED_BY_ID)
            .participantCount(DEFAULT_PARTICIPANT_COUNT)
            .wordCount(DEFAULT_WORD_COUNT)
            .excerpt(DEFAULT_EXCERPT)
            .pinnedGlobally(DEFAULT_PINNED_GLOBALLY)
            .pinnedUntil(DEFAULT_PINNED_UNTIL)
            .fancyTitle(DEFAULT_FANCY_TITLE)
            .highestStaffPostNumber(DEFAULT_HIGHEST_STAFF_POST_NUMBER)
            .featuredLink(DEFAULT_FEATURED_LINK)
            .reviewableScore(DEFAULT_REVIEWABLE_SCORE)
            .imageUploadId(DEFAULT_IMAGE_UPLOAD_ID)
            .slowModeSeconds(DEFAULT_SLOW_MODE_SECONDS);
        return topics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topics createUpdatedEntity(EntityManager em) {
        Topics topics = new Topics()
            .title(UPDATED_TITLE)
            .lastPostedAt(UPDATED_LAST_POSTED_AT)
            .views(UPDATED_VIEWS)
            .postsCount(UPDATED_POSTS_COUNT)
            .userId(UPDATED_USER_ID)
            .lastPostUserId(UPDATED_LAST_POST_USER_ID)
            .replyCount(UPDATED_REPLY_COUNT)
            .featuredUser1Id(UPDATED_FEATURED_USER_1_ID)
            .featuredUser2Id(UPDATED_FEATURED_USER_2_ID)
            .featuredUser3Id(UPDATED_FEATURED_USER_3_ID)
            .deletedAt(UPDATED_DELETED_AT)
            .highestPostNumber(UPDATED_HIGHEST_POST_NUMBER)
            .likeCount(UPDATED_LIKE_COUNT)
            .incomingLinkCount(UPDATED_INCOMING_LINK_COUNT)
            .categoryId(UPDATED_CATEGORY_ID)
            .visible(UPDATED_VISIBLE)
            .moderatorPostsCount(UPDATED_MODERATOR_POSTS_COUNT)
            .closed(UPDATED_CLOSED)
            .archived(UPDATED_ARCHIVED)
            .bumpedAt(UPDATED_BUMPED_AT)
            .hasSummary(UPDATED_HAS_SUMMARY)
            .archetype(UPDATED_ARCHETYPE)
            .featuredUser4Id(UPDATED_FEATURED_USER_4_ID)
            .notifyModeratorsCount(UPDATED_NOTIFY_MODERATORS_COUNT)
            .spamCount(UPDATED_SPAM_COUNT)
            .pinnedAt(UPDATED_PINNED_AT)
            .score(UPDATED_SCORE)
            .percentRank(UPDATED_PERCENT_RANK)
            .subtype(UPDATED_SUBTYPE)
            .slug(UPDATED_SLUG)
            .deletedById(UPDATED_DELETED_BY_ID)
            .participantCount(UPDATED_PARTICIPANT_COUNT)
            .wordCount(UPDATED_WORD_COUNT)
            .excerpt(UPDATED_EXCERPT)
            .pinnedGlobally(UPDATED_PINNED_GLOBALLY)
            .pinnedUntil(UPDATED_PINNED_UNTIL)
            .fancyTitle(UPDATED_FANCY_TITLE)
            .highestStaffPostNumber(UPDATED_HIGHEST_STAFF_POST_NUMBER)
            .featuredLink(UPDATED_FEATURED_LINK)
            .reviewableScore(UPDATED_REVIEWABLE_SCORE)
            .imageUploadId(UPDATED_IMAGE_UPLOAD_ID)
            .slowModeSeconds(UPDATED_SLOW_MODE_SECONDS);
        return topics;
    }

    @BeforeEach
    public void initTest() {
        topics = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopics() throws Exception {
        int databaseSizeBeforeCreate = topicsRepository.findAll().size();
        // Create the Topics
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);
        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isCreated());

        // Validate the Topics in the database
        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeCreate + 1);
        Topics testTopics = topicsList.get(topicsList.size() - 1);
        assertThat(testTopics.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTopics.getLastPostedAt()).isEqualTo(DEFAULT_LAST_POSTED_AT);
        assertThat(testTopics.getViews()).isEqualTo(DEFAULT_VIEWS);
        assertThat(testTopics.getPostsCount()).isEqualTo(DEFAULT_POSTS_COUNT);
        assertThat(testTopics.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopics.getLastPostUserId()).isEqualTo(DEFAULT_LAST_POST_USER_ID);
        assertThat(testTopics.getReplyCount()).isEqualTo(DEFAULT_REPLY_COUNT);
        assertThat(testTopics.getFeaturedUser1Id()).isEqualTo(DEFAULT_FEATURED_USER_1_ID);
        assertThat(testTopics.getFeaturedUser2Id()).isEqualTo(DEFAULT_FEATURED_USER_2_ID);
        assertThat(testTopics.getFeaturedUser3Id()).isEqualTo(DEFAULT_FEATURED_USER_3_ID);
        assertThat(testTopics.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testTopics.getHighestPostNumber()).isEqualTo(DEFAULT_HIGHEST_POST_NUMBER);
        assertThat(testTopics.getLikeCount()).isEqualTo(DEFAULT_LIKE_COUNT);
        assertThat(testTopics.getIncomingLinkCount()).isEqualTo(DEFAULT_INCOMING_LINK_COUNT);
        assertThat(testTopics.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testTopics.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testTopics.getModeratorPostsCount()).isEqualTo(DEFAULT_MODERATOR_POSTS_COUNT);
        assertThat(testTopics.isClosed()).isEqualTo(DEFAULT_CLOSED);
        assertThat(testTopics.isArchived()).isEqualTo(DEFAULT_ARCHIVED);
        assertThat(testTopics.getBumpedAt()).isEqualTo(DEFAULT_BUMPED_AT);
        assertThat(testTopics.isHasSummary()).isEqualTo(DEFAULT_HAS_SUMMARY);
        assertThat(testTopics.getArchetype()).isEqualTo(DEFAULT_ARCHETYPE);
        assertThat(testTopics.getFeaturedUser4Id()).isEqualTo(DEFAULT_FEATURED_USER_4_ID);
        assertThat(testTopics.getNotifyModeratorsCount()).isEqualTo(DEFAULT_NOTIFY_MODERATORS_COUNT);
        assertThat(testTopics.getSpamCount()).isEqualTo(DEFAULT_SPAM_COUNT);
        assertThat(testTopics.getPinnedAt()).isEqualTo(DEFAULT_PINNED_AT);
        assertThat(testTopics.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testTopics.getPercentRank()).isEqualTo(DEFAULT_PERCENT_RANK);
        assertThat(testTopics.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testTopics.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testTopics.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
        assertThat(testTopics.getParticipantCount()).isEqualTo(DEFAULT_PARTICIPANT_COUNT);
        assertThat(testTopics.getWordCount()).isEqualTo(DEFAULT_WORD_COUNT);
        assertThat(testTopics.getExcerpt()).isEqualTo(DEFAULT_EXCERPT);
        assertThat(testTopics.isPinnedGlobally()).isEqualTo(DEFAULT_PINNED_GLOBALLY);
        assertThat(testTopics.getPinnedUntil()).isEqualTo(DEFAULT_PINNED_UNTIL);
        assertThat(testTopics.getFancyTitle()).isEqualTo(DEFAULT_FANCY_TITLE);
        assertThat(testTopics.getHighestStaffPostNumber()).isEqualTo(DEFAULT_HIGHEST_STAFF_POST_NUMBER);
        assertThat(testTopics.getFeaturedLink()).isEqualTo(DEFAULT_FEATURED_LINK);
        assertThat(testTopics.getReviewableScore()).isEqualTo(DEFAULT_REVIEWABLE_SCORE);
        assertThat(testTopics.getImageUploadId()).isEqualTo(DEFAULT_IMAGE_UPLOAD_ID);
        assertThat(testTopics.getSlowModeSeconds()).isEqualTo(DEFAULT_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void createTopicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicsRepository.findAll().size();

        // Create the Topics with an existing ID
        topics.setId(1L);
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topics in the database
        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setTitle(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setViews(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setPostsCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastPostUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setLastPostUserId(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReplyCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setReplyCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHighestPostNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setHighestPostNumber(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setLikeCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncomingLinkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setIncomingLinkCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibleIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setVisible(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeratorPostsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setModeratorPostsCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClosedIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setClosed(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArchivedIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setArchived(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBumpedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setBumpedAt(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasSummaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setHasSummary(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArchetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setArchetype(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotifyModeratorsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setNotifyModeratorsCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpamCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setSpamCount(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentRankIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setPercentRank(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPinnedGloballyIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setPinnedGlobally(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHighestStaffPostNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setHighestStaffPostNumber(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewableScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setReviewableScore(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSlowModeSecondsIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicsRepository.findAll().size();
        // set the field null
        topics.setSlowModeSeconds(null);

        // Create the Topics, which fails.
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);


        restTopicsMockMvc.perform(post("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopics() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList
        restTopicsMockMvc.perform(get("/api/topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topics.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].lastPostedAt").value(hasItem(DEFAULT_LAST_POSTED_AT.toString())))
            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS)))
            .andExpect(jsonPath("$.[*].postsCount").value(hasItem(DEFAULT_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].lastPostUserId").value(hasItem(DEFAULT_LAST_POST_USER_ID)))
            .andExpect(jsonPath("$.[*].replyCount").value(hasItem(DEFAULT_REPLY_COUNT)))
            .andExpect(jsonPath("$.[*].featuredUser1Id").value(hasItem(DEFAULT_FEATURED_USER_1_ID)))
            .andExpect(jsonPath("$.[*].featuredUser2Id").value(hasItem(DEFAULT_FEATURED_USER_2_ID)))
            .andExpect(jsonPath("$.[*].featuredUser3Id").value(hasItem(DEFAULT_FEATURED_USER_3_ID)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].highestPostNumber").value(hasItem(DEFAULT_HIGHEST_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].likeCount").value(hasItem(DEFAULT_LIKE_COUNT)))
            .andExpect(jsonPath("$.[*].incomingLinkCount").value(hasItem(DEFAULT_INCOMING_LINK_COUNT)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].moderatorPostsCount").value(hasItem(DEFAULT_MODERATOR_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].closed").value(hasItem(DEFAULT_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].archived").value(hasItem(DEFAULT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].bumpedAt").value(hasItem(DEFAULT_BUMPED_AT.toString())))
            .andExpect(jsonPath("$.[*].hasSummary").value(hasItem(DEFAULT_HAS_SUMMARY.booleanValue())))
            .andExpect(jsonPath("$.[*].archetype").value(hasItem(DEFAULT_ARCHETYPE)))
            .andExpect(jsonPath("$.[*].featuredUser4Id").value(hasItem(DEFAULT_FEATURED_USER_4_ID)))
            .andExpect(jsonPath("$.[*].notifyModeratorsCount").value(hasItem(DEFAULT_NOTIFY_MODERATORS_COUNT)))
            .andExpect(jsonPath("$.[*].spamCount").value(hasItem(DEFAULT_SPAM_COUNT)))
            .andExpect(jsonPath("$.[*].pinnedAt").value(hasItem(DEFAULT_PINNED_AT.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentRank").value(hasItem(DEFAULT_PERCENT_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)))
            .andExpect(jsonPath("$.[*].participantCount").value(hasItem(DEFAULT_PARTICIPANT_COUNT)))
            .andExpect(jsonPath("$.[*].wordCount").value(hasItem(DEFAULT_WORD_COUNT)))
            .andExpect(jsonPath("$.[*].excerpt").value(hasItem(DEFAULT_EXCERPT)))
            .andExpect(jsonPath("$.[*].pinnedGlobally").value(hasItem(DEFAULT_PINNED_GLOBALLY.booleanValue())))
            .andExpect(jsonPath("$.[*].pinnedUntil").value(hasItem(DEFAULT_PINNED_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].fancyTitle").value(hasItem(DEFAULT_FANCY_TITLE)))
            .andExpect(jsonPath("$.[*].highestStaffPostNumber").value(hasItem(DEFAULT_HIGHEST_STAFF_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].featuredLink").value(hasItem(DEFAULT_FEATURED_LINK)))
            .andExpect(jsonPath("$.[*].reviewableScore").value(hasItem(DEFAULT_REVIEWABLE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageUploadId").value(hasItem(DEFAULT_IMAGE_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].slowModeSeconds").value(hasItem(DEFAULT_SLOW_MODE_SECONDS)));
    }

    @Test
    @Transactional
    public void getTopics() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get the topics
        restTopicsMockMvc.perform(get("/api/topics/{id}", topics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topics.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.lastPostedAt").value(DEFAULT_LAST_POSTED_AT.toString()))
            .andExpect(jsonPath("$.views").value(DEFAULT_VIEWS))
            .andExpect(jsonPath("$.postsCount").value(DEFAULT_POSTS_COUNT))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.lastPostUserId").value(DEFAULT_LAST_POST_USER_ID))
            .andExpect(jsonPath("$.replyCount").value(DEFAULT_REPLY_COUNT))
            .andExpect(jsonPath("$.featuredUser1Id").value(DEFAULT_FEATURED_USER_1_ID))
            .andExpect(jsonPath("$.featuredUser2Id").value(DEFAULT_FEATURED_USER_2_ID))
            .andExpect(jsonPath("$.featuredUser3Id").value(DEFAULT_FEATURED_USER_3_ID))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.highestPostNumber").value(DEFAULT_HIGHEST_POST_NUMBER))
            .andExpect(jsonPath("$.likeCount").value(DEFAULT_LIKE_COUNT))
            .andExpect(jsonPath("$.incomingLinkCount").value(DEFAULT_INCOMING_LINK_COUNT))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.moderatorPostsCount").value(DEFAULT_MODERATOR_POSTS_COUNT))
            .andExpect(jsonPath("$.closed").value(DEFAULT_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.archived").value(DEFAULT_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.bumpedAt").value(DEFAULT_BUMPED_AT.toString()))
            .andExpect(jsonPath("$.hasSummary").value(DEFAULT_HAS_SUMMARY.booleanValue()))
            .andExpect(jsonPath("$.archetype").value(DEFAULT_ARCHETYPE))
            .andExpect(jsonPath("$.featuredUser4Id").value(DEFAULT_FEATURED_USER_4_ID))
            .andExpect(jsonPath("$.notifyModeratorsCount").value(DEFAULT_NOTIFY_MODERATORS_COUNT))
            .andExpect(jsonPath("$.spamCount").value(DEFAULT_SPAM_COUNT))
            .andExpect(jsonPath("$.pinnedAt").value(DEFAULT_PINNED_AT.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.percentRank").value(DEFAULT_PERCENT_RANK.doubleValue()))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.deletedById").value(DEFAULT_DELETED_BY_ID))
            .andExpect(jsonPath("$.participantCount").value(DEFAULT_PARTICIPANT_COUNT))
            .andExpect(jsonPath("$.wordCount").value(DEFAULT_WORD_COUNT))
            .andExpect(jsonPath("$.excerpt").value(DEFAULT_EXCERPT))
            .andExpect(jsonPath("$.pinnedGlobally").value(DEFAULT_PINNED_GLOBALLY.booleanValue()))
            .andExpect(jsonPath("$.pinnedUntil").value(DEFAULT_PINNED_UNTIL.toString()))
            .andExpect(jsonPath("$.fancyTitle").value(DEFAULT_FANCY_TITLE))
            .andExpect(jsonPath("$.highestStaffPostNumber").value(DEFAULT_HIGHEST_STAFF_POST_NUMBER))
            .andExpect(jsonPath("$.featuredLink").value(DEFAULT_FEATURED_LINK))
            .andExpect(jsonPath("$.reviewableScore").value(DEFAULT_REVIEWABLE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.imageUploadId").value(DEFAULT_IMAGE_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.slowModeSeconds").value(DEFAULT_SLOW_MODE_SECONDS));
    }


    @Test
    @Transactional
    public void getTopicsByIdFiltering() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        Long id = topics.getId();

        defaultTopicsShouldBeFound("id.equals=" + id);
        defaultTopicsShouldNotBeFound("id.notEquals=" + id);

        defaultTopicsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTopicsShouldNotBeFound("id.greaterThan=" + id);

        defaultTopicsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTopicsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTopicsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where title equals to DEFAULT_TITLE
        defaultTopicsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the topicsList where title equals to UPDATED_TITLE
        defaultTopicsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where title not equals to DEFAULT_TITLE
        defaultTopicsShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the topicsList where title not equals to UPDATED_TITLE
        defaultTopicsShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTopicsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the topicsList where title equals to UPDATED_TITLE
        defaultTopicsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where title is not null
        defaultTopicsShouldBeFound("title.specified=true");

        // Get all the topicsList where title is null
        defaultTopicsShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByTitleContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where title contains DEFAULT_TITLE
        defaultTopicsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the topicsList where title contains UPDATED_TITLE
        defaultTopicsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where title does not contain DEFAULT_TITLE
        defaultTopicsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the topicsList where title does not contain UPDATED_TITLE
        defaultTopicsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllTopicsByLastPostedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostedAt equals to DEFAULT_LAST_POSTED_AT
        defaultTopicsShouldBeFound("lastPostedAt.equals=" + DEFAULT_LAST_POSTED_AT);

        // Get all the topicsList where lastPostedAt equals to UPDATED_LAST_POSTED_AT
        defaultTopicsShouldNotBeFound("lastPostedAt.equals=" + UPDATED_LAST_POSTED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostedAt not equals to DEFAULT_LAST_POSTED_AT
        defaultTopicsShouldNotBeFound("lastPostedAt.notEquals=" + DEFAULT_LAST_POSTED_AT);

        // Get all the topicsList where lastPostedAt not equals to UPDATED_LAST_POSTED_AT
        defaultTopicsShouldBeFound("lastPostedAt.notEquals=" + UPDATED_LAST_POSTED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostedAtIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostedAt in DEFAULT_LAST_POSTED_AT or UPDATED_LAST_POSTED_AT
        defaultTopicsShouldBeFound("lastPostedAt.in=" + DEFAULT_LAST_POSTED_AT + "," + UPDATED_LAST_POSTED_AT);

        // Get all the topicsList where lastPostedAt equals to UPDATED_LAST_POSTED_AT
        defaultTopicsShouldNotBeFound("lastPostedAt.in=" + UPDATED_LAST_POSTED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostedAt is not null
        defaultTopicsShouldBeFound("lastPostedAt.specified=true");

        // Get all the topicsList where lastPostedAt is null
        defaultTopicsShouldNotBeFound("lastPostedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views equals to DEFAULT_VIEWS
        defaultTopicsShouldBeFound("views.equals=" + DEFAULT_VIEWS);

        // Get all the topicsList where views equals to UPDATED_VIEWS
        defaultTopicsShouldNotBeFound("views.equals=" + UPDATED_VIEWS);
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views not equals to DEFAULT_VIEWS
        defaultTopicsShouldNotBeFound("views.notEquals=" + DEFAULT_VIEWS);

        // Get all the topicsList where views not equals to UPDATED_VIEWS
        defaultTopicsShouldBeFound("views.notEquals=" + UPDATED_VIEWS);
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views in DEFAULT_VIEWS or UPDATED_VIEWS
        defaultTopicsShouldBeFound("views.in=" + DEFAULT_VIEWS + "," + UPDATED_VIEWS);

        // Get all the topicsList where views equals to UPDATED_VIEWS
        defaultTopicsShouldNotBeFound("views.in=" + UPDATED_VIEWS);
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views is not null
        defaultTopicsShouldBeFound("views.specified=true");

        // Get all the topicsList where views is null
        defaultTopicsShouldNotBeFound("views.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views is greater than or equal to DEFAULT_VIEWS
        defaultTopicsShouldBeFound("views.greaterThanOrEqual=" + DEFAULT_VIEWS);

        // Get all the topicsList where views is greater than or equal to UPDATED_VIEWS
        defaultTopicsShouldNotBeFound("views.greaterThanOrEqual=" + UPDATED_VIEWS);
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views is less than or equal to DEFAULT_VIEWS
        defaultTopicsShouldBeFound("views.lessThanOrEqual=" + DEFAULT_VIEWS);

        // Get all the topicsList where views is less than or equal to SMALLER_VIEWS
        defaultTopicsShouldNotBeFound("views.lessThanOrEqual=" + SMALLER_VIEWS);
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views is less than DEFAULT_VIEWS
        defaultTopicsShouldNotBeFound("views.lessThan=" + DEFAULT_VIEWS);

        // Get all the topicsList where views is less than UPDATED_VIEWS
        defaultTopicsShouldBeFound("views.lessThan=" + UPDATED_VIEWS);
    }

    @Test
    @Transactional
    public void getAllTopicsByViewsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where views is greater than DEFAULT_VIEWS
        defaultTopicsShouldNotBeFound("views.greaterThan=" + DEFAULT_VIEWS);

        // Get all the topicsList where views is greater than SMALLER_VIEWS
        defaultTopicsShouldBeFound("views.greaterThan=" + SMALLER_VIEWS);
    }


    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount equals to DEFAULT_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.equals=" + DEFAULT_POSTS_COUNT);

        // Get all the topicsList where postsCount equals to UPDATED_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.equals=" + UPDATED_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount not equals to DEFAULT_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.notEquals=" + DEFAULT_POSTS_COUNT);

        // Get all the topicsList where postsCount not equals to UPDATED_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.notEquals=" + UPDATED_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount in DEFAULT_POSTS_COUNT or UPDATED_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.in=" + DEFAULT_POSTS_COUNT + "," + UPDATED_POSTS_COUNT);

        // Get all the topicsList where postsCount equals to UPDATED_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.in=" + UPDATED_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount is not null
        defaultTopicsShouldBeFound("postsCount.specified=true");

        // Get all the topicsList where postsCount is null
        defaultTopicsShouldNotBeFound("postsCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount is greater than or equal to DEFAULT_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.greaterThanOrEqual=" + DEFAULT_POSTS_COUNT);

        // Get all the topicsList where postsCount is greater than or equal to UPDATED_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.greaterThanOrEqual=" + UPDATED_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount is less than or equal to DEFAULT_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.lessThanOrEqual=" + DEFAULT_POSTS_COUNT);

        // Get all the topicsList where postsCount is less than or equal to SMALLER_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.lessThanOrEqual=" + SMALLER_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount is less than DEFAULT_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.lessThan=" + DEFAULT_POSTS_COUNT);

        // Get all the topicsList where postsCount is less than UPDATED_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.lessThan=" + UPDATED_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPostsCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where postsCount is greater than DEFAULT_POSTS_COUNT
        defaultTopicsShouldNotBeFound("postsCount.greaterThan=" + DEFAULT_POSTS_COUNT);

        // Get all the topicsList where postsCount is greater than SMALLER_POSTS_COUNT
        defaultTopicsShouldBeFound("postsCount.greaterThan=" + SMALLER_POSTS_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where userId equals to DEFAULT_USER_ID
        defaultTopicsShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the topicsList where userId equals to UPDATED_USER_ID
        defaultTopicsShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where userId not equals to DEFAULT_USER_ID
        defaultTopicsShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the topicsList where userId not equals to UPDATED_USER_ID
        defaultTopicsShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultTopicsShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the topicsList where userId equals to UPDATED_USER_ID
        defaultTopicsShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where userId is not null
        defaultTopicsShouldBeFound("userId.specified=true");

        // Get all the topicsList where userId is null
        defaultTopicsShouldNotBeFound("userId.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByUserIdContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where userId contains DEFAULT_USER_ID
        defaultTopicsShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the topicsList where userId contains UPDATED_USER_ID
        defaultTopicsShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where userId does not contain DEFAULT_USER_ID
        defaultTopicsShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the topicsList where userId does not contain UPDATED_USER_ID
        defaultTopicsShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId equals to DEFAULT_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.equals=" + DEFAULT_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId equals to UPDATED_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.equals=" + UPDATED_LAST_POST_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId not equals to DEFAULT_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.notEquals=" + DEFAULT_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId not equals to UPDATED_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.notEquals=" + UPDATED_LAST_POST_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId in DEFAULT_LAST_POST_USER_ID or UPDATED_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.in=" + DEFAULT_LAST_POST_USER_ID + "," + UPDATED_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId equals to UPDATED_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.in=" + UPDATED_LAST_POST_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId is not null
        defaultTopicsShouldBeFound("lastPostUserId.specified=true");

        // Get all the topicsList where lastPostUserId is null
        defaultTopicsShouldNotBeFound("lastPostUserId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId is greater than or equal to DEFAULT_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.greaterThanOrEqual=" + DEFAULT_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId is greater than or equal to UPDATED_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.greaterThanOrEqual=" + UPDATED_LAST_POST_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId is less than or equal to DEFAULT_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.lessThanOrEqual=" + DEFAULT_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId is less than or equal to SMALLER_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.lessThanOrEqual=" + SMALLER_LAST_POST_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId is less than DEFAULT_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.lessThan=" + DEFAULT_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId is less than UPDATED_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.lessThan=" + UPDATED_LAST_POST_USER_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByLastPostUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where lastPostUserId is greater than DEFAULT_LAST_POST_USER_ID
        defaultTopicsShouldNotBeFound("lastPostUserId.greaterThan=" + DEFAULT_LAST_POST_USER_ID);

        // Get all the topicsList where lastPostUserId is greater than SMALLER_LAST_POST_USER_ID
        defaultTopicsShouldBeFound("lastPostUserId.greaterThan=" + SMALLER_LAST_POST_USER_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount equals to DEFAULT_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.equals=" + DEFAULT_REPLY_COUNT);

        // Get all the topicsList where replyCount equals to UPDATED_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.equals=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount not equals to DEFAULT_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.notEquals=" + DEFAULT_REPLY_COUNT);

        // Get all the topicsList where replyCount not equals to UPDATED_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.notEquals=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount in DEFAULT_REPLY_COUNT or UPDATED_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.in=" + DEFAULT_REPLY_COUNT + "," + UPDATED_REPLY_COUNT);

        // Get all the topicsList where replyCount equals to UPDATED_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.in=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount is not null
        defaultTopicsShouldBeFound("replyCount.specified=true");

        // Get all the topicsList where replyCount is null
        defaultTopicsShouldNotBeFound("replyCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount is greater than or equal to DEFAULT_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.greaterThanOrEqual=" + DEFAULT_REPLY_COUNT);

        // Get all the topicsList where replyCount is greater than or equal to UPDATED_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.greaterThanOrEqual=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount is less than or equal to DEFAULT_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.lessThanOrEqual=" + DEFAULT_REPLY_COUNT);

        // Get all the topicsList where replyCount is less than or equal to SMALLER_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.lessThanOrEqual=" + SMALLER_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount is less than DEFAULT_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.lessThan=" + DEFAULT_REPLY_COUNT);

        // Get all the topicsList where replyCount is less than UPDATED_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.lessThan=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByReplyCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where replyCount is greater than DEFAULT_REPLY_COUNT
        defaultTopicsShouldNotBeFound("replyCount.greaterThan=" + DEFAULT_REPLY_COUNT);

        // Get all the topicsList where replyCount is greater than SMALLER_REPLY_COUNT
        defaultTopicsShouldBeFound("replyCount.greaterThan=" + SMALLER_REPLY_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser1IdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser1Id equals to DEFAULT_FEATURED_USER_1_ID
        defaultTopicsShouldBeFound("featuredUser1Id.equals=" + DEFAULT_FEATURED_USER_1_ID);

        // Get all the topicsList where featuredUser1Id equals to UPDATED_FEATURED_USER_1_ID
        defaultTopicsShouldNotBeFound("featuredUser1Id.equals=" + UPDATED_FEATURED_USER_1_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser1IdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser1Id not equals to DEFAULT_FEATURED_USER_1_ID
        defaultTopicsShouldNotBeFound("featuredUser1Id.notEquals=" + DEFAULT_FEATURED_USER_1_ID);

        // Get all the topicsList where featuredUser1Id not equals to UPDATED_FEATURED_USER_1_ID
        defaultTopicsShouldBeFound("featuredUser1Id.notEquals=" + UPDATED_FEATURED_USER_1_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser1IdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser1Id in DEFAULT_FEATURED_USER_1_ID or UPDATED_FEATURED_USER_1_ID
        defaultTopicsShouldBeFound("featuredUser1Id.in=" + DEFAULT_FEATURED_USER_1_ID + "," + UPDATED_FEATURED_USER_1_ID);

        // Get all the topicsList where featuredUser1Id equals to UPDATED_FEATURED_USER_1_ID
        defaultTopicsShouldNotBeFound("featuredUser1Id.in=" + UPDATED_FEATURED_USER_1_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser1IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser1Id is not null
        defaultTopicsShouldBeFound("featuredUser1Id.specified=true");

        // Get all the topicsList where featuredUser1Id is null
        defaultTopicsShouldNotBeFound("featuredUser1Id.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByFeaturedUser1IdContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser1Id contains DEFAULT_FEATURED_USER_1_ID
        defaultTopicsShouldBeFound("featuredUser1Id.contains=" + DEFAULT_FEATURED_USER_1_ID);

        // Get all the topicsList where featuredUser1Id contains UPDATED_FEATURED_USER_1_ID
        defaultTopicsShouldNotBeFound("featuredUser1Id.contains=" + UPDATED_FEATURED_USER_1_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser1IdNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser1Id does not contain DEFAULT_FEATURED_USER_1_ID
        defaultTopicsShouldNotBeFound("featuredUser1Id.doesNotContain=" + DEFAULT_FEATURED_USER_1_ID);

        // Get all the topicsList where featuredUser1Id does not contain UPDATED_FEATURED_USER_1_ID
        defaultTopicsShouldBeFound("featuredUser1Id.doesNotContain=" + UPDATED_FEATURED_USER_1_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser2IdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser2Id equals to DEFAULT_FEATURED_USER_2_ID
        defaultTopicsShouldBeFound("featuredUser2Id.equals=" + DEFAULT_FEATURED_USER_2_ID);

        // Get all the topicsList where featuredUser2Id equals to UPDATED_FEATURED_USER_2_ID
        defaultTopicsShouldNotBeFound("featuredUser2Id.equals=" + UPDATED_FEATURED_USER_2_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser2IdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser2Id not equals to DEFAULT_FEATURED_USER_2_ID
        defaultTopicsShouldNotBeFound("featuredUser2Id.notEquals=" + DEFAULT_FEATURED_USER_2_ID);

        // Get all the topicsList where featuredUser2Id not equals to UPDATED_FEATURED_USER_2_ID
        defaultTopicsShouldBeFound("featuredUser2Id.notEquals=" + UPDATED_FEATURED_USER_2_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser2IdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser2Id in DEFAULT_FEATURED_USER_2_ID or UPDATED_FEATURED_USER_2_ID
        defaultTopicsShouldBeFound("featuredUser2Id.in=" + DEFAULT_FEATURED_USER_2_ID + "," + UPDATED_FEATURED_USER_2_ID);

        // Get all the topicsList where featuredUser2Id equals to UPDATED_FEATURED_USER_2_ID
        defaultTopicsShouldNotBeFound("featuredUser2Id.in=" + UPDATED_FEATURED_USER_2_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser2IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser2Id is not null
        defaultTopicsShouldBeFound("featuredUser2Id.specified=true");

        // Get all the topicsList where featuredUser2Id is null
        defaultTopicsShouldNotBeFound("featuredUser2Id.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByFeaturedUser2IdContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser2Id contains DEFAULT_FEATURED_USER_2_ID
        defaultTopicsShouldBeFound("featuredUser2Id.contains=" + DEFAULT_FEATURED_USER_2_ID);

        // Get all the topicsList where featuredUser2Id contains UPDATED_FEATURED_USER_2_ID
        defaultTopicsShouldNotBeFound("featuredUser2Id.contains=" + UPDATED_FEATURED_USER_2_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser2IdNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser2Id does not contain DEFAULT_FEATURED_USER_2_ID
        defaultTopicsShouldNotBeFound("featuredUser2Id.doesNotContain=" + DEFAULT_FEATURED_USER_2_ID);

        // Get all the topicsList where featuredUser2Id does not contain UPDATED_FEATURED_USER_2_ID
        defaultTopicsShouldBeFound("featuredUser2Id.doesNotContain=" + UPDATED_FEATURED_USER_2_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser3IdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser3Id equals to DEFAULT_FEATURED_USER_3_ID
        defaultTopicsShouldBeFound("featuredUser3Id.equals=" + DEFAULT_FEATURED_USER_3_ID);

        // Get all the topicsList where featuredUser3Id equals to UPDATED_FEATURED_USER_3_ID
        defaultTopicsShouldNotBeFound("featuredUser3Id.equals=" + UPDATED_FEATURED_USER_3_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser3IdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser3Id not equals to DEFAULT_FEATURED_USER_3_ID
        defaultTopicsShouldNotBeFound("featuredUser3Id.notEquals=" + DEFAULT_FEATURED_USER_3_ID);

        // Get all the topicsList where featuredUser3Id not equals to UPDATED_FEATURED_USER_3_ID
        defaultTopicsShouldBeFound("featuredUser3Id.notEquals=" + UPDATED_FEATURED_USER_3_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser3IdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser3Id in DEFAULT_FEATURED_USER_3_ID or UPDATED_FEATURED_USER_3_ID
        defaultTopicsShouldBeFound("featuredUser3Id.in=" + DEFAULT_FEATURED_USER_3_ID + "," + UPDATED_FEATURED_USER_3_ID);

        // Get all the topicsList where featuredUser3Id equals to UPDATED_FEATURED_USER_3_ID
        defaultTopicsShouldNotBeFound("featuredUser3Id.in=" + UPDATED_FEATURED_USER_3_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser3IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser3Id is not null
        defaultTopicsShouldBeFound("featuredUser3Id.specified=true");

        // Get all the topicsList where featuredUser3Id is null
        defaultTopicsShouldNotBeFound("featuredUser3Id.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByFeaturedUser3IdContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser3Id contains DEFAULT_FEATURED_USER_3_ID
        defaultTopicsShouldBeFound("featuredUser3Id.contains=" + DEFAULT_FEATURED_USER_3_ID);

        // Get all the topicsList where featuredUser3Id contains UPDATED_FEATURED_USER_3_ID
        defaultTopicsShouldNotBeFound("featuredUser3Id.contains=" + UPDATED_FEATURED_USER_3_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser3IdNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser3Id does not contain DEFAULT_FEATURED_USER_3_ID
        defaultTopicsShouldNotBeFound("featuredUser3Id.doesNotContain=" + DEFAULT_FEATURED_USER_3_ID);

        // Get all the topicsList where featuredUser3Id does not contain UPDATED_FEATURED_USER_3_ID
        defaultTopicsShouldBeFound("featuredUser3Id.doesNotContain=" + UPDATED_FEATURED_USER_3_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultTopicsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the topicsList where deletedAt equals to UPDATED_DELETED_AT
        defaultTopicsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedAt not equals to DEFAULT_DELETED_AT
        defaultTopicsShouldNotBeFound("deletedAt.notEquals=" + DEFAULT_DELETED_AT);

        // Get all the topicsList where deletedAt not equals to UPDATED_DELETED_AT
        defaultTopicsShouldBeFound("deletedAt.notEquals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultTopicsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the topicsList where deletedAt equals to UPDATED_DELETED_AT
        defaultTopicsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedAt is not null
        defaultTopicsShouldBeFound("deletedAt.specified=true");

        // Get all the topicsList where deletedAt is null
        defaultTopicsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber equals to DEFAULT_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.equals=" + DEFAULT_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber equals to UPDATED_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.equals=" + UPDATED_HIGHEST_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber not equals to DEFAULT_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.notEquals=" + DEFAULT_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber not equals to UPDATED_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.notEquals=" + UPDATED_HIGHEST_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber in DEFAULT_HIGHEST_POST_NUMBER or UPDATED_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.in=" + DEFAULT_HIGHEST_POST_NUMBER + "," + UPDATED_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber equals to UPDATED_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.in=" + UPDATED_HIGHEST_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber is not null
        defaultTopicsShouldBeFound("highestPostNumber.specified=true");

        // Get all the topicsList where highestPostNumber is null
        defaultTopicsShouldNotBeFound("highestPostNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber is greater than or equal to DEFAULT_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.greaterThanOrEqual=" + DEFAULT_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber is greater than or equal to UPDATED_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.greaterThanOrEqual=" + UPDATED_HIGHEST_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber is less than or equal to DEFAULT_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.lessThanOrEqual=" + DEFAULT_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber is less than or equal to SMALLER_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.lessThanOrEqual=" + SMALLER_HIGHEST_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber is less than DEFAULT_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.lessThan=" + DEFAULT_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber is less than UPDATED_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.lessThan=" + UPDATED_HIGHEST_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestPostNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestPostNumber is greater than DEFAULT_HIGHEST_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestPostNumber.greaterThan=" + DEFAULT_HIGHEST_POST_NUMBER);

        // Get all the topicsList where highestPostNumber is greater than SMALLER_HIGHEST_POST_NUMBER
        defaultTopicsShouldBeFound("highestPostNumber.greaterThan=" + SMALLER_HIGHEST_POST_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount equals to DEFAULT_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.equals=" + DEFAULT_LIKE_COUNT);

        // Get all the topicsList where likeCount equals to UPDATED_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.equals=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount not equals to DEFAULT_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.notEquals=" + DEFAULT_LIKE_COUNT);

        // Get all the topicsList where likeCount not equals to UPDATED_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.notEquals=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount in DEFAULT_LIKE_COUNT or UPDATED_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.in=" + DEFAULT_LIKE_COUNT + "," + UPDATED_LIKE_COUNT);

        // Get all the topicsList where likeCount equals to UPDATED_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.in=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount is not null
        defaultTopicsShouldBeFound("likeCount.specified=true");

        // Get all the topicsList where likeCount is null
        defaultTopicsShouldNotBeFound("likeCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount is greater than or equal to DEFAULT_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.greaterThanOrEqual=" + DEFAULT_LIKE_COUNT);

        // Get all the topicsList where likeCount is greater than or equal to UPDATED_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.greaterThanOrEqual=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount is less than or equal to DEFAULT_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.lessThanOrEqual=" + DEFAULT_LIKE_COUNT);

        // Get all the topicsList where likeCount is less than or equal to SMALLER_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.lessThanOrEqual=" + SMALLER_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount is less than DEFAULT_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.lessThan=" + DEFAULT_LIKE_COUNT);

        // Get all the topicsList where likeCount is less than UPDATED_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.lessThan=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByLikeCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where likeCount is greater than DEFAULT_LIKE_COUNT
        defaultTopicsShouldNotBeFound("likeCount.greaterThan=" + DEFAULT_LIKE_COUNT);

        // Get all the topicsList where likeCount is greater than SMALLER_LIKE_COUNT
        defaultTopicsShouldBeFound("likeCount.greaterThan=" + SMALLER_LIKE_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount equals to DEFAULT_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.equals=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount equals to UPDATED_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.equals=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount not equals to DEFAULT_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.notEquals=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount not equals to UPDATED_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.notEquals=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount in DEFAULT_INCOMING_LINK_COUNT or UPDATED_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.in=" + DEFAULT_INCOMING_LINK_COUNT + "," + UPDATED_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount equals to UPDATED_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.in=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount is not null
        defaultTopicsShouldBeFound("incomingLinkCount.specified=true");

        // Get all the topicsList where incomingLinkCount is null
        defaultTopicsShouldNotBeFound("incomingLinkCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount is greater than or equal to DEFAULT_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.greaterThanOrEqual=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount is greater than or equal to UPDATED_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.greaterThanOrEqual=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount is less than or equal to DEFAULT_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.lessThanOrEqual=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount is less than or equal to SMALLER_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.lessThanOrEqual=" + SMALLER_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount is less than DEFAULT_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.lessThan=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount is less than UPDATED_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.lessThan=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByIncomingLinkCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where incomingLinkCount is greater than DEFAULT_INCOMING_LINK_COUNT
        defaultTopicsShouldNotBeFound("incomingLinkCount.greaterThan=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the topicsList where incomingLinkCount is greater than SMALLER_INCOMING_LINK_COUNT
        defaultTopicsShouldBeFound("incomingLinkCount.greaterThan=" + SMALLER_INCOMING_LINK_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId equals to DEFAULT_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.equals=" + DEFAULT_CATEGORY_ID);

        // Get all the topicsList where categoryId equals to UPDATED_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.equals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId not equals to DEFAULT_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.notEquals=" + DEFAULT_CATEGORY_ID);

        // Get all the topicsList where categoryId not equals to UPDATED_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.notEquals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId in DEFAULT_CATEGORY_ID or UPDATED_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.in=" + DEFAULT_CATEGORY_ID + "," + UPDATED_CATEGORY_ID);

        // Get all the topicsList where categoryId equals to UPDATED_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.in=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId is not null
        defaultTopicsShouldBeFound("categoryId.specified=true");

        // Get all the topicsList where categoryId is null
        defaultTopicsShouldNotBeFound("categoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId is greater than or equal to DEFAULT_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.greaterThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the topicsList where categoryId is greater than or equal to UPDATED_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.greaterThanOrEqual=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId is less than or equal to DEFAULT_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.lessThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the topicsList where categoryId is less than or equal to SMALLER_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.lessThanOrEqual=" + SMALLER_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId is less than DEFAULT_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.lessThan=" + DEFAULT_CATEGORY_ID);

        // Get all the topicsList where categoryId is less than UPDATED_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.lessThan=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByCategoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where categoryId is greater than DEFAULT_CATEGORY_ID
        defaultTopicsShouldNotBeFound("categoryId.greaterThan=" + DEFAULT_CATEGORY_ID);

        // Get all the topicsList where categoryId is greater than SMALLER_CATEGORY_ID
        defaultTopicsShouldBeFound("categoryId.greaterThan=" + SMALLER_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByVisibleIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where visible equals to DEFAULT_VISIBLE
        defaultTopicsShouldBeFound("visible.equals=" + DEFAULT_VISIBLE);

        // Get all the topicsList where visible equals to UPDATED_VISIBLE
        defaultTopicsShouldNotBeFound("visible.equals=" + UPDATED_VISIBLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByVisibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where visible not equals to DEFAULT_VISIBLE
        defaultTopicsShouldNotBeFound("visible.notEquals=" + DEFAULT_VISIBLE);

        // Get all the topicsList where visible not equals to UPDATED_VISIBLE
        defaultTopicsShouldBeFound("visible.notEquals=" + UPDATED_VISIBLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByVisibleIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where visible in DEFAULT_VISIBLE or UPDATED_VISIBLE
        defaultTopicsShouldBeFound("visible.in=" + DEFAULT_VISIBLE + "," + UPDATED_VISIBLE);

        // Get all the topicsList where visible equals to UPDATED_VISIBLE
        defaultTopicsShouldNotBeFound("visible.in=" + UPDATED_VISIBLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByVisibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where visible is not null
        defaultTopicsShouldBeFound("visible.specified=true");

        // Get all the topicsList where visible is null
        defaultTopicsShouldNotBeFound("visible.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount equals to DEFAULT_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.equals=" + DEFAULT_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount equals to UPDATED_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.equals=" + UPDATED_MODERATOR_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount not equals to DEFAULT_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.notEquals=" + DEFAULT_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount not equals to UPDATED_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.notEquals=" + UPDATED_MODERATOR_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount in DEFAULT_MODERATOR_POSTS_COUNT or UPDATED_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.in=" + DEFAULT_MODERATOR_POSTS_COUNT + "," + UPDATED_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount equals to UPDATED_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.in=" + UPDATED_MODERATOR_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount is not null
        defaultTopicsShouldBeFound("moderatorPostsCount.specified=true");

        // Get all the topicsList where moderatorPostsCount is null
        defaultTopicsShouldNotBeFound("moderatorPostsCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount is greater than or equal to DEFAULT_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.greaterThanOrEqual=" + DEFAULT_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount is greater than or equal to UPDATED_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.greaterThanOrEqual=" + UPDATED_MODERATOR_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount is less than or equal to DEFAULT_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.lessThanOrEqual=" + DEFAULT_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount is less than or equal to SMALLER_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.lessThanOrEqual=" + SMALLER_MODERATOR_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount is less than DEFAULT_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.lessThan=" + DEFAULT_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount is less than UPDATED_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.lessThan=" + UPDATED_MODERATOR_POSTS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByModeratorPostsCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where moderatorPostsCount is greater than DEFAULT_MODERATOR_POSTS_COUNT
        defaultTopicsShouldNotBeFound("moderatorPostsCount.greaterThan=" + DEFAULT_MODERATOR_POSTS_COUNT);

        // Get all the topicsList where moderatorPostsCount is greater than SMALLER_MODERATOR_POSTS_COUNT
        defaultTopicsShouldBeFound("moderatorPostsCount.greaterThan=" + SMALLER_MODERATOR_POSTS_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByClosedIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where closed equals to DEFAULT_CLOSED
        defaultTopicsShouldBeFound("closed.equals=" + DEFAULT_CLOSED);

        // Get all the topicsList where closed equals to UPDATED_CLOSED
        defaultTopicsShouldNotBeFound("closed.equals=" + UPDATED_CLOSED);
    }

    @Test
    @Transactional
    public void getAllTopicsByClosedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where closed not equals to DEFAULT_CLOSED
        defaultTopicsShouldNotBeFound("closed.notEquals=" + DEFAULT_CLOSED);

        // Get all the topicsList where closed not equals to UPDATED_CLOSED
        defaultTopicsShouldBeFound("closed.notEquals=" + UPDATED_CLOSED);
    }

    @Test
    @Transactional
    public void getAllTopicsByClosedIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where closed in DEFAULT_CLOSED or UPDATED_CLOSED
        defaultTopicsShouldBeFound("closed.in=" + DEFAULT_CLOSED + "," + UPDATED_CLOSED);

        // Get all the topicsList where closed equals to UPDATED_CLOSED
        defaultTopicsShouldNotBeFound("closed.in=" + UPDATED_CLOSED);
    }

    @Test
    @Transactional
    public void getAllTopicsByClosedIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where closed is not null
        defaultTopicsShouldBeFound("closed.specified=true");

        // Get all the topicsList where closed is null
        defaultTopicsShouldNotBeFound("closed.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByArchivedIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archived equals to DEFAULT_ARCHIVED
        defaultTopicsShouldBeFound("archived.equals=" + DEFAULT_ARCHIVED);

        // Get all the topicsList where archived equals to UPDATED_ARCHIVED
        defaultTopicsShouldNotBeFound("archived.equals=" + UPDATED_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archived not equals to DEFAULT_ARCHIVED
        defaultTopicsShouldNotBeFound("archived.notEquals=" + DEFAULT_ARCHIVED);

        // Get all the topicsList where archived not equals to UPDATED_ARCHIVED
        defaultTopicsShouldBeFound("archived.notEquals=" + UPDATED_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchivedIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archived in DEFAULT_ARCHIVED or UPDATED_ARCHIVED
        defaultTopicsShouldBeFound("archived.in=" + DEFAULT_ARCHIVED + "," + UPDATED_ARCHIVED);

        // Get all the topicsList where archived equals to UPDATED_ARCHIVED
        defaultTopicsShouldNotBeFound("archived.in=" + UPDATED_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archived is not null
        defaultTopicsShouldBeFound("archived.specified=true");

        // Get all the topicsList where archived is null
        defaultTopicsShouldNotBeFound("archived.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByBumpedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where bumpedAt equals to DEFAULT_BUMPED_AT
        defaultTopicsShouldBeFound("bumpedAt.equals=" + DEFAULT_BUMPED_AT);

        // Get all the topicsList where bumpedAt equals to UPDATED_BUMPED_AT
        defaultTopicsShouldNotBeFound("bumpedAt.equals=" + UPDATED_BUMPED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByBumpedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where bumpedAt not equals to DEFAULT_BUMPED_AT
        defaultTopicsShouldNotBeFound("bumpedAt.notEquals=" + DEFAULT_BUMPED_AT);

        // Get all the topicsList where bumpedAt not equals to UPDATED_BUMPED_AT
        defaultTopicsShouldBeFound("bumpedAt.notEquals=" + UPDATED_BUMPED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByBumpedAtIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where bumpedAt in DEFAULT_BUMPED_AT or UPDATED_BUMPED_AT
        defaultTopicsShouldBeFound("bumpedAt.in=" + DEFAULT_BUMPED_AT + "," + UPDATED_BUMPED_AT);

        // Get all the topicsList where bumpedAt equals to UPDATED_BUMPED_AT
        defaultTopicsShouldNotBeFound("bumpedAt.in=" + UPDATED_BUMPED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByBumpedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where bumpedAt is not null
        defaultTopicsShouldBeFound("bumpedAt.specified=true");

        // Get all the topicsList where bumpedAt is null
        defaultTopicsShouldNotBeFound("bumpedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByHasSummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where hasSummary equals to DEFAULT_HAS_SUMMARY
        defaultTopicsShouldBeFound("hasSummary.equals=" + DEFAULT_HAS_SUMMARY);

        // Get all the topicsList where hasSummary equals to UPDATED_HAS_SUMMARY
        defaultTopicsShouldNotBeFound("hasSummary.equals=" + UPDATED_HAS_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllTopicsByHasSummaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where hasSummary not equals to DEFAULT_HAS_SUMMARY
        defaultTopicsShouldNotBeFound("hasSummary.notEquals=" + DEFAULT_HAS_SUMMARY);

        // Get all the topicsList where hasSummary not equals to UPDATED_HAS_SUMMARY
        defaultTopicsShouldBeFound("hasSummary.notEquals=" + UPDATED_HAS_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllTopicsByHasSummaryIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where hasSummary in DEFAULT_HAS_SUMMARY or UPDATED_HAS_SUMMARY
        defaultTopicsShouldBeFound("hasSummary.in=" + DEFAULT_HAS_SUMMARY + "," + UPDATED_HAS_SUMMARY);

        // Get all the topicsList where hasSummary equals to UPDATED_HAS_SUMMARY
        defaultTopicsShouldNotBeFound("hasSummary.in=" + UPDATED_HAS_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllTopicsByHasSummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where hasSummary is not null
        defaultTopicsShouldBeFound("hasSummary.specified=true");

        // Get all the topicsList where hasSummary is null
        defaultTopicsShouldNotBeFound("hasSummary.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByArchetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archetype equals to DEFAULT_ARCHETYPE
        defaultTopicsShouldBeFound("archetype.equals=" + DEFAULT_ARCHETYPE);

        // Get all the topicsList where archetype equals to UPDATED_ARCHETYPE
        defaultTopicsShouldNotBeFound("archetype.equals=" + UPDATED_ARCHETYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchetypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archetype not equals to DEFAULT_ARCHETYPE
        defaultTopicsShouldNotBeFound("archetype.notEquals=" + DEFAULT_ARCHETYPE);

        // Get all the topicsList where archetype not equals to UPDATED_ARCHETYPE
        defaultTopicsShouldBeFound("archetype.notEquals=" + UPDATED_ARCHETYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchetypeIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archetype in DEFAULT_ARCHETYPE or UPDATED_ARCHETYPE
        defaultTopicsShouldBeFound("archetype.in=" + DEFAULT_ARCHETYPE + "," + UPDATED_ARCHETYPE);

        // Get all the topicsList where archetype equals to UPDATED_ARCHETYPE
        defaultTopicsShouldNotBeFound("archetype.in=" + UPDATED_ARCHETYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archetype is not null
        defaultTopicsShouldBeFound("archetype.specified=true");

        // Get all the topicsList where archetype is null
        defaultTopicsShouldNotBeFound("archetype.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByArchetypeContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archetype contains DEFAULT_ARCHETYPE
        defaultTopicsShouldBeFound("archetype.contains=" + DEFAULT_ARCHETYPE);

        // Get all the topicsList where archetype contains UPDATED_ARCHETYPE
        defaultTopicsShouldNotBeFound("archetype.contains=" + UPDATED_ARCHETYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsByArchetypeNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where archetype does not contain DEFAULT_ARCHETYPE
        defaultTopicsShouldNotBeFound("archetype.doesNotContain=" + DEFAULT_ARCHETYPE);

        // Get all the topicsList where archetype does not contain UPDATED_ARCHETYPE
        defaultTopicsShouldBeFound("archetype.doesNotContain=" + UPDATED_ARCHETYPE);
    }


    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser4IdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser4Id equals to DEFAULT_FEATURED_USER_4_ID
        defaultTopicsShouldBeFound("featuredUser4Id.equals=" + DEFAULT_FEATURED_USER_4_ID);

        // Get all the topicsList where featuredUser4Id equals to UPDATED_FEATURED_USER_4_ID
        defaultTopicsShouldNotBeFound("featuredUser4Id.equals=" + UPDATED_FEATURED_USER_4_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser4IdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser4Id not equals to DEFAULT_FEATURED_USER_4_ID
        defaultTopicsShouldNotBeFound("featuredUser4Id.notEquals=" + DEFAULT_FEATURED_USER_4_ID);

        // Get all the topicsList where featuredUser4Id not equals to UPDATED_FEATURED_USER_4_ID
        defaultTopicsShouldBeFound("featuredUser4Id.notEquals=" + UPDATED_FEATURED_USER_4_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser4IdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser4Id in DEFAULT_FEATURED_USER_4_ID or UPDATED_FEATURED_USER_4_ID
        defaultTopicsShouldBeFound("featuredUser4Id.in=" + DEFAULT_FEATURED_USER_4_ID + "," + UPDATED_FEATURED_USER_4_ID);

        // Get all the topicsList where featuredUser4Id equals to UPDATED_FEATURED_USER_4_ID
        defaultTopicsShouldNotBeFound("featuredUser4Id.in=" + UPDATED_FEATURED_USER_4_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser4IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser4Id is not null
        defaultTopicsShouldBeFound("featuredUser4Id.specified=true");

        // Get all the topicsList where featuredUser4Id is null
        defaultTopicsShouldNotBeFound("featuredUser4Id.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByFeaturedUser4IdContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser4Id contains DEFAULT_FEATURED_USER_4_ID
        defaultTopicsShouldBeFound("featuredUser4Id.contains=" + DEFAULT_FEATURED_USER_4_ID);

        // Get all the topicsList where featuredUser4Id contains UPDATED_FEATURED_USER_4_ID
        defaultTopicsShouldNotBeFound("featuredUser4Id.contains=" + UPDATED_FEATURED_USER_4_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedUser4IdNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredUser4Id does not contain DEFAULT_FEATURED_USER_4_ID
        defaultTopicsShouldNotBeFound("featuredUser4Id.doesNotContain=" + DEFAULT_FEATURED_USER_4_ID);

        // Get all the topicsList where featuredUser4Id does not contain UPDATED_FEATURED_USER_4_ID
        defaultTopicsShouldBeFound("featuredUser4Id.doesNotContain=" + UPDATED_FEATURED_USER_4_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount equals to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.equals=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount equals to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.equals=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount not equals to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.notEquals=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount not equals to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.notEquals=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount in DEFAULT_NOTIFY_MODERATORS_COUNT or UPDATED_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.in=" + DEFAULT_NOTIFY_MODERATORS_COUNT + "," + UPDATED_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount equals to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.in=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount is not null
        defaultTopicsShouldBeFound("notifyModeratorsCount.specified=true");

        // Get all the topicsList where notifyModeratorsCount is null
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount is greater than or equal to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.greaterThanOrEqual=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount is greater than or equal to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.greaterThanOrEqual=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount is less than or equal to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.lessThanOrEqual=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount is less than or equal to SMALLER_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.lessThanOrEqual=" + SMALLER_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount is less than DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.lessThan=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount is less than UPDATED_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.lessThan=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByNotifyModeratorsCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where notifyModeratorsCount is greater than DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldNotBeFound("notifyModeratorsCount.greaterThan=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the topicsList where notifyModeratorsCount is greater than SMALLER_NOTIFY_MODERATORS_COUNT
        defaultTopicsShouldBeFound("notifyModeratorsCount.greaterThan=" + SMALLER_NOTIFY_MODERATORS_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount equals to DEFAULT_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.equals=" + DEFAULT_SPAM_COUNT);

        // Get all the topicsList where spamCount equals to UPDATED_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.equals=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount not equals to DEFAULT_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.notEquals=" + DEFAULT_SPAM_COUNT);

        // Get all the topicsList where spamCount not equals to UPDATED_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.notEquals=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount in DEFAULT_SPAM_COUNT or UPDATED_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.in=" + DEFAULT_SPAM_COUNT + "," + UPDATED_SPAM_COUNT);

        // Get all the topicsList where spamCount equals to UPDATED_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.in=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount is not null
        defaultTopicsShouldBeFound("spamCount.specified=true");

        // Get all the topicsList where spamCount is null
        defaultTopicsShouldNotBeFound("spamCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount is greater than or equal to DEFAULT_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.greaterThanOrEqual=" + DEFAULT_SPAM_COUNT);

        // Get all the topicsList where spamCount is greater than or equal to UPDATED_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.greaterThanOrEqual=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount is less than or equal to DEFAULT_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.lessThanOrEqual=" + DEFAULT_SPAM_COUNT);

        // Get all the topicsList where spamCount is less than or equal to SMALLER_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.lessThanOrEqual=" + SMALLER_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount is less than DEFAULT_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.lessThan=" + DEFAULT_SPAM_COUNT);

        // Get all the topicsList where spamCount is less than UPDATED_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.lessThan=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsBySpamCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where spamCount is greater than DEFAULT_SPAM_COUNT
        defaultTopicsShouldNotBeFound("spamCount.greaterThan=" + DEFAULT_SPAM_COUNT);

        // Get all the topicsList where spamCount is greater than SMALLER_SPAM_COUNT
        defaultTopicsShouldBeFound("spamCount.greaterThan=" + SMALLER_SPAM_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByPinnedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedAt equals to DEFAULT_PINNED_AT
        defaultTopicsShouldBeFound("pinnedAt.equals=" + DEFAULT_PINNED_AT);

        // Get all the topicsList where pinnedAt equals to UPDATED_PINNED_AT
        defaultTopicsShouldNotBeFound("pinnedAt.equals=" + UPDATED_PINNED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedAt not equals to DEFAULT_PINNED_AT
        defaultTopicsShouldNotBeFound("pinnedAt.notEquals=" + DEFAULT_PINNED_AT);

        // Get all the topicsList where pinnedAt not equals to UPDATED_PINNED_AT
        defaultTopicsShouldBeFound("pinnedAt.notEquals=" + UPDATED_PINNED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedAtIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedAt in DEFAULT_PINNED_AT or UPDATED_PINNED_AT
        defaultTopicsShouldBeFound("pinnedAt.in=" + DEFAULT_PINNED_AT + "," + UPDATED_PINNED_AT);

        // Get all the topicsList where pinnedAt equals to UPDATED_PINNED_AT
        defaultTopicsShouldNotBeFound("pinnedAt.in=" + UPDATED_PINNED_AT);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedAt is not null
        defaultTopicsShouldBeFound("pinnedAt.specified=true");

        // Get all the topicsList where pinnedAt is null
        defaultTopicsShouldNotBeFound("pinnedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score equals to DEFAULT_SCORE
        defaultTopicsShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the topicsList where score equals to UPDATED_SCORE
        defaultTopicsShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score not equals to DEFAULT_SCORE
        defaultTopicsShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the topicsList where score not equals to UPDATED_SCORE
        defaultTopicsShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultTopicsShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the topicsList where score equals to UPDATED_SCORE
        defaultTopicsShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score is not null
        defaultTopicsShouldBeFound("score.specified=true");

        // Get all the topicsList where score is null
        defaultTopicsShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score is greater than or equal to DEFAULT_SCORE
        defaultTopicsShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the topicsList where score is greater than or equal to UPDATED_SCORE
        defaultTopicsShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score is less than or equal to DEFAULT_SCORE
        defaultTopicsShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the topicsList where score is less than or equal to SMALLER_SCORE
        defaultTopicsShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score is less than DEFAULT_SCORE
        defaultTopicsShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the topicsList where score is less than UPDATED_SCORE
        defaultTopicsShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where score is greater than DEFAULT_SCORE
        defaultTopicsShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the topicsList where score is greater than SMALLER_SCORE
        defaultTopicsShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank equals to DEFAULT_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.equals=" + DEFAULT_PERCENT_RANK);

        // Get all the topicsList where percentRank equals to UPDATED_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.equals=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank not equals to DEFAULT_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.notEquals=" + DEFAULT_PERCENT_RANK);

        // Get all the topicsList where percentRank not equals to UPDATED_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.notEquals=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank in DEFAULT_PERCENT_RANK or UPDATED_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.in=" + DEFAULT_PERCENT_RANK + "," + UPDATED_PERCENT_RANK);

        // Get all the topicsList where percentRank equals to UPDATED_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.in=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank is not null
        defaultTopicsShouldBeFound("percentRank.specified=true");

        // Get all the topicsList where percentRank is null
        defaultTopicsShouldNotBeFound("percentRank.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank is greater than or equal to DEFAULT_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.greaterThanOrEqual=" + DEFAULT_PERCENT_RANK);

        // Get all the topicsList where percentRank is greater than or equal to UPDATED_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.greaterThanOrEqual=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank is less than or equal to DEFAULT_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.lessThanOrEqual=" + DEFAULT_PERCENT_RANK);

        // Get all the topicsList where percentRank is less than or equal to SMALLER_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.lessThanOrEqual=" + SMALLER_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank is less than DEFAULT_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.lessThan=" + DEFAULT_PERCENT_RANK);

        // Get all the topicsList where percentRank is less than UPDATED_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.lessThan=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllTopicsByPercentRankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where percentRank is greater than DEFAULT_PERCENT_RANK
        defaultTopicsShouldNotBeFound("percentRank.greaterThan=" + DEFAULT_PERCENT_RANK);

        // Get all the topicsList where percentRank is greater than SMALLER_PERCENT_RANK
        defaultTopicsShouldBeFound("percentRank.greaterThan=" + SMALLER_PERCENT_RANK);
    }


    @Test
    @Transactional
    public void getAllTopicsBySubtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where subtype equals to DEFAULT_SUBTYPE
        defaultTopicsShouldBeFound("subtype.equals=" + DEFAULT_SUBTYPE);

        // Get all the topicsList where subtype equals to UPDATED_SUBTYPE
        defaultTopicsShouldNotBeFound("subtype.equals=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsBySubtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where subtype not equals to DEFAULT_SUBTYPE
        defaultTopicsShouldNotBeFound("subtype.notEquals=" + DEFAULT_SUBTYPE);

        // Get all the topicsList where subtype not equals to UPDATED_SUBTYPE
        defaultTopicsShouldBeFound("subtype.notEquals=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsBySubtypeIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where subtype in DEFAULT_SUBTYPE or UPDATED_SUBTYPE
        defaultTopicsShouldBeFound("subtype.in=" + DEFAULT_SUBTYPE + "," + UPDATED_SUBTYPE);

        // Get all the topicsList where subtype equals to UPDATED_SUBTYPE
        defaultTopicsShouldNotBeFound("subtype.in=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsBySubtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where subtype is not null
        defaultTopicsShouldBeFound("subtype.specified=true");

        // Get all the topicsList where subtype is null
        defaultTopicsShouldNotBeFound("subtype.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsBySubtypeContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where subtype contains DEFAULT_SUBTYPE
        defaultTopicsShouldBeFound("subtype.contains=" + DEFAULT_SUBTYPE);

        // Get all the topicsList where subtype contains UPDATED_SUBTYPE
        defaultTopicsShouldNotBeFound("subtype.contains=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    public void getAllTopicsBySubtypeNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where subtype does not contain DEFAULT_SUBTYPE
        defaultTopicsShouldNotBeFound("subtype.doesNotContain=" + DEFAULT_SUBTYPE);

        // Get all the topicsList where subtype does not contain UPDATED_SUBTYPE
        defaultTopicsShouldBeFound("subtype.doesNotContain=" + UPDATED_SUBTYPE);
    }


    @Test
    @Transactional
    public void getAllTopicsBySlugIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slug equals to DEFAULT_SLUG
        defaultTopicsShouldBeFound("slug.equals=" + DEFAULT_SLUG);

        // Get all the topicsList where slug equals to UPDATED_SLUG
        defaultTopicsShouldNotBeFound("slug.equals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlugIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slug not equals to DEFAULT_SLUG
        defaultTopicsShouldNotBeFound("slug.notEquals=" + DEFAULT_SLUG);

        // Get all the topicsList where slug not equals to UPDATED_SLUG
        defaultTopicsShouldBeFound("slug.notEquals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlugIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slug in DEFAULT_SLUG or UPDATED_SLUG
        defaultTopicsShouldBeFound("slug.in=" + DEFAULT_SLUG + "," + UPDATED_SLUG);

        // Get all the topicsList where slug equals to UPDATED_SLUG
        defaultTopicsShouldNotBeFound("slug.in=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlugIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slug is not null
        defaultTopicsShouldBeFound("slug.specified=true");

        // Get all the topicsList where slug is null
        defaultTopicsShouldNotBeFound("slug.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsBySlugContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slug contains DEFAULT_SLUG
        defaultTopicsShouldBeFound("slug.contains=" + DEFAULT_SLUG);

        // Get all the topicsList where slug contains UPDATED_SLUG
        defaultTopicsShouldNotBeFound("slug.contains=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlugNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slug does not contain DEFAULT_SLUG
        defaultTopicsShouldNotBeFound("slug.doesNotContain=" + DEFAULT_SLUG);

        // Get all the topicsList where slug does not contain UPDATED_SLUG
        defaultTopicsShouldBeFound("slug.doesNotContain=" + UPDATED_SLUG);
    }


    @Test
    @Transactional
    public void getAllTopicsByDeletedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedById equals to DEFAULT_DELETED_BY_ID
        defaultTopicsShouldBeFound("deletedById.equals=" + DEFAULT_DELETED_BY_ID);

        // Get all the topicsList where deletedById equals to UPDATED_DELETED_BY_ID
        defaultTopicsShouldNotBeFound("deletedById.equals=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedByIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedById not equals to DEFAULT_DELETED_BY_ID
        defaultTopicsShouldNotBeFound("deletedById.notEquals=" + DEFAULT_DELETED_BY_ID);

        // Get all the topicsList where deletedById not equals to UPDATED_DELETED_BY_ID
        defaultTopicsShouldBeFound("deletedById.notEquals=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedById in DEFAULT_DELETED_BY_ID or UPDATED_DELETED_BY_ID
        defaultTopicsShouldBeFound("deletedById.in=" + DEFAULT_DELETED_BY_ID + "," + UPDATED_DELETED_BY_ID);

        // Get all the topicsList where deletedById equals to UPDATED_DELETED_BY_ID
        defaultTopicsShouldNotBeFound("deletedById.in=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedById is not null
        defaultTopicsShouldBeFound("deletedById.specified=true");

        // Get all the topicsList where deletedById is null
        defaultTopicsShouldNotBeFound("deletedById.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByDeletedByIdContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedById contains DEFAULT_DELETED_BY_ID
        defaultTopicsShouldBeFound("deletedById.contains=" + DEFAULT_DELETED_BY_ID);

        // Get all the topicsList where deletedById contains UPDATED_DELETED_BY_ID
        defaultTopicsShouldNotBeFound("deletedById.contains=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByDeletedByIdNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where deletedById does not contain DEFAULT_DELETED_BY_ID
        defaultTopicsShouldNotBeFound("deletedById.doesNotContain=" + DEFAULT_DELETED_BY_ID);

        // Get all the topicsList where deletedById does not contain UPDATED_DELETED_BY_ID
        defaultTopicsShouldBeFound("deletedById.doesNotContain=" + UPDATED_DELETED_BY_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount equals to DEFAULT_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.equals=" + DEFAULT_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount equals to UPDATED_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.equals=" + UPDATED_PARTICIPANT_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount not equals to DEFAULT_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.notEquals=" + DEFAULT_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount not equals to UPDATED_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.notEquals=" + UPDATED_PARTICIPANT_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount in DEFAULT_PARTICIPANT_COUNT or UPDATED_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.in=" + DEFAULT_PARTICIPANT_COUNT + "," + UPDATED_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount equals to UPDATED_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.in=" + UPDATED_PARTICIPANT_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount is not null
        defaultTopicsShouldBeFound("participantCount.specified=true");

        // Get all the topicsList where participantCount is null
        defaultTopicsShouldNotBeFound("participantCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount is greater than or equal to DEFAULT_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.greaterThanOrEqual=" + DEFAULT_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount is greater than or equal to UPDATED_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.greaterThanOrEqual=" + UPDATED_PARTICIPANT_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount is less than or equal to DEFAULT_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.lessThanOrEqual=" + DEFAULT_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount is less than or equal to SMALLER_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.lessThanOrEqual=" + SMALLER_PARTICIPANT_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount is less than DEFAULT_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.lessThan=" + DEFAULT_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount is less than UPDATED_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.lessThan=" + UPDATED_PARTICIPANT_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByParticipantCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where participantCount is greater than DEFAULT_PARTICIPANT_COUNT
        defaultTopicsShouldNotBeFound("participantCount.greaterThan=" + DEFAULT_PARTICIPANT_COUNT);

        // Get all the topicsList where participantCount is greater than SMALLER_PARTICIPANT_COUNT
        defaultTopicsShouldBeFound("participantCount.greaterThan=" + SMALLER_PARTICIPANT_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByWordCountIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount equals to DEFAULT_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.equals=" + DEFAULT_WORD_COUNT);

        // Get all the topicsList where wordCount equals to UPDATED_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.equals=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount not equals to DEFAULT_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.notEquals=" + DEFAULT_WORD_COUNT);

        // Get all the topicsList where wordCount not equals to UPDATED_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.notEquals=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount in DEFAULT_WORD_COUNT or UPDATED_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.in=" + DEFAULT_WORD_COUNT + "," + UPDATED_WORD_COUNT);

        // Get all the topicsList where wordCount equals to UPDATED_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.in=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount is not null
        defaultTopicsShouldBeFound("wordCount.specified=true");

        // Get all the topicsList where wordCount is null
        defaultTopicsShouldNotBeFound("wordCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount is greater than or equal to DEFAULT_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.greaterThanOrEqual=" + DEFAULT_WORD_COUNT);

        // Get all the topicsList where wordCount is greater than or equal to UPDATED_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.greaterThanOrEqual=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount is less than or equal to DEFAULT_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.lessThanOrEqual=" + DEFAULT_WORD_COUNT);

        // Get all the topicsList where wordCount is less than or equal to SMALLER_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.lessThanOrEqual=" + SMALLER_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount is less than DEFAULT_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.lessThan=" + DEFAULT_WORD_COUNT);

        // Get all the topicsList where wordCount is less than UPDATED_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.lessThan=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllTopicsByWordCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where wordCount is greater than DEFAULT_WORD_COUNT
        defaultTopicsShouldNotBeFound("wordCount.greaterThan=" + DEFAULT_WORD_COUNT);

        // Get all the topicsList where wordCount is greater than SMALLER_WORD_COUNT
        defaultTopicsShouldBeFound("wordCount.greaterThan=" + SMALLER_WORD_COUNT);
    }


    @Test
    @Transactional
    public void getAllTopicsByExcerptIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where excerpt equals to DEFAULT_EXCERPT
        defaultTopicsShouldBeFound("excerpt.equals=" + DEFAULT_EXCERPT);

        // Get all the topicsList where excerpt equals to UPDATED_EXCERPT
        defaultTopicsShouldNotBeFound("excerpt.equals=" + UPDATED_EXCERPT);
    }

    @Test
    @Transactional
    public void getAllTopicsByExcerptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where excerpt not equals to DEFAULT_EXCERPT
        defaultTopicsShouldNotBeFound("excerpt.notEquals=" + DEFAULT_EXCERPT);

        // Get all the topicsList where excerpt not equals to UPDATED_EXCERPT
        defaultTopicsShouldBeFound("excerpt.notEquals=" + UPDATED_EXCERPT);
    }

    @Test
    @Transactional
    public void getAllTopicsByExcerptIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where excerpt in DEFAULT_EXCERPT or UPDATED_EXCERPT
        defaultTopicsShouldBeFound("excerpt.in=" + DEFAULT_EXCERPT + "," + UPDATED_EXCERPT);

        // Get all the topicsList where excerpt equals to UPDATED_EXCERPT
        defaultTopicsShouldNotBeFound("excerpt.in=" + UPDATED_EXCERPT);
    }

    @Test
    @Transactional
    public void getAllTopicsByExcerptIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where excerpt is not null
        defaultTopicsShouldBeFound("excerpt.specified=true");

        // Get all the topicsList where excerpt is null
        defaultTopicsShouldNotBeFound("excerpt.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByExcerptContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where excerpt contains DEFAULT_EXCERPT
        defaultTopicsShouldBeFound("excerpt.contains=" + DEFAULT_EXCERPT);

        // Get all the topicsList where excerpt contains UPDATED_EXCERPT
        defaultTopicsShouldNotBeFound("excerpt.contains=" + UPDATED_EXCERPT);
    }

    @Test
    @Transactional
    public void getAllTopicsByExcerptNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where excerpt does not contain DEFAULT_EXCERPT
        defaultTopicsShouldNotBeFound("excerpt.doesNotContain=" + DEFAULT_EXCERPT);

        // Get all the topicsList where excerpt does not contain UPDATED_EXCERPT
        defaultTopicsShouldBeFound("excerpt.doesNotContain=" + UPDATED_EXCERPT);
    }


    @Test
    @Transactional
    public void getAllTopicsByPinnedGloballyIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedGlobally equals to DEFAULT_PINNED_GLOBALLY
        defaultTopicsShouldBeFound("pinnedGlobally.equals=" + DEFAULT_PINNED_GLOBALLY);

        // Get all the topicsList where pinnedGlobally equals to UPDATED_PINNED_GLOBALLY
        defaultTopicsShouldNotBeFound("pinnedGlobally.equals=" + UPDATED_PINNED_GLOBALLY);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedGloballyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedGlobally not equals to DEFAULT_PINNED_GLOBALLY
        defaultTopicsShouldNotBeFound("pinnedGlobally.notEquals=" + DEFAULT_PINNED_GLOBALLY);

        // Get all the topicsList where pinnedGlobally not equals to UPDATED_PINNED_GLOBALLY
        defaultTopicsShouldBeFound("pinnedGlobally.notEquals=" + UPDATED_PINNED_GLOBALLY);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedGloballyIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedGlobally in DEFAULT_PINNED_GLOBALLY or UPDATED_PINNED_GLOBALLY
        defaultTopicsShouldBeFound("pinnedGlobally.in=" + DEFAULT_PINNED_GLOBALLY + "," + UPDATED_PINNED_GLOBALLY);

        // Get all the topicsList where pinnedGlobally equals to UPDATED_PINNED_GLOBALLY
        defaultTopicsShouldNotBeFound("pinnedGlobally.in=" + UPDATED_PINNED_GLOBALLY);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedGloballyIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedGlobally is not null
        defaultTopicsShouldBeFound("pinnedGlobally.specified=true");

        // Get all the topicsList where pinnedGlobally is null
        defaultTopicsShouldNotBeFound("pinnedGlobally.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedUntil equals to DEFAULT_PINNED_UNTIL
        defaultTopicsShouldBeFound("pinnedUntil.equals=" + DEFAULT_PINNED_UNTIL);

        // Get all the topicsList where pinnedUntil equals to UPDATED_PINNED_UNTIL
        defaultTopicsShouldNotBeFound("pinnedUntil.equals=" + UPDATED_PINNED_UNTIL);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedUntilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedUntil not equals to DEFAULT_PINNED_UNTIL
        defaultTopicsShouldNotBeFound("pinnedUntil.notEquals=" + DEFAULT_PINNED_UNTIL);

        // Get all the topicsList where pinnedUntil not equals to UPDATED_PINNED_UNTIL
        defaultTopicsShouldBeFound("pinnedUntil.notEquals=" + UPDATED_PINNED_UNTIL);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedUntilIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedUntil in DEFAULT_PINNED_UNTIL or UPDATED_PINNED_UNTIL
        defaultTopicsShouldBeFound("pinnedUntil.in=" + DEFAULT_PINNED_UNTIL + "," + UPDATED_PINNED_UNTIL);

        // Get all the topicsList where pinnedUntil equals to UPDATED_PINNED_UNTIL
        defaultTopicsShouldNotBeFound("pinnedUntil.in=" + UPDATED_PINNED_UNTIL);
    }

    @Test
    @Transactional
    public void getAllTopicsByPinnedUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where pinnedUntil is not null
        defaultTopicsShouldBeFound("pinnedUntil.specified=true");

        // Get all the topicsList where pinnedUntil is null
        defaultTopicsShouldNotBeFound("pinnedUntil.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByFancyTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where fancyTitle equals to DEFAULT_FANCY_TITLE
        defaultTopicsShouldBeFound("fancyTitle.equals=" + DEFAULT_FANCY_TITLE);

        // Get all the topicsList where fancyTitle equals to UPDATED_FANCY_TITLE
        defaultTopicsShouldNotBeFound("fancyTitle.equals=" + UPDATED_FANCY_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByFancyTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where fancyTitle not equals to DEFAULT_FANCY_TITLE
        defaultTopicsShouldNotBeFound("fancyTitle.notEquals=" + DEFAULT_FANCY_TITLE);

        // Get all the topicsList where fancyTitle not equals to UPDATED_FANCY_TITLE
        defaultTopicsShouldBeFound("fancyTitle.notEquals=" + UPDATED_FANCY_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByFancyTitleIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where fancyTitle in DEFAULT_FANCY_TITLE or UPDATED_FANCY_TITLE
        defaultTopicsShouldBeFound("fancyTitle.in=" + DEFAULT_FANCY_TITLE + "," + UPDATED_FANCY_TITLE);

        // Get all the topicsList where fancyTitle equals to UPDATED_FANCY_TITLE
        defaultTopicsShouldNotBeFound("fancyTitle.in=" + UPDATED_FANCY_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByFancyTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where fancyTitle is not null
        defaultTopicsShouldBeFound("fancyTitle.specified=true");

        // Get all the topicsList where fancyTitle is null
        defaultTopicsShouldNotBeFound("fancyTitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByFancyTitleContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where fancyTitle contains DEFAULT_FANCY_TITLE
        defaultTopicsShouldBeFound("fancyTitle.contains=" + DEFAULT_FANCY_TITLE);

        // Get all the topicsList where fancyTitle contains UPDATED_FANCY_TITLE
        defaultTopicsShouldNotBeFound("fancyTitle.contains=" + UPDATED_FANCY_TITLE);
    }

    @Test
    @Transactional
    public void getAllTopicsByFancyTitleNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where fancyTitle does not contain DEFAULT_FANCY_TITLE
        defaultTopicsShouldNotBeFound("fancyTitle.doesNotContain=" + DEFAULT_FANCY_TITLE);

        // Get all the topicsList where fancyTitle does not contain UPDATED_FANCY_TITLE
        defaultTopicsShouldBeFound("fancyTitle.doesNotContain=" + UPDATED_FANCY_TITLE);
    }


    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber equals to DEFAULT_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.equals=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber equals to UPDATED_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.equals=" + UPDATED_HIGHEST_STAFF_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber not equals to DEFAULT_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.notEquals=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber not equals to UPDATED_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.notEquals=" + UPDATED_HIGHEST_STAFF_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber in DEFAULT_HIGHEST_STAFF_POST_NUMBER or UPDATED_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.in=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER + "," + UPDATED_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber equals to UPDATED_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.in=" + UPDATED_HIGHEST_STAFF_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber is not null
        defaultTopicsShouldBeFound("highestStaffPostNumber.specified=true");

        // Get all the topicsList where highestStaffPostNumber is null
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber is greater than or equal to DEFAULT_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.greaterThanOrEqual=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber is greater than or equal to UPDATED_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.greaterThanOrEqual=" + UPDATED_HIGHEST_STAFF_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber is less than or equal to DEFAULT_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.lessThanOrEqual=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber is less than or equal to SMALLER_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.lessThanOrEqual=" + SMALLER_HIGHEST_STAFF_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber is less than DEFAULT_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.lessThan=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber is less than UPDATED_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.lessThan=" + UPDATED_HIGHEST_STAFF_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTopicsByHighestStaffPostNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where highestStaffPostNumber is greater than DEFAULT_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldNotBeFound("highestStaffPostNumber.greaterThan=" + DEFAULT_HIGHEST_STAFF_POST_NUMBER);

        // Get all the topicsList where highestStaffPostNumber is greater than SMALLER_HIGHEST_STAFF_POST_NUMBER
        defaultTopicsShouldBeFound("highestStaffPostNumber.greaterThan=" + SMALLER_HIGHEST_STAFF_POST_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTopicsByFeaturedLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredLink equals to DEFAULT_FEATURED_LINK
        defaultTopicsShouldBeFound("featuredLink.equals=" + DEFAULT_FEATURED_LINK);

        // Get all the topicsList where featuredLink equals to UPDATED_FEATURED_LINK
        defaultTopicsShouldNotBeFound("featuredLink.equals=" + UPDATED_FEATURED_LINK);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredLink not equals to DEFAULT_FEATURED_LINK
        defaultTopicsShouldNotBeFound("featuredLink.notEquals=" + DEFAULT_FEATURED_LINK);

        // Get all the topicsList where featuredLink not equals to UPDATED_FEATURED_LINK
        defaultTopicsShouldBeFound("featuredLink.notEquals=" + UPDATED_FEATURED_LINK);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedLinkIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredLink in DEFAULT_FEATURED_LINK or UPDATED_FEATURED_LINK
        defaultTopicsShouldBeFound("featuredLink.in=" + DEFAULT_FEATURED_LINK + "," + UPDATED_FEATURED_LINK);

        // Get all the topicsList where featuredLink equals to UPDATED_FEATURED_LINK
        defaultTopicsShouldNotBeFound("featuredLink.in=" + UPDATED_FEATURED_LINK);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredLink is not null
        defaultTopicsShouldBeFound("featuredLink.specified=true");

        // Get all the topicsList where featuredLink is null
        defaultTopicsShouldNotBeFound("featuredLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicsByFeaturedLinkContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredLink contains DEFAULT_FEATURED_LINK
        defaultTopicsShouldBeFound("featuredLink.contains=" + DEFAULT_FEATURED_LINK);

        // Get all the topicsList where featuredLink contains UPDATED_FEATURED_LINK
        defaultTopicsShouldNotBeFound("featuredLink.contains=" + UPDATED_FEATURED_LINK);
    }

    @Test
    @Transactional
    public void getAllTopicsByFeaturedLinkNotContainsSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where featuredLink does not contain DEFAULT_FEATURED_LINK
        defaultTopicsShouldNotBeFound("featuredLink.doesNotContain=" + DEFAULT_FEATURED_LINK);

        // Get all the topicsList where featuredLink does not contain UPDATED_FEATURED_LINK
        defaultTopicsShouldBeFound("featuredLink.doesNotContain=" + UPDATED_FEATURED_LINK);
    }


    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore equals to DEFAULT_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.equals=" + DEFAULT_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore equals to UPDATED_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.equals=" + UPDATED_REVIEWABLE_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore not equals to DEFAULT_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.notEquals=" + DEFAULT_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore not equals to UPDATED_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.notEquals=" + UPDATED_REVIEWABLE_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore in DEFAULT_REVIEWABLE_SCORE or UPDATED_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.in=" + DEFAULT_REVIEWABLE_SCORE + "," + UPDATED_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore equals to UPDATED_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.in=" + UPDATED_REVIEWABLE_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore is not null
        defaultTopicsShouldBeFound("reviewableScore.specified=true");

        // Get all the topicsList where reviewableScore is null
        defaultTopicsShouldNotBeFound("reviewableScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore is greater than or equal to DEFAULT_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.greaterThanOrEqual=" + DEFAULT_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore is greater than or equal to UPDATED_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.greaterThanOrEqual=" + UPDATED_REVIEWABLE_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore is less than or equal to DEFAULT_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.lessThanOrEqual=" + DEFAULT_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore is less than or equal to SMALLER_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.lessThanOrEqual=" + SMALLER_REVIEWABLE_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore is less than DEFAULT_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.lessThan=" + DEFAULT_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore is less than UPDATED_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.lessThan=" + UPDATED_REVIEWABLE_SCORE);
    }

    @Test
    @Transactional
    public void getAllTopicsByReviewableScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where reviewableScore is greater than DEFAULT_REVIEWABLE_SCORE
        defaultTopicsShouldNotBeFound("reviewableScore.greaterThan=" + DEFAULT_REVIEWABLE_SCORE);

        // Get all the topicsList where reviewableScore is greater than SMALLER_REVIEWABLE_SCORE
        defaultTopicsShouldBeFound("reviewableScore.greaterThan=" + SMALLER_REVIEWABLE_SCORE);
    }


    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId equals to DEFAULT_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.equals=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId equals to UPDATED_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.equals=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId not equals to DEFAULT_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.notEquals=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId not equals to UPDATED_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.notEquals=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId in DEFAULT_IMAGE_UPLOAD_ID or UPDATED_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.in=" + DEFAULT_IMAGE_UPLOAD_ID + "," + UPDATED_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId equals to UPDATED_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.in=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId is not null
        defaultTopicsShouldBeFound("imageUploadId.specified=true");

        // Get all the topicsList where imageUploadId is null
        defaultTopicsShouldNotBeFound("imageUploadId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId is greater than or equal to DEFAULT_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.greaterThanOrEqual=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId is greater than or equal to UPDATED_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.greaterThanOrEqual=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId is less than or equal to DEFAULT_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.lessThanOrEqual=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId is less than or equal to SMALLER_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.lessThanOrEqual=" + SMALLER_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId is less than DEFAULT_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.lessThan=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId is less than UPDATED_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.lessThan=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllTopicsByImageUploadIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where imageUploadId is greater than DEFAULT_IMAGE_UPLOAD_ID
        defaultTopicsShouldNotBeFound("imageUploadId.greaterThan=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the topicsList where imageUploadId is greater than SMALLER_IMAGE_UPLOAD_ID
        defaultTopicsShouldBeFound("imageUploadId.greaterThan=" + SMALLER_IMAGE_UPLOAD_ID);
    }


    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds equals to DEFAULT_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.equals=" + DEFAULT_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds equals to UPDATED_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.equals=" + UPDATED_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds not equals to DEFAULT_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.notEquals=" + DEFAULT_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds not equals to UPDATED_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.notEquals=" + UPDATED_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsInShouldWork() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds in DEFAULT_SLOW_MODE_SECONDS or UPDATED_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.in=" + DEFAULT_SLOW_MODE_SECONDS + "," + UPDATED_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds equals to UPDATED_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.in=" + UPDATED_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds is not null
        defaultTopicsShouldBeFound("slowModeSeconds.specified=true");

        // Get all the topicsList where slowModeSeconds is null
        defaultTopicsShouldNotBeFound("slowModeSeconds.specified=false");
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds is greater than or equal to DEFAULT_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.greaterThanOrEqual=" + DEFAULT_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds is greater than or equal to UPDATED_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.greaterThanOrEqual=" + UPDATED_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds is less than or equal to DEFAULT_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.lessThanOrEqual=" + DEFAULT_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds is less than or equal to SMALLER_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.lessThanOrEqual=" + SMALLER_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsLessThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds is less than DEFAULT_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.lessThan=" + DEFAULT_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds is less than UPDATED_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.lessThan=" + UPDATED_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void getAllTopicsBySlowModeSecondsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        // Get all the topicsList where slowModeSeconds is greater than DEFAULT_SLOW_MODE_SECONDS
        defaultTopicsShouldNotBeFound("slowModeSeconds.greaterThan=" + DEFAULT_SLOW_MODE_SECONDS);

        // Get all the topicsList where slowModeSeconds is greater than SMALLER_SLOW_MODE_SECONDS
        defaultTopicsShouldBeFound("slowModeSeconds.greaterThan=" + SMALLER_SLOW_MODE_SECONDS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTopicsShouldBeFound(String filter) throws Exception {
        restTopicsMockMvc.perform(get("/api/topics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topics.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].lastPostedAt").value(hasItem(DEFAULT_LAST_POSTED_AT.toString())))
            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS)))
            .andExpect(jsonPath("$.[*].postsCount").value(hasItem(DEFAULT_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].lastPostUserId").value(hasItem(DEFAULT_LAST_POST_USER_ID)))
            .andExpect(jsonPath("$.[*].replyCount").value(hasItem(DEFAULT_REPLY_COUNT)))
            .andExpect(jsonPath("$.[*].featuredUser1Id").value(hasItem(DEFAULT_FEATURED_USER_1_ID)))
            .andExpect(jsonPath("$.[*].featuredUser2Id").value(hasItem(DEFAULT_FEATURED_USER_2_ID)))
            .andExpect(jsonPath("$.[*].featuredUser3Id").value(hasItem(DEFAULT_FEATURED_USER_3_ID)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].highestPostNumber").value(hasItem(DEFAULT_HIGHEST_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].likeCount").value(hasItem(DEFAULT_LIKE_COUNT)))
            .andExpect(jsonPath("$.[*].incomingLinkCount").value(hasItem(DEFAULT_INCOMING_LINK_COUNT)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].moderatorPostsCount").value(hasItem(DEFAULT_MODERATOR_POSTS_COUNT)))
            .andExpect(jsonPath("$.[*].closed").value(hasItem(DEFAULT_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].archived").value(hasItem(DEFAULT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].bumpedAt").value(hasItem(DEFAULT_BUMPED_AT.toString())))
            .andExpect(jsonPath("$.[*].hasSummary").value(hasItem(DEFAULT_HAS_SUMMARY.booleanValue())))
            .andExpect(jsonPath("$.[*].archetype").value(hasItem(DEFAULT_ARCHETYPE)))
            .andExpect(jsonPath("$.[*].featuredUser4Id").value(hasItem(DEFAULT_FEATURED_USER_4_ID)))
            .andExpect(jsonPath("$.[*].notifyModeratorsCount").value(hasItem(DEFAULT_NOTIFY_MODERATORS_COUNT)))
            .andExpect(jsonPath("$.[*].spamCount").value(hasItem(DEFAULT_SPAM_COUNT)))
            .andExpect(jsonPath("$.[*].pinnedAt").value(hasItem(DEFAULT_PINNED_AT.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentRank").value(hasItem(DEFAULT_PERCENT_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)))
            .andExpect(jsonPath("$.[*].participantCount").value(hasItem(DEFAULT_PARTICIPANT_COUNT)))
            .andExpect(jsonPath("$.[*].wordCount").value(hasItem(DEFAULT_WORD_COUNT)))
            .andExpect(jsonPath("$.[*].excerpt").value(hasItem(DEFAULT_EXCERPT)))
            .andExpect(jsonPath("$.[*].pinnedGlobally").value(hasItem(DEFAULT_PINNED_GLOBALLY.booleanValue())))
            .andExpect(jsonPath("$.[*].pinnedUntil").value(hasItem(DEFAULT_PINNED_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].fancyTitle").value(hasItem(DEFAULT_FANCY_TITLE)))
            .andExpect(jsonPath("$.[*].highestStaffPostNumber").value(hasItem(DEFAULT_HIGHEST_STAFF_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].featuredLink").value(hasItem(DEFAULT_FEATURED_LINK)))
            .andExpect(jsonPath("$.[*].reviewableScore").value(hasItem(DEFAULT_REVIEWABLE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageUploadId").value(hasItem(DEFAULT_IMAGE_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].slowModeSeconds").value(hasItem(DEFAULT_SLOW_MODE_SECONDS)));

        // Check, that the count call also returns 1
        restTopicsMockMvc.perform(get("/api/topics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTopicsShouldNotBeFound(String filter) throws Exception {
        restTopicsMockMvc.perform(get("/api/topics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTopicsMockMvc.perform(get("/api/topics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTopics() throws Exception {
        // Get the topics
        restTopicsMockMvc.perform(get("/api/topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopics() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        int databaseSizeBeforeUpdate = topicsRepository.findAll().size();

        // Update the topics
        Topics updatedTopics = topicsRepository.findById(topics.getId()).get();
        // Disconnect from session so that the updates on updatedTopics are not directly saved in db
        em.detach(updatedTopics);
        updatedTopics
            .title(UPDATED_TITLE)
            .lastPostedAt(UPDATED_LAST_POSTED_AT)
            .views(UPDATED_VIEWS)
            .postsCount(UPDATED_POSTS_COUNT)
            .userId(UPDATED_USER_ID)
            .lastPostUserId(UPDATED_LAST_POST_USER_ID)
            .replyCount(UPDATED_REPLY_COUNT)
            .featuredUser1Id(UPDATED_FEATURED_USER_1_ID)
            .featuredUser2Id(UPDATED_FEATURED_USER_2_ID)
            .featuredUser3Id(UPDATED_FEATURED_USER_3_ID)
            .deletedAt(UPDATED_DELETED_AT)
            .highestPostNumber(UPDATED_HIGHEST_POST_NUMBER)
            .likeCount(UPDATED_LIKE_COUNT)
            .incomingLinkCount(UPDATED_INCOMING_LINK_COUNT)
            .categoryId(UPDATED_CATEGORY_ID)
            .visible(UPDATED_VISIBLE)
            .moderatorPostsCount(UPDATED_MODERATOR_POSTS_COUNT)
            .closed(UPDATED_CLOSED)
            .archived(UPDATED_ARCHIVED)
            .bumpedAt(UPDATED_BUMPED_AT)
            .hasSummary(UPDATED_HAS_SUMMARY)
            .archetype(UPDATED_ARCHETYPE)
            .featuredUser4Id(UPDATED_FEATURED_USER_4_ID)
            .notifyModeratorsCount(UPDATED_NOTIFY_MODERATORS_COUNT)
            .spamCount(UPDATED_SPAM_COUNT)
            .pinnedAt(UPDATED_PINNED_AT)
            .score(UPDATED_SCORE)
            .percentRank(UPDATED_PERCENT_RANK)
            .subtype(UPDATED_SUBTYPE)
            .slug(UPDATED_SLUG)
            .deletedById(UPDATED_DELETED_BY_ID)
            .participantCount(UPDATED_PARTICIPANT_COUNT)
            .wordCount(UPDATED_WORD_COUNT)
            .excerpt(UPDATED_EXCERPT)
            .pinnedGlobally(UPDATED_PINNED_GLOBALLY)
            .pinnedUntil(UPDATED_PINNED_UNTIL)
            .fancyTitle(UPDATED_FANCY_TITLE)
            .highestStaffPostNumber(UPDATED_HIGHEST_STAFF_POST_NUMBER)
            .featuredLink(UPDATED_FEATURED_LINK)
            .reviewableScore(UPDATED_REVIEWABLE_SCORE)
            .imageUploadId(UPDATED_IMAGE_UPLOAD_ID)
            .slowModeSeconds(UPDATED_SLOW_MODE_SECONDS);
        TopicsDTO topicsDTO = topicsMapper.toDto(updatedTopics);

        restTopicsMockMvc.perform(put("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isOk());

        // Validate the Topics in the database
        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeUpdate);
        Topics testTopics = topicsList.get(topicsList.size() - 1);
        assertThat(testTopics.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTopics.getLastPostedAt()).isEqualTo(UPDATED_LAST_POSTED_AT);
        assertThat(testTopics.getViews()).isEqualTo(UPDATED_VIEWS);
        assertThat(testTopics.getPostsCount()).isEqualTo(UPDATED_POSTS_COUNT);
        assertThat(testTopics.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopics.getLastPostUserId()).isEqualTo(UPDATED_LAST_POST_USER_ID);
        assertThat(testTopics.getReplyCount()).isEqualTo(UPDATED_REPLY_COUNT);
        assertThat(testTopics.getFeaturedUser1Id()).isEqualTo(UPDATED_FEATURED_USER_1_ID);
        assertThat(testTopics.getFeaturedUser2Id()).isEqualTo(UPDATED_FEATURED_USER_2_ID);
        assertThat(testTopics.getFeaturedUser3Id()).isEqualTo(UPDATED_FEATURED_USER_3_ID);
        assertThat(testTopics.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testTopics.getHighestPostNumber()).isEqualTo(UPDATED_HIGHEST_POST_NUMBER);
        assertThat(testTopics.getLikeCount()).isEqualTo(UPDATED_LIKE_COUNT);
        assertThat(testTopics.getIncomingLinkCount()).isEqualTo(UPDATED_INCOMING_LINK_COUNT);
        assertThat(testTopics.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testTopics.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testTopics.getModeratorPostsCount()).isEqualTo(UPDATED_MODERATOR_POSTS_COUNT);
        assertThat(testTopics.isClosed()).isEqualTo(UPDATED_CLOSED);
        assertThat(testTopics.isArchived()).isEqualTo(UPDATED_ARCHIVED);
        assertThat(testTopics.getBumpedAt()).isEqualTo(UPDATED_BUMPED_AT);
        assertThat(testTopics.isHasSummary()).isEqualTo(UPDATED_HAS_SUMMARY);
        assertThat(testTopics.getArchetype()).isEqualTo(UPDATED_ARCHETYPE);
        assertThat(testTopics.getFeaturedUser4Id()).isEqualTo(UPDATED_FEATURED_USER_4_ID);
        assertThat(testTopics.getNotifyModeratorsCount()).isEqualTo(UPDATED_NOTIFY_MODERATORS_COUNT);
        assertThat(testTopics.getSpamCount()).isEqualTo(UPDATED_SPAM_COUNT);
        assertThat(testTopics.getPinnedAt()).isEqualTo(UPDATED_PINNED_AT);
        assertThat(testTopics.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testTopics.getPercentRank()).isEqualTo(UPDATED_PERCENT_RANK);
        assertThat(testTopics.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testTopics.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testTopics.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
        assertThat(testTopics.getParticipantCount()).isEqualTo(UPDATED_PARTICIPANT_COUNT);
        assertThat(testTopics.getWordCount()).isEqualTo(UPDATED_WORD_COUNT);
        assertThat(testTopics.getExcerpt()).isEqualTo(UPDATED_EXCERPT);
        assertThat(testTopics.isPinnedGlobally()).isEqualTo(UPDATED_PINNED_GLOBALLY);
        assertThat(testTopics.getPinnedUntil()).isEqualTo(UPDATED_PINNED_UNTIL);
        assertThat(testTopics.getFancyTitle()).isEqualTo(UPDATED_FANCY_TITLE);
        assertThat(testTopics.getHighestStaffPostNumber()).isEqualTo(UPDATED_HIGHEST_STAFF_POST_NUMBER);
        assertThat(testTopics.getFeaturedLink()).isEqualTo(UPDATED_FEATURED_LINK);
        assertThat(testTopics.getReviewableScore()).isEqualTo(UPDATED_REVIEWABLE_SCORE);
        assertThat(testTopics.getImageUploadId()).isEqualTo(UPDATED_IMAGE_UPLOAD_ID);
        assertThat(testTopics.getSlowModeSeconds()).isEqualTo(UPDATED_SLOW_MODE_SECONDS);
    }

    @Test
    @Transactional
    public void updateNonExistingTopics() throws Exception {
        int databaseSizeBeforeUpdate = topicsRepository.findAll().size();

        // Create the Topics
        TopicsDTO topicsDTO = topicsMapper.toDto(topics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicsMockMvc.perform(put("/api/topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topics in the database
        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopics() throws Exception {
        // Initialize the database
        topicsRepository.saveAndFlush(topics);

        int databaseSizeBeforeDelete = topicsRepository.findAll().size();

        // Delete the topics
        restTopicsMockMvc.perform(delete("/api/topics/{id}", topics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Topics> topicsList = topicsRepository.findAll();
        assertThat(topicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
