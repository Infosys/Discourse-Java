/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ApiKeys;
import com.infy.repository.ApiKeysRepository;
import com.infy.service.ApiKeysService;
import com.infy.service.dto.ApiKeysDTO;
import com.infy.service.mapper.ApiKeysMapper;

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
 * Integration tests for the {@link ApiKeysResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ApiKeysResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_IPS = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_IPS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIDDEN = false;
    private static final Boolean UPDATED_HIDDEN = true;

    private static final Instant DEFAULT_LAST_USED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_USED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REVOKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVOKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_HASH = "AAAAAAAAAA";
    private static final String UPDATED_KEY_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_TRUNCATED_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TRUNCATED_KEY = "BBBBBBBBBB";

    @Autowired
    private ApiKeysRepository apiKeysRepository;

    @Autowired
    private ApiKeysMapper apiKeysMapper;

    @Autowired
    private ApiKeysService apiKeysService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiKeysMockMvc;

    private ApiKeys apiKeys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKeys createEntity(EntityManager em) {
        ApiKeys apiKeys = new ApiKeys()
            .userId(DEFAULT_USER_ID)
            .allowedIps(DEFAULT_ALLOWED_IPS)
            .hidden(DEFAULT_HIDDEN)
            .lastUsedAt(DEFAULT_LAST_USED_AT)
            .revokedAt(DEFAULT_REVOKED_AT)
            .description(DEFAULT_DESCRIPTION)
            .keyHash(DEFAULT_KEY_HASH)
            .truncatedKey(DEFAULT_TRUNCATED_KEY);
        return apiKeys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKeys createUpdatedEntity(EntityManager em) {
        ApiKeys apiKeys = new ApiKeys()
            .userId(UPDATED_USER_ID)
            .allowedIps(UPDATED_ALLOWED_IPS)
            .hidden(UPDATED_HIDDEN)
            .lastUsedAt(UPDATED_LAST_USED_AT)
            .revokedAt(UPDATED_REVOKED_AT)
            .description(UPDATED_DESCRIPTION)
            .keyHash(UPDATED_KEY_HASH)
            .truncatedKey(UPDATED_TRUNCATED_KEY);
        return apiKeys;
    }

    @BeforeEach
    public void initTest() {
        apiKeys = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiKeys() throws Exception {
        int databaseSizeBeforeCreate = apiKeysRepository.findAll().size();
        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);
        restApiKeysMockMvc.perform(post("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeCreate + 1);
        ApiKeys testApiKeys = apiKeysList.get(apiKeysList.size() - 1);
        assertThat(testApiKeys.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApiKeys.getAllowedIps()).isEqualTo(DEFAULT_ALLOWED_IPS);
        assertThat(testApiKeys.isHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testApiKeys.getLastUsedAt()).isEqualTo(DEFAULT_LAST_USED_AT);
        assertThat(testApiKeys.getRevokedAt()).isEqualTo(DEFAULT_REVOKED_AT);
        assertThat(testApiKeys.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiKeys.getKeyHash()).isEqualTo(DEFAULT_KEY_HASH);
        assertThat(testApiKeys.getTruncatedKey()).isEqualTo(DEFAULT_TRUNCATED_KEY);
    }

    @Test
    @Transactional
    public void createApiKeysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiKeysRepository.findAll().size();

        // Create the ApiKeys with an existing ID
        apiKeys.setId(1L);
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiKeysMockMvc.perform(post("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setHidden(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);


        restApiKeysMockMvc.perform(post("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setKeyHash(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);


        restApiKeysMockMvc.perform(post("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTruncatedKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setTruncatedKey(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);


        restApiKeysMockMvc.perform(post("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        // Get all the apiKeysList
        restApiKeysMockMvc.perform(get("/api/api-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].allowedIps").value(hasItem(DEFAULT_ALLOWED_IPS)))
            .andExpect(jsonPath("$.[*].hidden").value(hasItem(DEFAULT_HIDDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUsedAt").value(hasItem(DEFAULT_LAST_USED_AT.toString())))
            .andExpect(jsonPath("$.[*].revokedAt").value(hasItem(DEFAULT_REVOKED_AT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].keyHash").value(hasItem(DEFAULT_KEY_HASH)))
            .andExpect(jsonPath("$.[*].truncatedKey").value(hasItem(DEFAULT_TRUNCATED_KEY)));
    }

    @Test
    @Transactional
    public void getApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        // Get the apiKeys
        restApiKeysMockMvc.perform(get("/api/api-keys/{id}", apiKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiKeys.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.allowedIps").value(DEFAULT_ALLOWED_IPS))
            .andExpect(jsonPath("$.hidden").value(DEFAULT_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.lastUsedAt").value(DEFAULT_LAST_USED_AT.toString()))
            .andExpect(jsonPath("$.revokedAt").value(DEFAULT_REVOKED_AT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.keyHash").value(DEFAULT_KEY_HASH))
            .andExpect(jsonPath("$.truncatedKey").value(DEFAULT_TRUNCATED_KEY));
    }
    @Test
    @Transactional
    public void getNonExistingApiKeys() throws Exception {
        // Get the apiKeys
        restApiKeysMockMvc.perform(get("/api/api-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();

        // Update the apiKeys
        ApiKeys updatedApiKeys = apiKeysRepository.findById(apiKeys.getId()).get();
        // Disconnect from session so that the updates on updatedApiKeys are not directly saved in db
        em.detach(updatedApiKeys);
        updatedApiKeys
            .userId(UPDATED_USER_ID)
            .allowedIps(UPDATED_ALLOWED_IPS)
            .hidden(UPDATED_HIDDEN)
            .lastUsedAt(UPDATED_LAST_USED_AT)
            .revokedAt(UPDATED_REVOKED_AT)
            .description(UPDATED_DESCRIPTION)
            .keyHash(UPDATED_KEY_HASH)
            .truncatedKey(UPDATED_TRUNCATED_KEY);
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(updatedApiKeys);

        restApiKeysMockMvc.perform(put("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isOk());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
        ApiKeys testApiKeys = apiKeysList.get(apiKeysList.size() - 1);
        assertThat(testApiKeys.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApiKeys.getAllowedIps()).isEqualTo(UPDATED_ALLOWED_IPS);
        assertThat(testApiKeys.isHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testApiKeys.getLastUsedAt()).isEqualTo(UPDATED_LAST_USED_AT);
        assertThat(testApiKeys.getRevokedAt()).isEqualTo(UPDATED_REVOKED_AT);
        assertThat(testApiKeys.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiKeys.getKeyHash()).isEqualTo(UPDATED_KEY_HASH);
        assertThat(testApiKeys.getTruncatedKey()).isEqualTo(UPDATED_TRUNCATED_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiKeysMockMvc.perform(put("/api/api-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        int databaseSizeBeforeDelete = apiKeysRepository.findAll().size();

        // Delete the apiKeys
        restApiKeysMockMvc.perform(delete("/api/api-keys/{id}", apiKeys.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
