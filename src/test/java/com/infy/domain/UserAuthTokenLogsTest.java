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

public class UserAuthTokenLogsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAuthTokenLogs.class);
        UserAuthTokenLogs userAuthTokenLogs1 = new UserAuthTokenLogs();
        userAuthTokenLogs1.setId(1L);
        UserAuthTokenLogs userAuthTokenLogs2 = new UserAuthTokenLogs();
        userAuthTokenLogs2.setId(userAuthTokenLogs1.getId());
        assertThat(userAuthTokenLogs1).isEqualTo(userAuthTokenLogs2);
        userAuthTokenLogs2.setId(2L);
        assertThat(userAuthTokenLogs1).isNotEqualTo(userAuthTokenLogs2);
        userAuthTokenLogs1.setId(null);
        assertThat(userAuthTokenLogs1).isNotEqualTo(userAuthTokenLogs2);
    }
}
