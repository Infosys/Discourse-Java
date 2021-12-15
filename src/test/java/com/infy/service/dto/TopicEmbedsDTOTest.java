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

public class TopicEmbedsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicEmbedsDTO.class);
        TopicEmbedsDTO topicEmbedsDTO1 = new TopicEmbedsDTO();
        topicEmbedsDTO1.setId(1L);
        TopicEmbedsDTO topicEmbedsDTO2 = new TopicEmbedsDTO();
        assertThat(topicEmbedsDTO1).isNotEqualTo(topicEmbedsDTO2);
        topicEmbedsDTO2.setId(topicEmbedsDTO1.getId());
        assertThat(topicEmbedsDTO1).isEqualTo(topicEmbedsDTO2);
        topicEmbedsDTO2.setId(2L);
        assertThat(topicEmbedsDTO1).isNotEqualTo(topicEmbedsDTO2);
        topicEmbedsDTO1.setId(null);
        assertThat(topicEmbedsDTO1).isNotEqualTo(topicEmbedsDTO2);
    }
}
