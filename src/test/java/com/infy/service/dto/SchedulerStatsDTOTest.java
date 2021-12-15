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

public class SchedulerStatsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedulerStatsDTO.class);
        SchedulerStatsDTO schedulerStatsDTO1 = new SchedulerStatsDTO();
        schedulerStatsDTO1.setId(1L);
        SchedulerStatsDTO schedulerStatsDTO2 = new SchedulerStatsDTO();
        assertThat(schedulerStatsDTO1).isNotEqualTo(schedulerStatsDTO2);
        schedulerStatsDTO2.setId(schedulerStatsDTO1.getId());
        assertThat(schedulerStatsDTO1).isEqualTo(schedulerStatsDTO2);
        schedulerStatsDTO2.setId(2L);
        assertThat(schedulerStatsDTO1).isNotEqualTo(schedulerStatsDTO2);
        schedulerStatsDTO1.setId(null);
        assertThat(schedulerStatsDTO1).isNotEqualTo(schedulerStatsDTO2);
    }
}
