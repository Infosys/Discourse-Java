/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Permalinks;
import com.infy.repository.PermalinksRepository;
import com.infy.service.PermalinksService;
import com.infy.service.dto.PermalinksDTO;
import com.infy.service.mapper.PermalinksMapper;

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
 * Integration tests for the {@link PermalinksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PermalinksResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_EXTERNAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    @Autowired
    private PermalinksRepository permalinksRepository;

    @Autowired
    private PermalinksMapper permalinksMapper;

    @Autowired
    private PermalinksService permalinksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermalinksMockMvc;

    private Permalinks permalinks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permalinks createEntity(EntityManager em) {
        Permalinks permalinks = new Permalinks()
            .url(DEFAULT_URL)
            .topicId(DEFAULT_TOPIC_ID)
            .postId(DEFAULT_POST_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .externalUrl(DEFAULT_EXTERNAL_URL)
            .tagId(DEFAULT_TAG_ID);
        return permalinks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permalinks createUpdatedEntity(EntityManager em) {
        Permalinks permalinks = new Permalinks()
            .url(UPDATED_URL)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .externalUrl(UPDATED_EXTERNAL_URL)
            .tagId(UPDATED_TAG_ID);
        return permalinks;
    }

    @BeforeEach
    public void initTest() {
        permalinks = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermalinks() throws Exception {
        int databaseSizeBeforeCreate = permalinksRepository.findAll().size();
        // Create the Permalinks
        PermalinksDTO permalinksDTO = permalinksMapper.toDto(permalinks);
        restPermalinksMockMvc.perform(post("/api/permalinks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permalinksDTO)))
            .andExpect(status().isCreated());

        // Validate the Permalinks in the database
        List<Permalinks> permalinksList = permalinksRepository.findAll();
        assertThat(permalinksList).hasSize(databaseSizeBeforeCreate + 1);
        Permalinks testPermalinks = permalinksList.get(permalinksList.size() - 1);
        assertThat(testPermalinks.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testPermalinks.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testPermalinks.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPermalinks.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testPermalinks.getExternalUrl()).isEqualTo(DEFAULT_EXTERNAL_URL);
        assertThat(testPermalinks.getTagId()).isEqualTo(DEFAULT_TAG_ID);
    }

    @Test
    @Transactional
    public void createPermalinksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permalinksRepository.findAll().size();

        // Create the Permalinks with an existing ID
        permalinks.setId(1L);
        PermalinksDTO permalinksDTO = permalinksMapper.toDto(permalinks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermalinksMockMvc.perform(post("/api/permalinks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permalinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Permalinks in the database
        List<Permalinks> permalinksList = permalinksRepository.findAll();
        assertThat(permalinksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = permalinksRepository.findAll().size();
        // set the field null
        permalinks.setUrl(null);

        // Create the Permalinks, which fails.
        PermalinksDTO permalinksDTO = permalinksMapper.toDto(permalinks);


        restPermalinksMockMvc.perform(post("/api/permalinks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permalinksDTO)))
            .andExpect(status().isBadRequest());

        List<Permalinks> permalinksList = permalinksRepository.findAll();
        assertThat(permalinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPermalinks() throws Exception {
        // Initialize the database
        permalinksRepository.saveAndFlush(permalinks);

        // Get all the permalinksList
        restPermalinksMockMvc.perform(get("/api/permalinks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permalinks.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].externalUrl").value(hasItem(DEFAULT_EXTERNAL_URL)))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPermalinks() throws Exception {
        // Initialize the database
        permalinksRepository.saveAndFlush(permalinks);

        // Get the permalinks
        restPermalinksMockMvc.perform(get("/api/permalinks/{id}", permalinks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permalinks.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.externalUrl").value(DEFAULT_EXTERNAL_URL))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPermalinks() throws Exception {
        // Get the permalinks
        restPermalinksMockMvc.perform(get("/api/permalinks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermalinks() throws Exception {
        // Initialize the database
        permalinksRepository.saveAndFlush(permalinks);

        int databaseSizeBeforeUpdate = permalinksRepository.findAll().size();

        // Update the permalinks
        Permalinks updatedPermalinks = permalinksRepository.findById(permalinks.getId()).get();
        // Disconnect from session so that the updates on updatedPermalinks are not directly saved in db
        em.detach(updatedPermalinks);
        updatedPermalinks
            .url(UPDATED_URL)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .externalUrl(UPDATED_EXTERNAL_URL)
            .tagId(UPDATED_TAG_ID);
        PermalinksDTO permalinksDTO = permalinksMapper.toDto(updatedPermalinks);

        restPermalinksMockMvc.perform(put("/api/permalinks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permalinksDTO)))
            .andExpect(status().isOk());

        // Validate the Permalinks in the database
        List<Permalinks> permalinksList = permalinksRepository.findAll();
        assertThat(permalinksList).hasSize(databaseSizeBeforeUpdate);
        Permalinks testPermalinks = permalinksList.get(permalinksList.size() - 1);
        assertThat(testPermalinks.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testPermalinks.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testPermalinks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPermalinks.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testPermalinks.getExternalUrl()).isEqualTo(UPDATED_EXTERNAL_URL);
        assertThat(testPermalinks.getTagId()).isEqualTo(UPDATED_TAG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPermalinks() throws Exception {
        int databaseSizeBeforeUpdate = permalinksRepository.findAll().size();

        // Create the Permalinks
        PermalinksDTO permalinksDTO = permalinksMapper.toDto(permalinks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermalinksMockMvc.perform(put("/api/permalinks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permalinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Permalinks in the database
        List<Permalinks> permalinksList = permalinksRepository.findAll();
        assertThat(permalinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermalinks() throws Exception {
        // Initialize the database
        permalinksRepository.saveAndFlush(permalinks);

        int databaseSizeBeforeDelete = permalinksRepository.findAll().size();

        // Delete the permalinks
        restPermalinksMockMvc.perform(delete("/api/permalinks/{id}", permalinks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Permalinks> permalinksList = permalinksRepository.findAll();
        assertThat(permalinksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
