/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.Oauth2UserInfosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Oauth2UserInfos} and its DTO {@link Oauth2UserInfosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Oauth2UserInfosMapper extends EntityMapper<Oauth2UserInfosDTO, Oauth2UserInfos> {



    default Oauth2UserInfos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Oauth2UserInfos oauth2UserInfos = new Oauth2UserInfos();
        oauth2UserInfos.setId(id);
        return oauth2UserInfos;
    }
}
