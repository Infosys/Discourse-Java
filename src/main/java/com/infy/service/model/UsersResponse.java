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

@JsonPropertyOrder({ "user_badges", "user" })
public class UsersResponse {

	@JsonProperty("user_badges")
	private List<Object> userBadges = null;
	@JsonProperty("user")
	private User user;

	@JsonProperty("user_badges")
	public List<Object> getUserBadges() {
		return userBadges;
	}

	@JsonProperty("user_badges")
	public void setUserBadges(List<Object> userBadges) {
		this.userBadges = userBadges;
	}

	@JsonProperty("user")
	public User getUser() {
		return user;
	}

	@JsonProperty("user")
	public void setUser(User user) {
		this.user = user;
	}

}
