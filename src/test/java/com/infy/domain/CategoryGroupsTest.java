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

public class CategoryGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryGroups.class);
        CategoryGroups categoryGroups1 = new CategoryGroups();
        categoryGroups1.setId(1L);
        CategoryGroups categoryGroups2 = new CategoryGroups();
        categoryGroups2.setId(categoryGroups1.getId());
        assertThat(categoryGroups1).isEqualTo(categoryGroups2);
        categoryGroups2.setId(2L);
        assertThat(categoryGroups1).isNotEqualTo(categoryGroups2);
        categoryGroups1.setId(null);
        assertThat(categoryGroups1).isNotEqualTo(categoryGroups2);
    }
}
