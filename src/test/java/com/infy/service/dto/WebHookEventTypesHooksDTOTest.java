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

public class WebHookEventTypesHooksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebHookEventTypesHooksDTO.class);
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO1 = new WebHookEventTypesHooksDTO();
        webHookEventTypesHooksDTO1.setId(1L);
        WebHookEventTypesHooksDTO webHookEventTypesHooksDTO2 = new WebHookEventTypesHooksDTO();
        assertThat(webHookEventTypesHooksDTO1).isNotEqualTo(webHookEventTypesHooksDTO2);
        webHookEventTypesHooksDTO2.setId(webHookEventTypesHooksDTO1.getId());
        assertThat(webHookEventTypesHooksDTO1).isEqualTo(webHookEventTypesHooksDTO2);
        webHookEventTypesHooksDTO2.setId(2L);
        assertThat(webHookEventTypesHooksDTO1).isNotEqualTo(webHookEventTypesHooksDTO2);
        webHookEventTypesHooksDTO1.setId(null);
        assertThat(webHookEventTypesHooksDTO1).isNotEqualTo(webHookEventTypesHooksDTO2);
    }
}
