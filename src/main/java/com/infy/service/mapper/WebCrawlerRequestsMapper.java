/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.WebCrawlerRequestsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebCrawlerRequests} and its DTO {@link WebCrawlerRequestsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebCrawlerRequestsMapper extends EntityMapper<WebCrawlerRequestsDTO, WebCrawlerRequests> {



    default WebCrawlerRequests fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebCrawlerRequests webCrawlerRequests = new WebCrawlerRequests();
        webCrawlerRequests.setId(id);
        return webCrawlerRequests;
    }
}
