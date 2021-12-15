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

public class WebHookEventTypesHooksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebHookEventTypesHooks.class);
        WebHookEventTypesHooks webHookEventTypesHooks1 = new WebHookEventTypesHooks();
        webHookEventTypesHooks1.setId(1L);
        WebHookEventTypesHooks webHookEventTypesHooks2 = new WebHookEventTypesHooks();
        webHookEventTypesHooks2.setId(webHookEventTypesHooks1.getId());
        assertThat(webHookEventTypesHooks1).isEqualTo(webHookEventTypesHooks2);
        webHookEventTypesHooks2.setId(2L);
        assertThat(webHookEventTypesHooks1).isNotEqualTo(webHookEventTypesHooks2);
        webHookEventTypesHooks1.setId(null);
        assertThat(webHookEventTypesHooks1).isNotEqualTo(webHookEventTypesHooks2);
    }
}
