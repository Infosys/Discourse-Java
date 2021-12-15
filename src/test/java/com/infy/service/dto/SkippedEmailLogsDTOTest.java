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

public class SkippedEmailLogsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkippedEmailLogsDTO.class);
        SkippedEmailLogsDTO skippedEmailLogsDTO1 = new SkippedEmailLogsDTO();
        skippedEmailLogsDTO1.setId(1L);
        SkippedEmailLogsDTO skippedEmailLogsDTO2 = new SkippedEmailLogsDTO();
        assertThat(skippedEmailLogsDTO1).isNotEqualTo(skippedEmailLogsDTO2);
        skippedEmailLogsDTO2.setId(skippedEmailLogsDTO1.getId());
        assertThat(skippedEmailLogsDTO1).isEqualTo(skippedEmailLogsDTO2);
        skippedEmailLogsDTO2.setId(2L);
        assertThat(skippedEmailLogsDTO1).isNotEqualTo(skippedEmailLogsDTO2);
        skippedEmailLogsDTO1.setId(null);
        assertThat(skippedEmailLogsDTO1).isNotEqualTo(skippedEmailLogsDTO2);
    }
}
