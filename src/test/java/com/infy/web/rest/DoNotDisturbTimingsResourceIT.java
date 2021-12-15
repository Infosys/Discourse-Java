/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.DoNotDisturbTimings;
import com.infy.repository.DoNotDisturbTimingsRepository;
import com.infy.service.DoNotDisturbTimingsService;
import com.infy.service.dto.DoNotDisturbTimingsDTO;
import com.infy.service.mapper.DoNotDisturbTimingsMapper;

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
 * Integration tests for the {@link DoNotDisturbTimingsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DoNotDisturbTimingsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTS_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTS_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDS_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDS_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SCHEDULED = false;
    private static final Boolean UPDATED_SCHEDULED = true;

    @Autowired
    private DoNotDisturbTimingsRepository doNotDisturbTimingsRepository;

    @Autowired
    private DoNotDisturbTimingsMapper doNotDisturbTimingsMapper;

    @Autowired
    private DoNotDisturbTimingsService doNotDisturbTimingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoNotDisturbTimingsMockMvc;

    private DoNotDisturbTimings doNotDisturbTimings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoNotDisturbTimings createEntity(EntityManager em) {
        DoNotDisturbTimings doNotDisturbTimings = new DoNotDisturbTimings()
            .userId(DEFAULT_USER_ID)
            .startsAt(DEFAULT_STARTS_AT)
            .endsAt(DEFAULT_ENDS_AT)
            .scheduled(DEFAULT_SCHEDULED);
        return doNotDisturbTimings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoNotDisturbTimings createUpdatedEntity(EntityManager em) {
        DoNotDisturbTimings doNotDisturbTimings = new DoNotDisturbTimings()
            .userId(UPDATED_USER_ID)
            .startsAt(UPDATED_STARTS_AT)
            .endsAt(UPDATED_ENDS_AT)
            .scheduled(UPDATED_SCHEDULED);
        return doNotDisturbTimings;
    }

    @BeforeEach
    public void initTest() {
        doNotDisturbTimings = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoNotDisturbTimings() throws Exception {
        int databaseSizeBeforeCreate = doNotDisturbTimingsRepository.findAll().size();
        // Create the DoNotDisturbTimings
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);
        restDoNotDisturbTimingsMockMvc.perform(post("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isCreated());

        // Validate the DoNotDisturbTimings in the database
        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeCreate + 1);
        DoNotDisturbTimings testDoNotDisturbTimings = doNotDisturbTimingsList.get(doNotDisturbTimingsList.size() - 1);
        assertThat(testDoNotDisturbTimings.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDoNotDisturbTimings.getStartsAt()).isEqualTo(DEFAULT_STARTS_AT);
        assertThat(testDoNotDisturbTimings.getEndsAt()).isEqualTo(DEFAULT_ENDS_AT);
        assertThat(testDoNotDisturbTimings.isScheduled()).isEqualTo(DEFAULT_SCHEDULED);
    }

    @Test
    @Transactional
    public void createDoNotDisturbTimingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doNotDisturbTimingsRepository.findAll().size();

        // Create the DoNotDisturbTimings with an existing ID
        doNotDisturbTimings.setId(1L);
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoNotDisturbTimingsMockMvc.perform(post("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DoNotDisturbTimings in the database
        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = doNotDisturbTimingsRepository.findAll().size();
        // set the field null
        doNotDisturbTimings.setUserId(null);

        // Create the DoNotDisturbTimings, which fails.
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);


        restDoNotDisturbTimingsMockMvc.perform(post("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartsAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = doNotDisturbTimingsRepository.findAll().size();
        // set the field null
        doNotDisturbTimings.setStartsAt(null);

        // Create the DoNotDisturbTimings, which fails.
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);


        restDoNotDisturbTimingsMockMvc.perform(post("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndsAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = doNotDisturbTimingsRepository.findAll().size();
        // set the field null
        doNotDisturbTimings.setEndsAt(null);

        // Create the DoNotDisturbTimings, which fails.
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);


        restDoNotDisturbTimingsMockMvc.perform(post("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isBadRequest());

        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDoNotDisturbTimings() throws Exception {
        // Initialize the database
        doNotDisturbTimingsRepository.saveAndFlush(doNotDisturbTimings);

        // Get all the doNotDisturbTimingsList
        restDoNotDisturbTimingsMockMvc.perform(get("/api/do-not-disturb-timings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doNotDisturbTimings.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].startsAt").value(hasItem(DEFAULT_STARTS_AT.toString())))
            .andExpect(jsonPath("$.[*].endsAt").value(hasItem(DEFAULT_ENDS_AT.toString())))
            .andExpect(jsonPath("$.[*].scheduled").value(hasItem(DEFAULT_SCHEDULED.booleanValue())));
    }

    @Test
    @Transactional
    public void getDoNotDisturbTimings() throws Exception {
        // Initialize the database
        doNotDisturbTimingsRepository.saveAndFlush(doNotDisturbTimings);

        // Get the doNotDisturbTimings
        restDoNotDisturbTimingsMockMvc.perform(get("/api/do-not-disturb-timings/{id}", doNotDisturbTimings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doNotDisturbTimings.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.startsAt").value(DEFAULT_STARTS_AT.toString()))
            .andExpect(jsonPath("$.endsAt").value(DEFAULT_ENDS_AT.toString()))
            .andExpect(jsonPath("$.scheduled").value(DEFAULT_SCHEDULED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDoNotDisturbTimings() throws Exception {
        // Get the doNotDisturbTimings
        restDoNotDisturbTimingsMockMvc.perform(get("/api/do-not-disturb-timings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoNotDisturbTimings() throws Exception {
        // Initialize the database
        doNotDisturbTimingsRepository.saveAndFlush(doNotDisturbTimings);

        int databaseSizeBeforeUpdate = doNotDisturbTimingsRepository.findAll().size();

        // Update the doNotDisturbTimings
        DoNotDisturbTimings updatedDoNotDisturbTimings = doNotDisturbTimingsRepository.findById(doNotDisturbTimings.getId()).get();
        // Disconnect from session so that the updates on updatedDoNotDisturbTimings are not directly saved in db
        em.detach(updatedDoNotDisturbTimings);
        updatedDoNotDisturbTimings
            .userId(UPDATED_USER_ID)
            .startsAt(UPDATED_STARTS_AT)
            .endsAt(UPDATED_ENDS_AT)
            .scheduled(UPDATED_SCHEDULED);
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(updatedDoNotDisturbTimings);

        restDoNotDisturbTimingsMockMvc.perform(put("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isOk());

        // Validate the DoNotDisturbTimings in the database
        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeUpdate);
        DoNotDisturbTimings testDoNotDisturbTimings = doNotDisturbTimingsList.get(doNotDisturbTimingsList.size() - 1);
        assertThat(testDoNotDisturbTimings.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDoNotDisturbTimings.getStartsAt()).isEqualTo(UPDATED_STARTS_AT);
        assertThat(testDoNotDisturbTimings.getEndsAt()).isEqualTo(UPDATED_ENDS_AT);
        assertThat(testDoNotDisturbTimings.isScheduled()).isEqualTo(UPDATED_SCHEDULED);
    }

    @Test
    @Transactional
    public void updateNonExistingDoNotDisturbTimings() throws Exception {
        int databaseSizeBeforeUpdate = doNotDisturbTimingsRepository.findAll().size();

        // Create the DoNotDisturbTimings
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO = doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoNotDisturbTimingsMockMvc.perform(put("/api/do-not-disturb-timings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doNotDisturbTimingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DoNotDisturbTimings in the database
        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoNotDisturbTimings() throws Exception {
        // Initialize the database
        doNotDisturbTimingsRepository.saveAndFlush(doNotDisturbTimings);

        int databaseSizeBeforeDelete = doNotDisturbTimingsRepository.findAll().size();

        // Delete the doNotDisturbTimings
        restDoNotDisturbTimingsMockMvc.perform(delete("/api/do-not-disturb-timings/{id}", doNotDisturbTimings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoNotDisturbTimings> doNotDisturbTimingsList = doNotDisturbTimingsRepository.findAll();
        assertThat(doNotDisturbTimingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
