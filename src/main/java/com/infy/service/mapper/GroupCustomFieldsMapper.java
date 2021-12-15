/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.GroupCustomFieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupCustomFields} and its DTO {@link GroupCustomFieldsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupCustomFieldsMapper extends EntityMapper<GroupCustomFieldsDTO, GroupCustomFields> {



    default GroupCustomFields fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupCustomFields groupCustomFields = new GroupCustomFields();
        groupCustomFields.setId(id);
        return groupCustomFields;
    }
}
