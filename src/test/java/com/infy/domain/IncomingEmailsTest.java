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

public class IncomingEmailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingEmails.class);
        IncomingEmails incomingEmails1 = new IncomingEmails();
        incomingEmails1.setId(1L);
        IncomingEmails incomingEmails2 = new IncomingEmails();
        incomingEmails2.setId(incomingEmails1.getId());
        assertThat(incomingEmails1).isEqualTo(incomingEmails2);
        incomingEmails2.setId(2L);
        assertThat(incomingEmails1).isNotEqualTo(incomingEmails2);
        incomingEmails1.setId(null);
        assertThat(incomingEmails1).isNotEqualTo(incomingEmails2);
    }
}
