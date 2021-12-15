/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ThemesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Themes} and its DTO {@link ThemesDTO}.
 */
@Mapper(componentModel = "spring", uses = {JavascriptCachesMapper.class})
public interface ThemesMapper extends EntityMapper<ThemesDTO, Themes> {

    @Mapping(source = "javascriptCaches.id", target = "javascriptCachesId")
    ThemesDTO toDto(Themes themes);

    @Mapping(source = "javascriptCachesId", target = "javascriptCaches")
    Themes toEntity(ThemesDTO themesDTO);

    default Themes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Themes themes = new Themes();
        themes.setId(id);
        return themes;
    }
}
