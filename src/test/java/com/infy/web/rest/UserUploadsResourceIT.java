/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserUploads;
import com.infy.repository.UserUploadsRepository;
import com.infy.service.UserUploadsService;
import com.infy.service.dto.UserUploadsDTO;
import com.infy.service.mapper.UserUploadsMapper;

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
 * Integration tests for the {@link UserUploadsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserUploadsResourceIT {

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private UserUploadsRepository userUploadsRepository;

    @Autowired
    private UserUploadsMapper userUploadsMapper;

    @Autowired
    private UserUploadsService userUploadsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserUploadsMockMvc;

    private UserUploads userUploads;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserUploads createEntity(EntityManager em) {
        UserUploads userUploads = new UserUploads()
            .uploadId(DEFAULT_UPLOAD_ID)
            .userId(DEFAULT_USER_ID);
        return userUploads;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserUploads createUpdatedEntity(EntityManager em) {
        UserUploads userUploads = new UserUploads()
            .uploadId(UPDATED_UPLOAD_ID)
            .userId(UPDATED_USER_ID);
        return userUploads;
    }

    @BeforeEach
    public void initTest() {
        userUploads = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserUploads() throws Exception {
        int databaseSizeBeforeCreate = userUploadsRepository.findAll().size();
        // Create the UserUploads
        UserUploadsDTO userUploadsDTO = userUploadsMapper.toDto(userUploads);
        restUserUploadsMockMvc.perform(post("/api/user-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userUploadsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserUploads in the database
        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeCreate + 1);
        UserUploads testUserUploads = userUploadsList.get(userUploadsList.size() - 1);
        assertThat(testUserUploads.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
        assertThat(testUserUploads.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createUserUploadsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userUploadsRepository.findAll().size();

        // Create the UserUploads with an existing ID
        userUploads.setId(1L);
        UserUploadsDTO userUploadsDTO = userUploadsMapper.toDto(userUploads);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserUploadsMockMvc.perform(post("/api/user-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userUploadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserUploads in the database
        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUploadIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userUploadsRepository.findAll().size();
        // set the field null
        userUploads.setUploadId(null);

        // Create the UserUploads, which fails.
        UserUploadsDTO userUploadsDTO = userUploadsMapper.toDto(userUploads);


        restUserUploadsMockMvc.perform(post("/api/user-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userUploadsDTO)))
            .andExpect(status().isBadRequest());

        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userUploadsRepository.findAll().size();
        // set the field null
        userUploads.setUserId(null);

        // Create the UserUploads, which fails.
        UserUploadsDTO userUploadsDTO = userUploadsMapper.toDto(userUploads);


        restUserUploadsMockMvc.perform(post("/api/user-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userUploadsDTO)))
            .andExpect(status().isBadRequest());

        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserUploads() throws Exception {
        // Initialize the database
        userUploadsRepository.saveAndFlush(userUploads);

        // Get all the userUploadsList
        restUserUploadsMockMvc.perform(get("/api/user-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userUploads.getId().intValue())))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void getUserUploads() throws Exception {
        // Initialize the database
        userUploadsRepository.saveAndFlush(userUploads);

        // Get the userUploads
        restUserUploadsMockMvc.perform(get("/api/user-uploads/{id}", userUploads.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userUploads.getId().intValue()))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingUserUploads() throws Exception {
        // Get the userUploads
        restUserUploadsMockMvc.perform(get("/api/user-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserUploads() throws Exception {
        // Initialize the database
        userUploadsRepository.saveAndFlush(userUploads);

        int databaseSizeBeforeUpdate = userUploadsRepository.findAll().size();

        // Update the userUploads
        UserUploads updatedUserUploads = userUploadsRepository.findById(userUploads.getId()).get();
        // Disconnect from session so that the updates on updatedUserUploads are not directly saved in db
        em.detach(updatedUserUploads);
        updatedUserUploads
            .uploadId(UPDATED_UPLOAD_ID)
            .userId(UPDATED_USER_ID);
        UserUploadsDTO userUploadsDTO = userUploadsMapper.toDto(updatedUserUploads);

        restUserUploadsMockMvc.perform(put("/api/user-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userUploadsDTO)))
            .andExpect(status().isOk());

        // Validate the UserUploads in the database
        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeUpdate);
        UserUploads testUserUploads = userUploadsList.get(userUploadsList.size() - 1);
        assertThat(testUserUploads.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
        assertThat(testUserUploads.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserUploads() throws Exception {
        int databaseSizeBeforeUpdate = userUploadsRepository.findAll().size();

        // Create the UserUploads
        UserUploadsDTO userUploadsDTO = userUploadsMapper.toDto(userUploads);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserUploadsMockMvc.perform(put("/api/user-uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userUploadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserUploads in the database
        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserUploads() throws Exception {
        // Initialize the database
        userUploadsRepository.saveAndFlush(userUploads);

        int databaseSizeBeforeDelete = userUploadsRepository.findAll().size();

        // Delete the userUploads
        restUserUploadsMockMvc.perform(delete("/api/user-uploads/{id}", userUploads.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserUploads> userUploadsList = userUploadsRepository.findAll();
        assertThat(userUploadsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
