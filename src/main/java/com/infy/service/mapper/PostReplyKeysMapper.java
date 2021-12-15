/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostReplyKeysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostReplyKeys} and its DTO {@link PostReplyKeysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostReplyKeysMapper extends EntityMapper<PostReplyKeysDTO, PostReplyKeys> {



    default PostReplyKeys fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostReplyKeys postReplyKeys = new PostReplyKeys();
        postReplyKeys.setId(id);
        return postReplyKeys;
    }
}
