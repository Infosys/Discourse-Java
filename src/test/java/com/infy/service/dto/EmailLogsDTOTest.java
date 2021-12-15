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

public class EmailLogsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailLogsDTO.class);
        EmailLogsDTO emailLogsDTO1 = new EmailLogsDTO();
        emailLogsDTO1.setId(1L);
        EmailLogsDTO emailLogsDTO2 = new EmailLogsDTO();
        assertThat(emailLogsDTO1).isNotEqualTo(emailLogsDTO2);
        emailLogsDTO2.setId(emailLogsDTO1.getId());
        assertThat(emailLogsDTO1).isEqualTo(emailLogsDTO2);
        emailLogsDTO2.setId(2L);
        assertThat(emailLogsDTO1).isNotEqualTo(emailLogsDTO2);
        emailLogsDTO1.setId(null);
        assertThat(emailLogsDTO1).isNotEqualTo(emailLogsDTO2);
    }
}
