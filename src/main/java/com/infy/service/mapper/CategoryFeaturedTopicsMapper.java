/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategoryFeaturedTopicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryFeaturedTopics} and its DTO {@link CategoryFeaturedTopicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryFeaturedTopicsMapper extends EntityMapper<CategoryFeaturedTopicsDTO, CategoryFeaturedTopics> {



    default CategoryFeaturedTopics fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryFeaturedTopics categoryFeaturedTopics = new CategoryFeaturedTopics();
        categoryFeaturedTopics.setId(id);
        return categoryFeaturedTopics;
    }
}
