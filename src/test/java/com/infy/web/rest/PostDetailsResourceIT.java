/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostDetails;
import com.infy.repository.PostDetailsRepository;
import com.infy.service.PostDetailsService;
import com.infy.service.dto.PostDetailsDTO;
import com.infy.service.mapper.PostDetailsMapper;

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
 * Integration tests for the {@link PostDetailsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostDetailsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    @Autowired
    private PostDetailsMapper postDetailsMapper;

    @Autowired
    private PostDetailsService postDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostDetailsMockMvc;

    private PostDetails postDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostDetails createEntity(EntityManager em) {
        PostDetails postDetails = new PostDetails()
            .postId(DEFAULT_POST_ID)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE)
            .extra(DEFAULT_EXTRA);
        return postDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostDetails createUpdatedEntity(EntityManager em) {
        PostDetails postDetails = new PostDetails()
            .postId(UPDATED_POST_ID)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .extra(UPDATED_EXTRA);
        return postDetails;
    }

    @BeforeEach
    public void initTest() {
        postDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostDetails() throws Exception {
        int databaseSizeBeforeCreate = postDetailsRepository.findAll().size();
        // Create the PostDetails
        PostDetailsDTO postDetailsDTO = postDetailsMapper.toDto(postDetails);
        restPostDetailsMockMvc.perform(post("/api/post-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostDetails in the database
        List<PostDetails> postDetailsList = postDetailsRepository.findAll();
        assertThat(postDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PostDetails testPostDetails = postDetailsList.get(postDetailsList.size() - 1);
        assertThat(testPostDetails.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostDetails.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testPostDetails.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPostDetails.getExtra()).isEqualTo(DEFAULT_EXTRA);
    }

    @Test
    @Transactional
    public void createPostDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postDetailsRepository.findAll().size();

        // Create the PostDetails with an existing ID
        postDetails.setId(1L);
        PostDetailsDTO postDetailsDTO = postDetailsMapper.toDto(postDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostDetailsMockMvc.perform(post("/api/post-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostDetails in the database
        List<PostDetails> postDetailsList = postDetailsRepository.findAll();
        assertThat(postDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostDetails() throws Exception {
        // Initialize the database
        postDetailsRepository.saveAndFlush(postDetails);

        // Get all the postDetailsList
        restPostDetailsMockMvc.perform(get("/api/post-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA)));
    }

    @Test
    @Transactional
    public void getPostDetails() throws Exception {
        // Initialize the database
        postDetailsRepository.saveAndFlush(postDetails);

        // Get the postDetails
        restPostDetailsMockMvc.perform(get("/api/post-details/{id}", postDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postDetails.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA));
    }
    @Test
    @Transactional
    public void getNonExistingPostDetails() throws Exception {
        // Get the postDetails
        restPostDetailsMockMvc.perform(get("/api/post-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostDetails() throws Exception {
        // Initialize the database
        postDetailsRepository.saveAndFlush(postDetails);

        int databaseSizeBeforeUpdate = postDetailsRepository.findAll().size();

        // Update the postDetails
        PostDetails updatedPostDetails = postDetailsRepository.findById(postDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPostDetails are not directly saved in db
        em.detach(updatedPostDetails);
        updatedPostDetails
            .postId(UPDATED_POST_ID)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .extra(UPDATED_EXTRA);
        PostDetailsDTO postDetailsDTO = postDetailsMapper.toDto(updatedPostDetails);

        restPostDetailsMockMvc.perform(put("/api/post-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the PostDetails in the database
        List<PostDetails> postDetailsList = postDetailsRepository.findAll();
        assertThat(postDetailsList).hasSize(databaseSizeBeforeUpdate);
        PostDetails testPostDetails = postDetailsList.get(postDetailsList.size() - 1);
        assertThat(testPostDetails.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostDetails.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPostDetails.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPostDetails.getExtra()).isEqualTo(UPDATED_EXTRA);
    }

    @Test
    @Transactional
    public void updateNonExistingPostDetails() throws Exception {
        int databaseSizeBeforeUpdate = postDetailsRepository.findAll().size();

        // Create the PostDetails
        PostDetailsDTO postDetailsDTO = postDetailsMapper.toDto(postDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostDetailsMockMvc.perform(put("/api/post-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostDetails in the database
        List<PostDetails> postDetailsList = postDetailsRepository.findAll();
        assertThat(postDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostDetails() throws Exception {
        // Initialize the database
        postDetailsRepository.saveAndFlush(postDetails);

        int databaseSizeBeforeDelete = postDetailsRepository.findAll().size();

        // Delete the postDetails
        restPostDetailsMockMvc.perform(delete("/api/post-details/{id}", postDetails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostDetails> postDetailsList = postDetailsRepository.findAll();
        assertThat(postDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
