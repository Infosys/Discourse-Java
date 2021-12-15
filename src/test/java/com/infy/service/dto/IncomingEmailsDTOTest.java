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

public class IncomingEmailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingEmailsDTO.class);
        IncomingEmailsDTO incomingEmailsDTO1 = new IncomingEmailsDTO();
        incomingEmailsDTO1.setId(1L);
        IncomingEmailsDTO incomingEmailsDTO2 = new IncomingEmailsDTO();
        assertThat(incomingEmailsDTO1).isNotEqualTo(incomingEmailsDTO2);
        incomingEmailsDTO2.setId(incomingEmailsDTO1.getId());
        assertThat(incomingEmailsDTO1).isEqualTo(incomingEmailsDTO2);
        incomingEmailsDTO2.setId(2L);
        assertThat(incomingEmailsDTO1).isNotEqualTo(incomingEmailsDTO2);
        incomingEmailsDTO1.setId(null);
        assertThat(incomingEmailsDTO1).isNotEqualTo(incomingEmailsDTO2);
    }
}
