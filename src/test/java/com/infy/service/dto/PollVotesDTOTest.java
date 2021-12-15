/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class PollVotesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollVotesDTO.class);
        PollVotesDTO pollVotesDTO1 = new PollVotesDTO();
        pollVotesDTO1.setId(1L);
        PollVotesDTO pollVotesDTO2 = new PollVotesDTO();
        assertThat(pollVotesDTO1).isNotEqualTo(pollVotesDTO2);
        pollVotesDTO2.setId(pollVotesDTO1.getId());
        assertThat(pollVotesDTO1).isEqualTo(pollVotesDTO2);
        pollVotesDTO2.setId(2L);
        assertThat(pollVotesDTO1).isNotEqualTo(pollVotesDTO2);
        pollVotesDTO1.setId(null);
        assertThat(pollVotesDTO1).isNotEqualTo(pollVotesDTO2);
    }
}
