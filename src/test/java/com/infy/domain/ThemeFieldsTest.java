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

public class ThemeFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeFields.class);
        ThemeFields themeFields1 = new ThemeFields();
        themeFields1.setId(1L);
        ThemeFields themeFields2 = new ThemeFields();
        themeFields2.setId(themeFields1.getId());
        assertThat(themeFields1).isEqualTo(themeFields2);
        themeFields2.setId(2L);
        assertThat(themeFields1).isNotEqualTo(themeFields2);
        themeFields1.setId(null);
        assertThat(themeFields1).isNotEqualTo(themeFields2);
    }
}
