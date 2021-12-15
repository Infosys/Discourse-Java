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

public class GivenDailyLikesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GivenDailyLikes.class);
        GivenDailyLikes givenDailyLikes1 = new GivenDailyLikes();
        givenDailyLikes1.setId(1L);
        GivenDailyLikes givenDailyLikes2 = new GivenDailyLikes();
        givenDailyLikes2.setId(givenDailyLikes1.getId());
        assertThat(givenDailyLikes1).isEqualTo(givenDailyLikes2);
        givenDailyLikes2.setId(2L);
        assertThat(givenDailyLikes1).isNotEqualTo(givenDailyLikes2);
        givenDailyLikes1.setId(null);
        assertThat(givenDailyLikes1).isNotEqualTo(givenDailyLikes2);
    }
}
