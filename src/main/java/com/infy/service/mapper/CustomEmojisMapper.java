/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.CustomEmojisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomEmojis} and its DTO {@link CustomEmojisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomEmojisMapper extends EntityMapper<CustomEmojisDTO, CustomEmojis> {



    default CustomEmojis fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomEmojis customEmojis = new CustomEmojis();
        customEmojis.setId(id);
        return customEmojis;
    }
}
