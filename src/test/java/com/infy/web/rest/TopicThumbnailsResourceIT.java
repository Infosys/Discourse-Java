/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicThumbnails;
import com.infy.repository.TopicThumbnailsRepository;
import com.infy.service.TopicThumbnailsService;
import com.infy.service.dto.TopicThumbnailsDTO;
import com.infy.service.mapper.TopicThumbnailsMapper;

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
 * Integration tests for the {@link TopicThumbnailsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicThumbnailsResourceIT {

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    private static final Long DEFAULT_OPTIMIZED_IMAGE_ID = 1L;
    private static final Long UPDATED_OPTIMIZED_IMAGE_ID = 2L;

    private static final Integer DEFAULT_MAX_WIDTH = 1;
    private static final Integer UPDATED_MAX_WIDTH = 2;

    private static final Integer DEFAULT_MAX_HEIGHT = 1;
    private static final Integer UPDATED_MAX_HEIGHT = 2;

    @Autowired
    private TopicThumbnailsRepository topicThumbnailsRepository;

    @Autowired
    private TopicThumbnailsMapper topicThumbnailsMapper;

    @Autowired
    private TopicThumbnailsService topicThumbnailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicThumbnailsMockMvc;

    private TopicThumbnails topicThumbnails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicThumbnails createEntity(EntityManager em) {
        TopicThumbnails topicThumbnails = new TopicThumbnails()
            .uploadId(DEFAULT_UPLOAD_ID)
            .optimizedImageId(DEFAULT_OPTIMIZED_IMAGE_ID)
            .maxWidth(DEFAULT_MAX_WIDTH)
            .maxHeight(DEFAULT_MAX_HEIGHT);
        return topicThumbnails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicThumbnails createUpdatedEntity(EntityManager em) {
        TopicThumbnails topicThumbnails = new TopicThumbnails()
            .uploadId(UPDATED_UPLOAD_ID)
            .optimizedImageId(UPDATED_OPTIMIZED_IMAGE_ID)
            .maxWidth(UPDATED_MAX_WIDTH)
            .maxHeight(UPDATED_MAX_HEIGHT);
        return topicThumbnails;
    }

    @BeforeEach
    public void initTest() {
        topicThumbnails = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicThumbnails() throws Exception {
        int databaseSizeBeforeCreate = topicThumbnailsRepository.findAll().size();
        // Create the TopicThumbnails
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(topicThumbnails);
        restTopicThumbnailsMockMvc.perform(post("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicThumbnails in the database
        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicThumbnails testTopicThumbnails = topicThumbnailsList.get(topicThumbnailsList.size() - 1);
        assertThat(testTopicThumbnails.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
        assertThat(testTopicThumbnails.getOptimizedImageId()).isEqualTo(DEFAULT_OPTIMIZED_IMAGE_ID);
        assertThat(testTopicThumbnails.getMaxWidth()).isEqualTo(DEFAULT_MAX_WIDTH);
        assertThat(testTopicThumbnails.getMaxHeight()).isEqualTo(DEFAULT_MAX_HEIGHT);
    }

    @Test
    @Transactional
    public void createTopicThumbnailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicThumbnailsRepository.findAll().size();

        // Create the TopicThumbnails with an existing ID
        topicThumbnails.setId(1L);
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(topicThumbnails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicThumbnailsMockMvc.perform(post("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicThumbnails in the database
        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUploadIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicThumbnailsRepository.findAll().size();
        // set the field null
        topicThumbnails.setUploadId(null);

        // Create the TopicThumbnails, which fails.
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(topicThumbnails);


        restTopicThumbnailsMockMvc.perform(post("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicThumbnailsRepository.findAll().size();
        // set the field null
        topicThumbnails.setMaxWidth(null);

        // Create the TopicThumbnails, which fails.
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(topicThumbnails);


        restTopicThumbnailsMockMvc.perform(post("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicThumbnailsRepository.findAll().size();
        // set the field null
        topicThumbnails.setMaxHeight(null);

        // Create the TopicThumbnails, which fails.
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(topicThumbnails);


        restTopicThumbnailsMockMvc.perform(post("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicThumbnails() throws Exception {
        // Initialize the database
        topicThumbnailsRepository.saveAndFlush(topicThumbnails);

        // Get all the topicThumbnailsList
        restTopicThumbnailsMockMvc.perform(get("/api/topic-thumbnails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicThumbnails.getId().intValue())))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].optimizedImageId").value(hasItem(DEFAULT_OPTIMIZED_IMAGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].maxWidth").value(hasItem(DEFAULT_MAX_WIDTH)))
            .andExpect(jsonPath("$.[*].maxHeight").value(hasItem(DEFAULT_MAX_HEIGHT)));
    }

    @Test
    @Transactional
    public void getTopicThumbnails() throws Exception {
        // Initialize the database
        topicThumbnailsRepository.saveAndFlush(topicThumbnails);

        // Get the topicThumbnails
        restTopicThumbnailsMockMvc.perform(get("/api/topic-thumbnails/{id}", topicThumbnails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicThumbnails.getId().intValue()))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.optimizedImageId").value(DEFAULT_OPTIMIZED_IMAGE_ID.intValue()))
            .andExpect(jsonPath("$.maxWidth").value(DEFAULT_MAX_WIDTH))
            .andExpect(jsonPath("$.maxHeight").value(DEFAULT_MAX_HEIGHT));
    }
    @Test
    @Transactional
    public void getNonExistingTopicThumbnails() throws Exception {
        // Get the topicThumbnails
        restTopicThumbnailsMockMvc.perform(get("/api/topic-thumbnails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicThumbnails() throws Exception {
        // Initialize the database
        topicThumbnailsRepository.saveAndFlush(topicThumbnails);

        int databaseSizeBeforeUpdate = topicThumbnailsRepository.findAll().size();

        // Update the topicThumbnails
        TopicThumbnails updatedTopicThumbnails = topicThumbnailsRepository.findById(topicThumbnails.getId()).get();
        // Disconnect from session so that the updates on updatedTopicThumbnails are not directly saved in db
        em.detach(updatedTopicThumbnails);
        updatedTopicThumbnails
            .uploadId(UPDATED_UPLOAD_ID)
            .optimizedImageId(UPDATED_OPTIMIZED_IMAGE_ID)
            .maxWidth(UPDATED_MAX_WIDTH)
            .maxHeight(UPDATED_MAX_HEIGHT);
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(updatedTopicThumbnails);

        restTopicThumbnailsMockMvc.perform(put("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicThumbnails in the database
        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeUpdate);
        TopicThumbnails testTopicThumbnails = topicThumbnailsList.get(topicThumbnailsList.size() - 1);
        assertThat(testTopicThumbnails.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
        assertThat(testTopicThumbnails.getOptimizedImageId()).isEqualTo(UPDATED_OPTIMIZED_IMAGE_ID);
        assertThat(testTopicThumbnails.getMaxWidth()).isEqualTo(UPDATED_MAX_WIDTH);
        assertThat(testTopicThumbnails.getMaxHeight()).isEqualTo(UPDATED_MAX_HEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicThumbnails() throws Exception {
        int databaseSizeBeforeUpdate = topicThumbnailsRepository.findAll().size();

        // Create the TopicThumbnails
        TopicThumbnailsDTO topicThumbnailsDTO = topicThumbnailsMapper.toDto(topicThumbnails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicThumbnailsMockMvc.perform(put("/api/topic-thumbnails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicThumbnailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicThumbnails in the database
        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicThumbnails() throws Exception {
        // Initialize the database
        topicThumbnailsRepository.saveAndFlush(topicThumbnails);

        int databaseSizeBeforeDelete = topicThumbnailsRepository.findAll().size();

        // Delete the topicThumbnails
        restTopicThumbnailsMockMvc.perform(delete("/api/topic-thumbnails/{id}", topicThumbnails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicThumbnails> topicThumbnailsList = topicThumbnailsRepository.findAll();
        assertThat(topicThumbnailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
