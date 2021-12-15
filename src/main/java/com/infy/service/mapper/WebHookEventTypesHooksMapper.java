/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.WebHookEventTypesHooksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebHookEventTypesHooks} and its DTO {@link WebHookEventTypesHooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebHookEventTypesHooksMapper extends EntityMapper<WebHookEventTypesHooksDTO, WebHookEventTypesHooks> {



    default WebHookEventTypesHooks fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebHookEventTypesHooks webHookEventTypesHooks = new WebHookEventTypesHooks();
        webHookEventTypesHooks.setId(id);
        return webHookEventTypesHooks;
    }
}
