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

public class PollsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Polls.class);
        Polls polls1 = new Polls();
        polls1.setId(1L);
        Polls polls2 = new Polls();
        polls2.setId(polls1.getId());
        assertThat(polls1).isEqualTo(polls2);
        polls2.setId(2L);
        assertThat(polls1).isNotEqualTo(polls2);
        polls1.setId(null);
        assertThat(polls1).isNotEqualTo(polls2);
    }
}
