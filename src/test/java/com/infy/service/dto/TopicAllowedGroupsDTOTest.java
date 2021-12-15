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

public class TopicAllowedGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicAllowedGroupsDTO.class);
        TopicAllowedGroupsDTO topicAllowedGroupsDTO1 = new TopicAllowedGroupsDTO();
        topicAllowedGroupsDTO1.setId(1L);
        TopicAllowedGroupsDTO topicAllowedGroupsDTO2 = new TopicAllowedGroupsDTO();
        assertThat(topicAllowedGroupsDTO1).isNotEqualTo(topicAllowedGroupsDTO2);
        topicAllowedGroupsDTO2.setId(topicAllowedGroupsDTO1.getId());
        assertThat(topicAllowedGroupsDTO1).isEqualTo(topicAllowedGroupsDTO2);
        topicAllowedGroupsDTO2.setId(2L);
        assertThat(topicAllowedGroupsDTO1).isNotEqualTo(topicAllowedGroupsDTO2);
        topicAllowedGroupsDTO1.setId(null);
        assertThat(topicAllowedGroupsDTO1).isNotEqualTo(topicAllowedGroupsDTO2);
    }
}
