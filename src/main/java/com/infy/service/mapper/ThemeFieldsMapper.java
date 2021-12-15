/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ThemeFieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThemeFields} and its DTO {@link ThemeFieldsDTO}.
 */
@Mapper(componentModel = "spring", uses = {JavascriptCachesMapper.class})
public interface ThemeFieldsMapper extends EntityMapper<ThemeFieldsDTO, ThemeFields> {

    @Mapping(source = "javascriptCaches.id", target = "javascriptCachesId")
    ThemeFieldsDTO toDto(ThemeFields themeFields);

    @Mapping(source = "javascriptCachesId", target = "javascriptCaches")
    ThemeFields toEntity(ThemeFieldsDTO themeFieldsDTO);

    default ThemeFields fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThemeFields themeFields = new ThemeFields();
        themeFields.setId(id);
        return themeFields;
    }
}
