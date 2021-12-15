/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicAllowedGroups;
import com.infy.repository.TopicAllowedGroupsRepository;
import com.infy.service.TopicAllowedGroupsService;
import com.infy.service.dto.TopicAllowedGroupsDTO;
import com.infy.service.mapper.TopicAllowedGroupsMapper;

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
 * Integration tests for the {@link TopicAllowedGroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicAllowedGroupsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private TopicAllowedGroupsRepository topicAllowedGroupsRepository;

    @Autowired
    private TopicAllowedGroupsMapper topicAllowedGroupsMapper;

    @Autowired
    private TopicAllowedGroupsService topicAllowedGroupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicAllowedGroupsMockMvc;

    private TopicAllowedGroups topicAllowedGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicAllowedGroups createEntity(EntityManager em) {
        TopicAllowedGroups topicAllowedGroups = new TopicAllowedGroups()
            .groupId(DEFAULT_GROUP_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return topicAllowedGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicAllowedGroups createUpdatedEntity(EntityManager em) {
        TopicAllowedGroups topicAllowedGroups = new TopicAllowedGroups()
            .groupId(UPDATED_GROUP_ID)
            .topicId(UPDATED_TOPIC_ID);
        return topicAllowedGroups;
    }

    @BeforeEach
    public void initTest() {
        topicAllowedGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicAllowedGroups() throws Exception {
        int databaseSizeBeforeCreate = topicAllowedGroupsRepository.findAll().size();
        // Create the TopicAllowedGroups
        TopicAllowedGroupsDTO topicAllowedGroupsDTO = topicAllowedGroupsMapper.toDto(topicAllowedGroups);
        restTopicAllowedGroupsMockMvc.perform(post("/api/topic-allowed-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicAllowedGroups in the database
        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        TopicAllowedGroups testTopicAllowedGroups = topicAllowedGroupsList.get(topicAllowedGroupsList.size() - 1);
        assertThat(testTopicAllowedGroups.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testTopicAllowedGroups.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createTopicAllowedGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicAllowedGroupsRepository.findAll().size();

        // Create the TopicAllowedGroups with an existing ID
        topicAllowedGroups.setId(1L);
        TopicAllowedGroupsDTO topicAllowedGroupsDTO = topicAllowedGroupsMapper.toDto(topicAllowedGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicAllowedGroupsMockMvc.perform(post("/api/topic-allowed-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicAllowedGroups in the database
        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicAllowedGroupsRepository.findAll().size();
        // set the field null
        topicAllowedGroups.setGroupId(null);

        // Create the TopicAllowedGroups, which fails.
        TopicAllowedGroupsDTO topicAllowedGroupsDTO = topicAllowedGroupsMapper.toDto(topicAllowedGroups);


        restTopicAllowedGroupsMockMvc.perform(post("/api/topic-allowed-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicAllowedGroupsRepository.findAll().size();
        // set the field null
        topicAllowedGroups.setTopicId(null);

        // Create the TopicAllowedGroups, which fails.
        TopicAllowedGroupsDTO topicAllowedGroupsDTO = topicAllowedGroupsMapper.toDto(topicAllowedGroups);


        restTopicAllowedGroupsMockMvc.perform(post("/api/topic-allowed-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicAllowedGroups() throws Exception {
        // Initialize the database
        topicAllowedGroupsRepository.saveAndFlush(topicAllowedGroups);

        // Get all the topicAllowedGroupsList
        restTopicAllowedGroupsMockMvc.perform(get("/api/topic-allowed-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicAllowedGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTopicAllowedGroups() throws Exception {
        // Initialize the database
        topicAllowedGroupsRepository.saveAndFlush(topicAllowedGroups);

        // Get the topicAllowedGroups
        restTopicAllowedGroupsMockMvc.perform(get("/api/topic-allowed-groups/{id}", topicAllowedGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicAllowedGroups.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTopicAllowedGroups() throws Exception {
        // Get the topicAllowedGroups
        restTopicAllowedGroupsMockMvc.perform(get("/api/topic-allowed-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicAllowedGroups() throws Exception {
        // Initialize the database
        topicAllowedGroupsRepository.saveAndFlush(topicAllowedGroups);

        int databaseSizeBeforeUpdate = topicAllowedGroupsRepository.findAll().size();

        // Update the topicAllowedGroups
        TopicAllowedGroups updatedTopicAllowedGroups = topicAllowedGroupsRepository.findById(topicAllowedGroups.getId()).get();
        // Disconnect from session so that the updates on updatedTopicAllowedGroups are not directly saved in db
        em.detach(updatedTopicAllowedGroups);
        updatedTopicAllowedGroups
            .groupId(UPDATED_GROUP_ID)
            .topicId(UPDATED_TOPIC_ID);
        TopicAllowedGroupsDTO topicAllowedGroupsDTO = topicAllowedGroupsMapper.toDto(updatedTopicAllowedGroups);

        restTopicAllowedGroupsMockMvc.perform(put("/api/topic-allowed-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the TopicAllowedGroups in the database
        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeUpdate);
        TopicAllowedGroups testTopicAllowedGroups = topicAllowedGroupsList.get(topicAllowedGroupsList.size() - 1);
        assertThat(testTopicAllowedGroups.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testTopicAllowedGroups.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicAllowedGroups() throws Exception {
        int databaseSizeBeforeUpdate = topicAllowedGroupsRepository.findAll().size();

        // Create the TopicAllowedGroups
        TopicAllowedGroupsDTO topicAllowedGroupsDTO = topicAllowedGroupsMapper.toDto(topicAllowedGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicAllowedGroupsMockMvc.perform(put("/api/topic-allowed-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicAllowedGroups in the database
        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicAllowedGroups() throws Exception {
        // Initialize the database
        topicAllowedGroupsRepository.saveAndFlush(topicAllowedGroups);

        int databaseSizeBeforeDelete = topicAllowedGroupsRepository.findAll().size();

        // Delete the topicAllowedGroups
        restTopicAllowedGroupsMockMvc.perform(delete("/api/topic-allowed-groups/{id}", topicAllowedGroups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicAllowedGroups> topicAllowedGroupsList = topicAllowedGroupsRepository.findAll();
        assertThat(topicAllowedGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
