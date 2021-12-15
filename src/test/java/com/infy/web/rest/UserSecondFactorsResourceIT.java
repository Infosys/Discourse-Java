/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserSecondFactors;
import com.infy.repository.UserSecondFactorsRepository;
import com.infy.service.UserSecondFactorsService;
import com.infy.service.dto.UserSecondFactorsDTO;
import com.infy.service.mapper.UserSecondFactorsMapper;

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
 * Integration tests for the {@link UserSecondFactorsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserSecondFactorsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_METHOD = 1;
    private static final Integer UPDATED_METHOD = 2;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Instant DEFAULT_LAST_USED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_USED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private UserSecondFactorsRepository userSecondFactorsRepository;

    @Autowired
    private UserSecondFactorsMapper userSecondFactorsMapper;

    @Autowired
    private UserSecondFactorsService userSecondFactorsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSecondFactorsMockMvc;

    private UserSecondFactors userSecondFactors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSecondFactors createEntity(EntityManager em) {
        UserSecondFactors userSecondFactors = new UserSecondFactors()
            .userId(DEFAULT_USER_ID)
            .method(DEFAULT_METHOD)
            .data(DEFAULT_DATA)
            .enabled(DEFAULT_ENABLED)
            .lastUsed(DEFAULT_LAST_USED)
            .name(DEFAULT_NAME);
        return userSecondFactors;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSecondFactors createUpdatedEntity(EntityManager em) {
        UserSecondFactors userSecondFactors = new UserSecondFactors()
            .userId(UPDATED_USER_ID)
            .method(UPDATED_METHOD)
            .data(UPDATED_DATA)
            .enabled(UPDATED_ENABLED)
            .lastUsed(UPDATED_LAST_USED)
            .name(UPDATED_NAME);
        return userSecondFactors;
    }

    @BeforeEach
    public void initTest() {
        userSecondFactors = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSecondFactors() throws Exception {
        int databaseSizeBeforeCreate = userSecondFactorsRepository.findAll().size();
        // Create the UserSecondFactors
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);
        restUserSecondFactorsMockMvc.perform(post("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSecondFactors in the database
        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeCreate + 1);
        UserSecondFactors testUserSecondFactors = userSecondFactorsList.get(userSecondFactorsList.size() - 1);
        assertThat(testUserSecondFactors.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserSecondFactors.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testUserSecondFactors.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testUserSecondFactors.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testUserSecondFactors.getLastUsed()).isEqualTo(DEFAULT_LAST_USED);
        assertThat(testUserSecondFactors.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUserSecondFactorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSecondFactorsRepository.findAll().size();

        // Create the UserSecondFactors with an existing ID
        userSecondFactors.setId(1L);
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSecondFactorsMockMvc.perform(post("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSecondFactors in the database
        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecondFactorsRepository.findAll().size();
        // set the field null
        userSecondFactors.setUserId(null);

        // Create the UserSecondFactors, which fails.
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);


        restUserSecondFactorsMockMvc.perform(post("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecondFactorsRepository.findAll().size();
        // set the field null
        userSecondFactors.setMethod(null);

        // Create the UserSecondFactors, which fails.
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);


        restUserSecondFactorsMockMvc.perform(post("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecondFactorsRepository.findAll().size();
        // set the field null
        userSecondFactors.setData(null);

        // Create the UserSecondFactors, which fails.
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);


        restUserSecondFactorsMockMvc.perform(post("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSecondFactorsRepository.findAll().size();
        // set the field null
        userSecondFactors.setEnabled(null);

        // Create the UserSecondFactors, which fails.
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);


        restUserSecondFactorsMockMvc.perform(post("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isBadRequest());

        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserSecondFactors() throws Exception {
        // Initialize the database
        userSecondFactorsRepository.saveAndFlush(userSecondFactors);

        // Get all the userSecondFactorsList
        restUserSecondFactorsMockMvc.perform(get("/api/user-second-factors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSecondFactors.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUsed").value(hasItem(DEFAULT_LAST_USED.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getUserSecondFactors() throws Exception {
        // Initialize the database
        userSecondFactorsRepository.saveAndFlush(userSecondFactors);

        // Get the userSecondFactors
        restUserSecondFactorsMockMvc.perform(get("/api/user-second-factors/{id}", userSecondFactors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSecondFactors.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.lastUsed").value(DEFAULT_LAST_USED.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingUserSecondFactors() throws Exception {
        // Get the userSecondFactors
        restUserSecondFactorsMockMvc.perform(get("/api/user-second-factors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSecondFactors() throws Exception {
        // Initialize the database
        userSecondFactorsRepository.saveAndFlush(userSecondFactors);

        int databaseSizeBeforeUpdate = userSecondFactorsRepository.findAll().size();

        // Update the userSecondFactors
        UserSecondFactors updatedUserSecondFactors = userSecondFactorsRepository.findById(userSecondFactors.getId()).get();
        // Disconnect from session so that the updates on updatedUserSecondFactors are not directly saved in db
        em.detach(updatedUserSecondFactors);
        updatedUserSecondFactors
            .userId(UPDATED_USER_ID)
            .method(UPDATED_METHOD)
            .data(UPDATED_DATA)
            .enabled(UPDATED_ENABLED)
            .lastUsed(UPDATED_LAST_USED)
            .name(UPDATED_NAME);
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(updatedUserSecondFactors);

        restUserSecondFactorsMockMvc.perform(put("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isOk());

        // Validate the UserSecondFactors in the database
        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeUpdate);
        UserSecondFactors testUserSecondFactors = userSecondFactorsList.get(userSecondFactorsList.size() - 1);
        assertThat(testUserSecondFactors.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserSecondFactors.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testUserSecondFactors.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testUserSecondFactors.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testUserSecondFactors.getLastUsed()).isEqualTo(UPDATED_LAST_USED);
        assertThat(testUserSecondFactors.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSecondFactors() throws Exception {
        int databaseSizeBeforeUpdate = userSecondFactorsRepository.findAll().size();

        // Create the UserSecondFactors
        UserSecondFactorsDTO userSecondFactorsDTO = userSecondFactorsMapper.toDto(userSecondFactors);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSecondFactorsMockMvc.perform(put("/api/user-second-factors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSecondFactorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSecondFactors in the database
        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSecondFactors() throws Exception {
        // Initialize the database
        userSecondFactorsRepository.saveAndFlush(userSecondFactors);

        int databaseSizeBeforeDelete = userSecondFactorsRepository.findAll().size();

        // Delete the userSecondFactors
        restUserSecondFactorsMockMvc.perform(delete("/api/user-second-factors/{id}", userSecondFactors.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSecondFactors> userSecondFactorsList = userSecondFactorsRepository.findAll();
        assertThat(userSecondFactorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
