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

public class TopicSearchDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicSearchData.class);
        TopicSearchData topicSearchData1 = new TopicSearchData();
        topicSearchData1.setId(1L);
        TopicSearchData topicSearchData2 = new TopicSearchData();
        topicSearchData2.setId(topicSearchData1.getId());
        assertThat(topicSearchData1).isEqualTo(topicSearchData2);
        topicSearchData2.setId(2L);
        assertThat(topicSearchData1).isNotEqualTo(topicSearchData2);
        topicSearchData1.setId(null);
        assertThat(topicSearchData1).isNotEqualTo(topicSearchData2);
    }
}
