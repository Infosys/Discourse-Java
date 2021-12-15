/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.Categories} entity.
 */
public class CategoriesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String color;

    private Long topicId;

    @NotNull
    private Integer topicCount;

    @NotNull
    private String userId;

    private Integer topicsYear;

    private Integer topicsMonth;

    private Integer topicsWeek;

    @NotNull
    private String slug;

    private String description;

    @NotNull
    private String textColor;

    @NotNull
    private Boolean readRestricted;

    private Double autoCloseHours;

    @NotNull
    private Integer postCount;

    private Long latestPostId;

    private Long latestTopicId;

    private Integer position;

    private Long parentCategoryId;

    private Integer postsYear;

    private Integer postsMonth;

    private Integer postsWeek;

    private String emailIn;

    private Boolean emailInAllowStrangers;

    private Integer topicsDay;

    private Integer postsDay;

    @NotNull
    private Boolean allowBadges;

    private String nameLower;

    private Boolean autoCloseBasedOnLastPost;

    private String topicTemplate;

    private Boolean containsMessages;

    private String sortOrder;

    private Boolean sortAscending;

    private Long uploadedLogoId;

    private Long uploadedBackgroundId;

    private Boolean topicFeaturedLinkAllowed;

    @NotNull
    private Boolean allTopicsWiki;

    private Boolean showSubcategoryList;

    private Integer numFeaturedTopics;

    private String defaultView;

    private String subcategoryListStyle;

    private String defaultTopPeriod;

    @NotNull
    private Boolean mailinglistMirror;

    @NotNull
    private Integer minimumRequiredTags;

    @NotNull
    private Boolean navigateToFirstPostAfterRead;

    private Integer searchPriority;

    @NotNull
    private Boolean allowGlobalTags;

    private Long reviewableByGroupId;

    private Long requiredTagGroupId;

    @NotNull
    private Integer minTagsFromRequiredGroup;

    private String readOnlyBanner;

    private String defaultListFilter;

    @NotNull
    private Boolean allowUnlimitedOwnerEditsOnFirstPost;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTopicsYear() {
        return topicsYear;
    }

    public void setTopicsYear(Integer topicsYear) {
        this.topicsYear = topicsYear;
    }

    public Integer getTopicsMonth() {
        return topicsMonth;
    }

    public void setTopicsMonth(Integer topicsMonth) {
        this.topicsMonth = topicsMonth;
    }

    public Integer getTopicsWeek() {
        return topicsWeek;
    }

    public void setTopicsWeek(Integer topicsWeek) {
        this.topicsWeek = topicsWeek;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Boolean isReadRestricted() {
        return readRestricted;
    }

    public void setReadRestricted(Boolean readRestricted) {
        this.readRestricted = readRestricted;
    }

    public Double getAutoCloseHours() {
        return autoCloseHours;
    }

    public void setAutoCloseHours(Double autoCloseHours) {
        this.autoCloseHours = autoCloseHours;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Long getLatestPostId() {
        return latestPostId;
    }

    public void setLatestPostId(Long latestPostId) {
        this.latestPostId = latestPostId;
    }

    public Long getLatestTopicId() {
        return latestTopicId;
    }

    public void setLatestTopicId(Long latestTopicId) {
        this.latestTopicId = latestTopicId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getPostsYear() {
        return postsYear;
    }

    public void setPostsYear(Integer postsYear) {
        this.postsYear = postsYear;
    }

    public Integer getPostsMonth() {
        return postsMonth;
    }

    public void setPostsMonth(Integer postsMonth) {
        this.postsMonth = postsMonth;
    }

    public Integer getPostsWeek() {
        return postsWeek;
    }

    public void setPostsWeek(Integer postsWeek) {
        this.postsWeek = postsWeek;
    }

    public String getEmailIn() {
        return emailIn;
    }

    public void setEmailIn(String emailIn) {
        this.emailIn = emailIn;
    }

    public Boolean isEmailInAllowStrangers() {
        return emailInAllowStrangers;
    }

    public void setEmailInAllowStrangers(Boolean emailInAllowStrangers) {
        this.emailInAllowStrangers = emailInAllowStrangers;
    }

    public Integer getTopicsDay() {
        return topicsDay;
    }

    public void setTopicsDay(Integer topicsDay) {
        this.topicsDay = topicsDay;
    }

    public Integer getPostsDay() {
        return postsDay;
    }

    public void setPostsDay(Integer postsDay) {
        this.postsDay = postsDay;
    }

    public Boolean isAllowBadges() {
        return allowBadges;
    }

    public void setAllowBadges(Boolean allowBadges) {
        this.allowBadges = allowBadges;
    }

    public String getNameLower() {
        return nameLower;
    }

    public void setNameLower(String nameLower) {
        this.nameLower = nameLower;
    }

    public Boolean isAutoCloseBasedOnLastPost() {
        return autoCloseBasedOnLastPost;
    }

    public void setAutoCloseBasedOnLastPost(Boolean autoCloseBasedOnLastPost) {
        this.autoCloseBasedOnLastPost = autoCloseBasedOnLastPost;
    }

    public String getTopicTemplate() {
        return topicTemplate;
    }

    public void setTopicTemplate(String topicTemplate) {
        this.topicTemplate = topicTemplate;
    }

    public Boolean isContainsMessages() {
        return containsMessages;
    }

    public void setContainsMessages(Boolean containsMessages) {
        this.containsMessages = containsMessages;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    public Long getUploadedLogoId() {
        return uploadedLogoId;
    }

    public void setUploadedLogoId(Long uploadedLogoId) {
        this.uploadedLogoId = uploadedLogoId;
    }

    public Long getUploadedBackgroundId() {
        return uploadedBackgroundId;
    }

    public void setUploadedBackgroundId(Long uploadedBackgroundId) {
        this.uploadedBackgroundId = uploadedBackgroundId;
    }

    public Boolean isTopicFeaturedLinkAllowed() {
        return topicFeaturedLinkAllowed;
    }

    public void setTopicFeaturedLinkAllowed(Boolean topicFeaturedLinkAllowed) {
        this.topicFeaturedLinkAllowed = topicFeaturedLinkAllowed;
    }

    public Boolean isAllTopicsWiki() {
        return allTopicsWiki;
    }

    public void setAllTopicsWiki(Boolean allTopicsWiki) {
        this.allTopicsWiki = allTopicsWiki;
    }

    public Boolean isShowSubcategoryList() {
        return showSubcategoryList;
    }

    public void setShowSubcategoryList(Boolean showSubcategoryList) {
        this.showSubcategoryList = showSubcategoryList;
    }

    public Integer getNumFeaturedTopics() {
        return numFeaturedTopics;
    }

    public void setNumFeaturedTopics(Integer numFeaturedTopics) {
        this.numFeaturedTopics = numFeaturedTopics;
    }

    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }

    public String getSubcategoryListStyle() {
        return subcategoryListStyle;
    }

    public void setSubcategoryListStyle(String subcategoryListStyle) {
        this.subcategoryListStyle = subcategoryListStyle;
    }

    public String getDefaultTopPeriod() {
        return defaultTopPeriod;
    }

    public void setDefaultTopPeriod(String defaultTopPeriod) {
        this.defaultTopPeriod = defaultTopPeriod;
    }

    public Boolean isMailinglistMirror() {
        return mailinglistMirror;
    }

    public void setMailinglistMirror(Boolean mailinglistMirror) {
        this.mailinglistMirror = mailinglistMirror;
    }

    public Integer getMinimumRequiredTags() {
        return minimumRequiredTags;
    }

    public void setMinimumRequiredTags(Integer minimumRequiredTags) {
        this.minimumRequiredTags = minimumRequiredTags;
    }

    public Boolean isNavigateToFirstPostAfterRead() {
        return navigateToFirstPostAfterRead;
    }

    public void setNavigateToFirstPostAfterRead(Boolean navigateToFirstPostAfterRead) {
        this.navigateToFirstPostAfterRead = navigateToFirstPostAfterRead;
    }

    public Integer getSearchPriority() {
        return searchPriority;
    }

    public void setSearchPriority(Integer searchPriority) {
        this.searchPriority = searchPriority;
    }

    public Boolean isAllowGlobalTags() {
        return allowGlobalTags;
    }

    public void setAllowGlobalTags(Boolean allowGlobalTags) {
        this.allowGlobalTags = allowGlobalTags;
    }

    public Long getReviewableByGroupId() {
        return reviewableByGroupId;
    }

    public void setReviewableByGroupId(Long reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
    }

    public Long getRequiredTagGroupId() {
        return requiredTagGroupId;
    }

    public void setRequiredTagGroupId(Long requiredTagGroupId) {
        this.requiredTagGroupId = requiredTagGroupId;
    }

    public Integer getMinTagsFromRequiredGroup() {
        return minTagsFromRequiredGroup;
    }

    public void setMinTagsFromRequiredGroup(Integer minTagsFromRequiredGroup) {
        this.minTagsFromRequiredGroup = minTagsFromRequiredGroup;
    }

    public String getReadOnlyBanner() {
        return readOnlyBanner;
    }

    public void setReadOnlyBanner(String readOnlyBanner) {
        this.readOnlyBanner = readOnlyBanner;
    }

    public String getDefaultListFilter() {
        return defaultListFilter;
    }

    public void setDefaultListFilter(String defaultListFilter) {
        this.defaultListFilter = defaultListFilter;
    }

    public Boolean isAllowUnlimitedOwnerEditsOnFirstPost() {
        return allowUnlimitedOwnerEditsOnFirstPost;
    }

    public void setAllowUnlimitedOwnerEditsOnFirstPost(Boolean allowUnlimitedOwnerEditsOnFirstPost) {
        this.allowUnlimitedOwnerEditsOnFirstPost = allowUnlimitedOwnerEditsOnFirstPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriesDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoriesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesDTO{" +
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
