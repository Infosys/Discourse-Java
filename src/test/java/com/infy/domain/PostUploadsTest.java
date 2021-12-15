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

public class PostUploadsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostUploads.class);
        PostUploads postUploads1 = new PostUploads();
        postUploads1.setId(1L);
        PostUploads postUploads2 = new PostUploads();
        postUploads2.setId(postUploads1.getId());
        assertThat(postUploads1).isEqualTo(postUploads2);
        postUploads2.setId(2L);
        assertThat(postUploads1).isNotEqualTo(postUploads2);
        postUploads1.setId(null);
        assertThat(postUploads1).isNotEqualTo(postUploads2);
    }
}
