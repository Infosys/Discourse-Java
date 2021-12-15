/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.IncomingLinks;
import com.infy.repository.IncomingLinksRepository;
import com.infy.service.IncomingLinksService;
import com.infy.service.dto.IncomingLinksDTO;
import com.infy.service.mapper.IncomingLinksMapper;

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
 * Integration tests for the {@link IncomingLinksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IncomingLinksResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Long DEFAULT_INCOMING_REFERER_ID = 1L;
    private static final Long UPDATED_INCOMING_REFERER_ID = 2L;

    @Autowired
    private IncomingLinksRepository incomingLinksRepository;

    @Autowired
    private IncomingLinksMapper incomingLinksMapper;

    @Autowired
    private IncomingLinksService incomingLinksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomingLinksMockMvc;

    private IncomingLinks incomingLinks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingLinks createEntity(EntityManager em) {
        IncomingLinks incomingLinks = new IncomingLinks()
            .userId(DEFAULT_USER_ID)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .currentUserId(DEFAULT_CURRENT_USER_ID)
            .postId(DEFAULT_POST_ID)
            .incomingRefererId(DEFAULT_INCOMING_REFERER_ID);
        return incomingLinks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingLinks createUpdatedEntity(EntityManager em) {
        IncomingLinks incomingLinks = new IncomingLinks()
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS)
            .currentUserId(UPDATED_CURRENT_USER_ID)
            .postId(UPDATED_POST_ID)
            .incomingRefererId(UPDATED_INCOMING_REFERER_ID);
        return incomingLinks;
    }

    @BeforeEach
    public void initTest() {
        incomingLinks = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomingLinks() throws Exception {
        int databaseSizeBeforeCreate = incomingLinksRepository.findAll().size();
        // Create the IncomingLinks
        IncomingLinksDTO incomingLinksDTO = incomingLinksMapper.toDto(incomingLinks);
        restIncomingLinksMockMvc.perform(post("/api/incoming-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingLinksDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomingLinks in the database
        List<IncomingLinks> incomingLinksList = incomingLinksRepository.findAll();
        assertThat(incomingLinksList).hasSize(databaseSizeBeforeCreate + 1);
        IncomingLinks testIncomingLinks = incomingLinksList.get(incomingLinksList.size() - 1);
        assertThat(testIncomingLinks.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testIncomingLinks.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testIncomingLinks.getCurrentUserId()).isEqualTo(DEFAULT_CURRENT_USER_ID);
        assertThat(testIncomingLinks.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testIncomingLinks.getIncomingRefererId()).isEqualTo(DEFAULT_INCOMING_REFERER_ID);
    }

    @Test
    @Transactional
    public void createIncomingLinksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomingLinksRepository.findAll().size();

        // Create the IncomingLinks with an existing ID
        incomingLinks.setId(1L);
        IncomingLinksDTO incomingLinksDTO = incomingLinksMapper.toDto(incomingLinks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomingLinksMockMvc.perform(post("/api/incoming-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingLinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingLinks in the database
        List<IncomingLinks> incomingLinksList = incomingLinksRepository.findAll();
        assertThat(incomingLinksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingLinksRepository.findAll().size();
        // set the field null
        incomingLinks.setPostId(null);

        // Create the IncomingLinks, which fails.
        IncomingLinksDTO incomingLinksDTO = incomingLinksMapper.toDto(incomingLinks);


        restIncomingLinksMockMvc.perform(post("/api/incoming-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingLinksDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingLinks> incomingLinksList = incomingLinksRepository.findAll();
        assertThat(incomingLinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomingLinks() throws Exception {
        // Initialize the database
        incomingLinksRepository.saveAndFlush(incomingLinks);

        // Get all the incomingLinksList
        restIncomingLinksMockMvc.perform(get("/api/incoming-links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomingLinks.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].currentUserId").value(hasItem(DEFAULT_CURRENT_USER_ID)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].incomingRefererId").value(hasItem(DEFAULT_INCOMING_REFERER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getIncomingLinks() throws Exception {
        // Initialize the database
        incomingLinksRepository.saveAndFlush(incomingLinks);

        // Get the incomingLinks
        restIncomingLinksMockMvc.perform(get("/api/incoming-links/{id}", incomingLinks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomingLinks.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.currentUserId").value(DEFAULT_CURRENT_USER_ID))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.incomingRefererId").value(DEFAULT_INCOMING_REFERER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingIncomingLinks() throws Exception {
        // Get the incomingLinks
        restIncomingLinksMockMvc.perform(get("/api/incoming-links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomingLinks() throws Exception {
        // Initialize the database
        incomingLinksRepository.saveAndFlush(incomingLinks);

        int databaseSizeBeforeUpdate = incomingLinksRepository.findAll().size();

        // Update the incomingLinks
        IncomingLinks updatedIncomingLinks = incomingLinksRepository.findById(incomingLinks.getId()).get();
        // Disconnect from session so that the updates on updatedIncomingLinks are not directly saved in db
        em.detach(updatedIncomingLinks);
        updatedIncomingLinks
            .userId(UPDATED_USER_ID)
            .ipAddress(UPDATED_IP_ADDRESS)
            .currentUserId(UPDATED_CURRENT_USER_ID)
            .postId(UPDATED_POST_ID)
            .incomingRefererId(UPDATED_INCOMING_REFERER_ID);
        IncomingLinksDTO incomingLinksDTO = incomingLinksMapper.toDto(updatedIncomingLinks);

        restIncomingLinksMockMvc.perform(put("/api/incoming-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingLinksDTO)))
            .andExpect(status().isOk());

        // Validate the IncomingLinks in the database
        List<IncomingLinks> incomingLinksList = incomingLinksRepository.findAll();
        assertThat(incomingLinksList).hasSize(databaseSizeBeforeUpdate);
        IncomingLinks testIncomingLinks = incomingLinksList.get(incomingLinksList.size() - 1);
        assertThat(testIncomingLinks.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testIncomingLinks.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testIncomingLinks.getCurrentUserId()).isEqualTo(UPDATED_CURRENT_USER_ID);
        assertThat(testIncomingLinks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testIncomingLinks.getIncomingRefererId()).isEqualTo(UPDATED_INCOMING_REFERER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomingLinks() throws Exception {
        int databaseSizeBeforeUpdate = incomingLinksRepository.findAll().size();

        // Create the IncomingLinks
        IncomingLinksDTO incomingLinksDTO = incomingLinksMapper.toDto(incomingLinks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomingLinksMockMvc.perform(put("/api/incoming-links").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingLinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingLinks in the database
        List<IncomingLinks> incomingLinksList = incomingLinksRepository.findAll();
        assertThat(incomingLinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomingLinks() throws Exception {
        // Initialize the database
        incomingLinksRepository.saveAndFlush(incomingLinks);

        int databaseSizeBeforeDelete = incomingLinksRepository.findAll().size();

        // Delete the incomingLinks
        restIncomingLinksMockMvc.perform(delete("/api/incoming-links/{id}", incomingLinks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncomingLinks> incomingLinksList = incomingLinksRepository.findAll();
        assertThat(incomingLinksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
