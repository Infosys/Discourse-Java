/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupMentions;
import com.infy.repository.GroupMentionsRepository;
import com.infy.service.GroupMentionsService;
import com.infy.service.dto.GroupMentionsDTO;
import com.infy.service.mapper.GroupMentionsMapper;

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
 * Integration tests for the {@link GroupMentionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupMentionsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GroupMentionsRepository groupMentionsRepository;

    @Autowired
    private GroupMentionsMapper groupMentionsMapper;

    @Autowired
    private GroupMentionsService groupMentionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupMentionsMockMvc;

    private GroupMentions groupMentions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMentions createEntity(EntityManager em) {
        GroupMentions groupMentions = new GroupMentions()
            .postId(DEFAULT_POST_ID)
            .groupId(DEFAULT_GROUP_ID)
            .updatedAt(DEFAULT_UPDATED_AT);
        return groupMentions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMentions createUpdatedEntity(EntityManager em) {
        GroupMentions groupMentions = new GroupMentions()
            .postId(UPDATED_POST_ID)
            .groupId(UPDATED_GROUP_ID)
            .updatedAt(UPDATED_UPDATED_AT);
        return groupMentions;
    }

    @BeforeEach
    public void initTest() {
        groupMentions = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupMentions() throws Exception {
        int databaseSizeBeforeCreate = groupMentionsRepository.findAll().size();
        // Create the GroupMentions
        GroupMentionsDTO groupMentionsDTO = groupMentionsMapper.toDto(groupMentions);
        restGroupMentionsMockMvc.perform(post("/api/group-mentions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMentionsDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupMentions in the database
        List<GroupMentions> groupMentionsList = groupMentionsRepository.findAll();
        assertThat(groupMentionsList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMentions testGroupMentions = groupMentionsList.get(groupMentionsList.size() - 1);
        assertThat(testGroupMentions.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testGroupMentions.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupMentions.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createGroupMentionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupMentionsRepository.findAll().size();

        // Create the GroupMentions with an existing ID
        groupMentions.setId(1L);
        GroupMentionsDTO groupMentionsDTO = groupMentionsMapper.toDto(groupMentions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMentionsMockMvc.perform(post("/api/group-mentions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMentionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMentions in the database
        List<GroupMentions> groupMentionsList = groupMentionsRepository.findAll();
        assertThat(groupMentionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupMentionsRepository.findAll().size();
        // set the field null
        groupMentions.setUpdatedAt(null);

        // Create the GroupMentions, which fails.
        GroupMentionsDTO groupMentionsDTO = groupMentionsMapper.toDto(groupMentions);


        restGroupMentionsMockMvc.perform(post("/api/group-mentions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMentionsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupMentions> groupMentionsList = groupMentionsRepository.findAll();
        assertThat(groupMentionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupMentions() throws Exception {
        // Initialize the database
        groupMentionsRepository.saveAndFlush(groupMentions);

        // Get all the groupMentionsList
        restGroupMentionsMockMvc.perform(get("/api/group-mentions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMentions.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getGroupMentions() throws Exception {
        // Initialize the database
        groupMentionsRepository.saveAndFlush(groupMentions);

        // Get the groupMentions
        restGroupMentionsMockMvc.perform(get("/api/group-mentions/{id}", groupMentions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupMentions.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupMentions() throws Exception {
        // Get the groupMentions
        restGroupMentionsMockMvc.perform(get("/api/group-mentions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupMentions() throws Exception {
        // Initialize the database
        groupMentionsRepository.saveAndFlush(groupMentions);

        int databaseSizeBeforeUpdate = groupMentionsRepository.findAll().size();

        // Update the groupMentions
        GroupMentions updatedGroupMentions = groupMentionsRepository.findById(groupMentions.getId()).get();
        // Disconnect from session so that the updates on updatedGroupMentions are not directly saved in db
        em.detach(updatedGroupMentions);
        updatedGroupMentions
            .postId(UPDATED_POST_ID)
            .groupId(UPDATED_GROUP_ID)
            .updatedAt(UPDATED_UPDATED_AT);
        GroupMentionsDTO groupMentionsDTO = groupMentionsMapper.toDto(updatedGroupMentions);

        restGroupMentionsMockMvc.perform(put("/api/group-mentions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMentionsDTO)))
            .andExpect(status().isOk());

        // Validate the GroupMentions in the database
        List<GroupMentions> groupMentionsList = groupMentionsRepository.findAll();
        assertThat(groupMentionsList).hasSize(databaseSizeBeforeUpdate);
        GroupMentions testGroupMentions = groupMentionsList.get(groupMentionsList.size() - 1);
        assertThat(testGroupMentions.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testGroupMentions.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupMentions.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupMentions() throws Exception {
        int databaseSizeBeforeUpdate = groupMentionsRepository.findAll().size();

        // Create the GroupMentions
        GroupMentionsDTO groupMentionsDTO = groupMentionsMapper.toDto(groupMentions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMentionsMockMvc.perform(put("/api/group-mentions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMentionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMentions in the database
        List<GroupMentions> groupMentionsList = groupMentionsRepository.findAll();
        assertThat(groupMentionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupMentions() throws Exception {
        // Initialize the database
        groupMentionsRepository.saveAndFlush(groupMentions);

        int databaseSizeBeforeDelete = groupMentionsRepository.findAll().size();

        // Delete the groupMentions
        restGroupMentionsMockMvc.perform(delete("/api/group-mentions/{id}", groupMentions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupMentions> groupMentionsList = groupMentionsRepository.findAll();
        assertThat(groupMentionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
