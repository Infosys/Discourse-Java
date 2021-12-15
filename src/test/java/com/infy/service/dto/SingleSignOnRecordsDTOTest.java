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

public class SingleSignOnRecordsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SingleSignOnRecordsDTO.class);
        SingleSignOnRecordsDTO singleSignOnRecordsDTO1 = new SingleSignOnRecordsDTO();
        singleSignOnRecordsDTO1.setId(1L);
        SingleSignOnRecordsDTO singleSignOnRecordsDTO2 = new SingleSignOnRecordsDTO();
        assertThat(singleSignOnRecordsDTO1).isNotEqualTo(singleSignOnRecordsDTO2);
        singleSignOnRecordsDTO2.setId(singleSignOnRecordsDTO1.getId());
        assertThat(singleSignOnRecordsDTO1).isEqualTo(singleSignOnRecordsDTO2);
        singleSignOnRecordsDTO2.setId(2L);
        assertThat(singleSignOnRecordsDTO1).isNotEqualTo(singleSignOnRecordsDTO2);
        singleSignOnRecordsDTO1.setId(null);
        assertThat(singleSignOnRecordsDTO1).isNotEqualTo(singleSignOnRecordsDTO2);
    }
}
