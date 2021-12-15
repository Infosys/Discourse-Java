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

public class IgnoredUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IgnoredUsers.class);
        IgnoredUsers ignoredUsers1 = new IgnoredUsers();
        ignoredUsers1.setId(1L);
        IgnoredUsers ignoredUsers2 = new IgnoredUsers();
        ignoredUsers2.setId(ignoredUsers1.getId());
        assertThat(ignoredUsers1).isEqualTo(ignoredUsers2);
        ignoredUsers2.setId(2L);
        assertThat(ignoredUsers1).isNotEqualTo(ignoredUsers2);
        ignoredUsers1.setId(null);
        assertThat(ignoredUsers1).isNotEqualTo(ignoredUsers2);
    }
}
