/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UnsubscribeKeysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnsubscribeKeys} and its DTO {@link UnsubscribeKeysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnsubscribeKeysMapper extends EntityMapper<UnsubscribeKeysDTO, UnsubscribeKeys> {



    default UnsubscribeKeys fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnsubscribeKeys unsubscribeKeys = new UnsubscribeKeys();
        unsubscribeKeys.setId(id);
        return unsubscribeKeys;
    }
}
