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

public class IncomingLinksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingLinksDTO.class);
        IncomingLinksDTO incomingLinksDTO1 = new IncomingLinksDTO();
        incomingLinksDTO1.setId(1L);
        IncomingLinksDTO incomingLinksDTO2 = new IncomingLinksDTO();
        assertThat(incomingLinksDTO1).isNotEqualTo(incomingLinksDTO2);
        incomingLinksDTO2.setId(incomingLinksDTO1.getId());
        assertThat(incomingLinksDTO1).isEqualTo(incomingLinksDTO2);
        incomingLinksDTO2.setId(2L);
        assertThat(incomingLinksDTO1).isNotEqualTo(incomingLinksDTO2);
        incomingLinksDTO1.setId(null);
        assertThat(incomingLinksDTO1).isNotEqualTo(incomingLinksDTO2);
    }
}
