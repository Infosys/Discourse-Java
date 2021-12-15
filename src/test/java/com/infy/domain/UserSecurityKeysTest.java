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

public class UserSecurityKeysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSecurityKeys.class);
        UserSecurityKeys userSecurityKeys1 = new UserSecurityKeys();
        userSecurityKeys1.setId(1L);
        UserSecurityKeys userSecurityKeys2 = new UserSecurityKeys();
        userSecurityKeys2.setId(userSecurityKeys1.getId());
        assertThat(userSecurityKeys1).isEqualTo(userSecurityKeys2);
        userSecurityKeys2.setId(2L);
        assertThat(userSecurityKeys1).isNotEqualTo(userSecurityKeys2);
        userSecurityKeys1.setId(null);
        assertThat(userSecurityKeys1).isNotEqualTo(userSecurityKeys2);
    }
}
