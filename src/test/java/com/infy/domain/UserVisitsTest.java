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

public class UserVisitsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVisits.class);
        UserVisits userVisits1 = new UserVisits();
        userVisits1.setId(1L);
        UserVisits userVisits2 = new UserVisits();
        userVisits2.setId(userVisits1.getId());
        assertThat(userVisits1).isEqualTo(userVisits2);
        userVisits2.setId(2L);
        assertThat(userVisits1).isNotEqualTo(userVisits2);
        userVisits1.setId(null);
        assertThat(userVisits1).isNotEqualTo(userVisits2);
    }
}
