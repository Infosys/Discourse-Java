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

public class MutedUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutedUsersDTO.class);
        MutedUsersDTO mutedUsersDTO1 = new MutedUsersDTO();
        mutedUsersDTO1.setId(1L);
        MutedUsersDTO mutedUsersDTO2 = new MutedUsersDTO();
        assertThat(mutedUsersDTO1).isNotEqualTo(mutedUsersDTO2);
        mutedUsersDTO2.setId(mutedUsersDTO1.getId());
        assertThat(mutedUsersDTO1).isEqualTo(mutedUsersDTO2);
        mutedUsersDTO2.setId(2L);
        assertThat(mutedUsersDTO1).isNotEqualTo(mutedUsersDTO2);
        mutedUsersDTO1.setId(null);
        assertThat(mutedUsersDTO1).isNotEqualTo(mutedUsersDTO2);
    }
}
