/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ColorSchemeColorsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ColorSchemeColors} and its DTO {@link ColorSchemeColorsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ColorSchemeColorsMapper extends EntityMapper<ColorSchemeColorsDTO, ColorSchemeColors> {



    default ColorSchemeColors fromId(Long id) {
        if (id == null) {
            return null;
        }
        ColorSchemeColors colorSchemeColors = new ColorSchemeColors();
        colorSchemeColors.setId(id);
        return colorSchemeColors;
    }
}
