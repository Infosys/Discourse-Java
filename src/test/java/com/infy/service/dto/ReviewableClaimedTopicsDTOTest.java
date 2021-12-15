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

public class ReviewableClaimedTopicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewableClaimedTopicsDTO.class);
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO1 = new ReviewableClaimedTopicsDTO();
        reviewableClaimedTopicsDTO1.setId(1L);
        ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO2 = new ReviewableClaimedTopicsDTO();
        assertThat(reviewableClaimedTopicsDTO1).isNotEqualTo(reviewableClaimedTopicsDTO2);
        reviewableClaimedTopicsDTO2.setId(reviewableClaimedTopicsDTO1.getId());
        assertThat(reviewableClaimedTopicsDTO1).isEqualTo(reviewableClaimedTopicsDTO2);
        reviewableClaimedTopicsDTO2.setId(2L);
        assertThat(reviewableClaimedTopicsDTO1).isNotEqualTo(reviewableClaimedTopicsDTO2);
        reviewableClaimedTopicsDTO1.setId(null);
        assertThat(reviewableClaimedTopicsDTO1).isNotEqualTo(reviewableClaimedTopicsDTO2);
    }
}
