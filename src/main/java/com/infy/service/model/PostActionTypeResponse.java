/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostActionTypeResponse {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name_key")
	private String nameKey;

	@JsonProperty("is_flag")
	private Boolean isFlag;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("position")
	private Integer position;

	@JsonProperty("score_bonus")
	private Double scoreBonus;

	@JsonProperty("reviewable_priority")
	private Integer reviewablePriority;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("created_by")
	private String createdBy;

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("name_key")
	public String getNameKey() {
		return nameKey;
	}

	@JsonProperty("name_key")
	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	@JsonProperty("is_flag")
	public Boolean isIsFlag() {
		return isFlag;
	}

	@JsonProperty("is_flag")
	public void setIsFlag(Boolean isFlag) {
		this.isFlag = isFlag;
	}

	@JsonProperty("icon")
	public String getIcon() {
		return icon;
	}

	@JsonProperty("icon")
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JsonProperty("position")
	public Integer getPosition() {
		return position;
	}

	@JsonProperty("position")
	public void setPosition(Integer position) {
		this.position = position;
	}

	@JsonProperty("score_bonus")
	public Double getScoreBonus() {
		return scoreBonus;
	}

	@JsonProperty("score_bonus")
	public void setScoreBonus(Double scoreBonus) {
		this.scoreBonus = scoreBonus;
	}

	@JsonProperty("reviewable_priority")
	public Integer getReviewablePriority() {
		return reviewablePriority;
	}

	@JsonProperty("reviewable_priority")
	public void setReviewablePriority(Integer reviewablePriority) {
		this.reviewablePriority = reviewablePriority;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	@JsonProperty("created_by")
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PostActionTypesDTO{" + "id=" + getId() + ", nameKey='" + getNameKey() + "'" + ", isFlag='" + isIsFlag()
				+ "'" + ", icon='" + getIcon() + "'" + ", position=" + getPosition() + ", scoreBonus=" + getScoreBonus()
				+ ", reviewablePriority=" + getReviewablePriority() + "}";
	}

}
