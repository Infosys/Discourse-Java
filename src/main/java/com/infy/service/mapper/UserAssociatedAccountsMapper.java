/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserAssociatedAccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAssociatedAccounts} and its DTO {@link UserAssociatedAccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAssociatedAccountsMapper extends EntityMapper<UserAssociatedAccountsDTO, UserAssociatedAccounts> {



    default UserAssociatedAccounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAssociatedAccounts userAssociatedAccounts = new UserAssociatedAccounts();
        userAssociatedAccounts.setId(id);
        return userAssociatedAccounts;
    }
}
