/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnouncementResponse {

	private Long id;
	private String title;
	private String raw;

	@JsonProperty("created_at")
	private Instant createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	@JsonProperty("created_at")
	public Instant getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "AnnouncementResponse [id=" + id + ", title=" + title + ", raw=" + raw + ", createdAt=" + createdAt
				+ "]";
	}
}
