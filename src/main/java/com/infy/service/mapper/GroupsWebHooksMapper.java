/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.GroupsWebHooksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupsWebHooks} and its DTO {@link GroupsWebHooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupsWebHooksMapper extends EntityMapper<GroupsWebHooksDTO, GroupsWebHooks> {



    default GroupsWebHooks fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupsWebHooks groupsWebHooks = new GroupsWebHooks();
        groupsWebHooks.setId(id);
        return groupsWebHooks;
    }
}
