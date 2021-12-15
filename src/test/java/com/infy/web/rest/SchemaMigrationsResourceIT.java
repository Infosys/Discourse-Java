/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SchemaMigrations;
import com.infy.repository.SchemaMigrationsRepository;
import com.infy.service.SchemaMigrationsService;
import com.infy.service.dto.SchemaMigrationsDTO;
import com.infy.service.mapper.SchemaMigrationsMapper;

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
 * Integration tests for the {@link SchemaMigrationsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SchemaMigrationsResourceIT {

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private SchemaMigrationsRepository schemaMigrationsRepository;

    @Autowired
    private SchemaMigrationsMapper schemaMigrationsMapper;

    @Autowired
    private SchemaMigrationsService schemaMigrationsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchemaMigrationsMockMvc;

    private SchemaMigrations schemaMigrations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemaMigrations createEntity(EntityManager em) {
        SchemaMigrations schemaMigrations = new SchemaMigrations()
            .version(DEFAULT_VERSION);
        return schemaMigrations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemaMigrations createUpdatedEntity(EntityManager em) {
        SchemaMigrations schemaMigrations = new SchemaMigrations()
            .version(UPDATED_VERSION);
        return schemaMigrations;
    }

    @BeforeEach
    public void initTest() {
        schemaMigrations = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchemaMigrations() throws Exception {
        int databaseSizeBeforeCreate = schemaMigrationsRepository.findAll().size();
        // Create the SchemaMigrations
        SchemaMigrationsDTO schemaMigrationsDTO = schemaMigrationsMapper.toDto(schemaMigrations);
        restSchemaMigrationsMockMvc.perform(post("/api/schema-migrations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationsDTO)))
            .andExpect(status().isCreated());

        // Validate the SchemaMigrations in the database
        List<SchemaMigrations> schemaMigrationsList = schemaMigrationsRepository.findAll();
        assertThat(schemaMigrationsList).hasSize(databaseSizeBeforeCreate + 1);
        SchemaMigrations testSchemaMigrations = schemaMigrationsList.get(schemaMigrationsList.size() - 1);
        assertThat(testSchemaMigrations.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createSchemaMigrationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemaMigrationsRepository.findAll().size();

        // Create the SchemaMigrations with an existing ID
        schemaMigrations.setId(1L);
        SchemaMigrationsDTO schemaMigrationsDTO = schemaMigrationsMapper.toDto(schemaMigrations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemaMigrationsMockMvc.perform(post("/api/schema-migrations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaMigrations in the database
        List<SchemaMigrations> schemaMigrationsList = schemaMigrationsRepository.findAll();
        assertThat(schemaMigrationsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaMigrationsRepository.findAll().size();
        // set the field null
        schemaMigrations.setVersion(null);

        // Create the SchemaMigrations, which fails.
        SchemaMigrationsDTO schemaMigrationsDTO = schemaMigrationsMapper.toDto(schemaMigrations);


        restSchemaMigrationsMockMvc.perform(post("/api/schema-migrations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationsDTO)))
            .andExpect(status().isBadRequest());

        List<SchemaMigrations> schemaMigrationsList = schemaMigrationsRepository.findAll();
        assertThat(schemaMigrationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemaMigrations() throws Exception {
        // Initialize the database
        schemaMigrationsRepository.saveAndFlush(schemaMigrations);

        // Get all the schemaMigrationsList
        restSchemaMigrationsMockMvc.perform(get("/api/schema-migrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemaMigrations.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getSchemaMigrations() throws Exception {
        // Initialize the database
        schemaMigrationsRepository.saveAndFlush(schemaMigrations);

        // Get the schemaMigrations
        restSchemaMigrationsMockMvc.perform(get("/api/schema-migrations/{id}", schemaMigrations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schemaMigrations.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingSchemaMigrations() throws Exception {
        // Get the schemaMigrations
        restSchemaMigrationsMockMvc.perform(get("/api/schema-migrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchemaMigrations() throws Exception {
        // Initialize the database
        schemaMigrationsRepository.saveAndFlush(schemaMigrations);

        int databaseSizeBeforeUpdate = schemaMigrationsRepository.findAll().size();

        // Update the schemaMigrations
        SchemaMigrations updatedSchemaMigrations = schemaMigrationsRepository.findById(schemaMigrations.getId()).get();
        // Disconnect from session so that the updates on updatedSchemaMigrations are not directly saved in db
        em.detach(updatedSchemaMigrations);
        updatedSchemaMigrations
            .version(UPDATED_VERSION);
        SchemaMigrationsDTO schemaMigrationsDTO = schemaMigrationsMapper.toDto(updatedSchemaMigrations);

        restSchemaMigrationsMockMvc.perform(put("/api/schema-migrations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationsDTO)))
            .andExpect(status().isOk());

        // Validate the SchemaMigrations in the database
        List<SchemaMigrations> schemaMigrationsList = schemaMigrationsRepository.findAll();
        assertThat(schemaMigrationsList).hasSize(databaseSizeBeforeUpdate);
        SchemaMigrations testSchemaMigrations = schemaMigrationsList.get(schemaMigrationsList.size() - 1);
        assertThat(testSchemaMigrations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingSchemaMigrations() throws Exception {
        int databaseSizeBeforeUpdate = schemaMigrationsRepository.findAll().size();

        // Create the SchemaMigrations
        SchemaMigrationsDTO schemaMigrationsDTO = schemaMigrationsMapper.toDto(schemaMigrations);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchemaMigrationsMockMvc.perform(put("/api/schema-migrations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaMigrations in the database
        List<SchemaMigrations> schemaMigrationsList = schemaMigrationsRepository.findAll();
        assertThat(schemaMigrationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchemaMigrations() throws Exception {
        // Initialize the database
        schemaMigrationsRepository.saveAndFlush(schemaMigrations);

        int databaseSizeBeforeDelete = schemaMigrationsRepository.findAll().size();

        // Delete the schemaMigrations
        restSchemaMigrationsMockMvc.perform(delete("/api/schema-migrations/{id}", schemaMigrations.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchemaMigrations> schemaMigrationsList = schemaMigrationsRepository.findAll();
        assertThat(schemaMigrationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
