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

public class TopicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicsDTO.class);
        TopicsDTO topicsDTO1 = new TopicsDTO();
        topicsDTO1.setId(1L);
        TopicsDTO topicsDTO2 = new TopicsDTO();
        assertThat(topicsDTO1).isNotEqualTo(topicsDTO2);
        topicsDTO2.setId(topicsDTO1.getId());
        assertThat(topicsDTO1).isEqualTo(topicsDTO2);
        topicsDTO2.setId(2L);
        assertThat(topicsDTO1).isNotEqualTo(topicsDTO2);
        topicsDTO1.setId(null);
        assertThat(topicsDTO1).isNotEqualTo(topicsDTO2);
    }
}
