/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ReviewableClaimedTopicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReviewableClaimedTopics} and its DTO {@link ReviewableClaimedTopicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReviewableClaimedTopicsMapper extends EntityMapper<ReviewableClaimedTopicsDTO, ReviewableClaimedTopics> {



    default ReviewableClaimedTopics fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReviewableClaimedTopics reviewableClaimedTopics = new ReviewableClaimedTopics();
        reviewableClaimedTopics.setId(id);
        return reviewableClaimedTopics;
    }
}
