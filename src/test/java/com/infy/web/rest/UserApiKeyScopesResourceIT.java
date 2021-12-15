/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserApiKeyScopes;
import com.infy.repository.UserApiKeyScopesRepository;
import com.infy.service.UserApiKeyScopesService;
import com.infy.service.dto.UserApiKeyScopesDTO;
import com.infy.service.mapper.UserApiKeyScopesMapper;

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
 * Integration tests for the {@link UserApiKeyScopesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserApiKeyScopesResourceIT {

    private static final Long DEFAULT_USER_API_KEY_ID = 1L;
    private static final Long UPDATED_USER_API_KEY_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_PARAMETERS = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_PARAMETERS = "BBBBBBBBBB";

    @Autowired
    private UserApiKeyScopesRepository userApiKeyScopesRepository;

    @Autowired
    private UserApiKeyScopesMapper userApiKeyScopesMapper;

    @Autowired
    private UserApiKeyScopesService userApiKeyScopesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserApiKeyScopesMockMvc;

    private UserApiKeyScopes userApiKeyScopes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApiKeyScopes createEntity(EntityManager em) {
        UserApiKeyScopes userApiKeyScopes = new UserApiKeyScopes()
            .userApiKeyId(DEFAULT_USER_API_KEY_ID)
            .name(DEFAULT_NAME)
            .allowedParameters(DEFAULT_ALLOWED_PARAMETERS);
        return userApiKeyScopes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApiKeyScopes createUpdatedEntity(EntityManager em) {
        UserApiKeyScopes userApiKeyScopes = new UserApiKeyScopes()
            .userApiKeyId(UPDATED_USER_API_KEY_ID)
            .name(UPDATED_NAME)
            .allowedParameters(UPDATED_ALLOWED_PARAMETERS);
        return userApiKeyScopes;
    }

    @BeforeEach
    public void initTest() {
        userApiKeyScopes = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApiKeyScopes() throws Exception {
        int databaseSizeBeforeCreate = userApiKeyScopesRepository.findAll().size();
        // Create the UserApiKeyScopes
        UserApiKeyScopesDTO userApiKeyScopesDTO = userApiKeyScopesMapper.toDto(userApiKeyScopes);
        restUserApiKeyScopesMockMvc.perform(post("/api/user-api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeyScopesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserApiKeyScopes in the database
        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeCreate + 1);
        UserApiKeyScopes testUserApiKeyScopes = userApiKeyScopesList.get(userApiKeyScopesList.size() - 1);
        assertThat(testUserApiKeyScopes.getUserApiKeyId()).isEqualTo(DEFAULT_USER_API_KEY_ID);
        assertThat(testUserApiKeyScopes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserApiKeyScopes.getAllowedParameters()).isEqualTo(DEFAULT_ALLOWED_PARAMETERS);
    }

    @Test
    @Transactional
    public void createUserApiKeyScopesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userApiKeyScopesRepository.findAll().size();

        // Create the UserApiKeyScopes with an existing ID
        userApiKeyScopes.setId(1L);
        UserApiKeyScopesDTO userApiKeyScopesDTO = userApiKeyScopesMapper.toDto(userApiKeyScopes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserApiKeyScopesMockMvc.perform(post("/api/user-api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApiKeyScopes in the database
        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserApiKeyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeyScopesRepository.findAll().size();
        // set the field null
        userApiKeyScopes.setUserApiKeyId(null);

        // Create the UserApiKeyScopes, which fails.
        UserApiKeyScopesDTO userApiKeyScopesDTO = userApiKeyScopesMapper.toDto(userApiKeyScopes);


        restUserApiKeyScopesMockMvc.perform(post("/api/user-api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userApiKeyScopesRepository.findAll().size();
        // set the field null
        userApiKeyScopes.setName(null);

        // Create the UserApiKeyScopes, which fails.
        UserApiKeyScopesDTO userApiKeyScopesDTO = userApiKeyScopesMapper.toDto(userApiKeyScopes);


        restUserApiKeyScopesMockMvc.perform(post("/api/user-api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserApiKeyScopes() throws Exception {
        // Initialize the database
        userApiKeyScopesRepository.saveAndFlush(userApiKeyScopes);

        // Get all the userApiKeyScopesList
        restUserApiKeyScopesMockMvc.perform(get("/api/user-api-key-scopes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApiKeyScopes.getId().intValue())))
            .andExpect(jsonPath("$.[*].userApiKeyId").value(hasItem(DEFAULT_USER_API_KEY_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].allowedParameters").value(hasItem(DEFAULT_ALLOWED_PARAMETERS)));
    }

    @Test
    @Transactional
    public void getUserApiKeyScopes() throws Exception {
        // Initialize the database
        userApiKeyScopesRepository.saveAndFlush(userApiKeyScopes);

        // Get the userApiKeyScopes
        restUserApiKeyScopesMockMvc.perform(get("/api/user-api-key-scopes/{id}", userApiKeyScopes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userApiKeyScopes.getId().intValue()))
            .andExpect(jsonPath("$.userApiKeyId").value(DEFAULT_USER_API_KEY_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.allowedParameters").value(DEFAULT_ALLOWED_PARAMETERS));
    }
    @Test
    @Transactional
    public void getNonExistingUserApiKeyScopes() throws Exception {
        // Get the userApiKeyScopes
        restUserApiKeyScopesMockMvc.perform(get("/api/user-api-key-scopes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApiKeyScopes() throws Exception {
        // Initialize the database
        userApiKeyScopesRepository.saveAndFlush(userApiKeyScopes);

        int databaseSizeBeforeUpdate = userApiKeyScopesRepository.findAll().size();

        // Update the userApiKeyScopes
        UserApiKeyScopes updatedUserApiKeyScopes = userApiKeyScopesRepository.findById(userApiKeyScopes.getId()).get();
        // Disconnect from session so that the updates on updatedUserApiKeyScopes are not directly saved in db
        em.detach(updatedUserApiKeyScopes);
        updatedUserApiKeyScopes
            .userApiKeyId(UPDATED_USER_API_KEY_ID)
            .name(UPDATED_NAME)
            .allowedParameters(UPDATED_ALLOWED_PARAMETERS);
        UserApiKeyScopesDTO userApiKeyScopesDTO = userApiKeyScopesMapper.toDto(updatedUserApiKeyScopes);

        restUserApiKeyScopesMockMvc.perform(put("/api/user-api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeyScopesDTO)))
            .andExpect(status().isOk());

        // Validate the UserApiKeyScopes in the database
        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeUpdate);
        UserApiKeyScopes testUserApiKeyScopes = userApiKeyScopesList.get(userApiKeyScopesList.size() - 1);
        assertThat(testUserApiKeyScopes.getUserApiKeyId()).isEqualTo(UPDATED_USER_API_KEY_ID);
        assertThat(testUserApiKeyScopes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserApiKeyScopes.getAllowedParameters()).isEqualTo(UPDATED_ALLOWED_PARAMETERS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApiKeyScopes() throws Exception {
        int databaseSizeBeforeUpdate = userApiKeyScopesRepository.findAll().size();

        // Create the UserApiKeyScopes
        UserApiKeyScopesDTO userApiKeyScopesDTO = userApiKeyScopesMapper.toDto(userApiKeyScopes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserApiKeyScopesMockMvc.perform(put("/api/user-api-key-scopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApiKeyScopesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApiKeyScopes in the database
        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApiKeyScopes() throws Exception {
        // Initialize the database
        userApiKeyScopesRepository.saveAndFlush(userApiKeyScopes);

        int databaseSizeBeforeDelete = userApiKeyScopesRepository.findAll().size();

        // Delete the userApiKeyScopes
        restUserApiKeyScopesMockMvc.perform(delete("/api/user-api-key-scopes/{id}", userApiKeyScopes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserApiKeyScopes> userApiKeyScopesList = userApiKeyScopesRepository.findAll();
        assertThat(userApiKeyScopesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
