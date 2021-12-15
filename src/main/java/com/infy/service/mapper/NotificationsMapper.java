/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.NotificationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notifications} and its DTO {@link NotificationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificationsMapper extends EntityMapper<NotificationsDTO, Notifications> {



    default Notifications fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notifications notifications = new Notifications();
        notifications.setId(id);
        return notifications;
    }
}
