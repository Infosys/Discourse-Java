/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PostUploadsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostUploads} and its DTO {@link PostUploadsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostUploadsMapper extends EntityMapper<PostUploadsDTO, PostUploads> {



    default PostUploads fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostUploads postUploads = new PostUploads();
        postUploads.setId(id);
        return postUploads;
    }
}
