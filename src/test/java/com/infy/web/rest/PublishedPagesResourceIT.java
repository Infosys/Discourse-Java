/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PublishedPages;
import com.infy.repository.PublishedPagesRepository;
import com.infy.service.PublishedPagesService;
import com.infy.service.dto.PublishedPagesDTO;
import com.infy.service.mapper.PublishedPagesMapper;

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
 * Integration tests for the {@link PublishedPagesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PublishedPagesResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLICLY_AVAILABLE = false;
    private static final Boolean UPDATED_PUBLICLY_AVAILABLE = true;

    @Autowired
    private PublishedPagesRepository publishedPagesRepository;

    @Autowired
    private PublishedPagesMapper publishedPagesMapper;

    @Autowired
    private PublishedPagesService publishedPagesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublishedPagesMockMvc;

    private PublishedPages publishedPages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublishedPages createEntity(EntityManager em) {
        PublishedPages publishedPages = new PublishedPages()
            .topicId(DEFAULT_TOPIC_ID)
            .slug(DEFAULT_SLUG)
            .publiclyAvailable(DEFAULT_PUBLICLY_AVAILABLE);
        return publishedPages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublishedPages createUpdatedEntity(EntityManager em) {
        PublishedPages publishedPages = new PublishedPages()
            .topicId(UPDATED_TOPIC_ID)
            .slug(UPDATED_SLUG)
            .publiclyAvailable(UPDATED_PUBLICLY_AVAILABLE);
        return publishedPages;
    }

    @BeforeEach
    public void initTest() {
        publishedPages = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublishedPages() throws Exception {
        int databaseSizeBeforeCreate = publishedPagesRepository.findAll().size();
        // Create the PublishedPages
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(publishedPages);
        restPublishedPagesMockMvc.perform(post("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isCreated());

        // Validate the PublishedPages in the database
        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeCreate + 1);
        PublishedPages testPublishedPages = publishedPagesList.get(publishedPagesList.size() - 1);
        assertThat(testPublishedPages.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testPublishedPages.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testPublishedPages.isPubliclyAvailable()).isEqualTo(DEFAULT_PUBLICLY_AVAILABLE);
    }

    @Test
    @Transactional
    public void createPublishedPagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publishedPagesRepository.findAll().size();

        // Create the PublishedPages with an existing ID
        publishedPages.setId(1L);
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(publishedPages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublishedPagesMockMvc.perform(post("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublishedPages in the database
        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishedPagesRepository.findAll().size();
        // set the field null
        publishedPages.setTopicId(null);

        // Create the PublishedPages, which fails.
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(publishedPages);


        restPublishedPagesMockMvc.perform(post("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isBadRequest());

        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishedPagesRepository.findAll().size();
        // set the field null
        publishedPages.setSlug(null);

        // Create the PublishedPages, which fails.
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(publishedPages);


        restPublishedPagesMockMvc.perform(post("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isBadRequest());

        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPubliclyAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishedPagesRepository.findAll().size();
        // set the field null
        publishedPages.setPubliclyAvailable(null);

        // Create the PublishedPages, which fails.
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(publishedPages);


        restPublishedPagesMockMvc.perform(post("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isBadRequest());

        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublishedPages() throws Exception {
        // Initialize the database
        publishedPagesRepository.saveAndFlush(publishedPages);

        // Get all the publishedPagesList
        restPublishedPagesMockMvc.perform(get("/api/published-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publishedPages.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].publiclyAvailable").value(hasItem(DEFAULT_PUBLICLY_AVAILABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPublishedPages() throws Exception {
        // Initialize the database
        publishedPagesRepository.saveAndFlush(publishedPages);

        // Get the publishedPages
        restPublishedPagesMockMvc.perform(get("/api/published-pages/{id}", publishedPages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publishedPages.getId().intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.publiclyAvailable").value(DEFAULT_PUBLICLY_AVAILABLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPublishedPages() throws Exception {
        // Get the publishedPages
        restPublishedPagesMockMvc.perform(get("/api/published-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublishedPages() throws Exception {
        // Initialize the database
        publishedPagesRepository.saveAndFlush(publishedPages);

        int databaseSizeBeforeUpdate = publishedPagesRepository.findAll().size();

        // Update the publishedPages
        PublishedPages updatedPublishedPages = publishedPagesRepository.findById(publishedPages.getId()).get();
        // Disconnect from session so that the updates on updatedPublishedPages are not directly saved in db
        em.detach(updatedPublishedPages);
        updatedPublishedPages
            .topicId(UPDATED_TOPIC_ID)
            .slug(UPDATED_SLUG)
            .publiclyAvailable(UPDATED_PUBLICLY_AVAILABLE);
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(updatedPublishedPages);

        restPublishedPagesMockMvc.perform(put("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isOk());

        // Validate the PublishedPages in the database
        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeUpdate);
        PublishedPages testPublishedPages = publishedPagesList.get(publishedPagesList.size() - 1);
        assertThat(testPublishedPages.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testPublishedPages.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testPublishedPages.isPubliclyAvailable()).isEqualTo(UPDATED_PUBLICLY_AVAILABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingPublishedPages() throws Exception {
        int databaseSizeBeforeUpdate = publishedPagesRepository.findAll().size();

        // Create the PublishedPages
        PublishedPagesDTO publishedPagesDTO = publishedPagesMapper.toDto(publishedPages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublishedPagesMockMvc.perform(put("/api/published-pages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publishedPagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublishedPages in the database
        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublishedPages() throws Exception {
        // Initialize the database
        publishedPagesRepository.saveAndFlush(publishedPages);

        int databaseSizeBeforeDelete = publishedPagesRepository.findAll().size();

        // Delete the publishedPages
        restPublishedPagesMockMvc.perform(delete("/api/published-pages/{id}", publishedPages.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublishedPages> publishedPagesList = publishedPagesRepository.findAll();
        assertThat(publishedPagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
