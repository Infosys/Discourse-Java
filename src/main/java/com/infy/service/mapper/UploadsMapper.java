/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.UploadsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Uploads} and its DTO {@link UploadsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfilesMapper.class})
public interface UploadsMapper extends EntityMapper<UploadsDTO, Uploads> {

    @Mapping(source = "userProfiles.id", target = "userProfilesId")
    UploadsDTO toDto(Uploads uploads);

    @Mapping(source = "userProfilesId", target = "userProfiles")
    Uploads toEntity(UploadsDTO uploadsDTO);

    default Uploads fromId(Long id) {
        if (id == null) {
            return null;
        }
        Uploads uploads = new Uploads();
        uploads.setId(id);
        return uploads;
    }
}
