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

public class TopicLinkClicksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicLinkClicksDTO.class);
        TopicLinkClicksDTO topicLinkClicksDTO1 = new TopicLinkClicksDTO();
        topicLinkClicksDTO1.setId(1L);
        TopicLinkClicksDTO topicLinkClicksDTO2 = new TopicLinkClicksDTO();
        assertThat(topicLinkClicksDTO1).isNotEqualTo(topicLinkClicksDTO2);
        topicLinkClicksDTO2.setId(topicLinkClicksDTO1.getId());
        assertThat(topicLinkClicksDTO1).isEqualTo(topicLinkClicksDTO2);
        topicLinkClicksDTO2.setId(2L);
        assertThat(topicLinkClicksDTO1).isNotEqualTo(topicLinkClicksDTO2);
        topicLinkClicksDTO1.setId(null);
        assertThat(topicLinkClicksDTO1).isNotEqualTo(topicLinkClicksDTO2);
    }
}
