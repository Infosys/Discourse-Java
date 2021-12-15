/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.LinkedTopics;
import com.infy.repository.LinkedTopicsRepository;
import com.infy.service.LinkedTopicsService;
import com.infy.service.dto.LinkedTopicsDTO;
import com.infy.service.mapper.LinkedTopicsMapper;

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
 * Integration tests for the {@link LinkedTopicsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class LinkedTopicsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_ORIGINAL_TOPIC_ID = 1L;
    private static final Long UPDATED_ORIGINAL_TOPIC_ID = 2L;

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    @Autowired
    private LinkedTopicsRepository linkedTopicsRepository;

    @Autowired
    private LinkedTopicsMapper linkedTopicsMapper;

    @Autowired
    private LinkedTopicsService linkedTopicsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkedTopicsMockMvc;

    private LinkedTopics linkedTopics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkedTopics createEntity(EntityManager em) {
        LinkedTopics linkedTopics = new LinkedTopics()
            .topicId(DEFAULT_TOPIC_ID)
            .originalTopicId(DEFAULT_ORIGINAL_TOPIC_ID)
            .sequence(DEFAULT_SEQUENCE);
        return linkedTopics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkedTopics createUpdatedEntity(EntityManager em) {
        LinkedTopics linkedTopics = new LinkedTopics()
            .topicId(UPDATED_TOPIC_ID)
            .originalTopicId(UPDATED_ORIGINAL_TOPIC_ID)
            .sequence(UPDATED_SEQUENCE);
        return linkedTopics;
    }

    @BeforeEach
    public void initTest() {
        linkedTopics = createEntity(em);
    }

    @Test
    @Transactional
    public void createLinkedTopics() throws Exception {
        int databaseSizeBeforeCreate = linkedTopicsRepository.findAll().size();
        // Create the LinkedTopics
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(linkedTopics);
        restLinkedTopicsMockMvc.perform(post("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isCreated());

        // Validate the LinkedTopics in the database
        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeCreate + 1);
        LinkedTopics testLinkedTopics = linkedTopicsList.get(linkedTopicsList.size() - 1);
        assertThat(testLinkedTopics.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testLinkedTopics.getOriginalTopicId()).isEqualTo(DEFAULT_ORIGINAL_TOPIC_ID);
        assertThat(testLinkedTopics.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    public void createLinkedTopicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linkedTopicsRepository.findAll().size();

        // Create the LinkedTopics with an existing ID
        linkedTopics.setId(1L);
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(linkedTopics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkedTopicsMockMvc.perform(post("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinkedTopics in the database
        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkedTopicsRepository.findAll().size();
        // set the field null
        linkedTopics.setTopicId(null);

        // Create the LinkedTopics, which fails.
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(linkedTopics);


        restLinkedTopicsMockMvc.perform(post("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkedTopicsRepository.findAll().size();
        // set the field null
        linkedTopics.setOriginalTopicId(null);

        // Create the LinkedTopics, which fails.
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(linkedTopics);


        restLinkedTopicsMockMvc.perform(post("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkedTopicsRepository.findAll().size();
        // set the field null
        linkedTopics.setSequence(null);

        // Create the LinkedTopics, which fails.
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(linkedTopics);


        restLinkedTopicsMockMvc.perform(post("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isBadRequest());

        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLinkedTopics() throws Exception {
        // Initialize the database
        linkedTopicsRepository.saveAndFlush(linkedTopics);

        // Get all the linkedTopicsList
        restLinkedTopicsMockMvc.perform(get("/api/linked-topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkedTopics.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].originalTopicId").value(hasItem(DEFAULT_ORIGINAL_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)));
    }

    @Test
    @Transactional
    public void getLinkedTopics() throws Exception {
        // Initialize the database
        linkedTopicsRepository.saveAndFlush(linkedTopics);

        // Get the linkedTopics
        restLinkedTopicsMockMvc.perform(get("/api/linked-topics/{id}", linkedTopics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linkedTopics.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.originalTopicId").value(DEFAULT_ORIGINAL_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE));
    }
    @Test
    @Transactional
    public void getNonExistingLinkedTopics() throws Exception {
        // Get the linkedTopics
        restLinkedTopicsMockMvc.perform(get("/api/linked-topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLinkedTopics() throws Exception {
        // Initialize the database
        linkedTopicsRepository.saveAndFlush(linkedTopics);

        int databaseSizeBeforeUpdate = linkedTopicsRepository.findAll().size();

        // Update the linkedTopics
        LinkedTopics updatedLinkedTopics = linkedTopicsRepository.findById(linkedTopics.getId()).get();
        // Disconnect from session so that the updates on updatedLinkedTopics are not directly saved in db
        em.detach(updatedLinkedTopics);
        updatedLinkedTopics
            .topicId(UPDATED_TOPIC_ID)
            .originalTopicId(UPDATED_ORIGINAL_TOPIC_ID)
            .sequence(UPDATED_SEQUENCE);
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(updatedLinkedTopics);

        restLinkedTopicsMockMvc.perform(put("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isOk());

        // Validate the LinkedTopics in the database
        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeUpdate);
        LinkedTopics testLinkedTopics = linkedTopicsList.get(linkedTopicsList.size() - 1);
        assertThat(testLinkedTopics.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testLinkedTopics.getOriginalTopicId()).isEqualTo(UPDATED_ORIGINAL_TOPIC_ID);
        assertThat(testLinkedTopics.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingLinkedTopics() throws Exception {
        int databaseSizeBeforeUpdate = linkedTopicsRepository.findAll().size();

        // Create the LinkedTopics
        LinkedTopicsDTO linkedTopicsDTO = linkedTopicsMapper.toDto(linkedTopics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkedTopicsMockMvc.perform(put("/api/linked-topics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkedTopicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinkedTopics in the database
        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLinkedTopics() throws Exception {
        // Initialize the database
        linkedTopicsRepository.saveAndFlush(linkedTopics);

        int databaseSizeBeforeDelete = linkedTopicsRepository.findAll().size();

        // Delete the linkedTopics
        restLinkedTopicsMockMvc.perform(delete("/api/linked-topics/{id}", linkedTopics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinkedTopics> linkedTopicsList = linkedTopicsRepository.findAll();
        assertThat(linkedTopicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
