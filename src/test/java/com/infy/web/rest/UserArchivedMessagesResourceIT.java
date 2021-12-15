/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserArchivedMessages;
import com.infy.repository.UserArchivedMessagesRepository;
import com.infy.service.UserArchivedMessagesService;
import com.infy.service.dto.UserArchivedMessagesDTO;
import com.infy.service.mapper.UserArchivedMessagesMapper;

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
 * Integration tests for the {@link UserArchivedMessagesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserArchivedMessagesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private UserArchivedMessagesRepository userArchivedMessagesRepository;

    @Autowired
    private UserArchivedMessagesMapper userArchivedMessagesMapper;

    @Autowired
    private UserArchivedMessagesService userArchivedMessagesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserArchivedMessagesMockMvc;

    private UserArchivedMessages userArchivedMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserArchivedMessages createEntity(EntityManager em) {
        UserArchivedMessages userArchivedMessages = new UserArchivedMessages()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return userArchivedMessages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserArchivedMessages createUpdatedEntity(EntityManager em) {
        UserArchivedMessages userArchivedMessages = new UserArchivedMessages()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        return userArchivedMessages;
    }

    @BeforeEach
    public void initTest() {
        userArchivedMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserArchivedMessages() throws Exception {
        int databaseSizeBeforeCreate = userArchivedMessagesRepository.findAll().size();
        // Create the UserArchivedMessages
        UserArchivedMessagesDTO userArchivedMessagesDTO = userArchivedMessagesMapper.toDto(userArchivedMessages);
        restUserArchivedMessagesMockMvc.perform(post("/api/user-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userArchivedMessagesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserArchivedMessages in the database
        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        UserArchivedMessages testUserArchivedMessages = userArchivedMessagesList.get(userArchivedMessagesList.size() - 1);
        assertThat(testUserArchivedMessages.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserArchivedMessages.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createUserArchivedMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userArchivedMessagesRepository.findAll().size();

        // Create the UserArchivedMessages with an existing ID
        userArchivedMessages.setId(1L);
        UserArchivedMessagesDTO userArchivedMessagesDTO = userArchivedMessagesMapper.toDto(userArchivedMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserArchivedMessagesMockMvc.perform(post("/api/user-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserArchivedMessages in the database
        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userArchivedMessagesRepository.findAll().size();
        // set the field null
        userArchivedMessages.setUserId(null);

        // Create the UserArchivedMessages, which fails.
        UserArchivedMessagesDTO userArchivedMessagesDTO = userArchivedMessagesMapper.toDto(userArchivedMessages);


        restUserArchivedMessagesMockMvc.perform(post("/api/user-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userArchivedMessagesRepository.findAll().size();
        // set the field null
        userArchivedMessages.setTopicId(null);

        // Create the UserArchivedMessages, which fails.
        UserArchivedMessagesDTO userArchivedMessagesDTO = userArchivedMessagesMapper.toDto(userArchivedMessages);


        restUserArchivedMessagesMockMvc.perform(post("/api/user-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserArchivedMessages() throws Exception {
        // Initialize the database
        userArchivedMessagesRepository.saveAndFlush(userArchivedMessages);

        // Get all the userArchivedMessagesList
        restUserArchivedMessagesMockMvc.perform(get("/api/user-archived-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userArchivedMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserArchivedMessages() throws Exception {
        // Initialize the database
        userArchivedMessagesRepository.saveAndFlush(userArchivedMessages);

        // Get the userArchivedMessages
        restUserArchivedMessagesMockMvc.perform(get("/api/user-archived-messages/{id}", userArchivedMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userArchivedMessages.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserArchivedMessages() throws Exception {
        // Get the userArchivedMessages
        restUserArchivedMessagesMockMvc.perform(get("/api/user-archived-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserArchivedMessages() throws Exception {
        // Initialize the database
        userArchivedMessagesRepository.saveAndFlush(userArchivedMessages);

        int databaseSizeBeforeUpdate = userArchivedMessagesRepository.findAll().size();

        // Update the userArchivedMessages
        UserArchivedMessages updatedUserArchivedMessages = userArchivedMessagesRepository.findById(userArchivedMessages.getId()).get();
        // Disconnect from session so that the updates on updatedUserArchivedMessages are not directly saved in db
        em.detach(updatedUserArchivedMessages);
        updatedUserArchivedMessages
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID);
        UserArchivedMessagesDTO userArchivedMessagesDTO = userArchivedMessagesMapper.toDto(updatedUserArchivedMessages);

        restUserArchivedMessagesMockMvc.perform(put("/api/user-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userArchivedMessagesDTO)))
            .andExpect(status().isOk());

        // Validate the UserArchivedMessages in the database
        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeUpdate);
        UserArchivedMessages testUserArchivedMessages = userArchivedMessagesList.get(userArchivedMessagesList.size() - 1);
        assertThat(testUserArchivedMessages.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserArchivedMessages.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserArchivedMessages() throws Exception {
        int databaseSizeBeforeUpdate = userArchivedMessagesRepository.findAll().size();

        // Create the UserArchivedMessages
        UserArchivedMessagesDTO userArchivedMessagesDTO = userArchivedMessagesMapper.toDto(userArchivedMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserArchivedMessagesMockMvc.perform(put("/api/user-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserArchivedMessages in the database
        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserArchivedMessages() throws Exception {
        // Initialize the database
        userArchivedMessagesRepository.saveAndFlush(userArchivedMessages);

        int databaseSizeBeforeDelete = userArchivedMessagesRepository.findAll().size();

        // Delete the userArchivedMessages
        restUserArchivedMessagesMockMvc.perform(delete("/api/user-archived-messages/{id}", userArchivedMessages.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserArchivedMessages> userArchivedMessagesList = userArchivedMessagesRepository.findAll();
        assertThat(userArchivedMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
