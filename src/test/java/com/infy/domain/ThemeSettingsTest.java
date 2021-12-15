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

public class ThemeSettingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeSettings.class);
        ThemeSettings themeSettings1 = new ThemeSettings();
        themeSettings1.setId(1L);
        ThemeSettings themeSettings2 = new ThemeSettings();
        themeSettings2.setId(themeSettings1.getId());
        assertThat(themeSettings1).isEqualTo(themeSettings2);
        themeSettings2.setId(2L);
        assertThat(themeSettings1).isNotEqualTo(themeSettings2);
        themeSettings1.setId(null);
        assertThat(themeSettings1).isNotEqualTo(themeSettings2);
    }
}
