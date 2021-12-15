/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.EmbeddableHostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmbeddableHosts} and its DTO {@link EmbeddableHostsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmbeddableHostsMapper extends EntityMapper<EmbeddableHostsDTO, EmbeddableHosts> {



    default EmbeddableHosts fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmbeddableHosts embeddableHosts = new EmbeddableHosts();
        embeddableHosts.setId(id);
        return embeddableHosts;
    }
}
