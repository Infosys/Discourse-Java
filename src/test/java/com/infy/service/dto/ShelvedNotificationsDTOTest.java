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

public class ShelvedNotificationsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelvedNotificationsDTO.class);
        ShelvedNotificationsDTO shelvedNotificationsDTO1 = new ShelvedNotificationsDTO();
        shelvedNotificationsDTO1.setId(1L);
        ShelvedNotificationsDTO shelvedNotificationsDTO2 = new ShelvedNotificationsDTO();
        assertThat(shelvedNotificationsDTO1).isNotEqualTo(shelvedNotificationsDTO2);
        shelvedNotificationsDTO2.setId(shelvedNotificationsDTO1.getId());
        assertThat(shelvedNotificationsDTO1).isEqualTo(shelvedNotificationsDTO2);
        shelvedNotificationsDTO2.setId(2L);
        assertThat(shelvedNotificationsDTO1).isNotEqualTo(shelvedNotificationsDTO2);
        shelvedNotificationsDTO1.setId(null);
        assertThat(shelvedNotificationsDTO1).isNotEqualTo(shelvedNotificationsDTO2);
    }
}
