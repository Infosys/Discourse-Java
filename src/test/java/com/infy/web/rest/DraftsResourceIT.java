/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Drafts;
import com.infy.repository.DraftsRepository;
import com.infy.service.DraftsService;
import com.infy.service.dto.DraftsDTO;
import com.infy.service.mapper.DraftsMapper;

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
 * Integration tests for the {@link DraftsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DraftsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DRAFT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DRAFT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Long DEFAULT_SEQUENCE = 1L;
    private static final Long UPDATED_SEQUENCE = 2L;

    private static final Integer DEFAULT_REVISIONS = 1;
    private static final Integer UPDATED_REVISIONS = 2;

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    @Autowired
    private DraftsRepository draftsRepository;

    @Autowired
    private DraftsMapper draftsMapper;

    @Autowired
    private DraftsService draftsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDraftsMockMvc;

    private Drafts drafts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drafts createEntity(EntityManager em) {
        Drafts drafts = new Drafts()
            .userId(DEFAULT_USER_ID)
            .draftKey(DEFAULT_DRAFT_KEY)
            .data(DEFAULT_DATA)
            .sequence(DEFAULT_SEQUENCE)
            .revisions(DEFAULT_REVISIONS)
            .owner(DEFAULT_OWNER);
        return drafts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drafts createUpdatedEntity(EntityManager em) {
        Drafts drafts = new Drafts()
            .userId(UPDATED_USER_ID)
            .draftKey(UPDATED_DRAFT_KEY)
            .data(UPDATED_DATA)
            .sequence(UPDATED_SEQUENCE)
            .revisions(UPDATED_REVISIONS)
            .owner(UPDATED_OWNER);
        return drafts;
    }

    @BeforeEach
    public void initTest() {
        drafts = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrafts() throws Exception {
        int databaseSizeBeforeCreate = draftsRepository.findAll().size();
        // Create the Drafts
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);
        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isCreated());

        // Validate the Drafts in the database
        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeCreate + 1);
        Drafts testDrafts = draftsList.get(draftsList.size() - 1);
        assertThat(testDrafts.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDrafts.getDraftKey()).isEqualTo(DEFAULT_DRAFT_KEY);
        assertThat(testDrafts.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDrafts.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testDrafts.getRevisions()).isEqualTo(DEFAULT_REVISIONS);
        assertThat(testDrafts.getOwner()).isEqualTo(DEFAULT_OWNER);
    }

    @Test
    @Transactional
    public void createDraftsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = draftsRepository.findAll().size();

        // Create the Drafts with an existing ID
        drafts.setId(1L);
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drafts in the database
        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftsRepository.findAll().size();
        // set the field null
        drafts.setUserId(null);

        // Create the Drafts, which fails.
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);


        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDraftKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftsRepository.findAll().size();
        // set the field null
        drafts.setDraftKey(null);

        // Create the Drafts, which fails.
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);


        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftsRepository.findAll().size();
        // set the field null
        drafts.setData(null);

        // Create the Drafts, which fails.
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);


        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftsRepository.findAll().size();
        // set the field null
        drafts.setSequence(null);

        // Create the Drafts, which fails.
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);


        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRevisionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftsRepository.findAll().size();
        // set the field null
        drafts.setRevisions(null);

        // Create the Drafts, which fails.
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);


        restDraftsMockMvc.perform(post("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrafts() throws Exception {
        // Initialize the database
        draftsRepository.saveAndFlush(drafts);

        // Get all the draftsList
        restDraftsMockMvc.perform(get("/api/drafts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drafts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].draftKey").value(hasItem(DEFAULT_DRAFT_KEY)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].revisions").value(hasItem(DEFAULT_REVISIONS)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)));
    }

    @Test
    @Transactional
    public void getDrafts() throws Exception {
        // Initialize the database
        draftsRepository.saveAndFlush(drafts);

        // Get the drafts
        restDraftsMockMvc.perform(get("/api/drafts/{id}", drafts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drafts.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.draftKey").value(DEFAULT_DRAFT_KEY))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.intValue()))
            .andExpect(jsonPath("$.revisions").value(DEFAULT_REVISIONS))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER));
    }
    @Test
    @Transactional
    public void getNonExistingDrafts() throws Exception {
        // Get the drafts
        restDraftsMockMvc.perform(get("/api/drafts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrafts() throws Exception {
        // Initialize the database
        draftsRepository.saveAndFlush(drafts);

        int databaseSizeBeforeUpdate = draftsRepository.findAll().size();

        // Update the drafts
        Drafts updatedDrafts = draftsRepository.findById(drafts.getId()).get();
        // Disconnect from session so that the updates on updatedDrafts are not directly saved in db
        em.detach(updatedDrafts);
        updatedDrafts
            .userId(UPDATED_USER_ID)
            .draftKey(UPDATED_DRAFT_KEY)
            .data(UPDATED_DATA)
            .sequence(UPDATED_SEQUENCE)
            .revisions(UPDATED_REVISIONS)
            .owner(UPDATED_OWNER);
        DraftsDTO draftsDTO = draftsMapper.toDto(updatedDrafts);

        restDraftsMockMvc.perform(put("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isOk());

        // Validate the Drafts in the database
        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeUpdate);
        Drafts testDrafts = draftsList.get(draftsList.size() - 1);
        assertThat(testDrafts.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDrafts.getDraftKey()).isEqualTo(UPDATED_DRAFT_KEY);
        assertThat(testDrafts.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDrafts.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDrafts.getRevisions()).isEqualTo(UPDATED_REVISIONS);
        assertThat(testDrafts.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void updateNonExistingDrafts() throws Exception {
        int databaseSizeBeforeUpdate = draftsRepository.findAll().size();

        // Create the Drafts
        DraftsDTO draftsDTO = draftsMapper.toDto(drafts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDraftsMockMvc.perform(put("/api/drafts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drafts in the database
        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrafts() throws Exception {
        // Initialize the database
        draftsRepository.saveAndFlush(drafts);

        int databaseSizeBeforeDelete = draftsRepository.findAll().size();

        // Delete the drafts
        restDraftsMockMvc.perform(delete("/api/drafts/{id}", drafts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drafts> draftsList = draftsRepository.findAll();
        assertThat(draftsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
