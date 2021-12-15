/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.IncomingDomains;
import com.infy.repository.IncomingDomainsRepository;
import com.infy.service.IncomingDomainsService;
import com.infy.service.dto.IncomingDomainsDTO;
import com.infy.service.mapper.IncomingDomainsMapper;

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
 * Integration tests for the {@link IncomingDomainsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IncomingDomainsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HTTPS = false;
    private static final Boolean UPDATED_HTTPS = true;

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    @Autowired
    private IncomingDomainsRepository incomingDomainsRepository;

    @Autowired
    private IncomingDomainsMapper incomingDomainsMapper;

    @Autowired
    private IncomingDomainsService incomingDomainsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomingDomainsMockMvc;

    private IncomingDomains incomingDomains;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingDomains createEntity(EntityManager em) {
        IncomingDomains incomingDomains = new IncomingDomains()
            .name(DEFAULT_NAME)
            .https(DEFAULT_HTTPS)
            .port(DEFAULT_PORT);
        return incomingDomains;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingDomains createUpdatedEntity(EntityManager em) {
        IncomingDomains incomingDomains = new IncomingDomains()
            .name(UPDATED_NAME)
            .https(UPDATED_HTTPS)
            .port(UPDATED_PORT);
        return incomingDomains;
    }

    @BeforeEach
    public void initTest() {
        incomingDomains = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomingDomains() throws Exception {
        int databaseSizeBeforeCreate = incomingDomainsRepository.findAll().size();
        // Create the IncomingDomains
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(incomingDomains);
        restIncomingDomainsMockMvc.perform(post("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomingDomains in the database
        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeCreate + 1);
        IncomingDomains testIncomingDomains = incomingDomainsList.get(incomingDomainsList.size() - 1);
        assertThat(testIncomingDomains.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIncomingDomains.isHttps()).isEqualTo(DEFAULT_HTTPS);
        assertThat(testIncomingDomains.getPort()).isEqualTo(DEFAULT_PORT);
    }

    @Test
    @Transactional
    public void createIncomingDomainsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomingDomainsRepository.findAll().size();

        // Create the IncomingDomains with an existing ID
        incomingDomains.setId(1L);
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(incomingDomains);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomingDomainsMockMvc.perform(post("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingDomains in the database
        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingDomainsRepository.findAll().size();
        // set the field null
        incomingDomains.setName(null);

        // Create the IncomingDomains, which fails.
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(incomingDomains);


        restIncomingDomainsMockMvc.perform(post("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHttpsIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingDomainsRepository.findAll().size();
        // set the field null
        incomingDomains.setHttps(null);

        // Create the IncomingDomains, which fails.
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(incomingDomains);


        restIncomingDomainsMockMvc.perform(post("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingDomainsRepository.findAll().size();
        // set the field null
        incomingDomains.setPort(null);

        // Create the IncomingDomains, which fails.
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(incomingDomains);


        restIncomingDomainsMockMvc.perform(post("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomingDomains() throws Exception {
        // Initialize the database
        incomingDomainsRepository.saveAndFlush(incomingDomains);

        // Get all the incomingDomainsList
        restIncomingDomainsMockMvc.perform(get("/api/incoming-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomingDomains.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].https").value(hasItem(DEFAULT_HTTPS.booleanValue())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)));
    }

    @Test
    @Transactional
    public void getIncomingDomains() throws Exception {
        // Initialize the database
        incomingDomainsRepository.saveAndFlush(incomingDomains);

        // Get the incomingDomains
        restIncomingDomainsMockMvc.perform(get("/api/incoming-domains/{id}", incomingDomains.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomingDomains.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.https").value(DEFAULT_HTTPS.booleanValue()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT));
    }
    @Test
    @Transactional
    public void getNonExistingIncomingDomains() throws Exception {
        // Get the incomingDomains
        restIncomingDomainsMockMvc.perform(get("/api/incoming-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomingDomains() throws Exception {
        // Initialize the database
        incomingDomainsRepository.saveAndFlush(incomingDomains);

        int databaseSizeBeforeUpdate = incomingDomainsRepository.findAll().size();

        // Update the incomingDomains
        IncomingDomains updatedIncomingDomains = incomingDomainsRepository.findById(incomingDomains.getId()).get();
        // Disconnect from session so that the updates on updatedIncomingDomains are not directly saved in db
        em.detach(updatedIncomingDomains);
        updatedIncomingDomains
            .name(UPDATED_NAME)
            .https(UPDATED_HTTPS)
            .port(UPDATED_PORT);
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(updatedIncomingDomains);

        restIncomingDomainsMockMvc.perform(put("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isOk());

        // Validate the IncomingDomains in the database
        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeUpdate);
        IncomingDomains testIncomingDomains = incomingDomainsList.get(incomingDomainsList.size() - 1);
        assertThat(testIncomingDomains.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIncomingDomains.isHttps()).isEqualTo(UPDATED_HTTPS);
        assertThat(testIncomingDomains.getPort()).isEqualTo(UPDATED_PORT);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomingDomains() throws Exception {
        int databaseSizeBeforeUpdate = incomingDomainsRepository.findAll().size();

        // Create the IncomingDomains
        IncomingDomainsDTO incomingDomainsDTO = incomingDomainsMapper.toDto(incomingDomains);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomingDomainsMockMvc.perform(put("/api/incoming-domains").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingDomainsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingDomains in the database
        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomingDomains() throws Exception {
        // Initialize the database
        incomingDomainsRepository.saveAndFlush(incomingDomains);

        int databaseSizeBeforeDelete = incomingDomainsRepository.findAll().size();

        // Delete the incomingDomains
        restIncomingDomainsMockMvc.perform(delete("/api/incoming-domains/{id}", incomingDomains.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncomingDomains> incomingDomainsList = incomingDomainsRepository.findAll();
        assertThat(incomingDomainsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
