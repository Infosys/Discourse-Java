/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TagUsers;
import com.infy.repository.TagUsersRepository;
import com.infy.service.TagUsersService;
import com.infy.service.dto.TagUsersDTO;
import com.infy.service.mapper.TagUsersMapper;

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
 * Integration tests for the {@link TagUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TagUsersResourceIT {

    private static final Long DEFAULT_TAG_ID = 1L;
    private static final Long UPDATED_TAG_ID = 2L;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL = 2;

    @Autowired
    private TagUsersRepository tagUsersRepository;

    @Autowired
    private TagUsersMapper tagUsersMapper;

    @Autowired
    private TagUsersService tagUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagUsersMockMvc;

    private TagUsers tagUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagUsers createEntity(EntityManager em) {
        TagUsers tagUsers = new TagUsers()
            .tagId(DEFAULT_TAG_ID)
            .userId(DEFAULT_USER_ID)
            .notificationLevel(DEFAULT_NOTIFICATION_LEVEL);
        return tagUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagUsers createUpdatedEntity(EntityManager em) {
        TagUsers tagUsers = new TagUsers()
            .tagId(UPDATED_TAG_ID)
            .userId(UPDATED_USER_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL);
        return tagUsers;
    }

    @BeforeEach
    public void initTest() {
        tagUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagUsers() throws Exception {
        int databaseSizeBeforeCreate = tagUsersRepository.findAll().size();
        // Create the TagUsers
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(tagUsers);
        restTagUsersMockMvc.perform(post("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the TagUsers in the database
        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeCreate + 1);
        TagUsers testTagUsers = tagUsersList.get(tagUsersList.size() - 1);
        assertThat(testTagUsers.getTagId()).isEqualTo(DEFAULT_TAG_ID);
        assertThat(testTagUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTagUsers.getNotificationLevel()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL);
    }

    @Test
    @Transactional
    public void createTagUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagUsersRepository.findAll().size();

        // Create the TagUsers with an existing ID
        tagUsers.setId(1L);
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(tagUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagUsersMockMvc.perform(post("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagUsers in the database
        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTagIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagUsersRepository.findAll().size();
        // set the field null
        tagUsers.setTagId(null);

        // Create the TagUsers, which fails.
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(tagUsers);


        restTagUsersMockMvc.perform(post("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagUsersRepository.findAll().size();
        // set the field null
        tagUsers.setUserId(null);

        // Create the TagUsers, which fails.
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(tagUsers);


        restTagUsersMockMvc.perform(post("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagUsersRepository.findAll().size();
        // set the field null
        tagUsers.setNotificationLevel(null);

        // Create the TagUsers, which fails.
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(tagUsers);


        restTagUsersMockMvc.perform(post("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagUsers() throws Exception {
        // Initialize the database
        tagUsersRepository.saveAndFlush(tagUsers);

        // Get all the tagUsersList
        restTagUsersMockMvc.perform(get("/api/tag-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].notificationLevel").value(hasItem(DEFAULT_NOTIFICATION_LEVEL)));
    }

    @Test
    @Transactional
    public void getTagUsers() throws Exception {
        // Initialize the database
        tagUsersRepository.saveAndFlush(tagUsers);

        // Get the tagUsers
        restTagUsersMockMvc.perform(get("/api/tag-users/{id}", tagUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagUsers.getId().intValue()))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.notificationLevel").value(DEFAULT_NOTIFICATION_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingTagUsers() throws Exception {
        // Get the tagUsers
        restTagUsersMockMvc.perform(get("/api/tag-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagUsers() throws Exception {
        // Initialize the database
        tagUsersRepository.saveAndFlush(tagUsers);

        int databaseSizeBeforeUpdate = tagUsersRepository.findAll().size();

        // Update the tagUsers
        TagUsers updatedTagUsers = tagUsersRepository.findById(tagUsers.getId()).get();
        // Disconnect from session so that the updates on updatedTagUsers are not directly saved in db
        em.detach(updatedTagUsers);
        updatedTagUsers
            .tagId(UPDATED_TAG_ID)
            .userId(UPDATED_USER_ID)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL);
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(updatedTagUsers);

        restTagUsersMockMvc.perform(put("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isOk());

        // Validate the TagUsers in the database
        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeUpdate);
        TagUsers testTagUsers = tagUsersList.get(tagUsersList.size() - 1);
        assertThat(testTagUsers.getTagId()).isEqualTo(UPDATED_TAG_ID);
        assertThat(testTagUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTagUsers.getNotificationLevel()).isEqualTo(UPDATED_NOTIFICATION_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingTagUsers() throws Exception {
        int databaseSizeBeforeUpdate = tagUsersRepository.findAll().size();

        // Create the TagUsers
        TagUsersDTO tagUsersDTO = tagUsersMapper.toDto(tagUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagUsersMockMvc.perform(put("/api/tag-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagUsers in the database
        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagUsers() throws Exception {
        // Initialize the database
        tagUsersRepository.saveAndFlush(tagUsers);

        int databaseSizeBeforeDelete = tagUsersRepository.findAll().size();

        // Delete the tagUsers
        restTagUsersMockMvc.perform(delete("/api/tag-users/{id}", tagUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagUsers> tagUsersList = tagUsersRepository.findAll();
        assertThat(tagUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
