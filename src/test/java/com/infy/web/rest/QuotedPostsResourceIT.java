/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.QuotedPosts;
import com.infy.repository.QuotedPostsRepository;
import com.infy.service.QuotedPostsService;
import com.infy.service.dto.QuotedPostsDTO;
import com.infy.service.mapper.QuotedPostsMapper;

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
 * Integration tests for the {@link QuotedPostsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QuotedPostsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_QUOTED_POST_ID = 1L;
    private static final Long UPDATED_QUOTED_POST_ID = 2L;

    @Autowired
    private QuotedPostsRepository quotedPostsRepository;

    @Autowired
    private QuotedPostsMapper quotedPostsMapper;

    @Autowired
    private QuotedPostsService quotedPostsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotedPostsMockMvc;

    private QuotedPosts quotedPosts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotedPosts createEntity(EntityManager em) {
        QuotedPosts quotedPosts = new QuotedPosts()
            .postId(DEFAULT_POST_ID)
            .quotedPostId(DEFAULT_QUOTED_POST_ID);
        return quotedPosts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotedPosts createUpdatedEntity(EntityManager em) {
        QuotedPosts quotedPosts = new QuotedPosts()
            .postId(UPDATED_POST_ID)
            .quotedPostId(UPDATED_QUOTED_POST_ID);
        return quotedPosts;
    }

    @BeforeEach
    public void initTest() {
        quotedPosts = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotedPosts() throws Exception {
        int databaseSizeBeforeCreate = quotedPostsRepository.findAll().size();
        // Create the QuotedPosts
        QuotedPostsDTO quotedPostsDTO = quotedPostsMapper.toDto(quotedPosts);
        restQuotedPostsMockMvc.perform(post("/api/quoted-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotedPostsDTO)))
            .andExpect(status().isCreated());

        // Validate the QuotedPosts in the database
        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeCreate + 1);
        QuotedPosts testQuotedPosts = quotedPostsList.get(quotedPostsList.size() - 1);
        assertThat(testQuotedPosts.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testQuotedPosts.getQuotedPostId()).isEqualTo(DEFAULT_QUOTED_POST_ID);
    }

    @Test
    @Transactional
    public void createQuotedPostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotedPostsRepository.findAll().size();

        // Create the QuotedPosts with an existing ID
        quotedPosts.setId(1L);
        QuotedPostsDTO quotedPostsDTO = quotedPostsMapper.toDto(quotedPosts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotedPostsMockMvc.perform(post("/api/quoted-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotedPostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuotedPosts in the database
        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotedPostsRepository.findAll().size();
        // set the field null
        quotedPosts.setPostId(null);

        // Create the QuotedPosts, which fails.
        QuotedPostsDTO quotedPostsDTO = quotedPostsMapper.toDto(quotedPosts);


        restQuotedPostsMockMvc.perform(post("/api/quoted-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotedPostsDTO)))
            .andExpect(status().isBadRequest());

        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuotedPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotedPostsRepository.findAll().size();
        // set the field null
        quotedPosts.setQuotedPostId(null);

        // Create the QuotedPosts, which fails.
        QuotedPostsDTO quotedPostsDTO = quotedPostsMapper.toDto(quotedPosts);


        restQuotedPostsMockMvc.perform(post("/api/quoted-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotedPostsDTO)))
            .andExpect(status().isBadRequest());

        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotedPosts() throws Exception {
        // Initialize the database
        quotedPostsRepository.saveAndFlush(quotedPosts);

        // Get all the quotedPostsList
        restQuotedPostsMockMvc.perform(get("/api/quoted-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotedPosts.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].quotedPostId").value(hasItem(DEFAULT_QUOTED_POST_ID.intValue())));
    }

    @Test
    @Transactional
    public void getQuotedPosts() throws Exception {
        // Initialize the database
        quotedPostsRepository.saveAndFlush(quotedPosts);

        // Get the quotedPosts
        restQuotedPostsMockMvc.perform(get("/api/quoted-posts/{id}", quotedPosts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotedPosts.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.quotedPostId").value(DEFAULT_QUOTED_POST_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingQuotedPosts() throws Exception {
        // Get the quotedPosts
        restQuotedPostsMockMvc.perform(get("/api/quoted-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotedPosts() throws Exception {
        // Initialize the database
        quotedPostsRepository.saveAndFlush(quotedPosts);

        int databaseSizeBeforeUpdate = quotedPostsRepository.findAll().size();

        // Update the quotedPosts
        QuotedPosts updatedQuotedPosts = quotedPostsRepository.findById(quotedPosts.getId()).get();
        // Disconnect from session so that the updates on updatedQuotedPosts are not directly saved in db
        em.detach(updatedQuotedPosts);
        updatedQuotedPosts
            .postId(UPDATED_POST_ID)
            .quotedPostId(UPDATED_QUOTED_POST_ID);
        QuotedPostsDTO quotedPostsDTO = quotedPostsMapper.toDto(updatedQuotedPosts);

        restQuotedPostsMockMvc.perform(put("/api/quoted-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotedPostsDTO)))
            .andExpect(status().isOk());

        // Validate the QuotedPosts in the database
        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeUpdate);
        QuotedPosts testQuotedPosts = quotedPostsList.get(quotedPostsList.size() - 1);
        assertThat(testQuotedPosts.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testQuotedPosts.getQuotedPostId()).isEqualTo(UPDATED_QUOTED_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotedPosts() throws Exception {
        int databaseSizeBeforeUpdate = quotedPostsRepository.findAll().size();

        // Create the QuotedPosts
        QuotedPostsDTO quotedPostsDTO = quotedPostsMapper.toDto(quotedPosts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotedPostsMockMvc.perform(put("/api/quoted-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotedPostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuotedPosts in the database
        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuotedPosts() throws Exception {
        // Initialize the database
        quotedPostsRepository.saveAndFlush(quotedPosts);

        int databaseSizeBeforeDelete = quotedPostsRepository.findAll().size();

        // Delete the quotedPosts
        restQuotedPostsMockMvc.perform(delete("/api/quoted-posts/{id}", quotedPosts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuotedPosts> quotedPostsList = quotedPostsRepository.findAll();
        assertThat(quotedPostsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
