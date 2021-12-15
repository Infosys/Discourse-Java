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

public class SchedulerStatsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedulerStats.class);
        SchedulerStats schedulerStats1 = new SchedulerStats();
        schedulerStats1.setId(1L);
        SchedulerStats schedulerStats2 = new SchedulerStats();
        schedulerStats2.setId(schedulerStats1.getId());
        assertThat(schedulerStats1).isEqualTo(schedulerStats2);
        schedulerStats2.setId(2L);
        assertThat(schedulerStats1).isNotEqualTo(schedulerStats2);
        schedulerStats1.setId(null);
        assertThat(schedulerStats1).isNotEqualTo(schedulerStats2);
    }
}
