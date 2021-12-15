/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicInvitesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicInvites} and its DTO {@link TopicInvitesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicInvitesMapper extends EntityMapper<TopicInvitesDTO, TopicInvites> {



    default TopicInvites fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicInvites topicInvites = new TopicInvites();
        topicInvites.setId(id);
        return topicInvites;
    }
}
