/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Notifications;
import com.infy.repository.NotificationsRepository;
import com.infy.service.NotificationsService;
import com.infy.service.dto.NotificationsDTO;
import com.infy.service.mapper.NotificationsMapper;

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
 * Integration tests for the {@link NotificationsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class NotificationsResourceIT {

    private static final Integer DEFAULT_NOTIFICATION_TYPE = 1;
    private static final Integer UPDATED_NOTIFICATION_TYPE = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ = false;
    private static final Boolean UPDATED_READ = true;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Integer DEFAULT_POST_NUMBER = 1;
    private static final Integer UPDATED_POST_NUMBER = 2;

    private static final Long DEFAULT_POST_ACTION_ID = 1L;
    private static final Long UPDATED_POST_ACTION_ID = 2L;

    private static final Boolean DEFAULT_HIGH_PRIORITY = false;
    private static final Boolean UPDATED_HIGH_PRIORITY = true;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationsMockMvc;

    private Notifications notifications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notifications createEntity(EntityManager em) {
        Notifications notifications = new Notifications()
            .notificationType(DEFAULT_NOTIFICATION_TYPE)
            .userId(DEFAULT_USER_ID)
            .data(DEFAULT_DATA)
            .read(DEFAULT_READ)
            .topicId(DEFAULT_TOPIC_ID)
            .postNumber(DEFAULT_POST_NUMBER)
            .postActionId(DEFAULT_POST_ACTION_ID)
            .highPriority(DEFAULT_HIGH_PRIORITY);
        return notifications;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notifications createUpdatedEntity(EntityManager em) {
        Notifications notifications = new Notifications()
            .notificationType(UPDATED_NOTIFICATION_TYPE)
            .userId(UPDATED_USER_ID)
            .data(UPDATED_DATA)
            .read(UPDATED_READ)
            .topicId(UPDATED_TOPIC_ID)
            .postNumber(UPDATED_POST_NUMBER)
            .postActionId(UPDATED_POST_ACTION_ID)
            .highPriority(UPDATED_HIGH_PRIORITY);
        return notifications;
    }

    @BeforeEach
    public void initTest() {
        notifications = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotifications() throws Exception {
        int databaseSizeBeforeCreate = notificationsRepository.findAll().size();
        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);
        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeCreate + 1);
        Notifications testNotifications = notificationsList.get(notificationsList.size() - 1);
        assertThat(testNotifications.getNotificationType()).isEqualTo(DEFAULT_NOTIFICATION_TYPE);
        assertThat(testNotifications.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testNotifications.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testNotifications.isRead()).isEqualTo(DEFAULT_READ);
        assertThat(testNotifications.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testNotifications.getPostNumber()).isEqualTo(DEFAULT_POST_NUMBER);
        assertThat(testNotifications.getPostActionId()).isEqualTo(DEFAULT_POST_ACTION_ID);
        assertThat(testNotifications.isHighPriority()).isEqualTo(DEFAULT_HIGH_PRIORITY);
    }

    @Test
    @Transactional
    public void createNotificationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationsRepository.findAll().size();

        // Create the Notifications with an existing ID
        notifications.setId(1L);
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNotificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationsRepository.findAll().size();
        // set the field null
        notifications.setNotificationType(null);

        // Create the Notifications, which fails.
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);


        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationsRepository.findAll().size();
        // set the field null
        notifications.setUserId(null);

        // Create the Notifications, which fails.
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);


        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationsRepository.findAll().size();
        // set the field null
        notifications.setData(null);

        // Create the Notifications, which fails.
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);


        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationsRepository.findAll().size();
        // set the field null
        notifications.setRead(null);

        // Create the Notifications, which fails.
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);


        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHighPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationsRepository.findAll().size();
        // set the field null
        notifications.setHighPriority(null);

        // Create the Notifications, which fails.
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);


        restNotificationsMockMvc.perform(post("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        // Get all the notificationsList
        restNotificationsMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notifications.getId().intValue())))
            .andExpect(jsonPath("$.[*].notificationType").value(hasItem(DEFAULT_NOTIFICATION_TYPE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postNumber").value(hasItem(DEFAULT_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].postActionId").value(hasItem(DEFAULT_POST_ACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].highPriority").value(hasItem(DEFAULT_HIGH_PRIORITY.booleanValue())));
    }

    @Test
    @Transactional
    public void getNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        // Get the notifications
        restNotificationsMockMvc.perform(get("/api/notifications/{id}", notifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notifications.getId().intValue()))
            .andExpect(jsonPath("$.notificationType").value(DEFAULT_NOTIFICATION_TYPE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.read").value(DEFAULT_READ.booleanValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postNumber").value(DEFAULT_POST_NUMBER))
            .andExpect(jsonPath("$.postActionId").value(DEFAULT_POST_ACTION_ID.intValue()))
            .andExpect(jsonPath("$.highPriority").value(DEFAULT_HIGH_PRIORITY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNotifications() throws Exception {
        // Get the notifications
        restNotificationsMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();

        // Update the notifications
        Notifications updatedNotifications = notificationsRepository.findById(notifications.getId()).get();
        // Disconnect from session so that the updates on updatedNotifications are not directly saved in db
        em.detach(updatedNotifications);
        updatedNotifications
            .notificationType(UPDATED_NOTIFICATION_TYPE)
            .userId(UPDATED_USER_ID)
            .data(UPDATED_DATA)
            .read(UPDATED_READ)
            .topicId(UPDATED_TOPIC_ID)
            .postNumber(UPDATED_POST_NUMBER)
            .postActionId(UPDATED_POST_ACTION_ID)
            .highPriority(UPDATED_HIGH_PRIORITY);
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(updatedNotifications);

        restNotificationsMockMvc.perform(put("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isOk());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
        Notifications testNotifications = notificationsList.get(notificationsList.size() - 1);
        assertThat(testNotifications.getNotificationType()).isEqualTo(UPDATED_NOTIFICATION_TYPE);
        assertThat(testNotifications.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testNotifications.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testNotifications.isRead()).isEqualTo(UPDATED_READ);
        assertThat(testNotifications.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testNotifications.getPostNumber()).isEqualTo(UPDATED_POST_NUMBER);
        assertThat(testNotifications.getPostActionId()).isEqualTo(UPDATED_POST_ACTION_ID);
        assertThat(testNotifications.isHighPriority()).isEqualTo(UPDATED_HIGH_PRIORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationsMockMvc.perform(put("/api/notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        int databaseSizeBeforeDelete = notificationsRepository.findAll().size();

        // Delete the notifications
        restNotificationsMockMvc.perform(delete("/api/notifications/{id}", notifications.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
