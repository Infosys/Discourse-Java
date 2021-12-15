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

public class EmailChangeRequestsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailChangeRequestsDTO.class);
        EmailChangeRequestsDTO emailChangeRequestsDTO1 = new EmailChangeRequestsDTO();
        emailChangeRequestsDTO1.setId(1L);
        EmailChangeRequestsDTO emailChangeRequestsDTO2 = new EmailChangeRequestsDTO();
        assertThat(emailChangeRequestsDTO1).isNotEqualTo(emailChangeRequestsDTO2);
        emailChangeRequestsDTO2.setId(emailChangeRequestsDTO1.getId());
        assertThat(emailChangeRequestsDTO1).isEqualTo(emailChangeRequestsDTO2);
        emailChangeRequestsDTO2.setId(2L);
        assertThat(emailChangeRequestsDTO1).isNotEqualTo(emailChangeRequestsDTO2);
        emailChangeRequestsDTO1.setId(null);
        assertThat(emailChangeRequestsDTO1).isNotEqualTo(emailChangeRequestsDTO2);
    }
}
