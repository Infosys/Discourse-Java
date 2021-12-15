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

public class TopicViewsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicViewsDTO.class);
        TopicViewsDTO topicViewsDTO1 = new TopicViewsDTO();
        topicViewsDTO1.setId(1L);
        TopicViewsDTO topicViewsDTO2 = new TopicViewsDTO();
        assertThat(topicViewsDTO1).isNotEqualTo(topicViewsDTO2);
        topicViewsDTO2.setId(topicViewsDTO1.getId());
        assertThat(topicViewsDTO1).isEqualTo(topicViewsDTO2);
        topicViewsDTO2.setId(2L);
        assertThat(topicViewsDTO1).isNotEqualTo(topicViewsDTO2);
        topicViewsDTO1.setId(null);
        assertThat(topicViewsDTO1).isNotEqualTo(topicViewsDTO2);
    }
}
