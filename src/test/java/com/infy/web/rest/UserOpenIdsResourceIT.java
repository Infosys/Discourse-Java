/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserOpenIds;
import com.infy.repository.UserOpenIdsRepository;
import com.infy.service.UserOpenIdsService;
import com.infy.service.dto.UserOpenIdsDTO;
import com.infy.service.mapper.UserOpenIdsMapper;

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
 * Integration tests for the {@link UserOpenIdsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserOpenIdsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private UserOpenIdsRepository userOpenIdsRepository;

    @Autowired
    private UserOpenIdsMapper userOpenIdsMapper;

    @Autowired
    private UserOpenIdsService userOpenIdsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserOpenIdsMockMvc;

    private UserOpenIds userOpenIds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOpenIds createEntity(EntityManager em) {
        UserOpenIds userOpenIds = new UserOpenIds()
            .userId(DEFAULT_USER_ID)
            .email(DEFAULT_EMAIL)
            .url(DEFAULT_URL)
            .active(DEFAULT_ACTIVE);
        return userOpenIds;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOpenIds createUpdatedEntity(EntityManager em) {
        UserOpenIds userOpenIds = new UserOpenIds()
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .url(UPDATED_URL)
            .active(UPDATED_ACTIVE);
        return userOpenIds;
    }

    @BeforeEach
    public void initTest() {
        userOpenIds = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserOpenIds() throws Exception {
        int databaseSizeBeforeCreate = userOpenIdsRepository.findAll().size();
        // Create the UserOpenIds
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);
        restUserOpenIdsMockMvc.perform(post("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserOpenIds in the database
        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeCreate + 1);
        UserOpenIds testUserOpenIds = userOpenIdsList.get(userOpenIdsList.size() - 1);
        assertThat(testUserOpenIds.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserOpenIds.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserOpenIds.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testUserOpenIds.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createUserOpenIdsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userOpenIdsRepository.findAll().size();

        // Create the UserOpenIds with an existing ID
        userOpenIds.setId(1L);
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserOpenIdsMockMvc.perform(post("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserOpenIds in the database
        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOpenIdsRepository.findAll().size();
        // set the field null
        userOpenIds.setUserId(null);

        // Create the UserOpenIds, which fails.
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);


        restUserOpenIdsMockMvc.perform(post("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOpenIdsRepository.findAll().size();
        // set the field null
        userOpenIds.setEmail(null);

        // Create the UserOpenIds, which fails.
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);


        restUserOpenIdsMockMvc.perform(post("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOpenIdsRepository.findAll().size();
        // set the field null
        userOpenIds.setUrl(null);

        // Create the UserOpenIds, which fails.
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);


        restUserOpenIdsMockMvc.perform(post("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = userOpenIdsRepository.findAll().size();
        // set the field null
        userOpenIds.setActive(null);

        // Create the UserOpenIds, which fails.
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);


        restUserOpenIdsMockMvc.perform(post("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isBadRequest());

        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserOpenIds() throws Exception {
        // Initialize the database
        userOpenIdsRepository.saveAndFlush(userOpenIds);

        // Get all the userOpenIdsList
        restUserOpenIdsMockMvc.perform(get("/api/user-open-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userOpenIds.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserOpenIds() throws Exception {
        // Initialize the database
        userOpenIdsRepository.saveAndFlush(userOpenIds);

        // Get the userOpenIds
        restUserOpenIdsMockMvc.perform(get("/api/user-open-ids/{id}", userOpenIds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userOpenIds.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserOpenIds() throws Exception {
        // Get the userOpenIds
        restUserOpenIdsMockMvc.perform(get("/api/user-open-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserOpenIds() throws Exception {
        // Initialize the database
        userOpenIdsRepository.saveAndFlush(userOpenIds);

        int databaseSizeBeforeUpdate = userOpenIdsRepository.findAll().size();

        // Update the userOpenIds
        UserOpenIds updatedUserOpenIds = userOpenIdsRepository.findById(userOpenIds.getId()).get();
        // Disconnect from session so that the updates on updatedUserOpenIds are not directly saved in db
        em.detach(updatedUserOpenIds);
        updatedUserOpenIds
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .url(UPDATED_URL)
            .active(UPDATED_ACTIVE);
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(updatedUserOpenIds);

        restUserOpenIdsMockMvc.perform(put("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isOk());

        // Validate the UserOpenIds in the database
        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeUpdate);
        UserOpenIds testUserOpenIds = userOpenIdsList.get(userOpenIdsList.size() - 1);
        assertThat(testUserOpenIds.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserOpenIds.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserOpenIds.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testUserOpenIds.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserOpenIds() throws Exception {
        int databaseSizeBeforeUpdate = userOpenIdsRepository.findAll().size();

        // Create the UserOpenIds
        UserOpenIdsDTO userOpenIdsDTO = userOpenIdsMapper.toDto(userOpenIds);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOpenIdsMockMvc.perform(put("/api/user-open-ids").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userOpenIdsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserOpenIds in the database
        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserOpenIds() throws Exception {
        // Initialize the database
        userOpenIdsRepository.saveAndFlush(userOpenIds);

        int databaseSizeBeforeDelete = userOpenIdsRepository.findAll().size();

        // Delete the userOpenIds
        restUserOpenIdsMockMvc.perform(delete("/api/user-open-ids/{id}", userOpenIds.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserOpenIds> userOpenIdsList = userOpenIdsRepository.findAll();
        assertThat(userOpenIdsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
