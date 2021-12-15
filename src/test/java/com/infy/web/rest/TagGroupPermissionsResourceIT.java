/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TagGroupPermissions;
import com.infy.repository.TagGroupPermissionsRepository;
import com.infy.service.TagGroupPermissionsService;
import com.infy.service.dto.TagGroupPermissionsDTO;
import com.infy.service.mapper.TagGroupPermissionsMapper;

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
 * Integration tests for the {@link TagGroupPermissionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TagGroupPermissionsResourceIT {

    private static final Long DEFAULT_TAG_GROUP_ID = 1L;
    private static final Long UPDATED_TAG_GROUP_ID = 2L;

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Integer DEFAULT_PERMISSION_TYPE = 1;
    private static final Integer UPDATED_PERMISSION_TYPE = 2;

    @Autowired
    private TagGroupPermissionsRepository tagGroupPermissionsRepository;

    @Autowired
    private TagGroupPermissionsMapper tagGroupPermissionsMapper;

    @Autowired
    private TagGroupPermissionsService tagGroupPermissionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagGroupPermissionsMockMvc;

    private TagGroupPermissions tagGroupPermissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagGroupPermissions createEntity(EntityManager em) {
        TagGroupPermissions tagGroupPermissions = new TagGroupPermissions()
            .tagGroupId(DEFAULT_TAG_GROUP_ID)
            .groupId(DEFAULT_GROUP_ID)
            .permissionType(DEFAULT_PERMISSION_TYPE);
        return tagGroupPermissions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagGroupPermissions createUpdatedEntity(EntityManager em) {
        TagGroupPermissions tagGroupPermissions = new TagGroupPermissions()
            .tagGroupId(UPDATED_TAG_GROUP_ID)
            .groupId(UPDATED_GROUP_ID)
            .permissionType(UPDATED_PERMISSION_TYPE);
        return tagGroupPermissions;
    }

    @BeforeEach
    public void initTest() {
        tagGroupPermissions = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagGroupPermissions() throws Exception {
        int databaseSizeBeforeCreate = tagGroupPermissionsRepository.findAll().size();
        // Create the TagGroupPermissions
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(tagGroupPermissions);
        restTagGroupPermissionsMockMvc.perform(post("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isCreated());

        // Validate the TagGroupPermissions in the database
        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeCreate + 1);
        TagGroupPermissions testTagGroupPermissions = tagGroupPermissionsList.get(tagGroupPermissionsList.size() - 1);
        assertThat(testTagGroupPermissions.getTagGroupId()).isEqualTo(DEFAULT_TAG_GROUP_ID);
        assertThat(testTagGroupPermissions.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testTagGroupPermissions.getPermissionType()).isEqualTo(DEFAULT_PERMISSION_TYPE);
    }

    @Test
    @Transactional
    public void createTagGroupPermissionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagGroupPermissionsRepository.findAll().size();

        // Create the TagGroupPermissions with an existing ID
        tagGroupPermissions.setId(1L);
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(tagGroupPermissions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagGroupPermissionsMockMvc.perform(post("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagGroupPermissions in the database
        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTagGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagGroupPermissionsRepository.findAll().size();
        // set the field null
        tagGroupPermissions.setTagGroupId(null);

        // Create the TagGroupPermissions, which fails.
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(tagGroupPermissions);


        restTagGroupPermissionsMockMvc.perform(post("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isBadRequest());

        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagGroupPermissionsRepository.findAll().size();
        // set the field null
        tagGroupPermissions.setGroupId(null);

        // Create the TagGroupPermissions, which fails.
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(tagGroupPermissions);


        restTagGroupPermissionsMockMvc.perform(post("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isBadRequest());

        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPermissionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagGroupPermissionsRepository.findAll().size();
        // set the field null
        tagGroupPermissions.setPermissionType(null);

        // Create the TagGroupPermissions, which fails.
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(tagGroupPermissions);


        restTagGroupPermissionsMockMvc.perform(post("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isBadRequest());

        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagGroupPermissions() throws Exception {
        // Initialize the database
        tagGroupPermissionsRepository.saveAndFlush(tagGroupPermissions);

        // Get all the tagGroupPermissionsList
        restTagGroupPermissionsMockMvc.perform(get("/api/tag-group-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagGroupPermissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].tagGroupId").value(hasItem(DEFAULT_TAG_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].permissionType").value(hasItem(DEFAULT_PERMISSION_TYPE)));
    }

    @Test
    @Transactional
    public void getTagGroupPermissions() throws Exception {
        // Initialize the database
        tagGroupPermissionsRepository.saveAndFlush(tagGroupPermissions);

        // Get the tagGroupPermissions
        restTagGroupPermissionsMockMvc.perform(get("/api/tag-group-permissions/{id}", tagGroupPermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagGroupPermissions.getId().intValue()))
            .andExpect(jsonPath("$.tagGroupId").value(DEFAULT_TAG_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.permissionType").value(DEFAULT_PERMISSION_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingTagGroupPermissions() throws Exception {
        // Get the tagGroupPermissions
        restTagGroupPermissionsMockMvc.perform(get("/api/tag-group-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagGroupPermissions() throws Exception {
        // Initialize the database
        tagGroupPermissionsRepository.saveAndFlush(tagGroupPermissions);

        int databaseSizeBeforeUpdate = tagGroupPermissionsRepository.findAll().size();

        // Update the tagGroupPermissions
        TagGroupPermissions updatedTagGroupPermissions = tagGroupPermissionsRepository.findById(tagGroupPermissions.getId()).get();
        // Disconnect from session so that the updates on updatedTagGroupPermissions are not directly saved in db
        em.detach(updatedTagGroupPermissions);
        updatedTagGroupPermissions
            .tagGroupId(UPDATED_TAG_GROUP_ID)
            .groupId(UPDATED_GROUP_ID)
            .permissionType(UPDATED_PERMISSION_TYPE);
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(updatedTagGroupPermissions);

        restTagGroupPermissionsMockMvc.perform(put("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isOk());

        // Validate the TagGroupPermissions in the database
        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeUpdate);
        TagGroupPermissions testTagGroupPermissions = tagGroupPermissionsList.get(tagGroupPermissionsList.size() - 1);
        assertThat(testTagGroupPermissions.getTagGroupId()).isEqualTo(UPDATED_TAG_GROUP_ID);
        assertThat(testTagGroupPermissions.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testTagGroupPermissions.getPermissionType()).isEqualTo(UPDATED_PERMISSION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTagGroupPermissions() throws Exception {
        int databaseSizeBeforeUpdate = tagGroupPermissionsRepository.findAll().size();

        // Create the TagGroupPermissions
        TagGroupPermissionsDTO tagGroupPermissionsDTO = tagGroupPermissionsMapper.toDto(tagGroupPermissions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagGroupPermissionsMockMvc.perform(put("/api/tag-group-permissions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupPermissionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagGroupPermissions in the database
        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagGroupPermissions() throws Exception {
        // Initialize the database
        tagGroupPermissionsRepository.saveAndFlush(tagGroupPermissions);

        int databaseSizeBeforeDelete = tagGroupPermissionsRepository.findAll().size();

        // Delete the tagGroupPermissions
        restTagGroupPermissionsMockMvc.perform(delete("/api/tag-group-permissions/{id}", tagGroupPermissions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagGroupPermissions> tagGroupPermissionsList = tagGroupPermissionsRepository.findAll();
        assertThat(tagGroupPermissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
