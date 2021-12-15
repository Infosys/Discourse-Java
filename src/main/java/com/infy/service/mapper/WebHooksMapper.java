/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.WebHooksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebHooks} and its DTO {@link WebHooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebHooksMapper extends EntityMapper<WebHooksDTO, WebHooks> {



    default WebHooks fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebHooks webHooks = new WebHooks();
        webHooks.setId(id);
        return webHooks;
    }
}
