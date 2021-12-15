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

public class PushSubscriptionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PushSubscriptionsDTO.class);
        PushSubscriptionsDTO pushSubscriptionsDTO1 = new PushSubscriptionsDTO();
        pushSubscriptionsDTO1.setId(1L);
        PushSubscriptionsDTO pushSubscriptionsDTO2 = new PushSubscriptionsDTO();
        assertThat(pushSubscriptionsDTO1).isNotEqualTo(pushSubscriptionsDTO2);
        pushSubscriptionsDTO2.setId(pushSubscriptionsDTO1.getId());
        assertThat(pushSubscriptionsDTO1).isEqualTo(pushSubscriptionsDTO2);
        pushSubscriptionsDTO2.setId(2L);
        assertThat(pushSubscriptionsDTO1).isNotEqualTo(pushSubscriptionsDTO2);
        pushSubscriptionsDTO1.setId(null);
        assertThat(pushSubscriptionsDTO1).isNotEqualTo(pushSubscriptionsDTO2);
    }
}
