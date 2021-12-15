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

public class PollOptionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollOptionsDTO.class);
        PollOptionsDTO pollOptionsDTO1 = new PollOptionsDTO();
        pollOptionsDTO1.setId(1L);
        PollOptionsDTO pollOptionsDTO2 = new PollOptionsDTO();
        assertThat(pollOptionsDTO1).isNotEqualTo(pollOptionsDTO2);
        pollOptionsDTO2.setId(pollOptionsDTO1.getId());
        assertThat(pollOptionsDTO1).isEqualTo(pollOptionsDTO2);
        pollOptionsDTO2.setId(2L);
        assertThat(pollOptionsDTO1).isNotEqualTo(pollOptionsDTO2);
        pollOptionsDTO1.setId(null);
        assertThat(pollOptionsDTO1).isNotEqualTo(pollOptionsDTO2);
    }
}
