/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class CategoryUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryUsersDTO.class);
        CategoryUsersDTO categoryUsersDTO1 = new CategoryUsersDTO();
        categoryUsersDTO1.setId(1L);
        CategoryUsersDTO categoryUsersDTO2 = new CategoryUsersDTO();
        assertThat(categoryUsersDTO1).isNotEqualTo(categoryUsersDTO2);
        categoryUsersDTO2.setId(categoryUsersDTO1.getId());
        assertThat(categoryUsersDTO1).isEqualTo(categoryUsersDTO2);
        categoryUsersDTO2.setId(2L);
        assertThat(categoryUsersDTO1).isNotEqualTo(categoryUsersDTO2);
        categoryUsersDTO1.setId(null);
        assertThat(categoryUsersDTO1).isNotEqualTo(categoryUsersDTO2);
    }
}
