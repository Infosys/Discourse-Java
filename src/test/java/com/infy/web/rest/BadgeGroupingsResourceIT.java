/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.BadgeGroupings;
import com.infy.repository.BadgeGroupingsRepository;
import com.infy.service.BadgeGroupingsService;
import com.infy.service.dto.BadgeGroupingsDTO;
import com.infy.service.mapper.BadgeGroupingsMapper;

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
 * Integration tests for the {@link BadgeGroupingsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BadgeGroupingsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_POSITION = 1L;
    private static final Long UPDATED_POSITION = 2L;

    @Autowired
    private BadgeGroupingsRepository badgeGroupingsRepository;

    @Autowired
    private BadgeGroupingsMapper badgeGroupingsMapper;

    @Autowired
    private BadgeGroupingsService badgeGroupingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgeGroupingsMockMvc;

    private BadgeGroupings badgeGroupings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeGroupings createEntity(EntityManager em) {
        BadgeGroupings badgeGroupings = new BadgeGroupings()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .position(DEFAULT_POSITION);
        return badgeGroupings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeGroupings createUpdatedEntity(EntityManager em) {
        BadgeGroupings badgeGroupings = new BadgeGroupings()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION);
        return badgeGroupings;
    }

    @BeforeEach
    public void initTest() {
        badgeGroupings = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgeGroupings() throws Exception {
        int databaseSizeBeforeCreate = badgeGroupingsRepository.findAll().size();
        // Create the BadgeGroupings
        BadgeGroupingsDTO badgeGroupingsDTO = badgeGroupingsMapper.toDto(badgeGroupings);
        restBadgeGroupingsMockMvc.perform(post("/api/badge-groupings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeGroupingsDTO)))
            .andExpect(status().isCreated());

        // Validate the BadgeGroupings in the database
        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeCreate + 1);
        BadgeGroupings testBadgeGroupings = badgeGroupingsList.get(badgeGroupingsList.size() - 1);
        assertThat(testBadgeGroupings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBadgeGroupings.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBadgeGroupings.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void createBadgeGroupingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeGroupingsRepository.findAll().size();

        // Create the BadgeGroupings with an existing ID
        badgeGroupings.setId(1L);
        BadgeGroupingsDTO badgeGroupingsDTO = badgeGroupingsMapper.toDto(badgeGroupings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeGroupingsMockMvc.perform(post("/api/badge-groupings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeGroupingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeGroupings in the database
        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeGroupingsRepository.findAll().size();
        // set the field null
        badgeGroupings.setName(null);

        // Create the BadgeGroupings, which fails.
        BadgeGroupingsDTO badgeGroupingsDTO = badgeGroupingsMapper.toDto(badgeGroupings);


        restBadgeGroupingsMockMvc.perform(post("/api/badge-groupings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeGroupingsDTO)))
            .andExpect(status().isBadRequest());

        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeGroupingsRepository.findAll().size();
        // set the field null
        badgeGroupings.setPosition(null);

        // Create the BadgeGroupings, which fails.
        BadgeGroupingsDTO badgeGroupingsDTO = badgeGroupingsMapper.toDto(badgeGroupings);


        restBadgeGroupingsMockMvc.perform(post("/api/badge-groupings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeGroupingsDTO)))
            .andExpect(status().isBadRequest());

        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadgeGroupings() throws Exception {
        // Initialize the database
        badgeGroupingsRepository.saveAndFlush(badgeGroupings);

        // Get all the badgeGroupingsList
        restBadgeGroupingsMockMvc.perform(get("/api/badge-groupings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeGroupings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));
    }

    @Test
    @Transactional
    public void getBadgeGroupings() throws Exception {
        // Initialize the database
        badgeGroupingsRepository.saveAndFlush(badgeGroupings);

        // Get the badgeGroupings
        restBadgeGroupingsMockMvc.perform(get("/api/badge-groupings/{id}", badgeGroupings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badgeGroupings.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBadgeGroupings() throws Exception {
        // Get the badgeGroupings
        restBadgeGroupingsMockMvc.perform(get("/api/badge-groupings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgeGroupings() throws Exception {
        // Initialize the database
        badgeGroupingsRepository.saveAndFlush(badgeGroupings);

        int databaseSizeBeforeUpdate = badgeGroupingsRepository.findAll().size();

        // Update the badgeGroupings
        BadgeGroupings updatedBadgeGroupings = badgeGroupingsRepository.findById(badgeGroupings.getId()).get();
        // Disconnect from session so that the updates on updatedBadgeGroupings are not directly saved in db
        em.detach(updatedBadgeGroupings);
        updatedBadgeGroupings
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION);
        BadgeGroupingsDTO badgeGroupingsDTO = badgeGroupingsMapper.toDto(updatedBadgeGroupings);

        restBadgeGroupingsMockMvc.perform(put("/api/badge-groupings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeGroupingsDTO)))
            .andExpect(status().isOk());

        // Validate the BadgeGroupings in the database
        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeUpdate);
        BadgeGroupings testBadgeGroupings = badgeGroupingsList.get(badgeGroupingsList.size() - 1);
        assertThat(testBadgeGroupings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBadgeGroupings.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBadgeGroupings.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgeGroupings() throws Exception {
        int databaseSizeBeforeUpdate = badgeGroupingsRepository.findAll().size();

        // Create the BadgeGroupings
        BadgeGroupingsDTO badgeGroupingsDTO = badgeGroupingsMapper.toDto(badgeGroupings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeGroupingsMockMvc.perform(put("/api/badge-groupings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeGroupingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeGroupings in the database
        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadgeGroupings() throws Exception {
        // Initialize the database
        badgeGroupingsRepository.saveAndFlush(badgeGroupings);

        int databaseSizeBeforeDelete = badgeGroupingsRepository.findAll().size();

        // Delete the badgeGroupings
        restBadgeGroupingsMockMvc.perform(delete("/api/badge-groupings/{id}", badgeGroupings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BadgeGroupings> badgeGroupingsList = badgeGroupingsRepository.findAll();
        assertThat(badgeGroupingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
