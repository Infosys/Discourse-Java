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

public class CategoryCustomFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryCustomFields.class);
        CategoryCustomFields categoryCustomFields1 = new CategoryCustomFields();
        categoryCustomFields1.setId(1L);
        CategoryCustomFields categoryCustomFields2 = new CategoryCustomFields();
        categoryCustomFields2.setId(categoryCustomFields1.getId());
        assertThat(categoryCustomFields1).isEqualTo(categoryCustomFields2);
        categoryCustomFields2.setId(2L);
        assertThat(categoryCustomFields1).isNotEqualTo(categoryCustomFields2);
        categoryCustomFields1.setId(null);
        assertThat(categoryCustomFields1).isNotEqualTo(categoryCustomFields2);
    }
}
