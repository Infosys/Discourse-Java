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

public class PostSearchDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostSearchData.class);
        PostSearchData postSearchData1 = new PostSearchData();
        postSearchData1.setId(1L);
        PostSearchData postSearchData2 = new PostSearchData();
        postSearchData2.setId(postSearchData1.getId());
        assertThat(postSearchData1).isEqualTo(postSearchData2);
        postSearchData2.setId(2L);
        assertThat(postSearchData1).isNotEqualTo(postSearchData2);
        postSearchData1.setId(null);
        assertThat(postSearchData1).isNotEqualTo(postSearchData2);
    }
}
