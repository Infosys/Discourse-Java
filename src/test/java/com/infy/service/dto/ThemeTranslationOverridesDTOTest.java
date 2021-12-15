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

public class ThemeTranslationOverridesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeTranslationOverridesDTO.class);
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO1 = new ThemeTranslationOverridesDTO();
        themeTranslationOverridesDTO1.setId(1L);
        ThemeTranslationOverridesDTO themeTranslationOverridesDTO2 = new ThemeTranslationOverridesDTO();
        assertThat(themeTranslationOverridesDTO1).isNotEqualTo(themeTranslationOverridesDTO2);
        themeTranslationOverridesDTO2.setId(themeTranslationOverridesDTO1.getId());
        assertThat(themeTranslationOverridesDTO1).isEqualTo(themeTranslationOverridesDTO2);
        themeTranslationOverridesDTO2.setId(2L);
        assertThat(themeTranslationOverridesDTO1).isNotEqualTo(themeTranslationOverridesDTO2);
        themeTranslationOverridesDTO1.setId(null);
        assertThat(themeTranslationOverridesDTO1).isNotEqualTo(themeTranslationOverridesDTO2);
    }
}
