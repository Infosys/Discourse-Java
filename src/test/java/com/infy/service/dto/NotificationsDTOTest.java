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

public class NotificationsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationsDTO.class);
        NotificationsDTO notificationsDTO1 = new NotificationsDTO();
        notificationsDTO1.setId(1L);
        NotificationsDTO notificationsDTO2 = new NotificationsDTO();
        assertThat(notificationsDTO1).isNotEqualTo(notificationsDTO2);
        notificationsDTO2.setId(notificationsDTO1.getId());
        assertThat(notificationsDTO1).isEqualTo(notificationsDTO2);
        notificationsDTO2.setId(2L);
        assertThat(notificationsDTO1).isNotEqualTo(notificationsDTO2);
        notificationsDTO1.setId(null);
        assertThat(notificationsDTO1).isNotEqualTo(notificationsDTO2);
    }
}
