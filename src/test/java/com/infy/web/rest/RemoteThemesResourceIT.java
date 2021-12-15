/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.RemoteThemes;
import com.infy.repository.RemoteThemesRepository;
import com.infy.service.RemoteThemesService;
import com.infy.service.dto.RemoteThemesDTO;
import com.infy.service.mapper.RemoteThemesMapper;

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
 * Integration tests for the {@link RemoteThemesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class RemoteThemesResourceIT {

    private static final String DEFAULT_REMOTE_URL = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMMITS_BEHIND = 1;
    private static final Integer UPDATED_COMMITS_BEHIND = 2;

    private static final Instant DEFAULT_REMOTE_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REMOTE_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PRIVATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PRIVATE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_ERROR_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_LAST_ERROR_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORS = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORS = "BBBBBBBBBB";

    private static final String DEFAULT_THEME_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THEME_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_MINIMUM_DISCOURSE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_MINIMUM_DISCOURSE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMUM_DISCOURSE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMUM_DISCOURSE_VERSION = "BBBBBBBBBB";

    @Autowired
    private RemoteThemesRepository remoteThemesRepository;

    @Autowired
    private RemoteThemesMapper remoteThemesMapper;

    @Autowired
    private RemoteThemesService remoteThemesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemoteThemesMockMvc;

    private RemoteThemes remoteThemes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemoteThemes createEntity(EntityManager em) {
        RemoteThemes remoteThemes = new RemoteThemes()
            .remoteUrl(DEFAULT_REMOTE_URL)
            .remoteVersion(DEFAULT_REMOTE_VERSION)
            .localVersion(DEFAULT_LOCAL_VERSION)
            .aboutUrl(DEFAULT_ABOUT_URL)
            .licenseUrl(DEFAULT_LICENSE_URL)
            .commitsBehind(DEFAULT_COMMITS_BEHIND)
            .remoteUpdatedAt(DEFAULT_REMOTE_UPDATED_AT)
            .privateKey(DEFAULT_PRIVATE_KEY)
            .branch(DEFAULT_BRANCH)
            .lastErrorText(DEFAULT_LAST_ERROR_TEXT)
            .authors(DEFAULT_AUTHORS)
            .themeVersion(DEFAULT_THEME_VERSION)
            .minimumDiscourseVersion(DEFAULT_MINIMUM_DISCOURSE_VERSION)
            .maximumDiscourseVersion(DEFAULT_MAXIMUM_DISCOURSE_VERSION);
        return remoteThemes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemoteThemes createUpdatedEntity(EntityManager em) {
        RemoteThemes remoteThemes = new RemoteThemes()
            .remoteUrl(UPDATED_REMOTE_URL)
            .remoteVersion(UPDATED_REMOTE_VERSION)
            .localVersion(UPDATED_LOCAL_VERSION)
            .aboutUrl(UPDATED_ABOUT_URL)
            .licenseUrl(UPDATED_LICENSE_URL)
            .commitsBehind(UPDATED_COMMITS_BEHIND)
            .remoteUpdatedAt(UPDATED_REMOTE_UPDATED_AT)
            .privateKey(UPDATED_PRIVATE_KEY)
            .branch(UPDATED_BRANCH)
            .lastErrorText(UPDATED_LAST_ERROR_TEXT)
            .authors(UPDATED_AUTHORS)
            .themeVersion(UPDATED_THEME_VERSION)
            .minimumDiscourseVersion(UPDATED_MINIMUM_DISCOURSE_VERSION)
            .maximumDiscourseVersion(UPDATED_MAXIMUM_DISCOURSE_VERSION);
        return remoteThemes;
    }

    @BeforeEach
    public void initTest() {
        remoteThemes = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemoteThemes() throws Exception {
        int databaseSizeBeforeCreate = remoteThemesRepository.findAll().size();
        // Create the RemoteThemes
        RemoteThemesDTO remoteThemesDTO = remoteThemesMapper.toDto(remoteThemes);
        restRemoteThemesMockMvc.perform(post("/api/remote-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remoteThemesDTO)))
            .andExpect(status().isCreated());

        // Validate the RemoteThemes in the database
        List<RemoteThemes> remoteThemesList = remoteThemesRepository.findAll();
        assertThat(remoteThemesList).hasSize(databaseSizeBeforeCreate + 1);
        RemoteThemes testRemoteThemes = remoteThemesList.get(remoteThemesList.size() - 1);
        assertThat(testRemoteThemes.getRemoteUrl()).isEqualTo(DEFAULT_REMOTE_URL);
        assertThat(testRemoteThemes.getRemoteVersion()).isEqualTo(DEFAULT_REMOTE_VERSION);
        assertThat(testRemoteThemes.getLocalVersion()).isEqualTo(DEFAULT_LOCAL_VERSION);
        assertThat(testRemoteThemes.getAboutUrl()).isEqualTo(DEFAULT_ABOUT_URL);
        assertThat(testRemoteThemes.getLicenseUrl()).isEqualTo(DEFAULT_LICENSE_URL);
        assertThat(testRemoteThemes.getCommitsBehind()).isEqualTo(DEFAULT_COMMITS_BEHIND);
        assertThat(testRemoteThemes.getRemoteUpdatedAt()).isEqualTo(DEFAULT_REMOTE_UPDATED_AT);
        assertThat(testRemoteThemes.getPrivateKey()).isEqualTo(DEFAULT_PRIVATE_KEY);
        assertThat(testRemoteThemes.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testRemoteThemes.getLastErrorText()).isEqualTo(DEFAULT_LAST_ERROR_TEXT);
        assertThat(testRemoteThemes.getAuthors()).isEqualTo(DEFAULT_AUTHORS);
        assertThat(testRemoteThemes.getThemeVersion()).isEqualTo(DEFAULT_THEME_VERSION);
        assertThat(testRemoteThemes.getMinimumDiscourseVersion()).isEqualTo(DEFAULT_MINIMUM_DISCOURSE_VERSION);
        assertThat(testRemoteThemes.getMaximumDiscourseVersion()).isEqualTo(DEFAULT_MAXIMUM_DISCOURSE_VERSION);
    }

    @Test
    @Transactional
    public void createRemoteThemesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remoteThemesRepository.findAll().size();

        // Create the RemoteThemes with an existing ID
        remoteThemes.setId(1L);
        RemoteThemesDTO remoteThemesDTO = remoteThemesMapper.toDto(remoteThemes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemoteThemesMockMvc.perform(post("/api/remote-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remoteThemesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RemoteThemes in the database
        List<RemoteThemes> remoteThemesList = remoteThemesRepository.findAll();
        assertThat(remoteThemesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRemoteUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = remoteThemesRepository.findAll().size();
        // set the field null
        remoteThemes.setRemoteUrl(null);

        // Create the RemoteThemes, which fails.
        RemoteThemesDTO remoteThemesDTO = remoteThemesMapper.toDto(remoteThemes);


        restRemoteThemesMockMvc.perform(post("/api/remote-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remoteThemesDTO)))
            .andExpect(status().isBadRequest());

        List<RemoteThemes> remoteThemesList = remoteThemesRepository.findAll();
        assertThat(remoteThemesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRemoteThemes() throws Exception {
        // Initialize the database
        remoteThemesRepository.saveAndFlush(remoteThemes);

        // Get all the remoteThemesList
        restRemoteThemesMockMvc.perform(get("/api/remote-themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remoteThemes.getId().intValue())))
            .andExpect(jsonPath("$.[*].remoteUrl").value(hasItem(DEFAULT_REMOTE_URL)))
            .andExpect(jsonPath("$.[*].remoteVersion").value(hasItem(DEFAULT_REMOTE_VERSION)))
            .andExpect(jsonPath("$.[*].localVersion").value(hasItem(DEFAULT_LOCAL_VERSION)))
            .andExpect(jsonPath("$.[*].aboutUrl").value(hasItem(DEFAULT_ABOUT_URL)))
            .andExpect(jsonPath("$.[*].licenseUrl").value(hasItem(DEFAULT_LICENSE_URL)))
            .andExpect(jsonPath("$.[*].commitsBehind").value(hasItem(DEFAULT_COMMITS_BEHIND)))
            .andExpect(jsonPath("$.[*].remoteUpdatedAt").value(hasItem(DEFAULT_REMOTE_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].privateKey").value(hasItem(DEFAULT_PRIVATE_KEY)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].lastErrorText").value(hasItem(DEFAULT_LAST_ERROR_TEXT)))
            .andExpect(jsonPath("$.[*].authors").value(hasItem(DEFAULT_AUTHORS)))
            .andExpect(jsonPath("$.[*].themeVersion").value(hasItem(DEFAULT_THEME_VERSION)))
            .andExpect(jsonPath("$.[*].minimumDiscourseVersion").value(hasItem(DEFAULT_MINIMUM_DISCOURSE_VERSION)))
            .andExpect(jsonPath("$.[*].maximumDiscourseVersion").value(hasItem(DEFAULT_MAXIMUM_DISCOURSE_VERSION)));
    }

    @Test
    @Transactional
    public void getRemoteThemes() throws Exception {
        // Initialize the database
        remoteThemesRepository.saveAndFlush(remoteThemes);

        // Get the remoteThemes
        restRemoteThemesMockMvc.perform(get("/api/remote-themes/{id}", remoteThemes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remoteThemes.getId().intValue()))
            .andExpect(jsonPath("$.remoteUrl").value(DEFAULT_REMOTE_URL))
            .andExpect(jsonPath("$.remoteVersion").value(DEFAULT_REMOTE_VERSION))
            .andExpect(jsonPath("$.localVersion").value(DEFAULT_LOCAL_VERSION))
            .andExpect(jsonPath("$.aboutUrl").value(DEFAULT_ABOUT_URL))
            .andExpect(jsonPath("$.licenseUrl").value(DEFAULT_LICENSE_URL))
            .andExpect(jsonPath("$.commitsBehind").value(DEFAULT_COMMITS_BEHIND))
            .andExpect(jsonPath("$.remoteUpdatedAt").value(DEFAULT_REMOTE_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.privateKey").value(DEFAULT_PRIVATE_KEY))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.lastErrorText").value(DEFAULT_LAST_ERROR_TEXT))
            .andExpect(jsonPath("$.authors").value(DEFAULT_AUTHORS))
            .andExpect(jsonPath("$.themeVersion").value(DEFAULT_THEME_VERSION))
            .andExpect(jsonPath("$.minimumDiscourseVersion").value(DEFAULT_MINIMUM_DISCOURSE_VERSION))
            .andExpect(jsonPath("$.maximumDiscourseVersion").value(DEFAULT_MAXIMUM_DISCOURSE_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingRemoteThemes() throws Exception {
        // Get the remoteThemes
        restRemoteThemesMockMvc.perform(get("/api/remote-themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemoteThemes() throws Exception {
        // Initialize the database
        remoteThemesRepository.saveAndFlush(remoteThemes);

        int databaseSizeBeforeUpdate = remoteThemesRepository.findAll().size();

        // Update the remoteThemes
        RemoteThemes updatedRemoteThemes = remoteThemesRepository.findById(remoteThemes.getId()).get();
        // Disconnect from session so that the updates on updatedRemoteThemes are not directly saved in db
        em.detach(updatedRemoteThemes);
        updatedRemoteThemes
            .remoteUrl(UPDATED_REMOTE_URL)
            .remoteVersion(UPDATED_REMOTE_VERSION)
            .localVersion(UPDATED_LOCAL_VERSION)
            .aboutUrl(UPDATED_ABOUT_URL)
            .licenseUrl(UPDATED_LICENSE_URL)
            .commitsBehind(UPDATED_COMMITS_BEHIND)
            .remoteUpdatedAt(UPDATED_REMOTE_UPDATED_AT)
            .privateKey(UPDATED_PRIVATE_KEY)
            .branch(UPDATED_BRANCH)
            .lastErrorText(UPDATED_LAST_ERROR_TEXT)
            .authors(UPDATED_AUTHORS)
            .themeVersion(UPDATED_THEME_VERSION)
            .minimumDiscourseVersion(UPDATED_MINIMUM_DISCOURSE_VERSION)
            .maximumDiscourseVersion(UPDATED_MAXIMUM_DISCOURSE_VERSION);
        RemoteThemesDTO remoteThemesDTO = remoteThemesMapper.toDto(updatedRemoteThemes);

        restRemoteThemesMockMvc.perform(put("/api/remote-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remoteThemesDTO)))
            .andExpect(status().isOk());

        // Validate the RemoteThemes in the database
        List<RemoteThemes> remoteThemesList = remoteThemesRepository.findAll();
        assertThat(remoteThemesList).hasSize(databaseSizeBeforeUpdate);
        RemoteThemes testRemoteThemes = remoteThemesList.get(remoteThemesList.size() - 1);
        assertThat(testRemoteThemes.getRemoteUrl()).isEqualTo(UPDATED_REMOTE_URL);
        assertThat(testRemoteThemes.getRemoteVersion()).isEqualTo(UPDATED_REMOTE_VERSION);
        assertThat(testRemoteThemes.getLocalVersion()).isEqualTo(UPDATED_LOCAL_VERSION);
        assertThat(testRemoteThemes.getAboutUrl()).isEqualTo(UPDATED_ABOUT_URL);
        assertThat(testRemoteThemes.getLicenseUrl()).isEqualTo(UPDATED_LICENSE_URL);
        assertThat(testRemoteThemes.getCommitsBehind()).isEqualTo(UPDATED_COMMITS_BEHIND);
        assertThat(testRemoteThemes.getRemoteUpdatedAt()).isEqualTo(UPDATED_REMOTE_UPDATED_AT);
        assertThat(testRemoteThemes.getPrivateKey()).isEqualTo(UPDATED_PRIVATE_KEY);
        assertThat(testRemoteThemes.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testRemoteThemes.getLastErrorText()).isEqualTo(UPDATED_LAST_ERROR_TEXT);
        assertThat(testRemoteThemes.getAuthors()).isEqualTo(UPDATED_AUTHORS);
        assertThat(testRemoteThemes.getThemeVersion()).isEqualTo(UPDATED_THEME_VERSION);
        assertThat(testRemoteThemes.getMinimumDiscourseVersion()).isEqualTo(UPDATED_MINIMUM_DISCOURSE_VERSION);
        assertThat(testRemoteThemes.getMaximumDiscourseVersion()).isEqualTo(UPDATED_MAXIMUM_DISCOURSE_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingRemoteThemes() throws Exception {
        int databaseSizeBeforeUpdate = remoteThemesRepository.findAll().size();

        // Create the RemoteThemes
        RemoteThemesDTO remoteThemesDTO = remoteThemesMapper.toDto(remoteThemes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemoteThemesMockMvc.perform(put("/api/remote-themes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remoteThemesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RemoteThemes in the database
        List<RemoteThemes> remoteThemesList = remoteThemesRepository.findAll();
        assertThat(remoteThemesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRemoteThemes() throws Exception {
        // Initialize the database
        remoteThemesRepository.saveAndFlush(remoteThemes);

        int databaseSizeBeforeDelete = remoteThemesRepository.findAll().size();

        // Delete the remoteThemes
        restRemoteThemesMockMvc.perform(delete("/api/remote-themes/{id}", remoteThemes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RemoteThemes> remoteThemesList = remoteThemesRepository.findAll();
        assertThat(remoteThemesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
