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

public class ReviewableClaimedTopicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewableClaimedTopics.class);
        ReviewableClaimedTopics reviewableClaimedTopics1 = new ReviewableClaimedTopics();
        reviewableClaimedTopics1.setId(1L);
        ReviewableClaimedTopics reviewableClaimedTopics2 = new ReviewableClaimedTopics();
        reviewableClaimedTopics2.setId(reviewableClaimedTopics1.getId());
        assertThat(reviewableClaimedTopics1).isEqualTo(reviewableClaimedTopics2);
        reviewableClaimedTopics2.setId(2L);
        assertThat(reviewableClaimedTopics1).isNotEqualTo(reviewableClaimedTopics2);
        reviewableClaimedTopics1.setId(null);
        assertThat(reviewableClaimedTopics1).isNotEqualTo(reviewableClaimedTopics2);
    }
}
