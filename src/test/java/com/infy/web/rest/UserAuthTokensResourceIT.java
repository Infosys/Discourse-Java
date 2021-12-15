/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserAuthTokens;
import com.infy.repository.UserAuthTokensRepository;
import com.infy.service.UserAuthTokensService;
import com.infy.service.dto.UserAuthTokensDTO;
import com.infy.service.mapper.UserAuthTokensMapper;

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
 * Integration tests for the {@link UserAuthTokensResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserAuthTokensResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_AUTH_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PREV_AUTH_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTH_TOKEN_SEEN = false;
    private static final Boolean UPDATED_AUTH_TOKEN_SEEN = true;

    private static final String DEFAULT_CLIENT_IP = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_IP = "BBBBBBBBBB";

    private static final Instant DEFAULT_ROTATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ROTATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SEEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserAuthTokensRepository userAuthTokensRepository;

    @Autowired
    private UserAuthTokensMapper userAuthTokensMapper;

    @Autowired
    private UserAuthTokensService userAuthTokensService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAuthTokensMockMvc;

    private UserAuthTokens userAuthTokens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAuthTokens createEntity(EntityManager em) {
        UserAuthTokens userAuthTokens = new UserAuthTokens()
            .userId(DEFAULT_USER_ID)
            .authToken(DEFAULT_AUTH_TOKEN)
            .prevAuthToken(DEFAULT_PREV_AUTH_TOKEN)
            .userAgent(DEFAULT_USER_AGENT)
            .authTokenSeen(DEFAULT_AUTH_TOKEN_SEEN)
            .clientIp(DEFAULT_CLIENT_IP)
            .rotatedAt(DEFAULT_ROTATED_AT)
            .seenAt(DEFAULT_SEEN_AT);
        return userAuthTokens;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAuthTokens createUpdatedEntity(EntityManager em) {
        UserAuthTokens userAuthTokens = new UserAuthTokens()
            .userId(UPDATED_USER_ID)
            .authToken(UPDATED_AUTH_TOKEN)
            .prevAuthToken(UPDATED_PREV_AUTH_TOKEN)
            .userAgent(UPDATED_USER_AGENT)
            .authTokenSeen(UPDATED_AUTH_TOKEN_SEEN)
            .clientIp(UPDATED_CLIENT_IP)
            .rotatedAt(UPDATED_ROTATED_AT)
            .seenAt(UPDATED_SEEN_AT);
        return userAuthTokens;
    }

    @BeforeEach
    public void initTest() {
        userAuthTokens = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAuthTokens() throws Exception {
        int databaseSizeBeforeCreate = userAuthTokensRepository.findAll().size();
        // Create the UserAuthTokens
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);
        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAuthTokens in the database
        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeCreate + 1);
        UserAuthTokens testUserAuthTokens = userAuthTokensList.get(userAuthTokensList.size() - 1);
        assertThat(testUserAuthTokens.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserAuthTokens.getAuthToken()).isEqualTo(DEFAULT_AUTH_TOKEN);
        assertThat(testUserAuthTokens.getPrevAuthToken()).isEqualTo(DEFAULT_PREV_AUTH_TOKEN);
        assertThat(testUserAuthTokens.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
        assertThat(testUserAuthTokens.isAuthTokenSeen()).isEqualTo(DEFAULT_AUTH_TOKEN_SEEN);
        assertThat(testUserAuthTokens.getClientIp()).isEqualTo(DEFAULT_CLIENT_IP);
        assertThat(testUserAuthTokens.getRotatedAt()).isEqualTo(DEFAULT_ROTATED_AT);
        assertThat(testUserAuthTokens.getSeenAt()).isEqualTo(DEFAULT_SEEN_AT);
    }

    @Test
    @Transactional
    public void createUserAuthTokensWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAuthTokensRepository.findAll().size();

        // Create the UserAuthTokens with an existing ID
        userAuthTokens.setId(1L);
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthTokens in the database
        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthTokensRepository.findAll().size();
        // set the field null
        userAuthTokens.setUserId(null);

        // Create the UserAuthTokens, which fails.
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);


        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthTokensRepository.findAll().size();
        // set the field null
        userAuthTokens.setAuthToken(null);

        // Create the UserAuthTokens, which fails.
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);


        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrevAuthTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthTokensRepository.findAll().size();
        // set the field null
        userAuthTokens.setPrevAuthToken(null);

        // Create the UserAuthTokens, which fails.
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);


        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthTokenSeenIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthTokensRepository.findAll().size();
        // set the field null
        userAuthTokens.setAuthTokenSeen(null);

        // Create the UserAuthTokens, which fails.
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);


        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRotatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthTokensRepository.findAll().size();
        // set the field null
        userAuthTokens.setRotatedAt(null);

        // Create the UserAuthTokens, which fails.
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);


        restUserAuthTokensMockMvc.perform(post("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAuthTokens() throws Exception {
        // Initialize the database
        userAuthTokensRepository.saveAndFlush(userAuthTokens);

        // Get all the userAuthTokensList
        restUserAuthTokensMockMvc.perform(get("/api/user-auth-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAuthTokens.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].authToken").value(hasItem(DEFAULT_AUTH_TOKEN)))
            .andExpect(jsonPath("$.[*].prevAuthToken").value(hasItem(DEFAULT_PREV_AUTH_TOKEN)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].authTokenSeen").value(hasItem(DEFAULT_AUTH_TOKEN_SEEN.booleanValue())))
            .andExpect(jsonPath("$.[*].clientIp").value(hasItem(DEFAULT_CLIENT_IP)))
            .andExpect(jsonPath("$.[*].rotatedAt").value(hasItem(DEFAULT_ROTATED_AT.toString())))
            .andExpect(jsonPath("$.[*].seenAt").value(hasItem(DEFAULT_SEEN_AT.toString())));
    }

    @Test
    @Transactional
    public void getUserAuthTokens() throws Exception {
        // Initialize the database
        userAuthTokensRepository.saveAndFlush(userAuthTokens);

        // Get the userAuthTokens
        restUserAuthTokensMockMvc.perform(get("/api/user-auth-tokens/{id}", userAuthTokens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAuthTokens.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.authToken").value(DEFAULT_AUTH_TOKEN))
            .andExpect(jsonPath("$.prevAuthToken").value(DEFAULT_PREV_AUTH_TOKEN))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.authTokenSeen").value(DEFAULT_AUTH_TOKEN_SEEN.booleanValue()))
            .andExpect(jsonPath("$.clientIp").value(DEFAULT_CLIENT_IP))
            .andExpect(jsonPath("$.rotatedAt").value(DEFAULT_ROTATED_AT.toString()))
            .andExpect(jsonPath("$.seenAt").value(DEFAULT_SEEN_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserAuthTokens() throws Exception {
        // Get the userAuthTokens
        restUserAuthTokensMockMvc.perform(get("/api/user-auth-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAuthTokens() throws Exception {
        // Initialize the database
        userAuthTokensRepository.saveAndFlush(userAuthTokens);

        int databaseSizeBeforeUpdate = userAuthTokensRepository.findAll().size();

        // Update the userAuthTokens
        UserAuthTokens updatedUserAuthTokens = userAuthTokensRepository.findById(userAuthTokens.getId()).get();
        // Disconnect from session so that the updates on updatedUserAuthTokens are not directly saved in db
        em.detach(updatedUserAuthTokens);
        updatedUserAuthTokens
            .userId(UPDATED_USER_ID)
            .authToken(UPDATED_AUTH_TOKEN)
            .prevAuthToken(UPDATED_PREV_AUTH_TOKEN)
            .userAgent(UPDATED_USER_AGENT)
            .authTokenSeen(UPDATED_AUTH_TOKEN_SEEN)
            .clientIp(UPDATED_CLIENT_IP)
            .rotatedAt(UPDATED_ROTATED_AT)
            .seenAt(UPDATED_SEEN_AT);
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(updatedUserAuthTokens);

        restUserAuthTokensMockMvc.perform(put("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isOk());

        // Validate the UserAuthTokens in the database
        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeUpdate);
        UserAuthTokens testUserAuthTokens = userAuthTokensList.get(userAuthTokensList.size() - 1);
        assertThat(testUserAuthTokens.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserAuthTokens.getAuthToken()).isEqualTo(UPDATED_AUTH_TOKEN);
        assertThat(testUserAuthTokens.getPrevAuthToken()).isEqualTo(UPDATED_PREV_AUTH_TOKEN);
        assertThat(testUserAuthTokens.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
        assertThat(testUserAuthTokens.isAuthTokenSeen()).isEqualTo(UPDATED_AUTH_TOKEN_SEEN);
        assertThat(testUserAuthTokens.getClientIp()).isEqualTo(UPDATED_CLIENT_IP);
        assertThat(testUserAuthTokens.getRotatedAt()).isEqualTo(UPDATED_ROTATED_AT);
        assertThat(testUserAuthTokens.getSeenAt()).isEqualTo(UPDATED_SEEN_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAuthTokens() throws Exception {
        int databaseSizeBeforeUpdate = userAuthTokensRepository.findAll().size();

        // Create the UserAuthTokens
        UserAuthTokensDTO userAuthTokensDTO = userAuthTokensMapper.toDto(userAuthTokens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAuthTokensMockMvc.perform(put("/api/user-auth-tokens").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthTokens in the database
        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAuthTokens() throws Exception {
        // Initialize the database
        userAuthTokensRepository.saveAndFlush(userAuthTokens);

        int databaseSizeBeforeDelete = userAuthTokensRepository.findAll().size();

        // Delete the userAuthTokens
        restUserAuthTokensMockMvc.perform(delete("/api/user-auth-tokens/{id}", userAuthTokens.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAuthTokens> userAuthTokensList = userAuthTokensRepository.findAll();
        assertThat(userAuthTokensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
