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

public class EmailLogsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailLogs.class);
        EmailLogs emailLogs1 = new EmailLogs();
        emailLogs1.setId(1L);
        EmailLogs emailLogs2 = new EmailLogs();
        emailLogs2.setId(emailLogs1.getId());
        assertThat(emailLogs1).isEqualTo(emailLogs2);
        emailLogs2.setId(2L);
        assertThat(emailLogs1).isNotEqualTo(emailLogs2);
        emailLogs1.setId(null);
        assertThat(emailLogs1).isNotEqualTo(emailLogs2);
    }
}
