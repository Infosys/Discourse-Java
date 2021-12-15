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

public class ScreenedIpAddressesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenedIpAddressesDTO.class);
        ScreenedIpAddressesDTO screenedIpAddressesDTO1 = new ScreenedIpAddressesDTO();
        screenedIpAddressesDTO1.setId(1L);
        ScreenedIpAddressesDTO screenedIpAddressesDTO2 = new ScreenedIpAddressesDTO();
        assertThat(screenedIpAddressesDTO1).isNotEqualTo(screenedIpAddressesDTO2);
        screenedIpAddressesDTO2.setId(screenedIpAddressesDTO1.getId());
        assertThat(screenedIpAddressesDTO1).isEqualTo(screenedIpAddressesDTO2);
        screenedIpAddressesDTO2.setId(2L);
        assertThat(screenedIpAddressesDTO1).isNotEqualTo(screenedIpAddressesDTO2);
        screenedIpAddressesDTO1.setId(null);
        assertThat(screenedIpAddressesDTO1).isNotEqualTo(screenedIpAddressesDTO2);
    }
}
