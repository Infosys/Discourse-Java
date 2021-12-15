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

public class LinkedTopicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkedTopics.class);
        LinkedTopics linkedTopics1 = new LinkedTopics();
        linkedTopics1.setId(1L);
        LinkedTopics linkedTopics2 = new LinkedTopics();
        linkedTopics2.setId(linkedTopics1.getId());
        assertThat(linkedTopics1).isEqualTo(linkedTopics2);
        linkedTopics2.setId(2L);
        assertThat(linkedTopics1).isNotEqualTo(linkedTopics2);
        linkedTopics1.setId(null);
        assertThat(linkedTopics1).isNotEqualTo(linkedTopics2);
    }
}
