/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostReplyKeys;
import com.infy.repository.PostReplyKeysRepository;
import com.infy.service.PostReplyKeysService;
import com.infy.service.dto.PostReplyKeysDTO;
import com.infy.service.mapper.PostReplyKeysMapper;

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
 * Integration tests for the {@link PostReplyKeysResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostReplyKeysResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_REPLY_KEY = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_KEY = "BBBBBBBBBB";

    @Autowired
    private PostReplyKeysRepository postReplyKeysRepository;

    @Autowired
    private PostReplyKeysMapper postReplyKeysMapper;

    @Autowired
    private PostReplyKeysService postReplyKeysService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostReplyKeysMockMvc;

    private PostReplyKeys postReplyKeys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostReplyKeys createEntity(EntityManager em) {
        PostReplyKeys postReplyKeys = new PostReplyKeys()
            .userId(DEFAULT_USER_ID)
            .postId(DEFAULT_POST_ID)
            .replyKey(DEFAULT_REPLY_KEY);
        return postReplyKeys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostReplyKeys createUpdatedEntity(EntityManager em) {
        PostReplyKeys postReplyKeys = new PostReplyKeys()
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .replyKey(UPDATED_REPLY_KEY);
        return postReplyKeys;
    }

    @BeforeEach
    public void initTest() {
        postReplyKeys = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostReplyKeys() throws Exception {
        int databaseSizeBeforeCreate = postReplyKeysRepository.findAll().size();
        // Create the PostReplyKeys
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(postReplyKeys);
        restPostReplyKeysMockMvc.perform(post("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isCreated());

        // Validate the PostReplyKeys in the database
        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeCreate + 1);
        PostReplyKeys testPostReplyKeys = postReplyKeysList.get(postReplyKeysList.size() - 1);
        assertThat(testPostReplyKeys.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPostReplyKeys.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostReplyKeys.getReplyKey()).isEqualTo(DEFAULT_REPLY_KEY);
    }

    @Test
    @Transactional
    public void createPostReplyKeysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postReplyKeysRepository.findAll().size();

        // Create the PostReplyKeys with an existing ID
        postReplyKeys.setId(1L);
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(postReplyKeys);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostReplyKeysMockMvc.perform(post("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostReplyKeys in the database
        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postReplyKeysRepository.findAll().size();
        // set the field null
        postReplyKeys.setUserId(null);

        // Create the PostReplyKeys, which fails.
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(postReplyKeys);


        restPostReplyKeysMockMvc.perform(post("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isBadRequest());

        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postReplyKeysRepository.findAll().size();
        // set the field null
        postReplyKeys.setPostId(null);

        // Create the PostReplyKeys, which fails.
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(postReplyKeys);


        restPostReplyKeysMockMvc.perform(post("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isBadRequest());

        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReplyKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = postReplyKeysRepository.findAll().size();
        // set the field null
        postReplyKeys.setReplyKey(null);

        // Create the PostReplyKeys, which fails.
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(postReplyKeys);


        restPostReplyKeysMockMvc.perform(post("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isBadRequest());

        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostReplyKeys() throws Exception {
        // Initialize the database
        postReplyKeysRepository.saveAndFlush(postReplyKeys);

        // Get all the postReplyKeysList
        restPostReplyKeysMockMvc.perform(get("/api/post-reply-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postReplyKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].replyKey").value(hasItem(DEFAULT_REPLY_KEY)));
    }

    @Test
    @Transactional
    public void getPostReplyKeys() throws Exception {
        // Initialize the database
        postReplyKeysRepository.saveAndFlush(postReplyKeys);

        // Get the postReplyKeys
        restPostReplyKeysMockMvc.perform(get("/api/post-reply-keys/{id}", postReplyKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postReplyKeys.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.replyKey").value(DEFAULT_REPLY_KEY));
    }
    @Test
    @Transactional
    public void getNonExistingPostReplyKeys() throws Exception {
        // Get the postReplyKeys
        restPostReplyKeysMockMvc.perform(get("/api/post-reply-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostReplyKeys() throws Exception {
        // Initialize the database
        postReplyKeysRepository.saveAndFlush(postReplyKeys);

        int databaseSizeBeforeUpdate = postReplyKeysRepository.findAll().size();

        // Update the postReplyKeys
        PostReplyKeys updatedPostReplyKeys = postReplyKeysRepository.findById(postReplyKeys.getId()).get();
        // Disconnect from session so that the updates on updatedPostReplyKeys are not directly saved in db
        em.detach(updatedPostReplyKeys);
        updatedPostReplyKeys
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .replyKey(UPDATED_REPLY_KEY);
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(updatedPostReplyKeys);

        restPostReplyKeysMockMvc.perform(put("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isOk());

        // Validate the PostReplyKeys in the database
        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeUpdate);
        PostReplyKeys testPostReplyKeys = postReplyKeysList.get(postReplyKeysList.size() - 1);
        assertThat(testPostReplyKeys.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPostReplyKeys.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostReplyKeys.getReplyKey()).isEqualTo(UPDATED_REPLY_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingPostReplyKeys() throws Exception {
        int databaseSizeBeforeUpdate = postReplyKeysRepository.findAll().size();

        // Create the PostReplyKeys
        PostReplyKeysDTO postReplyKeysDTO = postReplyKeysMapper.toDto(postReplyKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostReplyKeysMockMvc.perform(put("/api/post-reply-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postReplyKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostReplyKeys in the database
        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostReplyKeys() throws Exception {
        // Initialize the database
        postReplyKeysRepository.saveAndFlush(postReplyKeys);

        int databaseSizeBeforeDelete = postReplyKeysRepository.findAll().size();

        // Delete the postReplyKeys
        restPostReplyKeysMockMvc.perform(delete("/api/post-reply-keys/{id}", postReplyKeys.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostReplyKeys> postReplyKeysList = postReplyKeysRepository.findAll();
        assertThat(postReplyKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
