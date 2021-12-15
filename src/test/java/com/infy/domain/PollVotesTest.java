/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class PollVotesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollVotes.class);
        PollVotes pollVotes1 = new PollVotes();
        pollVotes1.setId(1L);
        PollVotes pollVotes2 = new PollVotes();
        pollVotes2.setId(pollVotes1.getId());
        assertThat(pollVotes1).isEqualTo(pollVotes2);
        pollVotes2.setId(2L);
        assertThat(pollVotes1).isNotEqualTo(pollVotes2);
        pollVotes1.setId(null);
        assertThat(pollVotes1).isNotEqualTo(pollVotes2);
    }
}
