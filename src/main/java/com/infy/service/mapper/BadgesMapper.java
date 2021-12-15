/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.BadgesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badges} and its DTO {@link BadgesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfilesMapper.class})
public interface BadgesMapper extends EntityMapper<BadgesDTO, Badges> {

    @Mapping(source = "userProfiles.id", target = "userProfilesId")
    BadgesDTO toDto(Badges badges);

    @Mapping(source = "userProfilesId", target = "userProfiles")
    Badges toEntity(BadgesDTO badgesDTO);

    default Badges fromId(Long id) {
        if (id == null) {
            return null;
        }
        Badges badges = new Badges();
        badges.setId(id);
        return badges;
    }
}
