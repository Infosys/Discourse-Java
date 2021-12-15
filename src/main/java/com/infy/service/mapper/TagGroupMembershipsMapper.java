/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TagGroupMembershipsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagGroupMemberships} and its DTO {@link TagGroupMembershipsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagGroupMembershipsMapper extends EntityMapper<TagGroupMembershipsDTO, TagGroupMemberships> {



    default TagGroupMemberships fromId(Long id) {
        if (id == null) {
            return null;
        }
        TagGroupMemberships tagGroupMemberships = new TagGroupMemberships();
        tagGroupMemberships.setId(id);
        return tagGroupMemberships;
    }
}
