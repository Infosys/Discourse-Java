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

public class GroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupsDTO.class);
        GroupsDTO groupsDTO1 = new GroupsDTO();
        groupsDTO1.setId(1L);
        GroupsDTO groupsDTO2 = new GroupsDTO();
        assertThat(groupsDTO1).isNotEqualTo(groupsDTO2);
        groupsDTO2.setId(groupsDTO1.getId());
        assertThat(groupsDTO1).isEqualTo(groupsDTO2);
        groupsDTO2.setId(2L);
        assertThat(groupsDTO1).isNotEqualTo(groupsDTO2);
        groupsDTO1.setId(null);
        assertThat(groupsDTO1).isNotEqualTo(groupsDTO2);
    }
}
