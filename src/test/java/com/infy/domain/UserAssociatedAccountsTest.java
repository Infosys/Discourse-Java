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

public class UserAssociatedAccountsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAssociatedAccounts.class);
        UserAssociatedAccounts userAssociatedAccounts1 = new UserAssociatedAccounts();
        userAssociatedAccounts1.setId(1L);
        UserAssociatedAccounts userAssociatedAccounts2 = new UserAssociatedAccounts();
        userAssociatedAccounts2.setId(userAssociatedAccounts1.getId());
        assertThat(userAssociatedAccounts1).isEqualTo(userAssociatedAccounts2);
        userAssociatedAccounts2.setId(2L);
        assertThat(userAssociatedAccounts1).isNotEqualTo(userAssociatedAccounts2);
        userAssociatedAccounts1.setId(null);
        assertThat(userAssociatedAccounts1).isNotEqualTo(userAssociatedAccounts2);
    }
}
