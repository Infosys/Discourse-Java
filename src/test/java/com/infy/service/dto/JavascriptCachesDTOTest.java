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

public class JavascriptCachesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JavascriptCachesDTO.class);
        JavascriptCachesDTO javascriptCachesDTO1 = new JavascriptCachesDTO();
        javascriptCachesDTO1.setId(1L);
        JavascriptCachesDTO javascriptCachesDTO2 = new JavascriptCachesDTO();
        assertThat(javascriptCachesDTO1).isNotEqualTo(javascriptCachesDTO2);
        javascriptCachesDTO2.setId(javascriptCachesDTO1.getId());
        assertThat(javascriptCachesDTO1).isEqualTo(javascriptCachesDTO2);
        javascriptCachesDTO2.setId(2L);
        assertThat(javascriptCachesDTO1).isNotEqualTo(javascriptCachesDTO2);
        javascriptCachesDTO1.setId(null);
        assertThat(javascriptCachesDTO1).isNotEqualTo(javascriptCachesDTO2);
    }
}
