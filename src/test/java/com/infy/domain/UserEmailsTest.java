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

public class UserEmailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserEmails.class);
        UserEmails userEmails1 = new UserEmails();
        userEmails1.setId(1L);
        UserEmails userEmails2 = new UserEmails();
        userEmails2.setId(userEmails1.getId());
        assertThat(userEmails1).isEqualTo(userEmails2);
        userEmails2.setId(2L);
        assertThat(userEmails1).isNotEqualTo(userEmails2);
        userEmails1.setId(null);
        assertThat(userEmails1).isNotEqualTo(userEmails2);
    }
}
