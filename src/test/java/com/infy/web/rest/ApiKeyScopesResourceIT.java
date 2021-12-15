/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ApiKeyScopes;
import com.infy.repository.ApiKeyScopesRepository;
import com.infy.service.ApiKeyScopesService;
import com.infy.service.dto.ApiKeyScopesDTO;
import com.infy.service.mapper.ApiKeyScopesMapper;

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
 * Integration tests for the {@link ApiKeyScopesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ApiKeyScopesResourceIT {

    private static final Long DEFAULT_API_KEY_ID = 1L;
    private static final Long UPDATED_API_KEY_ID = 2L;

    private static final String DEFAULT_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_PARAMETERS = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_PARAMETERS = "BBBBBBBBBB";

    @Autowired
    private ApiKeyScopesRepository apiKeyScopesRepository;

    @Autowired
    private ApiKeyScopesMapper apiKeyScopesMapper;

    @Autowired
    private ApiKeyScopesService apiKeyScopesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiKeyScopesMockMvc;

    private ApiKeyScopes apiKeyScopes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKeyScopes createEntity(EntityManager em) {
        ApiKeyScopes apiKeyScopes = new ApiKeyScopes()
            .apiKeyId(DEFAULT_API_KEY_ID)
            .resource(DEFAULT_RESOURCE)
            .action(DEFAULT_ACTION)
            .allowedParameters(DEFAULT_ALLOWED_PARAMETERS);
        return apiKeyScopes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKeyScopes createUpdatedEntity(EntityManager em) {
        ApiKeyScopes apiKeyScopes = new ApiKeyScopes()
            .apiKeyId(UPDATED_API_KEY_ID)
            .resource(UPDATED_RESOURCE)
            .action(UPDATED_ACTION)
            .allowedParameters(UPDATED_ALLOWED_PARAMETERS);
        return apiKeyScopes;
    }

    @BeforeEach
    public void initTest() {
        apiKeyScopes = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiKeyScopes() throws Exception {
        int databaseSizeBeforeCreate = apiKeyScopesRepository.findAll().size();
        // Create the ApiKeyScopes
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(apiKeyScopes);
        restApiKeyScopesMockMvc.perform(post("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiKeyScopes in the database
        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeCreate + 1);
        ApiKeyScopes testApiKeyScopes = apiKeyScopesList.get(apiKeyScopesList.size() - 1);
        assertThat(testApiKeyScopes.getApiKeyId()).isEqualTo(DEFAULT_API_KEY_ID);
        assertThat(testApiKeyScopes.getResource()).isEqualTo(DEFAULT_RESOURCE);
        assertThat(testApiKeyScopes.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testApiKeyScopes.getAllowedParameters()).isEqualTo(DEFAULT_ALLOWED_PARAMETERS);
    }

    @Test
    @Transactional
    public void createApiKeyScopesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiKeyScopesRepository.findAll().size();

        // Create the ApiKeyScopes with an existing ID
        apiKeyScopes.setId(1L);
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(apiKeyScopes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiKeyScopesMockMvc.perform(post("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKeyScopes in the database
        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkApiKeyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeyScopesRepository.findAll().size();
        // set the field null
        apiKeyScopes.setApiKeyId(null);

        // Create the ApiKeyScopes, which fails.
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(apiKeyScopes);


        restApiKeyScopesMockMvc.perform(post("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeyScopesRepository.findAll().size();
        // set the field null
        apiKeyScopes.setResource(null);

        // Create the ApiKeyScopes, which fails.
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(apiKeyScopes);


        restApiKeyScopesMockMvc.perform(post("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeyScopesRepository.findAll().size();
        // set the field null
        apiKeyScopes.setAction(null);

        // Create the ApiKeyScopes, which fails.
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(apiKeyScopes);


        restApiKeyScopesMockMvc.perform(post("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiKeyScopes() throws Exception {
        // Initialize the database
        apiKeyScopesRepository.saveAndFlush(apiKeyScopes);

        // Get all the apiKeyScopesList
        restApiKeyScopesMockMvc.perform(get("/api/api-key-scopes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiKeyScopes.getId().intValue())))
            .andExpect(jsonPath("$.[*].apiKeyId").value(hasItem(DEFAULT_API_KEY_ID.intValue())))
            .andExpect(jsonPath("$.[*].resource").value(hasItem(DEFAULT_RESOURCE)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].allowedParameters").value(hasItem(DEFAULT_ALLOWED_PARAMETERS)));
    }

    @Test
    @Transactional
    public void getApiKeyScopes() throws Exception {
        // Initialize the database
        apiKeyScopesRepository.saveAndFlush(apiKeyScopes);

        // Get the apiKeyScopes
        restApiKeyScopesMockMvc.perform(get("/api/api-key-scopes/{id}", apiKeyScopes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiKeyScopes.getId().intValue()))
            .andExpect(jsonPath("$.apiKeyId").value(DEFAULT_API_KEY_ID.intValue()))
            .andExpect(jsonPath("$.resource").value(DEFAULT_RESOURCE))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.allowedParameters").value(DEFAULT_ALLOWED_PARAMETERS));
    }
    @Test
    @Transactional
    public void getNonExistingApiKeyScopes() throws Exception {
        // Get the apiKeyScopes
        restApiKeyScopesMockMvc.perform(get("/api/api-key-scopes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiKeyScopes() throws Exception {
        // Initialize the database
        apiKeyScopesRepository.saveAndFlush(apiKeyScopes);

        int databaseSizeBeforeUpdate = apiKeyScopesRepository.findAll().size();

        // Update the apiKeyScopes
        ApiKeyScopes updatedApiKeyScopes = apiKeyScopesRepository.findById(apiKeyScopes.getId()).get();
        // Disconnect from session so that the updates on updatedApiKeyScopes are not directly saved in db
        em.detach(updatedApiKeyScopes);
        updatedApiKeyScopes
            .apiKeyId(UPDATED_API_KEY_ID)
            .resource(UPDATED_RESOURCE)
            .action(UPDATED_ACTION)
            .allowedParameters(UPDATED_ALLOWED_PARAMETERS);
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(updatedApiKeyScopes);

        restApiKeyScopesMockMvc.perform(put("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isOk());

        // Validate the ApiKeyScopes in the database
        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeUpdate);
        ApiKeyScopes testApiKeyScopes = apiKeyScopesList.get(apiKeyScopesList.size() - 1);
        assertThat(testApiKeyScopes.getApiKeyId()).isEqualTo(UPDATED_API_KEY_ID);
        assertThat(testApiKeyScopes.getResource()).isEqualTo(UPDATED_RESOURCE);
        assertThat(testApiKeyScopes.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testApiKeyScopes.getAllowedParameters()).isEqualTo(UPDATED_ALLOWED_PARAMETERS);
    }

    @Test
    @Transactional
    public void updateNonExistingApiKeyScopes() throws Exception {
        int databaseSizeBeforeUpdate = apiKeyScopesRepository.findAll().size();

        // Create the ApiKeyScopes
        ApiKeyScopesDTO apiKeyScopesDTO = apiKeyScopesMapper.toDto(apiKeyScopes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiKeyScopesMockMvc.perform(put("/api/api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKeyScopes in the database
        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiKeyScopes() throws Exception {
        // Initialize the database
        apiKeyScopesRepository.saveAndFlush(apiKeyScopes);

        int databaseSizeBeforeDelete = apiKeyScopesRepository.findAll().size();

        // Delete the apiKeyScopes
        restApiKeyScopesMockMvc.perform(delete("/api/api-key-scopes/{id}", apiKeyScopes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiKeyScopes> apiKeyScopesList = apiKeyScopesRepository.findAll();
        assertThat(apiKeyScopesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
