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

public class ThemesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Themes.class);
        Themes themes1 = new Themes();
        themes1.setId(1L);
        Themes themes2 = new Themes();
        themes2.setId(themes1.getId());
        assertThat(themes1).isEqualTo(themes2);
        themes2.setId(2L);
        assertThat(themes1).isNotEqualTo(themes2);
        themes1.setId(null);
        assertThat(themes1).isNotEqualTo(themes2);
    }
}
