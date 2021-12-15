/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PollsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Polls} and its DTO {@link PollsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PollVotesMapper.class})
public interface PollsMapper extends EntityMapper<PollsDTO, Polls> {

    @Mapping(source = "pollVotes.id", target = "pollVotesId")
    PollsDTO toDto(Polls polls);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "removePosts", ignore = true)
    @Mapping(source = "pollVotesId", target = "pollVotes")
    Polls toEntity(PollsDTO pollsDTO);

    default Polls fromId(Long id) {
        if (id == null) {
            return null;
        }
        Polls polls = new Polls();
        polls.setId(id);
        return polls;
    }
}
