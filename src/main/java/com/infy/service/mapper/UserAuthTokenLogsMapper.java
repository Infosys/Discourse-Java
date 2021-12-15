/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserAuthTokenLogsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAuthTokenLogs} and its DTO {@link UserAuthTokenLogsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAuthTokenLogsMapper extends EntityMapper<UserAuthTokenLogsDTO, UserAuthTokenLogs> {



    default UserAuthTokenLogs fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAuthTokenLogs userAuthTokenLogs = new UserAuthTokenLogs();
        userAuthTokenLogs.setId(id);
        return userAuthTokenLogs;
    }
}
