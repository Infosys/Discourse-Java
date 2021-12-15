/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.SchedulerStats;
import com.infy.repository.SchedulerStatsRepository;
import com.infy.service.SchedulerStatsService;
import com.infy.service.dto.SchedulerStatsDTO;
import com.infy.service.mapper.SchedulerStatsMapper;

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
 * Integration tests for the {@link SchedulerStatsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SchedulerStatsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSTNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PID = 1L;
    private static final Long UPDATED_PID = 2L;

    private static final Integer DEFAULT_DURATION_MS = 1;
    private static final Integer UPDATED_DURATION_MS = 2;

    private static final Integer DEFAULT_LIVE_SLOTS_START = 1;
    private static final Integer UPDATED_LIVE_SLOTS_START = 2;

    private static final Integer DEFAULT_LIVE_SLOTS_FINISH = 1;
    private static final Integer UPDATED_LIVE_SLOTS_FINISH = 2;

    private static final Instant DEFAULT_STARTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final Boolean UPDATED_SUCCESS = true;

    private static final String DEFAULT_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_ERROR = "BBBBBBBBBB";

    @Autowired
    private SchedulerStatsRepository schedulerStatsRepository;

    @Autowired
    private SchedulerStatsMapper schedulerStatsMapper;

    @Autowired
    private SchedulerStatsService schedulerStatsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchedulerStatsMockMvc;

    private SchedulerStats schedulerStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerStats createEntity(EntityManager em) {
        SchedulerStats schedulerStats = new SchedulerStats()
            .name(DEFAULT_NAME)
            .hostname(DEFAULT_HOSTNAME)
            .pid(DEFAULT_PID)
            .durationMs(DEFAULT_DURATION_MS)
            .liveSlotsStart(DEFAULT_LIVE_SLOTS_START)
            .liveSlotsFinish(DEFAULT_LIVE_SLOTS_FINISH)
            .startedAt(DEFAULT_STARTED_AT)
            .success(DEFAULT_SUCCESS)
            .error(DEFAULT_ERROR);
        return schedulerStats;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchedulerStats createUpdatedEntity(EntityManager em) {
        SchedulerStats schedulerStats = new SchedulerStats()
            .name(UPDATED_NAME)
            .hostname(UPDATED_HOSTNAME)
            .pid(UPDATED_PID)
            .durationMs(UPDATED_DURATION_MS)
            .liveSlotsStart(UPDATED_LIVE_SLOTS_START)
            .liveSlotsFinish(UPDATED_LIVE_SLOTS_FINISH)
            .startedAt(UPDATED_STARTED_AT)
            .success(UPDATED_SUCCESS)
            .error(UPDATED_ERROR);
        return schedulerStats;
    }

    @BeforeEach
    public void initTest() {
        schedulerStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedulerStats() throws Exception {
        int databaseSizeBeforeCreate = schedulerStatsRepository.findAll().size();
        // Create the SchedulerStats
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);
        restSchedulerStatsMockMvc.perform(post("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the SchedulerStats in the database
        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeCreate + 1);
        SchedulerStats testSchedulerStats = schedulerStatsList.get(schedulerStatsList.size() - 1);
        assertThat(testSchedulerStats.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchedulerStats.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testSchedulerStats.getPid()).isEqualTo(DEFAULT_PID);
        assertThat(testSchedulerStats.getDurationMs()).isEqualTo(DEFAULT_DURATION_MS);
        assertThat(testSchedulerStats.getLiveSlotsStart()).isEqualTo(DEFAULT_LIVE_SLOTS_START);
        assertThat(testSchedulerStats.getLiveSlotsFinish()).isEqualTo(DEFAULT_LIVE_SLOTS_FINISH);
        assertThat(testSchedulerStats.getStartedAt()).isEqualTo(DEFAULT_STARTED_AT);
        assertThat(testSchedulerStats.isSuccess()).isEqualTo(DEFAULT_SUCCESS);
        assertThat(testSchedulerStats.getError()).isEqualTo(DEFAULT_ERROR);
    }

    @Test
    @Transactional
    public void createSchedulerStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schedulerStatsRepository.findAll().size();

        // Create the SchedulerStats with an existing ID
        schedulerStats.setId(1L);
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedulerStatsMockMvc.perform(post("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchedulerStats in the database
        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerStatsRepository.findAll().size();
        // set the field null
        schedulerStats.setName(null);

        // Create the SchedulerStats, which fails.
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);


        restSchedulerStatsMockMvc.perform(post("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isBadRequest());

        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHostnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerStatsRepository.findAll().size();
        // set the field null
        schedulerStats.setHostname(null);

        // Create the SchedulerStats, which fails.
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);


        restSchedulerStatsMockMvc.perform(post("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isBadRequest());

        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPidIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerStatsRepository.findAll().size();
        // set the field null
        schedulerStats.setPid(null);

        // Create the SchedulerStats, which fails.
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);


        restSchedulerStatsMockMvc.perform(post("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isBadRequest());

        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerStatsRepository.findAll().size();
        // set the field null
        schedulerStats.setStartedAt(null);

        // Create the SchedulerStats, which fails.
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);


        restSchedulerStatsMockMvc.perform(post("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isBadRequest());

        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchedulerStats() throws Exception {
        // Initialize the database
        schedulerStatsRepository.saveAndFlush(schedulerStats);

        // Get all the schedulerStatsList
        restSchedulerStatsMockMvc.perform(get("/api/scheduler-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedulerStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID.intValue())))
            .andExpect(jsonPath("$.[*].durationMs").value(hasItem(DEFAULT_DURATION_MS)))
            .andExpect(jsonPath("$.[*].liveSlotsStart").value(hasItem(DEFAULT_LIVE_SLOTS_START)))
            .andExpect(jsonPath("$.[*].liveSlotsFinish").value(hasItem(DEFAULT_LIVE_SLOTS_FINISH)))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(DEFAULT_STARTED_AT.toString())))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR)));
    }

    @Test
    @Transactional
    public void getSchedulerStats() throws Exception {
        // Initialize the database
        schedulerStatsRepository.saveAndFlush(schedulerStats);

        // Get the schedulerStats
        restSchedulerStatsMockMvc.perform(get("/api/scheduler-stats/{id}", schedulerStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedulerStats.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID.intValue()))
            .andExpect(jsonPath("$.durationMs").value(DEFAULT_DURATION_MS))
            .andExpect(jsonPath("$.liveSlotsStart").value(DEFAULT_LIVE_SLOTS_START))
            .andExpect(jsonPath("$.liveSlotsFinish").value(DEFAULT_LIVE_SLOTS_FINISH))
            .andExpect(jsonPath("$.startedAt").value(DEFAULT_STARTED_AT.toString()))
            .andExpect(jsonPath("$.success").value(DEFAULT_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR));
    }
    @Test
    @Transactional
    public void getNonExistingSchedulerStats() throws Exception {
        // Get the schedulerStats
        restSchedulerStatsMockMvc.perform(get("/api/scheduler-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedulerStats() throws Exception {
        // Initialize the database
        schedulerStatsRepository.saveAndFlush(schedulerStats);

        int databaseSizeBeforeUpdate = schedulerStatsRepository.findAll().size();

        // Update the schedulerStats
        SchedulerStats updatedSchedulerStats = schedulerStatsRepository.findById(schedulerStats.getId()).get();
        // Disconnect from session so that the updates on updatedSchedulerStats are not directly saved in db
        em.detach(updatedSchedulerStats);
        updatedSchedulerStats
            .name(UPDATED_NAME)
            .hostname(UPDATED_HOSTNAME)
            .pid(UPDATED_PID)
            .durationMs(UPDATED_DURATION_MS)
            .liveSlotsStart(UPDATED_LIVE_SLOTS_START)
            .liveSlotsFinish(UPDATED_LIVE_SLOTS_FINISH)
            .startedAt(UPDATED_STARTED_AT)
            .success(UPDATED_SUCCESS)
            .error(UPDATED_ERROR);
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(updatedSchedulerStats);

        restSchedulerStatsMockMvc.perform(put("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isOk());

        // Validate the SchedulerStats in the database
        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeUpdate);
        SchedulerStats testSchedulerStats = schedulerStatsList.get(schedulerStatsList.size() - 1);
        assertThat(testSchedulerStats.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchedulerStats.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testSchedulerStats.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testSchedulerStats.getDurationMs()).isEqualTo(UPDATED_DURATION_MS);
        assertThat(testSchedulerStats.getLiveSlotsStart()).isEqualTo(UPDATED_LIVE_SLOTS_START);
        assertThat(testSchedulerStats.getLiveSlotsFinish()).isEqualTo(UPDATED_LIVE_SLOTS_FINISH);
        assertThat(testSchedulerStats.getStartedAt()).isEqualTo(UPDATED_STARTED_AT);
        assertThat(testSchedulerStats.isSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testSchedulerStats.getError()).isEqualTo(UPDATED_ERROR);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedulerStats() throws Exception {
        int databaseSizeBeforeUpdate = schedulerStatsRepository.findAll().size();

        // Create the SchedulerStats
        SchedulerStatsDTO schedulerStatsDTO = schedulerStatsMapper.toDto(schedulerStats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerStatsMockMvc.perform(put("/api/scheduler-stats").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedulerStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchedulerStats in the database
        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchedulerStats() throws Exception {
        // Initialize the database
        schedulerStatsRepository.saveAndFlush(schedulerStats);

        int databaseSizeBeforeDelete = schedulerStatsRepository.findAll().size();

        // Delete the schedulerStats
        restSchedulerStatsMockMvc.perform(delete("/api/scheduler-stats/{id}", schedulerStats.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchedulerStats> schedulerStatsList = schedulerStatsRepository.findAll();
        assertThat(schedulerStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
