/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UserNotificationSchedules;
import com.infy.repository.UserNotificationSchedulesRepository;
import com.infy.service.UserNotificationSchedulesService;
import com.infy.service.dto.UserNotificationSchedulesDTO;
import com.infy.service.mapper.UserNotificationSchedulesMapper;

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
 * Integration tests for the {@link UserNotificationSchedulesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserNotificationSchedulesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Integer DEFAULT_DAY_0_START_TIME = 1;
    private static final Integer UPDATED_DAY_0_START_TIME = 2;

    private static final Integer DEFAULT_DAY_0_END_TIME = 1;
    private static final Integer UPDATED_DAY_0_END_TIME = 2;

    private static final Integer DEFAULT_DAY_1_START_TIME = 1;
    private static final Integer UPDATED_DAY_1_START_TIME = 2;

    private static final Integer DEFAULT_DAY_1_END_TIME = 1;
    private static final Integer UPDATED_DAY_1_END_TIME = 2;

    private static final Integer DEFAULT_DAY_2_START_TIME = 1;
    private static final Integer UPDATED_DAY_2_START_TIME = 2;

    private static final Integer DEFAULT_DAY_2_END_TIME = 1;
    private static final Integer UPDATED_DAY_2_END_TIME = 2;

    private static final Integer DEFAULT_DAY_3_START_TIME = 1;
    private static final Integer UPDATED_DAY_3_START_TIME = 2;

    private static final Integer DEFAULT_DAY_3_END_TIME = 1;
    private static final Integer UPDATED_DAY_3_END_TIME = 2;

    private static final Integer DEFAULT_DAY_4_START_TIME = 1;
    private static final Integer UPDATED_DAY_4_START_TIME = 2;

    private static final Integer DEFAULT_DAY_4_END_TIME = 1;
    private static final Integer UPDATED_DAY_4_END_TIME = 2;

    private static final Integer DEFAULT_DAY_5_START_TIME = 1;
    private static final Integer UPDATED_DAY_5_START_TIME = 2;

    private static final Integer DEFAULT_DAY_5_END_TIME = 1;
    private static final Integer UPDATED_DAY_5_END_TIME = 2;

    private static final Integer DEFAULT_DAY_6_START_TIME = 1;
    private static final Integer UPDATED_DAY_6_START_TIME = 2;

    private static final Integer DEFAULT_DAY_6_END_TIME = 1;
    private static final Integer UPDATED_DAY_6_END_TIME = 2;

    @Autowired
    private UserNotificationSchedulesRepository userNotificationSchedulesRepository;

    @Autowired
    private UserNotificationSchedulesMapper userNotificationSchedulesMapper;

    @Autowired
    private UserNotificationSchedulesService userNotificationSchedulesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserNotificationSchedulesMockMvc;

    private UserNotificationSchedules userNotificationSchedules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserNotificationSchedules createEntity(EntityManager em) {
        UserNotificationSchedules userNotificationSchedules = new UserNotificationSchedules()
            .userId(DEFAULT_USER_ID)
            .enabled(DEFAULT_ENABLED)
            .day0StartTime(DEFAULT_DAY_0_START_TIME)
            .day0EndTime(DEFAULT_DAY_0_END_TIME)
            .day1StartTime(DEFAULT_DAY_1_START_TIME)
            .day1EndTime(DEFAULT_DAY_1_END_TIME)
            .day2StartTime(DEFAULT_DAY_2_START_TIME)
            .day2EndTime(DEFAULT_DAY_2_END_TIME)
            .day3StartTime(DEFAULT_DAY_3_START_TIME)
            .day3EndTime(DEFAULT_DAY_3_END_TIME)
            .day4StartTime(DEFAULT_DAY_4_START_TIME)
            .day4EndTime(DEFAULT_DAY_4_END_TIME)
            .day5StartTime(DEFAULT_DAY_5_START_TIME)
            .day5EndTime(DEFAULT_DAY_5_END_TIME)
            .day6StartTime(DEFAULT_DAY_6_START_TIME)
            .day6EndTime(DEFAULT_DAY_6_END_TIME);
        return userNotificationSchedules;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserNotificationSchedules createUpdatedEntity(EntityManager em) {
        UserNotificationSchedules userNotificationSchedules = new UserNotificationSchedules()
            .userId(UPDATED_USER_ID)
            .enabled(UPDATED_ENABLED)
            .day0StartTime(UPDATED_DAY_0_START_TIME)
            .day0EndTime(UPDATED_DAY_0_END_TIME)
            .day1StartTime(UPDATED_DAY_1_START_TIME)
            .day1EndTime(UPDATED_DAY_1_END_TIME)
            .day2StartTime(UPDATED_DAY_2_START_TIME)
            .day2EndTime(UPDATED_DAY_2_END_TIME)
            .day3StartTime(UPDATED_DAY_3_START_TIME)
            .day3EndTime(UPDATED_DAY_3_END_TIME)
            .day4StartTime(UPDATED_DAY_4_START_TIME)
            .day4EndTime(UPDATED_DAY_4_END_TIME)
            .day5StartTime(UPDATED_DAY_5_START_TIME)
            .day5EndTime(UPDATED_DAY_5_END_TIME)
            .day6StartTime(UPDATED_DAY_6_START_TIME)
            .day6EndTime(UPDATED_DAY_6_END_TIME);
        return userNotificationSchedules;
    }

    @BeforeEach
    public void initTest() {
        userNotificationSchedules = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserNotificationSchedules() throws Exception {
        int databaseSizeBeforeCreate = userNotificationSchedulesRepository.findAll().size();
        // Create the UserNotificationSchedules
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);
        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserNotificationSchedules in the database
        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeCreate + 1);
        UserNotificationSchedules testUserNotificationSchedules = userNotificationSchedulesList.get(userNotificationSchedulesList.size() - 1);
        assertThat(testUserNotificationSchedules.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserNotificationSchedules.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testUserNotificationSchedules.getDay0StartTime()).isEqualTo(DEFAULT_DAY_0_START_TIME);
        assertThat(testUserNotificationSchedules.getDay0EndTime()).isEqualTo(DEFAULT_DAY_0_END_TIME);
        assertThat(testUserNotificationSchedules.getDay1StartTime()).isEqualTo(DEFAULT_DAY_1_START_TIME);
        assertThat(testUserNotificationSchedules.getDay1EndTime()).isEqualTo(DEFAULT_DAY_1_END_TIME);
        assertThat(testUserNotificationSchedules.getDay2StartTime()).isEqualTo(DEFAULT_DAY_2_START_TIME);
        assertThat(testUserNotificationSchedules.getDay2EndTime()).isEqualTo(DEFAULT_DAY_2_END_TIME);
        assertThat(testUserNotificationSchedules.getDay3StartTime()).isEqualTo(DEFAULT_DAY_3_START_TIME);
        assertThat(testUserNotificationSchedules.getDay3EndTime()).isEqualTo(DEFAULT_DAY_3_END_TIME);
        assertThat(testUserNotificationSchedules.getDay4StartTime()).isEqualTo(DEFAULT_DAY_4_START_TIME);
        assertThat(testUserNotificationSchedules.getDay4EndTime()).isEqualTo(DEFAULT_DAY_4_END_TIME);
        assertThat(testUserNotificationSchedules.getDay5StartTime()).isEqualTo(DEFAULT_DAY_5_START_TIME);
        assertThat(testUserNotificationSchedules.getDay5EndTime()).isEqualTo(DEFAULT_DAY_5_END_TIME);
        assertThat(testUserNotificationSchedules.getDay6StartTime()).isEqualTo(DEFAULT_DAY_6_START_TIME);
        assertThat(testUserNotificationSchedules.getDay6EndTime()).isEqualTo(DEFAULT_DAY_6_END_TIME);
    }

    @Test
    @Transactional
    public void createUserNotificationSchedulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userNotificationSchedulesRepository.findAll().size();

        // Create the UserNotificationSchedules with an existing ID
        userNotificationSchedules.setId(1L);
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserNotificationSchedules in the database
        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setUserId(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setEnabled(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay0StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay0StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay0EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay0EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay1StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay1StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay1EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay1EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay2StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay2StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay2EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay2EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay3StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay3StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay3EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay3EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay4StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay4StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay4EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay4EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay5StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay5StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay5EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay5EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay6StartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay6StartTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay6EndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationSchedulesRepository.findAll().size();
        // set the field null
        userNotificationSchedules.setDay6EndTime(null);

        // Create the UserNotificationSchedules, which fails.
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);


        restUserNotificationSchedulesMockMvc.perform(post("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserNotificationSchedules() throws Exception {
        // Initialize the database
        userNotificationSchedulesRepository.saveAndFlush(userNotificationSchedules);

        // Get all the userNotificationSchedulesList
        restUserNotificationSchedulesMockMvc.perform(get("/api/user-notification-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userNotificationSchedules.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].day0StartTime").value(hasItem(DEFAULT_DAY_0_START_TIME)))
            .andExpect(jsonPath("$.[*].day0EndTime").value(hasItem(DEFAULT_DAY_0_END_TIME)))
            .andExpect(jsonPath("$.[*].day1StartTime").value(hasItem(DEFAULT_DAY_1_START_TIME)))
            .andExpect(jsonPath("$.[*].day1EndTime").value(hasItem(DEFAULT_DAY_1_END_TIME)))
            .andExpect(jsonPath("$.[*].day2StartTime").value(hasItem(DEFAULT_DAY_2_START_TIME)))
            .andExpect(jsonPath("$.[*].day2EndTime").value(hasItem(DEFAULT_DAY_2_END_TIME)))
            .andExpect(jsonPath("$.[*].day3StartTime").value(hasItem(DEFAULT_DAY_3_START_TIME)))
            .andExpect(jsonPath("$.[*].day3EndTime").value(hasItem(DEFAULT_DAY_3_END_TIME)))
            .andExpect(jsonPath("$.[*].day4StartTime").value(hasItem(DEFAULT_DAY_4_START_TIME)))
            .andExpect(jsonPath("$.[*].day4EndTime").value(hasItem(DEFAULT_DAY_4_END_TIME)))
            .andExpect(jsonPath("$.[*].day5StartTime").value(hasItem(DEFAULT_DAY_5_START_TIME)))
            .andExpect(jsonPath("$.[*].day5EndTime").value(hasItem(DEFAULT_DAY_5_END_TIME)))
            .andExpect(jsonPath("$.[*].day6StartTime").value(hasItem(DEFAULT_DAY_6_START_TIME)))
            .andExpect(jsonPath("$.[*].day6EndTime").value(hasItem(DEFAULT_DAY_6_END_TIME)));
    }

    @Test
    @Transactional
    public void getUserNotificationSchedules() throws Exception {
        // Initialize the database
        userNotificationSchedulesRepository.saveAndFlush(userNotificationSchedules);

        // Get the userNotificationSchedules
        restUserNotificationSchedulesMockMvc.perform(get("/api/user-notification-schedules/{id}", userNotificationSchedules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userNotificationSchedules.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.day0StartTime").value(DEFAULT_DAY_0_START_TIME))
            .andExpect(jsonPath("$.day0EndTime").value(DEFAULT_DAY_0_END_TIME))
            .andExpect(jsonPath("$.day1StartTime").value(DEFAULT_DAY_1_START_TIME))
            .andExpect(jsonPath("$.day1EndTime").value(DEFAULT_DAY_1_END_TIME))
            .andExpect(jsonPath("$.day2StartTime").value(DEFAULT_DAY_2_START_TIME))
            .andExpect(jsonPath("$.day2EndTime").value(DEFAULT_DAY_2_END_TIME))
            .andExpect(jsonPath("$.day3StartTime").value(DEFAULT_DAY_3_START_TIME))
            .andExpect(jsonPath("$.day3EndTime").value(DEFAULT_DAY_3_END_TIME))
            .andExpect(jsonPath("$.day4StartTime").value(DEFAULT_DAY_4_START_TIME))
            .andExpect(jsonPath("$.day4EndTime").value(DEFAULT_DAY_4_END_TIME))
            .andExpect(jsonPath("$.day5StartTime").value(DEFAULT_DAY_5_START_TIME))
            .andExpect(jsonPath("$.day5EndTime").value(DEFAULT_DAY_5_END_TIME))
            .andExpect(jsonPath("$.day6StartTime").value(DEFAULT_DAY_6_START_TIME))
            .andExpect(jsonPath("$.day6EndTime").value(DEFAULT_DAY_6_END_TIME));
    }
    @Test
    @Transactional
    public void getNonExistingUserNotificationSchedules() throws Exception {
        // Get the userNotificationSchedules
        restUserNotificationSchedulesMockMvc.perform(get("/api/user-notification-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserNotificationSchedules() throws Exception {
        // Initialize the database
        userNotificationSchedulesRepository.saveAndFlush(userNotificationSchedules);

        int databaseSizeBeforeUpdate = userNotificationSchedulesRepository.findAll().size();

        // Update the userNotificationSchedules
        UserNotificationSchedules updatedUserNotificationSchedules = userNotificationSchedulesRepository.findById(userNotificationSchedules.getId()).get();
        // Disconnect from session so that the updates on updatedUserNotificationSchedules are not directly saved in db
        em.detach(updatedUserNotificationSchedules);
        updatedUserNotificationSchedules
            .userId(UPDATED_USER_ID)
            .enabled(UPDATED_ENABLED)
            .day0StartTime(UPDATED_DAY_0_START_TIME)
            .day0EndTime(UPDATED_DAY_0_END_TIME)
            .day1StartTime(UPDATED_DAY_1_START_TIME)
            .day1EndTime(UPDATED_DAY_1_END_TIME)
            .day2StartTime(UPDATED_DAY_2_START_TIME)
            .day2EndTime(UPDATED_DAY_2_END_TIME)
            .day3StartTime(UPDATED_DAY_3_START_TIME)
            .day3EndTime(UPDATED_DAY_3_END_TIME)
            .day4StartTime(UPDATED_DAY_4_START_TIME)
            .day4EndTime(UPDATED_DAY_4_END_TIME)
            .day5StartTime(UPDATED_DAY_5_START_TIME)
            .day5EndTime(UPDATED_DAY_5_END_TIME)
            .day6StartTime(UPDATED_DAY_6_START_TIME)
            .day6EndTime(UPDATED_DAY_6_END_TIME);
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(updatedUserNotificationSchedules);

        restUserNotificationSchedulesMockMvc.perform(put("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isOk());

        // Validate the UserNotificationSchedules in the database
        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeUpdate);
        UserNotificationSchedules testUserNotificationSchedules = userNotificationSchedulesList.get(userNotificationSchedulesList.size() - 1);
        assertThat(testUserNotificationSchedules.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserNotificationSchedules.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testUserNotificationSchedules.getDay0StartTime()).isEqualTo(UPDATED_DAY_0_START_TIME);
        assertThat(testUserNotificationSchedules.getDay0EndTime()).isEqualTo(UPDATED_DAY_0_END_TIME);
        assertThat(testUserNotificationSchedules.getDay1StartTime()).isEqualTo(UPDATED_DAY_1_START_TIME);
        assertThat(testUserNotificationSchedules.getDay1EndTime()).isEqualTo(UPDATED_DAY_1_END_TIME);
        assertThat(testUserNotificationSchedules.getDay2StartTime()).isEqualTo(UPDATED_DAY_2_START_TIME);
        assertThat(testUserNotificationSchedules.getDay2EndTime()).isEqualTo(UPDATED_DAY_2_END_TIME);
        assertThat(testUserNotificationSchedules.getDay3StartTime()).isEqualTo(UPDATED_DAY_3_START_TIME);
        assertThat(testUserNotificationSchedules.getDay3EndTime()).isEqualTo(UPDATED_DAY_3_END_TIME);
        assertThat(testUserNotificationSchedules.getDay4StartTime()).isEqualTo(UPDATED_DAY_4_START_TIME);
        assertThat(testUserNotificationSchedules.getDay4EndTime()).isEqualTo(UPDATED_DAY_4_END_TIME);
        assertThat(testUserNotificationSchedules.getDay5StartTime()).isEqualTo(UPDATED_DAY_5_START_TIME);
        assertThat(testUserNotificationSchedules.getDay5EndTime()).isEqualTo(UPDATED_DAY_5_END_TIME);
        assertThat(testUserNotificationSchedules.getDay6StartTime()).isEqualTo(UPDATED_DAY_6_START_TIME);
        assertThat(testUserNotificationSchedules.getDay6EndTime()).isEqualTo(UPDATED_DAY_6_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserNotificationSchedules() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationSchedulesRepository.findAll().size();

        // Create the UserNotificationSchedules
        UserNotificationSchedulesDTO userNotificationSchedulesDTO = userNotificationSchedulesMapper.toDto(userNotificationSchedules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserNotificationSchedulesMockMvc.perform(put("/api/user-notification-schedules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userNotificationSchedulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserNotificationSchedules in the database
        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserNotificationSchedules() throws Exception {
        // Initialize the database
        userNotificationSchedulesRepository.saveAndFlush(userNotificationSchedules);

        int databaseSizeBeforeDelete = userNotificationSchedulesRepository.findAll().size();

        // Delete the userNotificationSchedules
        restUserNotificationSchedulesMockMvc.perform(delete("/api/user-notification-schedules/{id}", userNotificationSchedules.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserNotificationSchedules> userNotificationSchedulesList = userNotificationSchedulesRepository.findAll();
        assertThat(userNotificationSchedulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
