/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePostActionRequest {

	@JsonProperty("id")
	@NotNull
	private Long id;

	@JsonProperty("post_action_type_id")
	@NotNull
	private Long postActionTypeId;

	@JsonProperty("flag_topic")
	private Boolean flagTopic;

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("post_action_type_id")
	public Long getPostActionTypeId() {
		return postActionTypeId;
	}

	@JsonProperty("post_action_type_id")
	public void setPostActionTypeId(Long postActionTypeId) {
		this.postActionTypeId = postActionTypeId;
	}

	@JsonProperty("flag_topic")
	public Boolean getFlagTopic() {
		return flagTopic;
	}

	@JsonProperty("flag_topic")
	public void setFlagTopic(Boolean flagTopic) {
		this.flagTopic = flagTopic;
	}

}
