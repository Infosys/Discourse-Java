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

public class ApiKeysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiKeys.class);
        ApiKeys apiKeys1 = new ApiKeys();
        apiKeys1.setId(1L);
        ApiKeys apiKeys2 = new ApiKeys();
        apiKeys2.setId(apiKeys1.getId());
        assertThat(apiKeys1).isEqualTo(apiKeys2);
        apiKeys2.setId(2L);
        assertThat(apiKeys1).isNotEqualTo(apiKeys2);
        apiKeys1.setId(null);
        assertThat(apiKeys1).isNotEqualTo(apiKeys2);
    }
}
