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

public class BadgeTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeTypes.class);
        BadgeTypes badgeTypes1 = new BadgeTypes();
        badgeTypes1.setId(1L);
        BadgeTypes badgeTypes2 = new BadgeTypes();
        badgeTypes2.setId(badgeTypes1.getId());
        assertThat(badgeTypes1).isEqualTo(badgeTypes2);
        badgeTypes2.setId(2L);
        assertThat(badgeTypes1).isNotEqualTo(badgeTypes2);
        badgeTypes1.setId(null);
        assertThat(badgeTypes1).isNotEqualTo(badgeTypes2);
    }
}
