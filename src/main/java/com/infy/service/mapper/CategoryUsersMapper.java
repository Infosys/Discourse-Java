/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CategoryUsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryUsers} and its DTO {@link CategoryUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryUsersMapper extends EntityMapper<CategoryUsersDTO, CategoryUsers> {



    default CategoryUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryUsers categoryUsers = new CategoryUsers();
        categoryUsers.setId(id);
        return categoryUsers;
    }
}
