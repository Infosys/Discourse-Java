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

public class PostActionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostActions.class);
        PostActions postActions1 = new PostActions();
        postActions1.setId(1L);
        PostActions postActions2 = new PostActions();
        postActions2.setId(postActions1.getId());
        assertThat(postActions1).isEqualTo(postActions2);
        postActions2.setId(2L);
        assertThat(postActions1).isNotEqualTo(postActions2);
        postActions1.setId(null);
        assertThat(postActions1).isNotEqualTo(postActions2);
    }
}
