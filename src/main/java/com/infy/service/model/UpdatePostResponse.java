/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "post" })
public class UpdatePostResponse {

	@JsonProperty("post")
	private TopicOrPostResponse post;

	@JsonProperty("post")
	public TopicOrPostResponse getPost() {
		return post;
	}

	@JsonProperty("post")
	public void setPost(TopicOrPostResponse post) {
		this.post = post;
	}

}
