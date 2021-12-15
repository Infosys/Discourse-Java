/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ArInternalMetadata;
import com.infy.repository.ArInternalMetadataRepository;
import com.infy.service.ArInternalMetadataService;
import com.infy.service.dto.ArInternalMetadataDTO;
import com.infy.service.mapper.ArInternalMetadataMapper;

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
 * Integration tests for the {@link ArInternalMetadataResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ArInternalMetadataResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ArInternalMetadataRepository arInternalMetadataRepository;

    @Autowired
    private ArInternalMetadataMapper arInternalMetadataMapper;

    @Autowired
    private ArInternalMetadataService arInternalMetadataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArInternalMetadataMockMvc;

    private ArInternalMetadata arInternalMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArInternalMetadata createEntity(EntityManager em) {
        ArInternalMetadata arInternalMetadata = new ArInternalMetadata()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return arInternalMetadata;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArInternalMetadata createUpdatedEntity(EntityManager em) {
        ArInternalMetadata arInternalMetadata = new ArInternalMetadata()
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        return arInternalMetadata;
    }

    @BeforeEach
    public void initTest() {
        arInternalMetadata = createEntity(em);
    }

    @Test
    @Transactional
    public void createArInternalMetadata() throws Exception {
        int databaseSizeBeforeCreate = arInternalMetadataRepository.findAll().size();
        // Create the ArInternalMetadata
        ArInternalMetadataDTO arInternalMetadataDTO = arInternalMetadataMapper.toDto(arInternalMetadata);
        restArInternalMetadataMockMvc.perform(post("/api/ar-internal-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arInternalMetadataDTO)))
            .andExpect(status().isCreated());

        // Validate the ArInternalMetadata in the database
        List<ArInternalMetadata> arInternalMetadataList = arInternalMetadataRepository.findAll();
        assertThat(arInternalMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        ArInternalMetadata testArInternalMetadata = arInternalMetadataList.get(arInternalMetadataList.size() - 1);
        assertThat(testArInternalMetadata.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testArInternalMetadata.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createArInternalMetadataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arInternalMetadataRepository.findAll().size();

        // Create the ArInternalMetadata with an existing ID
        arInternalMetadata.setId(1L);
        ArInternalMetadataDTO arInternalMetadataDTO = arInternalMetadataMapper.toDto(arInternalMetadata);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArInternalMetadataMockMvc.perform(post("/api/ar-internal-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arInternalMetadataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArInternalMetadata in the database
        List<ArInternalMetadata> arInternalMetadataList = arInternalMetadataRepository.findAll();
        assertThat(arInternalMetadataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = arInternalMetadataRepository.findAll().size();
        // set the field null
        arInternalMetadata.setKey(null);

        // Create the ArInternalMetadata, which fails.
        ArInternalMetadataDTO arInternalMetadataDTO = arInternalMetadataMapper.toDto(arInternalMetadata);


        restArInternalMetadataMockMvc.perform(post("/api/ar-internal-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arInternalMetadataDTO)))
            .andExpect(status().isBadRequest());

        List<ArInternalMetadata> arInternalMetadataList = arInternalMetadataRepository.findAll();
        assertThat(arInternalMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArInternalMetadata() throws Exception {
        // Initialize the database
        arInternalMetadataRepository.saveAndFlush(arInternalMetadata);

        // Get all the arInternalMetadataList
        restArInternalMetadataMockMvc.perform(get("/api/ar-internal-metadata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arInternalMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getArInternalMetadata() throws Exception {
        // Initialize the database
        arInternalMetadataRepository.saveAndFlush(arInternalMetadata);

        // Get the arInternalMetadata
        restArInternalMetadataMockMvc.perform(get("/api/ar-internal-metadata/{id}", arInternalMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arInternalMetadata.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingArInternalMetadata() throws Exception {
        // Get the arInternalMetadata
        restArInternalMetadataMockMvc.perform(get("/api/ar-internal-metadata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArInternalMetadata() throws Exception {
        // Initialize the database
        arInternalMetadataRepository.saveAndFlush(arInternalMetadata);

        int databaseSizeBeforeUpdate = arInternalMetadataRepository.findAll().size();

        // Update the arInternalMetadata
        ArInternalMetadata updatedArInternalMetadata = arInternalMetadataRepository.findById(arInternalMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedArInternalMetadata are not directly saved in db
        em.detach(updatedArInternalMetadata);
        updatedArInternalMetadata
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        ArInternalMetadataDTO arInternalMetadataDTO = arInternalMetadataMapper.toDto(updatedArInternalMetadata);

        restArInternalMetadataMockMvc.perform(put("/api/ar-internal-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arInternalMetadataDTO)))
            .andExpect(status().isOk());

        // Validate the ArInternalMetadata in the database
        List<ArInternalMetadata> arInternalMetadataList = arInternalMetadataRepository.findAll();
        assertThat(arInternalMetadataList).hasSize(databaseSizeBeforeUpdate);
        ArInternalMetadata testArInternalMetadata = arInternalMetadataList.get(arInternalMetadataList.size() - 1);
        assertThat(testArInternalMetadata.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testArInternalMetadata.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingArInternalMetadata() throws Exception {
        int databaseSizeBeforeUpdate = arInternalMetadataRepository.findAll().size();

        // Create the ArInternalMetadata
        ArInternalMetadataDTO arInternalMetadataDTO = arInternalMetadataMapper.toDto(arInternalMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArInternalMetadataMockMvc.perform(put("/api/ar-internal-metadata").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arInternalMetadataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArInternalMetadata in the database
        List<ArInternalMetadata> arInternalMetadataList = arInternalMetadataRepository.findAll();
        assertThat(arInternalMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArInternalMetadata() throws Exception {
        // Initialize the database
        arInternalMetadataRepository.saveAndFlush(arInternalMetadata);

        int databaseSizeBeforeDelete = arInternalMetadataRepository.findAll().size();

        // Delete the arInternalMetadata
        restArInternalMetadataMockMvc.perform(delete("/api/ar-internal-metadata/{id}", arInternalMetadata.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArInternalMetadata> arInternalMetadataList = arInternalMetadataRepository.findAll();
        assertThat(arInternalMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
