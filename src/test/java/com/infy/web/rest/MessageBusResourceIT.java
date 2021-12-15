/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.MessageBus;
import com.infy.repository.MessageBusRepository;
import com.infy.service.MessageBusService;
import com.infy.service.dto.MessageBusDTO;
import com.infy.service.mapper.MessageBusMapper;

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
 * Integration tests for the {@link MessageBusResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class MessageBusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    @Autowired
    private MessageBusRepository messageBusRepository;

    @Autowired
    private MessageBusMapper messageBusMapper;

    @Autowired
    private MessageBusService messageBusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageBusMockMvc;

    private MessageBus messageBus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageBus createEntity(EntityManager em) {
        MessageBus messageBus = new MessageBus()
            .name(DEFAULT_NAME)
            .context(DEFAULT_CONTEXT)
            .data(DEFAULT_DATA);
        return messageBus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageBus createUpdatedEntity(EntityManager em) {
        MessageBus messageBus = new MessageBus()
            .name(UPDATED_NAME)
            .context(UPDATED_CONTEXT)
            .data(UPDATED_DATA);
        return messageBus;
    }

    @BeforeEach
    public void initTest() {
        messageBus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageBus() throws Exception {
        int databaseSizeBeforeCreate = messageBusRepository.findAll().size();
        // Create the MessageBus
        MessageBusDTO messageBusDTO = messageBusMapper.toDto(messageBus);
        restMessageBusMockMvc.perform(post("/api/message-buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageBusDTO)))
            .andExpect(status().isCreated());

        // Validate the MessageBus in the database
        List<MessageBus> messageBusList = messageBusRepository.findAll();
        assertThat(messageBusList).hasSize(databaseSizeBeforeCreate + 1);
        MessageBus testMessageBus = messageBusList.get(messageBusList.size() - 1);
        assertThat(testMessageBus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMessageBus.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testMessageBus.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createMessageBusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageBusRepository.findAll().size();

        // Create the MessageBus with an existing ID
        messageBus.setId(1L);
        MessageBusDTO messageBusDTO = messageBusMapper.toDto(messageBus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageBusMockMvc.perform(post("/api/message-buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageBusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageBus in the database
        List<MessageBus> messageBusList = messageBusRepository.findAll();
        assertThat(messageBusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMessageBuses() throws Exception {
        // Initialize the database
        messageBusRepository.saveAndFlush(messageBus);

        // Get all the messageBusList
        restMessageBusMockMvc.perform(get("/api/message-buses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageBus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getMessageBus() throws Exception {
        // Initialize the database
        messageBusRepository.saveAndFlush(messageBus);

        // Get the messageBus
        restMessageBusMockMvc.perform(get("/api/message-buses/{id}", messageBus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageBus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA));
    }
    @Test
    @Transactional
    public void getNonExistingMessageBus() throws Exception {
        // Get the messageBus
        restMessageBusMockMvc.perform(get("/api/message-buses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageBus() throws Exception {
        // Initialize the database
        messageBusRepository.saveAndFlush(messageBus);

        int databaseSizeBeforeUpdate = messageBusRepository.findAll().size();

        // Update the messageBus
        MessageBus updatedMessageBus = messageBusRepository.findById(messageBus.getId()).get();
        // Disconnect from session so that the updates on updatedMessageBus are not directly saved in db
        em.detach(updatedMessageBus);
        updatedMessageBus
            .name(UPDATED_NAME)
            .context(UPDATED_CONTEXT)
            .data(UPDATED_DATA);
        MessageBusDTO messageBusDTO = messageBusMapper.toDto(updatedMessageBus);

        restMessageBusMockMvc.perform(put("/api/message-buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageBusDTO)))
            .andExpect(status().isOk());

        // Validate the MessageBus in the database
        List<MessageBus> messageBusList = messageBusRepository.findAll();
        assertThat(messageBusList).hasSize(databaseSizeBeforeUpdate);
        MessageBus testMessageBus = messageBusList.get(messageBusList.size() - 1);
        assertThat(testMessageBus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMessageBus.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testMessageBus.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageBus() throws Exception {
        int databaseSizeBeforeUpdate = messageBusRepository.findAll().size();

        // Create the MessageBus
        MessageBusDTO messageBusDTO = messageBusMapper.toDto(messageBus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageBusMockMvc.perform(put("/api/message-buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageBusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageBus in the database
        List<MessageBus> messageBusList = messageBusRepository.findAll();
        assertThat(messageBusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessageBus() throws Exception {
        // Initialize the database
        messageBusRepository.saveAndFlush(messageBus);

        int databaseSizeBeforeDelete = messageBusRepository.findAll().size();

        // Delete the messageBus
        restMessageBusMockMvc.perform(delete("/api/message-buses/{id}", messageBus.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageBus> messageBusList = messageBusRepository.findAll();
        assertThat(messageBusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
