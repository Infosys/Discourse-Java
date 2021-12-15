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

public class UserVisitsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVisitsDTO.class);
        UserVisitsDTO userVisitsDTO1 = new UserVisitsDTO();
        userVisitsDTO1.setId(1L);
        UserVisitsDTO userVisitsDTO2 = new UserVisitsDTO();
        assertThat(userVisitsDTO1).isNotEqualTo(userVisitsDTO2);
        userVisitsDTO2.setId(userVisitsDTO1.getId());
        assertThat(userVisitsDTO1).isEqualTo(userVisitsDTO2);
        userVisitsDTO2.setId(2L);
        assertThat(userVisitsDTO1).isNotEqualTo(userVisitsDTO2);
        userVisitsDTO1.setId(null);
        assertThat(userVisitsDTO1).isNotEqualTo(userVisitsDTO2);
    }
}
