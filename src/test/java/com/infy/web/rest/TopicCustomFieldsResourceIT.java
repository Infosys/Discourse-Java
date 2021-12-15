/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicCustomFields;
import com.infy.repository.TopicCustomFieldsRepository;
import com.infy.service.TopicCustomFieldsService;
import com.infy.service.dto.TopicCustomFieldsDTO;
import com.infy.service.mapper.TopicCustomFieldsMapper;

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
 * Integration tests for the {@link TopicCustomFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicCustomFieldsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private TopicCustomFieldsRepository topicCustomFieldsRepository;

    @Autowired
    private TopicCustomFieldsMapper topicCustomFieldsMapper;

    @Autowired
    private TopicCustomFieldsService topicCustomFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicCustomFieldsMockMvc;

    private TopicCustomFields topicCustomFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicCustomFields createEntity(EntityManager em) {
        TopicCustomFields topicCustomFields = new TopicCustomFields()
            .topicId(DEFAULT_TOPIC_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return topicCustomFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicCustomFields createUpdatedEntity(EntityManager em) {
        TopicCustomFields topicCustomFields = new TopicCustomFields()
            .topicId(UPDATED_TOPIC_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return topicCustomFields;
    }

    @BeforeEach
    public void initTest() {
        topicCustomFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicCustomFields() throws Exception {
        int databaseSizeBeforeCreate = topicCustomFieldsRepository.findAll().size();
        // Create the TopicCustomFields
        TopicCustomFieldsDTO topicCustomFieldsDTO = topicCustomFieldsMapper.toDto(topicCustomFields);
        restTopicCustomFieldsMockMvc.perform(post("/api/topic-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicCustomFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicCustomFields in the database
        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicCustomFields testTopicCustomFields = topicCustomFieldsList.get(topicCustomFieldsList.size() - 1);
        assertThat(testTopicCustomFields.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicCustomFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTopicCustomFields.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createTopicCustomFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicCustomFieldsRepository.findAll().size();

        // Create the TopicCustomFields with an existing ID
        topicCustomFields.setId(1L);
        TopicCustomFieldsDTO topicCustomFieldsDTO = topicCustomFieldsMapper.toDto(topicCustomFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicCustomFieldsMockMvc.perform(post("/api/topic-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicCustomFields in the database
        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicCustomFieldsRepository.findAll().size();
        // set the field null
        topicCustomFields.setTopicId(null);

        // Create the TopicCustomFields, which fails.
        TopicCustomFieldsDTO topicCustomFieldsDTO = topicCustomFieldsMapper.toDto(topicCustomFields);


        restTopicCustomFieldsMockMvc.perform(post("/api/topic-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicCustomFieldsRepository.findAll().size();
        // set the field null
        topicCustomFields.setName(null);

        // Create the TopicCustomFields, which fails.
        TopicCustomFieldsDTO topicCustomFieldsDTO = topicCustomFieldsMapper.toDto(topicCustomFields);


        restTopicCustomFieldsMockMvc.perform(post("/api/topic-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicCustomFields() throws Exception {
        // Initialize the database
        topicCustomFieldsRepository.saveAndFlush(topicCustomFields);

        // Get all the topicCustomFieldsList
        restTopicCustomFieldsMockMvc.perform(get("/api/topic-custom-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicCustomFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getTopicCustomFields() throws Exception {
        // Initialize the database
        topicCustomFieldsRepository.saveAndFlush(topicCustomFields);

        // Get the topicCustomFields
        restTopicCustomFieldsMockMvc.perform(get("/api/topic-custom-fields/{id}", topicCustomFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicCustomFields.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingTopicCustomFields() throws Exception {
        // Get the topicCustomFields
        restTopicCustomFieldsMockMvc.perform(get("/api/topic-custom-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicCustomFields() throws Exception {
        // Initialize the database
        topicCustomFieldsRepository.saveAndFlush(topicCustomFields);

        int databaseSizeBeforeUpdate = topicCustomFieldsRepository.findAll().size();

        // Update the topicCustomFields
        TopicCustomFields updatedTopicCustomFields = topicCustomFieldsRepository.findById(topicCustomFields.getId()).get();
        // Disconnect from session so that the updates on updatedTopicCustomFields are not directly saved in db
        em.detach(updatedTopicCustomFields);
        updatedTopicCustomFields
            .topicId(UPDATED_TOPIC_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        TopicCustomFieldsDTO topicCustomFieldsDTO = topicCustomFieldsMapper.toDto(updatedTopicCustomFields);

        restTopicCustomFieldsMockMvc.perform(put("/api/topic-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicCustomFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicCustomFields in the database
        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
        TopicCustomFields testTopicCustomFields = topicCustomFieldsList.get(topicCustomFieldsList.size() - 1);
        assertThat(testTopicCustomFields.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicCustomFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTopicCustomFields.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicCustomFields() throws Exception {
        int databaseSizeBeforeUpdate = topicCustomFieldsRepository.findAll().size();

        // Create the TopicCustomFields
        TopicCustomFieldsDTO topicCustomFieldsDTO = topicCustomFieldsMapper.toDto(topicCustomFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicCustomFieldsMockMvc.perform(put("/api/topic-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicCustomFields in the database
        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicCustomFields() throws Exception {
        // Initialize the database
        topicCustomFieldsRepository.saveAndFlush(topicCustomFields);

        int databaseSizeBeforeDelete = topicCustomFieldsRepository.findAll().size();

        // Delete the topicCustomFields
        restTopicCustomFieldsMockMvc.perform(delete("/api/topic-custom-fields/{id}", topicCustomFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicCustomFields> topicCustomFieldsList = topicCustomFieldsRepository.findAll();
        assertThat(topicCustomFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
