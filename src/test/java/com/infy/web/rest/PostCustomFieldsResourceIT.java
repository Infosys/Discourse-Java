/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.PostCustomFields;
import com.infy.repository.PostCustomFieldsRepository;
import com.infy.service.PostCustomFieldsService;
import com.infy.service.dto.PostCustomFieldsDTO;
import com.infy.service.mapper.PostCustomFieldsMapper;

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
 * Integration tests for the {@link PostCustomFieldsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostCustomFieldsResourceIT {

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PostCustomFieldsRepository postCustomFieldsRepository;

    @Autowired
    private PostCustomFieldsMapper postCustomFieldsMapper;

    @Autowired
    private PostCustomFieldsService postCustomFieldsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostCustomFieldsMockMvc;

    private PostCustomFields postCustomFields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCustomFields createEntity(EntityManager em) {
        PostCustomFields postCustomFields = new PostCustomFields()
            .postId(DEFAULT_POST_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .updatedAt(DEFAULT_UPDATED_AT);
        return postCustomFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCustomFields createUpdatedEntity(EntityManager em) {
        PostCustomFields postCustomFields = new PostCustomFields()
            .postId(UPDATED_POST_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .updatedAt(UPDATED_UPDATED_AT);
        return postCustomFields;
    }

    @BeforeEach
    public void initTest() {
        postCustomFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostCustomFields() throws Exception {
        int databaseSizeBeforeCreate = postCustomFieldsRepository.findAll().size();
        // Create the PostCustomFields
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(postCustomFields);
        restPostCustomFieldsMockMvc.perform(post("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostCustomFields in the database
        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        PostCustomFields testPostCustomFields = postCustomFieldsList.get(postCustomFieldsList.size() - 1);
        assertThat(testPostCustomFields.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testPostCustomFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPostCustomFields.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPostCustomFields.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPostCustomFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postCustomFieldsRepository.findAll().size();

        // Create the PostCustomFields with an existing ID
        postCustomFields.setId(1L);
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(postCustomFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostCustomFieldsMockMvc.perform(post("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCustomFields in the database
        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = postCustomFieldsRepository.findAll().size();
        // set the field null
        postCustomFields.setPostId(null);

        // Create the PostCustomFields, which fails.
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(postCustomFields);


        restPostCustomFieldsMockMvc.perform(post("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = postCustomFieldsRepository.findAll().size();
        // set the field null
        postCustomFields.setName(null);

        // Create the PostCustomFields, which fails.
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(postCustomFields);


        restPostCustomFieldsMockMvc.perform(post("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = postCustomFieldsRepository.findAll().size();
        // set the field null
        postCustomFields.setUpdatedAt(null);

        // Create the PostCustomFields, which fails.
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(postCustomFields);


        restPostCustomFieldsMockMvc.perform(post("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostCustomFields() throws Exception {
        // Initialize the database
        postCustomFieldsRepository.saveAndFlush(postCustomFields);

        // Get all the postCustomFieldsList
        restPostCustomFieldsMockMvc.perform(get("/api/post-custom-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postCustomFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getPostCustomFields() throws Exception {
        // Initialize the database
        postCustomFieldsRepository.saveAndFlush(postCustomFields);

        // Get the postCustomFields
        restPostCustomFieldsMockMvc.perform(get("/api/post-custom-fields/{id}", postCustomFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postCustomFields.getId().intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPostCustomFields() throws Exception {
        // Get the postCustomFields
        restPostCustomFieldsMockMvc.perform(get("/api/post-custom-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostCustomFields() throws Exception {
        // Initialize the database
        postCustomFieldsRepository.saveAndFlush(postCustomFields);

        int databaseSizeBeforeUpdate = postCustomFieldsRepository.findAll().size();

        // Update the postCustomFields
        PostCustomFields updatedPostCustomFields = postCustomFieldsRepository.findById(postCustomFields.getId()).get();
        // Disconnect from session so that the updates on updatedPostCustomFields are not directly saved in db
        em.detach(updatedPostCustomFields);
        updatedPostCustomFields
            .postId(UPDATED_POST_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .updatedAt(UPDATED_UPDATED_AT);
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(updatedPostCustomFields);

        restPostCustomFieldsMockMvc.perform(put("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the PostCustomFields in the database
        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
        PostCustomFields testPostCustomFields = postCustomFieldsList.get(postCustomFieldsList.size() - 1);
        assertThat(testPostCustomFields.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testPostCustomFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostCustomFields.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPostCustomFields.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPostCustomFields() throws Exception {
        int databaseSizeBeforeUpdate = postCustomFieldsRepository.findAll().size();

        // Create the PostCustomFields
        PostCustomFieldsDTO postCustomFieldsDTO = postCustomFieldsMapper.toDto(postCustomFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostCustomFieldsMockMvc.perform(put("/api/post-custom-fields").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCustomFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCustomFields in the database
        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostCustomFields() throws Exception {
        // Initialize the database
        postCustomFieldsRepository.saveAndFlush(postCustomFields);

        int databaseSizeBeforeDelete = postCustomFieldsRepository.findAll().size();

        // Delete the postCustomFields
        restPostCustomFieldsMockMvc.perform(delete("/api/post-custom-fields/{id}", postCustomFields.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostCustomFields> postCustomFieldsList = postCustomFieldsRepository.findAll();
        assertThat(postCustomFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
