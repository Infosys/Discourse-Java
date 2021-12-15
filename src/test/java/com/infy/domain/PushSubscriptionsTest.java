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

public class PushSubscriptionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PushSubscriptions.class);
        PushSubscriptions pushSubscriptions1 = new PushSubscriptions();
        pushSubscriptions1.setId(1L);
        PushSubscriptions pushSubscriptions2 = new PushSubscriptions();
        pushSubscriptions2.setId(pushSubscriptions1.getId());
        assertThat(pushSubscriptions1).isEqualTo(pushSubscriptions2);
        pushSubscriptions2.setId(2L);
        assertThat(pushSubscriptions1).isNotEqualTo(pushSubscriptions2);
        pushSubscriptions1.setId(null);
        assertThat(pushSubscriptions1).isNotEqualTo(pushSubscriptions2);
    }
}
