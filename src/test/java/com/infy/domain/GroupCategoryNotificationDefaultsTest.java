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

public class GroupCategoryNotificationDefaultsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCategoryNotificationDefaults.class);
        GroupCategoryNotificationDefaults groupCategoryNotificationDefaults1 = new GroupCategoryNotificationDefaults();
        groupCategoryNotificationDefaults1.setId(1L);
        GroupCategoryNotificationDefaults groupCategoryNotificationDefaults2 = new GroupCategoryNotificationDefaults();
        groupCategoryNotificationDefaults2.setId(groupCategoryNotificationDefaults1.getId());
        assertThat(groupCategoryNotificationDefaults1).isEqualTo(groupCategoryNotificationDefaults2);
        groupCategoryNotificationDefaults2.setId(2L);
        assertThat(groupCategoryNotificationDefaults1).isNotEqualTo(groupCategoryNotificationDefaults2);
        groupCategoryNotificationDefaults1.setId(null);
        assertThat(groupCategoryNotificationDefaults1).isNotEqualTo(groupCategoryNotificationDefaults2);
    }
}
