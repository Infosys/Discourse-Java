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

public class MessageBusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageBus.class);
        MessageBus messageBus1 = new MessageBus();
        messageBus1.setId(1L);
        MessageBus messageBus2 = new MessageBus();
        messageBus2.setId(messageBus1.getId());
        assertThat(messageBus1).isEqualTo(messageBus2);
        messageBus2.setId(2L);
        assertThat(messageBus1).isNotEqualTo(messageBus2);
        messageBus1.setId(null);
        assertThat(messageBus1).isNotEqualTo(messageBus2);
    }
}
