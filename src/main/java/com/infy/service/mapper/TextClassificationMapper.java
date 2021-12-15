/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.TextClassificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TextClassification} and its DTO {@link TextClassificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TextClassificationMapper extends EntityMapper<TextClassificationDTO, TextClassification> {



    default TextClassification fromId(Long id) {
        if (id == null) {
            return null;
        }
        TextClassification textClassification = new TextClassification();
        textClassification.setId(id);
        return textClassification;
    }
}
