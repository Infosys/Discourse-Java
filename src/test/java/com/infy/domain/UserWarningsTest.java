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

public class UserWarningsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWarnings.class);
        UserWarnings userWarnings1 = new UserWarnings();
        userWarnings1.setId(1L);
        UserWarnings userWarnings2 = new UserWarnings();
        userWarnings2.setId(userWarnings1.getId());
        assertThat(userWarnings1).isEqualTo(userWarnings2);
        userWarnings2.setId(2L);
        assertThat(userWarnings1).isNotEqualTo(userWarnings2);
        userWarnings1.setId(null);
        assertThat(userWarnings1).isNotEqualTo(userWarnings2);
    }
}
