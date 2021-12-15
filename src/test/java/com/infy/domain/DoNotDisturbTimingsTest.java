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

public class DoNotDisturbTimingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoNotDisturbTimings.class);
        DoNotDisturbTimings doNotDisturbTimings1 = new DoNotDisturbTimings();
        doNotDisturbTimings1.setId(1L);
        DoNotDisturbTimings doNotDisturbTimings2 = new DoNotDisturbTimings();
        doNotDisturbTimings2.setId(doNotDisturbTimings1.getId());
        assertThat(doNotDisturbTimings1).isEqualTo(doNotDisturbTimings2);
        doNotDisturbTimings2.setId(2L);
        assertThat(doNotDisturbTimings1).isNotEqualTo(doNotDisturbTimings2);
        doNotDisturbTimings1.setId(null);
        assertThat(doNotDisturbTimings1).isNotEqualTo(doNotDisturbTimings2);
    }
}
