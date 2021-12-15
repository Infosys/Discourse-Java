/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TagsWebHooksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagsWebHooks} and its DTO {@link TagsWebHooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagsWebHooksMapper extends EntityMapper<TagsWebHooksDTO, TagsWebHooks> {



    default TagsWebHooks fromId(Long id) {
        if (id == null) {
            return null;
        }
        TagsWebHooks tagsWebHooks = new TagsWebHooks();
        tagsWebHooks.setId(id);
        return tagsWebHooks;
    }
}
