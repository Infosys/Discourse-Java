/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategorySearchDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorySearchData} and its DTO {@link CategorySearchDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorySearchDataMapper extends EntityMapper<CategorySearchDataDTO, CategorySearchData> {



    default CategorySearchData fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategorySearchData categorySearchData = new CategorySearchData();
        categorySearchData.setId(id);
        return categorySearchData;
    }
}
