/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategoryTagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryTags} and its DTO {@link CategoryTagsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryTagsMapper extends EntityMapper<CategoryTagsDTO, CategoryTags> {



    default CategoryTags fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryTags categoryTags = new CategoryTags();
        categoryTags.setId(id);
        return categoryTags;
    }
}
