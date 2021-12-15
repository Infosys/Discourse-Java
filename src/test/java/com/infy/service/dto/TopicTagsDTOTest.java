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

public class TopicTagsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicTagsDTO.class);
        TopicTagsDTO topicTagsDTO1 = new TopicTagsDTO();
        topicTagsDTO1.setId(1L);
        TopicTagsDTO topicTagsDTO2 = new TopicTagsDTO();
        assertThat(topicTagsDTO1).isNotEqualTo(topicTagsDTO2);
        topicTagsDTO2.setId(topicTagsDTO1.getId());
        assertThat(topicTagsDTO1).isEqualTo(topicTagsDTO2);
        topicTagsDTO2.setId(2L);
        assertThat(topicTagsDTO1).isNotEqualTo(topicTagsDTO2);
        topicTagsDTO1.setId(null);
        assertThat(topicTagsDTO1).isNotEqualTo(topicTagsDTO2);
    }
}
