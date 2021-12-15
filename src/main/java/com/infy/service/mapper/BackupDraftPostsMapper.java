/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.BackupDraftPostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BackupDraftPosts} and its DTO {@link BackupDraftPostsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BackupDraftPostsMapper extends EntityMapper<BackupDraftPostsDTO, BackupDraftPosts> {



    default BackupDraftPosts fromId(Long id) {
        if (id == null) {
            return null;
        }
        BackupDraftPosts backupDraftPosts = new BackupDraftPosts();
        backupDraftPosts.setId(id);
        return backupDraftPosts;
    }
}
