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

public class TagSearchDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagSearchData.class);
        TagSearchData tagSearchData1 = new TagSearchData();
        tagSearchData1.setId(1L);
        TagSearchData tagSearchData2 = new TagSearchData();
        tagSearchData2.setId(tagSearchData1.getId());
        assertThat(tagSearchData1).isEqualTo(tagSearchData2);
        tagSearchData2.setId(2L);
        assertThat(tagSearchData1).isNotEqualTo(tagSearchData2);
        tagSearchData1.setId(null);
        assertThat(tagSearchData1).isNotEqualTo(tagSearchData2);
    }
}
