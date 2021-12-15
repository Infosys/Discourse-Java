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

public class UserHistoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserHistoriesDTO.class);
        UserHistoriesDTO userHistoriesDTO1 = new UserHistoriesDTO();
        userHistoriesDTO1.setId(1L);
        UserHistoriesDTO userHistoriesDTO2 = new UserHistoriesDTO();
        assertThat(userHistoriesDTO1).isNotEqualTo(userHistoriesDTO2);
        userHistoriesDTO2.setId(userHistoriesDTO1.getId());
        assertThat(userHistoriesDTO1).isEqualTo(userHistoriesDTO2);
        userHistoriesDTO2.setId(2L);
        assertThat(userHistoriesDTO1).isNotEqualTo(userHistoriesDTO2);
        userHistoriesDTO1.setId(null);
        assertThat(userHistoriesDTO1).isNotEqualTo(userHistoriesDTO2);
    }
}
