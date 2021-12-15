/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostActionTypes;
import com.infy.repository.PostActionTypesRepository;
import com.infy.service.PostActionTypesService;
import com.infy.service.dto.PostActionTypesDTO;
import com.infy.service.mapper.PostActionTypesMapper;

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
 * Integration tests for the {@link PostActionTypesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostActionTypesResourceIT {

    private static final String DEFAULT_NAME_KEY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_FLAG = false;
    private static final Boolean UPDATED_IS_FLAG = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Double DEFAULT_SCORE_BONUS = 1D;
    private static final Double UPDATED_SCORE_BONUS = 2D;

    private static final Integer DEFAULT_REVIEWABLE_PRIORITY = 1;
    private static final Integer UPDATED_REVIEWABLE_PRIORITY = 2;

    @Autowired
    private PostActionTypesRepository postActionTypesRepository;

    @Autowired
    private PostActionTypesMapper postActionTypesMapper;

    @Autowired
    private PostActionTypesService postActionTypesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostActionTypesMockMvc;

    private PostActionTypes postActionTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostActionTypes createEntity(EntityManager em) {
        PostActionTypes postActionTypes = new PostActionTypes()
            .nameKey(DEFAULT_NAME_KEY)
            .isFlag(DEFAULT_IS_FLAG)
            .icon(DEFAULT_ICON)
            .position(DEFAULT_POSITION)
            .scoreBonus(DEFAULT_SCORE_BONUS)
            .reviewablePriority(DEFAULT_REVIEWABLE_PRIORITY);
        return postActionTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostActionTypes createUpdatedEntity(EntityManager em) {
        PostActionTypes postActionTypes = new PostActionTypes()
            .nameKey(UPDATED_NAME_KEY)
            .isFlag(UPDATED_IS_FLAG)
            .icon(UPDATED_ICON)
            .position(UPDATED_POSITION)
            .scoreBonus(UPDATED_SCORE_BONUS)
            .reviewablePriority(UPDATED_REVIEWABLE_PRIORITY);
        return postActionTypes;
    }

    @BeforeEach
    public void initTest() {
        postActionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostActionTypes() throws Exception {
        int databaseSizeBeforeCreate = postActionTypesRepository.findAll().size();
        // Create the PostActionTypes
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);
        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the PostActionTypes in the database
        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        PostActionTypes testPostActionTypes = postActionTypesList.get(postActionTypesList.size() - 1);
        assertThat(testPostActionTypes.getNameKey()).isEqualTo(DEFAULT_NAME_KEY);
        assertThat(testPostActionTypes.isIsFlag()).isEqualTo(DEFAULT_IS_FLAG);
        assertThat(testPostActionTypes.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testPostActionTypes.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testPostActionTypes.getScoreBonus()).isEqualTo(DEFAULT_SCORE_BONUS);
        assertThat(testPostActionTypes.getReviewablePriority()).isEqualTo(DEFAULT_REVIEWABLE_PRIORITY);
    }

    @Test
    @Transactional
    public void createPostActionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postActionTypesRepository.findAll().size();

        // Create the PostActionTypes with an existing ID
        postActionTypes.setId(1L);
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostActionTypes in the database
        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionTypesRepository.findAll().size();
        // set the field null
        postActionTypes.setNameKey(null);

        // Create the PostActionTypes, which fails.
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);


        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionTypesRepository.findAll().size();
        // set the field null
        postActionTypes.setIsFlag(null);

        // Create the PostActionTypes, which fails.
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);


        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionTypesRepository.findAll().size();
        // set the field null
        postActionTypes.setPosition(null);

        // Create the PostActionTypes, which fails.
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);


        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreBonusIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionTypesRepository.findAll().size();
        // set the field null
        postActionTypes.setScoreBonus(null);

        // Create the PostActionTypes, which fails.
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);


        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewablePriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = postActionTypesRepository.findAll().size();
        // set the field null
        postActionTypes.setReviewablePriority(null);

        // Create the PostActionTypes, which fails.
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);


        restPostActionTypesMockMvc.perform(post("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostActionTypes() throws Exception {
        // Initialize the database
        postActionTypesRepository.saveAndFlush(postActionTypes);

        // Get all the postActionTypesList
        restPostActionTypesMockMvc.perform(get("/api/post-action-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postActionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameKey").value(hasItem(DEFAULT_NAME_KEY)))
            .andExpect(jsonPath("$.[*].isFlag").value(hasItem(DEFAULT_IS_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].scoreBonus").value(hasItem(DEFAULT_SCORE_BONUS.doubleValue())))
            .andExpect(jsonPath("$.[*].reviewablePriority").value(hasItem(DEFAULT_REVIEWABLE_PRIORITY)));
    }

    @Test
    @Transactional
    public void getPostActionTypes() throws Exception {
        // Initialize the database
        postActionTypesRepository.saveAndFlush(postActionTypes);

        // Get the postActionTypes
        restPostActionTypesMockMvc.perform(get("/api/post-action-types/{id}", postActionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postActionTypes.getId().intValue()))
            .andExpect(jsonPath("$.nameKey").value(DEFAULT_NAME_KEY))
            .andExpect(jsonPath("$.isFlag").value(DEFAULT_IS_FLAG.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.scoreBonus").value(DEFAULT_SCORE_BONUS.doubleValue()))
            .andExpect(jsonPath("$.reviewablePriority").value(DEFAULT_REVIEWABLE_PRIORITY));
    }
    @Test
    @Transactional
    public void getNonExistingPostActionTypes() throws Exception {
        // Get the postActionTypes
        restPostActionTypesMockMvc.perform(get("/api/post-action-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostActionTypes() throws Exception {
        // Initialize the database
        postActionTypesRepository.saveAndFlush(postActionTypes);

        int databaseSizeBeforeUpdate = postActionTypesRepository.findAll().size();

        // Update the postActionTypes
        PostActionTypes updatedPostActionTypes = postActionTypesRepository.findById(postActionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedPostActionTypes are not directly saved in db
        em.detach(updatedPostActionTypes);
        updatedPostActionTypes
            .nameKey(UPDATED_NAME_KEY)
            .isFlag(UPDATED_IS_FLAG)
            .icon(UPDATED_ICON)
            .position(UPDATED_POSITION)
            .scoreBonus(UPDATED_SCORE_BONUS)
            .reviewablePriority(UPDATED_REVIEWABLE_PRIORITY);
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(updatedPostActionTypes);

        restPostActionTypesMockMvc.perform(put("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the PostActionTypes in the database
        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeUpdate);
        PostActionTypes testPostActionTypes = postActionTypesList.get(postActionTypesList.size() - 1);
        assertThat(testPostActionTypes.getNameKey()).isEqualTo(UPDATED_NAME_KEY);
        assertThat(testPostActionTypes.isIsFlag()).isEqualTo(UPDATED_IS_FLAG);
        assertThat(testPostActionTypes.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testPostActionTypes.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testPostActionTypes.getScoreBonus()).isEqualTo(UPDATED_SCORE_BONUS);
        assertThat(testPostActionTypes.getReviewablePriority()).isEqualTo(UPDATED_REVIEWABLE_PRIORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPostActionTypes() throws Exception {
        int databaseSizeBeforeUpdate = postActionTypesRepository.findAll().size();

        // Create the PostActionTypes
        PostActionTypesDTO postActionTypesDTO = postActionTypesMapper.toDto(postActionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostActionTypesMockMvc.perform(put("/api/post-action-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postActionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostActionTypes in the database
        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostActionTypes() throws Exception {
        // Initialize the database
        postActionTypesRepository.saveAndFlush(postActionTypes);

        int databaseSizeBeforeDelete = postActionTypesRepository.findAll().size();

        // Delete the postActionTypes
        restPostActionTypesMockMvc.perform(delete("/api/post-action-types/{id}", postActionTypes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostActionTypes> postActionTypesList = postActionTypesRepository.findAll();
        assertThat(postActionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
