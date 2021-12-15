/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupCustomFields;
import com.infy.repository.GroupCustomFieldsRepository;
import com.infy.service.GroupCustomFieldsService;
import com.infy.service.dto.GroupCustomFieldsDTO;
import com.infy.service.mapper.GroupCustomFieldsMapper;

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
 * Integration tests for the {@link GroupCustomFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupCustomFieldsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private GroupCustomFieldsRepository groupCustomFieldsRepository;

    @Autowired
    private GroupCustomFieldsMapper groupCustomFieldsMapper;

    @Autowired
    private GroupCustomFieldsService groupCustomFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupCustomFieldsMockMvc;

    private GroupCustomFields groupCustomFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupCustomFields createEntity(EntityManager em) {
        GroupCustomFields groupCustomFields = new GroupCustomFields()
            .groupId(DEFAULT_GROUP_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return groupCustomFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupCustomFields createUpdatedEntity(EntityManager em) {
        GroupCustomFields groupCustomFields = new GroupCustomFields()
            .groupId(UPDATED_GROUP_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return groupCustomFields;
    }

    @BeforeEach
    public void initTest() {
        groupCustomFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupCustomFields() throws Exception {
        int databaseSizeBeforeCreate = groupCustomFieldsRepository.findAll().size();
        // Create the GroupCustomFields
        GroupCustomFieldsDTO groupCustomFieldsDTO = groupCustomFieldsMapper.toDto(groupCustomFields);
        restGroupCustomFieldsMockMvc.perform(post("/api/group-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCustomFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupCustomFields in the database
        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        GroupCustomFields testGroupCustomFields = groupCustomFieldsList.get(groupCustomFieldsList.size() - 1);
        assertThat(testGroupCustomFields.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupCustomFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupCustomFields.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createGroupCustomFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupCustomFieldsRepository.findAll().size();

        // Create the GroupCustomFields with an existing ID
        groupCustomFields.setId(1L);
        GroupCustomFieldsDTO groupCustomFieldsDTO = groupCustomFieldsMapper.toDto(groupCustomFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupCustomFieldsMockMvc.perform(post("/api/group-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCustomFields in the database
        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCustomFieldsRepository.findAll().size();
        // set the field null
        groupCustomFields.setGroupId(null);

        // Create the GroupCustomFields, which fails.
        GroupCustomFieldsDTO groupCustomFieldsDTO = groupCustomFieldsMapper.toDto(groupCustomFields);


        restGroupCustomFieldsMockMvc.perform(post("/api/group-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCustomFieldsRepository.findAll().size();
        // set the field null
        groupCustomFields.setName(null);

        // Create the GroupCustomFields, which fails.
        GroupCustomFieldsDTO groupCustomFieldsDTO = groupCustomFieldsMapper.toDto(groupCustomFields);


        restGroupCustomFieldsMockMvc.perform(post("/api/group-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupCustomFields() throws Exception {
        // Initialize the database
        groupCustomFieldsRepository.saveAndFlush(groupCustomFields);

        // Get all the groupCustomFieldsList
        restGroupCustomFieldsMockMvc.perform(get("/api/group-custom-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupCustomFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getGroupCustomFields() throws Exception {
        // Initialize the database
        groupCustomFieldsRepository.saveAndFlush(groupCustomFields);

        // Get the groupCustomFields
        restGroupCustomFieldsMockMvc.perform(get("/api/group-custom-fields/{id}", groupCustomFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupCustomFields.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingGroupCustomFields() throws Exception {
        // Get the groupCustomFields
        restGroupCustomFieldsMockMvc.perform(get("/api/group-custom-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupCustomFields() throws Exception {
        // Initialize the database
        groupCustomFieldsRepository.saveAndFlush(groupCustomFields);

        int databaseSizeBeforeUpdate = groupCustomFieldsRepository.findAll().size();

        // Update the groupCustomFields
        GroupCustomFields updatedGroupCustomFields = groupCustomFieldsRepository.findById(groupCustomFields.getId()).get();
        // Disconnect from session so that the updates on updatedGroupCustomFields are not directly saved in db
        em.detach(updatedGroupCustomFields);
        updatedGroupCustomFields
            .groupId(UPDATED_GROUP_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        GroupCustomFieldsDTO groupCustomFieldsDTO = groupCustomFieldsMapper.toDto(updatedGroupCustomFields);

        restGroupCustomFieldsMockMvc.perform(put("/api/group-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCustomFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the GroupCustomFields in the database
        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
        GroupCustomFields testGroupCustomFields = groupCustomFieldsList.get(groupCustomFieldsList.size() - 1);
        assertThat(testGroupCustomFields.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupCustomFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupCustomFields.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupCustomFields() throws Exception {
        int databaseSizeBeforeUpdate = groupCustomFieldsRepository.findAll().size();

        // Create the GroupCustomFields
        GroupCustomFieldsDTO groupCustomFieldsDTO = groupCustomFieldsMapper.toDto(groupCustomFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupCustomFieldsMockMvc.perform(put("/api/group-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCustomFields in the database
        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupCustomFields() throws Exception {
        // Initialize the database
        groupCustomFieldsRepository.saveAndFlush(groupCustomFields);

        int databaseSizeBeforeDelete = groupCustomFieldsRepository.findAll().size();

        // Delete the groupCustomFields
        restGroupCustomFieldsMockMvc.perform(delete("/api/group-custom-fields/{id}", groupCustomFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupCustomFields> groupCustomFieldsList = groupCustomFieldsRepository.findAll();
        assertThat(groupCustomFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
