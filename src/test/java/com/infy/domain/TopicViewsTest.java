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

public class TopicViewsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicViews.class);
        TopicViews topicViews1 = new TopicViews();
        topicViews1.setId(1L);
        TopicViews topicViews2 = new TopicViews();
        topicViews2.setId(topicViews1.getId());
        assertThat(topicViews1).isEqualTo(topicViews2);
        topicViews2.setId(2L);
        assertThat(topicViews1).isNotEqualTo(topicViews2);
        topicViews1.setId(null);
        assertThat(topicViews1).isNotEqualTo(topicViews2);
    }
}
