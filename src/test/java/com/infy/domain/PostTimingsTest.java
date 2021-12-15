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

public class PostTimingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostTimings.class);
        PostTimings postTimings1 = new PostTimings();
        postTimings1.setId(1L);
        PostTimings postTimings2 = new PostTimings();
        postTimings2.setId(postTimings1.getId());
        assertThat(postTimings1).isEqualTo(postTimings2);
        postTimings2.setId(2L);
        assertThat(postTimings1).isNotEqualTo(postTimings2);
        postTimings1.setId(null);
        assertThat(postTimings1).isNotEqualTo(postTimings2);
    }
}
