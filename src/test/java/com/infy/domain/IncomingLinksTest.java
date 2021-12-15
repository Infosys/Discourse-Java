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

public class IncomingLinksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingLinks.class);
        IncomingLinks incomingLinks1 = new IncomingLinks();
        incomingLinks1.setId(1L);
        IncomingLinks incomingLinks2 = new IncomingLinks();
        incomingLinks2.setId(incomingLinks1.getId());
        assertThat(incomingLinks1).isEqualTo(incomingLinks2);
        incomingLinks2.setId(2L);
        assertThat(incomingLinks1).isNotEqualTo(incomingLinks2);
        incomingLinks1.setId(null);
        assertThat(incomingLinks1).isNotEqualTo(incomingLinks2);
    }
}
