/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserFieldOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserFieldOptions} and its DTO {@link UserFieldOptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserFieldOptionsMapper extends EntityMapper<UserFieldOptionsDTO, UserFieldOptions> {



    default UserFieldOptions fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserFieldOptions userFieldOptions = new UserFieldOptions();
        userFieldOptions.setId(id);
        return userFieldOptions;
    }
}
