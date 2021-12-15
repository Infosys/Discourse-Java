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

public class CategoriesWebHooksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriesWebHooks.class);
        CategoriesWebHooks categoriesWebHooks1 = new CategoriesWebHooks();
        categoriesWebHooks1.setId(1L);
        CategoriesWebHooks categoriesWebHooks2 = new CategoriesWebHooks();
        categoriesWebHooks2.setId(categoriesWebHooks1.getId());
        assertThat(categoriesWebHooks1).isEqualTo(categoriesWebHooks2);
        categoriesWebHooks2.setId(2L);
        assertThat(categoriesWebHooks1).isNotEqualTo(categoriesWebHooks2);
        categoriesWebHooks1.setId(null);
        assertThat(categoriesWebHooks1).isNotEqualTo(categoriesWebHooks2);
    }
}
