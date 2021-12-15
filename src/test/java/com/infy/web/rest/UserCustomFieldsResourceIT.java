/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserCustomFields;
import com.infy.repository.UserCustomFieldsRepository;
import com.infy.service.UserCustomFieldsService;
import com.infy.service.dto.UserCustomFieldsDTO;
import com.infy.service.mapper.UserCustomFieldsMapper;

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
 * Integration tests for the {@link UserCustomFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserCustomFieldsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private UserCustomFieldsRepository userCustomFieldsRepository;

    @Autowired
    private UserCustomFieldsMapper userCustomFieldsMapper;

    @Autowired
    private UserCustomFieldsService userCustomFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserCustomFieldsMockMvc;

    private UserCustomFields userCustomFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCustomFields createEntity(EntityManager em) {
        UserCustomFields userCustomFields = new UserCustomFields()
            .userId(DEFAULT_USER_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return userCustomFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCustomFields createUpdatedEntity(EntityManager em) {
        UserCustomFields userCustomFields = new UserCustomFields()
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return userCustomFields;
    }

    @BeforeEach
    public void initTest() {
        userCustomFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserCustomFields() throws Exception {
        int databaseSizeBeforeCreate = userCustomFieldsRepository.findAll().size();
        // Create the UserCustomFields
        UserCustomFieldsDTO userCustomFieldsDTO = userCustomFieldsMapper.toDto(userCustomFields);
        restUserCustomFieldsMockMvc.perform(post("/api/user-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCustomFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCustomFields in the database
        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        UserCustomFields testUserCustomFields = userCustomFieldsList.get(userCustomFieldsList.size() - 1);
        assertThat(testUserCustomFields.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserCustomFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserCustomFields.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createUserCustomFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userCustomFieldsRepository.findAll().size();

        // Create the UserCustomFields with an existing ID
        userCustomFields.setId(1L);
        UserCustomFieldsDTO userCustomFieldsDTO = userCustomFieldsMapper.toDto(userCustomFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCustomFieldsMockMvc.perform(post("/api/user-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCustomFields in the database
        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCustomFieldsRepository.findAll().size();
        // set the field null
        userCustomFields.setUserId(null);

        // Create the UserCustomFields, which fails.
        UserCustomFieldsDTO userCustomFieldsDTO = userCustomFieldsMapper.toDto(userCustomFields);


        restUserCustomFieldsMockMvc.perform(post("/api/user-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCustomFieldsRepository.findAll().size();
        // set the field null
        userCustomFields.setName(null);

        // Create the UserCustomFields, which fails.
        UserCustomFieldsDTO userCustomFieldsDTO = userCustomFieldsMapper.toDto(userCustomFields);


        restUserCustomFieldsMockMvc.perform(post("/api/user-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserCustomFields() throws Exception {
        // Initialize the database
        userCustomFieldsRepository.saveAndFlush(userCustomFields);

        // Get all the userCustomFieldsList
        restUserCustomFieldsMockMvc.perform(get("/api/user-custom-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCustomFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getUserCustomFields() throws Exception {
        // Initialize the database
        userCustomFieldsRepository.saveAndFlush(userCustomFields);

        // Get the userCustomFields
        restUserCustomFieldsMockMvc.perform(get("/api/user-custom-fields/{id}", userCustomFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userCustomFields.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingUserCustomFields() throws Exception {
        // Get the userCustomFields
        restUserCustomFieldsMockMvc.perform(get("/api/user-custom-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserCustomFields() throws Exception {
        // Initialize the database
        userCustomFieldsRepository.saveAndFlush(userCustomFields);

        int databaseSizeBeforeUpdate = userCustomFieldsRepository.findAll().size();

        // Update the userCustomFields
        UserCustomFields updatedUserCustomFields = userCustomFieldsRepository.findById(userCustomFields.getId()).get();
        // Disconnect from session so that the updates on updatedUserCustomFields are not directly saved in db
        em.detach(updatedUserCustomFields);
        updatedUserCustomFields
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        UserCustomFieldsDTO userCustomFieldsDTO = userCustomFieldsMapper.toDto(updatedUserCustomFields);

        restUserCustomFieldsMockMvc.perform(put("/api/user-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCustomFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the UserCustomFields in the database
        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
        UserCustomFields testUserCustomFields = userCustomFieldsList.get(userCustomFieldsList.size() - 1);
        assertThat(testUserCustomFields.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserCustomFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserCustomFields.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserCustomFields() throws Exception {
        int databaseSizeBeforeUpdate = userCustomFieldsRepository.findAll().size();

        // Create the UserCustomFields
        UserCustomFieldsDTO userCustomFieldsDTO = userCustomFieldsMapper.toDto(userCustomFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCustomFieldsMockMvc.perform(put("/api/user-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCustomFields in the database
        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserCustomFields() throws Exception {
        // Initialize the database
        userCustomFieldsRepository.saveAndFlush(userCustomFields);

        int databaseSizeBeforeDelete = userCustomFieldsRepository.findAll().size();

        // Delete the userCustomFields
        restUserCustomFieldsMockMvc.perform(delete("/api/user-custom-fields/{id}", userCustomFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserCustomFields> userCustomFieldsList = userCustomFieldsRepository.findAll();
        assertThat(userCustomFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
