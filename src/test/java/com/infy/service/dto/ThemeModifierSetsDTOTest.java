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

public class ThemeModifierSetsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeModifierSetsDTO.class);
        ThemeModifierSetsDTO themeModifierSetsDTO1 = new ThemeModifierSetsDTO();
        themeModifierSetsDTO1.setId(1L);
        ThemeModifierSetsDTO themeModifierSetsDTO2 = new ThemeModifierSetsDTO();
        assertThat(themeModifierSetsDTO1).isNotEqualTo(themeModifierSetsDTO2);
        themeModifierSetsDTO2.setId(themeModifierSetsDTO1.getId());
        assertThat(themeModifierSetsDTO1).isEqualTo(themeModifierSetsDTO2);
        themeModifierSetsDTO2.setId(2L);
        assertThat(themeModifierSetsDTO1).isNotEqualTo(themeModifierSetsDTO2);
        themeModifierSetsDTO1.setId(null);
        assertThat(themeModifierSetsDTO1).isNotEqualTo(themeModifierSetsDTO2);
    }
}
