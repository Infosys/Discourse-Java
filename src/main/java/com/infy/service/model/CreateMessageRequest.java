
/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infy.domain.enumeration.ArcheType;

public class CreateMessageRequest {

	@JsonProperty("title")
	private String title;
	@JsonProperty("topic_id")
	private Long topicId;
	@JsonProperty("raw")
	@NotNull
	private String raw;
	@JsonProperty("category")
	private Long category;
	@JsonProperty("target_usernames")
	private String targetUsernames;
	@JsonProperty("archetype")
	private ArcheType archetype;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("reply_to_post")
	private Long replyToPost;
	@JsonProperty("tags")
	private String tags;

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("topic_id")
	public Long getTopicId() {
		return topicId;
	}

	@JsonProperty("topic_id")
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	@JsonProperty("raw")
	public String getRaw() {
		return raw;
	}

	@JsonProperty("raw")
	public void setRaw(String raw) {
		this.raw = raw;
	}

	@JsonProperty("category")
	public Long getCategory() {
		return category;
	}

	@JsonProperty("category")
	public void setCategory(Long category) {
		this.category = category;
	}

	@JsonProperty("target_usernames")
	public String getTargetUsernames() {
		return targetUsernames;
	}

	@JsonProperty("target_usernames")
	public void setTargetUsernames(String targetUsernames) {
		this.targetUsernames = targetUsernames;
	}

	@JsonProperty("archetype")
	public ArcheType getArchetype() {
		return archetype;
	}

	@JsonProperty("archetype")
	public void setArchetype(ArcheType archetype) {
		this.archetype = archetype;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("reply_to_post")
	public Long getReplyToPost() {
		return replyToPost;
	}

	@JsonProperty("reply_to_post")
	public void setReplyToPost(Long replyToPost) {
		this.replyToPost = replyToPost;
	}

	@JsonProperty("tags")
	public String getTags() {
		return tags;
	}

	@JsonProperty("tags")
	public void setTags(String tags) {
		this.tags = tags;
	}
}
