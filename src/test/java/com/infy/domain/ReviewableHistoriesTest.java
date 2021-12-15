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

public class ReviewableHistoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewableHistories.class);
        ReviewableHistories reviewableHistories1 = new ReviewableHistories();
        reviewableHistories1.setId(1L);
        ReviewableHistories reviewableHistories2 = new ReviewableHistories();
        reviewableHistories2.setId(reviewableHistories1.getId());
        assertThat(reviewableHistories1).isEqualTo(reviewableHistories2);
        reviewableHistories2.setId(2L);
        assertThat(reviewableHistories1).isNotEqualTo(reviewableHistories2);
        reviewableHistories1.setId(null);
        assertThat(reviewableHistories1).isNotEqualTo(reviewableHistories2);
    }
}
