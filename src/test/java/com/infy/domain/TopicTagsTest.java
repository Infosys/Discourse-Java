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

public class TopicTagsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicTags.class);
        TopicTags topicTags1 = new TopicTags();
        topicTags1.setId(1L);
        TopicTags topicTags2 = new TopicTags();
        topicTags2.setId(topicTags1.getId());
        assertThat(topicTags1).isEqualTo(topicTags2);
        topicTags2.setId(2L);
        assertThat(topicTags1).isNotEqualTo(topicTags2);
        topicTags1.setId(null);
        assertThat(topicTags1).isNotEqualTo(topicTags2);
    }
}
