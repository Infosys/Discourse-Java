/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupRequests;
import com.infy.repository.GroupRequestsRepository;
import com.infy.service.GroupRequestsService;
import com.infy.service.dto.GroupRequestsDTO;
import com.infy.service.mapper.GroupRequestsMapper;

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
 * Integration tests for the {@link GroupRequestsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupRequestsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GroupRequestsRepository groupRequestsRepository;

    @Autowired
    private GroupRequestsMapper groupRequestsMapper;

    @Autowired
    private GroupRequestsService groupRequestsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupRequestsMockMvc;

    private GroupRequests groupRequests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupRequests createEntity(EntityManager em) {
        GroupRequests groupRequests = new GroupRequests()
            .groupId(DEFAULT_GROUP_ID)
            .userId(DEFAULT_USER_ID)
            .reason(DEFAULT_REASON)
            .updatedAt(DEFAULT_UPDATED_AT);
        return groupRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupRequests createUpdatedEntity(EntityManager em) {
        GroupRequests groupRequests = new GroupRequests()
            .groupId(UPDATED_GROUP_ID)
            .userId(UPDATED_USER_ID)
            .reason(UPDATED_REASON)
            .updatedAt(UPDATED_UPDATED_AT);
        return groupRequests;
    }

    @BeforeEach
    public void initTest() {
        groupRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupRequests() throws Exception {
        int databaseSizeBeforeCreate = groupRequestsRepository.findAll().size();
        // Create the GroupRequests
        GroupRequestsDTO groupRequestsDTO = groupRequestsMapper.toDto(groupRequests);
        restGroupRequestsMockMvc.perform(post("/api/group-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupRequests in the database
        List<GroupRequests> groupRequestsList = groupRequestsRepository.findAll();
        assertThat(groupRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        GroupRequests testGroupRequests = groupRequestsList.get(groupRequestsList.size() - 1);
        assertThat(testGroupRequests.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupRequests.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testGroupRequests.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testGroupRequests.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createGroupRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupRequestsRepository.findAll().size();

        // Create the GroupRequests with an existing ID
        groupRequests.setId(1L);
        GroupRequestsDTO groupRequestsDTO = groupRequestsMapper.toDto(groupRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupRequestsMockMvc.perform(post("/api/group-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupRequests in the database
        List<GroupRequests> groupRequestsList = groupRequestsRepository.findAll();
        assertThat(groupRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupRequestsRepository.findAll().size();
        // set the field null
        groupRequests.setUpdatedAt(null);

        // Create the GroupRequests, which fails.
        GroupRequestsDTO groupRequestsDTO = groupRequestsMapper.toDto(groupRequests);


        restGroupRequestsMockMvc.perform(post("/api/group-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<GroupRequests> groupRequestsList = groupRequestsRepository.findAll();
        assertThat(groupRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupRequests() throws Exception {
        // Initialize the database
        groupRequestsRepository.saveAndFlush(groupRequests);

        // Get all the groupRequestsList
        restGroupRequestsMockMvc.perform(get("/api/group-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getGroupRequests() throws Exception {
        // Initialize the database
        groupRequestsRepository.saveAndFlush(groupRequests);

        // Get the groupRequests
        restGroupRequestsMockMvc.perform(get("/api/group-requests/{id}", groupRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupRequests.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupRequests() throws Exception {
        // Get the groupRequests
        restGroupRequestsMockMvc.perform(get("/api/group-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupRequests() throws Exception {
        // Initialize the database
        groupRequestsRepository.saveAndFlush(groupRequests);

        int databaseSizeBeforeUpdate = groupRequestsRepository.findAll().size();

        // Update the groupRequests
        GroupRequests updatedGroupRequests = groupRequestsRepository.findById(groupRequests.getId()).get();
        // Disconnect from session so that the updates on updatedGroupRequests are not directly saved in db
        em.detach(updatedGroupRequests);
        updatedGroupRequests
            .groupId(UPDATED_GROUP_ID)
            .userId(UPDATED_USER_ID)
            .reason(UPDATED_REASON)
            .updatedAt(UPDATED_UPDATED_AT);
        GroupRequestsDTO groupRequestsDTO = groupRequestsMapper.toDto(updatedGroupRequests);

        restGroupRequestsMockMvc.perform(put("/api/group-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the GroupRequests in the database
        List<GroupRequests> groupRequestsList = groupRequestsRepository.findAll();
        assertThat(groupRequestsList).hasSize(databaseSizeBeforeUpdate);
        GroupRequests testGroupRequests = groupRequestsList.get(groupRequestsList.size() - 1);
        assertThat(testGroupRequests.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupRequests.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testGroupRequests.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testGroupRequests.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupRequests() throws Exception {
        int databaseSizeBeforeUpdate = groupRequestsRepository.findAll().size();

        // Create the GroupRequests
        GroupRequestsDTO groupRequestsDTO = groupRequestsMapper.toDto(groupRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupRequestsMockMvc.perform(put("/api/group-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupRequests in the database
        List<GroupRequests> groupRequestsList = groupRequestsRepository.findAll();
        assertThat(groupRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupRequests() throws Exception {
        // Initialize the database
        groupRequestsRepository.saveAndFlush(groupRequests);

        int databaseSizeBeforeDelete = groupRequestsRepository.findAll().size();

        // Delete the groupRequests
        restGroupRequestsMockMvc.perform(delete("/api/group-requests/{id}", groupRequests.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupRequests> groupRequestsList = groupRequestsRepository.findAll();
        assertThat(groupRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
