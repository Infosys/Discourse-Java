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

public class CategoryGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryGroupsDTO.class);
        CategoryGroupsDTO categoryGroupsDTO1 = new CategoryGroupsDTO();
        categoryGroupsDTO1.setId(1L);
        CategoryGroupsDTO categoryGroupsDTO2 = new CategoryGroupsDTO();
        assertThat(categoryGroupsDTO1).isNotEqualTo(categoryGroupsDTO2);
        categoryGroupsDTO2.setId(categoryGroupsDTO1.getId());
        assertThat(categoryGroupsDTO1).isEqualTo(categoryGroupsDTO2);
        categoryGroupsDTO2.setId(2L);
        assertThat(categoryGroupsDTO1).isNotEqualTo(categoryGroupsDTO2);
        categoryGroupsDTO1.setId(null);
        assertThat(categoryGroupsDTO1).isNotEqualTo(categoryGroupsDTO2);
    }
}
