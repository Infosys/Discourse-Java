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

public class ReviewableScoresDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewableScoresDTO.class);
        ReviewableScoresDTO reviewableScoresDTO1 = new ReviewableScoresDTO();
        reviewableScoresDTO1.setId(1L);
        ReviewableScoresDTO reviewableScoresDTO2 = new ReviewableScoresDTO();
        assertThat(reviewableScoresDTO1).isNotEqualTo(reviewableScoresDTO2);
        reviewableScoresDTO2.setId(reviewableScoresDTO1.getId());
        assertThat(reviewableScoresDTO1).isEqualTo(reviewableScoresDTO2);
        reviewableScoresDTO2.setId(2L);
        assertThat(reviewableScoresDTO1).isNotEqualTo(reviewableScoresDTO2);
        reviewableScoresDTO1.setId(null);
        assertThat(reviewableScoresDTO1).isNotEqualTo(reviewableScoresDTO2);
    }
}
