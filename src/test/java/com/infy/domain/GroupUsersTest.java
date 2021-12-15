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

public class GroupUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupUsers.class);
        GroupUsers groupUsers1 = new GroupUsers();
        groupUsers1.setId(1L);
        GroupUsers groupUsers2 = new GroupUsers();
        groupUsers2.setId(groupUsers1.getId());
        assertThat(groupUsers1).isEqualTo(groupUsers2);
        groupUsers2.setId(2L);
        assertThat(groupUsers1).isNotEqualTo(groupUsers2);
        groupUsers1.setId(null);
        assertThat(groupUsers1).isNotEqualTo(groupUsers2);
    }
}
