/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class SiteSettingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteSettingsDTO.class);
        SiteSettingsDTO siteSettingsDTO1 = new SiteSettingsDTO();
        siteSettingsDTO1.setId(1L);
        SiteSettingsDTO siteSettingsDTO2 = new SiteSettingsDTO();
        assertThat(siteSettingsDTO1).isNotEqualTo(siteSettingsDTO2);
        siteSettingsDTO2.setId(siteSettingsDTO1.getId());
        assertThat(siteSettingsDTO1).isEqualTo(siteSettingsDTO2);
        siteSettingsDTO2.setId(2L);
        assertThat(siteSettingsDTO1).isNotEqualTo(siteSettingsDTO2);
        siteSettingsDTO1.setId(null);
        assertThat(siteSettingsDTO1).isNotEqualTo(siteSettingsDTO2);
    }
}
