/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.AllowedPmUsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AllowedPmUsers} and its DTO {@link AllowedPmUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AllowedPmUsersMapper extends EntityMapper<AllowedPmUsersDTO, AllowedPmUsers> {



    default AllowedPmUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        AllowedPmUsers allowedPmUsers = new AllowedPmUsers();
        allowedPmUsers.setId(id);
        return allowedPmUsers;
    }
}
