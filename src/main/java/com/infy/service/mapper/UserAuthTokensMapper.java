/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserAuthTokensDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAuthTokens} and its DTO {@link UserAuthTokensDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAuthTokensMapper extends EntityMapper<UserAuthTokensDTO, UserAuthTokens> {



    default UserAuthTokens fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAuthTokens userAuthTokens = new UserAuthTokens();
        userAuthTokens.setId(id);
        return userAuthTokens;
    }
}
