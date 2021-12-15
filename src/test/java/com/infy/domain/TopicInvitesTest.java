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

public class TopicInvitesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicInvites.class);
        TopicInvites topicInvites1 = new TopicInvites();
        topicInvites1.setId(1L);
        TopicInvites topicInvites2 = new TopicInvites();
        topicInvites2.setId(topicInvites1.getId());
        assertThat(topicInvites1).isEqualTo(topicInvites2);
        topicInvites2.setId(2L);
        assertThat(topicInvites1).isNotEqualTo(topicInvites2);
        topicInvites1.setId(null);
        assertThat(topicInvites1).isNotEqualTo(topicInvites2);
    }
}
