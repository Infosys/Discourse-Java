/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.WebHooks;
import com.infy.repository.WebHooksRepository;
import com.infy.service.WebHooksService;
import com.infy.service.dto.WebHooksDTO;
import com.infy.service.mapper.WebHooksMapper;

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
 * Integration tests for the {@link WebHooksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WebHooksResourceIT {

    private static final String DEFAULT_PAYLOAD_URL = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONTENT_TYPE = 1;
    private static final Integer UPDATED_CONTENT_TYPE = 2;

    private static final Integer DEFAULT_LAST_DELIVERY_STATUS = 1;
    private static final Integer UPDATED_LAST_DELIVERY_STATUS = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WILDCARD_WEB_HOOK = false;
    private static final Boolean UPDATED_WILDCARD_WEB_HOOK = true;

    private static final Boolean DEFAULT_VERIFY_CERTIFICATE = false;
    private static final Boolean UPDATED_VERIFY_CERTIFICATE = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private WebHooksRepository webHooksRepository;

    @Autowired
    private WebHooksMapper webHooksMapper;

    @Autowired
    private WebHooksService webHooksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWebHooksMockMvc;

    private WebHooks webHooks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebHooks createEntity(EntityManager em) {
        WebHooks webHooks = new WebHooks()
            .payloadUrl(DEFAULT_PAYLOAD_URL)
            .contentType(DEFAULT_CONTENT_TYPE)
            .lastDeliveryStatus(DEFAULT_LAST_DELIVERY_STATUS)
            .status(DEFAULT_STATUS)
            .secret(DEFAULT_SECRET)
            .wildcardWebHook(DEFAULT_WILDCARD_WEB_HOOK)
            .verifyCertificate(DEFAULT_VERIFY_CERTIFICATE)
            .active(DEFAULT_ACTIVE);
        return webHooks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebHooks createUpdatedEntity(EntityManager em) {
        WebHooks webHooks = new WebHooks()
            .payloadUrl(UPDATED_PAYLOAD_URL)
            .contentType(UPDATED_CONTENT_TYPE)
            .lastDeliveryStatus(UPDATED_LAST_DELIVERY_STATUS)
            .status(UPDATED_STATUS)
            .secret(UPDATED_SECRET)
            .wildcardWebHook(UPDATED_WILDCARD_WEB_HOOK)
            .verifyCertificate(UPDATED_VERIFY_CERTIFICATE)
            .active(UPDATED_ACTIVE);
        return webHooks;
    }

    @BeforeEach
    public void initTest() {
        webHooks = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebHooks() throws Exception {
        int databaseSizeBeforeCreate = webHooksRepository.findAll().size();
        // Create the WebHooks
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);
        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isCreated());

        // Validate the WebHooks in the database
        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeCreate + 1);
        WebHooks testWebHooks = webHooksList.get(webHooksList.size() - 1);
        assertThat(testWebHooks.getPayloadUrl()).isEqualTo(DEFAULT_PAYLOAD_URL);
        assertThat(testWebHooks.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testWebHooks.getLastDeliveryStatus()).isEqualTo(DEFAULT_LAST_DELIVERY_STATUS);
        assertThat(testWebHooks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWebHooks.getSecret()).isEqualTo(DEFAULT_SECRET);
        assertThat(testWebHooks.isWildcardWebHook()).isEqualTo(DEFAULT_WILDCARD_WEB_HOOK);
        assertThat(testWebHooks.isVerifyCertificate()).isEqualTo(DEFAULT_VERIFY_CERTIFICATE);
        assertThat(testWebHooks.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createWebHooksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webHooksRepository.findAll().size();

        // Create the WebHooks with an existing ID
        webHooks.setId(1L);
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebHooks in the database
        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPayloadUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setPayloadUrl(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setContentType(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastDeliveryStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setLastDeliveryStatus(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setStatus(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWildcardWebHookIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setWildcardWebHook(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVerifyCertificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setVerifyCertificate(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = webHooksRepository.findAll().size();
        // set the field null
        webHooks.setActive(null);

        // Create the WebHooks, which fails.
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);


        restWebHooksMockMvc.perform(post("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebHooks() throws Exception {
        // Initialize the database
        webHooksRepository.saveAndFlush(webHooks);

        // Get all the webHooksList
        restWebHooksMockMvc.perform(get("/api/web-hooks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webHooks.getId().intValue())))
            .andExpect(jsonPath("$.[*].payloadUrl").value(hasItem(DEFAULT_PAYLOAD_URL)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lastDeliveryStatus").value(hasItem(DEFAULT_LAST_DELIVERY_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET)))
            .andExpect(jsonPath("$.[*].wildcardWebHook").value(hasItem(DEFAULT_WILDCARD_WEB_HOOK.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyCertificate").value(hasItem(DEFAULT_VERIFY_CERTIFICATE.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getWebHooks() throws Exception {
        // Initialize the database
        webHooksRepository.saveAndFlush(webHooks);

        // Get the webHooks
        restWebHooksMockMvc.perform(get("/api/web-hooks/{id}", webHooks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(webHooks.getId().intValue()))
            .andExpect(jsonPath("$.payloadUrl").value(DEFAULT_PAYLOAD_URL))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.lastDeliveryStatus").value(DEFAULT_LAST_DELIVERY_STATUS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET))
            .andExpect(jsonPath("$.wildcardWebHook").value(DEFAULT_WILDCARD_WEB_HOOK.booleanValue()))
            .andExpect(jsonPath("$.verifyCertificate").value(DEFAULT_VERIFY_CERTIFICATE.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWebHooks() throws Exception {
        // Get the webHooks
        restWebHooksMockMvc.perform(get("/api/web-hooks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebHooks() throws Exception {
        // Initialize the database
        webHooksRepository.saveAndFlush(webHooks);

        int databaseSizeBeforeUpdate = webHooksRepository.findAll().size();

        // Update the webHooks
        WebHooks updatedWebHooks = webHooksRepository.findById(webHooks.getId()).get();
        // Disconnect from session so that the updates on updatedWebHooks are not directly saved in db
        em.detach(updatedWebHooks);
        updatedWebHooks
            .payloadUrl(UPDATED_PAYLOAD_URL)
            .contentType(UPDATED_CONTENT_TYPE)
            .lastDeliveryStatus(UPDATED_LAST_DELIVERY_STATUS)
            .status(UPDATED_STATUS)
            .secret(UPDATED_SECRET)
            .wildcardWebHook(UPDATED_WILDCARD_WEB_HOOK)
            .verifyCertificate(UPDATED_VERIFY_CERTIFICATE)
            .active(UPDATED_ACTIVE);
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(updatedWebHooks);

        restWebHooksMockMvc.perform(put("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isOk());

        // Validate the WebHooks in the database
        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeUpdate);
        WebHooks testWebHooks = webHooksList.get(webHooksList.size() - 1);
        assertThat(testWebHooks.getPayloadUrl()).isEqualTo(UPDATED_PAYLOAD_URL);
        assertThat(testWebHooks.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testWebHooks.getLastDeliveryStatus()).isEqualTo(UPDATED_LAST_DELIVERY_STATUS);
        assertThat(testWebHooks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWebHooks.getSecret()).isEqualTo(UPDATED_SECRET);
        assertThat(testWebHooks.isWildcardWebHook()).isEqualTo(UPDATED_WILDCARD_WEB_HOOK);
        assertThat(testWebHooks.isVerifyCertificate()).isEqualTo(UPDATED_VERIFY_CERTIFICATE);
        assertThat(testWebHooks.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingWebHooks() throws Exception {
        int databaseSizeBeforeUpdate = webHooksRepository.findAll().size();

        // Create the WebHooks
        WebHooksDTO webHooksDTO = webHooksMapper.toDto(webHooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebHooksMockMvc.perform(put("/api/web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebHooks in the database
        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebHooks() throws Exception {
        // Initialize the database
        webHooksRepository.saveAndFlush(webHooks);

        int databaseSizeBeforeDelete = webHooksRepository.findAll().size();

        // Delete the webHooks
        restWebHooksMockMvc.perform(delete("/api/web-hooks/{id}", webHooks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebHooks> webHooksList = webHooksRepository.findAll();
        assertThat(webHooksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
