/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SchemaMigrationDetails;
import com.infy.repository.SchemaMigrationDetailsRepository;
import com.infy.service.SchemaMigrationDetailsService;
import com.infy.service.dto.SchemaMigrationDetailsDTO;
import com.infy.service.mapper.SchemaMigrationDetailsMapper;

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
 * Integration tests for the {@link SchemaMigrationDetailsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SchemaMigrationDetailsResourceIT {

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_GIT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_GIT_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_RAILS_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_RAILS_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final String DEFAULT_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION = "BBBBBBBBBB";

    @Autowired
    private SchemaMigrationDetailsRepository schemaMigrationDetailsRepository;

    @Autowired
    private SchemaMigrationDetailsMapper schemaMigrationDetailsMapper;

    @Autowired
    private SchemaMigrationDetailsService schemaMigrationDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchemaMigrationDetailsMockMvc;

    private SchemaMigrationDetails schemaMigrationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemaMigrationDetails createEntity(EntityManager em) {
        SchemaMigrationDetails schemaMigrationDetails = new SchemaMigrationDetails()
            .version(DEFAULT_VERSION)
            .name(DEFAULT_NAME)
            .hostname(DEFAULT_HOSTNAME)
            .gitVersion(DEFAULT_GIT_VERSION)
            .railsVersion(DEFAULT_RAILS_VERSION)
            .duration(DEFAULT_DURATION)
            .direction(DEFAULT_DIRECTION);
        return schemaMigrationDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemaMigrationDetails createUpdatedEntity(EntityManager em) {
        SchemaMigrationDetails schemaMigrationDetails = new SchemaMigrationDetails()
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .hostname(UPDATED_HOSTNAME)
            .gitVersion(UPDATED_GIT_VERSION)
            .railsVersion(UPDATED_RAILS_VERSION)
            .duration(UPDATED_DURATION)
            .direction(UPDATED_DIRECTION);
        return schemaMigrationDetails;
    }

    @BeforeEach
    public void initTest() {
        schemaMigrationDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchemaMigrationDetails() throws Exception {
        int databaseSizeBeforeCreate = schemaMigrationDetailsRepository.findAll().size();
        // Create the SchemaMigrationDetails
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO = schemaMigrationDetailsMapper.toDto(schemaMigrationDetails);
        restSchemaMigrationDetailsMockMvc.perform(post("/api/schema-migration-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the SchemaMigrationDetails in the database
        List<SchemaMigrationDetails> schemaMigrationDetailsList = schemaMigrationDetailsRepository.findAll();
        assertThat(schemaMigrationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SchemaMigrationDetails testSchemaMigrationDetails = schemaMigrationDetailsList.get(schemaMigrationDetailsList.size() - 1);
        assertThat(testSchemaMigrationDetails.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSchemaMigrationDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchemaMigrationDetails.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testSchemaMigrationDetails.getGitVersion()).isEqualTo(DEFAULT_GIT_VERSION);
        assertThat(testSchemaMigrationDetails.getRailsVersion()).isEqualTo(DEFAULT_RAILS_VERSION);
        assertThat(testSchemaMigrationDetails.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSchemaMigrationDetails.getDirection()).isEqualTo(DEFAULT_DIRECTION);
    }

    @Test
    @Transactional
    public void createSchemaMigrationDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemaMigrationDetailsRepository.findAll().size();

        // Create the SchemaMigrationDetails with an existing ID
        schemaMigrationDetails.setId(1L);
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO = schemaMigrationDetailsMapper.toDto(schemaMigrationDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemaMigrationDetailsMockMvc.perform(post("/api/schema-migration-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaMigrationDetails in the database
        List<SchemaMigrationDetails> schemaMigrationDetailsList = schemaMigrationDetailsRepository.findAll();
        assertThat(schemaMigrationDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaMigrationDetailsRepository.findAll().size();
        // set the field null
        schemaMigrationDetails.setVersion(null);

        // Create the SchemaMigrationDetails, which fails.
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO = schemaMigrationDetailsMapper.toDto(schemaMigrationDetails);


        restSchemaMigrationDetailsMockMvc.perform(post("/api/schema-migration-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<SchemaMigrationDetails> schemaMigrationDetailsList = schemaMigrationDetailsRepository.findAll();
        assertThat(schemaMigrationDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemaMigrationDetails() throws Exception {
        // Initialize the database
        schemaMigrationDetailsRepository.saveAndFlush(schemaMigrationDetails);

        // Get all the schemaMigrationDetailsList
        restSchemaMigrationDetailsMockMvc.perform(get("/api/schema-migration-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemaMigrationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].gitVersion").value(hasItem(DEFAULT_GIT_VERSION)))
            .andExpect(jsonPath("$.[*].railsVersion").value(hasItem(DEFAULT_RAILS_VERSION)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION)));
    }

    @Test
    @Transactional
    public void getSchemaMigrationDetails() throws Exception {
        // Initialize the database
        schemaMigrationDetailsRepository.saveAndFlush(schemaMigrationDetails);

        // Get the schemaMigrationDetails
        restSchemaMigrationDetailsMockMvc.perform(get("/api/schema-migration-details/{id}", schemaMigrationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schemaMigrationDetails.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME))
            .andExpect(jsonPath("$.gitVersion").value(DEFAULT_GIT_VERSION))
            .andExpect(jsonPath("$.railsVersion").value(DEFAULT_RAILS_VERSION))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION));
    }
    @Test
    @Transactional
    public void getNonExistingSchemaMigrationDetails() throws Exception {
        // Get the schemaMigrationDetails
        restSchemaMigrationDetailsMockMvc.perform(get("/api/schema-migration-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchemaMigrationDetails() throws Exception {
        // Initialize the database
        schemaMigrationDetailsRepository.saveAndFlush(schemaMigrationDetails);

        int databaseSizeBeforeUpdate = schemaMigrationDetailsRepository.findAll().size();

        // Update the schemaMigrationDetails
        SchemaMigrationDetails updatedSchemaMigrationDetails = schemaMigrationDetailsRepository.findById(schemaMigrationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSchemaMigrationDetails are not directly saved in db
        em.detach(updatedSchemaMigrationDetails);
        updatedSchemaMigrationDetails
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .hostname(UPDATED_HOSTNAME)
            .gitVersion(UPDATED_GIT_VERSION)
            .railsVersion(UPDATED_RAILS_VERSION)
            .duration(UPDATED_DURATION)
            .direction(UPDATED_DIRECTION);
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO = schemaMigrationDetailsMapper.toDto(updatedSchemaMigrationDetails);

        restSchemaMigrationDetailsMockMvc.perform(put("/api/schema-migration-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the SchemaMigrationDetails in the database
        List<SchemaMigrationDetails> schemaMigrationDetailsList = schemaMigrationDetailsRepository.findAll();
        assertThat(schemaMigrationDetailsList).hasSize(databaseSizeBeforeUpdate);
        SchemaMigrationDetails testSchemaMigrationDetails = schemaMigrationDetailsList.get(schemaMigrationDetailsList.size() - 1);
        assertThat(testSchemaMigrationDetails.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSchemaMigrationDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchemaMigrationDetails.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testSchemaMigrationDetails.getGitVersion()).isEqualTo(UPDATED_GIT_VERSION);
        assertThat(testSchemaMigrationDetails.getRailsVersion()).isEqualTo(UPDATED_RAILS_VERSION);
        assertThat(testSchemaMigrationDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSchemaMigrationDetails.getDirection()).isEqualTo(UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSchemaMigrationDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemaMigrationDetailsRepository.findAll().size();

        // Create the SchemaMigrationDetails
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO = schemaMigrationDetailsMapper.toDto(schemaMigrationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchemaMigrationDetailsMockMvc.perform(put("/api/schema-migration-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaMigrationDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaMigrationDetails in the database
        List<SchemaMigrationDetails> schemaMigrationDetailsList = schemaMigrationDetailsRepository.findAll();
        assertThat(schemaMigrationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchemaMigrationDetails() throws Exception {
        // Initialize the database
        schemaMigrationDetailsRepository.saveAndFlush(schemaMigrationDetails);

        int databaseSizeBeforeDelete = schemaMigrationDetailsRepository.findAll().size();

        // Delete the schemaMigrationDetails
        restSchemaMigrationDetailsMockMvc.perform(delete("/api/schema-migration-details/{id}", schemaMigrationDetails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchemaMigrationDetails> schemaMigrationDetailsList = schemaMigrationDetailsRepository.findAll();
        assertThat(schemaMigrationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
