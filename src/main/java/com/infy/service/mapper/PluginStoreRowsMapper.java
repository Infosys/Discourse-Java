/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PluginStoreRowsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PluginStoreRows} and its DTO {@link PluginStoreRowsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PluginStoreRowsMapper extends EntityMapper<PluginStoreRowsDTO, PluginStoreRows> {



    default PluginStoreRows fromId(Long id) {
        if (id == null) {
            return null;
        }
        PluginStoreRows pluginStoreRows = new PluginStoreRows();
        pluginStoreRows.setId(id);
        return pluginStoreRows;
    }
}
