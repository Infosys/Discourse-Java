/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserUploadsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserUploads} and its DTO {@link UserUploadsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserUploadsMapper extends EntityMapper<UserUploadsDTO, UserUploads> {



    default UserUploads fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserUploads userUploads = new UserUploads();
        userUploads.setId(id);
        return userUploads;
    }
}
