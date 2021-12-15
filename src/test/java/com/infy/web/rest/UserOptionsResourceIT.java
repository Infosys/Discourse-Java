/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserOptions;
import com.infy.repository.UserOptionsRepository;
import com.infy.service.UserOptionsService;
import com.infy.service.dto.UserOptionsDTO;
import com.infy.service.mapper.UserOptionsMapper;

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
 * Integration tests for the {@link UserOptionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserOptionsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MAILING_LIST_MODE = false;
    private static final Boolean UPDATED_MAILING_LIST_MODE = true;

    private static final Boolean DEFAULT_EMAIL_DIGESTS = false;
    private static final Boolean UPDATED_EMAIL_DIGESTS = true;

    private static final Boolean DEFAULT_EXTERNAL_LINKS_IN_NEW_TAB = false;
    private static final Boolean UPDATED_EXTERNAL_LINKS_IN_NEW_TAB = true;

    private static final Boolean DEFAULT_ENABLE_QUOTING = false;
    private static final Boolean UPDATED_ENABLE_QUOTING = true;

    private static final Boolean DEFAULT_DYNAMIC_FAVICON = false;
    private static final Boolean UPDATED_DYNAMIC_FAVICON = true;

    private static final Boolean DEFAULT_DISABLE_JUMP_REPLY = false;
    private static final Boolean UPDATED_DISABLE_JUMP_REPLY = true;

    private static final Boolean DEFAULT_AUTOMATICALLY_UNPIN_TOPICS = false;
    private static final Boolean UPDATED_AUTOMATICALLY_UNPIN_TOPICS = true;

    private static final Integer DEFAULT_DIGEST_AFTER_MINUTES = 1;
    private static final Integer UPDATED_DIGEST_AFTER_MINUTES = 2;

    private static final Integer DEFAULT_AUTO_TRACK_TOPICS_AFTER_MSECS = 1;
    private static final Integer UPDATED_AUTO_TRACK_TOPICS_AFTER_MSECS = 2;

    private static final Integer DEFAULT_NEW_TOPIC_DURATION_MINUTES = 1;
    private static final Integer UPDATED_NEW_TOPIC_DURATION_MINUTES = 2;

    private static final Instant DEFAULT_LAST_REDIRECTED_TO_TOP_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_REDIRECTED_TO_TOP_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_EMAIL_PREVIOUS_REPLIES = 1;
    private static final Integer UPDATED_EMAIL_PREVIOUS_REPLIES = 2;

    private static final Boolean DEFAULT_EMAIL_IN_REPLY_TO = false;
    private static final Boolean UPDATED_EMAIL_IN_REPLY_TO = true;

    private static final Integer DEFAULT_LIKE_NOTIFICATION_FREQUENCY = 1;
    private static final Integer UPDATED_LIKE_NOTIFICATION_FREQUENCY = 2;

    private static final Integer DEFAULT_MAILING_LIST_MODE_FREQUENCY = 1;
    private static final Integer UPDATED_MAILING_LIST_MODE_FREQUENCY = 2;

    private static final Boolean DEFAULT_INCLUDE_TL_0_IN_DIGESTS = false;
    private static final Boolean UPDATED_INCLUDE_TL_0_IN_DIGESTS = true;

    private static final Integer DEFAULT_NOTIFICATION_LEVEL_WHEN_REPLYING = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL_WHEN_REPLYING = 2;

    private static final Integer DEFAULT_THEME_KEY_SEQ = 1;
    private static final Integer UPDATED_THEME_KEY_SEQ = 2;

    private static final Boolean DEFAULT_ALLOW_PRIVATE_MESSAGES = false;
    private static final Boolean UPDATED_ALLOW_PRIVATE_MESSAGES = true;

    private static final Long DEFAULT_HOMEPAGE_ID = 1L;
    private static final Long UPDATED_HOMEPAGE_ID = 2L;

    private static final Long DEFAULT_THEME_IDS = 1L;
    private static final Long UPDATED_THEME_IDS = 2L;

    private static final Boolean DEFAULT_HIDE_PROFILE_AND_PRESENCE = false;
    private static final Boolean UPDATED_HIDE_PROFILE_AND_PRESENCE = true;

    private static final Integer DEFAULT_TEXT_SIZE_KEY = 1;
    private static final Integer UPDATED_TEXT_SIZE_KEY = 2;

    private static final Integer DEFAULT_TEXT_SIZE_SEQ = 1;
    private static final Integer UPDATED_TEXT_SIZE_SEQ = 2;

    private static final Integer DEFAULT_EMAIL_LEVEL = 1;
    private static final Integer UPDATED_EMAIL_LEVEL = 2;

    private static final Integer DEFAULT_EMAIL_MESSAGES_LEVEL = 1;
    private static final Integer UPDATED_EMAIL_MESSAGES_LEVEL = 2;

    private static final Integer DEFAULT_TITLE_COUNT_MODE_KEY = 1;
    private static final Integer UPDATED_TITLE_COUNT_MODE_KEY = 2;

    private static final Boolean DEFAULT_ENABLE_DEFER = false;
    private static final Boolean UPDATED_ENABLE_DEFER = true;

    private static final String DEFAULT_TIMEZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIMEZONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_ALLOWED_PM_USERS = false;
    private static final Boolean UPDATED_ENABLE_ALLOWED_PM_USERS = true;

    private static final Long DEFAULT_DARK_SCHEME_ID = 1L;
    private static final Long UPDATED_DARK_SCHEME_ID = 2L;

    private static final Boolean DEFAULT_SKIP_NEW_USER_TIPS = false;
    private static final Boolean UPDATED_SKIP_NEW_USER_TIPS = true;

    private static final Long DEFAULT_COLOR_SCHEME_ID = 1L;
    private static final Long UPDATED_COLOR_SCHEME_ID = 2L;

    @Autowired
    private UserOptionsRepository userOptionsRepository;

    @Autowired
    private UserOptionsMapper userOptionsMapper;

    @Autowired
    private UserOptionsService userOptionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserOptionsMockMvc;

    private UserOptions userOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOptions createEntity(EntityManager em) {
        UserOptions userOptions = new UserOptions()
            .userId(DEFAULT_USER_ID)
            .mailingListMode(DEFAULT_MAILING_LIST_MODE)
            .emailDigests(DEFAULT_EMAIL_DIGESTS)
            .externalLinksInNewTab(DEFAULT_EXTERNAL_LINKS_IN_NEW_TAB)
            .enableQuoting(DEFAULT_ENABLE_QUOTING)
            .dynamicFavicon(DEFAULT_DYNAMIC_FAVICON)
            .disableJumpReply(DEFAULT_DISABLE_JUMP_REPLY)
            .automaticallyUnpinTopics(DEFAULT_AUTOMATICALLY_UNPIN_TOPICS)
            .digestAfterMinutes(DEFAULT_DIGEST_AFTER_MINUTES)
            .autoTrackTopicsAfterMsecs(DEFAULT_AUTO_TRACK_TOPICS_AFTER_MSECS)
            .newTopicDurationMinutes(DEFAULT_NEW_TOPIC_DURATION_MINUTES)
            .lastRedirectedToTopAt(DEFAULT_LAST_REDIRECTED_TO_TOP_AT)
            .emailPreviousReplies(DEFAULT_EMAIL_PREVIOUS_REPLIES)
            .emailInReplyTo(DEFAULT_EMAIL_IN_REPLY_TO)
            .likeNotificationFrequency(DEFAULT_LIKE_NOTIFICATION_FREQUENCY)
            .mailingListModeFrequency(DEFAULT_MAILING_LIST_MODE_FREQUENCY)
            .includeTl0InDigests(DEFAULT_INCLUDE_TL_0_IN_DIGESTS)
            .notificationLevelWhenReplying(DEFAULT_NOTIFICATION_LEVEL_WHEN_REPLYING)
            .themeKeySeq(DEFAULT_THEME_KEY_SEQ)
            .allowPrivateMessages(DEFAULT_ALLOW_PRIVATE_MESSAGES)
            .homepageId(DEFAULT_HOMEPAGE_ID)
            .themeIds(DEFAULT_THEME_IDS)
            .hideProfileAndPresence(DEFAULT_HIDE_PROFILE_AND_PRESENCE)
            .textSizeKey(DEFAULT_TEXT_SIZE_KEY)
            .textSizeSeq(DEFAULT_TEXT_SIZE_SEQ)
            .emailLevel(DEFAULT_EMAIL_LEVEL)
            .emailMessagesLevel(DEFAULT_EMAIL_MESSAGES_LEVEL)
            .titleCountModeKey(DEFAULT_TITLE_COUNT_MODE_KEY)
            .enableDefer(DEFAULT_ENABLE_DEFER)
            .timezone(DEFAULT_TIMEZONE)
            .enableAllowedPmUsers(DEFAULT_ENABLE_ALLOWED_PM_USERS)
            .darkSchemeId(DEFAULT_DARK_SCHEME_ID)
            .skipNewUserTips(DEFAULT_SKIP_NEW_USER_TIPS)
            .colorSchemeId(DEFAULT_COLOR_SCHEME_ID);
        return userOptions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOptions createUpdatedEntity(EntityManager em) {
        UserOptions userOptions = new UserOptions()
            .userId(UPDATED_USER_ID)
            .mailingListMode(UPDATED_MAILING_LIST_MODE)
            .emailDigests(UPDATED_EMAIL_DIGESTS)
            .externalLinksInNewTab(UPDATED_EXTERNAL_LINKS_IN_NEW_TAB)
            .enableQuoting(UPDATED_ENABLE_QUOTING)
            .dynamicFavicon(UPDATED_DYNAMIC_FAVICON)
            .disableJumpReply(UPDATED_DISABLE_JUMP_REPLY)
            .automaticallyUnpinTopics(UPDATED_AUTOMATICALLY_UNPIN_TOPICS)
            .digestAfterMinutes(UPDATED_DIGEST_AFTER_MINUTES)
            .autoTrackTopicsAfterMsecs(UPDATED_AUTO_TRACK_TOPICS_AFTER_MSECS)
            .newTopicDurationMinutes(UPDATED_NEW_TOPIC_DURATION_MINUTES)
            .lastRedirectedToTopAt(UPDATED_LAST_REDIRECTED_TO_TOP_AT)
            .emailPreviousReplies(UPDATED_EMAIL_PREVIOUS_REPLIES)
            .emailInReplyTo(UPDATED_EMAIL_IN_REPLY_TO)
            .likeNotificationFrequency(UPDATED_LIKE_NOTIFICATION_FREQUENCY)
            .mailingListModeFrequency(UPDATED_MAILING_LIST_MODE_FREQUENCY)
            .includeTl0InDigests(UPDATED_INCLUDE_TL_0_IN_DIGESTS)
            .notificationLevelWhenReplying(UPDATED_NOTIFICATION_LEVEL_WHEN_REPLYING)
            .themeKeySeq(UPDATED_THEME_KEY_SEQ)
            .allowPrivateMessages(UPDATED_ALLOW_PRIVATE_MESSAGES)
            .homepageId(UPDATED_HOMEPAGE_ID)
            .themeIds(UPDATED_THEME_IDS)
            .hideProfileAndPresence(UPDATED_HIDE_PROFILE_AND_PRESENCE)
            .textSizeKey(UPDATED_TEXT_SIZE_KEY)
            .textSizeSeq(UPDATED_TEXT_SIZE_SEQ)
            .emailLevel(UPDATED_EMAIL_LEVEL)
            .emailMessagesLevel(UPDATED_EMAIL_MESSAGES_LEVEL)
            .titleCountModeKey(UPDATED_TITLE_COUNT_MODE_KEY)
            .enableDefer(UPDATED_ENABLE_DEFER)
            .timezone(UPDATED_TIMEZONE)
            .enableAllowedPmUsers(UPDATED_ENABLE_ALLOWED_PM_USERS)
            .darkSchemeId(UPDATED_DARK_SCHEME_ID)
            .skipNewUserTips(UPDATED_SKIP_NEW_USER_TIPS)
            .colorSchemeId(UPDATED_COLOR_SCHEME_ID);
        return userOptions;
    }

    @BeforeEach
    public void initTest() {
        userOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserOptions() throws Exception {
        int databaseSizeBeforeCreate = userOptionsRepository.findAll().size();
        // Create the UserOptions
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);
        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserOptions in the database
        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        UserOptions testUserOptions = userOptionsList.get(userOptionsList.size() - 1);
        assertThat(testUserOptions.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserOptions.isMailingListMode()).isEqualTo(DEFAULT_MAILING_LIST_MODE);
        assertThat(testUserOptions.isEmailDigests()).isEqualTo(DEFAULT_EMAIL_DIGESTS);
        assertThat(testUserOptions.isExternalLinksInNewTab()).isEqualTo(DEFAULT_EXTERNAL_LINKS_IN_NEW_TAB);
        assertThat(testUserOptions.isEnableQuoting()).isEqualTo(DEFAULT_ENABLE_QUOTING);
        assertThat(testUserOptions.isDynamicFavicon()).isEqualTo(DEFAULT_DYNAMIC_FAVICON);
        assertThat(testUserOptions.isDisableJumpReply()).isEqualTo(DEFAULT_DISABLE_JUMP_REPLY);
        assertThat(testUserOptions.isAutomaticallyUnpinTopics()).isEqualTo(DEFAULT_AUTOMATICALLY_UNPIN_TOPICS);
        assertThat(testUserOptions.getDigestAfterMinutes()).isEqualTo(DEFAULT_DIGEST_AFTER_MINUTES);
        assertThat(testUserOptions.getAutoTrackTopicsAfterMsecs()).isEqualTo(DEFAULT_AUTO_TRACK_TOPICS_AFTER_MSECS);
        assertThat(testUserOptions.getNewTopicDurationMinutes()).isEqualTo(DEFAULT_NEW_TOPIC_DURATION_MINUTES);
        assertThat(testUserOptions.getLastRedirectedToTopAt()).isEqualTo(DEFAULT_LAST_REDIRECTED_TO_TOP_AT);
        assertThat(testUserOptions.getEmailPreviousReplies()).isEqualTo(DEFAULT_EMAIL_PREVIOUS_REPLIES);
        assertThat(testUserOptions.isEmailInReplyTo()).isEqualTo(DEFAULT_EMAIL_IN_REPLY_TO);
        assertThat(testUserOptions.getLikeNotificationFrequency()).isEqualTo(DEFAULT_LIKE_NOTIFICATION_FREQUENCY);
        assertThat(testUserOptions.getMailingListModeFrequency()).isEqualTo(DEFAULT_MAILING_LIST_MODE_FREQUENCY);
        assertThat(testUserOptions.isIncludeTl0InDigests()).isEqualTo(DEFAULT_INCLUDE_TL_0_IN_DIGESTS);
        assertThat(testUserOptions.getNotificationLevelWhenReplying()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL_WHEN_REPLYING);
        assertThat(testUserOptions.getThemeKeySeq()).isEqualTo(DEFAULT_THEME_KEY_SEQ);
        assertThat(testUserOptions.isAllowPrivateMessages()).isEqualTo(DEFAULT_ALLOW_PRIVATE_MESSAGES);
        assertThat(testUserOptions.getHomepageId()).isEqualTo(DEFAULT_HOMEPAGE_ID);
        assertThat(testUserOptions.getThemeIds()).isEqualTo(DEFAULT_THEME_IDS);
        assertThat(testUserOptions.isHideProfileAndPresence()).isEqualTo(DEFAULT_HIDE_PROFILE_AND_PRESENCE);
        assertThat(testUserOptions.getTextSizeKey()).isEqualTo(DEFAULT_TEXT_SIZE_KEY);
        assertThat(testUserOptions.getTextSizeSeq()).isEqualTo(DEFAULT_TEXT_SIZE_SEQ);
        assertThat(testUserOptions.getEmailLevel()).isEqualTo(DEFAULT_EMAIL_LEVEL);
        assertThat(testUserOptions.getEmailMessagesLevel()).isEqualTo(DEFAULT_EMAIL_MESSAGES_LEVEL);
        assertThat(testUserOptions.getTitleCountModeKey()).isEqualTo(DEFAULT_TITLE_COUNT_MODE_KEY);
        assertThat(testUserOptions.isEnableDefer()).isEqualTo(DEFAULT_ENABLE_DEFER);
        assertThat(testUserOptions.getTimezone()).isEqualTo(DEFAULT_TIMEZONE);
        assertThat(testUserOptions.isEnableAllowedPmUsers()).isEqualTo(DEFAULT_ENABLE_ALLOWED_PM_USERS);
        assertThat(testUserOptions.getDarkSchemeId()).isEqualTo(DEFAULT_DARK_SCHEME_ID);
        assertThat(testUserOptions.isSkipNewUserTips()).isEqualTo(DEFAULT_SKIP_NEW_USER_TIPS);
        assertThat(testUserOptions.getColorSchemeId()).isEqualTo(DEFAULT_COLOR_SCHEME_ID);
    }

    @Test
    @Transactional
    public void createUserOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userOptionsRepository.findAll().size();

        // Create the UserOptions with an existing ID
        userOptions.setId(1L);
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserOptions in the database
        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setUserId(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailingListModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setMailingListMode(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExternalLinksInNewTabIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setExternalLinksInNewTab(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableQuotingIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEnableQuoting(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDynamicFaviconIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setDynamicFavicon(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisableJumpReplyIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setDisableJumpReply(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutomaticallyUnpinTopicsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setAutomaticallyUnpinTopics(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPreviousRepliesIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEmailPreviousReplies(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailInReplyToIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEmailInReplyTo(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikeNotificationFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setLikeNotificationFrequency(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailingListModeFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setMailingListModeFrequency(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThemeKeySeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setThemeKeySeq(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowPrivateMessagesIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setAllowPrivateMessages(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThemeIdsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setThemeIds(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHideProfileAndPresenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setHideProfileAndPresence(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextSizeKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setTextSizeKey(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextSizeSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setTextSizeSeq(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEmailLevel(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailMessagesLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEmailMessagesLevel(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleCountModeKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setTitleCountModeKey(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableDeferIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEnableDefer(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableAllowedPmUsersIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setEnableAllowedPmUsers(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSkipNewUserTipsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOptionsRepository.findAll().size();
        // set the field null
        userOptions.setSkipNewUserTips(null);

        // Create the UserOptions, which fails.
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);


        restUserOptionsMockMvc.perform(post("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserOptions() throws Exception {
        // Initialize the database
        userOptionsRepository.saveAndFlush(userOptions);

        // Get all the userOptionsList
        restUserOptionsMockMvc.perform(get("/api/user-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].mailingListMode").value(hasItem(DEFAULT_MAILING_LIST_MODE.booleanValue())))
            .andExpect(jsonPath("$.[*].emailDigests").value(hasItem(DEFAULT_EMAIL_DIGESTS.booleanValue())))
            .andExpect(jsonPath("$.[*].externalLinksInNewTab").value(hasItem(DEFAULT_EXTERNAL_LINKS_IN_NEW_TAB.booleanValue())))
            .andExpect(jsonPath("$.[*].enableQuoting").value(hasItem(DEFAULT_ENABLE_QUOTING.booleanValue())))
            .andExpect(jsonPath("$.[*].dynamicFavicon").value(hasItem(DEFAULT_DYNAMIC_FAVICON.booleanValue())))
            .andExpect(jsonPath("$.[*].disableJumpReply").value(hasItem(DEFAULT_DISABLE_JUMP_REPLY.booleanValue())))
            .andExpect(jsonPath("$.[*].automaticallyUnpinTopics").value(hasItem(DEFAULT_AUTOMATICALLY_UNPIN_TOPICS.booleanValue())))
            .andExpect(jsonPath("$.[*].digestAfterMinutes").value(hasItem(DEFAULT_DIGEST_AFTER_MINUTES)))
            .andExpect(jsonPath("$.[*].autoTrackTopicsAfterMsecs").value(hasItem(DEFAULT_AUTO_TRACK_TOPICS_AFTER_MSECS)))
            .andExpect(jsonPath("$.[*].newTopicDurationMinutes").value(hasItem(DEFAULT_NEW_TOPIC_DURATION_MINUTES)))
            .andExpect(jsonPath("$.[*].lastRedirectedToTopAt").value(hasItem(DEFAULT_LAST_REDIRECTED_TO_TOP_AT.toString())))
            .andExpect(jsonPath("$.[*].emailPreviousReplies").value(hasItem(DEFAULT_EMAIL_PREVIOUS_REPLIES)))
            .andExpect(jsonPath("$.[*].emailInReplyTo").value(hasItem(DEFAULT_EMAIL_IN_REPLY_TO.booleanValue())))
            .andExpect(jsonPath("$.[*].likeNotificationFrequency").value(hasItem(DEFAULT_LIKE_NOTIFICATION_FREQUENCY)))
            .andExpect(jsonPath("$.[*].mailingListModeFrequency").value(hasItem(DEFAULT_MAILING_LIST_MODE_FREQUENCY)))
            .andExpect(jsonPath("$.[*].includeTl0InDigests").value(hasItem(DEFAULT_INCLUDE_TL_0_IN_DIGESTS.booleanValue())))
            .andExpect(jsonPath("$.[*].notificationLevelWhenReplying").value(hasItem(DEFAULT_NOTIFICATION_LEVEL_WHEN_REPLYING)))
            .andExpect(jsonPath("$.[*].themeKeySeq").value(hasItem(DEFAULT_THEME_KEY_SEQ)))
            .andExpect(jsonPath("$.[*].allowPrivateMessages").value(hasItem(DEFAULT_ALLOW_PRIVATE_MESSAGES.booleanValue())))
            .andExpect(jsonPath("$.[*].homepageId").value(hasItem(DEFAULT_HOMEPAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].themeIds").value(hasItem(DEFAULT_THEME_IDS.intValue())))
            .andExpect(jsonPath("$.[*].hideProfileAndPresence").value(hasItem(DEFAULT_HIDE_PROFILE_AND_PRESENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].textSizeKey").value(hasItem(DEFAULT_TEXT_SIZE_KEY)))
            .andExpect(jsonPath("$.[*].textSizeSeq").value(hasItem(DEFAULT_TEXT_SIZE_SEQ)))
            .andExpect(jsonPath("$.[*].emailLevel").value(hasItem(DEFAULT_EMAIL_LEVEL)))
            .andExpect(jsonPath("$.[*].emailMessagesLevel").value(hasItem(DEFAULT_EMAIL_MESSAGES_LEVEL)))
            .andExpect(jsonPath("$.[*].titleCountModeKey").value(hasItem(DEFAULT_TITLE_COUNT_MODE_KEY)))
            .andExpect(jsonPath("$.[*].enableDefer").value(hasItem(DEFAULT_ENABLE_DEFER.booleanValue())))
            .andExpect(jsonPath("$.[*].timezone").value(hasItem(DEFAULT_TIMEZONE)))
            .andExpect(jsonPath("$.[*].enableAllowedPmUsers").value(hasItem(DEFAULT_ENABLE_ALLOWED_PM_USERS.booleanValue())))
            .andExpect(jsonPath("$.[*].darkSchemeId").value(hasItem(DEFAULT_DARK_SCHEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].skipNewUserTips").value(hasItem(DEFAULT_SKIP_NEW_USER_TIPS.booleanValue())))
            .andExpect(jsonPath("$.[*].colorSchemeId").value(hasItem(DEFAULT_COLOR_SCHEME_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserOptions() throws Exception {
        // Initialize the database
        userOptionsRepository.saveAndFlush(userOptions);

        // Get the userOptions
        restUserOptionsMockMvc.perform(get("/api/user-options/{id}", userOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userOptions.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.mailingListMode").value(DEFAULT_MAILING_LIST_MODE.booleanValue()))
            .andExpect(jsonPath("$.emailDigests").value(DEFAULT_EMAIL_DIGESTS.booleanValue()))
            .andExpect(jsonPath("$.externalLinksInNewTab").value(DEFAULT_EXTERNAL_LINKS_IN_NEW_TAB.booleanValue()))
            .andExpect(jsonPath("$.enableQuoting").value(DEFAULT_ENABLE_QUOTING.booleanValue()))
            .andExpect(jsonPath("$.dynamicFavicon").value(DEFAULT_DYNAMIC_FAVICON.booleanValue()))
            .andExpect(jsonPath("$.disableJumpReply").value(DEFAULT_DISABLE_JUMP_REPLY.booleanValue()))
            .andExpect(jsonPath("$.automaticallyUnpinTopics").value(DEFAULT_AUTOMATICALLY_UNPIN_TOPICS.booleanValue()))
            .andExpect(jsonPath("$.digestAfterMinutes").value(DEFAULT_DIGEST_AFTER_MINUTES))
            .andExpect(jsonPath("$.autoTrackTopicsAfterMsecs").value(DEFAULT_AUTO_TRACK_TOPICS_AFTER_MSECS))
            .andExpect(jsonPath("$.newTopicDurationMinutes").value(DEFAULT_NEW_TOPIC_DURATION_MINUTES))
            .andExpect(jsonPath("$.lastRedirectedToTopAt").value(DEFAULT_LAST_REDIRECTED_TO_TOP_AT.toString()))
            .andExpect(jsonPath("$.emailPreviousReplies").value(DEFAULT_EMAIL_PREVIOUS_REPLIES))
            .andExpect(jsonPath("$.emailInReplyTo").value(DEFAULT_EMAIL_IN_REPLY_TO.booleanValue()))
            .andExpect(jsonPath("$.likeNotificationFrequency").value(DEFAULT_LIKE_NOTIFICATION_FREQUENCY))
            .andExpect(jsonPath("$.mailingListModeFrequency").value(DEFAULT_MAILING_LIST_MODE_FREQUENCY))
            .andExpect(jsonPath("$.includeTl0InDigests").value(DEFAULT_INCLUDE_TL_0_IN_DIGESTS.booleanValue()))
            .andExpect(jsonPath("$.notificationLevelWhenReplying").value(DEFAULT_NOTIFICATION_LEVEL_WHEN_REPLYING))
            .andExpect(jsonPath("$.themeKeySeq").value(DEFAULT_THEME_KEY_SEQ))
            .andExpect(jsonPath("$.allowPrivateMessages").value(DEFAULT_ALLOW_PRIVATE_MESSAGES.booleanValue()))
            .andExpect(jsonPath("$.homepageId").value(DEFAULT_HOMEPAGE_ID.intValue()))
            .andExpect(jsonPath("$.themeIds").value(DEFAULT_THEME_IDS.intValue()))
            .andExpect(jsonPath("$.hideProfileAndPresence").value(DEFAULT_HIDE_PROFILE_AND_PRESENCE.booleanValue()))
            .andExpect(jsonPath("$.textSizeKey").value(DEFAULT_TEXT_SIZE_KEY))
            .andExpect(jsonPath("$.textSizeSeq").value(DEFAULT_TEXT_SIZE_SEQ))
            .andExpect(jsonPath("$.emailLevel").value(DEFAULT_EMAIL_LEVEL))
            .andExpect(jsonPath("$.emailMessagesLevel").value(DEFAULT_EMAIL_MESSAGES_LEVEL))
            .andExpect(jsonPath("$.titleCountModeKey").value(DEFAULT_TITLE_COUNT_MODE_KEY))
            .andExpect(jsonPath("$.enableDefer").value(DEFAULT_ENABLE_DEFER.booleanValue()))
            .andExpect(jsonPath("$.timezone").value(DEFAULT_TIMEZONE))
            .andExpect(jsonPath("$.enableAllowedPmUsers").value(DEFAULT_ENABLE_ALLOWED_PM_USERS.booleanValue()))
            .andExpect(jsonPath("$.darkSchemeId").value(DEFAULT_DARK_SCHEME_ID.intValue()))
            .andExpect(jsonPath("$.skipNewUserTips").value(DEFAULT_SKIP_NEW_USER_TIPS.booleanValue()))
            .andExpect(jsonPath("$.colorSchemeId").value(DEFAULT_COLOR_SCHEME_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserOptions() throws Exception {
        // Get the userOptions
        restUserOptionsMockMvc.perform(get("/api/user-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserOptions() throws Exception {
        // Initialize the database
        userOptionsRepository.saveAndFlush(userOptions);

        int databaseSizeBeforeUpdate = userOptionsRepository.findAll().size();

        // Update the userOptions
        UserOptions updatedUserOptions = userOptionsRepository.findById(userOptions.getId()).get();
        // Disconnect from session so that the updates on updatedUserOptions are not directly saved in db
        em.detach(updatedUserOptions);
        updatedUserOptions
            .userId(UPDATED_USER_ID)
            .mailingListMode(UPDATED_MAILING_LIST_MODE)
            .emailDigests(UPDATED_EMAIL_DIGESTS)
            .externalLinksInNewTab(UPDATED_EXTERNAL_LINKS_IN_NEW_TAB)
            .enableQuoting(UPDATED_ENABLE_QUOTING)
            .dynamicFavicon(UPDATED_DYNAMIC_FAVICON)
            .disableJumpReply(UPDATED_DISABLE_JUMP_REPLY)
            .automaticallyUnpinTopics(UPDATED_AUTOMATICALLY_UNPIN_TOPICS)
            .digestAfterMinutes(UPDATED_DIGEST_AFTER_MINUTES)
            .autoTrackTopicsAfterMsecs(UPDATED_AUTO_TRACK_TOPICS_AFTER_MSECS)
            .newTopicDurationMinutes(UPDATED_NEW_TOPIC_DURATION_MINUTES)
            .lastRedirectedToTopAt(UPDATED_LAST_REDIRECTED_TO_TOP_AT)
            .emailPreviousReplies(UPDATED_EMAIL_PREVIOUS_REPLIES)
            .emailInReplyTo(UPDATED_EMAIL_IN_REPLY_TO)
            .likeNotificationFrequency(UPDATED_LIKE_NOTIFICATION_FREQUENCY)
            .mailingListModeFrequency(UPDATED_MAILING_LIST_MODE_FREQUENCY)
            .includeTl0InDigests(UPDATED_INCLUDE_TL_0_IN_DIGESTS)
            .notificationLevelWhenReplying(UPDATED_NOTIFICATION_LEVEL_WHEN_REPLYING)
            .themeKeySeq(UPDATED_THEME_KEY_SEQ)
            .allowPrivateMessages(UPDATED_ALLOW_PRIVATE_MESSAGES)
            .homepageId(UPDATED_HOMEPAGE_ID)
            .themeIds(UPDATED_THEME_IDS)
            .hideProfileAndPresence(UPDATED_HIDE_PROFILE_AND_PRESENCE)
            .textSizeKey(UPDATED_TEXT_SIZE_KEY)
            .textSizeSeq(UPDATED_TEXT_SIZE_SEQ)
            .emailLevel(UPDATED_EMAIL_LEVEL)
            .emailMessagesLevel(UPDATED_EMAIL_MESSAGES_LEVEL)
            .titleCountModeKey(UPDATED_TITLE_COUNT_MODE_KEY)
            .enableDefer(UPDATED_ENABLE_DEFER)
            .timezone(UPDATED_TIMEZONE)
            .enableAllowedPmUsers(UPDATED_ENABLE_ALLOWED_PM_USERS)
            .darkSchemeId(UPDATED_DARK_SCHEME_ID)
            .skipNewUserTips(UPDATED_SKIP_NEW_USER_TIPS)
            .colorSchemeId(UPDATED_COLOR_SCHEME_ID);
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(updatedUserOptions);

        restUserOptionsMockMvc.perform(put("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isOk());

        // Validate the UserOptions in the database
        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeUpdate);
        UserOptions testUserOptions = userOptionsList.get(userOptionsList.size() - 1);
        assertThat(testUserOptions.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserOptions.isMailingListMode()).isEqualTo(UPDATED_MAILING_LIST_MODE);
        assertThat(testUserOptions.isEmailDigests()).isEqualTo(UPDATED_EMAIL_DIGESTS);
        assertThat(testUserOptions.isExternalLinksInNewTab()).isEqualTo(UPDATED_EXTERNAL_LINKS_IN_NEW_TAB);
        assertThat(testUserOptions.isEnableQuoting()).isEqualTo(UPDATED_ENABLE_QUOTING);
        assertThat(testUserOptions.isDynamicFavicon()).isEqualTo(UPDATED_DYNAMIC_FAVICON);
        assertThat(testUserOptions.isDisableJumpReply()).isEqualTo(UPDATED_DISABLE_JUMP_REPLY);
        assertThat(testUserOptions.isAutomaticallyUnpinTopics()).isEqualTo(UPDATED_AUTOMATICALLY_UNPIN_TOPICS);
        assertThat(testUserOptions.getDigestAfterMinutes()).isEqualTo(UPDATED_DIGEST_AFTER_MINUTES);
        assertThat(testUserOptions.getAutoTrackTopicsAfterMsecs()).isEqualTo(UPDATED_AUTO_TRACK_TOPICS_AFTER_MSECS);
        assertThat(testUserOptions.getNewTopicDurationMinutes()).isEqualTo(UPDATED_NEW_TOPIC_DURATION_MINUTES);
        assertThat(testUserOptions.getLastRedirectedToTopAt()).isEqualTo(UPDATED_LAST_REDIRECTED_TO_TOP_AT);
        assertThat(testUserOptions.getEmailPreviousReplies()).isEqualTo(UPDATED_EMAIL_PREVIOUS_REPLIES);
        assertThat(testUserOptions.isEmailInReplyTo()).isEqualTo(UPDATED_EMAIL_IN_REPLY_TO);
        assertThat(testUserOptions.getLikeNotificationFrequency()).isEqualTo(UPDATED_LIKE_NOTIFICATION_FREQUENCY);
        assertThat(testUserOptions.getMailingListModeFrequency()).isEqualTo(UPDATED_MAILING_LIST_MODE_FREQUENCY);
        assertThat(testUserOptions.isIncludeTl0InDigests()).isEqualTo(UPDATED_INCLUDE_TL_0_IN_DIGESTS);
        assertThat(testUserOptions.getNotificationLevelWhenReplying()).isEqualTo(UPDATED_NOTIFICATION_LEVEL_WHEN_REPLYING);
        assertThat(testUserOptions.getThemeKeySeq()).isEqualTo(UPDATED_THEME_KEY_SEQ);
        assertThat(testUserOptions.isAllowPrivateMessages()).isEqualTo(UPDATED_ALLOW_PRIVATE_MESSAGES);
        assertThat(testUserOptions.getHomepageId()).isEqualTo(UPDATED_HOMEPAGE_ID);
        assertThat(testUserOptions.getThemeIds()).isEqualTo(UPDATED_THEME_IDS);
        assertThat(testUserOptions.isHideProfileAndPresence()).isEqualTo(UPDATED_HIDE_PROFILE_AND_PRESENCE);
        assertThat(testUserOptions.getTextSizeKey()).isEqualTo(UPDATED_TEXT_SIZE_KEY);
        assertThat(testUserOptions.getTextSizeSeq()).isEqualTo(UPDATED_TEXT_SIZE_SEQ);
        assertThat(testUserOptions.getEmailLevel()).isEqualTo(UPDATED_EMAIL_LEVEL);
        assertThat(testUserOptions.getEmailMessagesLevel()).isEqualTo(UPDATED_EMAIL_MESSAGES_LEVEL);
        assertThat(testUserOptions.getTitleCountModeKey()).isEqualTo(UPDATED_TITLE_COUNT_MODE_KEY);
        assertThat(testUserOptions.isEnableDefer()).isEqualTo(UPDATED_ENABLE_DEFER);
        assertThat(testUserOptions.getTimezone()).isEqualTo(UPDATED_TIMEZONE);
        assertThat(testUserOptions.isEnableAllowedPmUsers()).isEqualTo(UPDATED_ENABLE_ALLOWED_PM_USERS);
        assertThat(testUserOptions.getDarkSchemeId()).isEqualTo(UPDATED_DARK_SCHEME_ID);
        assertThat(testUserOptions.isSkipNewUserTips()).isEqualTo(UPDATED_SKIP_NEW_USER_TIPS);
        assertThat(testUserOptions.getColorSchemeId()).isEqualTo(UPDATED_COLOR_SCHEME_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserOptions() throws Exception {
        int databaseSizeBeforeUpdate = userOptionsRepository.findAll().size();

        // Create the UserOptions
        UserOptionsDTO userOptionsDTO = userOptionsMapper.toDto(userOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOptionsMockMvc.perform(put("/api/user-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserOptions in the database
        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserOptions() throws Exception {
        // Initialize the database
        userOptionsRepository.saveAndFlush(userOptions);

        int databaseSizeBeforeDelete = userOptionsRepository.findAll().size();

        // Delete the userOptions
        restUserOptionsMockMvc.perform(delete("/api/user-options/{id}", userOptions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserOptions> userOptionsList = userOptionsRepository.findAll();
        assertThat(userOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
