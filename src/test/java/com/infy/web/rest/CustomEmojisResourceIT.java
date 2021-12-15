/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.CustomEmojis;
import com.infy.repository.CustomEmojisRepository;
import com.infy.service.CustomEmojisService;
import com.infy.service.dto.CustomEmojisDTO;
import com.infy.service.mapper.CustomEmojisMapper;

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
 * Integration tests for the {@link CustomEmojisResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomEmojisResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    @Autowired
    private CustomEmojisRepository customEmojisRepository;

    @Autowired
    private CustomEmojisMapper customEmojisMapper;

    @Autowired
    private CustomEmojisService customEmojisService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomEmojisMockMvc;

    private CustomEmojis customEmojis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomEmojis createEntity(EntityManager em) {
        CustomEmojis customEmojis = new CustomEmojis()
            .name(DEFAULT_NAME)
            .uploadId(DEFAULT_UPLOAD_ID)
            .group(DEFAULT_GROUP);
        return customEmojis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomEmojis createUpdatedEntity(EntityManager em) {
        CustomEmojis customEmojis = new CustomEmojis()
            .name(UPDATED_NAME)
            .uploadId(UPDATED_UPLOAD_ID)
            .group(UPDATED_GROUP);
        return customEmojis;
    }

    @BeforeEach
    public void initTest() {
        customEmojis = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomEmojis() throws Exception {
        int databaseSizeBeforeCreate = customEmojisRepository.findAll().size();
        // Create the CustomEmojis
        CustomEmojisDTO customEmojisDTO = customEmojisMapper.toDto(customEmojis);
        restCustomEmojisMockMvc.perform(post("/api/custom-emojis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customEmojisDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomEmojis in the database
        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeCreate + 1);
        CustomEmojis testCustomEmojis = customEmojisList.get(customEmojisList.size() - 1);
        assertThat(testCustomEmojis.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomEmojis.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
        assertThat(testCustomEmojis.getGroup()).isEqualTo(DEFAULT_GROUP);
    }

    @Test
    @Transactional
    public void createCustomEmojisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customEmojisRepository.findAll().size();

        // Create the CustomEmojis with an existing ID
        customEmojis.setId(1L);
        CustomEmojisDTO customEmojisDTO = customEmojisMapper.toDto(customEmojis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomEmojisMockMvc.perform(post("/api/custom-emojis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customEmojisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomEmojis in the database
        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customEmojisRepository.findAll().size();
        // set the field null
        customEmojis.setName(null);

        // Create the CustomEmojis, which fails.
        CustomEmojisDTO customEmojisDTO = customEmojisMapper.toDto(customEmojis);


        restCustomEmojisMockMvc.perform(post("/api/custom-emojis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customEmojisDTO)))
            .andExpect(status().isBadRequest());

        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUploadIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = customEmojisRepository.findAll().size();
        // set the field null
        customEmojis.setUploadId(null);

        // Create the CustomEmojis, which fails.
        CustomEmojisDTO customEmojisDTO = customEmojisMapper.toDto(customEmojis);


        restCustomEmojisMockMvc.perform(post("/api/custom-emojis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customEmojisDTO)))
            .andExpect(status().isBadRequest());

        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomEmojis() throws Exception {
        // Initialize the database
        customEmojisRepository.saveAndFlush(customEmojis);

        // Get all the customEmojisList
        restCustomEmojisMockMvc.perform(get("/api/custom-emojis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customEmojis.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)));
    }

    @Test
    @Transactional
    public void getCustomEmojis() throws Exception {
        // Initialize the database
        customEmojisRepository.saveAndFlush(customEmojis);

        // Get the customEmojis
        restCustomEmojisMockMvc.perform(get("/api/custom-emojis/{id}", customEmojis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customEmojis.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP));
    }
    @Test
    @Transactional
    public void getNonExistingCustomEmojis() throws Exception {
        // Get the customEmojis
        restCustomEmojisMockMvc.perform(get("/api/custom-emojis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomEmojis() throws Exception {
        // Initialize the database
        customEmojisRepository.saveAndFlush(customEmojis);

        int databaseSizeBeforeUpdate = customEmojisRepository.findAll().size();

        // Update the customEmojis
        CustomEmojis updatedCustomEmojis = customEmojisRepository.findById(customEmojis.getId()).get();
        // Disconnect from session so that the updates on updatedCustomEmojis are not directly saved in db
        em.detach(updatedCustomEmojis);
        updatedCustomEmojis
            .name(UPDATED_NAME)
            .uploadId(UPDATED_UPLOAD_ID)
            .group(UPDATED_GROUP);
        CustomEmojisDTO customEmojisDTO = customEmojisMapper.toDto(updatedCustomEmojis);

        restCustomEmojisMockMvc.perform(put("/api/custom-emojis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customEmojisDTO)))
            .andExpect(status().isOk());

        // Validate the CustomEmojis in the database
        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeUpdate);
        CustomEmojis testCustomEmojis = customEmojisList.get(customEmojisList.size() - 1);
        assertThat(testCustomEmojis.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomEmojis.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
        assertThat(testCustomEmojis.getGroup()).isEqualTo(UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomEmojis() throws Exception {
        int databaseSizeBeforeUpdate = customEmojisRepository.findAll().size();

        // Create the CustomEmojis
        CustomEmojisDTO customEmojisDTO = customEmojisMapper.toDto(customEmojis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomEmojisMockMvc.perform(put("/api/custom-emojis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customEmojisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomEmojis in the database
        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomEmojis() throws Exception {
        // Initialize the database
        customEmojisRepository.saveAndFlush(customEmojis);

        int databaseSizeBeforeDelete = customEmojisRepository.findAll().size();

        // Delete the customEmojis
        restCustomEmojisMockMvc.perform(delete("/api/custom-emojis/{id}", customEmojis.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomEmojis> customEmojisList = customEmojisRepository.findAll();
        assertThat(customEmojisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
