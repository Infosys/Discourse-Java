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

public class PostSearchDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostSearchDataDTO.class);
        PostSearchDataDTO postSearchDataDTO1 = new PostSearchDataDTO();
        postSearchDataDTO1.setId(1L);
        PostSearchDataDTO postSearchDataDTO2 = new PostSearchDataDTO();
        assertThat(postSearchDataDTO1).isNotEqualTo(postSearchDataDTO2);
        postSearchDataDTO2.setId(postSearchDataDTO1.getId());
        assertThat(postSearchDataDTO1).isEqualTo(postSearchDataDTO2);
        postSearchDataDTO2.setId(2L);
        assertThat(postSearchDataDTO1).isNotEqualTo(postSearchDataDTO2);
        postSearchDataDTO1.setId(null);
        assertThat(postSearchDataDTO1).isNotEqualTo(postSearchDataDTO2);
    }
}
