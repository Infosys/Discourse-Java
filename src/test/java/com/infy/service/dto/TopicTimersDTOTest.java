/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class TopicTimersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicTimersDTO.class);
        TopicTimersDTO topicTimersDTO1 = new TopicTimersDTO();
        topicTimersDTO1.setId(1L);
        TopicTimersDTO topicTimersDTO2 = new TopicTimersDTO();
        assertThat(topicTimersDTO1).isNotEqualTo(topicTimersDTO2);
        topicTimersDTO2.setId(topicTimersDTO1.getId());
        assertThat(topicTimersDTO1).isEqualTo(topicTimersDTO2);
        topicTimersDTO2.setId(2L);
        assertThat(topicTimersDTO1).isNotEqualTo(topicTimersDTO2);
        topicTimersDTO1.setId(null);
        assertThat(topicTimersDTO1).isNotEqualTo(topicTimersDTO2);
    }
}
