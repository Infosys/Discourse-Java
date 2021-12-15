/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Uploads;
import com.infy.repository.UploadsRepository;
import com.infy.service.UploadsService;
import com.infy.service.dto.UploadsDTO;
import com.infy.service.mapper.UploadsMapper;

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
 * Integration tests for the {@link UploadsResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UploadsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_FILENAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FILESIZE = 1;
    private static final Integer UPDATED_FILESIZE = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_SHA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_RETAIN_HOURS = 1;
    private static final Integer UPDATED_RETAIN_HOURS = 2;

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_THUMBNAIL_WIDTH = 1;
    private static final Integer UPDATED_THUMBNAIL_WIDTH = 2;

    private static final Integer DEFAULT_THUMBNAIL_HEIGHT = 1;
    private static final Integer UPDATED_THUMBNAIL_HEIGHT = 2;

    private static final String DEFAULT_ETAG = "AAAAAAAAAA";
    private static final String UPDATED_ETAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SECURE = false;
    private static final Boolean UPDATED_SECURE = true;

    private static final Long DEFAULT_ACCESS_CONTROL_POST_ID = 1L;
    private static final Long UPDATED_ACCESS_CONTROL_POST_ID = 2L;

    private static final String DEFAULT_ORIGINAL_SHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_SHA_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANIMATED = false;
    private static final Boolean UPDATED_ANIMATED = true;

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final Integer DEFAULT_VERIFICATION_STATUS = 1;
    private static final Integer UPDATED_VERIFICATION_STATUS = 2;

    private static final Instant DEFAULT_SECURITY_LAST_CHANGED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SECURITY_LAST_CHANGED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SECURITY_LAST_CHANGED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_LAST_CHANGED_REASON = "BBBBBBBBBB";

    @Autowired
    private UploadsRepository uploadsRepository;

    @Autowired
    private UploadsMapper uploadsMapper;

    @Autowired
    private UploadsService uploadsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUploadsMockMvc;

    private Uploads uploads;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uploads createEntity(EntityManager em) {
        Uploads uploads = new Uploads()
            .userId(DEFAULT_USER_ID)
            .originalFilename(DEFAULT_ORIGINAL_FILENAME)
            .filesize(DEFAULT_FILESIZE)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .url(DEFAULT_URL)
            .sha1(DEFAULT_SHA_1)
            .origin(DEFAULT_ORIGIN)
            .retainHours(DEFAULT_RETAIN_HOURS)
            .extension(DEFAULT_EXTENSION)
            .thumbnailWidth(DEFAULT_THUMBNAIL_WIDTH)
            .thumbnailHeight(DEFAULT_THUMBNAIL_HEIGHT)
            .etag(DEFAULT_ETAG)
            .secure(DEFAULT_SECURE)
            .accessControlPostId(DEFAULT_ACCESS_CONTROL_POST_ID)
            .originalSha1(DEFAULT_ORIGINAL_SHA_1)
            .animated(DEFAULT_ANIMATED)
            .verified(DEFAULT_VERIFIED)
            .verificationStatus(DEFAULT_VERIFICATION_STATUS)
            .securityLastChangedAt(DEFAULT_SECURITY_LAST_CHANGED_AT)
            .securityLastChangedReason(DEFAULT_SECURITY_LAST_CHANGED_REASON);
        return uploads;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uploads createUpdatedEntity(EntityManager em) {
        Uploads uploads = new Uploads()
            .userId(UPDATED_USER_ID)
            .originalFilename(UPDATED_ORIGINAL_FILENAME)
            .filesize(UPDATED_FILESIZE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .url(UPDATED_URL)
            .sha1(UPDATED_SHA_1)
            .origin(UPDATED_ORIGIN)
            .retainHours(UPDATED_RETAIN_HOURS)
            .extension(UPDATED_EXTENSION)
            .thumbnailWidth(UPDATED_THUMBNAIL_WIDTH)
            .thumbnailHeight(UPDATED_THUMBNAIL_HEIGHT)
            .etag(UPDATED_ETAG)
            .secure(UPDATED_SECURE)
            .accessControlPostId(UPDATED_ACCESS_CONTROL_POST_ID)
            .originalSha1(UPDATED_ORIGINAL_SHA_1)
            .animated(UPDATED_ANIMATED)
            .verified(UPDATED_VERIFIED)
            .verificationStatus(UPDATED_VERIFICATION_STATUS)
            .securityLastChangedAt(UPDATED_SECURITY_LAST_CHANGED_AT)
            .securityLastChangedReason(UPDATED_SECURITY_LAST_CHANGED_REASON);
        return uploads;
    }

    @BeforeEach
    public void initTest() {
        uploads = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploads() throws Exception {
        int databaseSizeBeforeCreate = uploadsRepository.findAll().size();
        // Create the Uploads
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);
        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isCreated());

        // Validate the Uploads in the database
        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeCreate + 1);
        Uploads testUploads = uploadsList.get(uploadsList.size() - 1);
        assertThat(testUploads.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUploads.getOriginalFilename()).isEqualTo(DEFAULT_ORIGINAL_FILENAME);
        assertThat(testUploads.getFilesize()).isEqualTo(DEFAULT_FILESIZE);
        assertThat(testUploads.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testUploads.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testUploads.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testUploads.getSha1()).isEqualTo(DEFAULT_SHA_1);
        assertThat(testUploads.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testUploads.getRetainHours()).isEqualTo(DEFAULT_RETAIN_HOURS);
        assertThat(testUploads.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testUploads.getThumbnailWidth()).isEqualTo(DEFAULT_THUMBNAIL_WIDTH);
        assertThat(testUploads.getThumbnailHeight()).isEqualTo(DEFAULT_THUMBNAIL_HEIGHT);
        assertThat(testUploads.getEtag()).isEqualTo(DEFAULT_ETAG);
        assertThat(testUploads.isSecure()).isEqualTo(DEFAULT_SECURE);
        assertThat(testUploads.getAccessControlPostId()).isEqualTo(DEFAULT_ACCESS_CONTROL_POST_ID);
        assertThat(testUploads.getOriginalSha1()).isEqualTo(DEFAULT_ORIGINAL_SHA_1);
        assertThat(testUploads.isAnimated()).isEqualTo(DEFAULT_ANIMATED);
        assertThat(testUploads.isVerified()).isEqualTo(DEFAULT_VERIFIED);
        assertThat(testUploads.getVerificationStatus()).isEqualTo(DEFAULT_VERIFICATION_STATUS);
        assertThat(testUploads.getSecurityLastChangedAt()).isEqualTo(DEFAULT_SECURITY_LAST_CHANGED_AT);
        assertThat(testUploads.getSecurityLastChangedReason()).isEqualTo(DEFAULT_SECURITY_LAST_CHANGED_REASON);
    }

    @Test
    @Transactional
    public void createUploadsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadsRepository.findAll().size();

        // Create the Uploads with an existing ID
        uploads.setId(1L);
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Uploads in the database
        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadsRepository.findAll().size();
        // set the field null
        uploads.setUserId(null);

        // Create the Uploads, which fails.
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);


        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadsRepository.findAll().size();
        // set the field null
        uploads.setOriginalFilename(null);

        // Create the Uploads, which fails.
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);


        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilesizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadsRepository.findAll().size();
        // set the field null
        uploads.setFilesize(null);

        // Create the Uploads, which fails.
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);


        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadsRepository.findAll().size();
        // set the field null
        uploads.setUrl(null);

        // Create the Uploads, which fails.
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);


        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecureIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadsRepository.findAll().size();
        // set the field null
        uploads.setSecure(null);

        // Create the Uploads, which fails.
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);


        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVerificationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadsRepository.findAll().size();
        // set the field null
        uploads.setVerificationStatus(null);

        // Create the Uploads, which fails.
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);


        restUploadsMockMvc.perform(post("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUploads() throws Exception {
        // Initialize the database
        uploadsRepository.saveAndFlush(uploads);

        // Get all the uploadsList
        restUploadsMockMvc.perform(get("/api/uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploads.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].originalFilename").value(hasItem(DEFAULT_ORIGINAL_FILENAME)))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].sha1").value(hasItem(DEFAULT_SHA_1)))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].retainHours").value(hasItem(DEFAULT_RETAIN_HOURS)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].thumbnailWidth").value(hasItem(DEFAULT_THUMBNAIL_WIDTH)))
            .andExpect(jsonPath("$.[*].thumbnailHeight").value(hasItem(DEFAULT_THUMBNAIL_HEIGHT)))
            .andExpect(jsonPath("$.[*].etag").value(hasItem(DEFAULT_ETAG)))
            .andExpect(jsonPath("$.[*].secure").value(hasItem(DEFAULT_SECURE.booleanValue())))
            .andExpect(jsonPath("$.[*].accessControlPostId").value(hasItem(DEFAULT_ACCESS_CONTROL_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].originalSha1").value(hasItem(DEFAULT_ORIGINAL_SHA_1)))
            .andExpect(jsonPath("$.[*].animated").value(hasItem(DEFAULT_ANIMATED.booleanValue())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].verificationStatus").value(hasItem(DEFAULT_VERIFICATION_STATUS)))
            .andExpect(jsonPath("$.[*].securityLastChangedAt").value(hasItem(DEFAULT_SECURITY_LAST_CHANGED_AT.toString())))
            .andExpect(jsonPath("$.[*].securityLastChangedReason").value(hasItem(DEFAULT_SECURITY_LAST_CHANGED_REASON)));
    }

    @Test
    @Transactional
    public void getUploads() throws Exception {
        // Initialize the database
        uploadsRepository.saveAndFlush(uploads);

        // Get the uploads
        restUploadsMockMvc.perform(get("/api/uploads/{id}", uploads.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uploads.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.originalFilename").value(DEFAULT_ORIGINAL_FILENAME))
            .andExpect(jsonPath("$.filesize").value(DEFAULT_FILESIZE))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.sha1").value(DEFAULT_SHA_1))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.retainHours").value(DEFAULT_RETAIN_HOURS))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.thumbnailWidth").value(DEFAULT_THUMBNAIL_WIDTH))
            .andExpect(jsonPath("$.thumbnailHeight").value(DEFAULT_THUMBNAIL_HEIGHT))
            .andExpect(jsonPath("$.etag").value(DEFAULT_ETAG))
            .andExpect(jsonPath("$.secure").value(DEFAULT_SECURE.booleanValue()))
            .andExpect(jsonPath("$.accessControlPostId").value(DEFAULT_ACCESS_CONTROL_POST_ID.intValue()))
            .andExpect(jsonPath("$.originalSha1").value(DEFAULT_ORIGINAL_SHA_1))
            .andExpect(jsonPath("$.animated").value(DEFAULT_ANIMATED.booleanValue()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.verificationStatus").value(DEFAULT_VERIFICATION_STATUS))
            .andExpect(jsonPath("$.securityLastChangedAt").value(DEFAULT_SECURITY_LAST_CHANGED_AT.toString()))
            .andExpect(jsonPath("$.securityLastChangedReason").value(DEFAULT_SECURITY_LAST_CHANGED_REASON));
    }
    @Test
    @Transactional
    public void getNonExistingUploads() throws Exception {
        // Get the uploads
        restUploadsMockMvc.perform(get("/api/uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploads() throws Exception {
        // Initialize the database
        uploadsRepository.saveAndFlush(uploads);

        int databaseSizeBeforeUpdate = uploadsRepository.findAll().size();

        // Update the uploads
        Uploads updatedUploads = uploadsRepository.findById(uploads.getId()).get();
        // Disconnect from session so that the updates on updatedUploads are not directly saved in db
        em.detach(updatedUploads);
        updatedUploads
            .userId(UPDATED_USER_ID)
            .originalFilename(UPDATED_ORIGINAL_FILENAME)
            .filesize(UPDATED_FILESIZE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .url(UPDATED_URL)
            .sha1(UPDATED_SHA_1)
            .origin(UPDATED_ORIGIN)
            .retainHours(UPDATED_RETAIN_HOURS)
            .extension(UPDATED_EXTENSION)
            .thumbnailWidth(UPDATED_THUMBNAIL_WIDTH)
            .thumbnailHeight(UPDATED_THUMBNAIL_HEIGHT)
            .etag(UPDATED_ETAG)
            .secure(UPDATED_SECURE)
            .accessControlPostId(UPDATED_ACCESS_CONTROL_POST_ID)
            .originalSha1(UPDATED_ORIGINAL_SHA_1)
            .animated(UPDATED_ANIMATED)
            .verified(UPDATED_VERIFIED)
            .verificationStatus(UPDATED_VERIFICATION_STATUS)
            .securityLastChangedAt(UPDATED_SECURITY_LAST_CHANGED_AT)
            .securityLastChangedReason(UPDATED_SECURITY_LAST_CHANGED_REASON);
        UploadsDTO uploadsDTO = uploadsMapper.toDto(updatedUploads);

        restUploadsMockMvc.perform(put("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isOk());

        // Validate the Uploads in the database
        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeUpdate);
        Uploads testUploads = uploadsList.get(uploadsList.size() - 1);
        assertThat(testUploads.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUploads.getOriginalFilename()).isEqualTo(UPDATED_ORIGINAL_FILENAME);
        assertThat(testUploads.getFilesize()).isEqualTo(UPDATED_FILESIZE);
        assertThat(testUploads.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testUploads.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testUploads.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testUploads.getSha1()).isEqualTo(UPDATED_SHA_1);
        assertThat(testUploads.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testUploads.getRetainHours()).isEqualTo(UPDATED_RETAIN_HOURS);
        assertThat(testUploads.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testUploads.getThumbnailWidth()).isEqualTo(UPDATED_THUMBNAIL_WIDTH);
        assertThat(testUploads.getThumbnailHeight()).isEqualTo(UPDATED_THUMBNAIL_HEIGHT);
        assertThat(testUploads.getEtag()).isEqualTo(UPDATED_ETAG);
        assertThat(testUploads.isSecure()).isEqualTo(UPDATED_SECURE);
        assertThat(testUploads.getAccessControlPostId()).isEqualTo(UPDATED_ACCESS_CONTROL_POST_ID);
        assertThat(testUploads.getOriginalSha1()).isEqualTo(UPDATED_ORIGINAL_SHA_1);
        assertThat(testUploads.isAnimated()).isEqualTo(UPDATED_ANIMATED);
        assertThat(testUploads.isVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testUploads.getVerificationStatus()).isEqualTo(UPDATED_VERIFICATION_STATUS);
        assertThat(testUploads.getSecurityLastChangedAt()).isEqualTo(UPDATED_SECURITY_LAST_CHANGED_AT);
        assertThat(testUploads.getSecurityLastChangedReason()).isEqualTo(UPDATED_SECURITY_LAST_CHANGED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingUploads() throws Exception {
        int databaseSizeBeforeUpdate = uploadsRepository.findAll().size();

        // Create the Uploads
        UploadsDTO uploadsDTO = uploadsMapper.toDto(uploads);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadsMockMvc.perform(put("/api/uploads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Uploads in the database
        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploads() throws Exception {
        // Initialize the database
        uploadsRepository.saveAndFlush(uploads);

        int databaseSizeBeforeDelete = uploadsRepository.findAll().size();

        // Delete the uploads
        restUploadsMockMvc.perform(delete("/api/uploads/{id}", uploads.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uploads> uploadsList = uploadsRepository.findAll();
        assertThat(uploadsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
