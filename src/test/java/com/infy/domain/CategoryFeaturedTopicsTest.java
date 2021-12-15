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

public class CategoryFeaturedTopicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryFeaturedTopics.class);
        CategoryFeaturedTopics categoryFeaturedTopics1 = new CategoryFeaturedTopics();
        categoryFeaturedTopics1.setId(1L);
        CategoryFeaturedTopics categoryFeaturedTopics2 = new CategoryFeaturedTopics();
        categoryFeaturedTopics2.setId(categoryFeaturedTopics1.getId());
        assertThat(categoryFeaturedTopics1).isEqualTo(categoryFeaturedTopics2);
        categoryFeaturedTopics2.setId(2L);
        assertThat(categoryFeaturedTopics1).isNotEqualTo(categoryFeaturedTopics2);
        categoryFeaturedTopics1.setId(null);
        assertThat(categoryFeaturedTopics1).isNotEqualTo(categoryFeaturedTopics2);
    }
}
