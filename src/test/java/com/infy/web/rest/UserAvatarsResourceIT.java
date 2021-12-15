/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserAvatars;
import com.infy.repository.UserAvatarsRepository;
import com.infy.service.UserAvatarsService;
import com.infy.service.dto.UserAvatarsDTO;
import com.infy.service.mapper.UserAvatarsMapper;

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
 * Integration tests for the {@link UserAvatarsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserAvatarsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOM_UPLOAD_ID = 1L;
    private static final Long UPDATED_CUSTOM_UPLOAD_ID = 2L;

    private static final Long DEFAULT_GRAVATAR_UPLOAD_ID = 1L;
    private static final Long UPDATED_GRAVATAR_UPLOAD_ID = 2L;

    private static final Instant DEFAULT_LAST_GRAVATAR_DOWNLOAD_ATTEMPT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_GRAVATAR_DOWNLOAD_ATTEMPT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserAvatarsRepository userAvatarsRepository;

    @Autowired
    private UserAvatarsMapper userAvatarsMapper;

    @Autowired
    private UserAvatarsService userAvatarsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAvatarsMockMvc;

    private UserAvatars userAvatars;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAvatars createEntity(EntityManager em) {
        UserAvatars userAvatars = new UserAvatars()
            .userId(DEFAULT_USER_ID)
            .customUploadId(DEFAULT_CUSTOM_UPLOAD_ID)
            .gravatarUploadId(DEFAULT_GRAVATAR_UPLOAD_ID)
            .lastGravatarDownloadAttempt(DEFAULT_LAST_GRAVATAR_DOWNLOAD_ATTEMPT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return userAvatars;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAvatars createUpdatedEntity(EntityManager em) {
        UserAvatars userAvatars = new UserAvatars()
            .userId(UPDATED_USER_ID)
            .customUploadId(UPDATED_CUSTOM_UPLOAD_ID)
            .gravatarUploadId(UPDATED_GRAVATAR_UPLOAD_ID)
            .lastGravatarDownloadAttempt(UPDATED_LAST_GRAVATAR_DOWNLOAD_ATTEMPT)
            .updatedAt(UPDATED_UPDATED_AT);
        return userAvatars;
    }

    @BeforeEach
    public void initTest() {
        userAvatars = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAvatars() throws Exception {
        int databaseSizeBeforeCreate = userAvatarsRepository.findAll().size();
        // Create the UserAvatars
        UserAvatarsDTO userAvatarsDTO = userAvatarsMapper.toDto(userAvatars);
        restUserAvatarsMockMvc.perform(post("/api/user-avatars").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAvatarsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAvatars in the database
        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeCreate + 1);
        UserAvatars testUserAvatars = userAvatarsList.get(userAvatarsList.size() - 1);
        assertThat(testUserAvatars.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserAvatars.getCustomUploadId()).isEqualTo(DEFAULT_CUSTOM_UPLOAD_ID);
        assertThat(testUserAvatars.getGravatarUploadId()).isEqualTo(DEFAULT_GRAVATAR_UPLOAD_ID);
        assertThat(testUserAvatars.getLastGravatarDownloadAttempt()).isEqualTo(DEFAULT_LAST_GRAVATAR_DOWNLOAD_ATTEMPT);
        assertThat(testUserAvatars.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createUserAvatarsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAvatarsRepository.findAll().size();

        // Create the UserAvatars with an existing ID
        userAvatars.setId(1L);
        UserAvatarsDTO userAvatarsDTO = userAvatarsMapper.toDto(userAvatars);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAvatarsMockMvc.perform(post("/api/user-avatars").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAvatarsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAvatars in the database
        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAvatarsRepository.findAll().size();
        // set the field null
        userAvatars.setUserId(null);

        // Create the UserAvatars, which fails.
        UserAvatarsDTO userAvatarsDTO = userAvatarsMapper.toDto(userAvatars);


        restUserAvatarsMockMvc.perform(post("/api/user-avatars").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAvatarsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAvatarsRepository.findAll().size();
        // set the field null
        userAvatars.setUpdatedAt(null);

        // Create the UserAvatars, which fails.
        UserAvatarsDTO userAvatarsDTO = userAvatarsMapper.toDto(userAvatars);


        restUserAvatarsMockMvc.perform(post("/api/user-avatars").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAvatarsDTO)))
            .andExpect(status().isBadRequest());

        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAvatars() throws Exception {
        // Initialize the database
        userAvatarsRepository.saveAndFlush(userAvatars);

        // Get all the userAvatarsList
        restUserAvatarsMockMvc.perform(get("/api/user-avatars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAvatars.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].customUploadId").value(hasItem(DEFAULT_CUSTOM_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].gravatarUploadId").value(hasItem(DEFAULT_GRAVATAR_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastGravatarDownloadAttempt").value(hasItem(DEFAULT_LAST_GRAVATAR_DOWNLOAD_ATTEMPT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getUserAvatars() throws Exception {
        // Initialize the database
        userAvatarsRepository.saveAndFlush(userAvatars);

        // Get the userAvatars
        restUserAvatarsMockMvc.perform(get("/api/user-avatars/{id}", userAvatars.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAvatars.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.customUploadId").value(DEFAULT_CUSTOM_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.gravatarUploadId").value(DEFAULT_GRAVATAR_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.lastGravatarDownloadAttempt").value(DEFAULT_LAST_GRAVATAR_DOWNLOAD_ATTEMPT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserAvatars() throws Exception {
        // Get the userAvatars
        restUserAvatarsMockMvc.perform(get("/api/user-avatars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAvatars() throws Exception {
        // Initialize the database
        userAvatarsRepository.saveAndFlush(userAvatars);

        int databaseSizeBeforeUpdate = userAvatarsRepository.findAll().size();

        // Update the userAvatars
        UserAvatars updatedUserAvatars = userAvatarsRepository.findById(userAvatars.getId()).get();
        // Disconnect from session so that the updates on updatedUserAvatars are not directly saved in db
        em.detach(updatedUserAvatars);
        updatedUserAvatars
            .userId(UPDATED_USER_ID)
            .customUploadId(UPDATED_CUSTOM_UPLOAD_ID)
            .gravatarUploadId(UPDATED_GRAVATAR_UPLOAD_ID)
            .lastGravatarDownloadAttempt(UPDATED_LAST_GRAVATAR_DOWNLOAD_ATTEMPT)
            .updatedAt(UPDATED_UPDATED_AT);
        UserAvatarsDTO userAvatarsDTO = userAvatarsMapper.toDto(updatedUserAvatars);

        restUserAvatarsMockMvc.perform(put("/api/user-avatars").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAvatarsDTO)))
            .andExpect(status().isOk());

        // Validate the UserAvatars in the database
        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeUpdate);
        UserAvatars testUserAvatars = userAvatarsList.get(userAvatarsList.size() - 1);
        assertThat(testUserAvatars.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserAvatars.getCustomUploadId()).isEqualTo(UPDATED_CUSTOM_UPLOAD_ID);
        assertThat(testUserAvatars.getGravatarUploadId()).isEqualTo(UPDATED_GRAVATAR_UPLOAD_ID);
        assertThat(testUserAvatars.getLastGravatarDownloadAttempt()).isEqualTo(UPDATED_LAST_GRAVATAR_DOWNLOAD_ATTEMPT);
        assertThat(testUserAvatars.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAvatars() throws Exception {
        int databaseSizeBeforeUpdate = userAvatarsRepository.findAll().size();

        // Create the UserAvatars
        UserAvatarsDTO userAvatarsDTO = userAvatarsMapper.toDto(userAvatars);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAvatarsMockMvc.perform(put("/api/user-avatars").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAvatarsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAvatars in the database
        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAvatars() throws Exception {
        // Initialize the database
        userAvatarsRepository.saveAndFlush(userAvatars);

        int databaseSizeBeforeDelete = userAvatarsRepository.findAll().size();

        // Delete the userAvatars
        restUserAvatarsMockMvc.perform(delete("/api/user-avatars/{id}", userAvatars.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAvatars> userAvatarsList = userAvatarsRepository.findAll();
        assertThat(userAvatarsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
