/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.SharedDraftsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SharedDrafts} and its DTO {@link SharedDraftsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SharedDraftsMapper extends EntityMapper<SharedDraftsDTO, SharedDrafts> {



    default SharedDrafts fromId(Long id) {
        if (id == null) {
            return null;
        }
        SharedDrafts sharedDrafts = new SharedDrafts();
        sharedDrafts.setId(id);
        return sharedDrafts;
    }
}
