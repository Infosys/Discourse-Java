/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserExports;
import com.infy.repository.UserExportsRepository;
import com.infy.service.UserExportsService;
import com.infy.service.dto.UserExportsDTO;
import com.infy.service.mapper.UserExportsMapper;

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
 * Integration tests for the {@link UserExportsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserExportsResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private UserExportsRepository userExportsRepository;

    @Autowired
    private UserExportsMapper userExportsMapper;

    @Autowired
    private UserExportsService userExportsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExportsMockMvc;

    private UserExports userExports;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExports createEntity(EntityManager em) {
        UserExports userExports = new UserExports()
            .fileName(DEFAULT_FILE_NAME)
            .userId(DEFAULT_USER_ID)
            .uploadId(DEFAULT_UPLOAD_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return userExports;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExports createUpdatedEntity(EntityManager em) {
        UserExports userExports = new UserExports()
            .fileName(UPDATED_FILE_NAME)
            .userId(UPDATED_USER_ID)
            .uploadId(UPDATED_UPLOAD_ID)
            .topicId(UPDATED_TOPIC_ID);
        return userExports;
    }

    @BeforeEach
    public void initTest() {
        userExports = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExports() throws Exception {
        int databaseSizeBeforeCreate = userExportsRepository.findAll().size();
        // Create the UserExports
        UserExportsDTO userExportsDTO = userExportsMapper.toDto(userExports);
        restUserExportsMockMvc.perform(post("/api/user-exports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExportsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExports in the database
        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeCreate + 1);
        UserExports testUserExports = userExportsList.get(userExportsList.size() - 1);
        assertThat(testUserExports.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testUserExports.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserExports.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
        assertThat(testUserExports.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createUserExportsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExportsRepository.findAll().size();

        // Create the UserExports with an existing ID
        userExports.setId(1L);
        UserExportsDTO userExportsDTO = userExportsMapper.toDto(userExports);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExportsMockMvc.perform(post("/api/user-exports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExportsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExports in the database
        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExportsRepository.findAll().size();
        // set the field null
        userExports.setFileName(null);

        // Create the UserExports, which fails.
        UserExportsDTO userExportsDTO = userExportsMapper.toDto(userExports);


        restUserExportsMockMvc.perform(post("/api/user-exports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExportsDTO)))
            .andExpect(status().isBadRequest());

        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExportsRepository.findAll().size();
        // set the field null
        userExports.setUserId(null);

        // Create the UserExports, which fails.
        UserExportsDTO userExportsDTO = userExportsMapper.toDto(userExports);


        restUserExportsMockMvc.perform(post("/api/user-exports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExportsDTO)))
            .andExpect(status().isBadRequest());

        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserExports() throws Exception {
        // Initialize the database
        userExportsRepository.saveAndFlush(userExports);

        // Get all the userExportsList
        restUserExportsMockMvc.perform(get("/api/user-exports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExports.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserExports() throws Exception {
        // Initialize the database
        userExportsRepository.saveAndFlush(userExports);

        // Get the userExports
        restUserExportsMockMvc.perform(get("/api/user-exports/{id}", userExports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExports.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserExports() throws Exception {
        // Get the userExports
        restUserExportsMockMvc.perform(get("/api/user-exports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExports() throws Exception {
        // Initialize the database
        userExportsRepository.saveAndFlush(userExports);

        int databaseSizeBeforeUpdate = userExportsRepository.findAll().size();

        // Update the userExports
        UserExports updatedUserExports = userExportsRepository.findById(userExports.getId()).get();
        // Disconnect from session so that the updates on updatedUserExports are not directly saved in db
        em.detach(updatedUserExports);
        updatedUserExports
            .fileName(UPDATED_FILE_NAME)
            .userId(UPDATED_USER_ID)
            .uploadId(UPDATED_UPLOAD_ID)
            .topicId(UPDATED_TOPIC_ID);
        UserExportsDTO userExportsDTO = userExportsMapper.toDto(updatedUserExports);

        restUserExportsMockMvc.perform(put("/api/user-exports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExportsDTO)))
            .andExpect(status().isOk());

        // Validate the UserExports in the database
        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeUpdate);
        UserExports testUserExports = userExportsList.get(userExportsList.size() - 1);
        assertThat(testUserExports.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testUserExports.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserExports.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
        assertThat(testUserExports.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExports() throws Exception {
        int databaseSizeBeforeUpdate = userExportsRepository.findAll().size();

        // Create the UserExports
        UserExportsDTO userExportsDTO = userExportsMapper.toDto(userExports);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExportsMockMvc.perform(put("/api/user-exports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExportsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExports in the database
        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExports() throws Exception {
        // Initialize the database
        userExportsRepository.saveAndFlush(userExports);

        int databaseSizeBeforeDelete = userExportsRepository.findAll().size();

        // Delete the userExports
        restUserExportsMockMvc.perform(delete("/api/user-exports/{id}", userExports.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExports> userExportsList = userExportsRepository.findAll();
        assertThat(userExportsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
