/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicTimersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopicTimers} and its DTO {@link TopicTimersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicTimersMapper extends EntityMapper<TopicTimersDTO, TopicTimers> {



    default TopicTimers fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopicTimers topicTimers = new TopicTimers();
        topicTimers.setId(id);
        return topicTimers;
    }
}
