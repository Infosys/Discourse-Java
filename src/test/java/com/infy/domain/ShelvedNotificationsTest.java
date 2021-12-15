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

public class ShelvedNotificationsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelvedNotifications.class);
        ShelvedNotifications shelvedNotifications1 = new ShelvedNotifications();
        shelvedNotifications1.setId(1L);
        ShelvedNotifications shelvedNotifications2 = new ShelvedNotifications();
        shelvedNotifications2.setId(shelvedNotifications1.getId());
        assertThat(shelvedNotifications1).isEqualTo(shelvedNotifications2);
        shelvedNotifications2.setId(2L);
        assertThat(shelvedNotifications1).isNotEqualTo(shelvedNotifications2);
        shelvedNotifications1.setId(null);
        assertThat(shelvedNotifications1).isNotEqualTo(shelvedNotifications2);
    }
}
