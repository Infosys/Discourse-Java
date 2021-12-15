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

public class CategoryTagGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTagGroups.class);
        CategoryTagGroups categoryTagGroups1 = new CategoryTagGroups();
        categoryTagGroups1.setId(1L);
        CategoryTagGroups categoryTagGroups2 = new CategoryTagGroups();
        categoryTagGroups2.setId(categoryTagGroups1.getId());
        assertThat(categoryTagGroups1).isEqualTo(categoryTagGroups2);
        categoryTagGroups2.setId(2L);
        assertThat(categoryTagGroups1).isNotEqualTo(categoryTagGroups2);
        categoryTagGroups1.setId(null);
        assertThat(categoryTagGroups1).isNotEqualTo(categoryTagGroups2);
    }
}
