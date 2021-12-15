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

public class ChildThemesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildThemesDTO.class);
        ChildThemesDTO childThemesDTO1 = new ChildThemesDTO();
        childThemesDTO1.setId(1L);
        ChildThemesDTO childThemesDTO2 = new ChildThemesDTO();
        assertThat(childThemesDTO1).isNotEqualTo(childThemesDTO2);
        childThemesDTO2.setId(childThemesDTO1.getId());
        assertThat(childThemesDTO1).isEqualTo(childThemesDTO2);
        childThemesDTO2.setId(2L);
        assertThat(childThemesDTO1).isNotEqualTo(childThemesDTO2);
        childThemesDTO1.setId(null);
        assertThat(childThemesDTO1).isNotEqualTo(childThemesDTO2);
    }
}
