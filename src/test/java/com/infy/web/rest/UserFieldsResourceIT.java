/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserFields;
import com.infy.repository.UserFieldsRepository;
import com.infy.service.UserFieldsService;
import com.infy.service.dto.UserFieldsDTO;
import com.infy.service.mapper.UserFieldsMapper;

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
 * Integration tests for the {@link UserFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserFieldsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EDITABLE = false;
    private static final Boolean UPDATED_EDITABLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REQUIRED = false;
    private static final Boolean UPDATED_REQUIRED = true;

    private static final Boolean DEFAULT_SHOW_ON_PROFILE = false;
    private static final Boolean UPDATED_SHOW_ON_PROFILE = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Boolean DEFAULT_SHOW_ON_USER_CARD = false;
    private static final Boolean UPDATED_SHOW_ON_USER_CARD = true;

    private static final String DEFAULT_EXTERNAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEARCHABLE = false;
    private static final Boolean UPDATED_SEARCHABLE = true;

    @Autowired
    private UserFieldsRepository userFieldsRepository;

    @Autowired
    private UserFieldsMapper userFieldsMapper;

    @Autowired
    private UserFieldsService userFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserFieldsMockMvc;

    private UserFields userFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFields createEntity(EntityManager em) {
        UserFields userFields = new UserFields()
            .name(DEFAULT_NAME)
            .fieldType(DEFAULT_FIELD_TYPE)
            .editable(DEFAULT_EDITABLE)
            .description(DEFAULT_DESCRIPTION)
            .required(DEFAULT_REQUIRED)
            .showOnProfile(DEFAULT_SHOW_ON_PROFILE)
            .position(DEFAULT_POSITION)
            .showOnUserCard(DEFAULT_SHOW_ON_USER_CARD)
            .externalName(DEFAULT_EXTERNAL_NAME)
            .externalType(DEFAULT_EXTERNAL_TYPE)
            .searchable(DEFAULT_SEARCHABLE);
        return userFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFields createUpdatedEntity(EntityManager em) {
        UserFields userFields = new UserFields()
            .name(UPDATED_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .editable(UPDATED_EDITABLE)
            .description(UPDATED_DESCRIPTION)
            .required(UPDATED_REQUIRED)
            .showOnProfile(UPDATED_SHOW_ON_PROFILE)
            .position(UPDATED_POSITION)
            .showOnUserCard(UPDATED_SHOW_ON_USER_CARD)
            .externalName(UPDATED_EXTERNAL_NAME)
            .externalType(UPDATED_EXTERNAL_TYPE)
            .searchable(UPDATED_SEARCHABLE);
        return userFields;
    }

    @BeforeEach
    public void initTest() {
        userFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFields() throws Exception {
        int databaseSizeBeforeCreate = userFieldsRepository.findAll().size();
        // Create the UserFields
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);
        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserFields in the database
        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        UserFields testUserFields = userFieldsList.get(userFieldsList.size() - 1);
        assertThat(testUserFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserFields.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testUserFields.isEditable()).isEqualTo(DEFAULT_EDITABLE);
        assertThat(testUserFields.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserFields.isRequired()).isEqualTo(DEFAULT_REQUIRED);
        assertThat(testUserFields.isShowOnProfile()).isEqualTo(DEFAULT_SHOW_ON_PROFILE);
        assertThat(testUserFields.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testUserFields.isShowOnUserCard()).isEqualTo(DEFAULT_SHOW_ON_USER_CARD);
        assertThat(testUserFields.getExternalName()).isEqualTo(DEFAULT_EXTERNAL_NAME);
        assertThat(testUserFields.getExternalType()).isEqualTo(DEFAULT_EXTERNAL_TYPE);
        assertThat(testUserFields.isSearchable()).isEqualTo(DEFAULT_SEARCHABLE);
    }

    @Test
    @Transactional
    public void createUserFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFieldsRepository.findAll().size();

        // Create the UserFields with an existing ID
        userFields.setId(1L);
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFields in the database
        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setName(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setFieldType(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditableIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setEditable(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setDescription(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setRequired(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShowOnProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setShowOnProfile(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShowOnUserCardIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setShowOnUserCard(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSearchableIsRequired() throws Exception {
        int databaseSizeBeforeTest = userFieldsRepository.findAll().size();
        // set the field null
        userFields.setSearchable(null);

        // Create the UserFields, which fails.
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);


        restUserFieldsMockMvc.perform(post("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserFields() throws Exception {
        // Initialize the database
        userFieldsRepository.saveAndFlush(userFields);

        // Get all the userFieldsList
        restUserFieldsMockMvc.perform(get("/api/user-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].editable").value(hasItem(DEFAULT_EDITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].required").value(hasItem(DEFAULT_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].showOnProfile").value(hasItem(DEFAULT_SHOW_ON_PROFILE.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].showOnUserCard").value(hasItem(DEFAULT_SHOW_ON_USER_CARD.booleanValue())))
            .andExpect(jsonPath("$.[*].externalName").value(hasItem(DEFAULT_EXTERNAL_NAME)))
            .andExpect(jsonPath("$.[*].externalType").value(hasItem(DEFAULT_EXTERNAL_TYPE)))
            .andExpect(jsonPath("$.[*].searchable").value(hasItem(DEFAULT_SEARCHABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserFields() throws Exception {
        // Initialize the database
        userFieldsRepository.saveAndFlush(userFields);

        // Get the userFields
        restUserFieldsMockMvc.perform(get("/api/user-fields/{id}", userFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userFields.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE))
            .andExpect(jsonPath("$.editable").value(DEFAULT_EDITABLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.required").value(DEFAULT_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.showOnProfile").value(DEFAULT_SHOW_ON_PROFILE.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.showOnUserCard").value(DEFAULT_SHOW_ON_USER_CARD.booleanValue()))
            .andExpect(jsonPath("$.externalName").value(DEFAULT_EXTERNAL_NAME))
            .andExpect(jsonPath("$.externalType").value(DEFAULT_EXTERNAL_TYPE))
            .andExpect(jsonPath("$.searchable").value(DEFAULT_SEARCHABLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserFields() throws Exception {
        // Get the userFields
        restUserFieldsMockMvc.perform(get("/api/user-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFields() throws Exception {
        // Initialize the database
        userFieldsRepository.saveAndFlush(userFields);

        int databaseSizeBeforeUpdate = userFieldsRepository.findAll().size();

        // Update the userFields
        UserFields updatedUserFields = userFieldsRepository.findById(userFields.getId()).get();
        // Disconnect from session so that the updates on updatedUserFields are not directly saved in db
        em.detach(updatedUserFields);
        updatedUserFields
            .name(UPDATED_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .editable(UPDATED_EDITABLE)
            .description(UPDATED_DESCRIPTION)
            .required(UPDATED_REQUIRED)
            .showOnProfile(UPDATED_SHOW_ON_PROFILE)
            .position(UPDATED_POSITION)
            .showOnUserCard(UPDATED_SHOW_ON_USER_CARD)
            .externalName(UPDATED_EXTERNAL_NAME)
            .externalType(UPDATED_EXTERNAL_TYPE)
            .searchable(UPDATED_SEARCHABLE);
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(updatedUserFields);

        restUserFieldsMockMvc.perform(put("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the UserFields in the database
        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeUpdate);
        UserFields testUserFields = userFieldsList.get(userFieldsList.size() - 1);
        assertThat(testUserFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserFields.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testUserFields.isEditable()).isEqualTo(UPDATED_EDITABLE);
        assertThat(testUserFields.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserFields.isRequired()).isEqualTo(UPDATED_REQUIRED);
        assertThat(testUserFields.isShowOnProfile()).isEqualTo(UPDATED_SHOW_ON_PROFILE);
        assertThat(testUserFields.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testUserFields.isShowOnUserCard()).isEqualTo(UPDATED_SHOW_ON_USER_CARD);
        assertThat(testUserFields.getExternalName()).isEqualTo(UPDATED_EXTERNAL_NAME);
        assertThat(testUserFields.getExternalType()).isEqualTo(UPDATED_EXTERNAL_TYPE);
        assertThat(testUserFields.isSearchable()).isEqualTo(UPDATED_SEARCHABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFields() throws Exception {
        int databaseSizeBeforeUpdate = userFieldsRepository.findAll().size();

        // Create the UserFields
        UserFieldsDTO userFieldsDTO = userFieldsMapper.toDto(userFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFieldsMockMvc.perform(put("/api/user-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFields in the database
        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserFields() throws Exception {
        // Initialize the database
        userFieldsRepository.saveAndFlush(userFields);

        int databaseSizeBeforeDelete = userFieldsRepository.findAll().size();

        // Delete the userFields
        restUserFieldsMockMvc.perform(delete("/api/user-fields/{id}", userFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserFields> userFieldsList = userFieldsRepository.findAll();
        assertThat(userFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
