/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class SingleSignOnRecordsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SingleSignOnRecords.class);
        SingleSignOnRecords singleSignOnRecords1 = new SingleSignOnRecords();
        singleSignOnRecords1.setId(1L);
        SingleSignOnRecords singleSignOnRecords2 = new SingleSignOnRecords();
        singleSignOnRecords2.setId(singleSignOnRecords1.getId());
        assertThat(singleSignOnRecords1).isEqualTo(singleSignOnRecords2);
        singleSignOnRecords2.setId(2L);
        assertThat(singleSignOnRecords1).isNotEqualTo(singleSignOnRecords2);
        singleSignOnRecords1.setId(null);
        assertThat(singleSignOnRecords1).isNotEqualTo(singleSignOnRecords2);
    }
}
