/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.SchemaMigrationDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchemaMigrationDetails} and its DTO {@link SchemaMigrationDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchemaMigrationDetailsMapper extends EntityMapper<SchemaMigrationDetailsDTO, SchemaMigrationDetails> {



    default SchemaMigrationDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchemaMigrationDetails schemaMigrationDetails = new SchemaMigrationDetails();
        schemaMigrationDetails.setId(id);
        return schemaMigrationDetails;
    }
}
