/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.InvitedUsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvitedUsers} and its DTO {@link InvitedUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvitedUsersMapper extends EntityMapper<InvitedUsersDTO, InvitedUsers> {



    default InvitedUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvitedUsers invitedUsers = new InvitedUsers();
        invitedUsers.setId(id);
        return invitedUsers;
    }
}
