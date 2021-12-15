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

public class TopicGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicGroups.class);
        TopicGroups topicGroups1 = new TopicGroups();
        topicGroups1.setId(1L);
        TopicGroups topicGroups2 = new TopicGroups();
        topicGroups2.setId(topicGroups1.getId());
        assertThat(topicGroups1).isEqualTo(topicGroups2);
        topicGroups2.setId(2L);
        assertThat(topicGroups1).isNotEqualTo(topicGroups2);
        topicGroups1.setId(null);
        assertThat(topicGroups1).isNotEqualTo(topicGroups2);
    }
}
