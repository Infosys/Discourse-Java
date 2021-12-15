/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.WebHookEventTypesHooks;
import com.infy.repository.WebHookEventTypesHooksRepository;
import com.infy.service.WebHookEventTypesHooksService;
import com.infy.service.dto.WebHookEventTypesHooksDTO;
import com.infy.service.mapper.WebHookEventTypesHooksMapper;

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
 * Integration tests for the {@link WebHookEventTypesHooksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WebHookEventTypesHooksResourceIT {

    private static final Long DEFAULT_WEB_HOOK_ID = 1L;
    private static final Long UPDATED_WEB_HOOK_ID = 2L;

    private static final Long DEFAULT_WEB_HOOK_EVENT_TYPE_ID = 1L;
    private static final Long UPDATED_WEB_HOOK_EVENT_TYPE_ID = 2L;

    @Autowired
    private WebHookEventTypesHooksRepository webHookEventTypesHooksRepository;

    @Autowired
    private WebHookEventTypesHooksMapper webHookEventTypesHooksMapper;

    @Autowired
    private WebHookEventTypesHooksService webHookEventTypesHooksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWebHookEventTypesHooksMockMvc;

    private WebHookEventTypesHooks webHookEventTypesHooks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebHookEventTypesHooks createEntity(EntityManager em) {
        WebHookEventTypesHooks webHookEventTypesHooks = new WebHookEventTypesHooks()
            .webHookId(DEFAULT_WEB_HOOK_ID)
            .webHookEventTypeId(DEFAULT_WEB_HOOK_EVENT_TYPE_ID);
        return webHookEventTypesHooks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebHookEventTypesHooks createUpdatedEntity(EntityManager em) {
        WebHookEventTypesHooks webHookEventTypesHooks = new WebHookEventTypesHooks()
            .webHookId(UPDATED_WEB_HOOK_ID)
            .webHookEventTypeId(UPDATED_WEB_HOOK_EVENT_TYPE_ID);
        return webHookEventTypesHooks;
    }

    @BeforeEach
    public void initTest() {
        webHookEventTypesHooks = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebHookEventTypesHooks() throws Exception {
        int databaseSizeBeforeCreate = webHookEventTypesHooksRepository.findAll().size();
        // Create the WebHookEventTypesHooks
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO = webHookEventTypesHooksMapper.toDto(webHookEventTypesHooks);
        restWebHookEventTypesHooksMockMvc.perform(post("/api/web-hook-event-types-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventTypesHooksDTO)))
            .andExpect(status().isCreated());

        // Validate the WebHookEventTypesHooks in the database
        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeCreate + 1);
        WebHookEventTypesHooks testWebHookEventTypesHooks = webHookEventTypesHooksList.get(webHookEventTypesHooksList.size() - 1);
        assertThat(testWebHookEventTypesHooks.getWebHookId()).isEqualTo(DEFAULT_WEB_HOOK_ID);
        assertThat(testWebHookEventTypesHooks.getWebHookEventTypeId()).isEqualTo(DEFAULT_WEB_HOOK_EVENT_TYPE_ID);
    }

    @Test
    @Transactional
    public void createWebHookEventTypesHooksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webHookEventTypesHooksRepository.findAll().size();

        // Create the WebHookEventTypesHooks with an existing ID
        webHookEventTypesHooks.setId(1L);
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO = webHookEventTypesHooksMapper.toDto(webHookEventTypesHooks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebHookEventTypesHooksMockMvc.perform(post("/api/web-hook-event-types-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventTypesHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebHookEventTypesHooks in the database
        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWebHookIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHookEventTypesHooksRepository.findAll().size();
        // set the field null
        webHookEventTypesHooks.setWebHookId(null);

        // Create the WebHookEventTypesHooks, which fails.
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO = webHookEventTypesHooksMapper.toDto(webHookEventTypesHooks);


        restWebHookEventTypesHooksMockMvc.perform(post("/api/web-hook-event-types-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventTypesHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWebHookEventTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHookEventTypesHooksRepository.findAll().size();
        // set the field null
        webHookEventTypesHooks.setWebHookEventTypeId(null);

        // Create the WebHookEventTypesHooks, which fails.
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO = webHookEventTypesHooksMapper.toDto(webHookEventTypesHooks);


        restWebHookEventTypesHooksMockMvc.perform(post("/api/web-hook-event-types-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventTypesHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebHookEventTypesHooks() throws Exception {
        // Initialize the database
        webHookEventTypesHooksRepository.saveAndFlush(webHookEventTypesHooks);

        // Get all the webHookEventTypesHooksList
        restWebHookEventTypesHooksMockMvc.perform(get("/api/web-hook-event-types-hooks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webHookEventTypesHooks.getId().intValue())))
            .andExpect(jsonPath("$.[*].webHookId").value(hasItem(DEFAULT_WEB_HOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].webHookEventTypeId").value(hasItem(DEFAULT_WEB_HOOK_EVENT_TYPE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getWebHookEventTypesHooks() throws Exception {
        // Initialize the database
        webHookEventTypesHooksRepository.saveAndFlush(webHookEventTypesHooks);

        // Get the webHookEventTypesHooks
        restWebHookEventTypesHooksMockMvc.perform(get("/api/web-hook-event-types-hooks/{id}", webHookEventTypesHooks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(webHookEventTypesHooks.getId().intValue()))
            .andExpect(jsonPath("$.webHookId").value(DEFAULT_WEB_HOOK_ID.intValue()))
            .andExpect(jsonPath("$.webHookEventTypeId").value(DEFAULT_WEB_HOOK_EVENT_TYPE_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWebHookEventTypesHooks() throws Exception {
        // Get the webHookEventTypesHooks
        restWebHookEventTypesHooksMockMvc.perform(get("/api/web-hook-event-types-hooks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebHookEventTypesHooks() throws Exception {
        // Initialize the database
        webHookEventTypesHooksRepository.saveAndFlush(webHookEventTypesHooks);

        int databaseSizeBeforeUpdate = webHookEventTypesHooksRepository.findAll().size();

        // Update the webHookEventTypesHooks
        WebHookEventTypesHooks updatedWebHookEventTypesHooks = webHookEventTypesHooksRepository.findById(webHookEventTypesHooks.getId()).get();
        // Disconnect from session so that the updates on updatedWebHookEventTypesHooks are not directly saved in db
        em.detach(updatedWebHookEventTypesHooks);
        updatedWebHookEventTypesHooks
            .webHookId(UPDATED_WEB_HOOK_ID)
            .webHookEventTypeId(UPDATED_WEB_HOOK_EVENT_TYPE_ID);
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO = webHookEventTypesHooksMapper.toDto(updatedWebHookEventTypesHooks);

        restWebHookEventTypesHooksMockMvc.perform(put("/api/web-hook-event-types-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventTypesHooksDTO)))
            .andExpect(status().isOk());

        // Validate the WebHookEventTypesHooks in the database
        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeUpdate);
        WebHookEventTypesHooks testWebHookEventTypesHooks = webHookEventTypesHooksList.get(webHookEventTypesHooksList.size() - 1);
        assertThat(testWebHookEventTypesHooks.getWebHookId()).isEqualTo(UPDATED_WEB_HOOK_ID);
        assertThat(testWebHookEventTypesHooks.getWebHookEventTypeId()).isEqualTo(UPDATED_WEB_HOOK_EVENT_TYPE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingWebHookEventTypesHooks() throws Exception {
        int databaseSizeBeforeUpdate = webHookEventTypesHooksRepository.findAll().size();

        // Create the WebHookEventTypesHooks
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO = webHookEventTypesHooksMapper.toDto(webHookEventTypesHooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebHookEventTypesHooksMockMvc.perform(put("/api/web-hook-event-types-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHookEventTypesHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebHookEventTypesHooks in the database
        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebHookEventTypesHooks() throws Exception {
        // Initialize the database
        webHookEventTypesHooksRepository.saveAndFlush(webHookEventTypesHooks);

        int databaseSizeBeforeDelete = webHookEventTypesHooksRepository.findAll().size();

        // Delete the webHookEventTypesHooks
        restWebHookEventTypesHooksMockMvc.perform(delete("/api/web-hook-event-types-hooks/{id}", webHookEventTypesHooks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebHookEventTypesHooks> webHookEventTypesHooksList = webHookEventTypesHooksRepository.findAll();
        assertThat(webHookEventTypesHooksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
