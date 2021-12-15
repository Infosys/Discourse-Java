/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicEmbeds;
import com.infy.repository.TopicEmbedsRepository;
import com.infy.service.TopicEmbedsService;
import com.infy.service.dto.TopicEmbedsDTO;
import com.infy.service.mapper.TopicEmbedsMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopicEmbedsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicEmbedsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_EMBED_URL = "AAAAAAAAAA";
    private static final String UPDATED_EMBED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_SHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_SHA_1 = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    @Autowired
    private TopicEmbedsRepository topicEmbedsRepository;

    @Autowired
    private TopicEmbedsMapper topicEmbedsMapper;

    @Autowired
    private TopicEmbedsService topicEmbedsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicEmbedsMockMvc;

    private TopicEmbeds topicEmbeds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicEmbeds createEntity(EntityManager em) {
        TopicEmbeds topicEmbeds = new TopicEmbeds()
            .topicId(DEFAULT_TOPIC_ID)
            .postId(DEFAULT_POST_ID)
            .embedUrl(DEFAULT_EMBED_URL)
            .contentSha1(DEFAULT_CONTENT_SHA_1)
            .deletedAt(DEFAULT_DELETED_AT)
            .deletedById(DEFAULT_DELETED_BY_ID);
        return topicEmbeds;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicEmbeds createUpdatedEntity(EntityManager em) {
        TopicEmbeds topicEmbeds = new TopicEmbeds()
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .embedUrl(UPDATED_EMBED_URL)
            .contentSha1(UPDATED_CONTENT_SHA_1)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID);
        return topicEmbeds;
    }

    @BeforeEach
    public void initTest() {
        topicEmbeds = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicEmbeds() throws Exception {
        int databaseSizeBeforeCreate = topicEmbedsRepository.findAll().size();
        // Create the TopicEmbeds
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(topicEmbeds);
        restTopicEmbedsMockMvc.perform(post("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicEmbeds in the database
        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicEmbeds testTopicEmbeds = topicEmbedsList.get(topicEmbedsList.size() - 1);
        assertThat(testTopicEmbeds.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicEmbeds.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testTopicEmbeds.getEmbedUrl()).isEqualTo(DEFAULT_EMBED_URL);
        assertThat(testTopicEmbeds.getContentSha1()).isEqualTo(DEFAULT_CONTENT_SHA_1);
        assertThat(testTopicEmbeds.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testTopicEmbeds.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void createTopicEmbedsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicEmbedsRepository.findAll().size();

        // Create the TopicEmbeds with an existing ID
        topicEmbeds.setId(1L);
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(topicEmbeds);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicEmbedsMockMvc.perform(post("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicEmbeds in the database
        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicEmbedsRepository.findAll().size();
        // set the field null
        topicEmbeds.setTopicId(null);

        // Create the TopicEmbeds, which fails.
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(topicEmbeds);


        restTopicEmbedsMockMvc.perform(post("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicEmbedsRepository.findAll().size();
        // set the field null
        topicEmbeds.setPostId(null);

        // Create the TopicEmbeds, which fails.
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(topicEmbeds);


        restTopicEmbedsMockMvc.perform(post("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmbedUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicEmbedsRepository.findAll().size();
        // set the field null
        topicEmbeds.setEmbedUrl(null);

        // Create the TopicEmbeds, which fails.
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(topicEmbeds);


        restTopicEmbedsMockMvc.perform(post("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicEmbeds() throws Exception {
        // Initialize the database
        topicEmbedsRepository.saveAndFlush(topicEmbeds);

        // Get all the topicEmbedsList
        restTopicEmbedsMockMvc.perform(get("/api/topic-embeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicEmbeds.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].embedUrl").value(hasItem(DEFAULT_EMBED_URL)))
            .andExpect(jsonPath("$.[*].contentSha1").value(hasItem(DEFAULT_CONTENT_SHA_1)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)));
    }

    @Test
    @Transactional
    public void getTopicEmbeds() throws Exception {
        // Initialize the database
        topicEmbedsRepository.saveAndFlush(topicEmbeds);

        // Get the topicEmbeds
        restTopicEmbedsMockMvc.perform(get("/api/topic-embeds/{id}", topicEmbeds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicEmbeds.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.embedUrl").value(DEFAULT_EMBED_URL))
            .andExpect(jsonPath("$.contentSha1").value(DEFAULT_CONTENT_SHA_1))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.deletedById").value(DEFAULT_DELETED_BY_ID));
    }
    @Test
    @Transactional
    public void getNonExistingTopicEmbeds() throws Exception {
        // Get the topicEmbeds
        restTopicEmbedsMockMvc.perform(get("/api/topic-embeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicEmbeds() throws Exception {
        // Initialize the database
        topicEmbedsRepository.saveAndFlush(topicEmbeds);

        int databaseSizeBeforeUpdate = topicEmbedsRepository.findAll().size();

        // Update the topicEmbeds
        TopicEmbeds updatedTopicEmbeds = topicEmbedsRepository.findById(topicEmbeds.getId()).get();
        // Disconnect from session so that the updates on updatedTopicEmbeds are not directly saved in db
        em.detach(updatedTopicEmbeds);
        updatedTopicEmbeds
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .embedUrl(UPDATED_EMBED_URL)
            .contentSha1(UPDATED_CONTENT_SHA_1)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID);
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(updatedTopicEmbeds);

        restTopicEmbedsMockMvc.perform(put("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicEmbeds in the database
        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeUpdate);
        TopicEmbeds testTopicEmbeds = topicEmbedsList.get(topicEmbedsList.size() - 1);
        assertThat(testTopicEmbeds.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicEmbeds.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testTopicEmbeds.getEmbedUrl()).isEqualTo(UPDATED_EMBED_URL);
        assertThat(testTopicEmbeds.getContentSha1()).isEqualTo(UPDATED_CONTENT_SHA_1);
        assertThat(testTopicEmbeds.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testTopicEmbeds.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicEmbeds() throws Exception {
        int databaseSizeBeforeUpdate = topicEmbedsRepository.findAll().size();

        // Create the TopicEmbeds
        TopicEmbedsDTO topicEmbedsDTO = topicEmbedsMapper.toDto(topicEmbeds);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicEmbedsMockMvc.perform(put("/api/topic-embeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicEmbedsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicEmbeds in the database
        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicEmbeds() throws Exception {
        // Initialize the database
        topicEmbedsRepository.saveAndFlush(topicEmbeds);

        int databaseSizeBeforeDelete = topicEmbedsRepository.findAll().size();

        // Delete the topicEmbeds
        restTopicEmbedsMockMvc.perform(delete("/api/topic-embeds/{id}", topicEmbeds.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicEmbeds> topicEmbedsList = topicEmbedsRepository.findAll();
        assertThat(topicEmbedsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
