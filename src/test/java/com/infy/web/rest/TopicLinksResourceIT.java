/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicLinks;
import com.infy.repository.TopicLinksRepository;
import com.infy.service.TopicLinksService;
import com.infy.service.dto.TopicLinksDTO;
import com.infy.service.mapper.TopicLinksMapper;

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
 * Integration tests for the {@link TopicLinksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicLinksResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INTERNAL = false;
    private static final Boolean UPDATED_INTERNAL = true;

    private static final Long DEFAULT_LINK_TOPIC_ID = 1L;
    private static final Long UPDATED_LINK_TOPIC_ID = 2L;

    private static final Boolean DEFAULT_REFLECTION = false;
    private static final Boolean UPDATED_REFLECTION = true;

    private static final Integer DEFAULT_CLICKS = 1;
    private static final Integer UPDATED_CLICKS = 2;

    private static final Long DEFAULT_LINK_POST_ID = 1L;
    private static final Long UPDATED_LINK_POST_ID = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CRAWLED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRAWLED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_QUOTE = false;
    private static final Boolean UPDATED_QUOTE = true;

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    @Autowired
    private TopicLinksRepository topicLinksRepository;

    @Autowired
    private TopicLinksMapper topicLinksMapper;

    @Autowired
    private TopicLinksService topicLinksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicLinksMockMvc;

    private TopicLinks topicLinks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicLinks createEntity(EntityManager em) {
        TopicLinks topicLinks = new TopicLinks()
            .topicId(DEFAULT_TOPIC_ID)
            .postId(DEFAULT_POST_ID)
            .userId(DEFAULT_USER_ID)
            .url(DEFAULT_URL)
            .domain(DEFAULT_DOMAIN)
            .internal(DEFAULT_INTERNAL)
            .linkTopicId(DEFAULT_LINK_TOPIC_ID)
            .reflection(DEFAULT_REFLECTION)
            .clicks(DEFAULT_CLICKS)
            .linkPostId(DEFAULT_LINK_POST_ID)
            .title(DEFAULT_TITLE)
            .crawledAt(DEFAULT_CRAWLED_AT)
            .quote(DEFAULT_QUOTE)
            .extension(DEFAULT_EXTENSION);
        return topicLinks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicLinks createUpdatedEntity(EntityManager em) {
        TopicLinks topicLinks = new TopicLinks()
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .userId(UPDATED_USER_ID)
            .url(UPDATED_URL)
            .domain(UPDATED_DOMAIN)
            .internal(UPDATED_INTERNAL)
            .linkTopicId(UPDATED_LINK_TOPIC_ID)
            .reflection(UPDATED_REFLECTION)
            .clicks(UPDATED_CLICKS)
            .linkPostId(UPDATED_LINK_POST_ID)
            .title(UPDATED_TITLE)
            .crawledAt(UPDATED_CRAWLED_AT)
            .quote(UPDATED_QUOTE)
            .extension(UPDATED_EXTENSION);
        return topicLinks;
    }

    @BeforeEach
    public void initTest() {
        topicLinks = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicLinks() throws Exception {
        int databaseSizeBeforeCreate = topicLinksRepository.findAll().size();
        // Create the TopicLinks
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);
        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicLinks in the database
        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeCreate + 1);
        TopicLinks testTopicLinks = topicLinksList.get(topicLinksList.size() - 1);
        assertThat(testTopicLinks.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicLinks.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testTopicLinks.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopicLinks.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTopicLinks.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testTopicLinks.isInternal()).isEqualTo(DEFAULT_INTERNAL);
        assertThat(testTopicLinks.getLinkTopicId()).isEqualTo(DEFAULT_LINK_TOPIC_ID);
        assertThat(testTopicLinks.isReflection()).isEqualTo(DEFAULT_REFLECTION);
        assertThat(testTopicLinks.getClicks()).isEqualTo(DEFAULT_CLICKS);
        assertThat(testTopicLinks.getLinkPostId()).isEqualTo(DEFAULT_LINK_POST_ID);
        assertThat(testTopicLinks.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTopicLinks.getCrawledAt()).isEqualTo(DEFAULT_CRAWLED_AT);
        assertThat(testTopicLinks.isQuote()).isEqualTo(DEFAULT_QUOTE);
        assertThat(testTopicLinks.getExtension()).isEqualTo(DEFAULT_EXTENSION);
    }

    @Test
    @Transactional
    public void createTopicLinksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicLinksRepository.findAll().size();

        // Create the TopicLinks with an existing ID
        topicLinks.setId(1L);
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicLinks in the database
        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setTopicId(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setUserId(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setUrl(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setDomain(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternalIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setInternal(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClicksIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setClicks(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicLinksRepository.findAll().size();
        // set the field null
        topicLinks.setQuote(null);

        // Create the TopicLinks, which fails.
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);


        restTopicLinksMockMvc.perform(post("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicLinks() throws Exception {
        // Initialize the database
        topicLinksRepository.saveAndFlush(topicLinks);

        // Get all the topicLinksList
        restTopicLinksMockMvc.perform(get("/api/topic-links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicLinks.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)))
            .andExpect(jsonPath("$.[*].internal").value(hasItem(DEFAULT_INTERNAL.booleanValue())))
            .andExpect(jsonPath("$.[*].linkTopicId").value(hasItem(DEFAULT_LINK_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].reflection").value(hasItem(DEFAULT_REFLECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].clicks").value(hasItem(DEFAULT_CLICKS)))
            .andExpect(jsonPath("$.[*].linkPostId").value(hasItem(DEFAULT_LINK_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].crawledAt").value(hasItem(DEFAULT_CRAWLED_AT.toString())))
            .andExpect(jsonPath("$.[*].quote").value(hasItem(DEFAULT_QUOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)));
    }

    @Test
    @Transactional
    public void getTopicLinks() throws Exception {
        // Initialize the database
        topicLinksRepository.saveAndFlush(topicLinks);

        // Get the topicLinks
        restTopicLinksMockMvc.perform(get("/api/topic-links/{id}", topicLinks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicLinks.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN))
            .andExpect(jsonPath("$.internal").value(DEFAULT_INTERNAL.booleanValue()))
            .andExpect(jsonPath("$.linkTopicId").value(DEFAULT_LINK_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.reflection").value(DEFAULT_REFLECTION.booleanValue()))
            .andExpect(jsonPath("$.clicks").value(DEFAULT_CLICKS))
            .andExpect(jsonPath("$.linkPostId").value(DEFAULT_LINK_POST_ID.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.crawledAt").value(DEFAULT_CRAWLED_AT.toString()))
            .andExpect(jsonPath("$.quote").value(DEFAULT_QUOTE.booleanValue()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION));
    }
    @Test
    @Transactional
    public void getNonExistingTopicLinks() throws Exception {
        // Get the topicLinks
        restTopicLinksMockMvc.perform(get("/api/topic-links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicLinks() throws Exception {
        // Initialize the database
        topicLinksRepository.saveAndFlush(topicLinks);

        int databaseSizeBeforeUpdate = topicLinksRepository.findAll().size();

        // Update the topicLinks
        TopicLinks updatedTopicLinks = topicLinksRepository.findById(topicLinks.getId()).get();
        // Disconnect from session so that the updates on updatedTopicLinks are not directly saved in db
        em.detach(updatedTopicLinks);
        updatedTopicLinks
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .userId(UPDATED_USER_ID)
            .url(UPDATED_URL)
            .domain(UPDATED_DOMAIN)
            .internal(UPDATED_INTERNAL)
            .linkTopicId(UPDATED_LINK_TOPIC_ID)
            .reflection(UPDATED_REFLECTION)
            .clicks(UPDATED_CLICKS)
            .linkPostId(UPDATED_LINK_POST_ID)
            .title(UPDATED_TITLE)
            .crawledAt(UPDATED_CRAWLED_AT)
            .quote(UPDATED_QUOTE)
            .extension(UPDATED_EXTENSION);
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(updatedTopicLinks);

        restTopicLinksMockMvc.perform(put("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isOk());

        // Validate the TopicLinks in the database
        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeUpdate);
        TopicLinks testTopicLinks = topicLinksList.get(topicLinksList.size() - 1);
        assertThat(testTopicLinks.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicLinks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testTopicLinks.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopicLinks.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTopicLinks.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testTopicLinks.isInternal()).isEqualTo(UPDATED_INTERNAL);
        assertThat(testTopicLinks.getLinkTopicId()).isEqualTo(UPDATED_LINK_TOPIC_ID);
        assertThat(testTopicLinks.isReflection()).isEqualTo(UPDATED_REFLECTION);
        assertThat(testTopicLinks.getClicks()).isEqualTo(UPDATED_CLICKS);
        assertThat(testTopicLinks.getLinkPostId()).isEqualTo(UPDATED_LINK_POST_ID);
        assertThat(testTopicLinks.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTopicLinks.getCrawledAt()).isEqualTo(UPDATED_CRAWLED_AT);
        assertThat(testTopicLinks.isQuote()).isEqualTo(UPDATED_QUOTE);
        assertThat(testTopicLinks.getExtension()).isEqualTo(UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicLinks() throws Exception {
        int databaseSizeBeforeUpdate = topicLinksRepository.findAll().size();

        // Create the TopicLinks
        TopicLinksDTO topicLinksDTO = topicLinksMapper.toDto(topicLinks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicLinksMockMvc.perform(put("/api/topic-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicLinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicLinks in the database
        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicLinks() throws Exception {
        // Initialize the database
        topicLinksRepository.saveAndFlush(topicLinks);

        int databaseSizeBeforeDelete = topicLinksRepository.findAll().size();

        // Delete the topicLinks
        restTopicLinksMockMvc.perform(delete("/api/topic-links/{id}", topicLinks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicLinks> topicLinksList = topicLinksRepository.findAll();
        assertThat(topicLinksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
