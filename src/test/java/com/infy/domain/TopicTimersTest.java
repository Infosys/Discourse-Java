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

public class TopicTimersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicTimers.class);
        TopicTimers topicTimers1 = new TopicTimers();
        topicTimers1.setId(1L);
        TopicTimers topicTimers2 = new TopicTimers();
        topicTimers2.setId(topicTimers1.getId());
        assertThat(topicTimers1).isEqualTo(topicTimers2);
        topicTimers2.setId(2L);
        assertThat(topicTimers1).isNotEqualTo(topicTimers2);
        topicTimers1.setId(null);
        assertThat(topicTimers1).isNotEqualTo(topicTimers2);
    }
}
