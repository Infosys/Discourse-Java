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

public class CategorySearchDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorySearchData.class);
        CategorySearchData categorySearchData1 = new CategorySearchData();
        categorySearchData1.setId(1L);
        CategorySearchData categorySearchData2 = new CategorySearchData();
        categorySearchData2.setId(categorySearchData1.getId());
        assertThat(categorySearchData1).isEqualTo(categorySearchData2);
        categorySearchData2.setId(2L);
        assertThat(categorySearchData1).isNotEqualTo(categorySearchData2);
        categorySearchData1.setId(null);
        assertThat(categorySearchData1).isNotEqualTo(categorySearchData2);
    }
}
