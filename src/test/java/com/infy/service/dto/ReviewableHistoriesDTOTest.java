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

public class ReviewableHistoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewableHistoriesDTO.class);
        ReviewableHistoriesDTO reviewableHistoriesDTO1 = new ReviewableHistoriesDTO();
        reviewableHistoriesDTO1.setId(1L);
        ReviewableHistoriesDTO reviewableHistoriesDTO2 = new ReviewableHistoriesDTO();
        assertThat(reviewableHistoriesDTO1).isNotEqualTo(reviewableHistoriesDTO2);
        reviewableHistoriesDTO2.setId(reviewableHistoriesDTO1.getId());
        assertThat(reviewableHistoriesDTO1).isEqualTo(reviewableHistoriesDTO2);
        reviewableHistoriesDTO2.setId(2L);
        assertThat(reviewableHistoriesDTO1).isNotEqualTo(reviewableHistoriesDTO2);
        reviewableHistoriesDTO1.setId(null);
        assertThat(reviewableHistoriesDTO1).isNotEqualTo(reviewableHistoriesDTO2);
    }
}
