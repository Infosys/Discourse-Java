/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TopicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Topics} and its DTO {@link TopicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicsMapper extends EntityMapper<TopicsDTO, Topics> {



    default Topics fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topics topics = new Topics();
        topics.setId(id);
        return topics;
    }
}
