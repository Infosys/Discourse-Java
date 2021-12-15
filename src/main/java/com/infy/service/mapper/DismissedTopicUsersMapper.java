/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.DismissedTopicUsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DismissedTopicUsers} and its DTO {@link DismissedTopicUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DismissedTopicUsersMapper extends EntityMapper<DismissedTopicUsersDTO, DismissedTopicUsers> {



    default DismissedTopicUsers fromId(Long id) {
        if (id == null) {
            return null;
        }
        DismissedTopicUsers dismissedTopicUsers = new DismissedTopicUsers();
        dismissedTopicUsers.setId(id);
        return dismissedTopicUsers;
    }
}
