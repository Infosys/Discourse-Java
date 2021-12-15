/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ShelvedNotifications;
import com.infy.repository.ShelvedNotificationsRepository;
import com.infy.service.ShelvedNotificationsService;
import com.infy.service.dto.ShelvedNotificationsDTO;
import com.infy.service.mapper.ShelvedNotificationsMapper;

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
 * Integration tests for the {@link ShelvedNotificationsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ShelvedNotificationsResourceIT {

    private static final Long DEFAULT_NOTIFICATION_ID = 1L;
    private static final Long UPDATED_NOTIFICATION_ID = 2L;

    @Autowired
    private ShelvedNotificationsRepository shelvedNotificationsRepository;

    @Autowired
    private ShelvedNotificationsMapper shelvedNotificationsMapper;

    @Autowired
    private ShelvedNotificationsService shelvedNotificationsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShelvedNotificationsMockMvc;

    private ShelvedNotifications shelvedNotifications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShelvedNotifications createEntity(EntityManager em) {
        ShelvedNotifications shelvedNotifications = new ShelvedNotifications()
            .notificationId(DEFAULT_NOTIFICATION_ID);
        return shelvedNotifications;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShelvedNotifications createUpdatedEntity(EntityManager em) {
        ShelvedNotifications shelvedNotifications = new ShelvedNotifications()
            .notificationId(UPDATED_NOTIFICATION_ID);
        return shelvedNotifications;
    }

    @BeforeEach
    public void initTest() {
        shelvedNotifications = createEntity(em);
    }

    @Test
    @Transactional
    public void createShelvedNotifications() throws Exception {
        int databaseSizeBeforeCreate = shelvedNotificationsRepository.findAll().size();
        // Create the ShelvedNotifications
        ShelvedNotificationsDTO shelvedNotificationsDTO = shelvedNotificationsMapper.toDto(shelvedNotifications);
        restShelvedNotificationsMockMvc.perform(post("/api/shelved-notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelvedNotificationsDTO)))
            .andExpect(status().isCreated());

        // Validate the ShelvedNotifications in the database
        List<ShelvedNotifications> shelvedNotificationsList = shelvedNotificationsRepository.findAll();
        assertThat(shelvedNotificationsList).hasSize(databaseSizeBeforeCreate + 1);
        ShelvedNotifications testShelvedNotifications = shelvedNotificationsList.get(shelvedNotificationsList.size() - 1);
        assertThat(testShelvedNotifications.getNotificationId()).isEqualTo(DEFAULT_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    public void createShelvedNotificationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shelvedNotificationsRepository.findAll().size();

        // Create the ShelvedNotifications with an existing ID
        shelvedNotifications.setId(1L);
        ShelvedNotificationsDTO shelvedNotificationsDTO = shelvedNotificationsMapper.toDto(shelvedNotifications);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelvedNotificationsMockMvc.perform(post("/api/shelved-notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelvedNotificationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShelvedNotifications in the database
        List<ShelvedNotifications> shelvedNotificationsList = shelvedNotificationsRepository.findAll();
        assertThat(shelvedNotificationsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNotificationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shelvedNotificationsRepository.findAll().size();
        // set the field null
        shelvedNotifications.setNotificationId(null);

        // Create the ShelvedNotifications, which fails.
        ShelvedNotificationsDTO shelvedNotificationsDTO = shelvedNotificationsMapper.toDto(shelvedNotifications);


        restShelvedNotificationsMockMvc.perform(post("/api/shelved-notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelvedNotificationsDTO)))
            .andExpect(status().isBadRequest());

        List<ShelvedNotifications> shelvedNotificationsList = shelvedNotificationsRepository.findAll();
        assertThat(shelvedNotificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShelvedNotifications() throws Exception {
        // Initialize the database
        shelvedNotificationsRepository.saveAndFlush(shelvedNotifications);

        // Get all the shelvedNotificationsList
        restShelvedNotificationsMockMvc.perform(get("/api/shelved-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelvedNotifications.getId().intValue())))
            .andExpect(jsonPath("$.[*].notificationId").value(hasItem(DEFAULT_NOTIFICATION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getShelvedNotifications() throws Exception {
        // Initialize the database
        shelvedNotificationsRepository.saveAndFlush(shelvedNotifications);

        // Get the shelvedNotifications
        restShelvedNotificationsMockMvc.perform(get("/api/shelved-notifications/{id}", shelvedNotifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shelvedNotifications.getId().intValue()))
            .andExpect(jsonPath("$.notificationId").value(DEFAULT_NOTIFICATION_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingShelvedNotifications() throws Exception {
        // Get the shelvedNotifications
        restShelvedNotificationsMockMvc.perform(get("/api/shelved-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShelvedNotifications() throws Exception {
        // Initialize the database
        shelvedNotificationsRepository.saveAndFlush(shelvedNotifications);

        int databaseSizeBeforeUpdate = shelvedNotificationsRepository.findAll().size();

        // Update the shelvedNotifications
        ShelvedNotifications updatedShelvedNotifications = shelvedNotificationsRepository.findById(shelvedNotifications.getId()).get();
        // Disconnect from session so that the updates on updatedShelvedNotifications are not directly saved in db
        em.detach(updatedShelvedNotifications);
        updatedShelvedNotifications
            .notificationId(UPDATED_NOTIFICATION_ID);
        ShelvedNotificationsDTO shelvedNotificationsDTO = shelvedNotificationsMapper.toDto(updatedShelvedNotifications);

        restShelvedNotificationsMockMvc.perform(put("/api/shelved-notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelvedNotificationsDTO)))
            .andExpect(status().isOk());

        // Validate the ShelvedNotifications in the database
        List<ShelvedNotifications> shelvedNotificationsList = shelvedNotificationsRepository.findAll();
        assertThat(shelvedNotificationsList).hasSize(databaseSizeBeforeUpdate);
        ShelvedNotifications testShelvedNotifications = shelvedNotificationsList.get(shelvedNotificationsList.size() - 1);
        assertThat(testShelvedNotifications.getNotificationId()).isEqualTo(UPDATED_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingShelvedNotifications() throws Exception {
        int databaseSizeBeforeUpdate = shelvedNotificationsRepository.findAll().size();

        // Create the ShelvedNotifications
        ShelvedNotificationsDTO shelvedNotificationsDTO = shelvedNotificationsMapper.toDto(shelvedNotifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelvedNotificationsMockMvc.perform(put("/api/shelved-notifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelvedNotificationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShelvedNotifications in the database
        List<ShelvedNotifications> shelvedNotificationsList = shelvedNotificationsRepository.findAll();
        assertThat(shelvedNotificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShelvedNotifications() throws Exception {
        // Initialize the database
        shelvedNotificationsRepository.saveAndFlush(shelvedNotifications);

        int databaseSizeBeforeDelete = shelvedNotificationsRepository.findAll().size();

        // Delete the shelvedNotifications
        restShelvedNotificationsMockMvc.perform(delete("/api/shelved-notifications/{id}", shelvedNotifications.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShelvedNotifications> shelvedNotificationsList = shelvedNotificationsRepository.findAll();
        assertThat(shelvedNotificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
