/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserHistoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserHistories} and its DTO {@link UserHistoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserHistoriesMapper extends EntityMapper<UserHistoriesDTO, UserHistories> {



    default UserHistories fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserHistories userHistories = new UserHistories();
        userHistories.setId(id);
        return userHistories;
    }
}
