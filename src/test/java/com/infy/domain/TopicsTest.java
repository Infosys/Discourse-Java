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

public class TopicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topics.class);
        Topics topics1 = new Topics();
        topics1.setId(1L);
        Topics topics2 = new Topics();
        topics2.setId(topics1.getId());
        assertThat(topics1).isEqualTo(topics2);
        topics2.setId(2L);
        assertThat(topics1).isNotEqualTo(topics2);
        topics1.setId(null);
        assertThat(topics1).isNotEqualTo(topics2);
    }
}
