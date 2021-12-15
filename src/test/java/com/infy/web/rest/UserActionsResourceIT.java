/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserActions;
import com.infy.repository.UserActionsRepository;
import com.infy.service.UserActionsService;
import com.infy.service.dto.UserActionsDTO;
import com.infy.service.mapper.UserActionsMapper;

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
 * Integration tests for the {@link UserActionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserActionsResourceIT {

    private static final Integer DEFAULT_ACTION_TYPE = 1;
    private static final Integer UPDATED_ACTION_TYPE = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TARGET_TOPIC_ID = 1L;
    private static final Long UPDATED_TARGET_TOPIC_ID = 2L;

    private static final Long DEFAULT_TARGET_POST_ID = 1L;
    private static final Long UPDATED_TARGET_POST_ID = 2L;

    private static final String DEFAULT_TARGET_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTING_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTING_USER_ID = "BBBBBBBBBB";

    @Autowired
    private UserActionsRepository userActionsRepository;

    @Autowired
    private UserActionsMapper userActionsMapper;

    @Autowired
    private UserActionsService userActionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserActionsMockMvc;

    private UserActions userActions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActions createEntity(EntityManager em) {
        UserActions userActions = new UserActions()
            .actionType(DEFAULT_ACTION_TYPE)
            .userId(DEFAULT_USER_ID)
            .targetTopicId(DEFAULT_TARGET_TOPIC_ID)
            .targetPostId(DEFAULT_TARGET_POST_ID)
            .targetUserId(DEFAULT_TARGET_USER_ID)
            .actingUserId(DEFAULT_ACTING_USER_ID);
        return userActions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActions createUpdatedEntity(EntityManager em) {
        UserActions userActions = new UserActions()
            .actionType(UPDATED_ACTION_TYPE)
            .userId(UPDATED_USER_ID)
            .targetTopicId(UPDATED_TARGET_TOPIC_ID)
            .targetPostId(UPDATED_TARGET_POST_ID)
            .targetUserId(UPDATED_TARGET_USER_ID)
            .actingUserId(UPDATED_ACTING_USER_ID);
        return userActions;
    }

    @BeforeEach
    public void initTest() {
        userActions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserActions() throws Exception {
        int databaseSizeBeforeCreate = userActionsRepository.findAll().size();
        // Create the UserActions
        UserActionsDTO userActionsDTO = userActionsMapper.toDto(userActions);
        restUserActionsMockMvc.perform(post("/api/user-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserActions in the database
        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeCreate + 1);
        UserActions testUserActions = userActionsList.get(userActionsList.size() - 1);
        assertThat(testUserActions.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testUserActions.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserActions.getTargetTopicId()).isEqualTo(DEFAULT_TARGET_TOPIC_ID);
        assertThat(testUserActions.getTargetPostId()).isEqualTo(DEFAULT_TARGET_POST_ID);
        assertThat(testUserActions.getTargetUserId()).isEqualTo(DEFAULT_TARGET_USER_ID);
        assertThat(testUserActions.getActingUserId()).isEqualTo(DEFAULT_ACTING_USER_ID);
    }

    @Test
    @Transactional
    public void createUserActionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userActionsRepository.findAll().size();

        // Create the UserActions with an existing ID
        userActions.setId(1L);
        UserActionsDTO userActionsDTO = userActionsMapper.toDto(userActions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserActionsMockMvc.perform(post("/api/user-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserActions in the database
        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userActionsRepository.findAll().size();
        // set the field null
        userActions.setActionType(null);

        // Create the UserActions, which fails.
        UserActionsDTO userActionsDTO = userActionsMapper.toDto(userActions);


        restUserActionsMockMvc.perform(post("/api/user-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userActionsRepository.findAll().size();
        // set the field null
        userActions.setUserId(null);

        // Create the UserActions, which fails.
        UserActionsDTO userActionsDTO = userActionsMapper.toDto(userActions);


        restUserActionsMockMvc.perform(post("/api/user-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserActions() throws Exception {
        // Initialize the database
        userActionsRepository.saveAndFlush(userActions);

        // Get all the userActionsList
        restUserActionsMockMvc.perform(get("/api/user-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActions.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].targetTopicId").value(hasItem(DEFAULT_TARGET_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].targetPostId").value(hasItem(DEFAULT_TARGET_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].targetUserId").value(hasItem(DEFAULT_TARGET_USER_ID)))
            .andExpect(jsonPath("$.[*].actingUserId").value(hasItem(DEFAULT_ACTING_USER_ID)));
    }

    @Test
    @Transactional
    public void getUserActions() throws Exception {
        // Initialize the database
        userActionsRepository.saveAndFlush(userActions);

        // Get the userActions
        restUserActionsMockMvc.perform(get("/api/user-actions/{id}", userActions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userActions.getId().intValue()))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.targetTopicId").value(DEFAULT_TARGET_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.targetPostId").value(DEFAULT_TARGET_POST_ID.intValue()))
            .andExpect(jsonPath("$.targetUserId").value(DEFAULT_TARGET_USER_ID))
            .andExpect(jsonPath("$.actingUserId").value(DEFAULT_ACTING_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingUserActions() throws Exception {
        // Get the userActions
        restUserActionsMockMvc.perform(get("/api/user-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserActions() throws Exception {
        // Initialize the database
        userActionsRepository.saveAndFlush(userActions);

        int databaseSizeBeforeUpdate = userActionsRepository.findAll().size();

        // Update the userActions
        UserActions updatedUserActions = userActionsRepository.findById(userActions.getId()).get();
        // Disconnect from session so that the updates on updatedUserActions are not directly saved in db
        em.detach(updatedUserActions);
        updatedUserActions
            .actionType(UPDATED_ACTION_TYPE)
            .userId(UPDATED_USER_ID)
            .targetTopicId(UPDATED_TARGET_TOPIC_ID)
            .targetPostId(UPDATED_TARGET_POST_ID)
            .targetUserId(UPDATED_TARGET_USER_ID)
            .actingUserId(UPDATED_ACTING_USER_ID);
        UserActionsDTO userActionsDTO = userActionsMapper.toDto(updatedUserActions);

        restUserActionsMockMvc.perform(put("/api/user-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActionsDTO)))
            .andExpect(status().isOk());

        // Validate the UserActions in the database
        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeUpdate);
        UserActions testUserActions = userActionsList.get(userActionsList.size() - 1);
        assertThat(testUserActions.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testUserActions.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserActions.getTargetTopicId()).isEqualTo(UPDATED_TARGET_TOPIC_ID);
        assertThat(testUserActions.getTargetPostId()).isEqualTo(UPDATED_TARGET_POST_ID);
        assertThat(testUserActions.getTargetUserId()).isEqualTo(UPDATED_TARGET_USER_ID);
        assertThat(testUserActions.getActingUserId()).isEqualTo(UPDATED_ACTING_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserActions() throws Exception {
        int databaseSizeBeforeUpdate = userActionsRepository.findAll().size();

        // Create the UserActions
        UserActionsDTO userActionsDTO = userActionsMapper.toDto(userActions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserActionsMockMvc.perform(put("/api/user-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserActions in the database
        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserActions() throws Exception {
        // Initialize the database
        userActionsRepository.saveAndFlush(userActions);

        int databaseSizeBeforeDelete = userActionsRepository.findAll().size();

        // Delete the userActions
        restUserActionsMockMvc.perform(delete("/api/user-actions/{id}", userActions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserActions> userActionsList = userActionsRepository.findAll();
        assertThat(userActionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
