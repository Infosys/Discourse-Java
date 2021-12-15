/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.InvitedGroups;
import com.infy.repository.InvitedGroupsRepository;
import com.infy.service.InvitedGroupsService;
import com.infy.service.dto.InvitedGroupsDTO;
import com.infy.service.mapper.InvitedGroupsMapper;

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
 * Integration tests for the {@link InvitedGroupsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InvitedGroupsResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_INVITE_ID = 1L;
    private static final Long UPDATED_INVITE_ID = 2L;

    @Autowired
    private InvitedGroupsRepository invitedGroupsRepository;

    @Autowired
    private InvitedGroupsMapper invitedGroupsMapper;

    @Autowired
    private InvitedGroupsService invitedGroupsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitedGroupsMockMvc;

    private InvitedGroups invitedGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitedGroups createEntity(EntityManager em) {
        InvitedGroups invitedGroups = new InvitedGroups()
            .groupId(DEFAULT_GROUP_ID)
            .inviteId(DEFAULT_INVITE_ID);
        return invitedGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitedGroups createUpdatedEntity(EntityManager em) {
        InvitedGroups invitedGroups = new InvitedGroups()
            .groupId(UPDATED_GROUP_ID)
            .inviteId(UPDATED_INVITE_ID);
        return invitedGroups;
    }

    @BeforeEach
    public void initTest() {
        invitedGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitedGroups() throws Exception {
        int databaseSizeBeforeCreate = invitedGroupsRepository.findAll().size();
        // Create the InvitedGroups
        InvitedGroupsDTO invitedGroupsDTO = invitedGroupsMapper.toDto(invitedGroups);
        restInvitedGroupsMockMvc.perform(post("/api/invited-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the InvitedGroups in the database
        List<InvitedGroups> invitedGroupsList = invitedGroupsRepository.findAll();
        assertThat(invitedGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        InvitedGroups testInvitedGroups = invitedGroupsList.get(invitedGroupsList.size() - 1);
        assertThat(testInvitedGroups.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testInvitedGroups.getInviteId()).isEqualTo(DEFAULT_INVITE_ID);
    }

    @Test
    @Transactional
    public void createInvitedGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitedGroupsRepository.findAll().size();

        // Create the InvitedGroups with an existing ID
        invitedGroups.setId(1L);
        InvitedGroupsDTO invitedGroupsDTO = invitedGroupsMapper.toDto(invitedGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitedGroupsMockMvc.perform(post("/api/invited-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvitedGroups in the database
        List<InvitedGroups> invitedGroupsList = invitedGroupsRepository.findAll();
        assertThat(invitedGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvitedGroups() throws Exception {
        // Initialize the database
        invitedGroupsRepository.saveAndFlush(invitedGroups);

        // Get all the invitedGroupsList
        restInvitedGroupsMockMvc.perform(get("/api/invited-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitedGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].inviteId").value(hasItem(DEFAULT_INVITE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getInvitedGroups() throws Exception {
        // Initialize the database
        invitedGroupsRepository.saveAndFlush(invitedGroups);

        // Get the invitedGroups
        restInvitedGroupsMockMvc.perform(get("/api/invited-groups/{id}", invitedGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invitedGroups.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.inviteId").value(DEFAULT_INVITE_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInvitedGroups() throws Exception {
        // Get the invitedGroups
        restInvitedGroupsMockMvc.perform(get("/api/invited-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitedGroups() throws Exception {
        // Initialize the database
        invitedGroupsRepository.saveAndFlush(invitedGroups);

        int databaseSizeBeforeUpdate = invitedGroupsRepository.findAll().size();

        // Update the invitedGroups
        InvitedGroups updatedInvitedGroups = invitedGroupsRepository.findById(invitedGroups.getId()).get();
        // Disconnect from session so that the updates on updatedInvitedGroups are not directly saved in db
        em.detach(updatedInvitedGroups);
        updatedInvitedGroups
            .groupId(UPDATED_GROUP_ID)
            .inviteId(UPDATED_INVITE_ID);
        InvitedGroupsDTO invitedGroupsDTO = invitedGroupsMapper.toDto(updatedInvitedGroups);

        restInvitedGroupsMockMvc.perform(put("/api/invited-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the InvitedGroups in the database
        List<InvitedGroups> invitedGroupsList = invitedGroupsRepository.findAll();
        assertThat(invitedGroupsList).hasSize(databaseSizeBeforeUpdate);
        InvitedGroups testInvitedGroups = invitedGroupsList.get(invitedGroupsList.size() - 1);
        assertThat(testInvitedGroups.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testInvitedGroups.getInviteId()).isEqualTo(UPDATED_INVITE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitedGroups() throws Exception {
        int databaseSizeBeforeUpdate = invitedGroupsRepository.findAll().size();

        // Create the InvitedGroups
        InvitedGroupsDTO invitedGroupsDTO = invitedGroupsMapper.toDto(invitedGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitedGroupsMockMvc.perform(put("/api/invited-groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvitedGroups in the database
        List<InvitedGroups> invitedGroupsList = invitedGroupsRepository.findAll();
        assertThat(invitedGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvitedGroups() throws Exception {
        // Initialize the database
        invitedGroupsRepository.saveAndFlush(invitedGroups);

        int databaseSizeBeforeDelete = invitedGroupsRepository.findAll().size();

        // Delete the invitedGroups
        restInvitedGroupsMockMvc.perform(delete("/api/invited-groups/{id}", invitedGroups.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvitedGroups> invitedGroupsList = invitedGroupsRepository.findAll();
        assertThat(invitedGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
