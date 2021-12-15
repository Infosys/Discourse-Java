/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserEmailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserEmails} and its DTO {@link UserEmailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserEmailsMapper extends EntityMapper<UserEmailsDTO, UserEmails> {



    default UserEmails fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserEmails userEmails = new UserEmails();
        userEmails.setId(id);
        return userEmails;
    }
}
