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

public class CategoryCustomFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryCustomFieldsDTO.class);
        CategoryCustomFieldsDTO categoryCustomFieldsDTO1 = new CategoryCustomFieldsDTO();
        categoryCustomFieldsDTO1.setId(1L);
        CategoryCustomFieldsDTO categoryCustomFieldsDTO2 = new CategoryCustomFieldsDTO();
        assertThat(categoryCustomFieldsDTO1).isNotEqualTo(categoryCustomFieldsDTO2);
        categoryCustomFieldsDTO2.setId(categoryCustomFieldsDTO1.getId());
        assertThat(categoryCustomFieldsDTO1).isEqualTo(categoryCustomFieldsDTO2);
        categoryCustomFieldsDTO2.setId(2L);
        assertThat(categoryCustomFieldsDTO1).isNotEqualTo(categoryCustomFieldsDTO2);
        categoryCustomFieldsDTO1.setId(null);
        assertThat(categoryCustomFieldsDTO1).isNotEqualTo(categoryCustomFieldsDTO2);
    }
}
