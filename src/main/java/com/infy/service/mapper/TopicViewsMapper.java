/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicViewsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicViews} and its DTO {@link TopicViewsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicViewsMapper extends EntityMapper<TopicViewsDTO, TopicViews> {



    default TopicViews fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicViews topicViews = new TopicViews();
        topicViews.setId(id);
        return topicViews;
    }
}
