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

public class CategoriesWebHooksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriesWebHooksDTO.class);
        CategoriesWebHooksDTO categoriesWebHooksDTO1 = new CategoriesWebHooksDTO();
        categoriesWebHooksDTO1.setId(1L);
        CategoriesWebHooksDTO categoriesWebHooksDTO2 = new CategoriesWebHooksDTO();
        assertThat(categoriesWebHooksDTO1).isNotEqualTo(categoriesWebHooksDTO2);
        categoriesWebHooksDTO2.setId(categoriesWebHooksDTO1.getId());
        assertThat(categoriesWebHooksDTO1).isEqualTo(categoriesWebHooksDTO2);
        categoriesWebHooksDTO2.setId(2L);
        assertThat(categoriesWebHooksDTO1).isNotEqualTo(categoriesWebHooksDTO2);
        categoriesWebHooksDTO1.setId(null);
        assertThat(categoriesWebHooksDTO1).isNotEqualTo(categoriesWebHooksDTO2);
    }
}
