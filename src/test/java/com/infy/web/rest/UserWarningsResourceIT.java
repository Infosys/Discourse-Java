/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserWarnings;
import com.infy.repository.UserWarningsRepository;
import com.infy.service.UserWarningsService;
import com.infy.service.dto.UserWarningsDTO;
import com.infy.service.mapper.UserWarningsMapper;

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
 * Integration tests for the {@link UserWarningsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserWarningsResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private UserWarningsRepository userWarningsRepository;

    @Autowired
    private UserWarningsMapper userWarningsMapper;

    @Autowired
    private UserWarningsService userWarningsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserWarningsMockMvc;

    private UserWarnings userWarnings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWarnings createEntity(EntityManager em) {
        UserWarnings userWarnings = new UserWarnings()
            .topicId(DEFAULT_TOPIC_ID)
            .userId(DEFAULT_USER_ID);
        return userWarnings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWarnings createUpdatedEntity(EntityManager em) {
        UserWarnings userWarnings = new UserWarnings()
            .topicId(UPDATED_TOPIC_ID)
            .userId(UPDATED_USER_ID);
        return userWarnings;
    }

    @BeforeEach
    public void initTest() {
        userWarnings = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWarnings() throws Exception {
        int databaseSizeBeforeCreate = userWarningsRepository.findAll().size();
        // Create the UserWarnings
        UserWarningsDTO userWarningsDTO = userWarningsMapper.toDto(userWarnings);
        restUserWarningsMockMvc.perform(post("/api/user-warnings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWarningsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserWarnings in the database
        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeCreate + 1);
        UserWarnings testUserWarnings = userWarningsList.get(userWarningsList.size() - 1);
        assertThat(testUserWarnings.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testUserWarnings.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createUserWarningsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWarningsRepository.findAll().size();

        // Create the UserWarnings with an existing ID
        userWarnings.setId(1L);
        UserWarningsDTO userWarningsDTO = userWarningsMapper.toDto(userWarnings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWarningsMockMvc.perform(post("/api/user-warnings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWarningsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserWarnings in the database
        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWarningsRepository.findAll().size();
        // set the field null
        userWarnings.setTopicId(null);

        // Create the UserWarnings, which fails.
        UserWarningsDTO userWarningsDTO = userWarningsMapper.toDto(userWarnings);


        restUserWarningsMockMvc.perform(post("/api/user-warnings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWarningsDTO)))
            .andExpect(status().isBadRequest());

        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userWarningsRepository.findAll().size();
        // set the field null
        userWarnings.setUserId(null);

        // Create the UserWarnings, which fails.
        UserWarningsDTO userWarningsDTO = userWarningsMapper.toDto(userWarnings);


        restUserWarningsMockMvc.perform(post("/api/user-warnings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWarningsDTO)))
            .andExpect(status().isBadRequest());

        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserWarnings() throws Exception {
        // Initialize the database
        userWarningsRepository.saveAndFlush(userWarnings);

        // Get all the userWarningsList
        restUserWarningsMockMvc.perform(get("/api/user-warnings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWarnings.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void getUserWarnings() throws Exception {
        // Initialize the database
        userWarningsRepository.saveAndFlush(userWarnings);

        // Get the userWarnings
        restUserWarningsMockMvc.perform(get("/api/user-warnings/{id}", userWarnings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userWarnings.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingUserWarnings() throws Exception {
        // Get the userWarnings
        restUserWarningsMockMvc.perform(get("/api/user-warnings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWarnings() throws Exception {
        // Initialize the database
        userWarningsRepository.saveAndFlush(userWarnings);

        int databaseSizeBeforeUpdate = userWarningsRepository.findAll().size();

        // Update the userWarnings
        UserWarnings updatedUserWarnings = userWarningsRepository.findById(userWarnings.getId()).get();
        // Disconnect from session so that the updates on updatedUserWarnings are not directly saved in db
        em.detach(updatedUserWarnings);
        updatedUserWarnings
            .topicId(UPDATED_TOPIC_ID)
            .userId(UPDATED_USER_ID);
        UserWarningsDTO userWarningsDTO = userWarningsMapper.toDto(updatedUserWarnings);

        restUserWarningsMockMvc.perform(put("/api/user-warnings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWarningsDTO)))
            .andExpect(status().isOk());

        // Validate the UserWarnings in the database
        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeUpdate);
        UserWarnings testUserWarnings = userWarningsList.get(userWarningsList.size() - 1);
        assertThat(testUserWarnings.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testUserWarnings.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWarnings() throws Exception {
        int databaseSizeBeforeUpdate = userWarningsRepository.findAll().size();

        // Create the UserWarnings
        UserWarningsDTO userWarningsDTO = userWarningsMapper.toDto(userWarnings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWarningsMockMvc.perform(put("/api/user-warnings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWarningsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserWarnings in the database
        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserWarnings() throws Exception {
        // Initialize the database
        userWarningsRepository.saveAndFlush(userWarnings);

        int databaseSizeBeforeDelete = userWarningsRepository.findAll().size();

        // Delete the userWarnings
        restUserWarningsMockMvc.perform(delete("/api/user-warnings/{id}", userWarnings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserWarnings> userWarningsList = userWarningsRepository.findAll();
        assertThat(userWarningsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
