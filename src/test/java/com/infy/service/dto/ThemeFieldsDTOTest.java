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

public class ThemeFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeFieldsDTO.class);
        ThemeFieldsDTO themeFieldsDTO1 = new ThemeFieldsDTO();
        themeFieldsDTO1.setId(1L);
        ThemeFieldsDTO themeFieldsDTO2 = new ThemeFieldsDTO();
        assertThat(themeFieldsDTO1).isNotEqualTo(themeFieldsDTO2);
        themeFieldsDTO2.setId(themeFieldsDTO1.getId());
        assertThat(themeFieldsDTO1).isEqualTo(themeFieldsDTO2);
        themeFieldsDTO2.setId(2L);
        assertThat(themeFieldsDTO1).isNotEqualTo(themeFieldsDTO2);
        themeFieldsDTO1.setId(null);
        assertThat(themeFieldsDTO1).isNotEqualTo(themeFieldsDTO2);
    }
}
