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

public class DoNotDisturbTimingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoNotDisturbTimingsDTO.class);
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO1 = new DoNotDisturbTimingsDTO();
        doNotDisturbTimingsDTO1.setId(1L);
        DoNotDisturbTimingsDTO doNotDisturbTimingsDTO2 = new DoNotDisturbTimingsDTO();
        assertThat(doNotDisturbTimingsDTO1).isNotEqualTo(doNotDisturbTimingsDTO2);
        doNotDisturbTimingsDTO2.setId(doNotDisturbTimingsDTO1.getId());
        assertThat(doNotDisturbTimingsDTO1).isEqualTo(doNotDisturbTimingsDTO2);
        doNotDisturbTimingsDTO2.setId(2L);
        assertThat(doNotDisturbTimingsDTO1).isNotEqualTo(doNotDisturbTimingsDTO2);
        doNotDisturbTimingsDTO1.setId(null);
        assertThat(doNotDisturbTimingsDTO1).isNotEqualTo(doNotDisturbTimingsDTO2);
    }
}
