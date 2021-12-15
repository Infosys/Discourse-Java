/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PollOptions;
import com.infy.repository.PollOptionsRepository;
import com.infy.service.PollOptionsService;
import com.infy.service.dto.PollOptionsDTO;
import com.infy.service.mapper.PollOptionsMapper;

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
 * Integration tests for the {@link PollOptionsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PollOptionsResourceIT {

    private static final Long DEFAULT_POLL_ID = 1L;
    private static final Long UPDATED_POLL_ID = 2L;

    private static final String DEFAULT_DIGEST = "AAAAAAAAAA";
    private static final String UPDATED_DIGEST = "BBBBBBBBBB";

    private static final String DEFAULT_HTML = "AAAAAAAAAA";
    private static final String UPDATED_HTML = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANONYMOUS_VOTES = 1;
    private static final Integer UPDATED_ANONYMOUS_VOTES = 2;

    @Autowired
    private PollOptionsRepository pollOptionsRepository;

    @Autowired
    private PollOptionsMapper pollOptionsMapper;

    @Autowired
    private PollOptionsService pollOptionsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPollOptionsMockMvc;

    private PollOptions pollOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollOptions createEntity(EntityManager em) {
        PollOptions pollOptions = new PollOptions()
            .pollId(DEFAULT_POLL_ID)
            .digest(DEFAULT_DIGEST)
            .html(DEFAULT_HTML)
            .anonymousVotes(DEFAULT_ANONYMOUS_VOTES);
        return pollOptions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollOptions createUpdatedEntity(EntityManager em) {
        PollOptions pollOptions = new PollOptions()
            .pollId(UPDATED_POLL_ID)
            .digest(UPDATED_DIGEST)
            .html(UPDATED_HTML)
            .anonymousVotes(UPDATED_ANONYMOUS_VOTES);
        return pollOptions;
    }

    @BeforeEach
    public void initTest() {
        pollOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPollOptions() throws Exception {
        int databaseSizeBeforeCreate = pollOptionsRepository.findAll().size();
        // Create the PollOptions
        PollOptionsDTO pollOptionsDTO = pollOptionsMapper.toDto(pollOptions);
        restPollOptionsMockMvc.perform(post("/api/poll-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollOptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the PollOptions in the database
        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        PollOptions testPollOptions = pollOptionsList.get(pollOptionsList.size() - 1);
        assertThat(testPollOptions.getPollId()).isEqualTo(DEFAULT_POLL_ID);
        assertThat(testPollOptions.getDigest()).isEqualTo(DEFAULT_DIGEST);
        assertThat(testPollOptions.getHtml()).isEqualTo(DEFAULT_HTML);
        assertThat(testPollOptions.getAnonymousVotes()).isEqualTo(DEFAULT_ANONYMOUS_VOTES);
    }

    @Test
    @Transactional
    public void createPollOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollOptionsRepository.findAll().size();

        // Create the PollOptions with an existing ID
        pollOptions.setId(1L);
        PollOptionsDTO pollOptionsDTO = pollOptionsMapper.toDto(pollOptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollOptionsMockMvc.perform(post("/api/poll-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PollOptions in the database
        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDigestIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollOptionsRepository.findAll().size();
        // set the field null
        pollOptions.setDigest(null);

        // Create the PollOptions, which fails.
        PollOptionsDTO pollOptionsDTO = pollOptionsMapper.toDto(pollOptions);


        restPollOptionsMockMvc.perform(post("/api/poll-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHtmlIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollOptionsRepository.findAll().size();
        // set the field null
        pollOptions.setHtml(null);

        // Create the PollOptions, which fails.
        PollOptionsDTO pollOptionsDTO = pollOptionsMapper.toDto(pollOptions);


        restPollOptionsMockMvc.perform(post("/api/poll-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPollOptions() throws Exception {
        // Initialize the database
        pollOptionsRepository.saveAndFlush(pollOptions);

        // Get all the pollOptionsList
        restPollOptionsMockMvc.perform(get("/api/poll-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pollOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].pollId").value(hasItem(DEFAULT_POLL_ID.intValue())))
            .andExpect(jsonPath("$.[*].digest").value(hasItem(DEFAULT_DIGEST)))
            .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML)))
            .andExpect(jsonPath("$.[*].anonymousVotes").value(hasItem(DEFAULT_ANONYMOUS_VOTES)));
    }

    @Test
    @Transactional
    public void getPollOptions() throws Exception {
        // Initialize the database
        pollOptionsRepository.saveAndFlush(pollOptions);

        // Get the pollOptions
        restPollOptionsMockMvc.perform(get("/api/poll-options/{id}", pollOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pollOptions.getId().intValue()))
            .andExpect(jsonPath("$.pollId").value(DEFAULT_POLL_ID.intValue()))
            .andExpect(jsonPath("$.digest").value(DEFAULT_DIGEST))
            .andExpect(jsonPath("$.html").value(DEFAULT_HTML))
            .andExpect(jsonPath("$.anonymousVotes").value(DEFAULT_ANONYMOUS_VOTES));
    }
    @Test
    @Transactional
    public void getNonExistingPollOptions() throws Exception {
        // Get the pollOptions
        restPollOptionsMockMvc.perform(get("/api/poll-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePollOptions() throws Exception {
        // Initialize the database
        pollOptionsRepository.saveAndFlush(pollOptions);

        int databaseSizeBeforeUpdate = pollOptionsRepository.findAll().size();

        // Update the pollOptions
        PollOptions updatedPollOptions = pollOptionsRepository.findById(pollOptions.getId()).get();
        // Disconnect from session so that the updates on updatedPollOptions are not directly saved in db
        em.detach(updatedPollOptions);
        updatedPollOptions
            .pollId(UPDATED_POLL_ID)
            .digest(UPDATED_DIGEST)
            .html(UPDATED_HTML)
            .anonymousVotes(UPDATED_ANONYMOUS_VOTES);
        PollOptionsDTO pollOptionsDTO = pollOptionsMapper.toDto(updatedPollOptions);

        restPollOptionsMockMvc.perform(put("/api/poll-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollOptionsDTO)))
            .andExpect(status().isOk());

        // Validate the PollOptions in the database
        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeUpdate);
        PollOptions testPollOptions = pollOptionsList.get(pollOptionsList.size() - 1);
        assertThat(testPollOptions.getPollId()).isEqualTo(UPDATED_POLL_ID);
        assertThat(testPollOptions.getDigest()).isEqualTo(UPDATED_DIGEST);
        assertThat(testPollOptions.getHtml()).isEqualTo(UPDATED_HTML);
        assertThat(testPollOptions.getAnonymousVotes()).isEqualTo(UPDATED_ANONYMOUS_VOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingPollOptions() throws Exception {
        int databaseSizeBeforeUpdate = pollOptionsRepository.findAll().size();

        // Create the PollOptions
        PollOptionsDTO pollOptionsDTO = pollOptionsMapper.toDto(pollOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPollOptionsMockMvc.perform(put("/api/poll-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PollOptions in the database
        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePollOptions() throws Exception {
        // Initialize the database
        pollOptionsRepository.saveAndFlush(pollOptions);

        int databaseSizeBeforeDelete = pollOptionsRepository.findAll().size();

        // Delete the pollOptions
        restPollOptionsMockMvc.perform(delete("/api/poll-options/{id}", pollOptions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PollOptions> pollOptionsList = pollOptionsRepository.findAll();
        assertThat(pollOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
