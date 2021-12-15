/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "success", "category" })
public class UpdateCategoryResponse {

	@JsonProperty("success")
	private String success;
	@JsonProperty("category")
	private CategoryResponse category;

	@JsonProperty("success")
	public String getSuccess() {
		return success;
	}

	@JsonProperty("success")
	public void setSuccess(String success) {
		this.success = success;
	}

	@JsonProperty("category")
	public CategoryResponse getCategory() {
		return category;
	}

	@JsonProperty("category")
	public void setCategory(CategoryResponse category) {
		this.category = category;
	}

}
