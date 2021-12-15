/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserApiKeysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserApiKeys} and its DTO {@link UserApiKeysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserApiKeysMapper extends EntityMapper<UserApiKeysDTO, UserApiKeys> {



    default UserApiKeys fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserApiKeys userApiKeys = new UserApiKeys();
        userApiKeys.setId(id);
        return userApiKeys;
    }
}
