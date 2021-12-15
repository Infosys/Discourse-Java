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

public class CategoryFeaturedTopicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryFeaturedTopicsDTO.class);
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO1 = new CategoryFeaturedTopicsDTO();
        categoryFeaturedTopicsDTO1.setId(1L);
        CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO2 = new CategoryFeaturedTopicsDTO();
        assertThat(categoryFeaturedTopicsDTO1).isNotEqualTo(categoryFeaturedTopicsDTO2);
        categoryFeaturedTopicsDTO2.setId(categoryFeaturedTopicsDTO1.getId());
        assertThat(categoryFeaturedTopicsDTO1).isEqualTo(categoryFeaturedTopicsDTO2);
        categoryFeaturedTopicsDTO2.setId(2L);
        assertThat(categoryFeaturedTopicsDTO1).isNotEqualTo(categoryFeaturedTopicsDTO2);
        categoryFeaturedTopicsDTO1.setId(null);
        assertThat(categoryFeaturedTopicsDTO1).isNotEqualTo(categoryFeaturedTopicsDTO2);
    }
}
