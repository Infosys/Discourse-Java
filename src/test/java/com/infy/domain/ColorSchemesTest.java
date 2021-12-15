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

public class ColorSchemesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColorSchemes.class);
        ColorSchemes colorSchemes1 = new ColorSchemes();
        colorSchemes1.setId(1L);
        ColorSchemes colorSchemes2 = new ColorSchemes();
        colorSchemes2.setId(colorSchemes1.getId());
        assertThat(colorSchemes1).isEqualTo(colorSchemes2);
        colorSchemes2.setId(2L);
        assertThat(colorSchemes1).isNotEqualTo(colorSchemes2);
        colorSchemes1.setId(null);
        assertThat(colorSchemes1).isNotEqualTo(colorSchemes2);
    }
}
