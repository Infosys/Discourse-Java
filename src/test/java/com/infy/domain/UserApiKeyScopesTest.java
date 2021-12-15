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

public class UserApiKeyScopesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApiKeyScopes.class);
        UserApiKeyScopes userApiKeyScopes1 = new UserApiKeyScopes();
        userApiKeyScopes1.setId(1L);
        UserApiKeyScopes userApiKeyScopes2 = new UserApiKeyScopes();
        userApiKeyScopes2.setId(userApiKeyScopes1.getId());
        assertThat(userApiKeyScopes1).isEqualTo(userApiKeyScopes2);
        userApiKeyScopes2.setId(2L);
        assertThat(userApiKeyScopes1).isNotEqualTo(userApiKeyScopes2);
        userApiKeyScopes1.setId(null);
        assertThat(userApiKeyScopes1).isNotEqualTo(userApiKeyScopes2);
    }
}
