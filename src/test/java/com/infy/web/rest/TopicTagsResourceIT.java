/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicTags;
import com.infy.repository.TopicTagsRepository;
import com.infy.service.TopicTagsService;
import com.infy.service.dto.TopicTagsDTO;
import com.infy.service.mapper.TopicTagsMapper;

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
 * Integration tests for the {@link TopicTagsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicTagsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    @Autowired
    private TopicTagsRepository topicTagsRepository;

    @Autowired
    private TopicTagsMapper topicTagsMapper;

    @Autowired
    private TopicTagsService topicTagsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicTagsMockMvc;

    private TopicTags topicTags;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicTags createEntity(EntityManager em) {
        TopicTags topicTags = new TopicTags()
            .topicId(DEFAULT_TOPIC_ID)
            .tagId(DEFAULT_TAG_ID);
        return topicTags;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicTags createUpdatedEntity(EntityManager em) {
        TopicTags topicTags = new TopicTags()
            .topicId(UPDATED_TOPIC_ID)
            .tagId(UPDATED_TAG_ID);
        return topicTags;
    }

    @BeforeEach
    public void initTest() {
        topicTags = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicTags() throws Exception {
        int databaseSizeBeforeCreate = topicTagsRepository.findAll().size();
        // Create the TopicTags
        TopicTagsDTO topicTagsDTO = topicTagsMapper.toDto(topicTags);
        restTopicTagsMockMvc.perform(post("/api/topic-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTagsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicTags in the database
        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicTags testTopicTags = topicTagsList.get(topicTagsList.size() - 1);
        assertThat(testTopicTags.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicTags.getTagId()).isEqualTo(DEFAULT_TAG_ID);
    }

    @Test
    @Transactional
    public void createTopicTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicTagsRepository.findAll().size();

        // Create the TopicTags with an existing ID
        topicTags.setId(1L);
        TopicTagsDTO topicTagsDTO = topicTagsMapper.toDto(topicTags);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicTagsMockMvc.perform(post("/api/topic-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicTags in the database
        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTagsRepository.findAll().size();
        // set the field null
        topicTags.setTopicId(null);

        // Create the TopicTags, which fails.
        TopicTagsDTO topicTagsDTO = topicTagsMapper.toDto(topicTags);


        restTopicTagsMockMvc.perform(post("/api/topic-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTagsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicTagsRepository.findAll().size();
        // set the field null
        topicTags.setTagId(null);

        // Create the TopicTags, which fails.
        TopicTagsDTO topicTagsDTO = topicTagsMapper.toDto(topicTags);


        restTopicTagsMockMvc.perform(post("/api/topic-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTagsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicTags() throws Exception {
        // Initialize the database
        topicTagsRepository.saveAndFlush(topicTags);

        // Get all the topicTagsList
        restTopicTagsMockMvc.perform(get("/api/topic-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTopicTags() throws Exception {
        // Initialize the database
        topicTagsRepository.saveAndFlush(topicTags);

        // Get the topicTags
        restTopicTagsMockMvc.perform(get("/api/topic-tags/{id}", topicTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicTags.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTopicTags() throws Exception {
        // Get the topicTags
        restTopicTagsMockMvc.perform(get("/api/topic-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicTags() throws Exception {
        // Initialize the database
        topicTagsRepository.saveAndFlush(topicTags);

        int databaseSizeBeforeUpdate = topicTagsRepository.findAll().size();

        // Update the topicTags
        TopicTags updatedTopicTags = topicTagsRepository.findById(topicTags.getId()).get();
        // Disconnect from session so that the updates on updatedTopicTags are not directly saved in db
        em.detach(updatedTopicTags);
        updatedTopicTags
            .topicId(UPDATED_TOPIC_ID)
            .tagId(UPDATED_TAG_ID);
        TopicTagsDTO topicTagsDTO = topicTagsMapper.toDto(updatedTopicTags);

        restTopicTagsMockMvc.perform(put("/api/topic-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTagsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicTags in the database
        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeUpdate);
        TopicTags testTopicTags = topicTagsList.get(topicTagsList.size() - 1);
        assertThat(testTopicTags.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicTags.getTagId()).isEqualTo(UPDATED_TAG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicTags() throws Exception {
        int databaseSizeBeforeUpdate = topicTagsRepository.findAll().size();

        // Create the TopicTags
        TopicTagsDTO topicTagsDTO = topicTagsMapper.toDto(topicTags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicTagsMockMvc.perform(put("/api/topic-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicTags in the database
        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicTags() throws Exception {
        // Initialize the database
        topicTagsRepository.saveAndFlush(topicTags);

        int databaseSizeBeforeDelete = topicTagsRepository.findAll().size();

        // Delete the topicTags
        restTopicTagsMockMvc.perform(delete("/api/topic-tags/{id}", topicTags.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicTags> topicTagsList = topicTagsRepository.findAll();
        assertThat(topicTagsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
