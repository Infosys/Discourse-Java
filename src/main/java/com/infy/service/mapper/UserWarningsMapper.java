/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserWarningsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserWarnings} and its DTO {@link UserWarningsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserWarningsMapper extends EntityMapper<UserWarningsDTO, UserWarnings> {



    default UserWarnings fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserWarnings userWarnings = new UserWarnings();
        userWarnings.setId(id);
        return userWarnings;
    }
}
