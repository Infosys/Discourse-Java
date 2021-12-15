/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserSecurityKeys;
import com.infy.repository.UserSecurityKeysRepository;
import com.infy.service.UserSecurityKeysService;
import com.infy.service.dto.UserSecurityKeysDTO;
import com.infy.service.mapper.UserSecurityKeysMapper;

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
 * Integration tests for the {@link UserSecurityKeysResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserSecurityKeysResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_CREDENTIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_CREDENTIAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_KEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_FACTOR_TYPE = 1;
    private static final Integer UPDATED_FACTOR_TYPE = 2;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_USED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_USED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserSecurityKeysRepository userSecurityKeysRepository;

    @Autowired
    private UserSecurityKeysMapper userSecurityKeysMapper;

    @Autowired
    private UserSecurityKeysService userSecurityKeysService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSecurityKeysMockMvc;

    private UserSecurityKeys userSecurityKeys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSecurityKeys createEntity(EntityManager em) {
        UserSecurityKeys userSecurityKeys = new UserSecurityKeys()
            .userId(DEFAULT_USER_ID)
            .credentialId(DEFAULT_CREDENTIAL_ID)
            .publicKey(DEFAULT_PUBLIC_KEY)
            .factorType(DEFAULT_FACTOR_TYPE)
            .enabled(DEFAULT_ENABLED)
            .name(DEFAULT_NAME)
            .lastUsed(DEFAULT_LAST_USED);
        return userSecurityKeys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSecurityKeys createUpdatedEntity(EntityManager em) {
        UserSecurityKeys userSecurityKeys = new UserSecurityKeys()
            .userId(UPDATED_USER_ID)
            .credentialId(UPDATED_CREDENTIAL_ID)
            .publicKey(UPDATED_PUBLIC_KEY)
            .factorType(UPDATED_FACTOR_TYPE)
            .enabled(UPDATED_ENABLED)
            .name(UPDATED_NAME)
            .lastUsed(UPDATED_LAST_USED);
        return userSecurityKeys;
    }

    @BeforeEach
    public void initTest() {
        userSecurityKeys = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSecurityKeys() throws Exception {
        int databaseSizeBeforeCreate = userSecurityKeysRepository.findAll().size();
        // Create the UserSecurityKeys
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);
        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSecurityKeys in the database
        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeCreate + 1);
        UserSecurityKeys testUserSecurityKeys = userSecurityKeysList.get(userSecurityKeysList.size() - 1);
        assertThat(testUserSecurityKeys.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserSecurityKeys.getCredentialId()).isEqualTo(DEFAULT_CREDENTIAL_ID);
        assertThat(testUserSecurityKeys.getPublicKey()).isEqualTo(DEFAULT_PUBLIC_KEY);
        assertThat(testUserSecurityKeys.getFactorType()).isEqualTo(DEFAULT_FACTOR_TYPE);
        assertThat(testUserSecurityKeys.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testUserSecurityKeys.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserSecurityKeys.getLastUsed()).isEqualTo(DEFAULT_LAST_USED);
    }

    @Test
    @Transactional
    public void createUserSecurityKeysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSecurityKeysRepository.findAll().size();

        // Create the UserSecurityKeys with an existing ID
        userSecurityKeys.setId(1L);
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSecurityKeys in the database
        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecurityKeysRepository.findAll().size();
        // set the field null
        userSecurityKeys.setUserId(null);

        // Create the UserSecurityKeys, which fails.
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);


        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCredentialIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecurityKeysRepository.findAll().size();
        // set the field null
        userSecurityKeys.setCredentialId(null);

        // Create the UserSecurityKeys, which fails.
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);


        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecurityKeysRepository.findAll().size();
        // set the field null
        userSecurityKeys.setPublicKey(null);

        // Create the UserSecurityKeys, which fails.
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);


        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFactorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecurityKeysRepository.findAll().size();
        // set the field null
        userSecurityKeys.setFactorType(null);

        // Create the UserSecurityKeys, which fails.
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);


        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecurityKeysRepository.findAll().size();
        // set the field null
        userSecurityKeys.setEnabled(null);

        // Create the UserSecurityKeys, which fails.
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);


        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecurityKeysRepository.findAll().size();
        // set the field null
        userSecurityKeys.setName(null);

        // Create the UserSecurityKeys, which fails.
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);


        restUserSecurityKeysMockMvc.perform(post("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserSecurityKeys() throws Exception {
        // Initialize the database
        userSecurityKeysRepository.saveAndFlush(userSecurityKeys);

        // Get all the userSecurityKeysList
        restUserSecurityKeysMockMvc.perform(get("/api/user-security-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSecurityKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].credentialId").value(hasItem(DEFAULT_CREDENTIAL_ID)))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(DEFAULT_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].factorType").value(hasItem(DEFAULT_FACTOR_TYPE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastUsed").value(hasItem(DEFAULT_LAST_USED.toString())));
    }

    @Test
    @Transactional
    public void getUserSecurityKeys() throws Exception {
        // Initialize the database
        userSecurityKeysRepository.saveAndFlush(userSecurityKeys);

        // Get the userSecurityKeys
        restUserSecurityKeysMockMvc.perform(get("/api/user-security-keys/{id}", userSecurityKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSecurityKeys.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.credentialId").value(DEFAULT_CREDENTIAL_ID))
            .andExpect(jsonPath("$.publicKey").value(DEFAULT_PUBLIC_KEY))
            .andExpect(jsonPath("$.factorType").value(DEFAULT_FACTOR_TYPE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastUsed").value(DEFAULT_LAST_USED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserSecurityKeys() throws Exception {
        // Get the userSecurityKeys
        restUserSecurityKeysMockMvc.perform(get("/api/user-security-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSecurityKeys() throws Exception {
        // Initialize the database
        userSecurityKeysRepository.saveAndFlush(userSecurityKeys);

        int databaseSizeBeforeUpdate = userSecurityKeysRepository.findAll().size();

        // Update the userSecurityKeys
        UserSecurityKeys updatedUserSecurityKeys = userSecurityKeysRepository.findById(userSecurityKeys.getId()).get();
        // Disconnect from session so that the updates on updatedUserSecurityKeys are not directly saved in db
        em.detach(updatedUserSecurityKeys);
        updatedUserSecurityKeys
            .userId(UPDATED_USER_ID)
            .credentialId(UPDATED_CREDENTIAL_ID)
            .publicKey(UPDATED_PUBLIC_KEY)
            .factorType(UPDATED_FACTOR_TYPE)
            .enabled(UPDATED_ENABLED)
            .name(UPDATED_NAME)
            .lastUsed(UPDATED_LAST_USED);
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(updatedUserSecurityKeys);

        restUserSecurityKeysMockMvc.perform(put("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isOk());

        // Validate the UserSecurityKeys in the database
        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeUpdate);
        UserSecurityKeys testUserSecurityKeys = userSecurityKeysList.get(userSecurityKeysList.size() - 1);
        assertThat(testUserSecurityKeys.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserSecurityKeys.getCredentialId()).isEqualTo(UPDATED_CREDENTIAL_ID);
        assertThat(testUserSecurityKeys.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testUserSecurityKeys.getFactorType()).isEqualTo(UPDATED_FACTOR_TYPE);
        assertThat(testUserSecurityKeys.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testUserSecurityKeys.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserSecurityKeys.getLastUsed()).isEqualTo(UPDATED_LAST_USED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSecurityKeys() throws Exception {
        int databaseSizeBeforeUpdate = userSecurityKeysRepository.findAll().size();

        // Create the UserSecurityKeys
        UserSecurityKeysDTO userSecurityKeysDTO = userSecurityKeysMapper.toDto(userSecurityKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSecurityKeysMockMvc.perform(put("/api/user-security-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecurityKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSecurityKeys in the database
        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSecurityKeys() throws Exception {
        // Initialize the database
        userSecurityKeysRepository.saveAndFlush(userSecurityKeys);

        int databaseSizeBeforeDelete = userSecurityKeysRepository.findAll().size();

        // Delete the userSecurityKeys
        restUserSecurityKeysMockMvc.perform(delete("/api/user-security-keys/{id}", userSecurityKeys.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSecurityKeys> userSecurityKeysList = userSecurityKeysRepository.findAll();
        assertThat(userSecurityKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
