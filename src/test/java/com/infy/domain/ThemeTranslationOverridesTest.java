/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class ThemeTranslationOverridesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeTranslationOverrides.class);
        ThemeTranslationOverrides themeTranslationOverrides1 = new ThemeTranslationOverrides();
        themeTranslationOverrides1.setId(1L);
        ThemeTranslationOverrides themeTranslationOverrides2 = new ThemeTranslationOverrides();
        themeTranslationOverrides2.setId(themeTranslationOverrides1.getId());
        assertThat(themeTranslationOverrides1).isEqualTo(themeTranslationOverrides2);
        themeTranslationOverrides2.setId(2L);
        assertThat(themeTranslationOverrides1).isNotEqualTo(themeTranslationOverrides2);
        themeTranslationOverrides1.setId(null);
        assertThat(themeTranslationOverrides1).isNotEqualTo(themeTranslationOverrides2);
    }
}
