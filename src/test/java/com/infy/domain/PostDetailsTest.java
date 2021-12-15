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

public class PostDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostDetails.class);
        PostDetails postDetails1 = new PostDetails();
        postDetails1.setId(1L);
        PostDetails postDetails2 = new PostDetails();
        postDetails2.setId(postDetails1.getId());
        assertThat(postDetails1).isEqualTo(postDetails2);
        postDetails2.setId(2L);
        assertThat(postDetails1).isNotEqualTo(postDetails2);
        postDetails1.setId(null);
        assertThat(postDetails1).isNotEqualTo(postDetails2);
    }
}
