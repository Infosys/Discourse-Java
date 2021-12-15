/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Badges;
import com.infy.repository.BadgesRepository;
import com.infy.service.BadgesService;
import com.infy.service.dto.BadgesDTO;
import com.infy.service.mapper.BadgesMapper;

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
 * Integration tests for the {@link BadgesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BadgesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_BADGE_TYPE_ID = 1L;
    private static final Long UPDATED_BADGE_TYPE_ID = 2L;

    private static final Integer DEFAULT_GRANT_COUNT = 1;
    private static final Integer UPDATED_GRANT_COUNT = 2;

    private static final Boolean DEFAULT_ALLOW_TITLE = false;
    private static final Boolean UPDATED_ALLOW_TITLE = true;

    private static final Boolean DEFAULT_MULTIPLE_GRANT = false;
    private static final Boolean UPDATED_MULTIPLE_GRANT = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LISTABLE = false;
    private static final Boolean UPDATED_LISTABLE = true;

    private static final Boolean DEFAULT_TARGET_POSTS = false;
    private static final Boolean UPDATED_TARGET_POSTS = true;

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Boolean DEFAULT_AUTO_REVOKE = false;
    private static final Boolean UPDATED_AUTO_REVOKE = true;

    private static final Long DEFAULT_BADGE_GROUPING_ID = 1L;
    private static final Long UPDATED_BADGE_GROUPING_ID = 2L;

    private static final Integer DEFAULT_TRIGGER = 1;
    private static final Integer UPDATED_TRIGGER = 2;

    private static final Boolean DEFAULT_SHOW_POSTS = false;
    private static final Boolean UPDATED_SHOW_POSTS = true;

    private static final Boolean DEFAULT_SYSTEM = false;
    private static final Boolean UPDATED_SYSTEM = true;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_IMAGE_UPLOAD_ID = 1L;
    private static final Long UPDATED_IMAGE_UPLOAD_ID = 2L;

    @Autowired
    private BadgesRepository badgesRepository;

    @Autowired
    private BadgesMapper badgesMapper;

    @Autowired
    private BadgesService badgesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgesMockMvc;

    private Badges badges;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badges createEntity(EntityManager em) {
        Badges badges = new Badges()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .badgeTypeId(DEFAULT_BADGE_TYPE_ID)
            .grantCount(DEFAULT_GRANT_COUNT)
            .allowTitle(DEFAULT_ALLOW_TITLE)
            .multipleGrant(DEFAULT_MULTIPLE_GRANT)
            .icon(DEFAULT_ICON)
            .listable(DEFAULT_LISTABLE)
            .targetPosts(DEFAULT_TARGET_POSTS)
            .query(DEFAULT_QUERY)
            .enabled(DEFAULT_ENABLED)
            .autoRevoke(DEFAULT_AUTO_REVOKE)
            .badgeGroupingId(DEFAULT_BADGE_GROUPING_ID)
            .trigger(DEFAULT_TRIGGER)
            .showPosts(DEFAULT_SHOW_POSTS)
            .system(DEFAULT_SYSTEM)
            .image(DEFAULT_IMAGE)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .imageUploadId(DEFAULT_IMAGE_UPLOAD_ID);
        return badges;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badges createUpdatedEntity(EntityManager em) {
        Badges badges = new Badges()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .badgeTypeId(UPDATED_BADGE_TYPE_ID)
            .grantCount(UPDATED_GRANT_COUNT)
            .allowTitle(UPDATED_ALLOW_TITLE)
            .multipleGrant(UPDATED_MULTIPLE_GRANT)
            .icon(UPDATED_ICON)
            .listable(UPDATED_LISTABLE)
            .targetPosts(UPDATED_TARGET_POSTS)
            .query(UPDATED_QUERY)
            .enabled(UPDATED_ENABLED)
            .autoRevoke(UPDATED_AUTO_REVOKE)
            .badgeGroupingId(UPDATED_BADGE_GROUPING_ID)
            .trigger(UPDATED_TRIGGER)
            .showPosts(UPDATED_SHOW_POSTS)
            .system(UPDATED_SYSTEM)
            .image(UPDATED_IMAGE)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .imageUploadId(UPDATED_IMAGE_UPLOAD_ID);
        return badges;
    }

    @BeforeEach
    public void initTest() {
        badges = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadges() throws Exception {
        int databaseSizeBeforeCreate = badgesRepository.findAll().size();
        // Create the Badges
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);
        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isCreated());

        // Validate the Badges in the database
        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeCreate + 1);
        Badges testBadges = badgesList.get(badgesList.size() - 1);
        assertThat(testBadges.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBadges.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBadges.getBadgeTypeId()).isEqualTo(DEFAULT_BADGE_TYPE_ID);
        assertThat(testBadges.getGrantCount()).isEqualTo(DEFAULT_GRANT_COUNT);
        assertThat(testBadges.isAllowTitle()).isEqualTo(DEFAULT_ALLOW_TITLE);
        assertThat(testBadges.isMultipleGrant()).isEqualTo(DEFAULT_MULTIPLE_GRANT);
        assertThat(testBadges.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testBadges.isListable()).isEqualTo(DEFAULT_LISTABLE);
        assertThat(testBadges.isTargetPosts()).isEqualTo(DEFAULT_TARGET_POSTS);
        assertThat(testBadges.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testBadges.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testBadges.isAutoRevoke()).isEqualTo(DEFAULT_AUTO_REVOKE);
        assertThat(testBadges.getBadgeGroupingId()).isEqualTo(DEFAULT_BADGE_GROUPING_ID);
        assertThat(testBadges.getTrigger()).isEqualTo(DEFAULT_TRIGGER);
        assertThat(testBadges.isShowPosts()).isEqualTo(DEFAULT_SHOW_POSTS);
        assertThat(testBadges.isSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testBadges.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBadges.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testBadges.getImageUploadId()).isEqualTo(DEFAULT_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void createBadgesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgesRepository.findAll().size();

        // Create the Badges with an existing ID
        badges.setId(1L);
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Badges in the database
        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setName(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBadgeTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setBadgeTypeId(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrantCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setGrantCount(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setAllowTitle(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMultipleGrantIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setMultipleGrant(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setEnabled(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutoRevokeIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setAutoRevoke(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBadgeGroupingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setBadgeGroupingId(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShowPostsIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setShowPosts(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystemIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgesRepository.findAll().size();
        // set the field null
        badges.setSystem(null);

        // Create the Badges, which fails.
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);


        restBadgesMockMvc.perform(post("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);

        // Get all the badgesList
        restBadgesMockMvc.perform(get("/api/badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badges.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].badgeTypeId").value(hasItem(DEFAULT_BADGE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].grantCount").value(hasItem(DEFAULT_GRANT_COUNT)))
            .andExpect(jsonPath("$.[*].allowTitle").value(hasItem(DEFAULT_ALLOW_TITLE.booleanValue())))
            .andExpect(jsonPath("$.[*].multipleGrant").value(hasItem(DEFAULT_MULTIPLE_GRANT.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].listable").value(hasItem(DEFAULT_LISTABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].targetPosts").value(hasItem(DEFAULT_TARGET_POSTS.booleanValue())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].autoRevoke").value(hasItem(DEFAULT_AUTO_REVOKE.booleanValue())))
            .andExpect(jsonPath("$.[*].badgeGroupingId").value(hasItem(DEFAULT_BADGE_GROUPING_ID.intValue())))
            .andExpect(jsonPath("$.[*].trigger").value(hasItem(DEFAULT_TRIGGER)))
            .andExpect(jsonPath("$.[*].showPosts").value(hasItem(DEFAULT_SHOW_POSTS.booleanValue())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.booleanValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageUploadId").value(hasItem(DEFAULT_IMAGE_UPLOAD_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);

        // Get the badges
        restBadgesMockMvc.perform(get("/api/badges/{id}", badges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badges.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.badgeTypeId").value(DEFAULT_BADGE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.grantCount").value(DEFAULT_GRANT_COUNT))
            .andExpect(jsonPath("$.allowTitle").value(DEFAULT_ALLOW_TITLE.booleanValue()))
            .andExpect(jsonPath("$.multipleGrant").value(DEFAULT_MULTIPLE_GRANT.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.listable").value(DEFAULT_LISTABLE.booleanValue()))
            .andExpect(jsonPath("$.targetPosts").value(DEFAULT_TARGET_POSTS.booleanValue()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.autoRevoke").value(DEFAULT_AUTO_REVOKE.booleanValue()))
            .andExpect(jsonPath("$.badgeGroupingId").value(DEFAULT_BADGE_GROUPING_ID.intValue()))
            .andExpect(jsonPath("$.trigger").value(DEFAULT_TRIGGER))
            .andExpect(jsonPath("$.showPosts").value(DEFAULT_SHOW_POSTS.booleanValue()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.booleanValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION))
            .andExpect(jsonPath("$.imageUploadId").value(DEFAULT_IMAGE_UPLOAD_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBadges() throws Exception {
        // Get the badges
        restBadgesMockMvc.perform(get("/api/badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);

        int databaseSizeBeforeUpdate = badgesRepository.findAll().size();

        // Update the badges
        Badges updatedBadges = badgesRepository.findById(badges.getId()).get();
        // Disconnect from session so that the updates on updatedBadges are not directly saved in db
        em.detach(updatedBadges);
        updatedBadges
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .badgeTypeId(UPDATED_BADGE_TYPE_ID)
            .grantCount(UPDATED_GRANT_COUNT)
            .allowTitle(UPDATED_ALLOW_TITLE)
            .multipleGrant(UPDATED_MULTIPLE_GRANT)
            .icon(UPDATED_ICON)
            .listable(UPDATED_LISTABLE)
            .targetPosts(UPDATED_TARGET_POSTS)
            .query(UPDATED_QUERY)
            .enabled(UPDATED_ENABLED)
            .autoRevoke(UPDATED_AUTO_REVOKE)
            .badgeGroupingId(UPDATED_BADGE_GROUPING_ID)
            .trigger(UPDATED_TRIGGER)
            .showPosts(UPDATED_SHOW_POSTS)
            .system(UPDATED_SYSTEM)
            .image(UPDATED_IMAGE)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .imageUploadId(UPDATED_IMAGE_UPLOAD_ID);
        BadgesDTO badgesDTO = badgesMapper.toDto(updatedBadges);

        restBadgesMockMvc.perform(put("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isOk());

        // Validate the Badges in the database
        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeUpdate);
        Badges testBadges = badgesList.get(badgesList.size() - 1);
        assertThat(testBadges.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBadges.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBadges.getBadgeTypeId()).isEqualTo(UPDATED_BADGE_TYPE_ID);
        assertThat(testBadges.getGrantCount()).isEqualTo(UPDATED_GRANT_COUNT);
        assertThat(testBadges.isAllowTitle()).isEqualTo(UPDATED_ALLOW_TITLE);
        assertThat(testBadges.isMultipleGrant()).isEqualTo(UPDATED_MULTIPLE_GRANT);
        assertThat(testBadges.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testBadges.isListable()).isEqualTo(UPDATED_LISTABLE);
        assertThat(testBadges.isTargetPosts()).isEqualTo(UPDATED_TARGET_POSTS);
        assertThat(testBadges.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testBadges.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testBadges.isAutoRevoke()).isEqualTo(UPDATED_AUTO_REVOKE);
        assertThat(testBadges.getBadgeGroupingId()).isEqualTo(UPDATED_BADGE_GROUPING_ID);
        assertThat(testBadges.getTrigger()).isEqualTo(UPDATED_TRIGGER);
        assertThat(testBadges.isShowPosts()).isEqualTo(UPDATED_SHOW_POSTS);
        assertThat(testBadges.isSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testBadges.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBadges.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testBadges.getImageUploadId()).isEqualTo(UPDATED_IMAGE_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBadges() throws Exception {
        int databaseSizeBeforeUpdate = badgesRepository.findAll().size();

        // Create the Badges
        BadgesDTO badgesDTO = badgesMapper.toDto(badges);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgesMockMvc.perform(put("/api/badges").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Badges in the database
        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);

        int databaseSizeBeforeDelete = badgesRepository.findAll().size();

        // Delete the badges
        restBadgesMockMvc.perform(delete("/api/badges/{id}", badges.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Badges> badgesList = badgesRepository.findAll();
        assertThat(badgesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
