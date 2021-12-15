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
import java.time.Instant;

/**
 * A UserOptions.
 */
@Entity
@Table(name = "user_options")
public class UserOptions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "mailing_list_mode", nullable = false)
    private Boolean mailingListMode;

    @Column(name = "email_digests")
    private Boolean emailDigests;

    @NotNull
    @Column(name = "external_links_in_new_tab", nullable = false)
    private Boolean externalLinksInNewTab;

    @NotNull
    @Column(name = "enable_quoting", nullable = false)
    private Boolean enableQuoting;

    @NotNull
    @Column(name = "dynamic_favicon", nullable = false)
    private Boolean dynamicFavicon;

    @NotNull
    @Column(name = "disable_jump_reply", nullable = false)
    private Boolean disableJumpReply;

    @NotNull
    @Column(name = "automatically_unpin_topics", nullable = false)
    private Boolean automaticallyUnpinTopics;

    @Column(name = "digest_after_minutes")
    private Integer digestAfterMinutes;

    @Column(name = "auto_track_topics_after_msecs")
    private Integer autoTrackTopicsAfterMsecs;

    @Column(name = "new_topic_duration_minutes")
    private Integer newTopicDurationMinutes;

    @Column(name = "last_redirected_to_top_at")
    private Instant lastRedirectedToTopAt;

    @NotNull
    @Column(name = "email_previous_replies", nullable = false)
    private Integer emailPreviousReplies;

    @NotNull
    @Column(name = "email_in_reply_to", nullable = false)
    private Boolean emailInReplyTo;

    @NotNull
    @Column(name = "like_notification_frequency", nullable = false)
    private Integer likeNotificationFrequency;

    @NotNull
    @Column(name = "mailing_list_mode_frequency", nullable = false)
    private Integer mailingListModeFrequency;

    @Column(name = "include_tl_0_in_digests")
    private Boolean includeTl0InDigests;

    @Column(name = "notification_level_when_replying")
    private Integer notificationLevelWhenReplying;

    @NotNull
    @Column(name = "theme_key_seq", nullable = false)
    private Integer themeKeySeq;

    @NotNull
    @Column(name = "allow_private_messages", nullable = false)
    private Boolean allowPrivateMessages;

    @Column(name = "homepage_id")
    private Long homepageId;

    @NotNull
    @Column(name = "theme_ids", nullable = false)
    private Long themeIds;

    @NotNull
    @Column(name = "hide_profile_and_presence", nullable = false)
    private Boolean hideProfileAndPresence;

    @NotNull
    @Column(name = "text_size_key", nullable = false)
    private Integer textSizeKey;

    @NotNull
    @Column(name = "text_size_seq", nullable = false)
    private Integer textSizeSeq;

    @NotNull
    @Column(name = "email_level", nullable = false)
    private Integer emailLevel;

    @NotNull
    @Column(name = "email_messages_level", nullable = false)
    private Integer emailMessagesLevel;

    @NotNull
    @Column(name = "title_count_mode_key", nullable = false)
    private Integer titleCountModeKey;

    @NotNull
    @Column(name = "enable_defer", nullable = false)
    private Boolean enableDefer;

    @Column(name = "timezone")
    private String timezone;

    @NotNull
    @Column(name = "enable_allowed_pm_users", nullable = false)
    private Boolean enableAllowedPmUsers;

    @Column(name = "dark_scheme_id")
    private Long darkSchemeId;

    @NotNull
    @Column(name = "skip_new_user_tips", nullable = false)
    private Boolean skipNewUserTips;

    @Column(name = "color_scheme_id")
    private Long colorSchemeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserOptions userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isMailingListMode() {
        return mailingListMode;
    }

    public UserOptions mailingListMode(Boolean mailingListMode) {
        this.mailingListMode = mailingListMode;
        return this;
    }

    public void setMailingListMode(Boolean mailingListMode) {
        this.mailingListMode = mailingListMode;
    }

    public Boolean isEmailDigests() {
        return emailDigests;
    }

    public UserOptions emailDigests(Boolean emailDigests) {
        this.emailDigests = emailDigests;
        return this;
    }

    public void setEmailDigests(Boolean emailDigests) {
        this.emailDigests = emailDigests;
    }

    public Boolean isExternalLinksInNewTab() {
        return externalLinksInNewTab;
    }

    public UserOptions externalLinksInNewTab(Boolean externalLinksInNewTab) {
        this.externalLinksInNewTab = externalLinksInNewTab;
        return this;
    }

    public void setExternalLinksInNewTab(Boolean externalLinksInNewTab) {
        this.externalLinksInNewTab = externalLinksInNewTab;
    }

    public Boolean isEnableQuoting() {
        return enableQuoting;
    }

    public UserOptions enableQuoting(Boolean enableQuoting) {
        this.enableQuoting = enableQuoting;
        return this;
    }

    public void setEnableQuoting(Boolean enableQuoting) {
        this.enableQuoting = enableQuoting;
    }

    public Boolean isDynamicFavicon() {
        return dynamicFavicon;
    }

    public UserOptions dynamicFavicon(Boolean dynamicFavicon) {
        this.dynamicFavicon = dynamicFavicon;
        return this;
    }

    public void setDynamicFavicon(Boolean dynamicFavicon) {
        this.dynamicFavicon = dynamicFavicon;
    }

    public Boolean isDisableJumpReply() {
        return disableJumpReply;
    }

    public UserOptions disableJumpReply(Boolean disableJumpReply) {
        this.disableJumpReply = disableJumpReply;
        return this;
    }

    public void setDisableJumpReply(Boolean disableJumpReply) {
        this.disableJumpReply = disableJumpReply;
    }

    public Boolean isAutomaticallyUnpinTopics() {
        return automaticallyUnpinTopics;
    }

    public UserOptions automaticallyUnpinTopics(Boolean automaticallyUnpinTopics) {
        this.automaticallyUnpinTopics = automaticallyUnpinTopics;
        return this;
    }

    public void setAutomaticallyUnpinTopics(Boolean automaticallyUnpinTopics) {
        this.automaticallyUnpinTopics = automaticallyUnpinTopics;
    }

    public Integer getDigestAfterMinutes() {
        return digestAfterMinutes;
    }

    public UserOptions digestAfterMinutes(Integer digestAfterMinutes) {
        this.digestAfterMinutes = digestAfterMinutes;
        return this;
    }

    public void setDigestAfterMinutes(Integer digestAfterMinutes) {
        this.digestAfterMinutes = digestAfterMinutes;
    }

    public Integer getAutoTrackTopicsAfterMsecs() {
        return autoTrackTopicsAfterMsecs;
    }

    public UserOptions autoTrackTopicsAfterMsecs(Integer autoTrackTopicsAfterMsecs) {
        this.autoTrackTopicsAfterMsecs = autoTrackTopicsAfterMsecs;
        return this;
    }

    public void setAutoTrackTopicsAfterMsecs(Integer autoTrackTopicsAfterMsecs) {
        this.autoTrackTopicsAfterMsecs = autoTrackTopicsAfterMsecs;
    }

    public Integer getNewTopicDurationMinutes() {
        return newTopicDurationMinutes;
    }

    public UserOptions newTopicDurationMinutes(Integer newTopicDurationMinutes) {
        this.newTopicDurationMinutes = newTopicDurationMinutes;
        return this;
    }

    public void setNewTopicDurationMinutes(Integer newTopicDurationMinutes) {
        this.newTopicDurationMinutes = newTopicDurationMinutes;
    }

    public Instant getLastRedirectedToTopAt() {
        return lastRedirectedToTopAt;
    }

    public UserOptions lastRedirectedToTopAt(Instant lastRedirectedToTopAt) {
        this.lastRedirectedToTopAt = lastRedirectedToTopAt;
        return this;
    }

    public void setLastRedirectedToTopAt(Instant lastRedirectedToTopAt) {
        this.lastRedirectedToTopAt = lastRedirectedToTopAt;
    }

    public Integer getEmailPreviousReplies() {
        return emailPreviousReplies;
    }

    public UserOptions emailPreviousReplies(Integer emailPreviousReplies) {
        this.emailPreviousReplies = emailPreviousReplies;
        return this;
    }

    public void setEmailPreviousReplies(Integer emailPreviousReplies) {
        this.emailPreviousReplies = emailPreviousReplies;
    }

    public Boolean isEmailInReplyTo() {
        return emailInReplyTo;
    }

    public UserOptions emailInReplyTo(Boolean emailInReplyTo) {
        this.emailInReplyTo = emailInReplyTo;
        return this;
    }

    public void setEmailInReplyTo(Boolean emailInReplyTo) {
        this.emailInReplyTo = emailInReplyTo;
    }

    public Integer getLikeNotificationFrequency() {
        return likeNotificationFrequency;
    }

    public UserOptions likeNotificationFrequency(Integer likeNotificationFrequency) {
        this.likeNotificationFrequency = likeNotificationFrequency;
        return this;
    }

    public void setLikeNotificationFrequency(Integer likeNotificationFrequency) {
        this.likeNotificationFrequency = likeNotificationFrequency;
    }

    public Integer getMailingListModeFrequency() {
        return mailingListModeFrequency;
    }

    public UserOptions mailingListModeFrequency(Integer mailingListModeFrequency) {
        this.mailingListModeFrequency = mailingListModeFrequency;
        return this;
    }

    public void setMailingListModeFrequency(Integer mailingListModeFrequency) {
        this.mailingListModeFrequency = mailingListModeFrequency;
    }

    public Boolean isIncludeTl0InDigests() {
        return includeTl0InDigests;
    }

    public UserOptions includeTl0InDigests(Boolean includeTl0InDigests) {
        this.includeTl0InDigests = includeTl0InDigests;
        return this;
    }

    public void setIncludeTl0InDigests(Boolean includeTl0InDigests) {
        this.includeTl0InDigests = includeTl0InDigests;
    }

    public Integer getNotificationLevelWhenReplying() {
        return notificationLevelWhenReplying;
    }

    public UserOptions notificationLevelWhenReplying(Integer notificationLevelWhenReplying) {
        this.notificationLevelWhenReplying = notificationLevelWhenReplying;
        return this;
    }

    public void setNotificationLevelWhenReplying(Integer notificationLevelWhenReplying) {
        this.notificationLevelWhenReplying = notificationLevelWhenReplying;
    }

    public Integer getThemeKeySeq() {
        return themeKeySeq;
    }

    public UserOptions themeKeySeq(Integer themeKeySeq) {
        this.themeKeySeq = themeKeySeq;
        return this;
    }

    public void setThemeKeySeq(Integer themeKeySeq) {
        this.themeKeySeq = themeKeySeq;
    }

    public Boolean isAllowPrivateMessages() {
        return allowPrivateMessages;
    }

    public UserOptions allowPrivateMessages(Boolean allowPrivateMessages) {
        this.allowPrivateMessages = allowPrivateMessages;
        return this;
    }

    public void setAllowPrivateMessages(Boolean allowPrivateMessages) {
        this.allowPrivateMessages = allowPrivateMessages;
    }

    public Long getHomepageId() {
        return homepageId;
    }

    public UserOptions homepageId(Long homepageId) {
        this.homepageId = homepageId;
        return this;
    }

    public void setHomepageId(Long homepageId) {
        this.homepageId = homepageId;
    }

    public Long getThemeIds() {
        return themeIds;
    }

    public UserOptions themeIds(Long themeIds) {
        this.themeIds = themeIds;
        return this;
    }

    public void setThemeIds(Long themeIds) {
        this.themeIds = themeIds;
    }

    public Boolean isHideProfileAndPresence() {
        return hideProfileAndPresence;
    }

    public UserOptions hideProfileAndPresence(Boolean hideProfileAndPresence) {
        this.hideProfileAndPresence = hideProfileAndPresence;
        return this;
    }

    public void setHideProfileAndPresence(Boolean hideProfileAndPresence) {
        this.hideProfileAndPresence = hideProfileAndPresence;
    }

    public Integer getTextSizeKey() {
        return textSizeKey;
    }

    public UserOptions textSizeKey(Integer textSizeKey) {
        this.textSizeKey = textSizeKey;
        return this;
    }

    public void setTextSizeKey(Integer textSizeKey) {
        this.textSizeKey = textSizeKey;
    }

    public Integer getTextSizeSeq() {
        return textSizeSeq;
    }

    public UserOptions textSizeSeq(Integer textSizeSeq) {
        this.textSizeSeq = textSizeSeq;
        return this;
    }

    public void setTextSizeSeq(Integer textSizeSeq) {
        this.textSizeSeq = textSizeSeq;
    }

    public Integer getEmailLevel() {
        return emailLevel;
    }

    public UserOptions emailLevel(Integer emailLevel) {
        this.emailLevel = emailLevel;
        return this;
    }

    public void setEmailLevel(Integer emailLevel) {
        this.emailLevel = emailLevel;
    }

    public Integer getEmailMessagesLevel() {
        return emailMessagesLevel;
    }

    public UserOptions emailMessagesLevel(Integer emailMessagesLevel) {
        this.emailMessagesLevel = emailMessagesLevel;
        return this;
    }

    public void setEmailMessagesLevel(Integer emailMessagesLevel) {
        this.emailMessagesLevel = emailMessagesLevel;
    }

    public Integer getTitleCountModeKey() {
        return titleCountModeKey;
    }

    public UserOptions titleCountModeKey(Integer titleCountModeKey) {
        this.titleCountModeKey = titleCountModeKey;
        return this;
    }

    public void setTitleCountModeKey(Integer titleCountModeKey) {
        this.titleCountModeKey = titleCountModeKey;
    }

    public Boolean isEnableDefer() {
        return enableDefer;
    }

    public UserOptions enableDefer(Boolean enableDefer) {
        this.enableDefer = enableDefer;
        return this;
    }

    public void setEnableDefer(Boolean enableDefer) {
        this.enableDefer = enableDefer;
    }

    public String getTimezone() {
        return timezone;
    }

    public UserOptions timezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Boolean isEnableAllowedPmUsers() {
        return enableAllowedPmUsers;
    }

    public UserOptions enableAllowedPmUsers(Boolean enableAllowedPmUsers) {
        this.enableAllowedPmUsers = enableAllowedPmUsers;
        return this;
    }

    public void setEnableAllowedPmUsers(Boolean enableAllowedPmUsers) {
        this.enableAllowedPmUsers = enableAllowedPmUsers;
    }

    public Long getDarkSchemeId() {
        return darkSchemeId;
    }

    public UserOptions darkSchemeId(Long darkSchemeId) {
        this.darkSchemeId = darkSchemeId;
        return this;
    }

    public void setDarkSchemeId(Long darkSchemeId) {
        this.darkSchemeId = darkSchemeId;
    }

    public Boolean isSkipNewUserTips() {
        return skipNewUserTips;
    }

    public UserOptions skipNewUserTips(Boolean skipNewUserTips) {
        this.skipNewUserTips = skipNewUserTips;
        return this;
    }

    public void setSkipNewUserTips(Boolean skipNewUserTips) {
        this.skipNewUserTips = skipNewUserTips;
    }

    public Long getColorSchemeId() {
        return colorSchemeId;
    }

    public UserOptions colorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
        return this;
    }

    public void setColorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOptions)) {
            return false;
        }
        return id != null && id.equals(((UserOptions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOptions{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", mailingListMode='" + isMailingListMode() + "'" +
            ", emailDigests='" + isEmailDigests() + "'" +
            ", externalLinksInNewTab='" + isExternalLinksInNewTab() + "'" +
            ", enableQuoting='" + isEnableQuoting() + "'" +
            ", dynamicFavicon='" + isDynamicFavicon() + "'" +
            ", disableJumpReply='" + isDisableJumpReply() + "'" +
            ", automaticallyUnpinTopics='" + isAutomaticallyUnpinTopics() + "'" +
            ", digestAfterMinutes=" + getDigestAfterMinutes() +
            ", autoTrackTopicsAfterMsecs=" + getAutoTrackTopicsAfterMsecs() +
            ", newTopicDurationMinutes=" + getNewTopicDurationMinutes() +
            ", lastRedirectedToTopAt='" + getLastRedirectedToTopAt() + "'" +
            ", emailPreviousReplies=" + getEmailPreviousReplies() +
            ", emailInReplyTo='" + isEmailInReplyTo() + "'" +
            ", likeNotificationFrequency=" + getLikeNotificationFrequency() +
            ", mailingListModeFrequency=" + getMailingListModeFrequency() +
            ", includeTl0InDigests='" + isIncludeTl0InDigests() + "'" +
            ", notificationLevelWhenReplying=" + getNotificationLevelWhenReplying() +
            ", themeKeySeq=" + getThemeKeySeq() +
            ", allowPrivateMessages='" + isAllowPrivateMessages() + "'" +
            ", homepageId=" + getHomepageId() +
            ", themeIds=" + getThemeIds() +
            ", hideProfileAndPresence='" + isHideProfileAndPresence() + "'" +
            ", textSizeKey=" + getTextSizeKey() +
            ", textSizeSeq=" + getTextSizeSeq() +
            ", emailLevel=" + getEmailLevel() +
            ", emailMessagesLevel=" + getEmailMessagesLevel() +
            ", titleCountModeKey=" + getTitleCountModeKey() +
            ", enableDefer='" + isEnableDefer() + "'" +
            ", timezone='" + getTimezone() + "'" +
            ", enableAllowedPmUsers='" + isEnableAllowedPmUsers() + "'" +
            ", darkSchemeId=" + getDarkSchemeId() +
            ", skipNewUserTips='" + isSkipNewUserTips() + "'" +
            ", colorSchemeId=" + getColorSchemeId() +
            "}";
    }
}
