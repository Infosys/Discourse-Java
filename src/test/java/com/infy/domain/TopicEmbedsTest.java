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

public class TopicEmbedsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicEmbeds.class);
        TopicEmbeds topicEmbeds1 = new TopicEmbeds();
        topicEmbeds1.setId(1L);
        TopicEmbeds topicEmbeds2 = new TopicEmbeds();
        topicEmbeds2.setId(topicEmbeds1.getId());
        assertThat(topicEmbeds1).isEqualTo(topicEmbeds2);
        topicEmbeds2.setId(2L);
        assertThat(topicEmbeds1).isNotEqualTo(topicEmbeds2);
        topicEmbeds1.setId(null);
        assertThat(topicEmbeds1).isNotEqualTo(topicEmbeds2);
    }
}
