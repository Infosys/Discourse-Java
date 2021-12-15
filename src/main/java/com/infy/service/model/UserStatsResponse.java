/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserStatsResponse {

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("topics_entered")
	private Integer topicsEntered;

	@JsonProperty("time_read")
	private Integer timeRead;

	@JsonProperty("days_visited")
	private Integer daysVisited;

	@JsonProperty("post_read_count")
	private Integer postsReadCount;

	@JsonProperty("likes_given")
	private Integer likesGiven;

	@JsonProperty("likes_received")
	private Integer likesReceived;

	@JsonProperty("new_since")
	private Instant newSince;

	@JsonProperty("read_faq")
	private Instant readFaq;

	@JsonProperty("first_post_created_at")
	private Instant firstPostCreatedAt;

	@JsonProperty("post_count")
	private Integer postCount;

	@JsonProperty("topic_count")
	private Integer topicCount;

	@JsonProperty("bounce_score")
	private Double bounceScore;

	@JsonProperty("reset_bounse_score_after")
	private Instant resetBounceScoreAfter;

	@JsonProperty("flags_agrred")
	private Integer flagsAgreed;

	@JsonProperty("flags_disagreed")
	private Integer flagsDisagreed;

	@JsonProperty("flags_ignored")
	private Integer flagsIgnored;

	@JsonProperty("first_unread_at")
	private Instant firstUnreadAt;

	@JsonProperty("distinct_badge_count")
	private Integer distinctBadgeCount;

	@JsonProperty("first_unread_pm_at")
	private Instant firstUnreadPmAt;

	@JsonProperty("digest_attempted_at")
	private Instant digestAttemptedAt;

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("topics_entered")
	public Integer getTopicsEntered() {
		return topicsEntered;
	}

	@JsonProperty("topics_entered")
	public void setTopicsEntered(Integer topicsEntered) {
		this.topicsEntered = topicsEntered;
	}

	@JsonProperty("time_read")
	public Integer getTimeRead() {
		return timeRead;
	}

	@JsonProperty("time_read")
	public void setTimeRead(Integer timeRead) {
		this.timeRead = timeRead;
	}

	@JsonProperty("days_visited")
	public Integer getDaysVisited() {
		return daysVisited;
	}

	@JsonProperty("days_visited")
	public void setDaysVisited(Integer daysVisited) {
		this.daysVisited = daysVisited;
	}

	@JsonProperty("post_read_count")
	public Integer getPostsReadCount() {
		return postsReadCount;
	}

	@JsonProperty("post_read_count")
	public void setPostsReadCount(Integer postsReadCount) {
		this.postsReadCount = postsReadCount;
	}

	@JsonProperty("likes_given")
	public Integer getLikesGiven() {
		return likesGiven;
	}

	@JsonProperty("likes_given")
	public void setLikesGiven(Integer likesGiven) {
		this.likesGiven = likesGiven;
	}

	@JsonProperty("likes_received")
	public Integer getLikesReceived() {
		return likesReceived;
	}

	@JsonProperty("likes_received")
	public void setLikesReceived(Integer likesReceived) {
		this.likesReceived = likesReceived;
	}

	@JsonProperty("new_since")
	public Instant getNewSince() {
		return newSince;
	}

	@JsonProperty("new_since")
	public void setNewSince(Instant newSince) {
		this.newSince = newSince;
	}

	@JsonProperty("read_faq")
	public Instant getReadFaq() {
		return readFaq;
	}

	@JsonProperty("read_faq")
	public void setReadFaq(Instant readFaq) {
		this.readFaq = readFaq;
	}

	@JsonProperty("first_post_created_at")
	public Instant getFirstPostCreatedAt() {
		return firstPostCreatedAt;
	}

	@JsonProperty("first_post_created_at")
	public void setFirstPostCreatedAt(Instant firstPostCreatedAt) {
		this.firstPostCreatedAt = firstPostCreatedAt;
	}

	@JsonProperty("post_count")
	public Integer getPostCount() {
		return postCount;
	}

	@JsonProperty("post_count")
	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}

	@JsonProperty("topic_count")
	public Integer getTopicCount() {
		return topicCount;
	}

	@JsonProperty("topic_count")
	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	@JsonProperty("bounce_score")
	public Double getBounceScore() {
		return bounceScore;
	}

	@JsonProperty("bounce_score")
	public void setBounceScore(Double bounceScore) {
		this.bounceScore = bounceScore;
	}

	@JsonProperty("reset_bounse_score_after")
	public Instant getResetBounceScoreAfter() {
		return resetBounceScoreAfter;
	}

	@JsonProperty("reset_bounse_score_after")
	public void setResetBounceScoreAfter(Instant resetBounceScoreAfter) {
		this.resetBounceScoreAfter = resetBounceScoreAfter;
	}

	@JsonProperty("flags_agrred")
	public Integer getFlagsAgreed() {
		return flagsAgreed;
	}

	@JsonProperty("flags_agrred")
	public void setFlagsAgreed(Integer flagsAgreed) {
		this.flagsAgreed = flagsAgreed;
	}

	@JsonProperty("flags_disagreed")
	public Integer getFlagsDisagreed() {
		return flagsDisagreed;
	}

	@JsonProperty("flags_disagreed")
	public void setFlagsDisagreed(Integer flagsDisagreed) {
		this.flagsDisagreed = flagsDisagreed;
	}

	@JsonProperty("flags_ignored")
	public Integer getFlagsIgnored() {
		return flagsIgnored;
	}

	@JsonProperty("flags_ignored")
	public void setFlagsIgnored(Integer flagsIgnored) {
		this.flagsIgnored = flagsIgnored;
	}

	@JsonProperty("first_unread_at")
	public Instant getFirstUnreadAt() {
		return firstUnreadAt;
	}

	@JsonProperty("first_unread_at")
	public void setFirstUnreadAt(Instant firstUnreadAt) {
		this.firstUnreadAt = firstUnreadAt;
	}

	@JsonProperty("distinct_badge_count")
	public Integer getDistinctBadgeCount() {
		return distinctBadgeCount;
	}

	@JsonProperty("distinct_badge_count")
	public void setDistinctBadgeCount(Integer distinctBadgeCount) {
		this.distinctBadgeCount = distinctBadgeCount;
	}

	@JsonProperty("first_unread_pm_at")
	public Instant getFirstUnreadPmAt() {
		return firstUnreadPmAt;
	}

	@JsonProperty("first_unread_pm_at")
	public void setFirstUnreadPmAt(Instant firstUnreadPmAt) {
		this.firstUnreadPmAt = firstUnreadPmAt;
	}

	@JsonProperty("digest_attempted_at")
	public Instant getDigestAttemptedAt() {
		return digestAttemptedAt;
	}

	@JsonProperty("digest_attempted_at")
	public void setDigestAttemptedAt(Instant digestAttemptedAt) {
		this.digestAttemptedAt = digestAttemptedAt;
	}

}
