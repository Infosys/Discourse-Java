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

public class GroupRequestsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupRequestsDTO.class);
        GroupRequestsDTO groupRequestsDTO1 = new GroupRequestsDTO();
        groupRequestsDTO1.setId(1L);
        GroupRequestsDTO groupRequestsDTO2 = new GroupRequestsDTO();
        assertThat(groupRequestsDTO1).isNotEqualTo(groupRequestsDTO2);
        groupRequestsDTO2.setId(groupRequestsDTO1.getId());
        assertThat(groupRequestsDTO1).isEqualTo(groupRequestsDTO2);
        groupRequestsDTO2.setId(2L);
        assertThat(groupRequestsDTO1).isNotEqualTo(groupRequestsDTO2);
        groupRequestsDTO1.setId(null);
        assertThat(groupRequestsDTO1).isNotEqualTo(groupRequestsDTO2);
    }
}
