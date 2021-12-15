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

public class InvitedGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitedGroups.class);
        InvitedGroups invitedGroups1 = new InvitedGroups();
        invitedGroups1.setId(1L);
        InvitedGroups invitedGroups2 = new InvitedGroups();
        invitedGroups2.setId(invitedGroups1.getId());
        assertThat(invitedGroups1).isEqualTo(invitedGroups2);
        invitedGroups2.setId(2L);
        assertThat(invitedGroups1).isNotEqualTo(invitedGroups2);
        invitedGroups1.setId(null);
        assertThat(invitedGroups1).isNotEqualTo(invitedGroups2);
    }
}
