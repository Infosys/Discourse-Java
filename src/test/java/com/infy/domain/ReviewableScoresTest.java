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

public class ReviewableScoresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewableScores.class);
        ReviewableScores reviewableScores1 = new ReviewableScores();
        reviewableScores1.setId(1L);
        ReviewableScores reviewableScores2 = new ReviewableScores();
        reviewableScores2.setId(reviewableScores1.getId());
        assertThat(reviewableScores1).isEqualTo(reviewableScores2);
        reviewableScores2.setId(2L);
        assertThat(reviewableScores1).isNotEqualTo(reviewableScores2);
        reviewableScores1.setId(null);
        assertThat(reviewableScores1).isNotEqualTo(reviewableScores2);
    }
}
