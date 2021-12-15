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

public class ColorSchemeColorsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColorSchemeColors.class);
        ColorSchemeColors colorSchemeColors1 = new ColorSchemeColors();
        colorSchemeColors1.setId(1L);
        ColorSchemeColors colorSchemeColors2 = new ColorSchemeColors();
        colorSchemeColors2.setId(colorSchemeColors1.getId());
        assertThat(colorSchemeColors1).isEqualTo(colorSchemeColors2);
        colorSchemeColors2.setId(2L);
        assertThat(colorSchemeColors1).isNotEqualTo(colorSchemeColors2);
        colorSchemeColors1.setId(null);
        assertThat(colorSchemeColors1).isNotEqualTo(colorSchemeColors2);
    }
}
