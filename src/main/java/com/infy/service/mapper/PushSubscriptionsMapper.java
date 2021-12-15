/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PushSubscriptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PushSubscriptions} and its DTO {@link PushSubscriptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PushSubscriptionsMapper extends EntityMapper<PushSubscriptionsDTO, PushSubscriptions> {



    default PushSubscriptions fromId(Long id) {
        if (id == null) {
            return null;
        }
        PushSubscriptions pushSubscriptions = new PushSubscriptions();
        pushSubscriptions.setId(id);
        return pushSubscriptions;
    }
}
