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

public class StylesheetCacheDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StylesheetCacheDTO.class);
        StylesheetCacheDTO stylesheetCacheDTO1 = new StylesheetCacheDTO();
        stylesheetCacheDTO1.setId(1L);
        StylesheetCacheDTO stylesheetCacheDTO2 = new StylesheetCacheDTO();
        assertThat(stylesheetCacheDTO1).isNotEqualTo(stylesheetCacheDTO2);
        stylesheetCacheDTO2.setId(stylesheetCacheDTO1.getId());
        assertThat(stylesheetCacheDTO1).isEqualTo(stylesheetCacheDTO2);
        stylesheetCacheDTO2.setId(2L);
        assertThat(stylesheetCacheDTO1).isNotEqualTo(stylesheetCacheDTO2);
        stylesheetCacheDTO1.setId(null);
        assertThat(stylesheetCacheDTO1).isNotEqualTo(stylesheetCacheDTO2);
    }
}
