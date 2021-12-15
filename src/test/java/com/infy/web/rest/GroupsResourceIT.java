/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Groups;
import com.infy.repository.GroupsRepository;
import com.infy.service.GroupsService;
import com.infy.service.dto.GroupsDTO;
import com.infy.service.mapper.GroupsMapper;

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
 * Integration tests for the {@link GroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTOMATIC = false;
    private static final Boolean UPDATED_AUTOMATIC = true;

    private static final Integer DEFAULT_USER_COUNT = 1;
    private static final Integer UPDATED_USER_COUNT = 2;

    private static final String DEFAULT_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS = "AAAAAAAAAA";
    private static final String UPDATED_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIMARY_GROUP = false;
    private static final Boolean UPDATED_PRIMARY_GROUP = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRANT_TRUST_LEVEL = 1;
    private static final Integer UPDATED_GRANT_TRUST_LEVEL = 2;

    private static final String DEFAULT_INCOMING_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_INCOMING_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_MESSAGES = false;
    private static final Boolean UPDATED_HAS_MESSAGES = true;

    private static final String DEFAULT_FLAIR_URL = "AAAAAAAAAA";
    private static final String UPDATED_FLAIR_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FLAIR_BG_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_FLAIR_BG_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_FLAIR_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_FLAIR_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_BIO_RAW = "AAAAAAAAAA";
    private static final String UPDATED_BIO_RAW = "BBBBBBBBBB";

    private static final String DEFAULT_BIO_COOKED = "AAAAAAAAAA";
    private static final String UPDATED_BIO_COOKED = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_MEMBERSHIP_REQUESTS = false;
    private static final Boolean UPDATED_ALLOW_MEMBERSHIP_REQUESTS = true;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_DEFAULT_NOTIFICATION_LEVEL = 2;

    private static final Integer DEFAULT_VISIBILITY_LEVEL = 1;
    private static final Integer UPDATED_VISIBILITY_LEVEL = 2;

    private static final Boolean DEFAULT_PUBLIC_EXIT = false;
    private static final Boolean UPDATED_PUBLIC_EXIT = true;

    private static final Boolean DEFAULT_PUBLIC_ADMISSION = false;
    private static final Boolean UPDATED_PUBLIC_ADMISSION = true;

    private static final String DEFAULT_MEMBERSHIP_REQUEST_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_MEMBERSHIP_REQUEST_TEMPLATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MESSAGEABLE_LEVEL = 1;
    private static final Integer UPDATED_MESSAGEABLE_LEVEL = 2;

    private static final Integer DEFAULT_MENTIONABLE_LEVEL = 1;
    private static final Integer UPDATED_MENTIONABLE_LEVEL = 2;

    private static final String DEFAULT_SMTP_SERVER = "AAAAAAAAAA";
    private static final String UPDATED_SMTP_SERVER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SMTP_PORT = 1;
    private static final Integer UPDATED_SMTP_PORT = 2;

    private static final Boolean DEFAULT_SMTP_SSL = false;
    private static final Boolean UPDATED_SMTP_SSL = true;

    private static final String DEFAULT_IMAP_SERVER = "AAAAAAAAAA";
    private static final String UPDATED_IMAP_SERVER = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMAP_PORT = 1;
    private static final Integer UPDATED_IMAP_PORT = 2;

    private static final Boolean DEFAULT_IMAP_SSL = false;
    private static final Boolean UPDATED_IMAP_SSL = true;

    private static final String DEFAULT_IMAP_MAILBOX_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAP_MAILBOX_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMAP_UID_VALIDITY = 1;
    private static final Integer UPDATED_IMAP_UID_VALIDITY = 2;

    private static final Integer DEFAULT_IMAP_LAST_UID = 1;
    private static final Integer UPDATED_IMAP_LAST_UID = 2;

    private static final String DEFAULT_EMAIL_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLISH_READ_STATE = false;
    private static final Boolean UPDATED_PUBLISH_READ_STATE = true;

    private static final Integer DEFAULT_MEMBERS_VISIBILITY_LEVEL = 1;
    private static final Integer UPDATED_MEMBERS_VISIBILITY_LEVEL = 2;

    private static final String DEFAULT_IMAP_LAST_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_IMAP_LAST_ERROR = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMAP_OLD_EMAILS = 1;
    private static final Integer UPDATED_IMAP_OLD_EMAILS = 2;

    private static final Integer DEFAULT_IMAP_NEW_EMAILS = 1;
    private static final Integer UPDATED_IMAP_NEW_EMAILS = 2;

    private static final String DEFAULT_FLAIR_ICON = "AAAAAAAAAA";
    private static final String UPDATED_FLAIR_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLAIR_UPLOAD_ID = 1;
    private static final Integer UPDATED_FLAIR_UPLOAD_ID = 2;

    private static final Boolean DEFAULT_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES = false;
    private static final Boolean UPDATED_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES = true;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private GroupsMapper groupsMapper;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupsMockMvc;

    private Groups groups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groups createEntity(EntityManager em) {
        Groups groups = new Groups()
            .name(DEFAULT_NAME)
            .automatic(DEFAULT_AUTOMATIC)
            .userCount(DEFAULT_USER_COUNT)
            .automaticMembershipEmailDomains(DEFAULT_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS)
            .primaryGroup(DEFAULT_PRIMARY_GROUP)
            .title(DEFAULT_TITLE)
            .grantTrustLevel(DEFAULT_GRANT_TRUST_LEVEL)
            .incomingEmail(DEFAULT_INCOMING_EMAIL)
            .hasMessages(DEFAULT_HAS_MESSAGES)
            .flairUrl(DEFAULT_FLAIR_URL)
            .flairBgColor(DEFAULT_FLAIR_BG_COLOR)
            .flairColor(DEFAULT_FLAIR_COLOR)
            .bioRaw(DEFAULT_BIO_RAW)
            .bioCooked(DEFAULT_BIO_COOKED)
            .allowMembershipRequests(DEFAULT_ALLOW_MEMBERSHIP_REQUESTS)
            .fullName(DEFAULT_FULL_NAME)
            .defaultNotificationLevel(DEFAULT_DEFAULT_NOTIFICATION_LEVEL)
            .visibilityLevel(DEFAULT_VISIBILITY_LEVEL)
            .publicExit(DEFAULT_PUBLIC_EXIT)
            .publicAdmission(DEFAULT_PUBLIC_ADMISSION)
            .membershipRequestTemplate(DEFAULT_MEMBERSHIP_REQUEST_TEMPLATE)
            .messageableLevel(DEFAULT_MESSAGEABLE_LEVEL)
            .mentionableLevel(DEFAULT_MENTIONABLE_LEVEL)
            .smtpServer(DEFAULT_SMTP_SERVER)
            .smtpPort(DEFAULT_SMTP_PORT)
            .smtpSsl(DEFAULT_SMTP_SSL)
            .imapServer(DEFAULT_IMAP_SERVER)
            .imapPort(DEFAULT_IMAP_PORT)
            .imapSsl(DEFAULT_IMAP_SSL)
            .imapMailboxName(DEFAULT_IMAP_MAILBOX_NAME)
            .imapUidValidity(DEFAULT_IMAP_UID_VALIDITY)
            .imapLastUid(DEFAULT_IMAP_LAST_UID)
            .emailUsername(DEFAULT_EMAIL_USERNAME)
            .emailPassword(DEFAULT_EMAIL_PASSWORD)
            .publishReadState(DEFAULT_PUBLISH_READ_STATE)
            .membersVisibilityLevel(DEFAULT_MEMBERS_VISIBILITY_LEVEL)
            .imapLastError(DEFAULT_IMAP_LAST_ERROR)
            .imapOldEmails(DEFAULT_IMAP_OLD_EMAILS)
            .imapNewEmails(DEFAULT_IMAP_NEW_EMAILS)
            .flairIcon(DEFAULT_FLAIR_ICON)
            .flairUploadId(DEFAULT_FLAIR_UPLOAD_ID)
            .allowUnknownSenderTopicReplies(DEFAULT_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES);
        return groups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groups createUpdatedEntity(EntityManager em) {
        Groups groups = new Groups()
            .name(UPDATED_NAME)
            .automatic(UPDATED_AUTOMATIC)
            .userCount(UPDATED_USER_COUNT)
            .automaticMembershipEmailDomains(UPDATED_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS)
            .primaryGroup(UPDATED_PRIMARY_GROUP)
            .title(UPDATED_TITLE)
            .grantTrustLevel(UPDATED_GRANT_TRUST_LEVEL)
            .incomingEmail(UPDATED_INCOMING_EMAIL)
            .hasMessages(UPDATED_HAS_MESSAGES)
            .flairUrl(UPDATED_FLAIR_URL)
            .flairBgColor(UPDATED_FLAIR_BG_COLOR)
            .flairColor(UPDATED_FLAIR_COLOR)
            .bioRaw(UPDATED_BIO_RAW)
            .bioCooked(UPDATED_BIO_COOKED)
            .allowMembershipRequests(UPDATED_ALLOW_MEMBERSHIP_REQUESTS)
            .fullName(UPDATED_FULL_NAME)
            .defaultNotificationLevel(UPDATED_DEFAULT_NOTIFICATION_LEVEL)
            .visibilityLevel(UPDATED_VISIBILITY_LEVEL)
            .publicExit(UPDATED_PUBLIC_EXIT)
            .publicAdmission(UPDATED_PUBLIC_ADMISSION)
            .membershipRequestTemplate(UPDATED_MEMBERSHIP_REQUEST_TEMPLATE)
            .messageableLevel(UPDATED_MESSAGEABLE_LEVEL)
            .mentionableLevel(UPDATED_MENTIONABLE_LEVEL)
            .smtpServer(UPDATED_SMTP_SERVER)
            .smtpPort(UPDATED_SMTP_PORT)
            .smtpSsl(UPDATED_SMTP_SSL)
            .imapServer(UPDATED_IMAP_SERVER)
            .imapPort(UPDATED_IMAP_PORT)
            .imapSsl(UPDATED_IMAP_SSL)
            .imapMailboxName(UPDATED_IMAP_MAILBOX_NAME)
            .imapUidValidity(UPDATED_IMAP_UID_VALIDITY)
            .imapLastUid(UPDATED_IMAP_LAST_UID)
            .emailUsername(UPDATED_EMAIL_USERNAME)
            .emailPassword(UPDATED_EMAIL_PASSWORD)
            .publishReadState(UPDATED_PUBLISH_READ_STATE)
            .membersVisibilityLevel(UPDATED_MEMBERS_VISIBILITY_LEVEL)
            .imapLastError(UPDATED_IMAP_LAST_ERROR)
            .imapOldEmails(UPDATED_IMAP_OLD_EMAILS)
            .imapNewEmails(UPDATED_IMAP_NEW_EMAILS)
            .flairIcon(UPDATED_FLAIR_ICON)
            .flairUploadId(UPDATED_FLAIR_UPLOAD_ID)
            .allowUnknownSenderTopicReplies(UPDATED_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES);
        return groups;
    }

    @BeforeEach
    public void initTest() {
        groups = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroups() throws Exception {
        int databaseSizeBeforeCreate = groupsRepository.findAll().size();
        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);
        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isCreated());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeCreate + 1);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroups.isAutomatic()).isEqualTo(DEFAULT_AUTOMATIC);
        assertThat(testGroups.getUserCount()).isEqualTo(DEFAULT_USER_COUNT);
        assertThat(testGroups.getAutomaticMembershipEmailDomains()).isEqualTo(DEFAULT_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS);
        assertThat(testGroups.isPrimaryGroup()).isEqualTo(DEFAULT_PRIMARY_GROUP);
        assertThat(testGroups.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGroups.getGrantTrustLevel()).isEqualTo(DEFAULT_GRANT_TRUST_LEVEL);
        assertThat(testGroups.getIncomingEmail()).isEqualTo(DEFAULT_INCOMING_EMAIL);
        assertThat(testGroups.isHasMessages()).isEqualTo(DEFAULT_HAS_MESSAGES);
        assertThat(testGroups.getFlairUrl()).isEqualTo(DEFAULT_FLAIR_URL);
        assertThat(testGroups.getFlairBgColor()).isEqualTo(DEFAULT_FLAIR_BG_COLOR);
        assertThat(testGroups.getFlairColor()).isEqualTo(DEFAULT_FLAIR_COLOR);
        assertThat(testGroups.getBioRaw()).isEqualTo(DEFAULT_BIO_RAW);
        assertThat(testGroups.getBioCooked()).isEqualTo(DEFAULT_BIO_COOKED);
        assertThat(testGroups.isAllowMembershipRequests()).isEqualTo(DEFAULT_ALLOW_MEMBERSHIP_REQUESTS);
        assertThat(testGroups.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testGroups.getDefaultNotificationLevel()).isEqualTo(DEFAULT_DEFAULT_NOTIFICATION_LEVEL);
        assertThat(testGroups.getVisibilityLevel()).isEqualTo(DEFAULT_VISIBILITY_LEVEL);
        assertThat(testGroups.isPublicExit()).isEqualTo(DEFAULT_PUBLIC_EXIT);
        assertThat(testGroups.isPublicAdmission()).isEqualTo(DEFAULT_PUBLIC_ADMISSION);
        assertThat(testGroups.getMembershipRequestTemplate()).isEqualTo(DEFAULT_MEMBERSHIP_REQUEST_TEMPLATE);
        assertThat(testGroups.getMessageableLevel()).isEqualTo(DEFAULT_MESSAGEABLE_LEVEL);
        assertThat(testGroups.getMentionableLevel()).isEqualTo(DEFAULT_MENTIONABLE_LEVEL);
        assertThat(testGroups.getSmtpServer()).isEqualTo(DEFAULT_SMTP_SERVER);
        assertThat(testGroups.getSmtpPort()).isEqualTo(DEFAULT_SMTP_PORT);
        assertThat(testGroups.isSmtpSsl()).isEqualTo(DEFAULT_SMTP_SSL);
        assertThat(testGroups.getImapServer()).isEqualTo(DEFAULT_IMAP_SERVER);
        assertThat(testGroups.getImapPort()).isEqualTo(DEFAULT_IMAP_PORT);
        assertThat(testGroups.isImapSsl()).isEqualTo(DEFAULT_IMAP_SSL);
        assertThat(testGroups.getImapMailboxName()).isEqualTo(DEFAULT_IMAP_MAILBOX_NAME);
        assertThat(testGroups.getImapUidValidity()).isEqualTo(DEFAULT_IMAP_UID_VALIDITY);
        assertThat(testGroups.getImapLastUid()).isEqualTo(DEFAULT_IMAP_LAST_UID);
        assertThat(testGroups.getEmailUsername()).isEqualTo(DEFAULT_EMAIL_USERNAME);
        assertThat(testGroups.getEmailPassword()).isEqualTo(DEFAULT_EMAIL_PASSWORD);
        assertThat(testGroups.isPublishReadState()).isEqualTo(DEFAULT_PUBLISH_READ_STATE);
        assertThat(testGroups.getMembersVisibilityLevel()).isEqualTo(DEFAULT_MEMBERS_VISIBILITY_LEVEL);
        assertThat(testGroups.getImapLastError()).isEqualTo(DEFAULT_IMAP_LAST_ERROR);
        assertThat(testGroups.getImapOldEmails()).isEqualTo(DEFAULT_IMAP_OLD_EMAILS);
        assertThat(testGroups.getImapNewEmails()).isEqualTo(DEFAULT_IMAP_NEW_EMAILS);
        assertThat(testGroups.getFlairIcon()).isEqualTo(DEFAULT_FLAIR_ICON);
        assertThat(testGroups.getFlairUploadId()).isEqualTo(DEFAULT_FLAIR_UPLOAD_ID);
        assertThat(testGroups.isAllowUnknownSenderTopicReplies()).isEqualTo(DEFAULT_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES);
    }

    @Test
    @Transactional
    public void createGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupsRepository.findAll().size();

        // Create the Groups with an existing ID
        groups.setId(1L);
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setName(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutomaticIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setAutomatic(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setUserCount(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimaryGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setPrimaryGroup(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasMessagesIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setHasMessages(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowMembershipRequestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setAllowMembershipRequests(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefaultNotificationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setDefaultNotificationLevel(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibilityLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setVisibilityLevel(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicExitIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setPublicExit(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicAdmissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setPublicAdmission(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImapMailboxNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setImapMailboxName(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImapUidValidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setImapUidValidity(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImapLastUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setImapLastUid(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublishReadStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setPublishReadState(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMembersVisibilityLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setMembersVisibilityLevel(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowUnknownSenderTopicRepliesIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setAllowUnknownSenderTopicReplies(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);


        restGroupsMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        // Get all the groupsList
        restGroupsMockMvc.perform(get("/api/groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].automatic").value(hasItem(DEFAULT_AUTOMATIC.booleanValue())))
            .andExpect(jsonPath("$.[*].userCount").value(hasItem(DEFAULT_USER_COUNT)))
            .andExpect(jsonPath("$.[*].automaticMembershipEmailDomains").value(hasItem(DEFAULT_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS)))
            .andExpect(jsonPath("$.[*].primaryGroup").value(hasItem(DEFAULT_PRIMARY_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].grantTrustLevel").value(hasItem(DEFAULT_GRANT_TRUST_LEVEL)))
            .andExpect(jsonPath("$.[*].incomingEmail").value(hasItem(DEFAULT_INCOMING_EMAIL)))
            .andExpect(jsonPath("$.[*].hasMessages").value(hasItem(DEFAULT_HAS_MESSAGES.booleanValue())))
            .andExpect(jsonPath("$.[*].flairUrl").value(hasItem(DEFAULT_FLAIR_URL)))
            .andExpect(jsonPath("$.[*].flairBgColor").value(hasItem(DEFAULT_FLAIR_BG_COLOR)))
            .andExpect(jsonPath("$.[*].flairColor").value(hasItem(DEFAULT_FLAIR_COLOR)))
            .andExpect(jsonPath("$.[*].bioRaw").value(hasItem(DEFAULT_BIO_RAW)))
            .andExpect(jsonPath("$.[*].bioCooked").value(hasItem(DEFAULT_BIO_COOKED)))
            .andExpect(jsonPath("$.[*].allowMembershipRequests").value(hasItem(DEFAULT_ALLOW_MEMBERSHIP_REQUESTS.booleanValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].defaultNotificationLevel").value(hasItem(DEFAULT_DEFAULT_NOTIFICATION_LEVEL)))
            .andExpect(jsonPath("$.[*].visibilityLevel").value(hasItem(DEFAULT_VISIBILITY_LEVEL)))
            .andExpect(jsonPath("$.[*].publicExit").value(hasItem(DEFAULT_PUBLIC_EXIT.booleanValue())))
            .andExpect(jsonPath("$.[*].publicAdmission").value(hasItem(DEFAULT_PUBLIC_ADMISSION.booleanValue())))
            .andExpect(jsonPath("$.[*].membershipRequestTemplate").value(hasItem(DEFAULT_MEMBERSHIP_REQUEST_TEMPLATE)))
            .andExpect(jsonPath("$.[*].messageableLevel").value(hasItem(DEFAULT_MESSAGEABLE_LEVEL)))
            .andExpect(jsonPath("$.[*].mentionableLevel").value(hasItem(DEFAULT_MENTIONABLE_LEVEL)))
            .andExpect(jsonPath("$.[*].smtpServer").value(hasItem(DEFAULT_SMTP_SERVER)))
            .andExpect(jsonPath("$.[*].smtpPort").value(hasItem(DEFAULT_SMTP_PORT)))
            .andExpect(jsonPath("$.[*].smtpSsl").value(hasItem(DEFAULT_SMTP_SSL.booleanValue())))
            .andExpect(jsonPath("$.[*].imapServer").value(hasItem(DEFAULT_IMAP_SERVER)))
            .andExpect(jsonPath("$.[*].imapPort").value(hasItem(DEFAULT_IMAP_PORT)))
            .andExpect(jsonPath("$.[*].imapSsl").value(hasItem(DEFAULT_IMAP_SSL.booleanValue())))
            .andExpect(jsonPath("$.[*].imapMailboxName").value(hasItem(DEFAULT_IMAP_MAILBOX_NAME)))
            .andExpect(jsonPath("$.[*].imapUidValidity").value(hasItem(DEFAULT_IMAP_UID_VALIDITY)))
            .andExpect(jsonPath("$.[*].imapLastUid").value(hasItem(DEFAULT_IMAP_LAST_UID)))
            .andExpect(jsonPath("$.[*].emailUsername").value(hasItem(DEFAULT_EMAIL_USERNAME)))
            .andExpect(jsonPath("$.[*].emailPassword").value(hasItem(DEFAULT_EMAIL_PASSWORD)))
            .andExpect(jsonPath("$.[*].publishReadState").value(hasItem(DEFAULT_PUBLISH_READ_STATE.booleanValue())))
            .andExpect(jsonPath("$.[*].membersVisibilityLevel").value(hasItem(DEFAULT_MEMBERS_VISIBILITY_LEVEL)))
            .andExpect(jsonPath("$.[*].imapLastError").value(hasItem(DEFAULT_IMAP_LAST_ERROR)))
            .andExpect(jsonPath("$.[*].imapOldEmails").value(hasItem(DEFAULT_IMAP_OLD_EMAILS)))
            .andExpect(jsonPath("$.[*].imapNewEmails").value(hasItem(DEFAULT_IMAP_NEW_EMAILS)))
            .andExpect(jsonPath("$.[*].flairIcon").value(hasItem(DEFAULT_FLAIR_ICON)))
            .andExpect(jsonPath("$.[*].flairUploadId").value(hasItem(DEFAULT_FLAIR_UPLOAD_ID)))
            .andExpect(jsonPath("$.[*].allowUnknownSenderTopicReplies").value(hasItem(DEFAULT_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES.booleanValue())));
    }

    @Test
    @Transactional
    public void getGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        // Get the groups
        restGroupsMockMvc.perform(get("/api/groups/{id}", groups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.automatic").value(DEFAULT_AUTOMATIC.booleanValue()))
            .andExpect(jsonPath("$.userCount").value(DEFAULT_USER_COUNT))
            .andExpect(jsonPath("$.automaticMembershipEmailDomains").value(DEFAULT_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS))
            .andExpect(jsonPath("$.primaryGroup").value(DEFAULT_PRIMARY_GROUP.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.grantTrustLevel").value(DEFAULT_GRANT_TRUST_LEVEL))
            .andExpect(jsonPath("$.incomingEmail").value(DEFAULT_INCOMING_EMAIL))
            .andExpect(jsonPath("$.hasMessages").value(DEFAULT_HAS_MESSAGES.booleanValue()))
            .andExpect(jsonPath("$.flairUrl").value(DEFAULT_FLAIR_URL))
            .andExpect(jsonPath("$.flairBgColor").value(DEFAULT_FLAIR_BG_COLOR))
            .andExpect(jsonPath("$.flairColor").value(DEFAULT_FLAIR_COLOR))
            .andExpect(jsonPath("$.bioRaw").value(DEFAULT_BIO_RAW))
            .andExpect(jsonPath("$.bioCooked").value(DEFAULT_BIO_COOKED))
            .andExpect(jsonPath("$.allowMembershipRequests").value(DEFAULT_ALLOW_MEMBERSHIP_REQUESTS.booleanValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.defaultNotificationLevel").value(DEFAULT_DEFAULT_NOTIFICATION_LEVEL))
            .andExpect(jsonPath("$.visibilityLevel").value(DEFAULT_VISIBILITY_LEVEL))
            .andExpect(jsonPath("$.publicExit").value(DEFAULT_PUBLIC_EXIT.booleanValue()))
            .andExpect(jsonPath("$.publicAdmission").value(DEFAULT_PUBLIC_ADMISSION.booleanValue()))
            .andExpect(jsonPath("$.membershipRequestTemplate").value(DEFAULT_MEMBERSHIP_REQUEST_TEMPLATE))
            .andExpect(jsonPath("$.messageableLevel").value(DEFAULT_MESSAGEABLE_LEVEL))
            .andExpect(jsonPath("$.mentionableLevel").value(DEFAULT_MENTIONABLE_LEVEL))
            .andExpect(jsonPath("$.smtpServer").value(DEFAULT_SMTP_SERVER))
            .andExpect(jsonPath("$.smtpPort").value(DEFAULT_SMTP_PORT))
            .andExpect(jsonPath("$.smtpSsl").value(DEFAULT_SMTP_SSL.booleanValue()))
            .andExpect(jsonPath("$.imapServer").value(DEFAULT_IMAP_SERVER))
            .andExpect(jsonPath("$.imapPort").value(DEFAULT_IMAP_PORT))
            .andExpect(jsonPath("$.imapSsl").value(DEFAULT_IMAP_SSL.booleanValue()))
            .andExpect(jsonPath("$.imapMailboxName").value(DEFAULT_IMAP_MAILBOX_NAME))
            .andExpect(jsonPath("$.imapUidValidity").value(DEFAULT_IMAP_UID_VALIDITY))
            .andExpect(jsonPath("$.imapLastUid").value(DEFAULT_IMAP_LAST_UID))
            .andExpect(jsonPath("$.emailUsername").value(DEFAULT_EMAIL_USERNAME))
            .andExpect(jsonPath("$.emailPassword").value(DEFAULT_EMAIL_PASSWORD))
            .andExpect(jsonPath("$.publishReadState").value(DEFAULT_PUBLISH_READ_STATE.booleanValue()))
            .andExpect(jsonPath("$.membersVisibilityLevel").value(DEFAULT_MEMBERS_VISIBILITY_LEVEL))
            .andExpect(jsonPath("$.imapLastError").value(DEFAULT_IMAP_LAST_ERROR))
            .andExpect(jsonPath("$.imapOldEmails").value(DEFAULT_IMAP_OLD_EMAILS))
            .andExpect(jsonPath("$.imapNewEmails").value(DEFAULT_IMAP_NEW_EMAILS))
            .andExpect(jsonPath("$.flairIcon").value(DEFAULT_FLAIR_ICON))
            .andExpect(jsonPath("$.flairUploadId").value(DEFAULT_FLAIR_UPLOAD_ID))
            .andExpect(jsonPath("$.allowUnknownSenderTopicReplies").value(DEFAULT_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGroups() throws Exception {
        // Get the groups
        restGroupsMockMvc.perform(get("/api/groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Update the groups
        Groups updatedGroups = groupsRepository.findById(groups.getId()).get();
        // Disconnect from session so that the updates on updatedGroups are not directly saved in db
        em.detach(updatedGroups);
        updatedGroups
            .name(UPDATED_NAME)
            .automatic(UPDATED_AUTOMATIC)
            .userCount(UPDATED_USER_COUNT)
            .automaticMembershipEmailDomains(UPDATED_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS)
            .primaryGroup(UPDATED_PRIMARY_GROUP)
            .title(UPDATED_TITLE)
            .grantTrustLevel(UPDATED_GRANT_TRUST_LEVEL)
            .incomingEmail(UPDATED_INCOMING_EMAIL)
            .hasMessages(UPDATED_HAS_MESSAGES)
            .flairUrl(UPDATED_FLAIR_URL)
            .flairBgColor(UPDATED_FLAIR_BG_COLOR)
            .flairColor(UPDATED_FLAIR_COLOR)
            .bioRaw(UPDATED_BIO_RAW)
            .bioCooked(UPDATED_BIO_COOKED)
            .allowMembershipRequests(UPDATED_ALLOW_MEMBERSHIP_REQUESTS)
            .fullName(UPDATED_FULL_NAME)
            .defaultNotificationLevel(UPDATED_DEFAULT_NOTIFICATION_LEVEL)
            .visibilityLevel(UPDATED_VISIBILITY_LEVEL)
            .publicExit(UPDATED_PUBLIC_EXIT)
            .publicAdmission(UPDATED_PUBLIC_ADMISSION)
            .membershipRequestTemplate(UPDATED_MEMBERSHIP_REQUEST_TEMPLATE)
            .messageableLevel(UPDATED_MESSAGEABLE_LEVEL)
            .mentionableLevel(UPDATED_MENTIONABLE_LEVEL)
            .smtpServer(UPDATED_SMTP_SERVER)
            .smtpPort(UPDATED_SMTP_PORT)
            .smtpSsl(UPDATED_SMTP_SSL)
            .imapServer(UPDATED_IMAP_SERVER)
            .imapPort(UPDATED_IMAP_PORT)
            .imapSsl(UPDATED_IMAP_SSL)
            .imapMailboxName(UPDATED_IMAP_MAILBOX_NAME)
            .imapUidValidity(UPDATED_IMAP_UID_VALIDITY)
            .imapLastUid(UPDATED_IMAP_LAST_UID)
            .emailUsername(UPDATED_EMAIL_USERNAME)
            .emailPassword(UPDATED_EMAIL_PASSWORD)
            .publishReadState(UPDATED_PUBLISH_READ_STATE)
            .membersVisibilityLevel(UPDATED_MEMBERS_VISIBILITY_LEVEL)
            .imapLastError(UPDATED_IMAP_LAST_ERROR)
            .imapOldEmails(UPDATED_IMAP_OLD_EMAILS)
            .imapNewEmails(UPDATED_IMAP_NEW_EMAILS)
            .flairIcon(UPDATED_FLAIR_ICON)
            .flairUploadId(UPDATED_FLAIR_UPLOAD_ID)
            .allowUnknownSenderTopicReplies(UPDATED_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES);
        GroupsDTO groupsDTO = groupsMapper.toDto(updatedGroups);

        restGroupsMockMvc.perform(put("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroups.isAutomatic()).isEqualTo(UPDATED_AUTOMATIC);
        assertThat(testGroups.getUserCount()).isEqualTo(UPDATED_USER_COUNT);
        assertThat(testGroups.getAutomaticMembershipEmailDomains()).isEqualTo(UPDATED_AUTOMATIC_MEMBERSHIP_EMAIL_DOMAINS);
        assertThat(testGroups.isPrimaryGroup()).isEqualTo(UPDATED_PRIMARY_GROUP);
        assertThat(testGroups.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGroups.getGrantTrustLevel()).isEqualTo(UPDATED_GRANT_TRUST_LEVEL);
        assertThat(testGroups.getIncomingEmail()).isEqualTo(UPDATED_INCOMING_EMAIL);
        assertThat(testGroups.isHasMessages()).isEqualTo(UPDATED_HAS_MESSAGES);
        assertThat(testGroups.getFlairUrl()).isEqualTo(UPDATED_FLAIR_URL);
        assertThat(testGroups.getFlairBgColor()).isEqualTo(UPDATED_FLAIR_BG_COLOR);
        assertThat(testGroups.getFlairColor()).isEqualTo(UPDATED_FLAIR_COLOR);
        assertThat(testGroups.getBioRaw()).isEqualTo(UPDATED_BIO_RAW);
        assertThat(testGroups.getBioCooked()).isEqualTo(UPDATED_BIO_COOKED);
        assertThat(testGroups.isAllowMembershipRequests()).isEqualTo(UPDATED_ALLOW_MEMBERSHIP_REQUESTS);
        assertThat(testGroups.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testGroups.getDefaultNotificationLevel()).isEqualTo(UPDATED_DEFAULT_NOTIFICATION_LEVEL);
        assertThat(testGroups.getVisibilityLevel()).isEqualTo(UPDATED_VISIBILITY_LEVEL);
        assertThat(testGroups.isPublicExit()).isEqualTo(UPDATED_PUBLIC_EXIT);
        assertThat(testGroups.isPublicAdmission()).isEqualTo(UPDATED_PUBLIC_ADMISSION);
        assertThat(testGroups.getMembershipRequestTemplate()).isEqualTo(UPDATED_MEMBERSHIP_REQUEST_TEMPLATE);
        assertThat(testGroups.getMessageableLevel()).isEqualTo(UPDATED_MESSAGEABLE_LEVEL);
        assertThat(testGroups.getMentionableLevel()).isEqualTo(UPDATED_MENTIONABLE_LEVEL);
        assertThat(testGroups.getSmtpServer()).isEqualTo(UPDATED_SMTP_SERVER);
        assertThat(testGroups.getSmtpPort()).isEqualTo(UPDATED_SMTP_PORT);
        assertThat(testGroups.isSmtpSsl()).isEqualTo(UPDATED_SMTP_SSL);
        assertThat(testGroups.getImapServer()).isEqualTo(UPDATED_IMAP_SERVER);
        assertThat(testGroups.getImapPort()).isEqualTo(UPDATED_IMAP_PORT);
        assertThat(testGroups.isImapSsl()).isEqualTo(UPDATED_IMAP_SSL);
        assertThat(testGroups.getImapMailboxName()).isEqualTo(UPDATED_IMAP_MAILBOX_NAME);
        assertThat(testGroups.getImapUidValidity()).isEqualTo(UPDATED_IMAP_UID_VALIDITY);
        assertThat(testGroups.getImapLastUid()).isEqualTo(UPDATED_IMAP_LAST_UID);
        assertThat(testGroups.getEmailUsername()).isEqualTo(UPDATED_EMAIL_USERNAME);
        assertThat(testGroups.getEmailPassword()).isEqualTo(UPDATED_EMAIL_PASSWORD);
        assertThat(testGroups.isPublishReadState()).isEqualTo(UPDATED_PUBLISH_READ_STATE);
        assertThat(testGroups.getMembersVisibilityLevel()).isEqualTo(UPDATED_MEMBERS_VISIBILITY_LEVEL);
        assertThat(testGroups.getImapLastError()).isEqualTo(UPDATED_IMAP_LAST_ERROR);
        assertThat(testGroups.getImapOldEmails()).isEqualTo(UPDATED_IMAP_OLD_EMAILS);
        assertThat(testGroups.getImapNewEmails()).isEqualTo(UPDATED_IMAP_NEW_EMAILS);
        assertThat(testGroups.getFlairIcon()).isEqualTo(UPDATED_FLAIR_ICON);
        assertThat(testGroups.getFlairUploadId()).isEqualTo(UPDATED_FLAIR_UPLOAD_ID);
        assertThat(testGroups.isAllowUnknownSenderTopicReplies()).isEqualTo(UPDATED_ALLOW_UNKNOWN_SENDER_TOPIC_REPLIES);
    }

    @Test
    @Transactional
    public void updateNonExistingGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupsMockMvc.perform(put("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeDelete = groupsRepository.findAll().size();

        // Delete the groups
        restGroupsMockMvc.perform(delete("/api/groups/{id}", groups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
