/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.DraftSequences;
import com.infy.repository.DraftSequencesRepository;
import com.infy.service.DraftSequencesService;
import com.infy.service.dto.DraftSequencesDTO;
import com.infy.service.mapper.DraftSequencesMapper;

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
 * Integration tests for the {@link DraftSequencesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DraftSequencesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DRAFT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DRAFT_KEY = "BBBBBBBBBB";

    private static final Long DEFAULT_SEQUENCE = 1L;
    private static final Long UPDATED_SEQUENCE = 2L;

    @Autowired
    private DraftSequencesRepository draftSequencesRepository;

    @Autowired
    private DraftSequencesMapper draftSequencesMapper;

    @Autowired
    private DraftSequencesService draftSequencesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDraftSequencesMockMvc;

    private DraftSequences draftSequences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DraftSequences createEntity(EntityManager em) {
        DraftSequences draftSequences = new DraftSequences()
            .userId(DEFAULT_USER_ID)
            .draftKey(DEFAULT_DRAFT_KEY)
            .sequence(DEFAULT_SEQUENCE);
        return draftSequences;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DraftSequences createUpdatedEntity(EntityManager em) {
        DraftSequences draftSequences = new DraftSequences()
            .userId(UPDATED_USER_ID)
            .draftKey(UPDATED_DRAFT_KEY)
            .sequence(UPDATED_SEQUENCE);
        return draftSequences;
    }

    @BeforeEach
    public void initTest() {
        draftSequences = createEntity(em);
    }

    @Test
    @Transactional
    public void createDraftSequences() throws Exception {
        int databaseSizeBeforeCreate = draftSequencesRepository.findAll().size();
        // Create the DraftSequences
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(draftSequences);
        restDraftSequencesMockMvc.perform(post("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isCreated());

        // Validate the DraftSequences in the database
        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeCreate + 1);
        DraftSequences testDraftSequences = draftSequencesList.get(draftSequencesList.size() - 1);
        assertThat(testDraftSequences.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDraftSequences.getDraftKey()).isEqualTo(DEFAULT_DRAFT_KEY);
        assertThat(testDraftSequences.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    public void createDraftSequencesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = draftSequencesRepository.findAll().size();

        // Create the DraftSequences with an existing ID
        draftSequences.setId(1L);
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(draftSequences);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDraftSequencesMockMvc.perform(post("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DraftSequences in the database
        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftSequencesRepository.findAll().size();
        // set the field null
        draftSequences.setUserId(null);

        // Create the DraftSequences, which fails.
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(draftSequences);


        restDraftSequencesMockMvc.perform(post("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isBadRequest());

        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDraftKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftSequencesRepository.findAll().size();
        // set the field null
        draftSequences.setDraftKey(null);

        // Create the DraftSequences, which fails.
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(draftSequences);


        restDraftSequencesMockMvc.perform(post("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isBadRequest());

        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = draftSequencesRepository.findAll().size();
        // set the field null
        draftSequences.setSequence(null);

        // Create the DraftSequences, which fails.
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(draftSequences);


        restDraftSequencesMockMvc.perform(post("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isBadRequest());

        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDraftSequences() throws Exception {
        // Initialize the database
        draftSequencesRepository.saveAndFlush(draftSequences);

        // Get all the draftSequencesList
        restDraftSequencesMockMvc.perform(get("/api/draft-sequences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(draftSequences.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].draftKey").value(hasItem(DEFAULT_DRAFT_KEY)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.intValue())));
    }

    @Test
    @Transactional
    public void getDraftSequences() throws Exception {
        // Initialize the database
        draftSequencesRepository.saveAndFlush(draftSequences);

        // Get the draftSequences
        restDraftSequencesMockMvc.perform(get("/api/draft-sequences/{id}", draftSequences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(draftSequences.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.draftKey").value(DEFAULT_DRAFT_KEY))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDraftSequences() throws Exception {
        // Get the draftSequences
        restDraftSequencesMockMvc.perform(get("/api/draft-sequences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDraftSequences() throws Exception {
        // Initialize the database
        draftSequencesRepository.saveAndFlush(draftSequences);

        int databaseSizeBeforeUpdate = draftSequencesRepository.findAll().size();

        // Update the draftSequences
        DraftSequences updatedDraftSequences = draftSequencesRepository.findById(draftSequences.getId()).get();
        // Disconnect from session so that the updates on updatedDraftSequences are not directly saved in db
        em.detach(updatedDraftSequences);
        updatedDraftSequences
            .userId(UPDATED_USER_ID)
            .draftKey(UPDATED_DRAFT_KEY)
            .sequence(UPDATED_SEQUENCE);
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(updatedDraftSequences);

        restDraftSequencesMockMvc.perform(put("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isOk());

        // Validate the DraftSequences in the database
        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeUpdate);
        DraftSequences testDraftSequences = draftSequencesList.get(draftSequencesList.size() - 1);
        assertThat(testDraftSequences.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDraftSequences.getDraftKey()).isEqualTo(UPDATED_DRAFT_KEY);
        assertThat(testDraftSequences.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingDraftSequences() throws Exception {
        int databaseSizeBeforeUpdate = draftSequencesRepository.findAll().size();

        // Create the DraftSequences
        DraftSequencesDTO draftSequencesDTO = draftSequencesMapper.toDto(draftSequences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDraftSequencesMockMvc.perform(put("/api/draft-sequences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(draftSequencesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DraftSequences in the database
        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDraftSequences() throws Exception {
        // Initialize the database
        draftSequencesRepository.saveAndFlush(draftSequences);

        int databaseSizeBeforeDelete = draftSequencesRepository.findAll().size();

        // Delete the draftSequences
        restDraftSequencesMockMvc.perform(delete("/api/draft-sequences/{id}", draftSequences.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DraftSequences> draftSequencesList = draftSequencesRepository.findAll();
        assertThat(draftSequencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
