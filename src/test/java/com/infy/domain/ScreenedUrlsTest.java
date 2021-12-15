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

public class ScreenedUrlsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenedUrls.class);
        ScreenedUrls screenedUrls1 = new ScreenedUrls();
        screenedUrls1.setId(1L);
        ScreenedUrls screenedUrls2 = new ScreenedUrls();
        screenedUrls2.setId(screenedUrls1.getId());
        assertThat(screenedUrls1).isEqualTo(screenedUrls2);
        screenedUrls2.setId(2L);
        assertThat(screenedUrls1).isNotEqualTo(screenedUrls2);
        screenedUrls1.setId(null);
        assertThat(screenedUrls1).isNotEqualTo(screenedUrls2);
    }
}
