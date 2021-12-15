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

public class ColorSchemeColorsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColorSchemeColorsDTO.class);
        ColorSchemeColorsDTO colorSchemeColorsDTO1 = new ColorSchemeColorsDTO();
        colorSchemeColorsDTO1.setId(1L);
        ColorSchemeColorsDTO colorSchemeColorsDTO2 = new ColorSchemeColorsDTO();
        assertThat(colorSchemeColorsDTO1).isNotEqualTo(colorSchemeColorsDTO2);
        colorSchemeColorsDTO2.setId(colorSchemeColorsDTO1.getId());
        assertThat(colorSchemeColorsDTO1).isEqualTo(colorSchemeColorsDTO2);
        colorSchemeColorsDTO2.setId(2L);
        assertThat(colorSchemeColorsDTO1).isNotEqualTo(colorSchemeColorsDTO2);
        colorSchemeColorsDTO1.setId(null);
        assertThat(colorSchemeColorsDTO1).isNotEqualTo(colorSchemeColorsDTO2);
    }
}
