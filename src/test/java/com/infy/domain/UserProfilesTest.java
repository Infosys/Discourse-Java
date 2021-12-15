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

public class UserProfilesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfiles.class);
        UserProfiles userProfiles1 = new UserProfiles();
        userProfiles1.setId(1L);
        UserProfiles userProfiles2 = new UserProfiles();
        userProfiles2.setId(userProfiles1.getId());
        assertThat(userProfiles1).isEqualTo(userProfiles2);
        userProfiles2.setId(2L);
        assertThat(userProfiles1).isNotEqualTo(userProfiles2);
        userProfiles1.setId(null);
        assertThat(userProfiles1).isNotEqualTo(userProfiles2);
    }
}
