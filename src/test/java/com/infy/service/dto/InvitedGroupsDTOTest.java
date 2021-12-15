/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class InvitedGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitedGroupsDTO.class);
        InvitedGroupsDTO invitedGroupsDTO1 = new InvitedGroupsDTO();
        invitedGroupsDTO1.setId(1L);
        InvitedGroupsDTO invitedGroupsDTO2 = new InvitedGroupsDTO();
        assertThat(invitedGroupsDTO1).isNotEqualTo(invitedGroupsDTO2);
        invitedGroupsDTO2.setId(invitedGroupsDTO1.getId());
        assertThat(invitedGroupsDTO1).isEqualTo(invitedGroupsDTO2);
        invitedGroupsDTO2.setId(2L);
        assertThat(invitedGroupsDTO1).isNotEqualTo(invitedGroupsDTO2);
        invitedGroupsDTO1.setId(null);
        assertThat(invitedGroupsDTO1).isNotEqualTo(invitedGroupsDTO2);
    }
}
