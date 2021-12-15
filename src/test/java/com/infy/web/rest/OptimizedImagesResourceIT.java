/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.OptimizedImages;
import com.infy.repository.OptimizedImagesRepository;
import com.infy.service.OptimizedImagesService;
import com.infy.service.dto.OptimizedImagesDTO;
import com.infy.service.mapper.OptimizedImagesMapper;

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
 * Integration tests for the {@link OptimizedImagesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OptimizedImagesResourceIT {

    private static final String DEFAULT_SHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_SHA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Long DEFAULT_UPLOAD_ID = 1L;
    private static final Long UPDATED_UPLOAD_ID = 2L;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_FILESIZE = 1;
    private static final Integer UPDATED_FILESIZE = 2;

    private static final String DEFAULT_ETAG = "AAAAAAAAAA";
    private static final String UPDATED_ETAG = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private OptimizedImagesRepository optimizedImagesRepository;

    @Autowired
    private OptimizedImagesMapper optimizedImagesMapper;

    @Autowired
    private OptimizedImagesService optimizedImagesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptimizedImagesMockMvc;

    private OptimizedImages optimizedImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptimizedImages createEntity(EntityManager em) {
        OptimizedImages optimizedImages = new OptimizedImages()
            .sha1(DEFAULT_SHA_1)
            .extension(DEFAULT_EXTENSION)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .uploadId(DEFAULT_UPLOAD_ID)
            .url(DEFAULT_URL)
            .filesize(DEFAULT_FILESIZE)
            .etag(DEFAULT_ETAG)
            .version(DEFAULT_VERSION);
        return optimizedImages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptimizedImages createUpdatedEntity(EntityManager em) {
        OptimizedImages optimizedImages = new OptimizedImages()
            .sha1(UPDATED_SHA_1)
            .extension(UPDATED_EXTENSION)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .uploadId(UPDATED_UPLOAD_ID)
            .url(UPDATED_URL)
            .filesize(UPDATED_FILESIZE)
            .etag(UPDATED_ETAG)
            .version(UPDATED_VERSION);
        return optimizedImages;
    }

    @BeforeEach
    public void initTest() {
        optimizedImages = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptimizedImages() throws Exception {
        int databaseSizeBeforeCreate = optimizedImagesRepository.findAll().size();
        // Create the OptimizedImages
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);
        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isCreated());

        // Validate the OptimizedImages in the database
        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeCreate + 1);
        OptimizedImages testOptimizedImages = optimizedImagesList.get(optimizedImagesList.size() - 1);
        assertThat(testOptimizedImages.getSha1()).isEqualTo(DEFAULT_SHA_1);
        assertThat(testOptimizedImages.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testOptimizedImages.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testOptimizedImages.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testOptimizedImages.getUploadId()).isEqualTo(DEFAULT_UPLOAD_ID);
        assertThat(testOptimizedImages.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testOptimizedImages.getFilesize()).isEqualTo(DEFAULT_FILESIZE);
        assertThat(testOptimizedImages.getEtag()).isEqualTo(DEFAULT_ETAG);
        assertThat(testOptimizedImages.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createOptimizedImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optimizedImagesRepository.findAll().size();

        // Create the OptimizedImages with an existing ID
        optimizedImages.setId(1L);
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptimizedImages in the database
        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSha1IsRequired() throws Exception {
        int databaseSizeBeforeTest = optimizedImagesRepository.findAll().size();
        // set the field null
        optimizedImages.setSha1(null);

        // Create the OptimizedImages, which fails.
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);


        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = optimizedImagesRepository.findAll().size();
        // set the field null
        optimizedImages.setExtension(null);

        // Create the OptimizedImages, which fails.
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);


        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = optimizedImagesRepository.findAll().size();
        // set the field null
        optimizedImages.setWidth(null);

        // Create the OptimizedImages, which fails.
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);


        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = optimizedImagesRepository.findAll().size();
        // set the field null
        optimizedImages.setHeight(null);

        // Create the OptimizedImages, which fails.
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);


        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUploadIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = optimizedImagesRepository.findAll().size();
        // set the field null
        optimizedImages.setUploadId(null);

        // Create the OptimizedImages, which fails.
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);


        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = optimizedImagesRepository.findAll().size();
        // set the field null
        optimizedImages.setUrl(null);

        // Create the OptimizedImages, which fails.
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);


        restOptimizedImagesMockMvc.perform(post("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOptimizedImages() throws Exception {
        // Initialize the database
        optimizedImagesRepository.saveAndFlush(optimizedImages);

        // Get all the optimizedImagesList
        restOptimizedImagesMockMvc.perform(get("/api/optimized-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optimizedImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].sha1").value(hasItem(DEFAULT_SHA_1)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].uploadId").value(hasItem(DEFAULT_UPLOAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE)))
            .andExpect(jsonPath("$.[*].etag").value(hasItem(DEFAULT_ETAG)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getOptimizedImages() throws Exception {
        // Initialize the database
        optimizedImagesRepository.saveAndFlush(optimizedImages);

        // Get the optimizedImages
        restOptimizedImagesMockMvc.perform(get("/api/optimized-images/{id}", optimizedImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optimizedImages.getId().intValue()))
            .andExpect(jsonPath("$.sha1").value(DEFAULT_SHA_1))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.uploadId").value(DEFAULT_UPLOAD_ID.intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.filesize").value(DEFAULT_FILESIZE))
            .andExpect(jsonPath("$.etag").value(DEFAULT_ETAG))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }
    @Test
    @Transactional
    public void getNonExistingOptimizedImages() throws Exception {
        // Get the optimizedImages
        restOptimizedImagesMockMvc.perform(get("/api/optimized-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptimizedImages() throws Exception {
        // Initialize the database
        optimizedImagesRepository.saveAndFlush(optimizedImages);

        int databaseSizeBeforeUpdate = optimizedImagesRepository.findAll().size();

        // Update the optimizedImages
        OptimizedImages updatedOptimizedImages = optimizedImagesRepository.findById(optimizedImages.getId()).get();
        // Disconnect from session so that the updates on updatedOptimizedImages are not directly saved in db
        em.detach(updatedOptimizedImages);
        updatedOptimizedImages
            .sha1(UPDATED_SHA_1)
            .extension(UPDATED_EXTENSION)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .uploadId(UPDATED_UPLOAD_ID)
            .url(UPDATED_URL)
            .filesize(UPDATED_FILESIZE)
            .etag(UPDATED_ETAG)
            .version(UPDATED_VERSION);
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(updatedOptimizedImages);

        restOptimizedImagesMockMvc.perform(put("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isOk());

        // Validate the OptimizedImages in the database
        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeUpdate);
        OptimizedImages testOptimizedImages = optimizedImagesList.get(optimizedImagesList.size() - 1);
        assertThat(testOptimizedImages.getSha1()).isEqualTo(UPDATED_SHA_1);
        assertThat(testOptimizedImages.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testOptimizedImages.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testOptimizedImages.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testOptimizedImages.getUploadId()).isEqualTo(UPDATED_UPLOAD_ID);
        assertThat(testOptimizedImages.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testOptimizedImages.getFilesize()).isEqualTo(UPDATED_FILESIZE);
        assertThat(testOptimizedImages.getEtag()).isEqualTo(UPDATED_ETAG);
        assertThat(testOptimizedImages.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingOptimizedImages() throws Exception {
        int databaseSizeBeforeUpdate = optimizedImagesRepository.findAll().size();

        // Create the OptimizedImages
        OptimizedImagesDTO optimizedImagesDTO = optimizedImagesMapper.toDto(optimizedImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptimizedImagesMockMvc.perform(put("/api/optimized-images").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optimizedImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptimizedImages in the database
        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOptimizedImages() throws Exception {
        // Initialize the database
        optimizedImagesRepository.saveAndFlush(optimizedImages);

        int databaseSizeBeforeDelete = optimizedImagesRepository.findAll().size();

        // Delete the optimizedImages
        restOptimizedImagesMockMvc.perform(delete("/api/optimized-images/{id}", optimizedImages.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptimizedImages> optimizedImagesList = optimizedImagesRepository.findAll();
        assertThat(optimizedImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
