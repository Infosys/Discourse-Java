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

public class TopTopicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopTopics.class);
        TopTopics topTopics1 = new TopTopics();
        topTopics1.setId(1L);
        TopTopics topTopics2 = new TopTopics();
        topTopics2.setId(topTopics1.getId());
        assertThat(topTopics1).isEqualTo(topTopics2);
        topTopics2.setId(2L);
        assertThat(topTopics1).isNotEqualTo(topTopics2);
        topTopics1.setId(null);
        assertThat(topTopics1).isNotEqualTo(topTopics2);
    }
}
