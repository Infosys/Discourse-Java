/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserCustomFieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserCustomFields} and its DTO {@link UserCustomFieldsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserCustomFieldsMapper extends EntityMapper<UserCustomFieldsDTO, UserCustomFields> {



    default UserCustomFields fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserCustomFields userCustomFields = new UserCustomFields();
        userCustomFields.setId(id);
        return userCustomFields;
    }
}
