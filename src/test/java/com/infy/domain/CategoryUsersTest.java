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

public class CategoryUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryUsers.class);
        CategoryUsers categoryUsers1 = new CategoryUsers();
        categoryUsers1.setId(1L);
        CategoryUsers categoryUsers2 = new CategoryUsers();
        categoryUsers2.setId(categoryUsers1.getId());
        assertThat(categoryUsers1).isEqualTo(categoryUsers2);
        categoryUsers2.setId(2L);
        assertThat(categoryUsers1).isNotEqualTo(categoryUsers2);
        categoryUsers1.setId(null);
        assertThat(categoryUsers1).isNotEqualTo(categoryUsers2);
    }
}
