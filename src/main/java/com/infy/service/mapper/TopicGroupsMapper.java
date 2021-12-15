/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicGroups} and its DTO {@link TopicGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicGroupsMapper extends EntityMapper<TopicGroupsDTO, TopicGroups> {



    default TopicGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicGroups topicGroups = new TopicGroups();
        topicGroups.setId(id);
        return topicGroups;
    }
}
