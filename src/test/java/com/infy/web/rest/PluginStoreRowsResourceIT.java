/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PluginStoreRows;
import com.infy.repository.PluginStoreRowsRepository;
import com.infy.service.PluginStoreRowsService;
import com.infy.service.dto.PluginStoreRowsDTO;
import com.infy.service.mapper.PluginStoreRowsMapper;

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
 * Integration tests for the {@link PluginStoreRowsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PluginStoreRowsResourceIT {

    private static final String DEFAULT_PLUGIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLUGIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private PluginStoreRowsRepository pluginStoreRowsRepository;

    @Autowired
    private PluginStoreRowsMapper pluginStoreRowsMapper;

    @Autowired
    private PluginStoreRowsService pluginStoreRowsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPluginStoreRowsMockMvc;

    private PluginStoreRows pluginStoreRows;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PluginStoreRows createEntity(EntityManager em) {
        PluginStoreRows pluginStoreRows = new PluginStoreRows()
            .pluginName(DEFAULT_PLUGIN_NAME)
            .key(DEFAULT_KEY)
            .typeName(DEFAULT_TYPE_NAME)
            .value(DEFAULT_VALUE);
        return pluginStoreRows;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PluginStoreRows createUpdatedEntity(EntityManager em) {
        PluginStoreRows pluginStoreRows = new PluginStoreRows()
            .pluginName(UPDATED_PLUGIN_NAME)
            .key(UPDATED_KEY)
            .typeName(UPDATED_TYPE_NAME)
            .value(UPDATED_VALUE);
        return pluginStoreRows;
    }

    @BeforeEach
    public void initTest() {
        pluginStoreRows = createEntity(em);
    }

    @Test
    @Transactional
    public void createPluginStoreRows() throws Exception {
        int databaseSizeBeforeCreate = pluginStoreRowsRepository.findAll().size();
        // Create the PluginStoreRows
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(pluginStoreRows);
        restPluginStoreRowsMockMvc.perform(post("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isCreated());

        // Validate the PluginStoreRows in the database
        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeCreate + 1);
        PluginStoreRows testPluginStoreRows = pluginStoreRowsList.get(pluginStoreRowsList.size() - 1);
        assertThat(testPluginStoreRows.getPluginName()).isEqualTo(DEFAULT_PLUGIN_NAME);
        assertThat(testPluginStoreRows.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testPluginStoreRows.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testPluginStoreRows.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPluginStoreRowsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pluginStoreRowsRepository.findAll().size();

        // Create the PluginStoreRows with an existing ID
        pluginStoreRows.setId(1L);
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(pluginStoreRows);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPluginStoreRowsMockMvc.perform(post("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PluginStoreRows in the database
        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPluginNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pluginStoreRowsRepository.findAll().size();
        // set the field null
        pluginStoreRows.setPluginName(null);

        // Create the PluginStoreRows, which fails.
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(pluginStoreRows);


        restPluginStoreRowsMockMvc.perform(post("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isBadRequest());

        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = pluginStoreRowsRepository.findAll().size();
        // set the field null
        pluginStoreRows.setKey(null);

        // Create the PluginStoreRows, which fails.
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(pluginStoreRows);


        restPluginStoreRowsMockMvc.perform(post("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isBadRequest());

        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pluginStoreRowsRepository.findAll().size();
        // set the field null
        pluginStoreRows.setTypeName(null);

        // Create the PluginStoreRows, which fails.
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(pluginStoreRows);


        restPluginStoreRowsMockMvc.perform(post("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isBadRequest());

        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPluginStoreRows() throws Exception {
        // Initialize the database
        pluginStoreRowsRepository.saveAndFlush(pluginStoreRows);

        // Get all the pluginStoreRowsList
        restPluginStoreRowsMockMvc.perform(get("/api/plugin-store-rows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pluginStoreRows.getId().intValue())))
            .andExpect(jsonPath("$.[*].pluginName").value(hasItem(DEFAULT_PLUGIN_NAME)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getPluginStoreRows() throws Exception {
        // Initialize the database
        pluginStoreRowsRepository.saveAndFlush(pluginStoreRows);

        // Get the pluginStoreRows
        restPluginStoreRowsMockMvc.perform(get("/api/plugin-store-rows/{id}", pluginStoreRows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pluginStoreRows.getId().intValue()))
            .andExpect(jsonPath("$.pluginName").value(DEFAULT_PLUGIN_NAME))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingPluginStoreRows() throws Exception {
        // Get the pluginStoreRows
        restPluginStoreRowsMockMvc.perform(get("/api/plugin-store-rows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePluginStoreRows() throws Exception {
        // Initialize the database
        pluginStoreRowsRepository.saveAndFlush(pluginStoreRows);

        int databaseSizeBeforeUpdate = pluginStoreRowsRepository.findAll().size();

        // Update the pluginStoreRows
        PluginStoreRows updatedPluginStoreRows = pluginStoreRowsRepository.findById(pluginStoreRows.getId()).get();
        // Disconnect from session so that the updates on updatedPluginStoreRows are not directly saved in db
        em.detach(updatedPluginStoreRows);
        updatedPluginStoreRows
            .pluginName(UPDATED_PLUGIN_NAME)
            .key(UPDATED_KEY)
            .typeName(UPDATED_TYPE_NAME)
            .value(UPDATED_VALUE);
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(updatedPluginStoreRows);

        restPluginStoreRowsMockMvc.perform(put("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isOk());

        // Validate the PluginStoreRows in the database
        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeUpdate);
        PluginStoreRows testPluginStoreRows = pluginStoreRowsList.get(pluginStoreRowsList.size() - 1);
        assertThat(testPluginStoreRows.getPluginName()).isEqualTo(UPDATED_PLUGIN_NAME);
        assertThat(testPluginStoreRows.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPluginStoreRows.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testPluginStoreRows.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPluginStoreRows() throws Exception {
        int databaseSizeBeforeUpdate = pluginStoreRowsRepository.findAll().size();

        // Create the PluginStoreRows
        PluginStoreRowsDTO pluginStoreRowsDTO = pluginStoreRowsMapper.toDto(pluginStoreRows);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPluginStoreRowsMockMvc.perform(put("/api/plugin-store-rows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pluginStoreRowsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PluginStoreRows in the database
        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePluginStoreRows() throws Exception {
        // Initialize the database
        pluginStoreRowsRepository.saveAndFlush(pluginStoreRows);

        int databaseSizeBeforeDelete = pluginStoreRowsRepository.findAll().size();

        // Delete the pluginStoreRows
        restPluginStoreRowsMockMvc.perform(delete("/api/plugin-store-rows/{id}", pluginStoreRows.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PluginStoreRows> pluginStoreRowsList = pluginStoreRowsRepository.findAll();
        assertThat(pluginStoreRowsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
