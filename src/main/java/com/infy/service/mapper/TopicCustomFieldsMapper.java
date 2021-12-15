/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicCustomFieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicCustomFields} and its DTO {@link TopicCustomFieldsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicCustomFieldsMapper extends EntityMapper<TopicCustomFieldsDTO, TopicCustomFields> {



    default TopicCustomFields fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicCustomFields topicCustomFields = new TopicCustomFields();
        topicCustomFields.setId(id);
        return topicCustomFields;
    }
}
