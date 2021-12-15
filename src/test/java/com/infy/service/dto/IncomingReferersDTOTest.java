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

public class IncomingReferersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingReferersDTO.class);
        IncomingReferersDTO incomingReferersDTO1 = new IncomingReferersDTO();
        incomingReferersDTO1.setId(1L);
        IncomingReferersDTO incomingReferersDTO2 = new IncomingReferersDTO();
        assertThat(incomingReferersDTO1).isNotEqualTo(incomingReferersDTO2);
        incomingReferersDTO2.setId(incomingReferersDTO1.getId());
        assertThat(incomingReferersDTO1).isEqualTo(incomingReferersDTO2);
        incomingReferersDTO2.setId(2L);
        assertThat(incomingReferersDTO1).isNotEqualTo(incomingReferersDTO2);
        incomingReferersDTO1.setId(null);
        assertThat(incomingReferersDTO1).isNotEqualTo(incomingReferersDTO2);
    }
}
