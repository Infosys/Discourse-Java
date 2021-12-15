/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.EmbeddableHosts;
import com.infy.repository.EmbeddableHostsRepository;
import com.infy.service.EmbeddableHostsService;
import com.infy.service.dto.EmbeddableHostsDTO;
import com.infy.service.mapper.EmbeddableHostsMapper;

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
 * Integration tests for the {@link EmbeddableHostsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmbeddableHostsResourceIT {

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_CLASS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_PATHS = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_PATHS = "BBBBBBBBBB";

    @Autowired
    private EmbeddableHostsRepository embeddableHostsRepository;

    @Autowired
    private EmbeddableHostsMapper embeddableHostsMapper;

    @Autowired
    private EmbeddableHostsService embeddableHostsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmbeddableHostsMockMvc;

    private EmbeddableHosts embeddableHosts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbeddableHosts createEntity(EntityManager em) {
        EmbeddableHosts embeddableHosts = new EmbeddableHosts()
            .host(DEFAULT_HOST)
            .categoryId(DEFAULT_CATEGORY_ID)
            .className(DEFAULT_CLASS_NAME)
            .allowedPaths(DEFAULT_ALLOWED_PATHS);
        return embeddableHosts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbeddableHosts createUpdatedEntity(EntityManager em) {
        EmbeddableHosts embeddableHosts = new EmbeddableHosts()
            .host(UPDATED_HOST)
            .categoryId(UPDATED_CATEGORY_ID)
            .className(UPDATED_CLASS_NAME)
            .allowedPaths(UPDATED_ALLOWED_PATHS);
        return embeddableHosts;
    }

    @BeforeEach
    public void initTest() {
        embeddableHosts = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmbeddableHosts() throws Exception {
        int databaseSizeBeforeCreate = embeddableHostsRepository.findAll().size();
        // Create the EmbeddableHosts
        EmbeddableHostsDTO embeddableHostsDTO = embeddableHostsMapper.toDto(embeddableHosts);
        restEmbeddableHostsMockMvc.perform(post("/api/embeddable-hosts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embeddableHostsDTO)))
            .andExpect(status().isCreated());

        // Validate the EmbeddableHosts in the database
        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeCreate + 1);
        EmbeddableHosts testEmbeddableHosts = embeddableHostsList.get(embeddableHostsList.size() - 1);
        assertThat(testEmbeddableHosts.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testEmbeddableHosts.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testEmbeddableHosts.getClassName()).isEqualTo(DEFAULT_CLASS_NAME);
        assertThat(testEmbeddableHosts.getAllowedPaths()).isEqualTo(DEFAULT_ALLOWED_PATHS);
    }

    @Test
    @Transactional
    public void createEmbeddableHostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = embeddableHostsRepository.findAll().size();

        // Create the EmbeddableHosts with an existing ID
        embeddableHosts.setId(1L);
        EmbeddableHostsDTO embeddableHostsDTO = embeddableHostsMapper.toDto(embeddableHosts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbeddableHostsMockMvc.perform(post("/api/embeddable-hosts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embeddableHostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmbeddableHosts in the database
        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = embeddableHostsRepository.findAll().size();
        // set the field null
        embeddableHosts.setHost(null);

        // Create the EmbeddableHosts, which fails.
        EmbeddableHostsDTO embeddableHostsDTO = embeddableHostsMapper.toDto(embeddableHosts);


        restEmbeddableHostsMockMvc.perform(post("/api/embeddable-hosts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embeddableHostsDTO)))
            .andExpect(status().isBadRequest());

        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = embeddableHostsRepository.findAll().size();
        // set the field null
        embeddableHosts.setCategoryId(null);

        // Create the EmbeddableHosts, which fails.
        EmbeddableHostsDTO embeddableHostsDTO = embeddableHostsMapper.toDto(embeddableHosts);


        restEmbeddableHostsMockMvc.perform(post("/api/embeddable-hosts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embeddableHostsDTO)))
            .andExpect(status().isBadRequest());

        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmbeddableHosts() throws Exception {
        // Initialize the database
        embeddableHostsRepository.saveAndFlush(embeddableHosts);

        // Get all the embeddableHostsList
        restEmbeddableHostsMockMvc.perform(get("/api/embeddable-hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embeddableHosts.getId().intValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].allowedPaths").value(hasItem(DEFAULT_ALLOWED_PATHS)));
    }

    @Test
    @Transactional
    public void getEmbeddableHosts() throws Exception {
        // Initialize the database
        embeddableHostsRepository.saveAndFlush(embeddableHosts);

        // Get the embeddableHosts
        restEmbeddableHostsMockMvc.perform(get("/api/embeddable-hosts/{id}", embeddableHosts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(embeddableHosts.getId().intValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.className").value(DEFAULT_CLASS_NAME))
            .andExpect(jsonPath("$.allowedPaths").value(DEFAULT_ALLOWED_PATHS));
    }
    @Test
    @Transactional
    public void getNonExistingEmbeddableHosts() throws Exception {
        // Get the embeddableHosts
        restEmbeddableHostsMockMvc.perform(get("/api/embeddable-hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmbeddableHosts() throws Exception {
        // Initialize the database
        embeddableHostsRepository.saveAndFlush(embeddableHosts);

        int databaseSizeBeforeUpdate = embeddableHostsRepository.findAll().size();

        // Update the embeddableHosts
        EmbeddableHosts updatedEmbeddableHosts = embeddableHostsRepository.findById(embeddableHosts.getId()).get();
        // Disconnect from session so that the updates on updatedEmbeddableHosts are not directly saved in db
        em.detach(updatedEmbeddableHosts);
        updatedEmbeddableHosts
            .host(UPDATED_HOST)
            .categoryId(UPDATED_CATEGORY_ID)
            .className(UPDATED_CLASS_NAME)
            .allowedPaths(UPDATED_ALLOWED_PATHS);
        EmbeddableHostsDTO embeddableHostsDTO = embeddableHostsMapper.toDto(updatedEmbeddableHosts);

        restEmbeddableHostsMockMvc.perform(put("/api/embeddable-hosts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embeddableHostsDTO)))
            .andExpect(status().isOk());

        // Validate the EmbeddableHosts in the database
        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeUpdate);
        EmbeddableHosts testEmbeddableHosts = embeddableHostsList.get(embeddableHostsList.size() - 1);
        assertThat(testEmbeddableHosts.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testEmbeddableHosts.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testEmbeddableHosts.getClassName()).isEqualTo(UPDATED_CLASS_NAME);
        assertThat(testEmbeddableHosts.getAllowedPaths()).isEqualTo(UPDATED_ALLOWED_PATHS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmbeddableHosts() throws Exception {
        int databaseSizeBeforeUpdate = embeddableHostsRepository.findAll().size();

        // Create the EmbeddableHosts
        EmbeddableHostsDTO embeddableHostsDTO = embeddableHostsMapper.toDto(embeddableHosts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbeddableHostsMockMvc.perform(put("/api/embeddable-hosts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embeddableHostsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmbeddableHosts in the database
        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmbeddableHosts() throws Exception {
        // Initialize the database
        embeddableHostsRepository.saveAndFlush(embeddableHosts);

        int databaseSizeBeforeDelete = embeddableHostsRepository.findAll().size();

        // Delete the embeddableHosts
        restEmbeddableHostsMockMvc.perform(delete("/api/embeddable-hosts/{id}", embeddableHosts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmbeddableHosts> embeddableHostsList = embeddableHostsRepository.findAll();
        assertThat(embeddableHostsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
