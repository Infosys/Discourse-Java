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

public class UserAuthTokensTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAuthTokens.class);
        UserAuthTokens userAuthTokens1 = new UserAuthTokens();
        userAuthTokens1.setId(1L);
        UserAuthTokens userAuthTokens2 = new UserAuthTokens();
        userAuthTokens2.setId(userAuthTokens1.getId());
        assertThat(userAuthTokens1).isEqualTo(userAuthTokens2);
        userAuthTokens2.setId(2L);
        assertThat(userAuthTokens1).isNotEqualTo(userAuthTokens2);
        userAuthTokens1.setId(null);
        assertThat(userAuthTokens1).isNotEqualTo(userAuthTokens2);
    }
}
