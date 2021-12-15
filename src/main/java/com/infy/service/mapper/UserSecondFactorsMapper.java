/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UserSecondFactorsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSecondFactors} and its DTO {@link UserSecondFactorsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserSecondFactorsMapper extends EntityMapper<UserSecondFactorsDTO, UserSecondFactors> {



    default UserSecondFactors fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserSecondFactors userSecondFactors = new UserSecondFactors();
        userSecondFactors.setId(id);
        return userSecondFactors;
    }
}
