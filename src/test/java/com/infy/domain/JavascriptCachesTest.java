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

public class JavascriptCachesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JavascriptCaches.class);
        JavascriptCaches javascriptCaches1 = new JavascriptCaches();
        javascriptCaches1.setId(1L);
        JavascriptCaches javascriptCaches2 = new JavascriptCaches();
        javascriptCaches2.setId(javascriptCaches1.getId());
        assertThat(javascriptCaches1).isEqualTo(javascriptCaches2);
        javascriptCaches2.setId(2L);
        assertThat(javascriptCaches1).isNotEqualTo(javascriptCaches2);
        javascriptCaches1.setId(null);
        assertThat(javascriptCaches1).isNotEqualTo(javascriptCaches2);
    }
}
