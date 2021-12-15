/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;

import com.infy.domain.PostActionTypes;
import com.infy.service.model.CreatePostActionTypeRequest;

@Mapper(componentModel = "spring", uses = {})
public interface CreatePostActionRequestTypeMapper extends EntityMapper<CreatePostActionTypeRequest, PostActionTypes> {

    default PostActionTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostActionTypes postActionTypes = new PostActionTypes();
        postActionTypes.setId(id);
        return postActionTypes;
    }
}
