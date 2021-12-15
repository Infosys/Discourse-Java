/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ChildThemesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChildThemes} and its DTO {@link ChildThemesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChildThemesMapper extends EntityMapper<ChildThemesDTO, ChildThemes> {



    default ChildThemes fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChildThemes childThemes = new ChildThemes();
        childThemes.setId(id);
        return childThemes;
    }
}
