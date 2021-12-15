/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserApiKeys;
import com.infy.repository.UserApiKeysRepository;
import com.infy.service.UserApiKeysService;
import com.infy.service.dto.UserApiKeysDTO;
import com.infy.service.mapper.UserApiKeysMapper;

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
 * Integration tests for the {@link UserApiKeysResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserApiKeysResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PUSH_URL = "AAAAAAAAAA";
    private static final String UPDATED_PUSH_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_REVOKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVOKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SCOPES = "AAAAAAAAAA";
    private static final String UPDATED_SCOPES = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_USED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_USED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KEY_HASH = "AAAAAAAAAA";
    private static final String UPDATED_KEY_HASH = "BBBBBBBBBB";

    @Autowired
    private UserApiKeysRepository userApiKeysRepository;

    @Autowired
    private UserApiKeysMapper userApiKeysMapper;

    @Autowired
    private UserApiKeysService userApiKeysService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserApiKeysMockMvc;

    private UserApiKeys userApiKeys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApiKeys createEntity(EntityManager em) {
        UserApiKeys userApiKeys = new UserApiKeys()
            .userId(DEFAULT_USER_ID)
            .clientId(DEFAULT_CLIENT_ID)
            .applicationName(DEFAULT_APPLICATION_NAME)
            .pushUrl(DEFAULT_PUSH_URL)
            .revokedAt(DEFAULT_REVOKED_AT)
            .scopes(DEFAULT_SCOPES)
            .lastUsedAt(DEFAULT_LAST_USED_AT)
            .keyHash(DEFAULT_KEY_HASH);
        return userApiKeys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApiKeys createUpdatedEntity(EntityManager em) {
        UserApiKeys userApiKeys = new UserApiKeys()
            .userId(UPDATED_USER_ID)
            .clientId(UPDATED_CLIENT_ID)
            .applicationName(UPDATED_APPLICATION_NAME)
            .pushUrl(UPDATED_PUSH_URL)
            .revokedAt(UPDATED_REVOKED_AT)
            .scopes(UPDATED_SCOPES)
            .lastUsedAt(UPDATED_LAST_USED_AT)
            .keyHash(UPDATED_KEY_HASH);
        return userApiKeys;
    }

    @BeforeEach
    public void initTest() {
        userApiKeys = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApiKeys() throws Exception {
        int databaseSizeBeforeCreate = userApiKeysRepository.findAll().size();
        // Create the UserApiKeys
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);
        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isCreated());

        // Validate the UserApiKeys in the database
        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeCreate + 1);
        UserApiKeys testUserApiKeys = userApiKeysList.get(userApiKeysList.size() - 1);
        assertThat(testUserApiKeys.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserApiKeys.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testUserApiKeys.getApplicationName()).isEqualTo(DEFAULT_APPLICATION_NAME);
        assertThat(testUserApiKeys.getPushUrl()).isEqualTo(DEFAULT_PUSH_URL);
        assertThat(testUserApiKeys.getRevokedAt()).isEqualTo(DEFAULT_REVOKED_AT);
        assertThat(testUserApiKeys.getScopes()).isEqualTo(DEFAULT_SCOPES);
        assertThat(testUserApiKeys.getLastUsedAt()).isEqualTo(DEFAULT_LAST_USED_AT);
        assertThat(testUserApiKeys.getKeyHash()).isEqualTo(DEFAULT_KEY_HASH);
    }

    @Test
    @Transactional
    public void createUserApiKeysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userApiKeysRepository.findAll().size();

        // Create the UserApiKeys with an existing ID
        userApiKeys.setId(1L);
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApiKeys in the database
        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeysRepository.findAll().size();
        // set the field null
        userApiKeys.setUserId(null);

        // Create the UserApiKeys, which fails.
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);


        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeysRepository.findAll().size();
        // set the field null
        userApiKeys.setClientId(null);

        // Create the UserApiKeys, which fails.
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);


        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeysRepository.findAll().size();
        // set the field null
        userApiKeys.setApplicationName(null);

        // Create the UserApiKeys, which fails.
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);


        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUsedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeysRepository.findAll().size();
        // set the field null
        userApiKeys.setLastUsedAt(null);

        // Create the UserApiKeys, which fails.
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);


        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeysRepository.findAll().size();
        // set the field null
        userApiKeys.setKeyHash(null);

        // Create the UserApiKeys, which fails.
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);


        restUserApiKeysMockMvc.perform(post("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserApiKeys() throws Exception {
        // Initialize the database
        userApiKeysRepository.saveAndFlush(userApiKeys);

        // Get all the userApiKeysList
        restUserApiKeysMockMvc.perform(get("/api/user-api-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApiKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].applicationName").value(hasItem(DEFAULT_APPLICATION_NAME)))
            .andExpect(jsonPath("$.[*].pushUrl").value(hasItem(DEFAULT_PUSH_URL)))
            .andExpect(jsonPath("$.[*].revokedAt").value(hasItem(DEFAULT_REVOKED_AT.toString())))
            .andExpect(jsonPath("$.[*].scopes").value(hasItem(DEFAULT_SCOPES)))
            .andExpect(jsonPath("$.[*].lastUsedAt").value(hasItem(DEFAULT_LAST_USED_AT.toString())))
            .andExpect(jsonPath("$.[*].keyHash").value(hasItem(DEFAULT_KEY_HASH)));
    }

    @Test
    @Transactional
    public void getUserApiKeys() throws Exception {
        // Initialize the database
        userApiKeysRepository.saveAndFlush(userApiKeys);

        // Get the userApiKeys
        restUserApiKeysMockMvc.perform(get("/api/user-api-keys/{id}", userApiKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userApiKeys.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.applicationName").value(DEFAULT_APPLICATION_NAME))
            .andExpect(jsonPath("$.pushUrl").value(DEFAULT_PUSH_URL))
            .andExpect(jsonPath("$.revokedAt").value(DEFAULT_REVOKED_AT.toString()))
            .andExpect(jsonPath("$.scopes").value(DEFAULT_SCOPES))
            .andExpect(jsonPath("$.lastUsedAt").value(DEFAULT_LAST_USED_AT.toString()))
            .andExpect(jsonPath("$.keyHash").value(DEFAULT_KEY_HASH));
    }
    @Test
    @Transactional
    public void getNonExistingUserApiKeys() throws Exception {
        // Get the userApiKeys
        restUserApiKeysMockMvc.perform(get("/api/user-api-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApiKeys() throws Exception {
        // Initialize the database
        userApiKeysRepository.saveAndFlush(userApiKeys);

        int databaseSizeBeforeUpdate = userApiKeysRepository.findAll().size();

        // Update the userApiKeys
        UserApiKeys updatedUserApiKeys = userApiKeysRepository.findById(userApiKeys.getId()).get();
        // Disconnect from session so that the updates on updatedUserApiKeys are not directly saved in db
        em.detach(updatedUserApiKeys);
        updatedUserApiKeys
            .userId(UPDATED_USER_ID)
            .clientId(UPDATED_CLIENT_ID)
            .applicationName(UPDATED_APPLICATION_NAME)
            .pushUrl(UPDATED_PUSH_URL)
            .revokedAt(UPDATED_REVOKED_AT)
            .scopes(UPDATED_SCOPES)
            .lastUsedAt(UPDATED_LAST_USED_AT)
            .keyHash(UPDATED_KEY_HASH);
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(updatedUserApiKeys);

        restUserApiKeysMockMvc.perform(put("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isOk());

        // Validate the UserApiKeys in the database
        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeUpdate);
        UserApiKeys testUserApiKeys = userApiKeysList.get(userApiKeysList.size() - 1);
        assertThat(testUserApiKeys.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserApiKeys.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testUserApiKeys.getApplicationName()).isEqualTo(UPDATED_APPLICATION_NAME);
        assertThat(testUserApiKeys.getPushUrl()).isEqualTo(UPDATED_PUSH_URL);
        assertThat(testUserApiKeys.getRevokedAt()).isEqualTo(UPDATED_REVOKED_AT);
        assertThat(testUserApiKeys.getScopes()).isEqualTo(UPDATED_SCOPES);
        assertThat(testUserApiKeys.getLastUsedAt()).isEqualTo(UPDATED_LAST_USED_AT);
        assertThat(testUserApiKeys.getKeyHash()).isEqualTo(UPDATED_KEY_HASH);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = userApiKeysRepository.findAll().size();

        // Create the UserApiKeys
        UserApiKeysDTO userApiKeysDTO = userApiKeysMapper.toDto(userApiKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserApiKeysMockMvc.perform(put("/api/user-api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApiKeys in the database
        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApiKeys() throws Exception {
        // Initialize the database
        userApiKeysRepository.saveAndFlush(userApiKeys);

        int databaseSizeBeforeDelete = userApiKeysRepository.findAll().size();

        // Delete the userApiKeys
        restUserApiKeysMockMvc.perform(delete("/api/user-api-keys/{id}", userApiKeys.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserApiKeys> userApiKeysList = userApiKeysRepository.findAll();
        assertThat(userApiKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
