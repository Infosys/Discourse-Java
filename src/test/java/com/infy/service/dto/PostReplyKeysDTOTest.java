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

public class PostReplyKeysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostReplyKeysDTO.class);
        PostReplyKeysDTO postReplyKeysDTO1 = new PostReplyKeysDTO();
        postReplyKeysDTO1.setId(1L);
        PostReplyKeysDTO postReplyKeysDTO2 = new PostReplyKeysDTO();
        assertThat(postReplyKeysDTO1).isNotEqualTo(postReplyKeysDTO2);
        postReplyKeysDTO2.setId(postReplyKeysDTO1.getId());
        assertThat(postReplyKeysDTO1).isEqualTo(postReplyKeysDTO2);
        postReplyKeysDTO2.setId(2L);
        assertThat(postReplyKeysDTO1).isNotEqualTo(postReplyKeysDTO2);
        postReplyKeysDTO1.setId(null);
        assertThat(postReplyKeysDTO1).isNotEqualTo(postReplyKeysDTO2);
    }
}
