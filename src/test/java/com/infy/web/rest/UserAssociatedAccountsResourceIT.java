/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserAssociatedAccounts;
import com.infy.repository.UserAssociatedAccountsRepository;
import com.infy.service.UserAssociatedAccountsService;
import com.infy.service.dto.UserAssociatedAccountsDTO;
import com.infy.service.mapper.UserAssociatedAccountsMapper;

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
 * Integration tests for the {@link UserAssociatedAccountsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserAssociatedAccountsResourceIT {

    private static final String DEFAULT_PROVIDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROVIDER_UID = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_UID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_USED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_USED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CREDENTIALS = "AAAAAAAAAA";
    private static final String UPDATED_CREDENTIALS = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    @Autowired
    private UserAssociatedAccountsRepository userAssociatedAccountsRepository;

    @Autowired
    private UserAssociatedAccountsMapper userAssociatedAccountsMapper;

    @Autowired
    private UserAssociatedAccountsService userAssociatedAccountsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAssociatedAccountsMockMvc;

    private UserAssociatedAccounts userAssociatedAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAssociatedAccounts createEntity(EntityManager em) {
        UserAssociatedAccounts userAssociatedAccounts = new UserAssociatedAccounts()
            .providerName(DEFAULT_PROVIDER_NAME)
            .providerUid(DEFAULT_PROVIDER_UID)
            .userId(DEFAULT_USER_ID)
            .lastUsed(DEFAULT_LAST_USED)
            .info(DEFAULT_INFO)
            .credentials(DEFAULT_CREDENTIALS)
            .extra(DEFAULT_EXTRA);
        return userAssociatedAccounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAssociatedAccounts createUpdatedEntity(EntityManager em) {
        UserAssociatedAccounts userAssociatedAccounts = new UserAssociatedAccounts()
            .providerName(UPDATED_PROVIDER_NAME)
            .providerUid(UPDATED_PROVIDER_UID)
            .userId(UPDATED_USER_ID)
            .lastUsed(UPDATED_LAST_USED)
            .info(UPDATED_INFO)
            .credentials(UPDATED_CREDENTIALS)
            .extra(UPDATED_EXTRA);
        return userAssociatedAccounts;
    }

    @BeforeEach
    public void initTest() {
        userAssociatedAccounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAssociatedAccounts() throws Exception {
        int databaseSizeBeforeCreate = userAssociatedAccountsRepository.findAll().size();
        // Create the UserAssociatedAccounts
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);
        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAssociatedAccounts in the database
        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeCreate + 1);
        UserAssociatedAccounts testUserAssociatedAccounts = userAssociatedAccountsList.get(userAssociatedAccountsList.size() - 1);
        assertThat(testUserAssociatedAccounts.getProviderName()).isEqualTo(DEFAULT_PROVIDER_NAME);
        assertThat(testUserAssociatedAccounts.getProviderUid()).isEqualTo(DEFAULT_PROVIDER_UID);
        assertThat(testUserAssociatedAccounts.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserAssociatedAccounts.getLastUsed()).isEqualTo(DEFAULT_LAST_USED);
        assertThat(testUserAssociatedAccounts.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testUserAssociatedAccounts.getCredentials()).isEqualTo(DEFAULT_CREDENTIALS);
        assertThat(testUserAssociatedAccounts.getExtra()).isEqualTo(DEFAULT_EXTRA);
    }

    @Test
    @Transactional
    public void createUserAssociatedAccountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAssociatedAccountsRepository.findAll().size();

        // Create the UserAssociatedAccounts with an existing ID
        userAssociatedAccounts.setId(1L);
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAssociatedAccounts in the database
        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProviderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssociatedAccountsRepository.findAll().size();
        // set the field null
        userAssociatedAccounts.setProviderName(null);

        // Create the UserAssociatedAccounts, which fails.
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);


        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProviderUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssociatedAccountsRepository.findAll().size();
        // set the field null
        userAssociatedAccounts.setProviderUid(null);

        // Create the UserAssociatedAccounts, which fails.
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);


        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUsedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssociatedAccountsRepository.findAll().size();
        // set the field null
        userAssociatedAccounts.setLastUsed(null);

        // Create the UserAssociatedAccounts, which fails.
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);


        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssociatedAccountsRepository.findAll().size();
        // set the field null
        userAssociatedAccounts.setInfo(null);

        // Create the UserAssociatedAccounts, which fails.
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);


        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCredentialsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssociatedAccountsRepository.findAll().size();
        // set the field null
        userAssociatedAccounts.setCredentials(null);

        // Create the UserAssociatedAccounts, which fails.
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);


        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtraIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssociatedAccountsRepository.findAll().size();
        // set the field null
        userAssociatedAccounts.setExtra(null);

        // Create the UserAssociatedAccounts, which fails.
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);


        restUserAssociatedAccountsMockMvc.perform(post("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAssociatedAccounts() throws Exception {
        // Initialize the database
        userAssociatedAccountsRepository.saveAndFlush(userAssociatedAccounts);

        // Get all the userAssociatedAccountsList
        restUserAssociatedAccountsMockMvc.perform(get("/api/user-associated-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAssociatedAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].providerName").value(hasItem(DEFAULT_PROVIDER_NAME)))
            .andExpect(jsonPath("$.[*].providerUid").value(hasItem(DEFAULT_PROVIDER_UID)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].lastUsed").value(hasItem(DEFAULT_LAST_USED.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].credentials").value(hasItem(DEFAULT_CREDENTIALS)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA)));
    }

    @Test
    @Transactional
    public void getUserAssociatedAccounts() throws Exception {
        // Initialize the database
        userAssociatedAccountsRepository.saveAndFlush(userAssociatedAccounts);

        // Get the userAssociatedAccounts
        restUserAssociatedAccountsMockMvc.perform(get("/api/user-associated-accounts/{id}", userAssociatedAccounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAssociatedAccounts.getId().intValue()))
            .andExpect(jsonPath("$.providerName").value(DEFAULT_PROVIDER_NAME))
            .andExpect(jsonPath("$.providerUid").value(DEFAULT_PROVIDER_UID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.lastUsed").value(DEFAULT_LAST_USED.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO))
            .andExpect(jsonPath("$.credentials").value(DEFAULT_CREDENTIALS))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA));
    }
    @Test
    @Transactional
    public void getNonExistingUserAssociatedAccounts() throws Exception {
        // Get the userAssociatedAccounts
        restUserAssociatedAccountsMockMvc.perform(get("/api/user-associated-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAssociatedAccounts() throws Exception {
        // Initialize the database
        userAssociatedAccountsRepository.saveAndFlush(userAssociatedAccounts);

        int databaseSizeBeforeUpdate = userAssociatedAccountsRepository.findAll().size();

        // Update the userAssociatedAccounts
        UserAssociatedAccounts updatedUserAssociatedAccounts = userAssociatedAccountsRepository.findById(userAssociatedAccounts.getId()).get();
        // Disconnect from session so that the updates on updatedUserAssociatedAccounts are not directly saved in db
        em.detach(updatedUserAssociatedAccounts);
        updatedUserAssociatedAccounts
            .providerName(UPDATED_PROVIDER_NAME)
            .providerUid(UPDATED_PROVIDER_UID)
            .userId(UPDATED_USER_ID)
            .lastUsed(UPDATED_LAST_USED)
            .info(UPDATED_INFO)
            .credentials(UPDATED_CREDENTIALS)
            .extra(UPDATED_EXTRA);
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(updatedUserAssociatedAccounts);

        restUserAssociatedAccountsMockMvc.perform(put("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isOk());

        // Validate the UserAssociatedAccounts in the database
        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeUpdate);
        UserAssociatedAccounts testUserAssociatedAccounts = userAssociatedAccountsList.get(userAssociatedAccountsList.size() - 1);
        assertThat(testUserAssociatedAccounts.getProviderName()).isEqualTo(UPDATED_PROVIDER_NAME);
        assertThat(testUserAssociatedAccounts.getProviderUid()).isEqualTo(UPDATED_PROVIDER_UID);
        assertThat(testUserAssociatedAccounts.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserAssociatedAccounts.getLastUsed()).isEqualTo(UPDATED_LAST_USED);
        assertThat(testUserAssociatedAccounts.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testUserAssociatedAccounts.getCredentials()).isEqualTo(UPDATED_CREDENTIALS);
        assertThat(testUserAssociatedAccounts.getExtra()).isEqualTo(UPDATED_EXTRA);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAssociatedAccounts() throws Exception {
        int databaseSizeBeforeUpdate = userAssociatedAccountsRepository.findAll().size();

        // Create the UserAssociatedAccounts
        UserAssociatedAccountsDTO userAssociatedAccountsDTO = userAssociatedAccountsMapper.toDto(userAssociatedAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAssociatedAccountsMockMvc.perform(put("/api/user-associated-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAssociatedAccountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAssociatedAccounts in the database
        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAssociatedAccounts() throws Exception {
        // Initialize the database
        userAssociatedAccountsRepository.saveAndFlush(userAssociatedAccounts);

        int databaseSizeBeforeDelete = userAssociatedAccountsRepository.findAll().size();

        // Delete the userAssociatedAccounts
        restUserAssociatedAccountsMockMvc.perform(delete("/api/user-associated-accounts/{id}", userAssociatedAccounts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAssociatedAccounts> userAssociatedAccountsList = userAssociatedAccountsRepository.findAll();
        assertThat(userAssociatedAccountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
