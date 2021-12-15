/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.WebCrawlerRequests;
import com.infy.repository.WebCrawlerRequestsRepository;
import com.infy.service.WebCrawlerRequestsService;
import com.infy.service.dto.WebCrawlerRequestsDTO;
import com.infy.service.mapper.WebCrawlerRequestsMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WebCrawlerRequestsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WebCrawlerRequestsResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Autowired
    private WebCrawlerRequestsRepository webCrawlerRequestsRepository;

    @Autowired
    private WebCrawlerRequestsMapper webCrawlerRequestsMapper;

    @Autowired
    private WebCrawlerRequestsService webCrawlerRequestsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWebCrawlerRequestsMockMvc;

    private WebCrawlerRequests webCrawlerRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebCrawlerRequests createEntity(EntityManager em) {
        WebCrawlerRequests webCrawlerRequests = new WebCrawlerRequests()
            .date(DEFAULT_DATE)
            .userAgent(DEFAULT_USER_AGENT)
            .count(DEFAULT_COUNT);
        return webCrawlerRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebCrawlerRequests createUpdatedEntity(EntityManager em) {
        WebCrawlerRequests webCrawlerRequests = new WebCrawlerRequests()
            .date(UPDATED_DATE)
            .userAgent(UPDATED_USER_AGENT)
            .count(UPDATED_COUNT);
        return webCrawlerRequests;
    }

    @BeforeEach
    public void initTest() {
        webCrawlerRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebCrawlerRequests() throws Exception {
        int databaseSizeBeforeCreate = webCrawlerRequestsRepository.findAll().size();
        // Create the WebCrawlerRequests
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(webCrawlerRequests);
        restWebCrawlerRequestsMockMvc.perform(post("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the WebCrawlerRequests in the database
        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        WebCrawlerRequests testWebCrawlerRequests = webCrawlerRequestsList.get(webCrawlerRequestsList.size() - 1);
        assertThat(testWebCrawlerRequests.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWebCrawlerRequests.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
        assertThat(testWebCrawlerRequests.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createWebCrawlerRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webCrawlerRequestsRepository.findAll().size();

        // Create the WebCrawlerRequests with an existing ID
        webCrawlerRequests.setId(1L);
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(webCrawlerRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebCrawlerRequestsMockMvc.perform(post("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebCrawlerRequests in the database
        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = webCrawlerRequestsRepository.findAll().size();
        // set the field null
        webCrawlerRequests.setDate(null);

        // Create the WebCrawlerRequests, which fails.
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(webCrawlerRequests);


        restWebCrawlerRequestsMockMvc.perform(post("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserAgentIsRequired() throws Exception {
        int databaseSizeBeforeTest = webCrawlerRequestsRepository.findAll().size();
        // set the field null
        webCrawlerRequests.setUserAgent(null);

        // Create the WebCrawlerRequests, which fails.
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(webCrawlerRequests);


        restWebCrawlerRequestsMockMvc.perform(post("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = webCrawlerRequestsRepository.findAll().size();
        // set the field null
        webCrawlerRequests.setCount(null);

        // Create the WebCrawlerRequests, which fails.
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(webCrawlerRequests);


        restWebCrawlerRequestsMockMvc.perform(post("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebCrawlerRequests() throws Exception {
        // Initialize the database
        webCrawlerRequestsRepository.saveAndFlush(webCrawlerRequests);

        // Get all the webCrawlerRequestsList
        restWebCrawlerRequestsMockMvc.perform(get("/api/web-crawler-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webCrawlerRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getWebCrawlerRequests() throws Exception {
        // Initialize the database
        webCrawlerRequestsRepository.saveAndFlush(webCrawlerRequests);

        // Get the webCrawlerRequests
        restWebCrawlerRequestsMockMvc.perform(get("/api/web-crawler-requests/{id}", webCrawlerRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(webCrawlerRequests.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingWebCrawlerRequests() throws Exception {
        // Get the webCrawlerRequests
        restWebCrawlerRequestsMockMvc.perform(get("/api/web-crawler-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebCrawlerRequests() throws Exception {
        // Initialize the database
        webCrawlerRequestsRepository.saveAndFlush(webCrawlerRequests);

        int databaseSizeBeforeUpdate = webCrawlerRequestsRepository.findAll().size();

        // Update the webCrawlerRequests
        WebCrawlerRequests updatedWebCrawlerRequests = webCrawlerRequestsRepository.findById(webCrawlerRequests.getId()).get();
        // Disconnect from session so that the updates on updatedWebCrawlerRequests are not directly saved in db
        em.detach(updatedWebCrawlerRequests);
        updatedWebCrawlerRequests
            .date(UPDATED_DATE)
            .userAgent(UPDATED_USER_AGENT)
            .count(UPDATED_COUNT);
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(updatedWebCrawlerRequests);

        restWebCrawlerRequestsMockMvc.perform(put("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the WebCrawlerRequests in the database
        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeUpdate);
        WebCrawlerRequests testWebCrawlerRequests = webCrawlerRequestsList.get(webCrawlerRequestsList.size() - 1);
        assertThat(testWebCrawlerRequests.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWebCrawlerRequests.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
        assertThat(testWebCrawlerRequests.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingWebCrawlerRequests() throws Exception {
        int databaseSizeBeforeUpdate = webCrawlerRequestsRepository.findAll().size();

        // Create the WebCrawlerRequests
        WebCrawlerRequestsDTO webCrawlerRequestsDTO = webCrawlerRequestsMapper.toDto(webCrawlerRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebCrawlerRequestsMockMvc.perform(put("/api/web-crawler-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(webCrawlerRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebCrawlerRequests in the database
        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebCrawlerRequests() throws Exception {
        // Initialize the database
        webCrawlerRequestsRepository.saveAndFlush(webCrawlerRequests);

        int databaseSizeBeforeDelete = webCrawlerRequestsRepository.findAll().size();

        // Delete the webCrawlerRequests
        restWebCrawlerRequestsMockMvc.perform(delete("/api/web-crawler-requests/{id}", webCrawlerRequests.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebCrawlerRequests> webCrawlerRequestsList = webCrawlerRequestsRepository.findAll();
        assertThat(webCrawlerRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
