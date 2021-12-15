/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class PostReplyKeysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostReplyKeys.class);
        PostReplyKeys postReplyKeys1 = new PostReplyKeys();
        postReplyKeys1.setId(1L);
        PostReplyKeys postReplyKeys2 = new PostReplyKeys();
        postReplyKeys2.setId(postReplyKeys1.getId());
        assertThat(postReplyKeys1).isEqualTo(postReplyKeys2);
        postReplyKeys2.setId(2L);
        assertThat(postReplyKeys1).isNotEqualTo(postReplyKeys2);
        postReplyKeys1.setId(null);
        assertThat(postReplyKeys1).isNotEqualTo(postReplyKeys2);
    }
}
