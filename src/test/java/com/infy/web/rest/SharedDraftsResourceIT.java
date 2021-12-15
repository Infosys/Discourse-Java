/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SharedDrafts;
import com.infy.repository.SharedDraftsRepository;
import com.infy.service.SharedDraftsService;
import com.infy.service.dto.SharedDraftsDTO;
import com.infy.service.mapper.SharedDraftsMapper;

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
 * Integration tests for the {@link SharedDraftsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SharedDraftsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    @Autowired
    private SharedDraftsRepository sharedDraftsRepository;

    @Autowired
    private SharedDraftsMapper sharedDraftsMapper;

    @Autowired
    private SharedDraftsService sharedDraftsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSharedDraftsMockMvc;

    private SharedDrafts sharedDrafts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SharedDrafts createEntity(EntityManager em) {
        SharedDrafts sharedDrafts = new SharedDrafts()
            .topicId(DEFAULT_TOPIC_ID)
            .categoryId(DEFAULT_CATEGORY_ID);
        return sharedDrafts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SharedDrafts createUpdatedEntity(EntityManager em) {
        SharedDrafts sharedDrafts = new SharedDrafts()
            .topicId(UPDATED_TOPIC_ID)
            .categoryId(UPDATED_CATEGORY_ID);
        return sharedDrafts;
    }

    @BeforeEach
    public void initTest() {
        sharedDrafts = createEntity(em);
    }

    @Test
    @Transactional
    public void createSharedDrafts() throws Exception {
        int databaseSizeBeforeCreate = sharedDraftsRepository.findAll().size();
        // Create the SharedDrafts
        SharedDraftsDTO sharedDraftsDTO = sharedDraftsMapper.toDto(sharedDrafts);
        restSharedDraftsMockMvc.perform(post("/api/shared-drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharedDraftsDTO)))
            .andExpect(status().isCreated());

        // Validate the SharedDrafts in the database
        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeCreate + 1);
        SharedDrafts testSharedDrafts = sharedDraftsList.get(sharedDraftsList.size() - 1);
        assertThat(testSharedDrafts.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testSharedDrafts.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void createSharedDraftsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sharedDraftsRepository.findAll().size();

        // Create the SharedDrafts with an existing ID
        sharedDrafts.setId(1L);
        SharedDraftsDTO sharedDraftsDTO = sharedDraftsMapper.toDto(sharedDrafts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSharedDraftsMockMvc.perform(post("/api/shared-drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharedDraftsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SharedDrafts in the database
        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedDraftsRepository.findAll().size();
        // set the field null
        sharedDrafts.setTopicId(null);

        // Create the SharedDrafts, which fails.
        SharedDraftsDTO sharedDraftsDTO = sharedDraftsMapper.toDto(sharedDrafts);


        restSharedDraftsMockMvc.perform(post("/api/shared-drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharedDraftsDTO)))
            .andExpect(status().isBadRequest());

        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedDraftsRepository.findAll().size();
        // set the field null
        sharedDrafts.setCategoryId(null);

        // Create the SharedDrafts, which fails.
        SharedDraftsDTO sharedDraftsDTO = sharedDraftsMapper.toDto(sharedDrafts);


        restSharedDraftsMockMvc.perform(post("/api/shared-drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharedDraftsDTO)))
            .andExpect(status().isBadRequest());

        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSharedDrafts() throws Exception {
        // Initialize the database
        sharedDraftsRepository.saveAndFlush(sharedDrafts);

        // Get all the sharedDraftsList
        restSharedDraftsMockMvc.perform(get("/api/shared-drafts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sharedDrafts.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSharedDrafts() throws Exception {
        // Initialize the database
        sharedDraftsRepository.saveAndFlush(sharedDrafts);

        // Get the sharedDrafts
        restSharedDraftsMockMvc.perform(get("/api/shared-drafts/{id}", sharedDrafts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sharedDrafts.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSharedDrafts() throws Exception {
        // Get the sharedDrafts
        restSharedDraftsMockMvc.perform(get("/api/shared-drafts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSharedDrafts() throws Exception {
        // Initialize the database
        sharedDraftsRepository.saveAndFlush(sharedDrafts);

        int databaseSizeBeforeUpdate = sharedDraftsRepository.findAll().size();

        // Update the sharedDrafts
        SharedDrafts updatedSharedDrafts = sharedDraftsRepository.findById(sharedDrafts.getId()).get();
        // Disconnect from session so that the updates on updatedSharedDrafts are not directly saved in db
        em.detach(updatedSharedDrafts);
        updatedSharedDrafts
            .topicId(UPDATED_TOPIC_ID)
            .categoryId(UPDATED_CATEGORY_ID);
        SharedDraftsDTO sharedDraftsDTO = sharedDraftsMapper.toDto(updatedSharedDrafts);

        restSharedDraftsMockMvc.perform(put("/api/shared-drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharedDraftsDTO)))
            .andExpect(status().isOk());

        // Validate the SharedDrafts in the database
        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeUpdate);
        SharedDrafts testSharedDrafts = sharedDraftsList.get(sharedDraftsList.size() - 1);
        assertThat(testSharedDrafts.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testSharedDrafts.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSharedDrafts() throws Exception {
        int databaseSizeBeforeUpdate = sharedDraftsRepository.findAll().size();

        // Create the SharedDrafts
        SharedDraftsDTO sharedDraftsDTO = sharedDraftsMapper.toDto(sharedDrafts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSharedDraftsMockMvc.perform(put("/api/shared-drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharedDraftsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SharedDrafts in the database
        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSharedDrafts() throws Exception {
        // Initialize the database
        sharedDraftsRepository.saveAndFlush(sharedDrafts);

        int databaseSizeBeforeDelete = sharedDraftsRepository.findAll().size();

        // Delete the sharedDrafts
        restSharedDraftsMockMvc.perform(delete("/api/shared-drafts/{id}", sharedDrafts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SharedDrafts> sharedDraftsList = sharedDraftsRepository.findAll();
        assertThat(sharedDraftsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
