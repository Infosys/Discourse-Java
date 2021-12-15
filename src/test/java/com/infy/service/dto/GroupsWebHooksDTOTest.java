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

public class GroupsWebHooksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupsWebHooksDTO.class);
        GroupsWebHooksDTO groupsWebHooksDTO1 = new GroupsWebHooksDTO();
        groupsWebHooksDTO1.setId(1L);
        GroupsWebHooksDTO groupsWebHooksDTO2 = new GroupsWebHooksDTO();
        assertThat(groupsWebHooksDTO1).isNotEqualTo(groupsWebHooksDTO2);
        groupsWebHooksDTO2.setId(groupsWebHooksDTO1.getId());
        assertThat(groupsWebHooksDTO1).isEqualTo(groupsWebHooksDTO2);
        groupsWebHooksDTO2.setId(2L);
        assertThat(groupsWebHooksDTO1).isNotEqualTo(groupsWebHooksDTO2);
        groupsWebHooksDTO1.setId(null);
        assertThat(groupsWebHooksDTO1).isNotEqualTo(groupsWebHooksDTO2);
    }
}
