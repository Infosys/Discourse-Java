/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicLinksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicLinks} and its DTO {@link TopicLinksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicLinksMapper extends EntityMapper<TopicLinksDTO, TopicLinks> {



    default TopicLinks fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicLinks topicLinks = new TopicLinks();
        topicLinks.setId(id);
        return topicLinks;
    }
}
