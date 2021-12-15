/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserVisits;
import com.infy.repository.UserVisitsRepository;
import com.infy.service.UserVisitsService;
import com.infy.service.dto.UserVisitsDTO;
import com.infy.service.mapper.UserVisitsMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserVisitsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserVisitsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VISITED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISITED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_POSTS_READ = 1;
    private static final Integer UPDATED_POSTS_READ = 2;

    private static final Boolean DEFAULT_MOBILE = false;
    private static final Boolean UPDATED_MOBILE = true;

    private static final Integer DEFAULT_TIME_READ = 1;
    private static final Integer UPDATED_TIME_READ = 2;

    @Autowired
    private UserVisitsRepository userVisitsRepository;

    @Autowired
    private UserVisitsMapper userVisitsMapper;

    @Autowired
    private UserVisitsService userVisitsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserVisitsMockMvc;

    private UserVisits userVisits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVisits createEntity(EntityManager em) {
        UserVisits userVisits = new UserVisits()
            .userId(DEFAULT_USER_ID)
            .visitedAt(DEFAULT_VISITED_AT)
            .postsRead(DEFAULT_POSTS_READ)
            .mobile(DEFAULT_MOBILE)
            .timeRead(DEFAULT_TIME_READ);
        return userVisits;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVisits createUpdatedEntity(EntityManager em) {
        UserVisits userVisits = new UserVisits()
            .userId(UPDATED_USER_ID)
            .visitedAt(UPDATED_VISITED_AT)
            .postsRead(UPDATED_POSTS_READ)
            .mobile(UPDATED_MOBILE)
            .timeRead(UPDATED_TIME_READ);
        return userVisits;
    }

    @BeforeEach
    public void initTest() {
        userVisits = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserVisits() throws Exception {
        int databaseSizeBeforeCreate = userVisitsRepository.findAll().size();
        // Create the UserVisits
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(userVisits);
        restUserVisitsMockMvc.perform(post("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserVisits in the database
        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeCreate + 1);
        UserVisits testUserVisits = userVisitsList.get(userVisitsList.size() - 1);
        assertThat(testUserVisits.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserVisits.getVisitedAt()).isEqualTo(DEFAULT_VISITED_AT);
        assertThat(testUserVisits.getPostsRead()).isEqualTo(DEFAULT_POSTS_READ);
        assertThat(testUserVisits.isMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserVisits.getTimeRead()).isEqualTo(DEFAULT_TIME_READ);
    }

    @Test
    @Transactional
    public void createUserVisitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userVisitsRepository.findAll().size();

        // Create the UserVisits with an existing ID
        userVisits.setId(1L);
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(userVisits);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserVisitsMockMvc.perform(post("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserVisits in the database
        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVisitsRepository.findAll().size();
        // set the field null
        userVisits.setUserId(null);

        // Create the UserVisits, which fails.
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(userVisits);


        restUserVisitsMockMvc.perform(post("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isBadRequest());

        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisitedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVisitsRepository.findAll().size();
        // set the field null
        userVisits.setVisitedAt(null);

        // Create the UserVisits, which fails.
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(userVisits);


        restUserVisitsMockMvc.perform(post("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isBadRequest());

        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVisitsRepository.findAll().size();
        // set the field null
        userVisits.setTimeRead(null);

        // Create the UserVisits, which fails.
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(userVisits);


        restUserVisitsMockMvc.perform(post("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isBadRequest());

        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserVisits() throws Exception {
        // Initialize the database
        userVisitsRepository.saveAndFlush(userVisits);

        // Get all the userVisitsList
        restUserVisitsMockMvc.perform(get("/api/user-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userVisits.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].visitedAt").value(hasItem(DEFAULT_VISITED_AT.toString())))
            .andExpect(jsonPath("$.[*].postsRead").value(hasItem(DEFAULT_POSTS_READ)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeRead").value(hasItem(DEFAULT_TIME_READ)));
    }

    @Test
    @Transactional
    public void getUserVisits() throws Exception {
        // Initialize the database
        userVisitsRepository.saveAndFlush(userVisits);

        // Get the userVisits
        restUserVisitsMockMvc.perform(get("/api/user-visits/{id}", userVisits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userVisits.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.visitedAt").value(DEFAULT_VISITED_AT.toString()))
            .andExpect(jsonPath("$.postsRead").value(DEFAULT_POSTS_READ))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.booleanValue()))
            .andExpect(jsonPath("$.timeRead").value(DEFAULT_TIME_READ));
    }
    @Test
    @Transactional
    public void getNonExistingUserVisits() throws Exception {
        // Get the userVisits
        restUserVisitsMockMvc.perform(get("/api/user-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserVisits() throws Exception {
        // Initialize the database
        userVisitsRepository.saveAndFlush(userVisits);

        int databaseSizeBeforeUpdate = userVisitsRepository.findAll().size();

        // Update the userVisits
        UserVisits updatedUserVisits = userVisitsRepository.findById(userVisits.getId()).get();
        // Disconnect from session so that the updates on updatedUserVisits are not directly saved in db
        em.detach(updatedUserVisits);
        updatedUserVisits
            .userId(UPDATED_USER_ID)
            .visitedAt(UPDATED_VISITED_AT)
            .postsRead(UPDATED_POSTS_READ)
            .mobile(UPDATED_MOBILE)
            .timeRead(UPDATED_TIME_READ);
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(updatedUserVisits);

        restUserVisitsMockMvc.perform(put("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isOk());

        // Validate the UserVisits in the database
        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeUpdate);
        UserVisits testUserVisits = userVisitsList.get(userVisitsList.size() - 1);
        assertThat(testUserVisits.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserVisits.getVisitedAt()).isEqualTo(UPDATED_VISITED_AT);
        assertThat(testUserVisits.getPostsRead()).isEqualTo(UPDATED_POSTS_READ);
        assertThat(testUserVisits.isMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserVisits.getTimeRead()).isEqualTo(UPDATED_TIME_READ);
    }

    @Test
    @Transactional
    public void updateNonExistingUserVisits() throws Exception {
        int databaseSizeBeforeUpdate = userVisitsRepository.findAll().size();

        // Create the UserVisits
        UserVisitsDTO userVisitsDTO = userVisitsMapper.toDto(userVisits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserVisitsMockMvc.perform(put("/api/user-visits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVisitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserVisits in the database
        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserVisits() throws Exception {
        // Initialize the database
        userVisitsRepository.saveAndFlush(userVisits);

        int databaseSizeBeforeDelete = userVisitsRepository.findAll().size();

        // Delete the userVisits
        restUserVisitsMockMvc.perform(delete("/api/user-visits/{id}", userVisits.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserVisits> userVisitsList = userVisitsRepository.findAll();
        assertThat(userVisitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
