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

public class UserSecondFactorsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSecondFactors.class);
        UserSecondFactors userSecondFactors1 = new UserSecondFactors();
        userSecondFactors1.setId(1L);
        UserSecondFactors userSecondFactors2 = new UserSecondFactors();
        userSecondFactors2.setId(userSecondFactors1.getId());
        assertThat(userSecondFactors1).isEqualTo(userSecondFactors2);
        userSecondFactors2.setId(2L);
        assertThat(userSecondFactors1).isNotEqualTo(userSecondFactors2);
        userSecondFactors1.setId(null);
        assertThat(userSecondFactors1).isNotEqualTo(userSecondFactors2);
    }
}
