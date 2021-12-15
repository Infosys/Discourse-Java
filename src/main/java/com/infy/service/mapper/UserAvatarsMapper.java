/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserAvatarsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAvatars} and its DTO {@link UserAvatarsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAvatarsMapper extends EntityMapper<UserAvatarsDTO, UserAvatars> {



    default UserAvatars fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAvatars userAvatars = new UserAvatars();
        userAvatars.setId(id);
        return userAvatars;
    }
}
