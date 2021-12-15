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

public class ThemeModifierSetsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeModifierSets.class);
        ThemeModifierSets themeModifierSets1 = new ThemeModifierSets();
        themeModifierSets1.setId(1L);
        ThemeModifierSets themeModifierSets2 = new ThemeModifierSets();
        themeModifierSets2.setId(themeModifierSets1.getId());
        assertThat(themeModifierSets1).isEqualTo(themeModifierSets2);
        themeModifierSets2.setId(2L);
        assertThat(themeModifierSets1).isNotEqualTo(themeModifierSets2);
        themeModifierSets1.setId(null);
        assertThat(themeModifierSets1).isNotEqualTo(themeModifierSets2);
    }
}
