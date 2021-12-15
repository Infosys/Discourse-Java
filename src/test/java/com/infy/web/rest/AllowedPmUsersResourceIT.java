/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.AllowedPmUsers;
import com.infy.repository.AllowedPmUsersRepository;
import com.infy.service.AllowedPmUsersService;
import com.infy.service.dto.AllowedPmUsersDTO;
import com.infy.service.mapper.AllowedPmUsersMapper;

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
 * Integration tests for the {@link AllowedPmUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AllowedPmUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_PM_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_PM_USER_ID = "BBBBBBBBBB";

    @Autowired
    private AllowedPmUsersRepository allowedPmUsersRepository;

    @Autowired
    private AllowedPmUsersMapper allowedPmUsersMapper;

    @Autowired
    private AllowedPmUsersService allowedPmUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAllowedPmUsersMockMvc;

    private AllowedPmUsers allowedPmUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllowedPmUsers createEntity(EntityManager em) {
        AllowedPmUsers allowedPmUsers = new AllowedPmUsers()
            .userId(DEFAULT_USER_ID)
            .allowedPmUserId(DEFAULT_ALLOWED_PM_USER_ID);
        return allowedPmUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllowedPmUsers createUpdatedEntity(EntityManager em) {
        AllowedPmUsers allowedPmUsers = new AllowedPmUsers()
            .userId(UPDATED_USER_ID)
            .allowedPmUserId(UPDATED_ALLOWED_PM_USER_ID);
        return allowedPmUsers;
    }

    @BeforeEach
    public void initTest() {
        allowedPmUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllowedPmUsers() throws Exception {
        int databaseSizeBeforeCreate = allowedPmUsersRepository.findAll().size();
        // Create the AllowedPmUsers
        AllowedPmUsersDTO allowedPmUsersDTO = allowedPmUsersMapper.toDto(allowedPmUsers);
        restAllowedPmUsersMockMvc.perform(post("/api/allowed-pm-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allowedPmUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the AllowedPmUsers in the database
        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeCreate + 1);
        AllowedPmUsers testAllowedPmUsers = allowedPmUsersList.get(allowedPmUsersList.size() - 1);
        assertThat(testAllowedPmUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAllowedPmUsers.getAllowedPmUserId()).isEqualTo(DEFAULT_ALLOWED_PM_USER_ID);
    }

    @Test
    @Transactional
    public void createAllowedPmUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allowedPmUsersRepository.findAll().size();

        // Create the AllowedPmUsers with an existing ID
        allowedPmUsers.setId(1L);
        AllowedPmUsersDTO allowedPmUsersDTO = allowedPmUsersMapper.toDto(allowedPmUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllowedPmUsersMockMvc.perform(post("/api/allowed-pm-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allowedPmUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AllowedPmUsers in the database
        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = allowedPmUsersRepository.findAll().size();
        // set the field null
        allowedPmUsers.setUserId(null);

        // Create the AllowedPmUsers, which fails.
        AllowedPmUsersDTO allowedPmUsersDTO = allowedPmUsersMapper.toDto(allowedPmUsers);


        restAllowedPmUsersMockMvc.perform(post("/api/allowed-pm-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allowedPmUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowedPmUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = allowedPmUsersRepository.findAll().size();
        // set the field null
        allowedPmUsers.setAllowedPmUserId(null);

        // Create the AllowedPmUsers, which fails.
        AllowedPmUsersDTO allowedPmUsersDTO = allowedPmUsersMapper.toDto(allowedPmUsers);


        restAllowedPmUsersMockMvc.perform(post("/api/allowed-pm-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allowedPmUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAllowedPmUsers() throws Exception {
        // Initialize the database
        allowedPmUsersRepository.saveAndFlush(allowedPmUsers);

        // Get all the allowedPmUsersList
        restAllowedPmUsersMockMvc.perform(get("/api/allowed-pm-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allowedPmUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].allowedPmUserId").value(hasItem(DEFAULT_ALLOWED_PM_USER_ID)));
    }

    @Test
    @Transactional
    public void getAllowedPmUsers() throws Exception {
        // Initialize the database
        allowedPmUsersRepository.saveAndFlush(allowedPmUsers);

        // Get the allowedPmUsers
        restAllowedPmUsersMockMvc.perform(get("/api/allowed-pm-users/{id}", allowedPmUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(allowedPmUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.allowedPmUserId").value(DEFAULT_ALLOWED_PM_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingAllowedPmUsers() throws Exception {
        // Get the allowedPmUsers
        restAllowedPmUsersMockMvc.perform(get("/api/allowed-pm-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllowedPmUsers() throws Exception {
        // Initialize the database
        allowedPmUsersRepository.saveAndFlush(allowedPmUsers);

        int databaseSizeBeforeUpdate = allowedPmUsersRepository.findAll().size();

        // Update the allowedPmUsers
        AllowedPmUsers updatedAllowedPmUsers = allowedPmUsersRepository.findById(allowedPmUsers.getId()).get();
        // Disconnect from session so that the updates on updatedAllowedPmUsers are not directly saved in db
        em.detach(updatedAllowedPmUsers);
        updatedAllowedPmUsers
            .userId(UPDATED_USER_ID)
            .allowedPmUserId(UPDATED_ALLOWED_PM_USER_ID);
        AllowedPmUsersDTO allowedPmUsersDTO = allowedPmUsersMapper.toDto(updatedAllowedPmUsers);

        restAllowedPmUsersMockMvc.perform(put("/api/allowed-pm-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allowedPmUsersDTO)))
            .andExpect(status().isOk());

        // Validate the AllowedPmUsers in the database
        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeUpdate);
        AllowedPmUsers testAllowedPmUsers = allowedPmUsersList.get(allowedPmUsersList.size() - 1);
        assertThat(testAllowedPmUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAllowedPmUsers.getAllowedPmUserId()).isEqualTo(UPDATED_ALLOWED_PM_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAllowedPmUsers() throws Exception {
        int databaseSizeBeforeUpdate = allowedPmUsersRepository.findAll().size();

        // Create the AllowedPmUsers
        AllowedPmUsersDTO allowedPmUsersDTO = allowedPmUsersMapper.toDto(allowedPmUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllowedPmUsersMockMvc.perform(put("/api/allowed-pm-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allowedPmUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AllowedPmUsers in the database
        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllowedPmUsers() throws Exception {
        // Initialize the database
        allowedPmUsersRepository.saveAndFlush(allowedPmUsers);

        int databaseSizeBeforeDelete = allowedPmUsersRepository.findAll().size();

        // Delete the allowedPmUsers
        restAllowedPmUsersMockMvc.perform(delete("/api/allowed-pm-users/{id}", allowedPmUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AllowedPmUsers> allowedPmUsersList = allowedPmUsersRepository.findAll();
        assertThat(allowedPmUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
