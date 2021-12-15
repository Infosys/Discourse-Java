/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCategoryRequest {

	@JsonProperty("name")
	@NotNull
	private String name;
	@JsonProperty("color")
	private String color;
	@JsonProperty("text_color")
	private String textColor;
	@JsonProperty("description")
	private String description;

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("color")
	public String getColor() {
		return color;
	}

	@JsonProperty("color")
	public void setColor(String color) {
		this.color = color;
	}

	@JsonProperty("text_color")
	public String getTextColor() {
		return textColor;
	}

	@JsonProperty("text_color")
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
}
