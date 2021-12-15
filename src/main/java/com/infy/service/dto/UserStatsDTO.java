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
 * A DTO for the {@link com.infy.domain.UserStats} entity.
 */
public class UserStatsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Integer topicsEntered;

    @NotNull
    private Integer timeRead;

    @NotNull
    private Integer daysVisited;

    @NotNull
    private Integer postsReadCount;

    @NotNull
    private Integer likesGiven;

    @NotNull
    private Integer likesReceived;

    @NotNull
    private Instant newSince;

    private Instant readFaq;

    private Instant firstPostCreatedAt;

    @NotNull
    private Integer postCount;

    @NotNull
    private Integer topicCount;

    @NotNull
    private Double bounceScore;

    private Instant resetBounceScoreAfter;

    @NotNull
    private Integer flagsAgreed;

    @NotNull
    private Integer flagsDisagreed;

    @NotNull
    private Integer flagsIgnored;

    @NotNull
    private Instant firstUnreadAt;

    @NotNull
    private Integer distinctBadgeCount;

    @NotNull
    private Instant firstUnreadPmAt;

    private Instant digestAttemptedAt;


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

    public Integer getTopicsEntered() {
        return topicsEntered;
    }

    public void setTopicsEntered(Integer topicsEntered) {
        this.topicsEntered = topicsEntered;
    }

    public Integer getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(Integer timeRead) {
        this.timeRead = timeRead;
    }

    public Integer getDaysVisited() {
        return daysVisited;
    }

    public void setDaysVisited(Integer daysVisited) {
        this.daysVisited = daysVisited;
    }

    public Integer getPostsReadCount() {
        return postsReadCount;
    }

    public void setPostsReadCount(Integer postsReadCount) {
        this.postsReadCount = postsReadCount;
    }

    public Integer getLikesGiven() {
        return likesGiven;
    }

    public void setLikesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
    }

    public Integer getLikesReceived() {
        return likesReceived;
    }

    public void setLikesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
    }

    public Instant getNewSince() {
        return newSince;
    }

    public void setNewSince(Instant newSince) {
        this.newSince = newSince;
    }

    public Instant getReadFaq() {
        return readFaq;
    }

    public void setReadFaq(Instant readFaq) {
        this.readFaq = readFaq;
    }

    public Instant getFirstPostCreatedAt() {
        return firstPostCreatedAt;
    }

    public void setFirstPostCreatedAt(Instant firstPostCreatedAt) {
        this.firstPostCreatedAt = firstPostCreatedAt;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Double getBounceScore() {
        return bounceScore;
    }

    public void setBounceScore(Double bounceScore) {
        this.bounceScore = bounceScore;
    }

    public Instant getResetBounceScoreAfter() {
        return resetBounceScoreAfter;
    }

    public void setResetBounceScoreAfter(Instant resetBounceScoreAfter) {
        this.resetBounceScoreAfter = resetBounceScoreAfter;
    }

    public Integer getFlagsAgreed() {
        return flagsAgreed;
    }

    public void setFlagsAgreed(Integer flagsAgreed) {
        this.flagsAgreed = flagsAgreed;
    }

    public Integer getFlagsDisagreed() {
        return flagsDisagreed;
    }

    public void setFlagsDisagreed(Integer flagsDisagreed) {
        this.flagsDisagreed = flagsDisagreed;
    }

    public Integer getFlagsIgnored() {
        return flagsIgnored;
    }

    public void setFlagsIgnored(Integer flagsIgnored) {
        this.flagsIgnored = flagsIgnored;
    }

    public Instant getFirstUnreadAt() {
        return firstUnreadAt;
    }

    public void setFirstUnreadAt(Instant firstUnreadAt) {
        this.firstUnreadAt = firstUnreadAt;
    }

    public Integer getDistinctBadgeCount() {
        return distinctBadgeCount;
    }

    public void setDistinctBadgeCount(Integer distinctBadgeCount) {
        this.distinctBadgeCount = distinctBadgeCount;
    }

    public Instant getFirstUnreadPmAt() {
        return firstUnreadPmAt;
    }

    public void setFirstUnreadPmAt(Instant firstUnreadPmAt) {
        this.firstUnreadPmAt = firstUnreadPmAt;
    }

    public Instant getDigestAttemptedAt() {
        return digestAttemptedAt;
    }

    public void setDigestAttemptedAt(Instant digestAttemptedAt) {
        this.digestAttemptedAt = digestAttemptedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserStatsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserStatsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserStatsDTO{" +
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
