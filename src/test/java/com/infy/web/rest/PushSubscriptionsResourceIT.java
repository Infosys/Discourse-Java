/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PushSubscriptions;
import com.infy.repository.PushSubscriptionsRepository;
import com.infy.service.PushSubscriptionsService;
import com.infy.service.dto.PushSubscriptionsDTO;
import com.infy.service.mapper.PushSubscriptionsMapper;

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
 * Integration tests for the {@link PushSubscriptionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PushSubscriptionsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    @Autowired
    private PushSubscriptionsRepository pushSubscriptionsRepository;

    @Autowired
    private PushSubscriptionsMapper pushSubscriptionsMapper;

    @Autowired
    private PushSubscriptionsService pushSubscriptionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPushSubscriptionsMockMvc;

    private PushSubscriptions pushSubscriptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushSubscriptions createEntity(EntityManager em) {
        PushSubscriptions pushSubscriptions = new PushSubscriptions()
            .userId(DEFAULT_USER_ID)
            .data(DEFAULT_DATA);
        return pushSubscriptions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushSubscriptions createUpdatedEntity(EntityManager em) {
        PushSubscriptions pushSubscriptions = new PushSubscriptions()
            .userId(UPDATED_USER_ID)
            .data(UPDATED_DATA);
        return pushSubscriptions;
    }

    @BeforeEach
    public void initTest() {
        pushSubscriptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPushSubscriptions() throws Exception {
        int databaseSizeBeforeCreate = pushSubscriptionsRepository.findAll().size();
        // Create the PushSubscriptions
        PushSubscriptionsDTO pushSubscriptionsDTO = pushSubscriptionsMapper.toDto(pushSubscriptions);
        restPushSubscriptionsMockMvc.perform(post("/api/push-subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the PushSubscriptions in the database
        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeCreate + 1);
        PushSubscriptions testPushSubscriptions = pushSubscriptionsList.get(pushSubscriptionsList.size() - 1);
        assertThat(testPushSubscriptions.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPushSubscriptions.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createPushSubscriptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pushSubscriptionsRepository.findAll().size();

        // Create the PushSubscriptions with an existing ID
        pushSubscriptions.setId(1L);
        PushSubscriptionsDTO pushSubscriptionsDTO = pushSubscriptionsMapper.toDto(pushSubscriptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPushSubscriptionsMockMvc.perform(post("/api/push-subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PushSubscriptions in the database
        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pushSubscriptionsRepository.findAll().size();
        // set the field null
        pushSubscriptions.setUserId(null);

        // Create the PushSubscriptions, which fails.
        PushSubscriptionsDTO pushSubscriptionsDTO = pushSubscriptionsMapper.toDto(pushSubscriptions);


        restPushSubscriptionsMockMvc.perform(post("/api/push-subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionsDTO)))
            .andExpect(status().isBadRequest());

        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = pushSubscriptionsRepository.findAll().size();
        // set the field null
        pushSubscriptions.setData(null);

        // Create the PushSubscriptions, which fails.
        PushSubscriptionsDTO pushSubscriptionsDTO = pushSubscriptionsMapper.toDto(pushSubscriptions);


        restPushSubscriptionsMockMvc.perform(post("/api/push-subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionsDTO)))
            .andExpect(status().isBadRequest());

        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPushSubscriptions() throws Exception {
        // Initialize the database
        pushSubscriptionsRepository.saveAndFlush(pushSubscriptions);

        // Get all the pushSubscriptionsList
        restPushSubscriptionsMockMvc.perform(get("/api/push-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pushSubscriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getPushSubscriptions() throws Exception {
        // Initialize the database
        pushSubscriptionsRepository.saveAndFlush(pushSubscriptions);

        // Get the pushSubscriptions
        restPushSubscriptionsMockMvc.perform(get("/api/push-subscriptions/{id}", pushSubscriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pushSubscriptions.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA));
    }
    @Test
    @Transactional
    public void getNonExistingPushSubscriptions() throws Exception {
        // Get the pushSubscriptions
        restPushSubscriptionsMockMvc.perform(get("/api/push-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePushSubscriptions() throws Exception {
        // Initialize the database
        pushSubscriptionsRepository.saveAndFlush(pushSubscriptions);

        int databaseSizeBeforeUpdate = pushSubscriptionsRepository.findAll().size();

        // Update the pushSubscriptions
        PushSubscriptions updatedPushSubscriptions = pushSubscriptionsRepository.findById(pushSubscriptions.getId()).get();
        // Disconnect from session so that the updates on updatedPushSubscriptions are not directly saved in db
        em.detach(updatedPushSubscriptions);
        updatedPushSubscriptions
            .userId(UPDATED_USER_ID)
            .data(UPDATED_DATA);
        PushSubscriptionsDTO pushSubscriptionsDTO = pushSubscriptionsMapper.toDto(updatedPushSubscriptions);

        restPushSubscriptionsMockMvc.perform(put("/api/push-subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionsDTO)))
            .andExpect(status().isOk());

        // Validate the PushSubscriptions in the database
        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeUpdate);
        PushSubscriptions testPushSubscriptions = pushSubscriptionsList.get(pushSubscriptionsList.size() - 1);
        assertThat(testPushSubscriptions.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPushSubscriptions.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingPushSubscriptions() throws Exception {
        int databaseSizeBeforeUpdate = pushSubscriptionsRepository.findAll().size();

        // Create the PushSubscriptions
        PushSubscriptionsDTO pushSubscriptionsDTO = pushSubscriptionsMapper.toDto(pushSubscriptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPushSubscriptionsMockMvc.perform(put("/api/push-subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushSubscriptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PushSubscriptions in the database
        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePushSubscriptions() throws Exception {
        // Initialize the database
        pushSubscriptionsRepository.saveAndFlush(pushSubscriptions);

        int databaseSizeBeforeDelete = pushSubscriptionsRepository.findAll().size();

        // Delete the pushSubscriptions
        restPushSubscriptionsMockMvc.perform(delete("/api/push-subscriptions/{id}", pushSubscriptions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PushSubscriptions> pushSubscriptionsList = pushSubscriptionsRepository.findAll();
        assertThat(pushSubscriptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
