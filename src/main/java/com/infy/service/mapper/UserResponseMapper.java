/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;

import com.infy.domain.Users;
import com.infy.service.model.User;

@Mapper(componentModel = "spring", uses = {})
public interface UserResponseMapper extends EntityMapper<User, Users> {

	User toDto(Users users);

	default Users fromId(Long id) {
		if (id == null) {
			return null;
		}
		Users users = new Users();
		users.setId(id);
		return users;
	}
}
