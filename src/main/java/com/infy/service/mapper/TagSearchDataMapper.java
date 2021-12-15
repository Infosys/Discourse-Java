/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TagSearchDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagSearchData} and its DTO {@link TagSearchDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagSearchDataMapper extends EntityMapper<TagSearchDataDTO, TagSearchData> {



    default TagSearchData fromId(Long id) {
        if (id == null) {
            return null;
        }
        TagSearchData tagSearchData = new TagSearchData();
        tagSearchData.setId(id);
        return tagSearchData;
    }
}
