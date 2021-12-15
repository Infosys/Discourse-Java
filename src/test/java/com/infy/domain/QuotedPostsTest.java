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

public class QuotedPostsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotedPosts.class);
        QuotedPosts quotedPosts1 = new QuotedPosts();
        quotedPosts1.setId(1L);
        QuotedPosts quotedPosts2 = new QuotedPosts();
        quotedPosts2.setId(quotedPosts1.getId());
        assertThat(quotedPosts1).isEqualTo(quotedPosts2);
        quotedPosts2.setId(2L);
        assertThat(quotedPosts1).isNotEqualTo(quotedPosts2);
        quotedPosts1.setId(null);
        assertThat(quotedPosts1).isNotEqualTo(quotedPosts2);
    }
}
