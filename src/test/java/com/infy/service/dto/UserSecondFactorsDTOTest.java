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

public class UserSecondFactorsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSecondFactorsDTO.class);
        UserSecondFactorsDTO userSecondFactorsDTO1 = new UserSecondFactorsDTO();
        userSecondFactorsDTO1.setId(1L);
        UserSecondFactorsDTO userSecondFactorsDTO2 = new UserSecondFactorsDTO();
        assertThat(userSecondFactorsDTO1).isNotEqualTo(userSecondFactorsDTO2);
        userSecondFactorsDTO2.setId(userSecondFactorsDTO1.getId());
        assertThat(userSecondFactorsDTO1).isEqualTo(userSecondFactorsDTO2);
        userSecondFactorsDTO2.setId(2L);
        assertThat(userSecondFactorsDTO1).isNotEqualTo(userSecondFactorsDTO2);
        userSecondFactorsDTO1.setId(null);
        assertThat(userSecondFactorsDTO1).isNotEqualTo(userSecondFactorsDTO2);
    }
}
