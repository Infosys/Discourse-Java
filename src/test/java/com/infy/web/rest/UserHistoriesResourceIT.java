/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserHistories;
import com.infy.repository.UserHistoriesRepository;
import com.infy.service.UserHistoriesService;
import com.infy.service.dto.UserHistoriesDTO;
import com.infy.service.mapper.UserHistoriesMapper;

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
 * Integration tests for the {@link UserHistoriesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserHistoriesResourceIT {

    private static final Integer DEFAULT_ACTION = 1;
    private static final Integer UPDATED_ACTION = 2;

    private static final String DEFAULT_ACTING_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTING_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUS_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Boolean DEFAULT_ADMIN_ONLY = false;
    private static final Boolean UPDATED_ADMIN_ONLY = true;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_CUSTOM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    @Autowired
    private UserHistoriesRepository userHistoriesRepository;

    @Autowired
    private UserHistoriesMapper userHistoriesMapper;

    @Autowired
    private UserHistoriesService userHistoriesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserHistoriesMockMvc;

    private UserHistories userHistories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserHistories createEntity(EntityManager em) {
        UserHistories userHistories = new UserHistories()
            .action(DEFAULT_ACTION)
            .actingUserId(DEFAULT_ACTING_USER_ID)
            .targetUserId(DEFAULT_TARGET_USER_ID)
            .details(DEFAULT_DETAILS)
            .context(DEFAULT_CONTEXT)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .email(DEFAULT_EMAIL)
            .subject(DEFAULT_SUBJECT)
            .previousValue(DEFAULT_PREVIOUS_VALUE)
            .newValue(DEFAULT_NEW_VALUE)
            .topicId(DEFAULT_TOPIC_ID)
            .adminOnly(DEFAULT_ADMIN_ONLY)
            .postId(DEFAULT_POST_ID)
            .customType(DEFAULT_CUSTOM_TYPE)
            .categoryId(DEFAULT_CATEGORY_ID);
        return userHistories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserHistories createUpdatedEntity(EntityManager em) {
        UserHistories userHistories = new UserHistories()
            .action(UPDATED_ACTION)
            .actingUserId(UPDATED_ACTING_USER_ID)
            .targetUserId(UPDATED_TARGET_USER_ID)
            .details(UPDATED_DETAILS)
            .context(UPDATED_CONTEXT)
            .ipAddress(UPDATED_IP_ADDRESS)
            .email(UPDATED_EMAIL)
            .subject(UPDATED_SUBJECT)
            .previousValue(UPDATED_PREVIOUS_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .topicId(UPDATED_TOPIC_ID)
            .adminOnly(UPDATED_ADMIN_ONLY)
            .postId(UPDATED_POST_ID)
            .customType(UPDATED_CUSTOM_TYPE)
            .categoryId(UPDATED_CATEGORY_ID);
        return userHistories;
    }

    @BeforeEach
    public void initTest() {
        userHistories = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserHistories() throws Exception {
        int databaseSizeBeforeCreate = userHistoriesRepository.findAll().size();
        // Create the UserHistories
        UserHistoriesDTO userHistoriesDTO = userHistoriesMapper.toDto(userHistories);
        restUserHistoriesMockMvc.perform(post("/api/user-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userHistoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserHistories in the database
        List<UserHistories> userHistoriesList = userHistoriesRepository.findAll();
        assertThat(userHistoriesList).hasSize(databaseSizeBeforeCreate + 1);
        UserHistories testUserHistories = userHistoriesList.get(userHistoriesList.size() - 1);
        assertThat(testUserHistories.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testUserHistories.getActingUserId()).isEqualTo(DEFAULT_ACTING_USER_ID);
        assertThat(testUserHistories.getTargetUserId()).isEqualTo(DEFAULT_TARGET_USER_ID);
        assertThat(testUserHistories.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testUserHistories.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testUserHistories.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testUserHistories.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserHistories.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testUserHistories.getPreviousValue()).isEqualTo(DEFAULT_PREVIOUS_VALUE);
        assertThat(testUserHistories.getNewValue()).isEqualTo(DEFAULT_NEW_VALUE);
        assertThat(testUserHistories.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testUserHistories.isAdminOnly()).isEqualTo(DEFAULT_ADMIN_ONLY);
        assertThat(testUserHistories.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testUserHistories.getCustomType()).isEqualTo(DEFAULT_CUSTOM_TYPE);
        assertThat(testUserHistories.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void createUserHistoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userHistoriesRepository.findAll().size();

        // Create the UserHistories with an existing ID
        userHistories.setId(1L);
        UserHistoriesDTO userHistoriesDTO = userHistoriesMapper.toDto(userHistories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserHistoriesMockMvc.perform(post("/api/user-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserHistories in the database
        List<UserHistories> userHistoriesList = userHistoriesRepository.findAll();
        assertThat(userHistoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userHistoriesRepository.findAll().size();
        // set the field null
        userHistories.setAction(null);

        // Create the UserHistories, which fails.
        UserHistoriesDTO userHistoriesDTO = userHistoriesMapper.toDto(userHistories);


        restUserHistoriesMockMvc.perform(post("/api/user-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<UserHistories> userHistoriesList = userHistoriesRepository.findAll();
        assertThat(userHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserHistories() throws Exception {
        // Initialize the database
        userHistoriesRepository.saveAndFlush(userHistories);

        // Get all the userHistoriesList
        restUserHistoriesMockMvc.perform(get("/api/user-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userHistories.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].actingUserId").value(hasItem(DEFAULT_ACTING_USER_ID)))
            .andExpect(jsonPath("$.[*].targetUserId").value(hasItem(DEFAULT_TARGET_USER_ID)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].previousValue").value(hasItem(DEFAULT_PREVIOUS_VALUE)))
            .andExpect(jsonPath("$.[*].newValue").value(hasItem(DEFAULT_NEW_VALUE)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].adminOnly").value(hasItem(DEFAULT_ADMIN_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].customType").value(hasItem(DEFAULT_CUSTOM_TYPE)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserHistories() throws Exception {
        // Initialize the database
        userHistoriesRepository.saveAndFlush(userHistories);

        // Get the userHistories
        restUserHistoriesMockMvc.perform(get("/api/user-histories/{id}", userHistories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userHistories.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.actingUserId").value(DEFAULT_ACTING_USER_ID))
            .andExpect(jsonPath("$.targetUserId").value(DEFAULT_TARGET_USER_ID))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.previousValue").value(DEFAULT_PREVIOUS_VALUE))
            .andExpect(jsonPath("$.newValue").value(DEFAULT_NEW_VALUE))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.adminOnly").value(DEFAULT_ADMIN_ONLY.booleanValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.customType").value(DEFAULT_CUSTOM_TYPE))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserHistories() throws Exception {
        // Get the userHistories
        restUserHistoriesMockMvc.perform(get("/api/user-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserHistories() throws Exception {
        // Initialize the database
        userHistoriesRepository.saveAndFlush(userHistories);

        int databaseSizeBeforeUpdate = userHistoriesRepository.findAll().size();

        // Update the userHistories
        UserHistories updatedUserHistories = userHistoriesRepository.findById(userHistories.getId()).get();
        // Disconnect from session so that the updates on updatedUserHistories are not directly saved in db
        em.detach(updatedUserHistories);
        updatedUserHistories
            .action(UPDATED_ACTION)
            .actingUserId(UPDATED_ACTING_USER_ID)
            .targetUserId(UPDATED_TARGET_USER_ID)
            .details(UPDATED_DETAILS)
            .context(UPDATED_CONTEXT)
            .ipAddress(UPDATED_IP_ADDRESS)
            .email(UPDATED_EMAIL)
            .subject(UPDATED_SUBJECT)
            .previousValue(UPDATED_PREVIOUS_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .topicId(UPDATED_TOPIC_ID)
            .adminOnly(UPDATED_ADMIN_ONLY)
            .postId(UPDATED_POST_ID)
            .customType(UPDATED_CUSTOM_TYPE)
            .categoryId(UPDATED_CATEGORY_ID);
        UserHistoriesDTO userHistoriesDTO = userHistoriesMapper.toDto(updatedUserHistories);

        restUserHistoriesMockMvc.perform(put("/api/user-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userHistoriesDTO)))
            .andExpect(status().isOk());

        // Validate the UserHistories in the database
        List<UserHistories> userHistoriesList = userHistoriesRepository.findAll();
        assertThat(userHistoriesList).hasSize(databaseSizeBeforeUpdate);
        UserHistories testUserHistories = userHistoriesList.get(userHistoriesList.size() - 1);
        assertThat(testUserHistories.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testUserHistories.getActingUserId()).isEqualTo(UPDATED_ACTING_USER_ID);
        assertThat(testUserHistories.getTargetUserId()).isEqualTo(UPDATED_TARGET_USER_ID);
        assertThat(testUserHistories.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testUserHistories.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testUserHistories.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testUserHistories.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserHistories.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testUserHistories.getPreviousValue()).isEqualTo(UPDATED_PREVIOUS_VALUE);
        assertThat(testUserHistories.getNewValue()).isEqualTo(UPDATED_NEW_VALUE);
        assertThat(testUserHistories.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testUserHistories.isAdminOnly()).isEqualTo(UPDATED_ADMIN_ONLY);
        assertThat(testUserHistories.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testUserHistories.getCustomType()).isEqualTo(UPDATED_CUSTOM_TYPE);
        assertThat(testUserHistories.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserHistories() throws Exception {
        int databaseSizeBeforeUpdate = userHistoriesRepository.findAll().size();

        // Create the UserHistories
        UserHistoriesDTO userHistoriesDTO = userHistoriesMapper.toDto(userHistories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserHistoriesMockMvc.perform(put("/api/user-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserHistories in the database
        List<UserHistories> userHistoriesList = userHistoriesRepository.findAll();
        assertThat(userHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserHistories() throws Exception {
        // Initialize the database
        userHistoriesRepository.saveAndFlush(userHistories);

        int databaseSizeBeforeDelete = userHistoriesRepository.findAll().size();

        // Delete the userHistories
        restUserHistoriesMockMvc.perform(delete("/api/user-histories/{id}", userHistories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserHistories> userHistoriesList = userHistoriesRepository.findAll();
        assertThat(userHistoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
