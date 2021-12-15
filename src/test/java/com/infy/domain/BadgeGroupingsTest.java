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

public class BadgeGroupingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeGroupings.class);
        BadgeGroupings badgeGroupings1 = new BadgeGroupings();
        badgeGroupings1.setId(1L);
        BadgeGroupings badgeGroupings2 = new BadgeGroupings();
        badgeGroupings2.setId(badgeGroupings1.getId());
        assertThat(badgeGroupings1).isEqualTo(badgeGroupings2);
        badgeGroupings2.setId(2L);
        assertThat(badgeGroupings1).isNotEqualTo(badgeGroupings2);
        badgeGroupings1.setId(null);
        assertThat(badgeGroupings1).isNotEqualTo(badgeGroupings2);
    }
}
