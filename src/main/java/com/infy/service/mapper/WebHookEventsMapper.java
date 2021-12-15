/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.WebHookEventsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebHookEvents} and its DTO {@link WebHookEventsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebHookEventsMapper extends EntityMapper<WebHookEventsDTO, WebHookEvents> {



    default WebHookEvents fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebHookEvents webHookEvents = new WebHookEvents();
        webHookEvents.setId(id);
        return webHookEvents;
    }
}
