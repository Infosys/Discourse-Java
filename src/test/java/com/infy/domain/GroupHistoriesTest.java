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

public class GroupHistoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupHistories.class);
        GroupHistories groupHistories1 = new GroupHistories();
        groupHistories1.setId(1L);
        GroupHistories groupHistories2 = new GroupHistories();
        groupHistories2.setId(groupHistories1.getId());
        assertThat(groupHistories1).isEqualTo(groupHistories2);
        groupHistories2.setId(2L);
        assertThat(groupHistories1).isNotEqualTo(groupHistories2);
        groupHistories1.setId(null);
        assertThat(groupHistories1).isNotEqualTo(groupHistories2);
    }
}
