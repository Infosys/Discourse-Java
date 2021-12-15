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

public class GroupsWebHooksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupsWebHooks.class);
        GroupsWebHooks groupsWebHooks1 = new GroupsWebHooks();
        groupsWebHooks1.setId(1L);
        GroupsWebHooks groupsWebHooks2 = new GroupsWebHooks();
        groupsWebHooks2.setId(groupsWebHooks1.getId());
        assertThat(groupsWebHooks1).isEqualTo(groupsWebHooks2);
        groupsWebHooks2.setId(2L);
        assertThat(groupsWebHooks1).isNotEqualTo(groupsWebHooks2);
        groupsWebHooks1.setId(null);
        assertThat(groupsWebHooks1).isNotEqualTo(groupsWebHooks2);
    }
}
