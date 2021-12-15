/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.MutedUsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MutedUsers} and its DTO {@link MutedUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MutedUsersMapper extends EntityMapper<MutedUsersDTO, MutedUsers> {



    default MutedUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        MutedUsers mutedUsers = new MutedUsers();
        mutedUsers.setId(id);
        return mutedUsers;
    }
}
