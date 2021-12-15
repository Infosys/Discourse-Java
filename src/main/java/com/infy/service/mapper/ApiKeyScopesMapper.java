/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ApiKeyScopesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiKeyScopes} and its DTO {@link ApiKeyScopesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiKeyScopesMapper extends EntityMapper<ApiKeyScopesDTO, ApiKeyScopes> {



    default ApiKeyScopes fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiKeyScopes apiKeyScopes = new ApiKeyScopes();
        apiKeyScopes.setId(id);
        return apiKeyScopes;
    }
}
