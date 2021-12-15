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

public class PostsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostsDTO.class);
        PostsDTO postsDTO1 = new PostsDTO();
        postsDTO1.setId(1L);
        PostsDTO postsDTO2 = new PostsDTO();
        assertThat(postsDTO1).isNotEqualTo(postsDTO2);
        postsDTO2.setId(postsDTO1.getId());
        assertThat(postsDTO1).isEqualTo(postsDTO2);
        postsDTO2.setId(2L);
        assertThat(postsDTO1).isNotEqualTo(postsDTO2);
        postsDTO1.setId(null);
        assertThat(postsDTO1).isNotEqualTo(postsDTO2);
    }
}
