/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TagGroups;
import com.infy.repository.TagGroupsRepository;
import com.infy.service.TagGroupsService;
import com.infy.service.dto.TagGroupsDTO;
import com.infy.service.mapper.TagGroupsMapper;

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
 * Integration tests for the {@link TagGroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TagGroupsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_TAG_ID = 1L;
    private static final Long UPDATED_PARENT_TAG_ID = 2L;

    private static final Boolean DEFAULT_ONE_PER_TOPIC = false;
    private static final Boolean UPDATED_ONE_PER_TOPIC = true;

    @Autowired
    private TagGroupsRepository tagGroupsRepository;

    @Autowired
    private TagGroupsMapper tagGroupsMapper;

    @Autowired
    private TagGroupsService tagGroupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagGroupsMockMvc;

    private TagGroups tagGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagGroups createEntity(EntityManager em) {
        TagGroups tagGroups = new TagGroups()
            .name(DEFAULT_NAME)
            .parentTagId(DEFAULT_PARENT_TAG_ID)
            .onePerTopic(DEFAULT_ONE_PER_TOPIC);
        return tagGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagGroups createUpdatedEntity(EntityManager em) {
        TagGroups tagGroups = new TagGroups()
            .name(UPDATED_NAME)
            .parentTagId(UPDATED_PARENT_TAG_ID)
            .onePerTopic(UPDATED_ONE_PER_TOPIC);
        return tagGroups;
    }

    @BeforeEach
    public void initTest() {
        tagGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagGroups() throws Exception {
        int databaseSizeBeforeCreate = tagGroupsRepository.findAll().size();
        // Create the TagGroups
        TagGroupsDTO tagGroupsDTO = tagGroupsMapper.toDto(tagGroups);
        restTagGroupsMockMvc.perform(post("/api/tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the TagGroups in the database
        List<TagGroups> tagGroupsList = tagGroupsRepository.findAll();
        assertThat(tagGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        TagGroups testTagGroups = tagGroupsList.get(tagGroupsList.size() - 1);
        assertThat(testTagGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTagGroups.getParentTagId()).isEqualTo(DEFAULT_PARENT_TAG_ID);
        assertThat(testTagGroups.isOnePerTopic()).isEqualTo(DEFAULT_ONE_PER_TOPIC);
    }

    @Test
    @Transactional
    public void createTagGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagGroupsRepository.findAll().size();

        // Create the TagGroups with an existing ID
        tagGroups.setId(1L);
        TagGroupsDTO tagGroupsDTO = tagGroupsMapper.toDto(tagGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagGroupsMockMvc.perform(post("/api/tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagGroups in the database
        List<TagGroups> tagGroupsList = tagGroupsRepository.findAll();
        assertThat(tagGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagGroupsRepository.findAll().size();
        // set the field null
        tagGroups.setName(null);

        // Create the TagGroups, which fails.
        TagGroupsDTO tagGroupsDTO = tagGroupsMapper.toDto(tagGroups);


        restTagGroupsMockMvc.perform(post("/api/tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<TagGroups> tagGroupsList = tagGroupsRepository.findAll();
        assertThat(tagGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagGroups() throws Exception {
        // Initialize the database
        tagGroupsRepository.saveAndFlush(tagGroups);

        // Get all the tagGroupsList
        restTagGroupsMockMvc.perform(get("/api/tag-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].parentTagId").value(hasItem(DEFAULT_PARENT_TAG_ID.intValue())))
            .andExpect(jsonPath("$.[*].onePerTopic").value(hasItem(DEFAULT_ONE_PER_TOPIC.booleanValue())));
    }

    @Test
    @Transactional
    public void getTagGroups() throws Exception {
        // Initialize the database
        tagGroupsRepository.saveAndFlush(tagGroups);

        // Get the tagGroups
        restTagGroupsMockMvc.perform(get("/api/tag-groups/{id}", tagGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagGroups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.parentTagId").value(DEFAULT_PARENT_TAG_ID.intValue()))
            .andExpect(jsonPath("$.onePerTopic").value(DEFAULT_ONE_PER_TOPIC.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTagGroups() throws Exception {
        // Get the tagGroups
        restTagGroupsMockMvc.perform(get("/api/tag-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagGroups() throws Exception {
        // Initialize the database
        tagGroupsRepository.saveAndFlush(tagGroups);

        int databaseSizeBeforeUpdate = tagGroupsRepository.findAll().size();

        // Update the tagGroups
        TagGroups updatedTagGroups = tagGroupsRepository.findById(tagGroups.getId()).get();
        // Disconnect from session so that the updates on updatedTagGroups are not directly saved in db
        em.detach(updatedTagGroups);
        updatedTagGroups
            .name(UPDATED_NAME)
            .parentTagId(UPDATED_PARENT_TAG_ID)
            .onePerTopic(UPDATED_ONE_PER_TOPIC);
        TagGroupsDTO tagGroupsDTO = tagGroupsMapper.toDto(updatedTagGroups);

        restTagGroupsMockMvc.perform(put("/api/tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the TagGroups in the database
        List<TagGroups> tagGroupsList = tagGroupsRepository.findAll();
        assertThat(tagGroupsList).hasSize(databaseSizeBeforeUpdate);
        TagGroups testTagGroups = tagGroupsList.get(tagGroupsList.size() - 1);
        assertThat(testTagGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTagGroups.getParentTagId()).isEqualTo(UPDATED_PARENT_TAG_ID);
        assertThat(testTagGroups.isOnePerTopic()).isEqualTo(UPDATED_ONE_PER_TOPIC);
    }

    @Test
    @Transactional
    public void updateNonExistingTagGroups() throws Exception {
        int databaseSizeBeforeUpdate = tagGroupsRepository.findAll().size();

        // Create the TagGroups
        TagGroupsDTO tagGroupsDTO = tagGroupsMapper.toDto(tagGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagGroupsMockMvc.perform(put("/api/tag-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagGroups in the database
        List<TagGroups> tagGroupsList = tagGroupsRepository.findAll();
        assertThat(tagGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagGroups() throws Exception {
        // Initialize the database
        tagGroupsRepository.saveAndFlush(tagGroups);

        int databaseSizeBeforeDelete = tagGroupsRepository.findAll().size();

        // Delete the tagGroups
        restTagGroupsMockMvc.perform(delete("/api/tag-groups/{id}", tagGroups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagGroups> tagGroupsList = tagGroupsRepository.findAll();
        assertThat(tagGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
