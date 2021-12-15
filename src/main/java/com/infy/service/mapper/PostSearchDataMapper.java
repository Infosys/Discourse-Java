/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostSearchDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostSearchData} and its DTO {@link PostSearchDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostSearchDataMapper extends EntityMapper<PostSearchDataDTO, PostSearchData> {



    default PostSearchData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostSearchData postSearchData = new PostSearchData();
        postSearchData.setId(id);
        return postSearchData;
    }
}
