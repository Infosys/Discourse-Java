/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Oauth2UserInfos;
import com.infy.repository.Oauth2UserInfosRepository;
import com.infy.service.Oauth2UserInfosService;
import com.infy.service.dto.Oauth2UserInfosDTO;
import com.infy.service.mapper.Oauth2UserInfosMapper;

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
 * Integration tests for the {@link Oauth2UserInfosResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class Oauth2UserInfosResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private Oauth2UserInfosRepository oauth2UserInfosRepository;

    @Autowired
    private Oauth2UserInfosMapper oauth2UserInfosMapper;

    @Autowired
    private Oauth2UserInfosService oauth2UserInfosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOauth2UserInfosMockMvc;

    private Oauth2UserInfos oauth2UserInfos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oauth2UserInfos createEntity(EntityManager em) {
        Oauth2UserInfos oauth2UserInfos = new Oauth2UserInfos()
            .userId(DEFAULT_USER_ID)
            .uid(DEFAULT_UID)
            .provider(DEFAULT_PROVIDER)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .updatedAt(DEFAULT_UPDATED_AT);
        return oauth2UserInfos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oauth2UserInfos createUpdatedEntity(EntityManager em) {
        Oauth2UserInfos oauth2UserInfos = new Oauth2UserInfos()
            .userId(UPDATED_USER_ID)
            .uid(UPDATED_UID)
            .provider(UPDATED_PROVIDER)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .updatedAt(UPDATED_UPDATED_AT);
        return oauth2UserInfos;
    }

    @BeforeEach
    public void initTest() {
        oauth2UserInfos = createEntity(em);
    }

    @Test
    @Transactional
    public void createOauth2UserInfos() throws Exception {
        int databaseSizeBeforeCreate = oauth2UserInfosRepository.findAll().size();
        // Create the Oauth2UserInfos
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);
        restOauth2UserInfosMockMvc.perform(post("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isCreated());

        // Validate the Oauth2UserInfos in the database
        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeCreate + 1);
        Oauth2UserInfos testOauth2UserInfos = oauth2UserInfosList.get(oauth2UserInfosList.size() - 1);
        assertThat(testOauth2UserInfos.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testOauth2UserInfos.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testOauth2UserInfos.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testOauth2UserInfos.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOauth2UserInfos.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOauth2UserInfos.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createOauth2UserInfosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oauth2UserInfosRepository.findAll().size();

        // Create the Oauth2UserInfos with an existing ID
        oauth2UserInfos.setId(1L);
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOauth2UserInfosMockMvc.perform(post("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Oauth2UserInfos in the database
        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = oauth2UserInfosRepository.findAll().size();
        // set the field null
        oauth2UserInfos.setUserId(null);

        // Create the Oauth2UserInfos, which fails.
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);


        restOauth2UserInfosMockMvc.perform(post("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isBadRequest());

        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = oauth2UserInfosRepository.findAll().size();
        // set the field null
        oauth2UserInfos.setUid(null);

        // Create the Oauth2UserInfos, which fails.
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);


        restOauth2UserInfosMockMvc.perform(post("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isBadRequest());

        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = oauth2UserInfosRepository.findAll().size();
        // set the field null
        oauth2UserInfos.setProvider(null);

        // Create the Oauth2UserInfos, which fails.
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);


        restOauth2UserInfosMockMvc.perform(post("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isBadRequest());

        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = oauth2UserInfosRepository.findAll().size();
        // set the field null
        oauth2UserInfos.setUpdatedAt(null);

        // Create the Oauth2UserInfos, which fails.
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);


        restOauth2UserInfosMockMvc.perform(post("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isBadRequest());

        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOauth2UserInfos() throws Exception {
        // Initialize the database
        oauth2UserInfosRepository.saveAndFlush(oauth2UserInfos);

        // Get all the oauth2UserInfosList
        restOauth2UserInfosMockMvc.perform(get("/api/oauth-2-user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oauth2UserInfos.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getOauth2UserInfos() throws Exception {
        // Initialize the database
        oauth2UserInfosRepository.saveAndFlush(oauth2UserInfos);

        // Get the oauth2UserInfos
        restOauth2UserInfosMockMvc.perform(get("/api/oauth-2-user-infos/{id}", oauth2UserInfos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oauth2UserInfos.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOauth2UserInfos() throws Exception {
        // Get the oauth2UserInfos
        restOauth2UserInfosMockMvc.perform(get("/api/oauth-2-user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOauth2UserInfos() throws Exception {
        // Initialize the database
        oauth2UserInfosRepository.saveAndFlush(oauth2UserInfos);

        int databaseSizeBeforeUpdate = oauth2UserInfosRepository.findAll().size();

        // Update the oauth2UserInfos
        Oauth2UserInfos updatedOauth2UserInfos = oauth2UserInfosRepository.findById(oauth2UserInfos.getId()).get();
        // Disconnect from session so that the updates on updatedOauth2UserInfos are not directly saved in db
        em.detach(updatedOauth2UserInfos);
        updatedOauth2UserInfos
            .userId(UPDATED_USER_ID)
            .uid(UPDATED_UID)
            .provider(UPDATED_PROVIDER)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .updatedAt(UPDATED_UPDATED_AT);
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(updatedOauth2UserInfos);

        restOauth2UserInfosMockMvc.perform(put("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isOk());

        // Validate the Oauth2UserInfos in the database
        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeUpdate);
        Oauth2UserInfos testOauth2UserInfos = oauth2UserInfosList.get(oauth2UserInfosList.size() - 1);
        assertThat(testOauth2UserInfos.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOauth2UserInfos.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testOauth2UserInfos.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testOauth2UserInfos.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOauth2UserInfos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOauth2UserInfos.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOauth2UserInfos() throws Exception {
        int databaseSizeBeforeUpdate = oauth2UserInfosRepository.findAll().size();

        // Create the Oauth2UserInfos
        Oauth2UserInfosDTO oauth2UserInfosDTO = oauth2UserInfosMapper.toDto(oauth2UserInfos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOauth2UserInfosMockMvc.perform(put("/api/oauth-2-user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oauth2UserInfosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Oauth2UserInfos in the database
        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOauth2UserInfos() throws Exception {
        // Initialize the database
        oauth2UserInfosRepository.saveAndFlush(oauth2UserInfos);

        int databaseSizeBeforeDelete = oauth2UserInfosRepository.findAll().size();

        // Delete the oauth2UserInfos
        restOauth2UserInfosMockMvc.perform(delete("/api/oauth-2-user-infos/{id}", oauth2UserInfos.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Oauth2UserInfos> oauth2UserInfosList = oauth2UserInfosRepository.findAll();
        assertThat(oauth2UserInfosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
