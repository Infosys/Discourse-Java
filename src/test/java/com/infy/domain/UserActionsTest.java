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

public class UserActionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActions.class);
        UserActions userActions1 = new UserActions();
        userActions1.setId(1L);
        UserActions userActions2 = new UserActions();
        userActions2.setId(userActions1.getId());
        assertThat(userActions1).isEqualTo(userActions2);
        userActions2.setId(2L);
        assertThat(userActions1).isNotEqualTo(userActions2);
        userActions1.setId(null);
        assertThat(userActions1).isNotEqualTo(userActions2);
    }
}
