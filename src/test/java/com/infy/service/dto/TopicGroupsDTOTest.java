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

public class TopicGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicGroupsDTO.class);
        TopicGroupsDTO topicGroupsDTO1 = new TopicGroupsDTO();
        topicGroupsDTO1.setId(1L);
        TopicGroupsDTO topicGroupsDTO2 = new TopicGroupsDTO();
        assertThat(topicGroupsDTO1).isNotEqualTo(topicGroupsDTO2);
        topicGroupsDTO2.setId(topicGroupsDTO1.getId());
        assertThat(topicGroupsDTO1).isEqualTo(topicGroupsDTO2);
        topicGroupsDTO2.setId(2L);
        assertThat(topicGroupsDTO1).isNotEqualTo(topicGroupsDTO2);
        topicGroupsDTO1.setId(null);
        assertThat(topicGroupsDTO1).isNotEqualTo(topicGroupsDTO2);
    }
}
