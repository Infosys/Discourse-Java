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
 * A TopicUsers.
 */
@Entity
@Table(name = "topic_users")
public class TopicUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "posted", nullable = false)
    private Boolean posted;

    @Column(name = "last_read_post_number")
    private Integer lastReadPostNumber;

    @Column(name = "highest_seen_post_number")
    private Integer highestSeenPostNumber;

    @Column(name = "last_visited_at")
    private Instant lastVisitedAt;

    @Column(name = "first_visited_at")
    private Instant firstVisitedAt;

    @NotNull
    @Column(name = "notification_level", nullable = false)
    private Integer notificationLevel;

    @Column(name = "notifications_changed_at")
    private Instant notificationsChangedAt;

    @Column(name = "notifications_reason_id")
    private Long notificationsReasonId;

    @NotNull
    @Column(name = "total_msecs_viewed", nullable = false)
    private Integer totalMsecsViewed;

    @Column(name = "cleared_pinned_at")
    private Instant clearedPinnedAt;

    @Column(name = "last_emailed_post_number")
    private Integer lastEmailedPostNumber;

    @Column(name = "liked")
    private Boolean liked;

    @Column(name = "bookmarked")
    private Boolean bookmarked;

    @Column(name = "last_posted_at")
    private Instant lastPostedAt;

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

    public TopicUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public TopicUsers topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean isPosted() {
        return posted;
    }

    public TopicUsers posted(Boolean posted) {
        this.posted = posted;
        return this;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public Integer getLastReadPostNumber() {
        return lastReadPostNumber;
    }

    public TopicUsers lastReadPostNumber(Integer lastReadPostNumber) {
        this.lastReadPostNumber = lastReadPostNumber;
        return this;
    }

    public void setLastReadPostNumber(Integer lastReadPostNumber) {
        this.lastReadPostNumber = lastReadPostNumber;
    }

    public Integer getHighestSeenPostNumber() {
        return highestSeenPostNumber;
    }

    public TopicUsers highestSeenPostNumber(Integer highestSeenPostNumber) {
        this.highestSeenPostNumber = highestSeenPostNumber;
        return this;
    }

    public void setHighestSeenPostNumber(Integer highestSeenPostNumber) {
        this.highestSeenPostNumber = highestSeenPostNumber;
    }

    public Instant getLastVisitedAt() {
        return lastVisitedAt;
    }

    public TopicUsers lastVisitedAt(Instant lastVisitedAt) {
        this.lastVisitedAt = lastVisitedAt;
        return this;
    }

    public void setLastVisitedAt(Instant lastVisitedAt) {
        this.lastVisitedAt = lastVisitedAt;
    }

    public Instant getFirstVisitedAt() {
        return firstVisitedAt;
    }

    public TopicUsers firstVisitedAt(Instant firstVisitedAt) {
        this.firstVisitedAt = firstVisitedAt;
        return this;
    }

    public void setFirstVisitedAt(Instant firstVisitedAt) {
        this.firstVisitedAt = firstVisitedAt;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public TopicUsers notificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
        return this;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public Instant getNotificationsChangedAt() {
        return notificationsChangedAt;
    }

    public TopicUsers notificationsChangedAt(Instant notificationsChangedAt) {
        this.notificationsChangedAt = notificationsChangedAt;
        return this;
    }

    public void setNotificationsChangedAt(Instant notificationsChangedAt) {
        this.notificationsChangedAt = notificationsChangedAt;
    }

    public Long getNotificationsReasonId() {
        return notificationsReasonId;
    }

    public TopicUsers notificationsReasonId(Long notificationsReasonId) {
        this.notificationsReasonId = notificationsReasonId;
        return this;
    }

    public void setNotificationsReasonId(Long notificationsReasonId) {
        this.notificationsReasonId = notificationsReasonId;
    }

    public Integer getTotalMsecsViewed() {
        return totalMsecsViewed;
    }

    public TopicUsers totalMsecsViewed(Integer totalMsecsViewed) {
        this.totalMsecsViewed = totalMsecsViewed;
        return this;
    }

    public void setTotalMsecsViewed(Integer totalMsecsViewed) {
        this.totalMsecsViewed = totalMsecsViewed;
    }

    public Instant getClearedPinnedAt() {
        return clearedPinnedAt;
    }

    public TopicUsers clearedPinnedAt(Instant clearedPinnedAt) {
        this.clearedPinnedAt = clearedPinnedAt;
        return this;
    }

    public void setClearedPinnedAt(Instant clearedPinnedAt) {
        this.clearedPinnedAt = clearedPinnedAt;
    }

    public Integer getLastEmailedPostNumber() {
        return lastEmailedPostNumber;
    }

    public TopicUsers lastEmailedPostNumber(Integer lastEmailedPostNumber) {
        this.lastEmailedPostNumber = lastEmailedPostNumber;
        return this;
    }

    public void setLastEmailedPostNumber(Integer lastEmailedPostNumber) {
        this.lastEmailedPostNumber = lastEmailedPostNumber;
    }

    public Boolean isLiked() {
        return liked;
    }

    public TopicUsers liked(Boolean liked) {
        this.liked = liked;
        return this;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean isBookmarked() {
        return bookmarked;
    }

    public TopicUsers bookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
        return this;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Instant getLastPostedAt() {
        return lastPostedAt;
    }

    public TopicUsers lastPostedAt(Instant lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
        return this;
    }

    public void setLastPostedAt(Instant lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicUsers)) {
            return false;
        }
        return id != null && id.equals(((TopicUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicUsers{" +
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
