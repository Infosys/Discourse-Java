/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "raw", "edit_reason" })
public class UpdatedPost {

	@JsonProperty("raw")
	@NotNull
	private String raw;
	@JsonProperty("edit_reason")
	private String editReason;

	@JsonProperty("raw")
	public String getRaw() {
		return raw;
	}

	@JsonProperty("raw")
	public void setRaw(String raw) {
		this.raw = raw;
	}

	@JsonProperty("edit_reason")
	public String getEditReason() {
		return editReason;
	}

	@JsonProperty("edit_reason")
	public void setEditReason(String editReason) {
		this.editReason = editReason;
	}
}
