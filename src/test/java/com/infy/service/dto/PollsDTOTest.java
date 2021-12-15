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

public class PollsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollsDTO.class);
        PollsDTO pollsDTO1 = new PollsDTO();
        pollsDTO1.setId(1L);
        PollsDTO pollsDTO2 = new PollsDTO();
        assertThat(pollsDTO1).isNotEqualTo(pollsDTO2);
        pollsDTO2.setId(pollsDTO1.getId());
        assertThat(pollsDTO1).isEqualTo(pollsDTO2);
        pollsDTO2.setId(2L);
        assertThat(pollsDTO1).isNotEqualTo(pollsDTO2);
        pollsDTO1.setId(null);
        assertThat(pollsDTO1).isNotEqualTo(pollsDTO2);
    }
}
