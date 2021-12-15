/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostRevisions;
import com.infy.repository.PostRevisionsRepository;
import com.infy.service.PostRevisionsService;
import com.infy.service.dto.PostRevisionsDTO;
import com.infy.service.mapper.PostRevisionsMapper;

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
 * Integration tests for the {@link PostRevisionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostRevisionsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_MODIFICATIONS = "AAAAAAAAAA";
    private static final String UPDATED_MODIFICATIONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Boolean DEFAULT_HIDDEN = false;
    private static final Boolean UPDATED_HIDDEN = true;

    @Autowired
    private PostRevisionsRepository postRevisionsRepository;

    @Autowired
    private PostRevisionsMapper postRevisionsMapper;

    @Autowired
    private PostRevisionsService postRevisionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostRevisionsMockMvc;

    private PostRevisions postRevisions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostRevisions createEntity(EntityManager em) {
        PostRevisions postRevisions = new PostRevisions()
            .userId(DEFAULT_USER_ID)
            .postId(DEFAULT_POST_ID)
            .modifications(DEFAULT_MODIFICATIONS)
            .number(DEFAULT_NUMBER)
            .hidden(DEFAULT_HIDDEN);
        return postRevisions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostRevisions createUpdatedEntity(EntityManager em) {
        PostRevisions postRevisions = new PostRevisions()
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .modifications(UPDATED_MODIFICATIONS)
            .number(UPDATED_NUMBER)
            .hidden(UPDATED_HIDDEN);
        return postRevisions;
    }

    @BeforeEach
    public void initTest() {
        postRevisions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostRevisions() throws Exception {
        int databaseSizeBeforeCreate = postRevisionsRepository.findAll().size();
        // Create the PostRevisions
        PostRevisionsDTO postRevisionsDTO = postRevisionsMapper.toDto(postRevisions);
        restPostRevisionsMockMvc.perform(post("/api/post-revisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRevisionsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostRevisions in the database
        List<PostRevisions> postRevisionsList = postRevisionsRepository.findAll();
        assertThat(postRevisionsList).hasSize(databaseSizeBeforeCreate + 1);
        PostRevisions testPostRevisions = postRevisionsList.get(postRevisionsList.size() - 1);
        assertThat(testPostRevisions.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPostRevisions.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostRevisions.getModifications()).isEqualTo(DEFAULT_MODIFICATIONS);
        assertThat(testPostRevisions.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPostRevisions.isHidden()).isEqualTo(DEFAULT_HIDDEN);
    }

    @Test
    @Transactional
    public void createPostRevisionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRevisionsRepository.findAll().size();

        // Create the PostRevisions with an existing ID
        postRevisions.setId(1L);
        PostRevisionsDTO postRevisionsDTO = postRevisionsMapper.toDto(postRevisions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostRevisionsMockMvc.perform(post("/api/post-revisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRevisionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostRevisions in the database
        List<PostRevisions> postRevisionsList = postRevisionsRepository.findAll();
        assertThat(postRevisionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRevisionsRepository.findAll().size();
        // set the field null
        postRevisions.setHidden(null);

        // Create the PostRevisions, which fails.
        PostRevisionsDTO postRevisionsDTO = postRevisionsMapper.toDto(postRevisions);


        restPostRevisionsMockMvc.perform(post("/api/post-revisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRevisionsDTO)))
            .andExpect(status().isBadRequest());

        List<PostRevisions> postRevisionsList = postRevisionsRepository.findAll();
        assertThat(postRevisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostRevisions() throws Exception {
        // Initialize the database
        postRevisionsRepository.saveAndFlush(postRevisions);

        // Get all the postRevisionsList
        restPostRevisionsMockMvc.perform(get("/api/post-revisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postRevisions.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifications").value(hasItem(DEFAULT_MODIFICATIONS)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].hidden").value(hasItem(DEFAULT_HIDDEN.booleanValue())));
    }

    @Test
    @Transactional
    public void getPostRevisions() throws Exception {
        // Initialize the database
        postRevisionsRepository.saveAndFlush(postRevisions);

        // Get the postRevisions
        restPostRevisionsMockMvc.perform(get("/api/post-revisions/{id}", postRevisions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postRevisions.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.modifications").value(DEFAULT_MODIFICATIONS))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.hidden").value(DEFAULT_HIDDEN.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPostRevisions() throws Exception {
        // Get the postRevisions
        restPostRevisionsMockMvc.perform(get("/api/post-revisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostRevisions() throws Exception {
        // Initialize the database
        postRevisionsRepository.saveAndFlush(postRevisions);

        int databaseSizeBeforeUpdate = postRevisionsRepository.findAll().size();

        // Update the postRevisions
        PostRevisions updatedPostRevisions = postRevisionsRepository.findById(postRevisions.getId()).get();
        // Disconnect from session so that the updates on updatedPostRevisions are not directly saved in db
        em.detach(updatedPostRevisions);
        updatedPostRevisions
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .modifications(UPDATED_MODIFICATIONS)
            .number(UPDATED_NUMBER)
            .hidden(UPDATED_HIDDEN);
        PostRevisionsDTO postRevisionsDTO = postRevisionsMapper.toDto(updatedPostRevisions);

        restPostRevisionsMockMvc.perform(put("/api/post-revisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRevisionsDTO)))
            .andExpect(status().isOk());

        // Validate the PostRevisions in the database
        List<PostRevisions> postRevisionsList = postRevisionsRepository.findAll();
        assertThat(postRevisionsList).hasSize(databaseSizeBeforeUpdate);
        PostRevisions testPostRevisions = postRevisionsList.get(postRevisionsList.size() - 1);
        assertThat(testPostRevisions.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPostRevisions.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostRevisions.getModifications()).isEqualTo(UPDATED_MODIFICATIONS);
        assertThat(testPostRevisions.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPostRevisions.isHidden()).isEqualTo(UPDATED_HIDDEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPostRevisions() throws Exception {
        int databaseSizeBeforeUpdate = postRevisionsRepository.findAll().size();

        // Create the PostRevisions
        PostRevisionsDTO postRevisionsDTO = postRevisionsMapper.toDto(postRevisions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostRevisionsMockMvc.perform(put("/api/post-revisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRevisionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostRevisions in the database
        List<PostRevisions> postRevisionsList = postRevisionsRepository.findAll();
        assertThat(postRevisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostRevisions() throws Exception {
        // Initialize the database
        postRevisionsRepository.saveAndFlush(postRevisions);

        int databaseSizeBeforeDelete = postRevisionsRepository.findAll().size();

        // Delete the postRevisions
        restPostRevisionsMockMvc.perform(delete("/api/post-revisions/{id}", postRevisions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostRevisions> postRevisionsList = postRevisionsRepository.findAll();
        assertThat(postRevisionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
