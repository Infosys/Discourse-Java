/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserAuthTokenLogs;
import com.infy.repository.UserAuthTokenLogsRepository;
import com.infy.service.UserAuthTokenLogsService;
import com.infy.service.dto.UserAuthTokenLogsDTO;
import com.infy.service.mapper.UserAuthTokenLogsMapper;

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
 * Integration tests for the {@link UserAuthTokenLogsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserAuthTokenLogsResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_AUTH_TOKEN_ID = 1L;
    private static final Long UPDATED_USER_AUTH_TOKEN_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_IP = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_IP = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private UserAuthTokenLogsRepository userAuthTokenLogsRepository;

    @Autowired
    private UserAuthTokenLogsMapper userAuthTokenLogsMapper;

    @Autowired
    private UserAuthTokenLogsService userAuthTokenLogsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAuthTokenLogsMockMvc;

    private UserAuthTokenLogs userAuthTokenLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAuthTokenLogs createEntity(EntityManager em) {
        UserAuthTokenLogs userAuthTokenLogs = new UserAuthTokenLogs()
            .action(DEFAULT_ACTION)
            .userAuthTokenId(DEFAULT_USER_AUTH_TOKEN_ID)
            .userId(DEFAULT_USER_ID)
            .clientIp(DEFAULT_CLIENT_IP)
            .userAgent(DEFAULT_USER_AGENT)
            .authToken(DEFAULT_AUTH_TOKEN)
            .path(DEFAULT_PATH);
        return userAuthTokenLogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAuthTokenLogs createUpdatedEntity(EntityManager em) {
        UserAuthTokenLogs userAuthTokenLogs = new UserAuthTokenLogs()
            .action(UPDATED_ACTION)
            .userAuthTokenId(UPDATED_USER_AUTH_TOKEN_ID)
            .userId(UPDATED_USER_ID)
            .clientIp(UPDATED_CLIENT_IP)
            .userAgent(UPDATED_USER_AGENT)
            .authToken(UPDATED_AUTH_TOKEN)
            .path(UPDATED_PATH);
        return userAuthTokenLogs;
    }

    @BeforeEach
    public void initTest() {
        userAuthTokenLogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAuthTokenLogs() throws Exception {
        int databaseSizeBeforeCreate = userAuthTokenLogsRepository.findAll().size();
        // Create the UserAuthTokenLogs
        UserAuthTokenLogsDTO userAuthTokenLogsDTO = userAuthTokenLogsMapper.toDto(userAuthTokenLogs);
        restUserAuthTokenLogsMockMvc.perform(post("/api/user-auth-token-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokenLogsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAuthTokenLogs in the database
        List<UserAuthTokenLogs> userAuthTokenLogsList = userAuthTokenLogsRepository.findAll();
        assertThat(userAuthTokenLogsList).hasSize(databaseSizeBeforeCreate + 1);
        UserAuthTokenLogs testUserAuthTokenLogs = userAuthTokenLogsList.get(userAuthTokenLogsList.size() - 1);
        assertThat(testUserAuthTokenLogs.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testUserAuthTokenLogs.getUserAuthTokenId()).isEqualTo(DEFAULT_USER_AUTH_TOKEN_ID);
        assertThat(testUserAuthTokenLogs.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserAuthTokenLogs.getClientIp()).isEqualTo(DEFAULT_CLIENT_IP);
        assertThat(testUserAuthTokenLogs.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
        assertThat(testUserAuthTokenLogs.getAuthToken()).isEqualTo(DEFAULT_AUTH_TOKEN);
        assertThat(testUserAuthTokenLogs.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createUserAuthTokenLogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAuthTokenLogsRepository.findAll().size();

        // Create the UserAuthTokenLogs with an existing ID
        userAuthTokenLogs.setId(1L);
        UserAuthTokenLogsDTO userAuthTokenLogsDTO = userAuthTokenLogsMapper.toDto(userAuthTokenLogs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAuthTokenLogsMockMvc.perform(post("/api/user-auth-token-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokenLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthTokenLogs in the database
        List<UserAuthTokenLogs> userAuthTokenLogsList = userAuthTokenLogsRepository.findAll();
        assertThat(userAuthTokenLogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAuthTokenLogsRepository.findAll().size();
        // set the field null
        userAuthTokenLogs.setAction(null);

        // Create the UserAuthTokenLogs, which fails.
        UserAuthTokenLogsDTO userAuthTokenLogsDTO = userAuthTokenLogsMapper.toDto(userAuthTokenLogs);


        restUserAuthTokenLogsMockMvc.perform(post("/api/user-auth-token-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokenLogsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAuthTokenLogs> userAuthTokenLogsList = userAuthTokenLogsRepository.findAll();
        assertThat(userAuthTokenLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAuthTokenLogs() throws Exception {
        // Initialize the database
        userAuthTokenLogsRepository.saveAndFlush(userAuthTokenLogs);

        // Get all the userAuthTokenLogsList
        restUserAuthTokenLogsMockMvc.perform(get("/api/user-auth-token-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAuthTokenLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].userAuthTokenId").value(hasItem(DEFAULT_USER_AUTH_TOKEN_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].clientIp").value(hasItem(DEFAULT_CLIENT_IP)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].authToken").value(hasItem(DEFAULT_AUTH_TOKEN)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }

    @Test
    @Transactional
    public void getUserAuthTokenLogs() throws Exception {
        // Initialize the database
        userAuthTokenLogsRepository.saveAndFlush(userAuthTokenLogs);

        // Get the userAuthTokenLogs
        restUserAuthTokenLogsMockMvc.perform(get("/api/user-auth-token-logs/{id}", userAuthTokenLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAuthTokenLogs.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.userAuthTokenId").value(DEFAULT_USER_AUTH_TOKEN_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.clientIp").value(DEFAULT_CLIENT_IP))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.authToken").value(DEFAULT_AUTH_TOKEN))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }
    @Test
    @Transactional
    public void getNonExistingUserAuthTokenLogs() throws Exception {
        // Get the userAuthTokenLogs
        restUserAuthTokenLogsMockMvc.perform(get("/api/user-auth-token-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAuthTokenLogs() throws Exception {
        // Initialize the database
        userAuthTokenLogsRepository.saveAndFlush(userAuthTokenLogs);

        int databaseSizeBeforeUpdate = userAuthTokenLogsRepository.findAll().size();

        // Update the userAuthTokenLogs
        UserAuthTokenLogs updatedUserAuthTokenLogs = userAuthTokenLogsRepository.findById(userAuthTokenLogs.getId()).get();
        // Disconnect from session so that the updates on updatedUserAuthTokenLogs are not directly saved in db
        em.detach(updatedUserAuthTokenLogs);
        updatedUserAuthTokenLogs
            .action(UPDATED_ACTION)
            .userAuthTokenId(UPDATED_USER_AUTH_TOKEN_ID)
            .userId(UPDATED_USER_ID)
            .clientIp(UPDATED_CLIENT_IP)
            .userAgent(UPDATED_USER_AGENT)
            .authToken(UPDATED_AUTH_TOKEN)
            .path(UPDATED_PATH);
        UserAuthTokenLogsDTO userAuthTokenLogsDTO = userAuthTokenLogsMapper.toDto(updatedUserAuthTokenLogs);

        restUserAuthTokenLogsMockMvc.perform(put("/api/user-auth-token-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokenLogsDTO)))
            .andExpect(status().isOk());

        // Validate the UserAuthTokenLogs in the database
        List<UserAuthTokenLogs> userAuthTokenLogsList = userAuthTokenLogsRepository.findAll();
        assertThat(userAuthTokenLogsList).hasSize(databaseSizeBeforeUpdate);
        UserAuthTokenLogs testUserAuthTokenLogs = userAuthTokenLogsList.get(userAuthTokenLogsList.size() - 1);
        assertThat(testUserAuthTokenLogs.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testUserAuthTokenLogs.getUserAuthTokenId()).isEqualTo(UPDATED_USER_AUTH_TOKEN_ID);
        assertThat(testUserAuthTokenLogs.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserAuthTokenLogs.getClientIp()).isEqualTo(UPDATED_CLIENT_IP);
        assertThat(testUserAuthTokenLogs.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
        assertThat(testUserAuthTokenLogs.getAuthToken()).isEqualTo(UPDATED_AUTH_TOKEN);
        assertThat(testUserAuthTokenLogs.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAuthTokenLogs() throws Exception {
        int databaseSizeBeforeUpdate = userAuthTokenLogsRepository.findAll().size();

        // Create the UserAuthTokenLogs
        UserAuthTokenLogsDTO userAuthTokenLogsDTO = userAuthTokenLogsMapper.toDto(userAuthTokenLogs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAuthTokenLogsMockMvc.perform(put("/api/user-auth-token-logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAuthTokenLogsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAuthTokenLogs in the database
        List<UserAuthTokenLogs> userAuthTokenLogsList = userAuthTokenLogsRepository.findAll();
        assertThat(userAuthTokenLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAuthTokenLogs() throws Exception {
        // Initialize the database
        userAuthTokenLogsRepository.saveAndFlush(userAuthTokenLogs);

        int databaseSizeBeforeDelete = userAuthTokenLogsRepository.findAll().size();

        // Delete the userAuthTokenLogs
        restUserAuthTokenLogsMockMvc.perform(delete("/api/user-auth-token-logs/{id}", userAuthTokenLogs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAuthTokenLogs> userAuthTokenLogsList = userAuthTokenLogsRepository.findAll();
        assertThat(userAuthTokenLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
