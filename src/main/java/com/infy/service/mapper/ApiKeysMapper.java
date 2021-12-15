/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ApiKeysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiKeys} and its DTO {@link ApiKeysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiKeysMapper extends EntityMapper<ApiKeysDTO, ApiKeys> {



    default ApiKeys fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiKeys apiKeys = new ApiKeys();
        apiKeys.setId(id);
        return apiKeys;
    }
}
