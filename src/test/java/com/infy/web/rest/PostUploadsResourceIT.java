/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostUploads;
import com.infy.repository.PostUploadsRepository;
import com.infy.service.PostUploadsService;
import com.infy.service.dto.PostUploadsDTO;
import com.infy.service.mapper.PostUploadsMapper;

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
 * Integration tests for the {@link PostUploadsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostUploadsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    @Autowired
    private PostUploadsRepository postUploadsRepository;

    @Autowired
    private PostUploadsMapper postUploadsMapper;

    @Autowired
    private PostUploadsService postUploadsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostUploadsMockMvc;

    private PostUploads postUploads;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostUploads createEntity(EntityManager em) {
        PostUploads postUploads = new PostUploads()
            .postId(DEFAULT_POST_ID)
            .uploadId(DEFAULT_UPLOAD_ID);
        return postUploads;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostUploads createUpdatedEntity(EntityManager em) {
        PostUploads postUploads = new PostUploads()
            .postId(UPDATED_POST_ID)
            .uploadId(UPDATED_UPLOAD_ID);
        return postUploads;
    }

    @BeforeEach
    public void initTest() {
        postUploads = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostUploads() throws Exception {
        int databaseSizeBeforeCreate = postUploadsRepository.findAll().size();
        // Create the PostUploads
        PostUploadsDTO postUploadsDTO = postUploadsMapper.toDto(postUploads);
        restPostUploadsMockMvc.perform(post("/api/post-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postUploadsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostUploads in the database
        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeCreate + 1);
        PostUploads testPostUploads = postUploadsList.get(postUploadsList.size() - 1);
        assertThat(testPostUploads.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostUploads.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void createPostUploadsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postUploadsRepository.findAll().size();

        // Create the PostUploads with an existing ID
        postUploads.setId(1L);
        PostUploadsDTO postUploadsDTO = postUploadsMapper.toDto(postUploads);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostUploadsMockMvc.perform(post("/api/post-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postUploadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostUploads in the database
        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postUploadsRepository.findAll().size();
        // set the field null
        postUploads.setPostId(null);

        // Create the PostUploads, which fails.
        PostUploadsDTO postUploadsDTO = postUploadsMapper.toDto(postUploads);


        restPostUploadsMockMvc.perform(post("/api/post-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postUploadsDTO)))
            .andExpect(status().isBadRequest());

        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUploadIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postUploadsRepository.findAll().size();
        // set the field null
        postUploads.setUploadId(null);

        // Create the PostUploads, which fails.
        PostUploadsDTO postUploadsDTO = postUploadsMapper.toDto(postUploads);


        restPostUploadsMockMvc.perform(post("/api/post-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postUploadsDTO)))
            .andExpect(status().isBadRequest());

        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostUploads() throws Exception {
        // Initialize the database
        postUploadsRepository.saveAndFlush(postUploads);

        // Get all the postUploadsList
        restPostUploadsMockMvc.perform(get("/api/post-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postUploads.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPostUploads() throws Exception {
        // Initialize the database
        postUploadsRepository.saveAndFlush(postUploads);

        // Get the postUploads
        restPostUploadsMockMvc.perform(get("/api/post-uploads/{id}", postUploads.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postUploads.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPostUploads() throws Exception {
        // Get the postUploads
        restPostUploadsMockMvc.perform(get("/api/post-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostUploads() throws Exception {
        // Initialize the database
        postUploadsRepository.saveAndFlush(postUploads);

        int databaseSizeBeforeUpdate = postUploadsRepository.findAll().size();

        // Update the postUploads
        PostUploads updatedPostUploads = postUploadsRepository.findById(postUploads.getId()).get();
        // Disconnect from session so that the updates on updatedPostUploads are not directly saved in db
        em.detach(updatedPostUploads);
        updatedPostUploads
            .postId(UPDATED_POST_ID)
            .uploadId(UPDATED_UPLOAD_ID);
        PostUploadsDTO postUploadsDTO = postUploadsMapper.toDto(updatedPostUploads);

        restPostUploadsMockMvc.perform(put("/api/post-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postUploadsDTO)))
            .andExpect(status().isOk());

        // Validate the PostUploads in the database
        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeUpdate);
        PostUploads testPostUploads = postUploadsList.get(postUploadsList.size() - 1);
        assertThat(testPostUploads.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostUploads.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPostUploads() throws Exception {
        int databaseSizeBeforeUpdate = postUploadsRepository.findAll().size();

        // Create the PostUploads
        PostUploadsDTO postUploadsDTO = postUploadsMapper.toDto(postUploads);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostUploadsMockMvc.perform(put("/api/post-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postUploadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostUploads in the database
        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostUploads() throws Exception {
        // Initialize the database
        postUploadsRepository.saveAndFlush(postUploads);

        int databaseSizeBeforeDelete = postUploadsRepository.findAll().size();

        // Delete the postUploads
        restPostUploadsMockMvc.perform(delete("/api/post-uploads/{id}", postUploads.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostUploads> postUploadsList = postUploadsRepository.findAll();
        assertThat(postUploadsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
