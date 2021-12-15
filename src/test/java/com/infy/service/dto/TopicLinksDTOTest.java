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

public class TopicLinksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicLinksDTO.class);
        TopicLinksDTO topicLinksDTO1 = new TopicLinksDTO();
        topicLinksDTO1.setId(1L);
        TopicLinksDTO topicLinksDTO2 = new TopicLinksDTO();
        assertThat(topicLinksDTO1).isNotEqualTo(topicLinksDTO2);
        topicLinksDTO2.setId(topicLinksDTO1.getId());
        assertThat(topicLinksDTO1).isEqualTo(topicLinksDTO2);
        topicLinksDTO2.setId(2L);
        assertThat(topicLinksDTO1).isNotEqualTo(topicLinksDTO2);
        topicLinksDTO1.setId(null);
        assertThat(topicLinksDTO1).isNotEqualTo(topicLinksDTO2);
    }
}
