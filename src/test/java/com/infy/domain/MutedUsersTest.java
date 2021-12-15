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

public class MutedUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutedUsers.class);
        MutedUsers mutedUsers1 = new MutedUsers();
        mutedUsers1.setId(1L);
        MutedUsers mutedUsers2 = new MutedUsers();
        mutedUsers2.setId(mutedUsers1.getId());
        assertThat(mutedUsers1).isEqualTo(mutedUsers2);
        mutedUsers2.setId(2L);
        assertThat(mutedUsers1).isNotEqualTo(mutedUsers2);
        mutedUsers1.setId(null);
        assertThat(mutedUsers1).isNotEqualTo(mutedUsers2);
    }
}
