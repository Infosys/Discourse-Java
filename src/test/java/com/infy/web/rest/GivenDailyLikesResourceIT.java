/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GivenDailyLikes;
import com.infy.repository.GivenDailyLikesRepository;
import com.infy.service.GivenDailyLikesService;
import com.infy.service.dto.GivenDailyLikesDTO;
import com.infy.service.mapper.GivenDailyLikesMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GivenDailyLikesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GivenDailyLikesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIKES_GIVEN = 1;
    private static final Integer UPDATED_LIKES_GIVEN = 2;

    private static final LocalDate DEFAULT_GIVEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GIVEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_LIMIT_REACHED = false;
    private static final Boolean UPDATED_LIMIT_REACHED = true;

    @Autowired
    private GivenDailyLikesRepository givenDailyLikesRepository;

    @Autowired
    private GivenDailyLikesMapper givenDailyLikesMapper;

    @Autowired
    private GivenDailyLikesService givenDailyLikesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGivenDailyLikesMockMvc;

    private GivenDailyLikes givenDailyLikes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GivenDailyLikes createEntity(EntityManager em) {
        GivenDailyLikes givenDailyLikes = new GivenDailyLikes()
            .userId(DEFAULT_USER_ID)
            .likesGiven(DEFAULT_LIKES_GIVEN)
            .givenDate(DEFAULT_GIVEN_DATE)
            .limitReached(DEFAULT_LIMIT_REACHED);
        return givenDailyLikes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GivenDailyLikes createUpdatedEntity(EntityManager em) {
        GivenDailyLikes givenDailyLikes = new GivenDailyLikes()
            .userId(UPDATED_USER_ID)
            .likesGiven(UPDATED_LIKES_GIVEN)
            .givenDate(UPDATED_GIVEN_DATE)
            .limitReached(UPDATED_LIMIT_REACHED);
        return givenDailyLikes;
    }

    @BeforeEach
    public void initTest() {
        givenDailyLikes = createEntity(em);
    }

    @Test
    @Transactional
    public void createGivenDailyLikes() throws Exception {
        int databaseSizeBeforeCreate = givenDailyLikesRepository.findAll().size();
        // Create the GivenDailyLikes
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);
        restGivenDailyLikesMockMvc.perform(post("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isCreated());

        // Validate the GivenDailyLikes in the database
        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeCreate + 1);
        GivenDailyLikes testGivenDailyLikes = givenDailyLikesList.get(givenDailyLikesList.size() - 1);
        assertThat(testGivenDailyLikes.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testGivenDailyLikes.getLikesGiven()).isEqualTo(DEFAULT_LIKES_GIVEN);
        assertThat(testGivenDailyLikes.getGivenDate()).isEqualTo(DEFAULT_GIVEN_DATE);
        assertThat(testGivenDailyLikes.isLimitReached()).isEqualTo(DEFAULT_LIMIT_REACHED);
    }

    @Test
    @Transactional
    public void createGivenDailyLikesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = givenDailyLikesRepository.findAll().size();

        // Create the GivenDailyLikes with an existing ID
        givenDailyLikes.setId(1L);
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGivenDailyLikesMockMvc.perform(post("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GivenDailyLikes in the database
        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = givenDailyLikesRepository.findAll().size();
        // set the field null
        givenDailyLikes.setUserId(null);

        // Create the GivenDailyLikes, which fails.
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);


        restGivenDailyLikesMockMvc.perform(post("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isBadRequest());

        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLikesGivenIsRequired() throws Exception {
        int databaseSizeBeforeTest = givenDailyLikesRepository.findAll().size();
        // set the field null
        givenDailyLikes.setLikesGiven(null);

        // Create the GivenDailyLikes, which fails.
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);


        restGivenDailyLikesMockMvc.perform(post("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isBadRequest());

        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGivenDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = givenDailyLikesRepository.findAll().size();
        // set the field null
        givenDailyLikes.setGivenDate(null);

        // Create the GivenDailyLikes, which fails.
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);


        restGivenDailyLikesMockMvc.perform(post("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isBadRequest());

        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLimitReachedIsRequired() throws Exception {
        int databaseSizeBeforeTest = givenDailyLikesRepository.findAll().size();
        // set the field null
        givenDailyLikes.setLimitReached(null);

        // Create the GivenDailyLikes, which fails.
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);


        restGivenDailyLikesMockMvc.perform(post("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isBadRequest());

        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGivenDailyLikes() throws Exception {
        // Initialize the database
        givenDailyLikesRepository.saveAndFlush(givenDailyLikes);

        // Get all the givenDailyLikesList
        restGivenDailyLikesMockMvc.perform(get("/api/given-daily-likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(givenDailyLikes.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].likesGiven").value(hasItem(DEFAULT_LIKES_GIVEN)))
            .andExpect(jsonPath("$.[*].givenDate").value(hasItem(DEFAULT_GIVEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].limitReached").value(hasItem(DEFAULT_LIMIT_REACHED.booleanValue())));
    }

    @Test
    @Transactional
    public void getGivenDailyLikes() throws Exception {
        // Initialize the database
        givenDailyLikesRepository.saveAndFlush(givenDailyLikes);

        // Get the givenDailyLikes
        restGivenDailyLikesMockMvc.perform(get("/api/given-daily-likes/{id}", givenDailyLikes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(givenDailyLikes.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.likesGiven").value(DEFAULT_LIKES_GIVEN))
            .andExpect(jsonPath("$.givenDate").value(DEFAULT_GIVEN_DATE.toString()))
            .andExpect(jsonPath("$.limitReached").value(DEFAULT_LIMIT_REACHED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGivenDailyLikes() throws Exception {
        // Get the givenDailyLikes
        restGivenDailyLikesMockMvc.perform(get("/api/given-daily-likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGivenDailyLikes() throws Exception {
        // Initialize the database
        givenDailyLikesRepository.saveAndFlush(givenDailyLikes);

        int databaseSizeBeforeUpdate = givenDailyLikesRepository.findAll().size();

        // Update the givenDailyLikes
        GivenDailyLikes updatedGivenDailyLikes = givenDailyLikesRepository.findById(givenDailyLikes.getId()).get();
        // Disconnect from session so that the updates on updatedGivenDailyLikes are not directly saved in db
        em.detach(updatedGivenDailyLikes);
        updatedGivenDailyLikes
            .userId(UPDATED_USER_ID)
            .likesGiven(UPDATED_LIKES_GIVEN)
            .givenDate(UPDATED_GIVEN_DATE)
            .limitReached(UPDATED_LIMIT_REACHED);
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(updatedGivenDailyLikes);

        restGivenDailyLikesMockMvc.perform(put("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isOk());

        // Validate the GivenDailyLikes in the database
        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeUpdate);
        GivenDailyLikes testGivenDailyLikes = givenDailyLikesList.get(givenDailyLikesList.size() - 1);
        assertThat(testGivenDailyLikes.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testGivenDailyLikes.getLikesGiven()).isEqualTo(UPDATED_LIKES_GIVEN);
        assertThat(testGivenDailyLikes.getGivenDate()).isEqualTo(UPDATED_GIVEN_DATE);
        assertThat(testGivenDailyLikes.isLimitReached()).isEqualTo(UPDATED_LIMIT_REACHED);
    }

    @Test
    @Transactional
    public void updateNonExistingGivenDailyLikes() throws Exception {
        int databaseSizeBeforeUpdate = givenDailyLikesRepository.findAll().size();

        // Create the GivenDailyLikes
        GivenDailyLikesDTO givenDailyLikesDTO = givenDailyLikesMapper.toDto(givenDailyLikes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGivenDailyLikesMockMvc.perform(put("/api/given-daily-likes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(givenDailyLikesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GivenDailyLikes in the database
        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGivenDailyLikes() throws Exception {
        // Initialize the database
        givenDailyLikesRepository.saveAndFlush(givenDailyLikes);

        int databaseSizeBeforeDelete = givenDailyLikesRepository.findAll().size();

        // Delete the givenDailyLikes
        restGivenDailyLikesMockMvc.perform(delete("/api/given-daily-likes/{id}", givenDailyLikes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GivenDailyLikes> givenDailyLikesList = givenDailyLikesRepository.findAll();
        assertThat(givenDailyLikesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
