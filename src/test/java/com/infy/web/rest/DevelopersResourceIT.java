/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Developers;
import com.infy.repository.DevelopersRepository;
import com.infy.service.DevelopersService;
import com.infy.service.dto.DevelopersDTO;
import com.infy.service.mapper.DevelopersMapper;

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
 * Integration tests for the {@link DevelopersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DevelopersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private DevelopersRepository developersRepository;

    @Autowired
    private DevelopersMapper developersMapper;

    @Autowired
    private DevelopersService developersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevelopersMockMvc;

    private Developers developers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developers createEntity(EntityManager em) {
        Developers developers = new Developers()
            .userId(DEFAULT_USER_ID);
        return developers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developers createUpdatedEntity(EntityManager em) {
        Developers developers = new Developers()
            .userId(UPDATED_USER_ID);
        return developers;
    }

    @BeforeEach
    public void initTest() {
        developers = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevelopers() throws Exception {
        int databaseSizeBeforeCreate = developersRepository.findAll().size();
        // Create the Developers
        DevelopersDTO developersDTO = developersMapper.toDto(developers);
        restDevelopersMockMvc.perform(post("/api/developers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developersDTO)))
            .andExpect(status().isCreated());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeCreate + 1);
        Developers testDevelopers = developersList.get(developersList.size() - 1);
        assertThat(testDevelopers.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createDevelopersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = developersRepository.findAll().size();

        // Create the Developers with an existing ID
        developers.setId(1L);
        DevelopersDTO developersDTO = developersMapper.toDto(developers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevelopersMockMvc.perform(post("/api/developers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = developersRepository.findAll().size();
        // set the field null
        developers.setUserId(null);

        // Create the Developers, which fails.
        DevelopersDTO developersDTO = developersMapper.toDto(developers);


        restDevelopersMockMvc.perform(post("/api/developers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developersDTO)))
            .andExpect(status().isBadRequest());

        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        // Get all the developersList
        restDevelopersMockMvc.perform(get("/api/developers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(developers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void getDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        // Get the developers
        restDevelopersMockMvc.perform(get("/api/developers/{id}", developers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(developers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingDevelopers() throws Exception {
        // Get the developers
        restDevelopersMockMvc.perform(get("/api/developers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        int databaseSizeBeforeUpdate = developersRepository.findAll().size();

        // Update the developers
        Developers updatedDevelopers = developersRepository.findById(developers.getId()).get();
        // Disconnect from session so that the updates on updatedDevelopers are not directly saved in db
        em.detach(updatedDevelopers);
        updatedDevelopers
            .userId(UPDATED_USER_ID);
        DevelopersDTO developersDTO = developersMapper.toDto(updatedDevelopers);

        restDevelopersMockMvc.perform(put("/api/developers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developersDTO)))
            .andExpect(status().isOk());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
        Developers testDevelopers = developersList.get(developersList.size() - 1);
        assertThat(testDevelopers.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDevelopers() throws Exception {
        int databaseSizeBeforeUpdate = developersRepository.findAll().size();

        // Create the Developers
        DevelopersDTO developersDTO = developersMapper.toDto(developers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevelopersMockMvc.perform(put("/api/developers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Developers in the database
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevelopers() throws Exception {
        // Initialize the database
        developersRepository.saveAndFlush(developers);

        int databaseSizeBeforeDelete = developersRepository.findAll().size();

        // Delete the developers
        restDevelopersMockMvc.perform(delete("/api/developers/{id}", developers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Developers> developersList = developersRepository.findAll();
        assertThat(developersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
