/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserIpAddressHistories;
import com.infy.repository.UserIpAddressHistoriesRepository;
import com.infy.service.UserIpAddressHistoriesService;
import com.infy.service.dto.UserIpAddressHistoriesDTO;
import com.infy.service.mapper.UserIpAddressHistoriesMapper;

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
 * Integration tests for the {@link UserIpAddressHistoriesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserIpAddressHistoriesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private UserIpAddressHistoriesRepository userIpAddressHistoriesRepository;

    @Autowired
    private UserIpAddressHistoriesMapper userIpAddressHistoriesMapper;

    @Autowired
    private UserIpAddressHistoriesService userIpAddressHistoriesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserIpAddressHistoriesMockMvc;

    private UserIpAddressHistories userIpAddressHistories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserIpAddressHistories createEntity(EntityManager em) {
        UserIpAddressHistories userIpAddressHistories = new UserIpAddressHistories()
            .userId(DEFAULT_USER_ID)
            .ipAddress(DEFAULT_IP_ADDRESS);
        return userIpAddressHistories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserIpAddressHistories createUpdatedEntity(EntityManager em) {
        UserIpAddressHistories userIpAddressHistories = new UserIpAddressHistories()
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS);
        return userIpAddressHistories;
    }

    @BeforeEach
    public void initTest() {
        userIpAddressHistories = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserIpAddressHistories() throws Exception {
        int databaseSizeBeforeCreate = userIpAddressHistoriesRepository.findAll().size();
        // Create the UserIpAddressHistories
        UserIpAddressHistoriesDTO userIpAddressHistoriesDTO = userIpAddressHistoriesMapper.toDto(userIpAddressHistories);
        restUserIpAddressHistoriesMockMvc.perform(post("/api/user-ip-address-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userIpAddressHistoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserIpAddressHistories in the database
        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeCreate + 1);
        UserIpAddressHistories testUserIpAddressHistories = userIpAddressHistoriesList.get(userIpAddressHistoriesList.size() - 1);
        assertThat(testUserIpAddressHistories.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserIpAddressHistories.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createUserIpAddressHistoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userIpAddressHistoriesRepository.findAll().size();

        // Create the UserIpAddressHistories with an existing ID
        userIpAddressHistories.setId(1L);
        UserIpAddressHistoriesDTO userIpAddressHistoriesDTO = userIpAddressHistoriesMapper.toDto(userIpAddressHistories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserIpAddressHistoriesMockMvc.perform(post("/api/user-ip-address-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userIpAddressHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserIpAddressHistories in the database
        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userIpAddressHistoriesRepository.findAll().size();
        // set the field null
        userIpAddressHistories.setUserId(null);

        // Create the UserIpAddressHistories, which fails.
        UserIpAddressHistoriesDTO userIpAddressHistoriesDTO = userIpAddressHistoriesMapper.toDto(userIpAddressHistories);


        restUserIpAddressHistoriesMockMvc.perform(post("/api/user-ip-address-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userIpAddressHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = userIpAddressHistoriesRepository.findAll().size();
        // set the field null
        userIpAddressHistories.setIpAddress(null);

        // Create the UserIpAddressHistories, which fails.
        UserIpAddressHistoriesDTO userIpAddressHistoriesDTO = userIpAddressHistoriesMapper.toDto(userIpAddressHistories);


        restUserIpAddressHistoriesMockMvc.perform(post("/api/user-ip-address-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userIpAddressHistoriesDTO)))
            .andExpect(status().isBadRequest());

        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserIpAddressHistories() throws Exception {
        // Initialize the database
        userIpAddressHistoriesRepository.saveAndFlush(userIpAddressHistories);

        // Get all the userIpAddressHistoriesList
        restUserIpAddressHistoriesMockMvc.perform(get("/api/user-ip-address-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userIpAddressHistories.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)));
    }

    @Test
    @Transactional
    public void getUserIpAddressHistories() throws Exception {
        // Initialize the database
        userIpAddressHistoriesRepository.saveAndFlush(userIpAddressHistories);

        // Get the userIpAddressHistories
        restUserIpAddressHistoriesMockMvc.perform(get("/api/user-ip-address-histories/{id}", userIpAddressHistories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userIpAddressHistories.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingUserIpAddressHistories() throws Exception {
        // Get the userIpAddressHistories
        restUserIpAddressHistoriesMockMvc.perform(get("/api/user-ip-address-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserIpAddressHistories() throws Exception {
        // Initialize the database
        userIpAddressHistoriesRepository.saveAndFlush(userIpAddressHistories);

        int databaseSizeBeforeUpdate = userIpAddressHistoriesRepository.findAll().size();

        // Update the userIpAddressHistories
        UserIpAddressHistories updatedUserIpAddressHistories = userIpAddressHistoriesRepository.findById(userIpAddressHistories.getId()).get();
        // Disconnect from session so that the updates on updatedUserIpAddressHistories are not directly saved in db
        em.detach(updatedUserIpAddressHistories);
        updatedUserIpAddressHistories
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS);
        UserIpAddressHistoriesDTO userIpAddressHistoriesDTO = userIpAddressHistoriesMapper.toDto(updatedUserIpAddressHistories);

        restUserIpAddressHistoriesMockMvc.perform(put("/api/user-ip-address-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userIpAddressHistoriesDTO)))
            .andExpect(status().isOk());

        // Validate the UserIpAddressHistories in the database
        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeUpdate);
        UserIpAddressHistories testUserIpAddressHistories = userIpAddressHistoriesList.get(userIpAddressHistoriesList.size() - 1);
        assertThat(testUserIpAddressHistories.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserIpAddressHistories.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserIpAddressHistories() throws Exception {
        int databaseSizeBeforeUpdate = userIpAddressHistoriesRepository.findAll().size();

        // Create the UserIpAddressHistories
        UserIpAddressHistoriesDTO userIpAddressHistoriesDTO = userIpAddressHistoriesMapper.toDto(userIpAddressHistories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserIpAddressHistoriesMockMvc.perform(put("/api/user-ip-address-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userIpAddressHistoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserIpAddressHistories in the database
        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserIpAddressHistories() throws Exception {
        // Initialize the database
        userIpAddressHistoriesRepository.saveAndFlush(userIpAddressHistories);

        int databaseSizeBeforeDelete = userIpAddressHistoriesRepository.findAll().size();

        // Delete the userIpAddressHistories
        restUserIpAddressHistoriesMockMvc.perform(delete("/api/user-ip-address-histories/{id}", userIpAddressHistories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserIpAddressHistories> userIpAddressHistoriesList = userIpAddressHistoriesRepository.findAll();
        assertThat(userIpAddressHistoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
