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

public class ChildThemesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildThemes.class);
        ChildThemes childThemes1 = new ChildThemes();
        childThemes1.setId(1L);
        ChildThemes childThemes2 = new ChildThemes();
        childThemes2.setId(childThemes1.getId());
        assertThat(childThemes1).isEqualTo(childThemes2);
        childThemes2.setId(2L);
        assertThat(childThemes1).isNotEqualTo(childThemes2);
        childThemes1.setId(null);
        assertThat(childThemes1).isNotEqualTo(childThemes2);
    }
}
