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

public class ApiKeyScopesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiKeyScopes.class);
        ApiKeyScopes apiKeyScopes1 = new ApiKeyScopes();
        apiKeyScopes1.setId(1L);
        ApiKeyScopes apiKeyScopes2 = new ApiKeyScopes();
        apiKeyScopes2.setId(apiKeyScopes1.getId());
        assertThat(apiKeyScopes1).isEqualTo(apiKeyScopes2);
        apiKeyScopes2.setId(2L);
        assertThat(apiKeyScopes1).isNotEqualTo(apiKeyScopes2);
        apiKeyScopes1.setId(null);
        assertThat(apiKeyScopes1).isNotEqualTo(apiKeyScopes2);
    }
}
