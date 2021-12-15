/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicThumbnailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicThumbnails} and its DTO {@link TopicThumbnailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicThumbnailsMapper extends EntityMapper<TopicThumbnailsDTO, TopicThumbnails> {



    default TopicThumbnails fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicThumbnails topicThumbnails = new TopicThumbnails();
        topicThumbnails.setId(id);
        return topicThumbnails;
    }
}
