/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupsWebHooks;
import com.infy.repository.GroupsWebHooksRepository;
import com.infy.service.GroupsWebHooksService;
import com.infy.service.dto.GroupsWebHooksDTO;
import com.infy.service.mapper.GroupsWebHooksMapper;

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
 * Integration tests for the {@link GroupsWebHooksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupsWebHooksResourceIT {

    private static final Long DEFAULT_WEB_HOOK_ID = 1L;
    private static final Long UPDATED_WEB_HOOK_ID = 2L;

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    @Autowired
    private GroupsWebHooksRepository groupsWebHooksRepository;

    @Autowired
    private GroupsWebHooksMapper groupsWebHooksMapper;

    @Autowired
    private GroupsWebHooksService groupsWebHooksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupsWebHooksMockMvc;

    private GroupsWebHooks groupsWebHooks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupsWebHooks createEntity(EntityManager em) {
        GroupsWebHooks groupsWebHooks = new GroupsWebHooks()
            .webHookId(DEFAULT_WEB_HOOK_ID)
            .groupId(DEFAULT_GROUP_ID);
        return groupsWebHooks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupsWebHooks createUpdatedEntity(EntityManager em) {
        GroupsWebHooks groupsWebHooks = new GroupsWebHooks()
            .webHookId(UPDATED_WEB_HOOK_ID)
            .groupId(UPDATED_GROUP_ID);
        return groupsWebHooks;
    }

    @BeforeEach
    public void initTest() {
        groupsWebHooks = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupsWebHooks() throws Exception {
        int databaseSizeBeforeCreate = groupsWebHooksRepository.findAll().size();
        // Create the GroupsWebHooks
        GroupsWebHooksDTO groupsWebHooksDTO = groupsWebHooksMapper.toDto(groupsWebHooks);
        restGroupsWebHooksMockMvc.perform(post("/api/groups-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsWebHooksDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupsWebHooks in the database
        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeCreate + 1);
        GroupsWebHooks testGroupsWebHooks = groupsWebHooksList.get(groupsWebHooksList.size() - 1);
        assertThat(testGroupsWebHooks.getWebHookId()).isEqualTo(DEFAULT_WEB_HOOK_ID);
        assertThat(testGroupsWebHooks.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
    }

    @Test
    @Transactional
    public void createGroupsWebHooksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupsWebHooksRepository.findAll().size();

        // Create the GroupsWebHooks with an existing ID
        groupsWebHooks.setId(1L);
        GroupsWebHooksDTO groupsWebHooksDTO = groupsWebHooksMapper.toDto(groupsWebHooks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupsWebHooksMockMvc.perform(post("/api/groups-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupsWebHooks in the database
        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWebHookIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsWebHooksRepository.findAll().size();
        // set the field null
        groupsWebHooks.setWebHookId(null);

        // Create the GroupsWebHooks, which fails.
        GroupsWebHooksDTO groupsWebHooksDTO = groupsWebHooksMapper.toDto(groupsWebHooks);


        restGroupsWebHooksMockMvc.perform(post("/api/groups-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsWebHooksRepository.findAll().size();
        // set the field null
        groupsWebHooks.setGroupId(null);

        // Create the GroupsWebHooks, which fails.
        GroupsWebHooksDTO groupsWebHooksDTO = groupsWebHooksMapper.toDto(groupsWebHooks);


        restGroupsWebHooksMockMvc.perform(post("/api/groups-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupsWebHooks() throws Exception {
        // Initialize the database
        groupsWebHooksRepository.saveAndFlush(groupsWebHooks);

        // Get all the groupsWebHooksList
        restGroupsWebHooksMockMvc.perform(get("/api/groups-web-hooks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupsWebHooks.getId().intValue())))
            .andExpect(jsonPath("$.[*].webHookId").value(hasItem(DEFAULT_WEB_HOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())));
    }

    @Test
    @Transactional
    public void getGroupsWebHooks() throws Exception {
        // Initialize the database
        groupsWebHooksRepository.saveAndFlush(groupsWebHooks);

        // Get the groupsWebHooks
        restGroupsWebHooksMockMvc.perform(get("/api/groups-web-hooks/{id}", groupsWebHooks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupsWebHooks.getId().intValue()))
            .andExpect(jsonPath("$.webHookId").value(DEFAULT_WEB_HOOK_ID.intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupsWebHooks() throws Exception {
        // Get the groupsWebHooks
        restGroupsWebHooksMockMvc.perform(get("/api/groups-web-hooks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupsWebHooks() throws Exception {
        // Initialize the database
        groupsWebHooksRepository.saveAndFlush(groupsWebHooks);

        int databaseSizeBeforeUpdate = groupsWebHooksRepository.findAll().size();

        // Update the groupsWebHooks
        GroupsWebHooks updatedGroupsWebHooks = groupsWebHooksRepository.findById(groupsWebHooks.getId()).get();
        // Disconnect from session so that the updates on updatedGroupsWebHooks are not directly saved in db
        em.detach(updatedGroupsWebHooks);
        updatedGroupsWebHooks
            .webHookId(UPDATED_WEB_HOOK_ID)
            .groupId(UPDATED_GROUP_ID);
        GroupsWebHooksDTO groupsWebHooksDTO = groupsWebHooksMapper.toDto(updatedGroupsWebHooks);

        restGroupsWebHooksMockMvc.perform(put("/api/groups-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsWebHooksDTO)))
            .andExpect(status().isOk());

        // Validate the GroupsWebHooks in the database
        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeUpdate);
        GroupsWebHooks testGroupsWebHooks = groupsWebHooksList.get(groupsWebHooksList.size() - 1);
        assertThat(testGroupsWebHooks.getWebHookId()).isEqualTo(UPDATED_WEB_HOOK_ID);
        assertThat(testGroupsWebHooks.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupsWebHooks() throws Exception {
        int databaseSizeBeforeUpdate = groupsWebHooksRepository.findAll().size();

        // Create the GroupsWebHooks
        GroupsWebHooksDTO groupsWebHooksDTO = groupsWebHooksMapper.toDto(groupsWebHooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupsWebHooksMockMvc.perform(put("/api/groups-web-hooks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupsWebHooksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupsWebHooks in the database
        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupsWebHooks() throws Exception {
        // Initialize the database
        groupsWebHooksRepository.saveAndFlush(groupsWebHooks);

        int databaseSizeBeforeDelete = groupsWebHooksRepository.findAll().size();

        // Delete the groupsWebHooks
        restGroupsWebHooksMockMvc.perform(delete("/api/groups-web-hooks/{id}", groupsWebHooks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupsWebHooks> groupsWebHooksList = groupsWebHooksRepository.findAll();
        assertThat(groupsWebHooksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
