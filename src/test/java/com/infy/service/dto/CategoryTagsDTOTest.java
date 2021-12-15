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

public class CategoryTagsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTagsDTO.class);
        CategoryTagsDTO categoryTagsDTO1 = new CategoryTagsDTO();
        categoryTagsDTO1.setId(1L);
        CategoryTagsDTO categoryTagsDTO2 = new CategoryTagsDTO();
        assertThat(categoryTagsDTO1).isNotEqualTo(categoryTagsDTO2);
        categoryTagsDTO2.setId(categoryTagsDTO1.getId());
        assertThat(categoryTagsDTO1).isEqualTo(categoryTagsDTO2);
        categoryTagsDTO2.setId(2L);
        assertThat(categoryTagsDTO1).isNotEqualTo(categoryTagsDTO2);
        categoryTagsDTO1.setId(null);
        assertThat(categoryTagsDTO1).isNotEqualTo(categoryTagsDTO2);
    }
}
