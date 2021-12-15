/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserBadgesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserBadges} and its DTO {@link UserBadgesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserBadgesMapper extends EntityMapper<UserBadgesDTO, UserBadges> {



    default UserBadges fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserBadges userBadges = new UserBadges();
        userBadges.setId(id);
        return userBadges;
    }
}
