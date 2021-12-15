/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "can_create_category", "can_create_topic", "draft", "draft_key", "draft_sequence", "categoryResponses" })
public class CategoryList {

	@JsonProperty("can_create_category")
	private Boolean canCreateCategory;
	@JsonProperty("can_create_topic")
	private Boolean canCreateTopic;
	@JsonProperty("draft")
	private Object draft;
	@JsonProperty("draft_key")
	private String draftKey;
	@JsonProperty("draft_sequence")
	private Integer draftSequence;
	@JsonProperty("categoryResponses")
	private List<CategoryResponse> categoryResponses = null;

	@JsonProperty("can_create_category")
	public Boolean getCanCreateCategory() {
		return canCreateCategory;
	}

	@JsonProperty("can_create_category")
	public void setCanCreateCategory(Boolean canCreateCategory) {
		this.canCreateCategory = canCreateCategory;
	}

	@JsonProperty("can_create_topic")
	public Boolean getCanCreateTopic() {
		return canCreateTopic;
	}

	@JsonProperty("can_create_topic")
	public void setCanCreateTopic(Boolean canCreateTopic) {
		this.canCreateTopic = canCreateTopic;
	}

	@JsonProperty("draft")
	public Object getDraft() {
		return draft;
	}

	@JsonProperty("draft")
	public void setDraft(Object draft) {
		this.draft = draft;
	}

	@JsonProperty("draft_key")
	public String getDraftKey() {
		return draftKey;
	}

	@JsonProperty("draft_key")
	public void setDraftKey(String draftKey) {
		this.draftKey = draftKey;
	}

	@JsonProperty("draft_sequence")
	public Integer getDraftSequence() {
		return draftSequence;
	}

	@JsonProperty("draft_sequence")
	public void setDraftSequence(Integer draftSequence) {
		this.draftSequence = draftSequence;
	}

	@JsonProperty("categoryResponses")
	public List<CategoryResponse> getCategories() {
		return categoryResponses;
	}

	@JsonProperty("categoryResponses")
	public void setCategories(List<CategoryResponse> categoryResponses) {
		this.categoryResponses = categoryResponses;
	}

}
