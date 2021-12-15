/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Polls;
import com.infy.repository.PollsRepository;
import com.infy.service.PollsService;
import com.infy.service.dto.PollsDTO;
import com.infy.service.mapper.PollsMapper;

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
 * Integration tests for the {@link PollsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PollsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CLOSE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_RESULTS = 1;
    private static final Integer UPDATED_RESULTS = 2;

    private static final Integer DEFAULT_VISIBILITY = 1;
    private static final Integer UPDATED_VISIBILITY = 2;

    private static final Integer DEFAULT_MIN = 1;
    private static final Integer UPDATED_MIN = 2;

    private static final Integer DEFAULT_MAX = 1;
    private static final Integer UPDATED_MAX = 2;

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    private static final Integer DEFAULT_ANONYMOUS_VOTERS = 1;
    private static final Integer UPDATED_ANONYMOUS_VOTERS = 2;

    private static final Integer DEFAULT_CHART_TYPE = 1;
    private static final Integer UPDATED_CHART_TYPE = 2;

    private static final String DEFAULT_GROUPS = "AAAAAAAAAA";
    private static final String UPDATED_GROUPS = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private PollsRepository pollsRepository;

    @Autowired
    private PollsMapper pollsMapper;

    @Autowired
    private PollsService pollsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPollsMockMvc;

    private Polls polls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Polls createEntity(EntityManager em) {
        Polls polls = new Polls()
            .postId(DEFAULT_POST_ID)
            .name(DEFAULT_NAME)
            .closeAt(DEFAULT_CLOSE_AT)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .results(DEFAULT_RESULTS)
            .visibility(DEFAULT_VISIBILITY)
            .min(DEFAULT_MIN)
            .max(DEFAULT_MAX)
            .step(DEFAULT_STEP)
            .anonymousVoters(DEFAULT_ANONYMOUS_VOTERS)
            .chartType(DEFAULT_CHART_TYPE)
            .groups(DEFAULT_GROUPS)
            .title(DEFAULT_TITLE);
        return polls;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Polls createUpdatedEntity(EntityManager em) {
        Polls polls = new Polls()
            .postId(UPDATED_POST_ID)
            .name(UPDATED_NAME)
            .closeAt(UPDATED_CLOSE_AT)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .results(UPDATED_RESULTS)
            .visibility(UPDATED_VISIBILITY)
            .min(UPDATED_MIN)
            .max(UPDATED_MAX)
            .step(UPDATED_STEP)
            .anonymousVoters(UPDATED_ANONYMOUS_VOTERS)
            .chartType(UPDATED_CHART_TYPE)
            .groups(UPDATED_GROUPS)
            .title(UPDATED_TITLE);
        return polls;
    }

    @BeforeEach
    public void initTest() {
        polls = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolls() throws Exception {
        int databaseSizeBeforeCreate = pollsRepository.findAll().size();
        // Create the Polls
        PollsDTO pollsDTO = pollsMapper.toDto(polls);
        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isCreated());

        // Validate the Polls in the database
        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeCreate + 1);
        Polls testPolls = pollsList.get(pollsList.size() - 1);
        assertThat(testPolls.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPolls.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolls.getCloseAt()).isEqualTo(DEFAULT_CLOSE_AT);
        assertThat(testPolls.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPolls.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolls.getResults()).isEqualTo(DEFAULT_RESULTS);
        assertThat(testPolls.getVisibility()).isEqualTo(DEFAULT_VISIBILITY);
        assertThat(testPolls.getMin()).isEqualTo(DEFAULT_MIN);
        assertThat(testPolls.getMax()).isEqualTo(DEFAULT_MAX);
        assertThat(testPolls.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testPolls.getAnonymousVoters()).isEqualTo(DEFAULT_ANONYMOUS_VOTERS);
        assertThat(testPolls.getChartType()).isEqualTo(DEFAULT_CHART_TYPE);
        assertThat(testPolls.getGroups()).isEqualTo(DEFAULT_GROUPS);
        assertThat(testPolls.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createPollsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollsRepository.findAll().size();

        // Create the Polls with an existing ID
        polls.setId(1L);
        PollsDTO pollsDTO = pollsMapper.toDto(polls);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Polls in the database
        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollsRepository.findAll().size();
        // set the field null
        polls.setName(null);

        // Create the Polls, which fails.
        PollsDTO pollsDTO = pollsMapper.toDto(polls);


        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollsRepository.findAll().size();
        // set the field null
        polls.setType(null);

        // Create the Polls, which fails.
        PollsDTO pollsDTO = pollsMapper.toDto(polls);


        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollsRepository.findAll().size();
        // set the field null
        polls.setStatus(null);

        // Create the Polls, which fails.
        PollsDTO pollsDTO = pollsMapper.toDto(polls);


        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultsIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollsRepository.findAll().size();
        // set the field null
        polls.setResults(null);

        // Create the Polls, which fails.
        PollsDTO pollsDTO = pollsMapper.toDto(polls);


        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollsRepository.findAll().size();
        // set the field null
        polls.setVisibility(null);

        // Create the Polls, which fails.
        PollsDTO pollsDTO = pollsMapper.toDto(polls);


        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChartTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollsRepository.findAll().size();
        // set the field null
        polls.setChartType(null);

        // Create the Polls, which fails.
        PollsDTO pollsDTO = pollsMapper.toDto(polls);


        restPollsMockMvc.perform(post("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolls() throws Exception {
        // Initialize the database
        pollsRepository.saveAndFlush(polls);

        // Get all the pollsList
        restPollsMockMvc.perform(get("/api/polls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(polls.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].closeAt").value(hasItem(DEFAULT_CLOSE_AT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].results").value(hasItem(DEFAULT_RESULTS)))
            .andExpect(jsonPath("$.[*].visibility").value(hasItem(DEFAULT_VISIBILITY)))
            .andExpect(jsonPath("$.[*].min").value(hasItem(DEFAULT_MIN)))
            .andExpect(jsonPath("$.[*].max").value(hasItem(DEFAULT_MAX)))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)))
            .andExpect(jsonPath("$.[*].anonymousVoters").value(hasItem(DEFAULT_ANONYMOUS_VOTERS)))
            .andExpect(jsonPath("$.[*].chartType").value(hasItem(DEFAULT_CHART_TYPE)))
            .andExpect(jsonPath("$.[*].groups").value(hasItem(DEFAULT_GROUPS)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    public void getPolls() throws Exception {
        // Initialize the database
        pollsRepository.saveAndFlush(polls);

        // Get the polls
        restPollsMockMvc.perform(get("/api/polls/{id}", polls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(polls.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.closeAt").value(DEFAULT_CLOSE_AT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.results").value(DEFAULT_RESULTS))
            .andExpect(jsonPath("$.visibility").value(DEFAULT_VISIBILITY))
            .andExpect(jsonPath("$.min").value(DEFAULT_MIN))
            .andExpect(jsonPath("$.max").value(DEFAULT_MAX))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP))
            .andExpect(jsonPath("$.anonymousVoters").value(DEFAULT_ANONYMOUS_VOTERS))
            .andExpect(jsonPath("$.chartType").value(DEFAULT_CHART_TYPE))
            .andExpect(jsonPath("$.groups").value(DEFAULT_GROUPS))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }
    @Test
    @Transactional
    public void getNonExistingPolls() throws Exception {
        // Get the polls
        restPollsMockMvc.perform(get("/api/polls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolls() throws Exception {
        // Initialize the database
        pollsRepository.saveAndFlush(polls);

        int databaseSizeBeforeUpdate = pollsRepository.findAll().size();

        // Update the polls
        Polls updatedPolls = pollsRepository.findById(polls.getId()).get();
        // Disconnect from session so that the updates on updatedPolls are not directly saved in db
        em.detach(updatedPolls);
        updatedPolls
            .postId(UPDATED_POST_ID)
            .name(UPDATED_NAME)
            .closeAt(UPDATED_CLOSE_AT)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .results(UPDATED_RESULTS)
            .visibility(UPDATED_VISIBILITY)
            .min(UPDATED_MIN)
            .max(UPDATED_MAX)
            .step(UPDATED_STEP)
            .anonymousVoters(UPDATED_ANONYMOUS_VOTERS)
            .chartType(UPDATED_CHART_TYPE)
            .groups(UPDATED_GROUPS)
            .title(UPDATED_TITLE);
        PollsDTO pollsDTO = pollsMapper.toDto(updatedPolls);

        restPollsMockMvc.perform(put("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isOk());

        // Validate the Polls in the database
        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeUpdate);
        Polls testPolls = pollsList.get(pollsList.size() - 1);
        assertThat(testPolls.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPolls.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolls.getCloseAt()).isEqualTo(UPDATED_CLOSE_AT);
        assertThat(testPolls.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPolls.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolls.getResults()).isEqualTo(UPDATED_RESULTS);
        assertThat(testPolls.getVisibility()).isEqualTo(UPDATED_VISIBILITY);
        assertThat(testPolls.getMin()).isEqualTo(UPDATED_MIN);
        assertThat(testPolls.getMax()).isEqualTo(UPDATED_MAX);
        assertThat(testPolls.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testPolls.getAnonymousVoters()).isEqualTo(UPDATED_ANONYMOUS_VOTERS);
        assertThat(testPolls.getChartType()).isEqualTo(UPDATED_CHART_TYPE);
        assertThat(testPolls.getGroups()).isEqualTo(UPDATED_GROUPS);
        assertThat(testPolls.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingPolls() throws Exception {
        int databaseSizeBeforeUpdate = pollsRepository.findAll().size();

        // Create the Polls
        PollsDTO pollsDTO = pollsMapper.toDto(polls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPollsMockMvc.perform(put("/api/polls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Polls in the database
        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolls() throws Exception {
        // Initialize the database
        pollsRepository.saveAndFlush(polls);

        int databaseSizeBeforeDelete = pollsRepository.findAll().size();

        // Delete the polls
        restPollsMockMvc.perform(delete("/api/polls/{id}", polls.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Polls> pollsList = pollsRepository.findAll();
        assertThat(pollsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
