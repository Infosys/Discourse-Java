/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicAllowedUsers;
import com.infy.repository.TopicAllowedUsersRepository;
import com.infy.service.TopicAllowedUsersService;
import com.infy.service.dto.TopicAllowedUsersDTO;
import com.infy.service.mapper.TopicAllowedUsersMapper;

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
 * Integration tests for the {@link TopicAllowedUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicAllowedUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private TopicAllowedUsersRepository topicAllowedUsersRepository;

    @Autowired
    private TopicAllowedUsersMapper topicAllowedUsersMapper;

    @Autowired
    private TopicAllowedUsersService topicAllowedUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicAllowedUsersMockMvc;

    private TopicAllowedUsers topicAllowedUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicAllowedUsers createEntity(EntityManager em) {
        TopicAllowedUsers topicAllowedUsers = new TopicAllowedUsers()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return topicAllowedUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicAllowedUsers createUpdatedEntity(EntityManager em) {
        TopicAllowedUsers topicAllowedUsers = new TopicAllowedUsers()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        return topicAllowedUsers;
    }

    @BeforeEach
    public void initTest() {
        topicAllowedUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicAllowedUsers() throws Exception {
        int databaseSizeBeforeCreate = topicAllowedUsersRepository.findAll().size();
        // Create the TopicAllowedUsers
        TopicAllowedUsersDTO topicAllowedUsersDTO = topicAllowedUsersMapper.toDto(topicAllowedUsers);
        restTopicAllowedUsersMockMvc.perform(post("/api/topic-allowed-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicAllowedUsers in the database
        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeCreate + 1);
        TopicAllowedUsers testTopicAllowedUsers = topicAllowedUsersList.get(topicAllowedUsersList.size() - 1);
        assertThat(testTopicAllowedUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopicAllowedUsers.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createTopicAllowedUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicAllowedUsersRepository.findAll().size();

        // Create the TopicAllowedUsers with an existing ID
        topicAllowedUsers.setId(1L);
        TopicAllowedUsersDTO topicAllowedUsersDTO = topicAllowedUsersMapper.toDto(topicAllowedUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicAllowedUsersMockMvc.perform(post("/api/topic-allowed-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicAllowedUsers in the database
        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicAllowedUsersRepository.findAll().size();
        // set the field null
        topicAllowedUsers.setUserId(null);

        // Create the TopicAllowedUsers, which fails.
        TopicAllowedUsersDTO topicAllowedUsersDTO = topicAllowedUsersMapper.toDto(topicAllowedUsers);


        restTopicAllowedUsersMockMvc.perform(post("/api/topic-allowed-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicAllowedUsersRepository.findAll().size();
        // set the field null
        topicAllowedUsers.setTopicId(null);

        // Create the TopicAllowedUsers, which fails.
        TopicAllowedUsersDTO topicAllowedUsersDTO = topicAllowedUsersMapper.toDto(topicAllowedUsers);


        restTopicAllowedUsersMockMvc.perform(post("/api/topic-allowed-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicAllowedUsers() throws Exception {
        // Initialize the database
        topicAllowedUsersRepository.saveAndFlush(topicAllowedUsers);

        // Get all the topicAllowedUsersList
        restTopicAllowedUsersMockMvc.perform(get("/api/topic-allowed-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicAllowedUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTopicAllowedUsers() throws Exception {
        // Initialize the database
        topicAllowedUsersRepository.saveAndFlush(topicAllowedUsers);

        // Get the topicAllowedUsers
        restTopicAllowedUsersMockMvc.perform(get("/api/topic-allowed-users/{id}", topicAllowedUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicAllowedUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTopicAllowedUsers() throws Exception {
        // Get the topicAllowedUsers
        restTopicAllowedUsersMockMvc.perform(get("/api/topic-allowed-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicAllowedUsers() throws Exception {
        // Initialize the database
        topicAllowedUsersRepository.saveAndFlush(topicAllowedUsers);

        int databaseSizeBeforeUpdate = topicAllowedUsersRepository.findAll().size();

        // Update the topicAllowedUsers
        TopicAllowedUsers updatedTopicAllowedUsers = topicAllowedUsersRepository.findById(topicAllowedUsers.getId()).get();
        // Disconnect from session so that the updates on updatedTopicAllowedUsers are not directly saved in db
        em.detach(updatedTopicAllowedUsers);
        updatedTopicAllowedUsers
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        TopicAllowedUsersDTO topicAllowedUsersDTO = topicAllowedUsersMapper.toDto(updatedTopicAllowedUsers);

        restTopicAllowedUsersMockMvc.perform(put("/api/topic-allowed-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedUsersDTO)))
            .andExpect(status().isOk());

        // Validate the TopicAllowedUsers in the database
        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeUpdate);
        TopicAllowedUsers testTopicAllowedUsers = topicAllowedUsersList.get(topicAllowedUsersList.size() - 1);
        assertThat(testTopicAllowedUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopicAllowedUsers.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicAllowedUsers() throws Exception {
        int databaseSizeBeforeUpdate = topicAllowedUsersRepository.findAll().size();

        // Create the TopicAllowedUsers
        TopicAllowedUsersDTO topicAllowedUsersDTO = topicAllowedUsersMapper.toDto(topicAllowedUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicAllowedUsersMockMvc.perform(put("/api/topic-allowed-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicAllowedUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicAllowedUsers in the database
        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicAllowedUsers() throws Exception {
        // Initialize the database
        topicAllowedUsersRepository.saveAndFlush(topicAllowedUsers);

        int databaseSizeBeforeDelete = topicAllowedUsersRepository.findAll().size();

        // Delete the topicAllowedUsers
        restTopicAllowedUsersMockMvc.perform(delete("/api/topic-allowed-users/{id}", topicAllowedUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicAllowedUsers> topicAllowedUsersList = topicAllowedUsersRepository.findAll();
        assertThat(topicAllowedUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
