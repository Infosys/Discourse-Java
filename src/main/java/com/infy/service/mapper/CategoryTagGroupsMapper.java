/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategoryTagGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryTagGroups} and its DTO {@link CategoryTagGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryTagGroupsMapper extends EntityMapper<CategoryTagGroupsDTO, CategoryTagGroups> {



    default CategoryTagGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryTagGroups categoryTagGroups = new CategoryTagGroups();
        categoryTagGroups.setId(id);
        return categoryTagGroups;
    }
}
