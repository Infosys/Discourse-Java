/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostTimings;
import com.infy.repository.PostTimingsRepository;
import com.infy.service.PostTimingsService;
import com.infy.service.dto.PostTimingsDTO;
import com.infy.service.mapper.PostTimingsMapper;

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
 * Integration tests for the {@link PostTimingsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostTimingsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Integer DEFAULT_POST_NUMBER = 1;
    private static final Integer UPDATED_POST_NUMBER = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_MSECS = 1;
    private static final Integer UPDATED_MSECS = 2;

    @Autowired
    private PostTimingsRepository postTimingsRepository;

    @Autowired
    private PostTimingsMapper postTimingsMapper;

    @Autowired
    private PostTimingsService postTimingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostTimingsMockMvc;

    private PostTimings postTimings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostTimings createEntity(EntityManager em) {
        PostTimings postTimings = new PostTimings()
            .topicId(DEFAULT_TOPIC_ID)
            .postNumber(DEFAULT_POST_NUMBER)
            .userId(DEFAULT_USER_ID)
            .msecs(DEFAULT_MSECS);
        return postTimings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostTimings createUpdatedEntity(EntityManager em) {
        PostTimings postTimings = new PostTimings()
            .topicId(UPDATED_TOPIC_ID)
            .postNumber(UPDATED_POST_NUMBER)
            .userId(UPDATED_USER_ID)
            .msecs(UPDATED_MSECS);
        return postTimings;
    }

    @BeforeEach
    public void initTest() {
        postTimings = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostTimings() throws Exception {
        int databaseSizeBeforeCreate = postTimingsRepository.findAll().size();
        // Create the PostTimings
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);
        restPostTimingsMockMvc.perform(post("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostTimings in the database
        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeCreate + 1);
        PostTimings testPostTimings = postTimingsList.get(postTimingsList.size() - 1);
        assertThat(testPostTimings.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testPostTimings.getPostNumber()).isEqualTo(DEFAULT_POST_NUMBER);
        assertThat(testPostTimings.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPostTimings.getMsecs()).isEqualTo(DEFAULT_MSECS);
    }

    @Test
    @Transactional
    public void createPostTimingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postTimingsRepository.findAll().size();

        // Create the PostTimings with an existing ID
        postTimings.setId(1L);
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostTimingsMockMvc.perform(post("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostTimings in the database
        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postTimingsRepository.findAll().size();
        // set the field null
        postTimings.setTopicId(null);

        // Create the PostTimings, which fails.
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);


        restPostTimingsMockMvc.perform(post("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = postTimingsRepository.findAll().size();
        // set the field null
        postTimings.setPostNumber(null);

        // Create the PostTimings, which fails.
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);


        restPostTimingsMockMvc.perform(post("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postTimingsRepository.findAll().size();
        // set the field null
        postTimings.setUserId(null);

        // Create the PostTimings, which fails.
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);


        restPostTimingsMockMvc.perform(post("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMsecsIsRequired() throws Exception {
        int databaseSizeBeforeTest = postTimingsRepository.findAll().size();
        // set the field null
        postTimings.setMsecs(null);

        // Create the PostTimings, which fails.
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);


        restPostTimingsMockMvc.perform(post("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostTimings() throws Exception {
        // Initialize the database
        postTimingsRepository.saveAndFlush(postTimings);

        // Get all the postTimingsList
        restPostTimingsMockMvc.perform(get("/api/post-timings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postNumber").value(hasItem(DEFAULT_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].msecs").value(hasItem(DEFAULT_MSECS)));
    }

    @Test
    @Transactional
    public void getPostTimings() throws Exception {
        // Initialize the database
        postTimingsRepository.saveAndFlush(postTimings);

        // Get the postTimings
        restPostTimingsMockMvc.perform(get("/api/post-timings/{id}", postTimings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postTimings.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postNumber").value(DEFAULT_POST_NUMBER))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.msecs").value(DEFAULT_MSECS));
    }
    @Test
    @Transactional
    public void getNonExistingPostTimings() throws Exception {
        // Get the postTimings
        restPostTimingsMockMvc.perform(get("/api/post-timings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostTimings() throws Exception {
        // Initialize the database
        postTimingsRepository.saveAndFlush(postTimings);

        int databaseSizeBeforeUpdate = postTimingsRepository.findAll().size();

        // Update the postTimings
        PostTimings updatedPostTimings = postTimingsRepository.findById(postTimings.getId()).get();
        // Disconnect from session so that the updates on updatedPostTimings are not directly saved in db
        em.detach(updatedPostTimings);
        updatedPostTimings
            .topicId(UPDATED_TOPIC_ID)
            .postNumber(UPDATED_POST_NUMBER)
            .userId(UPDATED_USER_ID)
            .msecs(UPDATED_MSECS);
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(updatedPostTimings);

        restPostTimingsMockMvc.perform(put("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isOk());

        // Validate the PostTimings in the database
        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeUpdate);
        PostTimings testPostTimings = postTimingsList.get(postTimingsList.size() - 1);
        assertThat(testPostTimings.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testPostTimings.getPostNumber()).isEqualTo(UPDATED_POST_NUMBER);
        assertThat(testPostTimings.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPostTimings.getMsecs()).isEqualTo(UPDATED_MSECS);
    }

    @Test
    @Transactional
    public void updateNonExistingPostTimings() throws Exception {
        int databaseSizeBeforeUpdate = postTimingsRepository.findAll().size();

        // Create the PostTimings
        PostTimingsDTO postTimingsDTO = postTimingsMapper.toDto(postTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostTimingsMockMvc.perform(put("/api/post-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postTimingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostTimings in the database
        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostTimings() throws Exception {
        // Initialize the database
        postTimingsRepository.saveAndFlush(postTimings);

        int databaseSizeBeforeDelete = postTimingsRepository.findAll().size();

        // Delete the postTimings
        restPostTimingsMockMvc.perform(delete("/api/post-timings/{id}", postTimings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostTimings> postTimingsList = postTimingsRepository.findAll();
        assertThat(postTimingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
