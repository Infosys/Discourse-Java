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

public class PostDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostDetailsDTO.class);
        PostDetailsDTO postDetailsDTO1 = new PostDetailsDTO();
        postDetailsDTO1.setId(1L);
        PostDetailsDTO postDetailsDTO2 = new PostDetailsDTO();
        assertThat(postDetailsDTO1).isNotEqualTo(postDetailsDTO2);
        postDetailsDTO2.setId(postDetailsDTO1.getId());
        assertThat(postDetailsDTO1).isEqualTo(postDetailsDTO2);
        postDetailsDTO2.setId(2L);
        assertThat(postDetailsDTO1).isNotEqualTo(postDetailsDTO2);
        postDetailsDTO1.setId(null);
        assertThat(postDetailsDTO1).isNotEqualTo(postDetailsDTO2);
    }
}
