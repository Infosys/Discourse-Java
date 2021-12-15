/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "basic_topic" })
@Generated("jsonschema2pojo")
public class UpdateTopicResponse {

	@JsonProperty("basic_topic")
	private UpdatedTopic updatedTopic;

	@JsonProperty("basic_topic")
	public UpdatedTopic getUpdatedTopic() {
		return updatedTopic;
	}

	@JsonProperty("basic_topic")
	public void setUpdatedTopic(UpdatedTopic updatedTopic) {
		this.updatedTopic = updatedTopic;
	}

}
