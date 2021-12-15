/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.BadgeTypes;
import com.infy.repository.BadgeTypesRepository;
import com.infy.service.BadgeTypesService;
import com.infy.service.dto.BadgeTypesDTO;
import com.infy.service.mapper.BadgeTypesMapper;

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
 * Integration tests for the {@link BadgeTypesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BadgeTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BadgeTypesRepository badgeTypesRepository;

    @Autowired
    private BadgeTypesMapper badgeTypesMapper;

    @Autowired
    private BadgeTypesService badgeTypesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgeTypesMockMvc;

    private BadgeTypes badgeTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeTypes createEntity(EntityManager em) {
        BadgeTypes badgeTypes = new BadgeTypes()
            .name(DEFAULT_NAME);
        return badgeTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeTypes createUpdatedEntity(EntityManager em) {
        BadgeTypes badgeTypes = new BadgeTypes()
            .name(UPDATED_NAME);
        return badgeTypes;
    }

    @BeforeEach
    public void initTest() {
        badgeTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgeTypes() throws Exception {
        int databaseSizeBeforeCreate = badgeTypesRepository.findAll().size();
        // Create the BadgeTypes
        BadgeTypesDTO badgeTypesDTO = badgeTypesMapper.toDto(badgeTypes);
        restBadgeTypesMockMvc.perform(post("/api/badge-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the BadgeTypes in the database
        List<BadgeTypes> badgeTypesList = badgeTypesRepository.findAll();
        assertThat(badgeTypesList).hasSize(databaseSizeBeforeCreate + 1);
        BadgeTypes testBadgeTypes = badgeTypesList.get(badgeTypesList.size() - 1);
        assertThat(testBadgeTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBadgeTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeTypesRepository.findAll().size();

        // Create the BadgeTypes with an existing ID
        badgeTypes.setId(1L);
        BadgeTypesDTO badgeTypesDTO = badgeTypesMapper.toDto(badgeTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeTypesMockMvc.perform(post("/api/badge-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeTypes in the database
        List<BadgeTypes> badgeTypesList = badgeTypesRepository.findAll();
        assertThat(badgeTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeTypesRepository.findAll().size();
        // set the field null
        badgeTypes.setName(null);

        // Create the BadgeTypes, which fails.
        BadgeTypesDTO badgeTypesDTO = badgeTypesMapper.toDto(badgeTypes);


        restBadgeTypesMockMvc.perform(post("/api/badge-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeTypesDTO)))
            .andExpect(status().isBadRequest());

        List<BadgeTypes> badgeTypesList = badgeTypesRepository.findAll();
        assertThat(badgeTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadgeTypes() throws Exception {
        // Initialize the database
        badgeTypesRepository.saveAndFlush(badgeTypes);

        // Get all the badgeTypesList
        restBadgeTypesMockMvc.perform(get("/api/badge-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getBadgeTypes() throws Exception {
        // Initialize the database
        badgeTypesRepository.saveAndFlush(badgeTypes);

        // Get the badgeTypes
        restBadgeTypesMockMvc.perform(get("/api/badge-types/{id}", badgeTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badgeTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBadgeTypes() throws Exception {
        // Get the badgeTypes
        restBadgeTypesMockMvc.perform(get("/api/badge-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgeTypes() throws Exception {
        // Initialize the database
        badgeTypesRepository.saveAndFlush(badgeTypes);

        int databaseSizeBeforeUpdate = badgeTypesRepository.findAll().size();

        // Update the badgeTypes
        BadgeTypes updatedBadgeTypes = badgeTypesRepository.findById(badgeTypes.getId()).get();
        // Disconnect from session so that the updates on updatedBadgeTypes are not directly saved in db
        em.detach(updatedBadgeTypes);
        updatedBadgeTypes
            .name(UPDATED_NAME);
        BadgeTypesDTO badgeTypesDTO = badgeTypesMapper.toDto(updatedBadgeTypes);

        restBadgeTypesMockMvc.perform(put("/api/badge-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeTypesDTO)))
            .andExpect(status().isOk());

        // Validate the BadgeTypes in the database
        List<BadgeTypes> badgeTypesList = badgeTypesRepository.findAll();
        assertThat(badgeTypesList).hasSize(databaseSizeBeforeUpdate);
        BadgeTypes testBadgeTypes = badgeTypesList.get(badgeTypesList.size() - 1);
        assertThat(testBadgeTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgeTypes() throws Exception {
        int databaseSizeBeforeUpdate = badgeTypesRepository.findAll().size();

        // Create the BadgeTypes
        BadgeTypesDTO badgeTypesDTO = badgeTypesMapper.toDto(badgeTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeTypesMockMvc.perform(put("/api/badge-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeTypes in the database
        List<BadgeTypes> badgeTypesList = badgeTypesRepository.findAll();
        assertThat(badgeTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadgeTypes() throws Exception {
        // Initialize the database
        badgeTypesRepository.saveAndFlush(badgeTypes);

        int databaseSizeBeforeDelete = badgeTypesRepository.findAll().size();

        // Delete the badgeTypes
        restBadgeTypesMockMvc.perform(delete("/api/badge-types/{id}", badgeTypes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BadgeTypes> badgeTypesList = badgeTypesRepository.findAll();
        assertThat(badgeTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
