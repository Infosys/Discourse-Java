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

public class WebHookEventsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebHookEventsDTO.class);
        WebHookEventsDTO webHookEventsDTO1 = new WebHookEventsDTO();
        webHookEventsDTO1.setId(1L);
        WebHookEventsDTO webHookEventsDTO2 = new WebHookEventsDTO();
        assertThat(webHookEventsDTO1).isNotEqualTo(webHookEventsDTO2);
        webHookEventsDTO2.setId(webHookEventsDTO1.getId());
        assertThat(webHookEventsDTO1).isEqualTo(webHookEventsDTO2);
        webHookEventsDTO2.setId(2L);
        assertThat(webHookEventsDTO1).isNotEqualTo(webHookEventsDTO2);
        webHookEventsDTO1.setId(null);
        assertThat(webHookEventsDTO1).isNotEqualTo(webHookEventsDTO2);
    }
}
