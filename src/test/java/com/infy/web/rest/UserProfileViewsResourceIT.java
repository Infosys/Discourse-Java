/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserProfileViews;
import com.infy.repository.UserProfileViewsRepository;
import com.infy.service.UserProfileViewsService;
import com.infy.service.dto.UserProfileViewsDTO;
import com.infy.service.mapper.UserProfileViewsMapper;

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
 * Integration tests for the {@link UserProfileViewsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserProfileViewsResourceIT {

    private static final Long DEFAULT_USER_PROFILE_ID = 1L;
    private static final Long UPDATED_USER_PROFILE_ID = 2L;

    private static final Instant DEFAULT_VIEWED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VIEWED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private UserProfileViewsRepository userProfileViewsRepository;

    @Autowired
    private UserProfileViewsMapper userProfileViewsMapper;

    @Autowired
    private UserProfileViewsService userProfileViewsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfileViewsMockMvc;

    private UserProfileViews userProfileViews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfileViews createEntity(EntityManager em) {
        UserProfileViews userProfileViews = new UserProfileViews()
            .userProfileId(DEFAULT_USER_PROFILE_ID)
            .viewedAt(DEFAULT_VIEWED_AT)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userId(DEFAULT_USER_ID);
        return userProfileViews;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfileViews createUpdatedEntity(EntityManager em) {
        UserProfileViews userProfileViews = new UserProfileViews()
            .userProfileId(UPDATED_USER_PROFILE_ID)
            .viewedAt(UPDATED_VIEWED_AT)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userId(UPDATED_USER_ID);
        return userProfileViews;
    }

    @BeforeEach
    public void initTest() {
        userProfileViews = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfileViews() throws Exception {
        int databaseSizeBeforeCreate = userProfileViewsRepository.findAll().size();
        // Create the UserProfileViews
        UserProfileViewsDTO userProfileViewsDTO = userProfileViewsMapper.toDto(userProfileViews);
        restUserProfileViewsMockMvc.perform(post("/api/user-profile-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileViewsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfileViews in the database
        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfileViews testUserProfileViews = userProfileViewsList.get(userProfileViewsList.size() - 1);
        assertThat(testUserProfileViews.getUserProfileId()).isEqualTo(DEFAULT_USER_PROFILE_ID);
        assertThat(testUserProfileViews.getViewedAt()).isEqualTo(DEFAULT_VIEWED_AT);
        assertThat(testUserProfileViews.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testUserProfileViews.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createUserProfileViewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileViewsRepository.findAll().size();

        // Create the UserProfileViews with an existing ID
        userProfileViews.setId(1L);
        UserProfileViewsDTO userProfileViewsDTO = userProfileViewsMapper.toDto(userProfileViews);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileViewsMockMvc.perform(post("/api/user-profile-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileViewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfileViews in the database
        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserProfileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileViewsRepository.findAll().size();
        // set the field null
        userProfileViews.setUserProfileId(null);

        // Create the UserProfileViews, which fails.
        UserProfileViewsDTO userProfileViewsDTO = userProfileViewsMapper.toDto(userProfileViews);


        restUserProfileViewsMockMvc.perform(post("/api/user-profile-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileViewsDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViewedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileViewsRepository.findAll().size();
        // set the field null
        userProfileViews.setViewedAt(null);

        // Create the UserProfileViews, which fails.
        UserProfileViewsDTO userProfileViewsDTO = userProfileViewsMapper.toDto(userProfileViews);


        restUserProfileViewsMockMvc.perform(post("/api/user-profile-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileViewsDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserProfileViews() throws Exception {
        // Initialize the database
        userProfileViewsRepository.saveAndFlush(userProfileViews);

        // Get all the userProfileViewsList
        restUserProfileViewsMockMvc.perform(get("/api/user-profile-views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfileViews.getId().intValue())))
            .andExpect(jsonPath("$.[*].userProfileId").value(hasItem(DEFAULT_USER_PROFILE_ID.intValue())))
            .andExpect(jsonPath("$.[*].viewedAt").value(hasItem(DEFAULT_VIEWED_AT.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void getUserProfileViews() throws Exception {
        // Initialize the database
        userProfileViewsRepository.saveAndFlush(userProfileViews);

        // Get the userProfileViews
        restUserProfileViewsMockMvc.perform(get("/api/user-profile-views/{id}", userProfileViews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfileViews.getId().intValue()))
            .andExpect(jsonPath("$.userProfileId").value(DEFAULT_USER_PROFILE_ID.intValue()))
            .andExpect(jsonPath("$.viewedAt").value(DEFAULT_VIEWED_AT.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingUserProfileViews() throws Exception {
        // Get the userProfileViews
        restUserProfileViewsMockMvc.perform(get("/api/user-profile-views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfileViews() throws Exception {
        // Initialize the database
        userProfileViewsRepository.saveAndFlush(userProfileViews);

        int databaseSizeBeforeUpdate = userProfileViewsRepository.findAll().size();

        // Update the userProfileViews
        UserProfileViews updatedUserProfileViews = userProfileViewsRepository.findById(userProfileViews.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfileViews are not directly saved in db
        em.detach(updatedUserProfileViews);
        updatedUserProfileViews
            .userProfileId(UPDATED_USER_PROFILE_ID)
            .viewedAt(UPDATED_VIEWED_AT)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userId(UPDATED_USER_ID);
        UserProfileViewsDTO userProfileViewsDTO = userProfileViewsMapper.toDto(updatedUserProfileViews);

        restUserProfileViewsMockMvc.perform(put("/api/user-profile-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileViewsDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfileViews in the database
        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeUpdate);
        UserProfileViews testUserProfileViews = userProfileViewsList.get(userProfileViewsList.size() - 1);
        assertThat(testUserProfileViews.getUserProfileId()).isEqualTo(UPDATED_USER_PROFILE_ID);
        assertThat(testUserProfileViews.getViewedAt()).isEqualTo(UPDATED_VIEWED_AT);
        assertThat(testUserProfileViews.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testUserProfileViews.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfileViews() throws Exception {
        int databaseSizeBeforeUpdate = userProfileViewsRepository.findAll().size();

        // Create the UserProfileViews
        UserProfileViewsDTO userProfileViewsDTO = userProfileViewsMapper.toDto(userProfileViews);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileViewsMockMvc.perform(put("/api/user-profile-views").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileViewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfileViews in the database
        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfileViews() throws Exception {
        // Initialize the database
        userProfileViewsRepository.saveAndFlush(userProfileViews);

        int databaseSizeBeforeDelete = userProfileViewsRepository.findAll().size();

        // Delete the userProfileViews
        restUserProfileViewsMockMvc.perform(delete("/api/user-profile-views/{id}", userProfileViews.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfileViews> userProfileViewsList = userProfileViewsRepository.findAll();
        assertThat(userProfileViewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
