/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Posts;
import com.infy.domain.Polls;
import com.infy.repository.PostsRepository;
import com.infy.service.PostsService;
import com.infy.service.dto.PostsDTO;
import com.infy.service.mapper.PostsMapper;
import com.infy.service.dto.PostsCriteria;
import com.infy.service.PostsQueryService;

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
 * Integration tests for the {@link PostsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;
    private static final Long SMALLER_TOPIC_ID = 1L - 1L;

    private static final Integer DEFAULT_POST_NUMBER = 1;
    private static final Integer UPDATED_POST_NUMBER = 2;
    private static final Integer SMALLER_POST_NUMBER = 1 - 1;

    private static final String DEFAULT_RAW = "AAAAAAAAAA";
    private static final String UPDATED_RAW = "BBBBBBBBBB";

    private static final String DEFAULT_COOKED = "AAAAAAAAAA";
    private static final String UPDATED_COOKED = "BBBBBBBBBB";

    private static final Long DEFAULT_REPLY_TO_POST_NUMBER = 1L;
    private static final Long UPDATED_REPLY_TO_POST_NUMBER = 2L;
    private static final Long SMALLER_REPLY_TO_POST_NUMBER = 1L - 1L;

    private static final Integer DEFAULT_REPLY_COUNT = 1;
    private static final Integer UPDATED_REPLY_COUNT = 2;
    private static final Integer SMALLER_REPLY_COUNT = 1 - 1;

    private static final Integer DEFAULT_QUOTE_COUNT = 1;
    private static final Integer UPDATED_QUOTE_COUNT = 2;
    private static final Integer SMALLER_QUOTE_COUNT = 1 - 1;

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_OFF_TOPIC_COUNT = 1;
    private static final Integer UPDATED_OFF_TOPIC_COUNT = 2;
    private static final Integer SMALLER_OFF_TOPIC_COUNT = 1 - 1;

    private static final Integer DEFAULT_LIKE_COUNT = 1;
    private static final Integer UPDATED_LIKE_COUNT = 2;
    private static final Integer SMALLER_LIKE_COUNT = 1 - 1;

    private static final Integer DEFAULT_INCOMING_LINK_COUNT = 1;
    private static final Integer UPDATED_INCOMING_LINK_COUNT = 2;
    private static final Integer SMALLER_INCOMING_LINK_COUNT = 1 - 1;

    private static final Integer DEFAULT_BOOKMARK_COUNT = 1;
    private static final Integer UPDATED_BOOKMARK_COUNT = 2;
    private static final Integer SMALLER_BOOKMARK_COUNT = 1 - 1;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;
    private static final Double SMALLER_SCORE = 1D - 1D;

    private static final Integer DEFAULT_READS = 1;
    private static final Integer UPDATED_READS = 2;
    private static final Integer SMALLER_READS = 1 - 1;

    private static final Integer DEFAULT_POST_TYPE = 1;
    private static final Integer UPDATED_POST_TYPE = 2;
    private static final Integer SMALLER_POST_TYPE = 1 - 1;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_LAST_EDITOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITOR_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIDDEN = false;
    private static final Boolean UPDATED_HIDDEN = true;

    private static final Long DEFAULT_HIDDEN_REASON_ID = 1L;
    private static final Long UPDATED_HIDDEN_REASON_ID = 2L;
    private static final Long SMALLER_HIDDEN_REASON_ID = 1L - 1L;

    private static final Integer DEFAULT_NOTIFY_MODERATORS_COUNT = 1;
    private static final Integer UPDATED_NOTIFY_MODERATORS_COUNT = 2;
    private static final Integer SMALLER_NOTIFY_MODERATORS_COUNT = 1 - 1;

    private static final Integer DEFAULT_SPAM_COUNT = 1;
    private static final Integer UPDATED_SPAM_COUNT = 2;
    private static final Integer SMALLER_SPAM_COUNT = 1 - 1;

    private static final Integer DEFAULT_ILLEGAL_COUNT = 1;
    private static final Integer UPDATED_ILLEGAL_COUNT = 2;
    private static final Integer SMALLER_ILLEGAL_COUNT = 1 - 1;

    private static final Integer DEFAULT_INAPPROPRIATE_COUNT = 1;
    private static final Integer UPDATED_INAPPROPRIATE_COUNT = 2;
    private static final Integer SMALLER_INAPPROPRIATE_COUNT = 1 - 1;

    private static final Instant DEFAULT_LAST_VERSION_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_VERSION_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_USER_DELETED = false;
    private static final Boolean UPDATED_USER_DELETED = true;

    private static final String DEFAULT_REPLY_TO_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_TO_USER_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENT_RANK = 1D;
    private static final Double UPDATED_PERCENT_RANK = 2D;
    private static final Double SMALLER_PERCENT_RANK = 1D - 1D;

    private static final Integer DEFAULT_NOTIFY_USER_COUNT = 1;
    private static final Integer UPDATED_NOTIFY_USER_COUNT = 2;
    private static final Integer SMALLER_NOTIFY_USER_COUNT = 1 - 1;

    private static final Integer DEFAULT_LIKE_SCORE = 1;
    private static final Integer UPDATED_LIKE_SCORE = 2;
    private static final Integer SMALLER_LIKE_SCORE = 1 - 1;

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EDIT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_EDIT_REASON = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORD_COUNT = 1;
    private static final Integer UPDATED_WORD_COUNT = 2;
    private static final Integer SMALLER_WORD_COUNT = 1 - 1;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final Integer DEFAULT_COOK_METHOD = 1;
    private static final Integer UPDATED_COOK_METHOD = 2;
    private static final Integer SMALLER_COOK_METHOD = 1 - 1;

    private static final Boolean DEFAULT_WIKI = false;
    private static final Boolean UPDATED_WIKI = true;

    private static final Instant DEFAULT_BAKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BAKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_BAKED_VERSION = 1;
    private static final Integer UPDATED_BAKED_VERSION = 2;
    private static final Integer SMALLER_BAKED_VERSION = 1 - 1;

    private static final Instant DEFAULT_HIDDEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIDDEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SELF_EDITS = 1;
    private static final Integer UPDATED_SELF_EDITS = 2;
    private static final Integer SMALLER_SELF_EDITS = 1 - 1;

    private static final Boolean DEFAULT_REPLY_QUOTED = false;
    private static final Boolean UPDATED_REPLY_QUOTED = true;

    private static final Boolean DEFAULT_VIA_EMAIL = false;
    private static final Boolean UPDATED_VIA_EMAIL = true;

    private static final String DEFAULT_RAW_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_RAW_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUBLIC_VERSION = 1;
    private static final Integer UPDATED_PUBLIC_VERSION = 2;
    private static final Integer SMALLER_PUBLIC_VERSION = 1 - 1;

    private static final String DEFAULT_ACTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCKED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCKED_BY_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_IMAGE_UPLOAD_ID = 1L;
    private static final Long UPDATED_IMAGE_UPLOAD_ID = 2L;
    private static final Long SMALLER_IMAGE_UPLOAD_ID = 1L - 1L;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsQueryService postsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostsMockMvc;

    private Posts posts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posts createEntity(EntityManager em) {
        Posts posts = new Posts()
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
        return posts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posts createUpdatedEntity(EntityManager em) {
        Posts posts = new Posts()
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
        return posts;
    }

    @BeforeEach
    public void initTest() {
        posts = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosts() throws Exception {
        int databaseSizeBeforeCreate = postsRepository.findAll().size();
        // Create the Posts
        PostsDTO postsDTO = postsMapper.toDto(posts);
        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isCreated());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeCreate + 1);
        Posts testPosts = postsList.get(postsList.size() - 1);
        assertThat(testPosts.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPosts.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testPosts.getPostNumber()).isEqualTo(DEFAULT_POST_NUMBER);
        assertThat(testPosts.getRaw()).isEqualTo(DEFAULT_RAW);
        assertThat(testPosts.getCooked()).isEqualTo(DEFAULT_COOKED);
        assertThat(testPosts.getReplyToPostNumber()).isEqualTo(DEFAULT_REPLY_TO_POST_NUMBER);
        assertThat(testPosts.getReplyCount()).isEqualTo(DEFAULT_REPLY_COUNT);
        assertThat(testPosts.getQuoteCount()).isEqualTo(DEFAULT_QUOTE_COUNT);
        assertThat(testPosts.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPosts.getOffTopicCount()).isEqualTo(DEFAULT_OFF_TOPIC_COUNT);
        assertThat(testPosts.getLikeCount()).isEqualTo(DEFAULT_LIKE_COUNT);
        assertThat(testPosts.getIncomingLinkCount()).isEqualTo(DEFAULT_INCOMING_LINK_COUNT);
        assertThat(testPosts.getBookmarkCount()).isEqualTo(DEFAULT_BOOKMARK_COUNT);
        assertThat(testPosts.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testPosts.getReads()).isEqualTo(DEFAULT_READS);
        assertThat(testPosts.getPostType()).isEqualTo(DEFAULT_POST_TYPE);
        assertThat(testPosts.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testPosts.getLastEditorId()).isEqualTo(DEFAULT_LAST_EDITOR_ID);
        assertThat(testPosts.isHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testPosts.getHiddenReasonId()).isEqualTo(DEFAULT_HIDDEN_REASON_ID);
        assertThat(testPosts.getNotifyModeratorsCount()).isEqualTo(DEFAULT_NOTIFY_MODERATORS_COUNT);
        assertThat(testPosts.getSpamCount()).isEqualTo(DEFAULT_SPAM_COUNT);
        assertThat(testPosts.getIllegalCount()).isEqualTo(DEFAULT_ILLEGAL_COUNT);
        assertThat(testPosts.getInappropriateCount()).isEqualTo(DEFAULT_INAPPROPRIATE_COUNT);
        assertThat(testPosts.getLastVersionAt()).isEqualTo(DEFAULT_LAST_VERSION_AT);
        assertThat(testPosts.isUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
        assertThat(testPosts.getReplyToUserId()).isEqualTo(DEFAULT_REPLY_TO_USER_ID);
        assertThat(testPosts.getPercentRank()).isEqualTo(DEFAULT_PERCENT_RANK);
        assertThat(testPosts.getNotifyUserCount()).isEqualTo(DEFAULT_NOTIFY_USER_COUNT);
        assertThat(testPosts.getLikeScore()).isEqualTo(DEFAULT_LIKE_SCORE);
        assertThat(testPosts.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
        assertThat(testPosts.getEditReason()).isEqualTo(DEFAULT_EDIT_REASON);
        assertThat(testPosts.getWordCount()).isEqualTo(DEFAULT_WORD_COUNT);
        assertThat(testPosts.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPosts.getCookMethod()).isEqualTo(DEFAULT_COOK_METHOD);
        assertThat(testPosts.isWiki()).isEqualTo(DEFAULT_WIKI);
        assertThat(testPosts.getBakedAt()).isEqualTo(DEFAULT_BAKED_AT);
        assertThat(testPosts.getBakedVersion()).isEqualTo(DEFAULT_BAKED_VERSION);
        assertThat(testPosts.getHiddenAt()).isEqualTo(DEFAULT_HIDDEN_AT);
        assertThat(testPosts.getSelfEdits()).isEqualTo(DEFAULT_SELF_EDITS);
        assertThat(testPosts.isReplyQuoted()).isEqualTo(DEFAULT_REPLY_QUOTED);
        assertThat(testPosts.isViaEmail()).isEqualTo(DEFAULT_VIA_EMAIL);
        assertThat(testPosts.getRawEmail()).isEqualTo(DEFAULT_RAW_EMAIL);
        assertThat(testPosts.getPublicVersion()).isEqualTo(DEFAULT_PUBLIC_VERSION);
        assertThat(testPosts.getActionCode()).isEqualTo(DEFAULT_ACTION_CODE);
        assertThat(testPosts.getLockedById()).isEqualTo(DEFAULT_LOCKED_BY_ID);
        assertThat(testPosts.getImageUploadId()).isEqualTo(DEFAULT_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void createPostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postsRepository.findAll().size();

        // Create the Posts with an existing ID
        posts.setId(1L);
        PostsDTO postsDTO = postsMapper.toDto(posts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setTopicId(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setPostNumber(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCookedIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setCooked(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReplyCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setReplyCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuoteCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setQuoteCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOffTopicCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setOffTopicCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setLikeCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncomingLinkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setIncomingLinkCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookmarkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setBookmarkCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReadsIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setReads(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setPostType(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setHidden(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotifyModeratorsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setNotifyModeratorsCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpamCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setSpamCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIllegalCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setIllegalCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInappropriateCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setInappropriateCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastVersionAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setLastVersionAt(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setUserDeleted(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotifyUserCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setNotifyUserCount(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikeScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setLikeScore(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setVersion(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCookMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setCookMethod(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWikiIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setWiki(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSelfEditsIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setSelfEdits(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReplyQuotedIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setReplyQuoted(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViaEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setViaEmail(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = postsRepository.findAll().size();
        // set the field null
        posts.setPublicVersion(null);

        // Create the Posts, which fails.
        PostsDTO postsDTO = postsMapper.toDto(posts);


        restPostsMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList
        restPostsMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postNumber").value(hasItem(DEFAULT_POST_NUMBER)))
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
    public void getPosts() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get the posts
        restPostsMockMvc.perform(get("/api/posts/{id}", posts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(posts.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postNumber").value(DEFAULT_POST_NUMBER))
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
    public void getPostsByIdFiltering() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        Long id = posts.getId();

        defaultPostsShouldBeFound("id.equals=" + id);
        defaultPostsShouldNotBeFound("id.notEquals=" + id);

        defaultPostsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostsShouldNotBeFound("id.greaterThan=" + id);

        defaultPostsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userId equals to DEFAULT_USER_ID
        defaultPostsShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the postsList where userId equals to UPDATED_USER_ID
        defaultPostsShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userId not equals to DEFAULT_USER_ID
        defaultPostsShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the postsList where userId not equals to UPDATED_USER_ID
        defaultPostsShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultPostsShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the postsList where userId equals to UPDATED_USER_ID
        defaultPostsShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userId is not null
        defaultPostsShouldBeFound("userId.specified=true");

        // Get all the postsList where userId is null
        defaultPostsShouldNotBeFound("userId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByUserIdContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userId contains DEFAULT_USER_ID
        defaultPostsShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the postsList where userId contains UPDATED_USER_ID
        defaultPostsShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userId does not contain DEFAULT_USER_ID
        defaultPostsShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the postsList where userId does not contain UPDATED_USER_ID
        defaultPostsShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByTopicIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId equals to DEFAULT_TOPIC_ID
        defaultPostsShouldBeFound("topicId.equals=" + DEFAULT_TOPIC_ID);

        // Get all the postsList where topicId equals to UPDATED_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.equals=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId not equals to DEFAULT_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.notEquals=" + DEFAULT_TOPIC_ID);

        // Get all the postsList where topicId not equals to UPDATED_TOPIC_ID
        defaultPostsShouldBeFound("topicId.notEquals=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId in DEFAULT_TOPIC_ID or UPDATED_TOPIC_ID
        defaultPostsShouldBeFound("topicId.in=" + DEFAULT_TOPIC_ID + "," + UPDATED_TOPIC_ID);

        // Get all the postsList where topicId equals to UPDATED_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.in=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId is not null
        defaultPostsShouldBeFound("topicId.specified=true");

        // Get all the postsList where topicId is null
        defaultPostsShouldNotBeFound("topicId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId is greater than or equal to DEFAULT_TOPIC_ID
        defaultPostsShouldBeFound("topicId.greaterThanOrEqual=" + DEFAULT_TOPIC_ID);

        // Get all the postsList where topicId is greater than or equal to UPDATED_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.greaterThanOrEqual=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId is less than or equal to DEFAULT_TOPIC_ID
        defaultPostsShouldBeFound("topicId.lessThanOrEqual=" + DEFAULT_TOPIC_ID);

        // Get all the postsList where topicId is less than or equal to SMALLER_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.lessThanOrEqual=" + SMALLER_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId is less than DEFAULT_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.lessThan=" + DEFAULT_TOPIC_ID);

        // Get all the postsList where topicId is less than UPDATED_TOPIC_ID
        defaultPostsShouldBeFound("topicId.lessThan=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByTopicIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where topicId is greater than DEFAULT_TOPIC_ID
        defaultPostsShouldNotBeFound("topicId.greaterThan=" + DEFAULT_TOPIC_ID);

        // Get all the postsList where topicId is greater than SMALLER_TOPIC_ID
        defaultPostsShouldBeFound("topicId.greaterThan=" + SMALLER_TOPIC_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByPostNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber equals to DEFAULT_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.equals=" + DEFAULT_POST_NUMBER);

        // Get all the postsList where postNumber equals to UPDATED_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.equals=" + UPDATED_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber not equals to DEFAULT_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.notEquals=" + DEFAULT_POST_NUMBER);

        // Get all the postsList where postNumber not equals to UPDATED_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.notEquals=" + UPDATED_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber in DEFAULT_POST_NUMBER or UPDATED_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.in=" + DEFAULT_POST_NUMBER + "," + UPDATED_POST_NUMBER);

        // Get all the postsList where postNumber equals to UPDATED_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.in=" + UPDATED_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber is not null
        defaultPostsShouldBeFound("postNumber.specified=true");

        // Get all the postsList where postNumber is null
        defaultPostsShouldNotBeFound("postNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber is greater than or equal to DEFAULT_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.greaterThanOrEqual=" + DEFAULT_POST_NUMBER);

        // Get all the postsList where postNumber is greater than or equal to UPDATED_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.greaterThanOrEqual=" + UPDATED_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber is less than or equal to DEFAULT_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.lessThanOrEqual=" + DEFAULT_POST_NUMBER);

        // Get all the postsList where postNumber is less than or equal to SMALLER_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.lessThanOrEqual=" + SMALLER_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber is less than DEFAULT_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.lessThan=" + DEFAULT_POST_NUMBER);

        // Get all the postsList where postNumber is less than UPDATED_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.lessThan=" + UPDATED_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByPostNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postNumber is greater than DEFAULT_POST_NUMBER
        defaultPostsShouldNotBeFound("postNumber.greaterThan=" + DEFAULT_POST_NUMBER);

        // Get all the postsList where postNumber is greater than SMALLER_POST_NUMBER
        defaultPostsShouldBeFound("postNumber.greaterThan=" + SMALLER_POST_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPostsByCookedIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cooked equals to DEFAULT_COOKED
        defaultPostsShouldBeFound("cooked.equals=" + DEFAULT_COOKED);

        // Get all the postsList where cooked equals to UPDATED_COOKED
        defaultPostsShouldNotBeFound("cooked.equals=" + UPDATED_COOKED);
    }

    @Test
    @Transactional
    public void getAllPostsByCookedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cooked not equals to DEFAULT_COOKED
        defaultPostsShouldNotBeFound("cooked.notEquals=" + DEFAULT_COOKED);

        // Get all the postsList where cooked not equals to UPDATED_COOKED
        defaultPostsShouldBeFound("cooked.notEquals=" + UPDATED_COOKED);
    }

    @Test
    @Transactional
    public void getAllPostsByCookedIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cooked in DEFAULT_COOKED or UPDATED_COOKED
        defaultPostsShouldBeFound("cooked.in=" + DEFAULT_COOKED + "," + UPDATED_COOKED);

        // Get all the postsList where cooked equals to UPDATED_COOKED
        defaultPostsShouldNotBeFound("cooked.in=" + UPDATED_COOKED);
    }

    @Test
    @Transactional
    public void getAllPostsByCookedIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cooked is not null
        defaultPostsShouldBeFound("cooked.specified=true");

        // Get all the postsList where cooked is null
        defaultPostsShouldNotBeFound("cooked.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByCookedContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cooked contains DEFAULT_COOKED
        defaultPostsShouldBeFound("cooked.contains=" + DEFAULT_COOKED);

        // Get all the postsList where cooked contains UPDATED_COOKED
        defaultPostsShouldNotBeFound("cooked.contains=" + UPDATED_COOKED);
    }

    @Test
    @Transactional
    public void getAllPostsByCookedNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cooked does not contain DEFAULT_COOKED
        defaultPostsShouldNotBeFound("cooked.doesNotContain=" + DEFAULT_COOKED);

        // Get all the postsList where cooked does not contain UPDATED_COOKED
        defaultPostsShouldBeFound("cooked.doesNotContain=" + UPDATED_COOKED);
    }


    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber equals to DEFAULT_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.equals=" + DEFAULT_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber equals to UPDATED_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.equals=" + UPDATED_REPLY_TO_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber not equals to DEFAULT_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.notEquals=" + DEFAULT_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber not equals to UPDATED_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.notEquals=" + UPDATED_REPLY_TO_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber in DEFAULT_REPLY_TO_POST_NUMBER or UPDATED_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.in=" + DEFAULT_REPLY_TO_POST_NUMBER + "," + UPDATED_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber equals to UPDATED_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.in=" + UPDATED_REPLY_TO_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber is not null
        defaultPostsShouldBeFound("replyToPostNumber.specified=true");

        // Get all the postsList where replyToPostNumber is null
        defaultPostsShouldNotBeFound("replyToPostNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber is greater than or equal to DEFAULT_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.greaterThanOrEqual=" + DEFAULT_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber is greater than or equal to UPDATED_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.greaterThanOrEqual=" + UPDATED_REPLY_TO_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber is less than or equal to DEFAULT_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.lessThanOrEqual=" + DEFAULT_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber is less than or equal to SMALLER_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.lessThanOrEqual=" + SMALLER_REPLY_TO_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber is less than DEFAULT_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.lessThan=" + DEFAULT_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber is less than UPDATED_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.lessThan=" + UPDATED_REPLY_TO_POST_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToPostNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToPostNumber is greater than DEFAULT_REPLY_TO_POST_NUMBER
        defaultPostsShouldNotBeFound("replyToPostNumber.greaterThan=" + DEFAULT_REPLY_TO_POST_NUMBER);

        // Get all the postsList where replyToPostNumber is greater than SMALLER_REPLY_TO_POST_NUMBER
        defaultPostsShouldBeFound("replyToPostNumber.greaterThan=" + SMALLER_REPLY_TO_POST_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPostsByReplyCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount equals to DEFAULT_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.equals=" + DEFAULT_REPLY_COUNT);

        // Get all the postsList where replyCount equals to UPDATED_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.equals=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount not equals to DEFAULT_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.notEquals=" + DEFAULT_REPLY_COUNT);

        // Get all the postsList where replyCount not equals to UPDATED_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.notEquals=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount in DEFAULT_REPLY_COUNT or UPDATED_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.in=" + DEFAULT_REPLY_COUNT + "," + UPDATED_REPLY_COUNT);

        // Get all the postsList where replyCount equals to UPDATED_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.in=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount is not null
        defaultPostsShouldBeFound("replyCount.specified=true");

        // Get all the postsList where replyCount is null
        defaultPostsShouldNotBeFound("replyCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount is greater than or equal to DEFAULT_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.greaterThanOrEqual=" + DEFAULT_REPLY_COUNT);

        // Get all the postsList where replyCount is greater than or equal to UPDATED_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.greaterThanOrEqual=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount is less than or equal to DEFAULT_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.lessThanOrEqual=" + DEFAULT_REPLY_COUNT);

        // Get all the postsList where replyCount is less than or equal to SMALLER_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.lessThanOrEqual=" + SMALLER_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount is less than DEFAULT_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.lessThan=" + DEFAULT_REPLY_COUNT);

        // Get all the postsList where replyCount is less than UPDATED_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.lessThan=" + UPDATED_REPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyCount is greater than DEFAULT_REPLY_COUNT
        defaultPostsShouldNotBeFound("replyCount.greaterThan=" + DEFAULT_REPLY_COUNT);

        // Get all the postsList where replyCount is greater than SMALLER_REPLY_COUNT
        defaultPostsShouldBeFound("replyCount.greaterThan=" + SMALLER_REPLY_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount equals to DEFAULT_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.equals=" + DEFAULT_QUOTE_COUNT);

        // Get all the postsList where quoteCount equals to UPDATED_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.equals=" + UPDATED_QUOTE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount not equals to DEFAULT_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.notEquals=" + DEFAULT_QUOTE_COUNT);

        // Get all the postsList where quoteCount not equals to UPDATED_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.notEquals=" + UPDATED_QUOTE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount in DEFAULT_QUOTE_COUNT or UPDATED_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.in=" + DEFAULT_QUOTE_COUNT + "," + UPDATED_QUOTE_COUNT);

        // Get all the postsList where quoteCount equals to UPDATED_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.in=" + UPDATED_QUOTE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount is not null
        defaultPostsShouldBeFound("quoteCount.specified=true");

        // Get all the postsList where quoteCount is null
        defaultPostsShouldNotBeFound("quoteCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount is greater than or equal to DEFAULT_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.greaterThanOrEqual=" + DEFAULT_QUOTE_COUNT);

        // Get all the postsList where quoteCount is greater than or equal to UPDATED_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.greaterThanOrEqual=" + UPDATED_QUOTE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount is less than or equal to DEFAULT_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.lessThanOrEqual=" + DEFAULT_QUOTE_COUNT);

        // Get all the postsList where quoteCount is less than or equal to SMALLER_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.lessThanOrEqual=" + SMALLER_QUOTE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount is less than DEFAULT_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.lessThan=" + DEFAULT_QUOTE_COUNT);

        // Get all the postsList where quoteCount is less than UPDATED_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.lessThan=" + UPDATED_QUOTE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByQuoteCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where quoteCount is greater than DEFAULT_QUOTE_COUNT
        defaultPostsShouldNotBeFound("quoteCount.greaterThan=" + DEFAULT_QUOTE_COUNT);

        // Get all the postsList where quoteCount is greater than SMALLER_QUOTE_COUNT
        defaultPostsShouldBeFound("quoteCount.greaterThan=" + SMALLER_QUOTE_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultPostsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the postsList where deletedAt equals to UPDATED_DELETED_AT
        defaultPostsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedAt not equals to DEFAULT_DELETED_AT
        defaultPostsShouldNotBeFound("deletedAt.notEquals=" + DEFAULT_DELETED_AT);

        // Get all the postsList where deletedAt not equals to UPDATED_DELETED_AT
        defaultPostsShouldBeFound("deletedAt.notEquals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultPostsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the postsList where deletedAt equals to UPDATED_DELETED_AT
        defaultPostsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedAt is not null
        defaultPostsShouldBeFound("deletedAt.specified=true");

        // Get all the postsList where deletedAt is null
        defaultPostsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount equals to DEFAULT_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.equals=" + DEFAULT_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount equals to UPDATED_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.equals=" + UPDATED_OFF_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount not equals to DEFAULT_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.notEquals=" + DEFAULT_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount not equals to UPDATED_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.notEquals=" + UPDATED_OFF_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount in DEFAULT_OFF_TOPIC_COUNT or UPDATED_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.in=" + DEFAULT_OFF_TOPIC_COUNT + "," + UPDATED_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount equals to UPDATED_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.in=" + UPDATED_OFF_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount is not null
        defaultPostsShouldBeFound("offTopicCount.specified=true");

        // Get all the postsList where offTopicCount is null
        defaultPostsShouldNotBeFound("offTopicCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount is greater than or equal to DEFAULT_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.greaterThanOrEqual=" + DEFAULT_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount is greater than or equal to UPDATED_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.greaterThanOrEqual=" + UPDATED_OFF_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount is less than or equal to DEFAULT_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.lessThanOrEqual=" + DEFAULT_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount is less than or equal to SMALLER_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.lessThanOrEqual=" + SMALLER_OFF_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount is less than DEFAULT_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.lessThan=" + DEFAULT_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount is less than UPDATED_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.lessThan=" + UPDATED_OFF_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByOffTopicCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where offTopicCount is greater than DEFAULT_OFF_TOPIC_COUNT
        defaultPostsShouldNotBeFound("offTopicCount.greaterThan=" + DEFAULT_OFF_TOPIC_COUNT);

        // Get all the postsList where offTopicCount is greater than SMALLER_OFF_TOPIC_COUNT
        defaultPostsShouldBeFound("offTopicCount.greaterThan=" + SMALLER_OFF_TOPIC_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByLikeCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount equals to DEFAULT_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.equals=" + DEFAULT_LIKE_COUNT);

        // Get all the postsList where likeCount equals to UPDATED_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.equals=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount not equals to DEFAULT_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.notEquals=" + DEFAULT_LIKE_COUNT);

        // Get all the postsList where likeCount not equals to UPDATED_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.notEquals=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount in DEFAULT_LIKE_COUNT or UPDATED_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.in=" + DEFAULT_LIKE_COUNT + "," + UPDATED_LIKE_COUNT);

        // Get all the postsList where likeCount equals to UPDATED_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.in=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount is not null
        defaultPostsShouldBeFound("likeCount.specified=true");

        // Get all the postsList where likeCount is null
        defaultPostsShouldNotBeFound("likeCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount is greater than or equal to DEFAULT_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.greaterThanOrEqual=" + DEFAULT_LIKE_COUNT);

        // Get all the postsList where likeCount is greater than or equal to UPDATED_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.greaterThanOrEqual=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount is less than or equal to DEFAULT_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.lessThanOrEqual=" + DEFAULT_LIKE_COUNT);

        // Get all the postsList where likeCount is less than or equal to SMALLER_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.lessThanOrEqual=" + SMALLER_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount is less than DEFAULT_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.lessThan=" + DEFAULT_LIKE_COUNT);

        // Get all the postsList where likeCount is less than UPDATED_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.lessThan=" + UPDATED_LIKE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeCount is greater than DEFAULT_LIKE_COUNT
        defaultPostsShouldNotBeFound("likeCount.greaterThan=" + DEFAULT_LIKE_COUNT);

        // Get all the postsList where likeCount is greater than SMALLER_LIKE_COUNT
        defaultPostsShouldBeFound("likeCount.greaterThan=" + SMALLER_LIKE_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount equals to DEFAULT_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.equals=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount equals to UPDATED_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.equals=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount not equals to DEFAULT_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.notEquals=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount not equals to UPDATED_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.notEquals=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount in DEFAULT_INCOMING_LINK_COUNT or UPDATED_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.in=" + DEFAULT_INCOMING_LINK_COUNT + "," + UPDATED_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount equals to UPDATED_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.in=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount is not null
        defaultPostsShouldBeFound("incomingLinkCount.specified=true");

        // Get all the postsList where incomingLinkCount is null
        defaultPostsShouldNotBeFound("incomingLinkCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount is greater than or equal to DEFAULT_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.greaterThanOrEqual=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount is greater than or equal to UPDATED_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.greaterThanOrEqual=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount is less than or equal to DEFAULT_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.lessThanOrEqual=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount is less than or equal to SMALLER_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.lessThanOrEqual=" + SMALLER_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount is less than DEFAULT_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.lessThan=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount is less than UPDATED_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.lessThan=" + UPDATED_INCOMING_LINK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIncomingLinkCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where incomingLinkCount is greater than DEFAULT_INCOMING_LINK_COUNT
        defaultPostsShouldNotBeFound("incomingLinkCount.greaterThan=" + DEFAULT_INCOMING_LINK_COUNT);

        // Get all the postsList where incomingLinkCount is greater than SMALLER_INCOMING_LINK_COUNT
        defaultPostsShouldBeFound("incomingLinkCount.greaterThan=" + SMALLER_INCOMING_LINK_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount equals to DEFAULT_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.equals=" + DEFAULT_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount equals to UPDATED_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.equals=" + UPDATED_BOOKMARK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount not equals to DEFAULT_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.notEquals=" + DEFAULT_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount not equals to UPDATED_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.notEquals=" + UPDATED_BOOKMARK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount in DEFAULT_BOOKMARK_COUNT or UPDATED_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.in=" + DEFAULT_BOOKMARK_COUNT + "," + UPDATED_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount equals to UPDATED_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.in=" + UPDATED_BOOKMARK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount is not null
        defaultPostsShouldBeFound("bookmarkCount.specified=true");

        // Get all the postsList where bookmarkCount is null
        defaultPostsShouldNotBeFound("bookmarkCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount is greater than or equal to DEFAULT_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.greaterThanOrEqual=" + DEFAULT_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount is greater than or equal to UPDATED_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.greaterThanOrEqual=" + UPDATED_BOOKMARK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount is less than or equal to DEFAULT_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.lessThanOrEqual=" + DEFAULT_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount is less than or equal to SMALLER_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.lessThanOrEqual=" + SMALLER_BOOKMARK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount is less than DEFAULT_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.lessThan=" + DEFAULT_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount is less than UPDATED_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.lessThan=" + UPDATED_BOOKMARK_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByBookmarkCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bookmarkCount is greater than DEFAULT_BOOKMARK_COUNT
        defaultPostsShouldNotBeFound("bookmarkCount.greaterThan=" + DEFAULT_BOOKMARK_COUNT);

        // Get all the postsList where bookmarkCount is greater than SMALLER_BOOKMARK_COUNT
        defaultPostsShouldBeFound("bookmarkCount.greaterThan=" + SMALLER_BOOKMARK_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score equals to DEFAULT_SCORE
        defaultPostsShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the postsList where score equals to UPDATED_SCORE
        defaultPostsShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score not equals to DEFAULT_SCORE
        defaultPostsShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the postsList where score not equals to UPDATED_SCORE
        defaultPostsShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultPostsShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the postsList where score equals to UPDATED_SCORE
        defaultPostsShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score is not null
        defaultPostsShouldBeFound("score.specified=true");

        // Get all the postsList where score is null
        defaultPostsShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score is greater than or equal to DEFAULT_SCORE
        defaultPostsShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the postsList where score is greater than or equal to UPDATED_SCORE
        defaultPostsShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score is less than or equal to DEFAULT_SCORE
        defaultPostsShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the postsList where score is less than or equal to SMALLER_SCORE
        defaultPostsShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score is less than DEFAULT_SCORE
        defaultPostsShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the postsList where score is less than UPDATED_SCORE
        defaultPostsShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where score is greater than DEFAULT_SCORE
        defaultPostsShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the postsList where score is greater than SMALLER_SCORE
        defaultPostsShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllPostsByReadsIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads equals to DEFAULT_READS
        defaultPostsShouldBeFound("reads.equals=" + DEFAULT_READS);

        // Get all the postsList where reads equals to UPDATED_READS
        defaultPostsShouldNotBeFound("reads.equals=" + UPDATED_READS);
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads not equals to DEFAULT_READS
        defaultPostsShouldNotBeFound("reads.notEquals=" + DEFAULT_READS);

        // Get all the postsList where reads not equals to UPDATED_READS
        defaultPostsShouldBeFound("reads.notEquals=" + UPDATED_READS);
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads in DEFAULT_READS or UPDATED_READS
        defaultPostsShouldBeFound("reads.in=" + DEFAULT_READS + "," + UPDATED_READS);

        // Get all the postsList where reads equals to UPDATED_READS
        defaultPostsShouldNotBeFound("reads.in=" + UPDATED_READS);
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads is not null
        defaultPostsShouldBeFound("reads.specified=true");

        // Get all the postsList where reads is null
        defaultPostsShouldNotBeFound("reads.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads is greater than or equal to DEFAULT_READS
        defaultPostsShouldBeFound("reads.greaterThanOrEqual=" + DEFAULT_READS);

        // Get all the postsList where reads is greater than or equal to UPDATED_READS
        defaultPostsShouldNotBeFound("reads.greaterThanOrEqual=" + UPDATED_READS);
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads is less than or equal to DEFAULT_READS
        defaultPostsShouldBeFound("reads.lessThanOrEqual=" + DEFAULT_READS);

        // Get all the postsList where reads is less than or equal to SMALLER_READS
        defaultPostsShouldNotBeFound("reads.lessThanOrEqual=" + SMALLER_READS);
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads is less than DEFAULT_READS
        defaultPostsShouldNotBeFound("reads.lessThan=" + DEFAULT_READS);

        // Get all the postsList where reads is less than UPDATED_READS
        defaultPostsShouldBeFound("reads.lessThan=" + UPDATED_READS);
    }

    @Test
    @Transactional
    public void getAllPostsByReadsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where reads is greater than DEFAULT_READS
        defaultPostsShouldNotBeFound("reads.greaterThan=" + DEFAULT_READS);

        // Get all the postsList where reads is greater than SMALLER_READS
        defaultPostsShouldBeFound("reads.greaterThan=" + SMALLER_READS);
    }


    @Test
    @Transactional
    public void getAllPostsByPostTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType equals to DEFAULT_POST_TYPE
        defaultPostsShouldBeFound("postType.equals=" + DEFAULT_POST_TYPE);

        // Get all the postsList where postType equals to UPDATED_POST_TYPE
        defaultPostsShouldNotBeFound("postType.equals=" + UPDATED_POST_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType not equals to DEFAULT_POST_TYPE
        defaultPostsShouldNotBeFound("postType.notEquals=" + DEFAULT_POST_TYPE);

        // Get all the postsList where postType not equals to UPDATED_POST_TYPE
        defaultPostsShouldBeFound("postType.notEquals=" + UPDATED_POST_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType in DEFAULT_POST_TYPE or UPDATED_POST_TYPE
        defaultPostsShouldBeFound("postType.in=" + DEFAULT_POST_TYPE + "," + UPDATED_POST_TYPE);

        // Get all the postsList where postType equals to UPDATED_POST_TYPE
        defaultPostsShouldNotBeFound("postType.in=" + UPDATED_POST_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType is not null
        defaultPostsShouldBeFound("postType.specified=true");

        // Get all the postsList where postType is null
        defaultPostsShouldNotBeFound("postType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType is greater than or equal to DEFAULT_POST_TYPE
        defaultPostsShouldBeFound("postType.greaterThanOrEqual=" + DEFAULT_POST_TYPE);

        // Get all the postsList where postType is greater than or equal to UPDATED_POST_TYPE
        defaultPostsShouldNotBeFound("postType.greaterThanOrEqual=" + UPDATED_POST_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType is less than or equal to DEFAULT_POST_TYPE
        defaultPostsShouldBeFound("postType.lessThanOrEqual=" + DEFAULT_POST_TYPE);

        // Get all the postsList where postType is less than or equal to SMALLER_POST_TYPE
        defaultPostsShouldNotBeFound("postType.lessThanOrEqual=" + SMALLER_POST_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType is less than DEFAULT_POST_TYPE
        defaultPostsShouldNotBeFound("postType.lessThan=" + DEFAULT_POST_TYPE);

        // Get all the postsList where postType is less than UPDATED_POST_TYPE
        defaultPostsShouldBeFound("postType.lessThan=" + UPDATED_POST_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where postType is greater than DEFAULT_POST_TYPE
        defaultPostsShouldNotBeFound("postType.greaterThan=" + DEFAULT_POST_TYPE);

        // Get all the postsList where postType is greater than SMALLER_POST_TYPE
        defaultPostsShouldBeFound("postType.greaterThan=" + SMALLER_POST_TYPE);
    }


    @Test
    @Transactional
    public void getAllPostsBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the postsList where sortOrder equals to UPDATED_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the postsList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the postsList where sortOrder equals to UPDATED_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder is not null
        defaultPostsShouldBeFound("sortOrder.specified=true");

        // Get all the postsList where sortOrder is null
        defaultPostsShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the postsList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the postsList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the postsList where sortOrder is less than UPDATED_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPostsBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultPostsShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the postsList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultPostsShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllPostsByLastEditorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastEditorId equals to DEFAULT_LAST_EDITOR_ID
        defaultPostsShouldBeFound("lastEditorId.equals=" + DEFAULT_LAST_EDITOR_ID);

        // Get all the postsList where lastEditorId equals to UPDATED_LAST_EDITOR_ID
        defaultPostsShouldNotBeFound("lastEditorId.equals=" + UPDATED_LAST_EDITOR_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLastEditorIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastEditorId not equals to DEFAULT_LAST_EDITOR_ID
        defaultPostsShouldNotBeFound("lastEditorId.notEquals=" + DEFAULT_LAST_EDITOR_ID);

        // Get all the postsList where lastEditorId not equals to UPDATED_LAST_EDITOR_ID
        defaultPostsShouldBeFound("lastEditorId.notEquals=" + UPDATED_LAST_EDITOR_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLastEditorIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastEditorId in DEFAULT_LAST_EDITOR_ID or UPDATED_LAST_EDITOR_ID
        defaultPostsShouldBeFound("lastEditorId.in=" + DEFAULT_LAST_EDITOR_ID + "," + UPDATED_LAST_EDITOR_ID);

        // Get all the postsList where lastEditorId equals to UPDATED_LAST_EDITOR_ID
        defaultPostsShouldNotBeFound("lastEditorId.in=" + UPDATED_LAST_EDITOR_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLastEditorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastEditorId is not null
        defaultPostsShouldBeFound("lastEditorId.specified=true");

        // Get all the postsList where lastEditorId is null
        defaultPostsShouldNotBeFound("lastEditorId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByLastEditorIdContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastEditorId contains DEFAULT_LAST_EDITOR_ID
        defaultPostsShouldBeFound("lastEditorId.contains=" + DEFAULT_LAST_EDITOR_ID);

        // Get all the postsList where lastEditorId contains UPDATED_LAST_EDITOR_ID
        defaultPostsShouldNotBeFound("lastEditorId.contains=" + UPDATED_LAST_EDITOR_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLastEditorIdNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastEditorId does not contain DEFAULT_LAST_EDITOR_ID
        defaultPostsShouldNotBeFound("lastEditorId.doesNotContain=" + DEFAULT_LAST_EDITOR_ID);

        // Get all the postsList where lastEditorId does not contain UPDATED_LAST_EDITOR_ID
        defaultPostsShouldBeFound("lastEditorId.doesNotContain=" + UPDATED_LAST_EDITOR_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByHiddenIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hidden equals to DEFAULT_HIDDEN
        defaultPostsShouldBeFound("hidden.equals=" + DEFAULT_HIDDEN);

        // Get all the postsList where hidden equals to UPDATED_HIDDEN
        defaultPostsShouldNotBeFound("hidden.equals=" + UPDATED_HIDDEN);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hidden not equals to DEFAULT_HIDDEN
        defaultPostsShouldNotBeFound("hidden.notEquals=" + DEFAULT_HIDDEN);

        // Get all the postsList where hidden not equals to UPDATED_HIDDEN
        defaultPostsShouldBeFound("hidden.notEquals=" + UPDATED_HIDDEN);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hidden in DEFAULT_HIDDEN or UPDATED_HIDDEN
        defaultPostsShouldBeFound("hidden.in=" + DEFAULT_HIDDEN + "," + UPDATED_HIDDEN);

        // Get all the postsList where hidden equals to UPDATED_HIDDEN
        defaultPostsShouldNotBeFound("hidden.in=" + UPDATED_HIDDEN);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hidden is not null
        defaultPostsShouldBeFound("hidden.specified=true");

        // Get all the postsList where hidden is null
        defaultPostsShouldNotBeFound("hidden.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId equals to DEFAULT_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.equals=" + DEFAULT_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId equals to UPDATED_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.equals=" + UPDATED_HIDDEN_REASON_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId not equals to DEFAULT_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.notEquals=" + DEFAULT_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId not equals to UPDATED_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.notEquals=" + UPDATED_HIDDEN_REASON_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId in DEFAULT_HIDDEN_REASON_ID or UPDATED_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.in=" + DEFAULT_HIDDEN_REASON_ID + "," + UPDATED_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId equals to UPDATED_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.in=" + UPDATED_HIDDEN_REASON_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId is not null
        defaultPostsShouldBeFound("hiddenReasonId.specified=true");

        // Get all the postsList where hiddenReasonId is null
        defaultPostsShouldNotBeFound("hiddenReasonId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId is greater than or equal to DEFAULT_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.greaterThanOrEqual=" + DEFAULT_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId is greater than or equal to UPDATED_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.greaterThanOrEqual=" + UPDATED_HIDDEN_REASON_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId is less than or equal to DEFAULT_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.lessThanOrEqual=" + DEFAULT_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId is less than or equal to SMALLER_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.lessThanOrEqual=" + SMALLER_HIDDEN_REASON_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId is less than DEFAULT_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.lessThan=" + DEFAULT_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId is less than UPDATED_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.lessThan=" + UPDATED_HIDDEN_REASON_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenReasonIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenReasonId is greater than DEFAULT_HIDDEN_REASON_ID
        defaultPostsShouldNotBeFound("hiddenReasonId.greaterThan=" + DEFAULT_HIDDEN_REASON_ID);

        // Get all the postsList where hiddenReasonId is greater than SMALLER_HIDDEN_REASON_ID
        defaultPostsShouldBeFound("hiddenReasonId.greaterThan=" + SMALLER_HIDDEN_REASON_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount equals to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.equals=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount equals to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.equals=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount not equals to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.notEquals=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount not equals to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.notEquals=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount in DEFAULT_NOTIFY_MODERATORS_COUNT or UPDATED_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.in=" + DEFAULT_NOTIFY_MODERATORS_COUNT + "," + UPDATED_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount equals to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.in=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount is not null
        defaultPostsShouldBeFound("notifyModeratorsCount.specified=true");

        // Get all the postsList where notifyModeratorsCount is null
        defaultPostsShouldNotBeFound("notifyModeratorsCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount is greater than or equal to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.greaterThanOrEqual=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount is greater than or equal to UPDATED_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.greaterThanOrEqual=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount is less than or equal to DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.lessThanOrEqual=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount is less than or equal to SMALLER_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.lessThanOrEqual=" + SMALLER_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount is less than DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.lessThan=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount is less than UPDATED_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.lessThan=" + UPDATED_NOTIFY_MODERATORS_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyModeratorsCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyModeratorsCount is greater than DEFAULT_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldNotBeFound("notifyModeratorsCount.greaterThan=" + DEFAULT_NOTIFY_MODERATORS_COUNT);

        // Get all the postsList where notifyModeratorsCount is greater than SMALLER_NOTIFY_MODERATORS_COUNT
        defaultPostsShouldBeFound("notifyModeratorsCount.greaterThan=" + SMALLER_NOTIFY_MODERATORS_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsBySpamCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount equals to DEFAULT_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.equals=" + DEFAULT_SPAM_COUNT);

        // Get all the postsList where spamCount equals to UPDATED_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.equals=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount not equals to DEFAULT_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.notEquals=" + DEFAULT_SPAM_COUNT);

        // Get all the postsList where spamCount not equals to UPDATED_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.notEquals=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount in DEFAULT_SPAM_COUNT or UPDATED_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.in=" + DEFAULT_SPAM_COUNT + "," + UPDATED_SPAM_COUNT);

        // Get all the postsList where spamCount equals to UPDATED_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.in=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount is not null
        defaultPostsShouldBeFound("spamCount.specified=true");

        // Get all the postsList where spamCount is null
        defaultPostsShouldNotBeFound("spamCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount is greater than or equal to DEFAULT_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.greaterThanOrEqual=" + DEFAULT_SPAM_COUNT);

        // Get all the postsList where spamCount is greater than or equal to UPDATED_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.greaterThanOrEqual=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount is less than or equal to DEFAULT_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.lessThanOrEqual=" + DEFAULT_SPAM_COUNT);

        // Get all the postsList where spamCount is less than or equal to SMALLER_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.lessThanOrEqual=" + SMALLER_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount is less than DEFAULT_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.lessThan=" + DEFAULT_SPAM_COUNT);

        // Get all the postsList where spamCount is less than UPDATED_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.lessThan=" + UPDATED_SPAM_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsBySpamCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where spamCount is greater than DEFAULT_SPAM_COUNT
        defaultPostsShouldNotBeFound("spamCount.greaterThan=" + DEFAULT_SPAM_COUNT);

        // Get all the postsList where spamCount is greater than SMALLER_SPAM_COUNT
        defaultPostsShouldBeFound("spamCount.greaterThan=" + SMALLER_SPAM_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount equals to DEFAULT_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.equals=" + DEFAULT_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount equals to UPDATED_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.equals=" + UPDATED_ILLEGAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount not equals to DEFAULT_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.notEquals=" + DEFAULT_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount not equals to UPDATED_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.notEquals=" + UPDATED_ILLEGAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount in DEFAULT_ILLEGAL_COUNT or UPDATED_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.in=" + DEFAULT_ILLEGAL_COUNT + "," + UPDATED_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount equals to UPDATED_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.in=" + UPDATED_ILLEGAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount is not null
        defaultPostsShouldBeFound("illegalCount.specified=true");

        // Get all the postsList where illegalCount is null
        defaultPostsShouldNotBeFound("illegalCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount is greater than or equal to DEFAULT_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.greaterThanOrEqual=" + DEFAULT_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount is greater than or equal to UPDATED_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.greaterThanOrEqual=" + UPDATED_ILLEGAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount is less than or equal to DEFAULT_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.lessThanOrEqual=" + DEFAULT_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount is less than or equal to SMALLER_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.lessThanOrEqual=" + SMALLER_ILLEGAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount is less than DEFAULT_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.lessThan=" + DEFAULT_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount is less than UPDATED_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.lessThan=" + UPDATED_ILLEGAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByIllegalCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where illegalCount is greater than DEFAULT_ILLEGAL_COUNT
        defaultPostsShouldNotBeFound("illegalCount.greaterThan=" + DEFAULT_ILLEGAL_COUNT);

        // Get all the postsList where illegalCount is greater than SMALLER_ILLEGAL_COUNT
        defaultPostsShouldBeFound("illegalCount.greaterThan=" + SMALLER_ILLEGAL_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount equals to DEFAULT_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.equals=" + DEFAULT_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount equals to UPDATED_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.equals=" + UPDATED_INAPPROPRIATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount not equals to DEFAULT_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.notEquals=" + DEFAULT_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount not equals to UPDATED_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.notEquals=" + UPDATED_INAPPROPRIATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount in DEFAULT_INAPPROPRIATE_COUNT or UPDATED_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.in=" + DEFAULT_INAPPROPRIATE_COUNT + "," + UPDATED_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount equals to UPDATED_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.in=" + UPDATED_INAPPROPRIATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount is not null
        defaultPostsShouldBeFound("inappropriateCount.specified=true");

        // Get all the postsList where inappropriateCount is null
        defaultPostsShouldNotBeFound("inappropriateCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount is greater than or equal to DEFAULT_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.greaterThanOrEqual=" + DEFAULT_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount is greater than or equal to UPDATED_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.greaterThanOrEqual=" + UPDATED_INAPPROPRIATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount is less than or equal to DEFAULT_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.lessThanOrEqual=" + DEFAULT_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount is less than or equal to SMALLER_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.lessThanOrEqual=" + SMALLER_INAPPROPRIATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount is less than DEFAULT_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.lessThan=" + DEFAULT_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount is less than UPDATED_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.lessThan=" + UPDATED_INAPPROPRIATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByInappropriateCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where inappropriateCount is greater than DEFAULT_INAPPROPRIATE_COUNT
        defaultPostsShouldNotBeFound("inappropriateCount.greaterThan=" + DEFAULT_INAPPROPRIATE_COUNT);

        // Get all the postsList where inappropriateCount is greater than SMALLER_INAPPROPRIATE_COUNT
        defaultPostsShouldBeFound("inappropriateCount.greaterThan=" + SMALLER_INAPPROPRIATE_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByLastVersionAtIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastVersionAt equals to DEFAULT_LAST_VERSION_AT
        defaultPostsShouldBeFound("lastVersionAt.equals=" + DEFAULT_LAST_VERSION_AT);

        // Get all the postsList where lastVersionAt equals to UPDATED_LAST_VERSION_AT
        defaultPostsShouldNotBeFound("lastVersionAt.equals=" + UPDATED_LAST_VERSION_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByLastVersionAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastVersionAt not equals to DEFAULT_LAST_VERSION_AT
        defaultPostsShouldNotBeFound("lastVersionAt.notEquals=" + DEFAULT_LAST_VERSION_AT);

        // Get all the postsList where lastVersionAt not equals to UPDATED_LAST_VERSION_AT
        defaultPostsShouldBeFound("lastVersionAt.notEquals=" + UPDATED_LAST_VERSION_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByLastVersionAtIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastVersionAt in DEFAULT_LAST_VERSION_AT or UPDATED_LAST_VERSION_AT
        defaultPostsShouldBeFound("lastVersionAt.in=" + DEFAULT_LAST_VERSION_AT + "," + UPDATED_LAST_VERSION_AT);

        // Get all the postsList where lastVersionAt equals to UPDATED_LAST_VERSION_AT
        defaultPostsShouldNotBeFound("lastVersionAt.in=" + UPDATED_LAST_VERSION_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByLastVersionAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lastVersionAt is not null
        defaultPostsShouldBeFound("lastVersionAt.specified=true");

        // Get all the postsList where lastVersionAt is null
        defaultPostsShouldNotBeFound("lastVersionAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByUserDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userDeleted equals to DEFAULT_USER_DELETED
        defaultPostsShouldBeFound("userDeleted.equals=" + DEFAULT_USER_DELETED);

        // Get all the postsList where userDeleted equals to UPDATED_USER_DELETED
        defaultPostsShouldNotBeFound("userDeleted.equals=" + UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void getAllPostsByUserDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userDeleted not equals to DEFAULT_USER_DELETED
        defaultPostsShouldNotBeFound("userDeleted.notEquals=" + DEFAULT_USER_DELETED);

        // Get all the postsList where userDeleted not equals to UPDATED_USER_DELETED
        defaultPostsShouldBeFound("userDeleted.notEquals=" + UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void getAllPostsByUserDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userDeleted in DEFAULT_USER_DELETED or UPDATED_USER_DELETED
        defaultPostsShouldBeFound("userDeleted.in=" + DEFAULT_USER_DELETED + "," + UPDATED_USER_DELETED);

        // Get all the postsList where userDeleted equals to UPDATED_USER_DELETED
        defaultPostsShouldNotBeFound("userDeleted.in=" + UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void getAllPostsByUserDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where userDeleted is not null
        defaultPostsShouldBeFound("userDeleted.specified=true");

        // Get all the postsList where userDeleted is null
        defaultPostsShouldNotBeFound("userDeleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToUserId equals to DEFAULT_REPLY_TO_USER_ID
        defaultPostsShouldBeFound("replyToUserId.equals=" + DEFAULT_REPLY_TO_USER_ID);

        // Get all the postsList where replyToUserId equals to UPDATED_REPLY_TO_USER_ID
        defaultPostsShouldNotBeFound("replyToUserId.equals=" + UPDATED_REPLY_TO_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToUserId not equals to DEFAULT_REPLY_TO_USER_ID
        defaultPostsShouldNotBeFound("replyToUserId.notEquals=" + DEFAULT_REPLY_TO_USER_ID);

        // Get all the postsList where replyToUserId not equals to UPDATED_REPLY_TO_USER_ID
        defaultPostsShouldBeFound("replyToUserId.notEquals=" + UPDATED_REPLY_TO_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToUserId in DEFAULT_REPLY_TO_USER_ID or UPDATED_REPLY_TO_USER_ID
        defaultPostsShouldBeFound("replyToUserId.in=" + DEFAULT_REPLY_TO_USER_ID + "," + UPDATED_REPLY_TO_USER_ID);

        // Get all the postsList where replyToUserId equals to UPDATED_REPLY_TO_USER_ID
        defaultPostsShouldNotBeFound("replyToUserId.in=" + UPDATED_REPLY_TO_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToUserId is not null
        defaultPostsShouldBeFound("replyToUserId.specified=true");

        // Get all the postsList where replyToUserId is null
        defaultPostsShouldNotBeFound("replyToUserId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByReplyToUserIdContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToUserId contains DEFAULT_REPLY_TO_USER_ID
        defaultPostsShouldBeFound("replyToUserId.contains=" + DEFAULT_REPLY_TO_USER_ID);

        // Get all the postsList where replyToUserId contains UPDATED_REPLY_TO_USER_ID
        defaultPostsShouldNotBeFound("replyToUserId.contains=" + UPDATED_REPLY_TO_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyToUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyToUserId does not contain DEFAULT_REPLY_TO_USER_ID
        defaultPostsShouldNotBeFound("replyToUserId.doesNotContain=" + DEFAULT_REPLY_TO_USER_ID);

        // Get all the postsList where replyToUserId does not contain UPDATED_REPLY_TO_USER_ID
        defaultPostsShouldBeFound("replyToUserId.doesNotContain=" + UPDATED_REPLY_TO_USER_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByPercentRankIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank equals to DEFAULT_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.equals=" + DEFAULT_PERCENT_RANK);

        // Get all the postsList where percentRank equals to UPDATED_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.equals=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank not equals to DEFAULT_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.notEquals=" + DEFAULT_PERCENT_RANK);

        // Get all the postsList where percentRank not equals to UPDATED_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.notEquals=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank in DEFAULT_PERCENT_RANK or UPDATED_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.in=" + DEFAULT_PERCENT_RANK + "," + UPDATED_PERCENT_RANK);

        // Get all the postsList where percentRank equals to UPDATED_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.in=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank is not null
        defaultPostsShouldBeFound("percentRank.specified=true");

        // Get all the postsList where percentRank is null
        defaultPostsShouldNotBeFound("percentRank.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank is greater than or equal to DEFAULT_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.greaterThanOrEqual=" + DEFAULT_PERCENT_RANK);

        // Get all the postsList where percentRank is greater than or equal to UPDATED_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.greaterThanOrEqual=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank is less than or equal to DEFAULT_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.lessThanOrEqual=" + DEFAULT_PERCENT_RANK);

        // Get all the postsList where percentRank is less than or equal to SMALLER_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.lessThanOrEqual=" + SMALLER_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank is less than DEFAULT_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.lessThan=" + DEFAULT_PERCENT_RANK);

        // Get all the postsList where percentRank is less than UPDATED_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.lessThan=" + UPDATED_PERCENT_RANK);
    }

    @Test
    @Transactional
    public void getAllPostsByPercentRankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where percentRank is greater than DEFAULT_PERCENT_RANK
        defaultPostsShouldNotBeFound("percentRank.greaterThan=" + DEFAULT_PERCENT_RANK);

        // Get all the postsList where percentRank is greater than SMALLER_PERCENT_RANK
        defaultPostsShouldBeFound("percentRank.greaterThan=" + SMALLER_PERCENT_RANK);
    }


    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount equals to DEFAULT_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.equals=" + DEFAULT_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount equals to UPDATED_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.equals=" + UPDATED_NOTIFY_USER_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount not equals to DEFAULT_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.notEquals=" + DEFAULT_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount not equals to UPDATED_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.notEquals=" + UPDATED_NOTIFY_USER_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount in DEFAULT_NOTIFY_USER_COUNT or UPDATED_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.in=" + DEFAULT_NOTIFY_USER_COUNT + "," + UPDATED_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount equals to UPDATED_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.in=" + UPDATED_NOTIFY_USER_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount is not null
        defaultPostsShouldBeFound("notifyUserCount.specified=true");

        // Get all the postsList where notifyUserCount is null
        defaultPostsShouldNotBeFound("notifyUserCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount is greater than or equal to DEFAULT_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.greaterThanOrEqual=" + DEFAULT_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount is greater than or equal to UPDATED_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.greaterThanOrEqual=" + UPDATED_NOTIFY_USER_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount is less than or equal to DEFAULT_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.lessThanOrEqual=" + DEFAULT_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount is less than or equal to SMALLER_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.lessThanOrEqual=" + SMALLER_NOTIFY_USER_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount is less than DEFAULT_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.lessThan=" + DEFAULT_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount is less than UPDATED_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.lessThan=" + UPDATED_NOTIFY_USER_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByNotifyUserCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where notifyUserCount is greater than DEFAULT_NOTIFY_USER_COUNT
        defaultPostsShouldNotBeFound("notifyUserCount.greaterThan=" + DEFAULT_NOTIFY_USER_COUNT);

        // Get all the postsList where notifyUserCount is greater than SMALLER_NOTIFY_USER_COUNT
        defaultPostsShouldBeFound("notifyUserCount.greaterThan=" + SMALLER_NOTIFY_USER_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore equals to DEFAULT_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.equals=" + DEFAULT_LIKE_SCORE);

        // Get all the postsList where likeScore equals to UPDATED_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.equals=" + UPDATED_LIKE_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore not equals to DEFAULT_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.notEquals=" + DEFAULT_LIKE_SCORE);

        // Get all the postsList where likeScore not equals to UPDATED_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.notEquals=" + UPDATED_LIKE_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore in DEFAULT_LIKE_SCORE or UPDATED_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.in=" + DEFAULT_LIKE_SCORE + "," + UPDATED_LIKE_SCORE);

        // Get all the postsList where likeScore equals to UPDATED_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.in=" + UPDATED_LIKE_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore is not null
        defaultPostsShouldBeFound("likeScore.specified=true");

        // Get all the postsList where likeScore is null
        defaultPostsShouldNotBeFound("likeScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore is greater than or equal to DEFAULT_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.greaterThanOrEqual=" + DEFAULT_LIKE_SCORE);

        // Get all the postsList where likeScore is greater than or equal to UPDATED_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.greaterThanOrEqual=" + UPDATED_LIKE_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore is less than or equal to DEFAULT_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.lessThanOrEqual=" + DEFAULT_LIKE_SCORE);

        // Get all the postsList where likeScore is less than or equal to SMALLER_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.lessThanOrEqual=" + SMALLER_LIKE_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore is less than DEFAULT_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.lessThan=" + DEFAULT_LIKE_SCORE);

        // Get all the postsList where likeScore is less than UPDATED_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.lessThan=" + UPDATED_LIKE_SCORE);
    }

    @Test
    @Transactional
    public void getAllPostsByLikeScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where likeScore is greater than DEFAULT_LIKE_SCORE
        defaultPostsShouldNotBeFound("likeScore.greaterThan=" + DEFAULT_LIKE_SCORE);

        // Get all the postsList where likeScore is greater than SMALLER_LIKE_SCORE
        defaultPostsShouldBeFound("likeScore.greaterThan=" + SMALLER_LIKE_SCORE);
    }


    @Test
    @Transactional
    public void getAllPostsByDeletedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedById equals to DEFAULT_DELETED_BY_ID
        defaultPostsShouldBeFound("deletedById.equals=" + DEFAULT_DELETED_BY_ID);

        // Get all the postsList where deletedById equals to UPDATED_DELETED_BY_ID
        defaultPostsShouldNotBeFound("deletedById.equals=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedByIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedById not equals to DEFAULT_DELETED_BY_ID
        defaultPostsShouldNotBeFound("deletedById.notEquals=" + DEFAULT_DELETED_BY_ID);

        // Get all the postsList where deletedById not equals to UPDATED_DELETED_BY_ID
        defaultPostsShouldBeFound("deletedById.notEquals=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedById in DEFAULT_DELETED_BY_ID or UPDATED_DELETED_BY_ID
        defaultPostsShouldBeFound("deletedById.in=" + DEFAULT_DELETED_BY_ID + "," + UPDATED_DELETED_BY_ID);

        // Get all the postsList where deletedById equals to UPDATED_DELETED_BY_ID
        defaultPostsShouldNotBeFound("deletedById.in=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedById is not null
        defaultPostsShouldBeFound("deletedById.specified=true");

        // Get all the postsList where deletedById is null
        defaultPostsShouldNotBeFound("deletedById.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByDeletedByIdContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedById contains DEFAULT_DELETED_BY_ID
        defaultPostsShouldBeFound("deletedById.contains=" + DEFAULT_DELETED_BY_ID);

        // Get all the postsList where deletedById contains UPDATED_DELETED_BY_ID
        defaultPostsShouldNotBeFound("deletedById.contains=" + UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByDeletedByIdNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where deletedById does not contain DEFAULT_DELETED_BY_ID
        defaultPostsShouldNotBeFound("deletedById.doesNotContain=" + DEFAULT_DELETED_BY_ID);

        // Get all the postsList where deletedById does not contain UPDATED_DELETED_BY_ID
        defaultPostsShouldBeFound("deletedById.doesNotContain=" + UPDATED_DELETED_BY_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByEditReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where editReason equals to DEFAULT_EDIT_REASON
        defaultPostsShouldBeFound("editReason.equals=" + DEFAULT_EDIT_REASON);

        // Get all the postsList where editReason equals to UPDATED_EDIT_REASON
        defaultPostsShouldNotBeFound("editReason.equals=" + UPDATED_EDIT_REASON);
    }

    @Test
    @Transactional
    public void getAllPostsByEditReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where editReason not equals to DEFAULT_EDIT_REASON
        defaultPostsShouldNotBeFound("editReason.notEquals=" + DEFAULT_EDIT_REASON);

        // Get all the postsList where editReason not equals to UPDATED_EDIT_REASON
        defaultPostsShouldBeFound("editReason.notEquals=" + UPDATED_EDIT_REASON);
    }

    @Test
    @Transactional
    public void getAllPostsByEditReasonIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where editReason in DEFAULT_EDIT_REASON or UPDATED_EDIT_REASON
        defaultPostsShouldBeFound("editReason.in=" + DEFAULT_EDIT_REASON + "," + UPDATED_EDIT_REASON);

        // Get all the postsList where editReason equals to UPDATED_EDIT_REASON
        defaultPostsShouldNotBeFound("editReason.in=" + UPDATED_EDIT_REASON);
    }

    @Test
    @Transactional
    public void getAllPostsByEditReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where editReason is not null
        defaultPostsShouldBeFound("editReason.specified=true");

        // Get all the postsList where editReason is null
        defaultPostsShouldNotBeFound("editReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByEditReasonContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where editReason contains DEFAULT_EDIT_REASON
        defaultPostsShouldBeFound("editReason.contains=" + DEFAULT_EDIT_REASON);

        // Get all the postsList where editReason contains UPDATED_EDIT_REASON
        defaultPostsShouldNotBeFound("editReason.contains=" + UPDATED_EDIT_REASON);
    }

    @Test
    @Transactional
    public void getAllPostsByEditReasonNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where editReason does not contain DEFAULT_EDIT_REASON
        defaultPostsShouldNotBeFound("editReason.doesNotContain=" + DEFAULT_EDIT_REASON);

        // Get all the postsList where editReason does not contain UPDATED_EDIT_REASON
        defaultPostsShouldBeFound("editReason.doesNotContain=" + UPDATED_EDIT_REASON);
    }


    @Test
    @Transactional
    public void getAllPostsByWordCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount equals to DEFAULT_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.equals=" + DEFAULT_WORD_COUNT);

        // Get all the postsList where wordCount equals to UPDATED_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.equals=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount not equals to DEFAULT_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.notEquals=" + DEFAULT_WORD_COUNT);

        // Get all the postsList where wordCount not equals to UPDATED_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.notEquals=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount in DEFAULT_WORD_COUNT or UPDATED_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.in=" + DEFAULT_WORD_COUNT + "," + UPDATED_WORD_COUNT);

        // Get all the postsList where wordCount equals to UPDATED_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.in=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount is not null
        defaultPostsShouldBeFound("wordCount.specified=true");

        // Get all the postsList where wordCount is null
        defaultPostsShouldNotBeFound("wordCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount is greater than or equal to DEFAULT_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.greaterThanOrEqual=" + DEFAULT_WORD_COUNT);

        // Get all the postsList where wordCount is greater than or equal to UPDATED_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.greaterThanOrEqual=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount is less than or equal to DEFAULT_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.lessThanOrEqual=" + DEFAULT_WORD_COUNT);

        // Get all the postsList where wordCount is less than or equal to SMALLER_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.lessThanOrEqual=" + SMALLER_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount is less than DEFAULT_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.lessThan=" + DEFAULT_WORD_COUNT);

        // Get all the postsList where wordCount is less than UPDATED_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.lessThan=" + UPDATED_WORD_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByWordCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wordCount is greater than DEFAULT_WORD_COUNT
        defaultPostsShouldNotBeFound("wordCount.greaterThan=" + DEFAULT_WORD_COUNT);

        // Get all the postsList where wordCount is greater than SMALLER_WORD_COUNT
        defaultPostsShouldBeFound("wordCount.greaterThan=" + SMALLER_WORD_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version equals to DEFAULT_VERSION
        defaultPostsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the postsList where version equals to UPDATED_VERSION
        defaultPostsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version not equals to DEFAULT_VERSION
        defaultPostsShouldNotBeFound("version.notEquals=" + DEFAULT_VERSION);

        // Get all the postsList where version not equals to UPDATED_VERSION
        defaultPostsShouldBeFound("version.notEquals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPostsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the postsList where version equals to UPDATED_VERSION
        defaultPostsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version is not null
        defaultPostsShouldBeFound("version.specified=true");

        // Get all the postsList where version is null
        defaultPostsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version is greater than or equal to DEFAULT_VERSION
        defaultPostsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the postsList where version is greater than or equal to UPDATED_VERSION
        defaultPostsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version is less than or equal to DEFAULT_VERSION
        defaultPostsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the postsList where version is less than or equal to SMALLER_VERSION
        defaultPostsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version is less than DEFAULT_VERSION
        defaultPostsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the postsList where version is less than UPDATED_VERSION
        defaultPostsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where version is greater than DEFAULT_VERSION
        defaultPostsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the postsList where version is greater than SMALLER_VERSION
        defaultPostsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }


    @Test
    @Transactional
    public void getAllPostsByCookMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod equals to DEFAULT_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.equals=" + DEFAULT_COOK_METHOD);

        // Get all the postsList where cookMethod equals to UPDATED_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.equals=" + UPDATED_COOK_METHOD);
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod not equals to DEFAULT_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.notEquals=" + DEFAULT_COOK_METHOD);

        // Get all the postsList where cookMethod not equals to UPDATED_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.notEquals=" + UPDATED_COOK_METHOD);
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod in DEFAULT_COOK_METHOD or UPDATED_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.in=" + DEFAULT_COOK_METHOD + "," + UPDATED_COOK_METHOD);

        // Get all the postsList where cookMethod equals to UPDATED_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.in=" + UPDATED_COOK_METHOD);
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod is not null
        defaultPostsShouldBeFound("cookMethod.specified=true");

        // Get all the postsList where cookMethod is null
        defaultPostsShouldNotBeFound("cookMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod is greater than or equal to DEFAULT_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.greaterThanOrEqual=" + DEFAULT_COOK_METHOD);

        // Get all the postsList where cookMethod is greater than or equal to UPDATED_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.greaterThanOrEqual=" + UPDATED_COOK_METHOD);
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod is less than or equal to DEFAULT_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.lessThanOrEqual=" + DEFAULT_COOK_METHOD);

        // Get all the postsList where cookMethod is less than or equal to SMALLER_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.lessThanOrEqual=" + SMALLER_COOK_METHOD);
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod is less than DEFAULT_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.lessThan=" + DEFAULT_COOK_METHOD);

        // Get all the postsList where cookMethod is less than UPDATED_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.lessThan=" + UPDATED_COOK_METHOD);
    }

    @Test
    @Transactional
    public void getAllPostsByCookMethodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where cookMethod is greater than DEFAULT_COOK_METHOD
        defaultPostsShouldNotBeFound("cookMethod.greaterThan=" + DEFAULT_COOK_METHOD);

        // Get all the postsList where cookMethod is greater than SMALLER_COOK_METHOD
        defaultPostsShouldBeFound("cookMethod.greaterThan=" + SMALLER_COOK_METHOD);
    }


    @Test
    @Transactional
    public void getAllPostsByWikiIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wiki equals to DEFAULT_WIKI
        defaultPostsShouldBeFound("wiki.equals=" + DEFAULT_WIKI);

        // Get all the postsList where wiki equals to UPDATED_WIKI
        defaultPostsShouldNotBeFound("wiki.equals=" + UPDATED_WIKI);
    }

    @Test
    @Transactional
    public void getAllPostsByWikiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wiki not equals to DEFAULT_WIKI
        defaultPostsShouldNotBeFound("wiki.notEquals=" + DEFAULT_WIKI);

        // Get all the postsList where wiki not equals to UPDATED_WIKI
        defaultPostsShouldBeFound("wiki.notEquals=" + UPDATED_WIKI);
    }

    @Test
    @Transactional
    public void getAllPostsByWikiIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wiki in DEFAULT_WIKI or UPDATED_WIKI
        defaultPostsShouldBeFound("wiki.in=" + DEFAULT_WIKI + "," + UPDATED_WIKI);

        // Get all the postsList where wiki equals to UPDATED_WIKI
        defaultPostsShouldNotBeFound("wiki.in=" + UPDATED_WIKI);
    }

    @Test
    @Transactional
    public void getAllPostsByWikiIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where wiki is not null
        defaultPostsShouldBeFound("wiki.specified=true");

        // Get all the postsList where wiki is null
        defaultPostsShouldNotBeFound("wiki.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByBakedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedAt equals to DEFAULT_BAKED_AT
        defaultPostsShouldBeFound("bakedAt.equals=" + DEFAULT_BAKED_AT);

        // Get all the postsList where bakedAt equals to UPDATED_BAKED_AT
        defaultPostsShouldNotBeFound("bakedAt.equals=" + UPDATED_BAKED_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedAt not equals to DEFAULT_BAKED_AT
        defaultPostsShouldNotBeFound("bakedAt.notEquals=" + DEFAULT_BAKED_AT);

        // Get all the postsList where bakedAt not equals to UPDATED_BAKED_AT
        defaultPostsShouldBeFound("bakedAt.notEquals=" + UPDATED_BAKED_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedAtIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedAt in DEFAULT_BAKED_AT or UPDATED_BAKED_AT
        defaultPostsShouldBeFound("bakedAt.in=" + DEFAULT_BAKED_AT + "," + UPDATED_BAKED_AT);

        // Get all the postsList where bakedAt equals to UPDATED_BAKED_AT
        defaultPostsShouldNotBeFound("bakedAt.in=" + UPDATED_BAKED_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedAt is not null
        defaultPostsShouldBeFound("bakedAt.specified=true");

        // Get all the postsList where bakedAt is null
        defaultPostsShouldNotBeFound("bakedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion equals to DEFAULT_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.equals=" + DEFAULT_BAKED_VERSION);

        // Get all the postsList where bakedVersion equals to UPDATED_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.equals=" + UPDATED_BAKED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion not equals to DEFAULT_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.notEquals=" + DEFAULT_BAKED_VERSION);

        // Get all the postsList where bakedVersion not equals to UPDATED_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.notEquals=" + UPDATED_BAKED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion in DEFAULT_BAKED_VERSION or UPDATED_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.in=" + DEFAULT_BAKED_VERSION + "," + UPDATED_BAKED_VERSION);

        // Get all the postsList where bakedVersion equals to UPDATED_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.in=" + UPDATED_BAKED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion is not null
        defaultPostsShouldBeFound("bakedVersion.specified=true");

        // Get all the postsList where bakedVersion is null
        defaultPostsShouldNotBeFound("bakedVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion is greater than or equal to DEFAULT_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.greaterThanOrEqual=" + DEFAULT_BAKED_VERSION);

        // Get all the postsList where bakedVersion is greater than or equal to UPDATED_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.greaterThanOrEqual=" + UPDATED_BAKED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion is less than or equal to DEFAULT_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.lessThanOrEqual=" + DEFAULT_BAKED_VERSION);

        // Get all the postsList where bakedVersion is less than or equal to SMALLER_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.lessThanOrEqual=" + SMALLER_BAKED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion is less than DEFAULT_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.lessThan=" + DEFAULT_BAKED_VERSION);

        // Get all the postsList where bakedVersion is less than UPDATED_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.lessThan=" + UPDATED_BAKED_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByBakedVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where bakedVersion is greater than DEFAULT_BAKED_VERSION
        defaultPostsShouldNotBeFound("bakedVersion.greaterThan=" + DEFAULT_BAKED_VERSION);

        // Get all the postsList where bakedVersion is greater than SMALLER_BAKED_VERSION
        defaultPostsShouldBeFound("bakedVersion.greaterThan=" + SMALLER_BAKED_VERSION);
    }


    @Test
    @Transactional
    public void getAllPostsByHiddenAtIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenAt equals to DEFAULT_HIDDEN_AT
        defaultPostsShouldBeFound("hiddenAt.equals=" + DEFAULT_HIDDEN_AT);

        // Get all the postsList where hiddenAt equals to UPDATED_HIDDEN_AT
        defaultPostsShouldNotBeFound("hiddenAt.equals=" + UPDATED_HIDDEN_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenAt not equals to DEFAULT_HIDDEN_AT
        defaultPostsShouldNotBeFound("hiddenAt.notEquals=" + DEFAULT_HIDDEN_AT);

        // Get all the postsList where hiddenAt not equals to UPDATED_HIDDEN_AT
        defaultPostsShouldBeFound("hiddenAt.notEquals=" + UPDATED_HIDDEN_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenAtIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenAt in DEFAULT_HIDDEN_AT or UPDATED_HIDDEN_AT
        defaultPostsShouldBeFound("hiddenAt.in=" + DEFAULT_HIDDEN_AT + "," + UPDATED_HIDDEN_AT);

        // Get all the postsList where hiddenAt equals to UPDATED_HIDDEN_AT
        defaultPostsShouldNotBeFound("hiddenAt.in=" + UPDATED_HIDDEN_AT);
    }

    @Test
    @Transactional
    public void getAllPostsByHiddenAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where hiddenAt is not null
        defaultPostsShouldBeFound("hiddenAt.specified=true");

        // Get all the postsList where hiddenAt is null
        defaultPostsShouldNotBeFound("hiddenAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits equals to DEFAULT_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.equals=" + DEFAULT_SELF_EDITS);

        // Get all the postsList where selfEdits equals to UPDATED_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.equals=" + UPDATED_SELF_EDITS);
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits not equals to DEFAULT_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.notEquals=" + DEFAULT_SELF_EDITS);

        // Get all the postsList where selfEdits not equals to UPDATED_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.notEquals=" + UPDATED_SELF_EDITS);
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits in DEFAULT_SELF_EDITS or UPDATED_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.in=" + DEFAULT_SELF_EDITS + "," + UPDATED_SELF_EDITS);

        // Get all the postsList where selfEdits equals to UPDATED_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.in=" + UPDATED_SELF_EDITS);
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits is not null
        defaultPostsShouldBeFound("selfEdits.specified=true");

        // Get all the postsList where selfEdits is null
        defaultPostsShouldNotBeFound("selfEdits.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits is greater than or equal to DEFAULT_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.greaterThanOrEqual=" + DEFAULT_SELF_EDITS);

        // Get all the postsList where selfEdits is greater than or equal to UPDATED_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.greaterThanOrEqual=" + UPDATED_SELF_EDITS);
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits is less than or equal to DEFAULT_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.lessThanOrEqual=" + DEFAULT_SELF_EDITS);

        // Get all the postsList where selfEdits is less than or equal to SMALLER_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.lessThanOrEqual=" + SMALLER_SELF_EDITS);
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits is less than DEFAULT_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.lessThan=" + DEFAULT_SELF_EDITS);

        // Get all the postsList where selfEdits is less than UPDATED_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.lessThan=" + UPDATED_SELF_EDITS);
    }

    @Test
    @Transactional
    public void getAllPostsBySelfEditsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where selfEdits is greater than DEFAULT_SELF_EDITS
        defaultPostsShouldNotBeFound("selfEdits.greaterThan=" + DEFAULT_SELF_EDITS);

        // Get all the postsList where selfEdits is greater than SMALLER_SELF_EDITS
        defaultPostsShouldBeFound("selfEdits.greaterThan=" + SMALLER_SELF_EDITS);
    }


    @Test
    @Transactional
    public void getAllPostsByReplyQuotedIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyQuoted equals to DEFAULT_REPLY_QUOTED
        defaultPostsShouldBeFound("replyQuoted.equals=" + DEFAULT_REPLY_QUOTED);

        // Get all the postsList where replyQuoted equals to UPDATED_REPLY_QUOTED
        defaultPostsShouldNotBeFound("replyQuoted.equals=" + UPDATED_REPLY_QUOTED);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyQuotedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyQuoted not equals to DEFAULT_REPLY_QUOTED
        defaultPostsShouldNotBeFound("replyQuoted.notEquals=" + DEFAULT_REPLY_QUOTED);

        // Get all the postsList where replyQuoted not equals to UPDATED_REPLY_QUOTED
        defaultPostsShouldBeFound("replyQuoted.notEquals=" + UPDATED_REPLY_QUOTED);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyQuotedIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyQuoted in DEFAULT_REPLY_QUOTED or UPDATED_REPLY_QUOTED
        defaultPostsShouldBeFound("replyQuoted.in=" + DEFAULT_REPLY_QUOTED + "," + UPDATED_REPLY_QUOTED);

        // Get all the postsList where replyQuoted equals to UPDATED_REPLY_QUOTED
        defaultPostsShouldNotBeFound("replyQuoted.in=" + UPDATED_REPLY_QUOTED);
    }

    @Test
    @Transactional
    public void getAllPostsByReplyQuotedIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where replyQuoted is not null
        defaultPostsShouldBeFound("replyQuoted.specified=true");

        // Get all the postsList where replyQuoted is null
        defaultPostsShouldNotBeFound("replyQuoted.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByViaEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where viaEmail equals to DEFAULT_VIA_EMAIL
        defaultPostsShouldBeFound("viaEmail.equals=" + DEFAULT_VIA_EMAIL);

        // Get all the postsList where viaEmail equals to UPDATED_VIA_EMAIL
        defaultPostsShouldNotBeFound("viaEmail.equals=" + UPDATED_VIA_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByViaEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where viaEmail not equals to DEFAULT_VIA_EMAIL
        defaultPostsShouldNotBeFound("viaEmail.notEquals=" + DEFAULT_VIA_EMAIL);

        // Get all the postsList where viaEmail not equals to UPDATED_VIA_EMAIL
        defaultPostsShouldBeFound("viaEmail.notEquals=" + UPDATED_VIA_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByViaEmailIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where viaEmail in DEFAULT_VIA_EMAIL or UPDATED_VIA_EMAIL
        defaultPostsShouldBeFound("viaEmail.in=" + DEFAULT_VIA_EMAIL + "," + UPDATED_VIA_EMAIL);

        // Get all the postsList where viaEmail equals to UPDATED_VIA_EMAIL
        defaultPostsShouldNotBeFound("viaEmail.in=" + UPDATED_VIA_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByViaEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where viaEmail is not null
        defaultPostsShouldBeFound("viaEmail.specified=true");

        // Get all the postsList where viaEmail is null
        defaultPostsShouldNotBeFound("viaEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByRawEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where rawEmail equals to DEFAULT_RAW_EMAIL
        defaultPostsShouldBeFound("rawEmail.equals=" + DEFAULT_RAW_EMAIL);

        // Get all the postsList where rawEmail equals to UPDATED_RAW_EMAIL
        defaultPostsShouldNotBeFound("rawEmail.equals=" + UPDATED_RAW_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByRawEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where rawEmail not equals to DEFAULT_RAW_EMAIL
        defaultPostsShouldNotBeFound("rawEmail.notEquals=" + DEFAULT_RAW_EMAIL);

        // Get all the postsList where rawEmail not equals to UPDATED_RAW_EMAIL
        defaultPostsShouldBeFound("rawEmail.notEquals=" + UPDATED_RAW_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByRawEmailIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where rawEmail in DEFAULT_RAW_EMAIL or UPDATED_RAW_EMAIL
        defaultPostsShouldBeFound("rawEmail.in=" + DEFAULT_RAW_EMAIL + "," + UPDATED_RAW_EMAIL);

        // Get all the postsList where rawEmail equals to UPDATED_RAW_EMAIL
        defaultPostsShouldNotBeFound("rawEmail.in=" + UPDATED_RAW_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByRawEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where rawEmail is not null
        defaultPostsShouldBeFound("rawEmail.specified=true");

        // Get all the postsList where rawEmail is null
        defaultPostsShouldNotBeFound("rawEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByRawEmailContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where rawEmail contains DEFAULT_RAW_EMAIL
        defaultPostsShouldBeFound("rawEmail.contains=" + DEFAULT_RAW_EMAIL);

        // Get all the postsList where rawEmail contains UPDATED_RAW_EMAIL
        defaultPostsShouldNotBeFound("rawEmail.contains=" + UPDATED_RAW_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByRawEmailNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where rawEmail does not contain DEFAULT_RAW_EMAIL
        defaultPostsShouldNotBeFound("rawEmail.doesNotContain=" + DEFAULT_RAW_EMAIL);

        // Get all the postsList where rawEmail does not contain UPDATED_RAW_EMAIL
        defaultPostsShouldBeFound("rawEmail.doesNotContain=" + UPDATED_RAW_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion equals to DEFAULT_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.equals=" + DEFAULT_PUBLIC_VERSION);

        // Get all the postsList where publicVersion equals to UPDATED_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.equals=" + UPDATED_PUBLIC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion not equals to DEFAULT_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.notEquals=" + DEFAULT_PUBLIC_VERSION);

        // Get all the postsList where publicVersion not equals to UPDATED_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.notEquals=" + UPDATED_PUBLIC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion in DEFAULT_PUBLIC_VERSION or UPDATED_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.in=" + DEFAULT_PUBLIC_VERSION + "," + UPDATED_PUBLIC_VERSION);

        // Get all the postsList where publicVersion equals to UPDATED_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.in=" + UPDATED_PUBLIC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion is not null
        defaultPostsShouldBeFound("publicVersion.specified=true");

        // Get all the postsList where publicVersion is null
        defaultPostsShouldNotBeFound("publicVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion is greater than or equal to DEFAULT_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.greaterThanOrEqual=" + DEFAULT_PUBLIC_VERSION);

        // Get all the postsList where publicVersion is greater than or equal to UPDATED_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.greaterThanOrEqual=" + UPDATED_PUBLIC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion is less than or equal to DEFAULT_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.lessThanOrEqual=" + DEFAULT_PUBLIC_VERSION);

        // Get all the postsList where publicVersion is less than or equal to SMALLER_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.lessThanOrEqual=" + SMALLER_PUBLIC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion is less than DEFAULT_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.lessThan=" + DEFAULT_PUBLIC_VERSION);

        // Get all the postsList where publicVersion is less than UPDATED_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.lessThan=" + UPDATED_PUBLIC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPostsByPublicVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where publicVersion is greater than DEFAULT_PUBLIC_VERSION
        defaultPostsShouldNotBeFound("publicVersion.greaterThan=" + DEFAULT_PUBLIC_VERSION);

        // Get all the postsList where publicVersion is greater than SMALLER_PUBLIC_VERSION
        defaultPostsShouldBeFound("publicVersion.greaterThan=" + SMALLER_PUBLIC_VERSION);
    }


    @Test
    @Transactional
    public void getAllPostsByActionCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where actionCode equals to DEFAULT_ACTION_CODE
        defaultPostsShouldBeFound("actionCode.equals=" + DEFAULT_ACTION_CODE);

        // Get all the postsList where actionCode equals to UPDATED_ACTION_CODE
        defaultPostsShouldNotBeFound("actionCode.equals=" + UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    public void getAllPostsByActionCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where actionCode not equals to DEFAULT_ACTION_CODE
        defaultPostsShouldNotBeFound("actionCode.notEquals=" + DEFAULT_ACTION_CODE);

        // Get all the postsList where actionCode not equals to UPDATED_ACTION_CODE
        defaultPostsShouldBeFound("actionCode.notEquals=" + UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    public void getAllPostsByActionCodeIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where actionCode in DEFAULT_ACTION_CODE or UPDATED_ACTION_CODE
        defaultPostsShouldBeFound("actionCode.in=" + DEFAULT_ACTION_CODE + "," + UPDATED_ACTION_CODE);

        // Get all the postsList where actionCode equals to UPDATED_ACTION_CODE
        defaultPostsShouldNotBeFound("actionCode.in=" + UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    public void getAllPostsByActionCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where actionCode is not null
        defaultPostsShouldBeFound("actionCode.specified=true");

        // Get all the postsList where actionCode is null
        defaultPostsShouldNotBeFound("actionCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByActionCodeContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where actionCode contains DEFAULT_ACTION_CODE
        defaultPostsShouldBeFound("actionCode.contains=" + DEFAULT_ACTION_CODE);

        // Get all the postsList where actionCode contains UPDATED_ACTION_CODE
        defaultPostsShouldNotBeFound("actionCode.contains=" + UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    public void getAllPostsByActionCodeNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where actionCode does not contain DEFAULT_ACTION_CODE
        defaultPostsShouldNotBeFound("actionCode.doesNotContain=" + DEFAULT_ACTION_CODE);

        // Get all the postsList where actionCode does not contain UPDATED_ACTION_CODE
        defaultPostsShouldBeFound("actionCode.doesNotContain=" + UPDATED_ACTION_CODE);
    }


    @Test
    @Transactional
    public void getAllPostsByLockedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lockedById equals to DEFAULT_LOCKED_BY_ID
        defaultPostsShouldBeFound("lockedById.equals=" + DEFAULT_LOCKED_BY_ID);

        // Get all the postsList where lockedById equals to UPDATED_LOCKED_BY_ID
        defaultPostsShouldNotBeFound("lockedById.equals=" + UPDATED_LOCKED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLockedByIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lockedById not equals to DEFAULT_LOCKED_BY_ID
        defaultPostsShouldNotBeFound("lockedById.notEquals=" + DEFAULT_LOCKED_BY_ID);

        // Get all the postsList where lockedById not equals to UPDATED_LOCKED_BY_ID
        defaultPostsShouldBeFound("lockedById.notEquals=" + UPDATED_LOCKED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLockedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lockedById in DEFAULT_LOCKED_BY_ID or UPDATED_LOCKED_BY_ID
        defaultPostsShouldBeFound("lockedById.in=" + DEFAULT_LOCKED_BY_ID + "," + UPDATED_LOCKED_BY_ID);

        // Get all the postsList where lockedById equals to UPDATED_LOCKED_BY_ID
        defaultPostsShouldNotBeFound("lockedById.in=" + UPDATED_LOCKED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLockedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lockedById is not null
        defaultPostsShouldBeFound("lockedById.specified=true");

        // Get all the postsList where lockedById is null
        defaultPostsShouldNotBeFound("lockedById.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByLockedByIdContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lockedById contains DEFAULT_LOCKED_BY_ID
        defaultPostsShouldBeFound("lockedById.contains=" + DEFAULT_LOCKED_BY_ID);

        // Get all the postsList where lockedById contains UPDATED_LOCKED_BY_ID
        defaultPostsShouldNotBeFound("lockedById.contains=" + UPDATED_LOCKED_BY_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByLockedByIdNotContainsSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where lockedById does not contain DEFAULT_LOCKED_BY_ID
        defaultPostsShouldNotBeFound("lockedById.doesNotContain=" + DEFAULT_LOCKED_BY_ID);

        // Get all the postsList where lockedById does not contain UPDATED_LOCKED_BY_ID
        defaultPostsShouldBeFound("lockedById.doesNotContain=" + UPDATED_LOCKED_BY_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId equals to DEFAULT_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.equals=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId equals to UPDATED_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.equals=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId not equals to DEFAULT_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.notEquals=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId not equals to UPDATED_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.notEquals=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsInShouldWork() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId in DEFAULT_IMAGE_UPLOAD_ID or UPDATED_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.in=" + DEFAULT_IMAGE_UPLOAD_ID + "," + UPDATED_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId equals to UPDATED_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.in=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId is not null
        defaultPostsShouldBeFound("imageUploadId.specified=true");

        // Get all the postsList where imageUploadId is null
        defaultPostsShouldNotBeFound("imageUploadId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId is greater than or equal to DEFAULT_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.greaterThanOrEqual=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId is greater than or equal to UPDATED_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.greaterThanOrEqual=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId is less than or equal to DEFAULT_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.lessThanOrEqual=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId is less than or equal to SMALLER_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.lessThanOrEqual=" + SMALLER_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsLessThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId is less than DEFAULT_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.lessThan=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId is less than UPDATED_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.lessThan=" + UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void getAllPostsByImageUploadIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        // Get all the postsList where imageUploadId is greater than DEFAULT_IMAGE_UPLOAD_ID
        defaultPostsShouldNotBeFound("imageUploadId.greaterThan=" + DEFAULT_IMAGE_UPLOAD_ID);

        // Get all the postsList where imageUploadId is greater than SMALLER_IMAGE_UPLOAD_ID
        defaultPostsShouldBeFound("imageUploadId.greaterThan=" + SMALLER_IMAGE_UPLOAD_ID);
    }


    @Test
    @Transactional
    public void getAllPostsByPollsIsEqualToSomething() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);
        Polls polls = PollsResourceIT.createEntity(em);
        em.persist(polls);
        em.flush();
        posts.setPolls(polls);
        postsRepository.saveAndFlush(posts);
        Long pollsId = polls.getId();

        // Get all the postsList where polls equals to pollsId
        defaultPostsShouldBeFound("pollsId.equals=" + pollsId);

        // Get all the postsList where polls equals to pollsId + 1
        defaultPostsShouldNotBeFound("pollsId.equals=" + (pollsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostsShouldBeFound(String filter) throws Exception {
        restPostsMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postNumber").value(hasItem(DEFAULT_POST_NUMBER)))
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

        // Check, that the count call also returns 1
        restPostsMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostsShouldNotBeFound(String filter) throws Exception {
        restPostsMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostsMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPosts() throws Exception {
        // Get the posts
        restPostsMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosts() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        int databaseSizeBeforeUpdate = postsRepository.findAll().size();

        // Update the posts
        Posts updatedPosts = postsRepository.findById(posts.getId()).get();
        // Disconnect from session so that the updates on updatedPosts are not directly saved in db
        em.detach(updatedPosts);
        updatedPosts
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
        PostsDTO postsDTO = postsMapper.toDto(updatedPosts);

        restPostsMockMvc.perform(put("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isOk());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeUpdate);
        Posts testPosts = postsList.get(postsList.size() - 1);
        assertThat(testPosts.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPosts.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testPosts.getPostNumber()).isEqualTo(UPDATED_POST_NUMBER);
        assertThat(testPosts.getRaw()).isEqualTo(UPDATED_RAW);
        assertThat(testPosts.getCooked()).isEqualTo(UPDATED_COOKED);
        assertThat(testPosts.getReplyToPostNumber()).isEqualTo(UPDATED_REPLY_TO_POST_NUMBER);
        assertThat(testPosts.getReplyCount()).isEqualTo(UPDATED_REPLY_COUNT);
        assertThat(testPosts.getQuoteCount()).isEqualTo(UPDATED_QUOTE_COUNT);
        assertThat(testPosts.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPosts.getOffTopicCount()).isEqualTo(UPDATED_OFF_TOPIC_COUNT);
        assertThat(testPosts.getLikeCount()).isEqualTo(UPDATED_LIKE_COUNT);
        assertThat(testPosts.getIncomingLinkCount()).isEqualTo(UPDATED_INCOMING_LINK_COUNT);
        assertThat(testPosts.getBookmarkCount()).isEqualTo(UPDATED_BOOKMARK_COUNT);
        assertThat(testPosts.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testPosts.getReads()).isEqualTo(UPDATED_READS);
        assertThat(testPosts.getPostType()).isEqualTo(UPDATED_POST_TYPE);
        assertThat(testPosts.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testPosts.getLastEditorId()).isEqualTo(UPDATED_LAST_EDITOR_ID);
        assertThat(testPosts.isHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testPosts.getHiddenReasonId()).isEqualTo(UPDATED_HIDDEN_REASON_ID);
        assertThat(testPosts.getNotifyModeratorsCount()).isEqualTo(UPDATED_NOTIFY_MODERATORS_COUNT);
        assertThat(testPosts.getSpamCount()).isEqualTo(UPDATED_SPAM_COUNT);
        assertThat(testPosts.getIllegalCount()).isEqualTo(UPDATED_ILLEGAL_COUNT);
        assertThat(testPosts.getInappropriateCount()).isEqualTo(UPDATED_INAPPROPRIATE_COUNT);
        assertThat(testPosts.getLastVersionAt()).isEqualTo(UPDATED_LAST_VERSION_AT);
        assertThat(testPosts.isUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
        assertThat(testPosts.getReplyToUserId()).isEqualTo(UPDATED_REPLY_TO_USER_ID);
        assertThat(testPosts.getPercentRank()).isEqualTo(UPDATED_PERCENT_RANK);
        assertThat(testPosts.getNotifyUserCount()).isEqualTo(UPDATED_NOTIFY_USER_COUNT);
        assertThat(testPosts.getLikeScore()).isEqualTo(UPDATED_LIKE_SCORE);
        assertThat(testPosts.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
        assertThat(testPosts.getEditReason()).isEqualTo(UPDATED_EDIT_REASON);
        assertThat(testPosts.getWordCount()).isEqualTo(UPDATED_WORD_COUNT);
        assertThat(testPosts.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPosts.getCookMethod()).isEqualTo(UPDATED_COOK_METHOD);
        assertThat(testPosts.isWiki()).isEqualTo(UPDATED_WIKI);
        assertThat(testPosts.getBakedAt()).isEqualTo(UPDATED_BAKED_AT);
        assertThat(testPosts.getBakedVersion()).isEqualTo(UPDATED_BAKED_VERSION);
        assertThat(testPosts.getHiddenAt()).isEqualTo(UPDATED_HIDDEN_AT);
        assertThat(testPosts.getSelfEdits()).isEqualTo(UPDATED_SELF_EDITS);
        assertThat(testPosts.isReplyQuoted()).isEqualTo(UPDATED_REPLY_QUOTED);
        assertThat(testPosts.isViaEmail()).isEqualTo(UPDATED_VIA_EMAIL);
        assertThat(testPosts.getRawEmail()).isEqualTo(UPDATED_RAW_EMAIL);
        assertThat(testPosts.getPublicVersion()).isEqualTo(UPDATED_PUBLIC_VERSION);
        assertThat(testPosts.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
        assertThat(testPosts.getLockedById()).isEqualTo(UPDATED_LOCKED_BY_ID);
        assertThat(testPosts.getImageUploadId()).isEqualTo(UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPosts() throws Exception {
        int databaseSizeBeforeUpdate = postsRepository.findAll().size();

        // Create the Posts
        PostsDTO postsDTO = postsMapper.toDto(posts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostsMockMvc.perform(put("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePosts() throws Exception {
        // Initialize the database
        postsRepository.saveAndFlush(posts);

        int databaseSizeBeforeDelete = postsRepository.findAll().size();

        // Delete the posts
        restPostsMockMvc.perform(delete("/api/posts/{id}", posts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
