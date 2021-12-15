/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.UserOptions} entity.
 */
public class UserOptionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Boolean mailingListMode;

    private Boolean emailDigests;

    @NotNull
    private Boolean externalLinksInNewTab;

    @NotNull
    private Boolean enableQuoting;

    @NotNull
    private Boolean dynamicFavicon;

    @NotNull
    private Boolean disableJumpReply;

    @NotNull
    private Boolean automaticallyUnpinTopics;

    private Integer digestAfterMinutes;

    private Integer autoTrackTopicsAfterMsecs;

    private Integer newTopicDurationMinutes;

    private Instant lastRedirectedToTopAt;

    @NotNull
    private Integer emailPreviousReplies;

    @NotNull
    private Boolean emailInReplyTo;

    @NotNull
    private Integer likeNotificationFrequency;

    @NotNull
    private Integer mailingListModeFrequency;

    private Boolean includeTl0InDigests;

    private Integer notificationLevelWhenReplying;

    @NotNull
    private Integer themeKeySeq;

    @NotNull
    private Boolean allowPrivateMessages;

    private Long homepageId;

    @NotNull
    private Long themeIds;

    @NotNull
    private Boolean hideProfileAndPresence;

    @NotNull
    private Integer textSizeKey;

    @NotNull
    private Integer textSizeSeq;

    @NotNull
    private Integer emailLevel;

    @NotNull
    private Integer emailMessagesLevel;

    @NotNull
    private Integer titleCountModeKey;

    @NotNull
    private Boolean enableDefer;

    private String timezone;

    @NotNull
    private Boolean enableAllowedPmUsers;

    private Long darkSchemeId;

    @NotNull
    private Boolean skipNewUserTips;

    private Long colorSchemeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isMailingListMode() {
        return mailingListMode;
    }

    public void setMailingListMode(Boolean mailingListMode) {
        this.mailingListMode = mailingListMode;
    }

    public Boolean isEmailDigests() {
        return emailDigests;
    }

    public void setEmailDigests(Boolean emailDigests) {
        this.emailDigests = emailDigests;
    }

    public Boolean isExternalLinksInNewTab() {
        return externalLinksInNewTab;
    }

    public void setExternalLinksInNewTab(Boolean externalLinksInNewTab) {
        this.externalLinksInNewTab = externalLinksInNewTab;
    }

    public Boolean isEnableQuoting() {
        return enableQuoting;
    }

    public void setEnableQuoting(Boolean enableQuoting) {
        this.enableQuoting = enableQuoting;
    }

    public Boolean isDynamicFavicon() {
        return dynamicFavicon;
    }

    public void setDynamicFavicon(Boolean dynamicFavicon) {
        this.dynamicFavicon = dynamicFavicon;
    }

    public Boolean isDisableJumpReply() {
        return disableJumpReply;
    }

    public void setDisableJumpReply(Boolean disableJumpReply) {
        this.disableJumpReply = disableJumpReply;
    }

    public Boolean isAutomaticallyUnpinTopics() {
        return automaticallyUnpinTopics;
    }

    public void setAutomaticallyUnpinTopics(Boolean automaticallyUnpinTopics) {
        this.automaticallyUnpinTopics = automaticallyUnpinTopics;
    }

    public Integer getDigestAfterMinutes() {
        return digestAfterMinutes;
    }

    public void setDigestAfterMinutes(Integer digestAfterMinutes) {
        this.digestAfterMinutes = digestAfterMinutes;
    }

    public Integer getAutoTrackTopicsAfterMsecs() {
        return autoTrackTopicsAfterMsecs;
    }

    public void setAutoTrackTopicsAfterMsecs(Integer autoTrackTopicsAfterMsecs) {
        this.autoTrackTopicsAfterMsecs = autoTrackTopicsAfterMsecs;
    }

    public Integer getNewTopicDurationMinutes() {
        return newTopicDurationMinutes;
    }

    public void setNewTopicDurationMinutes(Integer newTopicDurationMinutes) {
        this.newTopicDurationMinutes = newTopicDurationMinutes;
    }

    public Instant getLastRedirectedToTopAt() {
        return lastRedirectedToTopAt;
    }

    public void setLastRedirectedToTopAt(Instant lastRedirectedToTopAt) {
        this.lastRedirectedToTopAt = lastRedirectedToTopAt;
    }

    public Integer getEmailPreviousReplies() {
        return emailPreviousReplies;
    }

    public void setEmailPreviousReplies(Integer emailPreviousReplies) {
        this.emailPreviousReplies = emailPreviousReplies;
    }

    public Boolean isEmailInReplyTo() {
        return emailInReplyTo;
    }

    public void setEmailInReplyTo(Boolean emailInReplyTo) {
        this.emailInReplyTo = emailInReplyTo;
    }

    public Integer getLikeNotificationFrequency() {
        return likeNotificationFrequency;
    }

    public void setLikeNotificationFrequency(Integer likeNotificationFrequency) {
        this.likeNotificationFrequency = likeNotificationFrequency;
    }

    public Integer getMailingListModeFrequency() {
        return mailingListModeFrequency;
    }

    public void setMailingListModeFrequency(Integer mailingListModeFrequency) {
        this.mailingListModeFrequency = mailingListModeFrequency;
    }

    public Boolean isIncludeTl0InDigests() {
        return includeTl0InDigests;
    }

    public void setIncludeTl0InDigests(Boolean includeTl0InDigests) {
        this.includeTl0InDigests = includeTl0InDigests;
    }

    public Integer getNotificationLevelWhenReplying() {
        return notificationLevelWhenReplying;
    }

    public void setNotificationLevelWhenReplying(Integer notificationLevelWhenReplying) {
        this.notificationLevelWhenReplying = notificationLevelWhenReplying;
    }

    public Integer getThemeKeySeq() {
        return themeKeySeq;
    }

    public void setThemeKeySeq(Integer themeKeySeq) {
        this.themeKeySeq = themeKeySeq;
    }

    public Boolean isAllowPrivateMessages() {
        return allowPrivateMessages;
    }

    public void setAllowPrivateMessages(Boolean allowPrivateMessages) {
        this.allowPrivateMessages = allowPrivateMessages;
    }

    public Long getHomepageId() {
        return homepageId;
    }

    public void setHomepageId(Long homepageId) {
        this.homepageId = homepageId;
    }

    public Long getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(Long themeIds) {
        this.themeIds = themeIds;
    }

    public Boolean isHideProfileAndPresence() {
        return hideProfileAndPresence;
    }

    public void setHideProfileAndPresence(Boolean hideProfileAndPresence) {
        this.hideProfileAndPresence = hideProfileAndPresence;
    }

    public Integer getTextSizeKey() {
        return textSizeKey;
    }

    public void setTextSizeKey(Integer textSizeKey) {
        this.textSizeKey = textSizeKey;
    }

    public Integer getTextSizeSeq() {
        return textSizeSeq;
    }

    public void setTextSizeSeq(Integer textSizeSeq) {
        this.textSizeSeq = textSizeSeq;
    }

    public Integer getEmailLevel() {
        return emailLevel;
    }

    public void setEmailLevel(Integer emailLevel) {
        this.emailLevel = emailLevel;
    }

    public Integer getEmailMessagesLevel() {
        return emailMessagesLevel;
    }

    public void setEmailMessagesLevel(Integer emailMessagesLevel) {
        this.emailMessagesLevel = emailMessagesLevel;
    }

    public Integer getTitleCountModeKey() {
        return titleCountModeKey;
    }

    public void setTitleCountModeKey(Integer titleCountModeKey) {
        this.titleCountModeKey = titleCountModeKey;
    }

    public Boolean isEnableDefer() {
        return enableDefer;
    }

    public void setEnableDefer(Boolean enableDefer) {
        this.enableDefer = enableDefer;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Boolean isEnableAllowedPmUsers() {
        return enableAllowedPmUsers;
    }

    public void setEnableAllowedPmUsers(Boolean enableAllowedPmUsers) {
        this.enableAllowedPmUsers = enableAllowedPmUsers;
    }

    public Long getDarkSchemeId() {
        return darkSchemeId;
    }

    public void setDarkSchemeId(Long darkSchemeId) {
        this.darkSchemeId = darkSchemeId;
    }

    public Boolean isSkipNewUserTips() {
        return skipNewUserTips;
    }

    public void setSkipNewUserTips(Boolean skipNewUserTips) {
        this.skipNewUserTips = skipNewUserTips;
    }

    public Long getColorSchemeId() {
        return colorSchemeId;
    }

    public void setColorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOptionsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserOptionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOptionsDTO{" +
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
