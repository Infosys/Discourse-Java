/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ShelvedNotificationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShelvedNotifications} and its DTO {@link ShelvedNotificationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShelvedNotificationsMapper extends EntityMapper<ShelvedNotificationsDTO, ShelvedNotifications> {



    default ShelvedNotifications fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShelvedNotifications shelvedNotifications = new ShelvedNotifications();
        shelvedNotifications.setId(id);
        return shelvedNotifications;
    }
}
