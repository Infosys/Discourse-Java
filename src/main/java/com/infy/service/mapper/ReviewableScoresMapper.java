/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ReviewableScoresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReviewableScores} and its DTO {@link ReviewableScoresDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReviewableScoresMapper extends EntityMapper<ReviewableScoresDTO, ReviewableScores> {



    default ReviewableScores fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReviewableScores reviewableScores = new ReviewableScores();
        reviewableScores.setId(id);
        return reviewableScores;
    }
}
