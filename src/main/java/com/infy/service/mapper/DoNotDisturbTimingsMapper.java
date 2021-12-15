/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.DoNotDisturbTimingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DoNotDisturbTimings} and its DTO {@link DoNotDisturbTimingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DoNotDisturbTimingsMapper extends EntityMapper<DoNotDisturbTimingsDTO, DoNotDisturbTimings> {



    default DoNotDisturbTimings fromId(Long id) {
        if (id == null) {
            return null;
        }
        DoNotDisturbTimings doNotDisturbTimings = new DoNotDisturbTimings();
        doNotDisturbTimings.setId(id);
        return doNotDisturbTimings;
    }
}
