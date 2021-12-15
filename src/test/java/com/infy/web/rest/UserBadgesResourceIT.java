/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserBadges;
import com.infy.repository.UserBadgesRepository;
import com.infy.service.UserBadgesService;
import com.infy.service.dto.UserBadgesDTO;
import com.infy.service.mapper.UserBadgesMapper;

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
 * Integration tests for the {@link UserBadgesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserBadgesResourceIT {

    private static final Long DEFAULT_BADGE_ID = 1L;
    private static final Long UPDATED_BADGE_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_GRANTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GRANTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GRANTED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_GRANTED_BY_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_NOTIFICATION_ID = 1L;
    private static final Long UPDATED_NOTIFICATION_ID = 2L;

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final Integer DEFAULT_FEATURED_RANK = 1;
    private static final Integer UPDATED_FEATURED_RANK = 2;

    @Autowired
    private UserBadgesRepository userBadgesRepository;

    @Autowired
    private UserBadgesMapper userBadgesMapper;

    @Autowired
    private UserBadgesService userBadgesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserBadgesMockMvc;

    private UserBadges userBadges;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBadges createEntity(EntityManager em) {
        UserBadges userBadges = new UserBadges()
            .badgeId(DEFAULT_BADGE_ID)
            .userId(DEFAULT_USER_ID)
            .grantedAt(DEFAULT_GRANTED_AT)
            .grantedById(DEFAULT_GRANTED_BY_ID)
            .postId(DEFAULT_POST_ID)
            .notificationId(DEFAULT_NOTIFICATION_ID)
            .seq(DEFAULT_SEQ)
            .featuredRank(DEFAULT_FEATURED_RANK);
        return userBadges;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBadges createUpdatedEntity(EntityManager em) {
        UserBadges userBadges = new UserBadges()
            .badgeId(UPDATED_BADGE_ID)
            .userId(UPDATED_USER_ID)
            .grantedAt(UPDATED_GRANTED_AT)
            .grantedById(UPDATED_GRANTED_BY_ID)
            .postId(UPDATED_POST_ID)
            .notificationId(UPDATED_NOTIFICATION_ID)
            .seq(UPDATED_SEQ)
            .featuredRank(UPDATED_FEATURED_RANK);
        return userBadges;
    }

    @BeforeEach
    public void initTest() {
        userBadges = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBadges() throws Exception {
        int databaseSizeBeforeCreate = userBadgesRepository.findAll().size();
        // Create the UserBadges
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);
        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserBadges in the database
        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeCreate + 1);
        UserBadges testUserBadges = userBadgesList.get(userBadgesList.size() - 1);
        assertThat(testUserBadges.getBadgeId()).isEqualTo(DEFAULT_BADGE_ID);
        assertThat(testUserBadges.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserBadges.getGrantedAt()).isEqualTo(DEFAULT_GRANTED_AT);
        assertThat(testUserBadges.getGrantedById()).isEqualTo(DEFAULT_GRANTED_BY_ID);
        assertThat(testUserBadges.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testUserBadges.getNotificationId()).isEqualTo(DEFAULT_NOTIFICATION_ID);
        assertThat(testUserBadges.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testUserBadges.getFeaturedRank()).isEqualTo(DEFAULT_FEATURED_RANK);
    }

    @Test
    @Transactional
    public void createUserBadgesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBadgesRepository.findAll().size();

        // Create the UserBadges with an existing ID
        userBadges.setId(1L);
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBadges in the database
        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBadgeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBadgesRepository.findAll().size();
        // set the field null
        userBadges.setBadgeId(null);

        // Create the UserBadges, which fails.
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);


        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBadgesRepository.findAll().size();
        // set the field null
        userBadges.setUserId(null);

        // Create the UserBadges, which fails.
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);


        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrantedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBadgesRepository.findAll().size();
        // set the field null
        userBadges.setGrantedAt(null);

        // Create the UserBadges, which fails.
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);


        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrantedByIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBadgesRepository.findAll().size();
        // set the field null
        userBadges.setGrantedById(null);

        // Create the UserBadges, which fails.
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);


        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBadgesRepository.findAll().size();
        // set the field null
        userBadges.setSeq(null);

        // Create the UserBadges, which fails.
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);


        restUserBadgesMockMvc.perform(post("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserBadges() throws Exception {
        // Initialize the database
        userBadgesRepository.saveAndFlush(userBadges);

        // Get all the userBadgesList
        restUserBadgesMockMvc.perform(get("/api/user-badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBadges.getId().intValue())))
            .andExpect(jsonPath("$.[*].badgeId").value(hasItem(DEFAULT_BADGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].grantedAt").value(hasItem(DEFAULT_GRANTED_AT.toString())))
            .andExpect(jsonPath("$.[*].grantedById").value(hasItem(DEFAULT_GRANTED_BY_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].notificationId").value(hasItem(DEFAULT_NOTIFICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].featuredRank").value(hasItem(DEFAULT_FEATURED_RANK)));
    }

    @Test
    @Transactional
    public void getUserBadges() throws Exception {
        // Initialize the database
        userBadgesRepository.saveAndFlush(userBadges);

        // Get the userBadges
        restUserBadgesMockMvc.perform(get("/api/user-badges/{id}", userBadges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userBadges.getId().intValue()))
            .andExpect(jsonPath("$.badgeId").value(DEFAULT_BADGE_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.grantedAt").value(DEFAULT_GRANTED_AT.toString()))
            .andExpect(jsonPath("$.grantedById").value(DEFAULT_GRANTED_BY_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.notificationId").value(DEFAULT_NOTIFICATION_ID.intValue()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.featuredRank").value(DEFAULT_FEATURED_RANK));
    }
    @Test
    @Transactional
    public void getNonExistingUserBadges() throws Exception {
        // Get the userBadges
        restUserBadgesMockMvc.perform(get("/api/user-badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBadges() throws Exception {
        // Initialize the database
        userBadgesRepository.saveAndFlush(userBadges);

        int databaseSizeBeforeUpdate = userBadgesRepository.findAll().size();

        // Update the userBadges
        UserBadges updatedUserBadges = userBadgesRepository.findById(userBadges.getId()).get();
        // Disconnect from session so that the updates on updatedUserBadges are not directly saved in db
        em.detach(updatedUserBadges);
        updatedUserBadges
            .badgeId(UPDATED_BADGE_ID)
            .userId(UPDATED_USER_ID)
            .grantedAt(UPDATED_GRANTED_AT)
            .grantedById(UPDATED_GRANTED_BY_ID)
            .postId(UPDATED_POST_ID)
            .notificationId(UPDATED_NOTIFICATION_ID)
            .seq(UPDATED_SEQ)
            .featuredRank(UPDATED_FEATURED_RANK);
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(updatedUserBadges);

        restUserBadgesMockMvc.perform(put("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isOk());

        // Validate the UserBadges in the database
        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeUpdate);
        UserBadges testUserBadges = userBadgesList.get(userBadgesList.size() - 1);
        assertThat(testUserBadges.getBadgeId()).isEqualTo(UPDATED_BADGE_ID);
        assertThat(testUserBadges.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserBadges.getGrantedAt()).isEqualTo(UPDATED_GRANTED_AT);
        assertThat(testUserBadges.getGrantedById()).isEqualTo(UPDATED_GRANTED_BY_ID);
        assertThat(testUserBadges.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testUserBadges.getNotificationId()).isEqualTo(UPDATED_NOTIFICATION_ID);
        assertThat(testUserBadges.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testUserBadges.getFeaturedRank()).isEqualTo(UPDATED_FEATURED_RANK);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBadges() throws Exception {
        int databaseSizeBeforeUpdate = userBadgesRepository.findAll().size();

        // Create the UserBadges
        UserBadgesDTO userBadgesDTO = userBadgesMapper.toDto(userBadges);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserBadgesMockMvc.perform(put("/api/user-badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBadges in the database
        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserBadges() throws Exception {
        // Initialize the database
        userBadgesRepository.saveAndFlush(userBadges);

        int databaseSizeBeforeDelete = userBadgesRepository.findAll().size();

        // Delete the userBadges
        restUserBadgesMockMvc.perform(delete("/api/user-badges/{id}", userBadges.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserBadges> userBadgesList = userBadgesRepository.findAll();
        assertThat(userBadgesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
