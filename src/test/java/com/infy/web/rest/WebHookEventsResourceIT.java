/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.WebHookEvents;
import com.infy.repository.WebHookEventsRepository;
import com.infy.service.WebHookEventsService;
import com.infy.service.dto.WebHookEventsDTO;
import com.infy.service.mapper.WebHookEventsMapper;

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
 * Integration tests for the {@link WebHookEventsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WebHookEventsResourceIT {

    private static final Long DEFAULT_WEB_HOOK_ID = 1L;
    private static final Long UPDATED_WEB_HOOK_ID = 2L;

    private static final String DEFAULT_HEADERS = "AAAAAAAAAA";
    private static final String UPDATED_HEADERS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_RESPONSE_HEADERS = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_HEADERS = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_BODY = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    @Autowired
    private WebHookEventsRepository webHookEventsRepository;

    @Autowired
    private WebHookEventsMapper webHookEventsMapper;

    @Autowired
    private WebHookEventsService webHookEventsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWebHookEventsMockMvc;

    private WebHookEvents webHookEvents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebHookEvents createEntity(EntityManager em) {
        WebHookEvents webHookEvents = new WebHookEvents()
            .webHookId(DEFAULT_WEB_HOOK_ID)
            .headers(DEFAULT_HEADERS)
            .payload(DEFAULT_PAYLOAD)
            .status(DEFAULT_STATUS)
            .responseHeaders(DEFAULT_RESPONSE_HEADERS)
            .responseBody(DEFAULT_RESPONSE_BODY)
            .duration(DEFAULT_DURATION);
        return webHookEvents;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebHookEvents createUpdatedEntity(EntityManager em) {
        WebHookEvents webHookEvents = new WebHookEvents()
            .webHookId(UPDATED_WEB_HOOK_ID)
            .headers(UPDATED_HEADERS)
            .payload(UPDATED_PAYLOAD)
            .status(UPDATED_STATUS)
            .responseHeaders(UPDATED_RESPONSE_HEADERS)
            .responseBody(UPDATED_RESPONSE_BODY)
            .duration(UPDATED_DURATION);
        return webHookEvents;
    }

    @BeforeEach
    public void initTest() {
        webHookEvents = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebHookEvents() throws Exception {
        int databaseSizeBeforeCreate = webHookEventsRepository.findAll().size();
        // Create the WebHookEvents
        WebHookEventsDTO webHookEventsDTO = webHookEventsMapper.toDto(webHookEvents);
        restWebHookEventsMockMvc.perform(post("/api/web-hook-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventsDTO)))
            .andExpect(status().isCreated());

        // Validate the WebHookEvents in the database
        List<WebHookEvents> webHookEventsList = webHookEventsRepository.findAll();
        assertThat(webHookEventsList).hasSize(databaseSizeBeforeCreate + 1);
        WebHookEvents testWebHookEvents = webHookEventsList.get(webHookEventsList.size() - 1);
        assertThat(testWebHookEvents.getWebHookId()).isEqualTo(DEFAULT_WEB_HOOK_ID);
        assertThat(testWebHookEvents.getHeaders()).isEqualTo(DEFAULT_HEADERS);
        assertThat(testWebHookEvents.getPayload()).isEqualTo(DEFAULT_PAYLOAD);
        assertThat(testWebHookEvents.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWebHookEvents.getResponseHeaders()).isEqualTo(DEFAULT_RESPONSE_HEADERS);
        assertThat(testWebHookEvents.getResponseBody()).isEqualTo(DEFAULT_RESPONSE_BODY);
        assertThat(testWebHookEvents.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createWebHookEventsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webHookEventsRepository.findAll().size();

        // Create the WebHookEvents with an existing ID
        webHookEvents.setId(1L);
        WebHookEventsDTO webHookEventsDTO = webHookEventsMapper.toDto(webHookEvents);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebHookEventsMockMvc.perform(post("/api/web-hook-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebHookEvents in the database
        List<WebHookEvents> webHookEventsList = webHookEventsRepository.findAll();
        assertThat(webHookEventsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWebHookIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHookEventsRepository.findAll().size();
        // set the field null
        webHookEvents.setWebHookId(null);

        // Create the WebHookEvents, which fails.
        WebHookEventsDTO webHookEventsDTO = webHookEventsMapper.toDto(webHookEvents);


        restWebHookEventsMockMvc.perform(post("/api/web-hook-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventsDTO)))
            .andExpect(status().isBadRequest());

        List<WebHookEvents> webHookEventsList = webHookEventsRepository.findAll();
        assertThat(webHookEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebHookEvents() throws Exception {
        // Initialize the database
        webHookEventsRepository.saveAndFlush(webHookEvents);

        // Get all the webHookEventsList
        restWebHookEventsMockMvc.perform(get("/api/web-hook-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webHookEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].webHookId").value(hasItem(DEFAULT_WEB_HOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].headers").value(hasItem(DEFAULT_HEADERS)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].responseHeaders").value(hasItem(DEFAULT_RESPONSE_HEADERS)))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)));
    }

    @Test
    @Transactional
    public void getWebHookEvents() throws Exception {
        // Initialize the database
        webHookEventsRepository.saveAndFlush(webHookEvents);

        // Get the webHookEvents
        restWebHookEventsMockMvc.perform(get("/api/web-hook-events/{id}", webHookEvents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(webHookEvents.getId().intValue()))
            .andExpect(jsonPath("$.webHookId").value(DEFAULT_WEB_HOOK_ID.intValue()))
            .andExpect(jsonPath("$.headers").value(DEFAULT_HEADERS))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.responseHeaders").value(DEFAULT_RESPONSE_HEADERS))
            .andExpect(jsonPath("$.responseBody").value(DEFAULT_RESPONSE_BODY))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION));
    }
    @Test
    @Transactional
    public void getNonExistingWebHookEvents() throws Exception {
        // Get the webHookEvents
        restWebHookEventsMockMvc.perform(get("/api/web-hook-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebHookEvents() throws Exception {
        // Initialize the database
        webHookEventsRepository.saveAndFlush(webHookEvents);

        int databaseSizeBeforeUpdate = webHookEventsRepository.findAll().size();

        // Update the webHookEvents
        WebHookEvents updatedWebHookEvents = webHookEventsRepository.findById(webHookEvents.getId()).get();
        // Disconnect from session so that the updates on updatedWebHookEvents are not directly saved in db
        em.detach(updatedWebHookEvents);
        updatedWebHookEvents
            .webHookId(UPDATED_WEB_HOOK_ID)
            .headers(UPDATED_HEADERS)
            .payload(UPDATED_PAYLOAD)
            .status(UPDATED_STATUS)
            .responseHeaders(UPDATED_RESPONSE_HEADERS)
            .responseBody(UPDATED_RESPONSE_BODY)
            .duration(UPDATED_DURATION);
        WebHookEventsDTO webHookEventsDTO = webHookEventsMapper.toDto(updatedWebHookEvents);

        restWebHookEventsMockMvc.perform(put("/api/web-hook-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventsDTO)))
            .andExpect(status().isOk());

        // Validate the WebHookEvents in the database
        List<WebHookEvents> webHookEventsList = webHookEventsRepository.findAll();
        assertThat(webHookEventsList).hasSize(databaseSizeBeforeUpdate);
        WebHookEvents testWebHookEvents = webHookEventsList.get(webHookEventsList.size() - 1);
        assertThat(testWebHookEvents.getWebHookId()).isEqualTo(UPDATED_WEB_HOOK_ID);
        assertThat(testWebHookEvents.getHeaders()).isEqualTo(UPDATED_HEADERS);
        assertThat(testWebHookEvents.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testWebHookEvents.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWebHookEvents.getResponseHeaders()).isEqualTo(UPDATED_RESPONSE_HEADERS);
        assertThat(testWebHookEvents.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
        assertThat(testWebHookEvents.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingWebHookEvents() throws Exception {
        int databaseSizeBeforeUpdate = webHookEventsRepository.findAll().size();

        // Create the WebHookEvents
        WebHookEventsDTO webHookEventsDTO = webHookEventsMapper.toDto(webHookEvents);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebHookEventsMockMvc.perform(put("/api/web-hook-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebHookEvents in the database
        List<WebHookEvents> webHookEventsList = webHookEventsRepository.findAll();
        assertThat(webHookEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebHookEvents() throws Exception {
        // Initialize the database
        webHookEventsRepository.saveAndFlush(webHookEvents);

        int databaseSizeBeforeDelete = webHookEventsRepository.findAll().size();

        // Delete the webHookEvents
        restWebHookEventsMockMvc.perform(delete("/api/web-hook-events/{id}", webHookEvents.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebHookEvents> webHookEventsList = webHookEventsRepository.findAll();
        assertThat(webHookEventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
