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

public class TopicThumbnailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicThumbnailsDTO.class);
        TopicThumbnailsDTO topicThumbnailsDTO1 = new TopicThumbnailsDTO();
        topicThumbnailsDTO1.setId(1L);
        TopicThumbnailsDTO topicThumbnailsDTO2 = new TopicThumbnailsDTO();
        assertThat(topicThumbnailsDTO1).isNotEqualTo(topicThumbnailsDTO2);
        topicThumbnailsDTO2.setId(topicThumbnailsDTO1.getId());
        assertThat(topicThumbnailsDTO1).isEqualTo(topicThumbnailsDTO2);
        topicThumbnailsDTO2.setId(2L);
        assertThat(topicThumbnailsDTO1).isNotEqualTo(topicThumbnailsDTO2);
        topicThumbnailsDTO1.setId(null);
        assertThat(topicThumbnailsDTO1).isNotEqualTo(topicThumbnailsDTO2);
    }
}
