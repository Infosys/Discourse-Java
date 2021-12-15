/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.DevelopersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Developers} and its DTO {@link DevelopersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DevelopersMapper extends EntityMapper<DevelopersDTO, Developers> {



    default Developers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Developers developers = new Developers();
        developers.setId(id);
        return developers;
    }
}
