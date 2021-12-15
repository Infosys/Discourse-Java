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

public class PostStatsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostStatsDTO.class);
        PostStatsDTO postStatsDTO1 = new PostStatsDTO();
        postStatsDTO1.setId(1L);
        PostStatsDTO postStatsDTO2 = new PostStatsDTO();
        assertThat(postStatsDTO1).isNotEqualTo(postStatsDTO2);
        postStatsDTO2.setId(postStatsDTO1.getId());
        assertThat(postStatsDTO1).isEqualTo(postStatsDTO2);
        postStatsDTO2.setId(2L);
        assertThat(postStatsDTO1).isNotEqualTo(postStatsDTO2);
        postStatsDTO1.setId(null);
        assertThat(postStatsDTO1).isNotEqualTo(postStatsDTO2);
    }
}
