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
 * A DTO for the {@link com.infy.domain.TopicUsers} entity.
 */
public class TopicUsersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Long topicId;

    @NotNull
    private Boolean posted;

    private Integer lastReadPostNumber;

    private Integer highestSeenPostNumber;

    private Instant lastVisitedAt;

    private Instant firstVisitedAt;

    @NotNull
    private Integer notificationLevel;

    private Instant notificationsChangedAt;

    private Long notificationsReasonId;

    @NotNull
    private Integer totalMsecsViewed;

    private Instant clearedPinnedAt;

    private Integer lastEmailedPostNumber;

    private Boolean liked;

    private Boolean bookmarked;

    private Instant lastPostedAt;


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

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean isPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public Integer getLastReadPostNumber() {
        return lastReadPostNumber;
    }

    public void setLastReadPostNumber(Integer lastReadPostNumber) {
        this.lastReadPostNumber = lastReadPostNumber;
    }

    public Integer getHighestSeenPostNumber() {
        return highestSeenPostNumber;
    }

    public void setHighestSeenPostNumber(Integer highestSeenPostNumber) {
        this.highestSeenPostNumber = highestSeenPostNumber;
    }

    public Instant getLastVisitedAt() {
        return lastVisitedAt;
    }

    public void setLastVisitedAt(Instant lastVisitedAt) {
        this.lastVisitedAt = lastVisitedAt;
    }

    public Instant getFirstVisitedAt() {
        return firstVisitedAt;
    }

    public void setFirstVisitedAt(Instant firstVisitedAt) {
        this.firstVisitedAt = firstVisitedAt;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public Instant getNotificationsChangedAt() {
        return notificationsChangedAt;
    }

    public void setNotificationsChangedAt(Instant notificationsChangedAt) {
        this.notificationsChangedAt = notificationsChangedAt;
    }

    public Long getNotificationsReasonId() {
        return notificationsReasonId;
    }

    public void setNotificationsReasonId(Long notificationsReasonId) {
        this.notificationsReasonId = notificationsReasonId;
    }

    public Integer getTotalMsecsViewed() {
        return totalMsecsViewed;
    }

    public void setTotalMsecsViewed(Integer totalMsecsViewed) {
        this.totalMsecsViewed = totalMsecsViewed;
    }

    public Instant getClearedPinnedAt() {
        return clearedPinnedAt;
    }

    public void setClearedPinnedAt(Instant clearedPinnedAt) {
        this.clearedPinnedAt = clearedPinnedAt;
    }

    public Integer getLastEmailedPostNumber() {
        return lastEmailedPostNumber;
    }

    public void setLastEmailedPostNumber(Integer lastEmailedPostNumber) {
        this.lastEmailedPostNumber = lastEmailedPostNumber;
    }

    public Boolean isLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Instant getLastPostedAt() {
        return lastPostedAt;
    }

    public void setLastPostedAt(Instant lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicUsersDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicUsersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicUsersDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", topicId=" + getTopicId() +
            ", posted='" + isPosted() + "'" +
            ", lastReadPostNumber=" + getLastReadPostNumber() +
            ", highestSeenPostNumber=" + getHighestSeenPostNumber() +
            ", lastVisitedAt='" + getLastVisitedAt() + "'" +
            ", firstVisitedAt='" + getFirstVisitedAt() + "'" +
            ", notificationLevel=" + getNotificationLevel() +
            ", notificationsChangedAt='" + getNotificationsChangedAt() + "'" +
            ", notificationsReasonId=" + getNotificationsReasonId() +
            ", totalMsecsViewed=" + getTotalMsecsViewed() +
            ", clearedPinnedAt='" + getClearedPinnedAt() + "'" +
            ", lastEmailedPostNumber=" + getLastEmailedPostNumber() +
            ", liked='" + isLiked() + "'" +
            ", bookmarked='" + isBookmarked() + "'" +
            ", lastPostedAt='" + getLastPostedAt() + "'" +
            "}";
    }
}
