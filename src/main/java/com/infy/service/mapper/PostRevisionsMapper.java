/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostRevisionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostRevisions} and its DTO {@link PostRevisionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostRevisionsMapper extends EntityMapper<PostRevisionsDTO, PostRevisions> {



    default PostRevisions fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostRevisions postRevisions = new PostRevisions();
        postRevisions.setId(id);
        return postRevisions;
    }
}
