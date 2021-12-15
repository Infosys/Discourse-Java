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

public class UserOptionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOptions.class);
        UserOptions userOptions1 = new UserOptions();
        userOptions1.setId(1L);
        UserOptions userOptions2 = new UserOptions();
        userOptions2.setId(userOptions1.getId());
        assertThat(userOptions1).isEqualTo(userOptions2);
        userOptions2.setId(2L);
        assertThat(userOptions1).isNotEqualTo(userOptions2);
        userOptions1.setId(null);
        assertThat(userOptions1).isNotEqualTo(userOptions2);
    }
}
