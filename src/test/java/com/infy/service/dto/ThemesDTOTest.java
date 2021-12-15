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

public class ThemesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemesDTO.class);
        ThemesDTO themesDTO1 = new ThemesDTO();
        themesDTO1.setId(1L);
        ThemesDTO themesDTO2 = new ThemesDTO();
        assertThat(themesDTO1).isNotEqualTo(themesDTO2);
        themesDTO2.setId(themesDTO1.getId());
        assertThat(themesDTO1).isEqualTo(themesDTO2);
        themesDTO2.setId(2L);
        assertThat(themesDTO1).isNotEqualTo(themesDTO2);
        themesDTO1.setId(null);
        assertThat(themesDTO1).isNotEqualTo(themesDTO2);
    }
}
