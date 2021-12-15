/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TagGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagGroups} and its DTO {@link TagGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagGroupsMapper extends EntityMapper<TagGroupsDTO, TagGroups> {



    default TagGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        TagGroups tagGroups = new TagGroups();
        tagGroups.setId(id);
        return tagGroups;
    }
}
