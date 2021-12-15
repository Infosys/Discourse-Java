/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ThemeTranslationOverridesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThemeTranslationOverrides} and its DTO {@link ThemeTranslationOverridesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThemeTranslationOverridesMapper extends EntityMapper<ThemeTranslationOverridesDTO, ThemeTranslationOverrides> {



    default ThemeTranslationOverrides fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThemeTranslationOverrides themeTranslationOverrides = new ThemeTranslationOverrides();
        themeTranslationOverrides.setId(id);
        return themeTranslationOverrides;
    }
}
