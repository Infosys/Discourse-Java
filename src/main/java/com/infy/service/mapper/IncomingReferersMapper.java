/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.IncomingReferersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IncomingReferers} and its DTO {@link IncomingReferersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncomingReferersMapper extends EntityMapper<IncomingReferersDTO, IncomingReferers> {



    default IncomingReferers fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncomingReferers incomingReferers = new IncomingReferers();
        incomingReferers.setId(id);
        return incomingReferers;
    }
}
