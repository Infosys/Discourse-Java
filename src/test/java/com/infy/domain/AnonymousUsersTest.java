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

public class AnonymousUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnonymousUsers.class);
        AnonymousUsers anonymousUsers1 = new AnonymousUsers();
        anonymousUsers1.setId(1L);
        AnonymousUsers anonymousUsers2 = new AnonymousUsers();
        anonymousUsers2.setId(anonymousUsers1.getId());
        assertThat(anonymousUsers1).isEqualTo(anonymousUsers2);
        anonymousUsers2.setId(2L);
        assertThat(anonymousUsers1).isNotEqualTo(anonymousUsers2);
        anonymousUsers1.setId(null);
        assertThat(anonymousUsers1).isNotEqualTo(anonymousUsers2);
    }
}
