/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.GroupArchivedMessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupArchivedMessages} and its DTO {@link GroupArchivedMessagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupArchivedMessagesMapper extends EntityMapper<GroupArchivedMessagesDTO, GroupArchivedMessages> {



    default GroupArchivedMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupArchivedMessages groupArchivedMessages = new GroupArchivedMessages();
        groupArchivedMessages.setId(id);
        return groupArchivedMessages;
    }
}
