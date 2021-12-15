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

public class ScreenedIpAddressesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenedIpAddresses.class);
        ScreenedIpAddresses screenedIpAddresses1 = new ScreenedIpAddresses();
        screenedIpAddresses1.setId(1L);
        ScreenedIpAddresses screenedIpAddresses2 = new ScreenedIpAddresses();
        screenedIpAddresses2.setId(screenedIpAddresses1.getId());
        assertThat(screenedIpAddresses1).isEqualTo(screenedIpAddresses2);
        screenedIpAddresses2.setId(2L);
        assertThat(screenedIpAddresses1).isNotEqualTo(screenedIpAddresses2);
        screenedIpAddresses1.setId(null);
        assertThat(screenedIpAddresses1).isNotEqualTo(screenedIpAddresses2);
    }
}
