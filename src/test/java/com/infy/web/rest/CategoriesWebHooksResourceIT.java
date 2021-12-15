/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CategoriesWebHooks;
import com.infy.repository.CategoriesWebHooksRepository;
import com.infy.service.CategoriesWebHooksService;
import com.infy.service.dto.CategoriesWebHooksDTO;
import com.infy.service.mapper.CategoriesWebHooksMapper;

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
 * Integration tests for the {@link CategoriesWebHooksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoriesWebHooksResourceIT {

    private static final Long DEFAULT_WEB_HOOK_ID = 1L;
    private static final Long UPDATED_WEB_HOOK_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    @Autowired
    private CategoriesWebHooksRepository categoriesWebHooksRepository;

    @Autowired
    private CategoriesWebHooksMapper categoriesWebHooksMapper;

    @Autowired
    private CategoriesWebHooksService categoriesWebHooksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesWebHooksMockMvc;

    private CategoriesWebHooks categoriesWebHooks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriesWebHooks createEntity(EntityManager em) {
        CategoriesWebHooks categoriesWebHooks = new CategoriesWebHooks()
            .webHookId(DEFAULT_WEB_HOOK_ID)
            .categoryId(DEFAULT_CATEGORY_ID);
        return categoriesWebHooks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriesWebHooks createUpdatedEntity(EntityManager em) {
        CategoriesWebHooks categoriesWebHooks = new CategoriesWebHooks()
            .webHookId(UPDATED_WEB_HOOK_ID)
            .categoryId(UPDATED_CATEGORY_ID);
        return categoriesWebHooks;
    }

    @BeforeEach
    public void initTest() {
        categoriesWebHooks = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriesWebHooks() throws Exception {
        int databaseSizeBeforeCreate = categoriesWebHooksRepository.findAll().size();
        // Create the CategoriesWebHooks
        CategoriesWebHooksDTO categoriesWebHooksDTO = categoriesWebHooksMapper.toDto(categoriesWebHooks);
        restCategoriesWebHooksMockMvc.perform(post("/api/categories-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesWebHooksDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriesWebHooks in the database
        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriesWebHooks testCategoriesWebHooks = categoriesWebHooksList.get(categoriesWebHooksList.size() - 1);
        assertThat(testCategoriesWebHooks.getWebHookId()).isEqualTo(DEFAULT_WEB_HOOK_ID);
        assertThat(testCategoriesWebHooks.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void createCategoriesWebHooksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriesWebHooksRepository.findAll().size();

        // Create the CategoriesWebHooks with an existing ID
        categoriesWebHooks.setId(1L);
        CategoriesWebHooksDTO categoriesWebHooksDTO = categoriesWebHooksMapper.toDto(categoriesWebHooks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesWebHooksMockMvc.perform(post("/api/categories-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesWebHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriesWebHooks in the database
        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWebHookIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesWebHooksRepository.findAll().size();
        // set the field null
        categoriesWebHooks.setWebHookId(null);

        // Create the CategoriesWebHooks, which fails.
        CategoriesWebHooksDTO categoriesWebHooksDTO = categoriesWebHooksMapper.toDto(categoriesWebHooks);


        restCategoriesWebHooksMockMvc.perform(post("/api/categories-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesWebHooksDTO)))
            .andExpect(status().isBadRequest());

        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesWebHooksRepository.findAll().size();
        // set the field null
        categoriesWebHooks.setCategoryId(null);

        // Create the CategoriesWebHooks, which fails.
        CategoriesWebHooksDTO categoriesWebHooksDTO = categoriesWebHooksMapper.toDto(categoriesWebHooks);


        restCategoriesWebHooksMockMvc.perform(post("/api/categories-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesWebHooksDTO)))
            .andExpect(status().isBadRequest());

        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriesWebHooks() throws Exception {
        // Initialize the database
        categoriesWebHooksRepository.saveAndFlush(categoriesWebHooks);

        // Get all the categoriesWebHooksList
        restCategoriesWebHooksMockMvc.perform(get("/api/categories-web-hooks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriesWebHooks.getId().intValue())))
            .andExpect(jsonPath("$.[*].webHookId").value(hasItem(DEFAULT_WEB_HOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCategoriesWebHooks() throws Exception {
        // Initialize the database
        categoriesWebHooksRepository.saveAndFlush(categoriesWebHooks);

        // Get the categoriesWebHooks
        restCategoriesWebHooksMockMvc.perform(get("/api/categories-web-hooks/{id}", categoriesWebHooks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriesWebHooks.getId().intValue()))
            .andExpect(jsonPath("$.webHookId").value(DEFAULT_WEB_HOOK_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCategoriesWebHooks() throws Exception {
        // Get the categoriesWebHooks
        restCategoriesWebHooksMockMvc.perform(get("/api/categories-web-hooks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriesWebHooks() throws Exception {
        // Initialize the database
        categoriesWebHooksRepository.saveAndFlush(categoriesWebHooks);

        int databaseSizeBeforeUpdate = categoriesWebHooksRepository.findAll().size();

        // Update the categoriesWebHooks
        CategoriesWebHooks updatedCategoriesWebHooks = categoriesWebHooksRepository.findById(categoriesWebHooks.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriesWebHooks are not directly saved in db
        em.detach(updatedCategoriesWebHooks);
        updatedCategoriesWebHooks
            .webHookId(UPDATED_WEB_HOOK_ID)
            .categoryId(UPDATED_CATEGORY_ID);
        CategoriesWebHooksDTO categoriesWebHooksDTO = categoriesWebHooksMapper.toDto(updatedCategoriesWebHooks);

        restCategoriesWebHooksMockMvc.perform(put("/api/categories-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesWebHooksDTO)))
            .andExpect(status().isOk());

        // Validate the CategoriesWebHooks in the database
        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeUpdate);
        CategoriesWebHooks testCategoriesWebHooks = categoriesWebHooksList.get(categoriesWebHooksList.size() - 1);
        assertThat(testCategoriesWebHooks.getWebHookId()).isEqualTo(UPDATED_WEB_HOOK_ID);
        assertThat(testCategoriesWebHooks.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriesWebHooks() throws Exception {
        int databaseSizeBeforeUpdate = categoriesWebHooksRepository.findAll().size();

        // Create the CategoriesWebHooks
        CategoriesWebHooksDTO categoriesWebHooksDTO = categoriesWebHooksMapper.toDto(categoriesWebHooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesWebHooksMockMvc.perform(put("/api/categories-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesWebHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriesWebHooks in the database
        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriesWebHooks() throws Exception {
        // Initialize the database
        categoriesWebHooksRepository.saveAndFlush(categoriesWebHooks);

        int databaseSizeBeforeDelete = categoriesWebHooksRepository.findAll().size();

        // Delete the categoriesWebHooks
        restCategoriesWebHooksMockMvc.perform(delete("/api/categories-web-hooks/{id}", categoriesWebHooks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriesWebHooks> categoriesWebHooksList = categoriesWebHooksRepository.findAll();
        assertThat(categoriesWebHooksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
