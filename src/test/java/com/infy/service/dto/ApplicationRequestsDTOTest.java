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

public class ApplicationRequestsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationRequestsDTO.class);
        ApplicationRequestsDTO applicationRequestsDTO1 = new ApplicationRequestsDTO();
        applicationRequestsDTO1.setId(1L);
        ApplicationRequestsDTO applicationRequestsDTO2 = new ApplicationRequestsDTO();
        assertThat(applicationRequestsDTO1).isNotEqualTo(applicationRequestsDTO2);
        applicationRequestsDTO2.setId(applicationRequestsDTO1.getId());
        assertThat(applicationRequestsDTO1).isEqualTo(applicationRequestsDTO2);
        applicationRequestsDTO2.setId(2L);
        assertThat(applicationRequestsDTO1).isNotEqualTo(applicationRequestsDTO2);
        applicationRequestsDTO1.setId(null);
        assertThat(applicationRequestsDTO1).isNotEqualTo(applicationRequestsDTO2);
    }
}
