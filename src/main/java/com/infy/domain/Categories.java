/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Categories.
 */
@Entity
@Table(name = "categories")
public class Categories extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "topic_id")
    private Long topicId;

    @NotNull
    @Column(name = "topic_count", nullable = false)
    private Integer topicCount;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "topics_year")
    private Integer topicsYear;

    @Column(name = "topics_month")
    private Integer topicsMonth;

    @Column(name = "topics_week")
    private Integer topicsWeek;

    @NotNull
    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "text_color", nullable = false)
    private String textColor;

    @NotNull
    @Column(name = "read_restricted", nullable = false)
    private Boolean readRestricted;

    @Column(name = "auto_close_hours")
    private Double autoCloseHours;

    @NotNull
    @Column(name = "post_count", nullable = false)
    private Integer postCount;

    @Column(name = "latest_post_id")
    private Long latestPostId;

    @Column(name = "latest_topic_id")
    private Long latestTopicId;

    @Column(name = "position")
    private Integer position;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @Column(name = "posts_year")
    private Integer postsYear;

    @Column(name = "posts_month")
    private Integer postsMonth;

    @Column(name = "posts_week")
    private Integer postsWeek;

    @Column(name = "email_in")
    private String emailIn;

    @Column(name = "email_in_allow_strangers")
    private Boolean emailInAllowStrangers;

    @Column(name = "topics_day")
    private Integer topicsDay;

    @Column(name = "posts_day")
    private Integer postsDay;

    @NotNull
    @Column(name = "allow_badges", nullable = false)
    private Boolean allowBadges;

    @Column(name = "name_lower")
    private String nameLower;

    @Column(name = "auto_close_based_on_last_post")
    private Boolean autoCloseBasedOnLastPost;

    @Column(name = "topic_template")
    private String topicTemplate;

    @Column(name = "contains_messages")
    private Boolean containsMessages;

    @Column(name = "sort_order")
    private String sortOrder;

    @Column(name = "sort_ascending")
    private Boolean sortAscending;

    @Column(name = "uploaded_logo_id")
    private Long uploadedLogoId;

    @Column(name = "uploaded_background_id")
    private Long uploadedBackgroundId;

    @Column(name = "topic_featured_link_allowed")
    private Boolean topicFeaturedLinkAllowed;

    @NotNull
    @Column(name = "all_topics_wiki", nullable = false)
    private Boolean allTopicsWiki;

    @Column(name = "show_subcategory_list")
    private Boolean showSubcategoryList;

    @Column(name = "num_featured_topics")
    private Integer numFeaturedTopics;

    @Column(name = "default_view")
    private String defaultView;

    @Column(name = "subcategory_list_style")
    private String subcategoryListStyle;

    @Column(name = "default_top_period")
    private String defaultTopPeriod;

    @NotNull
    @Column(name = "mailinglist_mirror", nullable = false)
    private Boolean mailinglistMirror;

    @NotNull
    @Column(name = "minimum_required_tags", nullable = false)
    private Integer minimumRequiredTags;

    @NotNull
    @Column(name = "navigate_to_first_post_after_read", nullable = false)
    private Boolean navigateToFirstPostAfterRead;

    @Column(name = "search_priority")
    private Integer searchPriority;

    @NotNull
    @Column(name = "allow_global_tags", nullable = false)
    private Boolean allowGlobalTags;

    @Column(name = "reviewable_by_group_id")
    private Long reviewableByGroupId;

    @Column(name = "required_tag_group_id")
    private Long requiredTagGroupId;

    @NotNull
    @Column(name = "min_tags_from_required_group", nullable = false)
    private Integer minTagsFromRequiredGroup;

    @Column(name = "read_only_banner")
    private String readOnlyBanner;

    @Column(name = "default_list_filter")
    private String defaultListFilter;

    @NotNull
    @Column(name = "allow_unlimited_owner_edits_on_first_post", nullable = false)
    private Boolean allowUnlimitedOwnerEditsOnFirstPost;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Categories name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public Categories color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Categories topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public Categories topicCount(Integer topicCount) {
        this.topicCount = topicCount;
        return this;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public String getUserId() {
        return userId;
    }

    public Categories userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTopicsYear() {
        return topicsYear;
    }

    public Categories topicsYear(Integer topicsYear) {
        this.topicsYear = topicsYear;
        return this;
    }

    public void setTopicsYear(Integer topicsYear) {
        this.topicsYear = topicsYear;
    }

    public Integer getTopicsMonth() {
        return topicsMonth;
    }

    public Categories topicsMonth(Integer topicsMonth) {
        this.topicsMonth = topicsMonth;
        return this;
    }

    public void setTopicsMonth(Integer topicsMonth) {
        this.topicsMonth = topicsMonth;
    }

    public Integer getTopicsWeek() {
        return topicsWeek;
    }

    public Categories topicsWeek(Integer topicsWeek) {
        this.topicsWeek = topicsWeek;
        return this;
    }

    public void setTopicsWeek(Integer topicsWeek) {
        this.topicsWeek = topicsWeek;
    }

    public String getSlug() {
        return slug;
    }

    public Categories slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public Categories description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextColor() {
        return textColor;
    }

    public Categories textColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Boolean isReadRestricted() {
        return readRestricted;
    }

    public Categories readRestricted(Boolean readRestricted) {
        this.readRestricted = readRestricted;
        return this;
    }

    public void setReadRestricted(Boolean readRestricted) {
        this.readRestricted = readRestricted;
    }

    public Double getAutoCloseHours() {
        return autoCloseHours;
    }

    public Categories autoCloseHours(Double autoCloseHours) {
        this.autoCloseHours = autoCloseHours;
        return this;
    }

    public void setAutoCloseHours(Double autoCloseHours) {
        this.autoCloseHours = autoCloseHours;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public Categories postCount(Integer postCount) {
        this.postCount = postCount;
        return this;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Long getLatestPostId() {
        return latestPostId;
    }

    public Categories latestPostId(Long latestPostId) {
        this.latestPostId = latestPostId;
        return this;
    }

    public void setLatestPostId(Long latestPostId) {
        this.latestPostId = latestPostId;
    }

    public Long getLatestTopicId() {
        return latestTopicId;
    }

    public Categories latestTopicId(Long latestTopicId) {
        this.latestTopicId = latestTopicId;
        return this;
    }

    public void setLatestTopicId(Long latestTopicId) {
        this.latestTopicId = latestTopicId;
    }

    public Integer getPosition() {
        return position;
    }

    public Categories position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public Categories parentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return this;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getPostsYear() {
        return postsYear;
    }

    public Categories postsYear(Integer postsYear) {
        this.postsYear = postsYear;
        return this;
    }

    public void setPostsYear(Integer postsYear) {
        this.postsYear = postsYear;
    }

    public Integer getPostsMonth() {
        return postsMonth;
    }

    public Categories postsMonth(Integer postsMonth) {
        this.postsMonth = postsMonth;
        return this;
    }

    public void setPostsMonth(Integer postsMonth) {
        this.postsMonth = postsMonth;
    }

    public Integer getPostsWeek() {
        return postsWeek;
    }

    public Categories postsWeek(Integer postsWeek) {
        this.postsWeek = postsWeek;
        return this;
    }

    public void setPostsWeek(Integer postsWeek) {
        this.postsWeek = postsWeek;
    }

    public String getEmailIn() {
        return emailIn;
    }

    public Categories emailIn(String emailIn) {
        this.emailIn = emailIn;
        return this;
    }

    public void setEmailIn(String emailIn) {
        this.emailIn = emailIn;
    }

    public Boolean isEmailInAllowStrangers() {
        return emailInAllowStrangers;
    }

    public Categories emailInAllowStrangers(Boolean emailInAllowStrangers) {
        this.emailInAllowStrangers = emailInAllowStrangers;
        return this;
    }

    public void setEmailInAllowStrangers(Boolean emailInAllowStrangers) {
        this.emailInAllowStrangers = emailInAllowStrangers;
    }

    public Integer getTopicsDay() {
        return topicsDay;
    }

    public Categories topicsDay(Integer topicsDay) {
        this.topicsDay = topicsDay;
        return this;
    }

    public void setTopicsDay(Integer topicsDay) {
        this.topicsDay = topicsDay;
    }

    public Integer getPostsDay() {
        return postsDay;
    }

    public Categories postsDay(Integer postsDay) {
        this.postsDay = postsDay;
        return this;
    }

    public void setPostsDay(Integer postsDay) {
        this.postsDay = postsDay;
    }

    public Boolean isAllowBadges() {
        return allowBadges;
    }

    public Categories allowBadges(Boolean allowBadges) {
        this.allowBadges = allowBadges;
        return this;
    }

    public void setAllowBadges(Boolean allowBadges) {
        this.allowBadges = allowBadges;
    }

    public String getNameLower() {
        return nameLower;
    }

    public Categories nameLower(String nameLower) {
        this.nameLower = nameLower;
        return this;
    }

    public void setNameLower(String nameLower) {
        this.nameLower = nameLower;
    }

    public Boolean isAutoCloseBasedOnLastPost() {
        return autoCloseBasedOnLastPost;
    }

    public Categories autoCloseBasedOnLastPost(Boolean autoCloseBasedOnLastPost) {
        this.autoCloseBasedOnLastPost = autoCloseBasedOnLastPost;
        return this;
    }

    public void setAutoCloseBasedOnLastPost(Boolean autoCloseBasedOnLastPost) {
        this.autoCloseBasedOnLastPost = autoCloseBasedOnLastPost;
    }

    public String getTopicTemplate() {
        return topicTemplate;
    }

    public Categories topicTemplate(String topicTemplate) {
        this.topicTemplate = topicTemplate;
        return this;
    }

    public void setTopicTemplate(String topicTemplate) {
        this.topicTemplate = topicTemplate;
    }

    public Boolean isContainsMessages() {
        return containsMessages;
    }

    public Categories containsMessages(Boolean containsMessages) {
        this.containsMessages = containsMessages;
        return this;
    }

    public void setContainsMessages(Boolean containsMessages) {
        this.containsMessages = containsMessages;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public Categories sortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isSortAscending() {
        return sortAscending;
    }

    public Categories sortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
        return this;
    }

    public void setSortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    public Long getUploadedLogoId() {
        return uploadedLogoId;
    }

    public Categories uploadedLogoId(Long uploadedLogoId) {
        this.uploadedLogoId = uploadedLogoId;
        return this;
    }

    public void setUploadedLogoId(Long uploadedLogoId) {
        this.uploadedLogoId = uploadedLogoId;
    }

    public Long getUploadedBackgroundId() {
        return uploadedBackgroundId;
    }

    public Categories uploadedBackgroundId(Long uploadedBackgroundId) {
        this.uploadedBackgroundId = uploadedBackgroundId;
        return this;
    }

    public void setUploadedBackgroundId(Long uploadedBackgroundId) {
        this.uploadedBackgroundId = uploadedBackgroundId;
    }

    public Boolean isTopicFeaturedLinkAllowed() {
        return topicFeaturedLinkAllowed;
    }

    public Categories topicFeaturedLinkAllowed(Boolean topicFeaturedLinkAllowed) {
        this.topicFeaturedLinkAllowed = topicFeaturedLinkAllowed;
        return this;
    }

    public void setTopicFeaturedLinkAllowed(Boolean topicFeaturedLinkAllowed) {
        this.topicFeaturedLinkAllowed = topicFeaturedLinkAllowed;
    }

    public Boolean isAllTopicsWiki() {
        return allTopicsWiki;
    }

    public Categories allTopicsWiki(Boolean allTopicsWiki) {
        this.allTopicsWiki = allTopicsWiki;
        return this;
    }

    public void setAllTopicsWiki(Boolean allTopicsWiki) {
        this.allTopicsWiki = allTopicsWiki;
    }

    public Boolean isShowSubcategoryList() {
        return showSubcategoryList;
    }

    public Categories showSubcategoryList(Boolean showSubcategoryList) {
        this.showSubcategoryList = showSubcategoryList;
        return this;
    }

    public void setShowSubcategoryList(Boolean showSubcategoryList) {
        this.showSubcategoryList = showSubcategoryList;
    }

    public Integer getNumFeaturedTopics() {
        return numFeaturedTopics;
    }

    public Categories numFeaturedTopics(Integer numFeaturedTopics) {
        this.numFeaturedTopics = numFeaturedTopics;
        return this;
    }

    public void setNumFeaturedTopics(Integer numFeaturedTopics) {
        this.numFeaturedTopics = numFeaturedTopics;
    }

    public String getDefaultView() {
        return defaultView;
    }

    public Categories defaultView(String defaultView) {
        this.defaultView = defaultView;
        return this;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }

    public String getSubcategoryListStyle() {
        return subcategoryListStyle;
    }

    public Categories subcategoryListStyle(String subcategoryListStyle) {
        this.subcategoryListStyle = subcategoryListStyle;
        return this;
    }

    public void setSubcategoryListStyle(String subcategoryListStyle) {
        this.subcategoryListStyle = subcategoryListStyle;
    }

    public String getDefaultTopPeriod() {
        return defaultTopPeriod;
    }

    public Categories defaultTopPeriod(String defaultTopPeriod) {
        this.defaultTopPeriod = defaultTopPeriod;
        return this;
    }

    public void setDefaultTopPeriod(String defaultTopPeriod) {
        this.defaultTopPeriod = defaultTopPeriod;
    }

    public Boolean isMailinglistMirror() {
        return mailinglistMirror;
    }

    public Categories mailinglistMirror(Boolean mailinglistMirror) {
        this.mailinglistMirror = mailinglistMirror;
        return this;
    }

    public void setMailinglistMirror(Boolean mailinglistMirror) {
        this.mailinglistMirror = mailinglistMirror;
    }

    public Integer getMinimumRequiredTags() {
        return minimumRequiredTags;
    }

    public Categories minimumRequiredTags(Integer minimumRequiredTags) {
        this.minimumRequiredTags = minimumRequiredTags;
        return this;
    }

    public void setMinimumRequiredTags(Integer minimumRequiredTags) {
        this.minimumRequiredTags = minimumRequiredTags;
    }

    public Boolean isNavigateToFirstPostAfterRead() {
        return navigateToFirstPostAfterRead;
    }

    public Categories navigateToFirstPostAfterRead(Boolean navigateToFirstPostAfterRead) {
        this.navigateToFirstPostAfterRead = navigateToFirstPostAfterRead;
        return this;
    }

    public void setNavigateToFirstPostAfterRead(Boolean navigateToFirstPostAfterRead) {
        this.navigateToFirstPostAfterRead = navigateToFirstPostAfterRead;
    }

    public Integer getSearchPriority() {
        return searchPriority;
    }

    public Categories searchPriority(Integer searchPriority) {
        this.searchPriority = searchPriority;
        return this;
    }

    public void setSearchPriority(Integer searchPriority) {
        this.searchPriority = searchPriority;
    }

    public Boolean isAllowGlobalTags() {
        return allowGlobalTags;
    }

    public Categories allowGlobalTags(Boolean allowGlobalTags) {
        this.allowGlobalTags = allowGlobalTags;
        return this;
    }

    public void setAllowGlobalTags(Boolean allowGlobalTags) {
        this.allowGlobalTags = allowGlobalTags;
    }

    public Long getReviewableByGroupId() {
        return reviewableByGroupId;
    }

    public Categories reviewableByGroupId(Long reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
        return this;
    }

    public void setReviewableByGroupId(Long reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
    }

    public Long getRequiredTagGroupId() {
        return requiredTagGroupId;
    }

    public Categories requiredTagGroupId(Long requiredTagGroupId) {
        this.requiredTagGroupId = requiredTagGroupId;
        return this;
    }

    public void setRequiredTagGroupId(Long requiredTagGroupId) {
        this.requiredTagGroupId = requiredTagGroupId;
    }

    public Integer getMinTagsFromRequiredGroup() {
        return minTagsFromRequiredGroup;
    }

    public Categories minTagsFromRequiredGroup(Integer minTagsFromRequiredGroup) {
        this.minTagsFromRequiredGroup = minTagsFromRequiredGroup;
        return this;
    }

    public void setMinTagsFromRequiredGroup(Integer minTagsFromRequiredGroup) {
        this.minTagsFromRequiredGroup = minTagsFromRequiredGroup;
    }

    public String getReadOnlyBanner() {
        return readOnlyBanner;
    }

    public Categories readOnlyBanner(String readOnlyBanner) {
        this.readOnlyBanner = readOnlyBanner;
        return this;
    }

    public void setReadOnlyBanner(String readOnlyBanner) {
        this.readOnlyBanner = readOnlyBanner;
    }

    public String getDefaultListFilter() {
        return defaultListFilter;
    }

    public Categories defaultListFilter(String defaultListFilter) {
        this.defaultListFilter = defaultListFilter;
        return this;
    }

    public void setDefaultListFilter(String defaultListFilter) {
        this.defaultListFilter = defaultListFilter;
    }

    public Boolean isAllowUnlimitedOwnerEditsOnFirstPost() {
        return allowUnlimitedOwnerEditsOnFirstPost;
    }

    public Categories allowUnlimitedOwnerEditsOnFirstPost(Boolean allowUnlimitedOwnerEditsOnFirstPost) {
        this.allowUnlimitedOwnerEditsOnFirstPost = allowUnlimitedOwnerEditsOnFirstPost;
        return this;
    }

    public void setAllowUnlimitedOwnerEditsOnFirstPost(Boolean allowUnlimitedOwnerEditsOnFirstPost) {
        this.allowUnlimitedOwnerEditsOnFirstPost = allowUnlimitedOwnerEditsOnFirstPost;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categories)) {
            return false;
        }
        return id != null && id.equals(((Categories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categories{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", topicId=" + getTopicId() +
            ", topicCount=" + getTopicCount() +
            ", userId='" + getUserId() + "'" +
            ", topicsYear=" + getTopicsYear() +
            ", topicsMonth=" + getTopicsMonth() +
            ", topicsWeek=" + getTopicsWeek() +
            ", slug='" + getSlug() + "'" +
            ", description='" + getDescription() + "'" +
            ", textColor='" + getTextColor() + "'" +
            ", readRestricted='" + isReadRestricted() + "'" +
            ", autoCloseHours=" + getAutoCloseHours() +
            ", postCount=" + getPostCount() +
            ", latestPostId=" + getLatestPostId() +
            ", latestTopicId=" + getLatestTopicId() +
            ", position=" + getPosition() +
            ", parentCategoryId=" + getParentCategoryId() +
            ", postsYear=" + getPostsYear() +
            ", postsMonth=" + getPostsMonth() +
            ", postsWeek=" + getPostsWeek() +
            ", emailIn='" + getEmailIn() + "'" +
            ", emailInAllowStrangers='" + isEmailInAllowStrangers() + "'" +
            ", topicsDay=" + getTopicsDay() +
            ", postsDay=" + getPostsDay() +
            ", allowBadges='" + isAllowBadges() + "'" +
            ", nameLower='" + getNameLower() + "'" +
            ", autoCloseBasedOnLastPost='" + isAutoCloseBasedOnLastPost() + "'" +
            ", topicTemplate='" + getTopicTemplate() + "'" +
            ", containsMessages='" + isContainsMessages() + "'" +
            ", sortOrder='" + getSortOrder() + "'" +
            ", sortAscending='" + isSortAscending() + "'" +
            ", uploadedLogoId=" + getUploadedLogoId() +
            ", uploadedBackgroundId=" + getUploadedBackgroundId() +
            ", topicFeaturedLinkAllowed='" + isTopicFeaturedLinkAllowed() + "'" +
            ", allTopicsWiki='" + isAllTopicsWiki() + "'" +
            ", showSubcategoryList='" + isShowSubcategoryList() + "'" +
            ", numFeaturedTopics=" + getNumFeaturedTopics() +
            ", defaultView='" + getDefaultView() + "'" +
            ", subcategoryListStyle='" + getSubcategoryListStyle() + "'" +
            ", defaultTopPeriod='" + getDefaultTopPeriod() + "'" +
            ", mailinglistMirror='" + isMailinglistMirror() + "'" +
            ", minimumRequiredTags=" + getMinimumRequiredTags() +
            ", navigateToFirstPostAfterRead='" + isNavigateToFirstPostAfterRead() + "'" +
            ", searchPriority=" + getSearchPriority() +
            ", allowGlobalTags='" + isAllowGlobalTags() + "'" +
            ", reviewableByGroupId=" + getReviewableByGroupId() +
            ", requiredTagGroupId=" + getRequiredTagGroupId() +
            ", minTagsFromRequiredGroup=" + getMinTagsFromRequiredGroup() +
            ", readOnlyBanner='" + getReadOnlyBanner() + "'" +
            ", defaultListFilter='" + getDefaultListFilter() + "'" +
            ", allowUnlimitedOwnerEditsOnFirstPost='" + isAllowUnlimitedOwnerEditsOnFirstPost() + "'" +
            "}";
    }
}
