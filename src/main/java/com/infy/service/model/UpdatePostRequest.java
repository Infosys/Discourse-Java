/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "post" })
public class UpdatePostRequest {

	@JsonProperty("post")
	@NotNull
	@Valid
	private UpdatedPost updatedPost;

	@JsonProperty("post")
	public UpdatedPost getUpdatedPost() {
		return updatedPost;
	}

	@JsonProperty("post")
	public void setUpdatedPost(UpdatedPost updatedPost) {
		this.updatedPost = updatedPost;
	}

}
