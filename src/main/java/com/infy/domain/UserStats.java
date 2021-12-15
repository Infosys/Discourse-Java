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
 * A UserStats.
 */
@Entity
@Table(name = "user_stats")
public class UserStats extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "topics_entered", nullable = false)
    private Integer topicsEntered;

    @NotNull
    @Column(name = "time_read", nullable = false)
    private Integer timeRead;

    @NotNull
    @Column(name = "days_visited", nullable = false)
    private Integer daysVisited;

    @NotNull
    @Column(name = "posts_read_count", nullable = false)
    private Integer postsReadCount;

    @NotNull
    @Column(name = "likes_given", nullable = false)
    private Integer likesGiven;

    @NotNull
    @Column(name = "likes_received", nullable = false)
    private Integer likesReceived;

    @NotNull
    @Column(name = "new_since", nullable = false)
    private Instant newSince;

    @Column(name = "read_faq")
    private Instant readFaq;

    @Column(name = "first_post_created_at")
    private Instant firstPostCreatedAt;

    @NotNull
    @Column(name = "post_count", nullable = false)
    private Integer postCount;

    @NotNull
    @Column(name = "topic_count", nullable = false)
    private Integer topicCount;

    @NotNull
    @Column(name = "bounce_score", nullable = false)
    private Double bounceScore;

    @Column(name = "reset_bounce_score_after")
    private Instant resetBounceScoreAfter;

    @NotNull
    @Column(name = "flags_agreed", nullable = false)
    private Integer flagsAgreed;

    @NotNull
    @Column(name = "flags_disagreed", nullable = false)
    private Integer flagsDisagreed;

    @NotNull
    @Column(name = "flags_ignored", nullable = false)
    private Integer flagsIgnored;

    @NotNull
    @Column(name = "first_unread_at", nullable = false)
    private Instant firstUnreadAt;

    @NotNull
    @Column(name = "distinct_badge_count", nullable = false)
    private Integer distinctBadgeCount;

    @NotNull
    @Column(name = "first_unread_pm_at", nullable = false)
    private Instant firstUnreadPmAt;

    @Column(name = "digest_attempted_at")
    private Instant digestAttemptedAt;

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

    public UserStats userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTopicsEntered() {
        return topicsEntered;
    }

    public UserStats topicsEntered(Integer topicsEntered) {
        this.topicsEntered = topicsEntered;
        return this;
    }

    public void setTopicsEntered(Integer topicsEntered) {
        this.topicsEntered = topicsEntered;
    }

    public Integer getTimeRead() {
        return timeRead;
    }

    public UserStats timeRead(Integer timeRead) {
        this.timeRead = timeRead;
        return this;
    }

    public void setTimeRead(Integer timeRead) {
        this.timeRead = timeRead;
    }

    public Integer getDaysVisited() {
        return daysVisited;
    }

    public UserStats daysVisited(Integer daysVisited) {
        this.daysVisited = daysVisited;
        return this;
    }

    public void setDaysVisited(Integer daysVisited) {
        this.daysVisited = daysVisited;
    }

    public Integer getPostsReadCount() {
        return postsReadCount;
    }

    public UserStats postsReadCount(Integer postsReadCount) {
        this.postsReadCount = postsReadCount;
        return this;
    }

    public void setPostsReadCount(Integer postsReadCount) {
        this.postsReadCount = postsReadCount;
    }

    public Integer getLikesGiven() {
        return likesGiven;
    }

    public UserStats likesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
        return this;
    }

    public void setLikesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
    }

    public Integer getLikesReceived() {
        return likesReceived;
    }

    public UserStats likesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
        return this;
    }

    public void setLikesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
    }

    public Instant getNewSince() {
        return newSince;
    }

    public UserStats newSince(Instant newSince) {
        this.newSince = newSince;
        return this;
    }

    public void setNewSince(Instant newSince) {
        this.newSince = newSince;
    }

    public Instant getReadFaq() {
        return readFaq;
    }

    public UserStats readFaq(Instant readFaq) {
        this.readFaq = readFaq;
        return this;
    }

    public void setReadFaq(Instant readFaq) {
        this.readFaq = readFaq;
    }

    public Instant getFirstPostCreatedAt() {
        return firstPostCreatedAt;
    }

    public UserStats firstPostCreatedAt(Instant firstPostCreatedAt) {
        this.firstPostCreatedAt = firstPostCreatedAt;
        return this;
    }

    public void setFirstPostCreatedAt(Instant firstPostCreatedAt) {
        this.firstPostCreatedAt = firstPostCreatedAt;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public UserStats postCount(Integer postCount) {
        this.postCount = postCount;
        return this;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public UserStats topicCount(Integer topicCount) {
        this.topicCount = topicCount;
        return this;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Double getBounceScore() {
        return bounceScore;
    }

    public UserStats bounceScore(Double bounceScore) {
        this.bounceScore = bounceScore;
        return this;
    }

    public void setBounceScore(Double bounceScore) {
        this.bounceScore = bounceScore;
    }

    public Instant getResetBounceScoreAfter() {
        return resetBounceScoreAfter;
    }

    public UserStats resetBounceScoreAfter(Instant resetBounceScoreAfter) {
        this.resetBounceScoreAfter = resetBounceScoreAfter;
        return this;
    }

    public void setResetBounceScoreAfter(Instant resetBounceScoreAfter) {
        this.resetBounceScoreAfter = resetBounceScoreAfter;
    }

    public Integer getFlagsAgreed() {
        return flagsAgreed;
    }

    public UserStats flagsAgreed(Integer flagsAgreed) {
        this.flagsAgreed = flagsAgreed;
        return this;
    }

    public void setFlagsAgreed(Integer flagsAgreed) {
        this.flagsAgreed = flagsAgreed;
    }

    public Integer getFlagsDisagreed() {
        return flagsDisagreed;
    }

    public UserStats flagsDisagreed(Integer flagsDisagreed) {
        this.flagsDisagreed = flagsDisagreed;
        return this;
    }

    public void setFlagsDisagreed(Integer flagsDisagreed) {
        this.flagsDisagreed = flagsDisagreed;
    }

    public Integer getFlagsIgnored() {
        return flagsIgnored;
    }

    public UserStats flagsIgnored(Integer flagsIgnored) {
        this.flagsIgnored = flagsIgnored;
        return this;
    }

    public void setFlagsIgnored(Integer flagsIgnored) {
        this.flagsIgnored = flagsIgnored;
    }

    public Instant getFirstUnreadAt() {
        return firstUnreadAt;
    }

    public UserStats firstUnreadAt(Instant firstUnreadAt) {
        this.firstUnreadAt = firstUnreadAt;
        return this;
    }

    public void setFirstUnreadAt(Instant firstUnreadAt) {
        this.firstUnreadAt = firstUnreadAt;
    }

    public Integer getDistinctBadgeCount() {
        return distinctBadgeCount;
    }

    public UserStats distinctBadgeCount(Integer distinctBadgeCount) {
        this.distinctBadgeCount = distinctBadgeCount;
        return this;
    }

    public void setDistinctBadgeCount(Integer distinctBadgeCount) {
        this.distinctBadgeCount = distinctBadgeCount;
    }

    public Instant getFirstUnreadPmAt() {
        return firstUnreadPmAt;
    }

    public UserStats firstUnreadPmAt(Instant firstUnreadPmAt) {
        this.firstUnreadPmAt = firstUnreadPmAt;
        return this;
    }

    public void setFirstUnreadPmAt(Instant firstUnreadPmAt) {
        this.firstUnreadPmAt = firstUnreadPmAt;
    }

    public Instant getDigestAttemptedAt() {
        return digestAttemptedAt;
    }

    public UserStats digestAttemptedAt(Instant digestAttemptedAt) {
        this.digestAttemptedAt = digestAttemptedAt;
        return this;
    }

    public void setDigestAttemptedAt(Instant digestAttemptedAt) {
        this.digestAttemptedAt = digestAttemptedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserStats)) {
            return false;
        }
        return id != null && id.equals(((UserStats) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserStats{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", topicsEntered=" + getTopicsEntered() +
            ", timeRead=" + getTimeRead() +
            ", daysVisited=" + getDaysVisited() +
            ", postsReadCount=" + getPostsReadCount() +
            ", likesGiven=" + getLikesGiven() +
            ", likesReceived=" + getLikesReceived() +
            ", newSince='" + getNewSince() + "'" +
            ", readFaq='" + getReadFaq() + "'" +
            ", firstPostCreatedAt='" + getFirstPostCreatedAt() + "'" +
            ", postCount=" + getPostCount() +
            ", topicCount=" + getTopicCount() +
            ", bounceScore=" + getBounceScore() +
            ", resetBounceScoreAfter='" + getResetBounceScoreAfter() + "'" +
            ", flagsAgreed=" + getFlagsAgreed() +
            ", flagsDisagreed=" + getFlagsDisagreed() +
            ", flagsIgnored=" + getFlagsIgnored() +
            ", firstUnreadAt='" + getFirstUnreadAt() + "'" +
            ", distinctBadgeCount=" + getDistinctBadgeCount() +
            ", firstUnreadPmAt='" + getFirstUnreadPmAt() + "'" +
            ", digestAttemptedAt='" + getDigestAttemptedAt() + "'" +
            "}";
    }
}
