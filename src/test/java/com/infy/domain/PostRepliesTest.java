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

public class PostRepliesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostReplies.class);
        PostReplies postReplies1 = new PostReplies();
        postReplies1.setId(1L);
        PostReplies postReplies2 = new PostReplies();
        postReplies2.setId(postReplies1.getId());
        assertThat(postReplies1).isEqualTo(postReplies2);
        postReplies2.setId(2L);
        assertThat(postReplies1).isNotEqualTo(postReplies2);
        postReplies1.setId(null);
        assertThat(postReplies1).isNotEqualTo(postReplies2);
    }
}
