/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostCustomFieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostCustomFields} and its DTO {@link PostCustomFieldsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostCustomFieldsMapper extends EntityMapper<PostCustomFieldsDTO, PostCustomFields> {



    default PostCustomFields fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostCustomFields postCustomFields = new PostCustomFields();
        postCustomFields.setId(id);
        return postCustomFields;
    }
}
