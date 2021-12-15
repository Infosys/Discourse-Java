/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ImapSyncLogsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImapSyncLogs} and its DTO {@link ImapSyncLogsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImapSyncLogsMapper extends EntityMapper<ImapSyncLogsDTO, ImapSyncLogs> {



    default ImapSyncLogs fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImapSyncLogs imapSyncLogs = new ImapSyncLogs();
        imapSyncLogs.setId(id);
        return imapSyncLogs;
    }
}
