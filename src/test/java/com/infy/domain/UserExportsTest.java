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

public class UserExportsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExports.class);
        UserExports userExports1 = new UserExports();
        userExports1.setId(1L);
        UserExports userExports2 = new UserExports();
        userExports2.setId(userExports1.getId());
        assertThat(userExports1).isEqualTo(userExports2);
        userExports2.setId(2L);
        assertThat(userExports1).isNotEqualTo(userExports2);
        userExports1.setId(null);
        assertThat(userExports1).isNotEqualTo(userExports2);
    }
}
