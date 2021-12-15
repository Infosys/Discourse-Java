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

public class ColorSchemesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColorSchemesDTO.class);
        ColorSchemesDTO colorSchemesDTO1 = new ColorSchemesDTO();
        colorSchemesDTO1.setId(1L);
        ColorSchemesDTO colorSchemesDTO2 = new ColorSchemesDTO();
        assertThat(colorSchemesDTO1).isNotEqualTo(colorSchemesDTO2);
        colorSchemesDTO2.setId(colorSchemesDTO1.getId());
        assertThat(colorSchemesDTO1).isEqualTo(colorSchemesDTO2);
        colorSchemesDTO2.setId(2L);
        assertThat(colorSchemesDTO1).isNotEqualTo(colorSchemesDTO2);
        colorSchemesDTO1.setId(null);
        assertThat(colorSchemesDTO1).isNotEqualTo(colorSchemesDTO2);
    }
}
