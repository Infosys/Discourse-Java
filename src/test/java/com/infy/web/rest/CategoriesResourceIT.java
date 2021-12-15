/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.DiscourseApp;
import com.infy.config.TestSecurityConfiguration;
import com.infy.domain.Categories;
import com.infy.repository.CategoriesRepository;
import com.infy.service.CategoriesService;
import com.infy.service.dto.CategoriesDTO;
import com.infy.service.mapper.CategoriesMapper;
import com.infy.service.dto.CategoriesCriteria;
import com.infy.service.CategoriesQueryService;

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
 * Integration tests for the {@link CategoriesResource} REST controller.
 */
@SpringBootTest(classes = { DiscourseApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CategoriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;
    private static final Long SMALLER_TOPIC_ID = 1L - 1L;

    private static final Integer DEFAULT_TOPIC_COUNT = 1;
    private static final Integer UPDATED_TOPIC_COUNT = 2;
    private static final Integer SMALLER_TOPIC_COUNT = 1 - 1;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOPICS_YEAR = 1;
    private static final Integer UPDATED_TOPICS_YEAR = 2;
    private static final Integer SMALLER_TOPICS_YEAR = 1 - 1;

    private static final Integer DEFAULT_TOPICS_MONTH = 1;
    private static final Integer UPDATED_TOPICS_MONTH = 2;
    private static final Integer SMALLER_TOPICS_MONTH = 1 - 1;

    private static final Integer DEFAULT_TOPICS_WEEK = 1;
    private static final Integer UPDATED_TOPICS_WEEK = 2;
    private static final Integer SMALLER_TOPICS_WEEK = 1 - 1;

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_COLOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ_RESTRICTED = false;
    private static final Boolean UPDATED_READ_RESTRICTED = true;

    private static final Double DEFAULT_AUTO_CLOSE_HOURS = 1D;
    private static final Double UPDATED_AUTO_CLOSE_HOURS = 2D;
    private static final Double SMALLER_AUTO_CLOSE_HOURS = 1D - 1D;

    private static final Integer DEFAULT_POST_COUNT = 1;
    private static final Integer UPDATED_POST_COUNT = 2;
    private static final Integer SMALLER_POST_COUNT = 1 - 1;

    private static final Long DEFAULT_LATEST_POST_ID = 1L;
    private static final Long UPDATED_LATEST_POST_ID = 2L;
    private static final Long SMALLER_LATEST_POST_ID = 1L - 1L;

    private static final Long DEFAULT_LATEST_TOPIC_ID = 1L;
    private static final Long UPDATED_LATEST_TOPIC_ID = 2L;
    private static final Long SMALLER_LATEST_TOPIC_ID = 1L - 1L;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;
    private static final Integer SMALLER_POSITION = 1 - 1;

    private static final Long DEFAULT_PARENT_CATEGORY_ID = 1L;
    private static final Long UPDATED_PARENT_CATEGORY_ID = 2L;
    private static final Long SMALLER_PARENT_CATEGORY_ID = 1L - 1L;

    private static final Integer DEFAULT_POSTS_YEAR = 1;
    private static final Integer UPDATED_POSTS_YEAR = 2;
    private static final Integer SMALLER_POSTS_YEAR = 1 - 1;

    private static final Integer DEFAULT_POSTS_MONTH = 1;
    private static final Integer UPDATED_POSTS_MONTH = 2;
    private static final Integer SMALLER_POSTS_MONTH = 1 - 1;

    private static final Integer DEFAULT_POSTS_WEEK = 1;
    private static final Integer UPDATED_POSTS_WEEK = 2;
    private static final Integer SMALLER_POSTS_WEEK = 1 - 1;

    private static final String DEFAULT_EMAIL_IN = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_IN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMAIL_IN_ALLOW_STRANGERS = false;
    private static final Boolean UPDATED_EMAIL_IN_ALLOW_STRANGERS = true;

    private static final Integer DEFAULT_TOPICS_DAY = 1;
    private static final Integer UPDATED_TOPICS_DAY = 2;
    private static final Integer SMALLER_TOPICS_DAY = 1 - 1;

    private static final Integer DEFAULT_POSTS_DAY = 1;
    private static final Integer UPDATED_POSTS_DAY = 2;
    private static final Integer SMALLER_POSTS_DAY = 1 - 1;

    private static final Boolean DEFAULT_ALLOW_BADGES = false;
    private static final Boolean UPDATED_ALLOW_BADGES = true;

    private static final String DEFAULT_NAME_LOWER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_LOWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST = false;
    private static final Boolean UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST = true;

    private static final String DEFAULT_TOPIC_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_TEMPLATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONTAINS_MESSAGES = false;
    private static final Boolean UPDATED_CONTAINS_MESSAGES = true;

    private static final String DEFAULT_SORT_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_SORT_ORDER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SORT_ASCENDING = false;
    private static final Boolean UPDATED_SORT_ASCENDING = true;

    private static final Long DEFAULT_UPLOADED_LOGO_ID = 1L;
    private static final Long UPDATED_UPLOADED_LOGO_ID = 2L;
    private static final Long SMALLER_UPLOADED_LOGO_ID = 1L - 1L;

    private static final Long DEFAULT_UPLOADED_BACKGROUND_ID = 1L;
    private static final Long UPDATED_UPLOADED_BACKGROUND_ID = 2L;
    private static final Long SMALLER_UPLOADED_BACKGROUND_ID = 1L - 1L;

    private static final Boolean DEFAULT_TOPIC_FEATURED_LINK_ALLOWED = false;
    private static final Boolean UPDATED_TOPIC_FEATURED_LINK_ALLOWED = true;

    private static final Boolean DEFAULT_ALL_TOPICS_WIKI = false;
    private static final Boolean UPDATED_ALL_TOPICS_WIKI = true;

    private static final Boolean DEFAULT_SHOW_SUBCATEGORY_LIST = false;
    private static final Boolean UPDATED_SHOW_SUBCATEGORY_LIST = true;

    private static final Integer DEFAULT_NUM_FEATURED_TOPICS = 1;
    private static final Integer UPDATED_NUM_FEATURED_TOPICS = 2;
    private static final Integer SMALLER_NUM_FEATURED_TOPICS = 1 - 1;

    private static final String DEFAULT_DEFAULT_VIEW = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VIEW = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCATEGORY_LIST_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORY_LIST_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_TOP_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_TOP_PERIOD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MAILINGLIST_MIRROR = false;
    private static final Boolean UPDATED_MAILINGLIST_MIRROR = true;

    private static final Integer DEFAULT_MINIMUM_REQUIRED_TAGS = 1;
    private static final Integer UPDATED_MINIMUM_REQUIRED_TAGS = 2;
    private static final Integer SMALLER_MINIMUM_REQUIRED_TAGS = 1 - 1;

    private static final Boolean DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ = false;
    private static final Boolean UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ = true;

    private static final Integer DEFAULT_SEARCH_PRIORITY = 1;
    private static final Integer UPDATED_SEARCH_PRIORITY = 2;
    private static final Integer SMALLER_SEARCH_PRIORITY = 1 - 1;

    private static final Boolean DEFAULT_ALLOW_GLOBAL_TAGS = false;
    private static final Boolean UPDATED_ALLOW_GLOBAL_TAGS = true;

    private static final Long DEFAULT_REVIEWABLE_BY_GROUP_ID = 1L;
    private static final Long UPDATED_REVIEWABLE_BY_GROUP_ID = 2L;
    private static final Long SMALLER_REVIEWABLE_BY_GROUP_ID = 1L - 1L;

    private static final Long DEFAULT_REQUIRED_TAG_GROUP_ID = 1L;
    private static final Long UPDATED_REQUIRED_TAG_GROUP_ID = 2L;
    private static final Long SMALLER_REQUIRED_TAG_GROUP_ID = 1L - 1L;

    private static final Integer DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP = 1;
    private static final Integer UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP = 2;
    private static final Integer SMALLER_MIN_TAGS_FROM_REQUIRED_GROUP = 1 - 1;

    private static final String DEFAULT_READ_ONLY_BANNER = "AAAAAAAAAA";
    private static final String UPDATED_READ_ONLY_BANNER = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_LIST_FILTER = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_LIST_FILTER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST = false;
    private static final Boolean UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST = true;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private CategoriesQueryService categoriesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesMockMvc;

    private Categories categories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createEntity(EntityManager em) {
        Categories categories = new Categories()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR)
            .topicId(DEFAULT_TOPIC_ID)
            .topicCount(DEFAULT_TOPIC_COUNT)
            .userId(DEFAULT_USER_ID)
            .topicsYear(DEFAULT_TOPICS_YEAR)
            .topicsMonth(DEFAULT_TOPICS_MONTH)
            .topicsWeek(DEFAULT_TOPICS_WEEK)
            .slug(DEFAULT_SLUG)
            .description(DEFAULT_DESCRIPTION)
            .textColor(DEFAULT_TEXT_COLOR)
            .readRestricted(DEFAULT_READ_RESTRICTED)
            .autoCloseHours(DEFAULT_AUTO_CLOSE_HOURS)
            .postCount(DEFAULT_POST_COUNT)
            .latestPostId(DEFAULT_LATEST_POST_ID)
            .latestTopicId(DEFAULT_LATEST_TOPIC_ID)
            .position(DEFAULT_POSITION)
            .parentCategoryId(DEFAULT_PARENT_CATEGORY_ID)
            .postsYear(DEFAULT_POSTS_YEAR)
            .postsMonth(DEFAULT_POSTS_MONTH)
            .postsWeek(DEFAULT_POSTS_WEEK)
            .emailIn(DEFAULT_EMAIL_IN)
            .emailInAllowStrangers(DEFAULT_EMAIL_IN_ALLOW_STRANGERS)
            .topicsDay(DEFAULT_TOPICS_DAY)
            .postsDay(DEFAULT_POSTS_DAY)
            .allowBadges(DEFAULT_ALLOW_BADGES)
            .nameLower(DEFAULT_NAME_LOWER)
            .autoCloseBasedOnLastPost(DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST)
            .topicTemplate(DEFAULT_TOPIC_TEMPLATE)
            .containsMessages(DEFAULT_CONTAINS_MESSAGES)
            .sortOrder(DEFAULT_SORT_ORDER)
            .sortAscending(DEFAULT_SORT_ASCENDING)
            .uploadedLogoId(DEFAULT_UPLOADED_LOGO_ID)
            .uploadedBackgroundId(DEFAULT_UPLOADED_BACKGROUND_ID)
            .topicFeaturedLinkAllowed(DEFAULT_TOPIC_FEATURED_LINK_ALLOWED)
            .allTopicsWiki(DEFAULT_ALL_TOPICS_WIKI)
            .showSubcategoryList(DEFAULT_SHOW_SUBCATEGORY_LIST)
            .numFeaturedTopics(DEFAULT_NUM_FEATURED_TOPICS)
            .defaultView(DEFAULT_DEFAULT_VIEW)
            .subcategoryListStyle(DEFAULT_SUBCATEGORY_LIST_STYLE)
            .defaultTopPeriod(DEFAULT_DEFAULT_TOP_PERIOD)
            .mailinglistMirror(DEFAULT_MAILINGLIST_MIRROR)
            .minimumRequiredTags(DEFAULT_MINIMUM_REQUIRED_TAGS)
            .navigateToFirstPostAfterRead(DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ)
            .searchPriority(DEFAULT_SEARCH_PRIORITY)
            .allowGlobalTags(DEFAULT_ALLOW_GLOBAL_TAGS)
            .reviewableByGroupId(DEFAULT_REVIEWABLE_BY_GROUP_ID)
            .requiredTagGroupId(DEFAULT_REQUIRED_TAG_GROUP_ID)
            .minTagsFromRequiredGroup(DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP)
            .readOnlyBanner(DEFAULT_READ_ONLY_BANNER)
            .defaultListFilter(DEFAULT_DEFAULT_LIST_FILTER)
            .allowUnlimitedOwnerEditsOnFirstPost(DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
        return categories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createUpdatedEntity(EntityManager em) {
        Categories categories = new Categories()
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .topicId(UPDATED_TOPIC_ID)
            .topicCount(UPDATED_TOPIC_COUNT)
            .userId(UPDATED_USER_ID)
            .topicsYear(UPDATED_TOPICS_YEAR)
            .topicsMonth(UPDATED_TOPICS_MONTH)
            .topicsWeek(UPDATED_TOPICS_WEEK)
            .slug(UPDATED_SLUG)
            .description(UPDATED_DESCRIPTION)
            .textColor(UPDATED_TEXT_COLOR)
            .readRestricted(UPDATED_READ_RESTRICTED)
            .autoCloseHours(UPDATED_AUTO_CLOSE_HOURS)
            .postCount(UPDATED_POST_COUNT)
            .latestPostId(UPDATED_LATEST_POST_ID)
            .latestTopicId(UPDATED_LATEST_TOPIC_ID)
            .position(UPDATED_POSITION)
            .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
            .postsYear(UPDATED_POSTS_YEAR)
            .postsMonth(UPDATED_POSTS_MONTH)
            .postsWeek(UPDATED_POSTS_WEEK)
            .emailIn(UPDATED_EMAIL_IN)
            .emailInAllowStrangers(UPDATED_EMAIL_IN_ALLOW_STRANGERS)
            .topicsDay(UPDATED_TOPICS_DAY)
            .postsDay(UPDATED_POSTS_DAY)
            .allowBadges(UPDATED_ALLOW_BADGES)
            .nameLower(UPDATED_NAME_LOWER)
            .autoCloseBasedOnLastPost(UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST)
            .topicTemplate(UPDATED_TOPIC_TEMPLATE)
            .containsMessages(UPDATED_CONTAINS_MESSAGES)
            .sortOrder(UPDATED_SORT_ORDER)
            .sortAscending(UPDATED_SORT_ASCENDING)
            .uploadedLogoId(UPDATED_UPLOADED_LOGO_ID)
            .uploadedBackgroundId(UPDATED_UPLOADED_BACKGROUND_ID)
            .topicFeaturedLinkAllowed(UPDATED_TOPIC_FEATURED_LINK_ALLOWED)
            .allTopicsWiki(UPDATED_ALL_TOPICS_WIKI)
            .showSubcategoryList(UPDATED_SHOW_SUBCATEGORY_LIST)
            .numFeaturedTopics(UPDATED_NUM_FEATURED_TOPICS)
            .defaultView(UPDATED_DEFAULT_VIEW)
            .subcategoryListStyle(UPDATED_SUBCATEGORY_LIST_STYLE)
            .defaultTopPeriod(UPDATED_DEFAULT_TOP_PERIOD)
            .mailinglistMirror(UPDATED_MAILINGLIST_MIRROR)
            .minimumRequiredTags(UPDATED_MINIMUM_REQUIRED_TAGS)
            .navigateToFirstPostAfterRead(UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ)
            .searchPriority(UPDATED_SEARCH_PRIORITY)
            .allowGlobalTags(UPDATED_ALLOW_GLOBAL_TAGS)
            .reviewableByGroupId(UPDATED_REVIEWABLE_BY_GROUP_ID)
            .requiredTagGroupId(UPDATED_REQUIRED_TAG_GROUP_ID)
            .minTagsFromRequiredGroup(UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP)
            .readOnlyBanner(UPDATED_READ_ONLY_BANNER)
            .defaultListFilter(UPDATED_DEFAULT_LIST_FILTER)
            .allowUnlimitedOwnerEditsOnFirstPost(UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
        return categories;
    }

    @BeforeEach
    public void initTest() {
        categories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategories() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();
        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);
        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategories.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCategories.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testCategories.getTopicCount()).isEqualTo(DEFAULT_TOPIC_COUNT);
        assertThat(testCategories.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCategories.getTopicsYear()).isEqualTo(DEFAULT_TOPICS_YEAR);
        assertThat(testCategories.getTopicsMonth()).isEqualTo(DEFAULT_TOPICS_MONTH);
        assertThat(testCategories.getTopicsWeek()).isEqualTo(DEFAULT_TOPICS_WEEK);
        assertThat(testCategories.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategories.getTextColor()).isEqualTo(DEFAULT_TEXT_COLOR);
        assertThat(testCategories.isReadRestricted()).isEqualTo(DEFAULT_READ_RESTRICTED);
        assertThat(testCategories.getAutoCloseHours()).isEqualTo(DEFAULT_AUTO_CLOSE_HOURS);
        assertThat(testCategories.getPostCount()).isEqualTo(DEFAULT_POST_COUNT);
        assertThat(testCategories.getLatestPostId()).isEqualTo(DEFAULT_LATEST_POST_ID);
        assertThat(testCategories.getLatestTopicId()).isEqualTo(DEFAULT_LATEST_TOPIC_ID);
        assertThat(testCategories.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCategories.getParentCategoryId()).isEqualTo(DEFAULT_PARENT_CATEGORY_ID);
        assertThat(testCategories.getPostsYear()).isEqualTo(DEFAULT_POSTS_YEAR);
        assertThat(testCategories.getPostsMonth()).isEqualTo(DEFAULT_POSTS_MONTH);
        assertThat(testCategories.getPostsWeek()).isEqualTo(DEFAULT_POSTS_WEEK);
        assertThat(testCategories.getEmailIn()).isEqualTo(DEFAULT_EMAIL_IN);
        assertThat(testCategories.isEmailInAllowStrangers()).isEqualTo(DEFAULT_EMAIL_IN_ALLOW_STRANGERS);
        assertThat(testCategories.getTopicsDay()).isEqualTo(DEFAULT_TOPICS_DAY);
        assertThat(testCategories.getPostsDay()).isEqualTo(DEFAULT_POSTS_DAY);
        assertThat(testCategories.isAllowBadges()).isEqualTo(DEFAULT_ALLOW_BADGES);
        assertThat(testCategories.getNameLower()).isEqualTo(DEFAULT_NAME_LOWER);
        assertThat(testCategories.isAutoCloseBasedOnLastPost()).isEqualTo(DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST);
        assertThat(testCategories.getTopicTemplate()).isEqualTo(DEFAULT_TOPIC_TEMPLATE);
        assertThat(testCategories.isContainsMessages()).isEqualTo(DEFAULT_CONTAINS_MESSAGES);
        assertThat(testCategories.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testCategories.isSortAscending()).isEqualTo(DEFAULT_SORT_ASCENDING);
        assertThat(testCategories.getUploadedLogoId()).isEqualTo(DEFAULT_UPLOADED_LOGO_ID);
        assertThat(testCategories.getUploadedBackgroundId()).isEqualTo(DEFAULT_UPLOADED_BACKGROUND_ID);
        assertThat(testCategories.isTopicFeaturedLinkAllowed()).isEqualTo(DEFAULT_TOPIC_FEATURED_LINK_ALLOWED);
        assertThat(testCategories.isAllTopicsWiki()).isEqualTo(DEFAULT_ALL_TOPICS_WIKI);
        assertThat(testCategories.isShowSubcategoryList()).isEqualTo(DEFAULT_SHOW_SUBCATEGORY_LIST);
        assertThat(testCategories.getNumFeaturedTopics()).isEqualTo(DEFAULT_NUM_FEATURED_TOPICS);
        assertThat(testCategories.getDefaultView()).isEqualTo(DEFAULT_DEFAULT_VIEW);
        assertThat(testCategories.getSubcategoryListStyle()).isEqualTo(DEFAULT_SUBCATEGORY_LIST_STYLE);
        assertThat(testCategories.getDefaultTopPeriod()).isEqualTo(DEFAULT_DEFAULT_TOP_PERIOD);
        assertThat(testCategories.isMailinglistMirror()).isEqualTo(DEFAULT_MAILINGLIST_MIRROR);
        assertThat(testCategories.getMinimumRequiredTags()).isEqualTo(DEFAULT_MINIMUM_REQUIRED_TAGS);
        assertThat(testCategories.isNavigateToFirstPostAfterRead()).isEqualTo(DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ);
        assertThat(testCategories.getSearchPriority()).isEqualTo(DEFAULT_SEARCH_PRIORITY);
        assertThat(testCategories.isAllowGlobalTags()).isEqualTo(DEFAULT_ALLOW_GLOBAL_TAGS);
        assertThat(testCategories.getReviewableByGroupId()).isEqualTo(DEFAULT_REVIEWABLE_BY_GROUP_ID);
        assertThat(testCategories.getRequiredTagGroupId()).isEqualTo(DEFAULT_REQUIRED_TAG_GROUP_ID);
        assertThat(testCategories.getMinTagsFromRequiredGroup()).isEqualTo(DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);
        assertThat(testCategories.getReadOnlyBanner()).isEqualTo(DEFAULT_READ_ONLY_BANNER);
        assertThat(testCategories.getDefaultListFilter()).isEqualTo(DEFAULT_DEFAULT_LIST_FILTER);
        assertThat(testCategories.isAllowUnlimitedOwnerEditsOnFirstPost()).isEqualTo(DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
    }

    @Test
    @Transactional
    public void createCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // Create the Categories with an existing ID
        categories.setId(1L);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setName(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setColor(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setTopicCount(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setUserId(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setSlug(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setTextColor(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReadRestrictedIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setReadRestricted(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setPostCount(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowBadgesIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setAllowBadges(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllTopicsWikiIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setAllTopicsWiki(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailinglistMirrorIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setMailinglistMirror(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinimumRequiredTagsIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setMinimumRequiredTags(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNavigateToFirstPostAfterReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setNavigateToFirstPostAfterRead(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowGlobalTagsIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setAllowGlobalTags(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinTagsFromRequiredGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setMinTagsFromRequiredGroup(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowUnlimitedOwnerEditsOnFirstPostIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setAllowUnlimitedOwnerEditsOnFirstPost(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList
        restCategoriesMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicCount").value(hasItem(DEFAULT_TOPIC_COUNT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicsYear").value(hasItem(DEFAULT_TOPICS_YEAR)))
            .andExpect(jsonPath("$.[*].topicsMonth").value(hasItem(DEFAULT_TOPICS_MONTH)))
            .andExpect(jsonPath("$.[*].topicsWeek").value(hasItem(DEFAULT_TOPICS_WEEK)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].textColor").value(hasItem(DEFAULT_TEXT_COLOR)))
            .andExpect(jsonPath("$.[*].readRestricted").value(hasItem(DEFAULT_READ_RESTRICTED.booleanValue())))
            .andExpect(jsonPath("$.[*].autoCloseHours").value(hasItem(DEFAULT_AUTO_CLOSE_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].postCount").value(hasItem(DEFAULT_POST_COUNT)))
            .andExpect(jsonPath("$.[*].latestPostId").value(hasItem(DEFAULT_LATEST_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].latestTopicId").value(hasItem(DEFAULT_LATEST_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].postsYear").value(hasItem(DEFAULT_POSTS_YEAR)))
            .andExpect(jsonPath("$.[*].postsMonth").value(hasItem(DEFAULT_POSTS_MONTH)))
            .andExpect(jsonPath("$.[*].postsWeek").value(hasItem(DEFAULT_POSTS_WEEK)))
            .andExpect(jsonPath("$.[*].emailIn").value(hasItem(DEFAULT_EMAIL_IN)))
            .andExpect(jsonPath("$.[*].emailInAllowStrangers").value(hasItem(DEFAULT_EMAIL_IN_ALLOW_STRANGERS.booleanValue())))
            .andExpect(jsonPath("$.[*].topicsDay").value(hasItem(DEFAULT_TOPICS_DAY)))
            .andExpect(jsonPath("$.[*].postsDay").value(hasItem(DEFAULT_POSTS_DAY)))
            .andExpect(jsonPath("$.[*].allowBadges").value(hasItem(DEFAULT_ALLOW_BADGES.booleanValue())))
            .andExpect(jsonPath("$.[*].nameLower").value(hasItem(DEFAULT_NAME_LOWER)))
            .andExpect(jsonPath("$.[*].autoCloseBasedOnLastPost").value(hasItem(DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST.booleanValue())))
            .andExpect(jsonPath("$.[*].topicTemplate").value(hasItem(DEFAULT_TOPIC_TEMPLATE)))
            .andExpect(jsonPath("$.[*].containsMessages").value(hasItem(DEFAULT_CONTAINS_MESSAGES.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].sortAscending").value(hasItem(DEFAULT_SORT_ASCENDING.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadedLogoId").value(hasItem(DEFAULT_UPLOADED_LOGO_ID.intValue())))
            .andExpect(jsonPath("$.[*].uploadedBackgroundId").value(hasItem(DEFAULT_UPLOADED_BACKGROUND_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicFeaturedLinkAllowed").value(hasItem(DEFAULT_TOPIC_FEATURED_LINK_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].allTopicsWiki").value(hasItem(DEFAULT_ALL_TOPICS_WIKI.booleanValue())))
            .andExpect(jsonPath("$.[*].showSubcategoryList").value(hasItem(DEFAULT_SHOW_SUBCATEGORY_LIST.booleanValue())))
            .andExpect(jsonPath("$.[*].numFeaturedTopics").value(hasItem(DEFAULT_NUM_FEATURED_TOPICS)))
            .andExpect(jsonPath("$.[*].defaultView").value(hasItem(DEFAULT_DEFAULT_VIEW)))
            .andExpect(jsonPath("$.[*].subcategoryListStyle").value(hasItem(DEFAULT_SUBCATEGORY_LIST_STYLE)))
            .andExpect(jsonPath("$.[*].defaultTopPeriod").value(hasItem(DEFAULT_DEFAULT_TOP_PERIOD)))
            .andExpect(jsonPath("$.[*].mailinglistMirror").value(hasItem(DEFAULT_MAILINGLIST_MIRROR.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumRequiredTags").value(hasItem(DEFAULT_MINIMUM_REQUIRED_TAGS)))
            .andExpect(jsonPath("$.[*].navigateToFirstPostAfterRead").value(hasItem(DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].searchPriority").value(hasItem(DEFAULT_SEARCH_PRIORITY)))
            .andExpect(jsonPath("$.[*].allowGlobalTags").value(hasItem(DEFAULT_ALLOW_GLOBAL_TAGS.booleanValue())))
            .andExpect(jsonPath("$.[*].reviewableByGroupId").value(hasItem(DEFAULT_REVIEWABLE_BY_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].requiredTagGroupId").value(hasItem(DEFAULT_REQUIRED_TAG_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].minTagsFromRequiredGroup").value(hasItem(DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP)))
            .andExpect(jsonPath("$.[*].readOnlyBanner").value(hasItem(DEFAULT_READ_ONLY_BANNER)))
            .andExpect(jsonPath("$.[*].defaultListFilter").value(hasItem(DEFAULT_DEFAULT_LIST_FILTER)))
            .andExpect(jsonPath("$.[*].allowUnlimitedOwnerEditsOnFirstPost").value(hasItem(DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST.booleanValue())));
    }

    @Test
    @Transactional
    public void getCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get the categories
        restCategoriesMockMvc.perform(get("/api/categories/{id}", categories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categories.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.topicCount").value(DEFAULT_TOPIC_COUNT))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.topicsYear").value(DEFAULT_TOPICS_YEAR))
            .andExpect(jsonPath("$.topicsMonth").value(DEFAULT_TOPICS_MONTH))
            .andExpect(jsonPath("$.topicsWeek").value(DEFAULT_TOPICS_WEEK))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.textColor").value(DEFAULT_TEXT_COLOR))
            .andExpect(jsonPath("$.readRestricted").value(DEFAULT_READ_RESTRICTED.booleanValue()))
            .andExpect(jsonPath("$.autoCloseHours").value(DEFAULT_AUTO_CLOSE_HOURS.doubleValue()))
            .andExpect(jsonPath("$.postCount").value(DEFAULT_POST_COUNT))
            .andExpect(jsonPath("$.latestPostId").value(DEFAULT_LATEST_POST_ID.intValue()))
            .andExpect(jsonPath("$.latestTopicId").value(DEFAULT_LATEST_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.parentCategoryId").value(DEFAULT_PARENT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.postsYear").value(DEFAULT_POSTS_YEAR))
            .andExpect(jsonPath("$.postsMonth").value(DEFAULT_POSTS_MONTH))
            .andExpect(jsonPath("$.postsWeek").value(DEFAULT_POSTS_WEEK))
            .andExpect(jsonPath("$.emailIn").value(DEFAULT_EMAIL_IN))
            .andExpect(jsonPath("$.emailInAllowStrangers").value(DEFAULT_EMAIL_IN_ALLOW_STRANGERS.booleanValue()))
            .andExpect(jsonPath("$.topicsDay").value(DEFAULT_TOPICS_DAY))
            .andExpect(jsonPath("$.postsDay").value(DEFAULT_POSTS_DAY))
            .andExpect(jsonPath("$.allowBadges").value(DEFAULT_ALLOW_BADGES.booleanValue()))
            .andExpect(jsonPath("$.nameLower").value(DEFAULT_NAME_LOWER))
            .andExpect(jsonPath("$.autoCloseBasedOnLastPost").value(DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST.booleanValue()))
            .andExpect(jsonPath("$.topicTemplate").value(DEFAULT_TOPIC_TEMPLATE))
            .andExpect(jsonPath("$.containsMessages").value(DEFAULT_CONTAINS_MESSAGES.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.sortAscending").value(DEFAULT_SORT_ASCENDING.booleanValue()))
            .andExpect(jsonPath("$.uploadedLogoId").value(DEFAULT_UPLOADED_LOGO_ID.intValue()))
            .andExpect(jsonPath("$.uploadedBackgroundId").value(DEFAULT_UPLOADED_BACKGROUND_ID.intValue()))
            .andExpect(jsonPath("$.topicFeaturedLinkAllowed").value(DEFAULT_TOPIC_FEATURED_LINK_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.allTopicsWiki").value(DEFAULT_ALL_TOPICS_WIKI.booleanValue()))
            .andExpect(jsonPath("$.showSubcategoryList").value(DEFAULT_SHOW_SUBCATEGORY_LIST.booleanValue()))
            .andExpect(jsonPath("$.numFeaturedTopics").value(DEFAULT_NUM_FEATURED_TOPICS))
            .andExpect(jsonPath("$.defaultView").value(DEFAULT_DEFAULT_VIEW))
            .andExpect(jsonPath("$.subcategoryListStyle").value(DEFAULT_SUBCATEGORY_LIST_STYLE))
            .andExpect(jsonPath("$.defaultTopPeriod").value(DEFAULT_DEFAULT_TOP_PERIOD))
            .andExpect(jsonPath("$.mailinglistMirror").value(DEFAULT_MAILINGLIST_MIRROR.booleanValue()))
            .andExpect(jsonPath("$.minimumRequiredTags").value(DEFAULT_MINIMUM_REQUIRED_TAGS))
            .andExpect(jsonPath("$.navigateToFirstPostAfterRead").value(DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ.booleanValue()))
            .andExpect(jsonPath("$.searchPriority").value(DEFAULT_SEARCH_PRIORITY))
            .andExpect(jsonPath("$.allowGlobalTags").value(DEFAULT_ALLOW_GLOBAL_TAGS.booleanValue()))
            .andExpect(jsonPath("$.reviewableByGroupId").value(DEFAULT_REVIEWABLE_BY_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.requiredTagGroupId").value(DEFAULT_REQUIRED_TAG_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.minTagsFromRequiredGroup").value(DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP))
            .andExpect(jsonPath("$.readOnlyBanner").value(DEFAULT_READ_ONLY_BANNER))
            .andExpect(jsonPath("$.defaultListFilter").value(DEFAULT_DEFAULT_LIST_FILTER))
            .andExpect(jsonPath("$.allowUnlimitedOwnerEditsOnFirstPost").value(DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST.booleanValue()));
    }


    @Test
    @Transactional
    public void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        Long id = categories.getId();

        defaultCategoriesShouldBeFound("id.equals=" + id);
        defaultCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where name equals to DEFAULT_NAME
        defaultCategoriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the categoriesList where name equals to UPDATED_NAME
        defaultCategoriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where name not equals to DEFAULT_NAME
        defaultCategoriesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the categoriesList where name not equals to UPDATED_NAME
        defaultCategoriesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCategoriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the categoriesList where name equals to UPDATED_NAME
        defaultCategoriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where name is not null
        defaultCategoriesShouldBeFound("name.specified=true");

        // Get all the categoriesList where name is null
        defaultCategoriesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where name contains DEFAULT_NAME
        defaultCategoriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the categoriesList where name contains UPDATED_NAME
        defaultCategoriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where name does not contain DEFAULT_NAME
        defaultCategoriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the categoriesList where name does not contain UPDATED_NAME
        defaultCategoriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCategoriesByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where color equals to DEFAULT_COLOR
        defaultCategoriesShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the categoriesList where color equals to UPDATED_COLOR
        defaultCategoriesShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where color not equals to DEFAULT_COLOR
        defaultCategoriesShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the categoriesList where color not equals to UPDATED_COLOR
        defaultCategoriesShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByColorIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultCategoriesShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the categoriesList where color equals to UPDATED_COLOR
        defaultCategoriesShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where color is not null
        defaultCategoriesShouldBeFound("color.specified=true");

        // Get all the categoriesList where color is null
        defaultCategoriesShouldNotBeFound("color.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByColorContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where color contains DEFAULT_COLOR
        defaultCategoriesShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the categoriesList where color contains UPDATED_COLOR
        defaultCategoriesShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByColorNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where color does not contain DEFAULT_COLOR
        defaultCategoriesShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the categoriesList where color does not contain UPDATED_COLOR
        defaultCategoriesShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId equals to DEFAULT_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.equals=" + DEFAULT_TOPIC_ID);

        // Get all the categoriesList where topicId equals to UPDATED_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.equals=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId not equals to DEFAULT_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.notEquals=" + DEFAULT_TOPIC_ID);

        // Get all the categoriesList where topicId not equals to UPDATED_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.notEquals=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId in DEFAULT_TOPIC_ID or UPDATED_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.in=" + DEFAULT_TOPIC_ID + "," + UPDATED_TOPIC_ID);

        // Get all the categoriesList where topicId equals to UPDATED_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.in=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId is not null
        defaultCategoriesShouldBeFound("topicId.specified=true");

        // Get all the categoriesList where topicId is null
        defaultCategoriesShouldNotBeFound("topicId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId is greater than or equal to DEFAULT_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.greaterThanOrEqual=" + DEFAULT_TOPIC_ID);

        // Get all the categoriesList where topicId is greater than or equal to UPDATED_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.greaterThanOrEqual=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId is less than or equal to DEFAULT_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.lessThanOrEqual=" + DEFAULT_TOPIC_ID);

        // Get all the categoriesList where topicId is less than or equal to SMALLER_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.lessThanOrEqual=" + SMALLER_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId is less than DEFAULT_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.lessThan=" + DEFAULT_TOPIC_ID);

        // Get all the categoriesList where topicId is less than UPDATED_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.lessThan=" + UPDATED_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicId is greater than DEFAULT_TOPIC_ID
        defaultCategoriesShouldNotBeFound("topicId.greaterThan=" + DEFAULT_TOPIC_ID);

        // Get all the categoriesList where topicId is greater than SMALLER_TOPIC_ID
        defaultCategoriesShouldBeFound("topicId.greaterThan=" + SMALLER_TOPIC_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount equals to DEFAULT_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.equals=" + DEFAULT_TOPIC_COUNT);

        // Get all the categoriesList where topicCount equals to UPDATED_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.equals=" + UPDATED_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount not equals to DEFAULT_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.notEquals=" + DEFAULT_TOPIC_COUNT);

        // Get all the categoriesList where topicCount not equals to UPDATED_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.notEquals=" + UPDATED_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount in DEFAULT_TOPIC_COUNT or UPDATED_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.in=" + DEFAULT_TOPIC_COUNT + "," + UPDATED_TOPIC_COUNT);

        // Get all the categoriesList where topicCount equals to UPDATED_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.in=" + UPDATED_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount is not null
        defaultCategoriesShouldBeFound("topicCount.specified=true");

        // Get all the categoriesList where topicCount is null
        defaultCategoriesShouldNotBeFound("topicCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount is greater than or equal to DEFAULT_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.greaterThanOrEqual=" + DEFAULT_TOPIC_COUNT);

        // Get all the categoriesList where topicCount is greater than or equal to UPDATED_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.greaterThanOrEqual=" + UPDATED_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount is less than or equal to DEFAULT_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.lessThanOrEqual=" + DEFAULT_TOPIC_COUNT);

        // Get all the categoriesList where topicCount is less than or equal to SMALLER_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.lessThanOrEqual=" + SMALLER_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount is less than DEFAULT_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.lessThan=" + DEFAULT_TOPIC_COUNT);

        // Get all the categoriesList where topicCount is less than UPDATED_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.lessThan=" + UPDATED_TOPIC_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicCount is greater than DEFAULT_TOPIC_COUNT
        defaultCategoriesShouldNotBeFound("topicCount.greaterThan=" + DEFAULT_TOPIC_COUNT);

        // Get all the categoriesList where topicCount is greater than SMALLER_TOPIC_COUNT
        defaultCategoriesShouldBeFound("topicCount.greaterThan=" + SMALLER_TOPIC_COUNT);
    }


    @Test
    @Transactional
    public void getAllCategoriesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where userId equals to DEFAULT_USER_ID
        defaultCategoriesShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the categoriesList where userId equals to UPDATED_USER_ID
        defaultCategoriesShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where userId not equals to DEFAULT_USER_ID
        defaultCategoriesShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the categoriesList where userId not equals to UPDATED_USER_ID
        defaultCategoriesShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultCategoriesShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the categoriesList where userId equals to UPDATED_USER_ID
        defaultCategoriesShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where userId is not null
        defaultCategoriesShouldBeFound("userId.specified=true");

        // Get all the categoriesList where userId is null
        defaultCategoriesShouldNotBeFound("userId.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByUserIdContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where userId contains DEFAULT_USER_ID
        defaultCategoriesShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the categoriesList where userId contains UPDATED_USER_ID
        defaultCategoriesShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where userId does not contain DEFAULT_USER_ID
        defaultCategoriesShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the categoriesList where userId does not contain UPDATED_USER_ID
        defaultCategoriesShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear equals to DEFAULT_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.equals=" + DEFAULT_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear equals to UPDATED_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.equals=" + UPDATED_TOPICS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear not equals to DEFAULT_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.notEquals=" + DEFAULT_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear not equals to UPDATED_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.notEquals=" + UPDATED_TOPICS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear in DEFAULT_TOPICS_YEAR or UPDATED_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.in=" + DEFAULT_TOPICS_YEAR + "," + UPDATED_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear equals to UPDATED_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.in=" + UPDATED_TOPICS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear is not null
        defaultCategoriesShouldBeFound("topicsYear.specified=true");

        // Get all the categoriesList where topicsYear is null
        defaultCategoriesShouldNotBeFound("topicsYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear is greater than or equal to DEFAULT_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.greaterThanOrEqual=" + DEFAULT_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear is greater than or equal to UPDATED_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.greaterThanOrEqual=" + UPDATED_TOPICS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear is less than or equal to DEFAULT_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.lessThanOrEqual=" + DEFAULT_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear is less than or equal to SMALLER_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.lessThanOrEqual=" + SMALLER_TOPICS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear is less than DEFAULT_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.lessThan=" + DEFAULT_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear is less than UPDATED_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.lessThan=" + UPDATED_TOPICS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsYear is greater than DEFAULT_TOPICS_YEAR
        defaultCategoriesShouldNotBeFound("topicsYear.greaterThan=" + DEFAULT_TOPICS_YEAR);

        // Get all the categoriesList where topicsYear is greater than SMALLER_TOPICS_YEAR
        defaultCategoriesShouldBeFound("topicsYear.greaterThan=" + SMALLER_TOPICS_YEAR);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth equals to DEFAULT_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.equals=" + DEFAULT_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth equals to UPDATED_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.equals=" + UPDATED_TOPICS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth not equals to DEFAULT_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.notEquals=" + DEFAULT_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth not equals to UPDATED_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.notEquals=" + UPDATED_TOPICS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth in DEFAULT_TOPICS_MONTH or UPDATED_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.in=" + DEFAULT_TOPICS_MONTH + "," + UPDATED_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth equals to UPDATED_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.in=" + UPDATED_TOPICS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth is not null
        defaultCategoriesShouldBeFound("topicsMonth.specified=true");

        // Get all the categoriesList where topicsMonth is null
        defaultCategoriesShouldNotBeFound("topicsMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth is greater than or equal to DEFAULT_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.greaterThanOrEqual=" + DEFAULT_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth is greater than or equal to UPDATED_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.greaterThanOrEqual=" + UPDATED_TOPICS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth is less than or equal to DEFAULT_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.lessThanOrEqual=" + DEFAULT_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth is less than or equal to SMALLER_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.lessThanOrEqual=" + SMALLER_TOPICS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth is less than DEFAULT_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.lessThan=" + DEFAULT_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth is less than UPDATED_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.lessThan=" + UPDATED_TOPICS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsMonth is greater than DEFAULT_TOPICS_MONTH
        defaultCategoriesShouldNotBeFound("topicsMonth.greaterThan=" + DEFAULT_TOPICS_MONTH);

        // Get all the categoriesList where topicsMonth is greater than SMALLER_TOPICS_MONTH
        defaultCategoriesShouldBeFound("topicsMonth.greaterThan=" + SMALLER_TOPICS_MONTH);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek equals to DEFAULT_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.equals=" + DEFAULT_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek equals to UPDATED_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.equals=" + UPDATED_TOPICS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek not equals to DEFAULT_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.notEquals=" + DEFAULT_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek not equals to UPDATED_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.notEquals=" + UPDATED_TOPICS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek in DEFAULT_TOPICS_WEEK or UPDATED_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.in=" + DEFAULT_TOPICS_WEEK + "," + UPDATED_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek equals to UPDATED_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.in=" + UPDATED_TOPICS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek is not null
        defaultCategoriesShouldBeFound("topicsWeek.specified=true");

        // Get all the categoriesList where topicsWeek is null
        defaultCategoriesShouldNotBeFound("topicsWeek.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek is greater than or equal to DEFAULT_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.greaterThanOrEqual=" + DEFAULT_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek is greater than or equal to UPDATED_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.greaterThanOrEqual=" + UPDATED_TOPICS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek is less than or equal to DEFAULT_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.lessThanOrEqual=" + DEFAULT_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek is less than or equal to SMALLER_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.lessThanOrEqual=" + SMALLER_TOPICS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek is less than DEFAULT_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.lessThan=" + DEFAULT_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek is less than UPDATED_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.lessThan=" + UPDATED_TOPICS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsWeekIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsWeek is greater than DEFAULT_TOPICS_WEEK
        defaultCategoriesShouldNotBeFound("topicsWeek.greaterThan=" + DEFAULT_TOPICS_WEEK);

        // Get all the categoriesList where topicsWeek is greater than SMALLER_TOPICS_WEEK
        defaultCategoriesShouldBeFound("topicsWeek.greaterThan=" + SMALLER_TOPICS_WEEK);
    }


    @Test
    @Transactional
    public void getAllCategoriesBySlugIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where slug equals to DEFAULT_SLUG
        defaultCategoriesShouldBeFound("slug.equals=" + DEFAULT_SLUG);

        // Get all the categoriesList where slug equals to UPDATED_SLUG
        defaultCategoriesShouldNotBeFound("slug.equals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySlugIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where slug not equals to DEFAULT_SLUG
        defaultCategoriesShouldNotBeFound("slug.notEquals=" + DEFAULT_SLUG);

        // Get all the categoriesList where slug not equals to UPDATED_SLUG
        defaultCategoriesShouldBeFound("slug.notEquals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySlugIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where slug in DEFAULT_SLUG or UPDATED_SLUG
        defaultCategoriesShouldBeFound("slug.in=" + DEFAULT_SLUG + "," + UPDATED_SLUG);

        // Get all the categoriesList where slug equals to UPDATED_SLUG
        defaultCategoriesShouldNotBeFound("slug.in=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySlugIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where slug is not null
        defaultCategoriesShouldBeFound("slug.specified=true");

        // Get all the categoriesList where slug is null
        defaultCategoriesShouldNotBeFound("slug.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesBySlugContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where slug contains DEFAULT_SLUG
        defaultCategoriesShouldBeFound("slug.contains=" + DEFAULT_SLUG);

        // Get all the categoriesList where slug contains UPDATED_SLUG
        defaultCategoriesShouldNotBeFound("slug.contains=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySlugNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where slug does not contain DEFAULT_SLUG
        defaultCategoriesShouldNotBeFound("slug.doesNotContain=" + DEFAULT_SLUG);

        // Get all the categoriesList where slug does not contain UPDATED_SLUG
        defaultCategoriesShouldBeFound("slug.doesNotContain=" + UPDATED_SLUG);
    }


    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where description equals to DEFAULT_DESCRIPTION
        defaultCategoriesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the categoriesList where description equals to UPDATED_DESCRIPTION
        defaultCategoriesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where description not equals to DEFAULT_DESCRIPTION
        defaultCategoriesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the categoriesList where description not equals to UPDATED_DESCRIPTION
        defaultCategoriesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCategoriesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the categoriesList where description equals to UPDATED_DESCRIPTION
        defaultCategoriesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where description is not null
        defaultCategoriesShouldBeFound("description.specified=true");

        // Get all the categoriesList where description is null
        defaultCategoriesShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where description contains DEFAULT_DESCRIPTION
        defaultCategoriesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the categoriesList where description contains UPDATED_DESCRIPTION
        defaultCategoriesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where description does not contain DEFAULT_DESCRIPTION
        defaultCategoriesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the categoriesList where description does not contain UPDATED_DESCRIPTION
        defaultCategoriesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTextColorIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where textColor equals to DEFAULT_TEXT_COLOR
        defaultCategoriesShouldBeFound("textColor.equals=" + DEFAULT_TEXT_COLOR);

        // Get all the categoriesList where textColor equals to UPDATED_TEXT_COLOR
        defaultCategoriesShouldNotBeFound("textColor.equals=" + UPDATED_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTextColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where textColor not equals to DEFAULT_TEXT_COLOR
        defaultCategoriesShouldNotBeFound("textColor.notEquals=" + DEFAULT_TEXT_COLOR);

        // Get all the categoriesList where textColor not equals to UPDATED_TEXT_COLOR
        defaultCategoriesShouldBeFound("textColor.notEquals=" + UPDATED_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTextColorIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where textColor in DEFAULT_TEXT_COLOR or UPDATED_TEXT_COLOR
        defaultCategoriesShouldBeFound("textColor.in=" + DEFAULT_TEXT_COLOR + "," + UPDATED_TEXT_COLOR);

        // Get all the categoriesList where textColor equals to UPDATED_TEXT_COLOR
        defaultCategoriesShouldNotBeFound("textColor.in=" + UPDATED_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTextColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where textColor is not null
        defaultCategoriesShouldBeFound("textColor.specified=true");

        // Get all the categoriesList where textColor is null
        defaultCategoriesShouldNotBeFound("textColor.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByTextColorContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where textColor contains DEFAULT_TEXT_COLOR
        defaultCategoriesShouldBeFound("textColor.contains=" + DEFAULT_TEXT_COLOR);

        // Get all the categoriesList where textColor contains UPDATED_TEXT_COLOR
        defaultCategoriesShouldNotBeFound("textColor.contains=" + UPDATED_TEXT_COLOR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTextColorNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where textColor does not contain DEFAULT_TEXT_COLOR
        defaultCategoriesShouldNotBeFound("textColor.doesNotContain=" + DEFAULT_TEXT_COLOR);

        // Get all the categoriesList where textColor does not contain UPDATED_TEXT_COLOR
        defaultCategoriesShouldBeFound("textColor.doesNotContain=" + UPDATED_TEXT_COLOR);
    }


    @Test
    @Transactional
    public void getAllCategoriesByReadRestrictedIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readRestricted equals to DEFAULT_READ_RESTRICTED
        defaultCategoriesShouldBeFound("readRestricted.equals=" + DEFAULT_READ_RESTRICTED);

        // Get all the categoriesList where readRestricted equals to UPDATED_READ_RESTRICTED
        defaultCategoriesShouldNotBeFound("readRestricted.equals=" + UPDATED_READ_RESTRICTED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadRestrictedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readRestricted not equals to DEFAULT_READ_RESTRICTED
        defaultCategoriesShouldNotBeFound("readRestricted.notEquals=" + DEFAULT_READ_RESTRICTED);

        // Get all the categoriesList where readRestricted not equals to UPDATED_READ_RESTRICTED
        defaultCategoriesShouldBeFound("readRestricted.notEquals=" + UPDATED_READ_RESTRICTED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadRestrictedIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readRestricted in DEFAULT_READ_RESTRICTED or UPDATED_READ_RESTRICTED
        defaultCategoriesShouldBeFound("readRestricted.in=" + DEFAULT_READ_RESTRICTED + "," + UPDATED_READ_RESTRICTED);

        // Get all the categoriesList where readRestricted equals to UPDATED_READ_RESTRICTED
        defaultCategoriesShouldNotBeFound("readRestricted.in=" + UPDATED_READ_RESTRICTED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadRestrictedIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readRestricted is not null
        defaultCategoriesShouldBeFound("readRestricted.specified=true");

        // Get all the categoriesList where readRestricted is null
        defaultCategoriesShouldNotBeFound("readRestricted.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours equals to DEFAULT_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.equals=" + DEFAULT_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours equals to UPDATED_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.equals=" + UPDATED_AUTO_CLOSE_HOURS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours not equals to DEFAULT_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.notEquals=" + DEFAULT_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours not equals to UPDATED_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.notEquals=" + UPDATED_AUTO_CLOSE_HOURS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours in DEFAULT_AUTO_CLOSE_HOURS or UPDATED_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.in=" + DEFAULT_AUTO_CLOSE_HOURS + "," + UPDATED_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours equals to UPDATED_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.in=" + UPDATED_AUTO_CLOSE_HOURS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours is not null
        defaultCategoriesShouldBeFound("autoCloseHours.specified=true");

        // Get all the categoriesList where autoCloseHours is null
        defaultCategoriesShouldNotBeFound("autoCloseHours.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours is greater than or equal to DEFAULT_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.greaterThanOrEqual=" + DEFAULT_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours is greater than or equal to UPDATED_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.greaterThanOrEqual=" + UPDATED_AUTO_CLOSE_HOURS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours is less than or equal to DEFAULT_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.lessThanOrEqual=" + DEFAULT_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours is less than or equal to SMALLER_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.lessThanOrEqual=" + SMALLER_AUTO_CLOSE_HOURS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours is less than DEFAULT_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.lessThan=" + DEFAULT_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours is less than UPDATED_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.lessThan=" + UPDATED_AUTO_CLOSE_HOURS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseHours is greater than DEFAULT_AUTO_CLOSE_HOURS
        defaultCategoriesShouldNotBeFound("autoCloseHours.greaterThan=" + DEFAULT_AUTO_CLOSE_HOURS);

        // Get all the categoriesList where autoCloseHours is greater than SMALLER_AUTO_CLOSE_HOURS
        defaultCategoriesShouldBeFound("autoCloseHours.greaterThan=" + SMALLER_AUTO_CLOSE_HOURS);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount equals to DEFAULT_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.equals=" + DEFAULT_POST_COUNT);

        // Get all the categoriesList where postCount equals to UPDATED_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.equals=" + UPDATED_POST_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount not equals to DEFAULT_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.notEquals=" + DEFAULT_POST_COUNT);

        // Get all the categoriesList where postCount not equals to UPDATED_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.notEquals=" + UPDATED_POST_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount in DEFAULT_POST_COUNT or UPDATED_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.in=" + DEFAULT_POST_COUNT + "," + UPDATED_POST_COUNT);

        // Get all the categoriesList where postCount equals to UPDATED_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.in=" + UPDATED_POST_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount is not null
        defaultCategoriesShouldBeFound("postCount.specified=true");

        // Get all the categoriesList where postCount is null
        defaultCategoriesShouldNotBeFound("postCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount is greater than or equal to DEFAULT_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.greaterThanOrEqual=" + DEFAULT_POST_COUNT);

        // Get all the categoriesList where postCount is greater than or equal to UPDATED_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.greaterThanOrEqual=" + UPDATED_POST_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount is less than or equal to DEFAULT_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.lessThanOrEqual=" + DEFAULT_POST_COUNT);

        // Get all the categoriesList where postCount is less than or equal to SMALLER_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.lessThanOrEqual=" + SMALLER_POST_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount is less than DEFAULT_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.lessThan=" + DEFAULT_POST_COUNT);

        // Get all the categoriesList where postCount is less than UPDATED_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.lessThan=" + UPDATED_POST_COUNT);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postCount is greater than DEFAULT_POST_COUNT
        defaultCategoriesShouldNotBeFound("postCount.greaterThan=" + DEFAULT_POST_COUNT);

        // Get all the categoriesList where postCount is greater than SMALLER_POST_COUNT
        defaultCategoriesShouldBeFound("postCount.greaterThan=" + SMALLER_POST_COUNT);
    }


    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId equals to DEFAULT_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.equals=" + DEFAULT_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId equals to UPDATED_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.equals=" + UPDATED_LATEST_POST_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId not equals to DEFAULT_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.notEquals=" + DEFAULT_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId not equals to UPDATED_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.notEquals=" + UPDATED_LATEST_POST_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId in DEFAULT_LATEST_POST_ID or UPDATED_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.in=" + DEFAULT_LATEST_POST_ID + "," + UPDATED_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId equals to UPDATED_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.in=" + UPDATED_LATEST_POST_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId is not null
        defaultCategoriesShouldBeFound("latestPostId.specified=true");

        // Get all the categoriesList where latestPostId is null
        defaultCategoriesShouldNotBeFound("latestPostId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId is greater than or equal to DEFAULT_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.greaterThanOrEqual=" + DEFAULT_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId is greater than or equal to UPDATED_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.greaterThanOrEqual=" + UPDATED_LATEST_POST_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId is less than or equal to DEFAULT_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.lessThanOrEqual=" + DEFAULT_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId is less than or equal to SMALLER_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.lessThanOrEqual=" + SMALLER_LATEST_POST_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId is less than DEFAULT_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.lessThan=" + DEFAULT_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId is less than UPDATED_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.lessThan=" + UPDATED_LATEST_POST_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestPostIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestPostId is greater than DEFAULT_LATEST_POST_ID
        defaultCategoriesShouldNotBeFound("latestPostId.greaterThan=" + DEFAULT_LATEST_POST_ID);

        // Get all the categoriesList where latestPostId is greater than SMALLER_LATEST_POST_ID
        defaultCategoriesShouldBeFound("latestPostId.greaterThan=" + SMALLER_LATEST_POST_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId equals to DEFAULT_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.equals=" + DEFAULT_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId equals to UPDATED_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.equals=" + UPDATED_LATEST_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId not equals to DEFAULT_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.notEquals=" + DEFAULT_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId not equals to UPDATED_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.notEquals=" + UPDATED_LATEST_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId in DEFAULT_LATEST_TOPIC_ID or UPDATED_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.in=" + DEFAULT_LATEST_TOPIC_ID + "," + UPDATED_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId equals to UPDATED_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.in=" + UPDATED_LATEST_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId is not null
        defaultCategoriesShouldBeFound("latestTopicId.specified=true");

        // Get all the categoriesList where latestTopicId is null
        defaultCategoriesShouldNotBeFound("latestTopicId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId is greater than or equal to DEFAULT_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.greaterThanOrEqual=" + DEFAULT_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId is greater than or equal to UPDATED_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.greaterThanOrEqual=" + UPDATED_LATEST_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId is less than or equal to DEFAULT_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.lessThanOrEqual=" + DEFAULT_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId is less than or equal to SMALLER_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.lessThanOrEqual=" + SMALLER_LATEST_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId is less than DEFAULT_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.lessThan=" + DEFAULT_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId is less than UPDATED_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.lessThan=" + UPDATED_LATEST_TOPIC_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByLatestTopicIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where latestTopicId is greater than DEFAULT_LATEST_TOPIC_ID
        defaultCategoriesShouldNotBeFound("latestTopicId.greaterThan=" + DEFAULT_LATEST_TOPIC_ID);

        // Get all the categoriesList where latestTopicId is greater than SMALLER_LATEST_TOPIC_ID
        defaultCategoriesShouldBeFound("latestTopicId.greaterThan=" + SMALLER_LATEST_TOPIC_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position equals to DEFAULT_POSITION
        defaultCategoriesShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the categoriesList where position equals to UPDATED_POSITION
        defaultCategoriesShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position not equals to DEFAULT_POSITION
        defaultCategoriesShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the categoriesList where position not equals to UPDATED_POSITION
        defaultCategoriesShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultCategoriesShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the categoriesList where position equals to UPDATED_POSITION
        defaultCategoriesShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position is not null
        defaultCategoriesShouldBeFound("position.specified=true");

        // Get all the categoriesList where position is null
        defaultCategoriesShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position is greater than or equal to DEFAULT_POSITION
        defaultCategoriesShouldBeFound("position.greaterThanOrEqual=" + DEFAULT_POSITION);

        // Get all the categoriesList where position is greater than or equal to UPDATED_POSITION
        defaultCategoriesShouldNotBeFound("position.greaterThanOrEqual=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position is less than or equal to DEFAULT_POSITION
        defaultCategoriesShouldBeFound("position.lessThanOrEqual=" + DEFAULT_POSITION);

        // Get all the categoriesList where position is less than or equal to SMALLER_POSITION
        defaultCategoriesShouldNotBeFound("position.lessThanOrEqual=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position is less than DEFAULT_POSITION
        defaultCategoriesShouldNotBeFound("position.lessThan=" + DEFAULT_POSITION);

        // Get all the categoriesList where position is less than UPDATED_POSITION
        defaultCategoriesShouldBeFound("position.lessThan=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where position is greater than DEFAULT_POSITION
        defaultCategoriesShouldNotBeFound("position.greaterThan=" + DEFAULT_POSITION);

        // Get all the categoriesList where position is greater than SMALLER_POSITION
        defaultCategoriesShouldBeFound("position.greaterThan=" + SMALLER_POSITION);
    }


    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId equals to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.equals=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId equals to UPDATED_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.equals=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId not equals to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.notEquals=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId not equals to UPDATED_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.notEquals=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId in DEFAULT_PARENT_CATEGORY_ID or UPDATED_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.in=" + DEFAULT_PARENT_CATEGORY_ID + "," + UPDATED_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId equals to UPDATED_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.in=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId is not null
        defaultCategoriesShouldBeFound("parentCategoryId.specified=true");

        // Get all the categoriesList where parentCategoryId is null
        defaultCategoriesShouldNotBeFound("parentCategoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId is greater than or equal to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.greaterThanOrEqual=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId is greater than or equal to UPDATED_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.greaterThanOrEqual=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId is less than or equal to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.lessThanOrEqual=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId is less than or equal to SMALLER_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.lessThanOrEqual=" + SMALLER_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId is less than DEFAULT_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.lessThan=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId is less than UPDATED_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.lessThan=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByParentCategoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where parentCategoryId is greater than DEFAULT_PARENT_CATEGORY_ID
        defaultCategoriesShouldNotBeFound("parentCategoryId.greaterThan=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoriesList where parentCategoryId is greater than SMALLER_PARENT_CATEGORY_ID
        defaultCategoriesShouldBeFound("parentCategoryId.greaterThan=" + SMALLER_PARENT_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear equals to DEFAULT_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.equals=" + DEFAULT_POSTS_YEAR);

        // Get all the categoriesList where postsYear equals to UPDATED_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.equals=" + UPDATED_POSTS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear not equals to DEFAULT_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.notEquals=" + DEFAULT_POSTS_YEAR);

        // Get all the categoriesList where postsYear not equals to UPDATED_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.notEquals=" + UPDATED_POSTS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear in DEFAULT_POSTS_YEAR or UPDATED_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.in=" + DEFAULT_POSTS_YEAR + "," + UPDATED_POSTS_YEAR);

        // Get all the categoriesList where postsYear equals to UPDATED_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.in=" + UPDATED_POSTS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear is not null
        defaultCategoriesShouldBeFound("postsYear.specified=true");

        // Get all the categoriesList where postsYear is null
        defaultCategoriesShouldNotBeFound("postsYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear is greater than or equal to DEFAULT_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.greaterThanOrEqual=" + DEFAULT_POSTS_YEAR);

        // Get all the categoriesList where postsYear is greater than or equal to UPDATED_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.greaterThanOrEqual=" + UPDATED_POSTS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear is less than or equal to DEFAULT_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.lessThanOrEqual=" + DEFAULT_POSTS_YEAR);

        // Get all the categoriesList where postsYear is less than or equal to SMALLER_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.lessThanOrEqual=" + SMALLER_POSTS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear is less than DEFAULT_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.lessThan=" + DEFAULT_POSTS_YEAR);

        // Get all the categoriesList where postsYear is less than UPDATED_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.lessThan=" + UPDATED_POSTS_YEAR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsYear is greater than DEFAULT_POSTS_YEAR
        defaultCategoriesShouldNotBeFound("postsYear.greaterThan=" + DEFAULT_POSTS_YEAR);

        // Get all the categoriesList where postsYear is greater than SMALLER_POSTS_YEAR
        defaultCategoriesShouldBeFound("postsYear.greaterThan=" + SMALLER_POSTS_YEAR);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth equals to DEFAULT_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.equals=" + DEFAULT_POSTS_MONTH);

        // Get all the categoriesList where postsMonth equals to UPDATED_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.equals=" + UPDATED_POSTS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth not equals to DEFAULT_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.notEquals=" + DEFAULT_POSTS_MONTH);

        // Get all the categoriesList where postsMonth not equals to UPDATED_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.notEquals=" + UPDATED_POSTS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth in DEFAULT_POSTS_MONTH or UPDATED_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.in=" + DEFAULT_POSTS_MONTH + "," + UPDATED_POSTS_MONTH);

        // Get all the categoriesList where postsMonth equals to UPDATED_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.in=" + UPDATED_POSTS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth is not null
        defaultCategoriesShouldBeFound("postsMonth.specified=true");

        // Get all the categoriesList where postsMonth is null
        defaultCategoriesShouldNotBeFound("postsMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth is greater than or equal to DEFAULT_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.greaterThanOrEqual=" + DEFAULT_POSTS_MONTH);

        // Get all the categoriesList where postsMonth is greater than or equal to UPDATED_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.greaterThanOrEqual=" + UPDATED_POSTS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth is less than or equal to DEFAULT_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.lessThanOrEqual=" + DEFAULT_POSTS_MONTH);

        // Get all the categoriesList where postsMonth is less than or equal to SMALLER_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.lessThanOrEqual=" + SMALLER_POSTS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth is less than DEFAULT_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.lessThan=" + DEFAULT_POSTS_MONTH);

        // Get all the categoriesList where postsMonth is less than UPDATED_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.lessThan=" + UPDATED_POSTS_MONTH);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsMonth is greater than DEFAULT_POSTS_MONTH
        defaultCategoriesShouldNotBeFound("postsMonth.greaterThan=" + DEFAULT_POSTS_MONTH);

        // Get all the categoriesList where postsMonth is greater than SMALLER_POSTS_MONTH
        defaultCategoriesShouldBeFound("postsMonth.greaterThan=" + SMALLER_POSTS_MONTH);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek equals to DEFAULT_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.equals=" + DEFAULT_POSTS_WEEK);

        // Get all the categoriesList where postsWeek equals to UPDATED_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.equals=" + UPDATED_POSTS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek not equals to DEFAULT_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.notEquals=" + DEFAULT_POSTS_WEEK);

        // Get all the categoriesList where postsWeek not equals to UPDATED_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.notEquals=" + UPDATED_POSTS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek in DEFAULT_POSTS_WEEK or UPDATED_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.in=" + DEFAULT_POSTS_WEEK + "," + UPDATED_POSTS_WEEK);

        // Get all the categoriesList where postsWeek equals to UPDATED_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.in=" + UPDATED_POSTS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek is not null
        defaultCategoriesShouldBeFound("postsWeek.specified=true");

        // Get all the categoriesList where postsWeek is null
        defaultCategoriesShouldNotBeFound("postsWeek.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek is greater than or equal to DEFAULT_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.greaterThanOrEqual=" + DEFAULT_POSTS_WEEK);

        // Get all the categoriesList where postsWeek is greater than or equal to UPDATED_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.greaterThanOrEqual=" + UPDATED_POSTS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek is less than or equal to DEFAULT_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.lessThanOrEqual=" + DEFAULT_POSTS_WEEK);

        // Get all the categoriesList where postsWeek is less than or equal to SMALLER_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.lessThanOrEqual=" + SMALLER_POSTS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek is less than DEFAULT_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.lessThan=" + DEFAULT_POSTS_WEEK);

        // Get all the categoriesList where postsWeek is less than UPDATED_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.lessThan=" + UPDATED_POSTS_WEEK);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsWeekIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsWeek is greater than DEFAULT_POSTS_WEEK
        defaultCategoriesShouldNotBeFound("postsWeek.greaterThan=" + DEFAULT_POSTS_WEEK);

        // Get all the categoriesList where postsWeek is greater than SMALLER_POSTS_WEEK
        defaultCategoriesShouldBeFound("postsWeek.greaterThan=" + SMALLER_POSTS_WEEK);
    }


    @Test
    @Transactional
    public void getAllCategoriesByEmailInIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailIn equals to DEFAULT_EMAIL_IN
        defaultCategoriesShouldBeFound("emailIn.equals=" + DEFAULT_EMAIL_IN);

        // Get all the categoriesList where emailIn equals to UPDATED_EMAIL_IN
        defaultCategoriesShouldNotBeFound("emailIn.equals=" + UPDATED_EMAIL_IN);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailIn not equals to DEFAULT_EMAIL_IN
        defaultCategoriesShouldNotBeFound("emailIn.notEquals=" + DEFAULT_EMAIL_IN);

        // Get all the categoriesList where emailIn not equals to UPDATED_EMAIL_IN
        defaultCategoriesShouldBeFound("emailIn.notEquals=" + UPDATED_EMAIL_IN);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailIn in DEFAULT_EMAIL_IN or UPDATED_EMAIL_IN
        defaultCategoriesShouldBeFound("emailIn.in=" + DEFAULT_EMAIL_IN + "," + UPDATED_EMAIL_IN);

        // Get all the categoriesList where emailIn equals to UPDATED_EMAIL_IN
        defaultCategoriesShouldNotBeFound("emailIn.in=" + UPDATED_EMAIL_IN);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailIn is not null
        defaultCategoriesShouldBeFound("emailIn.specified=true");

        // Get all the categoriesList where emailIn is null
        defaultCategoriesShouldNotBeFound("emailIn.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByEmailInContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailIn contains DEFAULT_EMAIL_IN
        defaultCategoriesShouldBeFound("emailIn.contains=" + DEFAULT_EMAIL_IN);

        // Get all the categoriesList where emailIn contains UPDATED_EMAIL_IN
        defaultCategoriesShouldNotBeFound("emailIn.contains=" + UPDATED_EMAIL_IN);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailIn does not contain DEFAULT_EMAIL_IN
        defaultCategoriesShouldNotBeFound("emailIn.doesNotContain=" + DEFAULT_EMAIL_IN);

        // Get all the categoriesList where emailIn does not contain UPDATED_EMAIL_IN
        defaultCategoriesShouldBeFound("emailIn.doesNotContain=" + UPDATED_EMAIL_IN);
    }


    @Test
    @Transactional
    public void getAllCategoriesByEmailInAllowStrangersIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailInAllowStrangers equals to DEFAULT_EMAIL_IN_ALLOW_STRANGERS
        defaultCategoriesShouldBeFound("emailInAllowStrangers.equals=" + DEFAULT_EMAIL_IN_ALLOW_STRANGERS);

        // Get all the categoriesList where emailInAllowStrangers equals to UPDATED_EMAIL_IN_ALLOW_STRANGERS
        defaultCategoriesShouldNotBeFound("emailInAllowStrangers.equals=" + UPDATED_EMAIL_IN_ALLOW_STRANGERS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInAllowStrangersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailInAllowStrangers not equals to DEFAULT_EMAIL_IN_ALLOW_STRANGERS
        defaultCategoriesShouldNotBeFound("emailInAllowStrangers.notEquals=" + DEFAULT_EMAIL_IN_ALLOW_STRANGERS);

        // Get all the categoriesList where emailInAllowStrangers not equals to UPDATED_EMAIL_IN_ALLOW_STRANGERS
        defaultCategoriesShouldBeFound("emailInAllowStrangers.notEquals=" + UPDATED_EMAIL_IN_ALLOW_STRANGERS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInAllowStrangersIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailInAllowStrangers in DEFAULT_EMAIL_IN_ALLOW_STRANGERS or UPDATED_EMAIL_IN_ALLOW_STRANGERS
        defaultCategoriesShouldBeFound("emailInAllowStrangers.in=" + DEFAULT_EMAIL_IN_ALLOW_STRANGERS + "," + UPDATED_EMAIL_IN_ALLOW_STRANGERS);

        // Get all the categoriesList where emailInAllowStrangers equals to UPDATED_EMAIL_IN_ALLOW_STRANGERS
        defaultCategoriesShouldNotBeFound("emailInAllowStrangers.in=" + UPDATED_EMAIL_IN_ALLOW_STRANGERS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByEmailInAllowStrangersIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where emailInAllowStrangers is not null
        defaultCategoriesShouldBeFound("emailInAllowStrangers.specified=true");

        // Get all the categoriesList where emailInAllowStrangers is null
        defaultCategoriesShouldNotBeFound("emailInAllowStrangers.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay equals to DEFAULT_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.equals=" + DEFAULT_TOPICS_DAY);

        // Get all the categoriesList where topicsDay equals to UPDATED_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.equals=" + UPDATED_TOPICS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay not equals to DEFAULT_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.notEquals=" + DEFAULT_TOPICS_DAY);

        // Get all the categoriesList where topicsDay not equals to UPDATED_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.notEquals=" + UPDATED_TOPICS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay in DEFAULT_TOPICS_DAY or UPDATED_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.in=" + DEFAULT_TOPICS_DAY + "," + UPDATED_TOPICS_DAY);

        // Get all the categoriesList where topicsDay equals to UPDATED_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.in=" + UPDATED_TOPICS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay is not null
        defaultCategoriesShouldBeFound("topicsDay.specified=true");

        // Get all the categoriesList where topicsDay is null
        defaultCategoriesShouldNotBeFound("topicsDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay is greater than or equal to DEFAULT_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.greaterThanOrEqual=" + DEFAULT_TOPICS_DAY);

        // Get all the categoriesList where topicsDay is greater than or equal to UPDATED_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.greaterThanOrEqual=" + UPDATED_TOPICS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay is less than or equal to DEFAULT_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.lessThanOrEqual=" + DEFAULT_TOPICS_DAY);

        // Get all the categoriesList where topicsDay is less than or equal to SMALLER_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.lessThanOrEqual=" + SMALLER_TOPICS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay is less than DEFAULT_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.lessThan=" + DEFAULT_TOPICS_DAY);

        // Get all the categoriesList where topicsDay is less than UPDATED_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.lessThan=" + UPDATED_TOPICS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicsDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicsDay is greater than DEFAULT_TOPICS_DAY
        defaultCategoriesShouldNotBeFound("topicsDay.greaterThan=" + DEFAULT_TOPICS_DAY);

        // Get all the categoriesList where topicsDay is greater than SMALLER_TOPICS_DAY
        defaultCategoriesShouldBeFound("topicsDay.greaterThan=" + SMALLER_TOPICS_DAY);
    }


    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay equals to DEFAULT_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.equals=" + DEFAULT_POSTS_DAY);

        // Get all the categoriesList where postsDay equals to UPDATED_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.equals=" + UPDATED_POSTS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay not equals to DEFAULT_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.notEquals=" + DEFAULT_POSTS_DAY);

        // Get all the categoriesList where postsDay not equals to UPDATED_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.notEquals=" + UPDATED_POSTS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay in DEFAULT_POSTS_DAY or UPDATED_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.in=" + DEFAULT_POSTS_DAY + "," + UPDATED_POSTS_DAY);

        // Get all the categoriesList where postsDay equals to UPDATED_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.in=" + UPDATED_POSTS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay is not null
        defaultCategoriesShouldBeFound("postsDay.specified=true");

        // Get all the categoriesList where postsDay is null
        defaultCategoriesShouldNotBeFound("postsDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay is greater than or equal to DEFAULT_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.greaterThanOrEqual=" + DEFAULT_POSTS_DAY);

        // Get all the categoriesList where postsDay is greater than or equal to UPDATED_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.greaterThanOrEqual=" + UPDATED_POSTS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay is less than or equal to DEFAULT_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.lessThanOrEqual=" + DEFAULT_POSTS_DAY);

        // Get all the categoriesList where postsDay is less than or equal to SMALLER_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.lessThanOrEqual=" + SMALLER_POSTS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay is less than DEFAULT_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.lessThan=" + DEFAULT_POSTS_DAY);

        // Get all the categoriesList where postsDay is less than UPDATED_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.lessThan=" + UPDATED_POSTS_DAY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByPostsDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where postsDay is greater than DEFAULT_POSTS_DAY
        defaultCategoriesShouldNotBeFound("postsDay.greaterThan=" + DEFAULT_POSTS_DAY);

        // Get all the categoriesList where postsDay is greater than SMALLER_POSTS_DAY
        defaultCategoriesShouldBeFound("postsDay.greaterThan=" + SMALLER_POSTS_DAY);
    }


    @Test
    @Transactional
    public void getAllCategoriesByAllowBadgesIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowBadges equals to DEFAULT_ALLOW_BADGES
        defaultCategoriesShouldBeFound("allowBadges.equals=" + DEFAULT_ALLOW_BADGES);

        // Get all the categoriesList where allowBadges equals to UPDATED_ALLOW_BADGES
        defaultCategoriesShouldNotBeFound("allowBadges.equals=" + UPDATED_ALLOW_BADGES);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowBadgesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowBadges not equals to DEFAULT_ALLOW_BADGES
        defaultCategoriesShouldNotBeFound("allowBadges.notEquals=" + DEFAULT_ALLOW_BADGES);

        // Get all the categoriesList where allowBadges not equals to UPDATED_ALLOW_BADGES
        defaultCategoriesShouldBeFound("allowBadges.notEquals=" + UPDATED_ALLOW_BADGES);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowBadgesIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowBadges in DEFAULT_ALLOW_BADGES or UPDATED_ALLOW_BADGES
        defaultCategoriesShouldBeFound("allowBadges.in=" + DEFAULT_ALLOW_BADGES + "," + UPDATED_ALLOW_BADGES);

        // Get all the categoriesList where allowBadges equals to UPDATED_ALLOW_BADGES
        defaultCategoriesShouldNotBeFound("allowBadges.in=" + UPDATED_ALLOW_BADGES);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowBadgesIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowBadges is not null
        defaultCategoriesShouldBeFound("allowBadges.specified=true");

        // Get all the categoriesList where allowBadges is null
        defaultCategoriesShouldNotBeFound("allowBadges.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameLowerIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameLower equals to DEFAULT_NAME_LOWER
        defaultCategoriesShouldBeFound("nameLower.equals=" + DEFAULT_NAME_LOWER);

        // Get all the categoriesList where nameLower equals to UPDATED_NAME_LOWER
        defaultCategoriesShouldNotBeFound("nameLower.equals=" + UPDATED_NAME_LOWER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameLowerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameLower not equals to DEFAULT_NAME_LOWER
        defaultCategoriesShouldNotBeFound("nameLower.notEquals=" + DEFAULT_NAME_LOWER);

        // Get all the categoriesList where nameLower not equals to UPDATED_NAME_LOWER
        defaultCategoriesShouldBeFound("nameLower.notEquals=" + UPDATED_NAME_LOWER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameLowerIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameLower in DEFAULT_NAME_LOWER or UPDATED_NAME_LOWER
        defaultCategoriesShouldBeFound("nameLower.in=" + DEFAULT_NAME_LOWER + "," + UPDATED_NAME_LOWER);

        // Get all the categoriesList where nameLower equals to UPDATED_NAME_LOWER
        defaultCategoriesShouldNotBeFound("nameLower.in=" + UPDATED_NAME_LOWER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameLowerIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameLower is not null
        defaultCategoriesShouldBeFound("nameLower.specified=true");

        // Get all the categoriesList where nameLower is null
        defaultCategoriesShouldNotBeFound("nameLower.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByNameLowerContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameLower contains DEFAULT_NAME_LOWER
        defaultCategoriesShouldBeFound("nameLower.contains=" + DEFAULT_NAME_LOWER);

        // Get all the categoriesList where nameLower contains UPDATED_NAME_LOWER
        defaultCategoriesShouldNotBeFound("nameLower.contains=" + UPDATED_NAME_LOWER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNameLowerNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameLower does not contain DEFAULT_NAME_LOWER
        defaultCategoriesShouldNotBeFound("nameLower.doesNotContain=" + DEFAULT_NAME_LOWER);

        // Get all the categoriesList where nameLower does not contain UPDATED_NAME_LOWER
        defaultCategoriesShouldBeFound("nameLower.doesNotContain=" + UPDATED_NAME_LOWER);
    }


    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseBasedOnLastPostIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseBasedOnLastPost equals to DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST
        defaultCategoriesShouldBeFound("autoCloseBasedOnLastPost.equals=" + DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST);

        // Get all the categoriesList where autoCloseBasedOnLastPost equals to UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST
        defaultCategoriesShouldNotBeFound("autoCloseBasedOnLastPost.equals=" + UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseBasedOnLastPostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseBasedOnLastPost not equals to DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST
        defaultCategoriesShouldNotBeFound("autoCloseBasedOnLastPost.notEquals=" + DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST);

        // Get all the categoriesList where autoCloseBasedOnLastPost not equals to UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST
        defaultCategoriesShouldBeFound("autoCloseBasedOnLastPost.notEquals=" + UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseBasedOnLastPostIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseBasedOnLastPost in DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST or UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST
        defaultCategoriesShouldBeFound("autoCloseBasedOnLastPost.in=" + DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST + "," + UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST);

        // Get all the categoriesList where autoCloseBasedOnLastPost equals to UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST
        defaultCategoriesShouldNotBeFound("autoCloseBasedOnLastPost.in=" + UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAutoCloseBasedOnLastPostIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where autoCloseBasedOnLastPost is not null
        defaultCategoriesShouldBeFound("autoCloseBasedOnLastPost.specified=true");

        // Get all the categoriesList where autoCloseBasedOnLastPost is null
        defaultCategoriesShouldNotBeFound("autoCloseBasedOnLastPost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicTemplate equals to DEFAULT_TOPIC_TEMPLATE
        defaultCategoriesShouldBeFound("topicTemplate.equals=" + DEFAULT_TOPIC_TEMPLATE);

        // Get all the categoriesList where topicTemplate equals to UPDATED_TOPIC_TEMPLATE
        defaultCategoriesShouldNotBeFound("topicTemplate.equals=" + UPDATED_TOPIC_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicTemplateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicTemplate not equals to DEFAULT_TOPIC_TEMPLATE
        defaultCategoriesShouldNotBeFound("topicTemplate.notEquals=" + DEFAULT_TOPIC_TEMPLATE);

        // Get all the categoriesList where topicTemplate not equals to UPDATED_TOPIC_TEMPLATE
        defaultCategoriesShouldBeFound("topicTemplate.notEquals=" + UPDATED_TOPIC_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicTemplate in DEFAULT_TOPIC_TEMPLATE or UPDATED_TOPIC_TEMPLATE
        defaultCategoriesShouldBeFound("topicTemplate.in=" + DEFAULT_TOPIC_TEMPLATE + "," + UPDATED_TOPIC_TEMPLATE);

        // Get all the categoriesList where topicTemplate equals to UPDATED_TOPIC_TEMPLATE
        defaultCategoriesShouldNotBeFound("topicTemplate.in=" + UPDATED_TOPIC_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicTemplate is not null
        defaultCategoriesShouldBeFound("topicTemplate.specified=true");

        // Get all the categoriesList where topicTemplate is null
        defaultCategoriesShouldNotBeFound("topicTemplate.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByTopicTemplateContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicTemplate contains DEFAULT_TOPIC_TEMPLATE
        defaultCategoriesShouldBeFound("topicTemplate.contains=" + DEFAULT_TOPIC_TEMPLATE);

        // Get all the categoriesList where topicTemplate contains UPDATED_TOPIC_TEMPLATE
        defaultCategoriesShouldNotBeFound("topicTemplate.contains=" + UPDATED_TOPIC_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicTemplateNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicTemplate does not contain DEFAULT_TOPIC_TEMPLATE
        defaultCategoriesShouldNotBeFound("topicTemplate.doesNotContain=" + DEFAULT_TOPIC_TEMPLATE);

        // Get all the categoriesList where topicTemplate does not contain UPDATED_TOPIC_TEMPLATE
        defaultCategoriesShouldBeFound("topicTemplate.doesNotContain=" + UPDATED_TOPIC_TEMPLATE);
    }


    @Test
    @Transactional
    public void getAllCategoriesByContainsMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where containsMessages equals to DEFAULT_CONTAINS_MESSAGES
        defaultCategoriesShouldBeFound("containsMessages.equals=" + DEFAULT_CONTAINS_MESSAGES);

        // Get all the categoriesList where containsMessages equals to UPDATED_CONTAINS_MESSAGES
        defaultCategoriesShouldNotBeFound("containsMessages.equals=" + UPDATED_CONTAINS_MESSAGES);
    }

    @Test
    @Transactional
    public void getAllCategoriesByContainsMessagesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where containsMessages not equals to DEFAULT_CONTAINS_MESSAGES
        defaultCategoriesShouldNotBeFound("containsMessages.notEquals=" + DEFAULT_CONTAINS_MESSAGES);

        // Get all the categoriesList where containsMessages not equals to UPDATED_CONTAINS_MESSAGES
        defaultCategoriesShouldBeFound("containsMessages.notEquals=" + UPDATED_CONTAINS_MESSAGES);
    }

    @Test
    @Transactional
    public void getAllCategoriesByContainsMessagesIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where containsMessages in DEFAULT_CONTAINS_MESSAGES or UPDATED_CONTAINS_MESSAGES
        defaultCategoriesShouldBeFound("containsMessages.in=" + DEFAULT_CONTAINS_MESSAGES + "," + UPDATED_CONTAINS_MESSAGES);

        // Get all the categoriesList where containsMessages equals to UPDATED_CONTAINS_MESSAGES
        defaultCategoriesShouldNotBeFound("containsMessages.in=" + UPDATED_CONTAINS_MESSAGES);
    }

    @Test
    @Transactional
    public void getAllCategoriesByContainsMessagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where containsMessages is not null
        defaultCategoriesShouldBeFound("containsMessages.specified=true");

        // Get all the categoriesList where containsMessages is null
        defaultCategoriesShouldNotBeFound("containsMessages.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultCategoriesShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the categoriesList where sortOrder equals to UPDATED_SORT_ORDER
        defaultCategoriesShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultCategoriesShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the categoriesList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultCategoriesShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultCategoriesShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the categoriesList where sortOrder equals to UPDATED_SORT_ORDER
        defaultCategoriesShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortOrder is not null
        defaultCategoriesShouldBeFound("sortOrder.specified=true");

        // Get all the categoriesList where sortOrder is null
        defaultCategoriesShouldNotBeFound("sortOrder.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesBySortOrderContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortOrder contains DEFAULT_SORT_ORDER
        defaultCategoriesShouldBeFound("sortOrder.contains=" + DEFAULT_SORT_ORDER);

        // Get all the categoriesList where sortOrder contains UPDATED_SORT_ORDER
        defaultCategoriesShouldNotBeFound("sortOrder.contains=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortOrderNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortOrder does not contain DEFAULT_SORT_ORDER
        defaultCategoriesShouldNotBeFound("sortOrder.doesNotContain=" + DEFAULT_SORT_ORDER);

        // Get all the categoriesList where sortOrder does not contain UPDATED_SORT_ORDER
        defaultCategoriesShouldBeFound("sortOrder.doesNotContain=" + UPDATED_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllCategoriesBySortAscendingIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortAscending equals to DEFAULT_SORT_ASCENDING
        defaultCategoriesShouldBeFound("sortAscending.equals=" + DEFAULT_SORT_ASCENDING);

        // Get all the categoriesList where sortAscending equals to UPDATED_SORT_ASCENDING
        defaultCategoriesShouldNotBeFound("sortAscending.equals=" + UPDATED_SORT_ASCENDING);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortAscendingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortAscending not equals to DEFAULT_SORT_ASCENDING
        defaultCategoriesShouldNotBeFound("sortAscending.notEquals=" + DEFAULT_SORT_ASCENDING);

        // Get all the categoriesList where sortAscending not equals to UPDATED_SORT_ASCENDING
        defaultCategoriesShouldBeFound("sortAscending.notEquals=" + UPDATED_SORT_ASCENDING);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortAscendingIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortAscending in DEFAULT_SORT_ASCENDING or UPDATED_SORT_ASCENDING
        defaultCategoriesShouldBeFound("sortAscending.in=" + DEFAULT_SORT_ASCENDING + "," + UPDATED_SORT_ASCENDING);

        // Get all the categoriesList where sortAscending equals to UPDATED_SORT_ASCENDING
        defaultCategoriesShouldNotBeFound("sortAscending.in=" + UPDATED_SORT_ASCENDING);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySortAscendingIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where sortAscending is not null
        defaultCategoriesShouldBeFound("sortAscending.specified=true");

        // Get all the categoriesList where sortAscending is null
        defaultCategoriesShouldNotBeFound("sortAscending.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId equals to DEFAULT_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.equals=" + DEFAULT_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId equals to UPDATED_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.equals=" + UPDATED_UPLOADED_LOGO_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId not equals to DEFAULT_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.notEquals=" + DEFAULT_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId not equals to UPDATED_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.notEquals=" + UPDATED_UPLOADED_LOGO_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId in DEFAULT_UPLOADED_LOGO_ID or UPDATED_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.in=" + DEFAULT_UPLOADED_LOGO_ID + "," + UPDATED_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId equals to UPDATED_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.in=" + UPDATED_UPLOADED_LOGO_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId is not null
        defaultCategoriesShouldBeFound("uploadedLogoId.specified=true");

        // Get all the categoriesList where uploadedLogoId is null
        defaultCategoriesShouldNotBeFound("uploadedLogoId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId is greater than or equal to DEFAULT_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.greaterThanOrEqual=" + DEFAULT_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId is greater than or equal to UPDATED_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.greaterThanOrEqual=" + UPDATED_UPLOADED_LOGO_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId is less than or equal to DEFAULT_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.lessThanOrEqual=" + DEFAULT_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId is less than or equal to SMALLER_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.lessThanOrEqual=" + SMALLER_UPLOADED_LOGO_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId is less than DEFAULT_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.lessThan=" + DEFAULT_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId is less than UPDATED_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.lessThan=" + UPDATED_UPLOADED_LOGO_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedLogoIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedLogoId is greater than DEFAULT_UPLOADED_LOGO_ID
        defaultCategoriesShouldNotBeFound("uploadedLogoId.greaterThan=" + DEFAULT_UPLOADED_LOGO_ID);

        // Get all the categoriesList where uploadedLogoId is greater than SMALLER_UPLOADED_LOGO_ID
        defaultCategoriesShouldBeFound("uploadedLogoId.greaterThan=" + SMALLER_UPLOADED_LOGO_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId equals to DEFAULT_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.equals=" + DEFAULT_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId equals to UPDATED_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.equals=" + UPDATED_UPLOADED_BACKGROUND_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId not equals to DEFAULT_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.notEquals=" + DEFAULT_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId not equals to UPDATED_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.notEquals=" + UPDATED_UPLOADED_BACKGROUND_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId in DEFAULT_UPLOADED_BACKGROUND_ID or UPDATED_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.in=" + DEFAULT_UPLOADED_BACKGROUND_ID + "," + UPDATED_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId equals to UPDATED_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.in=" + UPDATED_UPLOADED_BACKGROUND_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId is not null
        defaultCategoriesShouldBeFound("uploadedBackgroundId.specified=true");

        // Get all the categoriesList where uploadedBackgroundId is null
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId is greater than or equal to DEFAULT_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.greaterThanOrEqual=" + DEFAULT_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId is greater than or equal to UPDATED_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.greaterThanOrEqual=" + UPDATED_UPLOADED_BACKGROUND_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId is less than or equal to DEFAULT_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.lessThanOrEqual=" + DEFAULT_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId is less than or equal to SMALLER_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.lessThanOrEqual=" + SMALLER_UPLOADED_BACKGROUND_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId is less than DEFAULT_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.lessThan=" + DEFAULT_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId is less than UPDATED_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.lessThan=" + UPDATED_UPLOADED_BACKGROUND_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByUploadedBackgroundIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where uploadedBackgroundId is greater than DEFAULT_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldNotBeFound("uploadedBackgroundId.greaterThan=" + DEFAULT_UPLOADED_BACKGROUND_ID);

        // Get all the categoriesList where uploadedBackgroundId is greater than SMALLER_UPLOADED_BACKGROUND_ID
        defaultCategoriesShouldBeFound("uploadedBackgroundId.greaterThan=" + SMALLER_UPLOADED_BACKGROUND_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByTopicFeaturedLinkAllowedIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicFeaturedLinkAllowed equals to DEFAULT_TOPIC_FEATURED_LINK_ALLOWED
        defaultCategoriesShouldBeFound("topicFeaturedLinkAllowed.equals=" + DEFAULT_TOPIC_FEATURED_LINK_ALLOWED);

        // Get all the categoriesList where topicFeaturedLinkAllowed equals to UPDATED_TOPIC_FEATURED_LINK_ALLOWED
        defaultCategoriesShouldNotBeFound("topicFeaturedLinkAllowed.equals=" + UPDATED_TOPIC_FEATURED_LINK_ALLOWED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicFeaturedLinkAllowedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicFeaturedLinkAllowed not equals to DEFAULT_TOPIC_FEATURED_LINK_ALLOWED
        defaultCategoriesShouldNotBeFound("topicFeaturedLinkAllowed.notEquals=" + DEFAULT_TOPIC_FEATURED_LINK_ALLOWED);

        // Get all the categoriesList where topicFeaturedLinkAllowed not equals to UPDATED_TOPIC_FEATURED_LINK_ALLOWED
        defaultCategoriesShouldBeFound("topicFeaturedLinkAllowed.notEquals=" + UPDATED_TOPIC_FEATURED_LINK_ALLOWED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicFeaturedLinkAllowedIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicFeaturedLinkAllowed in DEFAULT_TOPIC_FEATURED_LINK_ALLOWED or UPDATED_TOPIC_FEATURED_LINK_ALLOWED
        defaultCategoriesShouldBeFound("topicFeaturedLinkAllowed.in=" + DEFAULT_TOPIC_FEATURED_LINK_ALLOWED + "," + UPDATED_TOPIC_FEATURED_LINK_ALLOWED);

        // Get all the categoriesList where topicFeaturedLinkAllowed equals to UPDATED_TOPIC_FEATURED_LINK_ALLOWED
        defaultCategoriesShouldNotBeFound("topicFeaturedLinkAllowed.in=" + UPDATED_TOPIC_FEATURED_LINK_ALLOWED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByTopicFeaturedLinkAllowedIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where topicFeaturedLinkAllowed is not null
        defaultCategoriesShouldBeFound("topicFeaturedLinkAllowed.specified=true");

        // Get all the categoriesList where topicFeaturedLinkAllowed is null
        defaultCategoriesShouldNotBeFound("topicFeaturedLinkAllowed.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllTopicsWikiIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allTopicsWiki equals to DEFAULT_ALL_TOPICS_WIKI
        defaultCategoriesShouldBeFound("allTopicsWiki.equals=" + DEFAULT_ALL_TOPICS_WIKI);

        // Get all the categoriesList where allTopicsWiki equals to UPDATED_ALL_TOPICS_WIKI
        defaultCategoriesShouldNotBeFound("allTopicsWiki.equals=" + UPDATED_ALL_TOPICS_WIKI);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllTopicsWikiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allTopicsWiki not equals to DEFAULT_ALL_TOPICS_WIKI
        defaultCategoriesShouldNotBeFound("allTopicsWiki.notEquals=" + DEFAULT_ALL_TOPICS_WIKI);

        // Get all the categoriesList where allTopicsWiki not equals to UPDATED_ALL_TOPICS_WIKI
        defaultCategoriesShouldBeFound("allTopicsWiki.notEquals=" + UPDATED_ALL_TOPICS_WIKI);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllTopicsWikiIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allTopicsWiki in DEFAULT_ALL_TOPICS_WIKI or UPDATED_ALL_TOPICS_WIKI
        defaultCategoriesShouldBeFound("allTopicsWiki.in=" + DEFAULT_ALL_TOPICS_WIKI + "," + UPDATED_ALL_TOPICS_WIKI);

        // Get all the categoriesList where allTopicsWiki equals to UPDATED_ALL_TOPICS_WIKI
        defaultCategoriesShouldNotBeFound("allTopicsWiki.in=" + UPDATED_ALL_TOPICS_WIKI);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllTopicsWikiIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allTopicsWiki is not null
        defaultCategoriesShouldBeFound("allTopicsWiki.specified=true");

        // Get all the categoriesList where allTopicsWiki is null
        defaultCategoriesShouldNotBeFound("allTopicsWiki.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByShowSubcategoryListIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where showSubcategoryList equals to DEFAULT_SHOW_SUBCATEGORY_LIST
        defaultCategoriesShouldBeFound("showSubcategoryList.equals=" + DEFAULT_SHOW_SUBCATEGORY_LIST);

        // Get all the categoriesList where showSubcategoryList equals to UPDATED_SHOW_SUBCATEGORY_LIST
        defaultCategoriesShouldNotBeFound("showSubcategoryList.equals=" + UPDATED_SHOW_SUBCATEGORY_LIST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByShowSubcategoryListIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where showSubcategoryList not equals to DEFAULT_SHOW_SUBCATEGORY_LIST
        defaultCategoriesShouldNotBeFound("showSubcategoryList.notEquals=" + DEFAULT_SHOW_SUBCATEGORY_LIST);

        // Get all the categoriesList where showSubcategoryList not equals to UPDATED_SHOW_SUBCATEGORY_LIST
        defaultCategoriesShouldBeFound("showSubcategoryList.notEquals=" + UPDATED_SHOW_SUBCATEGORY_LIST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByShowSubcategoryListIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where showSubcategoryList in DEFAULT_SHOW_SUBCATEGORY_LIST or UPDATED_SHOW_SUBCATEGORY_LIST
        defaultCategoriesShouldBeFound("showSubcategoryList.in=" + DEFAULT_SHOW_SUBCATEGORY_LIST + "," + UPDATED_SHOW_SUBCATEGORY_LIST);

        // Get all the categoriesList where showSubcategoryList equals to UPDATED_SHOW_SUBCATEGORY_LIST
        defaultCategoriesShouldNotBeFound("showSubcategoryList.in=" + UPDATED_SHOW_SUBCATEGORY_LIST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByShowSubcategoryListIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where showSubcategoryList is not null
        defaultCategoriesShouldBeFound("showSubcategoryList.specified=true");

        // Get all the categoriesList where showSubcategoryList is null
        defaultCategoriesShouldNotBeFound("showSubcategoryList.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics equals to DEFAULT_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.equals=" + DEFAULT_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics equals to UPDATED_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.equals=" + UPDATED_NUM_FEATURED_TOPICS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics not equals to DEFAULT_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.notEquals=" + DEFAULT_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics not equals to UPDATED_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.notEquals=" + UPDATED_NUM_FEATURED_TOPICS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics in DEFAULT_NUM_FEATURED_TOPICS or UPDATED_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.in=" + DEFAULT_NUM_FEATURED_TOPICS + "," + UPDATED_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics equals to UPDATED_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.in=" + UPDATED_NUM_FEATURED_TOPICS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics is not null
        defaultCategoriesShouldBeFound("numFeaturedTopics.specified=true");

        // Get all the categoriesList where numFeaturedTopics is null
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics is greater than or equal to DEFAULT_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.greaterThanOrEqual=" + DEFAULT_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics is greater than or equal to UPDATED_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.greaterThanOrEqual=" + UPDATED_NUM_FEATURED_TOPICS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics is less than or equal to DEFAULT_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.lessThanOrEqual=" + DEFAULT_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics is less than or equal to SMALLER_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.lessThanOrEqual=" + SMALLER_NUM_FEATURED_TOPICS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics is less than DEFAULT_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.lessThan=" + DEFAULT_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics is less than UPDATED_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.lessThan=" + UPDATED_NUM_FEATURED_TOPICS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNumFeaturedTopicsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where numFeaturedTopics is greater than DEFAULT_NUM_FEATURED_TOPICS
        defaultCategoriesShouldNotBeFound("numFeaturedTopics.greaterThan=" + DEFAULT_NUM_FEATURED_TOPICS);

        // Get all the categoriesList where numFeaturedTopics is greater than SMALLER_NUM_FEATURED_TOPICS
        defaultCategoriesShouldBeFound("numFeaturedTopics.greaterThan=" + SMALLER_NUM_FEATURED_TOPICS);
    }


    @Test
    @Transactional
    public void getAllCategoriesByDefaultViewIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultView equals to DEFAULT_DEFAULT_VIEW
        defaultCategoriesShouldBeFound("defaultView.equals=" + DEFAULT_DEFAULT_VIEW);

        // Get all the categoriesList where defaultView equals to UPDATED_DEFAULT_VIEW
        defaultCategoriesShouldNotBeFound("defaultView.equals=" + UPDATED_DEFAULT_VIEW);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultViewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultView not equals to DEFAULT_DEFAULT_VIEW
        defaultCategoriesShouldNotBeFound("defaultView.notEquals=" + DEFAULT_DEFAULT_VIEW);

        // Get all the categoriesList where defaultView not equals to UPDATED_DEFAULT_VIEW
        defaultCategoriesShouldBeFound("defaultView.notEquals=" + UPDATED_DEFAULT_VIEW);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultViewIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultView in DEFAULT_DEFAULT_VIEW or UPDATED_DEFAULT_VIEW
        defaultCategoriesShouldBeFound("defaultView.in=" + DEFAULT_DEFAULT_VIEW + "," + UPDATED_DEFAULT_VIEW);

        // Get all the categoriesList where defaultView equals to UPDATED_DEFAULT_VIEW
        defaultCategoriesShouldNotBeFound("defaultView.in=" + UPDATED_DEFAULT_VIEW);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultViewIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultView is not null
        defaultCategoriesShouldBeFound("defaultView.specified=true");

        // Get all the categoriesList where defaultView is null
        defaultCategoriesShouldNotBeFound("defaultView.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByDefaultViewContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultView contains DEFAULT_DEFAULT_VIEW
        defaultCategoriesShouldBeFound("defaultView.contains=" + DEFAULT_DEFAULT_VIEW);

        // Get all the categoriesList where defaultView contains UPDATED_DEFAULT_VIEW
        defaultCategoriesShouldNotBeFound("defaultView.contains=" + UPDATED_DEFAULT_VIEW);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultViewNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultView does not contain DEFAULT_DEFAULT_VIEW
        defaultCategoriesShouldNotBeFound("defaultView.doesNotContain=" + DEFAULT_DEFAULT_VIEW);

        // Get all the categoriesList where defaultView does not contain UPDATED_DEFAULT_VIEW
        defaultCategoriesShouldBeFound("defaultView.doesNotContain=" + UPDATED_DEFAULT_VIEW);
    }


    @Test
    @Transactional
    public void getAllCategoriesBySubcategoryListStyleIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where subcategoryListStyle equals to DEFAULT_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldBeFound("subcategoryListStyle.equals=" + DEFAULT_SUBCATEGORY_LIST_STYLE);

        // Get all the categoriesList where subcategoryListStyle equals to UPDATED_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldNotBeFound("subcategoryListStyle.equals=" + UPDATED_SUBCATEGORY_LIST_STYLE);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySubcategoryListStyleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where subcategoryListStyle not equals to DEFAULT_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldNotBeFound("subcategoryListStyle.notEquals=" + DEFAULT_SUBCATEGORY_LIST_STYLE);

        // Get all the categoriesList where subcategoryListStyle not equals to UPDATED_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldBeFound("subcategoryListStyle.notEquals=" + UPDATED_SUBCATEGORY_LIST_STYLE);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySubcategoryListStyleIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where subcategoryListStyle in DEFAULT_SUBCATEGORY_LIST_STYLE or UPDATED_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldBeFound("subcategoryListStyle.in=" + DEFAULT_SUBCATEGORY_LIST_STYLE + "," + UPDATED_SUBCATEGORY_LIST_STYLE);

        // Get all the categoriesList where subcategoryListStyle equals to UPDATED_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldNotBeFound("subcategoryListStyle.in=" + UPDATED_SUBCATEGORY_LIST_STYLE);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySubcategoryListStyleIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where subcategoryListStyle is not null
        defaultCategoriesShouldBeFound("subcategoryListStyle.specified=true");

        // Get all the categoriesList where subcategoryListStyle is null
        defaultCategoriesShouldNotBeFound("subcategoryListStyle.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesBySubcategoryListStyleContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where subcategoryListStyle contains DEFAULT_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldBeFound("subcategoryListStyle.contains=" + DEFAULT_SUBCATEGORY_LIST_STYLE);

        // Get all the categoriesList where subcategoryListStyle contains UPDATED_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldNotBeFound("subcategoryListStyle.contains=" + UPDATED_SUBCATEGORY_LIST_STYLE);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySubcategoryListStyleNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where subcategoryListStyle does not contain DEFAULT_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldNotBeFound("subcategoryListStyle.doesNotContain=" + DEFAULT_SUBCATEGORY_LIST_STYLE);

        // Get all the categoriesList where subcategoryListStyle does not contain UPDATED_SUBCATEGORY_LIST_STYLE
        defaultCategoriesShouldBeFound("subcategoryListStyle.doesNotContain=" + UPDATED_SUBCATEGORY_LIST_STYLE);
    }


    @Test
    @Transactional
    public void getAllCategoriesByDefaultTopPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultTopPeriod equals to DEFAULT_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldBeFound("defaultTopPeriod.equals=" + DEFAULT_DEFAULT_TOP_PERIOD);

        // Get all the categoriesList where defaultTopPeriod equals to UPDATED_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldNotBeFound("defaultTopPeriod.equals=" + UPDATED_DEFAULT_TOP_PERIOD);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultTopPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultTopPeriod not equals to DEFAULT_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldNotBeFound("defaultTopPeriod.notEquals=" + DEFAULT_DEFAULT_TOP_PERIOD);

        // Get all the categoriesList where defaultTopPeriod not equals to UPDATED_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldBeFound("defaultTopPeriod.notEquals=" + UPDATED_DEFAULT_TOP_PERIOD);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultTopPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultTopPeriod in DEFAULT_DEFAULT_TOP_PERIOD or UPDATED_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldBeFound("defaultTopPeriod.in=" + DEFAULT_DEFAULT_TOP_PERIOD + "," + UPDATED_DEFAULT_TOP_PERIOD);

        // Get all the categoriesList where defaultTopPeriod equals to UPDATED_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldNotBeFound("defaultTopPeriod.in=" + UPDATED_DEFAULT_TOP_PERIOD);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultTopPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultTopPeriod is not null
        defaultCategoriesShouldBeFound("defaultTopPeriod.specified=true");

        // Get all the categoriesList where defaultTopPeriod is null
        defaultCategoriesShouldNotBeFound("defaultTopPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByDefaultTopPeriodContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultTopPeriod contains DEFAULT_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldBeFound("defaultTopPeriod.contains=" + DEFAULT_DEFAULT_TOP_PERIOD);

        // Get all the categoriesList where defaultTopPeriod contains UPDATED_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldNotBeFound("defaultTopPeriod.contains=" + UPDATED_DEFAULT_TOP_PERIOD);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultTopPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultTopPeriod does not contain DEFAULT_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldNotBeFound("defaultTopPeriod.doesNotContain=" + DEFAULT_DEFAULT_TOP_PERIOD);

        // Get all the categoriesList where defaultTopPeriod does not contain UPDATED_DEFAULT_TOP_PERIOD
        defaultCategoriesShouldBeFound("defaultTopPeriod.doesNotContain=" + UPDATED_DEFAULT_TOP_PERIOD);
    }


    @Test
    @Transactional
    public void getAllCategoriesByMailinglistMirrorIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where mailinglistMirror equals to DEFAULT_MAILINGLIST_MIRROR
        defaultCategoriesShouldBeFound("mailinglistMirror.equals=" + DEFAULT_MAILINGLIST_MIRROR);

        // Get all the categoriesList where mailinglistMirror equals to UPDATED_MAILINGLIST_MIRROR
        defaultCategoriesShouldNotBeFound("mailinglistMirror.equals=" + UPDATED_MAILINGLIST_MIRROR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMailinglistMirrorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where mailinglistMirror not equals to DEFAULT_MAILINGLIST_MIRROR
        defaultCategoriesShouldNotBeFound("mailinglistMirror.notEquals=" + DEFAULT_MAILINGLIST_MIRROR);

        // Get all the categoriesList where mailinglistMirror not equals to UPDATED_MAILINGLIST_MIRROR
        defaultCategoriesShouldBeFound("mailinglistMirror.notEquals=" + UPDATED_MAILINGLIST_MIRROR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMailinglistMirrorIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where mailinglistMirror in DEFAULT_MAILINGLIST_MIRROR or UPDATED_MAILINGLIST_MIRROR
        defaultCategoriesShouldBeFound("mailinglistMirror.in=" + DEFAULT_MAILINGLIST_MIRROR + "," + UPDATED_MAILINGLIST_MIRROR);

        // Get all the categoriesList where mailinglistMirror equals to UPDATED_MAILINGLIST_MIRROR
        defaultCategoriesShouldNotBeFound("mailinglistMirror.in=" + UPDATED_MAILINGLIST_MIRROR);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMailinglistMirrorIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where mailinglistMirror is not null
        defaultCategoriesShouldBeFound("mailinglistMirror.specified=true");

        // Get all the categoriesList where mailinglistMirror is null
        defaultCategoriesShouldNotBeFound("mailinglistMirror.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags equals to DEFAULT_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.equals=" + DEFAULT_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags equals to UPDATED_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.equals=" + UPDATED_MINIMUM_REQUIRED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags not equals to DEFAULT_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.notEquals=" + DEFAULT_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags not equals to UPDATED_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.notEquals=" + UPDATED_MINIMUM_REQUIRED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags in DEFAULT_MINIMUM_REQUIRED_TAGS or UPDATED_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.in=" + DEFAULT_MINIMUM_REQUIRED_TAGS + "," + UPDATED_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags equals to UPDATED_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.in=" + UPDATED_MINIMUM_REQUIRED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags is not null
        defaultCategoriesShouldBeFound("minimumRequiredTags.specified=true");

        // Get all the categoriesList where minimumRequiredTags is null
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags is greater than or equal to DEFAULT_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.greaterThanOrEqual=" + DEFAULT_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags is greater than or equal to UPDATED_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.greaterThanOrEqual=" + UPDATED_MINIMUM_REQUIRED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags is less than or equal to DEFAULT_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.lessThanOrEqual=" + DEFAULT_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags is less than or equal to SMALLER_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.lessThanOrEqual=" + SMALLER_MINIMUM_REQUIRED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags is less than DEFAULT_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.lessThan=" + DEFAULT_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags is less than UPDATED_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.lessThan=" + UPDATED_MINIMUM_REQUIRED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinimumRequiredTagsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minimumRequiredTags is greater than DEFAULT_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldNotBeFound("minimumRequiredTags.greaterThan=" + DEFAULT_MINIMUM_REQUIRED_TAGS);

        // Get all the categoriesList where minimumRequiredTags is greater than SMALLER_MINIMUM_REQUIRED_TAGS
        defaultCategoriesShouldBeFound("minimumRequiredTags.greaterThan=" + SMALLER_MINIMUM_REQUIRED_TAGS);
    }


    @Test
    @Transactional
    public void getAllCategoriesByNavigateToFirstPostAfterReadIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where navigateToFirstPostAfterRead equals to DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ
        defaultCategoriesShouldBeFound("navigateToFirstPostAfterRead.equals=" + DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ);

        // Get all the categoriesList where navigateToFirstPostAfterRead equals to UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ
        defaultCategoriesShouldNotBeFound("navigateToFirstPostAfterRead.equals=" + UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNavigateToFirstPostAfterReadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where navigateToFirstPostAfterRead not equals to DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ
        defaultCategoriesShouldNotBeFound("navigateToFirstPostAfterRead.notEquals=" + DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ);

        // Get all the categoriesList where navigateToFirstPostAfterRead not equals to UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ
        defaultCategoriesShouldBeFound("navigateToFirstPostAfterRead.notEquals=" + UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNavigateToFirstPostAfterReadIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where navigateToFirstPostAfterRead in DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ or UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ
        defaultCategoriesShouldBeFound("navigateToFirstPostAfterRead.in=" + DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ + "," + UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ);

        // Get all the categoriesList where navigateToFirstPostAfterRead equals to UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ
        defaultCategoriesShouldNotBeFound("navigateToFirstPostAfterRead.in=" + UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ);
    }

    @Test
    @Transactional
    public void getAllCategoriesByNavigateToFirstPostAfterReadIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where navigateToFirstPostAfterRead is not null
        defaultCategoriesShouldBeFound("navigateToFirstPostAfterRead.specified=true");

        // Get all the categoriesList where navigateToFirstPostAfterRead is null
        defaultCategoriesShouldNotBeFound("navigateToFirstPostAfterRead.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority equals to DEFAULT_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.equals=" + DEFAULT_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority equals to UPDATED_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.equals=" + UPDATED_SEARCH_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority not equals to DEFAULT_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.notEquals=" + DEFAULT_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority not equals to UPDATED_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.notEquals=" + UPDATED_SEARCH_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority in DEFAULT_SEARCH_PRIORITY or UPDATED_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.in=" + DEFAULT_SEARCH_PRIORITY + "," + UPDATED_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority equals to UPDATED_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.in=" + UPDATED_SEARCH_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority is not null
        defaultCategoriesShouldBeFound("searchPriority.specified=true");

        // Get all the categoriesList where searchPriority is null
        defaultCategoriesShouldNotBeFound("searchPriority.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority is greater than or equal to DEFAULT_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.greaterThanOrEqual=" + DEFAULT_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority is greater than or equal to UPDATED_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.greaterThanOrEqual=" + UPDATED_SEARCH_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority is less than or equal to DEFAULT_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.lessThanOrEqual=" + DEFAULT_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority is less than or equal to SMALLER_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.lessThanOrEqual=" + SMALLER_SEARCH_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority is less than DEFAULT_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.lessThan=" + DEFAULT_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority is less than UPDATED_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.lessThan=" + UPDATED_SEARCH_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllCategoriesBySearchPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where searchPriority is greater than DEFAULT_SEARCH_PRIORITY
        defaultCategoriesShouldNotBeFound("searchPriority.greaterThan=" + DEFAULT_SEARCH_PRIORITY);

        // Get all the categoriesList where searchPriority is greater than SMALLER_SEARCH_PRIORITY
        defaultCategoriesShouldBeFound("searchPriority.greaterThan=" + SMALLER_SEARCH_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllCategoriesByAllowGlobalTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowGlobalTags equals to DEFAULT_ALLOW_GLOBAL_TAGS
        defaultCategoriesShouldBeFound("allowGlobalTags.equals=" + DEFAULT_ALLOW_GLOBAL_TAGS);

        // Get all the categoriesList where allowGlobalTags equals to UPDATED_ALLOW_GLOBAL_TAGS
        defaultCategoriesShouldNotBeFound("allowGlobalTags.equals=" + UPDATED_ALLOW_GLOBAL_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowGlobalTagsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowGlobalTags not equals to DEFAULT_ALLOW_GLOBAL_TAGS
        defaultCategoriesShouldNotBeFound("allowGlobalTags.notEquals=" + DEFAULT_ALLOW_GLOBAL_TAGS);

        // Get all the categoriesList where allowGlobalTags not equals to UPDATED_ALLOW_GLOBAL_TAGS
        defaultCategoriesShouldBeFound("allowGlobalTags.notEquals=" + UPDATED_ALLOW_GLOBAL_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowGlobalTagsIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowGlobalTags in DEFAULT_ALLOW_GLOBAL_TAGS or UPDATED_ALLOW_GLOBAL_TAGS
        defaultCategoriesShouldBeFound("allowGlobalTags.in=" + DEFAULT_ALLOW_GLOBAL_TAGS + "," + UPDATED_ALLOW_GLOBAL_TAGS);

        // Get all the categoriesList where allowGlobalTags equals to UPDATED_ALLOW_GLOBAL_TAGS
        defaultCategoriesShouldNotBeFound("allowGlobalTags.in=" + UPDATED_ALLOW_GLOBAL_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowGlobalTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowGlobalTags is not null
        defaultCategoriesShouldBeFound("allowGlobalTags.specified=true");

        // Get all the categoriesList where allowGlobalTags is null
        defaultCategoriesShouldNotBeFound("allowGlobalTags.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId equals to DEFAULT_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.equals=" + DEFAULT_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId equals to UPDATED_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.equals=" + UPDATED_REVIEWABLE_BY_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId not equals to DEFAULT_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.notEquals=" + DEFAULT_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId not equals to UPDATED_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.notEquals=" + UPDATED_REVIEWABLE_BY_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId in DEFAULT_REVIEWABLE_BY_GROUP_ID or UPDATED_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.in=" + DEFAULT_REVIEWABLE_BY_GROUP_ID + "," + UPDATED_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId equals to UPDATED_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.in=" + UPDATED_REVIEWABLE_BY_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId is not null
        defaultCategoriesShouldBeFound("reviewableByGroupId.specified=true");

        // Get all the categoriesList where reviewableByGroupId is null
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId is greater than or equal to DEFAULT_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.greaterThanOrEqual=" + DEFAULT_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId is greater than or equal to UPDATED_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.greaterThanOrEqual=" + UPDATED_REVIEWABLE_BY_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId is less than or equal to DEFAULT_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.lessThanOrEqual=" + DEFAULT_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId is less than or equal to SMALLER_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.lessThanOrEqual=" + SMALLER_REVIEWABLE_BY_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId is less than DEFAULT_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.lessThan=" + DEFAULT_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId is less than UPDATED_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.lessThan=" + UPDATED_REVIEWABLE_BY_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReviewableByGroupIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where reviewableByGroupId is greater than DEFAULT_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldNotBeFound("reviewableByGroupId.greaterThan=" + DEFAULT_REVIEWABLE_BY_GROUP_ID);

        // Get all the categoriesList where reviewableByGroupId is greater than SMALLER_REVIEWABLE_BY_GROUP_ID
        defaultCategoriesShouldBeFound("reviewableByGroupId.greaterThan=" + SMALLER_REVIEWABLE_BY_GROUP_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId equals to DEFAULT_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.equals=" + DEFAULT_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId equals to UPDATED_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.equals=" + UPDATED_REQUIRED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId not equals to DEFAULT_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.notEquals=" + DEFAULT_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId not equals to UPDATED_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.notEquals=" + UPDATED_REQUIRED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId in DEFAULT_REQUIRED_TAG_GROUP_ID or UPDATED_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.in=" + DEFAULT_REQUIRED_TAG_GROUP_ID + "," + UPDATED_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId equals to UPDATED_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.in=" + UPDATED_REQUIRED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId is not null
        defaultCategoriesShouldBeFound("requiredTagGroupId.specified=true");

        // Get all the categoriesList where requiredTagGroupId is null
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId is greater than or equal to DEFAULT_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.greaterThanOrEqual=" + DEFAULT_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId is greater than or equal to UPDATED_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.greaterThanOrEqual=" + UPDATED_REQUIRED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId is less than or equal to DEFAULT_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.lessThanOrEqual=" + DEFAULT_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId is less than or equal to SMALLER_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.lessThanOrEqual=" + SMALLER_REQUIRED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId is less than DEFAULT_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.lessThan=" + DEFAULT_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId is less than UPDATED_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.lessThan=" + UPDATED_REQUIRED_TAG_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByRequiredTagGroupIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where requiredTagGroupId is greater than DEFAULT_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldNotBeFound("requiredTagGroupId.greaterThan=" + DEFAULT_REQUIRED_TAG_GROUP_ID);

        // Get all the categoriesList where requiredTagGroupId is greater than SMALLER_REQUIRED_TAG_GROUP_ID
        defaultCategoriesShouldBeFound("requiredTagGroupId.greaterThan=" + SMALLER_REQUIRED_TAG_GROUP_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup equals to DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.equals=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup equals to UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.equals=" + UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup not equals to DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.notEquals=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup not equals to UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.notEquals=" + UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup in DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP or UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.in=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP + "," + UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup equals to UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.in=" + UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup is not null
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.specified=true");

        // Get all the categoriesList where minTagsFromRequiredGroup is null
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup is greater than or equal to DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.greaterThanOrEqual=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup is greater than or equal to UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.greaterThanOrEqual=" + UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup is less than or equal to DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.lessThanOrEqual=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup is less than or equal to SMALLER_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.lessThanOrEqual=" + SMALLER_MIN_TAGS_FROM_REQUIRED_GROUP);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup is less than DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.lessThan=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup is less than UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.lessThan=" + UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMinTagsFromRequiredGroupIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where minTagsFromRequiredGroup is greater than DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldNotBeFound("minTagsFromRequiredGroup.greaterThan=" + DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP);

        // Get all the categoriesList where minTagsFromRequiredGroup is greater than SMALLER_MIN_TAGS_FROM_REQUIRED_GROUP
        defaultCategoriesShouldBeFound("minTagsFromRequiredGroup.greaterThan=" + SMALLER_MIN_TAGS_FROM_REQUIRED_GROUP);
    }


    @Test
    @Transactional
    public void getAllCategoriesByReadOnlyBannerIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readOnlyBanner equals to DEFAULT_READ_ONLY_BANNER
        defaultCategoriesShouldBeFound("readOnlyBanner.equals=" + DEFAULT_READ_ONLY_BANNER);

        // Get all the categoriesList where readOnlyBanner equals to UPDATED_READ_ONLY_BANNER
        defaultCategoriesShouldNotBeFound("readOnlyBanner.equals=" + UPDATED_READ_ONLY_BANNER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadOnlyBannerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readOnlyBanner not equals to DEFAULT_READ_ONLY_BANNER
        defaultCategoriesShouldNotBeFound("readOnlyBanner.notEquals=" + DEFAULT_READ_ONLY_BANNER);

        // Get all the categoriesList where readOnlyBanner not equals to UPDATED_READ_ONLY_BANNER
        defaultCategoriesShouldBeFound("readOnlyBanner.notEquals=" + UPDATED_READ_ONLY_BANNER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadOnlyBannerIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readOnlyBanner in DEFAULT_READ_ONLY_BANNER or UPDATED_READ_ONLY_BANNER
        defaultCategoriesShouldBeFound("readOnlyBanner.in=" + DEFAULT_READ_ONLY_BANNER + "," + UPDATED_READ_ONLY_BANNER);

        // Get all the categoriesList where readOnlyBanner equals to UPDATED_READ_ONLY_BANNER
        defaultCategoriesShouldNotBeFound("readOnlyBanner.in=" + UPDATED_READ_ONLY_BANNER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadOnlyBannerIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readOnlyBanner is not null
        defaultCategoriesShouldBeFound("readOnlyBanner.specified=true");

        // Get all the categoriesList where readOnlyBanner is null
        defaultCategoriesShouldNotBeFound("readOnlyBanner.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByReadOnlyBannerContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readOnlyBanner contains DEFAULT_READ_ONLY_BANNER
        defaultCategoriesShouldBeFound("readOnlyBanner.contains=" + DEFAULT_READ_ONLY_BANNER);

        // Get all the categoriesList where readOnlyBanner contains UPDATED_READ_ONLY_BANNER
        defaultCategoriesShouldNotBeFound("readOnlyBanner.contains=" + UPDATED_READ_ONLY_BANNER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByReadOnlyBannerNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where readOnlyBanner does not contain DEFAULT_READ_ONLY_BANNER
        defaultCategoriesShouldNotBeFound("readOnlyBanner.doesNotContain=" + DEFAULT_READ_ONLY_BANNER);

        // Get all the categoriesList where readOnlyBanner does not contain UPDATED_READ_ONLY_BANNER
        defaultCategoriesShouldBeFound("readOnlyBanner.doesNotContain=" + UPDATED_READ_ONLY_BANNER);
    }


    @Test
    @Transactional
    public void getAllCategoriesByDefaultListFilterIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultListFilter equals to DEFAULT_DEFAULT_LIST_FILTER
        defaultCategoriesShouldBeFound("defaultListFilter.equals=" + DEFAULT_DEFAULT_LIST_FILTER);

        // Get all the categoriesList where defaultListFilter equals to UPDATED_DEFAULT_LIST_FILTER
        defaultCategoriesShouldNotBeFound("defaultListFilter.equals=" + UPDATED_DEFAULT_LIST_FILTER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultListFilterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultListFilter not equals to DEFAULT_DEFAULT_LIST_FILTER
        defaultCategoriesShouldNotBeFound("defaultListFilter.notEquals=" + DEFAULT_DEFAULT_LIST_FILTER);

        // Get all the categoriesList where defaultListFilter not equals to UPDATED_DEFAULT_LIST_FILTER
        defaultCategoriesShouldBeFound("defaultListFilter.notEquals=" + UPDATED_DEFAULT_LIST_FILTER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultListFilterIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultListFilter in DEFAULT_DEFAULT_LIST_FILTER or UPDATED_DEFAULT_LIST_FILTER
        defaultCategoriesShouldBeFound("defaultListFilter.in=" + DEFAULT_DEFAULT_LIST_FILTER + "," + UPDATED_DEFAULT_LIST_FILTER);

        // Get all the categoriesList where defaultListFilter equals to UPDATED_DEFAULT_LIST_FILTER
        defaultCategoriesShouldNotBeFound("defaultListFilter.in=" + UPDATED_DEFAULT_LIST_FILTER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultListFilterIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultListFilter is not null
        defaultCategoriesShouldBeFound("defaultListFilter.specified=true");

        // Get all the categoriesList where defaultListFilter is null
        defaultCategoriesShouldNotBeFound("defaultListFilter.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByDefaultListFilterContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultListFilter contains DEFAULT_DEFAULT_LIST_FILTER
        defaultCategoriesShouldBeFound("defaultListFilter.contains=" + DEFAULT_DEFAULT_LIST_FILTER);

        // Get all the categoriesList where defaultListFilter contains UPDATED_DEFAULT_LIST_FILTER
        defaultCategoriesShouldNotBeFound("defaultListFilter.contains=" + UPDATED_DEFAULT_LIST_FILTER);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDefaultListFilterNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where defaultListFilter does not contain DEFAULT_DEFAULT_LIST_FILTER
        defaultCategoriesShouldNotBeFound("defaultListFilter.doesNotContain=" + DEFAULT_DEFAULT_LIST_FILTER);

        // Get all the categoriesList where defaultListFilter does not contain UPDATED_DEFAULT_LIST_FILTER
        defaultCategoriesShouldBeFound("defaultListFilter.doesNotContain=" + UPDATED_DEFAULT_LIST_FILTER);
    }


    @Test
    @Transactional
    public void getAllCategoriesByAllowUnlimitedOwnerEditsOnFirstPostIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost equals to DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST
        defaultCategoriesShouldBeFound("allowUnlimitedOwnerEditsOnFirstPost.equals=" + DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost equals to UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST
        defaultCategoriesShouldNotBeFound("allowUnlimitedOwnerEditsOnFirstPost.equals=" + UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowUnlimitedOwnerEditsOnFirstPostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost not equals to DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST
        defaultCategoriesShouldNotBeFound("allowUnlimitedOwnerEditsOnFirstPost.notEquals=" + DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost not equals to UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST
        defaultCategoriesShouldBeFound("allowUnlimitedOwnerEditsOnFirstPost.notEquals=" + UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowUnlimitedOwnerEditsOnFirstPostIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost in DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST or UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST
        defaultCategoriesShouldBeFound("allowUnlimitedOwnerEditsOnFirstPost.in=" + DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST + "," + UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost equals to UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST
        defaultCategoriesShouldNotBeFound("allowUnlimitedOwnerEditsOnFirstPost.in=" + UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
    }

    @Test
    @Transactional
    public void getAllCategoriesByAllowUnlimitedOwnerEditsOnFirstPostIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost is not null
        defaultCategoriesShouldBeFound("allowUnlimitedOwnerEditsOnFirstPost.specified=true");

        // Get all the categoriesList where allowUnlimitedOwnerEditsOnFirstPost is null
        defaultCategoriesShouldNotBeFound("allowUnlimitedOwnerEditsOnFirstPost.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriesShouldBeFound(String filter) throws Exception {
        restCategoriesMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicCount").value(hasItem(DEFAULT_TOPIC_COUNT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].topicsYear").value(hasItem(DEFAULT_TOPICS_YEAR)))
            .andExpect(jsonPath("$.[*].topicsMonth").value(hasItem(DEFAULT_TOPICS_MONTH)))
            .andExpect(jsonPath("$.[*].topicsWeek").value(hasItem(DEFAULT_TOPICS_WEEK)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].textColor").value(hasItem(DEFAULT_TEXT_COLOR)))
            .andExpect(jsonPath("$.[*].readRestricted").value(hasItem(DEFAULT_READ_RESTRICTED.booleanValue())))
            .andExpect(jsonPath("$.[*].autoCloseHours").value(hasItem(DEFAULT_AUTO_CLOSE_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].postCount").value(hasItem(DEFAULT_POST_COUNT)))
            .andExpect(jsonPath("$.[*].latestPostId").value(hasItem(DEFAULT_LATEST_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].latestTopicId").value(hasItem(DEFAULT_LATEST_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].postsYear").value(hasItem(DEFAULT_POSTS_YEAR)))
            .andExpect(jsonPath("$.[*].postsMonth").value(hasItem(DEFAULT_POSTS_MONTH)))
            .andExpect(jsonPath("$.[*].postsWeek").value(hasItem(DEFAULT_POSTS_WEEK)))
            .andExpect(jsonPath("$.[*].emailIn").value(hasItem(DEFAULT_EMAIL_IN)))
            .andExpect(jsonPath("$.[*].emailInAllowStrangers").value(hasItem(DEFAULT_EMAIL_IN_ALLOW_STRANGERS.booleanValue())))
            .andExpect(jsonPath("$.[*].topicsDay").value(hasItem(DEFAULT_TOPICS_DAY)))
            .andExpect(jsonPath("$.[*].postsDay").value(hasItem(DEFAULT_POSTS_DAY)))
            .andExpect(jsonPath("$.[*].allowBadges").value(hasItem(DEFAULT_ALLOW_BADGES.booleanValue())))
            .andExpect(jsonPath("$.[*].nameLower").value(hasItem(DEFAULT_NAME_LOWER)))
            .andExpect(jsonPath("$.[*].autoCloseBasedOnLastPost").value(hasItem(DEFAULT_AUTO_CLOSE_BASED_ON_LAST_POST.booleanValue())))
            .andExpect(jsonPath("$.[*].topicTemplate").value(hasItem(DEFAULT_TOPIC_TEMPLATE)))
            .andExpect(jsonPath("$.[*].containsMessages").value(hasItem(DEFAULT_CONTAINS_MESSAGES.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].sortAscending").value(hasItem(DEFAULT_SORT_ASCENDING.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadedLogoId").value(hasItem(DEFAULT_UPLOADED_LOGO_ID.intValue())))
            .andExpect(jsonPath("$.[*].uploadedBackgroundId").value(hasItem(DEFAULT_UPLOADED_BACKGROUND_ID.intValue())))
            .andExpect(jsonPath("$.[*].topicFeaturedLinkAllowed").value(hasItem(DEFAULT_TOPIC_FEATURED_LINK_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].allTopicsWiki").value(hasItem(DEFAULT_ALL_TOPICS_WIKI.booleanValue())))
            .andExpect(jsonPath("$.[*].showSubcategoryList").value(hasItem(DEFAULT_SHOW_SUBCATEGORY_LIST.booleanValue())))
            .andExpect(jsonPath("$.[*].numFeaturedTopics").value(hasItem(DEFAULT_NUM_FEATURED_TOPICS)))
            .andExpect(jsonPath("$.[*].defaultView").value(hasItem(DEFAULT_DEFAULT_VIEW)))
            .andExpect(jsonPath("$.[*].subcategoryListStyle").value(hasItem(DEFAULT_SUBCATEGORY_LIST_STYLE)))
            .andExpect(jsonPath("$.[*].defaultTopPeriod").value(hasItem(DEFAULT_DEFAULT_TOP_PERIOD)))
            .andExpect(jsonPath("$.[*].mailinglistMirror").value(hasItem(DEFAULT_MAILINGLIST_MIRROR.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumRequiredTags").value(hasItem(DEFAULT_MINIMUM_REQUIRED_TAGS)))
            .andExpect(jsonPath("$.[*].navigateToFirstPostAfterRead").value(hasItem(DEFAULT_NAVIGATE_TO_FIRST_POST_AFTER_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].searchPriority").value(hasItem(DEFAULT_SEARCH_PRIORITY)))
            .andExpect(jsonPath("$.[*].allowGlobalTags").value(hasItem(DEFAULT_ALLOW_GLOBAL_TAGS.booleanValue())))
            .andExpect(jsonPath("$.[*].reviewableByGroupId").value(hasItem(DEFAULT_REVIEWABLE_BY_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].requiredTagGroupId").value(hasItem(DEFAULT_REQUIRED_TAG_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].minTagsFromRequiredGroup").value(hasItem(DEFAULT_MIN_TAGS_FROM_REQUIRED_GROUP)))
            .andExpect(jsonPath("$.[*].readOnlyBanner").value(hasItem(DEFAULT_READ_ONLY_BANNER)))
            .andExpect(jsonPath("$.[*].defaultListFilter").value(hasItem(DEFAULT_DEFAULT_LIST_FILTER)))
            .andExpect(jsonPath("$.[*].allowUnlimitedOwnerEditsOnFirstPost").value(hasItem(DEFAULT_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST.booleanValue())));

        // Check, that the count call also returns 1
        restCategoriesMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriesShouldNotBeFound(String filter) throws Exception {
        restCategoriesMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriesMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCategories() throws Exception {
        // Get the categories
        restCategoriesMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories
        Categories updatedCategories = categoriesRepository.findById(categories.getId()).get();
        // Disconnect from session so that the updates on updatedCategories are not directly saved in db
        em.detach(updatedCategories);
        updatedCategories
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .topicId(UPDATED_TOPIC_ID)
            .topicCount(UPDATED_TOPIC_COUNT)
            .userId(UPDATED_USER_ID)
            .topicsYear(UPDATED_TOPICS_YEAR)
            .topicsMonth(UPDATED_TOPICS_MONTH)
            .topicsWeek(UPDATED_TOPICS_WEEK)
            .slug(UPDATED_SLUG)
            .description(UPDATED_DESCRIPTION)
            .textColor(UPDATED_TEXT_COLOR)
            .readRestricted(UPDATED_READ_RESTRICTED)
            .autoCloseHours(UPDATED_AUTO_CLOSE_HOURS)
            .postCount(UPDATED_POST_COUNT)
            .latestPostId(UPDATED_LATEST_POST_ID)
            .latestTopicId(UPDATED_LATEST_TOPIC_ID)
            .position(UPDATED_POSITION)
            .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
            .postsYear(UPDATED_POSTS_YEAR)
            .postsMonth(UPDATED_POSTS_MONTH)
            .postsWeek(UPDATED_POSTS_WEEK)
            .emailIn(UPDATED_EMAIL_IN)
            .emailInAllowStrangers(UPDATED_EMAIL_IN_ALLOW_STRANGERS)
            .topicsDay(UPDATED_TOPICS_DAY)
            .postsDay(UPDATED_POSTS_DAY)
            .allowBadges(UPDATED_ALLOW_BADGES)
            .nameLower(UPDATED_NAME_LOWER)
            .autoCloseBasedOnLastPost(UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST)
            .topicTemplate(UPDATED_TOPIC_TEMPLATE)
            .containsMessages(UPDATED_CONTAINS_MESSAGES)
            .sortOrder(UPDATED_SORT_ORDER)
            .sortAscending(UPDATED_SORT_ASCENDING)
            .uploadedLogoId(UPDATED_UPLOADED_LOGO_ID)
            .uploadedBackgroundId(UPDATED_UPLOADED_BACKGROUND_ID)
            .topicFeaturedLinkAllowed(UPDATED_TOPIC_FEATURED_LINK_ALLOWED)
            .allTopicsWiki(UPDATED_ALL_TOPICS_WIKI)
            .showSubcategoryList(UPDATED_SHOW_SUBCATEGORY_LIST)
            .numFeaturedTopics(UPDATED_NUM_FEATURED_TOPICS)
            .defaultView(UPDATED_DEFAULT_VIEW)
            .subcategoryListStyle(UPDATED_SUBCATEGORY_LIST_STYLE)
            .defaultTopPeriod(UPDATED_DEFAULT_TOP_PERIOD)
            .mailinglistMirror(UPDATED_MAILINGLIST_MIRROR)
            .minimumRequiredTags(UPDATED_MINIMUM_REQUIRED_TAGS)
            .navigateToFirstPostAfterRead(UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ)
            .searchPriority(UPDATED_SEARCH_PRIORITY)
            .allowGlobalTags(UPDATED_ALLOW_GLOBAL_TAGS)
            .reviewableByGroupId(UPDATED_REVIEWABLE_BY_GROUP_ID)
            .requiredTagGroupId(UPDATED_REQUIRED_TAG_GROUP_ID)
            .minTagsFromRequiredGroup(UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP)
            .readOnlyBanner(UPDATED_READ_ONLY_BANNER)
            .defaultListFilter(UPDATED_DEFAULT_LIST_FILTER)
            .allowUnlimitedOwnerEditsOnFirstPost(UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(updatedCategories);

        restCategoriesMockMvc.perform(put("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategories.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCategories.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testCategories.getTopicCount()).isEqualTo(UPDATED_TOPIC_COUNT);
        assertThat(testCategories.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCategories.getTopicsYear()).isEqualTo(UPDATED_TOPICS_YEAR);
        assertThat(testCategories.getTopicsMonth()).isEqualTo(UPDATED_TOPICS_MONTH);
        assertThat(testCategories.getTopicsWeek()).isEqualTo(UPDATED_TOPICS_WEEK);
        assertThat(testCategories.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategories.getTextColor()).isEqualTo(UPDATED_TEXT_COLOR);
        assertThat(testCategories.isReadRestricted()).isEqualTo(UPDATED_READ_RESTRICTED);
        assertThat(testCategories.getAutoCloseHours()).isEqualTo(UPDATED_AUTO_CLOSE_HOURS);
        assertThat(testCategories.getPostCount()).isEqualTo(UPDATED_POST_COUNT);
        assertThat(testCategories.getLatestPostId()).isEqualTo(UPDATED_LATEST_POST_ID);
        assertThat(testCategories.getLatestTopicId()).isEqualTo(UPDATED_LATEST_TOPIC_ID);
        assertThat(testCategories.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCategories.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testCategories.getPostsYear()).isEqualTo(UPDATED_POSTS_YEAR);
        assertThat(testCategories.getPostsMonth()).isEqualTo(UPDATED_POSTS_MONTH);
        assertThat(testCategories.getPostsWeek()).isEqualTo(UPDATED_POSTS_WEEK);
        assertThat(testCategories.getEmailIn()).isEqualTo(UPDATED_EMAIL_IN);
        assertThat(testCategories.isEmailInAllowStrangers()).isEqualTo(UPDATED_EMAIL_IN_ALLOW_STRANGERS);
        assertThat(testCategories.getTopicsDay()).isEqualTo(UPDATED_TOPICS_DAY);
        assertThat(testCategories.getPostsDay()).isEqualTo(UPDATED_POSTS_DAY);
        assertThat(testCategories.isAllowBadges()).isEqualTo(UPDATED_ALLOW_BADGES);
        assertThat(testCategories.getNameLower()).isEqualTo(UPDATED_NAME_LOWER);
        assertThat(testCategories.isAutoCloseBasedOnLastPost()).isEqualTo(UPDATED_AUTO_CLOSE_BASED_ON_LAST_POST);
        assertThat(testCategories.getTopicTemplate()).isEqualTo(UPDATED_TOPIC_TEMPLATE);
        assertThat(testCategories.isContainsMessages()).isEqualTo(UPDATED_CONTAINS_MESSAGES);
        assertThat(testCategories.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testCategories.isSortAscending()).isEqualTo(UPDATED_SORT_ASCENDING);
        assertThat(testCategories.getUploadedLogoId()).isEqualTo(UPDATED_UPLOADED_LOGO_ID);
        assertThat(testCategories.getUploadedBackgroundId()).isEqualTo(UPDATED_UPLOADED_BACKGROUND_ID);
        assertThat(testCategories.isTopicFeaturedLinkAllowed()).isEqualTo(UPDATED_TOPIC_FEATURED_LINK_ALLOWED);
        assertThat(testCategories.isAllTopicsWiki()).isEqualTo(UPDATED_ALL_TOPICS_WIKI);
        assertThat(testCategories.isShowSubcategoryList()).isEqualTo(UPDATED_SHOW_SUBCATEGORY_LIST);
        assertThat(testCategories.getNumFeaturedTopics()).isEqualTo(UPDATED_NUM_FEATURED_TOPICS);
        assertThat(testCategories.getDefaultView()).isEqualTo(UPDATED_DEFAULT_VIEW);
        assertThat(testCategories.getSubcategoryListStyle()).isEqualTo(UPDATED_SUBCATEGORY_LIST_STYLE);
        assertThat(testCategories.getDefaultTopPeriod()).isEqualTo(UPDATED_DEFAULT_TOP_PERIOD);
        assertThat(testCategories.isMailinglistMirror()).isEqualTo(UPDATED_MAILINGLIST_MIRROR);
        assertThat(testCategories.getMinimumRequiredTags()).isEqualTo(UPDATED_MINIMUM_REQUIRED_TAGS);
        assertThat(testCategories.isNavigateToFirstPostAfterRead()).isEqualTo(UPDATED_NAVIGATE_TO_FIRST_POST_AFTER_READ);
        assertThat(testCategories.getSearchPriority()).isEqualTo(UPDATED_SEARCH_PRIORITY);
        assertThat(testCategories.isAllowGlobalTags()).isEqualTo(UPDATED_ALLOW_GLOBAL_TAGS);
        assertThat(testCategories.getReviewableByGroupId()).isEqualTo(UPDATED_REVIEWABLE_BY_GROUP_ID);
        assertThat(testCategories.getRequiredTagGroupId()).isEqualTo(UPDATED_REQUIRED_TAG_GROUP_ID);
        assertThat(testCategories.getMinTagsFromRequiredGroup()).isEqualTo(UPDATED_MIN_TAGS_FROM_REQUIRED_GROUP);
        assertThat(testCategories.getReadOnlyBanner()).isEqualTo(UPDATED_READ_ONLY_BANNER);
        assertThat(testCategories.getDefaultListFilter()).isEqualTo(UPDATED_DEFAULT_LIST_FILTER);
        assertThat(testCategories.isAllowUnlimitedOwnerEditsOnFirstPost()).isEqualTo(UPDATED_ALLOW_UNLIMITED_OWNER_EDITS_ON_FIRST_POST);
    }

    @Test
    @Transactional
    public void updateNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc.perform(put("/api/categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeDelete = categoriesRepository.findAll().size();

        // Delete the categories
        restCategoriesMockMvc.perform(delete("/api/categories/{id}", categories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
