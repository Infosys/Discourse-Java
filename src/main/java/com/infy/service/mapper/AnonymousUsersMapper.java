/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.AnonymousUsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnonymousUsers} and its DTO {@link AnonymousUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnonymousUsersMapper extends EntityMapper<AnonymousUsersDTO, AnonymousUsers> {



    default AnonymousUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnonymousUsers anonymousUsers = new AnonymousUsers();
        anonymousUsers.setId(id);
        return anonymousUsers;
    }
}
