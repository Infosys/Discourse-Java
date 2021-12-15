/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.SchemaMigrationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchemaMigrations} and its DTO {@link SchemaMigrationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchemaMigrationsMapper extends EntityMapper<SchemaMigrationsDTO, SchemaMigrations> {



    default SchemaMigrations fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchemaMigrations schemaMigrations = new SchemaMigrations();
        schemaMigrations.setId(id);
        return schemaMigrations;
    }
}
