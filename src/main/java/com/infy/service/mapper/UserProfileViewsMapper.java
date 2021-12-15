/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserProfileViewsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserProfileViews} and its DTO {@link UserProfileViewsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserProfileViewsMapper extends EntityMapper<UserProfileViewsDTO, UserProfileViews> {



    default UserProfileViews fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserProfileViews userProfileViews = new UserProfileViews();
        userProfileViews.setId(id);
        return userProfileViews;
    }
}
