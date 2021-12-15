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

public class UserFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFields.class);
        UserFields userFields1 = new UserFields();
        userFields1.setId(1L);
        UserFields userFields2 = new UserFields();
        userFields2.setId(userFields1.getId());
        assertThat(userFields1).isEqualTo(userFields2);
        userFields2.setId(2L);
        assertThat(userFields1).isNotEqualTo(userFields2);
        userFields1.setId(null);
        assertThat(userFields1).isNotEqualTo(userFields2);
    }
}
