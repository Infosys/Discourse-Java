/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Users;
import com.infy.repository.UsersRepository;
import com.infy.service.UsersService;
import com.infy.service.dto.UsersDTO;
import com.infy.service.mapper.UsersMapper;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UsersResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SEEN_NOTIFICATION_ID = 1L;
    private static final Long UPDATED_SEEN_NOTIFICATION_ID = 2L;

    private static final Instant DEFAULT_LAST_POSTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_POSTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_SALT = "AAAAAAAAAA";
    private static final String UPDATED_SALT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_USERNAME_LOWER = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME_LOWER = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_SEEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_SEEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ADMIN = false;
    private static final Boolean UPDATED_ADMIN = true;

    private static final Instant DEFAULT_LAST_EMAILED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EMAILED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TRUST_LEVEL = 1;
    private static final Integer UPDATED_TRUST_LEVEL = 2;

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String DEFAULT_APPROVED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_BY_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPROVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PREVIOUS_VISIT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PREVIOUS_VISIT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SUSPENDED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUSPENDED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SUSPENDED_TILL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUSPENDED_TILL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VIEWS = 1;
    private static final Integer UPDATED_VIEWS = 2;

    private static final Integer DEFAULT_FLAG_LEVEL = 1;
    private static final Integer UPDATED_FLAG_LEVEL = 2;

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MODERATOR = false;
    private static final Boolean UPDATED_MODERATOR = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_UPLOADED_AVATAR_ID = 1L;
    private static final Long UPDATED_UPLOADED_AVATAR_ID = 2L;

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIMARY_GROUP_ID = 1L;
    private static final Long UPDATED_PRIMARY_GROUP_ID = 2L;

    private static final String DEFAULT_REGISTRATION_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_IP_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STAGED = false;
    private static final Boolean UPDATED_STAGED = true;

    private static final Instant DEFAULT_FIRST_SEEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_SEEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SILENCED_TILL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SILENCED_TILL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_GROUP_LOCKED_TRUST_LEVEL = 1;
    private static final Integer UPDATED_GROUP_LOCKED_TRUST_LEVEL = 2;

    private static final Integer DEFAULT_MANUAL_LOCKED_TRUST_LEVEL = 1;
    private static final Integer UPDATED_MANUAL_LOCKED_TRUST_LEVEL = 2;

    private static final String DEFAULT_SECURE_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_SECURE_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersMockMvc;

    private Users users;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createEntity(EntityManager em) {
        Users users = new Users()
            .username(DEFAULT_USERNAME)
            .name(DEFAULT_NAME)
            .seenNotificationId(DEFAULT_SEEN_NOTIFICATION_ID)
            .lastPostedAt(DEFAULT_LAST_POSTED_AT)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .salt(DEFAULT_SALT)
            .active(DEFAULT_ACTIVE)
            .usernameLower(DEFAULT_USERNAME_LOWER)
            .lastSeenAt(DEFAULT_LAST_SEEN_AT)
            .admin(DEFAULT_ADMIN)
            .lastEmailedAt(DEFAULT_LAST_EMAILED_AT)
            .trustLevel(DEFAULT_TRUST_LEVEL)
            .approved(DEFAULT_APPROVED)
            .approvedById(DEFAULT_APPROVED_BY_ID)
            .approvedAt(DEFAULT_APPROVED_AT)
            .previousVisitAt(DEFAULT_PREVIOUS_VISIT_AT)
            .suspendedAt(DEFAULT_SUSPENDED_AT)
            .suspendedTill(DEFAULT_SUSPENDED_TILL)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .views(DEFAULT_VIEWS)
            .flagLevel(DEFAULT_FLAG_LEVEL)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .moderator(DEFAULT_MODERATOR)
            .title(DEFAULT_TITLE)
            .uploadedAvatarId(DEFAULT_UPLOADED_AVATAR_ID)
            .locale(DEFAULT_LOCALE)
            .primaryGroupId(DEFAULT_PRIMARY_GROUP_ID)
            .registrationIpAddress(DEFAULT_REGISTRATION_IP_ADDRESS)
            .staged(DEFAULT_STAGED)
            .firstSeenAt(DEFAULT_FIRST_SEEN_AT)
            .silencedTill(DEFAULT_SILENCED_TILL)
            .groupLockedTrustLevel(DEFAULT_GROUP_LOCKED_TRUST_LEVEL)
            .manualLockedTrustLevel(DEFAULT_MANUAL_LOCKED_TRUST_LEVEL)
            .secureIdentifier(DEFAULT_SECURE_IDENTIFIER);
        return users;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createUpdatedEntity(EntityManager em) {
        Users users = new Users()
            .username(UPDATED_USERNAME)
            .name(UPDATED_NAME)
            .seenNotificationId(UPDATED_SEEN_NOTIFICATION_ID)
            .lastPostedAt(UPDATED_LAST_POSTED_AT)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .salt(UPDATED_SALT)
            .active(UPDATED_ACTIVE)
            .usernameLower(UPDATED_USERNAME_LOWER)
            .lastSeenAt(UPDATED_LAST_SEEN_AT)
            .admin(UPDATED_ADMIN)
            .lastEmailedAt(UPDATED_LAST_EMAILED_AT)
            .trustLevel(UPDATED_TRUST_LEVEL)
            .approved(UPDATED_APPROVED)
            .approvedById(UPDATED_APPROVED_BY_ID)
            .approvedAt(UPDATED_APPROVED_AT)
            .previousVisitAt(UPDATED_PREVIOUS_VISIT_AT)
            .suspendedAt(UPDATED_SUSPENDED_AT)
            .suspendedTill(UPDATED_SUSPENDED_TILL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .views(UPDATED_VIEWS)
            .flagLevel(UPDATED_FLAG_LEVEL)
            .ipAddress(UPDATED_IP_ADDRESS)
            .moderator(UPDATED_MODERATOR)
            .title(UPDATED_TITLE)
            .uploadedAvatarId(UPDATED_UPLOADED_AVATAR_ID)
            .locale(UPDATED_LOCALE)
            .primaryGroupId(UPDATED_PRIMARY_GROUP_ID)
            .registrationIpAddress(UPDATED_REGISTRATION_IP_ADDRESS)
            .staged(UPDATED_STAGED)
            .firstSeenAt(UPDATED_FIRST_SEEN_AT)
            .silencedTill(UPDATED_SILENCED_TILL)
            .groupLockedTrustLevel(UPDATED_GROUP_LOCKED_TRUST_LEVEL)
            .manualLockedTrustLevel(UPDATED_MANUAL_LOCKED_TRUST_LEVEL)
            .secureIdentifier(UPDATED_SECURE_IDENTIFIER);
        return users;
    }

    @BeforeEach
    public void initTest() {
        users = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();
        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);
        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUsers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsers.getSeenNotificationId()).isEqualTo(DEFAULT_SEEN_NOTIFICATION_ID);
        assertThat(testUsers.getLastPostedAt()).isEqualTo(DEFAULT_LAST_POSTED_AT);
        assertThat(testUsers.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testUsers.getSalt()).isEqualTo(DEFAULT_SALT);
        assertThat(testUsers.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testUsers.getUsernameLower()).isEqualTo(DEFAULT_USERNAME_LOWER);
        assertThat(testUsers.getLastSeenAt()).isEqualTo(DEFAULT_LAST_SEEN_AT);
        assertThat(testUsers.isAdmin()).isEqualTo(DEFAULT_ADMIN);
        assertThat(testUsers.getLastEmailedAt()).isEqualTo(DEFAULT_LAST_EMAILED_AT);
        assertThat(testUsers.getTrustLevel()).isEqualTo(DEFAULT_TRUST_LEVEL);
        assertThat(testUsers.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testUsers.getApprovedById()).isEqualTo(DEFAULT_APPROVED_BY_ID);
        assertThat(testUsers.getApprovedAt()).isEqualTo(DEFAULT_APPROVED_AT);
        assertThat(testUsers.getPreviousVisitAt()).isEqualTo(DEFAULT_PREVIOUS_VISIT_AT);
        assertThat(testUsers.getSuspendedAt()).isEqualTo(DEFAULT_SUSPENDED_AT);
        assertThat(testUsers.getSuspendedTill()).isEqualTo(DEFAULT_SUSPENDED_TILL);
        assertThat(testUsers.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testUsers.getViews()).isEqualTo(DEFAULT_VIEWS);
        assertThat(testUsers.getFlagLevel()).isEqualTo(DEFAULT_FLAG_LEVEL);
        assertThat(testUsers.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testUsers.isModerator()).isEqualTo(DEFAULT_MODERATOR);
        assertThat(testUsers.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUsers.getUploadedAvatarId()).isEqualTo(DEFAULT_UPLOADED_AVATAR_ID);
        assertThat(testUsers.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testUsers.getPrimaryGroupId()).isEqualTo(DEFAULT_PRIMARY_GROUP_ID);
        assertThat(testUsers.getRegistrationIpAddress()).isEqualTo(DEFAULT_REGISTRATION_IP_ADDRESS);
        assertThat(testUsers.isStaged()).isEqualTo(DEFAULT_STAGED);
        assertThat(testUsers.getFirstSeenAt()).isEqualTo(DEFAULT_FIRST_SEEN_AT);
        assertThat(testUsers.getSilencedTill()).isEqualTo(DEFAULT_SILENCED_TILL);
        assertThat(testUsers.getGroupLockedTrustLevel()).isEqualTo(DEFAULT_GROUP_LOCKED_TRUST_LEVEL);
        assertThat(testUsers.getManualLockedTrustLevel()).isEqualTo(DEFAULT_MANUAL_LOCKED_TRUST_LEVEL);
        assertThat(testUsers.getSecureIdentifier()).isEqualTo(DEFAULT_SECURE_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users with an existing ID
        users.setId(1L);
        UsersDTO usersDTO = usersMapper.toDto(users);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setUsername(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeenNotificationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setSeenNotificationId(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setActive(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameLowerIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setUsernameLower(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdminIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setAdmin(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrustLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setTrustLevel(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setApproved(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setViews(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlagLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setFlagLevel(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStagedIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setStaged(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList
        restUsersMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].seenNotificationId").value(hasItem(DEFAULT_SEEN_NOTIFICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastPostedAt").value(hasItem(DEFAULT_LAST_POSTED_AT.toString())))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].salt").value(hasItem(DEFAULT_SALT)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].usernameLower").value(hasItem(DEFAULT_USERNAME_LOWER)))
            .andExpect(jsonPath("$.[*].lastSeenAt").value(hasItem(DEFAULT_LAST_SEEN_AT.toString())))
            .andExpect(jsonPath("$.[*].admin").value(hasItem(DEFAULT_ADMIN.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEmailedAt").value(hasItem(DEFAULT_LAST_EMAILED_AT.toString())))
            .andExpect(jsonPath("$.[*].trustLevel").value(hasItem(DEFAULT_TRUST_LEVEL)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].approvedById").value(hasItem(DEFAULT_APPROVED_BY_ID)))
            .andExpect(jsonPath("$.[*].approvedAt").value(hasItem(DEFAULT_APPROVED_AT.toString())))
            .andExpect(jsonPath("$.[*].previousVisitAt").value(hasItem(DEFAULT_PREVIOUS_VISIT_AT.toString())))
            .andExpect(jsonPath("$.[*].suspendedAt").value(hasItem(DEFAULT_SUSPENDED_AT.toString())))
            .andExpect(jsonPath("$.[*].suspendedTill").value(hasItem(DEFAULT_SUSPENDED_TILL.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS)))
            .andExpect(jsonPath("$.[*].flagLevel").value(hasItem(DEFAULT_FLAG_LEVEL)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].moderator").value(hasItem(DEFAULT_MODERATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].uploadedAvatarId").value(hasItem(DEFAULT_UPLOADED_AVATAR_ID.intValue())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].primaryGroupId").value(hasItem(DEFAULT_PRIMARY_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].registrationIpAddress").value(hasItem(DEFAULT_REGISTRATION_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].staged").value(hasItem(DEFAULT_STAGED.booleanValue())))
            .andExpect(jsonPath("$.[*].firstSeenAt").value(hasItem(DEFAULT_FIRST_SEEN_AT.toString())))
            .andExpect(jsonPath("$.[*].silencedTill").value(hasItem(DEFAULT_SILENCED_TILL.toString())))
            .andExpect(jsonPath("$.[*].groupLockedTrustLevel").value(hasItem(DEFAULT_GROUP_LOCKED_TRUST_LEVEL)))
            .andExpect(jsonPath("$.[*].manualLockedTrustLevel").value(hasItem(DEFAULT_MANUAL_LOCKED_TRUST_LEVEL)))
            .andExpect(jsonPath("$.[*].secureIdentifier").value(hasItem(DEFAULT_SECURE_IDENTIFIER)));
    }

    @Test
    @Transactional
    public void getUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.seenNotificationId").value(DEFAULT_SEEN_NOTIFICATION_ID.intValue()))
            .andExpect(jsonPath("$.lastPostedAt").value(DEFAULT_LAST_POSTED_AT.toString()))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.salt").value(DEFAULT_SALT))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.usernameLower").value(DEFAULT_USERNAME_LOWER))
            .andExpect(jsonPath("$.lastSeenAt").value(DEFAULT_LAST_SEEN_AT.toString()))
            .andExpect(jsonPath("$.admin").value(DEFAULT_ADMIN.booleanValue()))
            .andExpect(jsonPath("$.lastEmailedAt").value(DEFAULT_LAST_EMAILED_AT.toString()))
            .andExpect(jsonPath("$.trustLevel").value(DEFAULT_TRUST_LEVEL))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.approvedById").value(DEFAULT_APPROVED_BY_ID))
            .andExpect(jsonPath("$.approvedAt").value(DEFAULT_APPROVED_AT.toString()))
            .andExpect(jsonPath("$.previousVisitAt").value(DEFAULT_PREVIOUS_VISIT_AT.toString()))
            .andExpect(jsonPath("$.suspendedAt").value(DEFAULT_SUSPENDED_AT.toString()))
            .andExpect(jsonPath("$.suspendedTill").value(DEFAULT_SUSPENDED_TILL.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.views").value(DEFAULT_VIEWS))
            .andExpect(jsonPath("$.flagLevel").value(DEFAULT_FLAG_LEVEL))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.moderator").value(DEFAULT_MODERATOR.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.uploadedAvatarId").value(DEFAULT_UPLOADED_AVATAR_ID.intValue()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.primaryGroupId").value(DEFAULT_PRIMARY_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.registrationIpAddress").value(DEFAULT_REGISTRATION_IP_ADDRESS))
            .andExpect(jsonPath("$.staged").value(DEFAULT_STAGED.booleanValue()))
            .andExpect(jsonPath("$.firstSeenAt").value(DEFAULT_FIRST_SEEN_AT.toString()))
            .andExpect(jsonPath("$.silencedTill").value(DEFAULT_SILENCED_TILL.toString()))
            .andExpect(jsonPath("$.groupLockedTrustLevel").value(DEFAULT_GROUP_LOCKED_TRUST_LEVEL))
            .andExpect(jsonPath("$.manualLockedTrustLevel").value(DEFAULT_MANUAL_LOCKED_TRUST_LEVEL))
            .andExpect(jsonPath("$.secureIdentifier").value(DEFAULT_SECURE_IDENTIFIER));
    }
    @Test
    @Transactional
    public void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findById(users.getId()).get();
        // Disconnect from session so that the updates on updatedUsers are not directly saved in db
        em.detach(updatedUsers);
        updatedUsers
            .username(UPDATED_USERNAME)
            .name(UPDATED_NAME)
            .seenNotificationId(UPDATED_SEEN_NOTIFICATION_ID)
            .lastPostedAt(UPDATED_LAST_POSTED_AT)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .salt(UPDATED_SALT)
            .active(UPDATED_ACTIVE)
            .usernameLower(UPDATED_USERNAME_LOWER)
            .lastSeenAt(UPDATED_LAST_SEEN_AT)
            .admin(UPDATED_ADMIN)
            .lastEmailedAt(UPDATED_LAST_EMAILED_AT)
            .trustLevel(UPDATED_TRUST_LEVEL)
            .approved(UPDATED_APPROVED)
            .approvedById(UPDATED_APPROVED_BY_ID)
            .approvedAt(UPDATED_APPROVED_AT)
            .previousVisitAt(UPDATED_PREVIOUS_VISIT_AT)
            .suspendedAt(UPDATED_SUSPENDED_AT)
            .suspendedTill(UPDATED_SUSPENDED_TILL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .views(UPDATED_VIEWS)
            .flagLevel(UPDATED_FLAG_LEVEL)
            .ipAddress(UPDATED_IP_ADDRESS)
            .moderator(UPDATED_MODERATOR)
            .title(UPDATED_TITLE)
            .uploadedAvatarId(UPDATED_UPLOADED_AVATAR_ID)
            .locale(UPDATED_LOCALE)
            .primaryGroupId(UPDATED_PRIMARY_GROUP_ID)
            .registrationIpAddress(UPDATED_REGISTRATION_IP_ADDRESS)
            .staged(UPDATED_STAGED)
            .firstSeenAt(UPDATED_FIRST_SEEN_AT)
            .silencedTill(UPDATED_SILENCED_TILL)
            .groupLockedTrustLevel(UPDATED_GROUP_LOCKED_TRUST_LEVEL)
            .manualLockedTrustLevel(UPDATED_MANUAL_LOCKED_TRUST_LEVEL)
            .secureIdentifier(UPDATED_SECURE_IDENTIFIER);
        UsersDTO usersDTO = usersMapper.toDto(updatedUsers);

        restUsersMockMvc.perform(put("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUsers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsers.getSeenNotificationId()).isEqualTo(UPDATED_SEEN_NOTIFICATION_ID);
        assertThat(testUsers.getLastPostedAt()).isEqualTo(UPDATED_LAST_POSTED_AT);
        assertThat(testUsers.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testUsers.getSalt()).isEqualTo(UPDATED_SALT);
        assertThat(testUsers.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testUsers.getUsernameLower()).isEqualTo(UPDATED_USERNAME_LOWER);
        assertThat(testUsers.getLastSeenAt()).isEqualTo(UPDATED_LAST_SEEN_AT);
        assertThat(testUsers.isAdmin()).isEqualTo(UPDATED_ADMIN);
        assertThat(testUsers.getLastEmailedAt()).isEqualTo(UPDATED_LAST_EMAILED_AT);
        assertThat(testUsers.getTrustLevel()).isEqualTo(UPDATED_TRUST_LEVEL);
        assertThat(testUsers.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testUsers.getApprovedById()).isEqualTo(UPDATED_APPROVED_BY_ID);
        assertThat(testUsers.getApprovedAt()).isEqualTo(UPDATED_APPROVED_AT);
        assertThat(testUsers.getPreviousVisitAt()).isEqualTo(UPDATED_PREVIOUS_VISIT_AT);
        assertThat(testUsers.getSuspendedAt()).isEqualTo(UPDATED_SUSPENDED_AT);
        assertThat(testUsers.getSuspendedTill()).isEqualTo(UPDATED_SUSPENDED_TILL);
        assertThat(testUsers.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testUsers.getViews()).isEqualTo(UPDATED_VIEWS);
        assertThat(testUsers.getFlagLevel()).isEqualTo(UPDATED_FLAG_LEVEL);
        assertThat(testUsers.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testUsers.isModerator()).isEqualTo(UPDATED_MODERATOR);
        assertThat(testUsers.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUsers.getUploadedAvatarId()).isEqualTo(UPDATED_UPLOADED_AVATAR_ID);
        assertThat(testUsers.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testUsers.getPrimaryGroupId()).isEqualTo(UPDATED_PRIMARY_GROUP_ID);
        assertThat(testUsers.getRegistrationIpAddress()).isEqualTo(UPDATED_REGISTRATION_IP_ADDRESS);
        assertThat(testUsers.isStaged()).isEqualTo(UPDATED_STAGED);
        assertThat(testUsers.getFirstSeenAt()).isEqualTo(UPDATED_FIRST_SEEN_AT);
        assertThat(testUsers.getSilencedTill()).isEqualTo(UPDATED_SILENCED_TILL);
        assertThat(testUsers.getGroupLockedTrustLevel()).isEqualTo(UPDATED_GROUP_LOCKED_TRUST_LEVEL);
        assertThat(testUsers.getManualLockedTrustLevel()).isEqualTo(UPDATED_MANUAL_LOCKED_TRUST_LEVEL);
        assertThat(testUsers.getSecureIdentifier()).isEqualTo(UPDATED_SECURE_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc.perform(put("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Delete the users
        restUsersMockMvc.perform(delete("/api/users/{id}", users.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
