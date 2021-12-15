/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.DraftSequencesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DraftSequences} and its DTO {@link DraftSequencesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DraftSequencesMapper extends EntityMapper<DraftSequencesDTO, DraftSequences> {



    default DraftSequences fromId(Long id) {
        if (id == null) {
            return null;
        }
        DraftSequences draftSequences = new DraftSequences();
        draftSequences.setId(id);
        return draftSequences;
    }
}
