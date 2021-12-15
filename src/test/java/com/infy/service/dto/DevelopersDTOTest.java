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

public class DevelopersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevelopersDTO.class);
        DevelopersDTO developersDTO1 = new DevelopersDTO();
        developersDTO1.setId(1L);
        DevelopersDTO developersDTO2 = new DevelopersDTO();
        assertThat(developersDTO1).isNotEqualTo(developersDTO2);
        developersDTO2.setId(developersDTO1.getId());
        assertThat(developersDTO1).isEqualTo(developersDTO2);
        developersDTO2.setId(2L);
        assertThat(developersDTO1).isNotEqualTo(developersDTO2);
        developersDTO1.setId(null);
        assertThat(developersDTO1).isNotEqualTo(developersDTO2);
    }
}
