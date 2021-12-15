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

public class InvitesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invites.class);
        Invites invites1 = new Invites();
        invites1.setId(1L);
        Invites invites2 = new Invites();
        invites2.setId(invites1.getId());
        assertThat(invites1).isEqualTo(invites2);
        invites2.setId(2L);
        assertThat(invites1).isNotEqualTo(invites2);
        invites1.setId(null);
        assertThat(invites1).isNotEqualTo(invites2);
    }
}
