/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TextClassification;
import com.infy.repository.TextClassificationRepository;
import com.infy.service.TextClassificationService;
import com.infy.service.dto.TextClassificationDTO;
import com.infy.service.mapper.TextClassificationMapper;

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

import com.infy.domain.enumeration.TextClassificationType;
/**
 * Integration tests for the {@link TextClassificationResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TextClassificationResourceIT {

    private static final Long DEFAULT_CONTENT_ID = 1L;
    private static final Long UPDATED_CONTENT_ID = 2L;

    private static final Double DEFAULT_TOXICITY = 1D;
    private static final Double UPDATED_TOXICITY = 2D;

    private static final Double DEFAULT_SEVERE_TOXICITY = 1D;
    private static final Double UPDATED_SEVERE_TOXICITY = 2D;

    private static final Double DEFAULT_OBSCENE = 1D;
    private static final Double UPDATED_OBSCENE = 2D;

    private static final Double DEFAULT_THREAT = 1D;
    private static final Double UPDATED_THREAT = 2D;

    private static final Double DEFAULT_INSULT = 1D;
    private static final Double UPDATED_INSULT = 2D;

    private static final Double DEFAULT_IDENTITY_HATE = 1D;
    private static final Double UPDATED_IDENTITY_HATE = 2D;

    private static final TextClassificationType DEFAULT_TYPE = TextClassificationType.TOPIC;
    private static final TextClassificationType UPDATED_TYPE = TextClassificationType.POST;

    @Autowired
    private TextClassificationRepository textClassificationRepository;

    @Autowired
    private TextClassificationMapper textClassificationMapper;

    @Autowired
    private TextClassificationService textClassificationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTextClassificationMockMvc;

    private TextClassification textClassification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TextClassification createEntity(EntityManager em) {
        TextClassification textClassification = new TextClassification()
            .contentId(DEFAULT_CONTENT_ID)
            .toxicity(DEFAULT_TOXICITY)
            .severeToxicity(DEFAULT_SEVERE_TOXICITY)
            .obscene(DEFAULT_OBSCENE)
            .threat(DEFAULT_THREAT)
            .insult(DEFAULT_INSULT)
            .identityHate(DEFAULT_IDENTITY_HATE)
            .type(DEFAULT_TYPE);
        return textClassification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TextClassification createUpdatedEntity(EntityManager em) {
        TextClassification textClassification = new TextClassification()
            .contentId(UPDATED_CONTENT_ID)
            .toxicity(UPDATED_TOXICITY)
            .severeToxicity(UPDATED_SEVERE_TOXICITY)
            .obscene(UPDATED_OBSCENE)
            .threat(UPDATED_THREAT)
            .insult(UPDATED_INSULT)
            .identityHate(UPDATED_IDENTITY_HATE)
            .type(UPDATED_TYPE);
        return textClassification;
    }

    @BeforeEach
    public void initTest() {
        textClassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createTextClassification() throws Exception {
        int databaseSizeBeforeCreate = textClassificationRepository.findAll().size();
        // Create the TextClassification
        TextClassificationDTO textClassificationDTO = textClassificationMapper.toDto(textClassification);
        restTextClassificationMockMvc.perform(post("/api/text-classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(textClassificationDTO)))
            .andExpect(status().isCreated());

        // Validate the TextClassification in the database
        List<TextClassification> textClassificationList = textClassificationRepository.findAll();
        assertThat(textClassificationList).hasSize(databaseSizeBeforeCreate + 1);
        TextClassification testTextClassification = textClassificationList.get(textClassificationList.size() - 1);
        assertThat(testTextClassification.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
        assertThat(testTextClassification.getToxicity()).isEqualTo(DEFAULT_TOXICITY);
        assertThat(testTextClassification.getSevereToxicity()).isEqualTo(DEFAULT_SEVERE_TOXICITY);
        assertThat(testTextClassification.getObscene()).isEqualTo(DEFAULT_OBSCENE);
        assertThat(testTextClassification.getThreat()).isEqualTo(DEFAULT_THREAT);
        assertThat(testTextClassification.getInsult()).isEqualTo(DEFAULT_INSULT);
        assertThat(testTextClassification.getIdentityHate()).isEqualTo(DEFAULT_IDENTITY_HATE);
        assertThat(testTextClassification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTextClassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = textClassificationRepository.findAll().size();

        // Create the TextClassification with an existing ID
        textClassification.setId(1L);
        TextClassificationDTO textClassificationDTO = textClassificationMapper.toDto(textClassification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextClassificationMockMvc.perform(post("/api/text-classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(textClassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TextClassification in the database
        List<TextClassification> textClassificationList = textClassificationRepository.findAll();
        assertThat(textClassificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTextClassifications() throws Exception {
        // Initialize the database
        textClassificationRepository.saveAndFlush(textClassification);

        // Get all the textClassificationList
        restTextClassificationMockMvc.perform(get("/api/text-classifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].toxicity").value(hasItem(DEFAULT_TOXICITY.doubleValue())))
            .andExpect(jsonPath("$.[*].severeToxicity").value(hasItem(DEFAULT_SEVERE_TOXICITY.doubleValue())))
            .andExpect(jsonPath("$.[*].obscene").value(hasItem(DEFAULT_OBSCENE.doubleValue())))
            .andExpect(jsonPath("$.[*].threat").value(hasItem(DEFAULT_THREAT.doubleValue())))
            .andExpect(jsonPath("$.[*].insult").value(hasItem(DEFAULT_INSULT.doubleValue())))
            .andExpect(jsonPath("$.[*].identityHate").value(hasItem(DEFAULT_IDENTITY_HATE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTextClassification() throws Exception {
        // Initialize the database
        textClassificationRepository.saveAndFlush(textClassification);

        // Get the textClassification
        restTextClassificationMockMvc.perform(get("/api/text-classifications/{id}", textClassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(textClassification.getId().intValue()))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.intValue()))
            .andExpect(jsonPath("$.toxicity").value(DEFAULT_TOXICITY.doubleValue()))
            .andExpect(jsonPath("$.severeToxicity").value(DEFAULT_SEVERE_TOXICITY.doubleValue()))
            .andExpect(jsonPath("$.obscene").value(DEFAULT_OBSCENE.doubleValue()))
            .andExpect(jsonPath("$.threat").value(DEFAULT_THREAT.doubleValue()))
            .andExpect(jsonPath("$.insult").value(DEFAULT_INSULT.doubleValue()))
            .andExpect(jsonPath("$.identityHate").value(DEFAULT_IDENTITY_HATE.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTextClassification() throws Exception {
        // Get the textClassification
        restTextClassificationMockMvc.perform(get("/api/text-classifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTextClassification() throws Exception {
        // Initialize the database
        textClassificationRepository.saveAndFlush(textClassification);

        int databaseSizeBeforeUpdate = textClassificationRepository.findAll().size();

        // Update the textClassification
        TextClassification updatedTextClassification = textClassificationRepository.findById(textClassification.getId()).get();
        // Disconnect from session so that the updates on updatedTextClassification are not directly saved in db
        em.detach(updatedTextClassification);
        updatedTextClassification
            .contentId(UPDATED_CONTENT_ID)
            .toxicity(UPDATED_TOXICITY)
            .severeToxicity(UPDATED_SEVERE_TOXICITY)
            .obscene(UPDATED_OBSCENE)
            .threat(UPDATED_THREAT)
            .insult(UPDATED_INSULT)
            .identityHate(UPDATED_IDENTITY_HATE)
            .type(UPDATED_TYPE);
        TextClassificationDTO textClassificationDTO = textClassificationMapper.toDto(updatedTextClassification);

        restTextClassificationMockMvc.perform(put("/api/text-classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(textClassificationDTO)))
            .andExpect(status().isOk());

        // Validate the TextClassification in the database
        List<TextClassification> textClassificationList = textClassificationRepository.findAll();
        assertThat(textClassificationList).hasSize(databaseSizeBeforeUpdate);
        TextClassification testTextClassification = textClassificationList.get(textClassificationList.size() - 1);
        assertThat(testTextClassification.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
        assertThat(testTextClassification.getToxicity()).isEqualTo(UPDATED_TOXICITY);
        assertThat(testTextClassification.getSevereToxicity()).isEqualTo(UPDATED_SEVERE_TOXICITY);
        assertThat(testTextClassification.getObscene()).isEqualTo(UPDATED_OBSCENE);
        assertThat(testTextClassification.getThreat()).isEqualTo(UPDATED_THREAT);
        assertThat(testTextClassification.getInsult()).isEqualTo(UPDATED_INSULT);
        assertThat(testTextClassification.getIdentityHate()).isEqualTo(UPDATED_IDENTITY_HATE);
        assertThat(testTextClassification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTextClassification() throws Exception {
        int databaseSizeBeforeUpdate = textClassificationRepository.findAll().size();

        // Create the TextClassification
        TextClassificationDTO textClassificationDTO = textClassificationMapper.toDto(textClassification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextClassificationMockMvc.perform(put("/api/text-classifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(textClassificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TextClassification in the database
        List<TextClassification> textClassificationList = textClassificationRepository.findAll();
        assertThat(textClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTextClassification() throws Exception {
        // Initialize the database
        textClassificationRepository.saveAndFlush(textClassification);

        int databaseSizeBeforeDelete = textClassificationRepository.findAll().size();

        // Delete the textClassification
        restTextClassificationMockMvc.perform(delete("/api/text-classifications/{id}", textClassification.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TextClassification> textClassificationList = textClassificationRepository.findAll();
        assertThat(textClassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
