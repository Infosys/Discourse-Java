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

public class UserStatsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStatsDTO.class);
        UserStatsDTO userStatsDTO1 = new UserStatsDTO();
        userStatsDTO1.setId(1L);
        UserStatsDTO userStatsDTO2 = new UserStatsDTO();
        assertThat(userStatsDTO1).isNotEqualTo(userStatsDTO2);
        userStatsDTO2.setId(userStatsDTO1.getId());
        assertThat(userStatsDTO1).isEqualTo(userStatsDTO2);
        userStatsDTO2.setId(2L);
        assertThat(userStatsDTO1).isNotEqualTo(userStatsDTO2);
        userStatsDTO1.setId(null);
        assertThat(userStatsDTO1).isNotEqualTo(userStatsDTO2);
    }
}
