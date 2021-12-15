/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupCategoryNotificationDefaults;
import com.infy.repository.GroupCategoryNotificationDefaultsRepository;
import com.infy.service.GroupCategoryNotificationDefaultsService;
import com.infy.service.dto.GroupCategoryNotificationDefaultsDTO;
import com.infy.service.mapper.GroupCategoryNotificationDefaultsMapper;

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
 * Integration tests for the {@link GroupCategoryNotificationDefaultsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupCategoryNotificationDefaultsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Integer DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL = 2;

    @Autowired
    private GroupCategoryNotificationDefaultsRepository groupCategoryNotificationDefaultsRepository;

    @Autowired
    private GroupCategoryNotificationDefaultsMapper groupCategoryNotificationDefaultsMapper;

    @Autowired
    private GroupCategoryNotificationDefaultsService groupCategoryNotificationDefaultsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupCategoryNotificationDefaultsMockMvc;

    private GroupCategoryNotificationDefaults groupCategoryNotificationDefaults;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupCategoryNotificationDefaults createEntity(EntityManager em) {
        GroupCategoryNotificationDefaults groupCategoryNotificationDefaults = new GroupCategoryNotificationDefaults()
            .groupId(DEFAULT_GROUP_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .notificationLevel(DEFAULT_NOTIFICATION_LEVEL);
        return groupCategoryNotificationDefaults;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupCategoryNotificationDefaults createUpdatedEntity(EntityManager em) {
        GroupCategoryNotificationDefaults groupCategoryNotificationDefaults = new GroupCategoryNotificationDefaults()
            .groupId(UPDATED_GROUP_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL);
        return groupCategoryNotificationDefaults;
    }

    @BeforeEach
    public void initTest() {
        groupCategoryNotificationDefaults = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupCategoryNotificationDefaults() throws Exception {
        int databaseSizeBeforeCreate = groupCategoryNotificationDefaultsRepository.findAll().size();
        // Create the GroupCategoryNotificationDefaults
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);
        restGroupCategoryNotificationDefaultsMockMvc.perform(post("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupCategoryNotificationDefaults in the database
        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeCreate + 1);
        GroupCategoryNotificationDefaults testGroupCategoryNotificationDefaults = groupCategoryNotificationDefaultsList.get(groupCategoryNotificationDefaultsList.size() - 1);
        assertThat(testGroupCategoryNotificationDefaults.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupCategoryNotificationDefaults.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testGroupCategoryNotificationDefaults.getNotificationLevel()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL);
    }

    @Test
    @Transactional
    public void createGroupCategoryNotificationDefaultsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupCategoryNotificationDefaultsRepository.findAll().size();

        // Create the GroupCategoryNotificationDefaults with an existing ID
        groupCategoryNotificationDefaults.setId(1L);
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupCategoryNotificationDefaultsMockMvc.perform(post("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCategoryNotificationDefaults in the database
        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCategoryNotificationDefaultsRepository.findAll().size();
        // set the field null
        groupCategoryNotificationDefaults.setGroupId(null);

        // Create the GroupCategoryNotificationDefaults, which fails.
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);


        restGroupCategoryNotificationDefaultsMockMvc.perform(post("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCategoryNotificationDefaultsRepository.findAll().size();
        // set the field null
        groupCategoryNotificationDefaults.setCategoryId(null);

        // Create the GroupCategoryNotificationDefaults, which fails.
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);


        restGroupCategoryNotificationDefaultsMockMvc.perform(post("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCategoryNotificationDefaultsRepository.findAll().size();
        // set the field null
        groupCategoryNotificationDefaults.setNotificationLevel(null);

        // Create the GroupCategoryNotificationDefaults, which fails.
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);


        restGroupCategoryNotificationDefaultsMockMvc.perform(post("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupCategoryNotificationDefaults() throws Exception {
        // Initialize the database
        groupCategoryNotificationDefaultsRepository.saveAndFlush(groupCategoryNotificationDefaults);

        // Get all the groupCategoryNotificationDefaultsList
        restGroupCategoryNotificationDefaultsMockMvc.perform(get("/api/group-category-notification-defaults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupCategoryNotificationDefaults.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].notificationLevel").value(hasItem(DEFAULT_NOTIFICATION_LEVEL)));
    }

    @Test
    @Transactional
    public void getGroupCategoryNotificationDefaults() throws Exception {
        // Initialize the database
        groupCategoryNotificationDefaultsRepository.saveAndFlush(groupCategoryNotificationDefaults);

        // Get the groupCategoryNotificationDefaults
        restGroupCategoryNotificationDefaultsMockMvc.perform(get("/api/group-category-notification-defaults/{id}", groupCategoryNotificationDefaults.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupCategoryNotificationDefaults.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.notificationLevel").value(DEFAULT_NOTIFICATION_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingGroupCategoryNotificationDefaults() throws Exception {
        // Get the groupCategoryNotificationDefaults
        restGroupCategoryNotificationDefaultsMockMvc.perform(get("/api/group-category-notification-defaults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupCategoryNotificationDefaults() throws Exception {
        // Initialize the database
        groupCategoryNotificationDefaultsRepository.saveAndFlush(groupCategoryNotificationDefaults);

        int databaseSizeBeforeUpdate = groupCategoryNotificationDefaultsRepository.findAll().size();

        // Update the groupCategoryNotificationDefaults
        GroupCategoryNotificationDefaults updatedGroupCategoryNotificationDefaults = groupCategoryNotificationDefaultsRepository.findById(groupCategoryNotificationDefaults.getId()).get();
        // Disconnect from session so that the updates on updatedGroupCategoryNotificationDefaults are not directly saved in db
        em.detach(updatedGroupCategoryNotificationDefaults);
        updatedGroupCategoryNotificationDefaults
            .groupId(UPDATED_GROUP_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL);
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(updatedGroupCategoryNotificationDefaults);

        restGroupCategoryNotificationDefaultsMockMvc.perform(put("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isOk());

        // Validate the GroupCategoryNotificationDefaults in the database
        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeUpdate);
        GroupCategoryNotificationDefaults testGroupCategoryNotificationDefaults = groupCategoryNotificationDefaultsList.get(groupCategoryNotificationDefaultsList.size() - 1);
        assertThat(testGroupCategoryNotificationDefaults.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupCategoryNotificationDefaults.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testGroupCategoryNotificationDefaults.getNotificationLevel()).isEqualTo(UPDATED_NOTIFICATION_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupCategoryNotificationDefaults() throws Exception {
        int databaseSizeBeforeUpdate = groupCategoryNotificationDefaultsRepository.findAll().size();

        // Create the GroupCategoryNotificationDefaults
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO = groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupCategoryNotificationDefaultsMockMvc.perform(put("/api/group-category-notification-defaults").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupCategoryNotificationDefaultsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCategoryNotificationDefaults in the database
        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupCategoryNotificationDefaults() throws Exception {
        // Initialize the database
        groupCategoryNotificationDefaultsRepository.saveAndFlush(groupCategoryNotificationDefaults);

        int databaseSizeBeforeDelete = groupCategoryNotificationDefaultsRepository.findAll().size();

        // Delete the groupCategoryNotificationDefaults
        restGroupCategoryNotificationDefaultsMockMvc.perform(delete("/api/group-category-notification-defaults/{id}", groupCategoryNotificationDefaults.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupCategoryNotificationDefaults> groupCategoryNotificationDefaultsList = groupCategoryNotificationDefaultsRepository.findAll();
        assertThat(groupCategoryNotificationDefaultsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
