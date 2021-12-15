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

public class CategoryTagGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTagGroupsDTO.class);
        CategoryTagGroupsDTO categoryTagGroupsDTO1 = new CategoryTagGroupsDTO();
        categoryTagGroupsDTO1.setId(1L);
        CategoryTagGroupsDTO categoryTagGroupsDTO2 = new CategoryTagGroupsDTO();
        assertThat(categoryTagGroupsDTO1).isNotEqualTo(categoryTagGroupsDTO2);
        categoryTagGroupsDTO2.setId(categoryTagGroupsDTO1.getId());
        assertThat(categoryTagGroupsDTO1).isEqualTo(categoryTagGroupsDTO2);
        categoryTagGroupsDTO2.setId(2L);
        assertThat(categoryTagGroupsDTO1).isNotEqualTo(categoryTagGroupsDTO2);
        categoryTagGroupsDTO1.setId(null);
        assertThat(categoryTagGroupsDTO1).isNotEqualTo(categoryTagGroupsDTO2);
    }
}
