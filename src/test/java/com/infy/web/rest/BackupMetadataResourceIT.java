/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.BackupMetadata;
import com.infy.repository.BackupMetadataRepository;
import com.infy.service.BackupMetadataService;
import com.infy.service.dto.BackupMetadataDTO;
import com.infy.service.mapper.BackupMetadataMapper;

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
 * Integration tests for the {@link BackupMetadataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BackupMetadataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private BackupMetadataRepository backupMetadataRepository;

    @Autowired
    private BackupMetadataMapper backupMetadataMapper;

    @Autowired
    private BackupMetadataService backupMetadataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBackupMetadataMockMvc;

    private BackupMetadata backupMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackupMetadata createEntity(EntityManager em) {
        BackupMetadata backupMetadata = new BackupMetadata()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return backupMetadata;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackupMetadata createUpdatedEntity(EntityManager em) {
        BackupMetadata backupMetadata = new BackupMetadata()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return backupMetadata;
    }

    @BeforeEach
    public void initTest() {
        backupMetadata = createEntity(em);
    }

    @Test
    @Transactional
    public void createBackupMetadata() throws Exception {
        int databaseSizeBeforeCreate = backupMetadataRepository.findAll().size();
        // Create the BackupMetadata
        BackupMetadataDTO backupMetadataDTO = backupMetadataMapper.toDto(backupMetadata);
        restBackupMetadataMockMvc.perform(post("/api/backup-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupMetadataDTO)))
            .andExpect(status().isCreated());

        // Validate the BackupMetadata in the database
        List<BackupMetadata> backupMetadataList = backupMetadataRepository.findAll();
        assertThat(backupMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        BackupMetadata testBackupMetadata = backupMetadataList.get(backupMetadataList.size() - 1);
        assertThat(testBackupMetadata.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBackupMetadata.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBackupMetadataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backupMetadataRepository.findAll().size();

        // Create the BackupMetadata with an existing ID
        backupMetadata.setId(1L);
        BackupMetadataDTO backupMetadataDTO = backupMetadataMapper.toDto(backupMetadata);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackupMetadataMockMvc.perform(post("/api/backup-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupMetadataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackupMetadata in the database
        List<BackupMetadata> backupMetadataList = backupMetadataRepository.findAll();
        assertThat(backupMetadataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = backupMetadataRepository.findAll().size();
        // set the field null
        backupMetadata.setName(null);

        // Create the BackupMetadata, which fails.
        BackupMetadataDTO backupMetadataDTO = backupMetadataMapper.toDto(backupMetadata);


        restBackupMetadataMockMvc.perform(post("/api/backup-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupMetadataDTO)))
            .andExpect(status().isBadRequest());

        List<BackupMetadata> backupMetadataList = backupMetadataRepository.findAll();
        assertThat(backupMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBackupMetadata() throws Exception {
        // Initialize the database
        backupMetadataRepository.saveAndFlush(backupMetadata);

        // Get all the backupMetadataList
        restBackupMetadataMockMvc.perform(get("/api/backup-metadata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backupMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getBackupMetadata() throws Exception {
        // Initialize the database
        backupMetadataRepository.saveAndFlush(backupMetadata);

        // Get the backupMetadata
        restBackupMetadataMockMvc.perform(get("/api/backup-metadata/{id}", backupMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(backupMetadata.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingBackupMetadata() throws Exception {
        // Get the backupMetadata
        restBackupMetadataMockMvc.perform(get("/api/backup-metadata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackupMetadata() throws Exception {
        // Initialize the database
        backupMetadataRepository.saveAndFlush(backupMetadata);

        int databaseSizeBeforeUpdate = backupMetadataRepository.findAll().size();

        // Update the backupMetadata
        BackupMetadata updatedBackupMetadata = backupMetadataRepository.findById(backupMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedBackupMetadata are not directly saved in db
        em.detach(updatedBackupMetadata);
        updatedBackupMetadata
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        BackupMetadataDTO backupMetadataDTO = backupMetadataMapper.toDto(updatedBackupMetadata);

        restBackupMetadataMockMvc.perform(put("/api/backup-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupMetadataDTO)))
            .andExpect(status().isOk());

        // Validate the BackupMetadata in the database
        List<BackupMetadata> backupMetadataList = backupMetadataRepository.findAll();
        assertThat(backupMetadataList).hasSize(databaseSizeBeforeUpdate);
        BackupMetadata testBackupMetadata = backupMetadataList.get(backupMetadataList.size() - 1);
        assertThat(testBackupMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBackupMetadata.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBackupMetadata() throws Exception {
        int databaseSizeBeforeUpdate = backupMetadataRepository.findAll().size();

        // Create the BackupMetadata
        BackupMetadataDTO backupMetadataDTO = backupMetadataMapper.toDto(backupMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBackupMetadataMockMvc.perform(put("/api/backup-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backupMetadataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackupMetadata in the database
        List<BackupMetadata> backupMetadataList = backupMetadataRepository.findAll();
        assertThat(backupMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBackupMetadata() throws Exception {
        // Initialize the database
        backupMetadataRepository.saveAndFlush(backupMetadata);

        int databaseSizeBeforeDelete = backupMetadataRepository.findAll().size();

        // Delete the backupMetadata
        restBackupMetadataMockMvc.perform(delete("/api/backup-metadata/{id}", backupMetadata.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BackupMetadata> backupMetadataList = backupMetadataRepository.findAll();
        assertThat(backupMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
