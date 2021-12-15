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

public class InvitedUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitedUsersDTO.class);
        InvitedUsersDTO invitedUsersDTO1 = new InvitedUsersDTO();
        invitedUsersDTO1.setId(1L);
        InvitedUsersDTO invitedUsersDTO2 = new InvitedUsersDTO();
        assertThat(invitedUsersDTO1).isNotEqualTo(invitedUsersDTO2);
        invitedUsersDTO2.setId(invitedUsersDTO1.getId());
        assertThat(invitedUsersDTO1).isEqualTo(invitedUsersDTO2);
        invitedUsersDTO2.setId(2L);
        assertThat(invitedUsersDTO1).isNotEqualTo(invitedUsersDTO2);
        invitedUsersDTO1.setId(null);
        assertThat(invitedUsersDTO1).isNotEqualTo(invitedUsersDTO2);
    }
}
