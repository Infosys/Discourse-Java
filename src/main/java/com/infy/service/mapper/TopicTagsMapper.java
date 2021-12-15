/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicTagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicTags} and its DTO {@link TopicTagsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicTagsMapper extends EntityMapper<TopicTagsDTO, TopicTags> {



    default TopicTags fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicTags topicTags = new TopicTags();
        topicTags.setId(id);
        return topicTags;
    }
}
