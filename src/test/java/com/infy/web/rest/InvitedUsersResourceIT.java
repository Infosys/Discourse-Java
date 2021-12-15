/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.InvitedUsers;
import com.infy.repository.InvitedUsersRepository;
import com.infy.service.InvitedUsersService;
import com.infy.service.dto.InvitedUsersDTO;
import com.infy.service.mapper.InvitedUsersMapper;

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
 * Integration tests for the {@link InvitedUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InvitedUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_INVITE_ID = 1L;
    private static final Long UPDATED_INVITE_ID = 2L;

    private static final Instant DEFAULT_REDEEMED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REDEEMED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InvitedUsersRepository invitedUsersRepository;

    @Autowired
    private InvitedUsersMapper invitedUsersMapper;

    @Autowired
    private InvitedUsersService invitedUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitedUsersMockMvc;

    private InvitedUsers invitedUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitedUsers createEntity(EntityManager em) {
        InvitedUsers invitedUsers = new InvitedUsers()
            .userId(DEFAULT_USER_ID)
            .inviteId(DEFAULT_INVITE_ID)
            .redeemedAt(DEFAULT_REDEEMED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return invitedUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitedUsers createUpdatedEntity(EntityManager em) {
        InvitedUsers invitedUsers = new InvitedUsers()
            .userId(UPDATED_USER_ID)
            .inviteId(UPDATED_INVITE_ID)
            .redeemedAt(UPDATED_REDEEMED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return invitedUsers;
    }

    @BeforeEach
    public void initTest() {
        invitedUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitedUsers() throws Exception {
        int databaseSizeBeforeCreate = invitedUsersRepository.findAll().size();
        // Create the InvitedUsers
        InvitedUsersDTO invitedUsersDTO = invitedUsersMapper.toDto(invitedUsers);
        restInvitedUsersMockMvc.perform(post("/api/invited-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the InvitedUsers in the database
        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeCreate + 1);
        InvitedUsers testInvitedUsers = invitedUsersList.get(invitedUsersList.size() - 1);
        assertThat(testInvitedUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testInvitedUsers.getInviteId()).isEqualTo(DEFAULT_INVITE_ID);
        assertThat(testInvitedUsers.getRedeemedAt()).isEqualTo(DEFAULT_REDEEMED_AT);
        assertThat(testInvitedUsers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createInvitedUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitedUsersRepository.findAll().size();

        // Create the InvitedUsers with an existing ID
        invitedUsers.setId(1L);
        InvitedUsersDTO invitedUsersDTO = invitedUsersMapper.toDto(invitedUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitedUsersMockMvc.perform(post("/api/invited-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvitedUsers in the database
        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInviteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitedUsersRepository.findAll().size();
        // set the field null
        invitedUsers.setInviteId(null);

        // Create the InvitedUsers, which fails.
        InvitedUsersDTO invitedUsersDTO = invitedUsersMapper.toDto(invitedUsers);


        restInvitedUsersMockMvc.perform(post("/api/invited-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedUsersDTO)))
            .andExpect(status().isBadRequest());

        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitedUsersRepository.findAll().size();
        // set the field null
        invitedUsers.setUpdatedAt(null);

        // Create the InvitedUsers, which fails.
        InvitedUsersDTO invitedUsersDTO = invitedUsersMapper.toDto(invitedUsers);


        restInvitedUsersMockMvc.perform(post("/api/invited-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedUsersDTO)))
            .andExpect(status().isBadRequest());

        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitedUsers() throws Exception {
        // Initialize the database
        invitedUsersRepository.saveAndFlush(invitedUsers);

        // Get all the invitedUsersList
        restInvitedUsersMockMvc.perform(get("/api/invited-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitedUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].inviteId").value(hasItem(DEFAULT_INVITE_ID.intValue())))
            .andExpect(jsonPath("$.[*].redeemedAt").value(hasItem(DEFAULT_REDEEMED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getInvitedUsers() throws Exception {
        // Initialize the database
        invitedUsersRepository.saveAndFlush(invitedUsers);

        // Get the invitedUsers
        restInvitedUsersMockMvc.perform(get("/api/invited-users/{id}", invitedUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invitedUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.inviteId").value(DEFAULT_INVITE_ID.intValue()))
            .andExpect(jsonPath("$.redeemedAt").value(DEFAULT_REDEEMED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInvitedUsers() throws Exception {
        // Get the invitedUsers
        restInvitedUsersMockMvc.perform(get("/api/invited-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitedUsers() throws Exception {
        // Initialize the database
        invitedUsersRepository.saveAndFlush(invitedUsers);

        int databaseSizeBeforeUpdate = invitedUsersRepository.findAll().size();

        // Update the invitedUsers
        InvitedUsers updatedInvitedUsers = invitedUsersRepository.findById(invitedUsers.getId()).get();
        // Disconnect from session so that the updates on updatedInvitedUsers are not directly saved in db
        em.detach(updatedInvitedUsers);
        updatedInvitedUsers
            .userId(UPDATED_USER_ID)
            .inviteId(UPDATED_INVITE_ID)
            .redeemedAt(UPDATED_REDEEMED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        InvitedUsersDTO invitedUsersDTO = invitedUsersMapper.toDto(updatedInvitedUsers);

        restInvitedUsersMockMvc.perform(put("/api/invited-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedUsersDTO)))
            .andExpect(status().isOk());

        // Validate the InvitedUsers in the database
        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeUpdate);
        InvitedUsers testInvitedUsers = invitedUsersList.get(invitedUsersList.size() - 1);
        assertThat(testInvitedUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInvitedUsers.getInviteId()).isEqualTo(UPDATED_INVITE_ID);
        assertThat(testInvitedUsers.getRedeemedAt()).isEqualTo(UPDATED_REDEEMED_AT);
        assertThat(testInvitedUsers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitedUsers() throws Exception {
        int databaseSizeBeforeUpdate = invitedUsersRepository.findAll().size();

        // Create the InvitedUsers
        InvitedUsersDTO invitedUsersDTO = invitedUsersMapper.toDto(invitedUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitedUsersMockMvc.perform(put("/api/invited-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitedUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvitedUsers in the database
        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvitedUsers() throws Exception {
        // Initialize the database
        invitedUsersRepository.saveAndFlush(invitedUsers);

        int databaseSizeBeforeDelete = invitedUsersRepository.findAll().size();

        // Delete the invitedUsers
        restInvitedUsersMockMvc.perform(delete("/api/invited-users/{id}", invitedUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvitedUsers> invitedUsersList = invitedUsersRepository.findAll();
        assertThat(invitedUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
