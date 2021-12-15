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

public class PostRepliesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostRepliesDTO.class);
        PostRepliesDTO postRepliesDTO1 = new PostRepliesDTO();
        postRepliesDTO1.setId(1L);
        PostRepliesDTO postRepliesDTO2 = new PostRepliesDTO();
        assertThat(postRepliesDTO1).isNotEqualTo(postRepliesDTO2);
        postRepliesDTO2.setId(postRepliesDTO1.getId());
        assertThat(postRepliesDTO1).isEqualTo(postRepliesDTO2);
        postRepliesDTO2.setId(2L);
        assertThat(postRepliesDTO1).isNotEqualTo(postRepliesDTO2);
        postRepliesDTO1.setId(null);
        assertThat(postRepliesDTO1).isNotEqualTo(postRepliesDTO2);
    }
}
