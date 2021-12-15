/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostTimingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostTimings} and its DTO {@link PostTimingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostTimingsMapper extends EntityMapper<PostTimingsDTO, PostTimings> {



    default PostTimings fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostTimings postTimings = new PostTimings();
        postTimings.setId(id);
        return postTimings;
    }
}
