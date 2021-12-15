/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;

import com.infy.domain.UserStats;
import com.infy.service.model.UserStatsResponse;

@Mapper(componentModel = "spring", uses = {})
public interface UserStatsReponseMapper extends EntityMapper<UserStatsResponse, UserStats> {

	default UserStats fromId(Long id) {
		if (id == null) {
			return null;
		}
		UserStats userStats = new UserStats();
		userStats.setId(id);
		return userStats;
	}
}
