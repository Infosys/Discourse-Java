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

public class QuotedPostsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotedPostsDTO.class);
        QuotedPostsDTO quotedPostsDTO1 = new QuotedPostsDTO();
        quotedPostsDTO1.setId(1L);
        QuotedPostsDTO quotedPostsDTO2 = new QuotedPostsDTO();
        assertThat(quotedPostsDTO1).isNotEqualTo(quotedPostsDTO2);
        quotedPostsDTO2.setId(quotedPostsDTO1.getId());
        assertThat(quotedPostsDTO1).isEqualTo(quotedPostsDTO2);
        quotedPostsDTO2.setId(2L);
        assertThat(quotedPostsDTO1).isNotEqualTo(quotedPostsDTO2);
        quotedPostsDTO1.setId(null);
        assertThat(quotedPostsDTO1).isNotEqualTo(quotedPostsDTO2);
    }
}
