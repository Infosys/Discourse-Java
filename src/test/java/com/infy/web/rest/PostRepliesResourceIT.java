/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostReplies;
import com.infy.repository.PostRepliesRepository;
import com.infy.service.PostRepliesService;
import com.infy.service.dto.PostRepliesDTO;
import com.infy.service.mapper.PostRepliesMapper;

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
 * Integration tests for the {@link PostRepliesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostRepliesResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_REPLY_POST_ID = 1L;
    private static final Long UPDATED_REPLY_POST_ID = 2L;

    @Autowired
    private PostRepliesRepository postRepliesRepository;

    @Autowired
    private PostRepliesMapper postRepliesMapper;

    @Autowired
    private PostRepliesService postRepliesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostRepliesMockMvc;

    private PostReplies postReplies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostReplies createEntity(EntityManager em) {
        PostReplies postReplies = new PostReplies()
            .postId(DEFAULT_POST_ID)
            .replyPostId(DEFAULT_REPLY_POST_ID);
        return postReplies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostReplies createUpdatedEntity(EntityManager em) {
        PostReplies postReplies = new PostReplies()
            .postId(UPDATED_POST_ID)
            .replyPostId(UPDATED_REPLY_POST_ID);
        return postReplies;
    }

    @BeforeEach
    public void initTest() {
        postReplies = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostReplies() throws Exception {
        int databaseSizeBeforeCreate = postRepliesRepository.findAll().size();
        // Create the PostReplies
        PostRepliesDTO postRepliesDTO = postRepliesMapper.toDto(postReplies);
        restPostRepliesMockMvc.perform(post("/api/post-replies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRepliesDTO)))
            .andExpect(status().isCreated());

        // Validate the PostReplies in the database
        List<PostReplies> postRepliesList = postRepliesRepository.findAll();
        assertThat(postRepliesList).hasSize(databaseSizeBeforeCreate + 1);
        PostReplies testPostReplies = postRepliesList.get(postRepliesList.size() - 1);
        assertThat(testPostReplies.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostReplies.getReplyPostId()).isEqualTo(DEFAULT_REPLY_POST_ID);
    }

    @Test
    @Transactional
    public void createPostRepliesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepliesRepository.findAll().size();

        // Create the PostReplies with an existing ID
        postReplies.setId(1L);
        PostRepliesDTO postRepliesDTO = postRepliesMapper.toDto(postReplies);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostRepliesMockMvc.perform(post("/api/post-replies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRepliesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostReplies in the database
        List<PostReplies> postRepliesList = postRepliesRepository.findAll();
        assertThat(postRepliesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostReplies() throws Exception {
        // Initialize the database
        postRepliesRepository.saveAndFlush(postReplies);

        // Get all the postRepliesList
        restPostRepliesMockMvc.perform(get("/api/post-replies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postReplies.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].replyPostId").value(hasItem(DEFAULT_REPLY_POST_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPostReplies() throws Exception {
        // Initialize the database
        postRepliesRepository.saveAndFlush(postReplies);

        // Get the postReplies
        restPostRepliesMockMvc.perform(get("/api/post-replies/{id}", postReplies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postReplies.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.replyPostId").value(DEFAULT_REPLY_POST_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPostReplies() throws Exception {
        // Get the postReplies
        restPostRepliesMockMvc.perform(get("/api/post-replies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostReplies() throws Exception {
        // Initialize the database
        postRepliesRepository.saveAndFlush(postReplies);

        int databaseSizeBeforeUpdate = postRepliesRepository.findAll().size();

        // Update the postReplies
        PostReplies updatedPostReplies = postRepliesRepository.findById(postReplies.getId()).get();
        // Disconnect from session so that the updates on updatedPostReplies are not directly saved in db
        em.detach(updatedPostReplies);
        updatedPostReplies
            .postId(UPDATED_POST_ID)
            .replyPostId(UPDATED_REPLY_POST_ID);
        PostRepliesDTO postRepliesDTO = postRepliesMapper.toDto(updatedPostReplies);

        restPostRepliesMockMvc.perform(put("/api/post-replies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRepliesDTO)))
            .andExpect(status().isOk());

        // Validate the PostReplies in the database
        List<PostReplies> postRepliesList = postRepliesRepository.findAll();
        assertThat(postRepliesList).hasSize(databaseSizeBeforeUpdate);
        PostReplies testPostReplies = postRepliesList.get(postRepliesList.size() - 1);
        assertThat(testPostReplies.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostReplies.getReplyPostId()).isEqualTo(UPDATED_REPLY_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPostReplies() throws Exception {
        int databaseSizeBeforeUpdate = postRepliesRepository.findAll().size();

        // Create the PostReplies
        PostRepliesDTO postRepliesDTO = postRepliesMapper.toDto(postReplies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostRepliesMockMvc.perform(put("/api/post-replies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postRepliesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostReplies in the database
        List<PostReplies> postRepliesList = postRepliesRepository.findAll();
        assertThat(postRepliesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostReplies() throws Exception {
        // Initialize the database
        postRepliesRepository.saveAndFlush(postReplies);

        int databaseSizeBeforeDelete = postRepliesRepository.findAll().size();

        // Delete the postReplies
        restPostRepliesMockMvc.perform(delete("/api/post-replies/{id}", postReplies.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostReplies> postRepliesList = postRepliesRepository.findAll();
        assertThat(postRepliesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
