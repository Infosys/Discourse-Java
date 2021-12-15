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

public class UserHistoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserHistories.class);
        UserHistories userHistories1 = new UserHistories();
        userHistories1.setId(1L);
        UserHistories userHistories2 = new UserHistories();
        userHistories2.setId(userHistories1.getId());
        assertThat(userHistories1).isEqualTo(userHistories2);
        userHistories2.setId(2L);
        assertThat(userHistories1).isNotEqualTo(userHistories2);
        userHistories1.setId(null);
        assertThat(userHistories1).isNotEqualTo(userHistories2);
    }
}
