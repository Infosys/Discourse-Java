/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserExportsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExports} and its DTO {@link UserExportsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserExportsMapper extends EntityMapper<UserExportsDTO, UserExports> {



    default UserExports fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExports userExports = new UserExports();
        userExports.setId(id);
        return userExports;
    }
}
