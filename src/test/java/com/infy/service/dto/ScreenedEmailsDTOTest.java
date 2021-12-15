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

public class ScreenedEmailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenedEmailsDTO.class);
        ScreenedEmailsDTO screenedEmailsDTO1 = new ScreenedEmailsDTO();
        screenedEmailsDTO1.setId(1L);
        ScreenedEmailsDTO screenedEmailsDTO2 = new ScreenedEmailsDTO();
        assertThat(screenedEmailsDTO1).isNotEqualTo(screenedEmailsDTO2);
        screenedEmailsDTO2.setId(screenedEmailsDTO1.getId());
        assertThat(screenedEmailsDTO1).isEqualTo(screenedEmailsDTO2);
        screenedEmailsDTO2.setId(2L);
        assertThat(screenedEmailsDTO1).isNotEqualTo(screenedEmailsDTO2);
        screenedEmailsDTO1.setId(null);
        assertThat(screenedEmailsDTO1).isNotEqualTo(screenedEmailsDTO2);
    }
}
