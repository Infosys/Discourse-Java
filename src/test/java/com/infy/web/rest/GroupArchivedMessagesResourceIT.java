/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.GroupArchivedMessages;
import com.infy.repository.GroupArchivedMessagesRepository;
import com.infy.service.GroupArchivedMessagesService;
import com.infy.service.dto.GroupArchivedMessagesDTO;
import com.infy.service.mapper.GroupArchivedMessagesMapper;

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
 * Integration tests for the {@link GroupArchivedMessagesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupArchivedMessagesResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    @Autowired
    private GroupArchivedMessagesRepository groupArchivedMessagesRepository;

    @Autowired
    private GroupArchivedMessagesMapper groupArchivedMessagesMapper;

    @Autowired
    private GroupArchivedMessagesService groupArchivedMessagesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupArchivedMessagesMockMvc;

    private GroupArchivedMessages groupArchivedMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupArchivedMessages createEntity(EntityManager em) {
        GroupArchivedMessages groupArchivedMessages = new GroupArchivedMessages()
            .groupId(DEFAULT_GROUP_ID)
            .topicId(DEFAULT_TOPIC_ID);
        return groupArchivedMessages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupArchivedMessages createUpdatedEntity(EntityManager em) {
        GroupArchivedMessages groupArchivedMessages = new GroupArchivedMessages()
            .groupId(UPDATED_GROUP_ID)
            .topicId(UPDATED_TOPIC_ID);
        return groupArchivedMessages;
    }

    @BeforeEach
    public void initTest() {
        groupArchivedMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupArchivedMessages() throws Exception {
        int databaseSizeBeforeCreate = groupArchivedMessagesRepository.findAll().size();
        // Create the GroupArchivedMessages
        GroupArchivedMessagesDTO groupArchivedMessagesDTO = groupArchivedMessagesMapper.toDto(groupArchivedMessages);
        restGroupArchivedMessagesMockMvc.perform(post("/api/group-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupArchivedMessagesDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupArchivedMessages in the database
        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        GroupArchivedMessages testGroupArchivedMessages = groupArchivedMessagesList.get(groupArchivedMessagesList.size() - 1);
        assertThat(testGroupArchivedMessages.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupArchivedMessages.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
    }

    @Test
    @Transactional
    public void createGroupArchivedMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupArchivedMessagesRepository.findAll().size();

        // Create the GroupArchivedMessages with an existing ID
        groupArchivedMessages.setId(1L);
        GroupArchivedMessagesDTO groupArchivedMessagesDTO = groupArchivedMessagesMapper.toDto(groupArchivedMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupArchivedMessagesMockMvc.perform(post("/api/group-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupArchivedMessages in the database
        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupArchivedMessagesRepository.findAll().size();
        // set the field null
        groupArchivedMessages.setGroupId(null);

        // Create the GroupArchivedMessages, which fails.
        GroupArchivedMessagesDTO groupArchivedMessagesDTO = groupArchivedMessagesMapper.toDto(groupArchivedMessages);


        restGroupArchivedMessagesMockMvc.perform(post("/api/group-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupArchivedMessagesRepository.findAll().size();
        // set the field null
        groupArchivedMessages.setTopicId(null);

        // Create the GroupArchivedMessages, which fails.
        GroupArchivedMessagesDTO groupArchivedMessagesDTO = groupArchivedMessagesMapper.toDto(groupArchivedMessages);


        restGroupArchivedMessagesMockMvc.perform(post("/api/group-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupArchivedMessages() throws Exception {
        // Initialize the database
        groupArchivedMessagesRepository.saveAndFlush(groupArchivedMessages);

        // Get all the groupArchivedMessagesList
        restGroupArchivedMessagesMockMvc.perform(get("/api/group-archived-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupArchivedMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())));
    }

    @Test
    @Transactional
    public void getGroupArchivedMessages() throws Exception {
        // Initialize the database
        groupArchivedMessagesRepository.saveAndFlush(groupArchivedMessages);

        // Get the groupArchivedMessages
        restGroupArchivedMessagesMockMvc.perform(get("/api/group-archived-messages/{id}", groupArchivedMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupArchivedMessages.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupArchivedMessages() throws Exception {
        // Get the groupArchivedMessages
        restGroupArchivedMessagesMockMvc.perform(get("/api/group-archived-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupArchivedMessages() throws Exception {
        // Initialize the database
        groupArchivedMessagesRepository.saveAndFlush(groupArchivedMessages);

        int databaseSizeBeforeUpdate = groupArchivedMessagesRepository.findAll().size();

        // Update the groupArchivedMessages
        GroupArchivedMessages updatedGroupArchivedMessages = groupArchivedMessagesRepository.findById(groupArchivedMessages.getId()).get();
        // Disconnect from session so that the updates on updatedGroupArchivedMessages are not directly saved in db
        em.detach(updatedGroupArchivedMessages);
        updatedGroupArchivedMessages
            .groupId(UPDATED_GROUP_ID)
            .topicId(UPDATED_TOPIC_ID);
        GroupArchivedMessagesDTO groupArchivedMessagesDTO = groupArchivedMessagesMapper.toDto(updatedGroupArchivedMessages);

        restGroupArchivedMessagesMockMvc.perform(put("/api/group-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupArchivedMessagesDTO)))
            .andExpect(status().isOk());

        // Validate the GroupArchivedMessages in the database
        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeUpdate);
        GroupArchivedMessages testGroupArchivedMessages = groupArchivedMessagesList.get(groupArchivedMessagesList.size() - 1);
        assertThat(testGroupArchivedMessages.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupArchivedMessages.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupArchivedMessages() throws Exception {
        int databaseSizeBeforeUpdate = groupArchivedMessagesRepository.findAll().size();

        // Create the GroupArchivedMessages
        GroupArchivedMessagesDTO groupArchivedMessagesDTO = groupArchivedMessagesMapper.toDto(groupArchivedMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupArchivedMessagesMockMvc.perform(put("/api/group-archived-messages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupArchivedMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupArchivedMessages in the database
        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupArchivedMessages() throws Exception {
        // Initialize the database
        groupArchivedMessagesRepository.saveAndFlush(groupArchivedMessages);

        int databaseSizeBeforeDelete = groupArchivedMessagesRepository.findAll().size();

        // Delete the groupArchivedMessages
        restGroupArchivedMessagesMockMvc.perform(delete("/api/group-archived-messages/{id}", groupArchivedMessages.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupArchivedMessages> groupArchivedMessagesList = groupArchivedMessagesRepository.findAll();
        assertThat(groupArchivedMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
