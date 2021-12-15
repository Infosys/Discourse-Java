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

public class UserOpenIdsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOpenIds.class);
        UserOpenIds userOpenIds1 = new UserOpenIds();
        userOpenIds1.setId(1L);
        UserOpenIds userOpenIds2 = new UserOpenIds();
        userOpenIds2.setId(userOpenIds1.getId());
        assertThat(userOpenIds1).isEqualTo(userOpenIds2);
        userOpenIds2.setId(2L);
        assertThat(userOpenIds1).isNotEqualTo(userOpenIds2);
        userOpenIds1.setId(null);
        assertThat(userOpenIds1).isNotEqualTo(userOpenIds2);
    }
}
