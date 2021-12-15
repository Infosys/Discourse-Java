/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.BadgePosts;
import com.infy.repository.BadgePostsRepository;
import com.infy.service.BadgePostsService;
import com.infy.service.dto.BadgePostsDTO;
import com.infy.service.mapper.BadgePostsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link BadgePostsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BadgePostsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_NUMBER = 1L;
    private static final Long UPDATED_POST_NUMBER = 2L;

    private static final String DEFAULT_RAW = "AAAAAAAAAA";
    private static final String UPDATED_RAW = "BBBBBBBBBB";

    private static final String DEFAULT_COOKED = "AAAAAAAAAA";
    private static final String UPDATED_COOKED = "BBBBBBBBBB";

    private static final Long DEFAULT_REPLY_TO_POST_NUMBER = 1L;
    private static final Long UPDATED_REPLY_TO_POST_NUMBER = 2L;

    private static final Integer DEFAULT_REPLY_COUNT = 1;
    private static final Integer UPDATED_REPLY_COUNT = 2;

    private static final Integer DEFAULT_QUOTE_COUNT = 1;
    private static final Integer UPDATED_QUOTE_COUNT = 2;

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_OFF_TOPIC_COUNT = 1;
    private static final Integer UPDATED_OFF_TOPIC_COUNT = 2;

    private static final Integer DEFAULT_LIKE_COUNT = 1;
    private static final Integer UPDATED_LIKE_COUNT = 2;

    private static final Integer DEFAULT_INCOMING_LINK_COUNT = 1;
    private static final Integer UPDATED_INCOMING_LINK_COUNT = 2;

    private static final Integer DEFAULT_BOOKMARK_COUNT = 1;
    private static final Integer UPDATED_BOOKMARK_COUNT = 2;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final Integer DEFAULT_READS = 1;
    private static final Integer UPDATED_READS = 2;

    private static final Integer DEFAULT_POST_TYPE = 1;
    private static final Integer UPDATED_POST_TYPE = 2;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final String DEFAULT_LAST_EDITOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITOR_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIDDEN = false;
    private static final Boolean UPDATED_HIDDEN = true;

    private static final Long DEFAULT_HIDDEN_REASON_ID = 1L;
    private static final Long UPDATED_HIDDEN_REASON_ID = 2L;

    private static final Integer DEFAULT_NOTIFY_MODERATORS_COUNT = 1;
    private static final Integer UPDATED_NOTIFY_MODERATORS_COUNT = 2;

    private static final Integer DEFAULT_SPAM_COUNT = 1;
    private static final Integer UPDATED_SPAM_COUNT = 2;

    private static final Integer DEFAULT_ILLEGAL_COUNT = 1;
    private static final Integer UPDATED_ILLEGAL_COUNT = 2;

    private static final Integer DEFAULT_INAPPROPRIATE_COUNT = 1;
    private static final Integer UPDATED_INAPPROPRIATE_COUNT = 2;

    private static final Instant DEFAULT_LAST_VERSION_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_VERSION_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_USER_DELETED = false;
    private static final Boolean UPDATED_USER_DELETED = true;

    private static final String DEFAULT_REPLY_TO_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_TO_USER_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENT_RANK = 1D;
    private static final Double UPDATED_PERCENT_RANK = 2D;

    private static final Integer DEFAULT_NOTIFY_USER_COUNT = 1;
    private static final Integer UPDATED_NOTIFY_USER_COUNT = 2;

    private static final Integer DEFAULT_LIKE_SCORE = 1;
    private static final Integer UPDATED_LIKE_SCORE = 2;

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EDIT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_EDIT_REASON = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORD_COUNT = 1;
    private static final Integer UPDATED_WORD_COUNT = 2;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Integer DEFAULT_COOK_METHOD = 1;
    private static final Integer UPDATED_COOK_METHOD = 2;

    private static final Boolean DEFAULT_WIKI = false;
    private static final Boolean UPDATED_WIKI = true;

    private static final Instant DEFAULT_BAKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BAKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_BAKED_VERSION = 1;
    private static final Integer UPDATED_BAKED_VERSION = 2;

    private static final Instant DEFAULT_HIDDEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIDDEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SELF_EDITS = 1;
    private static final Integer UPDATED_SELF_EDITS = 2;

    private static final Boolean DEFAULT_REPLY_QUOTED = false;
    private static final Boolean UPDATED_REPLY_QUOTED = true;

    private static final Boolean DEFAULT_VIA_EMAIL = false;
    private static final Boolean UPDATED_VIA_EMAIL = true;

    private static final String DEFAULT_RAW_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_RAW_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUBLIC_VERSION = 1;
    private static final Integer UPDATED_PUBLIC_VERSION = 2;

    private static final String DEFAULT_ACTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCKED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCKED_BY_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_IMAGE_UPLOAD_ID = 1L;
    private static final Long UPDATED_IMAGE_UPLOAD_ID = 2L;

    @Autowired
    private BadgePostsRepository badgePostsRepository;

    @Autowired
    private BadgePostsMapper badgePostsMapper;

    @Autowired
    private BadgePostsService badgePostsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgePostsMockMvc;

    private BadgePosts badgePosts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgePosts createEntity(EntityManager em) {
        BadgePosts badgePosts = new BadgePosts()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .postNumber(DEFAULT_POST_NUMBER)
            .raw(DEFAULT_RAW)
            .cooked(DEFAULT_COOKED)
            .replyToPostNumber(DEFAULT_REPLY_TO_POST_NUMBER)
            .replyCount(DEFAULT_REPLY_COUNT)
            .quoteCount(DEFAULT_QUOTE_COUNT)
            .deletedAt(DEFAULT_DELETED_AT)
            .offTopicCount(DEFAULT_OFF_TOPIC_COUNT)
            .likeCount(DEFAULT_LIKE_COUNT)
            .incomingLinkCount(DEFAULT_INCOMING_LINK_COUNT)
            .bookmarkCount(DEFAULT_BOOKMARK_COUNT)
            .score(DEFAULT_SCORE)
            .reads(DEFAULT_READS)
            .postType(DEFAULT_POST_TYPE)
            .sortOrder(DEFAULT_SORT_ORDER)
            .lastEditorId(DEFAULT_LAST_EDITOR_ID)
            .hidden(DEFAULT_HIDDEN)
            .hiddenReasonId(DEFAULT_HIDDEN_REASON_ID)
            .notifyModeratorsCount(DEFAULT_NOTIFY_MODERATORS_COUNT)
            .spamCount(DEFAULT_SPAM_COUNT)
            .illegalCount(DEFAULT_ILLEGAL_COUNT)
            .inappropriateCount(DEFAULT_INAPPROPRIATE_COUNT)
            .lastVersionAt(DEFAULT_LAST_VERSION_AT)
            .userDeleted(DEFAULT_USER_DELETED)
            .replyToUserId(DEFAULT_REPLY_TO_USER_ID)
            .percentRank(DEFAULT_PERCENT_RANK)
            .notifyUserCount(DEFAULT_NOTIFY_USER_COUNT)
            .likeScore(DEFAULT_LIKE_SCORE)
            .deletedById(DEFAULT_DELETED_BY_ID)
            .editReason(DEFAULT_EDIT_REASON)
            .wordCount(DEFAULT_WORD_COUNT)
            .version(DEFAULT_VERSION)
            .cookMethod(DEFAULT_COOK_METHOD)
            .wiki(DEFAULT_WIKI)
            .bakedAt(DEFAULT_BAKED_AT)
            .bakedVersion(DEFAULT_BAKED_VERSION)
            .hiddenAt(DEFAULT_HIDDEN_AT)
            .selfEdits(DEFAULT_SELF_EDITS)
            .replyQuoted(DEFAULT_REPLY_QUOTED)
            .viaEmail(DEFAULT_VIA_EMAIL)
            .rawEmail(DEFAULT_RAW_EMAIL)
            .publicVersion(DEFAULT_PUBLIC_VERSION)
            .actionCode(DEFAULT_ACTION_CODE)
            .lockedById(DEFAULT_LOCKED_BY_ID)
            .imageUploadId(DEFAULT_IMAGE_UPLOAD_ID);
        return badgePosts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgePosts createUpdatedEntity(EntityManager em) {
        BadgePosts badgePosts = new BadgePosts()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .postNumber(UPDATED_POST_NUMBER)
            .raw(UPDATED_RAW)
            .cooked(UPDATED_COOKED)
            .replyToPostNumber(UPDATED_REPLY_TO_POST_NUMBER)
            .replyCount(UPDATED_REPLY_COUNT)
            .quoteCount(UPDATED_QUOTE_COUNT)
            .deletedAt(UPDATED_DELETED_AT)
            .offTopicCount(UPDATED_OFF_TOPIC_COUNT)
            .likeCount(UPDATED_LIKE_COUNT)
            .incomingLinkCount(UPDATED_INCOMING_LINK_COUNT)
            .bookmarkCount(UPDATED_BOOKMARK_COUNT)
            .score(UPDATED_SCORE)
            .reads(UPDATED_READS)
            .postType(UPDATED_POST_TYPE)
            .sortOrder(UPDATED_SORT_ORDER)
            .lastEditorId(UPDATED_LAST_EDITOR_ID)
            .hidden(UPDATED_HIDDEN)
            .hiddenReasonId(UPDATED_HIDDEN_REASON_ID)
            .notifyModeratorsCount(UPDATED_NOTIFY_MODERATORS_COUNT)
            .spamCount(UPDATED_SPAM_COUNT)
            .illegalCount(UPDATED_ILLEGAL_COUNT)
            .inappropriateCount(UPDATED_INAPPROPRIATE_COUNT)
            .lastVersionAt(UPDATED_LAST_VERSION_AT)
            .userDeleted(UPDATED_USER_DELETED)
            .replyToUserId(UPDATED_REPLY_TO_USER_ID)
            .percentRank(UPDATED_PERCENT_RANK)
            .notifyUserCount(UPDATED_NOTIFY_USER_COUNT)
            .likeScore(UPDATED_LIKE_SCORE)
            .deletedById(UPDATED_DELETED_BY_ID)
            .editReason(UPDATED_EDIT_REASON)
            .wordCount(UPDATED_WORD_COUNT)
            .version(UPDATED_VERSION)
            .cookMethod(UPDATED_COOK_METHOD)
            .wiki(UPDATED_WIKI)
            .bakedAt(UPDATED_BAKED_AT)
            .bakedVersion(UPDATED_BAKED_VERSION)
            .hiddenAt(UPDATED_HIDDEN_AT)
            .selfEdits(UPDATED_SELF_EDITS)
            .replyQuoted(UPDATED_REPLY_QUOTED)
            .viaEmail(UPDATED_VIA_EMAIL)
            .rawEmail(UPDATED_RAW_EMAIL)
            .publicVersion(UPDATED_PUBLIC_VERSION)
            .actionCode(UPDATED_ACTION_CODE)
            .lockedById(UPDATED_LOCKED_BY_ID)
            .imageUploadId(UPDATED_IMAGE_UPLOAD_ID);
        return badgePosts;
    }

    @BeforeEach
    public void initTest() {
        badgePosts = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgePosts() throws Exception {
        int databaseSizeBeforeCreate = badgePostsRepository.findAll().size();
        // Create the BadgePosts
        BadgePostsDTO badgePostsDTO = badgePostsMapper.toDto(badgePosts);
        restBadgePostsMockMvc.perform(post("/api/badge-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgePostsDTO)))
            .andExpect(status().isCreated());

        // Validate the BadgePosts in the database
        List<BadgePosts> badgePostsList = badgePostsRepository.findAll();
        assertThat(badgePostsList).hasSize(databaseSizeBeforeCreate + 1);
        BadgePosts testBadgePosts = badgePostsList.get(badgePostsList.size() - 1);
        assertThat(testBadgePosts.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBadgePosts.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testBadgePosts.getPostNumber()).isEqualTo(DEFAULT_POST_NUMBER);
        assertThat(testBadgePosts.getRaw()).isEqualTo(DEFAULT_RAW);
        assertThat(testBadgePosts.getCooked()).isEqualTo(DEFAULT_COOKED);
        assertThat(testBadgePosts.getReplyToPostNumber()).isEqualTo(DEFAULT_REPLY_TO_POST_NUMBER);
        assertThat(testBadgePosts.getReplyCount()).isEqualTo(DEFAULT_REPLY_COUNT);
        assertThat(testBadgePosts.getQuoteCount()).isEqualTo(DEFAULT_QUOTE_COUNT);
        assertThat(testBadgePosts.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testBadgePosts.getOffTopicCount()).isEqualTo(DEFAULT_OFF_TOPIC_COUNT);
        assertThat(testBadgePosts.getLikeCount()).isEqualTo(DEFAULT_LIKE_COUNT);
        assertThat(testBadgePosts.getIncomingLinkCount()).isEqualTo(DEFAULT_INCOMING_LINK_COUNT);
        assertThat(testBadgePosts.getBookmarkCount()).isEqualTo(DEFAULT_BOOKMARK_COUNT);
        assertThat(testBadgePosts.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testBadgePosts.getReads()).isEqualTo(DEFAULT_READS);
        assertThat(testBadgePosts.getPostType()).isEqualTo(DEFAULT_POST_TYPE);
        assertThat(testBadgePosts.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testBadgePosts.getLastEditorId()).isEqualTo(DEFAULT_LAST_EDITOR_ID);
        assertThat(testBadgePosts.isHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testBadgePosts.getHiddenReasonId()).isEqualTo(DEFAULT_HIDDEN_REASON_ID);
        assertThat(testBadgePosts.getNotifyModeratorsCount()).isEqualTo(DEFAULT_NOTIFY_MODERATORS_COUNT);
        assertThat(testBadgePosts.getSpamCount()).isEqualTo(DEFAULT_SPAM_COUNT);
        assertThat(testBadgePosts.getIllegalCount()).isEqualTo(DEFAULT_ILLEGAL_COUNT);
        assertThat(testBadgePosts.getInappropriateCount()).isEqualTo(DEFAULT_INAPPROPRIATE_COUNT);
        assertThat(testBadgePosts.getLastVersionAt()).isEqualTo(DEFAULT_LAST_VERSION_AT);
        assertThat(testBadgePosts.isUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
        assertThat(testBadgePosts.getReplyToUserId()).isEqualTo(DEFAULT_REPLY_TO_USER_ID);
        assertThat(testBadgePosts.getPercentRank()).isEqualTo(DEFAULT_PERCENT_RANK);
        assertThat(testBadgePosts.getNotifyUserCount()).isEqualTo(DEFAULT_NOTIFY_USER_COUNT);
        assertThat(testBadgePosts.getLikeScore()).isEqualTo(DEFAULT_LIKE_SCORE);
        assertThat(testBadgePosts.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
        assertThat(testBadgePosts.getEditReason()).isEqualTo(DEFAULT_EDIT_REASON);
        assertThat(testBadgePosts.getWordCount()).isEqualTo(DEFAULT_WORD_COUNT);
        assertThat(testBadgePosts.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testBadgePosts.getCookMethod()).isEqualTo(DEFAULT_COOK_METHOD);
        assertThat(testBadgePosts.isWiki()).isEqualTo(DEFAULT_WIKI);
        assertThat(testBadgePosts.getBakedAt()).isEqualTo(DEFAULT_BAKED_AT);
        assertThat(testBadgePosts.getBakedVersion()).isEqualTo(DEFAULT_BAKED_VERSION);
        assertThat(testBadgePosts.getHiddenAt()).isEqualTo(DEFAULT_HIDDEN_AT);
        assertThat(testBadgePosts.getSelfEdits()).isEqualTo(DEFAULT_SELF_EDITS);
        assertThat(testBadgePosts.isReplyQuoted()).isEqualTo(DEFAULT_REPLY_QUOTED);
        assertThat(testBadgePosts.isViaEmail()).isEqualTo(DEFAULT_VIA_EMAIL);
        assertThat(testBadgePosts.getRawEmail()).isEqualTo(DEFAULT_RAW_EMAIL);
        assertThat(testBadgePosts.getPublicVersion()).isEqualTo(DEFAULT_PUBLIC_VERSION);
        assertThat(testBadgePosts.getActionCode()).isEqualTo(DEFAULT_ACTION_CODE);
        assertThat(testBadgePosts.getLockedById()).isEqualTo(DEFAULT_LOCKED_BY_ID);
        assertThat(testBadgePosts.getImageUploadId()).isEqualTo(DEFAULT_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void createBadgePostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgePostsRepository.findAll().size();

        // Create the BadgePosts with an existing ID
        badgePosts.setId(1L);
        BadgePostsDTO badgePostsDTO = badgePostsMapper.toDto(badgePosts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgePostsMockMvc.perform(post("/api/badge-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgePostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BadgePosts in the database
        List<BadgePosts> badgePostsList = badgePostsRepository.findAll();
        assertThat(badgePostsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBadgePosts() throws Exception {
        // Initialize the database
        badgePostsRepository.saveAndFlush(badgePosts);

        // Get all the badgePostsList
        restBadgePostsMockMvc.perform(get("/api/badge-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgePosts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postNumber").value(hasItem(DEFAULT_POST_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].raw").value(hasItem(DEFAULT_RAW.toString())))
            .andExpect(jsonPath("$.[*].cooked").value(hasItem(DEFAULT_COOKED)))
            .andExpect(jsonPath("$.[*].replyToPostNumber").value(hasItem(DEFAULT_REPLY_TO_POST_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].replyCount").value(hasItem(DEFAULT_REPLY_COUNT)))
            .andExpect(jsonPath("$.[*].quoteCount").value(hasItem(DEFAULT_QUOTE_COUNT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].offTopicCount").value(hasItem(DEFAULT_OFF_TOPIC_COUNT)))
            .andExpect(jsonPath("$.[*].likeCount").value(hasItem(DEFAULT_LIKE_COUNT)))
            .andExpect(jsonPath("$.[*].incomingLinkCount").value(hasItem(DEFAULT_INCOMING_LINK_COUNT)))
            .andExpect(jsonPath("$.[*].bookmarkCount").value(hasItem(DEFAULT_BOOKMARK_COUNT)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].reads").value(hasItem(DEFAULT_READS)))
            .andExpect(jsonPath("$.[*].postType").value(hasItem(DEFAULT_POST_TYPE)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].lastEditorId").value(hasItem(DEFAULT_LAST_EDITOR_ID)))
            .andExpect(jsonPath("$.[*].hidden").value(hasItem(DEFAULT_HIDDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].hiddenReasonId").value(hasItem(DEFAULT_HIDDEN_REASON_ID.intValue())))
            .andExpect(jsonPath("$.[*].notifyModeratorsCount").value(hasItem(DEFAULT_NOTIFY_MODERATORS_COUNT)))
            .andExpect(jsonPath("$.[*].spamCount").value(hasItem(DEFAULT_SPAM_COUNT)))
            .andExpect(jsonPath("$.[*].illegalCount").value(hasItem(DEFAULT_ILLEGAL_COUNT)))
            .andExpect(jsonPath("$.[*].inappropriateCount").value(hasItem(DEFAULT_INAPPROPRIATE_COUNT)))
            .andExpect(jsonPath("$.[*].lastVersionAt").value(hasItem(DEFAULT_LAST_VERSION_AT.toString())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].replyToUserId").value(hasItem(DEFAULT_REPLY_TO_USER_ID)))
            .andExpect(jsonPath("$.[*].percentRank").value(hasItem(DEFAULT_PERCENT_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].notifyUserCount").value(hasItem(DEFAULT_NOTIFY_USER_COUNT)))
            .andExpect(jsonPath("$.[*].likeScore").value(hasItem(DEFAULT_LIKE_SCORE)))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)))
            .andExpect(jsonPath("$.[*].editReason").value(hasItem(DEFAULT_EDIT_REASON)))
            .andExpect(jsonPath("$.[*].wordCount").value(hasItem(DEFAULT_WORD_COUNT)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].cookMethod").value(hasItem(DEFAULT_COOK_METHOD)))
            .andExpect(jsonPath("$.[*].wiki").value(hasItem(DEFAULT_WIKI.booleanValue())))
            .andExpect(jsonPath("$.[*].bakedAt").value(hasItem(DEFAULT_BAKED_AT.toString())))
            .andExpect(jsonPath("$.[*].bakedVersion").value(hasItem(DEFAULT_BAKED_VERSION)))
            .andExpect(jsonPath("$.[*].hiddenAt").value(hasItem(DEFAULT_HIDDEN_AT.toString())))
            .andExpect(jsonPath("$.[*].selfEdits").value(hasItem(DEFAULT_SELF_EDITS)))
            .andExpect(jsonPath("$.[*].replyQuoted").value(hasItem(DEFAULT_REPLY_QUOTED.booleanValue())))
            .andExpect(jsonPath("$.[*].viaEmail").value(hasItem(DEFAULT_VIA_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].rawEmail").value(hasItem(DEFAULT_RAW_EMAIL)))
            .andExpect(jsonPath("$.[*].publicVersion").value(hasItem(DEFAULT_PUBLIC_VERSION)))
            .andExpect(jsonPath("$.[*].actionCode").value(hasItem(DEFAULT_ACTION_CODE)))
            .andExpect(jsonPath("$.[*].lockedById").value(hasItem(DEFAULT_LOCKED_BY_ID)))
            .andExpect(jsonPath("$.[*].imageUploadId").value(hasItem(DEFAULT_IMAGE_UPLOAD_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBadgePosts() throws Exception {
        // Initialize the database
        badgePostsRepository.saveAndFlush(badgePosts);

        // Get the badgePosts
        restBadgePostsMockMvc.perform(get("/api/badge-posts/{id}", badgePosts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badgePosts.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postNumber").value(DEFAULT_POST_NUMBER.intValue()))
            .andExpect(jsonPath("$.raw").value(DEFAULT_RAW.toString()))
            .andExpect(jsonPath("$.cooked").value(DEFAULT_COOKED))
            .andExpect(jsonPath("$.replyToPostNumber").value(DEFAULT_REPLY_TO_POST_NUMBER.intValue()))
            .andExpect(jsonPath("$.replyCount").value(DEFAULT_REPLY_COUNT))
            .andExpect(jsonPath("$.quoteCount").value(DEFAULT_QUOTE_COUNT))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.offTopicCount").value(DEFAULT_OFF_TOPIC_COUNT))
            .andExpect(jsonPath("$.likeCount").value(DEFAULT_LIKE_COUNT))
            .andExpect(jsonPath("$.incomingLinkCount").value(DEFAULT_INCOMING_LINK_COUNT))
            .andExpect(jsonPath("$.bookmarkCount").value(DEFAULT_BOOKMARK_COUNT))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.reads").value(DEFAULT_READS))
            .andExpect(jsonPath("$.postType").value(DEFAULT_POST_TYPE))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.lastEditorId").value(DEFAULT_LAST_EDITOR_ID))
            .andExpect(jsonPath("$.hidden").value(DEFAULT_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.hiddenReasonId").value(DEFAULT_HIDDEN_REASON_ID.intValue()))
            .andExpect(jsonPath("$.notifyModeratorsCount").value(DEFAULT_NOTIFY_MODERATORS_COUNT))
            .andExpect(jsonPath("$.spamCount").value(DEFAULT_SPAM_COUNT))
            .andExpect(jsonPath("$.illegalCount").value(DEFAULT_ILLEGAL_COUNT))
            .andExpect(jsonPath("$.inappropriateCount").value(DEFAULT_INAPPROPRIATE_COUNT))
            .andExpect(jsonPath("$.lastVersionAt").value(DEFAULT_LAST_VERSION_AT.toString()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.booleanValue()))
            .andExpect(jsonPath("$.replyToUserId").value(DEFAULT_REPLY_TO_USER_ID))
            .andExpect(jsonPath("$.percentRank").value(DEFAULT_PERCENT_RANK.doubleValue()))
            .andExpect(jsonPath("$.notifyUserCount").value(DEFAULT_NOTIFY_USER_COUNT))
            .andExpect(jsonPath("$.likeScore").value(DEFAULT_LIKE_SCORE))
            .andExpect(jsonPath("$.deletedById").value(DEFAULT_DELETED_BY_ID))
            .andExpect(jsonPath("$.editReason").value(DEFAULT_EDIT_REASON))
            .andExpect(jsonPath("$.wordCount").value(DEFAULT_WORD_COUNT))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.cookMethod").value(DEFAULT_COOK_METHOD))
            .andExpect(jsonPath("$.wiki").value(DEFAULT_WIKI.booleanValue()))
            .andExpect(jsonPath("$.bakedAt").value(DEFAULT_BAKED_AT.toString()))
            .andExpect(jsonPath("$.bakedVersion").value(DEFAULT_BAKED_VERSION))
            .andExpect(jsonPath("$.hiddenAt").value(DEFAULT_HIDDEN_AT.toString()))
            .andExpect(jsonPath("$.selfEdits").value(DEFAULT_SELF_EDITS))
            .andExpect(jsonPath("$.replyQuoted").value(DEFAULT_REPLY_QUOTED.booleanValue()))
            .andExpect(jsonPath("$.viaEmail").value(DEFAULT_VIA_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.rawEmail").value(DEFAULT_RAW_EMAIL))
            .andExpect(jsonPath("$.publicVersion").value(DEFAULT_PUBLIC_VERSION))
            .andExpect(jsonPath("$.actionCode").value(DEFAULT_ACTION_CODE))
            .andExpect(jsonPath("$.lockedById").value(DEFAULT_LOCKED_BY_ID))
            .andExpect(jsonPath("$.imageUploadId").value(DEFAULT_IMAGE_UPLOAD_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBadgePosts() throws Exception {
        // Get the badgePosts
        restBadgePostsMockMvc.perform(get("/api/badge-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgePosts() throws Exception {
        // Initialize the database
        badgePostsRepository.saveAndFlush(badgePosts);

        int databaseSizeBeforeUpdate = badgePostsRepository.findAll().size();

        // Update the badgePosts
        BadgePosts updatedBadgePosts = badgePostsRepository.findById(badgePosts.getId()).get();
        // Disconnect from session so that the updates on updatedBadgePosts are not directly saved in db
        em.detach(updatedBadgePosts);
        updatedBadgePosts
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .postNumber(UPDATED_POST_NUMBER)
            .raw(UPDATED_RAW)
            .cooked(UPDATED_COOKED)
            .replyToPostNumber(UPDATED_REPLY_TO_POST_NUMBER)
            .replyCount(UPDATED_REPLY_COUNT)
            .quoteCount(UPDATED_QUOTE_COUNT)
            .deletedAt(UPDATED_DELETED_AT)
            .offTopicCount(UPDATED_OFF_TOPIC_COUNT)
            .likeCount(UPDATED_LIKE_COUNT)
            .incomingLinkCount(UPDATED_INCOMING_LINK_COUNT)
            .bookmarkCount(UPDATED_BOOKMARK_COUNT)
            .score(UPDATED_SCORE)
            .reads(UPDATED_READS)
            .postType(UPDATED_POST_TYPE)
            .sortOrder(UPDATED_SORT_ORDER)
            .lastEditorId(UPDATED_LAST_EDITOR_ID)
            .hidden(UPDATED_HIDDEN)
            .hiddenReasonId(UPDATED_HIDDEN_REASON_ID)
            .notifyModeratorsCount(UPDATED_NOTIFY_MODERATORS_COUNT)
            .spamCount(UPDATED_SPAM_COUNT)
            .illegalCount(UPDATED_ILLEGAL_COUNT)
            .inappropriateCount(UPDATED_INAPPROPRIATE_COUNT)
            .lastVersionAt(UPDATED_LAST_VERSION_AT)
            .userDeleted(UPDATED_USER_DELETED)
            .replyToUserId(UPDATED_REPLY_TO_USER_ID)
            .percentRank(UPDATED_PERCENT_RANK)
            .notifyUserCount(UPDATED_NOTIFY_USER_COUNT)
            .likeScore(UPDATED_LIKE_SCORE)
            .deletedById(UPDATED_DELETED_BY_ID)
            .editReason(UPDATED_EDIT_REASON)
            .wordCount(UPDATED_WORD_COUNT)
            .version(UPDATED_VERSION)
            .cookMethod(UPDATED_COOK_METHOD)
            .wiki(UPDATED_WIKI)
            .bakedAt(UPDATED_BAKED_AT)
            .bakedVersion(UPDATED_BAKED_VERSION)
            .hiddenAt(UPDATED_HIDDEN_AT)
            .selfEdits(UPDATED_SELF_EDITS)
            .replyQuoted(UPDATED_REPLY_QUOTED)
            .viaEmail(UPDATED_VIA_EMAIL)
            .rawEmail(UPDATED_RAW_EMAIL)
            .publicVersion(UPDATED_PUBLIC_VERSION)
            .actionCode(UPDATED_ACTION_CODE)
            .lockedById(UPDATED_LOCKED_BY_ID)
            .imageUploadId(UPDATED_IMAGE_UPLOAD_ID);
        BadgePostsDTO badgePostsDTO = badgePostsMapper.toDto(updatedBadgePosts);

        restBadgePostsMockMvc.perform(put("/api/badge-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgePostsDTO)))
            .andExpect(status().isOk());

        // Validate the BadgePosts in the database
        List<BadgePosts> badgePostsList = badgePostsRepository.findAll();
        assertThat(badgePostsList).hasSize(databaseSizeBeforeUpdate);
        BadgePosts testBadgePosts = badgePostsList.get(badgePostsList.size() - 1);
        assertThat(testBadgePosts.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBadgePosts.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testBadgePosts.getPostNumber()).isEqualTo(UPDATED_POST_NUMBER);
        assertThat(testBadgePosts.getRaw()).isEqualTo(UPDATED_RAW);
        assertThat(testBadgePosts.getCooked()).isEqualTo(UPDATED_COOKED);
        assertThat(testBadgePosts.getReplyToPostNumber()).isEqualTo(UPDATED_REPLY_TO_POST_NUMBER);
        assertThat(testBadgePosts.getReplyCount()).isEqualTo(UPDATED_REPLY_COUNT);
        assertThat(testBadgePosts.getQuoteCount()).isEqualTo(UPDATED_QUOTE_COUNT);
        assertThat(testBadgePosts.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testBadgePosts.getOffTopicCount()).isEqualTo(UPDATED_OFF_TOPIC_COUNT);
        assertThat(testBadgePosts.getLikeCount()).isEqualTo(UPDATED_LIKE_COUNT);
        assertThat(testBadgePosts.getIncomingLinkCount()).isEqualTo(UPDATED_INCOMING_LINK_COUNT);
        assertThat(testBadgePosts.getBookmarkCount()).isEqualTo(UPDATED_BOOKMARK_COUNT);
        assertThat(testBadgePosts.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testBadgePosts.getReads()).isEqualTo(UPDATED_READS);
        assertThat(testBadgePosts.getPostType()).isEqualTo(UPDATED_POST_TYPE);
        assertThat(testBadgePosts.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testBadgePosts.getLastEditorId()).isEqualTo(UPDATED_LAST_EDITOR_ID);
        assertThat(testBadgePosts.isHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testBadgePosts.getHiddenReasonId()).isEqualTo(UPDATED_HIDDEN_REASON_ID);
        assertThat(testBadgePosts.getNotifyModeratorsCount()).isEqualTo(UPDATED_NOTIFY_MODERATORS_COUNT);
        assertThat(testBadgePosts.getSpamCount()).isEqualTo(UPDATED_SPAM_COUNT);
        assertThat(testBadgePosts.getIllegalCount()).isEqualTo(UPDATED_ILLEGAL_COUNT);
        assertThat(testBadgePosts.getInappropriateCount()).isEqualTo(UPDATED_INAPPROPRIATE_COUNT);
        assertThat(testBadgePosts.getLastVersionAt()).isEqualTo(UPDATED_LAST_VERSION_AT);
        assertThat(testBadgePosts.isUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
        assertThat(testBadgePosts.getReplyToUserId()).isEqualTo(UPDATED_REPLY_TO_USER_ID);
        assertThat(testBadgePosts.getPercentRank()).isEqualTo(UPDATED_PERCENT_RANK);
        assertThat(testBadgePosts.getNotifyUserCount()).isEqualTo(UPDATED_NOTIFY_USER_COUNT);
        assertThat(testBadgePosts.getLikeScore()).isEqualTo(UPDATED_LIKE_SCORE);
        assertThat(testBadgePosts.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
        assertThat(testBadgePosts.getEditReason()).isEqualTo(UPDATED_EDIT_REASON);
        assertThat(testBadgePosts.getWordCount()).isEqualTo(UPDATED_WORD_COUNT);
        assertThat(testBadgePosts.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testBadgePosts.getCookMethod()).isEqualTo(UPDATED_COOK_METHOD);
        assertThat(testBadgePosts.isWiki()).isEqualTo(UPDATED_WIKI);
        assertThat(testBadgePosts.getBakedAt()).isEqualTo(UPDATED_BAKED_AT);
        assertThat(testBadgePosts.getBakedVersion()).isEqualTo(UPDATED_BAKED_VERSION);
        assertThat(testBadgePosts.getHiddenAt()).isEqualTo(UPDATED_HIDDEN_AT);
        assertThat(testBadgePosts.getSelfEdits()).isEqualTo(UPDATED_SELF_EDITS);
        assertThat(testBadgePosts.isReplyQuoted()).isEqualTo(UPDATED_REPLY_QUOTED);
        assertThat(testBadgePosts.isViaEmail()).isEqualTo(UPDATED_VIA_EMAIL);
        assertThat(testBadgePosts.getRawEmail()).isEqualTo(UPDATED_RAW_EMAIL);
        assertThat(testBadgePosts.getPublicVersion()).isEqualTo(UPDATED_PUBLIC_VERSION);
        assertThat(testBadgePosts.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
        assertThat(testBadgePosts.getLockedById()).isEqualTo(UPDATED_LOCKED_BY_ID);
        assertThat(testBadgePosts.getImageUploadId()).isEqualTo(UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgePosts() throws Exception {
        int databaseSizeBeforeUpdate = badgePostsRepository.findAll().size();

        // Create the BadgePosts
        BadgePostsDTO badgePostsDTO = badgePostsMapper.toDto(badgePosts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgePostsMockMvc.perform(put("/api/badge-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgePostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BadgePosts in the database
        List<BadgePosts> badgePostsList = badgePostsRepository.findAll();
        assertThat(badgePostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadgePosts() throws Exception {
        // Initialize the database
        badgePostsRepository.saveAndFlush(badgePosts);

        int databaseSizeBeforeDelete = badgePostsRepository.findAll().size();

        // Delete the badgePosts
        restBadgePostsMockMvc.perform(delete("/api/badge-posts/{id}", badgePosts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BadgePosts> badgePostsList = badgePostsRepository.findAll();
        assertThat(badgePostsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
