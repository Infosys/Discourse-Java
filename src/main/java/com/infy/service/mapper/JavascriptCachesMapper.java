/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.JavascriptCachesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JavascriptCaches} and its DTO {@link JavascriptCachesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JavascriptCachesMapper extends EntityMapper<JavascriptCachesDTO, JavascriptCaches> {


    @Mapping(target = "themeFields", ignore = true)
    @Mapping(target = "removeThemeFields", ignore = true)
    @Mapping(target = "themes", ignore = true)
    @Mapping(target = "removeThemes", ignore = true)
    JavascriptCaches toEntity(JavascriptCachesDTO javascriptCachesDTO);

    default JavascriptCaches fromId(Long id) {
        if (id == null) {
            return null;
        }
        JavascriptCaches javascriptCaches = new JavascriptCaches();
        javascriptCaches.setId(id);
        return javascriptCaches;
    }
}
