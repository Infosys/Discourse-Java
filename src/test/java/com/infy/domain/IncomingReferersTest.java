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

public class IncomingReferersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingReferers.class);
        IncomingReferers incomingReferers1 = new IncomingReferers();
        incomingReferers1.setId(1L);
        IncomingReferers incomingReferers2 = new IncomingReferers();
        incomingReferers2.setId(incomingReferers1.getId());
        assertThat(incomingReferers1).isEqualTo(incomingReferers2);
        incomingReferers2.setId(2L);
        assertThat(incomingReferers1).isNotEqualTo(incomingReferers2);
        incomingReferers1.setId(null);
        assertThat(incomingReferers1).isNotEqualTo(incomingReferers2);
    }
}
