/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.WatchedWords;
import com.infy.repository.WatchedWordsRepository;
import com.infy.service.WatchedWordsService;
import com.infy.service.dto.WatchedWordsDTO;
import com.infy.service.mapper.WatchedWordsMapper;

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
 * Integration tests for the {@link WatchedWordsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WatchedWordsResourceIT {

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTION = 1;
    private static final Integer UPDATED_ACTION = 2;

    private static final String DEFAULT_REPLACEMENT = "AAAAAAAAAA";
    private static final String UPDATED_REPLACEMENT = "BBBBBBBBBB";

    @Autowired
    private WatchedWordsRepository watchedWordsRepository;

    @Autowired
    private WatchedWordsMapper watchedWordsMapper;

    @Autowired
    private WatchedWordsService watchedWordsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchedWordsMockMvc;

    private WatchedWords watchedWords;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchedWords createEntity(EntityManager em) {
        WatchedWords watchedWords = new WatchedWords()
            .word(DEFAULT_WORD)
            .action(DEFAULT_ACTION)
            .replacement(DEFAULT_REPLACEMENT);
        return watchedWords;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchedWords createUpdatedEntity(EntityManager em) {
        WatchedWords watchedWords = new WatchedWords()
            .word(UPDATED_WORD)
            .action(UPDATED_ACTION)
            .replacement(UPDATED_REPLACEMENT);
        return watchedWords;
    }

    @BeforeEach
    public void initTest() {
        watchedWords = createEntity(em);
    }

    @Test
    @Transactional
    public void createWatchedWords() throws Exception {
        int databaseSizeBeforeCreate = watchedWordsRepository.findAll().size();
        // Create the WatchedWords
        WatchedWordsDTO watchedWordsDTO = watchedWordsMapper.toDto(watchedWords);
        restWatchedWordsMockMvc.perform(post("/api/watched-words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchedWordsDTO)))
            .andExpect(status().isCreated());

        // Validate the WatchedWords in the database
        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeCreate + 1);
        WatchedWords testWatchedWords = watchedWordsList.get(watchedWordsList.size() - 1);
        assertThat(testWatchedWords.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testWatchedWords.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testWatchedWords.getReplacement()).isEqualTo(DEFAULT_REPLACEMENT);
    }

    @Test
    @Transactional
    public void createWatchedWordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = watchedWordsRepository.findAll().size();

        // Create the WatchedWords with an existing ID
        watchedWords.setId(1L);
        WatchedWordsDTO watchedWordsDTO = watchedWordsMapper.toDto(watchedWords);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchedWordsMockMvc.perform(post("/api/watched-words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchedWordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WatchedWords in the database
        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWordIsRequired() throws Exception {
        int databaseSizeBeforeTest = watchedWordsRepository.findAll().size();
        // set the field null
        watchedWords.setWord(null);

        // Create the WatchedWords, which fails.
        WatchedWordsDTO watchedWordsDTO = watchedWordsMapper.toDto(watchedWords);


        restWatchedWordsMockMvc.perform(post("/api/watched-words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchedWordsDTO)))
            .andExpect(status().isBadRequest());

        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = watchedWordsRepository.findAll().size();
        // set the field null
        watchedWords.setAction(null);

        // Create the WatchedWords, which fails.
        WatchedWordsDTO watchedWordsDTO = watchedWordsMapper.toDto(watchedWords);


        restWatchedWordsMockMvc.perform(post("/api/watched-words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchedWordsDTO)))
            .andExpect(status().isBadRequest());

        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWatchedWords() throws Exception {
        // Initialize the database
        watchedWordsRepository.saveAndFlush(watchedWords);

        // Get all the watchedWordsList
        restWatchedWordsMockMvc.perform(get("/api/watched-words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchedWords.getId().intValue())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].replacement").value(hasItem(DEFAULT_REPLACEMENT)));
    }

    @Test
    @Transactional
    public void getWatchedWords() throws Exception {
        // Initialize the database
        watchedWordsRepository.saveAndFlush(watchedWords);

        // Get the watchedWords
        restWatchedWordsMockMvc.perform(get("/api/watched-words/{id}", watchedWords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchedWords.getId().intValue()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.replacement").value(DEFAULT_REPLACEMENT));
    }
    @Test
    @Transactional
    public void getNonExistingWatchedWords() throws Exception {
        // Get the watchedWords
        restWatchedWordsMockMvc.perform(get("/api/watched-words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWatchedWords() throws Exception {
        // Initialize the database
        watchedWordsRepository.saveAndFlush(watchedWords);

        int databaseSizeBeforeUpdate = watchedWordsRepository.findAll().size();

        // Update the watchedWords
        WatchedWords updatedWatchedWords = watchedWordsRepository.findById(watchedWords.getId()).get();
        // Disconnect from session so that the updates on updatedWatchedWords are not directly saved in db
        em.detach(updatedWatchedWords);
        updatedWatchedWords
            .word(UPDATED_WORD)
            .action(UPDATED_ACTION)
            .replacement(UPDATED_REPLACEMENT);
        WatchedWordsDTO watchedWordsDTO = watchedWordsMapper.toDto(updatedWatchedWords);

        restWatchedWordsMockMvc.perform(put("/api/watched-words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchedWordsDTO)))
            .andExpect(status().isOk());

        // Validate the WatchedWords in the database
        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeUpdate);
        WatchedWords testWatchedWords = watchedWordsList.get(watchedWordsList.size() - 1);
        assertThat(testWatchedWords.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testWatchedWords.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testWatchedWords.getReplacement()).isEqualTo(UPDATED_REPLACEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingWatchedWords() throws Exception {
        int databaseSizeBeforeUpdate = watchedWordsRepository.findAll().size();

        // Create the WatchedWords
        WatchedWordsDTO watchedWordsDTO = watchedWordsMapper.toDto(watchedWords);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchedWordsMockMvc.perform(put("/api/watched-words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchedWordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WatchedWords in the database
        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWatchedWords() throws Exception {
        // Initialize the database
        watchedWordsRepository.saveAndFlush(watchedWords);

        int databaseSizeBeforeDelete = watchedWordsRepository.findAll().size();

        // Delete the watchedWords
        restWatchedWordsMockMvc.perform(delete("/api/watched-words/{id}", watchedWords.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WatchedWords> watchedWordsList = watchedWordsRepository.findAll();
        assertThat(watchedWordsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
