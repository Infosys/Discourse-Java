/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserOpenIdsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserOpenIds} and its DTO {@link UserOpenIdsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserOpenIdsMapper extends EntityMapper<UserOpenIdsDTO, UserOpenIds> {



    default UserOpenIds fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserOpenIds userOpenIds = new UserOpenIds();
        userOpenIds.setId(id);
        return userOpenIds;
    }
}
