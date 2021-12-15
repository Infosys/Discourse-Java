/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PublishedPagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublishedPages} and its DTO {@link PublishedPagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublishedPagesMapper extends EntityMapper<PublishedPagesDTO, PublishedPages> {



    default PublishedPages fromId(Long id) {
        if (id == null) {
            return null;
        }
        PublishedPages publishedPages = new PublishedPages();
        publishedPages.setId(id);
        return publishedPages;
    }
}
