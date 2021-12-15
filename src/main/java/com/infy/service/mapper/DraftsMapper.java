/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.DraftsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drafts} and its DTO {@link DraftsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DraftsMapper extends EntityMapper<DraftsDTO, Drafts> {



    default Drafts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Drafts drafts = new Drafts();
        drafts.setId(id);
        return drafts;
    }
}
