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

public class UserNotificationSchedulesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserNotificationSchedules.class);
        UserNotificationSchedules userNotificationSchedules1 = new UserNotificationSchedules();
        userNotificationSchedules1.setId(1L);
        UserNotificationSchedules userNotificationSchedules2 = new UserNotificationSchedules();
        userNotificationSchedules2.setId(userNotificationSchedules1.getId());
        assertThat(userNotificationSchedules1).isEqualTo(userNotificationSchedules2);
        userNotificationSchedules2.setId(2L);
        assertThat(userNotificationSchedules1).isNotEqualTo(userNotificationSchedules2);
        userNotificationSchedules1.setId(null);
        assertThat(userNotificationSchedules1).isNotEqualTo(userNotificationSchedules2);
    }
}
