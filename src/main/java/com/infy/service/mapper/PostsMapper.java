/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Posts} and its DTO {@link PostsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PollsMapper.class})
public interface PostsMapper extends EntityMapper<PostsDTO, Posts> {

    @Mapping(source = "polls.id", target = "pollsId")
    PostsDTO toDto(Posts posts);

    @Mapping(source = "pollsId", target = "polls")
    Posts toEntity(PostsDTO postsDTO);

    default Posts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Posts posts = new Posts();
        posts.setId(id);
        return posts;
    }
}
