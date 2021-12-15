/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.OptimizedImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OptimizedImages} and its DTO {@link OptimizedImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OptimizedImagesMapper extends EntityMapper<OptimizedImagesDTO, OptimizedImages> {



    default OptimizedImages fromId(Long id) {
        if (id == null) {
            return null;
        }
        OptimizedImages optimizedImages = new OptimizedImages();
        optimizedImages.setId(id);
        return optimizedImages;
    }
}
