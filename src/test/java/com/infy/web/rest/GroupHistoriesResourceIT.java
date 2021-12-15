/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupHistories;
import com.infy.repository.GroupHistoriesRepository;
import com.infy.service.GroupHistoriesService;
import com.infy.service.dto.GroupHistoriesDTO;
import com.infy.service.mapper.GroupHistoriesMapper;

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
 * Integration tests for the {@link GroupHistoriesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupHistoriesResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final String DEFAULT_ACTING_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTING_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTION = 1;
    private static final Integer UPDATED_ACTION = 2;

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PREV_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_VALUE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GroupHistoriesRepository groupHistoriesRepository;

    @Autowired
    private GroupHistoriesMapper groupHistoriesMapper;

    @Autowired
    private GroupHistoriesService groupHistoriesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupHistoriesMockMvc;

    private GroupHistories groupHistories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupHistories createEntity(EntityManager em) {
        GroupHistories groupHistories = new GroupHistories()
            .groupId(DEFAULT_GROUP_ID)
            .actingUserId(DEFAULT_ACTING_USER_ID)
            .targetUserId(DEFAULT_TARGET_USER_ID)
            .action(DEFAULT_ACTION)
            .subject(DEFAULT_SUBJECT)
            .prevValue(DEFAULT_PREV_VALUE)
            .newValue(DEFAULT_NEW_VALUE)
            .updatedAt(DEFAULT_UPDATED_AT);
        return groupHistories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupHistories createUpdatedEntity(EntityManager em) {
        GroupHistories groupHistories = new GroupHistories()
            .groupId(UPDATED_GROUP_ID)
            .actingUserId(UPDATED_ACTING_USER_ID)
            .targetUserId(UPDATED_TARGET_USER_ID)
            .action(UPDATED_ACTION)
            .subject(UPDATED_SUBJECT)
            .prevValue(UPDATED_PREV_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .updatedAt(UPDATED_UPDATED_AT);
        return groupHistories;
    }

    @BeforeEach
    public void initTest() {
        groupHistories = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupHistories() throws Exception {
        int databaseSizeBeforeCreate = groupHistoriesRepository.findAll().size();
        // Create the GroupHistories
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);
        restGroupHistoriesMockMvc.perform(post("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupHistories in the database
        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeCreate + 1);
        GroupHistories testGroupHistories = groupHistoriesList.get(groupHistoriesList.size() - 1);
        assertThat(testGroupHistories.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupHistories.getActingUserId()).isEqualTo(DEFAULT_ACTING_USER_ID);
        assertThat(testGroupHistories.getTargetUserId()).isEqualTo(DEFAULT_TARGET_USER_ID);
        assertThat(testGroupHistories.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testGroupHistories.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testGroupHistories.getPrevValue()).isEqualTo(DEFAULT_PREV_VALUE);
        assertThat(testGroupHistories.getNewValue()).isEqualTo(DEFAULT_NEW_VALUE);
        assertThat(testGroupHistories.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createGroupHistoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupHistoriesRepository.findAll().size();

        // Create the GroupHistories with an existing ID
        groupHistories.setId(1L);
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupHistoriesMockMvc.perform(post("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupHistories in the database
        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupHistoriesRepository.findAll().size();
        // set the field null
        groupHistories.setGroupId(null);

        // Create the GroupHistories, which fails.
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);


        restGroupHistoriesMockMvc.perform(post("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActingUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupHistoriesRepository.findAll().size();
        // set the field null
        groupHistories.setActingUserId(null);

        // Create the GroupHistories, which fails.
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);


        restGroupHistoriesMockMvc.perform(post("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupHistoriesRepository.findAll().size();
        // set the field null
        groupHistories.setAction(null);

        // Create the GroupHistories, which fails.
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);


        restGroupHistoriesMockMvc.perform(post("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupHistoriesRepository.findAll().size();
        // set the field null
        groupHistories.setUpdatedAt(null);

        // Create the GroupHistories, which fails.
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);


        restGroupHistoriesMockMvc.perform(post("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupHistories() throws Exception {
        // Initialize the database
        groupHistoriesRepository.saveAndFlush(groupHistories);

        // Get all the groupHistoriesList
        restGroupHistoriesMockMvc.perform(get("/api/group-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupHistories.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].actingUserId").value(hasItem(DEFAULT_ACTING_USER_ID)))
            .andExpect(jsonPath("$.[*].targetUserId").value(hasItem(DEFAULT_TARGET_USER_ID)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].prevValue").value(hasItem(DEFAULT_PREV_VALUE)))
            .andExpect(jsonPath("$.[*].newValue").value(hasItem(DEFAULT_NEW_VALUE)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getGroupHistories() throws Exception {
        // Initialize the database
        groupHistoriesRepository.saveAndFlush(groupHistories);

        // Get the groupHistories
        restGroupHistoriesMockMvc.perform(get("/api/group-histories/{id}", groupHistories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupHistories.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.actingUserId").value(DEFAULT_ACTING_USER_ID))
            .andExpect(jsonPath("$.targetUserId").value(DEFAULT_TARGET_USER_ID))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.prevValue").value(DEFAULT_PREV_VALUE))
            .andExpect(jsonPath("$.newValue").value(DEFAULT_NEW_VALUE))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupHistories() throws Exception {
        // Get the groupHistories
        restGroupHistoriesMockMvc.perform(get("/api/group-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupHistories() throws Exception {
        // Initialize the database
        groupHistoriesRepository.saveAndFlush(groupHistories);

        int databaseSizeBeforeUpdate = groupHistoriesRepository.findAll().size();

        // Update the groupHistories
        GroupHistories updatedGroupHistories = groupHistoriesRepository.findById(groupHistories.getId()).get();
        // Disconnect from session so that the updates on updatedGroupHistories are not directly saved in db
        em.detach(updatedGroupHistories);
        updatedGroupHistories
            .groupId(UPDATED_GROUP_ID)
            .actingUserId(UPDATED_ACTING_USER_ID)
            .targetUserId(UPDATED_TARGET_USER_ID)
            .action(UPDATED_ACTION)
            .subject(UPDATED_SUBJECT)
            .prevValue(UPDATED_PREV_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .updatedAt(UPDATED_UPDATED_AT);
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(updatedGroupHistories);

        restGroupHistoriesMockMvc.perform(put("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isOk());

        // Validate the GroupHistories in the database
        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeUpdate);
        GroupHistories testGroupHistories = groupHistoriesList.get(groupHistoriesList.size() - 1);
        assertThat(testGroupHistories.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupHistories.getActingUserId()).isEqualTo(UPDATED_ACTING_USER_ID);
        assertThat(testGroupHistories.getTargetUserId()).isEqualTo(UPDATED_TARGET_USER_ID);
        assertThat(testGroupHistories.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testGroupHistories.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testGroupHistories.getPrevValue()).isEqualTo(UPDATED_PREV_VALUE);
        assertThat(testGroupHistories.getNewValue()).isEqualTo(UPDATED_NEW_VALUE);
        assertThat(testGroupHistories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupHistories() throws Exception {
        int databaseSizeBeforeUpdate = groupHistoriesRepository.findAll().size();

        // Create the GroupHistories
        GroupHistoriesDTO groupHistoriesDTO = groupHistoriesMapper.toDto(groupHistories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupHistoriesMockMvc.perform(put("/api/group-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupHistories in the database
        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupHistories() throws Exception {
        // Initialize the database
        groupHistoriesRepository.saveAndFlush(groupHistories);

        int databaseSizeBeforeDelete = groupHistoriesRepository.findAll().size();

        // Delete the groupHistories
        restGroupHistoriesMockMvc.perform(delete("/api/group-histories/{id}", groupHistories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupHistories> groupHistoriesList = groupHistoriesRepository.findAll();
        assertThat(groupHistoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
