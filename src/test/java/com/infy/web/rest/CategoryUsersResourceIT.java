/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoryUsers;
import com.infy.repository.CategoryUsersRepository;
import com.infy.service.CategoryUsersService;
import com.infy.service.dto.CategoryUsersDTO;
import com.infy.service.mapper.CategoryUsersMapper;

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
 * Integration tests for the {@link CategoryUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoryUsersResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL = 2;

    private static final Instant DEFAULT_LAST_SEEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_SEEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CategoryUsersRepository categoryUsersRepository;

    @Autowired
    private CategoryUsersMapper categoryUsersMapper;

    @Autowired
    private CategoryUsersService categoryUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryUsersMockMvc;

    private CategoryUsers categoryUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryUsers createEntity(EntityManager em) {
        CategoryUsers categoryUsers = new CategoryUsers()
            .categoryId(DEFAULT_CATEGORY_ID)
            .userId(DEFAULT_USER_ID)
            .notificationLevel(DEFAULT_NOTIFICATION_LEVEL)
            .lastSeenAt(DEFAULT_LAST_SEEN_AT);
        return categoryUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryUsers createUpdatedEntity(EntityManager em) {
        CategoryUsers categoryUsers = new CategoryUsers()
            .categoryId(UPDATED_CATEGORY_ID)
            .userId(UPDATED_USER_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL)
            .lastSeenAt(UPDATED_LAST_SEEN_AT);
        return categoryUsers;
    }

    @BeforeEach
    public void initTest() {
        categoryUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryUsers() throws Exception {
        int databaseSizeBeforeCreate = categoryUsersRepository.findAll().size();
        // Create the CategoryUsers
        CategoryUsersDTO categoryUsersDTO = categoryUsersMapper.toDto(categoryUsers);
        restCategoryUsersMockMvc.perform(post("/api/category-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryUsers in the database
        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryUsers testCategoryUsers = categoryUsersList.get(categoryUsersList.size() - 1);
        assertThat(testCategoryUsers.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCategoryUsers.getNotificationLevel()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL);
        assertThat(testCategoryUsers.getLastSeenAt()).isEqualTo(DEFAULT_LAST_SEEN_AT);
    }

    @Test
    @Transactional
    public void createCategoryUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryUsersRepository.findAll().size();

        // Create the CategoryUsers with an existing ID
        categoryUsers.setId(1L);
        CategoryUsersDTO categoryUsersDTO = categoryUsersMapper.toDto(categoryUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryUsersMockMvc.perform(post("/api/category-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryUsers in the database
        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryUsersRepository.findAll().size();
        // set the field null
        categoryUsers.setCategoryId(null);

        // Create the CategoryUsers, which fails.
        CategoryUsersDTO categoryUsersDTO = categoryUsersMapper.toDto(categoryUsers);


        restCategoryUsersMockMvc.perform(post("/api/category-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryUsersDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryUsersRepository.findAll().size();
        // set the field null
        categoryUsers.setUserId(null);

        // Create the CategoryUsers, which fails.
        CategoryUsersDTO categoryUsersDTO = categoryUsersMapper.toDto(categoryUsers);


        restCategoryUsersMockMvc.perform(post("/api/category-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryUsersDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryUsers() throws Exception {
        // Initialize the database
        categoryUsersRepository.saveAndFlush(categoryUsers);

        // Get all the categoryUsersList
        restCategoryUsersMockMvc.perform(get("/api/category-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].notificationLevel").value(hasItem(DEFAULT_NOTIFICATION_LEVEL)))
            .andExpect(jsonPath("$.[*].lastSeenAt").value(hasItem(DEFAULT_LAST_SEEN_AT.toString())));
    }

    @Test
    @Transactional
    public void getCategoryUsers() throws Exception {
        // Initialize the database
        categoryUsersRepository.saveAndFlush(categoryUsers);

        // Get the categoryUsers
        restCategoryUsersMockMvc.perform(get("/api/category-users/{id}", categoryUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryUsers.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.notificationLevel").value(DEFAULT_NOTIFICATION_LEVEL))
            .andExpect(jsonPath("$.lastSeenAt").value(DEFAULT_LAST_SEEN_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryUsers() throws Exception {
        // Get the categoryUsers
        restCategoryUsersMockMvc.perform(get("/api/category-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryUsers() throws Exception {
        // Initialize the database
        categoryUsersRepository.saveAndFlush(categoryUsers);

        int databaseSizeBeforeUpdate = categoryUsersRepository.findAll().size();

        // Update the categoryUsers
        CategoryUsers updatedCategoryUsers = categoryUsersRepository.findById(categoryUsers.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryUsers are not directly saved in db
        em.detach(updatedCategoryUsers);
        updatedCategoryUsers
            .categoryId(UPDATED_CATEGORY_ID)
            .userId(UPDATED_USER_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL)
            .lastSeenAt(UPDATED_LAST_SEEN_AT);
        CategoryUsersDTO categoryUsersDTO = categoryUsersMapper.toDto(updatedCategoryUsers);

        restCategoryUsersMockMvc.perform(put("/api/category-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryUsersDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryUsers in the database
        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeUpdate);
        CategoryUsers testCategoryUsers = categoryUsersList.get(categoryUsersList.size() - 1);
        assertThat(testCategoryUsers.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCategoryUsers.getNotificationLevel()).isEqualTo(UPDATED_NOTIFICATION_LEVEL);
        assertThat(testCategoryUsers.getLastSeenAt()).isEqualTo(UPDATED_LAST_SEEN_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryUsers() throws Exception {
        int databaseSizeBeforeUpdate = categoryUsersRepository.findAll().size();

        // Create the CategoryUsers
        CategoryUsersDTO categoryUsersDTO = categoryUsersMapper.toDto(categoryUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryUsersMockMvc.perform(put("/api/category-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryUsers in the database
        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryUsers() throws Exception {
        // Initialize the database
        categoryUsersRepository.saveAndFlush(categoryUsers);

        int databaseSizeBeforeDelete = categoryUsersRepository.findAll().size();

        // Delete the categoryUsers
        restCategoryUsersMockMvc.perform(delete("/api/category-users/{id}", categoryUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryUsers> categoryUsersList = categoryUsersRepository.findAll();
        assertThat(categoryUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
