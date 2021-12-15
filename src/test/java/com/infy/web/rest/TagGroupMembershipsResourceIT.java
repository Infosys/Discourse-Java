/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TagGroupMemberships;
import com.infy.repository.TagGroupMembershipsRepository;
import com.infy.service.TagGroupMembershipsService;
import com.infy.service.dto.TagGroupMembershipsDTO;
import com.infy.service.mapper.TagGroupMembershipsMapper;

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
 * Integration tests for the {@link TagGroupMembershipsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TagGroupMembershipsResourceIT {

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    private static final Long DEFAULT_TAG_GROUP_ID = 1L;
    private static final Long UPDATED_TAG_GROUP_ID = 2L;

    @Autowired
    private TagGroupMembershipsRepository tagGroupMembershipsRepository;

    @Autowired
    private TagGroupMembershipsMapper tagGroupMembershipsMapper;

    @Autowired
    private TagGroupMembershipsService tagGroupMembershipsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagGroupMembershipsMockMvc;

    private TagGroupMemberships tagGroupMemberships;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagGroupMemberships createEntity(EntityManager em) {
        TagGroupMemberships tagGroupMemberships = new TagGroupMemberships()
            .tagId(DEFAULT_TAG_ID)
            .tagGroupId(DEFAULT_TAG_GROUP_ID);
        return tagGroupMemberships;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagGroupMemberships createUpdatedEntity(EntityManager em) {
        TagGroupMemberships tagGroupMemberships = new TagGroupMemberships()
            .tagId(UPDATED_TAG_ID)
            .tagGroupId(UPDATED_TAG_GROUP_ID);
        return tagGroupMemberships;
    }

    @BeforeEach
    public void initTest() {
        tagGroupMemberships = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagGroupMemberships() throws Exception {
        int databaseSizeBeforeCreate = tagGroupMembershipsRepository.findAll().size();
        // Create the TagGroupMemberships
        TagGroupMembershipsDTO tagGroupMembershipsDTO = tagGroupMembershipsMapper.toDto(tagGroupMemberships);
        restTagGroupMembershipsMockMvc.perform(post("/api/tag-group-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupMembershipsDTO)))
            .andExpect(status().isCreated());

        // Validate the TagGroupMemberships in the database
        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeCreate + 1);
        TagGroupMemberships testTagGroupMemberships = tagGroupMembershipsList.get(tagGroupMembershipsList.size() - 1);
        assertThat(testTagGroupMemberships.getTagId()).isEqualTo(DEFAULT_TAG_ID);
        assertThat(testTagGroupMemberships.getTagGroupId()).isEqualTo(DEFAULT_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void createTagGroupMembershipsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagGroupMembershipsRepository.findAll().size();

        // Create the TagGroupMemberships with an existing ID
        tagGroupMemberships.setId(1L);
        TagGroupMembershipsDTO tagGroupMembershipsDTO = tagGroupMembershipsMapper.toDto(tagGroupMemberships);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagGroupMembershipsMockMvc.perform(post("/api/tag-group-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupMembershipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagGroupMemberships in the database
        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagGroupMembershipsRepository.findAll().size();
        // set the field null
        tagGroupMemberships.setTagId(null);

        // Create the TagGroupMemberships, which fails.
        TagGroupMembershipsDTO tagGroupMembershipsDTO = tagGroupMembershipsMapper.toDto(tagGroupMemberships);


        restTagGroupMembershipsMockMvc.perform(post("/api/tag-group-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupMembershipsDTO)))
            .andExpect(status().isBadRequest());

        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagGroupMembershipsRepository.findAll().size();
        // set the field null
        tagGroupMemberships.setTagGroupId(null);

        // Create the TagGroupMemberships, which fails.
        TagGroupMembershipsDTO tagGroupMembershipsDTO = tagGroupMembershipsMapper.toDto(tagGroupMemberships);


        restTagGroupMembershipsMockMvc.perform(post("/api/tag-group-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupMembershipsDTO)))
            .andExpect(status().isBadRequest());

        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagGroupMemberships() throws Exception {
        // Initialize the database
        tagGroupMembershipsRepository.saveAndFlush(tagGroupMemberships);

        // Get all the tagGroupMembershipsList
        restTagGroupMembershipsMockMvc.perform(get("/api/tag-group-memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagGroupMemberships.getId().intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())))
            .andExpect(jsonPath("$.[*].tagGroupId").value(hasItem(DEFAULT_TAG_GROUP_ID.intValue())));
    }

    @Test
    @Transactional
    public void getTagGroupMemberships() throws Exception {
        // Initialize the database
        tagGroupMembershipsRepository.saveAndFlush(tagGroupMemberships);

        // Get the tagGroupMemberships
        restTagGroupMembershipsMockMvc.perform(get("/api/tag-group-memberships/{id}", tagGroupMemberships.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagGroupMemberships.getId().intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()))
            .andExpect(jsonPath("$.tagGroupId").value(DEFAULT_TAG_GROUP_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTagGroupMemberships() throws Exception {
        // Get the tagGroupMemberships
        restTagGroupMembershipsMockMvc.perform(get("/api/tag-group-memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagGroupMemberships() throws Exception {
        // Initialize the database
        tagGroupMembershipsRepository.saveAndFlush(tagGroupMemberships);

        int databaseSizeBeforeUpdate = tagGroupMembershipsRepository.findAll().size();

        // Update the tagGroupMemberships
        TagGroupMemberships updatedTagGroupMemberships = tagGroupMembershipsRepository.findById(tagGroupMemberships.getId()).get();
        // Disconnect from session so that the updates on updatedTagGroupMemberships are not directly saved in db
        em.detach(updatedTagGroupMemberships);
        updatedTagGroupMemberships
            .tagId(UPDATED_TAG_ID)
            .tagGroupId(UPDATED_TAG_GROUP_ID);
        TagGroupMembershipsDTO tagGroupMembershipsDTO = tagGroupMembershipsMapper.toDto(updatedTagGroupMemberships);

        restTagGroupMembershipsMockMvc.perform(put("/api/tag-group-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupMembershipsDTO)))
            .andExpect(status().isOk());

        // Validate the TagGroupMemberships in the database
        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeUpdate);
        TagGroupMemberships testTagGroupMemberships = tagGroupMembershipsList.get(tagGroupMembershipsList.size() - 1);
        assertThat(testTagGroupMemberships.getTagId()).isEqualTo(UPDATED_TAG_ID);
        assertThat(testTagGroupMemberships.getTagGroupId()).isEqualTo(UPDATED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTagGroupMemberships() throws Exception {
        int databaseSizeBeforeUpdate = tagGroupMembershipsRepository.findAll().size();

        // Create the TagGroupMemberships
        TagGroupMembershipsDTO tagGroupMembershipsDTO = tagGroupMembershipsMapper.toDto(tagGroupMemberships);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagGroupMembershipsMockMvc.perform(put("/api/tag-group-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagGroupMembershipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagGroupMemberships in the database
        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagGroupMemberships() throws Exception {
        // Initialize the database
        tagGroupMembershipsRepository.saveAndFlush(tagGroupMemberships);

        int databaseSizeBeforeDelete = tagGroupMembershipsRepository.findAll().size();

        // Delete the tagGroupMemberships
        restTagGroupMembershipsMockMvc.perform(delete("/api/tag-group-memberships/{id}", tagGroupMemberships.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagGroupMemberships> tagGroupMembershipsList = tagGroupMembershipsRepository.findAll();
        assertThat(tagGroupMembershipsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
