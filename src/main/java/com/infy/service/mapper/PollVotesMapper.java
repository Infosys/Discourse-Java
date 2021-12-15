/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PollVotesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PollVotes} and its DTO {@link PollVotesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PollVotesMapper extends EntityMapper<PollVotesDTO, PollVotes> {


    @Mapping(target = "pollOptions", ignore = true)
    @Mapping(target = "removePollOptions", ignore = true)
    @Mapping(target = "polls", ignore = true)
    @Mapping(target = "removePolls", ignore = true)
    PollVotes toEntity(PollVotesDTO pollVotesDTO);

    default PollVotes fromId(Long id) {
        if (id == null) {
            return null;
        }
        PollVotes pollVotes = new PollVotes();
        pollVotes.setId(id);
        return pollVotes;
    }
}
