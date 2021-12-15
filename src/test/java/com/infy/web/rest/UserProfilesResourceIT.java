/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserProfiles;
import com.infy.repository.UserProfilesRepository;
import com.infy.service.UserProfilesService;
import com.infy.service.dto.UserProfilesDTO;
import com.infy.service.mapper.UserProfilesMapper;

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
 * Integration tests for the {@link UserProfilesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserProfilesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_BIO_RAW = "AAAAAAAAAA";
    private static final String UPDATED_BIO_RAW = "BBBBBBBBBB";

    private static final String DEFAULT_BIO_COOKED = "AAAAAAAAAA";
    private static final String UPDATED_BIO_COOKED = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISMISSED_BANNER_KEY = 1;
    private static final Integer UPDATED_DISMISSED_BANNER_KEY = 2;

    private static final Integer DEFAULT_BIO_COOKED_VERSION = 1;
    private static final Integer UPDATED_BIO_COOKED_VERSION = 2;

    private static final Boolean DEFAULT_BADGE_GRANTED_TITLE = false;
    private static final Boolean UPDATED_BADGE_GRANTED_TITLE = true;

    private static final Integer DEFAULT_VIEWS = 1;
    private static final Integer UPDATED_VIEWS = 2;

    private static final Long DEFAULT_PROFILE_BACKGROUND_UPLOAD_ID = 1L;
    private static final Long UPDATED_PROFILE_BACKGROUND_UPLOAD_ID = 2L;

    private static final Long DEFAULT_CARD_BACKGROUND_UPLOAD_ID = 1L;
    private static final Long UPDATED_CARD_BACKGROUND_UPLOAD_ID = 2L;

    private static final Long DEFAULT_GRANTED_TITLE_BADGE_ID = 1L;
    private static final Long UPDATED_GRANTED_TITLE_BADGE_ID = 2L;

    private static final Long DEFAULT_FEATURED_TOPIC_ID = 1L;
    private static final Long UPDATED_FEATURED_TOPIC_ID = 2L;

    @Autowired
    private UserProfilesRepository userProfilesRepository;

    @Autowired
    private UserProfilesMapper userProfilesMapper;

    @Autowired
    private UserProfilesService userProfilesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfilesMockMvc;

    private UserProfiles userProfiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfiles createEntity(EntityManager em) {
        UserProfiles userProfiles = new UserProfiles()
            .userId(DEFAULT_USER_ID)
            .location(DEFAULT_LOCATION)
            .website(DEFAULT_WEBSITE)
            .bioRaw(DEFAULT_BIO_RAW)
            .bioCooked(DEFAULT_BIO_COOKED)
            .dismissedBannerKey(DEFAULT_DISMISSED_BANNER_KEY)
            .bioCookedVersion(DEFAULT_BIO_COOKED_VERSION)
            .badgeGrantedTitle(DEFAULT_BADGE_GRANTED_TITLE)
            .views(DEFAULT_VIEWS)
            .profileBackgroundUploadId(DEFAULT_PROFILE_BACKGROUND_UPLOAD_ID)
            .cardBackgroundUploadId(DEFAULT_CARD_BACKGROUND_UPLOAD_ID)
            .grantedTitleBadgeId(DEFAULT_GRANTED_TITLE_BADGE_ID)
            .featuredTopicId(DEFAULT_FEATURED_TOPIC_ID);
        return userProfiles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfiles createUpdatedEntity(EntityManager em) {
        UserProfiles userProfiles = new UserProfiles()
            .userId(UPDATED_USER_ID)
            .location(UPDATED_LOCATION)
            .website(UPDATED_WEBSITE)
            .bioRaw(UPDATED_BIO_RAW)
            .bioCooked(UPDATED_BIO_COOKED)
            .dismissedBannerKey(UPDATED_DISMISSED_BANNER_KEY)
            .bioCookedVersion(UPDATED_BIO_COOKED_VERSION)
            .badgeGrantedTitle(UPDATED_BADGE_GRANTED_TITLE)
            .views(UPDATED_VIEWS)
            .profileBackgroundUploadId(UPDATED_PROFILE_BACKGROUND_UPLOAD_ID)
            .cardBackgroundUploadId(UPDATED_CARD_BACKGROUND_UPLOAD_ID)
            .grantedTitleBadgeId(UPDATED_GRANTED_TITLE_BADGE_ID)
            .featuredTopicId(UPDATED_FEATURED_TOPIC_ID);
        return userProfiles;
    }

    @BeforeEach
    public void initTest() {
        userProfiles = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfiles() throws Exception {
        int databaseSizeBeforeCreate = userProfilesRepository.findAll().size();
        // Create the UserProfiles
        UserProfilesDTO userProfilesDTO = userProfilesMapper.toDto(userProfiles);
        restUserProfilesMockMvc.perform(post("/api/user-profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfilesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfiles in the database
        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfiles testUserProfiles = userProfilesList.get(userProfilesList.size() - 1);
        assertThat(testUserProfiles.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserProfiles.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testUserProfiles.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testUserProfiles.getBioRaw()).isEqualTo(DEFAULT_BIO_RAW);
        assertThat(testUserProfiles.getBioCooked()).isEqualTo(DEFAULT_BIO_COOKED);
        assertThat(testUserProfiles.getDismissedBannerKey()).isEqualTo(DEFAULT_DISMISSED_BANNER_KEY);
        assertThat(testUserProfiles.getBioCookedVersion()).isEqualTo(DEFAULT_BIO_COOKED_VERSION);
        assertThat(testUserProfiles.isBadgeGrantedTitle()).isEqualTo(DEFAULT_BADGE_GRANTED_TITLE);
        assertThat(testUserProfiles.getViews()).isEqualTo(DEFAULT_VIEWS);
        assertThat(testUserProfiles.getProfileBackgroundUploadId()).isEqualTo(DEFAULT_PROFILE_BACKGROUND_UPLOAD_ID);
        assertThat(testUserProfiles.getCardBackgroundUploadId()).isEqualTo(DEFAULT_CARD_BACKGROUND_UPLOAD_ID);
        assertThat(testUserProfiles.getGrantedTitleBadgeId()).isEqualTo(DEFAULT_GRANTED_TITLE_BADGE_ID);
        assertThat(testUserProfiles.getFeaturedTopicId()).isEqualTo(DEFAULT_FEATURED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createUserProfilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfilesRepository.findAll().size();

        // Create the UserProfiles with an existing ID
        userProfiles.setId(1L);
        UserProfilesDTO userProfilesDTO = userProfilesMapper.toDto(userProfiles);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfilesMockMvc.perform(post("/api/user-profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfiles in the database
        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfilesRepository.findAll().size();
        // set the field null
        userProfiles.setUserId(null);

        // Create the UserProfiles, which fails.
        UserProfilesDTO userProfilesDTO = userProfilesMapper.toDto(userProfiles);


        restUserProfilesMockMvc.perform(post("/api/user-profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfilesDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfilesRepository.findAll().size();
        // set the field null
        userProfiles.setViews(null);

        // Create the UserProfiles, which fails.
        UserProfilesDTO userProfilesDTO = userProfilesMapper.toDto(userProfiles);


        restUserProfilesMockMvc.perform(post("/api/user-profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfilesDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfilesRepository.saveAndFlush(userProfiles);

        // Get all the userProfilesList
        restUserProfilesMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].bioRaw").value(hasItem(DEFAULT_BIO_RAW)))
            .andExpect(jsonPath("$.[*].bioCooked").value(hasItem(DEFAULT_BIO_COOKED)))
            .andExpect(jsonPath("$.[*].dismissedBannerKey").value(hasItem(DEFAULT_DISMISSED_BANNER_KEY)))
            .andExpect(jsonPath("$.[*].bioCookedVersion").value(hasItem(DEFAULT_BIO_COOKED_VERSION)))
            .andExpect(jsonPath("$.[*].badgeGrantedTitle").value(hasItem(DEFAULT_BADGE_GRANTED_TITLE.booleanValue())))
            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS)))
            .andExpect(jsonPath("$.[*].profileBackgroundUploadId").value(hasItem(DEFAULT_PROFILE_BACKGROUND_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].cardBackgroundUploadId").value(hasItem(DEFAULT_CARD_BACKGROUND_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].grantedTitleBadgeId").value(hasItem(DEFAULT_GRANTED_TITLE_BADGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].featuredTopicId").value(hasItem(DEFAULT_FEATURED_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserProfiles() throws Exception {
        // Initialize the database
        userProfilesRepository.saveAndFlush(userProfiles);

        // Get the userProfiles
        restUserProfilesMockMvc.perform(get("/api/user-profiles/{id}", userProfiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfiles.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.bioRaw").value(DEFAULT_BIO_RAW))
            .andExpect(jsonPath("$.bioCooked").value(DEFAULT_BIO_COOKED))
            .andExpect(jsonPath("$.dismissedBannerKey").value(DEFAULT_DISMISSED_BANNER_KEY))
            .andExpect(jsonPath("$.bioCookedVersion").value(DEFAULT_BIO_COOKED_VERSION))
            .andExpect(jsonPath("$.badgeGrantedTitle").value(DEFAULT_BADGE_GRANTED_TITLE.booleanValue()))
            .andExpect(jsonPath("$.views").value(DEFAULT_VIEWS))
            .andExpect(jsonPath("$.profileBackgroundUploadId").value(DEFAULT_PROFILE_BACKGROUND_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.cardBackgroundUploadId").value(DEFAULT_CARD_BACKGROUND_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.grantedTitleBadgeId").value(DEFAULT_GRANTED_TITLE_BADGE_ID.intValue()))
            .andExpect(jsonPath("$.featuredTopicId").value(DEFAULT_FEATURED_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserProfiles() throws Exception {
        // Get the userProfiles
        restUserProfilesMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfiles() throws Exception {
        // Initialize the database
        userProfilesRepository.saveAndFlush(userProfiles);

        int databaseSizeBeforeUpdate = userProfilesRepository.findAll().size();

        // Update the userProfiles
        UserProfiles updatedUserProfiles = userProfilesRepository.findById(userProfiles.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfiles are not directly saved in db
        em.detach(updatedUserProfiles);
        updatedUserProfiles
            .userId(UPDATED_USER_ID)
            .location(UPDATED_LOCATION)
            .website(UPDATED_WEBSITE)
            .bioRaw(UPDATED_BIO_RAW)
            .bioCooked(UPDATED_BIO_COOKED)
            .dismissedBannerKey(UPDATED_DISMISSED_BANNER_KEY)
            .bioCookedVersion(UPDATED_BIO_COOKED_VERSION)
            .badgeGrantedTitle(UPDATED_BADGE_GRANTED_TITLE)
            .views(UPDATED_VIEWS)
            .profileBackgroundUploadId(UPDATED_PROFILE_BACKGROUND_UPLOAD_ID)
            .cardBackgroundUploadId(UPDATED_CARD_BACKGROUND_UPLOAD_ID)
            .grantedTitleBadgeId(UPDATED_GRANTED_TITLE_BADGE_ID)
            .featuredTopicId(UPDATED_FEATURED_TOPIC_ID);
        UserProfilesDTO userProfilesDTO = userProfilesMapper.toDto(updatedUserProfiles);

        restUserProfilesMockMvc.perform(put("/api/user-profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfilesDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfiles in the database
        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeUpdate);
        UserProfiles testUserProfiles = userProfilesList.get(userProfilesList.size() - 1);
        assertThat(testUserProfiles.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserProfiles.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testUserProfiles.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testUserProfiles.getBioRaw()).isEqualTo(UPDATED_BIO_RAW);
        assertThat(testUserProfiles.getBioCooked()).isEqualTo(UPDATED_BIO_COOKED);
        assertThat(testUserProfiles.getDismissedBannerKey()).isEqualTo(UPDATED_DISMISSED_BANNER_KEY);
        assertThat(testUserProfiles.getBioCookedVersion()).isEqualTo(UPDATED_BIO_COOKED_VERSION);
        assertThat(testUserProfiles.isBadgeGrantedTitle()).isEqualTo(UPDATED_BADGE_GRANTED_TITLE);
        assertThat(testUserProfiles.getViews()).isEqualTo(UPDATED_VIEWS);
        assertThat(testUserProfiles.getProfileBackgroundUploadId()).isEqualTo(UPDATED_PROFILE_BACKGROUND_UPLOAD_ID);
        assertThat(testUserProfiles.getCardBackgroundUploadId()).isEqualTo(UPDATED_CARD_BACKGROUND_UPLOAD_ID);
        assertThat(testUserProfiles.getGrantedTitleBadgeId()).isEqualTo(UPDATED_GRANTED_TITLE_BADGE_ID);
        assertThat(testUserProfiles.getFeaturedTopicId()).isEqualTo(UPDATED_FEATURED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfiles() throws Exception {
        int databaseSizeBeforeUpdate = userProfilesRepository.findAll().size();

        // Create the UserProfiles
        UserProfilesDTO userProfilesDTO = userProfilesMapper.toDto(userProfiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfilesMockMvc.perform(put("/api/user-profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfiles in the database
        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfiles() throws Exception {
        // Initialize the database
        userProfilesRepository.saveAndFlush(userProfiles);

        int databaseSizeBeforeDelete = userProfilesRepository.findAll().size();

        // Delete the userProfiles
        restUserProfilesMockMvc.perform(delete("/api/user-profiles/{id}", userProfiles.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfiles> userProfilesList = userProfilesRepository.findAll();
        assertThat(userProfilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
