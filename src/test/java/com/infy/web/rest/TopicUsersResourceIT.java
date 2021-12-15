/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.TopicUsers;
import com.infy.repository.TopicUsersRepository;
import com.infy.service.TopicUsersService;
import com.infy.service.dto.TopicUsersDTO;
import com.infy.service.mapper.TopicUsersMapper;

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
 * Integration tests for the {@link TopicUsersResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TopicUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final Boolean DEFAULT_POSTED = false;
    private static final Boolean UPDATED_POSTED = true;

    private static final Integer DEFAULT_LAST_READ_POST_NUMBER = 1;
    private static final Integer UPDATED_LAST_READ_POST_NUMBER = 2;

    private static final Integer DEFAULT_HIGHEST_SEEN_POST_NUMBER = 1;
    private static final Integer UPDATED_HIGHEST_SEEN_POST_NUMBER = 2;

    private static final Instant DEFAULT_LAST_VISITED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_VISITED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIRST_VISITED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_VISITED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NOTIFICATION_LEVEL = 1;
    private static final Integer UPDATED_NOTIFICATION_LEVEL = 2;

    private static final Instant DEFAULT_NOTIFICATIONS_CHANGED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOTIFICATIONS_CHANGED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_NOTIFICATIONS_REASON_ID = 1L;
    private static final Long UPDATED_NOTIFICATIONS_REASON_ID = 2L;

    private static final Integer DEFAULT_TOTAL_MSECS_VIEWED = 1;
    private static final Integer UPDATED_TOTAL_MSECS_VIEWED = 2;

    private static final Instant DEFAULT_CLEARED_PINNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLEARED_PINNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LAST_EMAILED_POST_NUMBER = 1;
    private static final Integer UPDATED_LAST_EMAILED_POST_NUMBER = 2;

    private static final Boolean DEFAULT_LIKED = false;
    private static final Boolean UPDATED_LIKED = true;

    private static final Boolean DEFAULT_BOOKMARKED = false;
    private static final Boolean UPDATED_BOOKMARKED = true;

    private static final Instant DEFAULT_LAST_POSTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_POSTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TopicUsersRepository topicUsersRepository;

    @Autowired
    private TopicUsersMapper topicUsersMapper;

    @Autowired
    private TopicUsersService topicUsersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicUsersMockMvc;

    private TopicUsers topicUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicUsers createEntity(EntityManager em) {
        TopicUsers topicUsers = new TopicUsers()
            .userId(DEFAULT_USER_ID)
            .topicId(DEFAULT_TOPIC_ID)
            .posted(DEFAULT_POSTED)
            .lastReadPostNumber(DEFAULT_LAST_READ_POST_NUMBER)
            .highestSeenPostNumber(DEFAULT_HIGHEST_SEEN_POST_NUMBER)
            .lastVisitedAt(DEFAULT_LAST_VISITED_AT)
            .firstVisitedAt(DEFAULT_FIRST_VISITED_AT)
            .notificationLevel(DEFAULT_NOTIFICATION_LEVEL)
            .notificationsChangedAt(DEFAULT_NOTIFICATIONS_CHANGED_AT)
            .notificationsReasonId(DEFAULT_NOTIFICATIONS_REASON_ID)
            .totalMsecsViewed(DEFAULT_TOTAL_MSECS_VIEWED)
            .clearedPinnedAt(DEFAULT_CLEARED_PINNED_AT)
            .lastEmailedPostNumber(DEFAULT_LAST_EMAILED_POST_NUMBER)
            .liked(DEFAULT_LIKED)
            .bookmarked(DEFAULT_BOOKMARKED)
            .lastPostedAt(DEFAULT_LAST_POSTED_AT);
        return topicUsers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicUsers createUpdatedEntity(EntityManager em) {
        TopicUsers topicUsers = new TopicUsers()
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .posted(UPDATED_POSTED)
            .lastReadPostNumber(UPDATED_LAST_READ_POST_NUMBER)
            .highestSeenPostNumber(UPDATED_HIGHEST_SEEN_POST_NUMBER)
            .lastVisitedAt(UPDATED_LAST_VISITED_AT)
            .firstVisitedAt(UPDATED_FIRST_VISITED_AT)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL)
            .notificationsChangedAt(UPDATED_NOTIFICATIONS_CHANGED_AT)
            .notificationsReasonId(UPDATED_NOTIFICATIONS_REASON_ID)
            .totalMsecsViewed(UPDATED_TOTAL_MSECS_VIEWED)
            .clearedPinnedAt(UPDATED_CLEARED_PINNED_AT)
            .lastEmailedPostNumber(UPDATED_LAST_EMAILED_POST_NUMBER)
            .liked(UPDATED_LIKED)
            .bookmarked(UPDATED_BOOKMARKED)
            .lastPostedAt(UPDATED_LAST_POSTED_AT);
        return topicUsers;
    }

    @BeforeEach
    public void initTest() {
        topicUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicUsers() throws Exception {
        int databaseSizeBeforeCreate = topicUsersRepository.findAll().size();
        // Create the TopicUsers
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);
        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the TopicUsers in the database
        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeCreate + 1);
        TopicUsers testTopicUsers = topicUsersList.get(topicUsersList.size() - 1);
        assertThat(testTopicUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTopicUsers.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testTopicUsers.isPosted()).isEqualTo(DEFAULT_POSTED);
        assertThat(testTopicUsers.getLastReadPostNumber()).isEqualTo(DEFAULT_LAST_READ_POST_NUMBER);
        assertThat(testTopicUsers.getHighestSeenPostNumber()).isEqualTo(DEFAULT_HIGHEST_SEEN_POST_NUMBER);
        assertThat(testTopicUsers.getLastVisitedAt()).isEqualTo(DEFAULT_LAST_VISITED_AT);
        assertThat(testTopicUsers.getFirstVisitedAt()).isEqualTo(DEFAULT_FIRST_VISITED_AT);
        assertThat(testTopicUsers.getNotificationLevel()).isEqualTo(DEFAULT_NOTIFICATION_LEVEL);
        assertThat(testTopicUsers.getNotificationsChangedAt()).isEqualTo(DEFAULT_NOTIFICATIONS_CHANGED_AT);
        assertThat(testTopicUsers.getNotificationsReasonId()).isEqualTo(DEFAULT_NOTIFICATIONS_REASON_ID);
        assertThat(testTopicUsers.getTotalMsecsViewed()).isEqualTo(DEFAULT_TOTAL_MSECS_VIEWED);
        assertThat(testTopicUsers.getClearedPinnedAt()).isEqualTo(DEFAULT_CLEARED_PINNED_AT);
        assertThat(testTopicUsers.getLastEmailedPostNumber()).isEqualTo(DEFAULT_LAST_EMAILED_POST_NUMBER);
        assertThat(testTopicUsers.isLiked()).isEqualTo(DEFAULT_LIKED);
        assertThat(testTopicUsers.isBookmarked()).isEqualTo(DEFAULT_BOOKMARKED);
        assertThat(testTopicUsers.getLastPostedAt()).isEqualTo(DEFAULT_LAST_POSTED_AT);
    }

    @Test
    @Transactional
    public void createTopicUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicUsersRepository.findAll().size();

        // Create the TopicUsers with an existing ID
        topicUsers.setId(1L);
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicUsers in the database
        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicUsersRepository.findAll().size();
        // set the field null
        topicUsers.setUserId(null);

        // Create the TopicUsers, which fails.
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);


        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicUsersRepository.findAll().size();
        // set the field null
        topicUsers.setTopicId(null);

        // Create the TopicUsers, which fails.
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);


        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostedIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicUsersRepository.findAll().size();
        // set the field null
        topicUsers.setPosted(null);

        // Create the TopicUsers, which fails.
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);


        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicUsersRepository.findAll().size();
        // set the field null
        topicUsers.setNotificationLevel(null);

        // Create the TopicUsers, which fails.
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);


        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalMsecsViewedIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicUsersRepository.findAll().size();
        // set the field null
        topicUsers.setTotalMsecsViewed(null);

        // Create the TopicUsers, which fails.
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);


        restTopicUsersMockMvc.perform(post("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicUsers() throws Exception {
        // Initialize the database
        topicUsersRepository.saveAndFlush(topicUsers);

        // Get all the topicUsersList
        restTopicUsersMockMvc.perform(get("/api/topic-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].posted").value(hasItem(DEFAULT_POSTED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastReadPostNumber").value(hasItem(DEFAULT_LAST_READ_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].highestSeenPostNumber").value(hasItem(DEFAULT_HIGHEST_SEEN_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].lastVisitedAt").value(hasItem(DEFAULT_LAST_VISITED_AT.toString())))
            .andExpect(jsonPath("$.[*].firstVisitedAt").value(hasItem(DEFAULT_FIRST_VISITED_AT.toString())))
            .andExpect(jsonPath("$.[*].notificationLevel").value(hasItem(DEFAULT_NOTIFICATION_LEVEL)))
            .andExpect(jsonPath("$.[*].notificationsChangedAt").value(hasItem(DEFAULT_NOTIFICATIONS_CHANGED_AT.toString())))
            .andExpect(jsonPath("$.[*].notificationsReasonId").value(hasItem(DEFAULT_NOTIFICATIONS_REASON_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalMsecsViewed").value(hasItem(DEFAULT_TOTAL_MSECS_VIEWED)))
            .andExpect(jsonPath("$.[*].clearedPinnedAt").value(hasItem(DEFAULT_CLEARED_PINNED_AT.toString())))
            .andExpect(jsonPath("$.[*].lastEmailedPostNumber").value(hasItem(DEFAULT_LAST_EMAILED_POST_NUMBER)))
            .andExpect(jsonPath("$.[*].liked").value(hasItem(DEFAULT_LIKED.booleanValue())))
            .andExpect(jsonPath("$.[*].bookmarked").value(hasItem(DEFAULT_BOOKMARKED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastPostedAt").value(hasItem(DEFAULT_LAST_POSTED_AT.toString())));
    }

    @Test
    @Transactional
    public void getTopicUsers() throws Exception {
        // Initialize the database
        topicUsersRepository.saveAndFlush(topicUsers);

        // Get the topicUsers
        restTopicUsersMockMvc.perform(get("/api/topic-users/{id}", topicUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicUsers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.posted").value(DEFAULT_POSTED.booleanValue()))
            .andExpect(jsonPath("$.lastReadPostNumber").value(DEFAULT_LAST_READ_POST_NUMBER))
            .andExpect(jsonPath("$.highestSeenPostNumber").value(DEFAULT_HIGHEST_SEEN_POST_NUMBER))
            .andExpect(jsonPath("$.lastVisitedAt").value(DEFAULT_LAST_VISITED_AT.toString()))
            .andExpect(jsonPath("$.firstVisitedAt").value(DEFAULT_FIRST_VISITED_AT.toString()))
            .andExpect(jsonPath("$.notificationLevel").value(DEFAULT_NOTIFICATION_LEVEL))
            .andExpect(jsonPath("$.notificationsChangedAt").value(DEFAULT_NOTIFICATIONS_CHANGED_AT.toString()))
            .andExpect(jsonPath("$.notificationsReasonId").value(DEFAULT_NOTIFICATIONS_REASON_ID.intValue()))
            .andExpect(jsonPath("$.totalMsecsViewed").value(DEFAULT_TOTAL_MSECS_VIEWED))
            .andExpect(jsonPath("$.clearedPinnedAt").value(DEFAULT_CLEARED_PINNED_AT.toString()))
            .andExpect(jsonPath("$.lastEmailedPostNumber").value(DEFAULT_LAST_EMAILED_POST_NUMBER))
            .andExpect(jsonPath("$.liked").value(DEFAULT_LIKED.booleanValue()))
            .andExpect(jsonPath("$.bookmarked").value(DEFAULT_BOOKMARKED.booleanValue()))
            .andExpect(jsonPath("$.lastPostedAt").value(DEFAULT_LAST_POSTED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTopicUsers() throws Exception {
        // Get the topicUsers
        restTopicUsersMockMvc.perform(get("/api/topic-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicUsers() throws Exception {
        // Initialize the database
        topicUsersRepository.saveAndFlush(topicUsers);

        int databaseSizeBeforeUpdate = topicUsersRepository.findAll().size();

        // Update the topicUsers
        TopicUsers updatedTopicUsers = topicUsersRepository.findById(topicUsers.getId()).get();
        // Disconnect from session so that the updates on updatedTopicUsers are not directly saved in db
        em.detach(updatedTopicUsers);
        updatedTopicUsers
            .userId(UPDATED_USER_ID)
            .topicId(UPDATED_TOPIC_ID)
            .posted(UPDATED_POSTED)
            .lastReadPostNumber(UPDATED_LAST_READ_POST_NUMBER)
            .highestSeenPostNumber(UPDATED_HIGHEST_SEEN_POST_NUMBER)
            .lastVisitedAt(UPDATED_LAST_VISITED_AT)
            .firstVisitedAt(UPDATED_FIRST_VISITED_AT)
            .notificationLevel(UPDATED_NOTIFICATION_LEVEL)
            .notificationsChangedAt(UPDATED_NOTIFICATIONS_CHANGED_AT)
            .notificationsReasonId(UPDATED_NOTIFICATIONS_REASON_ID)
            .totalMsecsViewed(UPDATED_TOTAL_MSECS_VIEWED)
            .clearedPinnedAt(UPDATED_CLEARED_PINNED_AT)
            .lastEmailedPostNumber(UPDATED_LAST_EMAILED_POST_NUMBER)
            .liked(UPDATED_LIKED)
            .bookmarked(UPDATED_BOOKMARKED)
            .lastPostedAt(UPDATED_LAST_POSTED_AT);
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(updatedTopicUsers);

        restTopicUsersMockMvc.perform(put("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isOk());

        // Validate the TopicUsers in the database
        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeUpdate);
        TopicUsers testTopicUsers = topicUsersList.get(topicUsersList.size() - 1);
        assertThat(testTopicUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTopicUsers.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testTopicUsers.isPosted()).isEqualTo(UPDATED_POSTED);
        assertThat(testTopicUsers.getLastReadPostNumber()).isEqualTo(UPDATED_LAST_READ_POST_NUMBER);
        assertThat(testTopicUsers.getHighestSeenPostNumber()).isEqualTo(UPDATED_HIGHEST_SEEN_POST_NUMBER);
        assertThat(testTopicUsers.getLastVisitedAt()).isEqualTo(UPDATED_LAST_VISITED_AT);
        assertThat(testTopicUsers.getFirstVisitedAt()).isEqualTo(UPDATED_FIRST_VISITED_AT);
        assertThat(testTopicUsers.getNotificationLevel()).isEqualTo(UPDATED_NOTIFICATION_LEVEL);
        assertThat(testTopicUsers.getNotificationsChangedAt()).isEqualTo(UPDATED_NOTIFICATIONS_CHANGED_AT);
        assertThat(testTopicUsers.getNotificationsReasonId()).isEqualTo(UPDATED_NOTIFICATIONS_REASON_ID);
        assertThat(testTopicUsers.getTotalMsecsViewed()).isEqualTo(UPDATED_TOTAL_MSECS_VIEWED);
        assertThat(testTopicUsers.getClearedPinnedAt()).isEqualTo(UPDATED_CLEARED_PINNED_AT);
        assertThat(testTopicUsers.getLastEmailedPostNumber()).isEqualTo(UPDATED_LAST_EMAILED_POST_NUMBER);
        assertThat(testTopicUsers.isLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testTopicUsers.isBookmarked()).isEqualTo(UPDATED_BOOKMARKED);
        assertThat(testTopicUsers.getLastPostedAt()).isEqualTo(UPDATED_LAST_POSTED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicUsers() throws Exception {
        int databaseSizeBeforeUpdate = topicUsersRepository.findAll().size();

        // Create the TopicUsers
        TopicUsersDTO topicUsersDTO = topicUsersMapper.toDto(topicUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicUsersMockMvc.perform(put("/api/topic-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TopicUsers in the database
        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicUsers() throws Exception {
        // Initialize the database
        topicUsersRepository.saveAndFlush(topicUsers);

        int databaseSizeBeforeDelete = topicUsersRepository.findAll().size();

        // Delete the topicUsers
        restTopicUsersMockMvc.perform(delete("/api/topic-users/{id}", topicUsers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicUsers> topicUsersList = topicUsersRepository.findAll();
        assertThat(topicUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
