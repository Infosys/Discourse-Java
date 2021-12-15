/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.infy.domain.Categories} entity. This class is used
 * in {@link com.infy.web.rest.CategoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter color;

    private LongFilter topicId;

    private IntegerFilter topicCount;

    private StringFilter userId;

    private IntegerFilter topicsYear;

    private IntegerFilter topicsMonth;

    private IntegerFilter topicsWeek;

    private StringFilter slug;

    private StringFilter description;

    private StringFilter textColor;

    private BooleanFilter readRestricted;

    private DoubleFilter autoCloseHours;

    private IntegerFilter postCount;

    private LongFilter latestPostId;

    private LongFilter latestTopicId;

    private IntegerFilter position;

    private LongFilter parentCategoryId;

    private IntegerFilter postsYear;

    private IntegerFilter postsMonth;

    private IntegerFilter postsWeek;

    private StringFilter emailIn;

    private BooleanFilter emailInAllowStrangers;

    private IntegerFilter topicsDay;

    private IntegerFilter postsDay;

    private BooleanFilter allowBadges;

    private StringFilter nameLower;

    private BooleanFilter autoCloseBasedOnLastPost;

    private StringFilter topicTemplate;

    private BooleanFilter containsMessages;

    private StringFilter sortOrder;

    private BooleanFilter sortAscending;

    private LongFilter uploadedLogoId;

    private LongFilter uploadedBackgroundId;

    private BooleanFilter topicFeaturedLinkAllowed;

    private BooleanFilter allTopicsWiki;

    private BooleanFilter showSubcategoryList;

    private IntegerFilter numFeaturedTopics;

    private StringFilter defaultView;

    private StringFilter subcategoryListStyle;

    private StringFilter defaultTopPeriod;

    private BooleanFilter mailinglistMirror;

    private IntegerFilter minimumRequiredTags;

    private BooleanFilter navigateToFirstPostAfterRead;

    private IntegerFilter searchPriority;

    private BooleanFilter allowGlobalTags;

    private LongFilter reviewableByGroupId;

    private LongFilter requiredTagGroupId;

    private IntegerFilter minTagsFromRequiredGroup;

    private StringFilter readOnlyBanner;

    private StringFilter defaultListFilter;

    private BooleanFilter allowUnlimitedOwnerEditsOnFirstPost;

    public CategoriesCriteria() {
    }

    public CategoriesCriteria(CategoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.topicId = other.topicId == null ? null : other.topicId.copy();
        this.topicCount = other.topicCount == null ? null : other.topicCount.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.topicsYear = other.topicsYear == null ? null : other.topicsYear.copy();
        this.topicsMonth = other.topicsMonth == null ? null : other.topicsMonth.copy();
        this.topicsWeek = other.topicsWeek == null ? null : other.topicsWeek.copy();
        this.slug = other.slug == null ? null : other.slug.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.textColor = other.textColor == null ? null : other.textColor.copy();
        this.readRestricted = other.readRestricted == null ? null : other.readRestricted.copy();
        this.autoCloseHours = other.autoCloseHours == null ? null : other.autoCloseHours.copy();
        this.postCount = other.postCount == null ? null : other.postCount.copy();
        this.latestPostId = other.latestPostId == null ? null : other.latestPostId.copy();
        this.latestTopicId = other.latestTopicId == null ? null : other.latestTopicId.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.parentCategoryId = other.parentCategoryId == null ? null : other.parentCategoryId.copy();
        this.postsYear = other.postsYear == null ? null : other.postsYear.copy();
        this.postsMonth = other.postsMonth == null ? null : other.postsMonth.copy();
        this.postsWeek = other.postsWeek == null ? null : other.postsWeek.copy();
        this.emailIn = other.emailIn == null ? null : other.emailIn.copy();
        this.emailInAllowStrangers = other.emailInAllowStrangers == null ? null : other.emailInAllowStrangers.copy();
        this.topicsDay = other.topicsDay == null ? null : other.topicsDay.copy();
        this.postsDay = other.postsDay == null ? null : other.postsDay.copy();
        this.allowBadges = other.allowBadges == null ? null : other.allowBadges.copy();
        this.nameLower = other.nameLower == null ? null : other.nameLower.copy();
        this.autoCloseBasedOnLastPost = other.autoCloseBasedOnLastPost == null ? null : other.autoCloseBasedOnLastPost.copy();
        this.topicTemplate = other.topicTemplate == null ? null : other.topicTemplate.copy();
        this.containsMessages = other.containsMessages == null ? null : other.containsMessages.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.sortAscending = other.sortAscending == null ? null : other.sortAscending.copy();
        this.uploadedLogoId = other.uploadedLogoId == null ? null : other.uploadedLogoId.copy();
        this.uploadedBackgroundId = other.uploadedBackgroundId == null ? null : other.uploadedBackgroundId.copy();
        this.topicFeaturedLinkAllowed = other.topicFeaturedLinkAllowed == null ? null : other.topicFeaturedLinkAllowed.copy();
        this.allTopicsWiki = other.allTopicsWiki == null ? null : other.allTopicsWiki.copy();
        this.showSubcategoryList = other.showSubcategoryList == null ? null : other.showSubcategoryList.copy();
        this.numFeaturedTopics = other.numFeaturedTopics == null ? null : other.numFeaturedTopics.copy();
        this.defaultView = other.defaultView == null ? null : other.defaultView.copy();
        this.subcategoryListStyle = other.subcategoryListStyle == null ? null : other.subcategoryListStyle.copy();
        this.defaultTopPeriod = other.defaultTopPeriod == null ? null : other.defaultTopPeriod.copy();
        this.mailinglistMirror = other.mailinglistMirror == null ? null : other.mailinglistMirror.copy();
        this.minimumRequiredTags = other.minimumRequiredTags == null ? null : other.minimumRequiredTags.copy();
        this.navigateToFirstPostAfterRead = other.navigateToFirstPostAfterRead == null ? null : other.navigateToFirstPostAfterRead.copy();
        this.searchPriority = other.searchPriority == null ? null : other.searchPriority.copy();
        this.allowGlobalTags = other.allowGlobalTags == null ? null : other.allowGlobalTags.copy();
        this.reviewableByGroupId = other.reviewableByGroupId == null ? null : other.reviewableByGroupId.copy();
        this.requiredTagGroupId = other.requiredTagGroupId == null ? null : other.requiredTagGroupId.copy();
        this.minTagsFromRequiredGroup = other.minTagsFromRequiredGroup == null ? null : other.minTagsFromRequiredGroup.copy();
        this.readOnlyBanner = other.readOnlyBanner == null ? null : other.readOnlyBanner.copy();
        this.defaultListFilter = other.defaultListFilter == null ? null : other.defaultListFilter.copy();
        this.allowUnlimitedOwnerEditsOnFirstPost = other.allowUnlimitedOwnerEditsOnFirstPost == null ? null : other.allowUnlimitedOwnerEditsOnFirstPost.copy();
    }

    @Override
    public CategoriesCriteria copy() {
        return new CategoriesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public LongFilter getTopicId() {
        return topicId;
    }

    public void setTopicId(LongFilter topicId) {
        this.topicId = topicId;
    }

    public IntegerFilter getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(IntegerFilter topicCount) {
        this.topicCount = topicCount;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public IntegerFilter getTopicsYear() {
        return topicsYear;
    }

    public void setTopicsYear(IntegerFilter topicsYear) {
        this.topicsYear = topicsYear;
    }

    public IntegerFilter getTopicsMonth() {
        return topicsMonth;
    }

    public void setTopicsMonth(IntegerFilter topicsMonth) {
        this.topicsMonth = topicsMonth;
    }

    public IntegerFilter getTopicsWeek() {
        return topicsWeek;
    }

    public void setTopicsWeek(IntegerFilter topicsWeek) {
        this.topicsWeek = topicsWeek;
    }

    public StringFilter getSlug() {
        return slug;
    }

    public void setSlug(StringFilter slug) {
        this.slug = slug;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getTextColor() {
        return textColor;
    }

    public void setTextColor(StringFilter textColor) {
        this.textColor = textColor;
    }

    public BooleanFilter getReadRestricted() {
        return readRestricted;
    }

    public void setReadRestricted(BooleanFilter readRestricted) {
        this.readRestricted = readRestricted;
    }

    public DoubleFilter getAutoCloseHours() {
        return autoCloseHours;
    }

    public void setAutoCloseHours(DoubleFilter autoCloseHours) {
        this.autoCloseHours = autoCloseHours;
    }

    public IntegerFilter getPostCount() {
        return postCount;
    }

    public void setPostCount(IntegerFilter postCount) {
        this.postCount = postCount;
    }

    public LongFilter getLatestPostId() {
        return latestPostId;
    }

    public void setLatestPostId(LongFilter latestPostId) {
        this.latestPostId = latestPostId;
    }

    public LongFilter getLatestTopicId() {
        return latestTopicId;
    }

    public void setLatestTopicId(LongFilter latestTopicId) {
        this.latestTopicId = latestTopicId;
    }

    public IntegerFilter getPosition() {
        return position;
    }

    public void setPosition(IntegerFilter position) {
        this.position = position;
    }

    public LongFilter getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(LongFilter parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public IntegerFilter getPostsYear() {
        return postsYear;
    }

    public void setPostsYear(IntegerFilter postsYear) {
        this.postsYear = postsYear;
    }

    public IntegerFilter getPostsMonth() {
        return postsMonth;
    }

    public void setPostsMonth(IntegerFilter postsMonth) {
        this.postsMonth = postsMonth;
    }

    public IntegerFilter getPostsWeek() {
        return postsWeek;
    }

    public void setPostsWeek(IntegerFilter postsWeek) {
        this.postsWeek = postsWeek;
    }

    public StringFilter getEmailIn() {
        return emailIn;
    }

    public void setEmailIn(StringFilter emailIn) {
        this.emailIn = emailIn;
    }

    public BooleanFilter getEmailInAllowStrangers() {
        return emailInAllowStrangers;
    }

    public void setEmailInAllowStrangers(BooleanFilter emailInAllowStrangers) {
        this.emailInAllowStrangers = emailInAllowStrangers;
    }

    public IntegerFilter getTopicsDay() {
        return topicsDay;
    }

    public void setTopicsDay(IntegerFilter topicsDay) {
        this.topicsDay = topicsDay;
    }

    public IntegerFilter getPostsDay() {
        return postsDay;
    }

    public void setPostsDay(IntegerFilter postsDay) {
        this.postsDay = postsDay;
    }

    public BooleanFilter getAllowBadges() {
        return allowBadges;
    }

    public void setAllowBadges(BooleanFilter allowBadges) {
        this.allowBadges = allowBadges;
    }

    public StringFilter getNameLower() {
        return nameLower;
    }

    public void setNameLower(StringFilter nameLower) {
        this.nameLower = nameLower;
    }

    public BooleanFilter getAutoCloseBasedOnLastPost() {
        return autoCloseBasedOnLastPost;
    }

    public void setAutoCloseBasedOnLastPost(BooleanFilter autoCloseBasedOnLastPost) {
        this.autoCloseBasedOnLastPost = autoCloseBasedOnLastPost;
    }

    public StringFilter getTopicTemplate() {
        return topicTemplate;
    }

    public void setTopicTemplate(StringFilter topicTemplate) {
        this.topicTemplate = topicTemplate;
    }

    public BooleanFilter getContainsMessages() {
        return containsMessages;
    }

    public void setContainsMessages(BooleanFilter containsMessages) {
        this.containsMessages = containsMessages;
    }

    public StringFilter getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(StringFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BooleanFilter getSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(BooleanFilter sortAscending) {
        this.sortAscending = sortAscending;
    }

    public LongFilter getUploadedLogoId() {
        return uploadedLogoId;
    }

    public void setUploadedLogoId(LongFilter uploadedLogoId) {
        this.uploadedLogoId = uploadedLogoId;
    }

    public LongFilter getUploadedBackgroundId() {
        return uploadedBackgroundId;
    }

    public void setUploadedBackgroundId(LongFilter uploadedBackgroundId) {
        this.uploadedBackgroundId = uploadedBackgroundId;
    }

    public BooleanFilter getTopicFeaturedLinkAllowed() {
        return topicFeaturedLinkAllowed;
    }

    public void setTopicFeaturedLinkAllowed(BooleanFilter topicFeaturedLinkAllowed) {
        this.topicFeaturedLinkAllowed = topicFeaturedLinkAllowed;
    }

    public BooleanFilter getAllTopicsWiki() {
        return allTopicsWiki;
    }

    public void setAllTopicsWiki(BooleanFilter allTopicsWiki) {
        this.allTopicsWiki = allTopicsWiki;
    }

    public BooleanFilter getShowSubcategoryList() {
        return showSubcategoryList;
    }

    public void setShowSubcategoryList(BooleanFilter showSubcategoryList) {
        this.showSubcategoryList = showSubcategoryList;
    }

    public IntegerFilter getNumFeaturedTopics() {
        return numFeaturedTopics;
    }

    public void setNumFeaturedTopics(IntegerFilter numFeaturedTopics) {
        this.numFeaturedTopics = numFeaturedTopics;
    }

    public StringFilter getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(StringFilter defaultView) {
        this.defaultView = defaultView;
    }

    public StringFilter getSubcategoryListStyle() {
        return subcategoryListStyle;
    }

    public void setSubcategoryListStyle(StringFilter subcategoryListStyle) {
        this.subcategoryListStyle = subcategoryListStyle;
    }

    public StringFilter getDefaultTopPeriod() {
        return defaultTopPeriod;
    }

    public void setDefaultTopPeriod(StringFilter defaultTopPeriod) {
        this.defaultTopPeriod = defaultTopPeriod;
    }

    public BooleanFilter getMailinglistMirror() {
        return mailinglistMirror;
    }

    public void setMailinglistMirror(BooleanFilter mailinglistMirror) {
        this.mailinglistMirror = mailinglistMirror;
    }

    public IntegerFilter getMinimumRequiredTags() {
        return minimumRequiredTags;
    }

    public void setMinimumRequiredTags(IntegerFilter minimumRequiredTags) {
        this.minimumRequiredTags = minimumRequiredTags;
    }

    public BooleanFilter getNavigateToFirstPostAfterRead() {
        return navigateToFirstPostAfterRead;
    }

    public void setNavigateToFirstPostAfterRead(BooleanFilter navigateToFirstPostAfterRead) {
        this.navigateToFirstPostAfterRead = navigateToFirstPostAfterRead;
    }

    public IntegerFilter getSearchPriority() {
        return searchPriority;
    }

    public void setSearchPriority(IntegerFilter searchPriority) {
        this.searchPriority = searchPriority;
    }

    public BooleanFilter getAllowGlobalTags() {
        return allowGlobalTags;
    }

    public void setAllowGlobalTags(BooleanFilter allowGlobalTags) {
        this.allowGlobalTags = allowGlobalTags;
    }

    public LongFilter getReviewableByGroupId() {
        return reviewableByGroupId;
    }

    public void setReviewableByGroupId(LongFilter reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
    }

    public LongFilter getRequiredTagGroupId() {
        return requiredTagGroupId;
    }

    public void setRequiredTagGroupId(LongFilter requiredTagGroupId) {
        this.requiredTagGroupId = requiredTagGroupId;
    }

    public IntegerFilter getMinTagsFromRequiredGroup() {
        return minTagsFromRequiredGroup;
    }

    public void setMinTagsFromRequiredGroup(IntegerFilter minTagsFromRequiredGroup) {
        this.minTagsFromRequiredGroup = minTagsFromRequiredGroup;
    }

    public StringFilter getReadOnlyBanner() {
        return readOnlyBanner;
    }

    public void setReadOnlyBanner(StringFilter readOnlyBanner) {
        this.readOnlyBanner = readOnlyBanner;
    }

    public StringFilter getDefaultListFilter() {
        return defaultListFilter;
    }

    public void setDefaultListFilter(StringFilter defaultListFilter) {
        this.defaultListFilter = defaultListFilter;
    }

    public BooleanFilter getAllowUnlimitedOwnerEditsOnFirstPost() {
        return allowUnlimitedOwnerEditsOnFirstPost;
    }

    public void setAllowUnlimitedOwnerEditsOnFirstPost(BooleanFilter allowUnlimitedOwnerEditsOnFirstPost) {
        this.allowUnlimitedOwnerEditsOnFirstPost = allowUnlimitedOwnerEditsOnFirstPost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoriesCriteria that = (CategoriesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(color, that.color) &&
            Objects.equals(topicId, that.topicId) &&
            Objects.equals(topicCount, that.topicCount) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(topicsYear, that.topicsYear) &&
            Objects.equals(topicsMonth, that.topicsMonth) &&
            Objects.equals(topicsWeek, that.topicsWeek) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(description, that.description) &&
            Objects.equals(textColor, that.textColor) &&
            Objects.equals(readRestricted, that.readRestricted) &&
            Objects.equals(autoCloseHours, that.autoCloseHours) &&
            Objects.equals(postCount, that.postCount) &&
            Objects.equals(latestPostId, that.latestPostId) &&
            Objects.equals(latestTopicId, that.latestTopicId) &&
            Objects.equals(position, that.position) &&
            Objects.equals(parentCategoryId, that.parentCategoryId) &&
            Objects.equals(postsYear, that.postsYear) &&
            Objects.equals(postsMonth, that.postsMonth) &&
            Objects.equals(postsWeek, that.postsWeek) &&
            Objects.equals(emailIn, that.emailIn) &&
            Objects.equals(emailInAllowStrangers, that.emailInAllowStrangers) &&
            Objects.equals(topicsDay, that.topicsDay) &&
            Objects.equals(postsDay, that.postsDay) &&
            Objects.equals(allowBadges, that.allowBadges) &&
            Objects.equals(nameLower, that.nameLower) &&
            Objects.equals(autoCloseBasedOnLastPost, that.autoCloseBasedOnLastPost) &&
            Objects.equals(topicTemplate, that.topicTemplate) &&
            Objects.equals(containsMessages, that.containsMessages) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(sortAscending, that.sortAscending) &&
            Objects.equals(uploadedLogoId, that.uploadedLogoId) &&
            Objects.equals(uploadedBackgroundId, that.uploadedBackgroundId) &&
            Objects.equals(topicFeaturedLinkAllowed, that.topicFeaturedLinkAllowed) &&
            Objects.equals(allTopicsWiki, that.allTopicsWiki) &&
            Objects.equals(showSubcategoryList, that.showSubcategoryList) &&
            Objects.equals(numFeaturedTopics, that.numFeaturedTopics) &&
            Objects.equals(defaultView, that.defaultView) &&
            Objects.equals(subcategoryListStyle, that.subcategoryListStyle) &&
            Objects.equals(defaultTopPeriod, that.defaultTopPeriod) &&
            Objects.equals(mailinglistMirror, that.mailinglistMirror) &&
            Objects.equals(minimumRequiredTags, that.minimumRequiredTags) &&
            Objects.equals(navigateToFirstPostAfterRead, that.navigateToFirstPostAfterRead) &&
            Objects.equals(searchPriority, that.searchPriority) &&
            Objects.equals(allowGlobalTags, that.allowGlobalTags) &&
            Objects.equals(reviewableByGroupId, that.reviewableByGroupId) &&
            Objects.equals(requiredTagGroupId, that.requiredTagGroupId) &&
            Objects.equals(minTagsFromRequiredGroup, that.minTagsFromRequiredGroup) &&
            Objects.equals(readOnlyBanner, that.readOnlyBanner) &&
            Objects.equals(defaultListFilter, that.defaultListFilter) &&
            Objects.equals(allowUnlimitedOwnerEditsOnFirstPost, that.allowUnlimitedOwnerEditsOnFirstPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        color,
        topicId,
        topicCount,
        userId,
        topicsYear,
        topicsMonth,
        topicsWeek,
        slug,
        description,
        textColor,
        readRestricted,
        autoCloseHours,
        postCount,
        latestPostId,
        latestTopicId,
        position,
        parentCategoryId,
        postsYear,
        postsMonth,
        postsWeek,
        emailIn,
        emailInAllowStrangers,
        topicsDay,
        postsDay,
        allowBadges,
        nameLower,
        autoCloseBasedOnLastPost,
        topicTemplate,
        containsMessages,
        sortOrder,
        sortAscending,
        uploadedLogoId,
        uploadedBackgroundId,
        topicFeaturedLinkAllowed,
        allTopicsWiki,
        showSubcategoryList,
        numFeaturedTopics,
        defaultView,
        subcategoryListStyle,
        defaultTopPeriod,
        mailinglistMirror,
        minimumRequiredTags,
        navigateToFirstPostAfterRead,
        searchPriority,
        allowGlobalTags,
        reviewableByGroupId,
        requiredTagGroupId,
        minTagsFromRequiredGroup,
        readOnlyBanner,
        defaultListFilter,
        allowUnlimitedOwnerEditsOnFirstPost
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (topicId != null ? "topicId=" + topicId + ", " : "") +
                (topicCount != null ? "topicCount=" + topicCount + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (topicsYear != null ? "topicsYear=" + topicsYear + ", " : "") +
                (topicsMonth != null ? "topicsMonth=" + topicsMonth + ", " : "") +
                (topicsWeek != null ? "topicsWeek=" + topicsWeek + ", " : "") +
                (slug != null ? "slug=" + slug + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (textColor != null ? "textColor=" + textColor + ", " : "") +
                (readRestricted != null ? "readRestricted=" + readRestricted + ", " : "") +
                (autoCloseHours != null ? "autoCloseHours=" + autoCloseHours + ", " : "") +
                (postCount != null ? "postCount=" + postCount + ", " : "") +
                (latestPostId != null ? "latestPostId=" + latestPostId + ", " : "") +
                (latestTopicId != null ? "latestTopicId=" + latestTopicId + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (parentCategoryId != null ? "parentCategoryId=" + parentCategoryId + ", " : "") +
                (postsYear != null ? "postsYear=" + postsYear + ", " : "") +
                (postsMonth != null ? "postsMonth=" + postsMonth + ", " : "") +
                (postsWeek != null ? "postsWeek=" + postsWeek + ", " : "") +
                (emailIn != null ? "emailIn=" + emailIn + ", " : "") +
                (emailInAllowStrangers != null ? "emailInAllowStrangers=" + emailInAllowStrangers + ", " : "") +
                (topicsDay != null ? "topicsDay=" + topicsDay + ", " : "") +
                (postsDay != null ? "postsDay=" + postsDay + ", " : "") +
                (allowBadges != null ? "allowBadges=" + allowBadges + ", " : "") +
                (nameLower != null ? "nameLower=" + nameLower + ", " : "") +
                (autoCloseBasedOnLastPost != null ? "autoCloseBasedOnLastPost=" + autoCloseBasedOnLastPost + ", " : "") +
                (topicTemplate != null ? "topicTemplate=" + topicTemplate + ", " : "") +
                (containsMessages != null ? "containsMessages=" + containsMessages + ", " : "") +
                (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
                (sortAscending != null ? "sortAscending=" + sortAscending + ", " : "") +
                (uploadedLogoId != null ? "uploadedLogoId=" + uploadedLogoId + ", " : "") +
                (uploadedBackgroundId != null ? "uploadedBackgroundId=" + uploadedBackgroundId + ", " : "") +
                (topicFeaturedLinkAllowed != null ? "topicFeaturedLinkAllowed=" + topicFeaturedLinkAllowed + ", " : "") +
                (allTopicsWiki != null ? "allTopicsWiki=" + allTopicsWiki + ", " : "") +
                (showSubcategoryList != null ? "showSubcategoryList=" + showSubcategoryList + ", " : "") +
                (numFeaturedTopics != null ? "numFeaturedTopics=" + numFeaturedTopics + ", " : "") +
                (defaultView != null ? "defaultView=" + defaultView + ", " : "") +
                (subcategoryListStyle != null ? "subcategoryListStyle=" + subcategoryListStyle + ", " : "") +
                (defaultTopPeriod != null ? "defaultTopPeriod=" + defaultTopPeriod + ", " : "") +
                (mailinglistMirror != null ? "mailinglistMirror=" + mailinglistMirror + ", " : "") +
                (minimumRequiredTags != null ? "minimumRequiredTags=" + minimumRequiredTags + ", " : "") +
                (navigateToFirstPostAfterRead != null ? "navigateToFirstPostAfterRead=" + navigateToFirstPostAfterRead + ", " : "") +
                (searchPriority != null ? "searchPriority=" + searchPriority + ", " : "") +
                (allowGlobalTags != null ? "allowGlobalTags=" + allowGlobalTags + ", " : "") +
                (reviewableByGroupId != null ? "reviewableByGroupId=" + reviewableByGroupId + ", " : "") +
                (requiredTagGroupId != null ? "requiredTagGroupId=" + requiredTagGroupId + ", " : "") +
                (minTagsFromRequiredGroup != null ? "minTagsFromRequiredGroup=" + minTagsFromRequiredGroup + ", " : "") +
                (readOnlyBanner != null ? "readOnlyBanner=" + readOnlyBanner + ", " : "") +
                (defaultListFilter != null ? "defaultListFilter=" + defaultListFilter + ", " : "") +
                (allowUnlimitedOwnerEditsOnFirstPost != null ? "allowUnlimitedOwnerEditsOnFirstPost=" + allowUnlimitedOwnerEditsOnFirstPost + ", " : "") +
            "}";
    }

}
