/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Bookmarks;
import com.infy.repository.BookmarksRepository;
import com.infy.service.BookmarksService;
import com.infy.service.dto.BookmarksDTO;
import com.infy.service.mapper.BookmarksMapper;

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
 * Integration tests for the {@link BookmarksResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BookmarksResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_REMINDER_TYPE = 1;
    private static final Integer UPDATED_REMINDER_TYPE = 2;

    private static final Instant DEFAULT_REMINDER_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REMINDER_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REMINDER_LAST_SENT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REMINDER_LAST_SENT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REMINDER_SET_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REMINDER_SET_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AUTO_DELETE_PREFERENCE = 1;
    private static final Integer UPDATED_AUTO_DELETE_PREFERENCE = 2;

    private static final Boolean DEFAULT_PINNED = false;
    private static final Boolean UPDATED_PINNED = true;

    @Autowired
    private BookmarksRepository bookmarksRepository;

    @Autowired
    private BookmarksMapper bookmarksMapper;

    @Autowired
    private BookmarksService bookmarksService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookmarksMockMvc;

    private Bookmarks bookmarks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookmarks createEntity(EntityManager em) {
        Bookmarks bookmarks = new Bookmarks()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .postId(DEFAULT_POST_ID)
            .name(DEFAULT_NAME)
            .reminderType(DEFAULT_REMINDER_TYPE)
            .reminderAt(DEFAULT_REMINDER_AT)
            .reminderLastSentAt(DEFAULT_REMINDER_LAST_SENT_AT)
            .reminderSetAt(DEFAULT_REMINDER_SET_AT)
            .autoDeletePreference(DEFAULT_AUTO_DELETE_PREFERENCE)
            .pinned(DEFAULT_PINNED);
        return bookmarks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookmarks createUpdatedEntity(EntityManager em) {
        Bookmarks bookmarks = new Bookmarks()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .name(UPDATED_NAME)
            .reminderType(UPDATED_REMINDER_TYPE)
            .reminderAt(UPDATED_REMINDER_AT)
            .reminderLastSentAt(UPDATED_REMINDER_LAST_SENT_AT)
            .reminderSetAt(UPDATED_REMINDER_SET_AT)
            .autoDeletePreference(UPDATED_AUTO_DELETE_PREFERENCE)
            .pinned(UPDATED_PINNED);
        return bookmarks;
    }

    @BeforeEach
    public void initTest() {
        bookmarks = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookmarks() throws Exception {
        int databaseSizeBeforeCreate = bookmarksRepository.findAll().size();
        // Create the Bookmarks
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);
        restBookmarksMockMvc.perform(post("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isCreated());

        // Validate the Bookmarks in the database
        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeCreate + 1);
        Bookmarks testBookmarks = bookmarksList.get(bookmarksList.size() - 1);
        assertThat(testBookmarks.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBookmarks.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testBookmarks.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testBookmarks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookmarks.getReminderType()).isEqualTo(DEFAULT_REMINDER_TYPE);
        assertThat(testBookmarks.getReminderAt()).isEqualTo(DEFAULT_REMINDER_AT);
        assertThat(testBookmarks.getReminderLastSentAt()).isEqualTo(DEFAULT_REMINDER_LAST_SENT_AT);
        assertThat(testBookmarks.getReminderSetAt()).isEqualTo(DEFAULT_REMINDER_SET_AT);
        assertThat(testBookmarks.getAutoDeletePreference()).isEqualTo(DEFAULT_AUTO_DELETE_PREFERENCE);
        assertThat(testBookmarks.isPinned()).isEqualTo(DEFAULT_PINNED);
    }

    @Test
    @Transactional
    public void createBookmarksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookmarksRepository.findAll().size();

        // Create the Bookmarks with an existing ID
        bookmarks.setId(1L);
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookmarksMockMvc.perform(post("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bookmarks in the database
        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookmarksRepository.findAll().size();
        // set the field null
        bookmarks.setUserId(null);

        // Create the Bookmarks, which fails.
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);


        restBookmarksMockMvc.perform(post("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isBadRequest());

        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookmarksRepository.findAll().size();
        // set the field null
        bookmarks.setTopicId(null);

        // Create the Bookmarks, which fails.
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);


        restBookmarksMockMvc.perform(post("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isBadRequest());

        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookmarksRepository.findAll().size();
        // set the field null
        bookmarks.setPostId(null);

        // Create the Bookmarks, which fails.
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);


        restBookmarksMockMvc.perform(post("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isBadRequest());

        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutoDeletePreferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookmarksRepository.findAll().size();
        // set the field null
        bookmarks.setAutoDeletePreference(null);

        // Create the Bookmarks, which fails.
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);


        restBookmarksMockMvc.perform(post("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isBadRequest());

        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookmarks() throws Exception {
        // Initialize the database
        bookmarksRepository.saveAndFlush(bookmarks);

        // Get all the bookmarksList
        restBookmarksMockMvc.perform(get("/api/bookmarks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookmarks.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].reminderType").value(hasItem(DEFAULT_REMINDER_TYPE)))
            .andExpect(jsonPath("$.[*].reminderAt").value(hasItem(DEFAULT_REMINDER_AT.toString())))
            .andExpect(jsonPath("$.[*].reminderLastSentAt").value(hasItem(DEFAULT_REMINDER_LAST_SENT_AT.toString())))
            .andExpect(jsonPath("$.[*].reminderSetAt").value(hasItem(DEFAULT_REMINDER_SET_AT.toString())))
            .andExpect(jsonPath("$.[*].autoDeletePreference").value(hasItem(DEFAULT_AUTO_DELETE_PREFERENCE)))
            .andExpect(jsonPath("$.[*].pinned").value(hasItem(DEFAULT_PINNED.booleanValue())));
    }

    @Test
    @Transactional
    public void getBookmarks() throws Exception {
        // Initialize the database
        bookmarksRepository.saveAndFlush(bookmarks);

        // Get the bookmarks
        restBookmarksMockMvc.perform(get("/api/bookmarks/{id}", bookmarks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookmarks.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.reminderType").value(DEFAULT_REMINDER_TYPE))
            .andExpect(jsonPath("$.reminderAt").value(DEFAULT_REMINDER_AT.toString()))
            .andExpect(jsonPath("$.reminderLastSentAt").value(DEFAULT_REMINDER_LAST_SENT_AT.toString()))
            .andExpect(jsonPath("$.reminderSetAt").value(DEFAULT_REMINDER_SET_AT.toString()))
            .andExpect(jsonPath("$.autoDeletePreference").value(DEFAULT_AUTO_DELETE_PREFERENCE))
            .andExpect(jsonPath("$.pinned").value(DEFAULT_PINNED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBookmarks() throws Exception {
        // Get the bookmarks
        restBookmarksMockMvc.perform(get("/api/bookmarks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookmarks() throws Exception {
        // Initialize the database
        bookmarksRepository.saveAndFlush(bookmarks);

        int databaseSizeBeforeUpdate = bookmarksRepository.findAll().size();

        // Update the bookmarks
        Bookmarks updatedBookmarks = bookmarksRepository.findById(bookmarks.getId()).get();
        // Disconnect from session so that the updates on updatedBookmarks are not directly saved in db
        em.detach(updatedBookmarks);
        updatedBookmarks
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .postId(UPDATED_POST_ID)
            .name(UPDATED_NAME)
            .reminderType(UPDATED_REMINDER_TYPE)
            .reminderAt(UPDATED_REMINDER_AT)
            .reminderLastSentAt(UPDATED_REMINDER_LAST_SENT_AT)
            .reminderSetAt(UPDATED_REMINDER_SET_AT)
            .autoDeletePreference(UPDATED_AUTO_DELETE_PREFERENCE)
            .pinned(UPDATED_PINNED);
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(updatedBookmarks);

        restBookmarksMockMvc.perform(put("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isOk());

        // Validate the Bookmarks in the database
        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeUpdate);
        Bookmarks testBookmarks = bookmarksList.get(bookmarksList.size() - 1);
        assertThat(testBookmarks.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBookmarks.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testBookmarks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testBookmarks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookmarks.getReminderType()).isEqualTo(UPDATED_REMINDER_TYPE);
        assertThat(testBookmarks.getReminderAt()).isEqualTo(UPDATED_REMINDER_AT);
        assertThat(testBookmarks.getReminderLastSentAt()).isEqualTo(UPDATED_REMINDER_LAST_SENT_AT);
        assertThat(testBookmarks.getReminderSetAt()).isEqualTo(UPDATED_REMINDER_SET_AT);
        assertThat(testBookmarks.getAutoDeletePreference()).isEqualTo(UPDATED_AUTO_DELETE_PREFERENCE);
        assertThat(testBookmarks.isPinned()).isEqualTo(UPDATED_PINNED);
    }

    @Test
    @Transactional
    public void updateNonExistingBookmarks() throws Exception {
        int databaseSizeBeforeUpdate = bookmarksRepository.findAll().size();

        // Create the Bookmarks
        BookmarksDTO bookmarksDTO = bookmarksMapper.toDto(bookmarks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookmarksMockMvc.perform(put("/api/bookmarks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookmarksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bookmarks in the database
        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookmarks() throws Exception {
        // Initialize the database
        bookmarksRepository.saveAndFlush(bookmarks);

        int databaseSizeBeforeDelete = bookmarksRepository.findAll().size();

        // Delete the bookmarks
        restBookmarksMockMvc.perform(delete("/api/bookmarks/{id}", bookmarks.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bookmarks> bookmarksList = bookmarksRepository.findAll();
        assertThat(bookmarksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
