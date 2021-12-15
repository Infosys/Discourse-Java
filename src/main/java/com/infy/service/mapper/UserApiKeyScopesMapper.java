/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserApiKeyScopesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserApiKeyScopes} and its DTO {@link UserApiKeyScopesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserApiKeyScopesMapper extends EntityMapper<UserApiKeyScopesDTO, UserApiKeyScopes> {



    default UserApiKeyScopes fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserApiKeyScopes userApiKeyScopes = new UserApiKeyScopes();
        userApiKeyScopes.setId(id);
        return userApiKeyScopes;
    }
}
