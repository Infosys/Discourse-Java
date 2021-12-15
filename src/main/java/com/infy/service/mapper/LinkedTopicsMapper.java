/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.LinkedTopicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LinkedTopics} and its DTO {@link LinkedTopicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LinkedTopicsMapper extends EntityMapper<LinkedTopicsDTO, LinkedTopics> {



    default LinkedTopics fromId(Long id) {
        if (id == null) {
            return null;
        }
        LinkedTopics linkedTopics = new LinkedTopics();
        linkedTopics.setId(id);
        return linkedTopics;
    }
}
