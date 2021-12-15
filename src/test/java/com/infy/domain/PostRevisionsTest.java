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

public class PostRevisionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostRevisions.class);
        PostRevisions postRevisions1 = new PostRevisions();
        postRevisions1.setId(1L);
        PostRevisions postRevisions2 = new PostRevisions();
        postRevisions2.setId(postRevisions1.getId());
        assertThat(postRevisions1).isEqualTo(postRevisions2);
        postRevisions2.setId(2L);
        assertThat(postRevisions1).isNotEqualTo(postRevisions2);
        postRevisions1.setId(null);
        assertThat(postRevisions1).isNotEqualTo(postRevisions2);
    }
}
