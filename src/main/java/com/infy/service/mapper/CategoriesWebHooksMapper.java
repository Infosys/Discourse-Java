/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategoriesWebHooksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriesWebHooks} and its DTO {@link CategoriesWebHooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriesWebHooksMapper extends EntityMapper<CategoriesWebHooksDTO, CategoriesWebHooks> {



    default CategoriesWebHooks fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoriesWebHooks categoriesWebHooks = new CategoriesWebHooks();
        categoriesWebHooks.setId(id);
        return categoriesWebHooks;
    }
}
