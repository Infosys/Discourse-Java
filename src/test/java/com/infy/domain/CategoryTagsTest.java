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

public class CategoryTagsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTags.class);
        CategoryTags categoryTags1 = new CategoryTags();
        categoryTags1.setId(1L);
        CategoryTags categoryTags2 = new CategoryTags();
        categoryTags2.setId(categoryTags1.getId());
        assertThat(categoryTags1).isEqualTo(categoryTags2);
        categoryTags2.setId(2L);
        assertThat(categoryTags1).isNotEqualTo(categoryTags2);
        categoryTags1.setId(null);
        assertThat(categoryTags1).isNotEqualTo(categoryTags2);
    }
}
