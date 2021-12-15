/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.IgnoredUsers;
import com.infy.repository.IgnoredUsersRepository;
import com.infy.service.IgnoredUsersService;
import com.infy.service.dto.IgnoredUsersDTO;
import com.infy.service.mapper.IgnoredUsersMapper;

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
 * Integration tests for the {@link IgnoredUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IgnoredUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IGNORED_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_IGNORED_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUMMARIZED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUMMARIZED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRING_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRING_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IgnoredUsersRepository ignoredUsersRepository;

    @Autowired
    private IgnoredUsersMapper ignoredUsersMapper;

    @Autowired
    private IgnoredUsersService ignoredUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIgnoredUsersMockMvc;

    private IgnoredUsers ignoredUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IgnoredUsers createEntity(EntityManager em) {
        IgnoredUsers ignoredUsers = new IgnoredUsers()
            .userId(DEFAULT_USER_ID)
            .ignoredUserId(DEFAULT_IGNORED_USER_ID)
            .summarizedAt(DEFAULT_SUMMARIZED_AT)
            .expiringAt(DEFAULT_EXPIRING_AT);
        return ignoredUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IgnoredUsers createUpdatedEntity(EntityManager em) {
        IgnoredUsers ignoredUsers = new IgnoredUsers()
            .userId(UPDATED_USER_ID)
            .ignoredUserId(UPDATED_IGNORED_USER_ID)
            .summarizedAt(UPDATED_SUMMARIZED_AT)
            .expiringAt(UPDATED_EXPIRING_AT);
        return ignoredUsers;
    }

    @BeforeEach
    public void initTest() {
        ignoredUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createIgnoredUsers() throws Exception {
        int databaseSizeBeforeCreate = ignoredUsersRepository.findAll().size();
        // Create the IgnoredUsers
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(ignoredUsers);
        restIgnoredUsersMockMvc.perform(post("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the IgnoredUsers in the database
        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeCreate + 1);
        IgnoredUsers testIgnoredUsers = ignoredUsersList.get(ignoredUsersList.size() - 1);
        assertThat(testIgnoredUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testIgnoredUsers.getIgnoredUserId()).isEqualTo(DEFAULT_IGNORED_USER_ID);
        assertThat(testIgnoredUsers.getSummarizedAt()).isEqualTo(DEFAULT_SUMMARIZED_AT);
        assertThat(testIgnoredUsers.getExpiringAt()).isEqualTo(DEFAULT_EXPIRING_AT);
    }

    @Test
    @Transactional
    public void createIgnoredUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ignoredUsersRepository.findAll().size();

        // Create the IgnoredUsers with an existing ID
        ignoredUsers.setId(1L);
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(ignoredUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIgnoredUsersMockMvc.perform(post("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IgnoredUsers in the database
        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ignoredUsersRepository.findAll().size();
        // set the field null
        ignoredUsers.setUserId(null);

        // Create the IgnoredUsers, which fails.
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(ignoredUsers);


        restIgnoredUsersMockMvc.perform(post("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isBadRequest());

        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoredUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ignoredUsersRepository.findAll().size();
        // set the field null
        ignoredUsers.setIgnoredUserId(null);

        // Create the IgnoredUsers, which fails.
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(ignoredUsers);


        restIgnoredUsersMockMvc.perform(post("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isBadRequest());

        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiringAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ignoredUsersRepository.findAll().size();
        // set the field null
        ignoredUsers.setExpiringAt(null);

        // Create the IgnoredUsers, which fails.
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(ignoredUsers);


        restIgnoredUsersMockMvc.perform(post("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isBadRequest());

        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIgnoredUsers() throws Exception {
        // Initialize the database
        ignoredUsersRepository.saveAndFlush(ignoredUsers);

        // Get all the ignoredUsersList
        restIgnoredUsersMockMvc.perform(get("/api/ignored-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ignoredUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].ignoredUserId").value(hasItem(DEFAULT_IGNORED_USER_ID)))
            .andExpect(jsonPath("$.[*].summarizedAt").value(hasItem(DEFAULT_SUMMARIZED_AT.toString())))
            .andExpect(jsonPath("$.[*].expiringAt").value(hasItem(DEFAULT_EXPIRING_AT.toString())));
    }

    @Test
    @Transactional
    public void getIgnoredUsers() throws Exception {
        // Initialize the database
        ignoredUsersRepository.saveAndFlush(ignoredUsers);

        // Get the ignoredUsers
        restIgnoredUsersMockMvc.perform(get("/api/ignored-users/{id}", ignoredUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ignoredUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.ignoredUserId").value(DEFAULT_IGNORED_USER_ID))
            .andExpect(jsonPath("$.summarizedAt").value(DEFAULT_SUMMARIZED_AT.toString()))
            .andExpect(jsonPath("$.expiringAt").value(DEFAULT_EXPIRING_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingIgnoredUsers() throws Exception {
        // Get the ignoredUsers
        restIgnoredUsersMockMvc.perform(get("/api/ignored-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIgnoredUsers() throws Exception {
        // Initialize the database
        ignoredUsersRepository.saveAndFlush(ignoredUsers);

        int databaseSizeBeforeUpdate = ignoredUsersRepository.findAll().size();

        // Update the ignoredUsers
        IgnoredUsers updatedIgnoredUsers = ignoredUsersRepository.findById(ignoredUsers.getId()).get();
        // Disconnect from session so that the updates on updatedIgnoredUsers are not directly saved in db
        em.detach(updatedIgnoredUsers);
        updatedIgnoredUsers
            .userId(UPDATED_USER_ID)
            .ignoredUserId(UPDATED_IGNORED_USER_ID)
            .summarizedAt(UPDATED_SUMMARIZED_AT)
            .expiringAt(UPDATED_EXPIRING_AT);
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(updatedIgnoredUsers);

        restIgnoredUsersMockMvc.perform(put("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isOk());

        // Validate the IgnoredUsers in the database
        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeUpdate);
        IgnoredUsers testIgnoredUsers = ignoredUsersList.get(ignoredUsersList.size() - 1);
        assertThat(testIgnoredUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testIgnoredUsers.getIgnoredUserId()).isEqualTo(UPDATED_IGNORED_USER_ID);
        assertThat(testIgnoredUsers.getSummarizedAt()).isEqualTo(UPDATED_SUMMARIZED_AT);
        assertThat(testIgnoredUsers.getExpiringAt()).isEqualTo(UPDATED_EXPIRING_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingIgnoredUsers() throws Exception {
        int databaseSizeBeforeUpdate = ignoredUsersRepository.findAll().size();

        // Create the IgnoredUsers
        IgnoredUsersDTO ignoredUsersDTO = ignoredUsersMapper.toDto(ignoredUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIgnoredUsersMockMvc.perform(put("/api/ignored-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ignoredUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IgnoredUsers in the database
        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIgnoredUsers() throws Exception {
        // Initialize the database
        ignoredUsersRepository.saveAndFlush(ignoredUsers);

        int databaseSizeBeforeDelete = ignoredUsersRepository.findAll().size();

        // Delete the ignoredUsers
        restIgnoredUsersMockMvc.perform(delete("/api/ignored-users/{id}", ignoredUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IgnoredUsers> ignoredUsersList = ignoredUsersRepository.findAll();
        assertThat(ignoredUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
