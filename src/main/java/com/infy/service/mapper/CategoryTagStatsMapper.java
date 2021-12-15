/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategoryTagStatsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryTagStats} and its DTO {@link CategoryTagStatsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryTagStatsMapper extends EntityMapper<CategoryTagStatsDTO, CategoryTagStats> {



    default CategoryTagStats fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryTagStats categoryTagStats = new CategoryTagStats();
        categoryTagStats.setId(id);
        return categoryTagStats;
    }
}
