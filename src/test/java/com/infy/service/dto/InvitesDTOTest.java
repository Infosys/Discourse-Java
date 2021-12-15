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

public class InvitesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitesDTO.class);
        InvitesDTO invitesDTO1 = new InvitesDTO();
        invitesDTO1.setId(1L);
        InvitesDTO invitesDTO2 = new InvitesDTO();
        assertThat(invitesDTO1).isNotEqualTo(invitesDTO2);
        invitesDTO2.setId(invitesDTO1.getId());
        assertThat(invitesDTO1).isEqualTo(invitesDTO2);
        invitesDTO2.setId(2L);
        assertThat(invitesDTO1).isNotEqualTo(invitesDTO2);
        invitesDTO1.setId(null);
        assertThat(invitesDTO1).isNotEqualTo(invitesDTO2);
    }
}
