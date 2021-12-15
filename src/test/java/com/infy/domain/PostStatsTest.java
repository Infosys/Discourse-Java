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

public class PostStatsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostStats.class);
        PostStats postStats1 = new PostStats();
        postStats1.setId(1L);
        PostStats postStats2 = new PostStats();
        postStats2.setId(postStats1.getId());
        assertThat(postStats1).isEqualTo(postStats2);
        postStats2.setId(2L);
        assertThat(postStats1).isNotEqualTo(postStats2);
        postStats1.setId(null);
        assertThat(postStats1).isNotEqualTo(postStats2);
    }
}
