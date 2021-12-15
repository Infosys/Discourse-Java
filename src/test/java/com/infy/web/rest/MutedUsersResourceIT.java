/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.MutedUsers;
import com.infy.repository.MutedUsersRepository;
import com.infy.service.MutedUsersService;
import com.infy.service.dto.MutedUsersDTO;
import com.infy.service.mapper.MutedUsersMapper;

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
 * Integration tests for the {@link MutedUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class MutedUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MUTED_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MUTED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private MutedUsersRepository mutedUsersRepository;

    @Autowired
    private MutedUsersMapper mutedUsersMapper;

    @Autowired
    private MutedUsersService mutedUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMutedUsersMockMvc;

    private MutedUsers mutedUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MutedUsers createEntity(EntityManager em) {
        MutedUsers mutedUsers = new MutedUsers()
            .userId(DEFAULT_USER_ID)
            .mutedUserId(DEFAULT_MUTED_USER_ID);
        return mutedUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MutedUsers createUpdatedEntity(EntityManager em) {
        MutedUsers mutedUsers = new MutedUsers()
            .userId(UPDATED_USER_ID)
            .mutedUserId(UPDATED_MUTED_USER_ID);
        return mutedUsers;
    }

    @BeforeEach
    public void initTest() {
        mutedUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createMutedUsers() throws Exception {
        int databaseSizeBeforeCreate = mutedUsersRepository.findAll().size();
        // Create the MutedUsers
        MutedUsersDTO mutedUsersDTO = mutedUsersMapper.toDto(mutedUsers);
        restMutedUsersMockMvc.perform(post("/api/muted-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mutedUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the MutedUsers in the database
        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeCreate + 1);
        MutedUsers testMutedUsers = mutedUsersList.get(mutedUsersList.size() - 1);
        assertThat(testMutedUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMutedUsers.getMutedUserId()).isEqualTo(DEFAULT_MUTED_USER_ID);
    }

    @Test
    @Transactional
    public void createMutedUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mutedUsersRepository.findAll().size();

        // Create the MutedUsers with an existing ID
        mutedUsers.setId(1L);
        MutedUsersDTO mutedUsersDTO = mutedUsersMapper.toDto(mutedUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMutedUsersMockMvc.perform(post("/api/muted-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mutedUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MutedUsers in the database
        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutedUsersRepository.findAll().size();
        // set the field null
        mutedUsers.setUserId(null);

        // Create the MutedUsers, which fails.
        MutedUsersDTO mutedUsersDTO = mutedUsersMapper.toDto(mutedUsers);


        restMutedUsersMockMvc.perform(post("/api/muted-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mutedUsersDTO)))
            .andExpect(status().isBadRequest());

        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMutedUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutedUsersRepository.findAll().size();
        // set the field null
        mutedUsers.setMutedUserId(null);

        // Create the MutedUsers, which fails.
        MutedUsersDTO mutedUsersDTO = mutedUsersMapper.toDto(mutedUsers);


        restMutedUsersMockMvc.perform(post("/api/muted-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mutedUsersDTO)))
            .andExpect(status().isBadRequest());

        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMutedUsers() throws Exception {
        // Initialize the database
        mutedUsersRepository.saveAndFlush(mutedUsers);

        // Get all the mutedUsersList
        restMutedUsersMockMvc.perform(get("/api/muted-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mutedUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].mutedUserId").value(hasItem(DEFAULT_MUTED_USER_ID)));
    }

    @Test
    @Transactional
    public void getMutedUsers() throws Exception {
        // Initialize the database
        mutedUsersRepository.saveAndFlush(mutedUsers);

        // Get the mutedUsers
        restMutedUsersMockMvc.perform(get("/api/muted-users/{id}", mutedUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mutedUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.mutedUserId").value(DEFAULT_MUTED_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingMutedUsers() throws Exception {
        // Get the mutedUsers
        restMutedUsersMockMvc.perform(get("/api/muted-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMutedUsers() throws Exception {
        // Initialize the database
        mutedUsersRepository.saveAndFlush(mutedUsers);

        int databaseSizeBeforeUpdate = mutedUsersRepository.findAll().size();

        // Update the mutedUsers
        MutedUsers updatedMutedUsers = mutedUsersRepository.findById(mutedUsers.getId()).get();
        // Disconnect from session so that the updates on updatedMutedUsers are not directly saved in db
        em.detach(updatedMutedUsers);
        updatedMutedUsers
            .userId(UPDATED_USER_ID)
            .mutedUserId(UPDATED_MUTED_USER_ID);
        MutedUsersDTO mutedUsersDTO = mutedUsersMapper.toDto(updatedMutedUsers);

        restMutedUsersMockMvc.perform(put("/api/muted-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mutedUsersDTO)))
            .andExpect(status().isOk());

        // Validate the MutedUsers in the database
        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeUpdate);
        MutedUsers testMutedUsers = mutedUsersList.get(mutedUsersList.size() - 1);
        assertThat(testMutedUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMutedUsers.getMutedUserId()).isEqualTo(UPDATED_MUTED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingMutedUsers() throws Exception {
        int databaseSizeBeforeUpdate = mutedUsersRepository.findAll().size();

        // Create the MutedUsers
        MutedUsersDTO mutedUsersDTO = mutedUsersMapper.toDto(mutedUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutedUsersMockMvc.perform(put("/api/muted-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mutedUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MutedUsers in the database
        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMutedUsers() throws Exception {
        // Initialize the database
        mutedUsersRepository.saveAndFlush(mutedUsers);

        int databaseSizeBeforeDelete = mutedUsersRepository.findAll().size();

        // Delete the mutedUsers
        restMutedUsersMockMvc.perform(delete("/api/muted-users/{id}", mutedUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MutedUsers> mutedUsersList = mutedUsersRepository.findAll();
        assertThat(mutedUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
