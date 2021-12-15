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

public class GroupHistoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupHistoriesDTO.class);
        GroupHistoriesDTO groupHistoriesDTO1 = new GroupHistoriesDTO();
        groupHistoriesDTO1.setId(1L);
        GroupHistoriesDTO groupHistoriesDTO2 = new GroupHistoriesDTO();
        assertThat(groupHistoriesDTO1).isNotEqualTo(groupHistoriesDTO2);
        groupHistoriesDTO2.setId(groupHistoriesDTO1.getId());
        assertThat(groupHistoriesDTO1).isEqualTo(groupHistoriesDTO2);
        groupHistoriesDTO2.setId(2L);
        assertThat(groupHistoriesDTO1).isNotEqualTo(groupHistoriesDTO2);
        groupHistoriesDTO1.setId(null);
        assertThat(groupHistoriesDTO1).isNotEqualTo(groupHistoriesDTO2);
    }
}
