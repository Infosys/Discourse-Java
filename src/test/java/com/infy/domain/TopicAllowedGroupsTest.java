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

public class TopicAllowedGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicAllowedGroups.class);
        TopicAllowedGroups topicAllowedGroups1 = new TopicAllowedGroups();
        topicAllowedGroups1.setId(1L);
        TopicAllowedGroups topicAllowedGroups2 = new TopicAllowedGroups();
        topicAllowedGroups2.setId(topicAllowedGroups1.getId());
        assertThat(topicAllowedGroups1).isEqualTo(topicAllowedGroups2);
        topicAllowedGroups2.setId(2L);
        assertThat(topicAllowedGroups1).isNotEqualTo(topicAllowedGroups2);
        topicAllowedGroups1.setId(null);
        assertThat(topicAllowedGroups1).isNotEqualTo(topicAllowedGroups2);
    }
}
