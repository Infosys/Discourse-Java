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

public class TopicCustomFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicCustomFields.class);
        TopicCustomFields topicCustomFields1 = new TopicCustomFields();
        topicCustomFields1.setId(1L);
        TopicCustomFields topicCustomFields2 = new TopicCustomFields();
        topicCustomFields2.setId(topicCustomFields1.getId());
        assertThat(topicCustomFields1).isEqualTo(topicCustomFields2);
        topicCustomFields2.setId(2L);
        assertThat(topicCustomFields1).isNotEqualTo(topicCustomFields2);
        topicCustomFields1.setId(null);
        assertThat(topicCustomFields1).isNotEqualTo(topicCustomFields2);
    }
}
