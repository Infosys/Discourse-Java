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

public class StylesheetCacheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StylesheetCache.class);
        StylesheetCache stylesheetCache1 = new StylesheetCache();
        stylesheetCache1.setId(1L);
        StylesheetCache stylesheetCache2 = new StylesheetCache();
        stylesheetCache2.setId(stylesheetCache1.getId());
        assertThat(stylesheetCache1).isEqualTo(stylesheetCache2);
        stylesheetCache2.setId(2L);
        assertThat(stylesheetCache1).isNotEqualTo(stylesheetCache2);
        stylesheetCache1.setId(null);
        assertThat(stylesheetCache1).isNotEqualTo(stylesheetCache2);
    }
}
