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

public class SkippedEmailLogsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkippedEmailLogs.class);
        SkippedEmailLogs skippedEmailLogs1 = new SkippedEmailLogs();
        skippedEmailLogs1.setId(1L);
        SkippedEmailLogs skippedEmailLogs2 = new SkippedEmailLogs();
        skippedEmailLogs2.setId(skippedEmailLogs1.getId());
        assertThat(skippedEmailLogs1).isEqualTo(skippedEmailLogs2);
        skippedEmailLogs2.setId(2L);
        assertThat(skippedEmailLogs1).isNotEqualTo(skippedEmailLogs2);
        skippedEmailLogs1.setId(null);
        assertThat(skippedEmailLogs1).isNotEqualTo(skippedEmailLogs2);
    }
}
