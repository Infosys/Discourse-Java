/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserFieldOptions;
import com.infy.repository.UserFieldOptionsRepository;
import com.infy.service.UserFieldOptionsService;
import com.infy.service.dto.UserFieldOptionsDTO;
import com.infy.service.mapper.UserFieldOptionsMapper;

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
 * Integration tests for the {@link UserFieldOptionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserFieldOptionsResourceIT {

    private static final Long DEFAULT_USER_FIELD_ID = 1L;
    private static final Long UPDATED_USER_FIELD_ID = 2L;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private UserFieldOptionsRepository userFieldOptionsRepository;

    @Autowired
    private UserFieldOptionsMapper userFieldOptionsMapper;

    @Autowired
    private UserFieldOptionsService userFieldOptionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserFieldOptionsMockMvc;

    private UserFieldOptions userFieldOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFieldOptions createEntity(EntityManager em) {
        UserFieldOptions userFieldOptions = new UserFieldOptions()
            .userFieldId(DEFAULT_USER_FIELD_ID)
            .value(DEFAULT_VALUE);
        return userFieldOptions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFieldOptions createUpdatedEntity(EntityManager em) {
        UserFieldOptions userFieldOptions = new UserFieldOptions()
            .userFieldId(UPDATED_USER_FIELD_ID)
            .value(UPDATED_VALUE);
        return userFieldOptions;
    }

    @BeforeEach
    public void initTest() {
        userFieldOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFieldOptions() throws Exception {
        int databaseSizeBeforeCreate = userFieldOptionsRepository.findAll().size();
        // Create the UserFieldOptions
        UserFieldOptionsDTO userFieldOptionsDTO = userFieldOptionsMapper.toDto(userFieldOptions);
        restUserFieldOptionsMockMvc.perform(post("/api/user-field-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldOptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserFieldOptions in the database
        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        UserFieldOptions testUserFieldOptions = userFieldOptionsList.get(userFieldOptionsList.size() - 1);
        assertThat(testUserFieldOptions.getUserFieldId()).isEqualTo(DEFAULT_USER_FIELD_ID);
        assertThat(testUserFieldOptions.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createUserFieldOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFieldOptionsRepository.findAll().size();

        // Create the UserFieldOptions with an existing ID
        userFieldOptions.setId(1L);
        UserFieldOptionsDTO userFieldOptionsDTO = userFieldOptionsMapper.toDto(userFieldOptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFieldOptionsMockMvc.perform(post("/api/user-field-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFieldOptions in the database
        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserFieldIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldOptionsRepository.findAll().size();
        // set the field null
        userFieldOptions.setUserFieldId(null);

        // Create the UserFieldOptions, which fails.
        UserFieldOptionsDTO userFieldOptionsDTO = userFieldOptionsMapper.toDto(userFieldOptions);


        restUserFieldOptionsMockMvc.perform(post("/api/user-field-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldOptionsRepository.findAll().size();
        // set the field null
        userFieldOptions.setValue(null);

        // Create the UserFieldOptions, which fails.
        UserFieldOptionsDTO userFieldOptionsDTO = userFieldOptionsMapper.toDto(userFieldOptions);


        restUserFieldOptionsMockMvc.perform(post("/api/user-field-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserFieldOptions() throws Exception {
        // Initialize the database
        userFieldOptionsRepository.saveAndFlush(userFieldOptions);

        // Get all the userFieldOptionsList
        restUserFieldOptionsMockMvc.perform(get("/api/user-field-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFieldOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].userFieldId").value(hasItem(DEFAULT_USER_FIELD_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getUserFieldOptions() throws Exception {
        // Initialize the database
        userFieldOptionsRepository.saveAndFlush(userFieldOptions);

        // Get the userFieldOptions
        restUserFieldOptionsMockMvc.perform(get("/api/user-field-options/{id}", userFieldOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userFieldOptions.getId().intValue()))
            .andExpect(jsonPath("$.userFieldId").value(DEFAULT_USER_FIELD_ID.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingUserFieldOptions() throws Exception {
        // Get the userFieldOptions
        restUserFieldOptionsMockMvc.perform(get("/api/user-field-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFieldOptions() throws Exception {
        // Initialize the database
        userFieldOptionsRepository.saveAndFlush(userFieldOptions);

        int databaseSizeBeforeUpdate = userFieldOptionsRepository.findAll().size();

        // Update the userFieldOptions
        UserFieldOptions updatedUserFieldOptions = userFieldOptionsRepository.findById(userFieldOptions.getId()).get();
        // Disconnect from session so that the updates on updatedUserFieldOptions are not directly saved in db
        em.detach(updatedUserFieldOptions);
        updatedUserFieldOptions
            .userFieldId(UPDATED_USER_FIELD_ID)
            .value(UPDATED_VALUE);
        UserFieldOptionsDTO userFieldOptionsDTO = userFieldOptionsMapper.toDto(updatedUserFieldOptions);

        restUserFieldOptionsMockMvc.perform(put("/api/user-field-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldOptionsDTO)))
            .andExpect(status().isOk());

        // Validate the UserFieldOptions in the database
        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeUpdate);
        UserFieldOptions testUserFieldOptions = userFieldOptionsList.get(userFieldOptionsList.size() - 1);
        assertThat(testUserFieldOptions.getUserFieldId()).isEqualTo(UPDATED_USER_FIELD_ID);
        assertThat(testUserFieldOptions.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFieldOptions() throws Exception {
        int databaseSizeBeforeUpdate = userFieldOptionsRepository.findAll().size();

        // Create the UserFieldOptions
        UserFieldOptionsDTO userFieldOptionsDTO = userFieldOptionsMapper.toDto(userFieldOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFieldOptionsMockMvc.perform(put("/api/user-field-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFieldOptions in the database
        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserFieldOptions() throws Exception {
        // Initialize the database
        userFieldOptionsRepository.saveAndFlush(userFieldOptions);

        int databaseSizeBeforeDelete = userFieldOptionsRepository.findAll().size();

        // Delete the userFieldOptions
        restUserFieldOptionsMockMvc.perform(delete("/api/user-field-options/{id}", userFieldOptions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserFieldOptions> userFieldOptionsList = userFieldOptionsRepository.findAll();
        assertThat(userFieldOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
