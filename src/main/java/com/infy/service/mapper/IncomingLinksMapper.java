/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.IncomingLinksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IncomingLinks} and its DTO {@link IncomingLinksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncomingLinksMapper extends EntityMapper<IncomingLinksDTO, IncomingLinks> {



    default IncomingLinks fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncomingLinks incomingLinks = new IncomingLinks();
        incomingLinks.setId(id);
        return incomingLinks;
    }
}
