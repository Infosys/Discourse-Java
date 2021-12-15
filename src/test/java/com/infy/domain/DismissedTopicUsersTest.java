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

public class DismissedTopicUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DismissedTopicUsers.class);
        DismissedTopicUsers dismissedTopicUsers1 = new DismissedTopicUsers();
        dismissedTopicUsers1.setId(1L);
        DismissedTopicUsers dismissedTopicUsers2 = new DismissedTopicUsers();
        dismissedTopicUsers2.setId(dismissedTopicUsers1.getId());
        assertThat(dismissedTopicUsers1).isEqualTo(dismissedTopicUsers2);
        dismissedTopicUsers2.setId(2L);
        assertThat(dismissedTopicUsers1).isNotEqualTo(dismissedTopicUsers2);
        dismissedTopicUsers1.setId(null);
        assertThat(dismissedTopicUsers1).isNotEqualTo(dismissedTopicUsers2);
    }
}
