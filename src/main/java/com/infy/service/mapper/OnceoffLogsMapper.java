/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.OnceoffLogsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OnceoffLogs} and its DTO {@link OnceoffLogsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OnceoffLogsMapper extends EntityMapper<OnceoffLogsDTO, OnceoffLogs> {



    default OnceoffLogs fromId(Long id) {
        if (id == null) {
            return null;
        }
        OnceoffLogs onceoffLogs = new OnceoffLogs();
        onceoffLogs.setId(id);
        return onceoffLogs;
    }
}
