/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.IncomingEmails;
import com.infy.repository.IncomingEmailsRepository;
import com.infy.service.IncomingEmailsService;
import com.infy.service.dto.IncomingEmailsDTO;
import com.infy.service.mapper.IncomingEmailsMapper;

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
 * Integration tests for the {@link IncomingEmailsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IncomingEmailsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_RAW = "AAAAAAAAAA";
    private static final String UPDATED_RAW = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_ERROR = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FROM_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TO_ADDRESSES = "AAAAAAAAAA";
    private static final String UPDATED_TO_ADDRESSES = "BBBBBBBBBB";

    private static final String DEFAULT_CC_ADDRESSES = "AAAAAAAAAA";
    private static final String UPDATED_CC_ADDRESSES = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AUTO_GENERATED = false;
    private static final Boolean UPDATED_IS_AUTO_GENERATED = true;

    private static final Boolean DEFAULT_IS_BOUNCE = false;
    private static final Boolean UPDATED_IS_BOUNCE = true;

    private static final Integer DEFAULT_IMAP_UID_VALIDITY = 1;
    private static final Integer UPDATED_IMAP_UID_VALIDITY = 2;

    private static final Integer DEFAULT_IMAP_UID = 1;
    private static final Integer UPDATED_IMAP_UID = 2;

    private static final Boolean DEFAULT_IMAP_SYNC = false;
    private static final Boolean UPDATED_IMAP_SYNC = true;

    private static final Long DEFAULT_IMAP_GROUP_ID = 1L;
    private static final Long UPDATED_IMAP_GROUP_ID = 2L;

    private static final Boolean DEFAULT_IMAP_MISSING = false;
    private static final Boolean UPDATED_IMAP_MISSING = true;

    private static final Integer DEFAULT_CREATED_VIA = 1;
    private static final Integer UPDATED_CREATED_VIA = 2;

    @Autowired
    private IncomingEmailsRepository incomingEmailsRepository;

    @Autowired
    private IncomingEmailsMapper incomingEmailsMapper;

    @Autowired
    private IncomingEmailsService incomingEmailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomingEmailsMockMvc;

    private IncomingEmails incomingEmails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingEmails createEntity(EntityManager em) {
        IncomingEmails incomingEmails = new IncomingEmails()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .postId(DEFAULT_POST_ID)
            .raw(DEFAULT_RAW)
            .error(DEFAULT_ERROR)
            .messageId(DEFAULT_MESSAGE_ID)
            .fromAddress(DEFAULT_FROM_ADDRESS)
            .toAddresses(DEFAULT_TO_ADDRESSES)
            .ccAddresses(DEFAULT_CC_ADDRESSES)
            .subject(DEFAULT_SUBJECT)
            .rejectionMessage(DEFAULT_REJECTION_MESSAGE)
            .isAutoGenerated(DEFAULT_IS_AUTO_GENERATED)
            .isBounce(DEFAULT_IS_BOUNCE)
            .imapUidValidity(DEFAULT_IMAP_UID_VALIDITY)
            .imapUid(DEFAULT_IMAP_UID)
            .imapSync(DEFAULT_IMAP_SYNC)
            .imapGroupId(DEFAULT_IMAP_GROUP_ID)
            .imapMissing(DEFAULT_IMAP_MISSING)
            .createdVia(DEFAULT_CREATED_VIA);
        return incomingEmails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomingEmails createUpdatedEntity(EntityManager em) {
        IncomingEmails incomingEmails = new IncomingEmails()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .raw(UPDATED_RAW)
            .error(UPDATED_ERROR)
            .messageId(UPDATED_MESSAGE_ID)
            .fromAddress(UPDATED_FROM_ADDRESS)
            .toAddresses(UPDATED_TO_ADDRESSES)
            .ccAddresses(UPDATED_CC_ADDRESSES)
            .subject(UPDATED_SUBJECT)
            .rejectionMessage(UPDATED_REJECTION_MESSAGE)
            .isAutoGenerated(UPDATED_IS_AUTO_GENERATED)
            .isBounce(UPDATED_IS_BOUNCE)
            .imapUidValidity(UPDATED_IMAP_UID_VALIDITY)
            .imapUid(UPDATED_IMAP_UID)
            .imapSync(UPDATED_IMAP_SYNC)
            .imapGroupId(UPDATED_IMAP_GROUP_ID)
            .imapMissing(UPDATED_IMAP_MISSING)
            .createdVia(UPDATED_CREATED_VIA);
        return incomingEmails;
    }

    @BeforeEach
    public void initTest() {
        incomingEmails = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomingEmails() throws Exception {
        int databaseSizeBeforeCreate = incomingEmailsRepository.findAll().size();
        // Create the IncomingEmails
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(incomingEmails);
        restIncomingEmailsMockMvc.perform(post("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomingEmails in the database
        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeCreate + 1);
        IncomingEmails testIncomingEmails = incomingEmailsList.get(incomingEmailsList.size() - 1);
        assertThat(testIncomingEmails.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testIncomingEmails.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testIncomingEmails.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testIncomingEmails.getRaw()).isEqualTo(DEFAULT_RAW);
        assertThat(testIncomingEmails.getError()).isEqualTo(DEFAULT_ERROR);
        assertThat(testIncomingEmails.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testIncomingEmails.getFromAddress()).isEqualTo(DEFAULT_FROM_ADDRESS);
        assertThat(testIncomingEmails.getToAddresses()).isEqualTo(DEFAULT_TO_ADDRESSES);
        assertThat(testIncomingEmails.getCcAddresses()).isEqualTo(DEFAULT_CC_ADDRESSES);
        assertThat(testIncomingEmails.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testIncomingEmails.getRejectionMessage()).isEqualTo(DEFAULT_REJECTION_MESSAGE);
        assertThat(testIncomingEmails.isIsAutoGenerated()).isEqualTo(DEFAULT_IS_AUTO_GENERATED);
        assertThat(testIncomingEmails.isIsBounce()).isEqualTo(DEFAULT_IS_BOUNCE);
        assertThat(testIncomingEmails.getImapUidValidity()).isEqualTo(DEFAULT_IMAP_UID_VALIDITY);
        assertThat(testIncomingEmails.getImapUid()).isEqualTo(DEFAULT_IMAP_UID);
        assertThat(testIncomingEmails.isImapSync()).isEqualTo(DEFAULT_IMAP_SYNC);
        assertThat(testIncomingEmails.getImapGroupId()).isEqualTo(DEFAULT_IMAP_GROUP_ID);
        assertThat(testIncomingEmails.isImapMissing()).isEqualTo(DEFAULT_IMAP_MISSING);
        assertThat(testIncomingEmails.getCreatedVia()).isEqualTo(DEFAULT_CREATED_VIA);
    }

    @Test
    @Transactional
    public void createIncomingEmailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomingEmailsRepository.findAll().size();

        // Create the IncomingEmails with an existing ID
        incomingEmails.setId(1L);
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(incomingEmails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomingEmailsMockMvc.perform(post("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingEmails in the database
        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsBounceIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingEmailsRepository.findAll().size();
        // set the field null
        incomingEmails.setIsBounce(null);

        // Create the IncomingEmails, which fails.
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(incomingEmails);


        restIncomingEmailsMockMvc.perform(post("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImapMissingIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingEmailsRepository.findAll().size();
        // set the field null
        incomingEmails.setImapMissing(null);

        // Create the IncomingEmails, which fails.
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(incomingEmails);


        restIncomingEmailsMockMvc.perform(post("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedViaIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomingEmailsRepository.findAll().size();
        // set the field null
        incomingEmails.setCreatedVia(null);

        // Create the IncomingEmails, which fails.
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(incomingEmails);


        restIncomingEmailsMockMvc.perform(post("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isBadRequest());

        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomingEmails() throws Exception {
        // Initialize the database
        incomingEmailsRepository.saveAndFlush(incomingEmails);

        // Get all the incomingEmailsList
        restIncomingEmailsMockMvc.perform(get("/api/incoming-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomingEmails.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].raw").value(hasItem(DEFAULT_RAW)))
            .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR)))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID)))
            .andExpect(jsonPath("$.[*].fromAddress").value(hasItem(DEFAULT_FROM_ADDRESS)))
            .andExpect(jsonPath("$.[*].toAddresses").value(hasItem(DEFAULT_TO_ADDRESSES)))
            .andExpect(jsonPath("$.[*].ccAddresses").value(hasItem(DEFAULT_CC_ADDRESSES)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].rejectionMessage").value(hasItem(DEFAULT_REJECTION_MESSAGE)))
            .andExpect(jsonPath("$.[*].isAutoGenerated").value(hasItem(DEFAULT_IS_AUTO_GENERATED.booleanValue())))
            .andExpect(jsonPath("$.[*].isBounce").value(hasItem(DEFAULT_IS_BOUNCE.booleanValue())))
            .andExpect(jsonPath("$.[*].imapUidValidity").value(hasItem(DEFAULT_IMAP_UID_VALIDITY)))
            .andExpect(jsonPath("$.[*].imapUid").value(hasItem(DEFAULT_IMAP_UID)))
            .andExpect(jsonPath("$.[*].imapSync").value(hasItem(DEFAULT_IMAP_SYNC.booleanValue())))
            .andExpect(jsonPath("$.[*].imapGroupId").value(hasItem(DEFAULT_IMAP_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].imapMissing").value(hasItem(DEFAULT_IMAP_MISSING.booleanValue())))
            .andExpect(jsonPath("$.[*].createdVia").value(hasItem(DEFAULT_CREATED_VIA)));
    }

    @Test
    @Transactional
    public void getIncomingEmails() throws Exception {
        // Initialize the database
        incomingEmailsRepository.saveAndFlush(incomingEmails);

        // Get the incomingEmails
        restIncomingEmailsMockMvc.perform(get("/api/incoming-emails/{id}", incomingEmails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomingEmails.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.raw").value(DEFAULT_RAW))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID))
            .andExpect(jsonPath("$.fromAddress").value(DEFAULT_FROM_ADDRESS))
            .andExpect(jsonPath("$.toAddresses").value(DEFAULT_TO_ADDRESSES))
            .andExpect(jsonPath("$.ccAddresses").value(DEFAULT_CC_ADDRESSES))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.rejectionMessage").value(DEFAULT_REJECTION_MESSAGE))
            .andExpect(jsonPath("$.isAutoGenerated").value(DEFAULT_IS_AUTO_GENERATED.booleanValue()))
            .andExpect(jsonPath("$.isBounce").value(DEFAULT_IS_BOUNCE.booleanValue()))
            .andExpect(jsonPath("$.imapUidValidity").value(DEFAULT_IMAP_UID_VALIDITY))
            .andExpect(jsonPath("$.imapUid").value(DEFAULT_IMAP_UID))
            .andExpect(jsonPath("$.imapSync").value(DEFAULT_IMAP_SYNC.booleanValue()))
            .andExpect(jsonPath("$.imapGroupId").value(DEFAULT_IMAP_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.imapMissing").value(DEFAULT_IMAP_MISSING.booleanValue()))
            .andExpect(jsonPath("$.createdVia").value(DEFAULT_CREATED_VIA));
    }
    @Test
    @Transactional
    public void getNonExistingIncomingEmails() throws Exception {
        // Get the incomingEmails
        restIncomingEmailsMockMvc.perform(get("/api/incoming-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomingEmails() throws Exception {
        // Initialize the database
        incomingEmailsRepository.saveAndFlush(incomingEmails);

        int databaseSizeBeforeUpdate = incomingEmailsRepository.findAll().size();

        // Update the incomingEmails
        IncomingEmails updatedIncomingEmails = incomingEmailsRepository.findById(incomingEmails.getId()).get();
        // Disconnect from session so that the updates on updatedIncomingEmails are not directly saved in db
        em.detach(updatedIncomingEmails);
        updatedIncomingEmails
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .raw(UPDATED_RAW)
            .error(UPDATED_ERROR)
            .messageId(UPDATED_MESSAGE_ID)
            .fromAddress(UPDATED_FROM_ADDRESS)
            .toAddresses(UPDATED_TO_ADDRESSES)
            .ccAddresses(UPDATED_CC_ADDRESSES)
            .subject(UPDATED_SUBJECT)
            .rejectionMessage(UPDATED_REJECTION_MESSAGE)
            .isAutoGenerated(UPDATED_IS_AUTO_GENERATED)
            .isBounce(UPDATED_IS_BOUNCE)
            .imapUidValidity(UPDATED_IMAP_UID_VALIDITY)
            .imapUid(UPDATED_IMAP_UID)
            .imapSync(UPDATED_IMAP_SYNC)
            .imapGroupId(UPDATED_IMAP_GROUP_ID)
            .imapMissing(UPDATED_IMAP_MISSING)
            .createdVia(UPDATED_CREATED_VIA);
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(updatedIncomingEmails);

        restIncomingEmailsMockMvc.perform(put("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isOk());

        // Validate the IncomingEmails in the database
        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeUpdate);
        IncomingEmails testIncomingEmails = incomingEmailsList.get(incomingEmailsList.size() - 1);
        assertThat(testIncomingEmails.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testIncomingEmails.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testIncomingEmails.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testIncomingEmails.getRaw()).isEqualTo(UPDATED_RAW);
        assertThat(testIncomingEmails.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testIncomingEmails.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testIncomingEmails.getFromAddress()).isEqualTo(UPDATED_FROM_ADDRESS);
        assertThat(testIncomingEmails.getToAddresses()).isEqualTo(UPDATED_TO_ADDRESSES);
        assertThat(testIncomingEmails.getCcAddresses()).isEqualTo(UPDATED_CC_ADDRESSES);
        assertThat(testIncomingEmails.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testIncomingEmails.getRejectionMessage()).isEqualTo(UPDATED_REJECTION_MESSAGE);
        assertThat(testIncomingEmails.isIsAutoGenerated()).isEqualTo(UPDATED_IS_AUTO_GENERATED);
        assertThat(testIncomingEmails.isIsBounce()).isEqualTo(UPDATED_IS_BOUNCE);
        assertThat(testIncomingEmails.getImapUidValidity()).isEqualTo(UPDATED_IMAP_UID_VALIDITY);
        assertThat(testIncomingEmails.getImapUid()).isEqualTo(UPDATED_IMAP_UID);
        assertThat(testIncomingEmails.isImapSync()).isEqualTo(UPDATED_IMAP_SYNC);
        assertThat(testIncomingEmails.getImapGroupId()).isEqualTo(UPDATED_IMAP_GROUP_ID);
        assertThat(testIncomingEmails.isImapMissing()).isEqualTo(UPDATED_IMAP_MISSING);
        assertThat(testIncomingEmails.getCreatedVia()).isEqualTo(UPDATED_CREATED_VIA);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomingEmails() throws Exception {
        int databaseSizeBeforeUpdate = incomingEmailsRepository.findAll().size();

        // Create the IncomingEmails
        IncomingEmailsDTO incomingEmailsDTO = incomingEmailsMapper.toDto(incomingEmails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomingEmailsMockMvc.perform(put("/api/incoming-emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomingEmailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomingEmails in the database
        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomingEmails() throws Exception {
        // Initialize the database
        incomingEmailsRepository.saveAndFlush(incomingEmails);

        int databaseSizeBeforeDelete = incomingEmailsRepository.findAll().size();

        // Delete the incomingEmails
        restIncomingEmailsMockMvc.perform(delete("/api/incoming-emails/{id}", incomingEmails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncomingEmails> incomingEmailsList = incomingEmailsRepository.findAll();
        assertThat(incomingEmailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
