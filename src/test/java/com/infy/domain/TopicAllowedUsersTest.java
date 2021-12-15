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

public class TopicAllowedUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicAllowedUsers.class);
        TopicAllowedUsers topicAllowedUsers1 = new TopicAllowedUsers();
        topicAllowedUsers1.setId(1L);
        TopicAllowedUsers topicAllowedUsers2 = new TopicAllowedUsers();
        topicAllowedUsers2.setId(topicAllowedUsers1.getId());
        assertThat(topicAllowedUsers1).isEqualTo(topicAllowedUsers2);
        topicAllowedUsers2.setId(2L);
        assertThat(topicAllowedUsers1).isNotEqualTo(topicAllowedUsers2);
        topicAllowedUsers1.setId(null);
        assertThat(topicAllowedUsers1).isNotEqualTo(topicAllowedUsers2);
    }
}
