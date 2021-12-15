/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TagsWebHooks;
import com.infy.repository.TagsWebHooksRepository;
import com.infy.service.TagsWebHooksService;
import com.infy.service.dto.TagsWebHooksDTO;
import com.infy.service.mapper.TagsWebHooksMapper;

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
 * Integration tests for the {@link TagsWebHooksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TagsWebHooksResourceIT {

    private static final Long DEFAULT_WEB_HOOK_ID = 1L;
    private static final Long UPDATED_WEB_HOOK_ID = 2L;

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    @Autowired
    private TagsWebHooksRepository tagsWebHooksRepository;

    @Autowired
    private TagsWebHooksMapper tagsWebHooksMapper;

    @Autowired
    private TagsWebHooksService tagsWebHooksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagsWebHooksMockMvc;

    private TagsWebHooks tagsWebHooks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagsWebHooks createEntity(EntityManager em) {
        TagsWebHooks tagsWebHooks = new TagsWebHooks()
            .webHookId(DEFAULT_WEB_HOOK_ID)
            .tagId(DEFAULT_TAG_ID);
        return tagsWebHooks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagsWebHooks createUpdatedEntity(EntityManager em) {
        TagsWebHooks tagsWebHooks = new TagsWebHooks()
            .webHookId(UPDATED_WEB_HOOK_ID)
            .tagId(UPDATED_TAG_ID);
        return tagsWebHooks;
    }

    @BeforeEach
    public void initTest() {
        tagsWebHooks = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagsWebHooks() throws Exception {
        int databaseSizeBeforeCreate = tagsWebHooksRepository.findAll().size();
        // Create the TagsWebHooks
        TagsWebHooksDTO tagsWebHooksDTO = tagsWebHooksMapper.toDto(tagsWebHooks);
        restTagsWebHooksMockMvc.perform(post("/api/tags-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagsWebHooksDTO)))
            .andExpect(status().isCreated());

        // Validate the TagsWebHooks in the database
        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeCreate + 1);
        TagsWebHooks testTagsWebHooks = tagsWebHooksList.get(tagsWebHooksList.size() - 1);
        assertThat(testTagsWebHooks.getWebHookId()).isEqualTo(DEFAULT_WEB_HOOK_ID);
        assertThat(testTagsWebHooks.getTagId()).isEqualTo(DEFAULT_TAG_ID);
    }

    @Test
    @Transactional
    public void createTagsWebHooksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagsWebHooksRepository.findAll().size();

        // Create the TagsWebHooks with an existing ID
        tagsWebHooks.setId(1L);
        TagsWebHooksDTO tagsWebHooksDTO = tagsWebHooksMapper.toDto(tagsWebHooks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagsWebHooksMockMvc.perform(post("/api/tags-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagsWebHooks in the database
        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWebHookIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagsWebHooksRepository.findAll().size();
        // set the field null
        tagsWebHooks.setWebHookId(null);

        // Create the TagsWebHooks, which fails.
        TagsWebHooksDTO tagsWebHooksDTO = tagsWebHooksMapper.toDto(tagsWebHooks);


        restTagsWebHooksMockMvc.perform(post("/api/tags-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagsWebHooksRepository.findAll().size();
        // set the field null
        tagsWebHooks.setTagId(null);

        // Create the TagsWebHooks, which fails.
        TagsWebHooksDTO tagsWebHooksDTO = tagsWebHooksMapper.toDto(tagsWebHooks);


        restTagsWebHooksMockMvc.perform(post("/api/tags-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagsWebHooks() throws Exception {
        // Initialize the database
        tagsWebHooksRepository.saveAndFlush(tagsWebHooks);

        // Get all the tagsWebHooksList
        restTagsWebHooksMockMvc.perform(get("/api/tags-web-hooks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagsWebHooks.getId().intValue())))
            .andExpect(jsonPath("$.[*].webHookId").value(hasItem(DEFAULT_WEB_HOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTagsWebHooks() throws Exception {
        // Initialize the database
        tagsWebHooksRepository.saveAndFlush(tagsWebHooks);

        // Get the tagsWebHooks
        restTagsWebHooksMockMvc.perform(get("/api/tags-web-hooks/{id}", tagsWebHooks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagsWebHooks.getId().intValue()))
            .andExpect(jsonPath("$.webHookId").value(DEFAULT_WEB_HOOK_ID.intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTagsWebHooks() throws Exception {
        // Get the tagsWebHooks
        restTagsWebHooksMockMvc.perform(get("/api/tags-web-hooks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagsWebHooks() throws Exception {
        // Initialize the database
        tagsWebHooksRepository.saveAndFlush(tagsWebHooks);

        int databaseSizeBeforeUpdate = tagsWebHooksRepository.findAll().size();

        // Update the tagsWebHooks
        TagsWebHooks updatedTagsWebHooks = tagsWebHooksRepository.findById(tagsWebHooks.getId()).get();
        // Disconnect from session so that the updates on updatedTagsWebHooks are not directly saved in db
        em.detach(updatedTagsWebHooks);
        updatedTagsWebHooks
            .webHookId(UPDATED_WEB_HOOK_ID)
            .tagId(UPDATED_TAG_ID);
        TagsWebHooksDTO tagsWebHooksDTO = tagsWebHooksMapper.toDto(updatedTagsWebHooks);

        restTagsWebHooksMockMvc.perform(put("/api/tags-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagsWebHooksDTO)))
            .andExpect(status().isOk());

        // Validate the TagsWebHooks in the database
        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeUpdate);
        TagsWebHooks testTagsWebHooks = tagsWebHooksList.get(tagsWebHooksList.size() - 1);
        assertThat(testTagsWebHooks.getWebHookId()).isEqualTo(UPDATED_WEB_HOOK_ID);
        assertThat(testTagsWebHooks.getTagId()).isEqualTo(UPDATED_TAG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTagsWebHooks() throws Exception {
        int databaseSizeBeforeUpdate = tagsWebHooksRepository.findAll().size();

        // Create the TagsWebHooks
        TagsWebHooksDTO tagsWebHooksDTO = tagsWebHooksMapper.toDto(tagsWebHooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagsWebHooksMockMvc.perform(put("/api/tags-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagsWebHooks in the database
        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagsWebHooks() throws Exception {
        // Initialize the database
        tagsWebHooksRepository.saveAndFlush(tagsWebHooks);

        int databaseSizeBeforeDelete = tagsWebHooksRepository.findAll().size();

        // Delete the tagsWebHooks
        restTagsWebHooksMockMvc.perform(delete("/api/tags-web-hooks/{id}", tagsWebHooks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagsWebHooks> tagsWebHooksList = tagsWebHooksRepository.findAll();
        assertThat(tagsWebHooksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
