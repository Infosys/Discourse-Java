/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.UnsubscribeKeys;
import com.infy.repository.UnsubscribeKeysRepository;
import com.infy.service.UnsubscribeKeysService;
import com.infy.service.dto.UnsubscribeKeysDTO;
import com.infy.service.mapper.UnsubscribeKeysMapper;

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
 * Integration tests for the {@link UnsubscribeKeysResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UnsubscribeKeysResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNSUBSCRIBE_KEY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_UNSUBSCRIBE_KEY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    @Autowired
    private UnsubscribeKeysRepository unsubscribeKeysRepository;

    @Autowired
    private UnsubscribeKeysMapper unsubscribeKeysMapper;

    @Autowired
    private UnsubscribeKeysService unsubscribeKeysService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnsubscribeKeysMockMvc;

    private UnsubscribeKeys unsubscribeKeys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnsubscribeKeys createEntity(EntityManager em) {
        UnsubscribeKeys unsubscribeKeys = new UnsubscribeKeys()
            .key(DEFAULT_KEY)
            .userId(DEFAULT_USER_ID)
            .unsubscribeKeyType(DEFAULT_UNSUBSCRIBE_KEY_TYPE)
            .topicId(DEFAULT_TOPIC_ID)
            .postId(DEFAULT_POST_ID);
        return unsubscribeKeys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnsubscribeKeys createUpdatedEntity(EntityManager em) {
        UnsubscribeKeys unsubscribeKeys = new UnsubscribeKeys()
            .key(UPDATED_KEY)
            .userId(UPDATED_USER_ID)
            .unsubscribeKeyType(UPDATED_UNSUBSCRIBE_KEY_TYPE)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID);
        return unsubscribeKeys;
    }

    @BeforeEach
    public void initTest() {
        unsubscribeKeys = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnsubscribeKeys() throws Exception {
        int databaseSizeBeforeCreate = unsubscribeKeysRepository.findAll().size();
        // Create the UnsubscribeKeys
        UnsubscribeKeysDTO unsubscribeKeysDTO = unsubscribeKeysMapper.toDto(unsubscribeKeys);
        restUnsubscribeKeysMockMvc.perform(post("/api/unsubscribe-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unsubscribeKeysDTO)))
            .andExpect(status().isCreated());

        // Validate the UnsubscribeKeys in the database
        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeCreate + 1);
        UnsubscribeKeys testUnsubscribeKeys = unsubscribeKeysList.get(unsubscribeKeysList.size() - 1);
        assertThat(testUnsubscribeKeys.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testUnsubscribeKeys.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUnsubscribeKeys.getUnsubscribeKeyType()).isEqualTo(DEFAULT_UNSUBSCRIBE_KEY_TYPE);
        assertThat(testUnsubscribeKeys.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testUnsubscribeKeys.getPostId()).isEqualTo(DEFAULT_POST_ID);
    }

    @Test
    @Transactional
    public void createUnsubscribeKeysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unsubscribeKeysRepository.findAll().size();

        // Create the UnsubscribeKeys with an existing ID
        unsubscribeKeys.setId(1L);
        UnsubscribeKeysDTO unsubscribeKeysDTO = unsubscribeKeysMapper.toDto(unsubscribeKeys);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnsubscribeKeysMockMvc.perform(post("/api/unsubscribe-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unsubscribeKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnsubscribeKeys in the database
        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = unsubscribeKeysRepository.findAll().size();
        // set the field null
        unsubscribeKeys.setKey(null);

        // Create the UnsubscribeKeys, which fails.
        UnsubscribeKeysDTO unsubscribeKeysDTO = unsubscribeKeysMapper.toDto(unsubscribeKeys);


        restUnsubscribeKeysMockMvc.perform(post("/api/unsubscribe-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unsubscribeKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = unsubscribeKeysRepository.findAll().size();
        // set the field null
        unsubscribeKeys.setUserId(null);

        // Create the UnsubscribeKeys, which fails.
        UnsubscribeKeysDTO unsubscribeKeysDTO = unsubscribeKeysMapper.toDto(unsubscribeKeys);


        restUnsubscribeKeysMockMvc.perform(post("/api/unsubscribe-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unsubscribeKeysDTO)))
            .andExpect(status().isBadRequest());

        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnsubscribeKeys() throws Exception {
        // Initialize the database
        unsubscribeKeysRepository.saveAndFlush(unsubscribeKeys);

        // Get all the unsubscribeKeysList
        restUnsubscribeKeysMockMvc.perform(get("/api/unsubscribe-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unsubscribeKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].unsubscribeKeyType").value(hasItem(DEFAULT_UNSUBSCRIBE_KEY_TYPE)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUnsubscribeKeys() throws Exception {
        // Initialize the database
        unsubscribeKeysRepository.saveAndFlush(unsubscribeKeys);

        // Get the unsubscribeKeys
        restUnsubscribeKeysMockMvc.perform(get("/api/unsubscribe-keys/{id}", unsubscribeKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unsubscribeKeys.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.unsubscribeKeyType").value(DEFAULT_UNSUBSCRIBE_KEY_TYPE))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUnsubscribeKeys() throws Exception {
        // Get the unsubscribeKeys
        restUnsubscribeKeysMockMvc.perform(get("/api/unsubscribe-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnsubscribeKeys() throws Exception {
        // Initialize the database
        unsubscribeKeysRepository.saveAndFlush(unsubscribeKeys);

        int databaseSizeBeforeUpdate = unsubscribeKeysRepository.findAll().size();

        // Update the unsubscribeKeys
        UnsubscribeKeys updatedUnsubscribeKeys = unsubscribeKeysRepository.findById(unsubscribeKeys.getId()).get();
        // Disconnect from session so that the updates on updatedUnsubscribeKeys are not directly saved in db
        em.detach(updatedUnsubscribeKeys);
        updatedUnsubscribeKeys
            .key(UPDATED_KEY)
            .userId(UPDATED_USER_ID)
            .unsubscribeKeyType(UPDATED_UNSUBSCRIBE_KEY_TYPE)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID);
        UnsubscribeKeysDTO unsubscribeKeysDTO = unsubscribeKeysMapper.toDto(updatedUnsubscribeKeys);

        restUnsubscribeKeysMockMvc.perform(put("/api/unsubscribe-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unsubscribeKeysDTO)))
            .andExpect(status().isOk());

        // Validate the UnsubscribeKeys in the database
        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeUpdate);
        UnsubscribeKeys testUnsubscribeKeys = unsubscribeKeysList.get(unsubscribeKeysList.size() - 1);
        assertThat(testUnsubscribeKeys.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testUnsubscribeKeys.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUnsubscribeKeys.getUnsubscribeKeyType()).isEqualTo(UPDATED_UNSUBSCRIBE_KEY_TYPE);
        assertThat(testUnsubscribeKeys.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testUnsubscribeKeys.getPostId()).isEqualTo(UPDATED_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUnsubscribeKeys() throws Exception {
        int databaseSizeBeforeUpdate = unsubscribeKeysRepository.findAll().size();

        // Create the UnsubscribeKeys
        UnsubscribeKeysDTO unsubscribeKeysDTO = unsubscribeKeysMapper.toDto(unsubscribeKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnsubscribeKeysMockMvc.perform(put("/api/unsubscribe-keys").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unsubscribeKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnsubscribeKeys in the database
        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnsubscribeKeys() throws Exception {
        // Initialize the database
        unsubscribeKeysRepository.saveAndFlush(unsubscribeKeys);

        int databaseSizeBeforeDelete = unsubscribeKeysRepository.findAll().size();

        // Delete the unsubscribeKeys
        restUnsubscribeKeysMockMvc.perform(delete("/api/unsubscribe-keys/{id}", unsubscribeKeys.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnsubscribeKeys> unsubscribeKeysList = unsubscribeKeysRepository.findAll();
        assertThat(unsubscribeKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
