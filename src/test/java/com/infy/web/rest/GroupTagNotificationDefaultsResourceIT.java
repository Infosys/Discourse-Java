/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupTagNotificationDefaults;
import com.infy.repository.GroupTagNotificationDefaultsRepository;
import com.infy.service.GroupTagNotificationDefaultsService;
import com.infy.service.dto.GroupTagNotificationDefaultsDTO;
import com.infy.service.mapper.GroupTagNotificationDefaultsMapper;

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
 * Integration tests for the {@link GroupTagNotificationDefaultsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupTagNotificationDefaultsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    private static final Integer DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL = 2;

    @Autowired
    private GroupTagNotificationDefaultsRepository groupTagNotificationDefaultsRepository;

    @Autowired
    private GroupTagNotificationDefaultsMapper groupTagNotificationDefaultsMapper;

    @Autowired
    private GroupTagNotificationDefaultsService groupTagNotificationDefaultsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupTagNotificationDefaultsMockMvc;

    private GroupTagNotificationDefaults groupTagNotificationDefaults;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupTagNotificationDefaults createEntity(EntityManager em) {
        GroupTagNotificationDefaults groupTagNotificationDefaults = new GroupTagNotificationDefaults()
            .groupId(DEFAULT_GROUP_ID)
            .tagId(DEFAULT_TAG_ID)
            .notificationLevel(DEFAULT_NOTIFICATION_LEVEL);
        return groupTagNotificationDefaults;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupTagNotificationDefaults createUpdatedEntity(EntityManager em) {
        GroupTagNotificationDefaults groupTagNotificationDefaults = new GroupTagNotificationDefaults()
            .groupId(UPDATED_GROUP_ID)
            .tagId(UPDATED_TAG_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL);
        return groupTagNotificationDefaults;
    }

    @BeforeEach
    public void initTest() {
        groupTagNotificationDefaults = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupTagNotificationDefaults() throws Exception {
        int databaseSizeBeforeCreate = groupTagNotificationDefaultsRepository.findAll().size();
        // Create the GroupTagNotificationDefaults
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);
        restGroupTagNotificationDefaultsMockMvc.perform(post("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupTagNotificationDefaults in the database
        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeCreate + 1);
        GroupTagNotificationDefaults testGroupTagNotificationDefaults = groupTagNotificationDefaultsList.get(groupTagNotificationDefaultsList.size() - 1);
        assertThat(testGroupTagNotificationDefaults.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupTagNotificationDefaults.getTagId()).isEqualTo(DEFAULT_TAG_ID);
        assertThat(testGroupTagNotificationDefaults.getNotificationLevel()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL);
    }

    @Test
    @Transactional
    public void createGroupTagNotificationDefaultsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupTagNotificationDefaultsRepository.findAll().size();

        // Create the GroupTagNotificationDefaults with an existing ID
        groupTagNotificationDefaults.setId(1L);
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupTagNotificationDefaultsMockMvc.perform(post("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupTagNotificationDefaults in the database
        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupTagNotificationDefaultsRepository.findAll().size();
        // set the field null
        groupTagNotificationDefaults.setGroupId(null);

        // Create the GroupTagNotificationDefaults, which fails.
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);


        restGroupTagNotificationDefaultsMockMvc.perform(post("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupTagNotificationDefaultsRepository.findAll().size();
        // set the field null
        groupTagNotificationDefaults.setTagId(null);

        // Create the GroupTagNotificationDefaults, which fails.
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);


        restGroupTagNotificationDefaultsMockMvc.perform(post("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupTagNotificationDefaultsRepository.findAll().size();
        // set the field null
        groupTagNotificationDefaults.setNotificationLevel(null);

        // Create the GroupTagNotificationDefaults, which fails.
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);


        restGroupTagNotificationDefaultsMockMvc.perform(post("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupTagNotificationDefaults() throws Exception {
        // Initialize the database
        groupTagNotificationDefaultsRepository.saveAndFlush(groupTagNotificationDefaults);

        // Get all the groupTagNotificationDefaultsList
        restGroupTagNotificationDefaultsMockMvc.perform(get("/api/group-tag-notification-defaults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupTagNotificationDefaults.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())))
            .andExpect(jsonPath("$.[*].notificationLevel").value(hasItem(DEFAULT_NOTIFICATION_LEVEL)));
    }

    @Test
    @Transactional
    public void getGroupTagNotificationDefaults() throws Exception {
        // Initialize the database
        groupTagNotificationDefaultsRepository.saveAndFlush(groupTagNotificationDefaults);

        // Get the groupTagNotificationDefaults
        restGroupTagNotificationDefaultsMockMvc.perform(get("/api/group-tag-notification-defaults/{id}", groupTagNotificationDefaults.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupTagNotificationDefaults.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()))
            .andExpect(jsonPath("$.notificationLevel").value(DEFAULT_NOTIFICATION_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingGroupTagNotificationDefaults() throws Exception {
        // Get the groupTagNotificationDefaults
        restGroupTagNotificationDefaultsMockMvc.perform(get("/api/group-tag-notification-defaults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupTagNotificationDefaults() throws Exception {
        // Initialize the database
        groupTagNotificationDefaultsRepository.saveAndFlush(groupTagNotificationDefaults);

        int databaseSizeBeforeUpdate = groupTagNotificationDefaultsRepository.findAll().size();

        // Update the groupTagNotificationDefaults
        GroupTagNotificationDefaults updatedGroupTagNotificationDefaults = groupTagNotificationDefaultsRepository.findById(groupTagNotificationDefaults.getId()).get();
        // Disconnect from session so that the updates on updatedGroupTagNotificationDefaults are not directly saved in db
        em.detach(updatedGroupTagNotificationDefaults);
        updatedGroupTagNotificationDefaults
            .groupId(UPDATED_GROUP_ID)
            .tagId(UPDATED_TAG_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL);
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(updatedGroupTagNotificationDefaults);

        restGroupTagNotificationDefaultsMockMvc.perform(put("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isOk());

        // Validate the GroupTagNotificationDefaults in the database
        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeUpdate);
        GroupTagNotificationDefaults testGroupTagNotificationDefaults = groupTagNotificationDefaultsList.get(groupTagNotificationDefaultsList.size() - 1);
        assertThat(testGroupTagNotificationDefaults.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupTagNotificationDefaults.getTagId()).isEqualTo(UPDATED_TAG_ID);
        assertThat(testGroupTagNotificationDefaults.getNotificationLevel()).isEqualTo(UPDATED_NOTIFICATION_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupTagNotificationDefaults() throws Exception {
        int databaseSizeBeforeUpdate = groupTagNotificationDefaultsRepository.findAll().size();

        // Create the GroupTagNotificationDefaults
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupTagNotificationDefaultsMockMvc.perform(put("/api/group-tag-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTagNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupTagNotificationDefaults in the database
        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupTagNotificationDefaults() throws Exception {
        // Initialize the database
        groupTagNotificationDefaultsRepository.saveAndFlush(groupTagNotificationDefaults);

        int databaseSizeBeforeDelete = groupTagNotificationDefaultsRepository.findAll().size();

        // Delete the groupTagNotificationDefaults
        restGroupTagNotificationDefaultsMockMvc.perform(delete("/api/group-tag-notification-defaults/{id}", groupTagNotificationDefaults.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupTagNotificationDefaults> groupTagNotificationDefaultsList = groupTagNotificationDefaultsRepository.findAll();
        assertThat(groupTagNotificationDefaultsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
