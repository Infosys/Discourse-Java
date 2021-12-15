/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.IncomingReferers;
import com.infy.repository.IncomingReferersRepository;
import com.infy.service.IncomingReferersService;
import com.infy.service.dto.IncomingReferersDTO;
import com.infy.service.mapper.IncomingReferersMapper;

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
 * Integration tests for the {@link IncomingReferersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IncomingReferersResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_INCOMING_DOMAIN_ID = 1L;
    private static final Long UPDATED_INCOMING_DOMAIN_ID = 2L;

    @Autowired
    private IncomingReferersRepository incomingReferersRepository;

    @Autowired
    private IncomingReferersMapper incomingReferersMapper;

    @Autowired
    private IncomingReferersService incomingReferersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomingReferersMockMvc;

    private IncomingReferers incomingReferers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingReferers createEntity(EntityManager em) {
        IncomingReferers incomingReferers = new IncomingReferers()
            .path(DEFAULT_PATH)
            .incomingDomainId(DEFAULT_INCOMING_DOMAIN_ID);
        return incomingReferers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingReferers createUpdatedEntity(EntityManager em) {
        IncomingReferers incomingReferers = new IncomingReferers()
            .path(UPDATED_PATH)
            .incomingDomainId(UPDATED_INCOMING_DOMAIN_ID);
        return incomingReferers;
    }

    @BeforeEach
    public void initTest() {
        incomingReferers = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomingReferers() throws Exception {
        int databaseSizeBeforeCreate = incomingReferersRepository.findAll().size();
        // Create the IncomingReferers
        IncomingReferersDTO incomingReferersDTO = incomingReferersMapper.toDto(incomingReferers);
        restIncomingReferersMockMvc.perform(post("/api/incoming-referers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingReferersDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomingReferers in the database
        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeCreate + 1);
        IncomingReferers testIncomingReferers = incomingReferersList.get(incomingReferersList.size() - 1);
        assertThat(testIncomingReferers.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testIncomingReferers.getIncomingDomainId()).isEqualTo(DEFAULT_INCOMING_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void createIncomingReferersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomingReferersRepository.findAll().size();

        // Create the IncomingReferers with an existing ID
        incomingReferers.setId(1L);
        IncomingReferersDTO incomingReferersDTO = incomingReferersMapper.toDto(incomingReferers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomingReferersMockMvc.perform(post("/api/incoming-referers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingReferersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingReferers in the database
        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingReferersRepository.findAll().size();
        // set the field null
        incomingReferers.setPath(null);

        // Create the IncomingReferers, which fails.
        IncomingReferersDTO incomingReferersDTO = incomingReferersMapper.toDto(incomingReferers);


        restIncomingReferersMockMvc.perform(post("/api/incoming-referers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingReferersDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncomingDomainIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingReferersRepository.findAll().size();
        // set the field null
        incomingReferers.setIncomingDomainId(null);

        // Create the IncomingReferers, which fails.
        IncomingReferersDTO incomingReferersDTO = incomingReferersMapper.toDto(incomingReferers);


        restIncomingReferersMockMvc.perform(post("/api/incoming-referers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingReferersDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomingReferers() throws Exception {
        // Initialize the database
        incomingReferersRepository.saveAndFlush(incomingReferers);

        // Get all the incomingReferersList
        restIncomingReferersMockMvc.perform(get("/api/incoming-referers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomingReferers.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].incomingDomainId").value(hasItem(DEFAULT_INCOMING_DOMAIN_ID.intValue())));
    }

    @Test
    @Transactional
    public void getIncomingReferers() throws Exception {
        // Initialize the database
        incomingReferersRepository.saveAndFlush(incomingReferers);

        // Get the incomingReferers
        restIncomingReferersMockMvc.perform(get("/api/incoming-referers/{id}", incomingReferers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomingReferers.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.incomingDomainId").value(DEFAULT_INCOMING_DOMAIN_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingIncomingReferers() throws Exception {
        // Get the incomingReferers
        restIncomingReferersMockMvc.perform(get("/api/incoming-referers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomingReferers() throws Exception {
        // Initialize the database
        incomingReferersRepository.saveAndFlush(incomingReferers);

        int databaseSizeBeforeUpdate = incomingReferersRepository.findAll().size();

        // Update the incomingReferers
        IncomingReferers updatedIncomingReferers = incomingReferersRepository.findById(incomingReferers.getId()).get();
        // Disconnect from session so that the updates on updatedIncomingReferers are not directly saved in db
        em.detach(updatedIncomingReferers);
        updatedIncomingReferers
            .path(UPDATED_PATH)
            .incomingDomainId(UPDATED_INCOMING_DOMAIN_ID);
        IncomingReferersDTO incomingReferersDTO = incomingReferersMapper.toDto(updatedIncomingReferers);

        restIncomingReferersMockMvc.perform(put("/api/incoming-referers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingReferersDTO)))
            .andExpect(status().isOk());

        // Validate the IncomingReferers in the database
        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeUpdate);
        IncomingReferers testIncomingReferers = incomingReferersList.get(incomingReferersList.size() - 1);
        assertThat(testIncomingReferers.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testIncomingReferers.getIncomingDomainId()).isEqualTo(UPDATED_INCOMING_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomingReferers() throws Exception {
        int databaseSizeBeforeUpdate = incomingReferersRepository.findAll().size();

        // Create the IncomingReferers
        IncomingReferersDTO incomingReferersDTO = incomingReferersMapper.toDto(incomingReferers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomingReferersMockMvc.perform(put("/api/incoming-referers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingReferersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingReferers in the database
        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomingReferers() throws Exception {
        // Initialize the database
        incomingReferersRepository.saveAndFlush(incomingReferers);

        int databaseSizeBeforeDelete = incomingReferersRepository.findAll().size();

        // Delete the incomingReferers
        restIncomingReferersMockMvc.perform(delete("/api/incoming-referers/{id}", incomingReferers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncomingReferers> incomingReferersList = incomingReferersRepository.findAll();
        assertThat(incomingReferersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
