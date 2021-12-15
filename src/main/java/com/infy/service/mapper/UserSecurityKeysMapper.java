/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserSecurityKeysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSecurityKeys} and its DTO {@link UserSecurityKeysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserSecurityKeysMapper extends EntityMapper<UserSecurityKeysDTO, UserSecurityKeys> {


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    UserSecurityKeys toEntity(UserSecurityKeysDTO userSecurityKeysDTO);

    default UserSecurityKeys fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserSecurityKeys userSecurityKeys = new UserSecurityKeys();
        userSecurityKeys.setId(id);
        return userSecurityKeys;
    }
}
