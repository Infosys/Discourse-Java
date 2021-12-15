/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatedTopic {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("fancy_title")
	private String fancyTitle;
	@JsonProperty("slug")
	private String slug;
	@JsonProperty("posts_count")
	private Integer postsCount;
	@JsonProperty("tags")
	private String tags;

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("fancy_title")
	public String getFancyTitle() {
		return fancyTitle;
	}

	@JsonProperty("fancy_title")
	public void setFancyTitle(String fancyTitle) {
		this.fancyTitle = fancyTitle;
	}

	@JsonProperty("slug")
	public String getSlug() {
		return slug;
	}

	@JsonProperty("slug")
	public void setSlug(String slug) {
		this.slug = slug;
	}

	@JsonProperty("posts_count")
	public Integer getPostsCount() {
		return postsCount;
	}

	@JsonProperty("posts_count")
	public void setPostsCount(Integer postsCount) {
		this.postsCount = postsCount;
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
