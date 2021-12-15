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

public class IncomingDomainsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingDomains.class);
        IncomingDomains incomingDomains1 = new IncomingDomains();
        incomingDomains1.setId(1L);
        IncomingDomains incomingDomains2 = new IncomingDomains();
        incomingDomains2.setId(incomingDomains1.getId());
        assertThat(incomingDomains1).isEqualTo(incomingDomains2);
        incomingDomains2.setId(2L);
        assertThat(incomingDomains1).isNotEqualTo(incomingDomains2);
        incomingDomains1.setId(null);
        assertThat(incomingDomains1).isNotEqualTo(incomingDomains2);
    }
}
