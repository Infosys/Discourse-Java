/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.DismissedTopicUsers;
import com.infy.repository.DismissedTopicUsersRepository;
import com.infy.service.DismissedTopicUsersService;
import com.infy.service.dto.DismissedTopicUsersDTO;
import com.infy.service.mapper.DismissedTopicUsersMapper;

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
 * Integration tests for the {@link DismissedTopicUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DismissedTopicUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DismissedTopicUsersRepository dismissedTopicUsersRepository;

    @Autowired
    private DismissedTopicUsersMapper dismissedTopicUsersMapper;

    @Autowired
    private DismissedTopicUsersService dismissedTopicUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDismissedTopicUsersMockMvc;

    private DismissedTopicUsers dismissedTopicUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DismissedTopicUsers createEntity(EntityManager em) {
        DismissedTopicUsers dismissedTopicUsers = new DismissedTopicUsers()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .createdAt(DEFAULT_CREATED_AT);
        return dismissedTopicUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DismissedTopicUsers createUpdatedEntity(EntityManager em) {
        DismissedTopicUsers dismissedTopicUsers = new DismissedTopicUsers()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .createdAt(UPDATED_CREATED_AT);
        return dismissedTopicUsers;
    }

    @BeforeEach
    public void initTest() {
        dismissedTopicUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createDismissedTopicUsers() throws Exception {
        int databaseSizeBeforeCreate = dismissedTopicUsersRepository.findAll().size();
        // Create the DismissedTopicUsers
        DismissedTopicUsersDTO dismissedTopicUsersDTO = dismissedTopicUsersMapper.toDto(dismissedTopicUsers);
        restDismissedTopicUsersMockMvc.perform(post("/api/dismissed-topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dismissedTopicUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the DismissedTopicUsers in the database
        List<DismissedTopicUsers> dismissedTopicUsersList = dismissedTopicUsersRepository.findAll();
        assertThat(dismissedTopicUsersList).hasSize(databaseSizeBeforeCreate + 1);
        DismissedTopicUsers testDismissedTopicUsers = dismissedTopicUsersList.get(dismissedTopicUsersList.size() - 1);
        assertThat(testDismissedTopicUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDismissedTopicUsers.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testDismissedTopicUsers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createDismissedTopicUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dismissedTopicUsersRepository.findAll().size();

        // Create the DismissedTopicUsers with an existing ID
        dismissedTopicUsers.setId(1L);
        DismissedTopicUsersDTO dismissedTopicUsersDTO = dismissedTopicUsersMapper.toDto(dismissedTopicUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDismissedTopicUsersMockMvc.perform(post("/api/dismissed-topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dismissedTopicUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DismissedTopicUsers in the database
        List<DismissedTopicUsers> dismissedTopicUsersList = dismissedTopicUsersRepository.findAll();
        assertThat(dismissedTopicUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDismissedTopicUsers() throws Exception {
        // Initialize the database
        dismissedTopicUsersRepository.saveAndFlush(dismissedTopicUsers);

        // Get all the dismissedTopicUsersList
        restDismissedTopicUsersMockMvc.perform(get("/api/dismissed-topic-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dismissedTopicUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getDismissedTopicUsers() throws Exception {
        // Initialize the database
        dismissedTopicUsersRepository.saveAndFlush(dismissedTopicUsers);

        // Get the dismissedTopicUsers
        restDismissedTopicUsersMockMvc.perform(get("/api/dismissed-topic-users/{id}", dismissedTopicUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dismissedTopicUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDismissedTopicUsers() throws Exception {
        // Get the dismissedTopicUsers
        restDismissedTopicUsersMockMvc.perform(get("/api/dismissed-topic-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDismissedTopicUsers() throws Exception {
        // Initialize the database
        dismissedTopicUsersRepository.saveAndFlush(dismissedTopicUsers);

        int databaseSizeBeforeUpdate = dismissedTopicUsersRepository.findAll().size();

        // Update the dismissedTopicUsers
        DismissedTopicUsers updatedDismissedTopicUsers = dismissedTopicUsersRepository.findById(dismissedTopicUsers.getId()).get();
        // Disconnect from session so that the updates on updatedDismissedTopicUsers are not directly saved in db
        em.detach(updatedDismissedTopicUsers);
        updatedDismissedTopicUsers
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .createdAt(UPDATED_CREATED_AT);
        DismissedTopicUsersDTO dismissedTopicUsersDTO = dismissedTopicUsersMapper.toDto(updatedDismissedTopicUsers);

        restDismissedTopicUsersMockMvc.perform(put("/api/dismissed-topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dismissedTopicUsersDTO)))
            .andExpect(status().isOk());

        // Validate the DismissedTopicUsers in the database
        List<DismissedTopicUsers> dismissedTopicUsersList = dismissedTopicUsersRepository.findAll();
        assertThat(dismissedTopicUsersList).hasSize(databaseSizeBeforeUpdate);
        DismissedTopicUsers testDismissedTopicUsers = dismissedTopicUsersList.get(dismissedTopicUsersList.size() - 1);
        assertThat(testDismissedTopicUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDismissedTopicUsers.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testDismissedTopicUsers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingDismissedTopicUsers() throws Exception {
        int databaseSizeBeforeUpdate = dismissedTopicUsersRepository.findAll().size();

        // Create the DismissedTopicUsers
        DismissedTopicUsersDTO dismissedTopicUsersDTO = dismissedTopicUsersMapper.toDto(dismissedTopicUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDismissedTopicUsersMockMvc.perform(put("/api/dismissed-topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dismissedTopicUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DismissedTopicUsers in the database
        List<DismissedTopicUsers> dismissedTopicUsersList = dismissedTopicUsersRepository.findAll();
        assertThat(dismissedTopicUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDismissedTopicUsers() throws Exception {
        // Initialize the database
        dismissedTopicUsersRepository.saveAndFlush(dismissedTopicUsers);

        int databaseSizeBeforeDelete = dismissedTopicUsersRepository.findAll().size();

        // Delete the dismissedTopicUsers
        restDismissedTopicUsersMockMvc.perform(delete("/api/dismissed-topic-users/{id}", dismissedTopicUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DismissedTopicUsers> dismissedTopicUsersList = dismissedTopicUsersRepository.findAll();
        assertThat(dismissedTopicUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
