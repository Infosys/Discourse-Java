/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.ChildThemes;
import com.infy.repository.ChildThemesRepository;
import com.infy.service.ChildThemesService;
import com.infy.service.dto.ChildThemesDTO;
import com.infy.service.mapper.ChildThemesMapper;

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
 * Integration tests for the {@link ChildThemesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ChildThemesResourceIT {

    private static final Long DEFAULT_PARENT_THEME_ID = 1L;
    private static final Long UPDATED_PARENT_THEME_ID = 2L;

    private static final Long DEFAULT_CHILD_THEME_ID = 1L;
    private static final Long UPDATED_CHILD_THEME_ID = 2L;

    @Autowired
    private ChildThemesRepository childThemesRepository;

    @Autowired
    private ChildThemesMapper childThemesMapper;

    @Autowired
    private ChildThemesService childThemesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildThemesMockMvc;

    private ChildThemes childThemes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildThemes createEntity(EntityManager em) {
        ChildThemes childThemes = new ChildThemes()
            .parentThemeId(DEFAULT_PARENT_THEME_ID)
            .childThemeId(DEFAULT_CHILD_THEME_ID);
        return childThemes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildThemes createUpdatedEntity(EntityManager em) {
        ChildThemes childThemes = new ChildThemes()
            .parentThemeId(UPDATED_PARENT_THEME_ID)
            .childThemeId(UPDATED_CHILD_THEME_ID);
        return childThemes;
    }

    @BeforeEach
    public void initTest() {
        childThemes = createEntity(em);
    }

    @Test
    @Transactional
    public void createChildThemes() throws Exception {
        int databaseSizeBeforeCreate = childThemesRepository.findAll().size();
        // Create the ChildThemes
        ChildThemesDTO childThemesDTO = childThemesMapper.toDto(childThemes);
        restChildThemesMockMvc.perform(post("/api/child-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(childThemesDTO)))
            .andExpect(status().isCreated());

        // Validate the ChildThemes in the database
        List<ChildThemes> childThemesList = childThemesRepository.findAll();
        assertThat(childThemesList).hasSize(databaseSizeBeforeCreate + 1);
        ChildThemes testChildThemes = childThemesList.get(childThemesList.size() - 1);
        assertThat(testChildThemes.getParentThemeId()).isEqualTo(DEFAULT_PARENT_THEME_ID);
        assertThat(testChildThemes.getChildThemeId()).isEqualTo(DEFAULT_CHILD_THEME_ID);
    }

    @Test
    @Transactional
    public void createChildThemesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childThemesRepository.findAll().size();

        // Create the ChildThemes with an existing ID
        childThemes.setId(1L);
        ChildThemesDTO childThemesDTO = childThemesMapper.toDto(childThemes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildThemesMockMvc.perform(post("/api/child-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(childThemesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChildThemes in the database
        List<ChildThemes> childThemesList = childThemesRepository.findAll();
        assertThat(childThemesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChildThemes() throws Exception {
        // Initialize the database
        childThemesRepository.saveAndFlush(childThemes);

        // Get all the childThemesList
        restChildThemesMockMvc.perform(get("/api/child-themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childThemes.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentThemeId").value(hasItem(DEFAULT_PARENT_THEME_ID.intValue())))
            .andExpect(jsonPath("$.[*].childThemeId").value(hasItem(DEFAULT_CHILD_THEME_ID.intValue())));
    }

    @Test
    @Transactional
    public void getChildThemes() throws Exception {
        // Initialize the database
        childThemesRepository.saveAndFlush(childThemes);

        // Get the childThemes
        restChildThemesMockMvc.perform(get("/api/child-themes/{id}", childThemes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childThemes.getId().intValue()))
            .andExpect(jsonPath("$.parentThemeId").value(DEFAULT_PARENT_THEME_ID.intValue()))
            .andExpect(jsonPath("$.childThemeId").value(DEFAULT_CHILD_THEME_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingChildThemes() throws Exception {
        // Get the childThemes
        restChildThemesMockMvc.perform(get("/api/child-themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChildThemes() throws Exception {
        // Initialize the database
        childThemesRepository.saveAndFlush(childThemes);

        int databaseSizeBeforeUpdate = childThemesRepository.findAll().size();

        // Update the childThemes
        ChildThemes updatedChildThemes = childThemesRepository.findById(childThemes.getId()).get();
        // Disconnect from session so that the updates on updatedChildThemes are not directly saved in db
        em.detach(updatedChildThemes);
        updatedChildThemes
            .parentThemeId(UPDATED_PARENT_THEME_ID)
            .childThemeId(UPDATED_CHILD_THEME_ID);
        ChildThemesDTO childThemesDTO = childThemesMapper.toDto(updatedChildThemes);

        restChildThemesMockMvc.perform(put("/api/child-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(childThemesDTO)))
            .andExpect(status().isOk());

        // Validate the ChildThemes in the database
        List<ChildThemes> childThemesList = childThemesRepository.findAll();
        assertThat(childThemesList).hasSize(databaseSizeBeforeUpdate);
        ChildThemes testChildThemes = childThemesList.get(childThemesList.size() - 1);
        assertThat(testChildThemes.getParentThemeId()).isEqualTo(UPDATED_PARENT_THEME_ID);
        assertThat(testChildThemes.getChildThemeId()).isEqualTo(UPDATED_CHILD_THEME_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingChildThemes() throws Exception {
        int databaseSizeBeforeUpdate = childThemesRepository.findAll().size();

        // Create the ChildThemes
        ChildThemesDTO childThemesDTO = childThemesMapper.toDto(childThemes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildThemesMockMvc.perform(put("/api/child-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(childThemesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChildThemes in the database
        List<ChildThemes> childThemesList = childThemesRepository.findAll();
        assertThat(childThemesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChildThemes() throws Exception {
        // Initialize the database
        childThemesRepository.saveAndFlush(childThemes);

        int databaseSizeBeforeDelete = childThemesRepository.findAll().size();

        // Delete the childThemes
        restChildThemesMockMvc.perform(delete("/api/child-themes/{id}", childThemes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChildThemes> childThemesList = childThemesRepository.findAll();
        assertThat(childThemesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
