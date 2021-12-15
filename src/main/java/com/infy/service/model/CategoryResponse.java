/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "color", "text_color", "slug", "topic_count", "post_count", "position",
		"description", "description_text", "description_excerpt", "topic_url", "read_restricted", "permission",
		"notification_level", "can_edit", "topic_template", "has_children", "sort_order", "sort_ascending",
		"show_subcategory_list", "num_featured_topics", "default_view", "subcategory_list_style", "default_top_period",
		"default_list_filter", "minimum_required_tags", "navigate_to_first_post_after_read", "topics_day",
		"topics_week", "topics_month", "topics_year", "topics_all_time", "is_uncategorized", "subcategory_ids",
		"uploaded_logo", "uploaded_background" })
public class CategoryResponse {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("color")
	private String color;
	@JsonProperty("text_color")
	private String textColor;
	@JsonProperty("slug")
	private String slug;
	@JsonProperty("topic_count")
	private Integer topicCount;
	@JsonProperty("post_count")
	private Integer postCount;
	@JsonProperty("position")
	private Integer position;
	@JsonProperty("description")
	private String description;
	@JsonProperty("description_text")
	private String descriptionText;
	@JsonProperty("description_excerpt")
	private String descriptionExcerpt;
	@JsonProperty("topic_url")
	private String topicUrl;
	@JsonProperty("read_restricted")
	private Boolean readRestricted;
	@JsonProperty("permission")
	private Integer permission;
	@JsonProperty("notification_level")
	private Integer notificationLevel;
	@JsonProperty("can_edit")
	private Boolean canEdit;
	@JsonProperty("topic_template")
	private String topicTemplate;
	@JsonProperty("has_children")
	private Boolean hasChildren;
	@JsonProperty("sort_order")
	private String sortOrder;
	@JsonProperty("sort_ascending")
	private String sortAscending;
	@JsonProperty("show_subcategory_list")
	private Boolean showSubcategoryList;
	@JsonProperty("num_featured_topics")
	private Integer numFeaturedTopics;
	@JsonProperty("default_view")
	private String defaultView;
	@JsonProperty("subcategory_list_style")
	private String subcategoryListStyle;
	@JsonProperty("default_top_period")
	private String defaultTopPeriod;
	@JsonProperty("default_list_filter")
	private String defaultListFilter;
	@JsonProperty("minimum_required_tags")
	private Integer minimumRequiredTags;
	@JsonProperty("navigate_to_first_post_after_read")
	private Boolean navigateToFirstPostAfterRead;
	@JsonProperty("topics_day")
	private Integer topicsDay;
	@JsonProperty("topics_week")
	private Integer topicsWeek;
	@JsonProperty("topics_month")
	private Integer topicsMonth;
	@JsonProperty("topics_year")
	private Integer topicsYear;
	@JsonProperty("topics_all_time")
	private Integer topicsAllTime;
	@JsonProperty("is_uncategorized")
	private Boolean isUncategorized;
	@JsonProperty("subcategory_ids")
	private List<String> subcategoryIds = null;
	@JsonProperty("uploaded_logo")
	private String uploadedLogo;
	@JsonProperty("uploaded_background")
	private String uploadedBackground;

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("color")
	public String getColor() {
		return color;
	}

	@JsonProperty("color")
	public void setColor(String color) {
		this.color = color;
	}

	@JsonProperty("text_color")
	public String getTextColor() {
		return textColor;
	}

	@JsonProperty("text_color")
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	@JsonProperty("slug")
	public String getSlug() {
		return slug;
	}

	@JsonProperty("slug")
	public void setSlug(String slug) {
		this.slug = slug;
	}

	@JsonProperty("topic_count")
	public Integer getTopicCount() {
		return topicCount;
	}

	@JsonProperty("topic_count")
	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	@JsonProperty("post_count")
	public Integer getPostCount() {
		return postCount;
	}

	@JsonProperty("post_count")
	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}

	@JsonProperty("position")
	public Integer getPosition() {
		return position;
	}

	@JsonProperty("position")
	public void setPosition(Integer position) {
		this.position = position;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("description_text")
	public String getDescriptionText() {
		return descriptionText;
	}

	@JsonProperty("description_text")
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	@JsonProperty("description_excerpt")
	public String getDescriptionExcerpt() {
		return descriptionExcerpt;
	}

	@JsonProperty("description_excerpt")
	public void setDescriptionExcerpt(String descriptionExcerpt) {
		this.descriptionExcerpt = descriptionExcerpt;
	}

	@JsonProperty("topic_url")
	public String getTopicUrl() {
		return topicUrl;
	}

	@JsonProperty("topic_url")
	public void setTopicUrl(String topicUrl) {
		this.topicUrl = topicUrl;
	}

	@JsonProperty("read_restricted")
	public Boolean getReadRestricted() {
		return readRestricted;
	}

	@JsonProperty("read_restricted")
	public void setReadRestricted(Boolean readRestricted) {
		this.readRestricted = readRestricted;
	}

	@JsonProperty("permission")
	public Integer getPermission() {
		return permission;
	}

	@JsonProperty("permission")
	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	@JsonProperty("notification_level")
	public Integer getNotificationLevel() {
		return notificationLevel;
	}

	@JsonProperty("notification_level")
	public void setNotificationLevel(Integer notificationLevel) {
		this.notificationLevel = notificationLevel;
	}

	@JsonProperty("can_edit")
	public Boolean getCanEdit() {
		return canEdit;
	}

	@JsonProperty("can_edit")
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	@JsonProperty("topic_template")
	public String getTopicTemplate() {
		return topicTemplate;
	}

	@JsonProperty("topic_template")
	public void setTopicTemplate(String topicTemplate) {
		this.topicTemplate = topicTemplate;
	}

	@JsonProperty("has_children")
	public Boolean getHasChildren() {
		return hasChildren;
	}

	@JsonProperty("has_children")
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@JsonProperty("sort_order")
	public String getSortOrder() {
		return sortOrder;
	}

	@JsonProperty("sort_order")
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@JsonProperty("sort_ascending")
	public String getSortAscending() {
		return sortAscending;
	}

	@JsonProperty("sort_ascending")
	public void setSortAscending(String sortAscending) {
		this.sortAscending = sortAscending;
	}

	@JsonProperty("show_subcategory_list")
	public Boolean getShowSubcategoryList() {
		return showSubcategoryList;
	}

	@JsonProperty("show_subcategory_list")
	public void setShowSubcategoryList(Boolean showSubcategoryList) {
		this.showSubcategoryList = showSubcategoryList;
	}

	@JsonProperty("num_featured_topics")
	public Integer getNumFeaturedTopics() {
		return numFeaturedTopics;
	}

	@JsonProperty("num_featured_topics")
	public void setNumFeaturedTopics(Integer numFeaturedTopics) {
		this.numFeaturedTopics = numFeaturedTopics;
	}

	@JsonProperty("default_view")
	public String getDefaultView() {
		return defaultView;
	}

	@JsonProperty("default_view")
	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}

	@JsonProperty("subcategory_list_style")
	public String getSubcategoryListStyle() {
		return subcategoryListStyle;
	}

	@JsonProperty("subcategory_list_style")
	public void setSubcategoryListStyle(String subcategoryListStyle) {
		this.subcategoryListStyle = subcategoryListStyle;
	}

	@JsonProperty("default_top_period")
	public String getDefaultTopPeriod() {
		return defaultTopPeriod;
	}

	@JsonProperty("default_top_period")
	public void setDefaultTopPeriod(String defaultTopPeriod) {
		this.defaultTopPeriod = defaultTopPeriod;
	}

	@JsonProperty("default_list_filter")
	public String getDefaultListFilter() {
		return defaultListFilter;
	}

	@JsonProperty("default_list_filter")
	public void setDefaultListFilter(String defaultListFilter) {
		this.defaultListFilter = defaultListFilter;
	}

	@JsonProperty("minimum_required_tags")
	public Integer getMinimumRequiredTags() {
		return minimumRequiredTags;
	}

	@JsonProperty("minimum_required_tags")
	public void setMinimumRequiredTags(Integer minimumRequiredTags) {
		this.minimumRequiredTags = minimumRequiredTags;
	}

	@JsonProperty("navigate_to_first_post_after_read")
	public Boolean getNavigateToFirstPostAfterRead() {
		return navigateToFirstPostAfterRead;
	}

	@JsonProperty("navigate_to_first_post_after_read")
	public void setNavigateToFirstPostAfterRead(Boolean navigateToFirstPostAfterRead) {
		this.navigateToFirstPostAfterRead = navigateToFirstPostAfterRead;
	}

	@JsonProperty("topics_day")
	public Integer getTopicsDay() {
		return topicsDay;
	}

	@JsonProperty("topics_day")
	public void setTopicsDay(Integer topicsDay) {
		this.topicsDay = topicsDay;
	}

	@JsonProperty("topics_week")
	public Integer getTopicsWeek() {
		return topicsWeek;
	}

	@JsonProperty("topics_week")
	public void setTopicsWeek(Integer topicsWeek) {
		this.topicsWeek = topicsWeek;
	}

	@JsonProperty("topics_month")
	public Integer getTopicsMonth() {
		return topicsMonth;
	}

	@JsonProperty("topics_month")
	public void setTopicsMonth(Integer topicsMonth) {
		this.topicsMonth = topicsMonth;
	}

	@JsonProperty("topics_year")
	public Integer getTopicsYear() {
		return topicsYear;
	}

	@JsonProperty("topics_year")
	public void setTopicsYear(Integer topicsYear) {
		this.topicsYear = topicsYear;
	}

	@JsonProperty("topics_all_time")
	public Integer getTopicsAllTime() {
		return topicsAllTime;
	}

	@JsonProperty("topics_all_time")
	public void setTopicsAllTime(Integer topicsAllTime) {
		this.topicsAllTime = topicsAllTime;
	}

	@JsonProperty("is_uncategorized")
	public Boolean getIsUncategorized() {
		return isUncategorized;
	}

	@JsonProperty("is_uncategorized")
	public void setIsUncategorized(Boolean isUncategorized) {
		this.isUncategorized = isUncategorized;
	}

	@JsonProperty("subcategory_ids")
	public List<String> getSubcategoryIds() {
		return subcategoryIds;
	}

	@JsonProperty("subcategory_ids")
	public void setSubcategoryIds(List<String> subcategoryIds) {
		this.subcategoryIds = subcategoryIds;
	}

	@JsonProperty("uploaded_logo")
	public String getUploadedLogo() {
		return uploadedLogo;
	}

	@JsonProperty("uploaded_logo")
	public void setUploadedLogo(String uploadedLogo) {
		this.uploadedLogo = uploadedLogo;
	}

	@JsonProperty("uploaded_background")
	public String getUploadedBackground() {
		return uploadedBackground;
	}

	@JsonProperty("uploaded_background")
	public void setUploadedBackground(String uploadedBackground) {
		this.uploadedBackground = uploadedBackground;
	}

}
