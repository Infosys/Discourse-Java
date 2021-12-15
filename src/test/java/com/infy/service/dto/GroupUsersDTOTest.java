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

public class GroupUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupUsersDTO.class);
        GroupUsersDTO groupUsersDTO1 = new GroupUsersDTO();
        groupUsersDTO1.setId(1L);
        GroupUsersDTO groupUsersDTO2 = new GroupUsersDTO();
        assertThat(groupUsersDTO1).isNotEqualTo(groupUsersDTO2);
        groupUsersDTO2.setId(groupUsersDTO1.getId());
        assertThat(groupUsersDTO1).isEqualTo(groupUsersDTO2);
        groupUsersDTO2.setId(2L);
        assertThat(groupUsersDTO1).isNotEqualTo(groupUsersDTO2);
        groupUsersDTO1.setId(null);
        assertThat(groupUsersDTO1).isNotEqualTo(groupUsersDTO2);
    }
}
