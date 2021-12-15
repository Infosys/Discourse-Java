/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostActionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostActions} and its DTO {@link PostActionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostActionsMapper extends EntityMapper<PostActionsDTO, PostActions> {



    default PostActions fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostActions postActions = new PostActions();
        postActions.setId(id);
        return postActions;
    }
}
