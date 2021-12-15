/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.BackupDraftPosts;
import com.infy.repository.BackupDraftPostsRepository;
import com.infy.service.BackupDraftPostsService;
import com.infy.service.dto.BackupDraftPostsDTO;
import com.infy.service.mapper.BackupDraftPostsMapper;

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
 * Integration tests for the {@link BackupDraftPostsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BackupDraftPostsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    @Autowired
    private BackupDraftPostsRepository backupDraftPostsRepository;

    @Autowired
    private BackupDraftPostsMapper backupDraftPostsMapper;

    @Autowired
    private BackupDraftPostsService backupDraftPostsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBackupDraftPostsMockMvc;

    private BackupDraftPosts backupDraftPosts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackupDraftPosts createEntity(EntityManager em) {
        BackupDraftPosts backupDraftPosts = new BackupDraftPosts()
            .userId(DEFAULT_USER_ID)
            .postId(DEFAULT_POST_ID)
            .key(DEFAULT_KEY);
        return backupDraftPosts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackupDraftPosts createUpdatedEntity(EntityManager em) {
        BackupDraftPosts backupDraftPosts = new BackupDraftPosts()
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .key(UPDATED_KEY);
        return backupDraftPosts;
    }

    @BeforeEach
    public void initTest() {
        backupDraftPosts = createEntity(em);
    }

    @Test
    @Transactional
    public void createBackupDraftPosts() throws Exception {
        int databaseSizeBeforeCreate = backupDraftPostsRepository.findAll().size();
        // Create the BackupDraftPosts
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(backupDraftPosts);
        restBackupDraftPostsMockMvc.perform(post("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isCreated());

        // Validate the BackupDraftPosts in the database
        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeCreate + 1);
        BackupDraftPosts testBackupDraftPosts = backupDraftPostsList.get(backupDraftPostsList.size() - 1);
        assertThat(testBackupDraftPosts.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBackupDraftPosts.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testBackupDraftPosts.getKey()).isEqualTo(DEFAULT_KEY);
    }

    @Test
    @Transactional
    public void createBackupDraftPostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backupDraftPostsRepository.findAll().size();

        // Create the BackupDraftPosts with an existing ID
        backupDraftPosts.setId(1L);
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(backupDraftPosts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackupDraftPostsMockMvc.perform(post("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackupDraftPosts in the database
        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = backupDraftPostsRepository.findAll().size();
        // set the field null
        backupDraftPosts.setUserId(null);

        // Create the BackupDraftPosts, which fails.
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(backupDraftPosts);


        restBackupDraftPostsMockMvc.perform(post("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isBadRequest());

        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = backupDraftPostsRepository.findAll().size();
        // set the field null
        backupDraftPosts.setPostId(null);

        // Create the BackupDraftPosts, which fails.
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(backupDraftPosts);


        restBackupDraftPostsMockMvc.perform(post("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isBadRequest());

        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = backupDraftPostsRepository.findAll().size();
        // set the field null
        backupDraftPosts.setKey(null);

        // Create the BackupDraftPosts, which fails.
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(backupDraftPosts);


        restBackupDraftPostsMockMvc.perform(post("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isBadRequest());

        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBackupDraftPosts() throws Exception {
        // Initialize the database
        backupDraftPostsRepository.saveAndFlush(backupDraftPosts);

        // Get all the backupDraftPostsList
        restBackupDraftPostsMockMvc.perform(get("/api/backup-draft-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backupDraftPosts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)));
    }

    @Test
    @Transactional
    public void getBackupDraftPosts() throws Exception {
        // Initialize the database
        backupDraftPostsRepository.saveAndFlush(backupDraftPosts);

        // Get the backupDraftPosts
        restBackupDraftPostsMockMvc.perform(get("/api/backup-draft-posts/{id}", backupDraftPosts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(backupDraftPosts.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY));
    }
    @Test
    @Transactional
    public void getNonExistingBackupDraftPosts() throws Exception {
        // Get the backupDraftPosts
        restBackupDraftPostsMockMvc.perform(get("/api/backup-draft-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackupDraftPosts() throws Exception {
        // Initialize the database
        backupDraftPostsRepository.saveAndFlush(backupDraftPosts);

        int databaseSizeBeforeUpdate = backupDraftPostsRepository.findAll().size();

        // Update the backupDraftPosts
        BackupDraftPosts updatedBackupDraftPosts = backupDraftPostsRepository.findById(backupDraftPosts.getId()).get();
        // Disconnect from session so that the updates on updatedBackupDraftPosts are not directly saved in db
        em.detach(updatedBackupDraftPosts);
        updatedBackupDraftPosts
            .userId(UPDATED_USER_ID)
            .postId(UPDATED_POST_ID)
            .key(UPDATED_KEY);
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(updatedBackupDraftPosts);

        restBackupDraftPostsMockMvc.perform(put("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isOk());

        // Validate the BackupDraftPosts in the database
        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeUpdate);
        BackupDraftPosts testBackupDraftPosts = backupDraftPostsList.get(backupDraftPostsList.size() - 1);
        assertThat(testBackupDraftPosts.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBackupDraftPosts.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testBackupDraftPosts.getKey()).isEqualTo(UPDATED_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingBackupDraftPosts() throws Exception {
        int databaseSizeBeforeUpdate = backupDraftPostsRepository.findAll().size();

        // Create the BackupDraftPosts
        BackupDraftPostsDTO backupDraftPostsDTO = backupDraftPostsMapper.toDto(backupDraftPosts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBackupDraftPostsMockMvc.perform(put("/api/backup-draft-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupDraftPostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackupDraftPosts in the database
        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBackupDraftPosts() throws Exception {
        // Initialize the database
        backupDraftPostsRepository.saveAndFlush(backupDraftPosts);

        int databaseSizeBeforeDelete = backupDraftPostsRepository.findAll().size();

        // Delete the backupDraftPosts
        restBackupDraftPostsMockMvc.perform(delete("/api/backup-draft-posts/{id}", backupDraftPosts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BackupDraftPosts> backupDraftPostsList = backupDraftPostsRepository.findAll();
        assertThat(backupDraftPostsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
