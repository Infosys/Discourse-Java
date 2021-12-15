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

public class SiteSettingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteSettings.class);
        SiteSettings siteSettings1 = new SiteSettings();
        siteSettings1.setId(1L);
        SiteSettings siteSettings2 = new SiteSettings();
        siteSettings2.setId(siteSettings1.getId());
        assertThat(siteSettings1).isEqualTo(siteSettings2);
        siteSettings2.setId(2L);
        assertThat(siteSettings1).isNotEqualTo(siteSettings2);
        siteSettings1.setId(null);
        assertThat(siteSettings1).isNotEqualTo(siteSettings2);
    }
}
