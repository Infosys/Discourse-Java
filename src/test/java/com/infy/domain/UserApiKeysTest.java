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

public class UserApiKeysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApiKeys.class);
        UserApiKeys userApiKeys1 = new UserApiKeys();
        userApiKeys1.setId(1L);
        UserApiKeys userApiKeys2 = new UserApiKeys();
        userApiKeys2.setId(userApiKeys1.getId());
        assertThat(userApiKeys1).isEqualTo(userApiKeys2);
        userApiKeys2.setId(2L);
        assertThat(userApiKeys1).isNotEqualTo(userApiKeys2);
        userApiKeys1.setId(null);
        assertThat(userApiKeys1).isNotEqualTo(userApiKeys2);
    }
}
