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

public class TopicLinksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicLinks.class);
        TopicLinks topicLinks1 = new TopicLinks();
        topicLinks1.setId(1L);
        TopicLinks topicLinks2 = new TopicLinks();
        topicLinks2.setId(topicLinks1.getId());
        assertThat(topicLinks1).isEqualTo(topicLinks2);
        topicLinks2.setId(2L);
        assertThat(topicLinks1).isNotEqualTo(topicLinks2);
        topicLinks1.setId(null);
        assertThat(topicLinks1).isNotEqualTo(topicLinks2);
    }
}
