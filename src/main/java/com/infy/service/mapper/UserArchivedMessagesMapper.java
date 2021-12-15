/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserArchivedMessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserArchivedMessages} and its DTO {@link UserArchivedMessagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserArchivedMessagesMapper extends EntityMapper<UserArchivedMessagesDTO, UserArchivedMessages> {



    default UserArchivedMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserArchivedMessages userArchivedMessages = new UserArchivedMessages();
        userArchivedMessages.setId(id);
        return userArchivedMessages;
    }
}
