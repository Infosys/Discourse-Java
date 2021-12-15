/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopTopicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TopTopics} and its DTO {@link TopTopicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopTopicsMapper extends EntityMapper<TopTopicsDTO, TopTopics> {



    default TopTopics fromId(Long id) {
        if (id == null) {
            return null;
        }
        TopTopics topTopics = new TopTopics();
        topTopics.setId(id);
        return topTopics;
    }
}
