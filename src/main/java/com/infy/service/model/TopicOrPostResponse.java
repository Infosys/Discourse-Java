/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicOrPostResponse {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("username")
	private String username;
	@JsonProperty("avatar_template")
	private String avatarTemplate;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("cooked")
	private String cooked;
	@JsonProperty("post_number")
	private Integer postNumber;
	@JsonProperty("post_type")
	private Integer postType;
	@JsonProperty("updated_at")
	private String updatedAt;
	@JsonProperty("reply_count")
	private Integer replyCount;
	@JsonProperty("reply_to_post_number")
	private String replyToPostNumber;
	@JsonProperty("quote_count")
	private Integer quoteCount;
	@JsonProperty("incoming_link_count")
	private Integer incomingLinkCount;
	@JsonProperty("like_count")
	private Integer likeCount;
	@JsonProperty("current_user_like")
	private Boolean currentUserLike;
	@JsonProperty("reads")
	private Integer reads;
	@JsonProperty("readers_count")
	private Integer readersCount;
	@JsonProperty("score")
	private Integer score;
	@JsonProperty("yours")
	private Boolean yours;
	@JsonProperty("topic_id")
	private Long topicId;
	@JsonProperty("topic_slug")
	private String topicSlug;
	@JsonProperty("display_username")
	private String displayUsername;
	@JsonProperty("primary_group_name")
	private String primaryGroupName;
	@JsonProperty("primary_group_flair_url")
	private String primaryGroupFlairUrl;
	@JsonProperty("primary_group_flair_bg_color")
	private String primaryGroupFlairBgColor;
	@JsonProperty("primary_group_flair_color")
	private String primaryGroupFlairColor;
	@JsonProperty("version")
	private Integer version;
	@JsonProperty("can_edit")
	private Boolean canEdit;
	@JsonProperty("can_delete")
	private Boolean canDelete;
	@JsonProperty("can_recover")
	private Boolean canRecover;
	@JsonProperty("can_wiki")
	private Boolean canWiki;
	@JsonProperty("user_title")
	private String userTitle;
	@JsonProperty("actions_summary")
	private List<ActionSummaryOfTopic> actionsSummary = null;
	@JsonProperty("moderator")
	private Boolean moderator;
	@JsonProperty("admin")
	private Boolean admin;
	@JsonProperty("staff")
	private Boolean staff;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("draft_sequence")
	private Integer draftSequence;
	@JsonProperty("hidden")
	private Boolean hidden;
	@JsonProperty("trust_level")
	private Integer trustLevel;
	@JsonProperty("deleted_at")
	private String deletedAt;
	@JsonProperty("user_deleted")
	private Boolean userDeleted;
	@JsonProperty("edit_reason")
	private String editReason;
	@JsonProperty("can_view_edit_history")
	private Boolean canViewEditHistory;
	@JsonProperty("wiki")
	private Boolean wiki;
	@JsonProperty("reviewable_id")
	private String reviewableId;
	@JsonProperty("reviewable_score_count")
	private Integer reviewableScoreCount;
	@JsonProperty("reviewable_score_pending_count")
	private Integer reviewableScorePendingCount;
	@JsonProperty("category_id")
	private Long categoryId;
	@JsonProperty("last_posted_at")
	private Instant lastPostedAt;
	@JsonProperty("tags")
	private String tags;
	@JsonProperty("arche_type")
	private String archetype;

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

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("avatar_template")
	public String getAvatarTemplate() {
		return avatarTemplate;
	}

	@JsonProperty("avatar_template")
	public void setAvatarTemplate(String avatarTemplate) {
		this.avatarTemplate = avatarTemplate;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("cooked")
	public String getCooked() {
		return cooked;
	}

	@JsonProperty("cooked")
	public void setCooked(String cooked) {
		this.cooked = cooked;
	}

	@JsonProperty("post_number")
	public Integer getPostNumber() {
		return postNumber;
	}

	@JsonProperty("post_number")
	public void setPostNumber(Integer postNumber) {
		this.postNumber = postNumber;
	}

	@JsonProperty("post_type")
	public Integer getPostType() {
		return postType;
	}

	@JsonProperty("post_type")
	public void setPostType(Integer postType) {
		this.postType = postType;
	}

	@JsonProperty("updated_at")
	public String getUpdatedAt() {
		return updatedAt;
	}

	@JsonProperty("updated_at")
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonProperty("reply_count")
	public Integer getReplyCount() {
		return replyCount;
	}

	@JsonProperty("reply_count")
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	@JsonProperty("reply_to_post_number")
	public String getReplyToPostNumber() {
		return replyToPostNumber;
	}

	@JsonProperty("reply_to_post_number")
	public void setReplyToPostNumber(String replyToPostNumber) {
		this.replyToPostNumber = replyToPostNumber;
	}

	@JsonProperty("quote_count")
	public Integer getQuoteCount() {
		return quoteCount;
	}

	@JsonProperty("quote_count")
	public void setQuoteCount(Integer quoteCount) {
		this.quoteCount = quoteCount;
	}

	@JsonProperty("incoming_link_count")
	public Integer getIncomingLinkCount() {
		return incomingLinkCount;
	}

	@JsonProperty("incoming_link_count")
	public void setIncomingLinkCount(Integer incomingLinkCount) {
		this.incomingLinkCount = incomingLinkCount;
	}

	@JsonProperty("like_count")
	public Integer getLikeCount() {
		return likeCount;
	}

	@JsonProperty("like_count")
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	@JsonProperty("current_user_like")
	public Boolean getCurrentUserLike() {
		return currentUserLike;
	}

	@JsonProperty("current_user_like")
	public void setCurrentUserLike(Boolean currentUserLike) {
		this.currentUserLike = currentUserLike;
	}

	@JsonProperty("reads")
	public Integer getReads() {
		return reads;
	}

	@JsonProperty("reads")
	public void setReads(Integer reads) {
		this.reads = reads;
	}

	@JsonProperty("readers_count")
	public Integer getReadersCount() {
		return readersCount;
	}

	@JsonProperty("readers_count")
	public void setReadersCount(Integer readersCount) {
		this.readersCount = readersCount;
	}

	@JsonProperty("score")
	public Integer getScore() {
		return score;
	}

	@JsonProperty("score")
	public void setScore(Integer score) {
		this.score = score;
	}

	@JsonProperty("yours")
	public Boolean getYours() {
		return yours;
	}

	@JsonProperty("yours")
	public void setYours(Boolean yours) {
		this.yours = yours;
	}

	@JsonProperty("topic_id")
	public Long getTopicId() {
		return topicId;
	}

	@JsonProperty("topic_id")
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	@JsonProperty("topic_slug")
	public String getTopicSlug() {
		return topicSlug;
	}

	@JsonProperty("topic_slug")
	public void setTopicSlug(String topicSlug) {
		this.topicSlug = topicSlug;
	}

	@JsonProperty("display_username")
	public String getDisplayUsername() {
		return displayUsername;
	}

	@JsonProperty("display_username")
	public void setDisplayUsername(String displayUsername) {
		this.displayUsername = displayUsername;
	}

	@JsonProperty("primary_group_name")
	public String getPrimaryGroupName() {
		return primaryGroupName;
	}

	@JsonProperty("primary_group_name")
	public void setPrimaryGroupName(String primaryGroupName) {
		this.primaryGroupName = primaryGroupName;
	}

	@JsonProperty("primary_group_flair_url")
	public String getPrimaryGroupFlairUrl() {
		return primaryGroupFlairUrl;
	}

	@JsonProperty("primary_group_flair_url")
	public void setPrimaryGroupFlairUrl(String primaryGroupFlairUrl) {
		this.primaryGroupFlairUrl = primaryGroupFlairUrl;
	}

	@JsonProperty("primary_group_flair_bg_color")
	public String getPrimaryGroupFlairBgColor() {
		return primaryGroupFlairBgColor;
	}

	@JsonProperty("primary_group_flair_bg_color")
	public void setPrimaryGroupFlairBgColor(String primaryGroupFlairBgColor) {
		this.primaryGroupFlairBgColor = primaryGroupFlairBgColor;
	}

	@JsonProperty("primary_group_flair_color")
	public String getPrimaryGroupFlairColor() {
		return primaryGroupFlairColor;
	}

	@JsonProperty("primary_group_flair_color")
	public void setPrimaryGroupFlairColor(String primaryGroupFlairColor) {
		this.primaryGroupFlairColor = primaryGroupFlairColor;
	}

	@JsonProperty("version")
	public Integer getVersion() {
		return version;
	}

	@JsonProperty("version")
	public void setVersion(Integer version) {
		this.version = version;
	}

	@JsonProperty("can_edit")
	public Boolean getCanEdit() {
		return canEdit;
	}

	@JsonProperty("can_edit")
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	@JsonProperty("can_delete")
	public Boolean getCanDelete() {
		return canDelete;
	}

	@JsonProperty("can_delete")
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	@JsonProperty("can_recover")
	public Boolean getCanRecover() {
		return canRecover;
	}

	@JsonProperty("can_recover")
	public void setCanRecover(Boolean canRecover) {
		this.canRecover = canRecover;
	}

	@JsonProperty("can_wiki")
	public Boolean getCanWiki() {
		return canWiki;
	}

	@JsonProperty("can_wiki")
	public void setCanWiki(Boolean canWiki) {
		this.canWiki = canWiki;
	}

	@JsonProperty("user_title")
	public String getUserTitle() {
		return userTitle;
	}

	@JsonProperty("user_title")
	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	@JsonProperty("actions_summary")
	public List<ActionSummaryOfTopic> getActionsSummary() {
		return actionsSummary;
	}

	@JsonProperty("actions_summary")
	public void setActionsSummary(List<ActionSummaryOfTopic> actionsSummary) {
		this.actionsSummary = actionsSummary;
	}

	@JsonProperty("moderator")
	public Boolean getModerator() {
		return moderator;
	}

	@JsonProperty("moderator")
	public void setModerator(Boolean moderator) {
		this.moderator = moderator;
	}

	@JsonProperty("admin")
	public Boolean getAdmin() {
		return admin;
	}

	@JsonProperty("admin")
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	@JsonProperty("staff")
	public Boolean getStaff() {
		return staff;
	}

	@JsonProperty("staff")
	public void setStaff(Boolean staff) {
		this.staff = staff;
	}

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("draft_sequence")
	public Integer getDraftSequence() {
		return draftSequence;
	}

	@JsonProperty("draft_sequence")
	public void setDraftSequence(Integer draftSequence) {
		this.draftSequence = draftSequence;
	}

	@JsonProperty("hidden")
	public Boolean getHidden() {
		return hidden;
	}

	@JsonProperty("hidden")
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@JsonProperty("trust_level")
	public Integer getTrustLevel() {
		return trustLevel;
	}

	@JsonProperty("trust_level")
	public void setTrustLevel(Integer trustLevel) {
		this.trustLevel = trustLevel;
	}

	@JsonProperty("deleted_at")
	public String getDeletedAt() {
		return deletedAt;
	}

	@JsonProperty("deleted_at")
	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	@JsonProperty("user_deleted")
	public Boolean getUserDeleted() {
		return userDeleted;
	}

	@JsonProperty("user_deleted")
	public void setUserDeleted(Boolean userDeleted) {
		this.userDeleted = userDeleted;
	}

	@JsonProperty("edit_reason")
	public String getEditReason() {
		return editReason;
	}

	@JsonProperty("edit_reason")
	public void setEditReason(String editReason) {
		this.editReason = editReason;
	}

	@JsonProperty("can_view_edit_history")
	public Boolean getCanViewEditHistory() {
		return canViewEditHistory;
	}

	@JsonProperty("can_view_edit_history")
	public void setCanViewEditHistory(Boolean canViewEditHistory) {
		this.canViewEditHistory = canViewEditHistory;
	}

	@JsonProperty("wiki")
	public Boolean getWiki() {
		return wiki;
	}

	@JsonProperty("wiki")
	public void setWiki(Boolean wiki) {
		this.wiki = wiki;
	}

	@JsonProperty("reviewable_id")
	public String getReviewableId() {
		return reviewableId;
	}

	@JsonProperty("reviewable_id")
	public void setReviewableId(String reviewableId) {
		this.reviewableId = reviewableId;
	}

	@JsonProperty("reviewable_score_count")
	public Integer getReviewableScoreCount() {
		return reviewableScoreCount;
	}

	@JsonProperty("reviewable_score_count")
	public void setReviewableScoreCount(Integer reviewableScoreCount) {
		this.reviewableScoreCount = reviewableScoreCount;
	}

	@JsonProperty("reviewable_score_pending_count")
	public Integer getReviewableScorePendingCount() {
		return reviewableScorePendingCount;
	}

	@JsonProperty("reviewable_score_pending_count")
	public void setReviewableScorePendingCount(Integer reviewableScorePendingCount) {
		this.reviewableScorePendingCount = reviewableScorePendingCount;
	}

	@JsonProperty("category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	@JsonProperty("category_id")
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@JsonProperty("last_posted_at")
	public Instant getLastPostedAt() {
		return lastPostedAt;
	}

	@JsonProperty("last_posted_at")
	public void setLastPostedAt(Instant lastPostedAt) {
		this.lastPostedAt = lastPostedAt;
	}

	@JsonProperty("tags")
	public String getTags() {
		return tags;
	}

	@JsonProperty("tags")
	public void setTags(String tags) {
		this.tags = tags;
	}

	@JsonProperty("arche_type")
	public String getArchetype() {
		return archetype;
	}

	@JsonProperty("arche_type")
	public void setArchetype(String archetype) {
		this.archetype = archetype;
	}

}
