/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.QuotedPostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuotedPosts} and its DTO {@link QuotedPostsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuotedPostsMapper extends EntityMapper<QuotedPostsDTO, QuotedPosts> {



    default QuotedPosts fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotedPosts quotedPosts = new QuotedPosts();
        quotedPosts.setId(id);
        return quotedPosts;
    }
}
