/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ReviewableHistoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReviewableHistories} and its DTO {@link ReviewableHistoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReviewableHistoriesMapper extends EntityMapper<ReviewableHistoriesDTO, ReviewableHistories> {



    default ReviewableHistories fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReviewableHistories reviewableHistories = new ReviewableHistories();
        reviewableHistories.setId(id);
        return reviewableHistories;
    }
}
