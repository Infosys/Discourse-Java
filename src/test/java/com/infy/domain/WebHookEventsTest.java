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

public class WebHookEventsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebHookEvents.class);
        WebHookEvents webHookEvents1 = new WebHookEvents();
        webHookEvents1.setId(1L);
        WebHookEvents webHookEvents2 = new WebHookEvents();
        webHookEvents2.setId(webHookEvents1.getId());
        assertThat(webHookEvents1).isEqualTo(webHookEvents2);
        webHookEvents2.setId(2L);
        assertThat(webHookEvents1).isNotEqualTo(webHookEvents2);
        webHookEvents1.setId(null);
        assertThat(webHookEvents1).isNotEqualTo(webHookEvents2);
    }
}
