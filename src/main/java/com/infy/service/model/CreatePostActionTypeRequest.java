/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePostActionTypeRequest {

	@NotNull
	@JsonProperty("name_key")
	private String nameKey;

	@NotNull
	@JsonProperty("is_flag")
	private Boolean isFlag;

	@JsonProperty("icon")
	private String icon;

	@NotNull
	@JsonProperty("position")
	private Integer position;

	@NotNull
	@JsonProperty("score_bonus")
	private Double scoreBonus;

	@NotNull
	@JsonProperty("reviewable_priority")
	private Integer reviewablePriority;

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

	// prettier-ignore
	@Override
	public String toString() {
		return "PostActionTypesDTO{" + "nameKey='" + getNameKey() + "'" + ", isFlag='" + isIsFlag() + "'" + ", icon='"
				+ getIcon() + "'" + ", position=" + getPosition() + ", scoreBonus=" + getScoreBonus()
				+ ", reviewablePriority=" + getReviewablePriority() + "}";
	}

}
