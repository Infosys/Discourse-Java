/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Announcement;
import com.infy.repository.AnnouncmentRepository;
import com.infy.service.AnnouncementService;
import com.infy.service.dto.AnnouncementDTO;
import com.infy.service.mapper.AnnouncementMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.infy.domain.enumeration.AnnouncementStatus;
/**
 * Integration tests for the {@link AnnouncementResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AnnouncmentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_RAW = "AAAAAAAAAA";
    private static final String UPDATED_RAW = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_BY = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AnnouncementStatus DEFAULT_STATUS = AnnouncementStatus.ACTIVE;
    private static final AnnouncementStatus UPDATED_STATUS = AnnouncementStatus.INACTIVE;

    @Autowired
    private AnnouncmentRepository announcmentRepository;

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnouncmentMockMvc;

    private Announcement announcement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(DEFAULT_TITLE)
            .raw(DEFAULT_RAW)
            .deletedBy(DEFAULT_DELETED_BY)
            .deletedAt(DEFAULT_DELETED_AT)
            .status(DEFAULT_STATUS);
        return announcement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createUpdatedEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(UPDATED_TITLE)
            .raw(UPDATED_RAW)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedAt(UPDATED_DELETED_AT)
            .status(UPDATED_STATUS);
        return announcement;
    }

    @BeforeEach
    public void initTest() {
        announcement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnouncment() throws Exception {
        int databaseSizeBeforeCreate = announcmentRepository.findAll().size();
        // Create the Announcement
        AnnouncementDTO announcementDTO = announcementMapper.toDto(announcement);
        restAnnouncmentMockMvc.perform(post("/api/announcments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcementDTO)))
            .andExpect(status().isCreated());

        // Validate the Announcement in the database
        List<Announcement> announcmentList = announcmentRepository.findAll();
        assertThat(announcmentList).hasSize(databaseSizeBeforeCreate + 1);
        Announcement testAnnouncment = announcmentList.get(announcmentList.size() - 1);
        assertThat(testAnnouncment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAnnouncment.getRaw()).isEqualTo(DEFAULT_RAW);
        assertThat(testAnnouncment.getDeletedBy()).isEqualTo(DEFAULT_DELETED_BY);
        assertThat(testAnnouncment.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testAnnouncment.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAnnouncmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = announcmentRepository.findAll().size();

        // Create the Announcement with an existing ID
        announcement.setId(1L);
        AnnouncementDTO announcementDTO = announcementMapper.toDto(announcement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnouncmentMockMvc.perform(post("/api/announcments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcmentList = announcmentRepository.findAll();
        assertThat(announcmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnnouncments() throws Exception {
        // Initialize the database
        announcmentRepository.saveAndFlush(announcement);

        // Get all the announcmentList
        restAnnouncmentMockMvc.perform(get("/api/announcments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].raw").value(hasItem(DEFAULT_RAW.toString())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAnnouncment() throws Exception {
        // Initialize the database
        announcmentRepository.saveAndFlush(announcement);

        // Get the announcement
        restAnnouncmentMockMvc.perform(get("/api/announcments/{id}", announcement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(announcement.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.raw").value(DEFAULT_RAW.toString()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAnnouncment() throws Exception {
        // Get the announcement
        restAnnouncmentMockMvc.perform(get("/api/announcments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnouncment() throws Exception {
        // Initialize the database
        announcmentRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcmentRepository.findAll().size();

        // Update the announcement
        Announcement updatedAnnouncment = announcmentRepository.findById(announcement.getId()).get();
        // Disconnect from session so that the updates on updatedAnnouncment are not directly saved in db
        em.detach(updatedAnnouncment);
        updatedAnnouncment
            .title(UPDATED_TITLE)
            .raw(UPDATED_RAW)
            .deletedBy(UPDATED_DELETED_BY)
            .deletedAt(UPDATED_DELETED_AT)
            .status(UPDATED_STATUS);
        AnnouncementDTO announcementDTO = announcementMapper.toDto(updatedAnnouncment);

        restAnnouncmentMockMvc.perform(put("/api/announcments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcementDTO)))
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcmentList = announcmentRepository.findAll();
        assertThat(announcmentList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncment = announcmentList.get(announcmentList.size() - 1);
        assertThat(testAnnouncment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnouncment.getRaw()).isEqualTo(UPDATED_RAW);
        assertThat(testAnnouncment.getDeletedBy()).isEqualTo(UPDATED_DELETED_BY);
        assertThat(testAnnouncment.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testAnnouncment.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnouncment() throws Exception {
        int databaseSizeBeforeUpdate = announcmentRepository.findAll().size();

        // Create the Announcement
        AnnouncementDTO announcementDTO = announcementMapper.toDto(announcement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncmentMockMvc.perform(put("/api/announcments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(announcementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcmentList = announcmentRepository.findAll();
        assertThat(announcmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnouncment() throws Exception {
        // Initialize the database
        announcmentRepository.saveAndFlush(announcement);

        int databaseSizeBeforeDelete = announcmentRepository.findAll().size();

        // Delete the announcement
        restAnnouncmentMockMvc.perform(delete("/api/announcments/{id}", announcement.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Announcement> announcmentList = announcmentRepository.findAll();
        assertThat(announcmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
