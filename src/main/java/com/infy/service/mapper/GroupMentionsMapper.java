/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.GroupMentionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupMentions} and its DTO {@link GroupMentionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupMentionsMapper extends EntityMapper<GroupMentionsDTO, GroupMentions> {



    default GroupMentions fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupMentions groupMentions = new GroupMentions();
        groupMentions.setId(id);
        return groupMentions;
    }
}
