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

public class BadgePostsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgePosts.class);
        BadgePosts badgePosts1 = new BadgePosts();
        badgePosts1.setId(1L);
        BadgePosts badgePosts2 = new BadgePosts();
        badgePosts2.setId(badgePosts1.getId());
        assertThat(badgePosts1).isEqualTo(badgePosts2);
        badgePosts2.setId(2L);
        assertThat(badgePosts1).isNotEqualTo(badgePosts2);
        badgePosts1.setId(null);
        assertThat(badgePosts1).isNotEqualTo(badgePosts2);
    }
}
