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

public class TopicLinkClicksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicLinkClicks.class);
        TopicLinkClicks topicLinkClicks1 = new TopicLinkClicks();
        topicLinkClicks1.setId(1L);
        TopicLinkClicks topicLinkClicks2 = new TopicLinkClicks();
        topicLinkClicks2.setId(topicLinkClicks1.getId());
        assertThat(topicLinkClicks1).isEqualTo(topicLinkClicks2);
        topicLinkClicks2.setId(2L);
        assertThat(topicLinkClicks1).isNotEqualTo(topicLinkClicks2);
        topicLinkClicks1.setId(null);
        assertThat(topicLinkClicks1).isNotEqualTo(topicLinkClicks2);
    }
}
