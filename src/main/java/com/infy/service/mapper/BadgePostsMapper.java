/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.BadgePostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BadgePosts} and its DTO {@link BadgePostsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BadgePostsMapper extends EntityMapper<BadgePostsDTO, BadgePosts> {



    default BadgePosts fromId(Long id) {
        if (id == null) {
            return null;
        }
        BadgePosts badgePosts = new BadgePosts();
        badgePosts.setId(id);
        return badgePosts;
    }
}
