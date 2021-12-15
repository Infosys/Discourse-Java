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

public class ScreenedEmailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenedEmails.class);
        ScreenedEmails screenedEmails1 = new ScreenedEmails();
        screenedEmails1.setId(1L);
        ScreenedEmails screenedEmails2 = new ScreenedEmails();
        screenedEmails2.setId(screenedEmails1.getId());
        assertThat(screenedEmails1).isEqualTo(screenedEmails2);
        screenedEmails2.setId(2L);
        assertThat(screenedEmails1).isNotEqualTo(screenedEmails2);
        screenedEmails1.setId(null);
        assertThat(screenedEmails1).isNotEqualTo(screenedEmails2);
    }
}
