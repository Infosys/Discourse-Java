/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.GivenDailyLikesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GivenDailyLikes} and its DTO {@link GivenDailyLikesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GivenDailyLikesMapper extends EntityMapper<GivenDailyLikesDTO, GivenDailyLikes> {



    default GivenDailyLikes fromId(Long id) {
        if (id == null) {
            return null;
        }
        GivenDailyLikes givenDailyLikes = new GivenDailyLikes();
        givenDailyLikes.setId(id);
        return givenDailyLikes;
    }
}
