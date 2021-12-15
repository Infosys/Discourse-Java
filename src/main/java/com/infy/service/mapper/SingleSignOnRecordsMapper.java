/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.SingleSignOnRecordsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SingleSignOnRecords} and its DTO {@link SingleSignOnRecordsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SingleSignOnRecordsMapper extends EntityMapper<SingleSignOnRecordsDTO, SingleSignOnRecords> {



    default SingleSignOnRecords fromId(Long id) {
        if (id == null) {
            return null;
        }
        SingleSignOnRecords singleSignOnRecords = new SingleSignOnRecords();
        singleSignOnRecords.setId(id);
        return singleSignOnRecords;
    }
}
