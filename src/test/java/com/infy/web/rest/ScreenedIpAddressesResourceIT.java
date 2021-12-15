/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ScreenedIpAddresses;
import com.infy.repository.ScreenedIpAddressesRepository;
import com.infy.service.ScreenedIpAddressesService;
import com.infy.service.dto.ScreenedIpAddressesDTO;
import com.infy.service.mapper.ScreenedIpAddressesMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScreenedIpAddressesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ScreenedIpAddressesResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTION_TYPE = 1;
    private static final Integer UPDATED_ACTION_TYPE = 2;

    private static final Integer DEFAULT_MATCH_COUNT = 1;
    private static final Integer UPDATED_MATCH_COUNT = 2;

    private static final Instant DEFAULT_LAST_MATCH_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MATCH_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScreenedIpAddressesRepository screenedIpAddressesRepository;

    @Autowired
    private ScreenedIpAddressesMapper screenedIpAddressesMapper;

    @Autowired
    private ScreenedIpAddressesService screenedIpAddressesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScreenedIpAddressesMockMvc;

    private ScreenedIpAddresses screenedIpAddresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScreenedIpAddresses createEntity(EntityManager em) {
        ScreenedIpAddresses screenedIpAddresses = new ScreenedIpAddresses()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .actionType(DEFAULT_ACTION_TYPE)
            .matchCount(DEFAULT_MATCH_COUNT)
            .lastMatchAt(DEFAULT_LAST_MATCH_AT);
        return screenedIpAddresses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScreenedIpAddresses createUpdatedEntity(EntityManager em) {
        ScreenedIpAddresses screenedIpAddresses = new ScreenedIpAddresses()
            .ipAddress(UPDATED_IP_ADDRESS)
            .actionType(UPDATED_ACTION_TYPE)
            .matchCount(UPDATED_MATCH_COUNT)
            .lastMatchAt(UPDATED_LAST_MATCH_AT);
        return screenedIpAddresses;
    }

    @BeforeEach
    public void initTest() {
        screenedIpAddresses = createEntity(em);
    }

    @Test
    @Transactional
    public void createScreenedIpAddresses() throws Exception {
        int databaseSizeBeforeCreate = screenedIpAddressesRepository.findAll().size();
        // Create the ScreenedIpAddresses
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(screenedIpAddresses);
        restScreenedIpAddressesMockMvc.perform(post("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isCreated());

        // Validate the ScreenedIpAddresses in the database
        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeCreate + 1);
        ScreenedIpAddresses testScreenedIpAddresses = screenedIpAddressesList.get(screenedIpAddressesList.size() - 1);
        assertThat(testScreenedIpAddresses.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testScreenedIpAddresses.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testScreenedIpAddresses.getMatchCount()).isEqualTo(DEFAULT_MATCH_COUNT);
        assertThat(testScreenedIpAddresses.getLastMatchAt()).isEqualTo(DEFAULT_LAST_MATCH_AT);
    }

    @Test
    @Transactional
    public void createScreenedIpAddressesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = screenedIpAddressesRepository.findAll().size();

        // Create the ScreenedIpAddresses with an existing ID
        screenedIpAddresses.setId(1L);
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(screenedIpAddresses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreenedIpAddressesMockMvc.perform(post("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScreenedIpAddresses in the database
        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedIpAddressesRepository.findAll().size();
        // set the field null
        screenedIpAddresses.setIpAddress(null);

        // Create the ScreenedIpAddresses, which fails.
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(screenedIpAddresses);


        restScreenedIpAddressesMockMvc.perform(post("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedIpAddressesRepository.findAll().size();
        // set the field null
        screenedIpAddresses.setActionType(null);

        // Create the ScreenedIpAddresses, which fails.
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(screenedIpAddresses);


        restScreenedIpAddressesMockMvc.perform(post("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatchCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenedIpAddressesRepository.findAll().size();
        // set the field null
        screenedIpAddresses.setMatchCount(null);

        // Create the ScreenedIpAddresses, which fails.
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(screenedIpAddresses);


        restScreenedIpAddressesMockMvc.perform(post("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isBadRequest());

        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScreenedIpAddresses() throws Exception {
        // Initialize the database
        screenedIpAddressesRepository.saveAndFlush(screenedIpAddresses);

        // Get all the screenedIpAddressesList
        restScreenedIpAddressesMockMvc.perform(get("/api/screened-ip-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screenedIpAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE)))
            .andExpect(jsonPath("$.[*].matchCount").value(hasItem(DEFAULT_MATCH_COUNT)))
            .andExpect(jsonPath("$.[*].lastMatchAt").value(hasItem(DEFAULT_LAST_MATCH_AT.toString())));
    }

    @Test
    @Transactional
    public void getScreenedIpAddresses() throws Exception {
        // Initialize the database
        screenedIpAddressesRepository.saveAndFlush(screenedIpAddresses);

        // Get the screenedIpAddresses
        restScreenedIpAddressesMockMvc.perform(get("/api/screened-ip-addresses/{id}", screenedIpAddresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(screenedIpAddresses.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE))
            .andExpect(jsonPath("$.matchCount").value(DEFAULT_MATCH_COUNT))
            .andExpect(jsonPath("$.lastMatchAt").value(DEFAULT_LAST_MATCH_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingScreenedIpAddresses() throws Exception {
        // Get the screenedIpAddresses
        restScreenedIpAddressesMockMvc.perform(get("/api/screened-ip-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScreenedIpAddresses() throws Exception {
        // Initialize the database
        screenedIpAddressesRepository.saveAndFlush(screenedIpAddresses);

        int databaseSizeBeforeUpdate = screenedIpAddressesRepository.findAll().size();

        // Update the screenedIpAddresses
        ScreenedIpAddresses updatedScreenedIpAddresses = screenedIpAddressesRepository.findById(screenedIpAddresses.getId()).get();
        // Disconnect from session so that the updates on updatedScreenedIpAddresses are not directly saved in db
        em.detach(updatedScreenedIpAddresses);
        updatedScreenedIpAddresses
            .ipAddress(UPDATED_IP_ADDRESS)
            .actionType(UPDATED_ACTION_TYPE)
            .matchCount(UPDATED_MATCH_COUNT)
            .lastMatchAt(UPDATED_LAST_MATCH_AT);
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(updatedScreenedIpAddresses);

        restScreenedIpAddressesMockMvc.perform(put("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isOk());

        // Validate the ScreenedIpAddresses in the database
        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeUpdate);
        ScreenedIpAddresses testScreenedIpAddresses = screenedIpAddressesList.get(screenedIpAddressesList.size() - 1);
        assertThat(testScreenedIpAddresses.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testScreenedIpAddresses.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testScreenedIpAddresses.getMatchCount()).isEqualTo(UPDATED_MATCH_COUNT);
        assertThat(testScreenedIpAddresses.getLastMatchAt()).isEqualTo(UPDATED_LAST_MATCH_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingScreenedIpAddresses() throws Exception {
        int databaseSizeBeforeUpdate = screenedIpAddressesRepository.findAll().size();

        // Create the ScreenedIpAddresses
        ScreenedIpAddressesDTO screenedIpAddressesDTO = screenedIpAddressesMapper.toDto(screenedIpAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScreenedIpAddressesMockMvc.perform(put("/api/screened-ip-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(screenedIpAddressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScreenedIpAddresses in the database
        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScreenedIpAddresses() throws Exception {
        // Initialize the database
        screenedIpAddressesRepository.saveAndFlush(screenedIpAddresses);

        int databaseSizeBeforeDelete = screenedIpAddressesRepository.findAll().size();

        // Delete the screenedIpAddresses
        restScreenedIpAddressesMockMvc.perform(delete("/api/screened-ip-addresses/{id}", screenedIpAddresses.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScreenedIpAddresses> screenedIpAddressesList = screenedIpAddressesRepository.findAll();
        assertThat(screenedIpAddressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
