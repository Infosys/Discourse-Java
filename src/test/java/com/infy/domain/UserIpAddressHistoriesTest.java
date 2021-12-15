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

public class UserIpAddressHistoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserIpAddressHistories.class);
        UserIpAddressHistories userIpAddressHistories1 = new UserIpAddressHistories();
        userIpAddressHistories1.setId(1L);
        UserIpAddressHistories userIpAddressHistories2 = new UserIpAddressHistories();
        userIpAddressHistories2.setId(userIpAddressHistories1.getId());
        assertThat(userIpAddressHistories1).isEqualTo(userIpAddressHistories2);
        userIpAddressHistories2.setId(2L);
        assertThat(userIpAddressHistories1).isNotEqualTo(userIpAddressHistories2);
        userIpAddressHistories1.setId(null);
        assertThat(userIpAddressHistories1).isNotEqualTo(userIpAddressHistories2);
    }
}
