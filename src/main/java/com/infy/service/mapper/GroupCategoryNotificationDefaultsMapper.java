/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.GroupCategoryNotificationDefaultsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupCategoryNotificationDefaults} and its DTO {@link GroupCategoryNotificationDefaultsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupCategoryNotificationDefaultsMapper extends EntityMapper<GroupCategoryNotificationDefaultsDTO, GroupCategoryNotificationDefaults> {



    default GroupCategoryNotificationDefaults fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupCategoryNotificationDefaults groupCategoryNotificationDefaults = new GroupCategoryNotificationDefaults();
        groupCategoryNotificationDefaults.setId(id);
        return groupCategoryNotificationDefaults;
    }
}
