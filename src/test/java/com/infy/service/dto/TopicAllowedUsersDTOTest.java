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

public class TopicAllowedUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicAllowedUsersDTO.class);
        TopicAllowedUsersDTO topicAllowedUsersDTO1 = new TopicAllowedUsersDTO();
        topicAllowedUsersDTO1.setId(1L);
        TopicAllowedUsersDTO topicAllowedUsersDTO2 = new TopicAllowedUsersDTO();
        assertThat(topicAllowedUsersDTO1).isNotEqualTo(topicAllowedUsersDTO2);
        topicAllowedUsersDTO2.setId(topicAllowedUsersDTO1.getId());
        assertThat(topicAllowedUsersDTO1).isEqualTo(topicAllowedUsersDTO2);
        topicAllowedUsersDTO2.setId(2L);
        assertThat(topicAllowedUsersDTO1).isNotEqualTo(topicAllowedUsersDTO2);
        topicAllowedUsersDTO1.setId(null);
        assertThat(topicAllowedUsersDTO1).isNotEqualTo(topicAllowedUsersDTO2);
    }
}
