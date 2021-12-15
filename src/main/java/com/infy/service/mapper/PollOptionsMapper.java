/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.PollOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PollOptions} and its DTO {@link PollOptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PollVotesMapper.class})
public interface PollOptionsMapper extends EntityMapper<PollOptionsDTO, PollOptions> {

    @Mapping(source = "pollVotes.id", target = "pollVotesId")
    PollOptionsDTO toDto(PollOptions pollOptions);

    @Mapping(source = "pollVotesId", target = "pollVotes")
    PollOptions toEntity(PollOptionsDTO pollOptionsDTO);

    default PollOptions fromId(Long id) {
        if (id == null) {
            return null;
        }
        PollOptions pollOptions = new PollOptions();
        pollOptions.setId(id);
        return pollOptions;
    }
}
