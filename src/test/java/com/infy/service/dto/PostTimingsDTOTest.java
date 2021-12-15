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

public class PostTimingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostTimingsDTO.class);
        PostTimingsDTO postTimingsDTO1 = new PostTimingsDTO();
        postTimingsDTO1.setId(1L);
        PostTimingsDTO postTimingsDTO2 = new PostTimingsDTO();
        assertThat(postTimingsDTO1).isNotEqualTo(postTimingsDTO2);
        postTimingsDTO2.setId(postTimingsDTO1.getId());
        assertThat(postTimingsDTO1).isEqualTo(postTimingsDTO2);
        postTimingsDTO2.setId(2L);
        assertThat(postTimingsDTO1).isNotEqualTo(postTimingsDTO2);
        postTimingsDTO1.setId(null);
        assertThat(postTimingsDTO1).isNotEqualTo(postTimingsDTO2);
    }
}
