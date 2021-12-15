/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TagGroupPermissionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagGroupPermissions} and its DTO {@link TagGroupPermissionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagGroupPermissionsMapper extends EntityMapper<TagGroupPermissionsDTO, TagGroupPermissions> {



    default TagGroupPermissions fromId(Long id) {
        if (id == null) {
            return null;
        }
        TagGroupPermissions tagGroupPermissions = new TagGroupPermissions();
        tagGroupPermissions.setId(id);
        return tagGroupPermissions;
    }
}
