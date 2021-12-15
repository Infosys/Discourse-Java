/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PollVotes;
import com.infy.repository.PollVotesRepository;
import com.infy.service.PollVotesService;
import com.infy.service.dto.PollVotesDTO;
import com.infy.service.mapper.PollVotesMapper;

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
 * Integration tests for the {@link PollVotesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PollVotesResourceIT {

    private static final Long DEFAULT_POLL_ID = 1L;
    private static final Long UPDATED_POLL_ID = 2L;

    private static final Long DEFAULT_POLL_OPTION_ID = 1L;
    private static final Long UPDATED_POLL_OPTION_ID = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private PollVotesRepository pollVotesRepository;

    @Autowired
    private PollVotesMapper pollVotesMapper;

    @Autowired
    private PollVotesService pollVotesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPollVotesMockMvc;

    private PollVotes pollVotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollVotes createEntity(EntityManager em) {
        PollVotes pollVotes = new PollVotes()
            .pollId(DEFAULT_POLL_ID)
            .pollOptionId(DEFAULT_POLL_OPTION_ID)
            .userId(DEFAULT_USER_ID);
        return pollVotes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollVotes createUpdatedEntity(EntityManager em) {
        PollVotes pollVotes = new PollVotes()
            .pollId(UPDATED_POLL_ID)
            .pollOptionId(UPDATED_POLL_OPTION_ID)
            .userId(UPDATED_USER_ID);
        return pollVotes;
    }

    @BeforeEach
    public void initTest() {
        pollVotes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPollVotes() throws Exception {
        int databaseSizeBeforeCreate = pollVotesRepository.findAll().size();
        // Create the PollVotes
        PollVotesDTO pollVotesDTO = pollVotesMapper.toDto(pollVotes);
        restPollVotesMockMvc.perform(post("/api/poll-votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollVotesDTO)))
            .andExpect(status().isCreated());

        // Validate the PollVotes in the database
        List<PollVotes> pollVotesList = pollVotesRepository.findAll();
        assertThat(pollVotesList).hasSize(databaseSizeBeforeCreate + 1);
        PollVotes testPollVotes = pollVotesList.get(pollVotesList.size() - 1);
        assertThat(testPollVotes.getPollId()).isEqualTo(DEFAULT_POLL_ID);
        assertThat(testPollVotes.getPollOptionId()).isEqualTo(DEFAULT_POLL_OPTION_ID);
        assertThat(testPollVotes.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createPollVotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollVotesRepository.findAll().size();

        // Create the PollVotes with an existing ID
        pollVotes.setId(1L);
        PollVotesDTO pollVotesDTO = pollVotesMapper.toDto(pollVotes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollVotesMockMvc.perform(post("/api/poll-votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollVotesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PollVotes in the database
        List<PollVotes> pollVotesList = pollVotesRepository.findAll();
        assertThat(pollVotesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPollVotes() throws Exception {
        // Initialize the database
        pollVotesRepository.saveAndFlush(pollVotes);

        // Get all the pollVotesList
        restPollVotesMockMvc.perform(get("/api/poll-votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pollVotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].pollId").value(hasItem(DEFAULT_POLL_ID.intValue())))
            .andExpect(jsonPath("$.[*].pollOptionId").value(hasItem(DEFAULT_POLL_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPollVotes() throws Exception {
        // Initialize the database
        pollVotesRepository.saveAndFlush(pollVotes);

        // Get the pollVotes
        restPollVotesMockMvc.perform(get("/api/poll-votes/{id}", pollVotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pollVotes.getId().intValue()))
            .andExpect(jsonPath("$.pollId").value(DEFAULT_POLL_ID.intValue()))
            .andExpect(jsonPath("$.pollOptionId").value(DEFAULT_POLL_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPollVotes() throws Exception {
        // Get the pollVotes
        restPollVotesMockMvc.perform(get("/api/poll-votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePollVotes() throws Exception {
        // Initialize the database
        pollVotesRepository.saveAndFlush(pollVotes);

        int databaseSizeBeforeUpdate = pollVotesRepository.findAll().size();

        // Update the pollVotes
        PollVotes updatedPollVotes = pollVotesRepository.findById(pollVotes.getId()).get();
        // Disconnect from session so that the updates on updatedPollVotes are not directly saved in db
        em.detach(updatedPollVotes);
        updatedPollVotes
            .pollId(UPDATED_POLL_ID)
            .pollOptionId(UPDATED_POLL_OPTION_ID)
            .userId(UPDATED_USER_ID);
        PollVotesDTO pollVotesDTO = pollVotesMapper.toDto(updatedPollVotes);

        restPollVotesMockMvc.perform(put("/api/poll-votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollVotesDTO)))
            .andExpect(status().isOk());

        // Validate the PollVotes in the database
        List<PollVotes> pollVotesList = pollVotesRepository.findAll();
        assertThat(pollVotesList).hasSize(databaseSizeBeforeUpdate);
        PollVotes testPollVotes = pollVotesList.get(pollVotesList.size() - 1);
        assertThat(testPollVotes.getPollId()).isEqualTo(UPDATED_POLL_ID);
        assertThat(testPollVotes.getPollOptionId()).isEqualTo(UPDATED_POLL_OPTION_ID);
        assertThat(testPollVotes.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPollVotes() throws Exception {
        int databaseSizeBeforeUpdate = pollVotesRepository.findAll().size();

        // Create the PollVotes
        PollVotesDTO pollVotesDTO = pollVotesMapper.toDto(pollVotes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPollVotesMockMvc.perform(put("/api/poll-votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollVotesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PollVotes in the database
        List<PollVotes> pollVotesList = pollVotesRepository.findAll();
        assertThat(pollVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePollVotes() throws Exception {
        // Initialize the database
        pollVotesRepository.saveAndFlush(pollVotes);

        int databaseSizeBeforeDelete = pollVotesRepository.findAll().size();

        // Delete the pollVotes
        restPollVotesMockMvc.perform(delete("/api/poll-votes/{id}", pollVotes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PollVotes> pollVotesList = pollVotesRepository.findAll();
        assertThat(pollVotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
