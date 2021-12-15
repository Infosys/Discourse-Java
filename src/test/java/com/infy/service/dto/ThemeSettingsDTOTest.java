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

public class ThemeSettingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeSettingsDTO.class);
        ThemeSettingsDTO themeSettingsDTO1 = new ThemeSettingsDTO();
        themeSettingsDTO1.setId(1L);
        ThemeSettingsDTO themeSettingsDTO2 = new ThemeSettingsDTO();
        assertThat(themeSettingsDTO1).isNotEqualTo(themeSettingsDTO2);
        themeSettingsDTO2.setId(themeSettingsDTO1.getId());
        assertThat(themeSettingsDTO1).isEqualTo(themeSettingsDTO2);
        themeSettingsDTO2.setId(2L);
        assertThat(themeSettingsDTO1).isNotEqualTo(themeSettingsDTO2);
        themeSettingsDTO1.setId(null);
        assertThat(themeSettingsDTO1).isNotEqualTo(themeSettingsDTO2);
    }
}
