/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Invites;
import com.infy.repository.InvitesRepository;
import com.infy.service.InvitesService;
import com.infy.service.dto.InvitesDTO;
import com.infy.service.mapper.InvitesMapper;

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
 * Integration tests for the {@link InvitesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InvitesResourceIT {

    private static final String DEFAULT_INVITE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_INVITE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_INVITED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_INVITED_BY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_REDEEMED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REDEEMED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DELETED_BY_ID = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_INVALIDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVALIDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_MODERATOR = false;
    private static final Boolean UPDATED_MODERATOR = true;

    private static final String DEFAULT_CUSTOM_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EMAILED_STATUS = 1;
    private static final Integer UPDATED_EMAILED_STATUS = 2;

    private static final Integer DEFAULT_MAX_REDEMPTIONS_ALLOWED = 1;
    private static final Integer UPDATED_MAX_REDEMPTIONS_ALLOWED = 2;

    private static final Integer DEFAULT_REDEMPTION_COUNT = 1;
    private static final Integer UPDATED_REDEMPTION_COUNT = 2;

    private static final Instant DEFAULT_EXPIRES_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRES_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_TOKEN = "BBBBBBBBBB";

    @Autowired
    private InvitesRepository invitesRepository;

    @Autowired
    private InvitesMapper invitesMapper;

    @Autowired
    private InvitesService invitesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitesMockMvc;

    private Invites invites;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invites createEntity(EntityManager em) {
        Invites invites = new Invites()
            .inviteKey(DEFAULT_INVITE_KEY)
            .email(DEFAULT_EMAIL)
            .invitedById(DEFAULT_INVITED_BY_ID)
            .userId(DEFAULT_USER_ID)
            .redeemedAt(DEFAULT_REDEEMED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .deletedById(DEFAULT_DELETED_BY_ID)
            .invalidatedAt(DEFAULT_INVALIDATED_AT)
            .moderator(DEFAULT_MODERATOR)
            .customMessage(DEFAULT_CUSTOM_MESSAGE)
            .emailedStatus(DEFAULT_EMAILED_STATUS)
            .maxRedemptionsAllowed(DEFAULT_MAX_REDEMPTIONS_ALLOWED)
            .redemptionCount(DEFAULT_REDEMPTION_COUNT)
            .expiresAt(DEFAULT_EXPIRES_AT)
            .emailToken(DEFAULT_EMAIL_TOKEN);
        return invites;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invites createUpdatedEntity(EntityManager em) {
        Invites invites = new Invites()
            .inviteKey(UPDATED_INVITE_KEY)
            .email(UPDATED_EMAIL)
            .invitedById(UPDATED_INVITED_BY_ID)
            .userId(UPDATED_USER_ID)
            .redeemedAt(UPDATED_REDEEMED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID)
            .invalidatedAt(UPDATED_INVALIDATED_AT)
            .moderator(UPDATED_MODERATOR)
            .customMessage(UPDATED_CUSTOM_MESSAGE)
            .emailedStatus(UPDATED_EMAILED_STATUS)
            .maxRedemptionsAllowed(UPDATED_MAX_REDEMPTIONS_ALLOWED)
            .redemptionCount(UPDATED_REDEMPTION_COUNT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .emailToken(UPDATED_EMAIL_TOKEN);
        return invites;
    }

    @BeforeEach
    public void initTest() {
        invites = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvites() throws Exception {
        int databaseSizeBeforeCreate = invitesRepository.findAll().size();
        // Create the Invites
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);
        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invites in the database
        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeCreate + 1);
        Invites testInvites = invitesList.get(invitesList.size() - 1);
        assertThat(testInvites.getInviteKey()).isEqualTo(DEFAULT_INVITE_KEY);
        assertThat(testInvites.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInvites.getInvitedById()).isEqualTo(DEFAULT_INVITED_BY_ID);
        assertThat(testInvites.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testInvites.getRedeemedAt()).isEqualTo(DEFAULT_REDEEMED_AT);
        assertThat(testInvites.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testInvites.getDeletedById()).isEqualTo(DEFAULT_DELETED_BY_ID);
        assertThat(testInvites.getInvalidatedAt()).isEqualTo(DEFAULT_INVALIDATED_AT);
        assertThat(testInvites.isModerator()).isEqualTo(DEFAULT_MODERATOR);
        assertThat(testInvites.getCustomMessage()).isEqualTo(DEFAULT_CUSTOM_MESSAGE);
        assertThat(testInvites.getEmailedStatus()).isEqualTo(DEFAULT_EMAILED_STATUS);
        assertThat(testInvites.getMaxRedemptionsAllowed()).isEqualTo(DEFAULT_MAX_REDEMPTIONS_ALLOWED);
        assertThat(testInvites.getRedemptionCount()).isEqualTo(DEFAULT_REDEMPTION_COUNT);
        assertThat(testInvites.getExpiresAt()).isEqualTo(DEFAULT_EXPIRES_AT);
        assertThat(testInvites.getEmailToken()).isEqualTo(DEFAULT_EMAIL_TOKEN);
    }

    @Test
    @Transactional
    public void createInvitesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitesRepository.findAll().size();

        // Create the Invites with an existing ID
        invites.setId(1L);
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invites in the database
        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInviteKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitesRepository.findAll().size();
        // set the field null
        invites.setInviteKey(null);

        // Create the Invites, which fails.
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);


        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvitedByIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitesRepository.findAll().size();
        // set the field null
        invites.setInvitedById(null);

        // Create the Invites, which fails.
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);


        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeratorIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitesRepository.findAll().size();
        // set the field null
        invites.setModerator(null);

        // Create the Invites, which fails.
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);


        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxRedemptionsAllowedIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitesRepository.findAll().size();
        // set the field null
        invites.setMaxRedemptionsAllowed(null);

        // Create the Invites, which fails.
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);


        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRedemptionCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitesRepository.findAll().size();
        // set the field null
        invites.setRedemptionCount(null);

        // Create the Invites, which fails.
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);


        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiresAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitesRepository.findAll().size();
        // set the field null
        invites.setExpiresAt(null);

        // Create the Invites, which fails.
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);


        restInvitesMockMvc.perform(post("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvites() throws Exception {
        // Initialize the database
        invitesRepository.saveAndFlush(invites);

        // Get all the invitesList
        restInvitesMockMvc.perform(get("/api/invites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invites.getId().intValue())))
            .andExpect(jsonPath("$.[*].inviteKey").value(hasItem(DEFAULT_INVITE_KEY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].invitedById").value(hasItem(DEFAULT_INVITED_BY_ID)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].redeemedAt").value(hasItem(DEFAULT_REDEEMED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedById").value(hasItem(DEFAULT_DELETED_BY_ID)))
            .andExpect(jsonPath("$.[*].invalidatedAt").value(hasItem(DEFAULT_INVALIDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].moderator").value(hasItem(DEFAULT_MODERATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].customMessage").value(hasItem(DEFAULT_CUSTOM_MESSAGE)))
            .andExpect(jsonPath("$.[*].emailedStatus").value(hasItem(DEFAULT_EMAILED_STATUS)))
            .andExpect(jsonPath("$.[*].maxRedemptionsAllowed").value(hasItem(DEFAULT_MAX_REDEMPTIONS_ALLOWED)))
            .andExpect(jsonPath("$.[*].redemptionCount").value(hasItem(DEFAULT_REDEMPTION_COUNT)))
            .andExpect(jsonPath("$.[*].expiresAt").value(hasItem(DEFAULT_EXPIRES_AT.toString())))
            .andExpect(jsonPath("$.[*].emailToken").value(hasItem(DEFAULT_EMAIL_TOKEN)));
    }

    @Test
    @Transactional
    public void getInvites() throws Exception {
        // Initialize the database
        invitesRepository.saveAndFlush(invites);

        // Get the invites
        restInvitesMockMvc.perform(get("/api/invites/{id}", invites.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invites.getId().intValue()))
            .andExpect(jsonPath("$.inviteKey").value(DEFAULT_INVITE_KEY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.invitedById").value(DEFAULT_INVITED_BY_ID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.redeemedAt").value(DEFAULT_REDEEMED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.deletedById").value(DEFAULT_DELETED_BY_ID))
            .andExpect(jsonPath("$.invalidatedAt").value(DEFAULT_INVALIDATED_AT.toString()))
            .andExpect(jsonPath("$.moderator").value(DEFAULT_MODERATOR.booleanValue()))
            .andExpect(jsonPath("$.customMessage").value(DEFAULT_CUSTOM_MESSAGE))
            .andExpect(jsonPath("$.emailedStatus").value(DEFAULT_EMAILED_STATUS))
            .andExpect(jsonPath("$.maxRedemptionsAllowed").value(DEFAULT_MAX_REDEMPTIONS_ALLOWED))
            .andExpect(jsonPath("$.redemptionCount").value(DEFAULT_REDEMPTION_COUNT))
            .andExpect(jsonPath("$.expiresAt").value(DEFAULT_EXPIRES_AT.toString()))
            .andExpect(jsonPath("$.emailToken").value(DEFAULT_EMAIL_TOKEN));
    }
    @Test
    @Transactional
    public void getNonExistingInvites() throws Exception {
        // Get the invites
        restInvitesMockMvc.perform(get("/api/invites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvites() throws Exception {
        // Initialize the database
        invitesRepository.saveAndFlush(invites);

        int databaseSizeBeforeUpdate = invitesRepository.findAll().size();

        // Update the invites
        Invites updatedInvites = invitesRepository.findById(invites.getId()).get();
        // Disconnect from session so that the updates on updatedInvites are not directly saved in db
        em.detach(updatedInvites);
        updatedInvites
            .inviteKey(UPDATED_INVITE_KEY)
            .email(UPDATED_EMAIL)
            .invitedById(UPDATED_INVITED_BY_ID)
            .userId(UPDATED_USER_ID)
            .redeemedAt(UPDATED_REDEEMED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedById(UPDATED_DELETED_BY_ID)
            .invalidatedAt(UPDATED_INVALIDATED_AT)
            .moderator(UPDATED_MODERATOR)
            .customMessage(UPDATED_CUSTOM_MESSAGE)
            .emailedStatus(UPDATED_EMAILED_STATUS)
            .maxRedemptionsAllowed(UPDATED_MAX_REDEMPTIONS_ALLOWED)
            .redemptionCount(UPDATED_REDEMPTION_COUNT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .emailToken(UPDATED_EMAIL_TOKEN);
        InvitesDTO invitesDTO = invitesMapper.toDto(updatedInvites);

        restInvitesMockMvc.perform(put("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isOk());

        // Validate the Invites in the database
        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeUpdate);
        Invites testInvites = invitesList.get(invitesList.size() - 1);
        assertThat(testInvites.getInviteKey()).isEqualTo(UPDATED_INVITE_KEY);
        assertThat(testInvites.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvites.getInvitedById()).isEqualTo(UPDATED_INVITED_BY_ID);
        assertThat(testInvites.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInvites.getRedeemedAt()).isEqualTo(UPDATED_REDEEMED_AT);
        assertThat(testInvites.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testInvites.getDeletedById()).isEqualTo(UPDATED_DELETED_BY_ID);
        assertThat(testInvites.getInvalidatedAt()).isEqualTo(UPDATED_INVALIDATED_AT);
        assertThat(testInvites.isModerator()).isEqualTo(UPDATED_MODERATOR);
        assertThat(testInvites.getCustomMessage()).isEqualTo(UPDATED_CUSTOM_MESSAGE);
        assertThat(testInvites.getEmailedStatus()).isEqualTo(UPDATED_EMAILED_STATUS);
        assertThat(testInvites.getMaxRedemptionsAllowed()).isEqualTo(UPDATED_MAX_REDEMPTIONS_ALLOWED);
        assertThat(testInvites.getRedemptionCount()).isEqualTo(UPDATED_REDEMPTION_COUNT);
        assertThat(testInvites.getExpiresAt()).isEqualTo(UPDATED_EXPIRES_AT);
        assertThat(testInvites.getEmailToken()).isEqualTo(UPDATED_EMAIL_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingInvites() throws Exception {
        int databaseSizeBeforeUpdate = invitesRepository.findAll().size();

        // Create the Invites
        InvitesDTO invitesDTO = invitesMapper.toDto(invites);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitesMockMvc.perform(put("/api/invites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invites in the database
        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvites() throws Exception {
        // Initialize the database
        invitesRepository.saveAndFlush(invites);

        int databaseSizeBeforeDelete = invitesRepository.findAll().size();

        // Delete the invites
        restInvitesMockMvc.perform(delete("/api/invites/{id}", invites.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invites> invitesList = invitesRepository.findAll();
        assertThat(invitesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
