/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.InvitedGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvitedGroups} and its DTO {@link InvitedGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvitedGroupsMapper extends EntityMapper<InvitedGroupsDTO, InvitedGroups> {



    default InvitedGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvitedGroups invitedGroups = new InvitedGroups();
        invitedGroups.setId(id);
        return invitedGroups;
    }
}
