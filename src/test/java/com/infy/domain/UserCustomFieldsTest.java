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

public class UserCustomFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCustomFields.class);
        UserCustomFields userCustomFields1 = new UserCustomFields();
        userCustomFields1.setId(1L);
        UserCustomFields userCustomFields2 = new UserCustomFields();
        userCustomFields2.setId(userCustomFields1.getId());
        assertThat(userCustomFields1).isEqualTo(userCustomFields2);
        userCustomFields2.setId(2L);
        assertThat(userCustomFields1).isNotEqualTo(userCustomFields2);
        userCustomFields1.setId(null);
        assertThat(userCustomFields1).isNotEqualTo(userCustomFields2);
    }
}
