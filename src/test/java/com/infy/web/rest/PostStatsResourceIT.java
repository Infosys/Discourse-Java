/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostStats;
import com.infy.repository.PostStatsRepository;
import com.infy.service.PostStatsService;
import com.infy.service.dto.PostStatsDTO;
import com.infy.service.mapper.PostStatsMapper;

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
 * Integration tests for the {@link PostStatsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostStatsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Integer DEFAULT_DRAFTS_SAVED = 1;
    private static final Integer UPDATED_DRAFTS_SAVED = 2;

    private static final Integer DEFAULT_TYPING_DURATION_MSECS = 1;
    private static final Integer UPDATED_TYPING_DURATION_MSECS = 2;

    private static final Integer DEFAULT_COMPOSER_OPEN_DURATION_MSECS = 1;
    private static final Integer UPDATED_COMPOSER_OPEN_DURATION_MSECS = 2;

    @Autowired
    private PostStatsRepository postStatsRepository;

    @Autowired
    private PostStatsMapper postStatsMapper;

    @Autowired
    private PostStatsService postStatsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostStatsMockMvc;

    private PostStats postStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostStats createEntity(EntityManager em) {
        PostStats postStats = new PostStats()
            .postId(DEFAULT_POST_ID)
            .draftsSaved(DEFAULT_DRAFTS_SAVED)
            .typingDurationMsecs(DEFAULT_TYPING_DURATION_MSECS)
            .composerOpenDurationMsecs(DEFAULT_COMPOSER_OPEN_DURATION_MSECS);
        return postStats;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostStats createUpdatedEntity(EntityManager em) {
        PostStats postStats = new PostStats()
            .postId(UPDATED_POST_ID)
            .draftsSaved(UPDATED_DRAFTS_SAVED)
            .typingDurationMsecs(UPDATED_TYPING_DURATION_MSECS)
            .composerOpenDurationMsecs(UPDATED_COMPOSER_OPEN_DURATION_MSECS);
        return postStats;
    }

    @BeforeEach
    public void initTest() {
        postStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostStats() throws Exception {
        int databaseSizeBeforeCreate = postStatsRepository.findAll().size();
        // Create the PostStats
        PostStatsDTO postStatsDTO = postStatsMapper.toDto(postStats);
        restPostStatsMockMvc.perform(post("/api/post-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostStats in the database
        List<PostStats> postStatsList = postStatsRepository.findAll();
        assertThat(postStatsList).hasSize(databaseSizeBeforeCreate + 1);
        PostStats testPostStats = postStatsList.get(postStatsList.size() - 1);
        assertThat(testPostStats.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostStats.getDraftsSaved()).isEqualTo(DEFAULT_DRAFTS_SAVED);
        assertThat(testPostStats.getTypingDurationMsecs()).isEqualTo(DEFAULT_TYPING_DURATION_MSECS);
        assertThat(testPostStats.getComposerOpenDurationMsecs()).isEqualTo(DEFAULT_COMPOSER_OPEN_DURATION_MSECS);
    }

    @Test
    @Transactional
    public void createPostStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postStatsRepository.findAll().size();

        // Create the PostStats with an existing ID
        postStats.setId(1L);
        PostStatsDTO postStatsDTO = postStatsMapper.toDto(postStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostStatsMockMvc.perform(post("/api/post-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostStats in the database
        List<PostStats> postStatsList = postStatsRepository.findAll();
        assertThat(postStatsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostStats() throws Exception {
        // Initialize the database
        postStatsRepository.saveAndFlush(postStats);

        // Get all the postStatsList
        restPostStatsMockMvc.perform(get("/api/post-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].draftsSaved").value(hasItem(DEFAULT_DRAFTS_SAVED)))
            .andExpect(jsonPath("$.[*].typingDurationMsecs").value(hasItem(DEFAULT_TYPING_DURATION_MSECS)))
            .andExpect(jsonPath("$.[*].composerOpenDurationMsecs").value(hasItem(DEFAULT_COMPOSER_OPEN_DURATION_MSECS)));
    }

    @Test
    @Transactional
    public void getPostStats() throws Exception {
        // Initialize the database
        postStatsRepository.saveAndFlush(postStats);

        // Get the postStats
        restPostStatsMockMvc.perform(get("/api/post-stats/{id}", postStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postStats.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.draftsSaved").value(DEFAULT_DRAFTS_SAVED))
            .andExpect(jsonPath("$.typingDurationMsecs").value(DEFAULT_TYPING_DURATION_MSECS))
            .andExpect(jsonPath("$.composerOpenDurationMsecs").value(DEFAULT_COMPOSER_OPEN_DURATION_MSECS));
    }
    @Test
    @Transactional
    public void getNonExistingPostStats() throws Exception {
        // Get the postStats
        restPostStatsMockMvc.perform(get("/api/post-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostStats() throws Exception {
        // Initialize the database
        postStatsRepository.saveAndFlush(postStats);

        int databaseSizeBeforeUpdate = postStatsRepository.findAll().size();

        // Update the postStats
        PostStats updatedPostStats = postStatsRepository.findById(postStats.getId()).get();
        // Disconnect from session so that the updates on updatedPostStats are not directly saved in db
        em.detach(updatedPostStats);
        updatedPostStats
            .postId(UPDATED_POST_ID)
            .draftsSaved(UPDATED_DRAFTS_SAVED)
            .typingDurationMsecs(UPDATED_TYPING_DURATION_MSECS)
            .composerOpenDurationMsecs(UPDATED_COMPOSER_OPEN_DURATION_MSECS);
        PostStatsDTO postStatsDTO = postStatsMapper.toDto(updatedPostStats);

        restPostStatsMockMvc.perform(put("/api/post-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postStatsDTO)))
            .andExpect(status().isOk());

        // Validate the PostStats in the database
        List<PostStats> postStatsList = postStatsRepository.findAll();
        assertThat(postStatsList).hasSize(databaseSizeBeforeUpdate);
        PostStats testPostStats = postStatsList.get(postStatsList.size() - 1);
        assertThat(testPostStats.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostStats.getDraftsSaved()).isEqualTo(UPDATED_DRAFTS_SAVED);
        assertThat(testPostStats.getTypingDurationMsecs()).isEqualTo(UPDATED_TYPING_DURATION_MSECS);
        assertThat(testPostStats.getComposerOpenDurationMsecs()).isEqualTo(UPDATED_COMPOSER_OPEN_DURATION_MSECS);
    }

    @Test
    @Transactional
    public void updateNonExistingPostStats() throws Exception {
        int databaseSizeBeforeUpdate = postStatsRepository.findAll().size();

        // Create the PostStats
        PostStatsDTO postStatsDTO = postStatsMapper.toDto(postStats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostStatsMockMvc.perform(put("/api/post-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostStats in the database
        List<PostStats> postStatsList = postStatsRepository.findAll();
        assertThat(postStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostStats() throws Exception {
        // Initialize the database
        postStatsRepository.saveAndFlush(postStats);

        int databaseSizeBeforeDelete = postStatsRepository.findAll().size();

        // Delete the postStats
        restPostStatsMockMvc.perform(delete("/api/post-stats/{id}", postStats.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostStats> postStatsList = postStatsRepository.findAll();
        assertThat(postStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
