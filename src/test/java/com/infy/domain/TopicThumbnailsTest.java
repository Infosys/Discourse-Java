/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class TopicThumbnailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicThumbnails.class);
        TopicThumbnails topicThumbnails1 = new TopicThumbnails();
        topicThumbnails1.setId(1L);
        TopicThumbnails topicThumbnails2 = new TopicThumbnails();
        topicThumbnails2.setId(topicThumbnails1.getId());
        assertThat(topicThumbnails1).isEqualTo(topicThumbnails2);
        topicThumbnails2.setId(2L);
        assertThat(topicThumbnails1).isNotEqualTo(topicThumbnails2);
        topicThumbnails1.setId(null);
        assertThat(topicThumbnails1).isNotEqualTo(topicThumbnails2);
    }
}
