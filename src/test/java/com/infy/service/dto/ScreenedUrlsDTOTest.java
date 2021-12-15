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

public class ScreenedUrlsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenedUrlsDTO.class);
        ScreenedUrlsDTO screenedUrlsDTO1 = new ScreenedUrlsDTO();
        screenedUrlsDTO1.setId(1L);
        ScreenedUrlsDTO screenedUrlsDTO2 = new ScreenedUrlsDTO();
        assertThat(screenedUrlsDTO1).isNotEqualTo(screenedUrlsDTO2);
        screenedUrlsDTO2.setId(screenedUrlsDTO1.getId());
        assertThat(screenedUrlsDTO1).isEqualTo(screenedUrlsDTO2);
        screenedUrlsDTO2.setId(2L);
        assertThat(screenedUrlsDTO1).isNotEqualTo(screenedUrlsDTO2);
        screenedUrlsDTO1.setId(null);
        assertThat(screenedUrlsDTO1).isNotEqualTo(screenedUrlsDTO2);
    }
}
