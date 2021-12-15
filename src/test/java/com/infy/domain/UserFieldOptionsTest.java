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

public class UserFieldOptionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFieldOptions.class);
        UserFieldOptions userFieldOptions1 = new UserFieldOptions();
        userFieldOptions1.setId(1L);
        UserFieldOptions userFieldOptions2 = new UserFieldOptions();
        userFieldOptions2.setId(userFieldOptions1.getId());
        assertThat(userFieldOptions1).isEqualTo(userFieldOptions2);
        userFieldOptions2.setId(2L);
        assertThat(userFieldOptions1).isNotEqualTo(userFieldOptions2);
        userFieldOptions1.setId(null);
        assertThat(userFieldOptions1).isNotEqualTo(userFieldOptions2);
    }
}
