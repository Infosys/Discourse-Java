/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty("id")
	private Integer id;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("username")
	private String username;
	@JsonProperty("name")
	private String name;
	@JsonProperty("avatar_template")
	private String avatarTemplate;
	@JsonProperty("last_posted_at")
	private String lastPostedAt;
	@JsonProperty("last_seen_at")
	private String lastSeenAt;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("ignored")
	private Boolean ignored;
	@JsonProperty("muted")
	private Boolean muted;
	@JsonProperty("can_ignore_user")
	private Boolean canIgnoreUser;
	@JsonProperty("can_mute_user")
	private Boolean canMuteUser;
	@JsonProperty("can_send_private_messages")
	private Boolean canSendPrivateMessages;
	@JsonProperty("can_send_private_message_to_user")
	private Boolean canSendPrivateMessageToUser;
	@JsonProperty("trust_level")
	private Integer trustLevel;
	@JsonProperty("moderator")
	private Boolean moderator;
	@JsonProperty("admin")
	private Boolean admin;
	@JsonProperty("title")
	private String title;
	@JsonProperty("badge_count")
	private Integer badgeCount;
	@JsonProperty("time_read")
	private Integer timeRead;
	@JsonProperty("recent_time_read")
	private Integer recentTimeRead;
	@JsonProperty("primary_group_id")
	private String primaryGroupId;
	@JsonProperty("primary_group_name")
	private String primaryGroupName;
	@JsonProperty("primary_group_flair_url")
	private String primaryGroupFlairUrl;
	@JsonProperty("primary_group_flair_bg_color")
	private String primaryGroupFlairBgColor;
	@JsonProperty("primary_group_flair_color")
	private String primaryGroupFlairColor;
	@JsonProperty("featured_topic")
	private String featuredTopic;
	@JsonProperty("staged")
	private Boolean staged;
	@JsonProperty("can_edit")
	private Boolean canEdit;
	@JsonProperty("can_edit_username")
	private Boolean canEditUsername;
	@JsonProperty("can_edit_email")
	private Boolean canEditEmail;
	@JsonProperty("can_edit_name")
	private Boolean canEditName;
	@JsonProperty("uploaded_avatar_id")
	private String uploadedAvatarId;
	@JsonProperty("has_title_badges")
	private Boolean hasTitleBadges;
	@JsonProperty("pending_count")
	private Integer pendingCount;
	@JsonProperty("profile_view_count")
	private Integer profileViewCount;
	@JsonProperty("second_factor_enabled")
	private Boolean secondFactorEnabled;
	@JsonProperty("can_upload_profile_header")
	private Boolean canUploadProfileHeader;
	@JsonProperty("can_upload_user_card_background")
	private Boolean canUploadUserCardBackground;
	@JsonProperty("post_count")
	private Integer postCount;
	@JsonProperty("can_be_deleted")
	private Boolean canBeDeleted;
	@JsonProperty("can_delete_all_posts")
	private Boolean canDeleteAllPosts;
	@JsonProperty("locale")
	private String locale;
	@JsonProperty("muted_category_ids")
	private List<Object> mutedCategoryIds = null;
	@JsonProperty("regular_category_ids")
	private List<Object> regularCategoryIds = null;
	@JsonProperty("watched_tags")
	private List<Object> watchedTags = null;
	@JsonProperty("watching_first_post_tags")
	private List<Object> watchingFirstPostTags = null;
	@JsonProperty("tracked_tags")
	private List<Object> trackedTags = null;
	@JsonProperty("muted_tags")
	private List<Object> mutedTags = null;
	@JsonProperty("tracked_category_ids")
	private List<Object> trackedCategoryIds = null;
	@JsonProperty("watched_category_ids")
	private List<Object> watchedCategoryIds = null;
	@JsonProperty("watched_first_post_category_ids")
	private List<Object> watchedFirstPostCategoryIds = null;
	@JsonProperty("system_avatar_upload_id")
	private String systemAvatarUploadId;
	@JsonProperty("system_avatar_template")
	private String systemAvatarTemplate;
	@JsonProperty("muted_usernames")
	private List<Object> mutedUsernames = null;
	@JsonProperty("ignored_usernames")
	private List<Object> ignoredUsernames = null;
	@JsonProperty("allowed_pm_usernames")
	private List<Object> allowedPmUsernames = null;
	@JsonProperty("mailing_list_posts_per_day")
	private Integer mailingListPostsPerDay;
	@JsonProperty("can_change_bio")
	private Boolean canChangeBio;
	@JsonProperty("can_change_location")
	private Boolean canChangeLocation;
	@JsonProperty("can_change_website")
	private Boolean canChangeWebsite;
	@JsonProperty("user_api_keys")
	private String userApiKeys;
	@JsonProperty("featured_user_badge_ids")
	private List<Object> featuredUserBadgeIds = null;
	@JsonProperty("invited_by")
	private String invitedBy;
	@JsonProperty("privacy_accepted")
	private Boolean privacyAccepted;
	@JsonProperty("privacy_base_token")
	private String fireBaseToken;
	@JsonProperty("notification_subscription")
	private Boolean notificationSubscription;

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("avatar_template")
	public String getAvatarTemplate() {
		return avatarTemplate;
	}

	@JsonProperty("avatar_template")
	public void setAvatarTemplate(String avatarTemplate) {
		this.avatarTemplate = avatarTemplate;
	}

	@JsonProperty("last_posted_at")
	public String getLastPostedAt() {
		return lastPostedAt;
	}

	@JsonProperty("last_posted_at")
	public void setLastPostedAt(String lastPostedAt) {
		this.lastPostedAt = lastPostedAt;
	}

	@JsonProperty("last_seen_at")
	public String getLastSeenAt() {
		return lastSeenAt;
	}

	@JsonProperty("last_seen_at")
	public void setLastSeenAt(String lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("ignored")
	public Boolean getIgnored() {
		return ignored;
	}

	@JsonProperty("ignored")
	public void setIgnored(Boolean ignored) {
		this.ignored = ignored;
	}

	@JsonProperty("muted")
	public Boolean getMuted() {
		return muted;
	}

	@JsonProperty("muted")
	public void setMuted(Boolean muted) {
		this.muted = muted;
	}

	@JsonProperty("can_ignore_user")
	public Boolean getCanIgnoreUser() {
		return canIgnoreUser;
	}

	@JsonProperty("can_ignore_user")
	public void setCanIgnoreUser(Boolean canIgnoreUser) {
		this.canIgnoreUser = canIgnoreUser;
	}

	@JsonProperty("can_mute_user")
	public Boolean getCanMuteUser() {
		return canMuteUser;
	}

	@JsonProperty("can_mute_user")
	public void setCanMuteUser(Boolean canMuteUser) {
		this.canMuteUser = canMuteUser;
	}

	@JsonProperty("can_send_private_messages")
	public Boolean getCanSendPrivateMessages() {
		return canSendPrivateMessages;
	}

	@JsonProperty("can_send_private_messages")
	public void setCanSendPrivateMessages(Boolean canSendPrivateMessages) {
		this.canSendPrivateMessages = canSendPrivateMessages;
	}

	@JsonProperty("can_send_private_message_to_user")
	public Boolean getCanSendPrivateMessageToUser() {
		return canSendPrivateMessageToUser;
	}

	@JsonProperty("can_send_private_message_to_user")
	public void setCanSendPrivateMessageToUser(Boolean canSendPrivateMessageToUser) {
		this.canSendPrivateMessageToUser = canSendPrivateMessageToUser;
	}

	@JsonProperty("trust_level")
	public Integer getTrustLevel() {
		return trustLevel;
	}

	@JsonProperty("trust_level")
	public void setTrustLevel(Integer trustLevel) {
		this.trustLevel = trustLevel;
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

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("badge_count")
	public Integer getBadgeCount() {
		return badgeCount;
	}

	@JsonProperty("badge_count")
	public void setBadgeCount(Integer badgeCount) {
		this.badgeCount = badgeCount;
	}

	@JsonProperty("time_read")
	public Integer getTimeRead() {
		return timeRead;
	}

	@JsonProperty("time_read")
	public void setTimeRead(Integer timeRead) {
		this.timeRead = timeRead;
	}

	@JsonProperty("recent_time_read")
	public Integer getRecentTimeRead() {
		return recentTimeRead;
	}

	@JsonProperty("recent_time_read")
	public void setRecentTimeRead(Integer recentTimeRead) {
		this.recentTimeRead = recentTimeRead;
	}

	@JsonProperty("primary_group_id")
	public String getPrimaryGroupId() {
		return primaryGroupId;
	}

	@JsonProperty("primary_group_id")
	public void setPrimaryGroupId(String primaryGroupId) {
		this.primaryGroupId = primaryGroupId;
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

	@JsonProperty("featured_topic")
	public String getFeaturedTopic() {
		return featuredTopic;
	}

	@JsonProperty("featured_topic")
	public void setFeaturedTopic(String featuredTopic) {
		this.featuredTopic = featuredTopic;
	}

	@JsonProperty("staged")
	public Boolean getStaged() {
		return staged;
	}

	@JsonProperty("staged")
	public void setStaged(Boolean staged) {
		this.staged = staged;
	}

	@JsonProperty("can_edit")
	public Boolean getCanEdit() {
		return canEdit;
	}

	@JsonProperty("can_edit")
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	@JsonProperty("can_edit_username")
	public Boolean getCanEditUsername() {
		return canEditUsername;
	}

	@JsonProperty("can_edit_username")
	public void setCanEditUsername(Boolean canEditUsername) {
		this.canEditUsername = canEditUsername;
	}

	@JsonProperty("can_edit_email")
	public Boolean getCanEditEmail() {
		return canEditEmail;
	}

	@JsonProperty("can_edit_email")
	public void setCanEditEmail(Boolean canEditEmail) {
		this.canEditEmail = canEditEmail;
	}

	@JsonProperty("can_edit_name")
	public Boolean getCanEditName() {
		return canEditName;
	}

	@JsonProperty("can_edit_name")
	public void setCanEditName(Boolean canEditName) {
		this.canEditName = canEditName;
	}

	@JsonProperty("uploaded_avatar_id")
	public String getUploadedAvatarId() {
		return uploadedAvatarId;
	}

	@JsonProperty("uploaded_avatar_id")
	public void setUploadedAvatarId(String uploadedAvatarId) {
		this.uploadedAvatarId = uploadedAvatarId;
	}

	@JsonProperty("has_title_badges")
	public Boolean getHasTitleBadges() {
		return hasTitleBadges;
	}

	@JsonProperty("has_title_badges")
	public void setHasTitleBadges(Boolean hasTitleBadges) {
		this.hasTitleBadges = hasTitleBadges;
	}

	@JsonProperty("pending_count")
	public Integer getPendingCount() {
		return pendingCount;
	}

	@JsonProperty("pending_count")
	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}

	@JsonProperty("profile_view_count")
	public Integer getProfileViewCount() {
		return profileViewCount;
	}

	@JsonProperty("profile_view_count")
	public void setProfileViewCount(Integer profileViewCount) {
		this.profileViewCount = profileViewCount;
	}

	@JsonProperty("second_factor_enabled")
	public Boolean getSecondFactorEnabled() {
		return secondFactorEnabled;
	}

	@JsonProperty("second_factor_enabled")
	public void setSecondFactorEnabled(Boolean secondFactorEnabled) {
		this.secondFactorEnabled = secondFactorEnabled;
	}

	@JsonProperty("can_upload_profile_header")
	public Boolean getCanUploadProfileHeader() {
		return canUploadProfileHeader;
	}

	@JsonProperty("can_upload_profile_header")
	public void setCanUploadProfileHeader(Boolean canUploadProfileHeader) {
		this.canUploadProfileHeader = canUploadProfileHeader;
	}

	@JsonProperty("can_upload_user_card_background")
	public Boolean getCanUploadUserCardBackground() {
		return canUploadUserCardBackground;
	}

	@JsonProperty("can_upload_user_card_background")
	public void setCanUploadUserCardBackground(Boolean canUploadUserCardBackground) {
		this.canUploadUserCardBackground = canUploadUserCardBackground;
	}

	@JsonProperty("post_count")
	public Integer getPostCount() {
		return postCount;
	}

	@JsonProperty("post_count")
	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}

	@JsonProperty("can_be_deleted")
	public Boolean getCanBeDeleted() {
		return canBeDeleted;
	}

	@JsonProperty("can_be_deleted")
	public void setCanBeDeleted(Boolean canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
	}

	@JsonProperty("can_delete_all_posts")
	public Boolean getCanDeleteAllPosts() {
		return canDeleteAllPosts;
	}

	@JsonProperty("can_delete_all_posts")
	public void setCanDeleteAllPosts(Boolean canDeleteAllPosts) {
		this.canDeleteAllPosts = canDeleteAllPosts;
	}

	@JsonProperty("locale")
	public String getLocale() {
		return locale;
	}

	@JsonProperty("locale")
	public void setLocale(String locale) {
		this.locale = locale;
	}

	@JsonProperty("muted_category_ids")
	public List<Object> getMutedCategoryIds() {
		return mutedCategoryIds;
	}

	@JsonProperty("muted_category_ids")
	public void setMutedCategoryIds(List<Object> mutedCategoryIds) {
		this.mutedCategoryIds = mutedCategoryIds;
	}

	@JsonProperty("regular_category_ids")
	public List<Object> getRegularCategoryIds() {
		return regularCategoryIds;
	}

	@JsonProperty("regular_category_ids")
	public void setRegularCategoryIds(List<Object> regularCategoryIds) {
		this.regularCategoryIds = regularCategoryIds;
	}

	@JsonProperty("watched_tags")
	public List<Object> getWatchedTags() {
		return watchedTags;
	}

	@JsonProperty("watched_tags")
	public void setWatchedTags(List<Object> watchedTags) {
		this.watchedTags = watchedTags;
	}

	@JsonProperty("watching_first_post_tags")
	public List<Object> getWatchingFirstPostTags() {
		return watchingFirstPostTags;
	}

	@JsonProperty("watching_first_post_tags")
	public void setWatchingFirstPostTags(List<Object> watchingFirstPostTags) {
		this.watchingFirstPostTags = watchingFirstPostTags;
	}

	@JsonProperty("tracked_tags")
	public List<Object> getTrackedTags() {
		return trackedTags;
	}

	@JsonProperty("tracked_tags")
	public void setTrackedTags(List<Object> trackedTags) {
		this.trackedTags = trackedTags;
	}

	@JsonProperty("muted_tags")
	public List<Object> getMutedTags() {
		return mutedTags;
	}

	@JsonProperty("muted_tags")
	public void setMutedTags(List<Object> mutedTags) {
		this.mutedTags = mutedTags;
	}

	@JsonProperty("tracked_category_ids")
	public List<Object> getTrackedCategoryIds() {
		return trackedCategoryIds;
	}

	@JsonProperty("tracked_category_ids")
	public void setTrackedCategoryIds(List<Object> trackedCategoryIds) {
		this.trackedCategoryIds = trackedCategoryIds;
	}

	@JsonProperty("watched_category_ids")
	public List<Object> getWatchedCategoryIds() {
		return watchedCategoryIds;
	}

	@JsonProperty("watched_category_ids")
	public void setWatchedCategoryIds(List<Object> watchedCategoryIds) {
		this.watchedCategoryIds = watchedCategoryIds;
	}

	@JsonProperty("watched_first_post_category_ids")
	public List<Object> getWatchedFirstPostCategoryIds() {
		return watchedFirstPostCategoryIds;
	}

	@JsonProperty("watched_first_post_category_ids")
	public void setWatchedFirstPostCategoryIds(List<Object> watchedFirstPostCategoryIds) {
		this.watchedFirstPostCategoryIds = watchedFirstPostCategoryIds;
	}

	@JsonProperty("system_avatar_upload_id")
	public String getSystemAvatarUploadId() {
		return systemAvatarUploadId;
	}

	@JsonProperty("system_avatar_upload_id")
	public void setSystemAvatarUploadId(String systemAvatarUploadId) {
		this.systemAvatarUploadId = systemAvatarUploadId;
	}

	@JsonProperty("system_avatar_template")
	public String getSystemAvatarTemplate() {
		return systemAvatarTemplate;
	}

	@JsonProperty("system_avatar_template")
	public void setSystemAvatarTemplate(String systemAvatarTemplate) {
		this.systemAvatarTemplate = systemAvatarTemplate;
	}

	@JsonProperty("muted_usernames")
	public List<Object> getMutedUsernames() {
		return mutedUsernames;
	}

	@JsonProperty("muted_usernames")
	public void setMutedUsernames(List<Object> mutedUsernames) {
		this.mutedUsernames = mutedUsernames;
	}

	@JsonProperty("ignored_usernames")
	public List<Object> getIgnoredUsernames() {
		return ignoredUsernames;
	}

	@JsonProperty("ignored_usernames")
	public void setIgnoredUsernames(List<Object> ignoredUsernames) {
		this.ignoredUsernames = ignoredUsernames;
	}

	@JsonProperty("allowed_pm_usernames")
	public List<Object> getAllowedPmUsernames() {
		return allowedPmUsernames;
	}

	@JsonProperty("allowed_pm_usernames")
	public void setAllowedPmUsernames(List<Object> allowedPmUsernames) {
		this.allowedPmUsernames = allowedPmUsernames;
	}

	@JsonProperty("mailing_list_posts_per_day")
	public Integer getMailingListPostsPerDay() {
		return mailingListPostsPerDay;
	}

	@JsonProperty("mailing_list_posts_per_day")
	public void setMailingListPostsPerDay(Integer mailingListPostsPerDay) {
		this.mailingListPostsPerDay = mailingListPostsPerDay;
	}

	@JsonProperty("can_change_bio")
	public Boolean getCanChangeBio() {
		return canChangeBio;
	}

	@JsonProperty("can_change_bio")
	public void setCanChangeBio(Boolean canChangeBio) {
		this.canChangeBio = canChangeBio;
	}

	@JsonProperty("can_change_location")
	public Boolean getCanChangeLocation() {
		return canChangeLocation;
	}

	@JsonProperty("can_change_location")
	public void setCanChangeLocation(Boolean canChangeLocation) {
		this.canChangeLocation = canChangeLocation;
	}

	@JsonProperty("can_change_website")
	public Boolean getCanChangeWebsite() {
		return canChangeWebsite;
	}

	@JsonProperty("can_change_website")
	public void setCanChangeWebsite(Boolean canChangeWebsite) {
		this.canChangeWebsite = canChangeWebsite;
	}

	@JsonProperty("user_api_keys")
	public String getUserApiKeys() {
		return userApiKeys;
	}

	@JsonProperty("user_api_keys")
	public void setUserApiKeys(String userApiKeys) {
		this.userApiKeys = userApiKeys;
	}

	@JsonProperty("featured_user_badge_ids")
	public List<Object> getFeaturedUserBadgeIds() {
		return featuredUserBadgeIds;
	}

	@JsonProperty("featured_user_badge_ids")
	public void setFeaturedUserBadgeIds(List<Object> featuredUserBadgeIds) {
		this.featuredUserBadgeIds = featuredUserBadgeIds;
	}

	@JsonProperty("invited_by")
	public String getInvitedBy() {
		return invitedBy;
	}

	@JsonProperty("invited_by")
	public void setInvitedBy(String invitedBy) {
		this.invitedBy = invitedBy;
	}

	@JsonProperty("privacy_accepted")
	public Boolean getPrivacyAccepted() {
		return privacyAccepted;
	}

	@JsonProperty("privacy_accepted")
	public void setPrivacyAccepted(Boolean privacyAccepted) {
		this.privacyAccepted = privacyAccepted;
	}

	@JsonProperty("privacy_base_token")
	public String getFireBaseToken() {
		return fireBaseToken;
	}

	@JsonProperty("privacy_base_token")
	public void setFireBaseToken(String fireBaseToken) {
		this.fireBaseToken = fireBaseToken;
	}

	@JsonProperty("notification_subscription")
	public Boolean getNotifiactionSubscription() {
		return notificationSubscription;
	}

	@JsonProperty("notification_subscription")
	public void setNotificationSubscription(Boolean notificationSubscription) {
		this.notificationSubscription = notificationSubscription;
	}
}
