/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.SiteSettingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteSettings} and its DTO {@link SiteSettingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SiteSettingsMapper extends EntityMapper<SiteSettingsDTO, SiteSettings> {



    default SiteSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiteSettings siteSettings = new SiteSettings();
        siteSettings.setId(id);
        return siteSettings;
    }
}
