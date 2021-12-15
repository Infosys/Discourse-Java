/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostActions;
import com.infy.repository.PostActionsRepository;
import com.infy.service.PostActionsService;
import com.infy.service.dto.PostActionsDTO;
import com.infy.service.mapper.PostActionsMapper;

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
 * Integration tests for the {@link PostActionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostActionsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ACTION_TYPE_ID = 1L;
    private static final Long UPDATED_POST_ACTION_TYPE_ID = 2L;

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_RELATED_POST_ID = 1L;
    private static final Long UPDATED_RELATED_POST_ID = 2L;

    private static final Boolean DEFAULT_STAFF_TOOK_ACTION = false;
    private static final Boolean UPDATED_STAFF_TOOK_ACTION = true;

    private static final String DEFAULT_DEFERRED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEFERRED_BY_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TARGETS_TOPIC = false;
    private static final Boolean UPDATED_TARGETS_TOPIC = true;

    private static final Instant DEFAULT_AGREED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AGREED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AGREED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGREED_BY_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEFERRED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEFERRED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISAGREED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISAGREED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DISAGREED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DISAGREED_BY_ID = "BBBBBBBBBB";

    @Autowired
    private PostActionsRepository postActionsRepository;

    @Autowired
    private PostActionsMapper postActionsMapper;

    @Autowired
    private PostActionsService postActionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostActionsMockMvc;

    private PostActions postActions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostActions createEntity(EntityManager em) {
        PostActions postActions = new PostActions()
            .postId(DEFAULT_POST_ID)
            .userId(DEFAULT_USER_ID)
            .postActionTypeId(DEFAULT_POST_ACTION_TYPE_ID)
            .deletedAt(DEFAULT_DELETED_AT)
            .deletedById(DEFAULT_DELETED_BY_ID)
            .relatedPostId(DEFAULT_RELATED_POST_ID)
            .staffTookAction(DEFAULT_STAFF_TOOK_ACTION)
            .deferredById(DEFAULT_DEFERRED_BY_ID)
            .targetsTopic(DEFAULT_TARGETS_TOPIC)
            .agreedAt(DEFAULT_AGREED_AT)
            .agreedById(DEFAULT_AGREED_BY_ID)
            .deferredAt(DEFAULT_DEFERRED_AT)
            .disagreedAt(DEFAULT_DISAGREED_AT)
            .disagreedById(DEFAULT_DISAGREED_BY_ID);
        return postActions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostActions createUpdatedEntity(EntityManager em) {
        PostActions postActions = new PostActions()
            .postId(UPDATED_POST_ID)
            .userId(UPDATED_USER_ID)
            .postActionTypeId(UPDATED_POST_ACTION_TYPE_ID)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID)
            .relatedPostId(UPDATED_RELATED_POST_ID)
            .staffTookAction(UPDATED_STAFF_TOOK_ACTION)
            .deferredById(UPDATED_DEFERRED_BY_ID)
            .targetsTopic(UPDATED_TARGETS_TOPIC)
            .agreedAt(UPDATED_AGREED_AT)
            .agreedById(UPDATED_AGREED_BY_ID)
            .deferredAt(UPDATED_DEFERRED_AT)
            .disagreedAt(UPDATED_DISAGREED_AT)
            .disagreedById(UPDATED_DISAGREED_BY_ID);
        return postActions;
    }

    @BeforeEach
    public void initTest() {
        postActions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostActions() throws Exception {
        int databaseSizeBeforeCreate = postActionsRepository.findAll().size();
        // Create the PostActions
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);
        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostActions in the database
        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeCreate + 1);
        PostActions testPostActions = postActionsList.get(postActionsList.size() - 1);
        assertThat(testPostActions.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostActions.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPostActions.getPostActionTypeId()).isEqualTo(DEFAULT_POST_ACTION_TYPE_ID);
        assertThat(testPostActions.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testPostActions.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
        assertThat(testPostActions.getRelatedPostId()).isEqualTo(DEFAULT_RELATED_POST_ID);
        assertThat(testPostActions.isStaffTookAction()).isEqualTo(DEFAULT_STAFF_TOOK_ACTION);
        assertThat(testPostActions.getDeferredById()).isEqualTo(DEFAULT_DEFERRED_BY_ID);
        assertThat(testPostActions.isTargetsTopic()).isEqualTo(DEFAULT_TARGETS_TOPIC);
        assertThat(testPostActions.getAgreedAt()).isEqualTo(DEFAULT_AGREED_AT);
        assertThat(testPostActions.getAgreedById()).isEqualTo(DEFAULT_AGREED_BY_ID);
        assertThat(testPostActions.getDeferredAt()).isEqualTo(DEFAULT_DEFERRED_AT);
        assertThat(testPostActions.getDisagreedAt()).isEqualTo(DEFAULT_DISAGREED_AT);
        assertThat(testPostActions.getDisagreedById()).isEqualTo(DEFAULT_DISAGREED_BY_ID);
    }

    @Test
    @Transactional
    public void createPostActionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postActionsRepository.findAll().size();

        // Create the PostActions with an existing ID
        postActions.setId(1L);
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostActions in the database
        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionsRepository.findAll().size();
        // set the field null
        postActions.setPostId(null);

        // Create the PostActions, which fails.
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);


        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionsRepository.findAll().size();
        // set the field null
        postActions.setUserId(null);

        // Create the PostActions, which fails.
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);


        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostActionTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionsRepository.findAll().size();
        // set the field null
        postActions.setPostActionTypeId(null);

        // Create the PostActions, which fails.
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);


        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStaffTookActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionsRepository.findAll().size();
        // set the field null
        postActions.setStaffTookAction(null);

        // Create the PostActions, which fails.
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);


        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetsTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionsRepository.findAll().size();
        // set the field null
        postActions.setTargetsTopic(null);

        // Create the PostActions, which fails.
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);


        restPostActionsMockMvc.perform(post("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostActions() throws Exception {
        // Initialize the database
        postActionsRepository.saveAndFlush(postActions);

        // Get all the postActionsList
        restPostActionsMockMvc.perform(get("/api/post-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postActions.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].postActionTypeId").value(hasItem(DEFAULT_POST_ACTION_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)))
            .andExpect(jsonPath("$.[*].relatedPostId").value(hasItem(DEFAULT_RELATED_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].staffTookAction").value(hasItem(DEFAULT_STAFF_TOOK_ACTION.booleanValue())))
            .andExpect(jsonPath("$.[*].deferredById").value(hasItem(DEFAULT_DEFERRED_BY_ID)))
            .andExpect(jsonPath("$.[*].targetsTopic").value(hasItem(DEFAULT_TARGETS_TOPIC.booleanValue())))
            .andExpect(jsonPath("$.[*].agreedAt").value(hasItem(DEFAULT_AGREED_AT.toString())))
            .andExpect(jsonPath("$.[*].agreedById").value(hasItem(DEFAULT_AGREED_BY_ID)))
            .andExpect(jsonPath("$.[*].deferredAt").value(hasItem(DEFAULT_DEFERRED_AT.toString())))
            .andExpect(jsonPath("$.[*].disagreedAt").value(hasItem(DEFAULT_DISAGREED_AT.toString())))
            .andExpect(jsonPath("$.[*].disagreedById").value(hasItem(DEFAULT_DISAGREED_BY_ID)));
    }

    @Test
    @Transactional
    public void getPostActions() throws Exception {
        // Initialize the database
        postActionsRepository.saveAndFlush(postActions);

        // Get the postActions
        restPostActionsMockMvc.perform(get("/api/post-actions/{id}", postActions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postActions.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.postActionTypeId").value(DEFAULT_POST_ACTION_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.deletedById").value(DEFAULT_DELETED_BY_ID))
            .andExpect(jsonPath("$.relatedPostId").value(DEFAULT_RELATED_POST_ID.intValue()))
            .andExpect(jsonPath("$.staffTookAction").value(DEFAULT_STAFF_TOOK_ACTION.booleanValue()))
            .andExpect(jsonPath("$.deferredById").value(DEFAULT_DEFERRED_BY_ID))
            .andExpect(jsonPath("$.targetsTopic").value(DEFAULT_TARGETS_TOPIC.booleanValue()))
            .andExpect(jsonPath("$.agreedAt").value(DEFAULT_AGREED_AT.toString()))
            .andExpect(jsonPath("$.agreedById").value(DEFAULT_AGREED_BY_ID))
            .andExpect(jsonPath("$.deferredAt").value(DEFAULT_DEFERRED_AT.toString()))
            .andExpect(jsonPath("$.disagreedAt").value(DEFAULT_DISAGREED_AT.toString()))
            .andExpect(jsonPath("$.disagreedById").value(DEFAULT_DISAGREED_BY_ID));
    }
    @Test
    @Transactional
    public void getNonExistingPostActions() throws Exception {
        // Get the postActions
        restPostActionsMockMvc.perform(get("/api/post-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostActions() throws Exception {
        // Initialize the database
        postActionsRepository.saveAndFlush(postActions);

        int databaseSizeBeforeUpdate = postActionsRepository.findAll().size();

        // Update the postActions
        PostActions updatedPostActions = postActionsRepository.findById(postActions.getId()).get();
        // Disconnect from session so that the updates on updatedPostActions are not directly saved in db
        em.detach(updatedPostActions);
        updatedPostActions
            .postId(UPDATED_POST_ID)
            .userId(UPDATED_USER_ID)
            .postActionTypeId(UPDATED_POST_ACTION_TYPE_ID)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID)
            .relatedPostId(UPDATED_RELATED_POST_ID)
            .staffTookAction(UPDATED_STAFF_TOOK_ACTION)
            .deferredById(UPDATED_DEFERRED_BY_ID)
            .targetsTopic(UPDATED_TARGETS_TOPIC)
            .agreedAt(UPDATED_AGREED_AT)
            .agreedById(UPDATED_AGREED_BY_ID)
            .deferredAt(UPDATED_DEFERRED_AT)
            .disagreedAt(UPDATED_DISAGREED_AT)
            .disagreedById(UPDATED_DISAGREED_BY_ID);
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(updatedPostActions);

        restPostActionsMockMvc.perform(put("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isOk());

        // Validate the PostActions in the database
        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeUpdate);
        PostActions testPostActions = postActionsList.get(postActionsList.size() - 1);
        assertThat(testPostActions.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostActions.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPostActions.getPostActionTypeId()).isEqualTo(UPDATED_POST_ACTION_TYPE_ID);
        assertThat(testPostActions.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testPostActions.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
        assertThat(testPostActions.getRelatedPostId()).isEqualTo(UPDATED_RELATED_POST_ID);
        assertThat(testPostActions.isStaffTookAction()).isEqualTo(UPDATED_STAFF_TOOK_ACTION);
        assertThat(testPostActions.getDeferredById()).isEqualTo(UPDATED_DEFERRED_BY_ID);
        assertThat(testPostActions.isTargetsTopic()).isEqualTo(UPDATED_TARGETS_TOPIC);
        assertThat(testPostActions.getAgreedAt()).isEqualTo(UPDATED_AGREED_AT);
        assertThat(testPostActions.getAgreedById()).isEqualTo(UPDATED_AGREED_BY_ID);
        assertThat(testPostActions.getDeferredAt()).isEqualTo(UPDATED_DEFERRED_AT);
        assertThat(testPostActions.getDisagreedAt()).isEqualTo(UPDATED_DISAGREED_AT);
        assertThat(testPostActions.getDisagreedById()).isEqualTo(UPDATED_DISAGREED_BY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPostActions() throws Exception {
        int databaseSizeBeforeUpdate = postActionsRepository.findAll().size();

        // Create the PostActions
        PostActionsDTO postActionsDTO = postActionsMapper.toDto(postActions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostActionsMockMvc.perform(put("/api/post-actions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostActions in the database
        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostActions() throws Exception {
        // Initialize the database
        postActionsRepository.saveAndFlush(postActions);

        int databaseSizeBeforeDelete = postActionsRepository.findAll().size();

        // Delete the postActions
        restPostActionsMockMvc.perform(delete("/api/post-actions/{id}", postActions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostActions> postActionsList = postActionsRepository.findAll();
        assertThat(postActionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
