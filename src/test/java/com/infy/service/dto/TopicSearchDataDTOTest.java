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

public class TopicSearchDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicSearchDataDTO.class);
        TopicSearchDataDTO topicSearchDataDTO1 = new TopicSearchDataDTO();
        topicSearchDataDTO1.setId(1L);
        TopicSearchDataDTO topicSearchDataDTO2 = new TopicSearchDataDTO();
        assertThat(topicSearchDataDTO1).isNotEqualTo(topicSearchDataDTO2);
        topicSearchDataDTO2.setId(topicSearchDataDTO1.getId());
        assertThat(topicSearchDataDTO1).isEqualTo(topicSearchDataDTO2);
        topicSearchDataDTO2.setId(2L);
        assertThat(topicSearchDataDTO1).isNotEqualTo(topicSearchDataDTO2);
        topicSearchDataDTO1.setId(null);
        assertThat(topicSearchDataDTO1).isNotEqualTo(topicSearchDataDTO2);
    }
}
