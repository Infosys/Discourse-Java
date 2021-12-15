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

public class UserStatsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStats.class);
        UserStats userStats1 = new UserStats();
        userStats1.setId(1L);
        UserStats userStats2 = new UserStats();
        userStats2.setId(userStats1.getId());
        assertThat(userStats1).isEqualTo(userStats2);
        userStats2.setId(2L);
        assertThat(userStats1).isNotEqualTo(userStats2);
        userStats1.setId(null);
        assertThat(userStats1).isNotEqualTo(userStats2);
    }
}
