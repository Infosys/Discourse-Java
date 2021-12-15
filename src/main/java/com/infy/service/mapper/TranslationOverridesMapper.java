/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TranslationOverridesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TranslationOverrides} and its DTO {@link TranslationOverridesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TranslationOverridesMapper extends EntityMapper<TranslationOverridesDTO, TranslationOverrides> {



    default TranslationOverrides fromId(Long id) {
        if (id == null) {
            return null;
        }
        TranslationOverrides translationOverrides = new TranslationOverrides();
        translationOverrides.setId(id);
        return translationOverrides;
    }
}
