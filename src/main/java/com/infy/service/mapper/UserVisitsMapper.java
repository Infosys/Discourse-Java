/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserVisitsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserVisits} and its DTO {@link UserVisitsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserVisitsMapper extends EntityMapper<UserVisitsDTO, UserVisits> {



    default UserVisits fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserVisits userVisits = new UserVisits();
        userVisits.setId(id);
        return userVisits;
    }
}
