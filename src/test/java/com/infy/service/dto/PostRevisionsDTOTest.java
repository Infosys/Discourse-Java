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

public class PostRevisionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostRevisionsDTO.class);
        PostRevisionsDTO postRevisionsDTO1 = new PostRevisionsDTO();
        postRevisionsDTO1.setId(1L);
        PostRevisionsDTO postRevisionsDTO2 = new PostRevisionsDTO();
        assertThat(postRevisionsDTO1).isNotEqualTo(postRevisionsDTO2);
        postRevisionsDTO2.setId(postRevisionsDTO1.getId());
        assertThat(postRevisionsDTO1).isEqualTo(postRevisionsDTO2);
        postRevisionsDTO2.setId(2L);
        assertThat(postRevisionsDTO1).isNotEqualTo(postRevisionsDTO2);
        postRevisionsDTO1.setId(null);
        assertThat(postRevisionsDTO1).isNotEqualTo(postRevisionsDTO2);
    }
}
