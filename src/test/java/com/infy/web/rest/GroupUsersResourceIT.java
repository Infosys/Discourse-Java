/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupUsers;
import com.infy.repository.GroupUsersRepository;
import com.infy.service.GroupUsersService;
import com.infy.service.dto.GroupUsersDTO;
import com.infy.service.mapper.GroupUsersMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GroupUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupUsersResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OWNER = false;
    private static final Boolean UPDATED_OWNER = true;

    private static final Integer DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL = 2;

    private static final Instant DEFAULT_FIRST_UNREAD_PM_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_UNREAD_PM_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GroupUsersRepository groupUsersRepository;

    @Autowired
    private GroupUsersMapper groupUsersMapper;

    @Autowired
    private GroupUsersService groupUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupUsersMockMvc;

    private GroupUsers groupUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupUsers createEntity(EntityManager em) {
        GroupUsers groupUsers = new GroupUsers()
            .groupId(DEFAULT_GROUP_ID)
            .userId(DEFAULT_USER_ID)
            .owner(DEFAULT_OWNER)
            .notificationLevel(DEFAULT_NOTIFICATION_LEVEL)
            .firstUnreadPmAt(DEFAULT_FIRST_UNREAD_PM_AT);
        return groupUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupUsers createUpdatedEntity(EntityManager em) {
        GroupUsers groupUsers = new GroupUsers()
            .groupId(UPDATED_GROUP_ID)
            .userId(UPDATED_USER_ID)
            .owner(UPDATED_OWNER)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL)
            .firstUnreadPmAt(UPDATED_FIRST_UNREAD_PM_AT);
        return groupUsers;
    }

    @BeforeEach
    public void initTest() {
        groupUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupUsers() throws Exception {
        int databaseSizeBeforeCreate = groupUsersRepository.findAll().size();
        // Create the GroupUsers
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);
        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupUsers in the database
        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeCreate + 1);
        GroupUsers testGroupUsers = groupUsersList.get(groupUsersList.size() - 1);
        assertThat(testGroupUsers.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testGroupUsers.isOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testGroupUsers.getNotificationLevel()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL);
        assertThat(testGroupUsers.getFirstUnreadPmAt()).isEqualTo(DEFAULT_FIRST_UNREAD_PM_AT);
    }

    @Test
    @Transactional
    public void createGroupUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupUsersRepository.findAll().size();

        // Create the GroupUsers with an existing ID
        groupUsers.setId(1L);
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupUsers in the database
        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupUsersRepository.findAll().size();
        // set the field null
        groupUsers.setGroupId(null);

        // Create the GroupUsers, which fails.
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);


        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupUsersRepository.findAll().size();
        // set the field null
        groupUsers.setUserId(null);

        // Create the GroupUsers, which fails.
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);


        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupUsersRepository.findAll().size();
        // set the field null
        groupUsers.setOwner(null);

        // Create the GroupUsers, which fails.
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);


        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupUsersRepository.findAll().size();
        // set the field null
        groupUsers.setNotificationLevel(null);

        // Create the GroupUsers, which fails.
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);


        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstUnreadPmAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupUsersRepository.findAll().size();
        // set the field null
        groupUsers.setFirstUnreadPmAt(null);

        // Create the GroupUsers, which fails.
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);


        restGroupUsersMockMvc.perform(post("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupUsers() throws Exception {
        // Initialize the database
        groupUsersRepository.saveAndFlush(groupUsers);

        // Get all the groupUsersList
        restGroupUsersMockMvc.perform(get("/api/group-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.booleanValue())))
            .andExpect(jsonPath("$.[*].notificationLevel").value(hasItem(DEFAULT_NOTIFICATION_LEVEL)))
            .andExpect(jsonPath("$.[*].firstUnreadPmAt").value(hasItem(DEFAULT_FIRST_UNREAD_PM_AT.toString())));
    }

    @Test
    @Transactional
    public void getGroupUsers() throws Exception {
        // Initialize the database
        groupUsersRepository.saveAndFlush(groupUsers);

        // Get the groupUsers
        restGroupUsersMockMvc.perform(get("/api/group-users/{id}", groupUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupUsers.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.booleanValue()))
            .andExpect(jsonPath("$.notificationLevel").value(DEFAULT_NOTIFICATION_LEVEL))
            .andExpect(jsonPath("$.firstUnreadPmAt").value(DEFAULT_FIRST_UNREAD_PM_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupUsers() throws Exception {
        // Get the groupUsers
        restGroupUsersMockMvc.perform(get("/api/group-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupUsers() throws Exception {
        // Initialize the database
        groupUsersRepository.saveAndFlush(groupUsers);

        int databaseSizeBeforeUpdate = groupUsersRepository.findAll().size();

        // Update the groupUsers
        GroupUsers updatedGroupUsers = groupUsersRepository.findById(groupUsers.getId()).get();
        // Disconnect from session so that the updates on updatedGroupUsers are not directly saved in db
        em.detach(updatedGroupUsers);
        updatedGroupUsers
            .groupId(UPDATED_GROUP_ID)
            .userId(UPDATED_USER_ID)
            .owner(UPDATED_OWNER)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL)
            .firstUnreadPmAt(UPDATED_FIRST_UNREAD_PM_AT);
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(updatedGroupUsers);

        restGroupUsersMockMvc.perform(put("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isOk());

        // Validate the GroupUsers in the database
        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeUpdate);
        GroupUsers testGroupUsers = groupUsersList.get(groupUsersList.size() - 1);
        assertThat(testGroupUsers.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testGroupUsers.isOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testGroupUsers.getNotificationLevel()).isEqualTo(UPDATED_NOTIFICATION_LEVEL);
        assertThat(testGroupUsers.getFirstUnreadPmAt()).isEqualTo(UPDATED_FIRST_UNREAD_PM_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupUsers() throws Exception {
        int databaseSizeBeforeUpdate = groupUsersRepository.findAll().size();

        // Create the GroupUsers
        GroupUsersDTO groupUsersDTO = groupUsersMapper.toDto(groupUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupUsersMockMvc.perform(put("/api/group-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupUsers in the database
        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupUsers() throws Exception {
        // Initialize the database
        groupUsersRepository.saveAndFlush(groupUsers);

        int databaseSizeBeforeDelete = groupUsersRepository.findAll().size();

        // Delete the groupUsers
        restGroupUsersMockMvc.perform(delete("/api/group-users/{id}", groupUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupUsers> groupUsersList = groupUsersRepository.findAll();
        assertThat(groupUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
