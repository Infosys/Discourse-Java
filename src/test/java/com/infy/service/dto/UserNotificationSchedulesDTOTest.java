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

public class UserNotificationSchedulesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserNotificationSchedulesDTO.class);
        UserNotificationSchedulesDTO userNotificationSchedulesDTO1 = new UserNotificationSchedulesDTO();
        userNotificationSchedulesDTO1.setId(1L);
        UserNotificationSchedulesDTO userNotificationSchedulesDTO2 = new UserNotificationSchedulesDTO();
        assertThat(userNotificationSchedulesDTO1).isNotEqualTo(userNotificationSchedulesDTO2);
        userNotificationSchedulesDTO2.setId(userNotificationSchedulesDTO1.getId());
        assertThat(userNotificationSchedulesDTO1).isEqualTo(userNotificationSchedulesDTO2);
        userNotificationSchedulesDTO2.setId(2L);
        assertThat(userNotificationSchedulesDTO1).isNotEqualTo(userNotificationSchedulesDTO2);
        userNotificationSchedulesDTO1.setId(null);
        assertThat(userNotificationSchedulesDTO1).isNotEqualTo(userNotificationSchedulesDTO2);
    }
}
