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

public class CategoryTagStatsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTagStatsDTO.class);
        CategoryTagStatsDTO categoryTagStatsDTO1 = new CategoryTagStatsDTO();
        categoryTagStatsDTO1.setId(1L);
        CategoryTagStatsDTO categoryTagStatsDTO2 = new CategoryTagStatsDTO();
        assertThat(categoryTagStatsDTO1).isNotEqualTo(categoryTagStatsDTO2);
        categoryTagStatsDTO2.setId(categoryTagStatsDTO1.getId());
        assertThat(categoryTagStatsDTO1).isEqualTo(categoryTagStatsDTO2);
        categoryTagStatsDTO2.setId(2L);
        assertThat(categoryTagStatsDTO1).isNotEqualTo(categoryTagStatsDTO2);
        categoryTagStatsDTO1.setId(null);
        assertThat(categoryTagStatsDTO1).isNotEqualTo(categoryTagStatsDTO2);
    }
}
