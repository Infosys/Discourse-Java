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

public class UserAvatarsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAvatars.class);
        UserAvatars userAvatars1 = new UserAvatars();
        userAvatars1.setId(1L);
        UserAvatars userAvatars2 = new UserAvatars();
        userAvatars2.setId(userAvatars1.getId());
        assertThat(userAvatars1).isEqualTo(userAvatars2);
        userAvatars2.setId(2L);
        assertThat(userAvatars1).isNotEqualTo(userAvatars2);
        userAvatars1.setId(null);
        assertThat(userAvatars1).isNotEqualTo(userAvatars2);
    }
}
