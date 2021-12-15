/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTopicRequest {

	@JsonProperty("title")
	private String title;
	@JsonProperty("category_id")
	private Long categoryId;
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

	@JsonProperty("category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	@JsonProperty("category_id")
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
