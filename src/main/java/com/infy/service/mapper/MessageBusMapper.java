/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.MessageBusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MessageBus} and its DTO {@link MessageBusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MessageBusMapper extends EntityMapper<MessageBusDTO, MessageBus> {



    default MessageBus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MessageBus messageBus = new MessageBus();
        messageBus.setId(id);
        return messageBus;
    }
}
