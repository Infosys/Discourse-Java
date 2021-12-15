/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ApplicationRequests;
import com.infy.repository.ApplicationRequestsRepository;
import com.infy.service.ApplicationRequestsService;
import com.infy.service.dto.ApplicationRequestsDTO;
import com.infy.service.mapper.ApplicationRequestsMapper;

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
 * Integration tests for the {@link ApplicationRequestsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ApplicationRequestsResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_REQ_TYPE = 1L;
    private static final Long UPDATED_REQ_TYPE = 2L;

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    @Autowired
    private ApplicationRequestsRepository applicationRequestsRepository;

    @Autowired
    private ApplicationRequestsMapper applicationRequestsMapper;

    @Autowired
    private ApplicationRequestsService applicationRequestsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationRequestsMockMvc;

    private ApplicationRequests applicationRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationRequests createEntity(EntityManager em) {
        ApplicationRequests applicationRequests = new ApplicationRequests()
            .date(DEFAULT_DATE)
            .reqType(DEFAULT_REQ_TYPE)
            .count(DEFAULT_COUNT);
        return applicationRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationRequests createUpdatedEntity(EntityManager em) {
        ApplicationRequests applicationRequests = new ApplicationRequests()
            .date(UPDATED_DATE)
            .reqType(UPDATED_REQ_TYPE)
            .count(UPDATED_COUNT);
        return applicationRequests;
    }

    @BeforeEach
    public void initTest() {
        applicationRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationRequests() throws Exception {
        int databaseSizeBeforeCreate = applicationRequestsRepository.findAll().size();
        // Create the ApplicationRequests
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(applicationRequests);
        restApplicationRequestsMockMvc.perform(post("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationRequests in the database
        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationRequests testApplicationRequests = applicationRequestsList.get(applicationRequestsList.size() - 1);
        assertThat(testApplicationRequests.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testApplicationRequests.getReqType()).isEqualTo(DEFAULT_REQ_TYPE);
        assertThat(testApplicationRequests.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createApplicationRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRequestsRepository.findAll().size();

        // Create the ApplicationRequests with an existing ID
        applicationRequests.setId(1L);
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(applicationRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationRequestsMockMvc.perform(post("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequests in the database
        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRequestsRepository.findAll().size();
        // set the field null
        applicationRequests.setDate(null);

        // Create the ApplicationRequests, which fails.
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(applicationRequests);


        restApplicationRequestsMockMvc.perform(post("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReqTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRequestsRepository.findAll().size();
        // set the field null
        applicationRequests.setReqType(null);

        // Create the ApplicationRequests, which fails.
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(applicationRequests);


        restApplicationRequestsMockMvc.perform(post("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRequestsRepository.findAll().size();
        // set the field null
        applicationRequests.setCount(null);

        // Create the ApplicationRequests, which fails.
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(applicationRequests);


        restApplicationRequestsMockMvc.perform(post("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationRequests() throws Exception {
        // Initialize the database
        applicationRequestsRepository.saveAndFlush(applicationRequests);

        // Get all the applicationRequestsList
        restApplicationRequestsMockMvc.perform(get("/api/application-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].reqType").value(hasItem(DEFAULT_REQ_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getApplicationRequests() throws Exception {
        // Initialize the database
        applicationRequestsRepository.saveAndFlush(applicationRequests);

        // Get the applicationRequests
        restApplicationRequestsMockMvc.perform(get("/api/application-requests/{id}", applicationRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationRequests.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.reqType").value(DEFAULT_REQ_TYPE.intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingApplicationRequests() throws Exception {
        // Get the applicationRequests
        restApplicationRequestsMockMvc.perform(get("/api/application-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationRequests() throws Exception {
        // Initialize the database
        applicationRequestsRepository.saveAndFlush(applicationRequests);

        int databaseSizeBeforeUpdate = applicationRequestsRepository.findAll().size();

        // Update the applicationRequests
        ApplicationRequests updatedApplicationRequests = applicationRequestsRepository.findById(applicationRequests.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationRequests are not directly saved in db
        em.detach(updatedApplicationRequests);
        updatedApplicationRequests
            .date(UPDATED_DATE)
            .reqType(UPDATED_REQ_TYPE)
            .count(UPDATED_COUNT);
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(updatedApplicationRequests);

        restApplicationRequestsMockMvc.perform(put("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationRequests in the database
        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeUpdate);
        ApplicationRequests testApplicationRequests = applicationRequestsList.get(applicationRequestsList.size() - 1);
        assertThat(testApplicationRequests.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testApplicationRequests.getReqType()).isEqualTo(UPDATED_REQ_TYPE);
        assertThat(testApplicationRequests.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationRequests() throws Exception {
        int databaseSizeBeforeUpdate = applicationRequestsRepository.findAll().size();

        // Create the ApplicationRequests
        ApplicationRequestsDTO applicationRequestsDTO = applicationRequestsMapper.toDto(applicationRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationRequestsMockMvc.perform(put("/api/application-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationRequests in the database
        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationRequests() throws Exception {
        // Initialize the database
        applicationRequestsRepository.saveAndFlush(applicationRequests);

        int databaseSizeBeforeDelete = applicationRequestsRepository.findAll().size();

        // Delete the applicationRequests
        restApplicationRequestsMockMvc.perform(delete("/api/application-requests/{id}", applicationRequests.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationRequests> applicationRequestsList = applicationRequestsRepository.findAll();
        assertThat(applicationRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
