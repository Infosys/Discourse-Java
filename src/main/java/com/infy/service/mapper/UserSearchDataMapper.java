/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserSearchDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSearchData} and its DTO {@link UserSearchDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserSearchDataMapper extends EntityMapper<UserSearchDataDTO, UserSearchData> {



    default UserSearchData fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserSearchData userSearchData = new UserSearchData();
        userSearchData.setId(id);
        return userSearchData;
    }
}
