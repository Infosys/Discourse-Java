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

public class UserBadgesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBadges.class);
        UserBadges userBadges1 = new UserBadges();
        userBadges1.setId(1L);
        UserBadges userBadges2 = new UserBadges();
        userBadges2.setId(userBadges1.getId());
        assertThat(userBadges1).isEqualTo(userBadges2);
        userBadges2.setId(2L);
        assertThat(userBadges1).isNotEqualTo(userBadges2);
        userBadges1.setId(null);
        assertThat(userBadges1).isNotEqualTo(userBadges2);
    }
}
