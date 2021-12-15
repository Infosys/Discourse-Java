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

public class ReviewablesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reviewables.class);
        Reviewables reviewables1 = new Reviewables();
        reviewables1.setId(1L);
        Reviewables reviewables2 = new Reviewables();
        reviewables2.setId(reviewables1.getId());
        assertThat(reviewables1).isEqualTo(reviewables2);
        reviewables2.setId(2L);
        assertThat(reviewables1).isNotEqualTo(reviewables2);
        reviewables1.setId(null);
        assertThat(reviewables1).isNotEqualTo(reviewables2);
    }
}
