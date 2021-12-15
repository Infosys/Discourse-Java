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

public class EmailTokensTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTokens.class);
        EmailTokens emailTokens1 = new EmailTokens();
        emailTokens1.setId(1L);
        EmailTokens emailTokens2 = new EmailTokens();
        emailTokens2.setId(emailTokens1.getId());
        assertThat(emailTokens1).isEqualTo(emailTokens2);
        emailTokens2.setId(2L);
        assertThat(emailTokens1).isNotEqualTo(emailTokens2);
        emailTokens1.setId(null);
        assertThat(emailTokens1).isNotEqualTo(emailTokens2);
    }
}
