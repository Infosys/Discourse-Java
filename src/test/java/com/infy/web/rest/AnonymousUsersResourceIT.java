/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.AnonymousUsers;
import com.infy.repository.AnonymousUsersRepository;
import com.infy.service.AnonymousUsersService;
import com.infy.service.dto.AnonymousUsersDTO;
import com.infy.service.mapper.AnonymousUsersMapper;

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
 * Integration tests for the {@link AnonymousUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AnonymousUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MASTER_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AnonymousUsersRepository anonymousUsersRepository;

    @Autowired
    private AnonymousUsersMapper anonymousUsersMapper;

    @Autowired
    private AnonymousUsersService anonymousUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnonymousUsersMockMvc;

    private AnonymousUsers anonymousUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnonymousUsers createEntity(EntityManager em) {
        AnonymousUsers anonymousUsers = new AnonymousUsers()
            .userId(DEFAULT_USER_ID)
            .masterUserId(DEFAULT_MASTER_USER_ID)
            .active(DEFAULT_ACTIVE);
        return anonymousUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnonymousUsers createUpdatedEntity(EntityManager em) {
        AnonymousUsers anonymousUsers = new AnonymousUsers()
            .userId(UPDATED_USER_ID)
            .masterUserId(UPDATED_MASTER_USER_ID)
            .active(UPDATED_ACTIVE);
        return anonymousUsers;
    }

    @BeforeEach
    public void initTest() {
        anonymousUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnonymousUsers() throws Exception {
        int databaseSizeBeforeCreate = anonymousUsersRepository.findAll().size();
        // Create the AnonymousUsers
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(anonymousUsers);
        restAnonymousUsersMockMvc.perform(post("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the AnonymousUsers in the database
        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeCreate + 1);
        AnonymousUsers testAnonymousUsers = anonymousUsersList.get(anonymousUsersList.size() - 1);
        assertThat(testAnonymousUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAnonymousUsers.getMasterUserId()).isEqualTo(DEFAULT_MASTER_USER_ID);
        assertThat(testAnonymousUsers.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAnonymousUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anonymousUsersRepository.findAll().size();

        // Create the AnonymousUsers with an existing ID
        anonymousUsers.setId(1L);
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(anonymousUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnonymousUsersMockMvc.perform(post("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnonymousUsers in the database
        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = anonymousUsersRepository.findAll().size();
        // set the field null
        anonymousUsers.setUserId(null);

        // Create the AnonymousUsers, which fails.
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(anonymousUsers);


        restAnonymousUsersMockMvc.perform(post("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMasterUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = anonymousUsersRepository.findAll().size();
        // set the field null
        anonymousUsers.setMasterUserId(null);

        // Create the AnonymousUsers, which fails.
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(anonymousUsers);


        restAnonymousUsersMockMvc.perform(post("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = anonymousUsersRepository.findAll().size();
        // set the field null
        anonymousUsers.setActive(null);

        // Create the AnonymousUsers, which fails.
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(anonymousUsers);


        restAnonymousUsersMockMvc.perform(post("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnonymousUsers() throws Exception {
        // Initialize the database
        anonymousUsersRepository.saveAndFlush(anonymousUsers);

        // Get all the anonymousUsersList
        restAnonymousUsersMockMvc.perform(get("/api/anonymous-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anonymousUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].masterUserId").value(hasItem(DEFAULT_MASTER_USER_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAnonymousUsers() throws Exception {
        // Initialize the database
        anonymousUsersRepository.saveAndFlush(anonymousUsers);

        // Get the anonymousUsers
        restAnonymousUsersMockMvc.perform(get("/api/anonymous-users/{id}", anonymousUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anonymousUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.masterUserId").value(DEFAULT_MASTER_USER_ID))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAnonymousUsers() throws Exception {
        // Get the anonymousUsers
        restAnonymousUsersMockMvc.perform(get("/api/anonymous-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnonymousUsers() throws Exception {
        // Initialize the database
        anonymousUsersRepository.saveAndFlush(anonymousUsers);

        int databaseSizeBeforeUpdate = anonymousUsersRepository.findAll().size();

        // Update the anonymousUsers
        AnonymousUsers updatedAnonymousUsers = anonymousUsersRepository.findById(anonymousUsers.getId()).get();
        // Disconnect from session so that the updates on updatedAnonymousUsers are not directly saved in db
        em.detach(updatedAnonymousUsers);
        updatedAnonymousUsers
            .userId(UPDATED_USER_ID)
            .masterUserId(UPDATED_MASTER_USER_ID)
            .active(UPDATED_ACTIVE);
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(updatedAnonymousUsers);

        restAnonymousUsersMockMvc.perform(put("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isOk());

        // Validate the AnonymousUsers in the database
        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeUpdate);
        AnonymousUsers testAnonymousUsers = anonymousUsersList.get(anonymousUsersList.size() - 1);
        assertThat(testAnonymousUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAnonymousUsers.getMasterUserId()).isEqualTo(UPDATED_MASTER_USER_ID);
        assertThat(testAnonymousUsers.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnonymousUsers() throws Exception {
        int databaseSizeBeforeUpdate = anonymousUsersRepository.findAll().size();

        // Create the AnonymousUsers
        AnonymousUsersDTO anonymousUsersDTO = anonymousUsersMapper.toDto(anonymousUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnonymousUsersMockMvc.perform(put("/api/anonymous-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymousUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnonymousUsers in the database
        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnonymousUsers() throws Exception {
        // Initialize the database
        anonymousUsersRepository.saveAndFlush(anonymousUsers);

        int databaseSizeBeforeDelete = anonymousUsersRepository.findAll().size();

        // Delete the anonymousUsers
        restAnonymousUsersMockMvc.perform(delete("/api/anonymous-users/{id}", anonymousUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnonymousUsers> anonymousUsersList = anonymousUsersRepository.findAll();
        assertThat(anonymousUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
