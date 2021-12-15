/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.BackupDraftTopicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BackupDraftTopics} and its DTO {@link BackupDraftTopicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BackupDraftTopicsMapper extends EntityMapper<BackupDraftTopicsDTO, BackupDraftTopics> {



    default BackupDraftTopics fromId(Long id) {
        if (id == null) {
            return null;
        }
        BackupDraftTopics backupDraftTopics = new BackupDraftTopics();
        backupDraftTopics.setId(id);
        return backupDraftTopics;
    }
}
