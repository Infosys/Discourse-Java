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

public class AllowedPmUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllowedPmUsers.class);
        AllowedPmUsers allowedPmUsers1 = new AllowedPmUsers();
        allowedPmUsers1.setId(1L);
        AllowedPmUsers allowedPmUsers2 = new AllowedPmUsers();
        allowedPmUsers2.setId(allowedPmUsers1.getId());
        assertThat(allowedPmUsers1).isEqualTo(allowedPmUsers2);
        allowedPmUsers2.setId(2L);
        assertThat(allowedPmUsers1).isNotEqualTo(allowedPmUsers2);
        allowedPmUsers1.setId(null);
        assertThat(allowedPmUsers1).isNotEqualTo(allowedPmUsers2);
    }
}
