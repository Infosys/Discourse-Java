/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserEmails;
import com.infy.repository.UserEmailsRepository;
import com.infy.service.UserEmailsService;
import com.infy.service.dto.UserEmailsDTO;
import com.infy.service.mapper.UserEmailsMapper;

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
 * Integration tests for the {@link UserEmailsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserEmailsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIMARY = false;
    private static final Boolean UPDATED_PRIMARY = true;

    @Autowired
    private UserEmailsRepository userEmailsRepository;

    @Autowired
    private UserEmailsMapper userEmailsMapper;

    @Autowired
    private UserEmailsService userEmailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserEmailsMockMvc;

    private UserEmails userEmails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserEmails createEntity(EntityManager em) {
        UserEmails userEmails = new UserEmails()
            .userId(DEFAULT_USER_ID)
            .email(DEFAULT_EMAIL)
            .primary(DEFAULT_PRIMARY);
        return userEmails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserEmails createUpdatedEntity(EntityManager em) {
        UserEmails userEmails = new UserEmails()
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .primary(UPDATED_PRIMARY);
        return userEmails;
    }

    @BeforeEach
    public void initTest() {
        userEmails = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserEmails() throws Exception {
        int databaseSizeBeforeCreate = userEmailsRepository.findAll().size();
        // Create the UserEmails
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(userEmails);
        restUserEmailsMockMvc.perform(post("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserEmails in the database
        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeCreate + 1);
        UserEmails testUserEmails = userEmailsList.get(userEmailsList.size() - 1);
        assertThat(testUserEmails.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserEmails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserEmails.isPrimary()).isEqualTo(DEFAULT_PRIMARY);
    }

    @Test
    @Transactional
    public void createUserEmailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userEmailsRepository.findAll().size();

        // Create the UserEmails with an existing ID
        userEmails.setId(1L);
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(userEmails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserEmailsMockMvc.perform(post("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserEmails in the database
        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userEmailsRepository.findAll().size();
        // set the field null
        userEmails.setUserId(null);

        // Create the UserEmails, which fails.
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(userEmails);


        restUserEmailsMockMvc.perform(post("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userEmailsRepository.findAll().size();
        // set the field null
        userEmails.setEmail(null);

        // Create the UserEmails, which fails.
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(userEmails);


        restUserEmailsMockMvc.perform(post("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = userEmailsRepository.findAll().size();
        // set the field null
        userEmails.setPrimary(null);

        // Create the UserEmails, which fails.
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(userEmails);


        restUserEmailsMockMvc.perform(post("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserEmails() throws Exception {
        // Initialize the database
        userEmailsRepository.saveAndFlush(userEmails);

        // Get all the userEmailsList
        restUserEmailsMockMvc.perform(get("/api/user-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userEmails.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].primary").value(hasItem(DEFAULT_PRIMARY.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserEmails() throws Exception {
        // Initialize the database
        userEmailsRepository.saveAndFlush(userEmails);

        // Get the userEmails
        restUserEmailsMockMvc.perform(get("/api/user-emails/{id}", userEmails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userEmails.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.primary").value(DEFAULT_PRIMARY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserEmails() throws Exception {
        // Get the userEmails
        restUserEmailsMockMvc.perform(get("/api/user-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserEmails() throws Exception {
        // Initialize the database
        userEmailsRepository.saveAndFlush(userEmails);

        int databaseSizeBeforeUpdate = userEmailsRepository.findAll().size();

        // Update the userEmails
        UserEmails updatedUserEmails = userEmailsRepository.findById(userEmails.getId()).get();
        // Disconnect from session so that the updates on updatedUserEmails are not directly saved in db
        em.detach(updatedUserEmails);
        updatedUserEmails
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .primary(UPDATED_PRIMARY);
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(updatedUserEmails);

        restUserEmailsMockMvc.perform(put("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isOk());

        // Validate the UserEmails in the database
        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeUpdate);
        UserEmails testUserEmails = userEmailsList.get(userEmailsList.size() - 1);
        assertThat(testUserEmails.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserEmails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserEmails.isPrimary()).isEqualTo(UPDATED_PRIMARY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserEmails() throws Exception {
        int databaseSizeBeforeUpdate = userEmailsRepository.findAll().size();

        // Create the UserEmails
        UserEmailsDTO userEmailsDTO = userEmailsMapper.toDto(userEmails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserEmailsMockMvc.perform(put("/api/user-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEmailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserEmails in the database
        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserEmails() throws Exception {
        // Initialize the database
        userEmailsRepository.saveAndFlush(userEmails);

        int databaseSizeBeforeDelete = userEmailsRepository.findAll().size();

        // Delete the userEmails
        restUserEmailsMockMvc.perform(delete("/api/user-emails/{id}", userEmails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserEmails> userEmailsList = userEmailsRepository.findAll();
        assertThat(userEmailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
