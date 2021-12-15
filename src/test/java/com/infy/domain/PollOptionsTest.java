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

public class PollOptionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollOptions.class);
        PollOptions pollOptions1 = new PollOptions();
        pollOptions1.setId(1L);
        PollOptions pollOptions2 = new PollOptions();
        pollOptions2.setId(pollOptions1.getId());
        assertThat(pollOptions1).isEqualTo(pollOptions2);
        pollOptions2.setId(2L);
        assertThat(pollOptions1).isNotEqualTo(pollOptions2);
        pollOptions1.setId(null);
        assertThat(pollOptions1).isNotEqualTo(pollOptions2);
    }
}
