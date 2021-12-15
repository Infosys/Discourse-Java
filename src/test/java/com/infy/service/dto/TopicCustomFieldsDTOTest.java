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

public class TopicCustomFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicCustomFieldsDTO.class);
        TopicCustomFieldsDTO topicCustomFieldsDTO1 = new TopicCustomFieldsDTO();
        topicCustomFieldsDTO1.setId(1L);
        TopicCustomFieldsDTO topicCustomFieldsDTO2 = new TopicCustomFieldsDTO();
        assertThat(topicCustomFieldsDTO1).isNotEqualTo(topicCustomFieldsDTO2);
        topicCustomFieldsDTO2.setId(topicCustomFieldsDTO1.getId());
        assertThat(topicCustomFieldsDTO1).isEqualTo(topicCustomFieldsDTO2);
        topicCustomFieldsDTO2.setId(2L);
        assertThat(topicCustomFieldsDTO1).isNotEqualTo(topicCustomFieldsDTO2);
        topicCustomFieldsDTO1.setId(null);
        assertThat(topicCustomFieldsDTO1).isNotEqualTo(topicCustomFieldsDTO2);
    }
}
