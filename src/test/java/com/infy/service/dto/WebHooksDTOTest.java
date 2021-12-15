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

public class WebHooksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebHooksDTO.class);
        WebHooksDTO webHooksDTO1 = new WebHooksDTO();
        webHooksDTO1.setId(1L);
        WebHooksDTO webHooksDTO2 = new WebHooksDTO();
        assertThat(webHooksDTO1).isNotEqualTo(webHooksDTO2);
        webHooksDTO2.setId(webHooksDTO1.getId());
        assertThat(webHooksDTO1).isEqualTo(webHooksDTO2);
        webHooksDTO2.setId(2L);
        assertThat(webHooksDTO1).isNotEqualTo(webHooksDTO2);
        webHooksDTO1.setId(null);
        assertThat(webHooksDTO1).isNotEqualTo(webHooksDTO2);
    }
}
