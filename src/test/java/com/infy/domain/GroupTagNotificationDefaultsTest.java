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

public class GroupTagNotificationDefaultsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupTagNotificationDefaults.class);
        GroupTagNotificationDefaults groupTagNotificationDefaults1 = new GroupTagNotificationDefaults();
        groupTagNotificationDefaults1.setId(1L);
        GroupTagNotificationDefaults groupTagNotificationDefaults2 = new GroupTagNotificationDefaults();
        groupTagNotificationDefaults2.setId(groupTagNotificationDefaults1.getId());
        assertThat(groupTagNotificationDefaults1).isEqualTo(groupTagNotificationDefaults2);
        groupTagNotificationDefaults2.setId(2L);
        assertThat(groupTagNotificationDefaults1).isNotEqualTo(groupTagNotificationDefaults2);
        groupTagNotificationDefaults1.setId(null);
        assertThat(groupTagNotificationDefaults1).isNotEqualTo(groupTagNotificationDefaults2);
    }
}
