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

public class TopicUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicUsers.class);
        TopicUsers topicUsers1 = new TopicUsers();
        topicUsers1.setId(1L);
        TopicUsers topicUsers2 = new TopicUsers();
        topicUsers2.setId(topicUsers1.getId());
        assertThat(topicUsers1).isEqualTo(topicUsers2);
        topicUsers2.setId(2L);
        assertThat(topicUsers1).isNotEqualTo(topicUsers2);
        topicUsers1.setId(null);
        assertThat(topicUsers1).isNotEqualTo(topicUsers2);
    }
}
