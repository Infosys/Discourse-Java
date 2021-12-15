/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import javax.validation.constraints.NotNull;

public class AnnouncementRequest {

	@NotNull
	private String title;
	@NotNull
	private String raw;

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

	@Override
	public String toString() {
		return "AnnouncementRequest [title=" + title + ", raw=" + raw + "]";
	}

}
