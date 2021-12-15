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

public class TopicUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicUsersDTO.class);
        TopicUsersDTO topicUsersDTO1 = new TopicUsersDTO();
        topicUsersDTO1.setId(1L);
        TopicUsersDTO topicUsersDTO2 = new TopicUsersDTO();
        assertThat(topicUsersDTO1).isNotEqualTo(topicUsersDTO2);
        topicUsersDTO2.setId(topicUsersDTO1.getId());
        assertThat(topicUsersDTO1).isEqualTo(topicUsersDTO2);
        topicUsersDTO2.setId(2L);
        assertThat(topicUsersDTO1).isNotEqualTo(topicUsersDTO2);
        topicUsersDTO1.setId(null);
        assertThat(topicUsersDTO1).isNotEqualTo(topicUsersDTO2);
    }
}
